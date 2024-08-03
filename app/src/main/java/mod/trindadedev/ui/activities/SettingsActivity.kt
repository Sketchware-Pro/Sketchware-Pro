package mod.trindadedev.ui.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.sketchware.remod.R
import com.sketchware.remod.databinding.ActivitySettingsBinding

import mod.trindadedev.ui.fragments.settings.appearance.SettingsAppearanceFragment

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    companion object {
        const val FRAGMENT_TAG_EXTRA = "fragment_tag"
        const val SETTINGS_APPEARANCE_FRAGMENT = "settings_appearance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentTag = intent.getStringExtra(FRAGMENT_TAG_EXTRA)
        val fragment = when (fragmentTag) {
            SETTINGS_APPEARANCE_FRAGMENT -> SettingsAppearanceFragment()
            else -> throw IllegalArgumentException("Unknown fragment tag: $fragmentTag")
        }
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragment_container.id, fragment)
            .commit()
    }
}
