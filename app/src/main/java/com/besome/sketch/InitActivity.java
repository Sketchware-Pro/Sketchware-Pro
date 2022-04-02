package com.besome.sketch;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.besome.sketch.language.LanguageActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.Locale;

import a.a.a.BB;
import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nd;
import a.a.a.oB;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class InitActivity extends BaseAppCompatActivity {

    private DB sharedPreferenceP1;
    private boolean r;

    private void continueToMainActivity() {
        sharedPreferenceP1.a("P1I0", Integer.valueOf(GB.d(getApplicationContext())));
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 190) {
            if (resultCode != -1) {
                showUpdateAvailableDialog();
            }
        } else if (requestCode == 221) {
            finish();
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
        sharedPreferenceP1 = new DB(getApplicationContext(), "P1");
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

    private boolean n() {
        boolean hasStorageAccess = super.j();
        if (!hasStorageAccess) {
            int p1I0Value = sharedPreferenceP1.a("P1I0", 0);
            if (p1I0Value <= 0 || p1I0Value >= 71) {
                z();
            } else if (nd.a((Activity) this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showStorageDialog();
            } else {
                showStorageDialog1();
            }
        }
        return hasStorageAccess;
    }

}
