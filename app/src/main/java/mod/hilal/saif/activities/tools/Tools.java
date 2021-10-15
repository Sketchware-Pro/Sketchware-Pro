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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import a.a.a.aB;
import a.a.a.xB;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.JksKeyStore;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.apksigner.ApkSigner;
import mod.hey.studios.util.Helper;

public class Tools extends Activity {

    private ViewGroup base;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.view_events);
        RecyclerView dump = findViewById(Resources.id.list_events);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        newToolbar(base);
        setupViews();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void makeup(View parent, int iconResourceId, String title, String subtitle) {
        View inflate = getLayoutInflater().inflate(Resources.layout.manage_library_base_item, null);
        ImageView icon = inflate.findViewById(Resources.id.lib_icon);
        inflate.findViewById(Resources.id.tv_enable).setVisibility(View.GONE);
        ((LinearLayout) icon.getParent()).setGravity(Gravity.CENTER);
        icon.setImageResource(iconResourceId);
        ((TextView) inflate.findViewById(Resources.id.lib_title)).setText(title);
        ((TextView) inflate.findViewById(Resources.id.lib_desc)).setText(subtitle);
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
        View toolbar = getLayoutInflater().inflate(Resources.layout.toolbar_improved, null);
        ((TextView) toolbar.findViewById(Resources.id.tx_toolbar_title)).setText("Tools");
        ImageView back = toolbar.findViewById(Resources.id.ig_toolbar_back);

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
                new AlertDialog.Builder(Tools.this)
                        .setTitle("Select an action")
                        .setSingleChoiceItems(new String[]{"Delete"}, -1, (actionDialog, which) -> {
                            new AlertDialog.Builder(Tools.this)
                                    .setTitle("Delete " + (isDirectory ? "folder" : "file") + "?")
                                    .setMessage("Are you sure you want to delete this " + (isDirectory ? "folder" : "file") + " permanently? This cannot be undone.")
                                    .setPositiveButton(Resources.string.common_word_delete, (deleteConfirmationDialog, which1) -> {
                                        for (String file : files) {
                                            FileUtil.deleteFile(file);
                                            deleteConfirmationDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton(Resources.string.common_word_cancel, null)
                                    .show();
                            actionDialog.dismiss();
                        })
                        .show();
            } else {
                new AlertDialog.Builder(Tools.this)
                        .setTitle("Select an action")
                        .setSingleChoiceItems(new String[]{"Edit", "Delete"}, -1, (actionDialog, which) -> {
                            switch (which) {
                                case 0:
                                    Intent intent = new Intent(getApplicationContext(), ConfigActivity.isLegacyCeEnabled() ?
                                            mod.hey.studios.activity.SrcCodeEditor.class
                                            : mod.hey.studios.code.SrcCodeEditor.class);
                                    intent.putExtra("title", Uri.parse(files[0]).getLastPathSegment());
                                    intent.putExtra("content", files[0]);
                                    intent.putExtra("xml", "");
                                    startActivity(intent);
                                    break;

                                case 1:
                                    new AlertDialog.Builder(Tools.this)
                                            .setTitle("Delete file?")
                                            .setMessage("Are you sure you want to delete this file permanently? This cannot be undone.")
                                            .setPositiveButton(Resources.string.common_word_delete, (dialog112, which12) ->
                                                    FileUtil.deleteFile(files[0]))
                                            .setNegativeButton(Resources.string.common_word_cancel, null)
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
        makeup(newLayout, 2131165374, "Block manager", "Manage your own blocks to use in Logic Editor");
        base.addView(blockManager);
        newLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlocksManager.class)));

        CardView blockSelectorManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout blockSelectorManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockSelectorManager.addView(blockSelectorManagerLayout);
        makeup(blockSelectorManagerLayout, 2131166037, "Block selector menu manager", "Manage your own block selector menus");
        base.addView(blockSelectorManager);
        blockSelectorManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), BlockSelectorActivity.class)));

        CardView componentManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout componentManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        componentManager.addView(componentManagerLayout);
        makeup(componentManagerLayout, 2131165449, "Component manager", "Manage your own components");
        base.addView(componentManager);
        componentManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ComponentsMaker.class)));

        CardView eventManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout eventManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        eventManager.addView(eventManagerLayout);
        makeup(eventManagerLayout, 2131165580, "Event manager", "Manage your own events");
        base.addView(eventManager);
        eventManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), EventsMaker.class)));

        CardView localLibraryManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout localLibraryManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        localLibraryManager.addView(localLibraryManagerLayout);
        makeup(localLibraryManagerLayout, 2131165477, "Local library manager", "Manage and download local libraries");
        base.addView(localLibraryManager);
        localLibraryManagerLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ManageLocalLibraryActivity.class),
                new Pair<>("sc_id", "system")));

        CardView modSettings = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout modSettingsLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        modSettings.addView(modSettingsLayout);
        makeup(modSettingsLayout, 2131165546, "Mod settings", "Change general mod settings");
        base.addView(modSettings);
        modSettingsLayout.setOnClickListener(new ActivityLauncher(
                new Intent(getApplicationContext(), ConfigActivity.class)));

        CardView openWorkingDirectory = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout openWorkingDirectoryLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        openWorkingDirectory.addView(openWorkingDirectoryLayout);
        makeup(openWorkingDirectoryLayout, 2131558403, "Open working directory", "Open Sketchware Pro's directory and edit files in it");
        base.addView(openWorkingDirectory);
        openWorkingDirectoryLayout.setOnClickListener(v -> openWorkingDirectory());

        CardView signApkFile = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout signApkFileLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        signApkFile.addView(signApkFileLayout);
        makeup(signApkFileLayout, 0x7f0701f8, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4");
        base.addView(signApkFile);
        signApkFileLayout.setOnClickListener(v -> signApkFileDialog());
    }

    private void signApkFileDialog() {
        View dialog_root = getLayoutInflater().inflate(Resources.layout.dialog, null, false);
        ImageView dialog_img = dialog_root.findViewById(Resources.id.dialog_img);
        TextView dialog_title = dialog_root.findViewById(Resources.id.dialog_title);
        TextView dialog_msg = dialog_root.findViewById(Resources.id.dialog_msg);
        FrameLayout custom_view = dialog_root.findViewById(Resources.id.custom_view);
        TextView dialog_btn_no = dialog_root.findViewById(Resources.id.dialog_btn_no);
        TextView dialog_btn_yes = dialog_root.findViewById(Resources.id.dialog_btn_yes);

        AlertDialog dialog_key_type = new AlertDialog.Builder(this)
                .setView(dialog_root)
                .create();

        RadioGroup radio_group = new RadioGroup(this);

        RadioButton radio_testkey = new RadioButton(this);
        radio_testkey.setText("Debug key (testkey)");
        radio_group.addView(radio_testkey);

        RadioButton radio_own_jks = new RadioButton(this);
        radio_own_jks.setText("Custom Java keystore (.jks)");
        radio_group.addView(radio_own_jks);

        RadioButton radio_own_key = new RadioButton(this);
        radio_own_key.setText("Custom key files (.pk8 and .x509.pem)");
        radio_group.addView(radio_own_key);

        custom_view.addView(radio_group);

        dialog_img.setImageResource(Resources.drawable.ic_apk_color_96dp);
        dialog_title.setText("Sign an APK");
        dialog_msg.setText("To sign an APK file, a key is required. What type is the key to sign the APK with?");
        dialog_btn_no.setText("Cancel");
        dialog_btn_no.setOnClickListener(Helper.getDialogDismissListener(dialog_key_type));
        dialog_btn_yes.setText("Next");
        dialog_btn_yes.setOnClickListener(v -> {
            /* Check if user has selected a key type */
            if (radio_group.getCheckedRadioButtonId() == -1) {
                SketchwareUtil.toast("Select a key type");
                return;
            }

            /* We recycle dialog_root, so we need to remove it from its parent first */
            ((ViewGroup) dialog_root.getParent()).removeView(dialog_root);

            if (radio_testkey.isChecked()) {
                aB apkPathDialog = new aB(this);
                apkPathDialog.a(Resources.drawable.ic_apk_color_96dp);
                apkPathDialog.b("Input APK");

                View testkey_root = getLayoutInflater().inflate(Resources.layout.manage_font_add, null, false);
                TextView font_preview = testkey_root.findViewById(Resources.id.font_preview);
                EasyDeleteEditText ed_input = testkey_root.findViewById(Resources.id.ed_input);
                ImageView select_file = testkey_root.findViewById(Resources.id.select_file);
                TextView tv_collection = testkey_root.findViewById(Resources.id.tv_collection);
                CheckBox chk_collection = testkey_root.findViewById(Resources.id.chk_collection);

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

                apkPathDialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                        Helper.getDialogDismissListener(apkPathDialog));
                apkPathDialog.b("Next", next -> {
                    apkPathDialog.dismiss();

                    String input_apk_path = ed_input.getEditText().getText().toString();
                    String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
                    String output_apk_path = new File(Environment.getExternalStorageDirectory(),
                            "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

                    if (new File(output_apk_path).exists()) {
                        aB confirmOverwrite = new aB(this);
                        confirmOverwrite.a(Resources.drawable.color_save_as_new_96);
                        confirmOverwrite.b("File exists");
                        confirmOverwrite.a("An APK named " + output_apk_file_name + " already exists at " +
                                "/Internal storage/sketchware/signed_apk/. Overwrite it?");

                        confirmOverwrite.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
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
            } else if (radio_own_jks.isChecked()) {
                View own_jks_root = getLayoutInflater().inflate(Resources.layout.manage_font_add, null, false);
                TextView font_preview = own_jks_root.findViewById(Resources.id.font_preview);
                EasyDeleteEditText keyStorePath = own_jks_root.findViewById(Resources.id.ed_input);
                ImageView selectFile = own_jks_root.findViewById(Resources.id.select_file);
                TextView tv_collection = own_jks_root.findViewById(Resources.id.tv_collection);
                CheckBox chk_collection = own_jks_root.findViewById(Resources.id.chk_collection);

                selectFile.setOnClickListener(v1 -> {
                    DialogProperties properties = new DialogProperties();
                    properties.selection_mode = DialogConfigs.SINGLE_MODE;
                    properties.selection_type = DialogConfigs.FILE_SELECT;
                    properties.extensions = new String[]{"jks", "keystore"};
                    FilePickerDialog dialog = new FilePickerDialog(Tools.this, properties);
                    dialog.setDialogSelectionListener(files -> keyStorePath.getEditText().setText(files[0]));
                    dialog.setTitle("Select a keystore");
                    dialog.show();
                });

                dialog_title.setText("Sign with custom keystore");
                dialog_img.setImageResource(Resources.drawable.ic_renew_48dp);
                dialog_msg.setVisibility(View.GONE);
                font_preview.setText("Custom keystore path");
                font_preview.setVisibility(View.VISIBLE);
                tv_collection.setVisibility(View.GONE);
                chk_collection.setVisibility(View.GONE);

                custom_view.removeAllViews();
                custom_view.addView(own_jks_root);

                AlertDialog dialog_own_jks_path = new AlertDialog.Builder(this)
                        .setView(dialog_root)
                        .create();

                dialog_btn_no.setOnClickListener(Helper.getDialogDismissListener(dialog_own_jks_path));

                dialog_btn_yes.setOnClickListener(view -> {
                    if (keyStorePath.getEditText().getText().toString().isEmpty()) {
                        SketchwareUtil.toast("Select a keystore or enter its path");
                    } else {
                        File keystoreFile = new File(keyStorePath.getEditText().getText().toString());
                        if (keystoreFile.exists()) {
                            final String keyStorePathString = keystoreFile.getAbsolutePath();
                            dialog_own_jks_path.dismiss();

                            aB keyStorePasswordDialog = new aB(this);
                            keyStorePasswordDialog.a(Resources.drawable.ic_key_48dp);
                            keyStorePasswordDialog.b("Unlock keystore");
                            keyStorePasswordDialog.a("Enter the keystore's password to use it.");

                            TextInputLayout passwordLayout = new TextInputLayout(this);
                            {
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                passwordLayout.setLayoutParams(params);
                                passwordLayout.setPasswordVisibilityToggleEnabled(true);
                            }

                            EditText password = new EditText(this);
                            {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                password.setLayoutParams(params);
                                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            }
                            passwordLayout.addView(password);

                            keyStorePasswordDialog.a(passwordLayout);

                            keyStorePasswordDialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                                    Helper.getDialogDismissListener(keyStorePasswordDialog));
                            keyStorePasswordDialog.b("Next", passwordNext -> {
                                final String keyStorePasswordString = password.getText().toString();

                                try {
                                    KeyStore keyStore = new JksKeyStore();
                                    try (FileInputStream keystoreStream = new FileInputStream(keyStorePath.getEditText().getText().toString())) {
                                        keyStore.load(keystoreStream, keyStorePasswordString.toCharArray());
                                    }

                                    keyStorePasswordDialog.dismiss();

                                    Enumeration<String> aliases = keyStore.aliases();

                                    aB aliasesDialog = new aB(this);
                                    // TODO: Get fitting icon
                                    // aliasesDialog.a(-1);
                                    aliasesDialog.b("Select an alias");
                                    aliasesDialog.a("Select the alias that will be used to sign the APK.");

                                    LinearLayout alternativeContainer = new LinearLayout(this);
                                    {
                                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                        int margin = (int) SketchwareUtil.getDip(8);
                                        params.leftMargin = margin;
                                        params.topMargin = margin;
                                        params.rightMargin = margin;
                                        params.bottomMargin = margin;
                                        alternativeContainer.setLayoutParams(params);
                                        alternativeContainer.setOrientation(LinearLayout.VERTICAL);
                                    }

                                    TextView alternativeTitle = new TextView(this);
                                    alternativeTitle.setText("Or alternatively, enter the alias' name.");
                                    alternativeContainer.addView(alternativeTitle);

                                    TextInputLayout alternativeAliasLayout = new TextInputLayout(this);
                                    {
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                        alternativeAliasLayout.setLayoutParams(params);
                                    }

                                    EditText alternativeAlias = new EditText(this);
                                    {
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                        alternativeAlias.setLayoutParams(params);
                                    }
                                    alternativeAliasLayout.addView(alternativeAlias);
                                    alternativeContainer.addView(alternativeAliasLayout);

                                    aliasesDialog.a(alternativeContainer);

                                    View.OnClickListener aliasNextOnClickListener = aliasNext -> {
                                        boolean validAliasProvided = false;
                                        final String aliasNameString;

                                        if (TextUtils.isEmpty(alternativeAlias.getText().toString())) {
                                            alternativeAliasLayout.setError("Enter an alias name");
                                            aliasNameString = "";
                                        } else {
                                            aliasNameString = alternativeAlias.getText().toString();
                                            try {
                                                if (keyStore.isKeyEntry(aliasNameString)) {
                                                    alternativeAliasLayout.setError(null);
                                                    validAliasProvided = true;
                                                } else {
                                                    alternativeAliasLayout.setError("Alias does not exist in keystore");
                                                }
                                            } catch (KeyStoreException e) {
                                                alternativeAliasLayout.setError("Failed to operate with keystore: " + e.getMessage());
                                            }
                                        }

                                        if (validAliasProvided) {
                                            aliasesDialog.dismiss();

                                            aB aliasPasswordDialog = new aB(this);
                                            // TODO: Get fitting icon
                                            // aliasPasswordDialog.a(-1);
                                            aliasPasswordDialog.b("Alias password");
                                            aliasPasswordDialog.a("Final step: Enter the alias' password to use it for signing.");

                                            // Recycling the password view from earlier,
                                            // clear password input's state
                                            ((ViewGroup) passwordLayout.getParent()).removeView(passwordLayout);
                                            passwordLayout.setError(null);
                                            password.setText("");
                                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                            aliasPasswordDialog.a(passwordLayout);

                                            aliasPasswordDialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                                                    Helper.getDialogDismissListener(aliasPasswordDialog));
                                            aliasPasswordDialog.b("Generate APK", generateApk -> {
                                                aliasesDialog.dismiss();
                                                final String aliasPasswordString = password.getText().toString();
                                                boolean correctAliasPassword = false;

                                                try {
                                                    keyStore.getKey(aliasNameString, aliasPasswordString.toCharArray());
                                                    correctAliasPassword = true;
                                                } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
                                                    passwordLayout.setError(e.getMessage());
                                                }

                                                if (correctAliasPassword) {
                                                    DialogProperties properties = new DialogProperties();
                                                    properties.selection_mode = DialogConfigs.SINGLE_MODE;
                                                    properties.selection_type = DialogConfigs.FILE_SELECT;
                                                    properties.extensions = new String[]{"apk"};
                                                    FilePickerDialog dialog = new FilePickerDialog(this, properties);
                                                    dialog.setDialogSelectionListener(files -> {
                                                        aliasPasswordDialog.dismiss();
                                                        String path = files[0];
                                                        String outputFilePath =
                                                                Environment.getExternalStorageDirectory().getAbsolutePath() +
                                                                        File.separator + "sketchware/signed_apk/" +
                                                                        Uri.fromFile(new File(path)).getLastPathSegment();
                                                        signApkFileWithDialog(path, outputFilePath,
                                                                false,
                                                                keyStorePathString, keyStorePasswordString,
                                                                aliasNameString, aliasPasswordString);
                                                    });
                                                    dialog.setTitle("Select the APK to sign");
                                                    dialog.show();
                                                }
                                            });
                                            aliasPasswordDialog.show();
                                        }
                                    };

                                    aliasesDialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                                            Helper.getDialogDismissListener(aliasesDialog));
                                    aliasesDialog.b("Next", aliasNextOnClickListener);

                                    aliasesDialog.show();
                                } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
                                    if ("Incorrect password, or integrity check failed.".equals(e.getMessage())) {
                                        passwordLayout.setError("Incorrect password! (Or broken keystore)");
                                    } else {
                                        passwordLayout.setError(null);
                                        SketchwareUtil.toastError("An error occurred while operating with the keystore: " + e.getMessage());
                                        Log.e("Tools", "Error while handling keystore "
                                                + keyStorePath.getEditText().getText().toString(), e);
                                    }
                                }
                            });

                            keyStorePasswordDialog.show();
                        } else {
                            keyStorePath.getEditText().setError("File doesn't exist");
                        }
                    }

                });

                dialog_own_jks_path.show();
            } else if (radio_own_key.isChecked()) {
                SketchwareUtil.toast("Coming soon");
            }
            dialog_key_type.dismiss();
        });

        dialog_key_type.show();
    }

    private void signApkFileWithDialog(String inputApkPath, String outputApkPath, boolean useTestkey,
                                       String keyStorePath, String keyStorePassword, String keyStoreKeyAlias,
                                       String keyPassword) {
        View building_root = getLayoutInflater().inflate(Resources.layout.build_progress_msg_box, null, false);
        LinearLayout layout_quiz = building_root.findViewById(Resources.id.layout_quiz);
        TextView tv_progress = building_root.findViewById(Resources.id.tv_progress);

        ScrollView scroll_view = new ScrollView(this);
        TextView tv_log = new TextView(this);
        scroll_view.addView(tv_log);
        layout_quiz.addView(scroll_view);

        tv_progress.setText("Signing APK...");

        AlertDialog building_dialog = new AlertDialog.Builder(this)
                .setView(building_root)
                .create();

        ApkSigner signer = new ApkSigner(Tools.this);
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
