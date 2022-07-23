package com.besome.sketch.export;

import static mod.SketchwareUtil.getDip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.BuildConfig;
import com.sketchware.remod.R;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Dp;
import a.a.a.KB;
import a.a.a.MA;
import a.a.a.QA;
import a.a.a.aB;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.iI;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.rB;
import a.a.a.wq;
import a.a.a.xq;
import a.a.a.yB;
import a.a.a.yq;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.LoadKeystoreException;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.jbk.build.compiler.bundle.AppBundleCompiler;
import mod.jbk.export.GetKeyStoreCredentialsDialog;

public class ExportProjectActivity extends BaseAppCompatActivity {

    private final oB file_utility = new oB();
    private Button btn_export_src;
    private LottieAnimationView loading_export_src;
    private LinearLayout layout_export_src;
    private TextView tv_src_path;
    /**
     * /sketchware/signed_apk
     */
    private String signed_apk_postfix;
    /**
     * /sketchware/export_src
     */
    private String export_src_postfix;
    /**
     * /sdcard/sketchware/export_src
     */
    private String export_src_full_path;
    private String export_src_filename;
    private String sc_id;
    private HashMap<String, Object> sc_metadata = null;
    private yq project_metadata = null;
    private Button btn_sign_apk;
    private LottieAnimationView loading_sign_apk;
    private LinearLayout layout_apk_path;
    private TextView tv_apk_path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_project);
        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(R.string.myprojects_export_project_actionbar_title));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loading_export_src.h()) {
            loading_export_src.e();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", sc_id);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Sets exported signed APK file path texts' content.
     */
    private void f(String filePath) {
        layout_apk_path.setVisibility(View.VISIBLE);
        btn_sign_apk.setVisibility(View.GONE);
        if (loading_sign_apk.h()) {
            loading_sign_apk.e();
        }
        loading_sign_apk.setVisibility(View.GONE);
        SketchwareUtil.toast(Helper.getResString(R.string.sign_apk_title_export_apk_file));
        tv_apk_path.setText(signed_apk_postfix + File.separator + filePath);
    }

    private void exportSrc() {
        try {
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

            /* Start generating project files */
            project_metadata.b(hCVar, eCVar, iCVar, true);
            if (yB.a(lC.b(sc_id), "custom_icon")) {
                project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
            }
            project_metadata.a();
            kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
            kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
            kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
            project_metadata.f();

            /* It makes no sense that those methods aren't static */
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
            runOnUiThread(() -> e(exportedFilename));
        } catch (Exception e) {
            runOnUiThread(() -> {
                Log.e("ProjectExporter", "While trying to export project's sources: "
                        + e.getMessage(), e);
                b(Log.getStackTraceString(e));
                layout_export_src.setVisibility(View.GONE);
                loading_export_src.setVisibility(View.GONE);
                btn_export_src.setVisibility(View.VISIBLE);
            });
        }
    }

    private void initializeAppBundleExportViews() {
        CardView exportAppBundleRoot = new CardView(this);
        {
            FrameLayout.LayoutParams exportAppBundleRootParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            exportAppBundleRootParams.setMargins(
                    (int) getDip(8),
                    (int) getDip(8),
                    (int) getDip(8),
                    (int) getDip(8)
            );
            exportAppBundleRoot.setLayoutParams(exportAppBundleRootParams);
        }

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        exportAppBundleRoot.addView(relativeLayout);

        ImageView imgAppBundle = new ImageView(this);
        {
            imgAppBundle.setId(R.id.icon_src);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) getDip(24),
                    (int) getDip(24)
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            imgAppBundle.setLayoutParams(params);
            imgAppBundle.setImageResource(R.drawable.open_box_48);
        }
        relativeLayout.addView(imgAppBundle);

        TextView titleExportAppBundle = new TextView(this);
        {
            RelativeLayout.LayoutParams titleExportAppBundleParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            titleExportAppBundleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            titleExportAppBundleParams.leftMargin = (int) getDip(8);
            titleExportAppBundleParams.addRule(RelativeLayout.RIGHT_OF, R.id.icon_src);
            titleExportAppBundle.setLayoutParams(titleExportAppBundleParams);
            titleExportAppBundle.setTextColor(ContextCompat.getColor(this, R.color.scolor_black_01));
            titleExportAppBundle.setTextSize(16f);
            titleExportAppBundle.setTypeface(Typeface.DEFAULT_BOLD);
        }
        relativeLayout.addView(titleExportAppBundle);

        Button btnExportAppBundle = new Button(this);
        {
            RelativeLayout.LayoutParams btnExportAppBundleParams = (RelativeLayout.LayoutParams) btn_export_src.getLayoutParams();
            btnExportAppBundleParams.setMargins(
                    0,
                    (int) getDip(48),
                    0,
                    (int) getDip(16)
            );
            btnExportAppBundle.setLayoutParams(btnExportAppBundleParams);
            btnExportAppBundle.setAllCaps(false);
            btnExportAppBundle.setTextColor(Color.WHITE);
            btnExportAppBundle.setTextSize(14f);
            {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(0xffff5955);
                drawable.setCornerRadius(6);
                btnExportAppBundle.setBackground(drawable);
            }
            btnExportAppBundle.setHighlightColor(0xffff8784);
        }

        relativeLayout.addView(btnExportAppBundle);

        LinearLayout layoutExportAppBundle = new LinearLayout(this);
        {
            LinearLayout.LayoutParams layoutExportAppBundleParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutExportAppBundleParams.topMargin = (int) getDip(32);
            layoutExportAppBundleParams.bottomMargin = (int) getDip(8);
            layoutExportAppBundle.setLayoutParams(layoutExportAppBundleParams);
            layoutExportAppBundle.setOrientation(LinearLayout.VERTICAL);
        }

        LinearLayout var1 = new LinearLayout(this);
        {
            LinearLayout.LayoutParams var1Params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) getDip(24));
            var1Params.leftMargin = (int) getDip(16);
            var1Params.rightMargin = (int) getDip(16);
            var1Params.gravity = Gravity.CENTER_VERTICAL;
            var1.setLayoutParams(var1Params);
            var1.setOrientation(LinearLayout.HORIZONTAL);
        }

        ImageView var2 = new ImageView(this);
        {
            var2.setLayoutParams(new LinearLayout.LayoutParams(
                    (int) getDip(24),
                    (int) getDip(24)));
            var2.setImageResource(R.drawable.ic_folder_48dp);
        }
        var1.addView(var2);

        TextView titleAppBundlePath = new TextView(this);
        {
            LinearLayout.LayoutParams titleAppBundlePathParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            titleAppBundlePathParams.leftMargin = (int) getDip(8);
            titleAppBundlePath.setLayoutParams(titleAppBundlePathParams);
            titleAppBundlePath.setTextSize(14f);
            titleAppBundlePath.setTypeface(Typeface.DEFAULT_BOLD);
        }
        var1.addView(titleAppBundlePath);

        layoutExportAppBundle.addView(var1);

        LinearLayout var3 = new LinearLayout(this);
        {
            LinearLayout.LayoutParams var3Params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) getDip(24));
            var3Params.leftMargin = (int) getDip(16);
            var3Params.topMargin = (int) getDip(4);
            var3Params.rightMargin = (int) getDip(16);
            var3.setLayoutParams(var3Params);
            var3.setBackgroundResource(R.drawable.bg_round_light_grey);
            var3.setOrientation(LinearLayout.HORIZONTAL);
        }

        HorizontalScrollView var4 = new HorizontalScrollView(this);
        {
            var4.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        TextView tvAppBundlePath = new TextView(this);
        {
            tvAppBundlePath.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            tvAppBundlePath.setGravity(Gravity.CENTER_VERTICAL);
            tvAppBundlePath.setLines(1);
            tvAppBundlePath.setPadding(
                    (int) getDip(8),
                    (int) getDip(0),
                    (int) getDip(8),
                    (int) getDip(0)
            );
            tvAppBundlePath.setTextColor(ContextCompat.getColor(this, R.color.scolor_black_01));
            tvAppBundlePath.setTextSize(13f);
        }
        var4.addView(tvAppBundlePath);

        var3.addView(var4);

        layoutExportAppBundle.addView(var3);

        Button btnSendAppBundle;
        {
            LinearLayout btnSendAppBundleContainer = new LinearLayout(this);
            {
                LinearLayout.LayoutParams btnSendAppBundleContainerParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) getDip(40));
                btnSendAppBundleContainerParams.gravity = Gravity.RIGHT;
                btnSendAppBundleContainerParams.rightMargin = (int) getDip(16);
                btnSendAppBundleContainer.setLayoutParams(btnSendAppBundleContainerParams);
            }

            btnSendAppBundle = new Button(this);
            {
                LinearLayout.LayoutParams btnSendAppBundleParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                btnSendAppBundle.setLayoutParams(btnSendAppBundleParams);
                btnSendAppBundle.setTextColor(Color.WHITE);
                btnSendAppBundle.setTextSize(12f);
                btnSendAppBundle.setBackgroundColor(ContextCompat.getColor(this, R.color.scolor_green_normal));
                btnSendAppBundle.setHighlightColor(ContextCompat.getColor(this, R.color.color_btn_green_highlight));
            }
            btnSendAppBundleContainer.addView(btnSendAppBundle);

            layoutExportAppBundle.addView(btnSendAppBundleContainer);
        }

        relativeLayout.addView(layoutExportAppBundle);

        ViewParent plannedParent = findViewById(R.id.icon_apk).getParent().getParent().getParent();
        if (plannedParent instanceof LinearLayout) {
            ((LinearLayout) plannedParent).addView(exportAppBundleRoot);
        }

        titleExportAppBundle.setText("Export Android App Bundle");
        btnExportAppBundle.setText("Export AAB");
        titleAppBundlePath.setText(Helper.getResString(R.string.myprojects_export_project_title_local_path));
        btnSendAppBundle.setText("Send AAB");
        layoutExportAppBundle.setVisibility(View.GONE);

        btnExportAppBundle.setOnClickListener(v -> {
            aB dialog = new aB(ExportProjectActivity.this);
            if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR_NAME_WITHOUT_AABS)) {
                dialog.a(R.drawable.break_warning_96_red);
                dialog.b("Can't generate App Bundle");
                dialog.a("This Sketchware Pro version doesn't support building AABs as it must work on " +
                        "Android 7.1.1 and earlier. Use Sketchware Pro " + BuildConfig.VERSION_NAME_WITHOUT_FLAVOR + "-" +
                        BuildConfig.FLAVOR_NAME_WITH_AABS + " instead.");
                dialog.b(Helper.getResString(R.string.common_word_close), Helper.getDialogDismissListener(dialog));
                dialog.show();
            } else {
                GetKeyStoreCredentialsDialog credentialsDialog = new GetKeyStoreCredentialsDialog(ExportProjectActivity.this,
                        R.drawable.color_about_96, "Sign outputted AAB", "The generated .aab file must be signed.\n" +
                        "Copy your keystore to /Internal storage/sketchware/keystore/release_key.jks " +
                        "and enter the alias' password.");
                credentialsDialog.setListener(credentials -> {
                    btnExportAppBundle.setVisibility(View.GONE);
                    layoutExportAppBundle.setVisibility(View.GONE);

                    BuildingAsyncTask task = new BuildingAsyncTask(getBaseContext());
                    task.enableAppBundleBuild();
                    if (credentials != null) {
                        task.configureResultJarSigning(
                                wq.j(),
                                credentials.getKeyStorePassword().toCharArray(),
                                credentials.getKeyAlias(),
                                credentials.getKeyPassword().toCharArray(),
                                credentials.getSigningAlgorithm()
                        );
                    } else {
                    }
                    task.execute();
                });
                credentialsDialog.show();
            }
        });
    }

    /**
     * Initialize Export to Android Studio views
     */
    private void initializeExportSrcViews() {
        TextView title_export_src = findViewById(R.id.title_export_src);
        btn_export_src = findViewById(R.id.btn_export_src);
        loading_export_src = findViewById(R.id.loading_export_src);
        layout_export_src = findViewById(R.id.layout_export_src);
        TextView title_src_path = findViewById(R.id.title_src_path);
        tv_src_path = findViewById(R.id.tv_src_path);
        Button btn_send_src = findViewById(R.id.btn_send_src);
        title_export_src.setText(Helper.getResString(R.string.myprojects_export_project_title_export_src));
        btn_export_src.setText(Helper.getResString(R.string.myprojects_export_project_button_export_src));
        title_src_path.setText(Helper.getResString(R.string.myprojects_export_project_title_local_path));
        btn_send_src.setText(Helper.getResString(R.string.myprojects_export_project_button_send_src_zip));
        loading_export_src.setVisibility(View.GONE);
        layout_export_src.setVisibility(View.GONE);
        btn_export_src.setOnClickListener(v -> {
            btn_export_src.setVisibility(View.GONE);
            layout_export_src.setVisibility(View.GONE);
            loading_export_src.setVisibility(View.VISIBLE);
            loading_export_src.j();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    exportSrc();
                }
            }.start();
        });
        btn_send_src.setOnClickListener(v -> q());
    }

    /**
     * Initialize APK Export views
     */
    private void initializeSignApkViews() {
        TextView title_sign_apk = findViewById(R.id.title_sign_apk);
        btn_sign_apk = findViewById(R.id.btn_sign_apk);
        loading_sign_apk = findViewById(R.id.loading_sign_apk);
        layout_apk_path = findViewById(R.id.layout_apk_path);
        TextView title_apk_path = findViewById(R.id.title_apk_path);
        tv_apk_path = findViewById(R.id.tv_apk_path);
        title_sign_apk.setText(Helper.getResString(R.string.myprojects_export_project_title_sign_apk));
        btn_sign_apk.setText(Helper.getResString(R.string.myprojects_export_project_button_sign_apk));
        title_apk_path.setText(Helper.getResString(R.string.myprojects_export_project_title_local_path));
        loading_sign_apk.setVisibility(View.GONE);
        layout_apk_path.setVisibility(View.GONE);
        btn_sign_apk.setOnClickListener(v -> {
            GetKeyStoreCredentialsDialog credentialsDialog = new GetKeyStoreCredentialsDialog(ExportProjectActivity.this,
                    R.drawable.color_about_96,
                    "Sign an APK",
                    "To sign an APK, you need a keystore. Use your already created one, and copy it to " +
                            "/Internal storage/sketchware/keystore/release_key.jks and enter the alias's password.\n" +
                            "Note that this only signs your APK using signing scheme V1, to target Android 11+ for example, " +
                            "use a 3rd-party tool (for now).");
            credentialsDialog.setListener(credentials -> {
                btn_sign_apk.setVisibility(View.GONE);
                layout_apk_path.setVisibility(View.GONE);
                loading_sign_apk.setVisibility(View.VISIBLE);
                loading_sign_apk.j();

                BuildingAsyncTask task = new BuildingAsyncTask(getBaseContext());
                if (credentials != null) {
                    task.configureResultJarSigning(
                            wq.j(),
                            credentials.getKeyStorePassword().toCharArray(),
                            credentials.getKeyAlias(),
                            credentials.getKeyPassword().toCharArray(),
                            credentials.getSigningAlgorithm()
                    );
                } else {
                    task.disableResultJarSigning();
                }
                task.execute();
            });
            credentialsDialog.show();
        });
    }

    private void initializeOutputDirectories() {
        signed_apk_postfix = File.separator + "sketchware" + File.separator + "signed_apk";
        export_src_postfix = File.separator + "sketchware" + File.separator + "export_src";
        /**
         * /sdcard/sketchware/signed_apk
         */
        String signed_apk_full_path = wq.s() + File.separator + "signed_apk";
        export_src_full_path = wq.s() + File.separator + "export_src";

        /* Check if they exist, if not, create them */
        file_utility.f(signed_apk_full_path);
        file_utility.f(export_src_full_path);
    }

    public final void q() {
        if (export_src_filename.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_SUBJECT, Helper.getResString(R.string.myprojects_export_src_title_email_subject, export_src_filename));
            intent.putExtra(Intent.EXTRA_TEXT, Helper.getResString(R.string.myprojects_export_src_title_email_body, export_src_filename));
            String filePath = export_src_full_path + File.separator + export_src_filename;
            if (Build.VERSION.SDK_INT >= 24) {
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.a(getApplicationContext(),
                        getApplicationContext().getPackageName() + ".provider", new File(filePath)));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(Intent.createChooser(intent, Helper.getResString(R.string.myprojects_export_src_chooser_title_email)));
        }
    }

    /**
     * Set content of exported source views
     */
    public final void e(String exportedSrcFilename) {
        export_src_filename = exportedSrcFilename;
        loading_export_src.e();
        loading_export_src.setVisibility(View.GONE);
        layout_export_src.setVisibility(View.VISIBLE);
        tv_src_path.setText(export_src_postfix + File.separator + export_src_filename);
    }

    /**
     * Show a "An error has occurred" dialog.
     *
     * @param errorMessage The dialog's error message
     */
    public final void b(String errorMessage) {
        aB dialog = new aB(this);
        dialog.a(R.drawable.break_warning_96_red);
        dialog.b(Helper.getResString(R.string.common_error_an_error_occurred));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView errorMessageThingy = new TextView(this);
        errorMessageThingy.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        errorMessageThingy.setText(errorMessage);
        scrollView.addView(errorMessageThingy);

        dialog.a(scrollView);
        dialog.b(Helper.getResString(R.string.common_word_ok), v -> {
            if (!mB.a()) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public class BuildingAsyncTask extends MA implements DialogInterface.OnCancelListener {

        public Dp c;
        /**
         * Boolean indicating if the user has cancelled building
         */
        public boolean d = false;
        public QA e = new QA();
        public rB f = new rB();
        public iI g = new iI();
        public String h = null;
        private boolean buildingAppBundle = false;
        private String signingKeystorePath = null;
        private char[] signingKeystorePassword = null;
        private String signingAliasName = null;
        private char[] signingAliasPassword = null;
        private String signingAlgorithm = null;
        private boolean signWithTestkey = false;

        public BuildingAsyncTask(Context context) {
            super(context);
            // Register as AsyncTask with dialog to Activity
            ExportProjectActivity.this.a((MA) this);
            // Make a simple ProgressDialog show and set its OnCancelListener
            ExportProjectActivity.this.a((DialogInterface.OnCancelListener) this);
            // Allow user to use back button
            ExportProjectActivity.this.g.a(false);
        }

        /**
         * a.a.a.MA's doInBackground()
         */
        @Override // a.a.a.MA
        public void b() {
            if (d) {
                cancel(true);
                return;
            }

            try {
                publishProgress("Deleting temporary files...");
                FileUtil.deleteFile(project_metadata.projectMyscPath);

                publishProgress(Helper.getResString(R.string.design_run_title_ready_to_build));
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
                if (d) {
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
                if (d) {
                    cancel(true);
                    return;
                }
                project_metadata.a(a, wq.e("600"));
                if (d) {
                    cancel(true);
                    return;
                }
                if (yB.a(lC.b(sc_id), "custom_icon")) {
                    project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
                }
                project_metadata.a();
                kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
                kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
                kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
                project_metadata.b(hCVar, eCVar, iCVar, true);
                if (d) {
                    cancel(true);
                    return;
                }
                c = new Dp(a, project_metadata, buildingAppBundle);

                /* Check AAPT/AAPT2 */
                publishProgress("Extracting AAPT/AAPT2 binaries...");
                c.maybeExtractAapt2();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Check built-in libraries */
                publishProgress("Extracting built-in libraries...");
                c.getBuiltInLibrariesReady();
                if (d) {
                    cancel(true);
                    return;
                }

                publishProgress("AAPT2 is running...");
                c.compileResources();
                if (d) {
                    cancel(true);
                    return;
                }

                KotlinCompilerBridge.compileKotlinCodeIfPossible(this, c);
                if (d) {
                    cancel(true);
                    return;
                }

                publishProgress("Java is compiling...");
                c.compileJavaCode();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Encrypt Strings in classes if enabled */
                StringfogHandler stringfogHandler = new StringfogHandler(project_metadata.sc_id);
                stringfogHandler.start(null, c);
                if (d) {
                    cancel(true);
                    return;
                }

                /* Obfuscate classes if enabled */
                ProguardHandler proguardHandler = new ProguardHandler(project_metadata.sc_id);
                proguardHandler.start(null, c);
                if (d) {
                    cancel(true);
                    return;
                }

                /* Create DEX file(s) */
                publishProgress(c.getDxRunningText());
                c.createDexFilesFromClasses();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Merge DEX file(s) with libraries' dexes */
                publishProgress("Merging libraries' DEX files...");
                c.getDexFilesReady();
                if (d) {
                    onCancelled();
                    return;
                }

                if (buildingAppBundle) {
                    AppBundleCompiler compiler = new AppBundleCompiler(c);
                    publishProgress("Creating app module...");
                    compiler.createModuleMainArchive();
                    publishProgress("Building app bundle...");
                    compiler.buildBundle();

                    /* Sign the generated .aab file */
                    publishProgress("Signing app bundle...");

                    String createdBundlePath = AppBundleCompiler.getDefaultAppBundleOutputFile(
                                    ExportProjectActivity.this, sc_id)
                            .getAbsolutePath();
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
                    } else {
                        if (isResultJarSigningEnabled()) {
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
                    }
                } else {
                    publishProgress("Building APK...");
                    c.buildApk();
                    if (d) {
                        cancel(true);
                        return;
                    }

                    publishProgress("Signing APK...");
                    if (signWithTestkey) {
                        ZipSigner signer = new ZipSigner();
                        signer.setKeymode(ZipSigner.KEY_TESTKEY);
                        signer.signZip(c.yq.unsignedUnalignedApkPath, c.yq.unalignedSignedApkPath);
                    } else if (isResultJarSigningEnabled()) {
                        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
                        CustomKeySigner.signZip(
                                new ZipSigner(),
                                wq.j(),
                                signingKeystorePassword,
                                signingAliasName,
                                signingKeystorePassword,
                                signingAlgorithm,
                                c.yq.unsignedUnalignedApkPath,
                                c.yq.unalignedSignedApkPath
                        );
                    } else {
                        FileUtil.copyFile(c.yq.unsignedUnalignedApkPath, c.yq.unalignedSignedApkPath);
                    }
                    if (d) {
                        cancel(true);
                        return;
                    }

                    publishProgress("Aligning APK...");
                    c.runZipalign(c.yq.unalignedSignedApkPath, getCorrectResultFilename(c.yq.releaseApkPath));
                }
            } catch (Throwable throwable) {
                if (throwable instanceof LoadKeystoreException &&
                        "Incorrect password, or integrity check failed.".equals(throwable.getMessage())) {
                    runOnUiThread(() -> ExportProjectActivity.this.b(
                            "Either an incorrect password was entered, " +
                                    "or your key store is corrupt."));
                } else {
                    Log.e("AppExporter", throwable.getMessage(), throwable);
                    runOnUiThread(() ->
                            ExportProjectActivity.this.b(Log.getStackTraceString(throwable)));
                }

                cancel(true);
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (!ExportProjectActivity.this.g.a()) {
                ExportProjectActivity.this.g.a(true);
                ExportProjectActivity.this.a((DialogInterface.OnCancelListener) this);
                publishProgress("Canceling process...");
                d = true;
            }
        }

        @Override
        public void onCancelled() {
            super.onCancelled();
            c = null;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            i();
            layout_apk_path.setVisibility(View.GONE);
            if (loading_sign_apk.h()) {
                loading_sign_apk.e();
            }
            loading_sign_apk.setVisibility(View.GONE);
            btn_sign_apk.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override // android.os.AsyncTask
        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            // Update the ProgressDialog's text
            ExportProjectActivity.this.a(strArr[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            b(s);
        }

        /**
         * a.a.a.MA's onPostExecute()
         */
        @Override // a.a.a.MA
        public void a() {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            i();

            if (new File(getCorrectResultFilename(project_metadata.releaseApkPath)).exists()) {
                f(getCorrectResultFilename(project_metadata.projectName + "_release.apk"));
            }

            String aabFilename = getCorrectResultFilename(project_metadata.projectName + ".aab");
            if (buildingAppBundle && new File(Environment.getExternalStorageDirectory(),
                    "sketchware" + File.separator + "signed_aab" + File.separator + aabFilename).exists()) {
                aB dialog = new aB(ExportProjectActivity.this);
                dialog.a(R.drawable.open_box_48);
                dialog.b("Finished exporting AAB");
                dialog.a("You can find the generated, signed AAB file at:\n" +
                        "/Internal storage/sketchware/signed_aab/" + aabFilename);
                dialog.b(Helper.getResString(R.string.common_word_ok), Helper.getDialogDismissListener(dialog));
                dialog.show();
            }
        }

        /**
         * Called by a.a.a.MA if doInBackground() (a.a.a.MA#b()) returned a non-empty String,
         * ergo, an error occurred.
         */
        @Override // a.a.a.MA
        public void a(String str) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            // Dismiss the ProgressDialog
            i();
            ExportProjectActivity.this.b(str);
            layout_apk_path.setVisibility(View.GONE);
            if (loading_sign_apk.h()) {
                loading_sign_apk.e();
            }
            loading_sign_apk.setVisibility(View.GONE);
            btn_sign_apk.setVisibility(View.VISIBLE);
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
            if (buildingAppBundle && !isResultJarSigningEnabled()) {
                return oldFormatFilename.replace(".aab", ".unsigned.aab");
            } else if (!buildingAppBundle && !isResultJarSigningEnabled()) {
                return oldFormatFilename.replace("_release", "_release.unsigned");
            } else {
                return oldFormatFilename;
            }
        }

        public void publicPublishProgress(String... values) {
            publishProgress(values);
        }
    }
}
