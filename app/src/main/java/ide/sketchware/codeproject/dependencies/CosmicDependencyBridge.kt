package ide.sketchware.codeproject.dependencies

import android.content.Context
import kotlinx.coroutines.runBlocking
import mod.pranav.dependency.resolver.DependencyResolver as PranavResolver
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.eventReciever
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.repositories
import java.io.File
import java.util.zip.ZipFile

/**
 * Adapts the project's existing cosmic-ide dependency resolver
 * (`org.cosmic.ide.dependency.resolver`, the same library the block editor's
 * library downloader uses) to the Code Project IDE.
 *
 * Unlike [PranavResolver], this does NOT pre-dex artifacts — it just places the
 * resolved `.jar` files into the project's `libs/resolved/` directory, because
 * `CodeProjectBuilder` dexes the whole classpath together at build time.
 */
object CosmicDependencyBridge {

    @JvmStatic
    fun resolve(
        context: Context,
        declarations: List<DependencyDeclaration>,
        outputDir: File,
        listener: DependencyResolver.ResolveListener
    ) {
        try {
            ensureRepositories()
            // The library calls into a process-wide event receiver. Save the
            // current one, install a no-op for our run, and restore it afterward
            // so we don't leave another caller's receiver pointing at our stub.
            val previousReceiver = eventReciever
            eventReciever = object : PranavResolver.DependencyResolverCallback() {}

            if (!outputDir.exists()) outputDir.mkdirs()

            val resolvedJars = ArrayList<File>()
            val errors = ArrayList<String>()
            val warnings = ArrayList<String>()
            val seen = HashSet<String>()

            try {
                runBlocking {
                    for (decl in declarations) {
                        listener.onProgress("Resolving ${decl.groupId}:${decl.artifactId}...")
                        try {
                            val artifact = getArtifact(decl.groupId, decl.artifactId, decl.version)
                            if (artifact == null) {
                                errors.add("$decl: not found in any repository")
                                continue
                            }

                            // getAllDependencies() resolves the tree internally, so the
                            // declared artifact + all transitives come back here.
                            val all = ArrayList<Artifact>()
                            all.add(artifact)
                            all.addAll(artifact.getAllDependencies())

                            for (art in all) {
                                val key = "${art.groupId}:${art.artifactId}"
                                if (!seen.add(key)) continue
                                val ext = art.extension
                                if (ext != "jar" && ext != "aar") continue
                                if (art.version.isNullOrEmpty()) continue
                                listener.onProgress("Downloading ${art.artifactId}-${art.version}...")
                                val jar = downloadJar(art, outputDir, warnings)
                                if (jar != null) resolvedJars.add(jar)
                            }
                        } catch (e: Exception) {
                            errors.add("$decl: ${e.message ?: "resolution failed"}")
                        }
                    }
                }
            } finally {
                eventReciever = previousReceiver
            }

            dispatchResult(listener, resolvedJars, errors, warnings)
        } catch (e: Exception) {
            listener.onError(e.message ?: "Dependency resolution failed")
        }
    }

    private fun dispatchResult(
        listener: DependencyResolver.ResolveListener,
        resolvedJars: List<File>,
        errors: List<String>,
        warnings: List<String>
    ) {
        val warningText = if (warnings.isEmpty()) null
        else "Warnings (build may be incomplete):\n" + warnings.joinToString("\n")

        when {
            errors.isEmpty() -> {
                listener.onComplete(resolvedJars)
                if (warningText != null) listener.onWarning(warningText)
            }
            resolvedJars.isNotEmpty() -> {
                listener.onComplete(resolvedJars)
                var msg = "Some dependencies failed to resolve:\n" + errors.joinToString("\n")
                if (warningText != null) msg += "\n\n$warningText"
                listener.onWarning(msg)
            }
            else -> {
                var msg = "All dependencies failed to resolve:\n" + errors.joinToString("\n")
                if (warningText != null) msg += "\n\n$warningText"
                listener.onError(msg)
            }
        }
    }

