package mod.hilal.saif.activities.tools;

import static com.besome.sketch.editor.view.ViewEditor.shakeView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.besome.sketch.editor.manage.library.LibraryCategoryView;
import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import dev.pranav.filepicker.FilePickerCallback;
import dev.pranav.filepicker.FilePickerDialogFragment;
import dev.pranav.filepicker.FilePickerOptions;
import dev.pranav.filepicker.SelectionMode;
import mod.alucard.tn.apksigner.ApkSigner;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import mod.khaled.logcat.LogReaderActivity;
import pro.sketchware.R;
import pro.sketchware.activities.editor.component.ManageCustomComponentActivity;
import pro.sketchware.activities.settings.SettingsActivity;
import pro.sketchware.databinding.ActivityAppSettingsBinding;
import pro.sketchware.databinding.DialogSelectApkToSignBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class AppSettings extends BaseAppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);
        var binding = ActivityAppSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        {
            View view = binding.appBarLayout;
            int left = view.getPaddingLeft();
            int top = view.getPaddingTop();
            int right = view.getPaddingRight();
            int bottom = view.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(left + insets.left, top + insets.top, right + insets.right, bottom + insets.bottom);
                return i;
            });
        }

        {
            View view = binding.contentScroll;
            int left = view.getPaddingLeft();
            int top = view.getPaddingTop();
            int right = view.getPaddingRight();
            int bottom = view.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(left, top, right, bottom + insets.bottom);
                return i;
            });
        }

        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        setupPreferences(binding.content);
    }

    private void setupPreferences(ViewGroup content) {
        var preferences = new ArrayList<LibraryCategoryView>();

        LibraryCategoryView managersCategory = new LibraryCategoryView(this);
        managersCategory.setTitle("Managers");
        preferences.add(managersCategory);

        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_block, "Block manager", "Manage your own blocks to use in Logic Editor", new ActivityLauncher(new Intent(getApplicationContext(), BlocksManager.class))), true);
        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_pull_down, "Block selector menu manager", "Manage your own block selector menus", openSettingsActivity(SettingsActivity.BLOCK_SELECTOR_MANAGER_FRAGMENT)), true);
        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_component, "Component manager", "Manage your own components", new ActivityLauncher(new Intent(getApplicationContext(), ManageCustomComponentActivity.class))), true);
        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_list, "Event manager", "Manage your own events", openSettingsActivity(SettingsActivity.EVENTS_MANAGER_FRAGMENT)), true);
        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_box, "Local library manager", "Manage and download local libraries", new ActivityLauncher(new Intent(getApplicationContext(), ManageLocalLibraryActivity.class), new Pair<>("sc_id", "system"))), true);
        managersCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_article, Helper.getResString(R.string.design_drawer_menu_title_logcat_reader), Helper.getResString(R.string.design_drawer_menu_subtitle_logcat_reader), new ActivityLauncher(new Intent(getApplicationContext(), LogReaderActivity.class))), false);

        LibraryCategoryView generalCategory = new LibraryCategoryView(this);
        generalCategory.setTitle("General");
        preferences.add(generalCategory);

        generalCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_settings_applications, "App settings", "Change general app settings", new ActivityLauncher(new Intent(getApplicationContext(), ConfigActivity.class))), true);
        generalCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_palette, Helper.getResString(R.string.settings_appearance), Helper.getResString(R.string.settings_appearance_description), openSettingsActivity(SettingsActivity.SETTINGS_APPEARANCE_FRAGMENT)), true);
        generalCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_folder, "Open working directory", "Open Sketchware Pro's directory and edit files in it", v -> openWorkingDirectory()), true);
        generalCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_apk_document, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4", v -> signApkFileDialog()), true);
        generalCategory.addLibraryItem(createPreference(R.drawable.ic_mtrl_settings, Helper.getResString(R.string.main_drawer_title_system_settings), "Auto-save and vibrations", new ActivityLauncher(new Intent(getApplicationContext(), SystemSettingActivity.class))), false);

        preferences.forEach(content::addView);
    }

    private View.OnClickListener openSettingsActivity(String fragmentTag) {
        return v -> {
            Intent intent = new Intent(v.getContext(), SettingsActivity.class);
            intent.putExtra(SettingsActivity.FRAGMENT_TAG_EXTRA, fragmentTag);
            v.getContext().startActivity(intent);
        };
    }

    private LibraryItemView createPreference(int icon, String title, String desc, View.OnClickListener listener) {
        LibraryItemView preference = new LibraryItemView(this);
        preference.enabled.setVisibility(View.GONE);
        preference.icon.setImageResource(icon);
        preference.title.setText(title);
        preference.description.setText(desc);
        preference.setOnClickListener(listener);
        return preference;
    }

    private void openWorkingDirectory() {
        FilePickerOptions options = new FilePickerOptions();
        options.setSelectionMode(SelectionMode.BOTH);
        options.setMultipleSelection(true);
        options.setTitle("Select an entry to modify");
        options.setInitialDirectory(getFilesDir().getParentFile().getAbsolutePath());

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFilesSelected(@NotNull List<? extends File> files) {
                boolean isDirectory = files.get(0).isDirectory();
                if (files.size() > 1 || isDirectory) {
                    new MaterialAlertDialogBuilder(AppSettings.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Delete"}, -1, (actionDialog, which) -> {
                                new MaterialAlertDialogBuilder(AppSettings.this)
                                        .setTitle("Delete " + (isDirectory ? "folder" : "file") + "?")
                                        .setMessage("Are you sure you want to delete this " + (isDirectory ? "folder" : "file") + " permanently? This cannot be undone.")
                                        .setPositiveButton(R.string.common_word_delete, (deleteConfirmationDialog, pressedButton) -> {
                                            for (File file : files) {
                                                FileUtil.deleteFile(file.getAbsolutePath());
                                                deleteConfirmationDialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(R.string.common_word_cancel, null)
                                        .show();
                                actionDialog.dismiss();
                            })
                            .show();
                } else {
                    new MaterialAlertDialogBuilder(AppSettings.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Edit", "Delete"}, -1, (actionDialog, which) -> {
                                switch (which) {
                                    case 0 -> {
                                        Intent intent = new Intent(getApplicationContext(), SrcCodeEditor.class);
                                        intent.putExtra("title", Uri.fromFile(files.get(0)).getLastPathSegment());
                                        intent.putExtra("content", files.get(0).getAbsolutePath());
                                        intent.putExtra("xml", "");
                                        startActivity(intent);
                                    }
                                    case 1 -> new MaterialAlertDialogBuilder(AppSettings.this)
                                            .setTitle("Delete file?")
                                            .setMessage("Are you sure you want to delete this file permanently? This cannot be undone.")
                                            .setPositiveButton(R.string.common_word_delete, (deleteDialog, pressedButton) ->
                                                    FileUtil.deleteFile(files.get(0).getAbsolutePath()))
                                            .setNegativeButton(R.string.common_word_cancel, null)
                                            .show();
                                }
                                actionDialog.dismiss();
                            })
                            .show();
                }
            }
        };

        new FilePickerDialogFragment(options, callback).show(getSupportFragmentManager(), "file_picker");
    }

    private void signApkFileDialog() {
        boolean[] isAPKSelected = {false};
        MaterialAlertDialogBuilder apkPathDialog = new MaterialAlertDialogBuilder(this);
        apkPathDialog.setTitle("Sign APK with testkey");

        DialogSelectApkToSignBinding binding = DialogSelectApkToSignBinding.inflate(getLayoutInflater());
        View testkey_root = binding.getRoot();
        TextView apk_path_txt = binding.apkPathTxt;

        binding.selectFile.setOnClickListener(v -> {
            FilePickerOptions options = new FilePickerOptions();
            options.setExtensions(new String[]{"apk"});
            FilePickerCallback callback = new FilePickerCallback() {
                @Override
                public void onFileSelected(File file) {
                    isAPKSelected[0] = true;
                    apk_path_txt.setText(file.getAbsolutePath());
                }
            };
            FilePickerDialogFragment dialog = new FilePickerDialogFragment(options, callback);
            dialog.show(getSupportFragmentManager(), "file_picker");
        });

        apkPathDialog.setPositiveButton("Continue", (v, which) -> {
            if (!isAPKSelected[0]) {
                SketchwareUtil.toast("Please select an APK file to sign", Toast.LENGTH_SHORT);
                shakeView(binding.selectFile);
                return;
            }
            String input_apk_path = Helper.getText(apk_path_txt);
            String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
            String output_apk_path = new File(Environment.getExternalStorageDirectory(),
                    "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

            if (new File(output_apk_path).exists()) {
                MaterialAlertDialogBuilder confirmOverwrite = new MaterialAlertDialogBuilder(this);
                confirmOverwrite.setIcon(R.drawable.color_save_as_new_96);
                confirmOverwrite.setTitle("File exists");
                confirmOverwrite.setMessage("An APK named " + output_apk_file_name + " already exists at /sketchware/signed_apk/.  Overwrite it?");

                confirmOverwrite.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
                confirmOverwrite.setPositiveButton("Overwrite", (view, which1) -> {
                    v.dismiss();
                    signApkFileWithDialog(input_apk_path, output_apk_path, true,
                            null, null, null, null);
                });
                confirmOverwrite.show();
            } else {
                signApkFileWithDialog(input_apk_path, output_apk_path, true,
                        null, null, null, null);
            }
        });

        apkPathDialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);

        apkPathDialog.setView(testkey_root);
        apkPathDialog.setCancelable(false);
        apkPathDialog.show();
    }

    private void signApkFileWithDialog(String inputApkPath, String outputApkPath, boolean useTestkey, String keyStorePath, String keyStorePassword, String keyStoreKeyAlias, String keyPassword) {
        View building_root = getLayoutInflater().inflate(R.layout.build_progress_msg_box, null, false);
        LinearLayout layout_quiz = building_root.findViewById(R.id.layout_quiz);
        TextView tv_progress = building_root.findViewById(R.id.tv_progress);

        ScrollView scroll_view = new ScrollView(this);
        TextView tv_log = new TextView(this);
        scroll_view.addView(tv_log);
        layout_quiz.addView(scroll_view);

        tv_progress.setText("Signing APK...");

        AlertDialog building_dialog = new MaterialAlertDialogBuilder(this)
                .setView(building_root)
                .create();

        ApkSigner signer = new ApkSigner();
        new Thread() {
            @Override
            public void run() {
                super.run();

                ApkSigner.LogCallback callback = line -> runOnUiThread(() ->
                        tv_log.setText(Helper.getText(tv_log) + line));

                if (useTestkey) {
                    signer.signWithTestKey(inputApkPath, outputApkPath, callback);
                } else {
                    signer.signWithKeyStore(inputApkPath, outputApkPath,
                            keyStorePath, keyStorePassword, keyStoreKeyAlias, keyPassword, callback);
                }

                runOnUiThread(() -> {
                    if (ApkSigner.LogCallback.errorCount.get() == 0) {
                        building_dialog.dismiss();
                        SketchwareUtil.toast("Successfully saved signed APK to: /Internal storage/sketchware/signed_apk/"
                                        + Uri.fromFile(new File(outputApkPath)).getLastPathSegment(),
                                Toast.LENGTH_LONG);
                    } else {
                        tv_progress.setText("An error occurred. Check the log for more details.");
                    }
                });
            }
        }.start();

        building_dialog.show();
    }

    private class ActivityLauncher implements View.OnClickListener {
        private final Intent launchIntent;
        private Pair<String, String> optionalExtra;

        ActivityLauncher(Intent launchIntent) {
            this.launchIntent = launchIntent;
        }

        ActivityLauncher(Intent launchIntent, Pair<String, String> optionalExtra) {
            this(launchIntent);
            this.optionalExtra = optionalExtra;
        }

        @Override
        public void onClick(View v) {
            if (optionalExtra != null) {
                launchIntent.putExtra(optionalExtra.first, optionalExtra.second);
            }
            startActivity(launchIntent);
        }
    }
}
