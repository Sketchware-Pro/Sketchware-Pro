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

import java.io.File;

import a.a.a.RB;
import a.a.a.SB;
import a.a.a.VB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.KeystoreNewBinding;
import pro.sketchware.utility.StrongKeyStoreCreator;

public class NewKeyStoreActivity extends BaseAppCompatActivity implements OnClickListener {

    public KeystoreNewBinding binding;

    /**
     * Which save request currently owns the keystore destination, across activity
     * instances (back out and reopen re-runs save() on a fresh instance). An abandoned
     * worker whose token no longer matches must not write the keystore or report success.
     */
    private static final java.util.concurrent.atomic.AtomicReference<String> activeSaveToken = new java.util.concurrent.atomic.AtomicReference<>(null);
    private String saveToken;

    private final int validityInYears = StrongKeyStoreCreator.DEFAULT_VALIDITY_YEARS;
    private RB organizationValidator;
    private RB localityValidator;
    private RB stateValidator;
    private RB countryValidator;
    private RB commonNameValidator;
    private RB organizationalUnitValidator;
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
        dialog.setTitle(R.string.myprojects_sign_apk_new_certificate_title_certificate);
        if (success) {
            dialog.setIcon(R.drawable.certificate_96_blue);
            dialog.setMessage(R.string.myprojects_sign_apk_dialog_complete_create_certificate);
        } else {
            dialog.setIcon(R.drawable.error_96_yellow);
            dialog.setMessage(R.string.myprojects_sign_apk_error_failed_create_new_certificate);
        }

        dialog.setPositiveButton(R.string.common_word_close, (v, which) -> {
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

            String dn = new StringBuilder("CN=")
                    .append(Helper.getText(commonName))
                    .append(", OU=").append(Helper.getText(organizationalUnit))
                    .append(", O=").append(Helper.getText(organization))
                    .append(", L=").append(Helper.getText(locality))
                    .append(", ST=").append(Helper.getText(state))
                    .append(", C=").append(Helper.getText(country))
                    .toString();

            try {
                String keystorePath = wq.j();
                File keystoreDir = new File(keystorePath).getParentFile();
                if (keystoreDir != null && !keystoreDir.exists() && !keystoreDir.mkdirs()) {
                    throw new Exception("Failed to create keystore directory: " + keystoreDir);
                }

                char[] pass = text.toCharArray();
                String aliasText = Helper.getText(alias);
                // Claim ownership of the destination: a stale worker from a previous
                // instance must not overwrite this keystore (see activeSaveToken).
                saveToken = java.util.UUID.randomUUID().toString();
                activeSaveToken.set(saveToken);
                // RSA key generation and filesystem writes can take seconds; keep them off
                // the main thread and prevent duplicate submissions while running.
                binding.btnKeystoreSave.setEnabled(false);
                binding.btnKeystoreCancel.setEnabled(false);
                binding.btnKeystoreSave.post(() -> new Thread(() -> {
                    try {
                        byte[] keystoreBytes = StrongKeyStoreCreator.createKeystore(
                                dn, validityInYears, aliasText, pass);
                        // Ownership check: if the user backed out and reopened the screen, a
                        // newer save request owns this destination - an abandoned worker from
                        // the dead instance must neither overwrite the keystore nor report
                        // success with credentials the file doesn't match.
                        if (!saveToken.equals(activeSaveToken.get())) return;
                        new oB().a(keystorePath, keystoreBytes);
                        if (!saveToken.equals(activeSaveToken.get())) return;
                        runOnUiThread(() -> {
                            // The user may have backed out during generation; a dead activity
                            // can't show dialogs (BadTokenException on an invalid window token).
                            if (isFinishing() || isDestroyed()) return;
                            binding.btnKeystoreSave.setEnabled(true);
                            binding.btnKeystoreCancel.setEnabled(true);
                            showDoneDialog(true, text);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            if (isFinishing() || isDestroyed()) return;
                            binding.btnKeystoreSave.setEnabled(true);
                            binding.btnKeystoreCancel.setEnabled(true);
                            showDoneDialog(false, null);
                        });
                    }
                }).start());
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
    public void onDestroy() {
        super.onDestroy();
        // Invalidate this instance's save request: its worker (if still running) must not
        // commit the keystore or report success after the user left the screen.
        activeSaveToken.compareAndSet(saveToken, null);
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
        getSupportActionBar().setTitle(R.string.myprojects_sign_apk_new_certificate_title_new_certificate);
        getSupportActionBar().setSubtitle(Helper.getResString(R.string.new_key_store_export_path, wq.D));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

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
