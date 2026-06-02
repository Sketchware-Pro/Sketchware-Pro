# Swap hand-rolled Maven resolver for cosmic-ide resolver reuse

The Code Project dependency feature drops its own Maven stack (`PomParser`, `ArtifactDownloader`, `MavenRepository`, ~734 lines) and routes resolution through the project's existing `org.cosmic.ide.dependency.resolver` library — the same one the block editor's `LibraryDownloaderDialogFragment` drives via `mod.pranav.dependency.resolver.DependencyResolver`. `DependencyResolver.java` becomes a thin facade that keeps `ResolveListener` and `parseDependenciesFile()` and forwards `resolve()` to the new `CosmicDependencyBridge.kt`. The bridge resolves each declared coordinate plus its transitive tree, downloads jar/aar, extracts `classes.jar` from AARs (warning on dropped res/assets/jni), and writes `group_artifact-version.jar` into `libs/resolved/` without pre-dexing, since `CodeProjectBuilder` dexes the whole classpath at build time.

**Watch for:** the bridge overwrites the library's **global** `eventReceiver` with a no-op (confirmed) — if a block-editor library download runs concurrently with a Code Project sync, the block editor's progress/error callbacks silently route to the no-op; `ensureRepositories()` doesn't do what it claims because the library pre-seeds Google/Central/JitPack under different hostnames, so it ends up appending duplicate mirrors to a shared global (confirmed); and version-conflict resolution semantics shifted from "first-wins everywhere" to "newest-wins within a tree, first-wins across declarations" (confirmed).

## High-level view

The swap itself is the right move: the deleted code was a partial reimplementation of POM parsing, repository fallback, transitive resolution, and scope filtering that the cosmic-ide library already does (and the block editor already depends on). Output naming, the `.resolved_tmp` swap-on-success, and the not-pre-dexing decision are carried over intact.

The resolve sequence — `getArtifact` → `resolveDependencyTree` → `getAllDependencies` → `downloadTo` — is used correctly, and the ordering question (must `downloadTo` precede `resolveDependencyTree`?) resolves to no: `getArtifact` already fetches the POM and sets each artifact's repository and extension, and `downloadTo` only fetches the binary, independent of tree resolution. pranav downloads the main artifact first only to dex incrementally, not as a precondition. The one wrinkle is that `getAllDependencies()` internally re-runs `resolveDependencyTree()`, so the explicit pre-call resolves the tree twice.

The real risk is shared mutable global state in the library. `eventReceiver` is a single process-wide `var`; both resolvers assign it without synchronization, so concurrent runs clobber each other. `repositories` is a process-wide pre-seeded queue; `ensureRepositories()` mutates it on every sync and, because its host check misses the pre-seeded Google/Central mirrors, leaves duplicate entries that the block editor's resolver then iterates too.

Version selection changed in a subtle way. The library collapses duplicate `group:artifact` within a tree to the newest version; the bridge's own first-wins dedup set only governs collisions across separately-resolved top-level declarations. The deleted resolver was uniformly first-wins, so declaration order can now pick an older shared transitive.

One smaller delta: there's no longer a download cache, so repeat syncs re-fetch from the network.

<details>
<summary>Issues (7)</summary>

