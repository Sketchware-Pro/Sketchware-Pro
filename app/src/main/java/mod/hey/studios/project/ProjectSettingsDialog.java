package mod.hey.studios.project;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import mod.hey.studios.util.Helper;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        this.settings = new ProjectSettings(sc_id);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(Resources.layout.project_config_layout, null);


        final TextView text_cancel = view.findViewById(Resources.id.text_cancel);
        final TextView text_save = view.findViewById(Resources.id.text_save);

        final LinearLayout prefs = view.findViewById(Resources.id.project_config_pref_layout);

        int numberType = InputType.TYPE_CLASS_NUMBER;

        EditText et_minsdk = addInputPref(
                ProjectSettings.SETTING_MINIMUM_SDK_VERSION,
                "21",
                "Minimum SDK version",
                numberType, prefs);

        EditText et_targetsdk = addInputPref(
                ProjectSettings.SETTING_TARGET_SDK_VERSION,
                "28",
                "Target SDK version",
                numberType,
                prefs);

        EditText et_app_class = addInputPref(
                ProjectSettings.SETTING_APPLICATION_CLASS,
                ".SketchApplication",
                "Application class name",
                InputType.TYPE_CLASS_TEXT,
                prefs);

        CheckBox cb_oldmethods = addTogglePref(
                ProjectSettings.SETTING_DISABLE_OLD_METHODS,
                false,
                "Remove old deprecated methods in files, like showMessage, getDip, etc.",
                prefs);

        CheckBox cb_largeheap = addTogglePref(
                ProjectSettings.SETTING_DISABLE_LARGE_HEAP,
                false,
                "Disable large heap for App",
                prefs);

        CheckBox cb_appthemeparent = addTogglePref(
                ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                false,
                "Use new MaterialComponents AppTheme (will replace e.g. Button with MaterialButton, be careful!)",
                prefs);

        // hmm, seem interesting!

        //EditText et_java_comp

        //CheckBox cb_ovrrd = addTogglePref("override_src_files", false, "Override existing Java/Res files with your files in case they co-exist. This option is especially useful if you want to edit a file that Sketchware generates automatically.", prefs);

        //CheckBox cb_block_opt = addTogglePref("block_opt", false, "Apply extra block optimization methods in logic editor", prefs);

        final View[] pref_views = {
                et_minsdk,
                et_targetsdk,
                et_app_class,
                cb_oldmethods,
                cb_largeheap,
                cb_appthemeparent
        };

        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        text_cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));

        text_save.setOnClickListener(v -> {
            settings.setValues(pref_views);

            dialog.dismiss();
        });

    }

    private CheckBox addTogglePref(String key, boolean defaultState, String hint, LinearLayout layout) {
        CheckBox i = new CheckBox(activity);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setMargins(
                0,
                (int) getDip(10),
                0,
                0
        );
        i.setLayoutParams(l);
        layout.addView(i);

        String v = settings.getValue(key, defaultState ? "true" : "false");

        i.setText(hint);
        i.setChecked(v.equals("true"));
        i.setTextColor(Color.BLACK);
        i.setPadding(
                (int) getDip(4),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
		
		/*i.setTag(0, key);
		i.setTag(1, v);*/

        i.setTag(key);

        return i;
    }

    private EditText addInputPref(String key, String defaultValue, String hint, int inputType, LinearLayout layout) {
        TextInputLayout i = new TextInputLayout(activity);
        LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        l.setMargins(
                0,
                (int) getDip(10),
                0,
                0
        );
        i.setLayoutParams(l);
        layout.addView(i);

        EditText e = new EditText(activity);
        e.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        e.setPadding(
                (int) getDip(4),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        e.setTextSize(16);
        e.setTextColor(Color.BLACK);
        e.setHint(hint);
        e.setHintTextColor(0xff607d8b);
        e.setText(settings.getValue(key, defaultValue));
		/*e.setTag(0, key);
		e.setTag(1, e.getText().toString());*/
        e.setTag(key);
        e.setInputType(inputType);
        i.addView(e);

        return e;
    }
}
