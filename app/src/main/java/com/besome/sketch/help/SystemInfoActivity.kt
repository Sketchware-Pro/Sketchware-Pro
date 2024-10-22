package com.besome.sketch.help

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.besome.sketch.lib.ui.PropertyOneLineItem
import com.besome.sketch.lib.ui.PropertyTwoLineItem
import com.sketchware.remod.R
import com.sketchware.remod.databinding.ActivitySystemInfoBinding
import a.a.a.GB
import a.a.a.mB
import androidx.activity.enableEdgeToEdge
import mod.hey.studios.util.Helper

class SystemInfoActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivitySystemInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivitySystemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this))

        addApiLevelInfo()
        addAndroidVersionNameInfo()
        addScreenResolutionInfo()
        addScreenDpiInfo()
        addModelNameInfo()
        addDeveloperOptionsShortcut()
    }

    private fun addInfo(key: Int, name: String, description: String) {
        val propertyTwoLineItem = PropertyTwoLineItem(this).apply {
            setKey(key)
            setName(name)
            setDesc(description)
        }
        binding.content.addView(propertyTwoLineItem)
    }

    private fun addAndroidVersionNameInfo() {
        addInfo(
            1,
            Helper.getResString(R.string.system_information_title_android_version),
            GB.b() + " (" + Build.VERSION.RELEASE + ")"
        )
    }

    private fun addApiLevelInfo() {
        addInfo(
            0,
            Helper.getResString(R.string.system_information_title_android_version),
            "API - ${Build.VERSION.SDK_INT}"
        )
    }

    private fun addDeveloperOptionsShortcut() {
        val propertyOneLineItem = PropertyOneLineItem(this).apply {
            key = 5
            setName(Helper.getResString(R.string.system_information_developer_options))
            setOnClickListener {
                if (!mB.a()) {
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                        startActivity(intent)
                    } catch (ignored: ActivityNotFoundException) {
                    }
                }
            }
        }
        binding.content.addView(propertyOneLineItem)
    }

    private fun addScreenDpiInfo() {
        val dpiXY = GB.b(this)
        addInfo(3, Helper.getResString(R.string.system_information_dpi), dpiXY[0].toString())
    }

    private fun addModelNameInfo() {
        addInfo(4, Helper.getResString(R.string.system_information_model_name), Build.MODEL)
    }

    private fun addScreenResolutionInfo() {
        val widthHeight = GB.c(this)
        addInfo(
            2,
            Helper.getResString(R.string.system_information_system_resolution),
            "${widthHeight[0]} x ${widthHeight[1]}"
        )
    }
}
