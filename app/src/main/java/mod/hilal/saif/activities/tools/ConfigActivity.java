package mod.hilal.saif.activities.tools;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.android.annotations.NonNull;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonParseException;
import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogCreateNewFileLayoutBinding;
import pro.sketchware.databinding.PreferenceActivityBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ConfigActivity extends BaseAppCompatActivity {

    public static final File SETTINGS_FILE = new File(FileUtil.getExternalStorageDir(), ".sketchware/data/settings.json");
    public static final String SETTING_ALWAYS_SHOW_BLOCKS = "always-show-blocks";
    public static final String SETTING_BACKUP_DIRECTORY = "backup-dir";
    public static final String SETTING_ROOT_AUTO_INSTALL_PROJECTS = "root-auto-install-projects";
    public static final String SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING = "root-auto-open-after-installing";
    public static final String SETTING_BACKUP_FILENAME = "backup-filename";
    public static final String SETTING_SHOW_BUILT_IN_BLOCKS = "built-in-blocks";
    public static final String SETTING_SHOW_EVERY_SINGLE_BLOCK = "show-every-single-block";
    public static final String SETTING_USE_NEW_VERSION_CONTROL = "use-new-version-control";
    public static final String SETTING_USE_ASD_HIGHLIGHTER = "use-asd-highlighter";
    public static final String SETTING_CRITICAL_UPDATE_REMINDER = "critical-update-reminder";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH = "palletteDir";
    public static final String SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH = "blockDir";

    public static String getBackupPath() {
        return DataStore.getInstance().getString(SETTING_BACKUP_DIRECTORY, "/.sketchware/backups/");
    }

    public static String getStringSettingValueOrSetAndGet(String settingKey, String toReturnAndSetIfNotFound) {
        var dataStore = DataStore.getInstance();
        Map<String, Object> settings = dataStore.getSettings();

        Object value = settings.get(settingKey);
        if (value instanceof String s) {
            return s;
        } else {
            dataStore.putString(settingKey, toReturnAndSetIfNotFound);
            dataStore.persist();

            return toReturnAndSetIfNotFound;
        }
    }

    public static String getBackupFileName() {
        return DataStore.getInstance().getString(SETTING_BACKUP_FILENAME, "$projectName v$versionName ($pkgName, $versionCode) $time(yyyy-MM-dd'T'HHmmss)");
    }

    public static boolean isSettingEnabled(String keyName) {
        return DataStore.getInstance().getBoolean(keyName, false);
    }

    public static void setSetting(String key, Object value) {
        var dataStore = DataStore.getInstance();
        if (value instanceof String s) {
            dataStore.putString(key, s);
        } else if (value instanceof Boolean b) {
            dataStore.putBoolean(key, b);
        } else {
            throw new IllegalArgumentException("Unhandled data type " + value.getClass());
        }
        dataStore.persist();
    }

    @NonNull
    private static HashMap<String, Object> readSettings() {
        HashMap<String, Object> settings;

        if (SETTINGS_FILE.exists()) {
            Exception toLog;

            try {
                settings = getGson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);

                if (settings != null) {
                    return settings;
                }

                toLog = new NullPointerException("settings == null");
                // fall-through to shared error handler
            } catch (JsonParseException e) {
                toLog = e;
                // fall-through to shared error handler
            }

            SketchwareUtil.toastError("Couldn't parse App Settings! Restoring defaults.");
            LogUtil.e("ConfigActivity", "Failed to parse App Settings.", toLog);
        }
        settings = new HashMap<>();
        restoreDefaultSettings(settings);

        return settings;
    }

    private static void restoreDefaultSettings(HashMap<String, Object> settings) {
        settings.clear();

        List<String> keys = Arrays.asList(SETTING_ALWAYS_SHOW_BLOCKS,
                SETTING_BACKUP_DIRECTORY,
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
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), getGson().toJson(settings));
    }

    public static Object getDefaultValue(String key) {
        return switch (key) {
            case SETTING_ALWAYS_SHOW_BLOCKS,
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        var binding = PreferenceActivityBinding.inflate(getLayoutInflater());
        // unfortunately, androidx.preference doesn't make it easy to support edge-to-edge layout properly, so this will have to do
        Insetter.builder()
                .padding(WindowInsetsCompat.Type.navigationBars())
                .applyToView(binding.getRoot());
        setContentView(binding.getRoot());

        binding.topAppBar.setTitle("App Settings");
        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        var fragment = new PreferenceFragment();
        fragment.setSnackbarView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat {
        private View snackbarView;
        private DataStore dataStore;

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            dataStore = DataStore.getInstance();
            getPreferenceManager().setPreferenceDataStore(dataStore);
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
                        getDataStore().putString(SETTING_BACKUP_DIRECTORY, Helper.getText(binding.inputText));
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
            installWithRoot.setOnPreferenceClickListener(preference -> {
                if (installWithRoot.isChecked()) {
                    Shell.getShell(shell -> {
                        if (!shell.isRoot()) {
                            Snackbar.make(snackbarView, "Couldn't acquire root access", BaseTransientBottomBar.LENGTH_SHORT).show();
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
                            Snackbar.make(snackbarView, "Reset to default complete.", BaseTransientBottomBar.LENGTH_SHORT).show();
                        })
                        .create();

                dialog.setOnShowListener(dialogInterface -> {
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
                            Helper.getDialogDismissListener(dialog));
                    Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(view -> {
                        getDataStore().putString(SETTING_BACKUP_FILENAME, Helper.getText(binding.inputText));
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

        public void setSnackbarView(View snackbarView) {
            this.snackbarView = snackbarView;
        }
    }

    /**
     * An in-memory caching store for settings listed in {@link ConfigActivity}.
     * Persists to {@link #SETTINGS_FILE}.
     *
     * @see #persist()
     */
    public static class DataStore extends PreferenceDataStore {
        private static DataStore INSTANCE;
        private final Map<String, Object> settings;

        private DataStore() {
            settings = readSettings();
        }

        public static DataStore getInstance() {
            return INSTANCE == null ? (INSTANCE = new DataStore()) : INSTANCE;
        }

        private Map<String, Object> getSettings() {
            return settings;
        }

        /**
         * Blocking method that writes its data to {@link #SETTINGS_FILE}. Should be called manually,
         * since there's no automatic persist. Meaning, every write, unless they are in batches.
         */
        public void persist() {
            FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), getGson().toJson(settings));
        }

        @Override
        public void putString(String key, @Nullable String value) {
            if (value == null) {
                settings.remove(key);
            } else {
                settings.put(key, value);
            }
            persist();
        }

        @Nullable
        @Override
        public String getString(String key, @Nullable String defValue) {
            var value = settings.get(key);
            if (value instanceof String s) {
                return s;
            }
            return defValue;
        }

        @Override
        public void putBoolean(String key, boolean value) {
            settings.put(key, value);
            persist();
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            var value = settings.get(key);
            if (value instanceof Boolean b) {
                return b;
            }
            return defValue;
        }
    }
}
