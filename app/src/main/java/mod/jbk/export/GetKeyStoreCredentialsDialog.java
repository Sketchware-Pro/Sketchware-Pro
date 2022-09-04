package mod.jbk.export;

import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.LinkedList;

import a.a.a.aB;
import a.a.a.wq;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class GetKeyStoreCredentialsDialog {

    private final aB dialog;
    private CredentialsReceiver receiver;
    private SigningMode mode;

    private TextInputLayout tilAlias;
    private EditText alias;
    private TextInputLayout tilPassword;
    private EditText password;
    private TextInputLayout tilSigningAlgorithm;
    private EditText signingAlgorithm;

    public GetKeyStoreCredentialsDialog(Activity activity, int iconResourceId, String title, String noticeText) {
        dialog = new aB(activity);
        dialog.a(iconResourceId);
        dialog.b(title);
        dialog.a(noticeText);
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(R.string.common_word_next), next -> {
            if (mode == SigningMode.OWN_KEY_STORE) {
                // La/a/a/wq;->j()Ljava/lang/String; returns /Internal storage/sketchware/keystore/release_key.jks
                if (new File(wq.j()).exists()) {
                    boolean aliasEmpty = TextUtils.isEmpty(alias.getText().toString());
                    boolean passwordEmpty = TextUtils.isEmpty(password.getText().toString());
                    boolean algorithmEmpty = TextUtils.isEmpty(signingAlgorithm.getText().toString());

                    if (aliasEmpty) {
                        tilAlias.setError("Alias can't be empty");
                    } else {
                        tilAlias.setError(null);
                    }
                    if (passwordEmpty) {
                        tilPassword.setError("Password can't be empty");
                    } else {
                        tilPassword.setError(null);
                    }
                    if (algorithmEmpty) {
                        tilSigningAlgorithm.setError("Algorithm can't be empty");
                    } else {
                        tilSigningAlgorithm.setError(null);
                    }

                    if (!aliasEmpty && !passwordEmpty && !algorithmEmpty) {
                        dialog.dismiss();

                        receiver.gotCredentials(new Credentials(signingAlgorithm.getText().toString(),
                                password.getText().toString(), alias.getText().toString(), password.getText().toString()));
                    }
                } else {
                    SketchwareUtil.toastError("Keystore not found");
                }
            } else if (mode == SigningMode.TESTKEY) {
                dialog.dismiss();
                receiver.gotCredentials(new Credentials(signingAlgorithm.getText().toString()));
            } else if (mode == SigningMode.DONT_SIGN) {
                dialog.dismiss();
                receiver.gotCredentials(null);
            }
        });

        LinearLayout contentView = new LinearLayout(activity);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setPadding(
                dpToPx(4),
                0,
                dpToPx(4),
                0
        );

        String[] dropdownItems;
        {
            LinkedList<String> labels = new LinkedList<>();
            for (SigningMode mode : SigningMode.values()) {
                labels.add(mode.label);
            }

            dropdownItems = labels.toArray(new String[0]);
        }

        Spinner bruhSpinner = new Spinner(activity);
        bruhSpinner.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, dropdownItems));
        bruhSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mode = SigningMode.OWN_KEY_STORE;
                        break;

                    case 1:
                        mode = SigningMode.TESTKEY;
                        break;

                    case 2:
                        mode = SigningMode.DONT_SIGN;
                        break;

                    default:
                }

                boolean signingWithKeyStore = mode == SigningMode.OWN_KEY_STORE;
                tilAlias.setEnabled(signingWithKeyStore);
                tilPassword.setEnabled(signingWithKeyStore);
                tilSigningAlgorithm.setEnabled(signingWithKeyStore);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        contentView.addView(bruhSpinner);

        LinearLayout inputContainer = new LinearLayout(activity);
        inputContainer.setOrientation(LinearLayout.VERTICAL);
        inputContainer.setPadding(
                dpToPx(12),
                dpToPx(12),
                dpToPx(12),
                dpToPx(12)
        );
        contentView.addView(inputContainer);

        tilAlias = new TextInputLayout(activity);
        alias = new EditText(activity);
        alias.setHint("Keystore alias");
        tilAlias.addView(alias, 0, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inputContainer.addView(tilAlias);

        tilPassword = new TextInputLayout(activity);
        password = new EditText(activity);
        password.setHint("Alias password");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        tilPassword.setPasswordVisibilityToggleEnabled(true);
        tilPassword.addView(password, 0, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inputContainer.addView(tilPassword);

        tilSigningAlgorithm = new TextInputLayout(activity);
        tilSigningAlgorithm.setHelperText("Example: SHA256withRSA");
        signingAlgorithm = new EditText(activity);
        signingAlgorithm.setHint("Signing algorithm");
        tilSigningAlgorithm.addView(signingAlgorithm, 0, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        inputContainer.addView(tilSigningAlgorithm);

        dialog.a(contentView);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void show() {
        alias.requestFocus();
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
        OWN_KEY_STORE("Sign using key store"),
        TESTKEY("Sign using testkey"),
        DONT_SIGN("Don't sign");

        private final String label;

        SigningMode(String label) {
            this.label = label;
        }
    }
}
