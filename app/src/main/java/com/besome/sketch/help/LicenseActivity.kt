package com.besome.sketch.help

import a.a.a.oB
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.activity.enableEdgeToEdge
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.sketchware.remod.databinding.ActivityOssLibrariesBinding
import mod.hey.studios.util.Helper

class LicenseActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val binding = ActivityOssLibrariesBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener(
            Helper.getBackPressedClickListener(
                this
            )
        )

        binding.licensesText.text = oB().b(applicationContext, "oss.txt")
        binding.licensesText.autoLinkMask = Linkify.WEB_URLS
        binding.licensesText.movementMethod = LinkMovementMethod.getInstance()
    }
}
