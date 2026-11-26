package a.a.a

object vq {
    @JvmField
    val a: Array<String> = arrayOf("Default", "NoActionBar", "FullScreen")

    @JvmField
    val b: Array<String> = arrayOf("Portrait", "Landscape", "Both")

    @JvmStatic
    fun a(i: Int): String {
        val masked = i and 15
        return when (masked) {
            1 -> "stateVisible"
            2 -> "stateHidden"
            else -> ""
        }
    }
}
