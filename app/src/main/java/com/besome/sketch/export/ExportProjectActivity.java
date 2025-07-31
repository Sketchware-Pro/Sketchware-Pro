package com.besome.sketch.export;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.ProjectBuilder;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.oB;
import a.a.a.wq;
import a.a.a.xq;
import a.a.a.yB;
import a.a.a.yq;
import extensions.anbui.sketchware.fragment.FragmentUtils;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.LoadKeystoreException;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.proguard.ManageProguardActivity;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.compiler.bundle.AppBundleCompiler;
import mod.jbk.export.GetKeyStoreCredentialsDialog;
import mod.jbk.util.TestkeySignBridge;
import pro.sketchware.R;
import pro.sketchware.dialogs.BuildSettingsBottomSheet;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ExportProjectActivity extends BaseAppCompatActivity {

    //Constants
    private static final int backPressInterval = 1000;

    //Flags
    private boolean isSetupInOnCreate = false;
    private boolean isUniversalCancelExportRequest = false;

    //Timing
    private long backPressedTime = 0;

    //Project / Metadata
    private final oB file_utility = new oB();
    private String sc_id;
    private HashMap<String, Object> sc_metadata = null;
    private yq project_metadata = null;

    //File paths
    /**
     * /sketchware/signed_apk
     */
    private String signed_apk_postfix;

    /**
     * /sketchware/signed_apk
     */
    private String export_aab_postfix;

    /**
     * /sketchware/export_src
     */
    private String export_src_postfix;

    /**
     * /sdcard/sketchware/export_src
     */
    private String signed_apk_full_path;
    private String export_aab_full_path;
    private String export_src_full_path;
    private String export_src_filename;

    //Sign APK UI
    private MaterialCardView sign_apk_card;
    private ImageView sign_apk_ic;
    private TextView sign_apk_title;
    private Button sign_apk_button;
    private LinearLayout sign_apk_output_stage;
    private TextView sign_apk_output_path;
    private LinearLayout sign_apk_progress_container;
    private TextView sign_apk_progress_text;
    private LinearProgressIndicator sign_apk_progress;
    private LinearLayout sign_apk_output_path_container;
    private Button export_apk_send_button;
    private Button sign_apk_cancel_button;

    //Export AAB UI
    private MaterialCardView export_aab_card;
    private ImageView export_aab_ic;
    private TextView export_aab_title;
    private Button export_aab_button;
    private LinearLayout export_aab_output_stage;
    private TextView export_aab_output_path;
    private LinearLayout export_aab_progress_container;
    private TextView export_aab_progress_text;
    private LinearProgressIndicator export_aab_progress;
    private LinearLayout export_aab_output_path_container;
    private Button export_aab_send_button;
    private Button export_aab_cancel_button;

    //Export Source UI
    private MaterialCardView export_source_card;
    private ImageView export_source_ic;
    private TextView export_source_title;
    private Button export_source_button;
    private TextView export_source_progress_text;
    private LinearProgressIndicator export_source_progress;
    private LinearLayout export_source_output_stage;
    private TextView export_source_output_path;
    private LinearLayout export_source_progress_container;
    private LinearLayout export_source_output_path_container;
    private Button export_source_send_button;
    private Button export_source_cancel_button;

    //Other UI
    private ConstraintLayout ln_r8;
    private ConstraintLayout ln_buid_settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enable Edge To Edge
        enableEdgeToEdgeNoContrast();

        //Set Content View
        setContentView(R.layout.export_project);

        setupUIAndInteraction();

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        sc_metadata = lC.b(sc_id);
        project_metadata = new yq(getApplicationContext(), wq.d(sc_id), sc_metadata);

        initializeOutputDirectories();
        initializeSignApkViews();
        initializeExportSrcViews();
        initializeAppBundleExportViews();

        isSetupInOnCreate = true;
    }

    @Override
    public void onBackPressed() {
        //Press the back key twice within one second to cancel while exporting.
        if (isExporting()) {
            //Prevent action from repeating while canceling.
            if (!isUniversalCancelExportRequest) {
                if (backPressedTime + backPressInterval > System.currentTimeMillis()) {
                    cancelAnyExportNow();
                } else {
                    SketchwareUtil.toast("Press back again to cancel export.");
                    backPressedTime = System.currentTimeMillis();
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", sc_id);
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * Initialize APK Export views
     */
    private void initializeSignApkViews() {

        signAPKUIController(0, "");

        sign_apk_button.setOnClickListener(view -> {
            MaterialAlertDialogBuilder confirmationDialog = new MaterialAlertDialogBuilder(this);
            confirmationDialog.setTitle("Important note");
            confirmationDialog.setMessage("""
                    To sign an APK, you need a keystore. Use your already created one, and copy it to \
                    /Internal storage/sketchware/keystore/release_key.jks and enter the alias's password.
                    
                    Note that this only signs your APK using signing scheme V1, to target Android 11+ for example, \
                    use a 3rd-party tool (for now).""");
            confirmationDialog.setIcon(R.drawable.ic_mtrl_info);

            confirmationDialog.setPositiveButton("Understood", (v, which) -> {
                showApkSigningDialog();
                v.dismiss();
            });
            confirmationDialog.show();
        });
    }

    private void showApkSigningDialog() {
        GetKeyStoreCredentialsDialog credentialsDialog = new GetKeyStoreCredentialsDialog(this,
                R.drawable.ic_mtrl_key,
                "Sign an APK",
                "Fill in the keystore details to sign the APK. " +
                        "If you don't have a keystore, you can use a test key.");
        credentialsDialog.setListener(credentials -> {
            signAPKUIController(1, "");

            BuildingAsyncTask task = new BuildingAsyncTask(this, yq.ExportType.SIGN_APP);
            if (credentials != null) {
                if (credentials.isForSigningWithTestkey()) {
                    task.setSignWithTestkey(true);
                } else {
                    task.configureResultJarSigning(
                            wq.j(),
                            credentials.getKeyStorePassword().toCharArray(),
                            credentials.getKeyAlias(),
                            credentials.getKeyPassword().toCharArray(),
                            credentials.getSigningAlgorithm()
                    );
                }
            } else {
                task.disableResultJarSigning();
            }
            task.execute();
        });
        credentialsDialog.show();
    }


    /**
     * Initialize AAB Export views
     */
    private void initializeAppBundleExportViews() {

        exportAABUIController(0, "");

        export_aab_button.setOnClickListener(view -> {
            MaterialAlertDialogBuilder confirmationDialog = new MaterialAlertDialogBuilder(this);
            confirmationDialog.setTitle("Important note");
            confirmationDialog.setMessage("The generated .aab file must be signed.\nCopy your keystore to /Internal storage/sketchware/keystore/release_key.jks and enter the alias' password.");
            confirmationDialog.setIcon(R.drawable.ic_mtrl_info);

            confirmationDialog.setPositiveButton("Understood", (v, which) -> {
                showAabSigningDialog();
                v.dismiss();
            });
            confirmationDialog.show();
        });
    }

    private void showAabSigningDialog() {
        GetKeyStoreCredentialsDialog credentialsDialog = new GetKeyStoreCredentialsDialog(this,
                R.drawable.ic_mtrl_key, "Sign outputted AAB", "Fill in the keystore details to sign the AAB.");
        credentialsDialog.setListener(credentials -> {
            exportAABUIController(1, "");
            BuildingAsyncTask task = new BuildingAsyncTask(this, yq.ExportType.AAB);
            task.enableAppBundleBuild();
            if (credentials != null) {
                if (credentials.isForSigningWithTestkey()) {
                    task.setSignWithTestkey(true);
                } else {
                    task.configureResultJarSigning(
                            wq.j(),
                            credentials.getKeyStorePassword().toCharArray(),
                            credentials.getKeyAlias(),
                            credentials.getKeyPassword().toCharArray(),
                            credentials.getSigningAlgorithm()
                    );
                }
            }
            task.execute();
        });
        credentialsDialog.show();
    }


    /**
     * Initialize Export to Android Studio views
     */
    private void initializeExportSrcViews() {
        exportSourceUIController(0, "");
    }


    //===================Setup paths===================
    private void initializeOutputDirectories() {
        signed_apk_postfix = File.separator + "sketchware" + File.separator + "signed_apk";
        export_aab_postfix = File.separator + "sketchware" + File.separator + "signed_aab";
        export_src_postfix = File.separator + "sketchware" + File.separator + "export_src";
        /* /sdcard/sketchware/signed_apk */
        signed_apk_full_path = wq.s() + File.separator + "signed_apk";
        export_src_full_path = wq.s() + File.separator + "export_src";
        export_aab_full_path = wq.s() + File.separator + "signed_aab";

        /* Check if they exist, if not, create them */
        file_utility.f(signed_apk_full_path);
        file_utility.f(export_src_full_path);
        //No need to check for AAB as it will be checked elsewhere.
    }
    //=================================================

    //======================Share======================

    //Prepare To Share
    private void universalPrepareToShare(int type) {
        String filePath;
        String fileName;
        String subject;

        if (type == 0) {
            //APK
            filePath = sign_apk_output_path.getText().toString();
            subject = "Here is my APK file just exported.";
        } else if (type == 1) {
            //AAB
            filePath = export_aab_output_path.getText().toString();
            subject = "Here is my AAB file just exported.";
        } else {
            //Source code
            filePath = export_source_output_path.getText().toString();
            subject = "Here is my source code file just exported.";
        }

        if (!filePath.isEmpty()) {

            File file = new File(filePath);
            if (!file.exists()) {
                signAPKUIController(0, "");
                SketchwareUtil.toast("Cannot share because the file does not exist.");
                return;
            }
            fileName = file.getName();

            universalShareFile(subject, Helper.getResString(R.string.myprojects_export_src_title_email_body, fileName), filePath);
        }
    }

    //Call to share
    private void universalShareFile(String subject ,String fileName, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, Helper.getResString(R.string.myprojects_export_src_title_email_body, fileName));
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(filePath)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(Intent.createChooser(intent, Helper.getResString(R.string.myprojects_export_src_chooser_title_email)));
    }
    //=================================================

    //=========Interface and interaction setup=========
    private void setupUIAndInteraction() {
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        //APK Card Here
        sign_apk_card = findViewById(R.id.sign_apk_card);
        sign_apk_ic = findViewById(R.id.sign_apk_ic);
        sign_apk_title = findViewById(R.id.sign_apk_title);
        //Export Button
        sign_apk_button = findViewById(R.id.sign_apk_button);
        //After completion
        sign_apk_output_path = findViewById(R.id.sign_apk_output_path);
        sign_apk_output_stage = findViewById(R.id.sign_apk_output_stage);
        sign_apk_output_path_container = findViewById(R.id.sign_apk_output_path_container);
        export_apk_send_button = findViewById(R.id.export_apk_send_button);
        //While processing
        sign_apk_progress_container = findViewById(R.id.sign_apk_progress_container);
        sign_apk_progress_text = findViewById(R.id.sign_apk_progress_text);
        sign_apk_progress = findViewById(R.id.sign_apk_progress);
        sign_apk_cancel_button = findViewById(R.id.sign_apk_cancel_button);


        //Set On Click
        sign_apk_output_path_container.setOnClickListener(view -> copyToClipboard(sign_apk_output_path.getText().toString()));
        export_apk_send_button.setOnClickListener(view -> universalPrepareToShare(0));
        sign_apk_cancel_button.setOnClickListener(view -> cancelAnyExportNow());


        //AAB Card Here
        export_aab_card = findViewById(R.id.export_aab_card);
        export_aab_ic = findViewById(R.id.export_aab_ic);
        export_aab_title = findViewById(R.id.export_aab_title);
        //Export Button
        export_aab_button = findViewById(R.id.export_aab_button);
        //After completion
        export_aab_output_stage = findViewById(R.id.export_aab_output_stage);
        export_aab_output_path = findViewById(R.id.export_aab_output_path);
        export_aab_output_path_container = findViewById(R.id.export_aab_output_path_container);
        export_aab_send_button = findViewById(R.id.export_aab_send_button);
        //While processing
        export_aab_progress_container = findViewById(R.id.export_aab_progress_container);
        export_aab_progress_text = findViewById(R.id.export_aab_progress_text);
        export_aab_progress = findViewById(R.id.export_aab_progress);
        export_aab_cancel_button = findViewById(R.id.export_aab_cancel_button);

        //Set On Click
        export_aab_output_path_container.setOnClickListener(view -> copyToClipboard(export_aab_output_path.getText().toString()));
        export_aab_send_button.setOnClickListener(view -> universalPrepareToShare(1));
        export_aab_cancel_button.setOnClickListener(view -> cancelAnyExportNow());


        //Source Card Here
        export_source_card = findViewById(R.id.export_source_card);
        export_source_ic = findViewById(R.id.export_source_ic);
        export_source_title = findViewById(R.id.export_source_title);
        //Export Button
        export_source_button = findViewById(R.id.export_source_button);
        //After completion
        export_source_output_path = findViewById(R.id.export_source_output_path);
        export_source_output_path_container = findViewById(R.id.export_source_output_path_container);
        export_source_send_button = findViewById(R.id.export_source_send_button);
        export_source_output_stage = findViewById(R.id.export_source_output_stage);
        //While processing
        export_source_progress_container = findViewById(R.id.export_source_progress_container);
        export_source_progress_text = findViewById(R.id.export_source_progress_text);
        export_source_progress = findViewById(R.id.export_source_progress);
        export_source_cancel_button = findViewById(R.id.export_source_cancel_button);

        //Set On Click
        export_source_button.setOnClickListener(v -> {
            exportSourceUIController(1, "");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    exportSrc();
                }
            }.start();
        });
        export_source_output_path_container.setOnClickListener(view -> copyToClipboard(export_source_output_path.getText().toString()));
        export_source_send_button.setOnClickListener(v -> universalPrepareToShare(2));
        export_source_cancel_button.setOnClickListener(view -> cancelAnyExportNow());


        //Code Shirnking Manager Card
        ln_r8 = findViewById(R.id.ln_r8);
        //Set On Click
        ln_r8.setOnClickListener(view -> {
            Intent intent = new Intent(this, ManageProguardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("sc_id", sc_id);
            startActivity(intent);
        });

        //Build Settings Card
        ln_buid_settings = findViewById(R.id.ln_buid_settings);
        //Set On Click
        ln_buid_settings.setOnClickListener(view -> {
            BuildSettingsBottomSheet sheet = BuildSettingsBottomSheet.newInstance(sc_id);
            sheet.show(getSupportFragmentManager(), BuildSettingsBottomSheet.TAG);
        });
    }
    //=================================================

    //==================UI Controller==================

    //APK Card
    public void signAPKUIController(int status, String path) {
        setAnimationLinearAll();
        if (status == 0) {
            //Not exported or error.
            sign_apk_button.setVisibility(View.VISIBLE);
            sign_apk_output_stage.setVisibility(View.GONE);
            sign_apk_progress_container.setVisibility(View.GONE);
        } else if (status == 1) {
            //Exporting
            beforeAnyStartExport();
            sign_apk_button.setVisibility(View.GONE);
            sign_apk_output_stage.setVisibility(View.GONE);
            sign_apk_progress_container.setVisibility(View.VISIBLE);
            sign_apk_cancel_button.setEnabled(true);
        } else if (status == 2) {
            //Done
            sign_apk_button.setVisibility(View.GONE);
            sign_apk_output_stage.setVisibility(View.VISIBLE);
            sign_apk_output_path.setText(path);
            sign_apk_progress_container.setVisibility(View.GONE);
        } else {
            //Canceling
            sign_apk_progress_text.setText("Canceling...");
            sign_apk_progress.setIndeterminate(true);
            sign_apk_cancel_button.setEnabled(false);
        }

        //Disable or un-disable other cards
        if (status == 1 || status == 3) {
            setEnableAABCard(false);
            setEnableSourceCard(false);
            setEnableR8Card(false);
            setEnableBuildSettingsCard(false);
        } else {
            setEnableAABCard(true);
            setEnableSourceCard(true);
            setEnableR8Card(true);
            setEnableBuildSettingsCard(true);
        }
    }

    //AAB Card
    public void exportAABUIController(int status, String path) {
        setAnimationLinearAll();
        if (status == 0) {
            //Not exported or error.
            export_aab_button.setVisibility(View.VISIBLE);
            export_aab_output_stage.setVisibility(View.GONE);
            export_aab_progress_container.setVisibility(View.GONE);
        } else if (status == 1) {
            //Exporting
            beforeAnyStartExport();
            export_aab_button.setVisibility(View.GONE);
            export_aab_output_stage.setVisibility(View.GONE);
            export_aab_progress_container.setVisibility(View.VISIBLE);
            export_aab_cancel_button.setEnabled(true);
        } else if (status == 2) {
            //Done
            export_aab_button.setVisibility(View.GONE);
            export_aab_output_stage.setVisibility(View.VISIBLE);
            export_aab_output_path.setText(path);
            export_aab_progress_container.setVisibility(View.GONE);
        } else {
            //Canceling
            export_aab_progress_text.setText("Canceling...");
            export_aab_progress.setIndeterminate(true);
            export_aab_cancel_button.setEnabled(false);
        }

        //Disable or un-disable other cards
        if (status == 1 || status == 3) {
            setEnableAPKCard(false);
            setEnableSourceCard(false);
            setEnableR8Card(false);
            setEnableBuildSettingsCard(false);
        } else {
            setEnableAPKCard(true);
            setEnableSourceCard(true);
            setEnableR8Card(true);
            setEnableBuildSettingsCard(true);
        }
    }

    //Export Source Card
    public void exportSourceUIController(int status, String path) {
        setAnimationLinearAll();
        if (status == 0) {
            //Not exported or error.
            export_source_button.setVisibility(View.VISIBLE);
            export_source_output_stage.setVisibility(View.GONE);
            export_source_progress_container.setVisibility(View.GONE);
        } else if (status == 1) {
            //Exporting
            beforeAnyStartExport();
            export_source_button.setVisibility(View.GONE);
            export_source_output_stage.setVisibility(View.GONE);
            export_source_progress_container.setVisibility(View.VISIBLE);
            export_source_cancel_button.setEnabled(true);
        } else if (status == 2) {
            //Done
            export_source_button.setVisibility(View.GONE);
            export_source_output_stage.setVisibility(View.VISIBLE);
            export_source_output_path.setText(path);
            export_source_progress_container.setVisibility(View.GONE);
        } else {
            //Canceling
            export_source_progress_text.setText("Canceling...");
            export_source_progress.setIndeterminate(true);
            export_source_cancel_button.setEnabled(false);
        }

        //Disable or un-disable other cards
        if (status == 1 || status == 3) {
            setEnableAPKCard(false);
            setEnableAABCard(false);
            setEnableR8Card(false);
            setEnableBuildSettingsCard(false);
        } else {
            setEnableAPKCard(true);
            setEnableAABCard(true);
            setEnableR8Card(true);
            setEnableBuildSettingsCard(true);
        }
    }

    //Enable or disable APK Card
    private void setEnableAPKCard(boolean enable) {
        if (isSetupInOnCreate) {
            sign_apk_button.setEnabled(enable);
            if (enable) {
                sign_apk_card.setAlpha(1);
            } else {
                sign_apk_card.setAlpha(0.5f);
            }
        }
    }

    //Enable or disable AAB Card
    private void setEnableAABCard(boolean enable) {
        if (isSetupInOnCreate) {
            export_aab_button.setEnabled(enable);
            if (enable) {
                export_aab_card.setAlpha(1);
            } else {
                export_aab_card.setAlpha(0.5f);
            }
        }
    }

    //Enable or disable Source Card
    private void setEnableSourceCard(boolean enable) {
        if (isSetupInOnCreate) {
            export_source_button.setEnabled(enable);
            if (enable) {
                export_source_card.setAlpha(1);
            } else {
                export_source_card.setAlpha(0.5f);
            }
        }
    }

    //Enable or disable Code Shrinking Manager Card
    private void setEnableR8Card(boolean enable) {
        if (isSetupInOnCreate) {
            ln_r8.setEnabled(enable);
            if (enable) {
                ln_r8.setAlpha(1);
            } else {
                ln_r8.setAlpha(0.5f);
            }
        }
    }

    //Enable or disable Code Shrinking Manager Card
    private void setEnableBuildSettingsCard(boolean enable) {
        if (isSetupInOnCreate) {
            ln_buid_settings.setEnabled(enable);
            if (enable) {
                ln_buid_settings.setAlpha(1);
            } else {
                ln_buid_settings.setAlpha(0.5f);
            }
        }
    }

    //Disposable animation
    private void setAnimationLinearAll() {
        if (isSetupInOnCreate) {
            Transition transition = new AutoTransition();
            transition.setDuration(200);
            TransitionManager.beginDelayedTransition(findViewById(R.id.ln_all), transition);
        }
    }
    //=================================================

    //===Check if there is any export in progress and cancel it===

    //Cancel now.
    public void cancelAnyExportNow() {
        //Prevent re-insertion when users spam the back key.
        if (!isUniversalCancelExportRequest) {

            isUniversalCancelExportRequest = true;

            if (sign_apk_progress_container.getVisibility() == View.VISIBLE) {
                signAPKUIController(3, "");
            }

            if (export_aab_progress_container.getVisibility() == View.VISIBLE) {
                exportAABUIController(3, "");
            }

            if (export_source_progress_container.getVisibility() == View.VISIBLE) {
                exportSourceUIController(3, "");
            }
        }
    }

    //Check if there is any export process in progress.
    public boolean isExporting() {
        return sign_apk_progress_container.getVisibility() == View.VISIBLE ||
                export_aab_progress_container.getVisibility() == View.VISIBLE ||
                export_source_progress_container.getVisibility() == View.VISIBLE;
    }
    //=================================================

    //============Before Any Start Export=============
    private void beforeAnyStartExport() {
        isUniversalCancelExportRequest = false;
    }
    //=================================================

    //================Export APK / AAB=================
    private static class BuildingAsyncTask extends MA implements DialogInterface.OnCancelListener, BuildProgressReceiver {
        private final WeakReference<ExportProjectActivity> activity;
        private final yq project_metadata;
        private final yq.ExportType exportType;

        private ProjectBuilder builder;
        private boolean canceled = false;
        private boolean buildingAppBundle = false;
        private String signingKeystorePath = null;
        private char[] signingKeystorePassword = null;
        private String signingAliasName = null;
        private char[] signingAliasPassword = null;
        private String signingAlgorithm = null;
        private boolean signWithTestkey = false;

        public BuildingAsyncTask(ExportProjectActivity exportProjectActivity, yq.ExportType exportType) {
            super(exportProjectActivity);
            this.exportType = exportType;
            activity = new WeakReference<>(exportProjectActivity);
            project_metadata = exportProjectActivity.project_metadata;
            // Register as AsyncTask with dialog to Activity
            activity.get().addTask(this);
            // Make a simple ProgressDialog show and set its OnCancelListener
            //activity.get().a((DialogInterface.OnCancelListener) this);
            // Allow user to use back button
            //activity.get().progressDialog.setCancelable(false);
        }

        /**
         * a.a.a.MA's doInBackground()
         */
        @Override // a.a.a.MA
        public void b() {
            if (canceled) {
                cancel(true);
                return;
            }

            String sc_id = activity.get().sc_id;

            try {
                onProgress("Deleting temporary files...", 1);
                FileUtil.deleteFile(project_metadata.projectMyscPath);

                onProgress(Helper.getResString(R.string.design_run_title_ready_to_build), 2);
                oB oBVar = new oB();
                /* Check if /Internal storage/sketchware/signed_apk/ exists */
                if (!oBVar.e(wq.o())) {
                    /* Doesn't exist yet, let's create it */
                    oBVar.f(wq.o());
                }
                hC hCVar = new hC(sc_id);
                kC kCVar = new kC(sc_id);
                eC eCVar = new eC(sc_id);
                iC iCVar = new iC(sc_id);
                hCVar.i();
                kCVar.s();
                eCVar.g();
                eCVar.e();
                iCVar.i();
                if (canceled) {
                    cancel(true);
                    return;
                }
                File outputFile = new File(getCorrectResultFilename(project_metadata.releaseApkPath));
                if (outputFile.exists()) {
                    if (!outputFile.delete()) {
                        throw new IllegalStateException("Couldn't delete file " + outputFile.getAbsolutePath());
                    }
                }
                project_metadata.c(a);
                if (canceled) {
                    cancel(true);
                    return;
                }
                project_metadata.a(a, wq.e("600"));
                if (canceled) {
                    cancel(true);
                    return;
                }
                if (yB.a(lC.b(sc_id), "custom_icon")) {
                    project_metadata.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
                    if (yB.a(lC.b(sc_id), "isIconAdaptive", false)) {
                        project_metadata.cf("""
                                <?xml version="1.0" encoding="utf-8"?>
                                <adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android" >
                                <background android:drawable="@mipmap/ic_launcher_background"/>
                                <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
                                <monochrome android:drawable="@mipmap/ic_launcher_monochrome"/>
                                </adaptive-icon>""");
                    } else {
                        project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
                    }
                }
                project_metadata.a();
                kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
                kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
                kCVar.a(project_metadata.assetsPath + File.separator + "fonts");

                builder = new ProjectBuilder(this, a, project_metadata);
                builder.setBuildAppBundle(buildingAppBundle);

                project_metadata.a(iCVar, hCVar, eCVar, exportType);
                builder.buildBuiltInLibraryInformation();
                project_metadata.b(hCVar, eCVar, iCVar, builder.getBuiltInLibraryManager());
                if (canceled) {
                    cancel(true);
                    return;
                }

                FragmentUtils.prepareFallbackFragmentFile(project_metadata.sc_id);

                /* Check AAPT/AAPT2 */
                onProgress("Extracting AAPT/AAPT2 binaries...", 3);
                builder.maybeExtractAapt2();
                if (canceled) {
                    cancel(true);
                    return;
                }

                /* Check built-in libraries */
                onProgress("Extracting built-in libraries...", 4);
                BuiltInLibraries.extractCompileAssets(this);
                if (canceled) {
                    cancel(true);
                    return;
                }

                builder.buildBuiltInLibraryInformation();

                onProgress("AAPT2 is running...", 5);
                builder.compileResources();
                if (canceled) {
                    cancel(true);
                    return;
                }

                KotlinCompilerBridge.compileKotlinCodeIfPossible(this, builder);
                if (canceled) {
                    cancel(true);
                    return;
                }

                onProgress("Java is compiling...", 6);
                builder.compileJavaCode();
                if (canceled) {
                    cancel(true);
                    return;
                }

                /* Encrypt Strings in classes if enabled */
                StringfogHandler stringfogHandler = new StringfogHandler(project_metadata.sc_id);
                stringfogHandler.start(this, builder);
                if (canceled) {
                    cancel(true);
                    return;
                }

                /* Obfuscate classes if enabled */
                ProguardHandler proguardHandler = new ProguardHandler(project_metadata.sc_id);
                proguardHandler.start(this, builder);
                if (canceled) {
                    cancel(true);
                    return;
                }

                /* Create DEX file(s) */
                onProgress(builder.getDxRunningText(), 7);
                builder.createDexFilesFromClasses();
                if (canceled) {
                    cancel(true);
                    return;
                }

                /* Merge DEX file(s) with libraries' dexes */
                onProgress("Merging libraries' DEX files...", 8);
                builder.getDexFilesReady();
                if (canceled) {
                    cancel(true);
                    return;
                }

                if (buildingAppBundle) {
                    AppBundleCompiler compiler = new AppBundleCompiler(builder);
                    onProgress("Creating app module...", 9);
                    compiler.createModuleMainArchive();
                    onProgress("Building app bundle...", 10);
                    compiler.buildBundle();

                    /* Sign the generated .aab file */
                    onProgress("Signing app bundle...", 11);

                    String createdBundlePath = AppBundleCompiler.getDefaultAppBundleOutputFile(project_metadata).getAbsolutePath();
                    String signedAppBundleDirectoryPath = FileUtil.getExternalStorageDir()
                            + File.separator + "sketchware"
                            + File.separator + "signed_aab";
                    FileUtil.makeDir(signedAppBundleDirectoryPath);
                    String outputPath = signedAppBundleDirectoryPath + File.separator +
                            Uri.fromFile(new File(createdBundlePath)).getLastPathSegment();

                    if (signWithTestkey) {
                        ZipSigner signer = new ZipSigner();
                        signer.setKeymode(ZipSigner.KEY_TESTKEY);
                        signer.signZip(createdBundlePath, outputPath);
                    } else if (isResultJarSigningEnabled()) {
                        Security.addProvider(new BouncyCastleProvider());
                        CustomKeySigner.signZip(
                                new ZipSigner(),
                                signingKeystorePath,
                                signingKeystorePassword,
                                signingAliasName,
                                signingAliasPassword,
                                signingAlgorithm,
                                createdBundlePath,
                                outputPath
                        );
                    } else {
                        FileUtil.copyFile(createdBundlePath, getCorrectResultFilename(outputPath));
                    }
                } else {
                    onProgress("Building APK...", 9);
                    builder.buildApk();
                    if (canceled) {
                        cancel(true);
                        return;
                    }

                    onProgress("Aligning APK...", 10);
                    builder.runZipalign(builder.yq.unsignedUnalignedApkPath, builder.yq.unsignedAlignedApkPath);
                    if (canceled) {
                        cancel(true);
                        return;
                    }

                    onProgress("Signing APK...", 11);
                    String outputLocation = getCorrectResultFilename(builder.yq.releaseApkPath);
                    if (signWithTestkey) {
                        TestkeySignBridge.signWithTestkey(builder.yq.unsignedAlignedApkPath, outputLocation);
                    } else if (isResultJarSigningEnabled()) {
                        Security.addProvider(new BouncyCastleProvider());
                        CustomKeySigner.signZip(
                                new ZipSigner(),
                                wq.j(),
                                signingKeystorePassword,
                                signingAliasName,
                                signingKeystorePassword,
                                signingAlgorithm,
                                builder.yq.unsignedAlignedApkPath,
                                outputLocation
                        );
                    } else {
                        FileUtil.copyFile(builder.yq.unsignedAlignedApkPath, outputLocation);
                    }
                }
            } catch (Throwable throwable) {
                if (throwable instanceof LoadKeystoreException &&
                        "Incorrect password, or integrity check failed.".equals(throwable.getMessage())) {
                    activity.get().runOnUiThread(() -> SketchwareUtil.showAnErrorOccurredDialog(activity.get(),
                            "Either an incorrect password was entered, or your key store is corrupt."));
                } else {
                    Log.e("AppExporter", throwable.getMessage(), throwable);
                    activity.get().runOnUiThread(() -> SketchwareUtil.showAnErrorOccurredDialog(activity.get(),
                            Log.getStackTraceString(throwable)));
                }

                onError();

                cancel(true);
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (!activity.get().progressDialog.isCancelable()) {
                activity.get().progressDialog.setCancelable(true);
                activity.get().a((DialogInterface.OnCancelListener) this);
                publishProgress("Canceling process...");
                canceled = true;
            }
        }

        @Override
        public void onCancelled() {
            super.onCancelled();
            builder = null;
            activity.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            //activity.get().i();
            onError();
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            activity.get().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            // Update the ProgressDialog's text
//            activity.get().a(strArr[0]);
        }

        /**
         * a.a.a.MA's onPostExecute()
         */
        @Override // a.a.a.MA
        public void a() {
            activity.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            //activity.get().i();

            //APK
            if (new File(getCorrectResultFilename(project_metadata.releaseApkPath)).exists()) {
                activity.get().afterExportedAPK(getCorrectResultFilename(project_metadata.projectName + "_release.apk"));
            }

            //AAB
            String aabFilename = getCorrectResultFilename(project_metadata.projectName + ".aab");
            if (buildingAppBundle && new File(Environment.getExternalStorageDirectory(), "sketchware" + File.separator + "signed_aab" + File.separator + aabFilename).exists()) {
                activity.get().afterExportedAAB(aabFilename);
            }
        }

        /**
         * Called by a.a.a.MA if doInBackground() (a.a.a.MA#b()) returned a non-empty String,
         * ergo, an error occurred.
         */
        @Override // a.a.a.MA
        public void a(String str) {
            activity.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            //activity.get().i();
            SketchwareUtil.showAnErrorOccurredDialog(activity.get(), str);
            onError();
        }

        public void enableAppBundleBuild() {
            buildingAppBundle = true;
        }

        /**
         * Configures parameters for JAR signing the result.
         * <p></p>
         * If {@link #signWithTestkey} is <code>true</code> though, the result will be signed
         * regardless of {@link #configureResultJarSigning(String, char[], String, char[], String)} and {@link #disableResultJarSigning()} calls.
         */
        public void configureResultJarSigning(String keystorePath, char[] keystorePassword, String aliasName, char[] aliasPassword, String signatureAlgorithm) {
            signingKeystorePath = keystorePath;
            signingKeystorePassword = keystorePassword;
            signingAliasName = aliasName;
            signingAliasPassword = aliasPassword;
            signingAlgorithm = signatureAlgorithm;
        }

        /**
         * Whether to sign the result with testkey or not.
         * Note that this value will always be prioritized over values set with {@link #configureResultJarSigning(String, char[], String, char[], String)}.
         */
        public void setSignWithTestkey(boolean signWithTestkey) {
            this.signWithTestkey = signWithTestkey;
        }

        /**
         * Disables JAR signing of the result. Equivalent to calling {@link #configureResultJarSigning(String, char[], String, char[], String)}
         * with <code>null</code> parameters.
         * <p></p>
         * If {@link #signWithTestkey} is <code>true</code> though, the result will be signed
         * regardless of {@link #configureResultJarSigning(String, char[], String, char[], String)} and {@link #disableResultJarSigning()} calls.
         */
        public void disableResultJarSigning() {
            signingKeystorePath = null;
            signingKeystorePassword = null;
            signingAliasName = null;
            signingAliasPassword = null;
            signingAlgorithm = null;
        }

        public boolean isResultJarSigningEnabled() {
            return signingKeystorePath != null && signingKeystorePassword != null &&
                    signingAliasName != null && signingAliasPassword != null && signingAlgorithm != null;
        }

        private String getCorrectResultFilename(String oldFormatFilename) {
            if (!isResultJarSigningEnabled() && !signWithTestkey) {
                if (buildingAppBundle) {
                    return oldFormatFilename.replace(".aab", ".unsigned.aab");
                } else {
                    return oldFormatFilename.replace("_release", "_release.unsigned");
                }
            } else {
                return oldFormatFilename;
            }
        }

        @Override
        public void onProgress(String progress, int step) {
            //Send progress to UI
            if (activity.get() == null) return;

            canceled = activity.get().isUniversalCancelExportRequest;

            activity.get().runOnUiThread(() -> {
                if (buildingAppBundle) {
                    if (!canceled) {
                        activity.get().export_aab_progress.setIndeterminate(step == -1);
                        activity.get().export_aab_progress_text.setText(progress);
                        if (step > 11) {
                            activity.get().export_aab_progress.setProgress(activity.get().export_aab_progress.getProgress() + 1, true);
                        } else {
                            activity.get().export_aab_progress.setProgress(step, true);
                        }
                    }

                } else {
                    if (!canceled) {
                        activity.get().sign_apk_progress.setIndeterminate(step == -1);
                        activity.get().sign_apk_progress_text.setText(progress);
                        if (step > 11) {
                            activity.get().sign_apk_progress.setProgress(activity.get().sign_apk_progress.getProgress() + 1, true);
                        } else {
                            activity.get().sign_apk_progress.setProgress(step, true);
                        }
                    }

                }
            });
        }

        private void onError() {
            activity.get().runOnUiThread(() -> {
                if (buildingAppBundle) {
                    activity.get().exportAABUIController(0, "");
                } else {
                    activity.get().signAPKUIController(0, "");
                }
            });
        }
    }

    /**
     * Sets exported signed APK file path texts' content.
     */
    private void afterExportedAPK(String filePath) {
        if (isUniversalCancelExportRequest) {
            signAPKUIController(0, "");
            //Delete exported file requested by user to cancel.
            deleteFilePath(signed_apk_full_path + File.separator + filePath);
        } else {
            signAPKUIController(2, signed_apk_full_path + File.separator + filePath);
        }
//        SketchwareUtil.toast(Helper.getResString(R.string.sign_apk_title_export_apk_file));
    }

    /**
     * Sets exported signed AAB file path texts' content.
     */
    private void afterExportedAAB(String fileName) {
        if (isUniversalCancelExportRequest) {
            exportAABUIController(0, "");
            //Delete exported file requested by user to cancel.
            deleteFilePath(export_aab_full_path + File.separator + fileName);
        } else {
            exportAABUIController(2, export_aab_full_path + File.separator + fileName);
        }
    }

    //=================================================

    //===============Export Source Code================
    private void exportSrc() {
        try {
            runOnUiThread(() -> {
                setStatusWhileExportingSourceCode(1, "Preparing...");
            });

            FileUtil.deleteFile(project_metadata.projectMyscPath);

            hC hCVar = new hC(sc_id);
            kC kCVar = new kC(sc_id);
            eC eCVar = new eC(sc_id);
            iC iCVar = new iC(sc_id);
            hCVar.i();
            kCVar.s();
            eCVar.g();
            eCVar.e();
            iCVar.i();

            /* Extract project type template */
            project_metadata.a(getApplicationContext(), wq.e(xq.a(sc_id) ? "600" : sc_id));

            if (isUniversalCancelExportRequest) {
                runOnUiThread(() -> {
                    exportSourceUIController(0, "");
                });
                return;
            }

            /* Start generating project files */

            runOnUiThread(() -> {
                setStatusWhileExportingSourceCode(2, "Generating project files...");
            });

            ProjectBuilder builder = new ProjectBuilder(this, project_metadata);
            project_metadata.a(iCVar, hCVar, eCVar, yq.ExportType.SOURCE_CODE);
            builder.buildBuiltInLibraryInformation();
            project_metadata.b(hCVar, eCVar, iCVar, builder.getBuiltInLibraryManager());
            if (yB.a(lC.b(sc_id), "custom_icon")) {
                project_metadata.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
                if (yB.a(lC.b(sc_id), "isIconAdaptive", false)) {
                    project_metadata.cf("""
                            <?xml version="1.0" encoding="utf-8"?>
                            <adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android" >
                            <background android:drawable="@mipmap/ic_launcher_background"/>
                            <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
                            <monochrome android:drawable="@mipmap/ic_launcher_monochrome"/>
                            </adaptive-icon>""");
                }
            }
            project_metadata.a();
            kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
            kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
            kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
            project_metadata.f();

            if (isUniversalCancelExportRequest) {
                runOnUiThread(() -> {
                    exportSourceUIController(0, "");
                });
                return;
            }

            /* It makes no sense that those methods aren't static */

            runOnUiThread(() -> {
                setStatusWhileExportingSourceCode(3, "Compressing...");
            });

            FilePathUtil util = new FilePathUtil();
            File pathJava = new File(util.getPathJava(sc_id));
            File pathResources = new File(util.getPathResource(sc_id));
            File pathAssets = new File(util.getPathAssets(sc_id));
            File pathNativeLibraries = new File(util.getPathNativelibs(sc_id));

            if (pathJava.exists()) {
                FileUtil.copyDirectory(pathJava, new File(project_metadata.javaFilesPath + File.separator + project_metadata.packageNameAsFolders));
            }
            if (pathResources.exists()) {
                FileUtil.copyDirectory(pathResources, new File(project_metadata.resDirectoryPath));
            }
            String pathProguard = util.getPathProguard(sc_id);
            if (FileUtil.isExistFile(pathProguard)) {
                FileUtil.copyFile(pathProguard, project_metadata.proguardFilePath);
            }
            if (pathAssets.exists()) {
                FileUtil.copyDirectory(pathAssets, new File(project_metadata.assetsPath));
            }
            if (pathNativeLibraries.exists()) {
                FileUtil.copyDirectory(pathNativeLibraries, new File(project_metadata.generatedFilesPath, "jniLibs"));
            }

            ArrayList<String> toCompress = new ArrayList<>();
            toCompress.add(project_metadata.projectMyscPath);
            String exportedFilename = yB.c(sc_metadata, "my_ws_name") + ".zip";

            String exportedSourcesZipPath = wq.s() + File.separator + "export_src" + File.separator + exportedFilename;
            if (file_utility.e(exportedSourcesZipPath)) {
                file_utility.c(exportedSourcesZipPath);
            }

            ArrayList<String> toExclude = new ArrayList<>();
            if (!new File(new FilePathUtil().getPathJava(sc_id) + File.separator + "SketchApplication.java").exists()) {
                toExclude.add("SketchApplication.java");
            }
            toExclude.add("DebugActivity.java");

            new KB().a(exportedSourcesZipPath, toCompress, toExclude);
            project_metadata.e();
            runOnUiThread(() -> initializeAfterExportedSourceViews(exportedFilename));
        } catch (Exception e) {
            runOnUiThread(() -> {
                Log.e("ProjectExporter", "While trying to export project's sources: "
                        + e.getMessage(), e);
                SketchwareUtil.showAnErrorOccurredDialog(this, Log.getStackTraceString(e));
                exportSourceUIController(0, "");
            });
        }
    }

    //Update progress
    private void setStatusWhileExportingSourceCode(int position, String status) {
        if (!isUniversalCancelExportRequest) {
            export_source_progress.setIndeterminate(position == -1);
            export_source_progress_text.setText(status);
            if (position > 4) {
                export_source_progress.setProgress(export_source_progress.getProgress() + 1, true);
            } else {
                export_source_progress.setProgress(position, true);
            }
        }
    }

    /**
     * Set content of exported source views
     */
    private void initializeAfterExportedSourceViews(String exportedSrcFilename) {
        export_src_filename = exportedSrcFilename;
        if (isUniversalCancelExportRequest) {
            exportSourceUIController(0, "");
            //Delete exported file requested by user to cancel.
            deleteFilePath(export_src_full_path + File.separator + export_src_filename);
        } else {
            exportSourceUIController(2, export_src_full_path + File.separator + export_src_filename);
        }
    }

    //=================================================

    //=======================Other=====================
    private void deleteFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Log.d("ExportProjectActivity", "Deleted exported file.");
            } else {
                Log.e("ExportProjectActivity", "The exported file does not exist.");
            }
        } else {
            Log.w("ExportProjectActivity", "The exported file does not exist.");
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Exported and saved in", text);
        clipboard.setPrimaryClip(clip);
        SketchwareUtil.toast("Copied.");
    }
    //=================================================
}