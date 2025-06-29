package mod.pranav.dependency.resolver

import android.net.Uri
import android.os.Build
import android.os.Environment
import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import mod.agus.jcoderz.dx.command.dexer.Main
import mod.hey.studios.build.BuildSettings
import mod.hey.studios.util.Helper
import mod.jbk.build.BuiltInLibraries
import okhttp3.internal.immutableListOf
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.EventReciever
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.eventReciever
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.repositories
import pro.sketchware.utility.FileUtil
import java.io.File
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.zip.ZipFile
import javax.xml.parsers.DocumentBuilderFactory
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
        private val DEFAULT_REPOS = """
          |[
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
        Gson().fromJson(repositoriesJson.readText(), Helper.TYPE_MAP_LIST).forEach {
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
        override fun onArtifactFound(artifact: Artifact) {}
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
        open fun onDownloadProgress(artifact: Artifact, bytesDownloaded: Long, totalBytes: Long) {}
        override fun onDownloadError(artifact: Artifact, error: Throwable) {}
        open fun unzipping(artifact: Artifact) {}
        open fun dexing(artifact: Artifact) {}
        open fun onTaskCompleted(artifacts: List<String>) {}
        open fun dexingFailed(artifact: Artifact, e: Exception) {}
        open fun invalidPackaging(artifact: Artifact) {}
    }

    fun resolveDependency(callback: DependencyResolverCallback) {
        eventReciever = callback

        val dependency = getArtifact(groupId, artifactId, version)

        if (dependency == null) {
            callback.onArtifactNotFound(Artifact(groupId, artifactId, version))
            return
        }
        val dependencies = mutableSetOf(dependency)

        callback.onResolutionComplete(dependency)
        if (skipDependencies.not()) {
            runBlocking {
                dependencies.addAll(dependency.resolve())
            }
        }

        val latestDeps =
            dependencies.groupBy { it.groupId to it.artifactId }.values.map { artifact -> artifact.maxBy { it.version } }
                .toMutableList()

        val libraryJars = immutableListOf(
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

        latestDeps.forEach { artifact ->
            callback.onResolving(artifact, dependency)
            if (artifact.version.startsWith("[")) {
                artifact.version = artifact.version.substring(1, artifact.version.length - 1)
            }
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(artifact.getPOM())
            val packaging = doc.getElementsByTagName("packaging").item(0)
            if (packaging != null) artifact.extension = packaging.textContent
            val ext = artifact.extension
            if (ext != "jar" && ext != "aar") {
                callback.invalidPackaging(artifact)
                return@forEach
            }
            val path = Paths.get(
                downloadPath,
                "${artifact.artifactId}-v${artifact.version}",
                "classes.${artifact.extension}"
            )
            if (Files.exists(path)) {
                callback.onSkippingResolution(artifact)
            }
            Files.createDirectories(path.parent)
            callback.onDownloadStart(artifact)
            try {
                downloadWithProgress(artifact, path.toFile(), callback)
                if (path.toFile().exists().not()) {
                    latestDeps.remove(artifact)
                    callback.onDependenciesNotFound(artifact)
                    return@forEach
                }
                dependencyClasspath.add(
                    if (ext == "jar") path else Paths.get(
                        downloadPath, "${artifact.artifactId}-v${artifact.version}", "classes.jar"
                    )
                )
            } catch (e: Exception) {
                callback.onDownloadError(artifact, e)
            }
            if (path.toFile().exists().not()) {
                callback.onDownloadError(artifact, Exception("Download failed"))
                return@forEach
            }
            if (ext == "aar") {
                callback.unzipping(artifact)
                unzip(path)
                Files.delete(path)
                val packageName =
                    findPackageName(path.parent.toAbsolutePath().toString(), artifact.groupId)
                path.parent.resolve("config").writeText(packageName)
            }
            val jar = if (ext == "jar") path else Paths.get(
                downloadPath, "${artifact.artifactId}-v${artifact.version}", "classes.jar"
            )
            if (Files.notExists(jar)) {
                return@forEach
            }
            callback.dexing(artifact)
            try {
                compileJar(
                    jar, dependencyClasspath.toMutableList().apply { remove(jar) }, libraryJars
                )
                callback.onResolutionComplete(artifact)
            } catch (e: Exception) {
                callback.dexingFailed(artifact, e)
                return@resolveDependency
            }
        }
        callback.onTaskCompleted(latestDeps.map { "${it.artifactId}-v${it.version}" })
    }

    private fun findPackageName(path: String, defaultValue: String): String {
        val files = ArrayList<String>()
        FileUtil.listDir(path, files)
        for (f in files) {
            if (Uri.parse(f).lastPathSegment == "AndroidManifest.xml") {
                val content = FileUtil.readFile(f)
                val p = Pattern.compile("<manifest.*package=\"(.*?)\"", Pattern.DOTALL)
                val m = p.matcher(content)
                if (m.find()) {
                    return m.group(1)!!
                }
            }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            D8.run(
                D8Command.builder().setIntermediate(true).setMode(CompilationMode.RELEASE)
                    .addProgramFiles(jarFile).addLibraryFiles(libraryJars).addClasspathFiles(jars)
                    .setOutput(jarFile.parent, OutputMode.DexIndexed).build()
            )
            return
        }
        Main.clearInternTables()
        val arguments = Main.Arguments()

        val parseMethod =
            Main.Arguments::class.java.getDeclaredMethod("parse", Array<String>::class.java)
        parseMethod.isAccessible = true
        parseMethod.invoke(
            arguments, arrayOf(
                "--debug",
                "--verbose",
                "--multi-dex",
                "--output=${jarFile.parent}",
                jarFile.toString()
            )
        )

        Main.run(arguments)
    }

    private fun downloadWithProgress(artifact: Artifact, targetFile: File, callback: DependencyResolverCallback) {
        try {
            val repository = artifact.repository
            val baseUrl = repository?.getURL()
            val artifactPath = "${artifact.groupId.replace('.', '/')}/${artifact.artifactId}/${artifact.version}/${artifact.artifactId}-${artifact.version}.${artifact.extension}"
            val downloadUrl = "$baseUrl/$artifactPath"

            val url = URL(downloadUrl)
            val connection = url.openConnection()

            connection.connectTimeout = 10000
            connection.readTimeout = 30000

            val totalBytes = connection.contentLengthLong

            connection.getInputStream().use { input ->
                targetFile.outputStream().use { output ->
                    val buffer = ByteArray(8192)
                    var bytesDownloaded = 0L
                    var bytesRead: Int

                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                        bytesDownloaded += bytesRead

                        if (bytesDownloaded % 65536 == 0L || bytesRead == -1) {
                            callback.onDownloadProgress(artifact, bytesDownloaded, totalBytes)
                        }
                    }
                    callback.onDownloadProgress(artifact, bytesDownloaded, totalBytes)
                }
            }
        } catch (e: SocketTimeoutException) {
            callback.onDownloadError(artifact, Exception("Download timeout: ${e.message}"))
        } catch (e: ConnectException) {
            callback.onDownloadError(artifact, Exception("Connection failed: ${e.message}"))
        } catch (e: UnknownHostException) {
            callback.onDownloadError(artifact, Exception("Network unavailable: ${e.message}"))
        } catch (e: IOException) {
            callback.onDownloadError(artifact, Exception("Download interrupted: ${e.message}"))
        } catch (e: Exception) {
            callback.onDownloadError(artifact, e)
        }
    }
}
