package com.besome.sketch.lib.base

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.sketchware.remod.R

open class BaseWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var imgWidget: ImageView
    private lateinit var tvWidget: TextView

    @DrawableRes
    private var widgetImgResId: Int = 0
    private var widgetType: Int = 0

    init {
        View.inflate(context, R.layout.widget_layout, this)

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

    fun getWidgetImageResId(): Int = widgetImgResId

    fun getWidgetName(): String = tvWidget.text.toString()

    fun getWidgetType(): Int = widgetType

    fun setWidgetImage(@DrawableRes image: Int) {
        widgetImgResId = image
        imgWidget.setImageResource(image)
    }

    fun setWidgetName(widgetName: String) {
        tvWidget.text = widgetName
    }

    fun setWidgetNameTextSize(sizeSp: Float) {
        tvWidget.textSize = sizeSp
    }

    fun setWidgetType(widgetType: WidgetType) {
        this.widgetType = widgetType.ordinal
    }

    enum class WidgetType {
        a,
        b
    }
}
