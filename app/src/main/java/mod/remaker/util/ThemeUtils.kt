package mod.remaker.util

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.google.android.material.color.MaterialColors

object ThemeUtils {
    @JvmStatic
    @ColorInt
    fun getColor(view: View, @AttrRes resourceId: Int): Int {
        return MaterialColors.getColor(view, resourceId)
    }

    @JvmStatic
    fun isDarkThemeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        val nightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightMode == Configuration.UI_MODE_NIGHT_YES
    }
}
