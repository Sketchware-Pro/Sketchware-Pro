package com.besome.sketch;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.besome.sketch.acc.GoogleSignActivity;
import com.besome.sketch.language.LanguageActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import a.a.a.BB;
import a.a.a.DB;
import a.a.a.GB;
import a.a.a.MA;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nd;
import a.a.a.oB;
import a.a.a.rB;
import a.a.a.sB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import a.a.a.zd;
import mod.hey.studios.util.Helper;

public class InitActivity extends BaseAppCompatActivity {

    private final HashMap<String, String> u = new HashMap<>();
    private DB sharedPreferenceP1;
    private DB sharedPreferenceP16;
    private String loginId;
    private String accessToken;
    private long expireTime;
    private boolean q;
    private boolean r;
    private String t;

    private void continueToMainActivity() {
        sharedPreferenceP1.a("P1I0", Integer.valueOf(GB.d(getApplicationContext())));
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (t != null) {
            intent.putExtra("auto_run_activity", t);
        }
        if (!u.isEmpty()) {
            for (String key : u.keySet()) {
                intent.putExtra(key, u.get(key));
            }
        }
        startActivity(intent);
        finish();
    }

    private boolean m() {
        return false;
    }

    private void onLoginFail() {
        i.q();
        bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), Resources.string.account_error_failed_login), 0).show();
        z();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == -1) {
                loginId = data.getStringExtra("sns_id");
                accessToken = data.getStringExtra("access_token");
                expireTime = data.getLongExtra("expire_time", 0);
                q = true;
                new LoginAsyncTask(getApplicationContext()).execute();
            } else {
                i.q();
                z();
            }
        } else if (requestCode == 190) {
            if (resultCode != -1) {
                showUpdateAvailableDialog();
            }
        } else if (requestCode == 221) {
            finish();
        } else if (requestCode == 507) {
            new a(getApplicationContext()).execute();
        } else if (requestCode != 9501) {
            z();
        } else if (n()) {
            r();
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            finish();
            return;
        }

        setContentView(Resources.layout.init);
        try {
            Uri data = getIntent().getData();
            for (String str : data.getQueryParameterNames()) {
                u.put(str, data.getQueryParameter(str));
            }
            if (data.getLastPathSegment().equals("deeplink.jsp")) {
                t = data.getQueryParameter("activity");
            }
        } catch (Exception ignored) {
        }
        sharedPreferenceP1 = new DB(getApplicationContext(), "P1");
        sharedPreferenceP16 = new DB(getApplicationContext(), "P16");
        ImageView img_bi = findViewById(Resources.id.img_bi);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(ObjectAnimator.ofFloat(img_bi, View.SCALE_X, 0.2f, 1.0f))
                .with(ObjectAnimator.ofFloat(img_bi, View.SCALE_Y, 0.2f, 1.0f));
        animatorSet.setDuration(500L)
                .setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
        // Removed binding of no-op AnimatorListenerAdapter to animatorSet
        new BB().a(this);
        new a(getApplicationContext()).execute();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            Uri data = intent.getData();
            for (String str : data.getQueryParameterNames()) {
                u.put(str, data.getQueryParameter(str));
            }
            if ("deeplink.jsp".equals(data.getLastPathSegment())) {
                t = data.getQueryParameter("activity");
            }
            z();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // a.a.a.nd.a, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults.length > 0 && grantResults[0] == 0 && grantResults[1] == 0) {
                    r();
                } else {
                    showStorageDialog1();
                    return;
                }
            }
        }
    }

    private void showUpdateAvailableDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.update_available_title));
        dialog.a(Resources.drawable.color_new_96);
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.update_available_description));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_update), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.besome.sketch&referrer=utm_source%3Din_sketchware%26utm_medium%3Dcheck_update"));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 190);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel), v -> {
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void r() {
        s();
        oB oBVar = new oB();
        try {
            File file = new File(wq.m());
            if (oBVar.a(getApplicationContext(), "localization/strings.xml") != (file.exists() ? file.length() : 0)) {
                oBVar.a(file);
                oBVar.a(getApplicationContext(), "localization/strings.xml", wq.m());
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        if (xB.b().b(getApplicationContext())) {
            bB.a(getApplicationContext(),
                    xB.b().a(getApplicationContext(), Resources.string.message_strings_xml_loaded),
                    0, 80, 0.0f, 128.0f).show();
        }
        z();
    }

    private void s() {
        lC.d();
    }

    private void showAccountSuspendedDialog() {
        aB dialog = new aB(this);
        dialog.a(Resources.drawable.color_ban_96);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.account_dialog_suspended_title));
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.account_dialog_suspended_description, i.e()));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> {
            if (!mB.a()) {
                dialog.dismiss();
                i.q();
                sB.a(InitActivity.this, true);
            }
        });
        dialog.show();
    }

    private void showLanguageSettingsDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.main_drawer_title_language_settings));
        dialog.a(Resources.drawable.language_translate_96);
        dialog.setCancelable(false);
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.init_language_setting_dialog_message));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> toLanguageActivity());
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel), v -> {
            // Let's say we've shown the Video Guide already
            sharedPreferenceP1.a("P1I17", (Object) false);
            continueToMainActivity();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showStorageDialog() {
        if (!this.r) {
            h();
            aB dialog = new aB(this);
            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_title_storage));
            dialog.a(Resources.drawable.break_warning_96_red);
            dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_storage));
            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> {
                if (!mB.a()) {
                    nd.a(InitActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 9501);
                    dialog.dismiss();
                }
            });
            dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                    Helper.getDialogDismissListener(dialog));
            dialog.setOnDismissListener(dialog1 -> r = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            r = true;
        }
    }

    private void showStorageDialog1() {
        if (!r) {
            h();
            aB dialog = new aB(this);
            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_title_storage));
            dialog.a(Resources.drawable.break_warning_96_red);
            dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_storage1));
            dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_settings), v -> {
                if (!mB.a()) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 9501);
                    dialog.dismiss();
                }
            });
            dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                    Helper.getDialogDismissListener(dialog));
            dialog.setOnDismissListener(dialog1 -> r = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            r = true;
        }
    }

    private void toLanguageActivity() {
        startActivity(new Intent(getApplicationContext(), LanguageActivity.class));
    }

    private void z() {
        String defaultDisplayLanguage = Locale.getDefault().getDisplayLanguage();
        /* Check if we've shown the Video Guide already */
        if (sharedPreferenceP1.b("P1I17")) {
            continueToMainActivity();
        } else if (Locale.KOREAN.getDisplayLanguage().equals(defaultDisplayLanguage)
                || Locale.ENGLISH.getDisplayLanguage().equals(defaultDisplayLanguage)) {
            continueToMainActivity();
        } else if (!j()) {
            showLanguageSettingsDialog();
        } else if (new oB().e(wq.l())) {
            /* /Internal storage/sketchware/localization/strings.xml exists, let's skip language setup */
            continueToMainActivity();
        } else {
            showLanguageSettingsDialog();
        }
    }

    private void loginWithGoogleAccount() {
        if (!i.n()) {
            onLoginFail();
        } else if (zd.a(this, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), GoogleSignActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("google_account", i.e());
            startActivityForResult(intent, 101);
        } else {
            onLoginFail();
        }
    }

    private boolean n() {
        boolean hasStorageAccess = super.j();
        if (!hasStorageAccess) {
            int p1I0Value = sharedPreferenceP1.a("P1I0", 0);
            if (p1I0Value <= 0 || p1I0Value >= 71) {
                if (i.a()) {
                    loginWithGoogleAccount();
                } else {
                    z();
                }
            } else if (nd.a((Activity) this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showStorageDialog();
            } else {
                showStorageDialog1();
            }
        }
        return hasStorageAccess;
    }

    private class LoginAsyncTask extends MA {

        private HashMap<String, Object> loginReturnMap;
        private String d;

        public LoginAsyncTask(Context context) {
            super(context);
            InitActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (loginReturnMap == null) {
                /* Clean up stored metadata about account */
                i.q();
            }
            if (i.l()) {
                showAccountSuspendedDialog();
            } else {
                z();
            }
        }

        @Override // a.a.a.MA
        public void b() {
            rB rBVar = new rB();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("login_id", loginId);
            hashMap.put("is_sns_user", "Y");
            hashMap.put("sns_kind", "google");
            hashMap.put("access_token", accessToken);
            loginReturnMap = rBVar.b(hashMap);
            if (loginReturnMap != null) {
                i.a(loginReturnMap);
                if (!i.l()) {
                    if (!i.b().equals(GB.b(getApplicationContext()))) {
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put("login_id", loginId);
                        hashMap2.put("session_id", yB.c(loginReturnMap, "session_id"));
                        hashMap2.put("device_id", GB.b(getApplicationContext()));
                        rBVar.Rb(hashMap2);
                    }
                    String p16I1Value = sharedPreferenceP16.f("P16I1");
                    if (p16I1Value.isEmpty() && !yB.c(loginReturnMap, "gcm_id").equals(p16I1Value)) {
                        hashMap.clear();
                        if (loginId == null) {
                            loginId = i.e();
                        }
                        hashMap.put("login_id", loginId);
                        hashMap.put("session_id", yB.c(loginReturnMap, "session_id"));
                        hashMap.put("gcm_id", p16I1Value);
                        d = rBVar.Sb(hashMap);
                    }
                }
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            i.q();
            z();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override
        protected void onPostExecute(String s) {
            b(s);
        }
    }

    class a extends MA {

        public boolean c = false;

        public a(Context context) {
            super(context);
            InitActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (!GB.h(getApplicationContext())) {
                z();
                i.q();
            } else if (c) {
                showUpdateAvailableDialog();
            } else if (n()) {
                r();
            }
        }

        @Override // a.a.a.MA
        public void b() {
            if (GB.h(getApplicationContext())) {
                c = m();
                if (sharedPreferenceP16.f("P16I1").isEmpty()) {
                    FirebaseInstanceId.c().a();
                }
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            z();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override
        protected void onPostExecute(String s) {
            b(s);
        }
    }
}
