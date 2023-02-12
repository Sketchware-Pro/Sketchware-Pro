package com.besome.sketch.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import a.a.a.RB;
import a.a.a.SB;
import a.a.a.VB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iI;
import a.a.a.mB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;

public class NewKeyStoreActivity extends BaseAppCompatActivity implements OnClickListener {

    public RB A, B, C, D, y, z;
    public iI E;
    public int k = 25;
    public Toolbar l;
    public EditText m;
    public EditText n;
    public EditText o;
    public EditText p;
    public EditText q;
    public EditText r;
    public EditText s;
    public EditText t;
    public EditText u;
    public VB v;
    public SB w, x;

    private void showDoneDialog(boolean success, String password) {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_certificate));
        if (success) {
            dialog.a(R.drawable.certificate_96_blue);
            dialog.a(Helper.getResString(R.string.myprojects_sign_apk_dialog_complete_create_certificate));
        } else {
            dialog.a(R.drawable.error_96_yellow);
            dialog.a(Helper.getResString(R.string.myprojects_sign_apk_error_failed_create_new_certificate));
        }

        dialog.b(Helper.getResString(R.string.common_word_close), v -> {
            if (!mB.a()) {
                dialog.dismiss();
                if (success) {
                    Intent intent = new Intent();
                    intent.putExtra("pwd", password);
                    setResult(-1, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
        dialog.show();
    }

    private void save() {
        if (w.b() && x.b()) {
            String text = n.getText().toString();
            if (!text.equals(o.getText().toString())) {
                bB.b(getApplicationContext(), Helper.getResString(R.string.myprojects_sign_apk_incorrect_password), 0).show();
                n.setText("");
                o.setText("");
                return;
            }

            if (!v.b()) return;

            if (!y.b()) return;

            if (!z.b()) return;

            if (!A.b()) return;

            if (!B.b()) return;

            if (!C.b()) return;

            if (!D.b()) return;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CN=");
            stringBuilder.append(p.getText().toString());
            stringBuilder.append("OU=");
            stringBuilder.append(q.getText().toString());
            stringBuilder.append("O=");
            stringBuilder.append(r.getText().toString());
            stringBuilder.append("L=");
            stringBuilder.append(s.getText().toString());
            stringBuilder.append("ST=");
            stringBuilder.append(t.getText().toString());
            stringBuilder.append("C=");
            stringBuilder.append(u.getText().toString());

            try {
                E.a(wq.j(), stringBuilder.toString(), k, m.getText().toString(), text);
                showDoneDialog(true, text);
            } catch (Exception e) {
                e.printStackTrace();
                showDoneDialog(false, null);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_keystore_cancel) {
                finish();
            } else if (id == R.id.btn_keystore_save) {
                save();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }

        setContentView(R.layout.keystore_new);
        l = findViewById(R.id.toolbar);
        setSupportActionBar(l);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_new_certificate));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        l.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        E = new iI();
        ((TextView) findViewById(R.id.tv_new_cert_title)).setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_new_certificate));
        Button var2 = findViewById(R.id.btn_keystore_save);
        var2.setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_button_create));
        var2.setOnClickListener(this);
        var2 = findViewById(R.id.btn_keystore_cancel);
        var2.setText(Helper.getResString(R.string.common_word_cancel));
        var2.setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_keystore_path)).setText(wq.D);
        m = findViewById(R.id.et_keystore_alias);
        ((TextInputLayout) findViewById(R.id.ti_keystore_alias)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_certificate_name));
        n = findViewById(R.id.et_keystore_passwd);
        ((TextInputLayout) findViewById(R.id.ti_keystore_passwd)).setHint(Helper.getResString(R.string.myprojects_sign_apk_hint_enter_new_password));
        o = findViewById(R.id.et_keystore_passwd1);
        ((TextInputLayout) findViewById(R.id.ti_keystore_passwd1)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_confirm_password));
        EditText var3 = findViewById(R.id.et_valid_year);
        ((TextInputLayout) findViewById(R.id.ti_valid_year)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_validity));
        var3.setText(String.valueOf(k));
        ((TextView) findViewById(R.id.tv_cert_title)).setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_certificate));
        p = findViewById(R.id.et_dn_cn);
        ((TextInputLayout) findViewById(R.id.ti_dn_cn)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_first_and_last_name));
        q = findViewById(R.id.et_dn_ou);
        ((TextInputLayout) findViewById(R.id.ti_dn_ou)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_organizational_unit));
        r = findViewById(R.id.et_dn_o);
        ((TextInputLayout) findViewById(R.id.ti_dn_o)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_organization));
        s = findViewById(R.id.et_dn_l);
        ((TextInputLayout) findViewById(R.id.ti_dn_l)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_city));
        t = findViewById(R.id.et_dn_st);
        ((TextInputLayout) findViewById(R.id.ti_dn_st)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_state));
        u = findViewById(R.id.et_dn_c);
        ((TextInputLayout) findViewById(R.id.ti_dn_c)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_country_code));
        v = new VB(getApplicationContext(), findViewById(R.id.ti_keystore_alias));
        w = new SB(getApplicationContext(), findViewById(R.id.ti_keystore_passwd), 4, 32);
        x = new SB(getApplicationContext(), findViewById(R.id.ti_keystore_passwd1), 4, 32);
        y = new RB(getApplicationContext(), findViewById(R.id.ti_dn_cn));
        z = new RB(getApplicationContext(), findViewById(R.id.ti_dn_ou));
        A = new RB(getApplicationContext(), findViewById(R.id.ti_dn_o));
        B = new RB(getApplicationContext(), findViewById(R.id.ti_dn_l));
        C = new RB(getApplicationContext(), findViewById(R.id.ti_dn_st));
        D = new RB(getApplicationContext(), findViewById(R.id.ti_dn_c));
        m.setPrivateImeOptions("defaultInputmode=english;");
        p.setPrivateImeOptions("defaultInputmode=english;");
        q.setPrivateImeOptions("defaultInputmode=english;");
        r.setPrivateImeOptions("defaultInputmode=english;");
        s.setPrivateImeOptions("defaultInputmode=english;");
        t.setPrivateImeOptions("defaultInputmode=english;");
        u.setPrivateImeOptions("defaultInputmode=english;");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }
}
