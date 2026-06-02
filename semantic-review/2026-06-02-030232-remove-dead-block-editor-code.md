# Remove three orphaned block-editor leaf classes + add DEVELOPMENT.md

This change deletes three dead leaf classes from the legacy block-editor trees
(`PropertySwitchItem`, `ManifestInjection`, `DialogButtonGradientDrawable`) and adds
a `DEVELOPMENT.md` plus a `README.md` refresh documenting the `pro.sketchware` →
`ide.sketchware` rename and the architectural reason the block editor can't be fully
removed. The deletions are deliberately conservative: rather than dropping whole
packages, the author peeled off only classes with zero inbound references, because
`com.besome.sketch.design.DesignActivity` pins almost the entire editor graph and is
itself depended on by the kept resource editor and build pipeline. I independently
re-verified all three deletions against source, layouts, the manifest, proguard rules,
the reflection surface, and the prebuilt JARs — they are genuinely orphaned and safe to
remove.

**Watch for:** the layout `res/layout/property_switch_item.xml` is now orphaned (confirmed) — the deleted `PropertySwitchItem` was its only consumer, and it was left behind by this cleanup. The DEVELOPMENT.md §4 coupling diagram draws its dependency arrows backwards for 4 of its 5 edges (confirmed) — the listed classes depend on `DesignActivity`, not the reverse, though the diagram's *conclusion* (keep `DesignActivity`) is still correct. No runtime/reflection reference to any deleted class survives anywhere in the tree (confirmed).

## High-level view

The deletion safety question is settled: a whole-word and fully-qualified search across every `.java`, `.kt`, `.xml` (layouts + `AndroidManifest.xml`), and `proguard-rules.pro` returns zero hits for all three classes, and the only near-matches (`PropertySwitchItemSinglelineBinding`, `AndroidManifestInjection`) are unrelated kept classes that merely share a substring. The build passing is not the load-bearing evidence here — the reflection path is.

The runtime/reflection angle is the one that a green build can't vouch for, and it checks out. The codebase instantiates views dynamically via `InvokeUtil.createView(Class.forName(name))`, but `name` comes from saved user view-beans (placeable widgets), and none of the three deleted classes are user-placeable widgets — one is an internal property-row view, one a no-op manifest data holder, one a drawable helper. There are also zero class-name string literals for them anywhere, including assets and JSON config, and zero references inside any of the eight `app/libs/*.jar` files (the JAR scan was validated against a known symbol).

The one real loose end is a resource, not code: `property_switch_item.xml` survived even though its sole consumer was deleted, so the cleanup left a dead layout behind. It doesn't break the build (unused resources are harmless) but it contradicts the commit's stated goal of removing the orphaned material completely.

On documentation, the DEVELOPMENT.md narrative is accurate and useful, but the ASCII coupling diagram in §4 inverts the dependency direction for the resource-editor classes, `VectorDrawableParser`, and `ResourceCompiler`: those import `DesignActivity.sc_id`, so they depend on `DesignActivity` rather than being reached into by it. Only `ViewEditorFragment` flows in the direction the diagram shows.

<details>
<summary>Issues (3)</summary>

1. **Orphaned layout left behind** — `res/layout/property_switch_item.xml` lost its only consumer when `PropertySwitchItem` was deleted; it has no `@layout/` include, no `R.layout.property_switch_item` reference, and no generated-binding use. Delete it (and confirm no `PropertySwitchItemBinding` is referenced) to finish the orphan removal. Low.
2. **Coupling diagram direction inverted** — DEVELOPMENT.md §4 lists `StringsAdapter`, `ColorsEditorManager`, `ResourceCompiler`, `VectorDrawableParser` as children of `DesignActivity`, but those classes depend on `DesignActivity` (via `import static …DesignActivity.sc_id`), not the reverse. Flip the arrows or relabel the diagram as "coupling involving DesignActivity"; only `ViewEditorFragment` is a genuine outbound dependency. Low.
3. **JAR reference surface is a documented blind spot** — verified clean for these three classes, but future phases relying solely on source grep should keep scanning `app/libs/*.jar`, as DEVELOPMENT.md §5 already prescribes. Low (informational).

</details>

<details>
<summary>Details</summary>

### Deletion safety: the three classes are genuinely unreferenced

Each deleted class was checked by whole-word name and by fully-qualified package path
across the entire source tree, not just trusted from the commit message. The only
superficial collisions are with unrelated kept classes:

```
PropertySwitchItem          → only PropertySwitchItemSinglelineBinding (a kept
                              data-binding class used by PropertyAttributesItem)
ManifestInjection           → only AndroidManifestInjection / …Details (kept activity,
                              different package: mod.hilal.saif.activities.android_manifest)
DialogButtonGradientDrawable → no matches of any kind
```