1. **eventReceiver clobbering (MEDIUM, confirmed/likely)** — the bridge overwrites the library's global `eventReceiver`; a concurrent block-editor download loses its progress/error callbacks. Guard against concurrent runs or save/restore the receiver around the bridge's resolution.
2. **ensureRepositories is a no-op that adds duplicate mirrors (LOW, confirmed)** — the library pre-seeds Google/Central/JitPack under different hostnames, so the host check adds a second Google (`dl.google.com`) and second Central (`repo.maven.apache.org`) to the shared global. Drop the function or match against the pre-seeded URLs.
3. **Version-conflict semantics shifted (LOW, confirmed)** — newest-wins within a tree but first-wins across declarations, vs uniformly first-wins before. Confirm this is acceptable; if a deterministic global policy is wanted, dedup before resolving.
4. **Redundant tree resolution (LOW, confirmed)** — `getAllDependencies()` already calls `resolveDependencyTree()`, so the explicit pre-call resolves the main tree twice (double POM fetches). Remove the explicit `artifact.resolveDependencyTree()` line.
5. **No artifact cache (LOW, confirmed)** — the deleted `ArtifactDownloader` cached under `maven_cache`; the bridge re-downloads every sync. Acceptable but slower/more bandwidth on repeat syncs.
6. **Resource-only AAR emits a warning but no error (LOW, confirmed)** — an AAR with no `classes.jar` produces the dropped-payload warning and returns null, contributing nothing without a hard error. Edge case; consider surfacing as an error.

</details>

<details>
<summary>Details</summary>

### Resolve sequence and the download-ordering question

`getArtifact(group, artifact, version)` runs `initHost` (which probes the registered repositories, fetches the POM, and sets `repository`) and sets `extension` from POM packaging — `aar` stays `aar`; `bundle` and `pom`-only parents collapse so the bridge's `ext != "jar" && ext != "aar"` filter skips them, matching the old `extension == null` skip. So by the time the bridge calls `downloadTo`, the main artifact already has the repository and extension it needs; `downloadTo` throws `IllegalStateException` if `repository` is null and otherwise fetches the binary purely from the repo URL, independent of tree resolution. Transitive artifacts get their repository/extension set during `resolveDependencyTree`/`getAllDependencies`. pranav downloads the main artifact before resolving the tree only so it can dex incrementally; it is not a precondition, so the bridge's order (resolve the tree, then download main + transitives) holds.

One redundancy: decompiling `Artifact.getAllDependencies` shows it calls `resolveDependencyTree$default` itself before flattening. The bridge calls `artifact.resolveDependencyTree()` explicitly and *then* `artifact.getAllDependencies()`, so the main artifact's tree is resolved twice — double the POM network round-trips for that subtree. (pranav is worse, calling `getAllDependencies()` three times, so this is not a regression.) The explicit `resolveDependencyTree()` line can be deleted with no behavioral change.

### eventReceiver clobbering across resolvers

`eventReceiver` is a single global `var` in the library's `UtilsKt`, shared by every caller in the process. The bridge sets it to a no-op `DependencyResolverCallback`; pranav sets it to a callback that drives the block editor's UI (`unzipping`, `dexing`, `onDownloadStart/End`, `onDownloadError`, `onTaskCompleted`). Both run on their own background threads (`CodeProjectActivity` spawns a `Thread`; `LibraryDownloaderDialogFragment` does too), and neither synchronizes on the global.

```text
# concurrency/sequence diagram pseudocode
Code Project sync thread          Block editor download thread
  eventReceiver = no-op   ----X---->  (pranav had set its callback)
  ... resolve ...                     ... downloadTo() fires onDownloadStart
                                          -> goes to the no-op, UI never updates
```

If both flows overlap, whichever assigns last wins for the whole process. The bridge clobbering pranav's callback means the block editor's progress and, more importantly, `onDownloadError` events vanish into the no-op while its download keeps running — the UI looks frozen and failures go unreported. Concurrency requires the user to trigger both at once, so impact is "likely" rather than guaranteed, but the global is genuinely shared. Saving and restoring `eventReceiver` around the bridge's `runBlocking`, or gating the two flows so they can't overlap, would close it.

### Repository seeding duplicates pre-seeded mirrors

