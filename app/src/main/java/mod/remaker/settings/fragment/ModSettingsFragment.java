package mod.remaker.settings.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sketchware.remod.R;

import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;

public class ModSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Mod Settings";
    }

    @Override
    public PreferenceContentFragment getContentFragment() {
        return new ModSettingsFragmentContent();
    }

    public static class ModSettingsFragmentContent extends PreferenceContentFragment {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preference_mod, rootKey);
        }
    }
}
