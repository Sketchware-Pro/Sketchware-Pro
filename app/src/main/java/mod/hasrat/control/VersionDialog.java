package mod.hasrat.control;

import static mod.SketchwareUtil.getDip;

import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import a.a.a.mB;
import mod.hasrat.dialog.SketchDialog;
import mod.hasrat.validator.VersionNamePostfixValidator;
import mod.hey.studios.util.Helper;

public class VersionDialog {

    private final MyProjectSettingActivity activity;

    public VersionDialog(MyProjectSettingActivity activity) {
        this.activity = activity;
    }

    public void show() {
        final SketchDialog dialog = new SketchDialog(activity);
        dialog.setTitle("Advanced Version Control");
        dialog.setIcon(R.drawable.numbers_48);
        final LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout regularNumbersContainer = new LinearLayout(activity);
        final TextInputLayout tilVersionCode = new TextInputLayout(activity);
        final LinearLayout.LayoutParams tilVersionCodeParams = new LinearLayout.LayoutParams(
                (int) getDip(100),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tilVersionCodeParams.setMargins(
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5)
        );
        tilVersionCode.setLayoutParams(tilVersionCodeParams);
        final EditText version_code = new EditText(activity);
        version_code.setHint("Version Code");
        version_code.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        version_code.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        version_code.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        version_code.setTextSize(13);
        tilVersionCode.addView(version_code);
        regularNumbersContainer.addView(tilVersionCode);

        final TextInputLayout tilVersionName = new TextInputLayout(activity);
        LinearLayout.LayoutParams tilVersionNameParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tilVersionNameParams.setMargins(
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5)
        );
        tilVersionName.setLayoutParams(tilVersionNameParams);
        final EditText version_name1 = new EditText(activity);
        version_name1.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        version_name1.setHint("Version Name");
        version_name1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        version_name1.setKeyListener(DigitsKeyListener.getInstance("1234567890."));
        version_name1.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        version_name1.setTextSize(13);
        tilVersionName.addView(version_name1);
        regularNumbersContainer.addView(tilVersionName);
        root.addView(regularNumbersContainer);


        final LinearLayout versionNamePostfixContainer = new LinearLayout(activity);
        versionNamePostfixContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final TextInputLayout til_version_name2 = new TextInputLayout(activity);
        final LinearLayout.LayoutParams tilVersionNamePostfixParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tilVersionNamePostfixParams.setMargins(
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5),
                (int) getDip(5)
        );
        til_version_name2.setLayoutParams(tilVersionNamePostfixParams);
        final EditText version_name2 = new EditText(activity);
        version_name2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        version_name2.setHint("Version Name Extra");
        version_name2.setImeOptions(EditorInfo.IME_ACTION_DONE);
        version_name2.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        version_name2.setTextSize(13);
        til_version_name2.addView(version_name2);
        version_name2.addTextChangedListener(new VersionNamePostfixValidator(activity, til_version_name2));
        versionNamePostfixContainer.addView(til_version_name2);
        root.addView(versionNamePostfixContainer);

        version_code.setText(String.valueOf(Integer.parseInt(activity.binding.verCode.getText().toString())));
        version_name1.setText(activity.binding.verName.getText().toString().split(" ")[0]);
        if (activity.binding.verName.getText().toString().split(" ").length > 1) {
            version_name2.setText(activity.binding.verName.getText().toString().split(" ")[1]);
        }
        dialog.setView(root);
        dialog.setPositiveButton(activity.getString(R.string.common_word_save), v -> {
            final String versionCode = version_code.getText().toString();
            final String versionName = version_name1.getText().toString();
            final String versionNamePostfix = version_name2.getText().toString();

            boolean validVersionCode = !TextUtils.isEmpty(versionCode);
            boolean validVersionName = !TextUtils.isEmpty(versionName);

            if (validVersionCode) {
                version_code.setError(null);
            } else {
                version_code.setError("Invalid Version Code");
            }

            if (validVersionName) {
                version_name1.setError(null);
            } else {
                version_name1.setError("Invalid Version Name");
            }

            if (!mB.a() && validVersionCode && validVersionName) {
                activity.binding.verCode.setText(versionCode);
                activity.binding.verName.setText(versionNamePostfix.length() > 0 ? (versionName + " " + versionNamePostfix) : versionName);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton(activity.getString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
