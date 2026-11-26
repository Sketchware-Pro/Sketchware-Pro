package a.a.a

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.NinePatch
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream

object zB {
    @JvmStatic
    @Throws(Exception::class)
    fun a(inputStream: InputStream): Bitmap {
        val decoded = BitmapFactory.decodeStream(inputStream)
        val chunk = a(decoded)
        if (!NinePatch.isNinePatchChunk(chunk)) {
            return decoded
        }
        val cropped = Bitmap.createBitmap(decoded, 1, 1, decoded.width - 2, decoded.height - 2)
        decoded.recycle()
        val field = cropped.javaClass.getDeclaredField("mNinePatchChunk")
        field.isAccessible = true
        field.set(cropped, chunk)
        return cropped
    }

    @JvmStatic
    @Throws(Exception::class)
    fun a(str: String): Bitmap {
        FileInputStream(str).use { fis ->
            return a(fis)
        }
    }

    @JvmStatic
    fun a(bitmap: Bitmap, bArr: ByteArray) {
        val xPixels = IntArray(bitmap.width - 2)
        bitmap.getPixels(xPixels, 0, xPixels.size, 1, bitmap.height - 1, xPixels.size, 1)
        for (i in xPixels.indices) {
            if (-16777216 == xPixels[i]) {
                a(bArr, 12, i)
                break
            }
        }
        for (i in xPixels.size - 1 downTo 0) {
            if (-16777216 == xPixels[i]) {
                a(bArr, 16, xPixels.size - i - 1)
                break
            }
        }
        val yPixels = IntArray(bitmap.height - 2)
        bitmap.getPixels(yPixels, 0, 1, bitmap.width - 1, 1, 1, yPixels.size)
        for (i in yPixels.indices) {
            if (-16777216 == yPixels[i]) {
                a(bArr, 20, i)
                break
            }
        }
        for (i in yPixels.size - 1 downTo 0) {
            if (-16777216 == yPixels[i]) {
                a(bArr, 24, yPixels.size - i - 1)
                return
            }
        }
    }

    @JvmStatic
    fun a(outputStream: OutputStream, i: Int) {
        outputStream.write((i ushr 0) and 255)
        outputStream.write((i ushr 8) and 255)
        outputStream.write((i ushr 16) and 255)
        outputStream.write((i ushr 24) and 255)
    }

    @JvmStatic
    fun a(bArr: ByteArray, i: Int, i2: Int) {
        bArr[i + 0] = (i2 shr 0).toByte()
        bArr[i + 1] = (i2 shr 8).toByte()
        bArr[i + 2] = (i2 shr 16).toByte()
        bArr[i + 3] = (i2 shr 24).toByte()
    }

    @JvmStatic
    fun a(bitmap: Bitmap): ByteArray {
        val width = bitmap.width
        val height = bitmap.height
        val baos = ByteArrayOutputStream()
        for (i in 0 until 32) {
            baos.write(0)
        }
        val xLen = width - 2
        val xPixels = IntArray(xLen)
        bitmap.getPixels(xPixels, 0, width, 1, 0, xLen, 1)
        val xStartBlack = xPixels[0] == -16777216
        val xEndBlack = xPixels[xPixels.size - 1] == -16777216
        var prevX = false
        var numXDivs = 0
        for (i in xPixels.indices) {
            val isBlack = xPixels[i] == -16777216
            if (prevX != isBlack) {
                numXDivs++
                a(baos, i)
                prevX = isBlack
            }
        }
        if (xEndBlack) {
            numXDivs++
            a(baos, xPixels.size)
        }
        var numXColors = numXDivs + 1
        if (xStartBlack) {
            numXColors--
        }
        if (xEndBlack) {
            numXColors--
        }
        val yLen = height - 2
        val yPixels = IntArray(yLen)
        bitmap.getPixels(yPixels, 0, 1, 0, 1, 1, yLen)
        val yStartBlack = yPixels[0] == -16777216
        val yEndBlack = yPixels[yPixels.size - 1] == -16777216
        var prevY = false
        var numYDivs = 0
        for (i in yPixels.indices) {
            val isBlack = yPixels[i] == -16777216
            if (prevY != isBlack) {
                numYDivs++
                a(baos, i)
                prevY = isBlack
            }
        }
        if (yEndBlack) {
            numYDivs++
            a(baos, yPixels.size)
        }
        var numYColors = numYDivs + 1
        if (yStartBlack) {
            numYColors--
        }
        if (yEndBlack) {
            numYColors--
        }
        val numColors = numXColors * numYColors
        require(numXDivs <= 255 && numYDivs <= 255 && numColors <= 255) {
            "NinePatch chunk counts exceed unsigned byte limit"
        }
        for (i in 0 until numColors) {
            a(baos, 1)
        }
        val byteArray = baos.toByteArray()
        byteArray[0] = 1
        byteArray[1] = numXDivs.toByte()
        byteArray[2] = numYDivs.toByte()
        byteArray[3] = numColors.toByte()
        a(bitmap, byteArray)
        return byteArray
    }
}
