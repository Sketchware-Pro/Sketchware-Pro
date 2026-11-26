package a.a.a

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import com.google.android.material.textfield.TextInputLayout

class OB(
    context: Context,
    textInputLayout: TextInputLayout,
    @JvmField var f: Int,
    @JvmField var g: Int
) : MB(context, textInputLayout) {
    init {
        c = textInputLayout.editText
        c.filters = arrayOf<InputFilter>(this)
        c.addTextChangedListener(this)
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    override fun filter(
        charSequence: CharSequence?,
        i: Int,
        i2: Int,
        spanned: Spanned?,
        i3: Int,
        i4: Int
    ): CharSequence? = null

    override fun onTextChanged(charSequence: CharSequence?, i: Int, i2: Int, i3: Int) {
        val str = charSequence?.toString() ?: ""
        if (str.isEmpty()) {
            b.isErrorEnabled = true
            b.error = "$f ~ $g"
            d = false
            return
        }
        try {
            val value = str.toFloat()
            if (value < f || value > g) {
                b.isErrorEnabled = true
                b.error = "$f ~ $g"
                d = false
            } else {
                b.isErrorEnabled = false
                d = true
            }
        } catch (_: NumberFormatException) {
            b.isErrorEnabled = true
            b.error = "$f ~ $g"
            d = false
        }
    }
}
