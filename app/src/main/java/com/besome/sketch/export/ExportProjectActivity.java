package com.besome.sketch.export;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.besome.sketch.beans.UploadFileBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.tools.ExportApkActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.net.MediaType;
import com.sketchware.remod.Resources;

import java.io.File;
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
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;

public class ExportProjectActivity extends BaseAppCompatActivity {

    public TextView A;
    public ImageView B;
    public TextView C;
    public Button D;
    public LottieAnimationView E;
    public LinearLayout F;
    public TextView G;
    public TextView H;
    public TextView I;
    public ImageView J;
    public TextView K;
    public Button L;
    public LottieAnimationView M;
    public LinearLayout N;
    public TextView O;
    public TextView P;
    public Button Q;
    /**
     * /sketchware/signed_apk
     */
    public String R;
    /**
     * /sdcard/sketchware/signed_apk
     */
    public String S;
    public String T;
    /**
     * /sketchware/export_src
     */
    public String U;
    /**
     * /sdcard/sketchware/export_src
     */
    public String V;
    public String W;
    public Toolbar k;

    /**
     * The {@code sc_id} of the project to export
     */
    public String l;
    public HashMap<String, Object> m = null;

    /**
     * The current project's metadata object
     */
    public yq n = null;

    /**
     * ClipboardManager instance, that's used to copy URLs to the clipboard
     */
    public ClipboardManager o;
    public oB p = new oB();
    public TextView q;
    public Button r;
    public LottieAnimationView s;
    public LinearLayout t;
    public TextView u;
    public TextView v;
    public Button w;
    public LinearLayout x;
    public TextView y;
    public TextView z;

