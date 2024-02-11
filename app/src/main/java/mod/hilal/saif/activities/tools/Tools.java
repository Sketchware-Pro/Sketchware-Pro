package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.editor.manage.library.LibraryItemView;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ManageFontAddBinding;

import java.io.File;

import a.a.a.aB;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import kellinwood.security.zipsigner.ZipSigner;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.apksigner.ApkSigner;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.util.Helper;
import mod.khaled.logcat.LogReaderActivity;

public class Tools extends Activity {

    private LinearLayout base;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams _lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout _base = new LinearLayout(this);
        _base.setOrientation(LinearLayout.VERTICAL);
        _base.setLayoutParams(_lp);
        newToolbar(_base);
        ScrollView _scroll = new ScrollView(this);

        base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);
        base.setLayoutParams(_lp);
        _scroll.setLayoutParams(_lp);
        _scroll.addView(base);
        _base.addView(_scroll);
        setupViews();
        setContentView(_base);
    }

    private void makeup(LibraryItemView parent, int iconResourceId, String title, String description) {
        parent.enabled.setVisibility(View.GONE);
        parent.icon.setImageResource(iconResourceId);
        parent.title.setText(title);
        parent.description.setText(description);
    }

    private void newToolbar(View parent) {
        View toolbar = getLayoutInflater().inflate(R.layout.toolbar_improved, null);
        ((TextView) toolbar.findViewById(R.id.tx_toolbar_title)).setText("Tools");
        ImageView back = toolbar.findViewById(R.id.ig_toolbar_back);

        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back);

        parent.setPadding(0, 0, 0, 0);
        ((ViewGroup) parent).addView(toolbar, 0);
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
                                case 0:
                                    Intent intent = new Intent(getApplicationContext(), ConfigActivity.isLegacyCeEnabled() ?
                                            SrcCodeEditorLegacy.class
                                            : mod.hey.studios.code.SrcCodeEditor.class);
                                    intent.putExtra("title", Uri.parse(files[0]).getLastPathSegment());
                                    intent.putExtra("content", files[0]);
                                    intent.putExtra("xml", "");
                                    startActivity(intent);
                                    break;

                                case 1:
                                    new AlertDialog.Builder(this)
                                            .setTitle("Delete file?")
                                            .setMessage("Are you sure you want to delete this file permanently? This cannot be undone.")
                                            .setPositiveButton(R.string.common_word_delete, (deleteDialog, pressedButton) ->
                                                    FileUtil.deleteFile(files[0]))
                                            .setNegativeButton(R.string.common_word_cancel, null)
                                            .show();
                                    break;
                            }
                            actionDialog.dismiss();
                        })
                        .show();
            }
        });
        dialog.show();
    }

    private void setupViews() {
        LibraryItemView blockManager = new LibraryItemView(this);
        makeup(blockManager, R.drawable.block_96_blue, "Block manager", "Manage your own blocks to use in Logic Editor");
        base.addView(blockManager);
        blockManager.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlocksManager.class)));

        LibraryItemView blockSelectorManager = new LibraryItemView(this);
        makeup(blockSelectorManager, R.drawable.pull_down_48, "Block selector menu manager", "Manage your own block selector menus");
        base.addView(blockSelectorManager);
        blockSelectorManager.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlockSelectorActivity.class)));

        LibraryItemView componentManager = new LibraryItemView(this);
        makeup(componentManager, R.drawable.collage_48, "Component manager", "Manage your own components");
        base.addView(componentManager);
        componentManager.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ManageCustomComponentActivity.class)));

        LibraryItemView eventManager = new LibraryItemView(this);
        makeup(eventManager, R.drawable.event_on_item_clicked_48dp, "Event manager", "Manage your own events");
        base.addView(eventManager);
        eventManager.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), EventsMaker.class)));

        LibraryItemView localLibraryManager = new LibraryItemView(this);
        makeup(localLibraryManager, R.drawable.colored_box_96, "Local library manager", "Manage and download local libraries");
        base.addView(localLibraryManager);
        localLibraryManager.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ManageLocalLibraryActivity.class),
                new Pair<>("sc_id", "system")));

        LibraryItemView modSettings = new LibraryItemView(this);
        makeup(modSettings, R.drawable.engineering_48, "Mod settings", "Change general mod settings");
        base.addView(modSettings);
        modSettings.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ConfigActivity.class)));

        LibraryItemView openWorkingDirectory = new LibraryItemView(this);
        makeup(openWorkingDirectory, R.mipmap.ic_type_folder, "Open working directory", "Open Sketchware Pro's directory and edit files in it");
        base.addView(openWorkingDirectory);
        openWorkingDirectory.setOnClickListener(v -> openWorkingDirectory());

        LibraryItemView signApkFile = new LibraryItemView(this);
        makeup(signApkFile, R.drawable.ic_apk_color_96dp, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4");
        base.addView(signApkFile);
        signApkFile.setOnClickListener(v -> signApkFileDialog());

        LibraryItemView openLogcatReader = new LibraryItemView(this);
        makeup(openLogcatReader, R.drawable.icons8_app_components, getString(R.string.design_drawer_menu_title_logcat_reader), getString(R.string.design_drawer_menu_subtitle_logcat_reader));
        base.addView(openLogcatReader);
        openLogcatReader.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), LogReaderActivity.class)));
    }

    private void signApkFileDialog() {
        aB apkPathDialog = new aB(this);
        apkPathDialog.b("Sign APK");

        ManageFontAddBinding binding = ManageFontAddBinding.inflate(getLayoutInflater());
        View testkey_root = binding.getRoot();
        TextView font_preview = binding.fontPreviewTxt;
        TextView font_preview_title = binding.fontPreviewTitle;
        LinearLayout input_holder = binding.inputHolder;
        CheckBox chk_collection = binding.addToCollectionCheckbox;

        font_preview_title.setText("Selected APK");
        font_preview.setText("APK path will appear here");
        binding.fontPreviewView.setVisibility(View.VISIBLE);
        input_holder.setVisibility(View.GONE);
        chk_collection.setVisibility(View.GONE);

        apkPathDialog.b("Select", (dialogInterface, i) -> {
            DialogProperties properties = new DialogProperties();
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
            properties.selection_type = DialogConfigs.FILE_SELECT;
            properties.extensions = new String[]{"apk"};
            FilePickerDialog dialog = new FilePickerDialog(this, properties);
            dialog.setDialogSelectionListener(files -> {
                font_preview.setText(files[0]);

                String input_apk_path = font_preview.getText().toString();
                String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
                String output_apk_path = new File(Environment.getExternalStorageDirectory(),
                        "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

                if (new File(output_apk_path).exists()) {
                    aB confirmOverwrite = new aB(this);
                    confirmOverwrite.a(R.drawable.color_save_as_new_96);
                    confirmOverwrite.b("File exists");
                    confirmOverwrite.a("An APK named " + output_apk_file_name + " already exists at /sketchware/signed_apk/.  Overwrite it?");

                    confirmOverwrite.a(Helper.getResString(R.string.common_word_cancel),
                            (d, which) -> Helper.getDialogDismissListener(d));
                    confirmOverwrite.b("Overwrite", (d, which) -> {
                        d.dismiss();
                        signApkFileWithDialog(input_apk_path, output_apk_path, true,
                                null, null, null, null);
                    });
                    confirmOverwrite.show();
                } else {
                    signApkFileWithDialog(input_apk_path, output_apk_path, true,
                            null, null, null, null);
                }



            });
            dialog.setTitle("Select the APK to sign");
            dialog.show();
        });

        apkPathDialog.a(Helper.getResString(R.string.common_word_cancel), (dialogInterface, whichDialog) -> Helper.getDialogDismissListener(dialogInterface));

        apkPathDialog.a(testkey_root);
        apkPathDialog.autoDismiss(false);
        apkPathDialog.show();
    }

    private void signApkFileWithDialog(String inputApkPath, String outputApkPath, boolean useTestkey,
                                       String keyStorePath, String keyStorePassword, String keyStoreKeyAlias,
                                       String keyPassword) {
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
