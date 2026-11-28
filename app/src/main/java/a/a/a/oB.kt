package a.a.a

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class oB @JvmOverloads constructor(@JvmField var a: Boolean = false) {
    fun a(context: Context, str: String): Long {
        var inputStream: InputStream? = null
        return try {
            inputStream = context.assets.open(str)
            inputStream.available().toLong()
        } catch (e: IOException) {
            e.printStackTrace()
            -1L
        } finally {
            try {
                inputStream?.close()
            } catch (_: IOException) {
            }
        }
    }

    @Throws(Exception::class)
    fun a(bArr: ByteArray): String = String(b(bArr), Charset.forName("UTF-8"))

    fun a(context: Context, str: String, str2: String) {
        val assets = context.assets
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            val lastSep = str2.lastIndexOf(File.separator)
            if (lastSep > 0) {
                f(str2.substring(0, lastSep))
            }
            inputStream = assets.open(str)
            fos = FileOutputStream(str2, false)
            val buf = ByteArray(1024)
            while (true) {
                val read = inputStream.read(buf)
                if (read <= 0) {
                    break
                }
                fos.write(buf, 0, read)
            }
            if (a) {
                Log.d(javaClass.simpleName, "assetFile =>$str2 copy success.")
            }
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun a(file: File) {
        a(file, true)
    }

    @Throws(IOException::class)
    fun a(file: File, file2: File) {
        if (!file.isDirectory) {
            a(file.absolutePath, file2.absolutePath)
            return
        }
        if (!file2.exists() && !file2.mkdirs()) {
            throw IOException("Cannot create dir ${file2.absolutePath}")
        }
        val list = file.list() ?: return
        for (name in list) {
            a(File(file, name), File(file2, name))
        }
    }

    fun a(file: File, z: Boolean) {
        if (file.exists()) {
            val listFiles = file.listFiles()
            if (listFiles != null) {
                for (child in listFiles) {
                    if (child.isDirectory) {
                        a(child)
                    }
                    if (child.isFile) {
                        if (child.delete()) {
                            if (a) {
                                Log.d(oB::class.java.simpleName, "Delete file success.${child.absolutePath}")
                            }
                        } else if (a) {
                            Log.d(oB::class.java.simpleName, "Delete file failed.${child.absolutePath}")
                        }
                    }
                }
            }
            if (z) {
                file.delete()
            }
        }
    }

    fun a(str: String) {
        b(str)
        f(str)
    }

    fun a(str: String, str2: String) {
        if (File(str).canonicalPath == File(str2).canonicalPath) {
            return
        }
        var fis: FileInputStream? = null
        var fos: FileOutputStream? = null
        try {
            fis = FileInputStream(str)
            fos = FileOutputStream(str2, false)
            val buf = ByteArray(1024)
            if (a) {
                Log.d(javaClass.simpleName, "src=$str,dest=$str2")
            }
            while (true) {
                val read = fis.read(buf)
                if (read <= 0) {
                    break
                }
                fos.write(buf, 0, read)
            }
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun a(str: String, z: Boolean) {
        a(File(str), z)
    }

    @Throws(IOException::class)
    fun a(str: String, bArr: ByteArray) {
        val lastSep = str.lastIndexOf(File.separator)
        if (lastSep > 0) {
            f(str.substring(0, lastSep))
        }
        val file = File(str)
        if (!file.exists()) {
            file.createNewFile()
        }
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(File(str))
            fos.write(bArr)
            fos.flush()
        } finally {
            try {
                fos?.close()
            } catch (_: IOException) {
            }
        }
    }

    fun b(context: Context, str: String): String {
        val sb = StringBuilder()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(context.assets.open(str.trim()), "utf-8"))
            while (true) {
                val line = reader.readLine() ?: break
                sb.append(line)
                sb.append("\r\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }

    fun b(file: File) {
        file.delete()
    }

    fun b(str: String) {
        a(str, true)
    }

    fun b(str: String, str2: String) {
        val lastSep = str.lastIndexOf(File.separator)
        if (lastSep > 0) {
            f(str.substring(0, lastSep))
        }
        val file = File(str)
        if (!file.exists()) {
            file.createNewFile()
        }
        var writer: FileWriter? = null
        try {
            writer = FileWriter(File(str), false)
            writer.write(str2)
            writer.flush()
            if (a) {
                Log.d(javaClass.simpleName, "$str saved")
            }
        } finally {
            try {
                writer?.close()
            } catch (_: IOException) {
            }
        }
    }

    @Throws(Exception::class)
    fun b(bArr: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keyBytes = "sketchwaresecure".toByteArray()
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(keyBytes, "AES"), IvParameterSpec(keyBytes))
        return cipher.doFinal(bArr)
    }

    @Throws(IOException::class)
    fun c(file: File): String {
        val sb = StringBuilder()
        var reader: FileReader? = null
        try {
            reader = FileReader(file)
            val buf = CharArray(1024)
            while (true) {
                val read = reader.read(buf)
                if (read <= 0) {
                    break
                }
                sb.append(String(buf, 0, read))
            }
        } finally {
            try {
                reader?.close()
            } catch (_: Exception) {
            }
        }
        return sb.toString()
    }

    fun c(str: String) {
        b(File(str))
    }

    @Throws(Exception::class)
    fun c(bArr: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keyBytes = "sketchwaresecure".toByteArray()
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(keyBytes, "AES"), IvParameterSpec(keyBytes))
        return cipher.doFinal(bArr)
    }

    @Throws(Exception::class)
    fun d(str: String): ByteArray = c(str.toByteArray(Charset.forName("UTF-8")))

    fun e(str: String): Boolean = File(str).exists()

    fun f(str: String): Boolean {
        if (e(str)) {
            return false
        }
        return File(str).mkdirs()
    }

    @Throws(IOException::class)
    fun g(str: String): String = c(File(str))

    @Throws(IOException::class)
    fun h(str: String): ByteArray? {
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(File(str))
            val available = fis.available()
            if (available <= 0) {
                return null
            }
            val buf = ByteArray(available)
            fis.read(buf)
            return buf
        } finally {
            try {
                fis?.close()
            } catch (_: Exception) {
            }
        }
    }
}
