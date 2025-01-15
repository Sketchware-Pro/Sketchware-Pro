package dev.juez.beans

import com.google.common.collect.BiMap
import com.google.common.collect.ImmutableBiMap
import pro.sketchware.R

object JuezViewBeans {
  const val VIEW_TYPE_WIDGET_MATERIALSWITCH = 49

  val views: BiMap<Int, String> = ImmutableBiMap.builder<Int, String>()
    .put(VIEW_TYPE_WIDGET_MATERIALSWITCH, "MaterialSwitch")
    .build()

  @JvmStatic fun buildClassInfo(id: Int): String = getViewTypeName(id)

  @JvmStatic fun getViewTypeByTypeName(typeName: String): Int = views.inverse().getOrDefault(typeName, 0)

  @JvmStatic fun getViewTypeName(id: Int): String = views.getOrDefault(id, "")

  @JvmStatic
  fun getViewTypeResId(id: Int): Int {
    return when (id) {
      VIEW_TYPE_WIDGET_MATERIALSWITCH -> R.drawable.ic_mtrl_toggle
      else -> id
    }
  }
}
