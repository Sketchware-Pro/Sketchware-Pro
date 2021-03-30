package mod.hey.studios.build;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import a.a.a.bB;
import mod.SketchwareUtil;

public class BuildSettingsDialog {

    private final Activity activity;
    private final String sc_id;
    private final BuildSettings settings;

    public BuildSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        this.sc_id = sc_id;
        settings = new BuildSettings(sc_id);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View inflate = activity.getLayoutInflater().inflate(2131427806, null);
        ((ImageView) inflate.findViewById(2131232630)).setImageResource(2131166152);
        ((TextView) inflate.findViewById(2131232629)).setText("Build Settings");
        TextView cancel = inflate.findViewById(2131232376);
        TextView save = inflate.findViewById(2131232377);
        LinearLayout contentView = inflate.findViewById(2131232508);
        View[] viewArr = {
                addInputPref(BuildSettings.SETTING_ANDROID_JAR_PATH, "", "Custom android.jar", 1, contentView),
                addInputPref(BuildSettings.SETTING_CLASSPATH, "", "Classpath (separated by :)", 1, contentView),
                addSingleChoicePref(BuildSettings.SETTING_RESOURCE_PROCESSOR, new String[]{"AAPT", "AAPT2"}, "false", "Resource processor", contentView),
                addSingleChoicePref(BuildSettings.SETTING_DEXER, new String[]{"Dx", "D8"}, "Dx", "Dexer", contentView),
                addSingleChoicePref(BuildSettings.SETTING_JAVA_VERSION, new String[]{"1.7", "1.8"}, "1.7", "Java version", contentView),
                addSingleChoicePref(BuildSettings.SETTING_OUTPUT_FORMAT, new String[]{"APK", "AAB"}, "false", "Output format", contentView),
                addTogglePref(BuildSettings.SETTING_NO_WARNINGS, false, "Hide warnings in error log", 12, contentView),
                addTogglePref(BuildSettings.SETTING_NO_HTTP_LEGACY, false, "Don't include http-legacy-28.dex", 12, contentView)
        };
        builder.setView(inflate);
        AlertDialog buildSettingsDialog = builder.create();
        buildSettingsDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildSettingsDialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setValues(viewArr);
                buildSettingsDialog.dismiss();
            }
        });
    }

    private RadioGroup addSingleChoicePref(String key, String[] choices, String defaultValue, String title, LinearLayout addTo) {
        TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(title);
        textView.setTextSize((float) 14);
        textView.setTextColor(Color.parseColor("#008DCD"));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(0, getDip(12), 0, getDip(12));
        addTo.addView(textView);
        RadioGroup radioGroup = new RadioGroup(activity);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setTag(key);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addTo.addView(radioGroup);
        for (String choice : choices) {
            RadioButton radioButton = new RadioButton(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            layoutParams.setMargins(0, 0, 0, 0);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setId(View.generateViewId());
            radioButton.setText(choice);
            radioButton.setTextColor(-16777216);
            radioButton.setTextSize((float) 16);
            if (settings.getValue(key, defaultValue).equals(choice)) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        return;
                    }
                    if (key.equals(BuildSettings.SETTING_JAVA_VERSION) && choice.equals(BuildSettings.SETTING_JAVA_VERSION_1_8)) {
                        SketchwareUtil.toast("Don't forget to enable D8 to be able to compile Java 8 code");
                    } else if (key.equals(BuildSettings.SETTING_DEXER) && choice.equals(BuildSettings.SETTING_DEXER_D8) && Build.VERSION.SDK_INT < 26) {
                        /* Here using bB.a(Context, String, int), because it allows a long toast */
                        bB.a(activity.getApplicationContext(), "Looks like your Android version isn't compatible with D8 (requires Android 8+). If you proceed to use it, compilation will not be successful.", Toast.LENGTH_SHORT).show();
                    } else if (key.equals(BuildSettings.SETTING_OUTPUT_FORMAT) && choice.equals(BuildSettings.SETTING_OUTPUT_FORMAT_AAB)) {
                        SketchwareUtil.toast("Ensure you are using AAPT2 as resource processor");
                    }
                }
            });
        }
        return radioGroup;
    }

    private CheckBox addTogglePref(String key, boolean defaultValue, String label, int leftMargin, LinearLayout addTo) {
        CheckBox checkBox = new CheckBox(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, getDip(leftMargin), 0, 0);
        checkBox.setLayoutParams(layoutParams);
        addTo.addView(checkBox);
        String value = settings.getValue(key, defaultValue ? "true" : "false");
        checkBox.setText(label);
        checkBox.setChecked(value.equals("true"));
        checkBox.setTextColor(-16777216);
        checkBox.setPadding(getDip(4), getDip(8), getDip(8), getDip(8));
        checkBox.setTag(key);
        if (key.equals(BuildSettings.SETTING_NO_HTTP_LEGACY)) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        SketchwareUtil.toast("Note that this option may cause issues if RequestNetwork component is used");
                    }
                }
            });
        }
        return checkBox;
    }

    private EditText addInputPref(String key, String defaultValue, String hint, int inputType, LinearLayout addTo) {
        TextInputLayout textInputLayout = new TextInputLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, getDip(12), 0, 0);
        textInputLayout.setLayoutParams(layoutParams);
        addTo.addView(textInputLayout);
        EditText editText = new EditText(activity);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        editText.setPadding(getDip(4), getDip(8), getDip(8), getDip(8));
        editText.setTextSize((float) 16);
        editText.setTextColor(-16777216);
        editText.setHint(hint);
        editText.setHintTextColor(Color.parseColor("#607D8B"));
        editText.setText(settings.getValue(key, defaultValue));
        editText.setTag(key);
        editText.setInputType(inputType);
        textInputLayout.addView(editText);
        return editText;
    }

    private int getDip(int i) {
        return (int) SketchwareUtil.getDip(i);
    }
}