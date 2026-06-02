# Package rename to ide.sketchware and block editor removal

This commit renames the application package from `pro.sketchware` to `ide.sketchware` across the entire codebase (applicationId, namespace, source tree, manifest, resource IDs), removes the block-project creation flow (FAB long-press, project-type dialog), and redirects the old `toDesignActivity()` entry point to the code project creator. The intent is to make Code Project the sole project type while retaining dead block-editor source for JAR compatibility.

**Watch for:** CodeProjectBuilder.java is missing from the git tree due to `.gitignore` matching the `build/` directory name — this is a **confirmed build-breaking** issue for any fresh clone. The `toDesignActivity()` redirect silently discards the `sc_id` parameter, making old block-based projects un-openable without explanation. A stale tooltip advertises a non-existent long-press action.

## High-level view

The package rename is mechanically thorough — no `pro.sketchware` references survive in tracked source, manifests, layouts, resource IDs, or build configs. The `.gitignore` pattern `build/` accidentally swallows the `codeproject/build/` package directory, which means the one file that lived there (`CodeProjectBuilder.java`) was deleted from tracking but never re-added under the new package path. This breaks compilation.

The block-editor disablement works by redirecting `toDesignActivity(sc_id)` to open `CreateCodeProjectActivity` with no extras. The `sc_id` argument is discarded, so tapping an old block project opens a blank "create project" form with no feedback. The FAB simplification is clean except for a tooltip string that still references the removed long-press behavior.

<details>
<summary>Issues (4)</summary>

1. **CodeProjectBuilder.java not tracked** — `.gitignore` rule `build/` matches `codeproject/build/` directory. The file was deleted from `pro/sketchware/...` but its replacement at `ide/sketchware/...` was never committed. Fix: `git add -f app/src/main/java/ide/sketchware/codeproject/build/CodeProjectBuilder.java`. *Severity: blocking, confirmed.*
2. **Old block projects silently un-openable** — `toDesignActivity(sc_id)` discards the `sc_id` and opens CreateCodeProjectActivity blank. Either show a toast explaining the project can't be opened, or open CodeProjectActivity in read-only mode for legacy projects. *Severity: medium, confirmed.*
3. **Stale FAB tooltip** — `code_project_long_press_hint` says "Long press for block project" but no long-press handler exists. Update or remove the tooltip. *Severity: low, confirmed.*
4. **Dead string resource** — `code_project_block_project` ("Block Project (Visual)") is unreferenced. Remove it. *Severity: low, confirmed.*

</details>

<details>
<summary>Details</summary>

## CodeProjectBuilder lost to .gitignore

The `.gitignore` at the repo root contains the pattern `build/` (line 20), intended to exclude Gradle build output directories. This pattern also matches any path segment named `build`, including the Java package `ide.sketchware.codeproject.build`. The old file at `pro/sketchware/codeproject/build/CodeProjectBuilder.java` was tracked (force-added at some point, overriding the ignore rule). When the rename deleted that tracked file, the replacement at `ide/sketchware/codeproject/build/CodeProjectBuilder.java` was never staged — git silently ignores it.

```
$ git ls-tree -r HEAD | grep "codeproject/build"
# (empty)

$ git check-ignore -v app/src/main/java/ide/sketchware/codeproject/build/CodeProjectBuilder.java
.gitignore:20:build/   ...
```

`CodeProjectActivity.java` (which IS tracked) imports and instantiates `CodeProjectBuilder`. A fresh clone will fail to compile with an unresolved symbol. The fix is `git add -f` for that file, but the broader pattern will bite again if any other source file lands in a directory named `build`. Consider narrowing the gitignore rule to `/build/` (root-only) or `app/build/` (explicit path).

## Block-project open path discards context

In `ProjectsAdapter.onBindViewHolder`, non-code projects still route through `projectsFragment.toDesignActivity(scId)`. That method now ignores its `sc_id` parameter entirely:

```java
public void toDesignActivity(String sc_id) {
    // Block editor removed - open code project instead
    Intent intent = new Intent(requireContext(), CreateCodeProjectActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    openCreateCodeProject.launch(intent);
}
```

A user with existing block-based projects sees them in the list, taps one, and lands on a blank "create project" screen with no indication of what happened. The same silent redirect triggers from the `openProjectSettings` result callback when `is_new=true` — though this path is unlikely to fire since block-project creation is removed.

At minimum, a short toast ("Block projects are no longer supported") before opening the creator would prevent confusion. A more robust approach would filter block projects out of the list or mark them visually as legacy/incompatible.

## Stale tooltip and dead string

The FAB's tooltip text reads "Long press for block project" (`R.string.code_project_long_press_hint`), set at line 154 of `ProjectsFragment`. No `setOnLongClickListener` exists on the FAB. The tooltip promises functionality that was removed. Additionally, `R.string.code_project_block_project` ("Block Project (Visual)") has zero references and is dead weight.

</details>

<details>
<summary>File map</summary>

| Path | Change |
|------|--------|
| `app/build.gradle` | applicationId + namespace `pro.sketchware` → `ide.sketchware` |
| `app/src/main/AndroidManifest.xml` | Activity/service declarations updated to `ide.sketchware.*` |
| `app/src/main/res/values/strings.xml` | App name "Sketchware Pro" → "Sketchware IDE" |
| `app/src/main/res/layout/activity_icon_creator.xml` | Custom view package reference updated |
| `app/src/main/res/layout/manage_custom_component_list_item.xml` | Custom view package reference updated |
| `public-stable-ids.txt` | Resource qualifier prefix updated |
| `app/src/main/java/pro/sketchware/codeproject/build/CodeProjectBuilder.java` | **Deleted** (518 lines) — NOT re-added under `ide/` due to .gitignore |
| `app/src/main/java/ide/sketchware/activities/main/fragments/projects/ProjectsFragment.java` | FAB simplified, `toDesignActivity` redirected |
| `app/src/main/java/com/besome/sketch/adapters/ProjectsAdapter.java` | Import renames only |
| `app/src/main/java/com/besome/sketch/design/DesignActivity.java` | Import renames only (class kept as dead code) |
| `app/src/main/java/ide/sketchware/SketchApplication.java` | Package rename from `pro.sketchware` |
| ~440 other files | Mechanical `pro.sketchware` → `ide.sketchware` in package declarations and imports |

Full diff: `git diff b0a75ab~1..b0a75ab`

</details>
