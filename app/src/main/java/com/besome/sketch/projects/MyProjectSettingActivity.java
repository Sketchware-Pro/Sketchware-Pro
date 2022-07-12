package com.besome.sketch.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.GB;
import a.a.a.HB;
import a.a.a.LB;
import a.a.a.MA;
import a.a.a.UB;
import a.a.a.VB;
import a.a.a.Zx;
import a.a.a.aB;
import a.a.a.gB;
import a.a.a.iB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nB;
import a.a.a.oB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import mod.SketchwareUtil;
import mod.hasrat.control.VersionDialog;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class MyProjectSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_PICK_CROPPED_ICON = 216;
    private static final int REQUEST_CODE_PICK_ICON = 207;
    private final String[] themeColorKeys = {"color_accent", "color_primary", "color_primary_dark", "color_control_highlight", "color_control_normal"};
    private final String[] themeColorLabels = {"colorAccent", "colorPrimary", "colorPrimaryDark", "colorControlHighlight", "colorControlNormal"};
    private final int[] projectThemeColors = new int[themeColorKeys.length];
    public TextView projectVersionCodeView;
    public TextView projectVersionNameView;
    private EditText projectAppName;
    private EditText projectPackageName;
    private EditText projectName;
    private LinearLayout themeColorsContainer;
    private ImageView colorGuide;
    private LinearLayout advancedSettingsContainer;
    private ImageView appIcon;
    private UB projectPackageNameValidator;
    private VB projectNameValidator;
    private LB projectAppNameValidator;
    private boolean projectHasCustomIcon = false;
    private boolean updatingExistingProject = false;
    private int projectVersionCode = 1;
    private int projectVersionNameFirstPart;
    private int projectVersionNameSecondPart;
    private boolean shownPackageNameChangeWarning;
    private String sc_id;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            SketchwareUtil.toast("Received invalid data");
            finish();
            return;
        }
        Uri uri = data.getData();
        if (requestCode == REQUEST_CODE_PICK_ICON) {
            if (resultCode == RESULT_OK && uri != null) {
                String filename = HB.a(getApplicationContext(), uri);
                Bitmap bitmap = iB.a(filename, 96, 96);
                try {
                    int attributeInt = new ExifInterface(filename).getAttributeInt("Orientation", -1);
                    Bitmap newBitmap = iB.a(bitmap, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
                    appIcon.setImageBitmap(newBitmap);
                    saveBitmapTo(newBitmap, getCustomIconPath());
                    projectHasCustomIcon = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Bundle extras = data.getExtras();
            if (requestCode == REQUEST_CODE_PICK_CROPPED_ICON && resultCode == RESULT_OK && extras != null) {
                try {
                    Bitmap bitmap = extras.getParcelable("data");
                    appIcon.setImageBitmap(bitmap);
                    projectHasCustomIcon = true;
                    saveBitmapTo(bitmap, getCustomIconPath());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.advanced_setting) {
            showHideAdvancedSettings();
        } else if (id == R.id.app_icon_layout) {
            showCustomIconOptions();
        } else if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            mB.a(v);
            if (isInputValid()) {
                new SaveProjectAsyncTask(getApplicationContext()).execute();
            }
        } else if (id == R.id.img_theme_color_help) {
            if (colorGuide.getVisibility() == View.VISIBLE) {
                colorGuide.setVisibility(View.GONE);
            } else {
                colorGuide.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.ver_code || id == R.id.ver_name) {
            if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_USE_NEW_VERSION_CONTROL)) {
                new VersionDialog(this).show();
            } else {
                showOldVersionControlDialog();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myproject_setting);
        if (!j()) {
            finish();
        }
        sc_id = getIntent().getStringExtra("sc_id");
        updatingExistingProject = getIntent().getBooleanExtra("is_update", false);
        boolean expandAdvancedOptions = getIntent().getBooleanExtra("advanced_open", false);

        ((TextView) findViewById(R.id.tv_change_icon)).setText(Helper.getResString(R.string.myprojects_settings_description_change_icon));
        findViewById(R.id.contents).setOnClickListener(this);
        findViewById(R.id.app_icon_layout).setOnClickListener(this);
        findViewById(R.id.advanced_setting).setOnClickListener(this);
        projectVersionCodeView = findViewById(R.id.ver_code);
        projectVersionCodeView.setOnClickListener(this);
        projectVersionNameView = findViewById(R.id.ver_name);
        projectVersionNameView.setOnClickListener(this);
        TextInputLayout appName = findViewById(R.id.ti_app_name);
        TextInputLayout packageName = findViewById(R.id.ti_package_name);
        TextInputLayout projectName = findViewById(R.id.ti_project_name);
        appName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_application_name));
        packageName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_package_name));
        projectName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_project_name));
        projectAppName = findViewById(R.id.et_app_name);
        projectPackageName = findViewById(R.id.et_package_name);
        this.projectName = findViewById(R.id.et_project_name);
        ((TextView) findViewById(R.id.tv_advanced_settings)).setText(Helper.getResString(R.string.myprojects_settings_title_advanced_settings));
        appIcon = findViewById(R.id.app_icon);

        projectAppNameValidator = new LB(getApplicationContext(), appName);
        projectPackageNameValidator = new UB(getApplicationContext(), packageName);
        projectNameValidator = new VB(getApplicationContext(), projectName);
        projectPackageName.setPrivateImeOptions("defaultInputmode=english;");
        this.projectName.setPrivateImeOptions("defaultInputmode=english;");
        projectPackageName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!shownPackageNameChangeWarning && !((EditText) v).getText().toString().trim().contains("com.my.newproject")) {
                    showPackageNameChangeWarning();
                }
            }
        });
        themeColorsContainer = findViewById(R.id.layout_theme_colors);
        findViewById(R.id.img_theme_color_help).setOnClickListener(this);
        colorGuide = findViewById(R.id.img_color_guide);
        advancedSettingsContainer = findViewById(R.id.advanced_setting_layout);
        /* Save & Cancel buttons */
        r.setOnClickListener(this);
        s.setOnClickListener(this);

        projectThemeColors[0] = getResources().getColor(R.color.color_accent);
        projectThemeColors[1] = getResources().getColor(R.color.color_primary);
        projectThemeColors[2] = getResources().getColor(R.color.color_primary_dark);
        projectThemeColors[3] = getResources().getColor(R.color.color_control_highlight);
        projectThemeColors[4] = getResources().getColor(R.color.color_control_normal);
        for (int i = 0; i < themeColorKeys.length; i++) {
            ThemeColorView colorView = new ThemeColorView(getApplicationContext(), i);
            colorView.name.setText(themeColorLabels[i]);
            colorView.color.setBackgroundColor(Color.WHITE);
            themeColorsContainer.addView(colorView);
            colorView.setOnClickListener(v -> {
                if (!mB.a()) {
                    pickColor((Integer) v.getTag());
                }
            });
        }
        /* Set the cancel button's label */
        b(Helper.getResString(R.string.common_word_cancel));

        if (updatingExistingProject) {
            /* Set the dialog's title & save button label */
            e(Helper.getResString(R.string.myprojects_settings_actionbar_title_project_settings));
            d(Helper.getResString(R.string.myprojects_settings_button_save));

            HashMap<String, Object> metadata = lC.b(sc_id);
            projectPackageName.setText(yB.c(metadata, "my_sc_pkg_name"));
            this.projectName.setText(yB.c(metadata, "my_ws_name"));
            projectAppName.setText(yB.c(metadata, "my_app_name"));
            projectVersionCode = parseInt(yB.c(metadata, "sc_ver_code"), 1);
            parseVersion(yB.c(metadata, "sc_ver_name"));
            projectVersionCodeView.setText(yB.c(metadata, "sc_ver_code"));
            projectVersionNameView.setText(yB.c(metadata, "sc_ver_name"));
            projectHasCustomIcon = yB.a(metadata, "custom_icon");
            if (projectHasCustomIcon) {
                if (Build.VERSION.SDK_INT >= 24) {
                    appIcon.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon()));
                } else {
                    appIcon.setImageURI(Uri.fromFile(getCustomIcon()));
                }
            }

            for (int i = 0; i < themeColorKeys.length; i++) {
                projectThemeColors[i] = yB.a(metadata, themeColorKeys[i], projectThemeColors[i]);
            }
        } else {
            /* Set the dialog's title & create button label */
            e(Helper.getResString(R.string.myprojects_settings_actionbar_title_new_projet));
            d(Helper.getResString(R.string.myprojects_settings_button_create_app));

            String newProjectName = getIntent().getStringExtra("my_ws_name");
            String newProjectPackageName = getIntent().getStringExtra("my_sc_pkg_name");
            if (sc_id == null || sc_id.equals("")) {
                sc_id = lC.b();
                newProjectName = lC.c();
                newProjectPackageName = "com.my." + newProjectName.toLowerCase();
            }
            projectPackageName.setText(newProjectPackageName);
            this.projectName.setText(newProjectName);
            projectAppName.setText(getIntent().getStringExtra("my_app_name"));

            String newProjectVersionCode = getIntent().getStringExtra("sc_ver_code");
            String newProjectVersionName = getIntent().getStringExtra("sc_ver_name");
            if (newProjectVersionCode == null || newProjectVersionCode.isEmpty()) {
                newProjectVersionCode = "1";
            }
            if (newProjectVersionName == null || newProjectVersionName.isEmpty()) {
                newProjectVersionName = "1.0";
            }
            projectVersionCode = parseInt(newProjectVersionCode, 1);
            parseVersion(newProjectVersionName);
            projectVersionCodeView.setText(newProjectVersionCode);
            projectVersionNameView.setText(newProjectVersionName);
            projectHasCustomIcon = getIntent().getBooleanExtra("custom_icon", false);
            if (projectHasCustomIcon) {
                if (Build.VERSION.SDK_INT >= 24) {
                    appIcon.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon()));
                } else {
                    appIcon.setImageURI(Uri.fromFile(getCustomIcon()));
                }
            }
        }
        syncThemeColors();

        if (expandAdvancedOptions) {
            advancedSettingsContainer.setVisibility(View.VISIBLE);
            projectPackageName.requestFocus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!j()) {
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        oB oBVar = new oB();
        oBVar.f(wq.e() + File.separator + sc_id);
        oBVar.f(wq.g() + File.separator + sc_id);
        oBVar.f(wq.t() + File.separator + sc_id);
        oBVar.f(wq.d() + File.separator + sc_id);
        File o = getCustomIcon();
        if (!o.exists()) {
            try {
                o.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showOldVersionControlDialog() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.numbers_48);
        dialog.b(Helper.getResString(R.string.myprojects_settings_version_control_title));
        View view = wB.a(getApplicationContext(), R.layout.property_popup_version_control);
        ((TextView) view.findViewById(R.id.tv_code)).setText(Helper.getResString(R.string.myprojects_settings_version_control_title_code));
        ((TextView) view.findViewById(R.id.tv_name)).setText(Helper.getResString(R.string.myprojects_settings_version_control_title_name));

        NumberPicker versionCodePicker = view.findViewById(R.id.version_code);
        NumberPicker versionNameFirstPartPicker = view.findViewById(R.id.version_name1);
        NumberPicker versionNameSecondPartPicker = view.findViewById(R.id.version_name2);

        versionCodePicker.setWrapSelectorWheel(false);
        versionNameFirstPartPicker.setWrapSelectorWheel(false);
        versionNameSecondPartPicker.setWrapSelectorWheel(false);

        int versionCode = Integer.parseInt(projectVersionCodeView.getText().toString());
        int versionCodeMinimum = versionCode - 5;
        int versionNameFirstPartMinimum = 1;
        if (versionCodeMinimum <= 0) {
            versionCodeMinimum = 1;
        }
        versionCodePicker.setMinValue(versionCodeMinimum);
        versionCodePicker.setMaxValue(versionCode + 5);
        versionCodePicker.setValue(versionCode);

        String[] split = projectVersionNameView.getText().toString().split("\\.");
        AtomicInteger projectNewVersionNameFirstPart = new AtomicInteger(parseInt(split[0], 1));
        AtomicInteger projectNewVersionNameSecondPart = new AtomicInteger(parseInt(split[1], 0));
        if (projectNewVersionNameFirstPart.get() - 5 > 0) {
            versionNameFirstPartMinimum = projectNewVersionNameFirstPart.get() - 5;
        }
        versionNameFirstPartPicker.setMinValue(versionNameFirstPartMinimum);
        versionNameFirstPartPicker.setMaxValue(projectNewVersionNameFirstPart.get() + 5);
        versionNameFirstPartPicker.setValue(projectNewVersionNameFirstPart.get());

        versionNameSecondPartPicker.setMinValue(Math.max(projectNewVersionNameSecondPart.get() - 20, 0));
        versionNameSecondPartPicker.setMaxValue(projectNewVersionNameSecondPart.get() + 20);
        versionNameSecondPartPicker.setValue(projectNewVersionNameSecondPart.get());
        dialog.a(view);

        versionCodePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (oldVal > newVal && newVal < projectVersionCode) {
                picker.setValue(projectVersionCode);
            }
        });
        versionNameFirstPartPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            projectNewVersionNameFirstPart.set(newVal);
            if (oldVal > newVal) {
                if (newVal < projectVersionNameFirstPart) {
                    versionCodePicker.setValue(projectVersionNameFirstPart);
                }
                if (projectNewVersionNameFirstPart.get() == projectVersionNameFirstPart || projectNewVersionNameSecondPart.get() <= projectVersionNameSecondPart) {
                    versionNameSecondPartPicker.setValue(projectVersionNameSecondPart);
                    projectNewVersionNameSecondPart.set(projectVersionNameSecondPart);
                }
            }
        });
        versionNameSecondPartPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            projectNewVersionNameSecondPart.set(newVal);
            if (oldVal > newVal && newVal < projectVersionNameSecondPart && projectNewVersionNameFirstPart.get() < projectVersionNameFirstPart) {
                picker.setValue(projectVersionNameSecondPart);
            }
        });
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (!mB.a()) {
                projectVersionCodeView.setText(String.valueOf(versionCodePicker.getValue()));
                projectVersionNameView.setText(projectNewVersionNameFirstPart + "." + projectNewVersionNameSecondPart);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void showHideAdvancedSettings() {
        if (!advancedSettingsContainer.isShown()) {
            advancedSettingsContainer.setVisibility(View.VISIBLE);
            gB.b(advancedSettingsContainer, 300, null);
            return;
        }
        gB.a(advancedSettingsContainer, 300, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                advancedSettingsContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
    }

    private void syncThemeColors() {
        for (int i = 0; i < projectThemeColors.length; i++) {
            ((ThemeColorView) themeColorsContainer.getChildAt(i)).color.setBackgroundColor(projectThemeColors[i]);
        }
    }

    private void parseVersion(String toParse) {
        try {
            String[] split = toParse.split("\\.");
            projectVersionNameFirstPart = parseInt(split[0], 1);
            projectVersionNameSecondPart = parseInt(split[1], 0);
        } catch (Exception ignored) {
        }
    }

    private void pickColor(int colorIndex) {
        View view = wB.a(this, R.layout.color_picker);
        view.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
        Zx zx = new Zx(view, this, projectThemeColors[colorIndex], false, false);
        zx.a(pickedColor -> {
            projectThemeColors[colorIndex] = pickedColor;
            syncThemeColors();
        });
        zx.setAnimationStyle(R.anim.abc_fade_in);
        zx.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void showResetIconConfirmation() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_settings));
        dialog.a(R.drawable.default_icon);
        dialog.a(Helper.getResString(R.string.myprojects_settings_confirm_reset_icon));
        dialog.b(Helper.getResString(R.string.common_word_reset), v -> {
            appIcon.setImageResource(R.drawable.default_icon);
            projectHasCustomIcon = false;
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private File getCustomIcon() {
        return new File(getCustomIconPath());
    }

    private String getCustomIconPath() {
        return wq.e() + File.separator + sc_id + File.separator + "icon.png";
    }

    private boolean isInputValid() {
        return projectPackageNameValidator.b() && projectNameValidator.b() && projectAppNameValidator.b();
    }

    private void pickCustomIcon() {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon());
        } else {
            uri = Uri.fromFile(getCustomIcon());
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)),
                REQUEST_CODE_PICK_ICON);
    }

    private void pickAndCropCustomIcon() {
        Uri uri;
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            uri = FileProvider.a(applicationContext, getApplicationContext().getPackageName() + ".provider", getCustomIcon());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(getCustomIcon());
        }

        intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 96);
        intent.putExtra("outputY", 96);
        intent.putExtra("scale", true);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)),
                REQUEST_CODE_PICK_CROPPED_ICON);
    }

    private void showCustomIconOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Helper.getResString(R.string.myprojects_settings_context_menu_title_choose));
        builder.setItems(new String[]{
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery),
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery_with_crop),
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery_default)
        }, (dialog, which) -> {
            switch (which) {
                case 0:
                    pickCustomIcon();
                    break;

                case 1:
                    pickAndCropCustomIcon();
                    break;

                case 2:
                    if (projectHasCustomIcon) showResetIconConfirmation();
                    break;
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    private void showPackageNameChangeWarning() {
        shownPackageNameChangeWarning = true;
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.break_warning_96_red);
        dialog.a(Helper.getResString(R.string.myprojects_settings_message_package_rename));
        dialog.b(Helper.getResString(R.string.common_word_ok), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private int parseInt(String input, int fallback) {
        try {
            return Integer.parseInt(input);
        } catch (Exception unused) {
            return fallback;
        }
    }

    private void saveBitmapTo(Bitmap bitmap, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException ignored) {
        }
    }

    private static class ThemeColorView extends LinearLayout {

        private TextView color;
        private TextView name;

        public ThemeColorView(Context context, int tag) {
            super(context);
            initialize(context, tag);
        }

        private void initialize(Context context, int tag) {
            setTag(tag);
            wB.a(context, this, R.layout.myproject_color);
            color = findViewById(R.id.color);
            name = findViewById(R.id.name);
        }
    }

    private class SaveProjectAsyncTask extends MA {

        public SaveProjectAsyncTask(Context context) {
            super(context);
            MyProjectSettingActivity.this.a(this);
            k();
        }

        @Override
        public void a() {
            h();
            Intent intent = getIntent();
            intent.putExtra("sc_id", sc_id);
            intent.putExtra("is_new", !updatingExistingProject);
            intent.putExtra("index", intent.getIntExtra("index", -1));
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void b() {
            HashMap<String, Object> data = new HashMap<>();
            data.put("sc_id", sc_id);
            data.put("my_sc_pkg_name", projectPackageName.getText().toString());
            data.put("my_ws_name", projectName.getText().toString());
            data.put("my_app_name", projectAppName.getText().toString());
            if (updatingExistingProject) {
                data.put("custom_icon", projectHasCustomIcon);
                data.put("sc_ver_code", projectVersionCodeView.getText().toString());
                data.put("sc_ver_name", projectVersionNameView.getText().toString());
                data.put("sketchware_ver", GB.d(getApplicationContext()));
                for (int i = 0; i < themeColorKeys.length; i++) {
                    data.put(themeColorKeys[i], projectThemeColors[i]);
                }
                lC.b(sc_id, data);
            } else {
                data.put("my_sc_reg_dt", new nB().a("yyyyMMddHHmmss"));
                data.put("custom_icon", projectHasCustomIcon);
                data.put("sc_ver_code", projectVersionCodeView.getText().toString());
                data.put("sc_ver_name", projectVersionNameView.getText().toString());
                data.put("sketchware_ver", GB.d(getApplicationContext()));
                for (int i = 0; i < themeColorKeys.length; i++) {
                    data.put(themeColorKeys[i], projectThemeColors[i]);
                }
                lC.a(sc_id, data);
                wq.a(getApplicationContext(), sc_id);
                new oB().b(wq.b(sc_id));
            }
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
