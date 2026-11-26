package a.a.a

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView

object mB {
    @JvmField
    var a: Long = 0L

    @JvmStatic
    fun a(context: Context, editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    @JvmStatic
    fun a(view: View) {
        view.isEnabled = false
        Handler().postDelayed({ view.isEnabled = true }, 100L)
    }

    @JvmStatic
    fun a(imageView: ImageView, i: Int) {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(i.toFloat())
        imageView.colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    @JvmStatic
    fun a(): Boolean {
        if (SystemClock.elapsedRealtime() - a < 100L) {
            return true
        }
        a = SystemClock.elapsedRealtime()
        return false
    }
}