The sibling classes in the affected packages stay live: `AppCompatInjection` (same
`dev.aldi.sayuti.editor.injection` package, referenced from `a.a.a.Ox` and
`ManageAppCompatActivity`), and `AsdDialog` / `AsdHandlerCodeEditor` (same
`mod.hilal.saif.asd` package), so the deletions are leaf-level, not package-level. The
deleted `ManifestInjection` was in any case a stub — its one method read a file and
discarded the result (`FileUtil.readFile(path).isEmpty();` with no use of the return
value), so it carried no live behavior even if it had been wired. A repo-wide sweep
across all tracked file types (excluding `app/build/`) confirms the only surviving
textual occurrence of the three names is inside DEVELOPMENT.md itself.

### The reflection/runtime question — why a green build isn't the proof

The real risk in this codebase is a string-based reflection lookup that compiles fine
and fails only at runtime. `InvokeUtil` is the relevant surface:

```java
public static View createView(Context context, String name) {
    // name with a "." → Class.forName(name); else try android.widget/view/webkit prefixes
}
```

`createView` is driven by view-bean class names from saved user projects (placeable
widgets), routed through `ViewBeanParser` / `ViewPane`. None of the three deleted classes
are user-placeable widgets: `PropertySwitchItem` is an internal property-editor row,
`ManifestInjection` a data holder, `DialogButtonGradientDrawable` a `GradientDrawable`
helper. Beyond that architectural argument, the direct evidence is that there are zero
string literals naming any of the three anywhere in the tree — including `assets/`,
`res/values`, and `.json` config that could feed a reflective lookup. So there is no
path by which `Class.forName` could be handed one of these names. Confidence: confirmed.

### Prebuilt JAR reference surface — verified, not just flagged

DEVELOPMENT.md §5 correctly calls out `app/libs/*.jar` as invisible to source grep. I
scanned all eight JARs by extracting string constants (`unzip -p | strings | grep`),
covering both dotted and slashed (`com/besome/…`) bytecode forms. Zero references to any
of the three classes. The scan was validated against a known symbol
(`com/besome/sketch/editor/logic/BlockPane` was found in `com.besome.sketch-classes.jar`),
so the empty result is a true negative rather than a broken pipe. Confidence: confirmed.

### Orphaned layout left behind: property_switch_item.xml

The deleted `PropertySwitchItem.initialize()` inflated `R.layout.property_switch_item`.
That layout file still exists at `app/src/main/res/layout/property_switch_item.xml` and is
now dead: no `R.layout.property_switch_item` reference in any `.java`/`.kt` (the word
boundary excludes `property_switch_item_singleline`), no `@layout/property_switch_item`
include in any XML, and no use of a generated `PropertySwitchItemBinding`. Its structure
(`tv_name`, `tv_desc`, `switch_value` in a `RelativeLayout`) mirrors the deleted class's
field bindings exactly, confirming the class was its only consumer.

Harmless to the build — an unused layout still gets an `R` id and is simply never
inflated — but the commit's stated goal was to remove the orphaned block-editor material,
and this resource is the matching orphan of a class that was removed. The *singleline*
sibling is unaffected: `property_switch_item_singleline.xml` is inflated by the kept
`PropertySwitchSingleLineItem`, which `ViewPropertyItems` still instantiates and
`instanceof`-checks. Confidence: confirmed.

### DEVELOPMENT.md coupling diagram inverts the dependency direction

§4 presents the keep-`DesignActivity` rationale with this tree:

```
DesignActivity  (kept)
  ├── …resourceseditor…StringsAdapter
  ├── …resourceseditor…ColorsEditorManager
  ├── mod.jbk.build.compiler.resource.ResourceCompiler
  ├── mod.bobur.VectorDrawableParser
  └── a.a.a.ViewEditorFragment
```

Read as a dependency tree (parent depends on children), four of the five edges point the
wrong way. `StringsAdapter`, `ColorsEditorManager`, and `VectorDrawableParser` each begin
with `import static com.besome.sketch.design.DesignActivity.sc_id;` — they depend on
`DesignActivity`. `DesignActivity` itself imports `ResourcesEditorActivity`, not those
adapter/util classes. Only `ViewEditorFragment` is a true outbound dependency
(`DesignActivity` imports, fields, and instantiates it). The inversion doesn't weaken the
§4 conclusion — inbound dependents are exactly why `DesignActivity` can't be deleted — but
the diagram as drawn contradicts the code. Relabeling it as "coupling involving
DesignActivity" or flipping the four inbound arrows would make it accurate.

</details>

<details>
<summary>File map</summary>

- `DEVELOPMENT.md` — new architecture/build/cleanup guide; coupling diagram in §4 has inverted arrow direction (see review).
- `README.md` — fork-direction note, JDK 17 build requirement, `pro.sketchware` → `ide.sketchware` correction, DEVELOPMENT.md link. No contradictions with code.
- `app/src/main/java/com/besome/sketch/editor/property/PropertySwitchItem.java` — deleted (orphaned; its layout `property_switch_item.xml` still remains and is now dead).
- `app/src/main/java/dev/aldi/sayuti/editor/injection/ManifestInjection.java` — deleted (orphaned no-op stub).
- `app/src/main/java/mod/hilal/saif/asd/DialogButtonGradientDrawable.java` — deleted (orphaned helper).

Full diff: `git diff 95384cf..HEAD`
</details>
