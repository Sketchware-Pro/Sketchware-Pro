package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.R;

import java.io.File;

import a.a.a.aB;
import a.a.a.xB;
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

    private void makeup(View parent, int iconResourceId, String title, String subtitle) {
        View inflate = getLayoutInflater().inflate(R.layout.manage_library_base_item, null);
        ImageView icon = inflate.findViewById(R.id.lib_icon);
        inflate.findViewById(R.id.tv_enable).setVisibility(View.GONE);
        ((LinearLayout) icon.getParent()).setGravity(Gravity.CENTER);
        icon.setImageResource(iconResourceId);
        ((TextView) inflate.findViewById(R.id.lib_title)).setText(title);
        ((TextView) inflate.findViewById(R.id.lib_desc)).setText(subtitle);
        ((ViewGroup) parent).addView(inflate);
    }

    private CardView newCard(int width, int height, float weight) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height, weight);
        layoutParams.setMargins(
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(4),
                (int) SketchwareUtil.getDip(2)
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding(
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2),
                (int) SketchwareUtil.getDip(2)
        );
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    private LinearLayout newLayout(int width, int height, float weight) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        linearLayout.setPadding(
                (int) SketchwareUtil.getDip(1),
                (int) SketchwareUtil.getDip(1),
                (int) SketchwareUtil.getDip(1),
                (int) SketchwareUtil.getDip(1)
        );
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
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
        CardView blockManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout newLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockManager.addView(newLayout);
        makeup(newLayout, R.drawable.block_96_blue, "Block manager", "Manage your own blocks to use in Logic Editor");
        base.addView(blockManager);
        newLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlocksManager.class)));

        CardView blockSelectorManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout blockSelectorManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockSelectorManager.addView(blockSelectorManagerLayout);
        makeup(blockSelectorManagerLayout, R.drawable.pull_down_48, "Block selector menu manager", "Manage your own block selector menus");
        base.addView(blockSelectorManager);
        blockSelectorManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlockSelectorActivity.class)));

        CardView componentManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout componentManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        componentManager.addView(componentManagerLayout);
        makeup(componentManagerLayout, R.drawable.collage_48, "Component manager", "Manage your own components");
        base.addView(componentManager);
        componentManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ComponentsMaker.class)));

        CardView eventManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout eventManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        eventManager.addView(eventManagerLayout);
        makeup(eventManagerLayout, R.drawable.event_on_item_clicked_48dp, "Event manager", "Manage your own events");
        base.addView(eventManager);
        eventManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), EventsMaker.class)));

        CardView localLibraryManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout localLibraryManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        localLibraryManager.addView(localLibraryManagerLayout);
        makeup(localLibraryManagerLayout, R.drawable.colored_box_96, "Local library manager", "Manage and download local libraries");
        base.addView(localLibraryManager);
        localLibraryManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ManageLocalLibraryActivity.class),
                new Pair<>("sc_id", "system")));

        CardView modSettings = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout modSettingsLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        modSettings.addView(modSettingsLayout);
        makeup(modSettingsLayout, R.drawable.engineering_48, "Mod settings", "Change general mod settings");
        base.addView(modSettings);
        modSettingsLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ConfigActivity.class)));

        CardView openWorkingDirectory = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout openWorkingDirectoryLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        openWorkingDirectory.addView(openWorkingDirectoryLayout);
        makeup(openWorkingDirectoryLayout, R.mipmap.ic_type_folder, "Open working directory", "Open Sketchware Pro's directory and edit files in it");
        base.addView(openWorkingDirectory);
        openWorkingDirectoryLayout.setOnClickListener(v -> openWorkingDirectory());

        CardView signApkFile = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout signApkFileLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        signApkFile.addView(signApkFileLayout);
        makeup(signApkFileLayout, R.drawable.ic_apk_color_96dp, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4");
        base.addView(signApkFile);
        signApkFileLayout.setOnClickListener(v -> signApkFileDialog());

        CardView openLogcatReader = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout LogcatReaderLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        openLogcatReader.addView(LogcatReaderLayout);
        makeup(LogcatReaderLayout, R.drawable.icons8_app_components, getString(R.string.design_drawer_menu_title_logcat_reader), getString(R.string.design_drawer_menu_subtitle_logcat_reader));
        base.addView(openLogcatReader);
        openLogcatReader.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), LogReaderActivity.class)));
    }

    private void signApkFileDialog() {
        aB apkPathDialog = new aB(this);
        apkPathDialog.a(R.drawable.ic_apk_color_96dp);
        apkPathDialog.b("Input APK");

        View testkey_root = getLayoutInflater().inflate(R.layout.manage_font_add, null, false);
        TextView font_preview = testkey_root.findViewById(R.id.font_preview);
        EasyDeleteEditText ed_input = testkey_root.findViewById(R.id.ed_input);
        ImageView select_file = testkey_root.findViewById(R.id.select_file);
        TextView tv_collection = testkey_root.findViewById(R.id.tv_collection);
        CheckBox chk_collection = testkey_root.findViewById(R.id.chk_collection);

        select_file.setOnClickListener(view -> {
            DialogProperties properties = new DialogProperties();
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
            properties.selection_type = DialogConfigs.FILE_SELECT;
            properties.extensions = new String[]{"apk"};
            FilePickerDialog dialog = new FilePickerDialog(this, properties);
            dialog.setDialogSelectionListener(files -> ed_input.getEditText().setText(files[0]));
            dialog.setTitle("Select the APK to sign");
            dialog.show();
        });

        font_preview.setText("Path of APK to sign");
        font_preview.setVisibility(View.VISIBLE);
        tv_collection.setVisibility(View.GONE);
        chk_collection.setVisibility(View.GONE);
        apkPathDialog.a(testkey_root);

        apkPathDialog.a(Helper.getResString(R.string.common_word_cancel),
                Helper.getDialogDismissListener(apkPathDialog));
        apkPathDialog.b("Next", next -> {
            apkPathDialog.dismiss();

            String input_apk_path = ed_input.getEditText().getText().toString();
            String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
            String output_apk_path = new File(Environment.getExternalStorageDirectory(),
                    "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

            if (new File(output_apk_path).exists()) {
                aB confirmOverwrite = new aB(this);
                confirmOverwrite.a(R.drawable.color_save_as_new_96);
                confirmOverwrite.b("File exists");
                confirmOverwrite.a("An APK named " + output_apk_file_name + " already exists at " +
                        "/Internal storage/sketchware/signed_apk/. Overwrite it?");

                confirmOverwrite.a(Helper.getResString(R.string.common_word_cancel),
                        Helper.getDialogDismissListener(confirmOverwrite));
                confirmOverwrite.b("Overwrite", overwrite -> {
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
