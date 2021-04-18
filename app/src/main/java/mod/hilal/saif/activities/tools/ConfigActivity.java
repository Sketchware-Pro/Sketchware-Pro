package mod.hilal.saif.activities.tools;

import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ConfigActivity extends Activity {

    private static final File SETTINGS_FILE = new File(FileUtil.getExternalStorageDir(), ".sketchware/data/settings.json");
    private static final String SETTING_ALWAYS_SHOW_BLOCKS = "always-show-blocks";
    private static final String SETTING_BACKUP_DIRECTORY = "backup-dir";
    private static final String SETTING_LEGACY_CODE_EDITOR = "legacy-ce";
    private static final String SETTING_SHOW_ALL_BLOCKS = "show-all-blocks";
    private static final String SETTING_SHOW_BUILT_IN_BLOCKS = "built-in-blocks";
    private static final String SETTING_USE_NEW_VERSION_CONTROL = "use-new-version-control";
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#fafafa");
    private LinearLayout root;
    private HashMap<String, Object> setting_map = new HashMap<>();

    public static String getBackupPath() {
        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            HashMap<String, Object> settings = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);
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

    public static boolean isNewVCEnabled() {
        if (!FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            return false;
        }

        HashMap<String, Object> settings = new Gson().fromJson(
                FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()),
                Helper.TYPE_MAP);
        if (settings.containsKey(SETTING_USE_NEW_VERSION_CONTROL)) {
            Object value = settings.get(SETTING_USE_NEW_VERSION_CONTROL);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else {
                SketchwareUtil.toastError("Detected invalid preference for Advanced "
                                + " Version Control. Restoring defaults",
                        Toast.LENGTH_LONG);
                settings.remove(SETTING_USE_NEW_VERSION_CONTROL);
                FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(settings));
            }
        }
        return false;
    }

    public static boolean isLegacyCeEnabled() {
        /* The legacy Code Editor is specifically opt-in */
        if (!FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            return false;
        }

        HashMap<String, Object> settings = new Gson().fromJson(
                FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()),
                Helper.TYPE_MAP);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FileUtil.isExistFile(SETTINGS_FILE.getAbsolutePath())) {
            setting_map = new Gson().fromJson(FileUtil.readFile(SETTINGS_FILE.getAbsolutePath()), Helper.TYPE_MAP);
            if (!setting_map.containsKey(SETTING_SHOW_BUILT_IN_BLOCKS) || !setting_map.containsKey(SETTING_ALWAYS_SHOW_BLOCKS)) {
                restoreDefaultSettings();
            }
        } else {
            restoreDefaultSettings();
        }
        initialize();
    }

    private void initialize() {
        root = new LinearLayout(this);
        root.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        root.setOrientation(LinearLayout.VERTICAL);
        setContentView(root);

        LinearLayout toolbar = (LinearLayout) getLayoutInflater().inflate(0x7f0b01d7, root, false);
        root.addView(toolbar);
        ImageView toolbar_back = toolbar.findViewById(0x7f0806c9);
        TextView toolbar_title = toolbar.findViewById(0x7f0806ca);
        toolbar_back.setClickable(true);
        toolbar_back.setFocusable(true);
        Helper.applyRippleToToolbarView(toolbar_back);
        toolbar_back.setOnClickListener(Helper.getBackPressedClickListener(this));
        toolbar_title.setText("Mod settings");

        addSwitchPreference("Built-in blocks",
                "May slow down loading blocks in Logic Editor.",
                SETTING_SHOW_BUILT_IN_BLOCKS,
                false);
        addSwitchPreference("Show all blocks",
                "All variable blocks will be visible, even if you don't have variables for them.",
                SETTING_SHOW_ALL_BLOCKS,
                false);
        addTextInputPreference("Backup directory",
                "The default directory is /Internal storage/.sketchware/backups/.",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText editText = new EditText(ConfigActivity.this);
                        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        editText.setTextSize(14.0f);
                        editText.setTextColor(-16777216);
                        editText.setText(getBackupPath());
                        AlertDialog create = new AlertDialog.Builder(ConfigActivity.this)
                                .setTitle("Backup directory inside Internal storage, e.g sketchware/backups")
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setting_map.put(SETTING_BACKUP_DIRECTORY, editText.getText().toString());
                                        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                                        SketchwareUtil.toast("Saved");
                                    }
                                }).create();
                        create.setView(editText);
                        create.show();
                    }
                });
        addSwitchPreference("Use legacy Code Editor",
                "Enables old Code Editor from v6.2.0.",
                SETTING_LEGACY_CODE_EDITOR,
                false);
        addSwitchPreference("Use new Version Control",
                "Use advanced Version Control System Dialog",
                SETTING_USE_NEW_VERSION_CONTROL,
                false);
    }

    private void applyDesign(View view) {
        Helper.applyRippleEffect(view, Color.parseColor("#dbedf5"), DEFAULT_BACKGROUND_COLOR);
        view.setClickable(true);
        view.setFocusable(true);
    }

    private void addSwitchPreference(String title, String subtitle, String keyName, boolean defaultValue) {
        LinearLayout preferenceRoot = new LinearLayout(this);
        preferenceRoot.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.0f
        ));
        preferenceRoot.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        preferenceRoot.setOrientation(LinearLayout.HORIZONTAL);
        preferenceRoot.setPadding(
                dpToPx(4),
                dpToPx(4),
                dpToPx(4),
                dpToPx(4)
        );
        /* Android Studio complained about that inside the original XML */
        preferenceRoot.setBaselineAligned(false);
        root.addView(preferenceRoot);

        LinearLayout textContainer = new LinearLayout(this);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(0),
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setPadding(
                dpToPx(8),
                dpToPx(8),
                dpToPx(8),
                dpToPx(8)
        );
        preferenceRoot.addView(textContainer);

        TextView titleView = new TextView(this);
        titleView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        titleView.setPadding(
                dpToPx(0),
                dpToPx(0),
                dpToPx(0),
                dpToPx(4)
        );
        titleView.setText(title);
        titleView.setTextColor(Color.parseColor("#616161"));
        titleView.setTextSize(16);
        textContainer.addView(titleView);

        TextView subtitleView = new TextView(this);
        subtitleView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        subtitleView.setText(subtitle);
        subtitleView.setTextColor(Color.parseColor("#bdbdbd"));
        subtitleView.setTextSize(12);
        textContainer.addView(subtitleView);

        LinearLayout switchContainer = new LinearLayout(this);
        switchContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.0f
        ));
        switchContainer.setGravity(Gravity.CENTER);
        switchContainer.setOrientation(LinearLayout.VERTICAL);
        switchContainer.setPadding(
                dpToPx(8),
                dpToPx(8),
                dpToPx(8),
                dpToPx(8)
        );
        preferenceRoot.addView(switchContainer);

        Switch switchView = new Switch(this);
        switchView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        switchView.setPadding(
                dpToPx(8),
                dpToPx(8),
                dpToPx(8),
                dpToPx(8)
        );
        switchView.setTextColor(Color.parseColor("#000000"));
        switchView.setTextSize(12);
        switchContainer.addView(switchView);

        preferenceRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView.setChecked(!switchView.isChecked());
            }
        });

        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting_map.put(keyName, isChecked);
                FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
            }
        });

        if (setting_map.containsKey(keyName)) {
            Object value = setting_map.get(keyName);
            if (value == null) {
                /* Nulls aren't great */
                setting_map.remove(keyName);
            } else {
                if (value instanceof Boolean) {
                    switchView.setChecked((boolean) value);
                } else {
                    SketchwareUtil.toastError("Detected invalid value for preference \""
                            + title + "\". Restoring defaults");
                    setting_map.remove(keyName);
                    FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
                }
            }
        } else {
            setting_map.put(keyName, defaultValue);
            FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(setting_map));
        }
        applyDesign(preferenceRoot);
    }

    private void addTextInputPreference(String title, String subtitle, View.OnClickListener listener) {
        LinearLayout preferenceRoot = new LinearLayout(this);
        LinearLayout.LayoutParams preferenceRootParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.0f
        );
        preferenceRootParams.bottomMargin = dpToPx(4);
        preferenceRoot.setLayoutParams(preferenceRootParams);
        preferenceRoot.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        preferenceRoot.setOrientation(LinearLayout.HORIZONTAL);
        preferenceRoot.setPadding(
                dpToPx(4),
                dpToPx(4),
                dpToPx(4),
                dpToPx(4)
        );
        /* Android Studio complained about this in the original XML files */
        preferenceRoot.setBaselineAligned(false);
        root.addView(preferenceRoot);

        LinearLayout textContainer = new LinearLayout(this);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(0),
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        ));
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setPadding(
                dpToPx(8),
                dpToPx(8),
                dpToPx(8),
                dpToPx(8)
        );
        preferenceRoot.addView(textContainer);

        TextView titleView = new TextView(this);
        titleView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        titleView.setText(title);
        titleView.setTextColor(Color.parseColor("#616161"));
        titleView.setTextSize(16);
        textContainer.addView(titleView);

        TextView subtitleView = new TextView(this);
        subtitleView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        subtitleView.setText(subtitle);
        subtitleView.setTextColor(Color.parseColor("#bdbdbd"));
        subtitleView.setTextSize(12);
        textContainer.addView(subtitleView);

        preferenceRoot.setOnClickListener(listener);
        applyDesign(preferenceRoot);
    }

    private void restoreDefaultSettings() {
        HashMap<String, Object> defaultSettings = new HashMap<>();
        defaultSettings.put(SETTING_ALWAYS_SHOW_BLOCKS, false);
        defaultSettings.put(SETTING_BACKUP_DIRECTORY, "");
        defaultSettings.put(SETTING_LEGACY_CODE_EDITOR, false);
        defaultSettings.put(SETTING_SHOW_ALL_BLOCKS, false);
        defaultSettings.put(SETTING_SHOW_BUILT_IN_BLOCKS, false);
        setting_map = defaultSettings;
        FileUtil.writeFile(SETTINGS_FILE.getAbsolutePath(), new Gson().toJson(defaultSettings));
    }
}
