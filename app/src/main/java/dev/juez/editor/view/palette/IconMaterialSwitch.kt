package dev.juez.editor.view.palette;

import android.content.Context;
import com.besome.sketch.beans.LayoutBean
import com.besome.sketch.beans.ViewBean
import com.besome.sketch.editor.view.palette.IconBase
import dev.juez.beans.JuezViewBeans
import pro.sketchware.R

class IconMaterialSwitch(private val context: Context): IconBase(context) {

  override fun a(context: Context) {
    super.a(context)
    setWidgetImage(R.drawable.ic_mtrl_toggle)
    setWidgetName("MaterialSwitch")
  }
  
  override fun getBean(): ViewBean {
    return ViewBean().apply {
      type = JuezViewBeans.VIEW_TYPE_WIDGET_MATERIALSWITCH
      text.text = getName()
      convert = "com.google.android.material.materialswitch.MaterialSwitch"
      layout.apply {
        paddingLeft = 8
        paddingTop = 8
        paddingRight = 8
        paddingBottom = 8
      }
    }
  }
}