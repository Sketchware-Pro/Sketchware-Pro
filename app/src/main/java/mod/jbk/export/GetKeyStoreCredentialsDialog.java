package mod.jbk.export;

import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.io.File;

import a.a.a.aB;
import a.a.a.wq;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class GetKeyStoreCredentialsDialog {

    private final aB dialog;
    private CredentialsReceiver receiver;

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
        dialog.a(Helper.getResString(Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(Resources.string.common_word_next), next -> {
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
        });

        LinearLayout inputContainer = new LinearLayout(activity);
        inputContainer.setOrientation(LinearLayout.VERTICAL);
        inputContainer.setPadding(
                dpToPx(4),
                0,
                dpToPx(4),
                0
        );

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

        dialog.a(inputContainer);
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
            signWithTestkey = false;
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
}
