package a.a.a

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class nB {
    fun a(): Long = Date().time

    fun a(j: Long, str: String): String = SimpleDateFormat(str, Locale.ENGLISH).format(Date(j))

    fun a(str: String): String? {
        return try {
            SimpleDateFormat(str).format(Date())
        } catch (_: Exception) {
            null
        }
    }

    fun a(str: String, str2: String): String {
        val sdf = SimpleDateFormat(str2)
        return try {
            val time = sdf.parse(str)?.time ?: return str
            val offset = TimeZone.getDefault().getOffset(time)
            sdf.format(Date(time + offset))
        } catch (_: Exception) {
            str
        }
    }

    fun a(str: String, str2: String, str3: String): String {
        return try {
            val time = SimpleDateFormat(str2).parse(str)?.time ?: return str
            val offset = TimeZone.getDefault().getOffset(time)
            SimpleDateFormat(str3).format(Date(time + offset))
        } catch (_: Exception) {
            str
        }
    }

    fun b(str: String, str2: String): Long {
        return try {
            SimpleDateFormat(str2, Locale.ENGLISH).parse(str)?.time ?: 0L
        } catch (_: ParseException) {
            0L
        }
    }

    fun b(str: String): String? {
        val sdf = SimpleDateFormat(str)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return try {
            sdf.format(Date())
        } catch (_: Exception) {
            null
        }
    }
}
