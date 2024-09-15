package mod.hilal.saif.activities.tools;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.android.annotations.NonNull;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.DialogCreateNewFileLayoutBinding;
import com.sketchware.remod.databinding.PreferenceActivityBinding;
import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;

public class ConfigActivity extends BaseAppCompatActivity {

    public static final File SETTINGS_FILE = new File(FileUtil.getExternalStorageDir(), ".sketchware/data/settings.json");
    public static final String SETTING_ALWAYS_SHOW_BLOCKS = "always-show-blocks";
    public static final String SETTING_BACKUP_DIRECTORY = "backup-dir";
    public static final String SETTING_ROOT_AUTO_INSTALL_PROJECTS = "root-auto-install-projects";
    public static final String SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING = "root-auto-open-after-installing";
    public static final String SETTING_BACKUP_FILENAME = "backup-filename";
    public static final String SETTING_LEGACY_CODE_EDITOR = "legacy-ce";
    public static final String SETTING_SHOW_BUILT_IN_BLOCKS = "built-in-blocks";
    public static final String SETTING_SHOW_EVERY_SINGLE_BLOCK = "show-every-single-block";
    public static final String SETTING_USE_NEW_VERSION_CONTROL = "use-new-version-control";
    public static final String SETTING_USE_ASD_HIGHLIGHTER = "use-asd-highlighter";
    public static final String SETTING_SKIP_MAJOR_CHANGES_REMINDER = "skip-major-changes-reminder";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH = "palletteDir";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH = "blockDir";
    private HashMap<String, Object> setting_map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        var binding = PreferenceActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.topAppBar.setTitle("Mod Settings");

        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            setting_map = readSettings();
            if (!setting_map.containsKey(SETTING_SHOW_BUILT_IN_BLOCKS) || !setting_map.containsKey(SETTING_ALWAYS_SHOW_BLOCKS)) {
                restoreDefaultSettings();
            }
        } else {
            restoreDefaultSettings();
        }

        var fragment = new PreferenceFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public static String getBackupPath() {
        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            HashMap<String, Object> settings = readSettings();
            if (settings.containsKey(SETTING_BACKUP_DIRECTORY)) {
                Object value = settings.get(SETTING_BACKUP_DIRECTORY);
                if (value instanceof String) {
                    return (String) value;
                } else {
                    SketchwareUtil.toastError("Detected invalid preference "
                                    + "for backup directory. Restoring defaults",
                            Toast.LENGTH_LONG);
                    settings.remove(SETTING_BACKUP_DIRECTORY);
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
                }
            }
        }
        return "/.sketchware/backups/";
    }

    public static String getStringSettingValueOrSetAndGet(String settingKey, String toReturnAndSetIfNotFound) {
        HashMap<String, Object> settings = readSettings();

        Object value = settings.get(settingKey);
        if (value instanceof String) {
            return (String) value;
        } else {
            settings.put(settingKey, toReturnAndSetIfNotFound);
            FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));

            return toReturnAndSetIfNotFound;
        }
    }

    public static String getBackupFileName() {
        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            HashMap<String, Object> settings = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);
            if (settings.containsKey(SETTING_BACKUP_FILENAME)) {
                Object value = settings.get(SETTING_BACKUP_FILENAME);
                if (value instanceof String) {
                    return (String) value;
                } else {
                    SketchwareUtil.toastError("Detected invalid preference "
                                    + "for backup filename. Restoring defaults",
                            Toast.LENGTH_LONG);
                    settings.remove(SETTING_BACKUP_FILENAME);
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
                }
            }
        }
        return "$projectName v$versionName ($pkgName, $versionCode) $time(yyyy-MM-dd'T'HHmmss)";
    }

    public static boolean isLegacyCeEnabled() {
        /* The legacy Code Editor is specifically opt-in */
        if (!FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            return false;
        }

        HashMap<String, Object> settings = readSettings();
        if (settings.containsKey(SETTING_LEGACY_CODE_EDITOR)) {
            Object value = settings.get(SETTING_LEGACY_CODE_EDITOR);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else {
                SketchwareUtil.toastError("Detected invalid preference for legacy "
                                + " Code Editor. Restoring defaults",
                        Toast.LENGTH_LONG);
                settings.remove(SETTING_LEGACY_CODE_EDITOR);
                FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
            }
        }
        return false;
    }

    public static boolean isSettingEnabled(String keyName) {
        if (!FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            return false;
        }

        HashMap<String, Object> settings = readSettings();
        if (settings.containsKey(keyName)) {
            Object value = settings.get(keyName);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else {
                SketchwareUtil.toastError("Detected invalid preference. Restoring defaults",
                        Toast.LENGTH_LONG);
                settings.remove(keyName);
                FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
            }
        }
        return false;
    }

    public static void setSetting(String key, Object value) {
        HashMap<String, Object> settings = readSettings();
        settings.put(key, value);
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
    }

    @NonNull
    private static HashMap<String, Object> readSettings() {
        HashMap<String, Object> settings;

        if (SETTINGS_FILE.exists()) {
            Exception toLog;

            try {
                settings = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);

                if (settings != null) {
                    return settings;
                }

                toLog = new NullPointerException("settings == null");
                // fall-through to shared error handler
            } catch (JsonParseException e) {
                toLog = e;
                // fall-through to shared error handler
            }

            SketchwareUtil.toastError("Couldn't parse Mod Settings! Restoring defaults.");
            LogUtil.e("ConfigActivity", "Failed to parse Mod Settings.", toLog);
        }
        settings = new HashMap<>();
        restoreDefaultSettings(settings);

        return settings;
    }

    private static void restoreDefaultSettings(HashMap<String, Object> settings) {
        settings.clear();

        List<String> keys = Arrays.asList(SETTING_ALWAYS_SHOW_BLOCKS,
                SETTING_BACKUP_DIRECTORY,
                SETTING_LEGACY_CODE_EDITOR,
                SETTING_ROOT_AUTO_INSTALL_PROJECTS,
                SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING,
                SETTING_SHOW_BUILT_IN_BLOCKS,
                SETTING_SHOW_EVERY_SINGLE_BLOCK,
                SETTING_USE_NEW_VERSION_CONTROL,
                SETTING_USE_ASD_HIGHLIGHTER,
                SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH,
                SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH);

        for (String key : keys) {
            settings.put(key, getDefaultValue(key));
        }
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
    }

    public static Object getDefaultValue(String key) {
        return switch (key) {
            case SETTING_ALWAYS_SHOW_BLOCKS, SETTING_LEGACY_CODE_EDITOR,
                 SETTING_ROOT_AUTO_INSTALL_PROJECTS, SETTING_SHOW_BUILT_IN_BLOCKS,
                 SETTING_SHOW_EVERY_SINGLE_BLOCK, SETTING_USE_NEW_VERSION_CONTROL,
                 SETTING_USE_ASD_HIGHLIGHTER -> false;
            case SETTING_BACKUP_DIRECTORY -> "/.sketchware/backups/";
            case SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING -> true;
            case SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH ->
                    "/.sketchware/resources/block/My Block/palette.json";
            case SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH ->
                    "/.sketchware/resources/block/My Block/block.json";
            default -> throw new IllegalArgumentException("Unknown key '" + key + "'!");
        };
    }

    private void restoreDefaultSettings() {
        restoreDefaultSettings(setting_map);
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat {
        private DataStore dataStore;

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.preferences_config_activity, rootKey);
            Preference backupDir = findPreference("backup-dir");
            assert backupDir != null;
            backupDir.setOnPreferenceClickListener(preference -> {
                DialogCreateNewFileLayoutBinding binding = DialogCreateNewFileLayoutBinding.inflate(getLayoutInflater());
                binding.inputText.setText(getBackupPath());
                binding.chipGroupTypes.setVisibility(View.GONE);
                AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                        .setView(binding.getRoot())
                        .setTitle("Backup directory")
                        .setMessage("Directory inside /Internal storage/, e.g. .sketchware/backups")
                        .setNegativeButton(R.string.common_word_cancel, null)
                        .setPositiveButton(R.string.common_word_save, null)
                        .create();

                dialog.setOnShowListener(dialogInterface -> {
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
                            Helper.getDialogDismissListener(dialogInterface));
                    Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(view -> {
                        getDataStore().putString(SETTING_BACKUP_DIRECTORY, binding.inputText.getText().toString());
                        SketchwareUtil.toast("Saved");
                        dialog.dismiss();
                    });

                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    binding.inputText.requestFocus();
                });
                dialog.show();
                return true;
            });

            SwitchPreferenceCompat installWithRoot = findPreference("root-auto-install-projects");
            assert installWithRoot != null;
            installWithRoot.setOnPreferenceChangeListener((preference, _newValue) -> {
                Boolean newValue = (Boolean) _newValue;
                if (newValue) {
                    Shell.getShell(shell -> {
                        if (!shell.isRoot()) {
                            SketchwareUtil.toastError("Couldn't acquire root access");
                            installWithRoot.setChecked(false);
                        }
                    });
                }
                return true;
            });

            Preference backupFilename = findPreference("backup-filename");
            assert backupFilename != null;
            backupFilename.setOnPreferenceClickListener(preference -> {
                DialogCreateNewFileLayoutBinding binding = DialogCreateNewFileLayoutBinding.inflate(getLayoutInflater());
                binding.chipGroupTypes.setVisibility(View.GONE);
                binding.inputText.setText(getBackupFileName());

                AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                        .setView(binding.getRoot())
                        .setTitle("Backup filename format")
                        .setMessage("This defines how SWB backup files get named.\n" +
                                "Available variables:\n" +
                                " - $projectName - Project name\n" +
                                " - $versionCode - App version code\n" +
                                " - $versionName - App version name\n" +
                                " - $pkgName - App package name\n" +
                                " - $timeInMs - Time during backup in milliseconds\n" +
                                "\n" +
                                "Additionally, you can format your own time like this using Java's date formatter syntax:\n" +
                                "$time(yyyy-MM-dd'T'HHmmss)\n")
                        .setNegativeButton(R.string.common_word_cancel, null)
                        .setPositiveButton(R.string.common_word_save, null)
                        .setNeutralButton(R.string.common_word_reset, (dialogInterface, which) -> {
                            getDataStore().putString(SETTING_BACKUP_FILENAME, null);
                            SketchwareUtil.toast("Reset to default complete.");
                        })
                        .create();

                dialog.setOnShowListener(dialogInterface -> {
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
                            Helper.getDialogDismissListener(dialog));
                    Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(view -> {
                        getDataStore().putString(SETTING_BACKUP_FILENAME, binding.inputText.getText().toString());
                        SketchwareUtil.toast("Saved");
                        dialog.dismiss();
                    });
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    binding.inputText.requestFocus();
                });
                dialog.show();
                return true;
            });
        }

        public DataStore getDataStore() {
            return dataStore;
        }

        public void setDataStore(DataStore dataStore) {
            this.dataStore = dataStore;
            getPreferenceManager().setPreferenceDataStore(dataStore);
        }
    }

    public static class DataStore extends PreferenceDataStore {
    }
}
