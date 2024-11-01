package mod.jbk.export;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import pro.sketchware.R;
import pro.sketchware.databinding.DialogKeystoreCredentialsBinding;

import java.io.File;
import java.util.LinkedList;

import a.a.a.aB;
import a.a.a.wq;
import pro.sketchware.utility.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class GetKeyStoreCredentialsDialog {

    private final aB dialog;
    private CredentialsReceiver receiver;
    private SigningMode mode;
    private final DialogKeystoreCredentialsBinding binding;

    public GetKeyStoreCredentialsDialog(Activity activity, int iconResourceId, String title, String noticeText) {
        dialog = new aB(activity);
        dialog.a(iconResourceId);
        dialog.b(title);
        dialog.a(noticeText);
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(R.string.common_word_next), this::onNextButtonClick);

        binding = DialogKeystoreCredentialsBinding.inflate(LayoutInflater.from(activity));
        dialog.a(binding.getRoot());

        setupSpinner(activity);
    }

    private void setupSpinner(Activity activity) {
        String[] dropdownItems = getDropdownItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, dropdownItems);
        binding.actSigningMode.setAdapter(adapter);
        binding.actSigningMode.setOnItemClickListener((parent, view, position, id) -> {
            mode = SigningMode.values()[position];
            updateInputFieldsState();
        });
    }

    private String[] getDropdownItems() {
        LinkedList<String> labels = new LinkedList<>();
        for (SigningMode mode : SigningMode.values()) {
            labels.add(mode.label);
        }
        return labels.toArray(new String[0]);
    }

    private void updateInputFieldsState() {
        boolean signingWithKeyStore = mode == SigningMode.OWN_KEY_STORE;
        binding.tilAlias.setEnabled(signingWithKeyStore);
        binding.tilPassword.setEnabled(signingWithKeyStore);
        binding.tilSigningAlgorithm.setEnabled(signingWithKeyStore);
    }

    private void onNextButtonClick(View v) {
        if (mode == SigningMode.OWN_KEY_STORE) {
            if (new File(wq.j()).exists()) {
                if (validateInputs()) {
                    dialog.dismiss();
                    receiver.gotCredentials(new Credentials(
                            binding.etSigningAlgorithm.getText().toString(),
                            binding.etPassword.getText().toString(),
                            binding.etAlias.getText().toString(),
                            binding.etPassword.getText().toString()
                    ));
                }
            } else {
                SketchwareUtil.toastError("Keystore not found");
            }
        } else if (mode == SigningMode.TESTKEY) {
            dialog.dismiss();
            receiver.gotCredentials(new Credentials(binding.etSigningAlgorithm.getText().toString()));
        } else if (mode == SigningMode.DONT_SIGN) {
            dialog.dismiss();
            receiver.gotCredentials(null);
        }
    }

    private boolean validateInputs() {
        boolean isValid = true;

        if (TextUtils.isEmpty(binding.etAlias.getText())) {
            binding.tilAlias.setError("Alias can't be empty");
            isValid = false;
        } else {
            binding.tilAlias.setError(null);
        }

        if (TextUtils.isEmpty(binding.etPassword.getText())) {
            binding.tilPassword.setError("Password can't be empty");
            isValid = false;
        } else {
            binding.tilPassword.setError(null);
        }

        if (TextUtils.isEmpty(binding.etSigningAlgorithm.getText())) {
            binding.tilSigningAlgorithm.setError("Algorithm can't be empty");
            isValid = false;
        } else {
            binding.tilSigningAlgorithm.setError(null);
        }

        return isValid;
    }

    public void show() {
        binding.etAlias.requestFocus();
        dialog.show();
    }

    public void setListener(CredentialsReceiver receiver) {
        this.receiver = receiver;
    }

    public interface CredentialsReceiver {
        /**
         * @param credentials The {@link Credentials} object made from user input.
         *                    May be null. In that case, the user disabled signing the file.
         */
        void gotCredentials(Credentials credentials);
    }

    public static class Credentials {

        private final boolean signWithTestkey;
        private final String keyStorePassword;
        private final String keyAlias;
        private final String keyPassword;
        private final String signingAlgorithm;

        /**
         * Constructs a credentials holder configured to sign with testkey,
         * meaning that no key store, aliases, and passwords were entered.
         */
        public Credentials(String signingAlgorithm) {
            signWithTestkey = true;
            keyStorePassword = null;
            keyAlias = null;
            keyPassword = null;
            this.signingAlgorithm = signingAlgorithm;
        }

        /**
         * Constructs a credentials holder configured to sign with a private key taken from a key store.
         */
        public Credentials(String signingAlgorithm, String keyPassword, String keyAlias, String keyStorePassword) {
            signWithTestkey = false;
            this.keyStorePassword = keyStorePassword;
            this.keyAlias = keyAlias;
            this.keyPassword = keyPassword;
            this.signingAlgorithm = signingAlgorithm;
        }

        /**
         * @return False if this credentials holder contains credentials for signing, true if not.
         */
        public boolean isForSigningWithTestkey() {
            return signWithTestkey;
        }

        /**
         * @return {@link #keyStorePassword}
         */
        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        /**
         * @return {@link #keyAlias}
         */
        public String getKeyAlias() {
            return keyAlias;
        }

        /**
         * @return {@link #keyPassword}
         */
        public String getKeyPassword() {
            return keyPassword;
        }

        /**
         * @return {@link #signingAlgorithm}
         */
        public String getSigningAlgorithm() {
            return signingAlgorithm;
        }
    }

    private enum SigningMode {
        OWN_KEY_STORE("Sign using keystore"),
        TESTKEY("Sign using a test key"),
        DONT_SIGN("Don't sign");

        private final String label;

        SigningMode(String label) {
            this.label = label;
        }
    }
}