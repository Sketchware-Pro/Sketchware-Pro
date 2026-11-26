package a.a.a

import android.content.Context
import android.content.SharedPreferences
import java.util.HashMap

class DB(context: Context, str: String) {
    @JvmField
    var a: SharedPreferences = context.getSharedPreferences(str, 0)

    @JvmField
    var b: SharedPreferences.Editor = a.edit()

    fun a(str: String, i: Int): Int = a.getInt(str, i)

    fun a(str: String, str2: String?): String? = a.getString(str, str2)

    fun a(str: String, obj: Any?) {
        a(str, obj, true)
    }

    fun a(str: String, obj: Any?, z: Boolean) {
        when (obj) {
            is String -> b.putString(str, obj)
            is Int -> b.putInt(str, obj)
            is Long -> b.putLong(str, obj)
            is Boolean -> b.putBoolean(str, obj)
        }
        if (z) {
            b.commit()
        }
    }

    fun a(str: String, hashMap: HashMap<String, Any?>?) {
        a(str, vB.a(hashMap) as Any?)
    }

    fun a(): Boolean {
        b.clear()
        return b.commit()
    }

    fun a(str: String): Boolean {
        b.remove(str)
        return b.commit()
    }

    fun a(str: String, z: Boolean): Boolean = a.getBoolean(str, z)

    fun b(): Boolean = b.commit()

    fun b(str: String): Boolean = a.contains(str)

    @Suppress("UNCHECKED_CAST")
    fun c(): HashMap<String, Any?> {
        return try {
            a.all as HashMap<String, Any?>
        } catch (_: Exception) {
            HashMap()
        }
    }

    fun c(str: String): Boolean = a.getBoolean(str, false)

    fun d(str: String): Int = a(str, 0)

    fun e(str: String): Long = a.getLong(str, 0L)

    fun f(str: String): String = a(str, "") ?: ""

    fun g(str: String): HashMap<String, Any?>? {
        val value = f(str)
        return if (value.isEmpty()) HashMap() else vB.a(value)
    }
}
