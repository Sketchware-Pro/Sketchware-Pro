package mod.remaker.settings.fragment;

import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_BACKUP_DIRECTORY;
import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_BACKUP_FILENAME;
import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_RESET_BACKUP_FILENAME_FORMAT;
import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_ROOT_AUTO_INSTALL_PROJECTS;
import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING;
import static mod.hilal.saif.activities.tools.ConfigActivity.getDefaultValue;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.sketchware.remod.R;
import com.topjohnwu.superuser.Shell;

import java.util.HashMap;

import mod.SketchwareUtil;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.ExperimentalSettingsActivity;
import mod.remaker.settings.PreferenceContentFragment;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.fragment.ChangeBackupDirectoryFragment.BackupDirectorySelectListener;
import mod.remaker.settings.model.BackupDirectory;

public class ModSettingsFragment extends PreferenceFragment {
    @Override
    public String getTitle(Context context) {
        return "Mod Settings";
    }

    @Override
    public PreferenceContentFragment getContentFragment() {
        return new ModSettingsFragmentContent();
    }

    public static class ModSettingsFragmentContent extends PreferenceContentFragment implements BackupDirectorySelectListener {
        private Preference backupPreference;

        @Override
        public void onDirectorySelect(BackupDirectory directory) {
            if (backupPreference != null) {
                backupPreference.setSummary(directory.path());
            }
        }

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preference_mod, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            if (preference instanceof SwitchPreferenceCompat switchPreference) {
                ConfigActivity.setSetting(key, switchPreference.isChecked());
            }
            if (key.equals(SETTING_BACKUP_DIRECTORY)) {
                ChangeBackupDirectoryFragment fragment = new ChangeBackupDirectoryFragment();
                fragment.setOnBackupDirectorySelectListener(this);
                switchFragment(fragment);
            }
            if (key.equals(SETTING_RESET_BACKUP_FILENAME_FORMAT)) {
                ConfigActivity.removeSetting(SETTING_BACKUP_FILENAME);
                SketchwareUtil.toast("Reset to default value complete.");
            }
            return false;
        }

        @Override
        public void onSetupPreference(Preference preference) {
            String key = preference.getKey();
            HashMap<String, Object> settingMap = ConfigActivity.readSettings();

            if (preference instanceof SwitchPreferenceCompat switchPreference) {
                if (settingMap.containsKey(key)) {
                    Object value = settingMap.get(key);
                    if (value == null) {
                        ConfigActivity.removeSetting(key);
                    } else {
                        if (value instanceof Boolean) {
                            switchPreference.setChecked((boolean) value);
                        } else {
                            SketchwareUtil.toastError("Detected invalid value for preference \""
                                    + preference.getTitle() + "\". Restoring defaults");
                            ConfigActivity.removeSetting(key);
                        }
                    }
                } else {
                    boolean defaultValue = Boolean.parseBoolean((String) getDefaultValue(key));
                    ConfigActivity.setSetting(key, defaultValue);
                    switchPreference.setChecked(defaultValue);
                }
            }

            if (key.equals(SETTING_ROOT_AUTO_INSTALL_PROJECTS) || key.equals(SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING)) {
                Shell shell = Shell.getShell();
                if (!shell.isRoot()) {
                    preference.setVisible(false);
                }
            }

            if (key.equals(SETTING_BACKUP_DIRECTORY)) {
                backupPreference = preference;
                backupPreference.setSummary(ConfigActivity.getBackupPath());
            }
        }

        private void switchFragment(PreferenceFragment fragment) {
            if (fragment != null && getActivity() instanceof ExperimentalSettingsActivity activity) {
                activity.switchFragment(fragment, true);
            }
        }
    }
}
