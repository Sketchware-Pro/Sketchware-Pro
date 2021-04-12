package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.apksigner.ApkSigner;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

public class Tools extends Activity {

    private ViewGroup base;
    private RecyclerView dump;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(0x7f0b01bf);
        dump = findViewById(2131231449);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        newToolbar(base);
        setupViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"))) {
            FileUtil.deleteFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"));
        }
    }

    private void makeup(View parent, int iconResourceId, String title, String subtitle) {
        View inflate = getLayoutInflater().inflate(2131427537, null);
        ImageView imageView = inflate.findViewById(2131231428);
        inflate.findViewById(2131231965).setVisibility(View.GONE);
        ((LinearLayout) imageView.getParent()).setGravity(17);
        imageView.setImageResource(iconResourceId);
        ((TextView) inflate.findViewById(2131231430)).setText(title);
        ((TextView) inflate.findViewById(2131231427)).setText(subtitle);
        ((ViewGroup) parent).addView(inflate);
    }

    private CardView newCard(int width, int height, float weight) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height, weight);
        layoutParams.setMargins((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2));
        cardView.setLayoutParams(layoutParams);
        cardView.setPadding((int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2), (int) SketchwareUtil.getDip(2));
        cardView.setCardBackgroundColor(-1);
        cardView.setRadius(SketchwareUtil.getDip(4));
        return cardView;
    }

    private LinearLayout newLayout(int width, int height, float weight) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
        linearLayout.setPadding((int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1), (int) SketchwareUtil.getDip(1));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);
        linearLayout.setBackground(new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#64B5F6")}), gradientDrawable, null));
        linearLayout.setClickable(true);
        linearLayout.setFocusable(true);
        return linearLayout;
    }

    private void newToolbar(View parent) {
        View inflate = getLayoutInflater().inflate(2131427799, null);
        ((TextView) inflate.findViewById(2131232458)).setText("Tools");
        ImageView back = inflate.findViewById(2131232457);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Helper.applyRippleToToolbarView(back);
        parent.setPadding(0, 0, 0, 0);
        ((ViewGroup) parent).addView(inflate, 0);
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
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(final String[] files) {
                final boolean isDirectory = new File(files[0]).isDirectory();
                final AlertDialog.Builder builder;
                if (files.length > 1 || isDirectory) {
                    builder = new AlertDialog.Builder(Tools.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Delete"}, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog confirmationDialog = new AlertDialog.Builder(Tools.this)
                                            .setTitle("Delete " + (isDirectory ? "folder" : "file") + "?")
                                            .setMessage("Are you sure you want to delete this " + (isDirectory ? "folder" : "file") + " permanently? This cannot be undone.")
                                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    for (String file : files) {
                                                        FileUtil.deleteFile(file);
                                                        dialog.dismiss();
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .create();
                                    confirmationDialog.show();
                                    dialog.dismiss();
                                }
                            });
                } else {
                    builder = new AlertDialog.Builder(Tools.this)
                            .setTitle("Select an action")
                            .setSingleChoiceItems(new String[]{"Edit", "Delete"}, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Intent intent = new Intent();
                                            if (ConfigActivity.isLegacyCeEnabled()) {
                                                intent.setClass(getApplicationContext(), mod.hey.studios.activity.SrcCodeEditor.class);
                                            } else {
                                                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
                                            }
                                            intent.putExtra("title", Uri.parse(files[0]).getLastPathSegment());
                                            intent.putExtra("content", files[0]);
                                            intent.putExtra("xml", "");
                                            startActivity(intent);
                                            break;

                                        case 1:
                                            AlertDialog confirmationDialog = new AlertDialog.Builder(Tools.this)
                                                    .setTitle("Delete file?")
                                                    .setMessage("Are you sure you want to delete this file permanently? This cannot be undone.")
                                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            FileUtil.deleteFile(files[0]);
                                                        }
                                                    })
                                                    .setNegativeButton("Cancel", null)
                                                    .create();
                                            confirmationDialog.show();
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            });
                }
                builder.show();
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
        newLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), BlocksManager.class);
                startActivity(intent);
            }
        });
        CardView blockSelectorManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout blockSelectorManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        blockSelectorManager.addView(blockSelectorManagerLayout);
        makeup(blockSelectorManagerLayout, 2131166037, "Block selector menu manager", "Manage your own block selector menus");
        base.addView(blockSelectorManager);
        blockSelectorManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), BlockSelectorActivity.class);
                startActivity(intent);
            }
        });
        CardView componentManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout componentManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        componentManager.addView(componentManagerLayout);
        makeup(componentManagerLayout, 2131165449, "Component manager", "Manage your own components");
        base.addView(componentManager);
        componentManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ComponentsMaker.class);
                startActivity(intent);
            }
        });
        CardView eventManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout eventManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        eventManager.addView(eventManagerLayout);
        makeup(eventManagerLayout, 2131165580, "Event manager", "Manage your own events");
        base.addView(eventManager);
        eventManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMaker.class);
                startActivity(intent);
            }
        });
        CardView localLibraryManager = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout localLibraryManagerLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        localLibraryManager.addView(localLibraryManagerLayout);
        makeup(localLibraryManagerLayout, 2131165477, "Local library manager", "Manage and download local libraries");
        base.addView(localLibraryManager);
        localLibraryManagerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/local_library"), "[]");
                Intent intent = new Intent();
                intent.putExtra("sc_id", "system");
                intent.setClass(getApplicationContext(), ManageLocalLibraryActivity.class);
                startActivity(intent);
            }
        });
        CardView modSettings = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout modSettingsLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        modSettings.addView(modSettingsLayout);
        makeup(modSettingsLayout, 2131165546, "Mod settings", "Change general mod settings");
        base.addView(modSettings);
        modSettingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ConfigActivity.class);
                startActivity(intent);
            }
        });
        CardView openWorkingDirectory = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout openWorkingDirectoryLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        openWorkingDirectory.addView(openWorkingDirectoryLayout);
        makeup(openWorkingDirectoryLayout, 2131558403, "Open working directory", "Open Sketchware Pro's directory and edit files in it");
        base.addView(openWorkingDirectory);
        openWorkingDirectoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkingDirectory();
            }
        });
        CardView signApkFile = newCard(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.0f);
        LinearLayout signApkFileLayout = newLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.0f);
        signApkFile.addView(signApkFileLayout);
        makeup(signApkFileLayout, 0x7f0701f8, "Sign an APK file with testkey", "Sign an already existing APK file with testkey and signature schemes up to V4");
        base.addView(signApkFile);
        signApkFileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signApkFileDialog();
            }
        });
    }

    private void signApkFileDialog() {
        class DialogCancelButtonOnClickListener implements View.OnClickListener {

            private final AlertDialog mDialog;

            public DialogCancelButtonOnClickListener(AlertDialog dialog) {
                mDialog = dialog;
            }

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        }

        View dialog_root = getLayoutInflater().inflate(0x7f0b0052, null, false);
        ImageView dialog_img = dialog_root.findViewById(0x7f0800fe);
        TextView dialog_title = dialog_root.findViewById(0x7f080100);
        TextView dialog_msg = dialog_root.findViewById(0x7f0800ff);
        FrameLayout custom_view = dialog_root.findViewById(0x7f0800dd);
        TextView dialog_btn_no = dialog_root.findViewById(0x7f0800fc);
        TextView dialog_btn_yes = dialog_root.findViewById(0x7f0800fd);

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

        /* ic_apk_color_96dp */
        dialog_img.setImageResource(0x7f0701f8);
        dialog_title.setText("Sign an APK");
        dialog_msg.setText("To sign an APK file, a key is required. What type is the key to sign the APK with?");
        dialog_btn_no.setText("Cancel");
        dialog_btn_no.setOnClickListener(new DialogCancelButtonOnClickListener(dialog_key_type));
        dialog_btn_yes.setText("Next");
        dialog_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Check if user has selected a key type */
                if (radio_group.getCheckedRadioButtonId() == -1) {
                    SketchwareUtil.toast("Select a key type");
                    return;
                }

                /* We recycle dialog_root, so we need to remove it from its parent first */
                ((ViewGroup) dialog_root.getParent()).removeView(dialog_root);

                if (radio_testkey.isChecked()) {
                    View testkey_root = getLayoutInflater().inflate(0x7f0b00c1, null, false);
                    TextView font_preview = testkey_root.findViewById(0x7f08015d);
                    EasyDeleteEditText ed_input = testkey_root.findViewById(0x7f08010e);
                    ImageView select_file = testkey_root.findViewById(0x7f0803de);
                    TextView tv_collection = testkey_root.findViewById(0x7f0804a9);
                    CheckBox chk_collection = testkey_root.findViewById(0x7f0800a7);

                    select_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogProperties properties = new DialogProperties();
                            properties.selection_mode = DialogConfigs.SINGLE_MODE;
                            properties.selection_type = DialogConfigs.FILE_SELECT;
                            properties.extensions = new String[]{"apk"};
                            FilePickerDialog dialog = new FilePickerDialog(Tools.this, properties);
                            dialog.setDialogSelectionListener(new DialogSelectionListener() {
                                @Override
                                public void onSelectedFilePaths(String[] files) {
                                    ed_input.getEditText().setText(files[0]);
                                }
                            });
                            dialog.setTitle("Select the APK to sign");
                            dialog.show();
                        }
                    });

                    dialog_title.setText("Input APK");
                    /* ic_apk_color_96dp */
                    dialog_img.setImageResource(0x7f0701f8);
                    dialog_msg.setVisibility(View.GONE);
                    font_preview.setText("Path of APK to sign");
                    font_preview.setVisibility(View.VISIBLE);
                    tv_collection.setVisibility(View.GONE);
                    chk_collection.setVisibility(View.GONE);

                    custom_view.removeAllViews();
                    custom_view.addView(testkey_root);

                    AlertDialog dialog_testkey = new AlertDialog.Builder(Tools.this)
                            .setView(dialog_root)
                            .create();

                    dialog_btn_no.setOnClickListener(new DialogCancelButtonOnClickListener(dialog_testkey));

                    dialog_btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_testkey.dismiss();

                            String input_apk_path = ed_input.getEditText().getText().toString();
                            String output_apk_file_name = Uri.fromFile(new File(input_apk_path)).getLastPathSegment();
                            String output_apk_path = new File(FileUtil.getExternalStorageDir(),
                                    "sketchware/signed_apk/" + output_apk_file_name).getAbsolutePath();

                            if (new File(output_apk_path).exists()) {
                                /* We recycle dialog_root, so we need to remove it from its parent first */
                                ((ViewGroup) dialog_root.getParent()).removeView(dialog_root);

                                /* As we're recycling dialog_root, we need to remove the checkboxes from earlier */
                                if (custom_view.getParent() != null) {
                                    ((ViewGroup) custom_view.getParent()).removeView(custom_view);
                                }

                                AlertDialog dialog_confirm_overwrite = new AlertDialog.Builder(Tools.this)
                                        .setView(dialog_root)
                                        .create();

                                /* color_save_as_new_96 */
                                dialog_img.setImageResource(0x7f070121);
                                dialog_title.setText("File exists");
                                dialog_msg.setText("An APK named " + output_apk_file_name
                                        + " exists already in /Internal storage/sketchware/signed_apk/. Overwrite it?");
                                dialog_msg.setVisibility(View.VISIBLE);
                                dialog_btn_no.setText("Cancel");
                                dialog_btn_no.setOnClickListener(new DialogCancelButtonOnClickListener(dialog_confirm_overwrite));
                                dialog_btn_yes.setText("Overwrite");
                                dialog_btn_yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog_confirm_overwrite.dismiss();
                                        signApkFileDebugWithDialog(input_apk_path, output_apk_path);
                                    }
                                });

                                dialog_confirm_overwrite.show();
                                return;
                            }

                            signApkFileDebugWithDialog(input_apk_path, output_apk_path);
                        }
                    });

                    dialog_testkey.show();
                } else if (radio_own_jks.isChecked()) {
                    View own_jks_root = getLayoutInflater().inflate(0x7f0b00c1, null, false);
                    TextView font_preview = own_jks_root.findViewById(0x7f08015d);
                    EasyDeleteEditText ed_input = own_jks_root.findViewById(0x7f08010e);
                    ImageView select_file = own_jks_root.findViewById(0x7f0803de);
                    TextView tv_collection = own_jks_root.findViewById(0x7f0804a9);
                    CheckBox chk_collection = own_jks_root.findViewById(0x7f0800a7);

                    select_file.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogProperties properties = new DialogProperties();
                            properties.selection_mode = DialogConfigs.SINGLE_MODE;
                            properties.selection_type = DialogConfigs.FILE_SELECT;
                            properties.extensions = new String[]{"jks", "keystore"};
                            FilePickerDialog dialog = new FilePickerDialog(Tools.this, properties);
                            dialog.setDialogSelectionListener(new DialogSelectionListener() {
                                @Override
                                public void onSelectedFilePaths(String[] files) {
                                    ed_input.getEditText().setText(files[0]);
                                }
                            });
                            dialog.setTitle("Select a keystore");
                            dialog.show();
                        }
                    });

                    dialog_title.setText("Sign with custom keystore");
                    /* ic_renew_48dp */
                    dialog_img.setImageResource(0x7f070289);
                    dialog_msg.setVisibility(View.GONE);
                    font_preview.setText("Custom keystore path");
                    font_preview.setVisibility(View.VISIBLE);
                    tv_collection.setVisibility(View.GONE);
                    chk_collection.setVisibility(View.GONE);

                    custom_view.removeAllViews();
                    custom_view.addView(own_jks_root);

                    AlertDialog dialog_own_jks_path = new AlertDialog.Builder(Tools.this)
                            .setView(dialog_root)
                            .create();

                    dialog_btn_no.setOnClickListener(new DialogCancelButtonOnClickListener(dialog_own_jks_path));

                    dialog_btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ed_input.getEditText().getText().toString().isEmpty()) {
                                SketchwareUtil.toast("Select a keystore or enter its path");
                                return;
                            }

                            File keystore_file = new File(ed_input.getEditText().getText().toString());
                            if (keystore_file.exists()) {
                                try {
                                    KeyStore key_store = KeyStore.getInstance("JKS");
                                    key_store.load(new FileInputStream(ed_input.getEditText().getText().toString()), "".toCharArray());
                                } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
                                    SketchwareUtil.toastError("An error occurred while operating with the keystore: " + e.getMessage());
                                    Log.e(Tools.class.getSimpleName(), "Error while handling keystore "
                                            + ed_input.getEditText().getText().toString() + ": " + e.getMessage(), e);
                                }
                                //TODO: Verify Keystore and list possible aliases
                                //TODO: Use 0x7f070250 as keystore password icon (ic_key_48dp)
                            } else {
                                SketchwareUtil.toastError("Check your keystore file path");
                            }
                        }
                    });

                    dialog_own_jks_path.show();
                } else if (radio_own_key.isChecked()) {
                    SketchwareUtil.toast("Coming soon");
                }
                dialog_key_type.dismiss();
            }
        });

        dialog_key_type.show();
    }

    private void signApkFileDebugWithDialog(String input_apk_path, String output_apk_path) {
        View building_root = getLayoutInflater().inflate(0x7f0b002c, null, false);
        LinearLayout layout_quiz = building_root.findViewById(0x7f0802a1);
        TextView tv_progress = building_root.findViewById(0x7f080557);

        ScrollView scroll_view = new ScrollView(Tools.this);
        TextView tv_log = new TextView(Tools.this);
        scroll_view.addView(tv_log);
        layout_quiz.addView(scroll_view);

        tv_progress.setText("Signing APK...");

        AlertDialog building_dialog = new AlertDialog.Builder(Tools.this)
                .setView(building_root)
                .create();

        ApkSigner signer = new ApkSigner(Tools.this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                signer.signWithTestKey(input_apk_path, output_apk_path, new ApkSigner.LogCallback() {
                    @Override
                    public void onNewLineLogged(String line) {
                        Log.d("Tools", "New System.out line: " + line);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_log.setText(tv_log.getText().toString() + line);
                            }
                        });
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_progress.setText("Signed APK file! You can close the dialog now.");
                        SketchwareUtil.toast("Successfully saved signed APK to: /Internal storage/sketchware/signed_apk/"
                                        + Uri.fromFile(new File(output_apk_path)).getLastPathSegment(),
                                Toast.LENGTH_LONG);
                    }
                });
            }
        }.start();

        building_dialog.show();
    }
}