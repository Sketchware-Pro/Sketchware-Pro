package mod.hey.studios.project;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import a.a.a.aB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        this.settings = new ProjectSettings(sc_id);
    }

    public void show() {
        aB dialog = new aB(activity);
        dialog.a(R.drawable.services_48);
        dialog.b("Project Configuration");

        ScrollView preferenceScroller = new ScrollView(dialog.getContext());
        {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            preferenceScroller.setLayoutParams(layoutParams);
        }

        LinearLayout preferenceContainer = new LinearLayout(dialog.getContext());
        {
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            preferenceContainer.setLayoutParams(layoutParams);
            preferenceContainer.setOrientation(LinearLayout.VERTICAL);
        }

        EditText minimumSdkVersion = addInputPref(
                ProjectSettings.SETTING_MINIMUM_SDK_VERSION,
                "21",
                "Minimum SDK version",
                InputType.TYPE_CLASS_NUMBER,
                preferenceContainer);

        EditText targetSdkVersion = addInputPref(
                ProjectSettings.SETTING_TARGET_SDK_VERSION,
                "28",
                "Target SDK version",
                InputType.TYPE_CLASS_NUMBER,
                preferenceContainer);

        EditText applicationClassName = addInputPref(
                ProjectSettings.SETTING_APPLICATION_CLASS,
                ".SketchApplication",
                "Application class name",
                InputType.TYPE_CLASS_TEXT,
                preferenceContainer);

        CheckBox removeOldMethods = addTogglePref(
                ProjectSettings.SETTING_DISABLE_OLD_METHODS,
                false,
                "Remove old deprecated methods in files, like showMessage, getDip, etc.",
                preferenceContainer);

        CheckBox useNewMaterialComponentsAppTheme = addTogglePref(
                ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                false,
                "Use new MaterialComponents AppTheme (will replace e.g. Button with MaterialButton, be careful!)",
                preferenceContainer);

        preferenceScroller.addView(preferenceContainer);
        dialog.a(preferenceScroller);

        final View[] preferences = {
                minimumSdkVersion,
                targetSdkVersion,
                applicationClassName,
                removeOldMethods,
                useNewMaterialComponentsAppTheme
        };

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            settings.setValues(preferences);
            dialog.dismiss();
        });
        dialog.show();
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
        e.setTag(key);
        e.setInputType(inputType);
        i.addView(e);

        return e;
    }
}
