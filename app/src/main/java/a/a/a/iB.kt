package a.a.a

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object iB {
    @JvmStatic
    fun a(options: BitmapFactory.Options, i: Int, i2: Int): Int {
        val outWidth = options.outWidth
        val outHeight = options.outHeight
        var sampleSize = 1
        if (outHeight > i2 || outWidth > i) {
            val halfHeight = outHeight / 2
            val halfWidth = outWidth / 2
            while (halfHeight / sampleSize >= i2 && halfWidth / sampleSize >= i) {
                sampleSize *= 2
            }
        }
        return sampleSize
    }

    @JvmStatic
    @Throws(IOException::class)
    fun a(str: String): Int {
        return when (ExifInterface(str).getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)) {
            3 -> 180
            6 -> 90
            8 -> 270
            else -> 0
        }
    }

    @JvmStatic
    fun a(bitmap: Bitmap, i: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(i.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
    }

    @JvmStatic
    fun a(bitmap: Bitmap, i: Int, i2: Int, i3: Int): Bitmap {
        val matrix = Matrix()
        val sx = if (i2 == 0) 1f else i2.toFloat()
        val sy = if (i3 == 0) 1f else i3.toFloat()
        matrix.setScale(sx, sy)
        matrix.postRotate(i.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
    }

    @JvmStatic
    fun a(str: String, i: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = i
        return BitmapFactory.decodeFile(str, options)
    }

    @JvmStatic
    fun a(str: String?, i: Int, i2: Int): Bitmap? {
        if (str == null) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(str, options)
        options.inSampleSize = a(options, i, i2)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(str, options)
    }

    @JvmStatic
    @Throws(IOException::class)
    fun a(str: String, str2: String, i: Int, i2: Int, i3: Int) {
        var bitmap = a(str, 512, 512) ?: return
        var orientation = 0
        try {
            orientation = a(str)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (orientation > 0) {
            bitmap = a(bitmap, orientation)
        }
        val transformed = a(bitmap, i, i2, i3)
        val file = File(str2)
        try {
            FileOutputStream(file).use { fos ->
                if (!transformed.compress(Bitmap.CompressFormat.PNG, 100, fos)) {
                    throw IOException("Failed to compress bitmap to $str2")
                }
                fos.flush()
            }
        } finally {
            transformed.recycle()
        }
    }
}
