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
import mod.agus.jcoderz.lib.FileUtil
import mod.hey.studios.util.Helper
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.initHost
import org.cosmic.ide.dependency.resolver.repositories
import org.w3c.dom.Element
import java.io.InputStream
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
    private val version: String
) {
    companion object {
        private val DEFAULT_REPOS = """
            |[
            |    {
            |        "url": "https://repo.hortonworks.com/content/repositories/releases",
            |        "name": "HortanWorks"
            |    },
            |    {
            |        "url": "https://maven.atlassian.com/content/repositories/atlassian-public",
            |        "name": "Atlassian"
            |    },
            |    {
            |        "url": "https://jcenter.bintray.com",
            |        "name": "JCenter"
            |    },
            |    {
            |        "url": "https://oss.sonatype.org/content/repositories/releases",
            |        "name": "Sonatype"
            |    },
            |    {
            |        "url": "https://repo.spring.io/plugins-release",
            |        "name": "Spring Plugins"
            |    },
            |    {
            |        "url": "https://repo.spring.io/libs-milestone",
            |        "name": "Spring Milestone"
            |    },
            |    {
            |        "url": "https://repo.maven.apache.org/maven2",
            |        "name": "Apache Maven"
            |    }
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

    interface DependencyResolverCallback {
        fun onDependencyResolved(dep: String) {}
        fun onDependencyResolveFailed(e: Exception) {}
        fun onDependencyNotFound(dep: String) {}
        fun onTaskCompleted(deps: List<String>) {}
        fun startResolving(dep: String) {}
        fun downloading(dep: String) {}
        fun dexing(dep: String) {}
        fun invalidPackaging(dep: String) {}

        fun log(msg: String) {}
    }

    private fun Artifact.toStr(): String {
        return "$groupId:$artifactId:$version"
    }

    fun resolveDependency(callback: DependencyResolverCallback) {
        // this is pretty much the same as `Artifact.downloadArtifact()`, but with some modifications for checks and callbacks
        val dependencies = mutableListOf<Artifact>()
        callback.startResolving("$groupId:$artifactId:$version")
        val dependency = getArtifact(groupId, artifactId, version)
        if (dependency == null) {
            callback.onDependencyNotFound("$groupId:$artifactId:$version")
            return
        }

        callback.onDependencyResolved(dependency.toStr())
        resolve(dependency, dependencies, callback)

        // basically, remove all the duplicates and keeps the latest among them
        val latestDeps =
            dependencies.groupBy { it.groupId to it.artifactId }.values.map { artifact -> artifact.maxBy { it.version } }

        latestDeps.forEach { artifact ->
            callback.startResolving(artifact.toStr())
            // set the packaging type
            artifact.getPOM()!!.bufferedReader().use {
                it.forEachLine { line ->
                    if (line.contains("<packaging>")) {
                        artifact.extension =
                            line.substringAfter("<packaging>").substringBefore("</packaging>")
                    }
                }
            }
            val ext = artifact.extension
            if (ext != "jar" && ext != "aar") {
                callback.invalidPackaging(artifact.toStr())
                return@forEach
            }
            val path =
                Paths.get(
                    downloadPath,
                    "${artifact.artifactId}-v${artifact.version}",
                    "classes.${artifact.extension}"
                )
            if (Files.exists(path)) {
                callback.log("Dependency ${artifact.toStr()} already exists, skipping...")
            }
            Files.createDirectories(path.parent)
            callback.downloading(artifact.toStr())
            try {
                artifact.downloadTo(path.toFile())
            } catch (e: Exception) {
                callback.onDependencyResolveFailed(e)
            }
            if (path.toFile().exists().not()) {
                callback.log("Cannot download ${artifact.toStr()}")
            }
            if (ext == "aar") {
                callback.log("Unzipping ${artifact.toStr()}")
                unzip(path)
                Files.delete(path)
                val packageName =
                    findPackageName(path.parent.toAbsolutePath().toString(), artifact.groupId)
                path.parent.resolve("config").writeText(packageName)
            }
            callback.dexing(artifact.toStr())
            compileJar(path.parent.resolve("classes.jar"))
            callback.onDependencyResolved(artifact.toStr())
        }
        callback.onTaskCompleted(latestDeps.map { "${it.artifactId}-v${it.version}" })
    }

    // copied from source to prevent useless recursive
    private fun resolve(
        artifact: Artifact,
        dependencies: MutableList<Artifact>,
        callback: DependencyResolverCallback
    ) {
        dependencies.add(artifact)
        callback.log("Resolving sub-dependencies for ${artifact.toStr()}...")
        val pom = artifact.getPOM()
        if (pom == null) {
            callback.log("Cannot resolve sub-dependencies for ${artifact.toStr()}")
            return
        }
        val deps = pom.resolvePOM(dependencies)
        deps.forEach { dep ->
            callback.log("Resolving ${dep.groupId}:${dep.artifactId}")
            if (dep.version.isEmpty()) {
                callback.log("Fetching latest version of ${dep.artifactId}")
                val factory = DocumentBuilderFactory.newInstance()
                val builder = factory.newDocumentBuilder()
                val doc = builder.parse(dep.getMavenMetadata())
                val v = doc.getElementsByTagName("release").item(0)
                if (v != null) {
                    dep.version = v.textContent
                    callback.log("Latest version of ${dep.groupId}:${dep.artifactId} is ${dep.version}")
                }
            }
            callback.log("Resolved ${dep.groupId}:${dep.artifactId}")
            if (artifact.version.isEmpty() || artifact.repository == null) {
                callback.onDependencyNotFound(artifact.toStr())
                callback.log("Cannot resolve ${artifact.toStr()}")
                return
            }
            resolve(dep, dependencies, callback)
        }
    }

    private fun InputStream.resolvePOM(deps: List<Artifact>): List<Artifact> {
        val artifacts = mutableListOf<Artifact>()
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(this)

        val elem = doc.getElementsByTagName("dependencies")
        if (elem.length == 0) {
            println("No dependencies found")
            return artifacts
        }
        val dependencies = elem.item(elem.length - 1) as Element
        val dependencyElements = dependencies.getElementsByTagName("dependency")
        val el = dependencies.getElementsByTagName("packaging").item(0)
        val packaging = if (el == null) "jar" else el.textContent
        for (i in 0 until dependencyElements.length) {
            val dependencyElement = dependencyElements.item(i) as Element
            val scopeItem = dependencyElement.getElementsByTagName("scope").item(0)
            if (scopeItem != null) {
                val scope = scopeItem.textContent
                // if scope is test/provided, there is no need to download them
                if (scope.isNotEmpty() && (scope == "test" || scope == "provided")) {
                    println("Skipping dependency with scope $scope")
                    continue
                }
            }
            val groupId = dependencyElement.getElementsByTagName("groupId").item(0).textContent
            val artifactId =
                dependencyElement.getElementsByTagName("artifactId").item(0).textContent

            if (artifactId.endsWith("bom")) {
                // TODO: handle versions from BOMs
                println("Skipping BOM $artifactId")
                continue
            }
            val artifact = Artifact(groupId, artifactId, extension = packaging)
            initHost(artifact)
            if (artifact.repository == null) {
                println("No repository for ${artifact.toStr()}")
                continue
            }
            val item = dependencyElement.getElementsByTagName("version").item(0)
            if (item != null) {
                var version = item.textContent
                // Some libraries define an array of compatible dependency versions.
                if (version.startsWith("[")) {
                    // remove square brackets so that we have something like `1.0.0,1.0.1` left
                    val versionArray = version.substring(1, version.length - 1)
                    val versions = versionArray.split(",")
                    val metadata = artifact.getMavenMetadata()

                    // sometimes, some of the defined versions don't exist, so we need to check all of them
                    versions.forEach { v ->
                        if (metadata.contains(v)) {
                            version = v
                        }
                    }
                }
                if (version.equals("+")) {
                    // fallback to fetching latest version from #resolve(Artifact, MutableList<Artifact>, DependencyResolverCallback)
                    version = ""
                }
                artifact.version = version
            }

            if (deps.any { it.groupId == groupId && it.artifactId == artifactId && it.version >= version }) {
                println("Dependency ${artifact.toStr()} already resolved, skipping...")
                continue
            }

            artifacts.add(artifact)
        }
        return artifacts
    }

    private fun findPackageName(path: String, defaultValue: String): String {
        val files = ArrayList<String>()
        FileUtil.listDir(path, files)

        // Method 1: use manifest
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

        // Method 2: screw manifest. use dependency
        return defaultValue
    }

    private fun unzip(path: Path) {
        val zipFile = ZipFile(path.toFile())
        zipFile.entries().asSequence().forEach { entry ->
            val entryDestination = path.parent.resolve(entry.name)
            if (entry.isDirectory) {
                Files.createDirectories(entryDestination)
            } else {
                Files.createDirectories(entryDestination.parent)
                zipFile.getInputStream(entry).use { input ->
                    Files.newOutputStream(entryDestination).use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    private fun compileJar(jarFile: Path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            D8.run(
                D8Command.builder()
                    .setIntermediate(true)
                    .setMode(CompilationMode.RELEASE)
                    .addProgramFiles(jarFile)
                    .setOutput(jarFile.parent, OutputMode.DexIndexed)
                    .build()
            )
            return
        }
        Main.clearInternTables()

        Main.main(
            arrayOf(
                "--debug",
                "--verbose",
                "--multi-dex",
                "--output=${jarFile.parent}",
                jarFile.toString()
            )
        )
    }
}
