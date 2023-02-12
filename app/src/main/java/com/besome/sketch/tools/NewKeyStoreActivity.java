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
    private RB organizationValidator;
    private RB localityValidator;
    private RB stateValidator;
    private RB countryValidator;
    private RB commonNameValidator;
    private RB organizationalUnitValidator;
    private iI E;
    private int validityInYears = 25;
    private EditText alias;
    private EditText password;
    private EditText passwordConfirm;
    private EditText commonName;
    private EditText organizationalUnit;
    private EditText organization;
    private EditText locality;
    private EditText state;
    private EditText country;
    private VB aliasValidator;
    private SB passwordValidator, passwordConfirmValidator;

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
        if (passwordValidator.b() && passwordConfirmValidator.b()) {
            String text = password.getText().toString();
            if (!text.equals(passwordConfirm.getText().toString())) {
                bB.b(getApplicationContext(), Helper.getResString(R.string.myprojects_sign_apk_incorrect_password), 0).show();
                password.setText("");
                passwordConfirm.setText("");
                return;
            }

            if (!aliasValidator.b()) return;

            if (!commonNameValidator.b()) return;

            if (!organizationalUnitValidator.b()) return;

            if (!organizationValidator.b()) return;

            if (!localityValidator.b()) return;

            if (!stateValidator.b()) return;

            if (!countryValidator.b()) return;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CN=");
            stringBuilder.append(commonName.getText().toString());
            stringBuilder.append("OU=");
            stringBuilder.append(organizationalUnit.getText().toString());
            stringBuilder.append("O=");
            stringBuilder.append(organization.getText().toString());
            stringBuilder.append("L=");
            stringBuilder.append(locality.getText().toString());
            stringBuilder.append("ST=");
            stringBuilder.append(state.getText().toString());
            stringBuilder.append("C=");
            stringBuilder.append(country.getText().toString());

            try {
                E.a(wq.j(), stringBuilder.toString(), validityInYears, alias.getText().toString(), text);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_new_certificate));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        E = new iI();
        ((TextView) findViewById(R.id.tv_new_cert_title)).setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_new_certificate));
        Button var2 = findViewById(R.id.btn_keystore_save);
        var2.setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_button_create));
        var2.setOnClickListener(this);
        var2 = findViewById(R.id.btn_keystore_cancel);
        var2.setText(Helper.getResString(R.string.common_word_cancel));
        var2.setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_keystore_path)).setText(wq.D);
        alias = findViewById(R.id.et_keystore_alias);
        ((TextInputLayout) findViewById(R.id.ti_keystore_alias)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_certificate_name));
        password = findViewById(R.id.et_keystore_passwd);
        ((TextInputLayout) findViewById(R.id.ti_keystore_passwd)).setHint(Helper.getResString(R.string.myprojects_sign_apk_hint_enter_new_password));
        passwordConfirm = findViewById(R.id.et_keystore_passwd1);
        ((TextInputLayout) findViewById(R.id.ti_keystore_passwd1)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_confirm_password));
        EditText validity = findViewById(R.id.et_valid_year);
        ((TextInputLayout) findViewById(R.id.ti_valid_year)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_validity));
        validity.setText(String.valueOf(validityInYears));
        ((TextView) findViewById(R.id.tv_cert_title)).setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_certificate));
        commonName = findViewById(R.id.et_dn_cn);
        ((TextInputLayout) findViewById(R.id.ti_dn_cn)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_first_and_last_name));
        organizationalUnit = findViewById(R.id.et_dn_ou);
        ((TextInputLayout) findViewById(R.id.ti_dn_ou)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_organizational_unit));
        organization = findViewById(R.id.et_dn_o);
        ((TextInputLayout) findViewById(R.id.ti_dn_o)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_organization));
        locality = findViewById(R.id.et_dn_l);
        ((TextInputLayout) findViewById(R.id.ti_dn_l)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_city));
        state = findViewById(R.id.et_dn_st);
        ((TextInputLayout) findViewById(R.id.ti_dn_st)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_state));
        country = findViewById(R.id.et_dn_c);
        ((TextInputLayout) findViewById(R.id.ti_dn_c)).setHint(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_hint_country_code));
        aliasValidator = new VB(getApplicationContext(), findViewById(R.id.ti_keystore_alias));
        passwordValidator = new SB(getApplicationContext(), findViewById(R.id.ti_keystore_passwd), 4, 32);
        passwordConfirmValidator = new SB(getApplicationContext(), findViewById(R.id.ti_keystore_passwd1), 4, 32);
        commonNameValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_cn));
        organizationalUnitValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_ou));
        organizationValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_o));
        localityValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_l));
        stateValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_st));
        countryValidator = new RB(getApplicationContext(), findViewById(R.id.ti_dn_c));
        alias.setPrivateImeOptions("defaultInputmode=english;");
        commonName.setPrivateImeOptions("defaultInputmode=english;");
        organizationalUnit.setPrivateImeOptions("defaultInputmode=english;");
        organization.setPrivateImeOptions("defaultInputmode=english;");
        locality.setPrivateImeOptions("defaultInputmode=english;");
        state.setPrivateImeOptions("defaultInputmode=english;");
        country.setPrivateImeOptions("defaultInputmode=english;");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }
}
