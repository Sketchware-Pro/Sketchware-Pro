package mod.remaker.settings.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sketchware.remod.R;

import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;

public class RootSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Sketchware Settings";
    }

    @Override
    public PreferenceContentFragment getContentFragment() {
        return new RootSettingsFragmentContent();
    }

    public static class RootSettingsFragmentContent extends PreferenceContentFragment {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preference_root, rootKey);
        }
    }
}
