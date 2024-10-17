package com.besome.sketch.help

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.preference.PreferenceFragmentCompat
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.sketchware.remod.R
import com.sketchware.remod.databinding.PreferenceActivityBinding
import mod.hey.studios.util.Helper

class SystemSettingActivity : BaseAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val binding = PreferenceActivityBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        binding.topAppBar.setTitle(R.string.main_drawer_title_system_settings)
        binding.topAppBar.setNavigationOnClickListener(
            Helper.getBackPressedClickListener(
                this
            )
        )
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, PreferenceFragment())
            .commit()
    }

    class PreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            preferenceManager.sharedPreferencesName = "P12"
            setPreferencesFromResource(R.xml.preferences_system_settings, rootKey)
        }

    }
}
