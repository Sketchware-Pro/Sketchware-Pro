package mod.remaker.settings.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.sketchware.remod.R;

import mod.remaker.settings.ExperimentalSettingsActivity;
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
        private static final String KEY_MOD = "mod";

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preference_root, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            PreferenceFragment fragment = switch (preference.getKey()) {
                case KEY_MOD -> new ModSettingsFragment();
                default -> null;
            };
            switchFragment(fragment);
            return false;
        }

        private void switchFragment(PreferenceFragment fragment) {
            if (fragment != null && getActivity() instanceof ExperimentalSettingsActivity activity) {
                activity.switchFragment(fragment, true);
            }
        }
    }
}
