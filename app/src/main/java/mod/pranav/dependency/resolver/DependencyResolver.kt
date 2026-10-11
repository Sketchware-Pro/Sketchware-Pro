package mod.pranav.dependency.resolver

import android.os.Environment
import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import mod.hey.studios.build.BuildSettings
import mod.hey.studios.util.Helper
import mod.jbk.build.BuiltInLibraries
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.EventReciever
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.eventReciever
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.repositories
import pro.sketchware.utility.FileUtil
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.regex.Pattern
import java.util.zip.ZipFile
import kotlin.io.path.readText
import kotlin.io.path.writeText

class DependencyResolver(
    private val groupId: String,
    private val artifactId: String,
    private val version: String,
    private val skipDependencies: Boolean,
    private val buildSettings: BuildSettings
) {
    companion object {
        private const val GOOGLE_MAVEN_URL = "https://dl.google.com/dl/android/maven2"

        private val DEFAULT_REPOS = """
          |[
          |    {"url": "$GOOGLE_MAVEN_URL", "name": "Google Maven"},
          |    {"url": "https://repo.hortonworks.com/content/repositories/releases", "name": "HortanWorks"},
          |    {"url": "https://maven.atlassian.com/content/repositories/atlassian-public", "name": "Atlassian"},
          |    {"url": "https://jcenter.bintray.com", "name": "JCenter"},
          |    {"url": "https://oss.sonatype.org/content/repositories/releases", "name": "Sonatype"},
          |    {"url": "https://repo.spring.io/plugins-release", "name": "Spring Plugins"},
          |    {"url": "https://repo.spring.io/libs-milestone", "name": "Spring Milestone"},
          |    {"url": "https://repo.maven.apache.org/maven2", "name": "Apache Maven"}
          |]
        """.trimMargin()
    }

    private val downloadPath: String =
        FileUtil.getExternalStorageDir() + "/.sketchware/libs/local_libs"

    private val repositoriesJson = Paths.get(
        Environment.getExternalStorageDirectory().absolutePath,
        ".sketchware",
        "libs",
        "repositories.json"
    )

    init {
        if (Files.notExists(repositoriesJson)) {
            Files.createDirectories(repositoriesJson.parent)
            repositoriesJson.writeText(DEFAULT_REPOS)
        }
        val configuredRepositories: ArrayList<HashMap<String, Any>> =
            Gson().fromJson(repositoriesJson.readText(), Helper.TYPE_MAP_LIST)
        if (configuredRepositories.none { it["url"] == GOOGLE_MAVEN_URL }) {
            configuredRepositories.add(
                hashMapOf("url" to GOOGLE_MAVEN_URL, "name" to "Google Maven")
            )
            repositoriesJson.writeText(Gson().toJson(configuredRepositories))
        }
        configuredRepositories.forEach {
            val url: String? = it["url"] as String?
            if (url != null) {
                repositories.add(object : Repository {
                    override fun getName(): String {
                        return it["name"] as String
                    }

                    override fun getURL(): String {
                        return if (url.endsWith("/")) {
                            url.substringBeforeLast("/")
                        } else {
                            url
                        }
                    }
                })
            }
        }
    }

    open class DependencyResolverCallback : EventReciever() {
        override fun artifactFound(artifact: Artifact) {}
        override fun onArtifactNotFound(artifact: Artifact) {}
        override fun onFetchingLatestVersion(artifact: Artifact) {}
        override fun onFetchedLatestVersion(artifact: Artifact, version: String) {}
        override fun onResolving(artifact: Artifact, dependency: Artifact) {}
        override fun onResolutionComplete(artifact: Artifact) {}
        override fun onSkippingResolution(artifact: Artifact) {}
        override fun onVersionNotFound(artifact: Artifact) {}
        override fun onDependenciesNotFound(artifact: Artifact) {}
        override fun onInvalidScope(artifact: Artifact, scope: String) {}
        override fun onInvalidPOM(artifact: Artifact) {}
        override fun onDownloadStart(artifact: Artifact) {}
        override fun onDownloadEnd(artifact: Artifact) {}
        override fun onDownloadError(artifact: Artifact, error: Throwable) {}
        open fun unzipping(artifact: Artifact) {}
        open fun dexing(artifact: Artifact) {}
        open fun onTaskCompleted(artifacts: List<String>) {}
        open fun dexingFailed(artifact: Artifact, e: Exception) {}
        open fun invalidPackaging(artifact: Artifact) {}
        open fun onResolutionError(error: Exception) {}
    }

    fun resolveDependency(callback: DependencyResolverCallback) = runBlocking {
        eventReciever = callback
        val requestedArtifact = try {
            getArtifact(groupId, artifactId, version)
        } catch (error: Exception) {
            callback.onResolutionError(error)
            return@runBlocking
        } ?: return@runBlocking
        val dependency = if (requestedArtifact.extension == "pom") {
            findJvmVariant(requestedArtifact) ?: requestedArtifact
        } else {
            requestedArtifact
        }

        if (dependency.extension != "jar" && dependency.extension != "aar") {
            callback.invalidPackaging(dependency)
            return@runBlocking
        }

        val libraryJars = listOf(
            BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.toPath()
                .resolve("core-lambda-stubs.jar"), Paths.get(
                buildSettings.getValue(
                    BuildSettings.SETTING_ANDROID_JAR_PATH,
                    BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.resolve("android.jar").absolutePath
                )
            )
        )
        val dependencyClasspath = mutableListOf<Path>()

        val classpath = buildSettings.getValue(BuildSettings.SETTING_CLASSPATH, "")

        classpath.split(":").forEach {
            if (it.isEmpty()) return@forEach
            dependencyClasspath.add(Paths.get(it))
        }

        val dependencyDir = File(downloadPath, "${dependency.artifactId}-v${dependency.version}")
        val downloaded = try {
            downloadVerified(dependency, File(dependencyDir, "classes.${dependency.extension}"))
        } catch (e: Exception) {
            dependencyDir.deleteRecursively()
            callback.onDownloadError(dependency, e)
            return@runBlocking
        }
        if (!downloaded) {
            // Artifact#downloadTo already reported the failure through onDownloadError.
            dependencyDir.deleteRecursively()
            return@runBlocking
        }

        if (dependency.extension == "aar") {
            callback.unzipping(dependency)
            unzip(
                Paths.get(
                    downloadPath,
                    "${dependency.artifactId}-v${dependency.version}",
                    "classes.aar"
                )
            )
            Files.delete(
                Paths.get(
                    downloadPath,
                    "${dependency.artifactId}-v${dependency.version}",
                    "classes.aar"
                )
            )
            val packageName = findPackageName(
                Paths.get(downloadPath, "${dependency.artifactId}-v${dependency.version}")
                    .toAbsolutePath().toString(),
                dependency.groupId
            )
            Paths.get(downloadPath, "${dependency.artifactId}-v${dependency.version}", "config")
                .writeText(packageName)
        }

        val jar = Paths.get(
            downloadPath,
            "${dependency.artifactId}-v${dependency.version}",
            "classes.jar"
        )

        callback.dexing(dependency)
        try {
            compileJar(jar, dependencyClasspath, libraryJars)
            callback.onResolutionComplete(dependency)
        } catch (e: Exception) {
            dependencyDir.deleteRecursively()
            callback.dexingFailed(dependency, e)
            return@runBlocking
        }

        if (skipDependencies) {
            callback.onSkippingResolution(dependency)
            callback.onTaskCompleted(listOf("${dependency.artifactId}-v${dependency.version}"))
            return@runBlocking
        }
        dependency.resolveDependencyTree()

        val allDependencies = dependency.getAllDependencies().toMutableList()
        // Kotlin Multiplatform root artifacts (packaging "pom", e.g. kotlinx-coroutines-core) contain no code,
        // it lives in their "-jvm" sibling, which isn't always listed as a dependency in the POM.
        allDependencies.filter { it.extension == "pom" }.forEach { pomArtifact ->
            val jvm = findJvmVariant(pomArtifact) ?: return@forEach
            jvm.resolveDependencyTree()
            (listOf(jvm) + jvm.getAllDependencies()).forEach { candidate ->
                if (allDependencies.none { it.groupId == candidate.groupId && it.artifactId == candidate.artifactId }) {
                    allDependencies.add(candidate)
                }
            }
        }

        allDependencies.forEach { dep ->
            println("Resolving dependency: ${dep.artifactId} v${dep.version}")
            if (dep.extension != "jar" && dep.extension != "aar") {
                callback.invalidPackaging(dep)
                return@forEach
            }

            if (dep.version.isEmpty()) {
                callback.onVersionNotFound(dep)
                return@forEach
            }

            val path = Paths.get(
                downloadPath,
                "${dep.artifactId}-v${dep.version}",
                "classes.${dep.extension}"
            )

            Files.createDirectories(path.parent)

            val downloaded = try {
                downloadVerified(dep, File(path.toString()))
            } catch (e: Exception) {
                path.parent.toFile().deleteRecursively()
                callback.onDownloadError(dep, e)
                return@runBlocking
            }
            if (!downloaded) {
                path.parent.toFile().deleteRecursively()
                return@runBlocking
            }

            if (dep.extension == "aar") {
                callback.unzipping(dep)
                unzip(path)
                Files.delete(path)
                val packageName =
                    findPackageName(path.parent.toAbsolutePath().toString(), dep.groupId)
                path.parent.resolve("config").writeText(packageName)
            }

            val jar = if (dep.extension == "jar") path else Paths.get(
                downloadPath, "${dep.artifactId}-v${dep.version}", "classes.jar"
            )
            if (Files.notExists(jar)) {
                callback.onDependenciesNotFound(dep)
                return@forEach
            }

            dependencyClasspath.add(jar)
        }

        allDependencies.forEach { dep ->
            val jar = Paths.get(downloadPath, "${dep.artifactId}-v${dep.version}", "classes.jar")

            // Resource-only AARs have no classes.jar, so there is nothing to dex.
            if (Files.notExists(jar)) return@forEach

            callback.dexing(dep)
            try {
                compileJar(
                    jar, dependencyClasspath.toMutableList().apply { remove(jar) }, libraryJars
                )
                callback.onResolutionComplete(dep)
            } catch (e: Exception) {
                jar.parent.toFile().deleteRecursively()
                callback.dexingFailed(dep, e)
                return@runBlocking
            }
        }

        callback.onTaskCompleted(
            allDependencies.map { "${it.artifactId}-v${it.version}" })
    }

    /**
     * Downloads [artifact] to a temporary file and only moves it to [target] once it was verified
     * to be a valid zip (JARs and AARs both are). This keeps HTML error pages from dead repositories
     * and truncated downloads from being stored as libraries.
     *
     * Artifact#downloadTo swallows its own errors and reports them via onDownloadError, leaving an
     * empty file behind. In that case this returns false and nothing more needs to be reported.
     *
     * @return true if [target] now holds a valid file
     * @throws IOException if the downloaded content is not a valid zip
     */
    private fun downloadVerified(artifact: Artifact, target: File): Boolean {
        target.parentFile?.mkdirs()
        val temp = File(target.parentFile, target.name + ".part")
        temp.delete()
        try {
            artifact.downloadTo(temp)
            if (!temp.isFile || temp.length() == 0L) {
                return false
            }
            try {
                ZipFile(temp).close()
            } catch (e: Exception) {
                throw IOException("The file downloaded for $artifact is not a valid ${artifact.extension}: ${e.message}", e)
            }
            Files.move(temp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
            return true
        } finally {
            temp.delete()
        }
    }

    /**
     * Finds the "-jvm" sibling of a Kotlin Multiplatform root artifact. Looks it up silently, so artifacts
     * without one (e.g. plain BOMs) don't trigger "not found" errors.
     */
    private fun findJvmVariant(artifact: Artifact): Artifact? {
        val jvm = Artifact(artifact.groupId, "${artifact.artifactId}-jvm", artifact.version)
        jvm.repository = repositories.firstOrNull { it.checkExists(jvm) } ?: return null
        val pom = jvm.getPOM() ?: return null
        jvm.extension = if (pom.packaging != null && pom.packaging != "bundle") pom.packaging else "jar"
        return if (jvm.extension == "jar" || jvm.extension == "aar") jvm else null
    }

    private fun findPackageName(path: String, defaultValue: String): String {
        val manifest =
            File(path).walk().filter { it.isFile && it.name == "AndroidManifest.xml" }.firstOrNull()
        val content = manifest?.readText() ?: return defaultValue
        val p = Pattern.compile("<manifest.*package=\"(.*?)\"", Pattern.DOTALL)
        val m = p.matcher(content)
        if (m.find()) {
            return m.group(1)!!
        }

        return defaultValue
    }

    private fun unzip(path: Path) {
        val zipFile = ZipFile(path.toFile())
        zipFile.use { zip ->
            zip.entries().asSequence().forEach { entry ->
                val entryDestination = path.parent.resolve(entry.name)
                if (entry.isDirectory) {
                    Files.createDirectories(entryDestination)
                } else {
                    Files.createDirectories(entryDestination.parent)
                    zip.getInputStream(entry).use { input ->
                        Files.newOutputStream(entryDestination).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }
    }

    private fun compileJar(jarFile: Path, jars: List<Path>, libraryJars: List<Path>) {
        Files.createDirectories(jarFile.parent)
        D8.run(
            D8Command.builder().setIntermediate(true).setMode(CompilationMode.RELEASE)
                .addProgramFiles(jarFile).addLibraryFiles(libraryJars).addClasspathFiles(jars)
                .setOutput(jarFile.parent, OutputMode.DexIndexed).build()
        )
    }
}
