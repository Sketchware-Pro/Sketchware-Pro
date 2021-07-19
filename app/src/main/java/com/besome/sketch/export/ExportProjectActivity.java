package com.besome.sketch.export;

import static mod.SketchwareUtil.getDip;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
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
import com.besome.sketch.beans.UploadFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.tools.ExportApkActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.net.MediaType;
import com.sketchware.remod.Resources;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Dp;
import a.a.a.KB;
import a.a.a.MA;
import a.a.a.QA;
import a.a.a.RA;
import a.a.a.SA;
import a.a.a.aB;
import a.a.a.bB;
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
import a.a.a.xB;
import a.a.a.xq;
import a.a.a.yB;
import a.a.a.yq;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.jbk.build.compiler.bundle.AppBundleCompiler;

public class ExportProjectActivity extends BaseAppCompatActivity {

    private final oB fileUtility = new oB();
    private TextView tv_apk_url_expire;
    private ImageView img_copy_apk_url;
    private Button btn_export_data;
    private LottieAnimationView loading_export_data;
    private LinearLayout layout_export_data;
    private TextView tv_data_url;
    private TextView tv_data_url_expire;
    private ImageView img_copy_data_url;
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
    /**
     * The {@code sc_id} of the project to export
     */
    private String sc_id;
    private HashMap<String, Object> sc_metadata = null;
    /**
     * The current project's metadata object
     */
    private yq project_metadata = null;
    /**
     * ClipboardManager instance, that's used to copy URLs to the clipboard
     */
    private ClipboardManager clipboardManager;
    private Button btn_sign_apk;
    private LottieAnimationView loading_sign_apk;
    private LinearLayout layout_apk_path;
    private TextView tv_apk_path;
    private LinearLayout layout_apk_url;
    private TextView tv_apk_url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.export_project);
        Toolbar toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_actionbar_title));
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
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        initializeOutputDirectories();
        initializeSignApkViews();
        initializeExportDataViews();
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

    public final void f(String str) {
        String valid_dt;
        layout_apk_path.setVisibility(View.VISIBLE);
        btn_sign_apk.setVisibility(View.GONE);
        layout_apk_url.setVisibility(View.GONE);
        if (loading_sign_apk.h()) {
            loading_sign_apk.e();
        }
        loading_sign_apk.setVisibility(View.GONE);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), Resources.string.sign_apk_title_export_apk_file), 0).show();
        tv_apk_path.setText(signed_apk_postfix + File.separator + str);
        if (j.h()) {
            valid_dt = "30 " + xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_remain_days);
        } else {
            valid_dt = "7 " + xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_remain_days);
        }
        tv_apk_url_expire.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_valid_dt) + " : " + valid_dt);
    }

    public final void l() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            hC hCVar = new hC(sc_id);
            kC kCVar = new kC(sc_id);
            eC eCVar = new eC(sc_id);
            iC iCVar = new iC(sc_id);
            hCVar.i();
            kCVar.s();
            eCVar.g();
            eCVar.e();
            iCVar.i();
            project_metadata.b(hCVar, eCVar, iCVar, true);
            project_metadata.a(getApplicationContext(), wq.e(xq.a(sc_id) ? "600" : sc_id));
            if (yB.a(lC.b(sc_id), "custom_icon")) {
                project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
            }
            project_metadata.a();
            kCVar.b(project_metadata.w + File.separator + "drawable-xhdpi");
            kCVar.c(project_metadata.w + File.separator + "raw");
            kCVar.a(project_metadata.A + File.separator + "fonts");
            project_metadata.f();
            arrayList.add(project_metadata.c);
            String str = yB.c(sc_metadata, "my_ws_name") + ".zip";
            project_metadata.J = wq.s() + File.separator + "export_src" + File.separator + str;
            if (fileUtility.e(project_metadata.J)) {
                fileUtility.c(project_metadata.J);
            }
            new KB().a(project_metadata.J, arrayList, project_metadata.K);
            project_metadata.e();
            e(str);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            layout_export_src.setVisibility(View.GONE);
            loading_export_src.setVisibility(View.GONE);
            btn_export_src.setVisibility(View.VISIBLE);
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
            imgAppBundle.setId(Resources.id.icon_src);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    (int) getDip(24),
                    (int) getDip(24)
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            imgAppBundle.setLayoutParams(params);
            imgAppBundle.setImageResource(Resources.drawable.open_box_48);
        }
        relativeLayout.addView(imgAppBundle);

        TextView titleExportAppBundle = new TextView(this);
        {
            RelativeLayout.LayoutParams titleExportAppBundleParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            titleExportAppBundleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            titleExportAppBundleParams.leftMargin = (int) getDip(8);
            titleExportAppBundleParams.addRule(RelativeLayout.RIGHT_OF, Resources.id.icon_src);
            titleExportAppBundle.setLayoutParams(titleExportAppBundleParams);
            titleExportAppBundle.setTextColor(ContextCompat.getColor(this,
                    Resources.color.scolor_black_01));
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
            var2.setImageResource(Resources.drawable.ic_folder_48dp);
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
            var3.setBackgroundResource(Resources.drawable.bg_round_light_grey);
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
            tvAppBundlePath.setTextColor(ContextCompat.getColor(this,
                    Resources.color.scolor_black_01));
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
                btnSendAppBundle.setBackgroundColor(ContextCompat.getColor(this,
                        Resources.color.scolor_green_normal));
                btnSendAppBundle.setHighlightColor(ContextCompat.getColor(this,
                        Resources.color.color_btn_green_highlight));
            }
            btnSendAppBundleContainer.addView(btnSendAppBundle);

            layoutExportAppBundle.addView(btnSendAppBundleContainer);
        }

        relativeLayout.addView(layoutExportAppBundle);

        ViewParent plannedParent = findViewById(Resources.id.icon_apk).getParent().getParent().getParent();
        if (plannedParent instanceof LinearLayout) {
            ((LinearLayout) plannedParent).addView(exportAppBundleRoot);
        }

        titleExportAppBundle.setText("Export Android App Bundle");
        btnExportAppBundle.setText("Export AAB");
        titleAppBundlePath.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_local_path));
        btnSendAppBundle.setText("Send AAB");
        layoutExportAppBundle.setVisibility(View.GONE);

        btnExportAppBundle.setOnClickListener(v -> {
            aB dialog = new aB(ExportProjectActivity.this);
            dialog.a(Resources.drawable.color_about_96);
            dialog.b("Instructions");
            dialog.a("Copy your keystore to /Internal storage/sketchware/keystore/release_key.jks " +
                    "and enter the alias's password.");

            LinearLayout layout_alias_and_password = new LinearLayout(ExportProjectActivity.this);
            layout_alias_and_password.setOrientation(LinearLayout.VERTICAL);
            layout_alias_and_password.setPadding(
                    (int) getDip(4),
                    0,
                    (int) getDip(4),
                    0
            );

            TextInputLayout til_alias = new TextInputLayout(ExportProjectActivity.this);

            EditText et_alias = new EditText(ExportProjectActivity.this);
            et_alias.setHint("Keystore alias");
            til_alias.addView(et_alias, 0, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout_alias_and_password.addView(til_alias);

            TextInputLayout til_password = new TextInputLayout(ExportProjectActivity.this);

            EditText et_password = new EditText(ExportProjectActivity.this);
            et_password.setHint("Alias password");
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            til_password.setPasswordVisibilityToggleEnabled(true);
            til_password.addView(et_password, 0, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout_alias_and_password.addView(til_password);

            dialog.a(layout_alias_and_password);

            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v1 -> {
                dialog.dismiss();
                btnExportAppBundle.setVisibility(View.GONE);
                layoutExportAppBundle.setVisibility(View.GONE);
                // loadingExportAppBundle.setVisibility(View.VISIBLE);
                // loadingExportAppBundle.j();

                BuildingAsyncTask task = new BuildingAsyncTask(getBaseContext());
                task.enableAppBundleBuild();
                task.configureResultJarSigning(
                        wq.j(),
                        et_password.getText().toString().toCharArray(),
                        et_alias.getText().toString(),
                        et_password.getText().toString().toCharArray(),
                        "SHA1WITHRSA"
                );
                task.execute();
            });
            dialog.show();
        });
    }

    /**
     * Initialize Project Data Export views
     */
    private void initializeExportDataViews() {
        TextView title_export_data = findViewById(Resources.id.title_export_data);
        btn_export_data = findViewById(Resources.id.btn_export_data);
        loading_export_data = findViewById(Resources.id.loading_export_data);
        layout_export_data = findViewById(Resources.id.layout_export_data);
        TextView title_data_url = findViewById(Resources.id.title_data_url);
        tv_data_url = findViewById(Resources.id.tv_data_url);
        tv_data_url_expire = findViewById(Resources.id.tv_data_url_expire);
        img_copy_data_url = findViewById(Resources.id.img_copy_data_url);
        title_export_data.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_export_data));
        btn_export_data.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_generate_url));
        title_data_url.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_download_url));
        loading_export_data.setVisibility(View.GONE);
        layout_export_data.setVisibility(View.GONE);
        btn_export_data.setOnClickListener(v -> {
            btn_export_data.setVisibility(View.GONE);
            layout_export_data.setVisibility(View.GONE);
            loading_export_data.setVisibility(View.VISIBLE);
            loading_export_data.j();
            new b(getBaseContext()).execute();
        });
    }

    /**
     * Initialize Export to Android Studio views
     */
    private void initializeExportSrcViews() {
        TextView title_export_src = findViewById(Resources.id.title_export_src);
        btn_export_src = findViewById(Resources.id.btn_export_src);
        loading_export_src = findViewById(Resources.id.loading_export_src);
        layout_export_src = findViewById(Resources.id.layout_export_src);
        TextView title_src_path = findViewById(Resources.id.title_src_path);
        tv_src_path = findViewById(Resources.id.tv_src_path);
        Button btn_send_src = findViewById(Resources.id.btn_send_src);
        title_export_src.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_export_src));
        btn_export_src.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_export_src));
        title_src_path.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_local_path));
        btn_send_src.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_send_src_zip));
        loading_export_src.setVisibility(View.GONE);
        layout_export_src.setVisibility(View.GONE);
        btn_export_src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_export_src.setVisibility(View.GONE);
                layout_export_src.setVisibility(View.GONE);
                loading_export_src.setVisibility(View.VISIBLE);
                loading_export_src.j();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        l();
                    }
                }.start();
            }
        });
        btn_send_src.setOnClickListener(v -> q());
    }

    /**
     * Initialize APK Export views
     */
    private void initializeSignApkViews() {
        TextView title_sign_apk = findViewById(Resources.id.title_sign_apk);
        btn_sign_apk = findViewById(Resources.id.btn_sign_apk);
        loading_sign_apk = findViewById(Resources.id.loading_sign_apk);
        layout_apk_path = findViewById(Resources.id.layout_apk_path);
        TextView title_apk_path = findViewById(Resources.id.title_apk_path);
        tv_apk_path = findViewById(Resources.id.tv_apk_path);
        Button btn_export_apk = findViewById(Resources.id.btn_export_apk);
        layout_apk_url = findViewById(Resources.id.layout_apk_url);
        TextView title_apk_url = findViewById(Resources.id.title_apk_url);
        tv_apk_url = findViewById(Resources.id.tv_apk_url);
        tv_apk_url_expire = findViewById(Resources.id.tv_apk_url_expire);
        img_copy_apk_url = findViewById(Resources.id.img_copy_apk_url);
        title_sign_apk.setText(xB.b().a(getApplicationContext(), 0x7f0e06b4));
        btn_sign_apk.setText(xB.b().a(getApplicationContext(), 0x7f0e06ac));
        title_apk_path.setText(xB.b().a(getApplicationContext(), 0x7f0e06b3));
        btn_export_apk.setText(xB.b().a(getApplicationContext(), 0x7f0e06aa));
        title_apk_url.setText(xB.b().a(getApplicationContext(), 0x7f0e06b0));
        loading_sign_apk.setVisibility(View.GONE);
        layout_apk_path.setVisibility(View.GONE);
        layout_apk_url.setVisibility(View.GONE);
        btn_sign_apk.setOnClickListener(v -> {
            aB dialog = new aB(ExportProjectActivity.this);
            dialog.a(Resources.drawable.color_about_96);
            dialog.b("Instructions");
            dialog.a("Copy your keystore to /Internal storage/sketchware/keystore/release_key.jks " +
                    "and enter the alias's password.");

            LinearLayout layout_alias_and_password = new LinearLayout(ExportProjectActivity.this);
            layout_alias_and_password.setOrientation(LinearLayout.VERTICAL);
            layout_alias_and_password.setPadding(
                    (int) getDip(4),
                    0,
                    (int) getDip(4),
                    0
            );

            TextInputLayout til_alias = new TextInputLayout(ExportProjectActivity.this);

            EditText et_alias = new EditText(ExportProjectActivity.this);
            et_alias.setHint("Keystore alias");
            til_alias.addView(et_alias, 0, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout_alias_and_password.addView(til_alias);

            TextInputLayout til_password = new TextInputLayout(ExportProjectActivity.this);

            EditText et_password = new EditText(ExportProjectActivity.this);
            et_password.setHint("Alias password");
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            til_password.setPasswordVisibilityToggleEnabled(true);
            til_password.addView(et_password, 0, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout_alias_and_password.addView(til_password);

            dialog.a(layout_alias_and_password);

            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v1 -> {
                dialog.dismiss();
                btn_sign_apk.setVisibility(View.GONE);
                layout_apk_path.setVisibility(View.GONE);
                layout_apk_url.setVisibility(View.GONE);
                loading_sign_apk.setVisibility(View.VISIBLE);
                loading_sign_apk.j();

                BuildingAsyncTask task = new BuildingAsyncTask(getBaseContext());
                task.configureResultJarSigning(
                        wq.j(),
                        et_password.getText().toString().toCharArray(),
                        et_alias.getText().toString(),
                        et_password.getText().toString().toCharArray(),
                        "SHA1WITHRSA"
                );
                task.execute();
            });
            dialog.show();
        });
        btn_export_apk.setOnClickListener(v -> r());
    }

    /**
     * Initialize output directories
     */
    private void initializeOutputDirectories() {
        signed_apk_postfix = File.separator + "sketchware" + File.separator + "signed_apk";
        export_src_postfix = File.separator + "sketchware" + File.separator + "export_src";
        /**
         * /sdcard/sketchware/signed_apk
         */
        String signed_apk_full_path = wq.s() + File.separator + "signed_apk";
        export_src_full_path = wq.s() + File.separator + "export_src";

        /* Check if they exist, if not, create them */
        fileUtility.f(signed_apk_full_path);
        fileUtility.f(export_src_full_path);
    }

    public final void q() {
        if (export_src_filename.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_SUBJECT, xB.b().a(getApplicationContext(), 0x7f0e06b9, export_src_filename));
            intent.putExtra(Intent.EXTRA_TEXT, xB.b().a(getApplicationContext(), 0x7f0e06b8, export_src_filename));
            String filePath = export_src_full_path + File.separator + export_src_filename;
            if (Build.VERSION.SDK_INT >= 24) {
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(filePath)));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(Intent.createChooser(intent, xB.b().a(getApplicationContext(), 0x7f0e06b7)));
        }
    }

    public final void r() {
        Intent intent = new Intent(getApplicationContext(), ExportApkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        startActivity(intent);
    }

    public final void c(String str) {
        layout_apk_url.setVisibility(View.VISIBLE);
        btn_sign_apk.setVisibility(View.GONE);
        if (loading_sign_apk.h()) {
            loading_sign_apk.e();
        }
        loading_sign_apk.setVisibility(View.GONE);
        tv_apk_url.setText("http://sketchware.io/download.jsp?id=" + str);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625645), 1).show();
        this.img_copy_apk_url.setOnClickListener(v -> {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Download APK URL", tv_apk_url.getText()));
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624934), 0).show();
        });
    }

    public final void d(String id) {
        String valid_dt;
        if (loading_export_data.h()) {
            loading_export_data.e();
        }
        loading_export_data.setVisibility(View.GONE);
        layout_export_data.setVisibility(View.VISIBLE);
        btn_export_data.setVisibility(View.GONE);
        tv_data_url.setText("http://sketchware.io/import.jsp?id=" + id);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 0x7f0e06ad), 1).show();
        if (j.h()) {
            valid_dt = "30 " + xB.b().a(getApplicationContext(), 0x7f0e06b5);
        } else {
            valid_dt = "7 " + xB.b().a(getApplicationContext(), 0x7f0e06b5);
        }
        tv_data_url_expire.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_valid_dt) + " : " + valid_dt);
        img_copy_data_url.setOnClickListener(v -> {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Download Data URL", tv_data_url.getText()));
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 0x7f0e03e6), 0).show();
        });
    }

    public final void e(String str) {
        export_src_filename = str;
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
        dialog.a(Resources.drawable.break_warning_96_red);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_error_an_error_occurred));

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
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> {
            if (!mB.a()) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class BuildingAsyncTask extends MA implements DialogInterface.OnCancelListener {

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
        private String signingKeystorePath = "";
        private char[] signingKeystorePassword = new char[0];
        private String signingAliasName = "";
        private char[] signingAliasPassword = new char[0];
        private String signingAlgorithm = "";

        public BuildingAsyncTask(Context context) {
            super(context);
            ExportProjectActivity.this.a((MA) this);
            ExportProjectActivity.this.a((DialogInterface.OnCancelListener) this);
            ExportProjectActivity.this.g.a(false);
        }

        public void a(String... strArr) {
            super.onProgressUpdate(strArr);
            ExportProjectActivity.this.a(strArr[0]);
        }

        @Override // a.a.a.MA
        public void b() {
            if (d) {
                cancel(true);
                return;
            }
            try {
                publishProgress(xB.b().a(getApplicationContext(), Resources.string.design_run_title_ready_to_build));
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
                project_metadata.c();
                project_metadata.d();
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
                kCVar.b(project_metadata.w + File.separator + "drawable-xhdpi");
                kCVar.c(project_metadata.w + File.separator + "raw");
                kCVar.a(project_metadata.A + File.separator + "fonts");
                project_metadata.b(hCVar, eCVar, iCVar, true);
                if (d) {
                    cancel(true);
                    return;
                }
                c = new Dp(a, project_metadata, buildingAppBundle);

                /* Check AAPT/AAPT2 */
                publishProgress("Extracting AAPT/AAPT2 binaries...");
                c.i();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Check built-in libraries */
                publishProgress("Extracting built-in libraries...");
                c.j();
                if (d) {
                    cancel(true);
                    return;
                }

                BuildSettings buildSettings = new BuildSettings(sc_id);
                boolean usingAapt2 = buildingAppBundle || buildSettings
                        .getValue(BuildSettings.SETTING_RESOURCE_PROCESSOR,
                                BuildSettings.SETTING_RESOURCE_PROCESSOR_AAPT
                        ).equals(BuildSettings.SETTING_RESOURCE_PROCESSOR_AAPT2);
                publishProgress(usingAapt2 ? "AAPT2 is running..." : "AAPT is running...");
                c.a();
                if (d) {
                    cancel(true);
                    return;
                }

                publishProgress("Java is compiling...");
                c.f();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Encrypt Strings in classes if enabled */
                StringfogHandler stringfogHandler = new StringfogHandler(project_metadata.b);
                stringfogHandler.start(null, c);
                if (d) {
                    cancel(true);
                    return;
                }

                /* Obfuscate classes if enabled */
                ProguardHandler proguardHandler = new ProguardHandler(project_metadata.b);
                proguardHandler.start(null, c);
                if (d) {
                    cancel(true);
                    return;
                }

                /* Create DEX file(s) */
                publishProgress(c.getDxRunningText());
                c.c();
                if (d) {
                    cancel(true);
                    return;
                }

                /* Merge DEX file(s) with libraries' dexes */
                publishProgress("Merging libraries' DEX files...");
                c.h();
                if (d) {
                    onCancelled();
                    return;
                }

                if (buildingAppBundle) {
                    AppBundleCompiler compiler = new AppBundleCompiler(c, null);
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

                    Security.addProvider(new BouncyCastleProvider());
                    CustomKeySigner.signZip(
                            new ZipSigner(),
                            signingKeystorePath,
                            signingKeystorePassword,
                            signingAliasName,
                            signingAliasPassword,
                            signingAlgorithm,
                            createdBundlePath,
                            signedAppBundleDirectoryPath
                                    + File.separator + Uri.fromFile(new File(createdBundlePath)).getLastPathSegment()
                    );
                } else {
                    publishProgress("Building APK...");
                    c.g();
                    if (d) {
                        cancel(true);
                        return;
                    }

                    publishProgress("Signing APK...");
                    c.b(new String(signingKeystorePassword), signingAliasName);
                }
            } catch (OutOfMemoryError error) {
                System.gc();
                Log.e("AppExporter", error.getMessage(), error);
                runOnUiThread(new ErrorRunOnUiThreadRunnable(error.getMessage()));
            } catch (Throwable throwable) {
                Log.e("AppExporter", throwable.getMessage(), throwable);
                runOnUiThread(new ErrorRunOnUiThreadRunnable(throwable.getMessage()));
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
            project_metadata.b();
            i();
            layout_apk_path.setVisibility(View.GONE);
            layout_apk_url.setVisibility(View.GONE);
            if (loading_sign_apk.h()) {
                loading_sign_apk.e();
            }
            loading_sign_apk.setVisibility(View.GONE);
            btn_sign_apk.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ void onProgressUpdate(String... strArr) {
            a(strArr);
        }

        @Override // a.a.a.MA
        public void a() {
            project_metadata.b();
            i();
            if (project_metadata.g()) {
                f(project_metadata.d + "_release.apk");
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            project_metadata.b();
            i();
            ExportProjectActivity.this.b(str);
            layout_apk_path.setVisibility(View.GONE);
            layout_apk_url.setVisibility(View.GONE);
            if (loading_sign_apk.h()) {
                loading_sign_apk.e();
            }
            loading_sign_apk.setVisibility(View.GONE);
            btn_sign_apk.setVisibility(View.VISIBLE);
        }

        public void enableAppBundleBuild() {
            buildingAppBundle = true;
        }

        public void configureResultJarSigning(String keystorePath, char[] keystorePassword, String aliasName, char[] aliasPassword, String signatureAlgorithm) {
            signingKeystorePath = keystorePath;
            signingKeystorePassword = keystorePassword;
            signingAliasName = aliasName;
            signingAliasPassword = aliasPassword;
            signingAlgorithm = signatureAlgorithm;
        }
    }

    /**
     * Class to generate an Export URL on Sketchware's servers?
     */
    class b extends MA {

        public String c = null;

        public b(Context context) {
            super(context);
            ExportProjectActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (this.c.equals("fail")) {
                btn_export_data.setVisibility(View.VISIBLE);
                if (loading_export_data.h()) {
                    loading_export_data.e();
                }
                loading_export_data.setVisibility(View.GONE);
                bB.b(a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
            } else if (c.equals("limit")) {
                btn_export_data.setVisibility(View.VISIBLE);
                if (loading_export_data.h()) {
                    loading_export_data.e();
                }
                loading_export_data.setVisibility(View.GONE);
                bB.b(a, xB.b().a(getApplicationContext(), 2131625646), 1).show();
            } else {
                ExportProjectActivity.this.d(c);
            }
        }

        @Override // a.a.a.MA
        public void b() {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("login_id", ExportProjectActivity.this.i.e());
            hashMap.put("session_id", ExportProjectActivity.this.i.f());
            hashMap.put("has_purchase", ExportProjectActivity.this.j.h() ? "Y" : "N");
            hashMap.put("pkg_name", ExportProjectActivity.this.project_metadata.e);
            hashMap.put("app_name", ExportProjectActivity.this.project_metadata.f);
            this.c = new rB().X(hashMap);
            if (!(this.c.equals("fail") || this.c.equals("limit"))) {
                String str = this.c;
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("login_id", ExportProjectActivity.this.i.e());
                hashMap2.put("session_id", ExportProjectActivity.this.i.f());
                hashMap2.put("url_id", str);
                KB kb = new KB();
                ArrayList<UploadFileBean> arrayList = new ArrayList<>();
                arrayList.add(new UploadFileBean(MediaType.PLAIN_TEXT_UTF_8, "project", wq.c(ExportProjectActivity.this.sc_id) + File.separator + "project"));
                arrayList.add(new UploadFileBean(MediaType.PNG, "icon.png", wq.e() + File.separator + ExportProjectActivity.this.sc_id + File.separator + "icon.png"));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "data.zip", wq.b(ExportProjectActivity.this.sc_id)));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_image.zip", wq.g() + File.separator + ExportProjectActivity.this.sc_id));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_sound.zip", wq.t() + File.separator + ExportProjectActivity.this.sc_id));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_font.zip", wq.d() + File.separator + ExportProjectActivity.this.sc_id));
                for (int i = 0; i < arrayList.size(); i++) {
                    UploadFileBean uploadFileBean = arrayList.get(i);
                    byte[] bArr = null;
                    if (uploadFileBean.contentType.equals(MediaType.PLAIN_TEXT_UTF_8.toString())) {
                        bArr = ExportProjectActivity.this.fileUtility.h(uploadFileBean.path);
                    } else if (uploadFileBean.contentType.equals(MediaType.PNG.toString())) {
                        bArr = ExportProjectActivity.this.fileUtility.h(uploadFileBean.path);
                    } else if (uploadFileBean.contentType.equals(MediaType.ZIP.toString())) {
                        bArr = kb.a(uploadFileBean.path);
                    }
                    if (bArr == null) {
                        bArr = new byte[0];
                    }
                    this.c = new RA(new SA.a() {
                        @Override
                        public void a(long l, long l1) {
                            // Bytecode said original method had 5 registers, probably
                            // lost calls, as this is an Upload-AsyncTask and probably wouldn't work
                            // anyway without Sketchware's original signature?

                            // Never mind, the original Sketchware APK from the Play Store (version 3.10.0)
                            // has the same amount of registers too
                        }
                    }).c(hashMap2, uploadFileBean, bArr);
                    if (!this.c.equals("success")) {
                        this.c = "fail";
                        return;
                    }
                }
                this.c = str;
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            btn_export_data.setVisibility(View.VISIBLE);
            if (loading_export_data.h()) {
                loading_export_data.e();
            }
            loading_export_data.setVisibility(View.GONE);
            bB.b(a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    private class ErrorRunOnUiThreadRunnable implements Runnable {

        private final String message;

        ErrorRunOnUiThreadRunnable(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            b(message);
        }
    }
}