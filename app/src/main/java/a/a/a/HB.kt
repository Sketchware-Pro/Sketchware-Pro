package a.a.a

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.core.net.toUri

object HB {
    @JvmStatic
    fun a(context: Context, uri: Uri): String? {
        val path: String? = if (DocumentsContract.isDocumentUri(context, uri)) {
            if (b(uri)) {
                val split = DocumentsContract.getDocumentId(uri).split(":")
                if ("primary".equals(split[0], ignoreCase = true)) {
                    "${Environment.getExternalStorageDirectory()}/${split[1]}"
                } else {
                    null
                }
            } else if (a(uri)) {
                val documentId = DocumentsContract.getDocumentId(uri)
                if (!TextUtils.isEmpty(documentId) && documentId.startsWith("raw:")) {
                    return documentId.replaceFirst("raw:", "")
                }
                val id = documentId.toLongOrNull() ?: return null
                a(
                    context,
                    ContentUris.withAppendedId(
                        "content://downloads/public_downloads".toUri(),
                        id
                    ),
                    null,
                    null
                )
            } else if (c(uri)) {
                val split = DocumentsContract.getDocumentId(uri).split(":")
                val type = split[0]
                val contentUri: Uri? = when (type) {
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> null
                }
                contentUri?.let { a(context, it, "_id=?", arrayOf(split[1])) }
            } else {
                null
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            a(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            uri.path
        } else {
            null
        }
        return path
    }

    @JvmStatic
    fun a(context: Context, uri: Uri, str: String?, strArr: Array<String>?): String? {
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(uri, arrayOf("_data"), str, strArr, null)
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow("_data"))
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message, e)
        } finally {
            cursor?.close()
        }
        return null
    }

    @JvmStatic
    fun a(uri: Uri): Boolean = "com.android.providers.downloads.documents" == uri.authority

    @JvmStatic
    fun b(uri: Uri): Boolean = "com.android.externalstorage.documents" == uri.authority

    @JvmStatic
    fun c(uri: Uri): Boolean = "com.android.providers.media.documents" == uri.authority
}
