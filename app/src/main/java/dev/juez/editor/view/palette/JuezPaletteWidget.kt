package dev.juez.editor.view.palette

import android.content.Context
import com.besome.sketch.editor.view.palette.IconBase

class JuezPaletteWidget(private val context: Context) {
  fun extraWidget(title: String): IconBase? {
    return when (title) {
      "MaterialSwitch" -> IconMaterialSwitch(context)
      else -> null
    }
  }
}