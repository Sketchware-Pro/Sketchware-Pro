package com.besome.sketch

import a.a.a.mB
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import androidx.annotation.IdRes
import androidx.core.view.WindowInsetsCompat
import com.besome.sketch.help.ProgramInfoActivity
import com.besome.sketch.tools.NewKeyStoreActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap
import com.sketchware.remod.R
import dev.chrisbanes.insetter.Insetter
import dev.chrisbanes.insetter.Side
import mod.hilal.saif.activities.tools.AppSettings
import mod.ilyasse.activities.about.AboutActivity

class MainDrawer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.navigationViewStyle
) : NavigationView(
    wrap(context, attrs, defStyleAttr, R.style.Widget_SketchwarePro_NavigationView_Main),
    attrs,
    defStyleAttr
) {

    init {
        val layoutDirection = context.resources.configuration.layoutDirection
        Insetter.builder()
            .margin(
                WindowInsetsCompat.Type.navigationBars(),
                Side.create(
                    left = layoutDirection == LAYOUT_DIRECTION_LTR,
                    top = false,
                    right = layoutDirection == LAYOUT_DIRECTION_RTL,
                    bottom = false
                )
            )
            .applyToView(this)

        inflateHeaderView(R.layout.main_drawer_header)
        inflateMenu(R.menu.main_drawer_menu)
        setNavigationItemSelectedListener { item ->
            handleNavigationItemSelected(item.itemId)
            false // Prevent selection
        }
    }

    private fun handleNavigationItemSelected(@IdRes itemId: Int) {
        if (!mB.a()) {
            handleSocialLinks(itemId)
        }
        handleDrawerItems(itemId)
    }

    private fun handleSocialLinks(@IdRes itemId: Int) {
        val urlResId = when (itemId) {
            R.id.social_discord -> R.string.link_discord_invite
            R.id.social_telegram -> R.string.link_telegram_invite
            R.id.social_github -> R.string.link_github_url
            else -> null
        }

        urlResId?.let { openUrl(context.getString(it)) }
    }

    private fun handleDrawerItems(@IdRes itemId: Int) {
        when (itemId) {
            R.id.about_team -> startActivity<AboutActivity>()
            R.id.changelog -> startActivity<AboutActivity>("select" to "changelog")
            R.id.program_info -> startActivityForResult<ProgramInfoActivity>(105)
            R.id.app_settings -> startActivity<AppSettings>()
            R.id.create_release_keystore -> startActivity<NewKeyStoreActivity>()
        }
    }

    private inline fun <reified T : Activity> startActivity(vararg extras: Pair<String, String?>) {
        val intent = Intent(context, T::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            extras.forEach { putExtra(it.first, it.second) }
        }
        context.startActivity(intent)
    }

    private inline fun <reified T : Activity> startActivityForResult(requestCode: Int) {
        val intent = Intent(context, T::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        (context as? Activity)?.startActivityForResult(intent, requestCode)
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}