package mod.pranav.dependency.resolver

import android.net.Uri
import android.os.Build
import android.os.Environment
import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.google.gson.Gson
import mod.agus.jcoderz.dx.command.dexer.Main
import pro.sketchware.utility.FileUtil
import mod.hey.studios.build.BuildSettings
import mod.hey.studios.util.Helper
import mod.jbk.build.BuiltInLibraries
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.EventReciever
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.repositories
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.zip.ZipFile
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlinx.coroutines.runBlocking
import okhttp3.internal.immutableListOf
import org.cosmic.ide.dependency.resolver.eventReciever
import javax.xml.parsers.DocumentBuilderFactory

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
            |    { "url": "https://repo.hortonworks.com/content/repositories/releases", "name": "HortonWorks" }, 
            |    { "url": "https://maven.atlassian.com/content/repositories/atlassian-public", "name": "Atlassian" }, 
            |    { "url": "https://jcenter.bintray.com", "name": "JCenter" }, 
            |    { "url": "https://oss.sonatype.org/content/repositories/releases", "name": "Sonatype" }, 
            |    { "url": "https://repo.spring.io/plugins-release", "name": "Spring Plugins" }, 
            |    { "url": "https://repo.spring.io/libs-milestone", "name": "Spring Milestone" }, 
            |    { "url": "https://repo.maven.apache.org/maven2", "name": "Apache Maven" } 
            |]
        """.trimMargin()
    }

    private val downloadPath: String =
        FileUtil.getExternalStorageDir() + "/.sketchware/libs/local_libs"

    private val repositoriesJson = Paths.get(
        Environment.getExternalStorageDirectory().absolutePath,
        ".sketchware", "libs", "repositories.json"
    )

    init {
        if (Files.notExists(repositoriesJson)) {
            Files.createDirectories(repositoriesJson.parent)
            repositoriesJson.writeText(DEFAULT_REPOS)
        }
        Gson().fromJson(repositoriesJson.readText(), Helper.TYPE_MAP_LIST).forEach {
            val url = it["url"] as? String ?: return@forEach
            repositories.add(object : Repository {
                override fun getName() = it["name"] as String
                override fun getURL() = url.removeSuffix("/")
            })
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

        if (!skipDependencies) {
            runBlocking { dependencies.addAll(dependency.resolve()) }
        }

        val latestDeps = dependencies.groupBy { it.groupId to it.artifactId }
            .values.map { artifacts -> artifacts.maxBy { it.version } }
            .toMutableList()

        latestDeps.forEach { artifact ->
            resolveSingleDependency(artifact, callback)
        }
        callback.onTaskCompleted(latestDeps.map { "${it.artifactId}-v${it.version}" })
    }

    private fun resolveSingleDependency(artifact: Artifact, callback: DependencyResolverCallback) {
        callback.onResolving(artifact, artifact)
        
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(artifact.getPOM())
        artifact.extension = doc.getElementsByTagName("packaging").item(0)?.textContent ?: "jar"

        if (artifact.extension != "jar" && artifact.extension != "aar") {
            callback.invalidPackaging(artifact)
            return
        }

        val path = Paths.get(downloadPath, "${artifact.artifactId}-v${artifact.version}", "classes.${artifact.extension}")
        if (Files.exists(path)) {
            callback.onSkippingResolution(artifact)
            return
        }

        Files.createDirectories(path.parent)
        callback.onDownloadStart(artifact)
        try {
            artifact.downloadTo(path.toFile())
            if (!path.toFile().exists()) {
                callback.onDependenciesNotFound(artifact)
                return
            }
            if (artifact.extension == "aar") {
                callback.unzipping(artifact)
                unzip(path)
                Files.delete(path)
                writeConfig(path.parent.toAbsolutePath().toString(), artifact.groupId)
            }
            callback.dexing(artifact)
            compileJar(path, callback)
            callback.onResolutionComplete(artifact)
        } catch (e: Exception) {
            callback.onDownloadError(artifact, e)
        }
    }

    private fun unzip(path: Path) {
        ZipFile(path.toFile()).use { zip ->
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

    private fun writeConfig(dir: String, defaultValue: String) {
        val packageName = findPackageName(dir, defaultValue)
        Paths.get(dir, "config").writeText(packageName)
    }

    private fun findPackageName(path: String, defaultValue: String): String {
        return FileUtil.listDir(path).firstNotNullOfOrNull {
            if (Uri.parse(it).lastPathSegment == "AndroidManifest.xml") {
                val content = FileUtil.readFile(it)
                val matcher = Pattern.compile("<manifest.*package=\"(.*?)\"", Pattern.DOTALL).matcher(content)
                if (matcher.find()) matcher.group(1) else null
            } else null
        } ?: defaultValue
    }

    private fun compileJar(jarFile: Path, callback: DependencyResolverCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            D8.run(
                D8Command.builder()
                    .setIntermediate(true)
                    .setMode(CompilationMode.RELEASE)
                    .addProgramFiles(jarFile)
                    .setOutput(jarFile.parent, OutputMode.DexIndexed)
                    .build()
            )
        } else {
            Main.clearInternTables()
            val arguments = Main.Arguments()
            Main.Arguments::class.java.getDeclaredMethod("parse", Array<String>::class.java)
                .apply { isAccessible = true }
                .invoke(arguments, arrayOf("--debug", "--verbose", "--multi-dex", "--output=${jarFile.parent}", jarFile.toString()))
            Main.run(arguments)
        }
    }
}