    private fun isCoordinateSafe(value: String): Boolean {
        val pattern = Regex("[a-zA-Z0-9._-]+")
        return value.isNotEmpty() && pattern.matches(value)
    }

    private fun ensureRepositories() {
        if (repositories.isNotEmpty()) return
        val defaults = listOf(
            "Google" to "https://dl.google.com/android/maven2",
            "Maven Central" to "https://repo.maven.apache.org/maven2",
            "JitPack" to "https://jitpack.io"
        )
        for ((repoName, url) in defaults) {
            repositories.add(object : Repository {
                override fun getName() = repoName
                override fun getURL() = url
            })
        }
    }

    /**
     * Downloads an artifact and returns the resolved `.jar` placed in [outputDir].
     * For AARs, extracts `classes.jar` and records a warning if resources/assets/
     * native libs are present (those aren't linked by the Code Project build yet).
     */
    private fun downloadJar(art: Artifact, outputDir: File, warnings: MutableList<String>): File? {
        if (!isCoordinateSafe(art.groupId) || !isCoordinateSafe(art.artifactId) || !isCoordinateSafe(art.version ?: "")) {
            warnings.add("Skipped unsafe artifact coordinates: ${art.groupId}:${art.artifactId}:${art.version}")
            return null
        }

        // Include groupId so same-named artifacts from different groups don't collide
        val base = "${art.groupId.replace('.', '_')}-${art.artifactId}-${art.version}"
        val outJar = File(outputDir, "$base.jar")

        return if (art.extension == "jar") {
            val tmp = File(outputDir, "$base.jar.tmp")
            try {
                art.downloadTo(tmp)
                if (outJar.exists()) outJar.delete()
                if (!tmp.renameTo(outJar)) {
                    tmp.copyTo(outJar, overwrite = true)
                    tmp.delete()
                }
                outJar
            } catch (e: Exception) {
                if (tmp.exists()) tmp.delete()
                throw e
            }
        } else {
            val aar = File(outputDir, "$base.aar.tmp")
            try {
                art.downloadTo(aar)
                val jar = extractClassesJar(aar, outJar, art, warnings)
                jar
            } finally {
                if (aar.exists()) aar.delete()
            }
        }
    }

    private fun extractClassesJar(
        aar: File,
        outJar: File,
        art: Artifact,
        warnings: MutableList<String>
    ): File? {
        var hasRes = false
        var hasAssets = false
        var hasJni = false
        var found = false

        ZipFile(aar).use { zip ->
            val entries = zip.entries()
            while (entries.hasMoreElements()) {
                val e = entries.nextElement()
                if (!e.isDirectory) {
                    val n = e.name
                    when {
                        n.startsWith("res/") -> hasRes = true
                        n.startsWith("assets/") -> hasAssets = true
                        n.startsWith("jni/") -> hasJni = true
                    }
                }
                if (e.name == "classes.jar") {
                    val tmp = File(outJar.parentFile, outJar.name + ".tmp")
                    zip.getInputStream(e).use { input ->
                        tmp.outputStream().use { output -> input.copyTo(output) }
                    }
                    if (outJar.exists()) outJar.delete()
                    if (!tmp.renameTo(outJar)) {
                        tmp.copyTo(outJar, overwrite = true)
                        tmp.delete()
                    }
                    found = true
                }
            }
        }

        if (hasRes || hasAssets || hasJni) {
            val dropped = buildString {
                if (hasRes) append("resources ")
                if (hasAssets) append("assets ")
                if (hasJni) append("native-libs ")
            }.trim()
            warnings.add(
                "${art.groupId}:${art.artifactId}:${art.version} is an AAR; its $dropped " +
                    "were not included (only classes). It may not work correctly at runtime."
            )
        }

        return if (found) outJar else null
    }
}
