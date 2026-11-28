package a.a.a

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Random

object FB {
    private class Tokenizer(@JvmField var a: String) {
        @JvmField
        var b: Int = 0

        fun a(): Boolean = b >= a.length

        fun b(): String {
            c()
            var str = ""
            if (a()) {
                return ""
            }
            var inFormat = false
            val start = b
            while (b < a.length && a[b] != ' ') {
                val ch = a[b]
                if (ch != '\\') {
                    if (ch == '%') {
                        if (b > start) {
                            break
                        }
                        inFormat = true
                    }
                    if (inFormat && (ch == '?' || ch == '-')) {
                        break
                    }
                    str += ch
                    b++
                } else {
                    str += ch.code + a[b + 1].code
                    b += 2
                }
            }
            return str
        }

        fun c() {
            while (b < a.length && a[b] == ' ') {
                b++
            }
        }
    }

    @JvmStatic
    fun a(c: Char): Int {
        if (c in '0'..'9') {
            return c.code - '0'.code
        }
        val base = when (c) {
            in 'A'..'F' -> 'A'
            in 'a'..'f' -> 'a'
            else -> throw IllegalArgumentException("invalid hex digit '$c'")
        }
        return c.code - base.code + 10
    }

    @JvmStatic
    fun a(): String {
        val random = Random()
        while (true) {
            val nextInt = random.nextInt(100000)
            if (nextInt in 10000..99999) {
                return nextInt.toString()
            }
        }
    }

    @JvmStatic
    fun a(i: Int): String {
        if (i < 0) {
            return "0"
        }
        val f = i.toFloat()
        return when {
            f >= 1024.0f && f < 1048576.0f -> DecimalFormat("#.#KB").format((f / 1024.0f).toDouble())
            f >= 1048576.0f && f < 1.07374182E9f -> DecimalFormat("#.#MB").format((f / 1048576.0f).toDouble())
            f >= 1.07374182E9f && f < 1.09951163E12f -> DecimalFormat("#.#GB").format((f / 1.07374182E9f).toDouble())
            else -> "${i}B"
        }
    }

    @JvmStatic
    fun a(bArr: ByteArray): String {
        val sb = StringBuffer(bArr.size * 2)
        for (b in bArr) {
            val unsigned = b.toInt() and 255
            if (unsigned < 16) {
                sb.append("0")
            }
            sb.append(unsigned.toString(16))
        }
        return sb.toString()
    }

    @JvmStatic
    fun a(context: Context, str: String, str2: String) {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(ClipData.newPlainText(str, str2))
    }

    @JvmStatic
    fun a(str: String): ByteArray {
        val length = str.length
        val result = ByteArray((length + 1) / 2)
        var i = 0
        var outIndex = 0
        if (length % 2 == 1) {
            result[0] = a(str[0]).toByte()
            i = 1
            outIndex = 1
        }
        while (i < length) {
            val next = i + 1
            result[outIndex] = ((a(str[i]) shl 4) or a(str[next])).toByte()
            outIndex++
            i = next + 1
        }
        return result
    }

    @JvmStatic
    fun b(i: Int): String {
        val f = i.toFloat()
        return when {
            f >= 1000.0f && f < 1000000.0f -> DecimalFormat("#.#K").format((f / 1000.0f).toDouble())
            f >= 1000000.0f && f < 1.0E9f -> DecimalFormat("#.#M").format((f / 1000000.0f).toDouble())
            f >= 1.0E9f && f < 1.0E12f -> DecimalFormat("#.#G").format((f / 1.0E9f).toDouble())
            else -> i.toString()
        }
    }

    @JvmStatic
    fun b(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (_: NumberFormatException) {
            false
        }
    }

    @JvmStatic
    fun c(i: Int): String = DecimalFormat("#,###").format(i.toLong())

    @JvmStatic
    fun c(str: String): ArrayList<String> {
        val result = ArrayList<String>()
        val tokenizer = Tokenizer(str)
        while (!tokenizer.a()) {
            val token = tokenizer.b()
            if (token.isNotEmpty()) {
                result.add(token)
            }
        }
        return result
    }

    @JvmStatic
    fun d(str: String): String {
        var result = ""
        var i = 0
        while (i < str.length) {
            val ch = str[i]
            if (ch == '\\') {
                i++
                result += str[i]
            } else {
                result += ch
            }
            i++
        }
        return result
    }
}
