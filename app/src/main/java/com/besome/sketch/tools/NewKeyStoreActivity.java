package com.besome.sketch.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import a.a.a.RB;
import a.a.a.SB;
import a.a.a.VB;
import a.a.a.bB;
import a.a.a.iI;
import a.a.a.mB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.KeystoreNewBinding;

public class NewKeyStoreActivity extends BaseAppCompatActivity implements OnClickListener {

    public KeystoreNewBinding binding;

    private final int validityInYears = 25;
    private RB organizationValidator;
    private RB localityValidator;
    private RB stateValidator;
    private RB countryValidator;
    private RB commonNameValidator;
    private RB organizationalUnitValidator;
    private iI E;
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
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_certificate));
        if (success) {
            dialog.setIcon(R.drawable.certificate_96_blue);
            dialog.setMessage(Helper.getResString(R.string.myprojects_sign_apk_dialog_complete_create_certificate));
        } else {
            dialog.setIcon(R.drawable.error_96_yellow);
            dialog.setMessage(Helper.getResString(R.string.myprojects_sign_apk_error_failed_create_new_certificate));
        }

        dialog.setPositiveButton(Helper.getResString(R.string.common_word_close), (v, which) -> {
            if (!mB.a()) {
                v.dismiss();
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
            String text = Helper.getText(password);
            if (!text.equals(Helper.getText(passwordConfirm))) {
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
            stringBuilder.append(Helper.getText(commonName));
            stringBuilder.append("OU=");
            stringBuilder.append(Helper.getText(organizationalUnit));
            stringBuilder.append("O=");
            stringBuilder.append(Helper.getText(organization));
            stringBuilder.append("L=");
            stringBuilder.append(Helper.getText(locality));
            stringBuilder.append("ST=");
            stringBuilder.append(Helper.getText(state));
            stringBuilder.append("C=");
            stringBuilder.append(Helper.getText(country));

            try {
                E.a(wq.j(), stringBuilder.toString(), validityInYears, Helper.getText(alias), text);
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
        if (!super.isStoragePermissionGranted()) {
            finish();
        }

        binding = KeystoreNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar.toolbar;
        setSupportActionBar(toolbar);
        binding.toolbar.layoutMainLogo.setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_new_certificate));
        getSupportActionBar().setSubtitle("Export path: " + wq.D);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        E = new iI();

        binding.btnKeystoreSave.setOnClickListener(this);
        binding.btnKeystoreCancel.setOnClickListener(this);
        alias = binding.etKeystoreAlias;
        TextInputLayout tilAlias = binding.tiKeystoreAlias;
        password = binding.etKeystorePasswd;
        TextInputLayout tilPassword = binding.tiKeystorePasswd;
        passwordConfirm = binding.etKeystorePasswd1;
        TextInputLayout tilPasswordConfirm = binding.tiKeystorePasswd1;
        EditText validity = binding.etValidYear;
        validity.setText(String.valueOf(validityInYears));
        binding.tvCertTitle.setText(Helper.getResString(R.string.myprojects_sign_apk_new_certificate_title_certificate));
        commonName = binding.etDnCn;
        TextInputLayout tilCommonName = binding.tiDnCn;
        organizationalUnit = binding.etDnOu;
        TextInputLayout tilOrganizationalUnit = binding.tiDnOu;
        organization = binding.etDnO;
        TextInputLayout tilOrganization = binding.tiDnO;
        locality = binding.etDnL;
        TextInputLayout tilLocality = binding.tiDnL;
        state = binding.etDnSt;
        TextInputLayout tilState = binding.tiDnSt;
        country = binding.etDnC;
        TextInputLayout tilCountry = binding.tiDnC;


        aliasValidator = new VB(getApplicationContext(), tilAlias);
        passwordValidator = new SB(getApplicationContext(), tilPassword, 4, 32);
        passwordConfirmValidator = new SB(getApplicationContext(), tilPasswordConfirm, 4, 32);
        commonNameValidator = new RB(getApplicationContext(), tilCommonName);
        organizationalUnitValidator = new RB(getApplicationContext(), tilOrganizationalUnit);
        organizationValidator = new RB(getApplicationContext(), tilOrganization);
        localityValidator = new RB(getApplicationContext(), tilLocality);
        stateValidator = new RB(getApplicationContext(), tilState);
        countryValidator = new RB(getApplicationContext(), tilCountry);


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
        if (!super.isStoragePermissionGranted()) {
            finish();
        }
    }
}
