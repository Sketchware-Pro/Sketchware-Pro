package mod.hilal.saif.activities.tools;

import static mod.SketchwareUtil.dpToPx;
import static mod.SketchwareUtil.getDip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AlertDialog;

import com.android.annotations.NonNull;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;
import com.topjohnwu.superuser.Shell;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sketchware.remod.databinding.DialogCreateNewFileLayoutBinding;
import com.besome.sketch.editor.property.PropertySwitchItem;
import com.besome.sketch.SketchApplication;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import a.a.a.mB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import mod.trindadedev.ui.preferences.Preference;

public class ConfigActivity extends AppCompatActivity {

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

    private LinearLayout content;
    private NestedScrollView contentLayout;
    private com.google.android.material.appbar.AppBarLayout appBarLayout;
    private com.google.android.material.appbar.MaterialToolbar topAppBar;
    private com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefences_content_appbar);

        content = findViewById(R.id.content);
        topAppBar = findViewById(R.id.topAppBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        contentLayout = findViewById(R.id.contentLayout);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);

        topAppBar.setTitle(SketchApplication.getContext().getResources().getString(R.string.mod_settings));
        topAppBar.setNavigationOnClickListener(view -> onBackPressed());

        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            setting_map = readSettings();
            if (!setting_map.containsKey(SETTING_SHOW_BUILT_IN_BLOCKS) || !setting_map.containsKey(SETTING_ALWAYS_SHOW_BLOCKS)) {
                restoreDefaultSettings();
            }
        } else {
            restoreDefaultSettings();
        }
        initialize();
    }

    public static String getBackupPath() {
        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            HashMap<String, Object> settings = readSettings();
            if (settings.containsKey(SETTING_BACKUP_DIRECTORY)) {
                Object value = settings.get(SETTING_BACKUP_DIRECTORY);
                if (value instanceof String) {
                    return (String) value;
                } else {
                    SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs)
                                    + SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs_msg),
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
                    SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs)
                                    + SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs_backup_filename),
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
                SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs_legacy)
                                + SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs_code_editor),
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
                SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.detected_invalid_prefs_reset),
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

            SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.error_to_parse_settings));
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
        switch (key) {
            case SETTING_ALWAYS_SHOW_BLOCKS:
            case SETTING_LEGACY_CODE_EDITOR:
            case SETTING_ROOT_AUTO_INSTALL_PROJECTS:
            case SETTING_SHOW_BUILT_IN_BLOCKS:
            case SETTING_SHOW_EVERY_SINGLE_BLOCK:
            case SETTING_USE_NEW_VERSION_CONTROL:
            case SETTING_USE_ASD_HIGHLIGHTER:
                return false;

            case SETTING_BACKUP_DIRECTORY:
                return "/.sketchware/backups/";

            case SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING:
                return true;

            case SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH:
                return "/.sketchware/resources/block/My Block/palette.json";

            case SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH:
                return "/.sketchware/resources/block/My Block/block.json";

            default:
                throw new IllegalArgumentException("Unknown key '" + key + "'!");
        }
    }

    @SuppressLint("SetTextI18n")
    private void initialize() {
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.built_in_blocks),
            SketchApplication.getContext().getResources().getString(R.string.built_in_blocks_summary),
            SETTING_SHOW_BUILT_IN_BLOCKS,
            false);
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.show_all_variable_blocks),
            SketchApplication.getContext().getResources().getString(R.string.show_all_variable_blocks_summary),
            SETTING_ALWAYS_SHOW_BLOCKS,
            false);
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.show_all_blocks_of_palettes),
            SketchApplication.getContext().getResources().getString(R.string.show_all_blocks_of_palettes_summary),
            SETTING_SHOW_EVERY_SINGLE_BLOCK,
            false);
        addTextInputPreference(SketchApplication.getContext().getResources().getString(R.string.backup_directory),
            SketchApplication.getContext().getResources().getString(R.string.backup_directory_summary), v -> openBackupDirDialog());
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.install_projects_with_root_access), SketchApplication.getContext().getResources().getString(R.string.install_projects_with_root_access_summary),
            SETTING_ROOT_AUTO_INSTALL_PROJECTS, false, (buttonView, isChecked) -> {
            if (isChecked) {
                Shell.getShell(shell -> {
                   if (!shell.isRoot()) {
                       SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.root_access_error));
                       buttonView.setChecked(false);
                   }
                });
            }
        });
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.launch_projects_after_installing),
            SketchApplication.getContext().getResources().getString(R.string.launch_projects_after_installing_summary),
            SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING,
            true);
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.use_new_version_control),
            SketchApplication.getContext().getResources().getString(R.string.use_new_version_control_summary),
            SETTING_USE_NEW_VERSION_CONTROL,
            false);
        addSwitchPreference(SketchApplication.getContext().getResources().getString(R.string.enable_block_text_input_highlighting),
            SketchApplication.getContext().getResources().getString(R.string.enable_block_text_input_highlighting_summary),
            SETTING_USE_ASD_HIGHLIGHTER,
            false);
        addTextInputPreference(SketchApplication.getContext().getResources().getString(R.string.backup_filename_format),
            SketchApplication.getContext().getResources().getString(R.string.backup_filename_format_summary), v -> openBackupFilenameFormatDialog());
    }
    
    private void openBackupDirDialog() {
         DialogCreateNewFileLayoutBinding dialogBinding = DialogCreateNewFileLayoutBinding.inflate(getLayoutInflater());
         EditText inputText = dialogBinding.inputText;
         inputText.setText(getBackupPath());
         AlertDialog dialog = new MaterialAlertDialogBuilder(this) 
               .setView(dialogBinding.getRoot())
               .setTitle(SketchApplication.getContext().getResources().getString(R.string.backup_directory))
               .setMessage(SketchApplication.getContext().getResources().getString(R.string.backup_directory_help))
               .setNegativeButton(R.string.common_word_cancel, (dialogInterface, i) -> dialogInterface.dismiss())
               .setPositiveButton(R.string.common_word_save, null)
               .create();
               
         dialogBinding.chipGroupTypes.setVisibility(View.GONE);
         dialog.setOnShowListener(dialogInterface -> {
               Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
               positiveButton.setOnClickListener(view -> {
                     setSetting(SETTING_BACKUP_DIRECTORY, inputText.getText().toString());
                     SketchwareUtil.toast(SketchApplication.getContext().getResources().getString(R.string.common_word_saved));
                     dialog.dismiss();
               });
               dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
               inputText.requestFocus();
         });
         dialog.show();
    }

    private void openBackupFilenameFormatDialog () {
        DialogCreateNewFileLayoutBinding dialogBinding = DialogCreateNewFileLayoutBinding.inflate(getLayoutInflater());
        EditText inputText = dialogBinding.inputText;
        inputText.setText(getBackupFileName());
        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
             .setView(dialogBinding.getRoot())
             .setTitle(SketchApplication.getContext().getResources().getString(R.string.backup_filename_format))
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
             .setNegativeButton(R.string.common_word_cancel, (dialogInterface, i) -> dialogInterface.dismiss())
             .setPositiveButton(R.string.common_word_save, null)
             .setNeutralButton(R.string.common_word_reset, (dialogInterface, which) -> {
                    setting_map.remove(SETTING_BACKUP_FILENAME);
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                    SketchwareUtil.toast(SketchApplication.getContext().getResources().getString(R.string.reset_to_default_complete));
             }).create();
             
             dialogBinding.chipGroupTypes.setVisibility(View.GONE);
             dialog.setOnShowListener(dialogInterface -> {
                    Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(view -> {
                          setting_map.put(SETTING_BACKUP_FILENAME, inputText.getText().toString());
                          FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                          SketchwareUtil.toast(SketchApplication.getContext().getResources().getString(R.string.common_word_saved));
                          dialog.dismiss();
                    });
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    inputText.requestFocus();
             });
             dialog.show();
    }

    private void addSwitchPreference(String title, String subtitle, String keyName, boolean defaultValue) {
        addSwitchPreference(title, subtitle, keyName, defaultValue, null);
    }

    private void addSwitchPreference(String title, String subtitle, String keyName, boolean defaultValue, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
         PropertySwitchItem preferenceSwitch = new PropertySwitchItem(this);
         preferenceSwitch.setName(title);
         preferenceSwitch.setDesc(subtitle);
         preferenceSwitch.setValue(defaultValue);
         preferenceSwitch.setSwitchChangedListener((buttonView, isChecked) -> {
              ConfigActivity.setSetting(keyName, isChecked);
              if (onCheckedChangeListener != null) {
                  onCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
              }
         });
         
         content.addView(preferenceSwitch);

         if (setting_map.containsKey(keyName)) {
            Object value = setting_map.get(keyName);
            if (value == null) {
                /* Nulls aren't great */
                setting_map.remove(keyName);
            } else {
                if (value instanceof Boolean) {
                    preferenceSwitch.setValue((boolean) value);
                } else {
                    SketchwareUtil.toastError(SketchApplication.getContext().getResources().getString(R.string.detected_invalid_value)
                            + title + SketchApplication.getContext().getResources().getString(R.string.restoring_to_default));
                    setting_map.remove(keyName);
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                }
            }
         } else {
             setting_map.put(keyName, defaultValue);
             preferenceSwitch.setValue(defaultValue);
             FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
         }
    }

    private void addTextInputPreference(String title, String subtitle, View.OnClickListener listener) {
        Preference preference = new Preference(this, title, subtitle, listener);
        content.addView(preference);
    }

    private void restoreDefaultSettings() {
        restoreDefaultSettings(setting_map);
    }
}