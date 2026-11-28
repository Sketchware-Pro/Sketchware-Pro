package a.a.a

import com.besome.sketch.beans.ProjectFileBean

object vq {
    @JvmField
    val a: Array<String> = arrayOf("Default", "NoActionBar", "FullScreen")

    @JvmField
    val b: Array<String> = arrayOf("Portrait", "Landscape", "Both")

    @JvmStatic
    fun a(state: Int): String {
        val masked = state and ProjectFileBean.OPTION_ACTIVITY_MASK
        return when (masked) {
            ProjectFileBean.KEYBOARD_STATE_VISIBLE -> "stateVisible"
            ProjectFileBean.KEYBOARD_STATE_HIDDEN -> "stateHidden"
            else -> ""
        }
    }
}
