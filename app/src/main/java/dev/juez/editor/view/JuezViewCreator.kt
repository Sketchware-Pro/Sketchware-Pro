package dev.juez.editor.view

import android.content.Context
import android.view.View
import com.besome.sketch.beans.ViewBean
import com.besome.sketch.editor.view.ViewPane
import dev.juez.beans.JuezViewBeans
import dev.juez.editor.view.item.ItemMaterialSwitch

class JuezViewCreator(private val context: Context) {
  fun create(bean: ViewBean): View {
    return when (bean.type) {
      JuezViewBeans.VIEW_TYPE_WIDGET_MATERIALSWITCH -> ItemMaterialSwitch(context)
      else -> ViewPane.getUnknownItemView(context, bean);
    }
  }
}