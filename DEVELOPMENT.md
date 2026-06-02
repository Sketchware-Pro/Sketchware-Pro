# Development Guide

This document explains the architecture of this fork, how to build it, and the
constraints future work must respect. It is aimed at contributors who need to
understand *why* the codebase is shaped the way it is — especially the ongoing
migration away from the legacy block editor toward a full-code IDE.

> If you only want to build the app, jump to [Building](#building).

---

## 1. What this fork is

This is a fork of Sketchware Pro that is being refactored from a purely
drag-and-drop **block-based** app builder into a tool that *also* offers a
**Code Project** mode: a full-code IDE (file explorer, editable source with
sora-editor, direct compilation of raw `.java`/`.xml` files).

The application id and namespace were renamed from the upstream
`pro.sketchware` to **`ide.sketchware`**. New first-party code lives under the
`ide.sketchware.*` package tree.

| Setting       | Value          |
| ------------- | -------------- |
| applicationId | `ide.sketchware` |
| namespace     | `ide.sketchware` |
| compileSdk    | 36             |
| targetSdk     | 28             |
| minSdk        | 26             |
| JDK           | 17             |

---

## 2. Building

The project builds with Gradle and the Android Gradle Plugin. A JDK 17 is
required (mise is configured to provide it via `mise.toml` → `java = "17"`).

```bash
# Use the project's pinned JDK 17 (path may vary on your machine)
export JAVA_HOME="/root/.local/share/mise/installs/java/17.0.2"
export PATH="$JAVA_HOME/bin:$PATH"
# or: mise use java@17

./gradlew assembleDebug
```

The **iron rule** for any refactor: `./gradlew assembleDebug` must report
`BUILD SUCCESSFUL` after every commit. Never leave the build broken.

---

## 3. Package map

### First-party / actively maintained — `ide.sketchware.*`
| Package | Role |
| ------- | ---- |
| `ide.sketchware.codeproject.*` | The Code Project IDE (build, editor, model, template, UI). New feature work goes here. |
| `ide.sketchware.activities.resourceseditor.*` | Standalone resource editor (strings, colors, etc.). Reused independently of the block editor. |
| `ide.sketchware.activities.*` | Main screens, settings, about, icon creator, etc. |
| `ide.sketchware.util`, `ide.sketchware.utility`, `ide.sketchware.xml`, `ide.sketchware.lib` | Shared helpers (`FileUtil`, `XmlBuilder`, …). |

### Code generation / build pipeline — `a.a.a.*` (decompiled core, keep)
| Class | Role |
| ----- | ---- |
| `a.a.a.ProjectBuilder` | Compiles an entire project. |
| `a.a.a.Ix` | Generates `AndroidManifest.xml`. |
| `a.a.a.Jx` | Generates activity source code. |
| `a.a.a.Lx` | Generates component source (listeners, etc.). |
| `a.a.a.Ox` | Generates layout XML. |
| `a.a.a.qq` | Registry of built-in library dependencies. |
| `a.a.a.yq` | Organizes project file paths. |

### Contributor mods — `mod.*` (keep)
`mod.jbk.build.*`, `mod.jbk.code.*`, `mod.hey.studios.compiler.*`,
`mod.hey.studios.code.*`, `mod.bobur.*` and friends provide the compiler/build
infra and are referenced by the resource editor and build pipeline.

### Legacy block editor — being retired (see §4)
- `com.besome.sketch.editor.*` (≈133 files) — the drag-and-drop logic/view editor.
- `dev.aldi.sayuti.*` (≈39 files) — block palette, custom view items, local library management.
- `mod.hilal.saif.*` (≈18 files) — blocks manager, events/components handlers, config/settings.

> Note: `mod.hilal.saif.activities.tools.ConfigActivity` and `AppSettings`,
> and `dev.aldi.sayuti.editor.manage.*` (local library management) live in the
> "block editor" trees but are **kept** — they are general settings / build
> infrastructure, not block-only code.

### Prebuilt JARs — `app/libs/*.jar`
`com.besome.sketch-classes.jar`, `base_libs.jar`, `a.a.a-*-classes.jar`, etc.
contain compiled classes that the source tree extends/overrides. References
from these JARs are **invisible to source-level grep**, so treat them as a
hidden dependency surface when deleting classes.

---

## 4. Why the block editor cannot simply be deleted

The block editor is **not** an isolated module. It is anchored by
`com.besome.sketch.design.DesignActivity`, which must be **kept** because
non-block, actively-used code depends on it:

```
DesignActivity  (kept — cannot be deleted)
  ▲ depended on by (inbound — these import DesignActivity.sc_id):
  │   ├── ide.sketchware.activities.resourceseditor.components.adapters.StringsAdapter
  │   ├── ide.sketchware.activities.resourceseditor.components.utils.ColorsEditorManager
  │   ├── mod.jbk.build.compiler.resource.ResourceCompiler
  │   └── mod.bobur.VectorDrawableParser
  ▼ depends on (outbound):
      └── a.a.a.ViewEditorFragment  (and the editor view/logic/property trees)
```

Both directions pin `DesignActivity`: kept code depends on it (inbound), and it
reaches into the editor graph (outbound). Either alone is enough to block deletion.

Because `DesignActivity` reaches into the editor view/logic/property trees, and
because many block-editor activities are still declared in
`AndroidManifest.xml`, almost the entire `com.besome.sketch.editor.*` graph is
*live* and cannot be removed without cascading compile failures and a rewrite
of the resource editor and build pipeline. That rewrite is **out of scope** for
the incremental cleanup phases.

**Consequence:** block-editor cleanup proceeds by removing only *genuinely
orphaned* leaf classes, not whole packages.

---

## 5. Safe dead-code removal methodology ("orphan peeling")

Future cleanup phases should follow this conservative, verify-driven process.

A class is **safe to remove** only if it has zero references from *everything*:

1. No whole-word reference in any other `.java` file
   (a fully-qualified `a.b.C` string also matches because `.` is a word
   boundary — this catches reflection-by-string too).
2. No reference in any `.kt` file.
3. No custom-view tag or `tools:`/binding reference in any `.xml`
   (layouts **and** `AndroidManifest.xml`).
4. No `<activity>`/`<service>` declaration in the manifest.
5. No keep rule in `app/proguard-rules.pro`.
6. No occurrence inside `app/libs/*.jar` (scan with `unzip -p … | grep`).

Iterate to a fixpoint ("peeling"): after deleting a batch, recompute — a class
that was only referenced by now-deleted files may itself become orphaned.

```bash
# Example: list block-editor leaves with no whole-word ref anywhere in Java
JAVA_ROOT=app/src/main/java
find "$JAVA_ROOT/com/besome/sketch/editor" "$JAVA_ROOT/dev/aldi/sayuti" \
     "$JAVA_ROOT/mod/hilal/saif" -name '*.java' | while read f; do
  cls=$(basename "$f" .java)
  refs=$(grep -rlw "$cls" "$JAVA_ROOT" --include=*.java | grep -vF "$f" | wc -l)
  xml=$(grep -rlw "$cls" app/src/main --include=*.xml | wc -l)
  [ "$refs" -eq 0 ] && [ "$xml" -eq 0 ] && echo "ORPHAN: $f"
done
```

**Batch discipline:** delete a small batch → `./gradlew assembleDebug`.
- Build passes → commit the batch.
- Build fails with a 1–3 file fix → fix it.
- Build fails and the fix cascades into 10+ files → `git checkout .` and skip it.

When you remove an Activity/Service class, also remove its declaration from
`AndroidManifest.xml`. When you keep a class, leave its declaration intact.

---

## 6. Cleanup progress

| Phase | Outcome |
| ----- | ------- |
| Block-editor orphan removal (this phase) | Removed 3 fully-orphaned leaf classes (`PropertySwitchItem`, `dev.aldi.sayuti.editor.injection.ManifestInjection`, `DialogButtonGradientDrawable`) plus the now-orphaned `res/layout/property_switch_item.xml`. Peeling converged after one round; no further leaves were safely removable because the rest of the graph is pinned by `DesignActivity` and the manifest. |

### Future work
- The block editor can only be fully removed once the resource editor and
  build pipeline stop depending on `DesignActivity`/`ViewEditorFragment`.
  Decoupling those is the prerequisite for any large `com.besome.sketch.editor`
  deletion.
- Continue building out `ide.sketchware.codeproject.*` as the primary authoring
  experience.

---

## 7. Contributing conventions

- Prefer **Java** over Kotlin for new code unless Kotlin is clearly warranted
  (the build supports both).
- Put new first-party features under `ide.sketchware.*`, respecting existing
  directory/file naming.
- Commit message prefixes: `feat:`, `fix:`, `style:`, `refactor:`, `test:`,
  `docs:`, `chore:`.
- Always run `./gradlew assembleDebug` before committing.
