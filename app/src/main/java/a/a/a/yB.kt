package a.a.a

object yB {
    @JvmStatic
    fun a(map: Map<String, Any?>?, str: String, i: Int): Int {
        val value = a(map, str, i as Any?)
        return when (value) {
            is Int -> value
            is Double -> value.toInt()
            else -> i
        }
    }

    @JvmStatic
    fun a(map: Map<String, Any?>?, str: String, obj: Any?): Any? {
        if (map != null) {
            val found = map[str]
            if (found != null) {
                return found
            }
        }
        return obj
    }

    @JvmStatic
    fun a(map: Map<String, Any?>?, str: String): Boolean {
        return (a(map, str, false as Any?) as? Boolean) ?: false
    }

    @JvmStatic
    fun a(map: Map<String, Any?>?, str: String, z: Boolean): Boolean {
        return (a(map, str, z as Any?) as? Boolean) ?: z
    }

    @JvmStatic
    fun b(map: Map<String, Any?>?, str: String): Int {
        return a(map, str, -1)
    }

    @JvmStatic
    fun c(map: Map<String, Any?>?, str: String): String {
        return (a(map, str, "" as Any?) as? String) ?: ""
    }
}
