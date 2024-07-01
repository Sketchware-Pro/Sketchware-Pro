package mod.hilal.saif.activities.tools;

import static com.besome.sketch.editor.view.ViewEditor.shakeView;
import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.activity.EdgeToEdge;

import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.DialogSelectApkToSignBinding;

import java.io.File;

import a.a.a.aB;
import a.a.a.mB;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import kellinwood.security.zipsigner.ZipSigner;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.apksigner.ApkSigner;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.util.Helper;
import mod.khaled.logcat.LogReaderActivity;
import mod.trindadedev.settings.appearance.AppearanceActivity;

public class Tools extends AppCompatActivity {

    private LinearLayout content;
    private NestedScrollView contentLayout;
    private com.google.android.material.appbar.AppBarLayout appBarLayout;
    private com.google.android.material.appbar.MaterialToolbar topAppBar;
    private com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefences_content_appbar);

        content = findViewById(R.id.content);
        topAppBar = findViewById(R.id.topAppBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        contentLayout = findViewById(R.id.contentLayout);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);

        topAppBar.setTitle("Tools");
        topAppBar.setNavigationOnClickListener(view -> onBackPressed());
        setupViews();
    }

    private void openWorkingDirectory() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = getFilesDir().getParentFile();
        properties.error_dir = getExternalCacheDir();
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select an entry to modify");
        dialog.setDialogSelectionListener(files -> {
            final boolean isDirectory = new File(files[0]).isDirectory();
            if (files.length > 1 || isDirectory) {
                new AlertDialog.Builder(this)
                        .setTitle("Select an action")
                        .setSingleChoiceItems(new String[]{"Delete"}, -1, (actionDialog, which) -> {
                            new AlertDialog.Builder(this)
                                    .setTitle("Delete " + (isDirectory ? "folder" : "file") + "?")
                                    .setMessage("Are you sure you want to delete this " + (isDirectory ? "folder" : "file") + " permanently? This cannot be undone.")
                                    .setPositiveButton(R.string.common_word_delete, (deleteConfirmationDialog, pressedButton) -> {
                                        for (String file : files) {
                                            FileUtil.deleteFile(file);
                                            deleteConfirmationDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton(R.string.common_word_cancel, null)
                                    .show();
                            actionDialog.dismiss();
                        })
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Select an action")
                        .setSingleChoiceItems(new String[]{"Edit", "Delete"}, -1, (actionDialog, which) -> {
                            switch (which) {
                                case 0 -> {
                                    Intent intent = new Intent(getApplicationContext(), ConfigActivity.isLegacyCeEnabled() ?
                                            SrcCodeEditorLegacy.class
                                            : mod.hey.studios.code.SrcCodeEditor.class);
                                    intent.putExtra("title", Uri.parse(files[0]).getLastPathSegment());
                                    intent.putExtra("content", files[0]);
                                    intent.putExtra("xml", "");
                                    startActivity(intent);
                                }
                                case 1 -> new AlertDialog.Builder(this)
                                        .setTitle("Delete file?")
                                        .setMessage("Are you sure you want to delete this file permanently? This cannot be undone.")
                                        .setPositiveButton(R.string.common_word_delete, (deleteDialog, pressedButton) ->
                                                FileUtil.deleteFile(files[0]))
                                        .setNegativeButton(R.string.common_word_cancel, null)
                                        .show();
                            }
                            actionDialog.dismiss();
                        })
                        .show();
            }
        });
        dialog.show();
    }

    private void setupViews() {
        createToolsView(R.drawable.block_96_blue, "Block manager", "Manage your own blocks to use in Logic Editor", content, new ActivityLauncher(new Intent(getApplicationContext(), BlocksManager.class)), false);
        createToolsView(R.drawable.pull_down_48, "Block selector menu manager", "Manage your own block selector menus", content, new ActivityLauncher(new Intent(getApplicationContext(), BlockSelectorActivity.class)), false);
        createToolsView(R.drawable.collage_48, "Component manager", "Manage your own components", content, new ActivityLauncher(new Intent(getApplicationContext(), ManageCustomComponentActivity.class)), false);
        createToolsView(R.drawable.event_on_item_clicked_48dp, "Event manager", "Manage your own events", content, new ActivityLauncher(new Intent(getApplicationContext(), EventsMaker.class)), false);
        createToolsView(R.drawable.colored_box_96, "Local library manager", "Manage and download local libraries", content, new ActivityLauncher(new Intent(getApplicationContext(), ManageLocalLibraryActivity.class), new Pair<>("sc_id", "system")), false);
        createToolsView(R.drawable.engineering_48, "Mod settings", "Change general mod settings", content, new ActivityLauncher(new Intent(getApplicationContext(), ConfigActivity.class)), false);
        createToolsView(R.drawable.icon_pallete, getString(R.string.appearance), getString(R.string.appearance_description), content, new ActivityLauncher(new Intent(getApplicationContext(), AppearanceActivity.class)), false);
        createToolsView(R.mipmap.ic_type_folder, "Open working directory", "Open Sketchware Pro's directory and edit files in it", content, v -> openWorkingDirectory(), false);
        createToolsView(R.drawable.ic_apk_color_96dp, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4", content, v -> signApkFileDialog(), false);
        createToolsView(R.drawable.icons8_app_components, getString(R.string.design_drawer_menu_title_logcat_reader), getString(R.string.design_drawer_menu_subtitle_logcat_reader), content, new ActivityLauncher(new Intent(getApplicationContext(), LogReaderActivity.class)), true);
    }

    private void createToolsView(int icon, String title, String desc, LinearLayout toView, View.OnClickListener listener, boolean lastItem) {
        LibraryItemView item = new LibraryItemView(this);
        item.enabled.setVisibility(View.GONE);
        item.icon.setImageResource(icon);
        item.title.setText(title);
        item.description.setText(desc);
        toView.addView(item);
        item.setOnClickListener(listener);
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
             LinearLayout.LayoutParams.MATCH_PARENT,
             LinearLayout.LayoutParams.WRAP_CONTENT,
             0.0f
        );
        itemParams.bottomMargin = lastItem ? dpToPx(25) : dpToPx(0);
        item.setLayoutParams(itemParams);
    }

    private void signApkFileDialog() {
        final boolean[] isAPKSelected = {false};
        aB apkPathDialog = new aB(this);
        apkPathDialog.b("Sign APK with testkey");

        DialogSelectApkToSignBinding binding = DialogSelectApkToSignBinding.inflate(getLayoutInflater());
        View testkey_root = binding.getRoot();
        TextView apk_path_txt = binding.apkPathTxt;

        binding.selectFile.setOnClickListener(v -> {
            DialogProperties properties = new DialogProperties();
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
            properties.selection_type = DialogConfigs.FILE_SELECT;
            properties.extensions = new String[]{"apk"};
            FilePickerDialog dialog = new FilePickerDialog(this, properties);
            dialog.setDialogSelectionListener(files -> {
                isAPKSelected[0] = true;
                apk_path_txt.setText(files[0]);
            });
            dialog.show();
        });

        apkPathDialog.b("Continue", v -> {
            if (!isAPKSelected[0]) {
                SketchwareUtil.toast("Please select an APK file to sign", Toast.LENGTH_SHORT);
                shakeView(binding.selectFile);
                return;
            }
            String input_apk_path = apk_path_txt.getText().toString();
            String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
            String output_apk_path = new File(Environment.getExternalStorageDirectory(),
                    "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

            if (new File(output_apk_path).exists()) {
                aB confirmOverwrite = new aB(this);
                confirmOverwrite.a(R.drawable.color_save_as_new_96);
                confirmOverwrite.b("File exists");
                confirmOverwrite.a("An APK named " + output_apk_file_name + " already exists at /sketchware/signed_apk/.  Overwrite it?");

                confirmOverwrite.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(confirmOverwrite));
                confirmOverwrite.b("Overwrite", view -> {
                    confirmOverwrite.dismiss();
                    signApkFileWithDialog(input_apk_path, output_apk_path, true,
                            null, null, null, null);
                });
                confirmOverwrite.show();
            } else {
                signApkFileWithDialog(input_apk_path, output_apk_path, true,
                        null, null, null, null);
            }
        });

        apkPathDialog.a(Helper.getResString(R.string.common_word_cancel), v -> apkPathDialog.dismiss());

        apkPathDialog.a(testkey_root);
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

        AlertDialog building_dialog = new AlertDialog.Builder(this)
                .setView(building_root)
                .create();

        ApkSigner signer = new ApkSigner();
        new Thread() {
            @Override
            public void run() {
                super.run();

                ApkSigner.LogCallback callback = line -> runOnUiThread(() ->
                        tv_log.setText(tv_log.getText().toString() + line));

                if (useTestkey) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        signer.signWithTestKey(inputApkPath, outputApkPath, callback);
                    } else {
                        try {
                            ZipSigner zipSigner = new ZipSigner();
                            zipSigner.setKeymode(ZipSigner.KEY_TESTKEY);
                            zipSigner.signZip(inputApkPath, outputApkPath);
                        } catch (Exception e) {
                            tv_progress.setText("An error occurred. Check the log for more details.");
                            tv_log.setText("Failed to sign APK with zipsigner: " + e);
                        }
                    }
                } else {
                    signer.signWithKeyStore(inputApkPath, outputApkPath,
                            keyStorePath, keyStorePassword, keyStoreKeyAlias, keyPassword, callback);
                }

                runOnUiThread(() -> {
                    if (callback.errorCount.get() == 0) {
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