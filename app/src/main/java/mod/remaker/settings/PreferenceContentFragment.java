package mod.remaker.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import mod.remaker.util.ThemeUtils;

public abstract class PreferenceContentFragment extends PreferenceFragmentCompat {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ThemeUtils.getColor(view, android.R.attr.colorBackground));

        PreferenceScreen screen = getPreferenceScreen();
        for (int i = 0; i < screen.getPreferenceCount(); i++) {
            Preference preference = screen.getPreference(i);
            setupPreference(preference);
        }
    }

    private void setupPreference(Preference preference) {
        if (!preference.isVisible()) return;
        if (preference instanceof PreferenceCategory category) {
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                Preference pref = category.getPreference(i);
                setupPreference(pref);
            }
            return;
        }
        onSetupPreference(preference);
    }

    protected void onSetupPreference(Preference preference) {
    }
}