    private String keystoreAliasName;
    private String keystoreAliasPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.export_project);
        k = findViewById(Resources.id.toolbar);
        a(k);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_actionbar_title));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        if (savedInstanceState == null) {
            l = getIntent().getStringExtra("sc_id");
        } else {
            l = savedInstanceState.getString("sc_id");
        }
        m = lC.b(l);
        n = new yq(getApplicationContext(), wq.d(l), m);
        o = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        p();
        o();
        m();
        n();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (M.h()) {
            M.e();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", l);
        super.onSaveInstanceState(savedInstanceState);
    }

    public final void f(String str) {
        String valid_dt;
        T = str;
        t.setVisibility(View.VISIBLE);
        r.setVisibility(View.GONE);
        x.setVisibility(View.GONE);
        if (s.h()) {
            s.e();
        }
        s.setVisibility(View.GONE);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), Resources.string.sign_apk_title_export_apk_file), 0).show();
        v.setText(R + File.separator + T);
        if (j.h()) {
            valid_dt = "30 " + xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_remain_days);
        } else {
            valid_dt = "7 " + xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_remain_days);
        }
        A.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_valid_dt) + " : " + valid_dt);
    }

    public final void l() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            hC hCVar = new hC(l);
            kC kCVar = new kC(l);
            eC eCVar = new eC(l);
            iC iCVar = new iC(l);
            hCVar.i();
            kCVar.s();
            eCVar.g();
            eCVar.e();
            iCVar.i();
            n.b(hCVar, eCVar, iCVar, true);
            n.a(getApplicationContext(), wq.e(xq.a(l) ? "600" : l));
            if (yB.a(lC.b(l), "custom_icon")) {
                n.a(wq.e() + File.separator + l + File.separator + "icon.png");
            }
            n.a();
            kCVar.b(n.w + File.separator + "drawable-xhdpi");
            kCVar.c(n.w + File.separator + "raw");
            kCVar.a(n.A + File.separator + "fonts");
            n.f();
            arrayList.add(n.c);
            String str = yB.c(m, "my_ws_name") + ".zip";
            n.J = wq.s() + File.separator + "export_src" + File.separator + str;
            if (p.e(n.J)) {
                p.c(n.J);
            }
            new KB().a(n.J, arrayList, n.K);
            n.e();
            e(str);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            N.setVisibility(View.GONE);
            M.setVisibility(View.GONE);
            L.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Initialize Project Data Export views
     */
    public final void m() {
        C = findViewById(Resources.id.title_export_data);
        D = findViewById(Resources.id.btn_export_data);
        E = findViewById(Resources.id.loading_export_data);
        F = findViewById(Resources.id.layout_export_data);
        G = findViewById(Resources.id.title_data_url);
        H = findViewById(Resources.id.tv_data_url);
        I = findViewById(Resources.id.tv_data_url_expire);
        J = findViewById(Resources.id.img_copy_data_url);
        C.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_export_data));
        D.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_generate_url));
        G.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_download_url));
        E.setVisibility(View.GONE);
        F.setVisibility(View.GONE);
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                D.setVisibility(View.GONE);
                F.setVisibility(View.GONE);
                E.setVisibility(View.VISIBLE);
                E.j();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new b(getBaseContext()).execute();
                    }
                }, 500);
            }
        });
    }

    /**
     * Initialize Export to Android Studio views
     */
    public final void n() {
        K = findViewById(Resources.id.title_export_src);
        L = findViewById(Resources.id.btn_export_src);
        M = findViewById(Resources.id.loading_export_src);
        N = findViewById(Resources.id.layout_export_src);
        O = findViewById(Resources.id.title_src_path);
        P = findViewById(Resources.id.tv_src_path);
        Q = findViewById(Resources.id.btn_send_src);
        K.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_export_src));
        L.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_export_src));
        O.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_title_local_path));
        Q.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_button_send_src_zip));
        M.setVisibility(View.GONE);
        N.setVisibility(View.GONE);
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.setVisibility(View.GONE);
                N.setVisibility(View.GONE);
                M.setVisibility(View.VISIBLE);
                M.j();
                l();
            }
        });
        Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q();
            }
        });
    }

    /**
     * Initialize APK Export views
     */
    public final void o() {
        q = findViewById(Resources.id.title_sign_apk);
        r = findViewById(Resources.id.btn_sign_apk);
        s = findViewById(Resources.id.loading_sign_apk);
        t = findViewById(Resources.id.layout_apk_path);
        u = findViewById(Resources.id.title_apk_path);
        v = findViewById(Resources.id.tv_apk_path);
        w = findViewById(Resources.id.btn_export_apk);
        x = findViewById(Resources.id.layout_apk_url);
        y = findViewById(Resources.id.title_apk_url);
        z = findViewById(Resources.id.tv_apk_url);
        A = findViewById(Resources.id.tv_apk_url_expire);
        B = findViewById(Resources.id.img_copy_apk_url);
        q.setText(xB.b().a(getApplicationContext(), 0x7f0e06b4));
        r.setText(xB.b().a(getApplicationContext(), 0x7f0e06ac));
        u.setText(xB.b().a(getApplicationContext(), 0x7f0e06b3));
        w.setText(xB.b().a(getApplicationContext(), 0x7f0e06aa));
        y.setText(xB.b().a(getApplicationContext(), 0x7f0e06b0));
        s.setVisibility(View.GONE);
        t.setVisibility(View.GONE);
        x.setVisibility(View.GONE);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aB dialog = new aB(ExportProjectActivity.this);
                dialog.a(Resources.drawable.color_about_96);
                dialog.b("Instructions");
                dialog.a("Copy your keystore to /Internal storage/sketchware/keystore/release_key.jks " +
                        "and enter the alias's password.");

                LinearLayout layout_alias_and_password = new LinearLayout(ExportProjectActivity.this);
                layout_alias_and_password.setOrientation(LinearLayout.VERTICAL);

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

                dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        keystoreAliasName = et_alias.getText().toString();
                        keystoreAliasPassword = et_password.getText().toString();
                        dialog.dismiss();
                        r.setVisibility(View.GONE);
                        t.setVisibility(View.GONE);
                        x.setVisibility(View.GONE);
                        s.setVisibility(View.VISIBLE);
                        s.j();
                        new c(getBaseContext()).execute();
                    }
                });
                dialog.show();
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r();
            }
        });
    }

    /**
     * Initialize output directories
     */
    public final void p() {
        R = File.separator + "sketchware" + File.separator + "signed_apk";
        U = File.separator + "sketchware" + File.separator + "export_src";
        S = wq.s() + File.separator + "signed_apk";
        V = wq.s() + File.separator + "export_src";

        /* Check if they exist, if not, create them */
        p.f(S);
        p.f(V);
    }

    public final void q() {
        if (W.length() > 0) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_SUBJECT, xB.b().a(getApplicationContext(), 0x7f0e06b9, W));
            intent.putExtra(Intent.EXTRA_TEXT, xB.b().a(getApplicationContext(), 0x7f0e06b8, W));
            String filePath = V + File.separator + W;
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
        intent.putExtra("sc_id", l);
        startActivity(intent);
    }

    public final void c(String str) {
        x.setVisibility(View.VISIBLE);
        r.setVisibility(View.GONE);
        if (s.h()) {
            s.e();
        }
        s.setVisibility(View.GONE);
        z.setText("http://sketchware.io/download.jsp?id=" + str);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625645), 1).show();
        this.B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                o.setPrimaryClip(ClipData.newPlainText("Download APK URL", z.getText()));
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624934), 0).show();
            }
        });
    }

    public final void d(String id) {
        String valid_dt;
        if (E.h()) {
            E.e();
        }
        E.setVisibility(View.GONE);
        F.setVisibility(View.VISIBLE);
        D.setVisibility(View.GONE);
        H.setText("http://sketchware.io/import.jsp?id=" + id);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625645), 1).show();
        if (j.h()) {
            valid_dt = "30 " + xB.b().a(getApplicationContext(), 2131625653);
        } else {
            valid_dt = "7 " + xB.b().a(getApplicationContext(), 2131625653);
        }
        I.setText(xB.b().a(getApplicationContext(), Resources.string.myprojects_export_project_word_valid_dt) + " : " + valid_dt);
        J.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                o.setPrimaryClip(ClipData.newPlainText("Download Data URL", H.getText()));
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624934), 0).show();
            }
        });
    }

    public final void e(String str) {
        W = str;
        M.e();
        M.setVisibility(View.GONE);
        N.setVisibility(View.VISIBLE);
        P.setText(U + File.separator + W);
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
        dialog.a(errorMessage);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    class c extends MA implements DialogInterface.OnCancelListener {

        public Dp c;
        /**
         * Boolean indicating if the user has cancelled building
         */
        public boolean d = false;
        public QA e = new QA();
        public rB f = new rB();
        public iI g = new iI();
        public String h = null;

        public c(Context context) {
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
                hC hCVar = new hC(l);
                kC kCVar = new kC(l);
                eC eCVar = new eC(l);
                iC iCVar = new iC(l);
                hCVar.i();
                kCVar.s();
                eCVar.g();
                eCVar.e();
                iCVar.i();
                if (d) {
                    cancel(true);
                    return;
                }
                n.c();
                n.d();
                n.c(a);
                if (d) {
                    cancel(true);
                    return;
                }
                n.a(a, wq.e("600"));
                if (d) {
                    cancel(true);
                    return;
                }
                if (yB.a(lC.b(l), "custom_icon")) {
                    n.a(wq.e() + File.separator + l + File.separator + "icon.png");
                }
                n.a();
                kCVar.b(n.w + File.separator + "drawable-xhdpi");
                kCVar.c(n.w + File.separator + "raw");
                kCVar.a(n.A + File.separator + "fonts");
                n.b(hCVar, eCVar, iCVar, true);
                if (d) {
                    cancel(true);
                    return;
                }
                c = new Dp(a, n);

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

                BuildSettings buildSettings = new BuildSettings(l);
                boolean usingAapt2 = buildSettings
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
                StringfogHandler stringfogHandler = new StringfogHandler(n.b);
                stringfogHandler.start(null, c);
                if (d) {
                    cancel(true);
                    return;
                }

                /* Obfuscate classes if enabled */
                ProguardHandler proguardHandler = new ProguardHandler(n.b);
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

                publishProgress("Building APK...");
                c.g();
                if (d) {
                    cancel(true);
                    return;
                }

                publishProgress("Signing APK...");
                c.b(keystoreAliasPassword, keystoreAliasName);
                publishProgress("Release Apk ready.");
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
            n.b();
            i();
            t.setVisibility(View.GONE);
            x.setVisibility(View.GONE);
            if (s.h()) {
                s.e();
            }
            s.setVisibility(View.GONE);
            r.setVisibility(View.VISIBLE);
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
            n.b();
            i();
            if (n.g()) {
                f(n.d + "_release.apk");
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            n.b();
            i();
            ExportProjectActivity.this.b(str);
            t.setVisibility(View.GONE);
            x.setVisibility(View.GONE);
            if (s.h()) {
                s.e();
            }
            s.setVisibility(View.GONE);
            r.setVisibility(View.VISIBLE);
        }
    }

    class a extends MA {

        public String c = null;

        public a(Context context) {
            super(context);
            ExportProjectActivity.this.a((MA) this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (c.equals("fail") || c.startsWith("<!")) {
                w.setVisibility(View.VISIBLE);
                if (s.h()) {
                    s.e();
                }
                s.setVisibility(View.GONE);
                bB.b(a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
            } else if (c.equals("limit")) {
                w.setVisibility(View.VISIBLE);
                if (s.h()) {
                    s.e();
                }
                s.setVisibility(View.GONE);
                bB.b(a, xB.b().a(getApplicationContext(), 2131625646), 1).show();
            } else {
                c(this.c);
            }
        }

        @Override // a.a.a.MA
        public void b() {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("login_id", i.e());
            hashMap.put("session_id", i.f());
            hashMap.put("has_purchase", j.h() ? "Y" : "N");
            hashMap.put("pkg_name", n.e);
            this.c = new rB().a(hashMap, n.d + ".apk", p.h(S + File.separator + T));
        }

        @Override // a.a.a.MA
        public void a(String str) {
            w.setVisibility(View.VISIBLE);
            if (s.h()) {
                s.e();
            }
            s.setVisibility(View.GONE);
            bB.b(a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    /**
     * Class to generate a Export URL on Sketchware's servers?
     */
    class b extends MA {

        public String c = null;

        public b(Context context) {
            super(context);
            ExportProjectActivity.this.a((MA) this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (this.c.equals("fail")) {
                ExportProjectActivity.this.D.setVisibility(View.VISIBLE);
                if (ExportProjectActivity.this.E.h()) {
                    ExportProjectActivity.this.E.e();
                }
                ExportProjectActivity.this.E.setVisibility(View.GONE);
                bB.b(this.a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
            } else if (this.c.equals("limit")) {
                ExportProjectActivity.this.D.setVisibility(View.VISIBLE);
                if (ExportProjectActivity.this.E.h()) {
                    ExportProjectActivity.this.E.e();
                }
                ExportProjectActivity.this.E.setVisibility(View.GONE);
                bB.b(this.a, xB.b().a(getApplicationContext(), 2131625646), 1).show();
            } else {
                ExportProjectActivity.this.d(this.c);
            }
        }

        @Override // a.a.a.MA
        public void b() {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("login_id", ExportProjectActivity.this.i.e());
            hashMap.put("session_id", ExportProjectActivity.this.i.f());
            hashMap.put("has_purchase", ExportProjectActivity.this.j.h() ? "Y" : "N");
            hashMap.put("pkg_name", ExportProjectActivity.this.n.e);
            hashMap.put("app_name", ExportProjectActivity.this.n.f);
            this.c = new rB().X(hashMap);
            if (!(this.c.equals("fail") || this.c.equals("limit"))) {
                String str = this.c;
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put("login_id", ExportProjectActivity.this.i.e());
                hashMap2.put("session_id", ExportProjectActivity.this.i.f());
                hashMap2.put("url_id", str);
                KB kb = new KB();
                ArrayList<UploadFileBean> arrayList = new ArrayList<>();
                arrayList.add(new UploadFileBean(MediaType.PLAIN_TEXT_UTF_8, "project", wq.c(ExportProjectActivity.this.l) + File.separator + "project"));
                arrayList.add(new UploadFileBean(MediaType.PNG, "icon.png", wq.e() + File.separator + ExportProjectActivity.this.l + File.separator + "icon.png"));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "data.zip", wq.b(ExportProjectActivity.this.l)));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_image.zip", wq.g() + File.separator + ExportProjectActivity.this.l));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_sound.zip", wq.t() + File.separator + ExportProjectActivity.this.l));
                arrayList.add(new UploadFileBean(MediaType.ZIP, "res_font.zip", wq.d() + File.separator + ExportProjectActivity.this.l));
                for (int i = 0; i < arrayList.size(); i++) {
                    UploadFileBean uploadFileBean = (UploadFileBean) arrayList.get(i);
                    byte[] bArr = null;
                    if (uploadFileBean.contentType.equals(MediaType.PLAIN_TEXT_UTF_8.toString())) {
                        bArr = ExportProjectActivity.this.p.h(uploadFileBean.path);
                    } else if (uploadFileBean.contentType.equals(MediaType.PNG.toString())) {
                        bArr = ExportProjectActivity.this.p.h(uploadFileBean.path);
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
            ExportProjectActivity.this.D.setVisibility(View.VISIBLE);
            if (ExportProjectActivity.this.E.h()) {
                ExportProjectActivity.this.E.e();
            }
            ExportProjectActivity.this.E.setVisibility(View.GONE);
            bB.b(this.a, xB.b().a(getApplicationContext(), 2131625647), 0).show();
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