package mod.pranav.dependency.resolver

import android.net.Uri
import android.os.Environment
import com.android.tools.r8.CompilationMode
import com.android.tools.r8.D8
import com.android.tools.r8.D8Command
import com.android.tools.r8.OutputMode
import com.google.gson.Gson
import mod.agus.jcoderz.lib.FileUtil
import mod.hey.studios.util.Helper
import org.cosmic.ide.dependency.resolver.api.Artifact
import org.cosmic.ide.dependency.resolver.api.Repository
import org.cosmic.ide.dependency.resolver.getArtifact
import org.cosmic.ide.dependency.resolver.repositories
import java.io.File
import java.nio.file.Path
import java.util.regex.Pattern
import java.util.zip.ZipFile

class DependencyResolver(val groupId: String, val artifactId: String, val version: String) {

    // now, you might be wondering, where is the option for enabling/disabling D8/R8?
    // well, that's because its not. D8/R8 will be enabled by default, and there is no way to disable it. Good luck with that.
    private val downloadPath: String = FileUtil.getExternalStorageDir() + "/.sketchware/libs/local_libs"

    val repoFile = File(
        Environment.getExternalStorageDirectory(),
        ".sketchware" + File.separator + "libs" + File.separator + "repositories.json"
    )
    private val DEFAULT_REPOSITORIES_FILE_CONTENT =
        "[{\"url\":\"https://repo.hortonworks.com/content/repositories/releases\",\"name\":\"HortanWorks\"},{\"url\":\"https://maven.atlassian.com/content/repositories/atlassian-public\",\"name\":\"Atlassian\"},{\"url\":\"https://jitpack.io\",\"name\":\"JitPack\"},{\"url\":\"https://jcenter.bintray.com\",\"name\":\"JCenter\"},{\"url\":\"https://oss.sonatype.org/content/repositories/releases\",\"name\":\"Sonatype\"},{\"url\":\"https://repo.spring.io/plugins-release\",\"name\":\"Spring Plugins\"},{\"url\":\"https://repo.spring.io/libs-milestone\",\"name\":\"Spring Milestone\"},{\"url\":\"https://repo.maven.apache.org/maven2\",\"name\":\"Apache Maven\"},{\"url\":\"https://dl.google.com/dl/android/maven2\",\"name\":\"Google Maven\"},{\"url\":\"https://repo1.maven.org/maven2\",\"name\":\"Maven Central\"}]"


    init {
        if (!repoFile.exists()) {
            repoFile.parentFile?.mkdirs()
            repoFile.createNewFile()
            repoFile.writeText(DEFAULT_REPOSITORIES_FILE_CONTENT)
        }
       Gson().fromJson(repoFile.readText(), Helper.TYPE_MAP_LIST).forEach {
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
        fun onTaskCompleted() {}
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
        System.setOut(object : java.io.PrintStream(System.out) {
            override fun println(x: String) {
                callback.log(x)
            }
        })
        val dependencies = mutableListOf<Artifact>()
        callback.startResolving("$groupId:$artifactId:$version")
        val dependency = getArtifact(groupId, artifactId, version)

        callback.onDependencyResolved(dependency.toStr())
        dependency.resolve().forEach { artifact ->
            if (artifact.version.isEmpty() || artifact.repository == null) {
                callback.onDependencyNotFound(artifact.toStr())
                return
            }
            dependencies.add(artifact)
        }
        dependencies.add(dependency)
        // basically, remove all the duplicates and keeps the latest among them
        val latestDeps =
                dependencies.groupBy { it.groupId to it.artifactId }.values.map { artifact -> artifact.maxBy { it.version } }

        latestDeps.forEach { artifact ->
            callback.startResolving(artifact.toStr())
            val ext = artifact.extension
            if (ext != "jar" && ext != "aar") {
                callback.invalidPackaging(artifact.toStr())
                println("Invalid packaging ${artifact.toStr()}")
                return@forEach
            }
            val path = File("$downloadPath/${artifact.artifactId}-${artifact.version}/classes.${artifact.extension}")
            path.deleteRecursively()
            path.parentFile!!.mkdirs()
            callback.downloading(artifact.toStr())
            artifact.downloadTo(path)
            if (ext == "aar") {
                callback.log("Unzipping ${artifact.toStr()}")
                println("Unzipping ${artifact.toStr()}")
                unzip(path)
                path.delete()
                val packageName = findPackageName(path.parentFile!!.absolutePath, artifact.groupId)
                path.parentFile!!.resolve("config").writeText(packageName)
            }
            callback.dexing(artifact.toStr())
            compileJar(path.parentFile!!.resolve("classes.jar").toPath())
            callback.onDependencyResolved(artifact.toStr())
            println("Download ${artifact.toStr()}")
        }
        callback.onTaskCompleted()
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

    private fun unzip(file: File) {
        val zipFile = ZipFile(file)
        zipFile.entries().asSequence().forEach { entry ->
            val entryDestination = File(file.parentFile, entry.name)
            if (entry.isDirectory) {
                entryDestination.mkdirs()
            } else {
                entryDestination.parentFile?.mkdirs()
                zipFile.getInputStream(entry).use { input ->
                    entryDestination.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

    fun compileJar(jarFile: Path) {
        D8.run(
                D8Command.builder()
                    .setIntermediate(true)
                    .setMode(CompilationMode.RELEASE)
                    .addProgramFiles(jarFile)
                    .setOutput(jarFile.parent, OutputMode.DexIndexed)
                    .build()
            )
    }
}