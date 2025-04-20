package mod.tyron.backup

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class SingleCopyTask(private val context: Context, private val callback: CallBackTask) {

    interface CallBackTask {
        fun onCopyPreExecute()
        fun onCopyProgressUpdate(progress: Int)
        fun onCopyPostExecute(path: String, success: Boolean, error: String)
    }

    fun copyFile(uri: Uri) {
        CoroutineScope(Dispatchers.Main).launch {
            callback.onCopyPreExecute()
            val result = withContext(Dispatchers.IO) {
                copyFileInBackground(uri)
            }
            callback.onCopyPostExecute(result.first, result.second, result.third)
        }
    }

    @SuppressLint("Range")
    private fun copyFileInBackground(uri: Uri): Triple<String, Boolean, String> {
        var pathPlusName = ""
        var errorReason = ""
        var success = false

        try {
            val folder = context.cacheDir
            val returnCursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val size = returnCursor?.use {
                if (it.moveToFirst()) {
                    when (uri.scheme) {
                        "content" -> it.getLong(it.getColumnIndex(OpenableColumns.SIZE)).toInt()
                        "file" -> File(uri.path!!).length().toInt()
                        else -> -1
                    }
                } else -1
            } ?: -1

            if (size == 0) throw IOException("Empty file (size = 0)")

            pathPlusName = "${folder}/${getFileName(uri)}"
            val file = File(pathPlusName)

            inputStream?.use { input ->
                BufferedInputStream(input).use { bis ->
                    FileOutputStream(file).use { fos ->
                        val data = ByteArray(1024)
                        var total: Long = 0
                        var count: Int
                        while (bis.read(data).also { count = it } != -1) {
                            total += count
                            if (size != -1) {
                                callback.onCopyProgressUpdate((total * 100 / size).toInt())
                            }
                            fos.write(data, 0, count)
                        }
                        fos.flush()
                    }
                }
            }
            success = true
        } catch (e: IOException) {
            errorReason = e.message ?: "Unknown error"
        }

        return Triple(pathPlusName, success, errorReason)
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/') ?: -1
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "unknown"
    }
}
