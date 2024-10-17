package com.besome.sketch.ctrls

import a.a.a.wB
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.sketchware.remod.R

class CommonSpinnerItem(context: Context) : LinearLayout(context) {
    var imgSelected: ImageView? = null
    var spinnerName: TextView? = null
    var context1: Context? = null

    init {
        context1 = context
        wB.a(context, this, R.layout.common_spinner_item)
        spinnerName = findViewById(R.id.tv_spn_name)
        imgSelected = findViewById(R.id.imgv_selected)
    }

    fun setData(name: String?, isVisible: Boolean) {
        spinnerName!!.text = name
        imgSelected?.visibility = if (isVisible) VISIBLE else GONE
    }

    fun setTextSize(textSize: Float) {
        spinnerName?.textSize = textSize
    }
}
