package com.besome.sketch.lib.base

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.sketchware.remod.R

open class BaseWidget(context: Context?) : LinearLayout(context) {
    private var imgWidget: ImageView? = null
    private var tvWidget: TextView? = null

    @DrawableRes
    var widgetImageResId: Int = 0
        private set
    var widgetType: Int = 0
        private set

    init {
        inflate(context, R.layout.widget_layout, this)

        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        a(context)
    }

    open fun a(context: Context?) {
        imgWidget = findViewById(R.id.img_widget)
        tvWidget = findViewById(R.id.tv_widget)

        setBackgroundResource(R.drawable.icon_bg)
        isDrawingCacheEnabled = true
    }

    var widgetName: String?
        get() = tvWidget!!.text.toString()
        set(widgetName) {
            tvWidget!!.text = widgetName
        }

    fun setWidgetImage(@DrawableRes image: Int) {
        this.widgetImageResId = image
        imgWidget!!.setImageResource(image)
    }

    fun setWidgetNameTextSize(sizeSp: Float) {
        tvWidget!!.textSize = sizeSp
    }

    fun setWidgetType(widgetType: a) {
        this.widgetType = widgetType.ordinal
    }

    enum class a {
        a,
        b
    }
}
