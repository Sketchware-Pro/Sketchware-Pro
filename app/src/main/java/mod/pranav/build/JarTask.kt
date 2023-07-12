package mod.pranav.build

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

object JarBuilder {
    fun generateJar(classes: File) {
        // Open archive file
        val stream = FileOutputStream(File(classes.parent, "classes.jar"))
        val manifest = buildManifest()

        // Create the jar file
        val out = JarOutputStream(stream, manifest)

        // Add the files
        val files = classes.listFiles()
        if (files != null) {
            for (clazz in files) {
                add(classes.path, clazz, out)
            }
        }
        out.close()
        stream.close()
    }

    private fun buildManifest(): Manifest {
        val manifest = Manifest()
        manifest.mainAttributes[Attributes.Name.MANIFEST_VERSION] = "1.0"
        return manifest
    }

    private fun add(parentPath: String, source: File, target: JarOutputStream) {
        var name = source.path.substring(parentPath.length + 1)
        if (source.isDirectory) {
            if (name.isNotEmpty()) {
                if (!name.endsWith("/")) name += "/"

                // Add the Entry
                val entry = JarEntry(name)
                entry.time = source.lastModified()
                target.putNextEntry(entry)
                target.closeEntry()
            }
            for (nestedFile in source.listFiles()!!) {
                add(parentPath, nestedFile, target)
            }
            return
        }
        val entry = JarEntry(name)
        entry.time = source.lastModified()
        target.putNextEntry(entry)
        try {
            BufferedInputStream(FileInputStream(source)).use { `in` ->
                val buffer = ByteArray(1024)
                while (true) {
                    val count = `in`.read(buffer)
                    if (count == -1) break
                    target.write(buffer, 0, count)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        target.closeEntry()
    }
}
