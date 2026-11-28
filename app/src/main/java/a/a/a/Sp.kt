package a.a.a

object Sp {
    @JvmField
    var a: Boolean = false

    @JvmField
    var b: LongArray? = LongArray(10)

    @JvmStatic
    fun a(i: Int): Long {
        if (b == null) {
            b = LongArray(10)
        }
        return System.currentTimeMillis() - b!![i]
    }

    @JvmStatic
    fun b(i: Int): Boolean {
        if (b == null) {
            b = LongArray(10)
        }
        return b!![i] == 0L || a(i) > 30000L
    }
}
