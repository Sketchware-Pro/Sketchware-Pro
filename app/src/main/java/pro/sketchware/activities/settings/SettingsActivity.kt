package pro.sketchware.activities.settings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment

import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.sketchware.remod.databinding.ActivitySettingsBinding

import pro.sketchware.fragments.settings.events.EventsManagerFragment
import pro.sketchware.fragments.settings.appearance.SettingsAppearanceFragment
import pro.sketchware.fragments.settings.selector.block.BlockSelectorManagerFragment

class SettingsActivity : BaseAppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    companion object {
        const val FRAGMENT_TAG_EXTRA = "fragment_tag"
        
        const val SETTINGS_APPEARANCE_FRAGMENT = "settings_appearance"
        const val EVENTS_MANAGER_FRAGMENT = "events_manager"
        const val BLOCK_SELECTOR_MANAGER_FRAGMENT = "block_selector_manager"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val fragment = when (val fragmentTag = intent.getStringExtra(FRAGMENT_TAG_EXTRA)) {
            SETTINGS_APPEARANCE_FRAGMENT -> SettingsAppearanceFragment()
            EVENTS_MANAGER_FRAGMENT -> EventsManagerFragment()
            BLOCK_SELECTOR_MANAGER_FRAGMENT -> BlockSelectorManagerFragment()
            else -> throw IllegalArgumentException("Unknown fragment tag: $fragmentTag")
        }
        
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.settingsFragmentContainer.id, fragment)
            .commit()
    }
}
