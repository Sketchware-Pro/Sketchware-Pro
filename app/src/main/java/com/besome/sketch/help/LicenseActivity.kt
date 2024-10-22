package com.besome.sketch.help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.sketchware.remod.databinding.ActivityOssLibrariesBinding
import mod.hey.studios.util.Helper
import a.a.a.oB
import androidx.activity.enableEdgeToEdge

class LicenseActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val binding = ActivityOssLibrariesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this))

        binding.licensesText.apply {
            text = oB().b(applicationContext, "oss.txt")
            autoLinkMask = Linkify.WEB_URLS
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}