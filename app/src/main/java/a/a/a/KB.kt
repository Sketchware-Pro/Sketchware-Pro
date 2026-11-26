package a.a.a

import android.content.Context
import android.util.Log
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

class KB {
    companion object {
        @JvmStatic
        fun a(context: Context, str: String, str2: String) {
            val buf = ByteArray(1024)
            val targetDir = if (str2.endsWith(File.separator)) str2 else "$str2${File.separator}"
            val dir = File(targetDir)
            if (!dir.exists() && !dir.mkdirs()) {
                dir.mkdir()
            }
            val dirPath = dir.canonicalPath
            val canonicalDir = dirPath + File.separator
            var bis: BufferedInputStream? = null
            var zis: ZipInputStream? = null
            try {
                bis = BufferedInputStream(context.assets.open(str))
                zis = ZipInputStream(bis)
                while (true) {
                    val entry = zis.nextEntry ?: break
                    val outFile = File(dir, entry.name)
                    val outPath = outFile.canonicalPath
                    if (outPath != dirPath && !outPath.startsWith(canonicalDir)) {
                        throw SecurityException("Invalid zip entry: ${entry.name}")
                    }
                    if (!entry.isDirectory) {
                        val parent = outFile.parentFile
                        if (parent != null && !parent.isDirectory) {
                            parent.mkdirs()
                        }
                        FileOutputStream(outFile, false).use { fos ->
                            while (true) {
                                val read = zis.read(buf, 0, buf.size)
                                if (read <= 0) {
                                    break
                                }
                                fos.write(buf, 0, read)
                            }
                            fos.flush()
                        }
                        zis.closeEntry()
                    } else if (!outFile.isDirectory) {
                        outFile.mkdirs()
                    }
                }
            } catch (e: Exception) {
                Log.e("DEBUG", e.message, e)
                throw e
            } finally {
                try {
                    zis?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    bis?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun a(str: String, file: File, zipOutputStream: ZipOutputStream, arrayList: ArrayList<String>): Int {
        val listFiles = file.listFiles() ?: return 0
        if (listFiles.isEmpty()) {
            val absPath = file.absolutePath
            a(str, absPath.substring(str.length), zipOutputStream)
        }
        var count = 0
        for (child in listFiles) {
            if (child.isDirectory) {
                a(str, child, zipOutputStream, arrayList)
            }
            if (child.isFile) {
                val absPath = child.absolutePath
                val relPath = absPath.substring(str.length)
                var excluded = false
                for (pattern in arrayList) {
                    if (absPath.contains(pattern)) {
                        excluded = true
                        break
                    }
                }
                if (!excluded && a(str, relPath, zipOutputStream)) {
                    count++
                }
            }
        }
        return count
    }

    fun a(inputStream: InputStream, str: String) {
        val buf = ByteArray(1024)
        val targetDir = if (str.endsWith(File.separator)) str else "$str${File.separator}"
        val dir = File(targetDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val dirPath = dir.canonicalPath
        val canonicalDir = dirPath + File.separator
        var bis: BufferedInputStream? = null
        var zis: ZipInputStream? = null
        try {
            bis = BufferedInputStream(inputStream)
            zis = ZipInputStream(bis)
            while (true) {
                val entry = zis.nextEntry ?: break
                val outFile = File(dir, entry.name)
                val outPath = outFile.canonicalPath
                if (outPath != dirPath && !outPath.startsWith(canonicalDir)) {
                    throw SecurityException("Invalid zip entry: ${entry.name}")
                }
                if (!entry.isDirectory) {
                    val parent = outFile.parentFile
                    if (parent != null && !parent.isDirectory) {
                        parent.mkdirs()
                    }
                    FileOutputStream(outFile, false).use { fos ->
                        while (true) {
                            val read = zis.read(buf, 0, buf.size)
                            if (read <= 0) {
                                break
                            }
                            fos.write(buf, 0, read)
                        }
                        fos.flush()
                    }
                    zis.closeEntry()
                } else if (!outFile.isDirectory) {
                    outFile.mkdirs()
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                zis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun a(str: String, str2: String) {
        a(FileInputStream(str), str2)
    }

    @Throws(Exception::class)
    fun a(str: String, arrayList: ArrayList<String>, arrayList2: ArrayList<String>) {
        FileOutputStream(str).use { fos ->
            ZipOutputStream(fos).use { zos ->
                for (path in arrayList) {
                    a(path, File(path), zos, arrayList2)
                }
            }
        }
    }

    @Throws(Exception::class)
    fun a(str: String, str2: String, zipOutputStream: ZipOutputStream?): Boolean {
        val file = File("$str$str2")
        if (!file.isFile) {
            try {
                zipOutputStream?.closeEntry()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return false
        }
        var fis: FileInputStream? = null
        var bis: BufferedInputStream? = null
        try {
            fis = FileInputStream(file)
            bis = BufferedInputStream(fis)
            zipOutputStream!!.putNextEntry(ZipEntry(str2))
            val buf = ByteArray(1024)
            while (true) {
                val read = bis.read(buf)
                if (read <= 0) {
                    break
                }
                zipOutputStream.write(buf, 0, read)
            }
            return true
        } finally {
            try {
                zipOutputStream?.closeEntry()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                bis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    fun a(str: String): ByteArray {
        val baos = ByteArrayOutputStream()
        ZipOutputStream(baos).use { zos ->
            a(str, File(str), zos, ArrayList())
        }
        return baos.toByteArray()
    }
}
