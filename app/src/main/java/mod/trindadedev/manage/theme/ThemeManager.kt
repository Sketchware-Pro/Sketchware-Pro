package mod.trindadedev.manage.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    const val THEME_PREF: String = "themedata"
    const val THEME_KEY: String = "idetheme"

    const val THEME_SYSTEM: Int = 0
    const val THEME_LIGHT: Int = 1
    const val THEME_DARK: Int = 2

    @JvmStatic
    fun applyTheme(context: Context, type: Int) {
        saveTheme(context, type)

        when (type) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    @JvmStatic
    fun getCurrentTheme(context: Context): Int {
        val preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
        return preferences.getInt(THEME_KEY, THEME_SYSTEM)
    }

    private fun saveTheme(context: Context, theme: Int) {
        val preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(THEME_KEY, theme)
        editor.apply()
    }
}