The library's static initializer pre-seeds the global `repositories` queue with `MavenCentral` (`repo1.maven.org/maven2`), `GoogleMaven` (`maven.google.com`), `Jitpack` (`jitpack.io`), and `SonatypeSnapshots`. `ensureRepositories()` intends to guarantee Google/Central/JitPack are present, checking `repositories.none { it.getURL().contains(host) }` for hosts `dl.google.com`, `repo.maven.apache.org`, and `jitpack.io`. Only JitPack matches the pre-seeded entry. The Google check looks for `dl.google.com` but the pre-seeded Google mirror is `maven.google.com`, and the Central check looks for `repo.maven.apache.org` but the pre-seeded one is `repo1.maven.org` — so the bridge appends a *second* Google and a *second* Central, each a different mirror of the same content, to the shared global.

For androidx the resolver walks the queue and `checkExists` picks the first hosting repo, which is the pre-seeded `GoogleMaven`; the bridge's `dl.google.com` entry is never reached and is dead weight. The function doesn't achieve its stated purpose (the repos were already present), and it permanently grows a shared global that the block editor's resolver also iterates on every download. Either drop `ensureRepositories()` or compare against the pre-seeded URLs rather than the bridge's own hostnames.

### Version-conflict resolution: newest-within-tree vs first-across-declarations

`getAllDependencies()` keys duplicates by `Pair(groupId, artifactId)` and keeps the newer version (via `getNewerVersion`), so a single declared coordinate's transitive closure never contains two versions of the same `group:artifact`. The bridge then layers its own `seen` set keyed by `"$group:$artifactId"` with first-wins semantics — but since each tree is already collapsed, that set only decides collisions *across* separately-resolved top-level declarations.

Net: within one declaration's tree the newest version wins; across two declarations sharing a transitive, the first-declared declaration's resolved version wins regardless of which is newer. The deleted resolver used one `resolvedSet` spanning all declarations and recursion, so it was uniformly first-wins in DFS order. The new behavior is usually an improvement (proper newest-wins inside each tree), but it's a real semantic change: declaration order in `dependencies.txt` can now select an older version of a shared transitive than another declaration would have pulled. A pre-resolution dedup of the declared list would make the policy deterministic.

### AAR extraction: resource-only edge case

The jar path downloads to `base.jar.tmp` and renames to the final name; the AAR path now extracts `classes.jar` to a temp file and renames it into place too. Both paths are also masked by the outer `.resolved_tmp` swap before anything reaches `libs/resolved/`.

The full-archive scan is correct: it iterates every entry (no early return) so `res/`, `assets/`, and `jni/` are detected even though `classes.jar` typically precedes them. One edge case: the dropped-payload warning is appended based on the scan regardless of whether `classes.jar` was found, so a resource-only AAR (no `classes.jar`) emits the "resources were not included" warning and returns null — contributing no jar and no hard error, only a warning. Rare, but such a dependency silently produces nothing buildable.

### Warning path should stay distinct from errors

When there are warnings but no errors, `dispatchResult` should complete the sync and surface the warning separately rather than routing the warning through `onError`. Keeping warning UI distinct avoids double dismissal and avoids treating a successfully swapped dependency set as a failed sync.

</details>

<details>
<summary>File map</summary>

- `CosmicDependencyBridge.kt` (new) — adapts cosmic-ide `getArtifact`/`resolveDependencyTree`/`getAllDependencies`/`downloadTo` to the Code Project; AAR `classes.jar` extraction + dropped-payload warnings; first-wins cross-declaration dedup; `ensureRepositories` seeding.
- `DependencyResolver.java` (modified) — reduced to a facade: keeps `ResolveListener` and `parseDependenciesFile()`, forwards `resolve()` to the bridge.
- `ArtifactDownloader.java`, `MavenRepository.java`, `PomParser.java` (deleted) — hand-rolled Maven internals replaced by the library.
- Unchanged: `DependencyDeclaration.java` (still parses/validates coordinates); `CodeProjectActivity.syncDependencies()` (worker thread, `.resolved_tmp` swap-on-success, UI marshaling).

Full diff: `git diff HEAD` on `feat/gradle-dependency-resolver` plus the untracked `CosmicDependencyBridge.kt`.

</details>
