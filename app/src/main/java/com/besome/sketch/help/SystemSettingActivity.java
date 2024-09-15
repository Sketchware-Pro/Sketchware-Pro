package com.besome.sketch.help;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PreferenceActivityBinding;

public class SystemSettingActivity extends BaseAppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = PreferenceActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topAppBar.setTitle(R.string.main_drawer_title_system_settings);
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), new PreferenceFragment())
                .commit();
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            getPreferenceManager().setSharedPreferencesName("P12");
            setPreferencesFromResource(R.xml.preferences_system_settings, rootKey);
        }
    }
}
