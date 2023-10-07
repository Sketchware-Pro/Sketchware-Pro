package com.besome.sketch.projects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.MyprojectSettingBinding;

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
import a.a.a.iB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nB;
import a.a.a.oB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.yB;
import mod.SketchwareUtil;
import mod.hasrat.control.VersionDialog;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.ilyasse.AppUtils;

public class MyProjectSettingActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public MyprojectSettingBinding binding;

    private static final int REQUEST_CODE_PICK_CROPPED_ICON = 216;
    private static final int REQUEST_CODE_PICK_ICON = 207;
    private final String[] themeColorKeys = {"color_accent", "color_primary", "color_primary_dark", "color_control_highlight", "color_control_normal"};
    private final String[] themeColorLabels = {"colorAccent", "colorPrimary", "colorPrimaryDark", "colorControlHighlight", "colorControlNormal"};
    private final int[] projectThemeColors = new int[themeColorKeys.length];
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyprojectSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitle(R.string.myprojects_list_menu_title_create_a_new_project);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(arg0 -> onBackPressed());

        if (!isStoragePermissionGranted()) finish();

        sc_id = getIntent().getStringExtra("sc_id");
        updatingExistingProject = getIntent().getBooleanExtra("is_update", false);

        binding.contents.setOnClickListener(this);
        binding.appIconLayout.setOnClickListener(this);
        binding.verCode.setOnClickListener(this);
        binding.verName.setOnClickListener(this);
        binding.etAppName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_application_name));
        binding.etPackageName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_package_name));
        binding.etProjectName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_project_name));

        projectAppNameValidator = new LB(getApplicationContext(), binding.tiAppName);
        projectPackageNameValidator = new UB(getApplicationContext(), binding.tiPackageName);
        projectNameValidator = new VB(getApplicationContext(), binding.tiProjectName);
        binding.etPackageName.setPrivateImeOptions("defaultInputmode=english;");
        binding.etProjectName.setPrivateImeOptions("defaultInputmode=english;");
        binding.tiPackageName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (!shownPackageNameChangeWarning && !((EditText) v).getText().toString().trim().contains("com.my.newproject")) {
                    showPackageNameChangeWarning();
                }
            }
        });
        binding.imgThemeColorHelp.setOnClickListener(this);
        /* Save & Cancel buttons */
        binding.okButton.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);

        projectThemeColors[0] = getResources().getColor(R.color.color_accent);
        projectThemeColors[1] = getResources().getColor(R.color.color_primary);
        projectThemeColors[2] = getResources().getColor(R.color.color_primary_dark);
        projectThemeColors[3] = getResources().getColor(R.color.color_control_highlight);
        projectThemeColors[4] = getResources().getColor(R.color.color_control_normal);
        for (int i = 0; i < themeColorKeys.length; i++) {
            ThemeColorView colorView = new ThemeColorView(this, i);
            colorView.name.setText(themeColorLabels[i]);
            colorView.color.setBackgroundColor(Color.WHITE);
            binding.layoutThemeColors.addView(colorView);
            colorView.setOnClickListener(v -> {
                if (!mB.a()) {
                    pickColor((Integer) v.getTag());
                }
            });
        }
        if (updatingExistingProject) {
            /* Set the dialog's title & save button label */
            HashMap<String, Object> metadata = lC.b(sc_id);
            binding.etPackageName.setText(yB.c(metadata, "my_sc_pkg_name"));
            binding.etProjectName.setText(yB.c(metadata, "my_ws_name"));
            binding.etAppName.setText(yB.c(metadata, "my_app_name"));
            projectVersionCode = parseInt(yB.c(metadata, "sc_ver_code"), 1);
            parseVersion(yB.c(metadata, "sc_ver_name"));
            binding.verCode.setText(yB.c(metadata, "sc_ver_code"));
            binding.verName.setText(yB.c(metadata, "sc_ver_name"));
            projectHasCustomIcon = yB.a(metadata, "custom_icon");
            if (projectHasCustomIcon) {
                if (Build.VERSION.SDK_INT >= 24) {
                    binding.appIcon.setImageURI(FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon()));
                } else {
                    binding.appIcon.setImageURI(Uri.fromFile(getCustomIcon()));
                }
            }

            for (int i = 0; i < themeColorKeys.length; i++) {
                projectThemeColors[i] = yB.a(metadata, themeColorKeys[i], projectThemeColors[i]);
            }
        } else {
            /* Set the dialog's title & create button label */
            String newProjectName = getIntent().getStringExtra("my_ws_name");
            String newProjectPackageName = getIntent().getStringExtra("my_sc_pkg_name");
            if (sc_id == null || sc_id.equals("")) {
                sc_id = lC.b();
                newProjectName = lC.c();
                newProjectPackageName = "com.my." + newProjectName.toLowerCase();
            }
            binding.etPackageName.setText(newProjectPackageName);
            binding.etProjectName.setText(newProjectName);
            binding.etAppName.setText(getIntent().getStringExtra("my_app_name"));

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
            binding.verCode.setText(newProjectVersionCode);
            binding.verName.setText(newProjectVersionName);
            projectHasCustomIcon = getIntent().getBooleanExtra("custom_icon", false);
            if (projectHasCustomIcon) {
                if (Build.VERSION.SDK_INT >= 24) {
                    binding.appIcon.setImageURI(FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon()));
                } else {
                    binding.appIcon.setImageURI(Uri.fromFile(getCustomIcon()));
                }
            }
        }
        syncThemeColors();
    }

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
                    binding.appIcon.setImageBitmap(newBitmap);
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
                    binding.appIcon.setImageBitmap(bitmap);
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
        if (id == R.id.app_icon_layout) {
            showCustomIconOptions();
        } else if (id == R.id.ok_button) {
            mB.a(v);
            if (isInputValid()) {
                new SaveProjectAsyncTask(getApplicationContext()).execute();
            }
        } else if (id == R.id.cancel) {
            finish();
        } else if (id == R.id.img_theme_color_help) {
            AppUtils.animateLayoutChanges(binding.getRoot());
            if (binding.imgColorGuide.getVisibility() == View.VISIBLE) {
                binding.imgColorGuide.setVisibility(View.GONE);
            } else {
                binding.imgColorGuide.setVisibility(View.VISIBLE);
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
    public void onResume() {
        super.onResume();

        if (!isStoragePermissionGranted()) {
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

        int versionCode = Integer.parseInt(binding.verCode.getText().toString());
        int versionCodeMinimum = versionCode - 5;
        int versionNameFirstPartMinimum = 1;
        if (versionCodeMinimum <= 0) {
            versionCodeMinimum = 1;
        }
        versionCodePicker.setMinValue(versionCodeMinimum);
        versionCodePicker.setMaxValue(versionCode + 5);
        versionCodePicker.setValue(versionCode);

        String[] split = binding.verName.getText().toString().split("\\.");
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
                binding.verCode.setText(String.valueOf(versionCodePicker.getValue()));
                binding.verName.setText(projectNewVersionNameFirstPart + "." + projectNewVersionNameSecondPart);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void syncThemeColors() {
        for (int i = 0; i < projectThemeColors.length; i++) {
            ((ThemeColorView) binding.layoutThemeColors.getChildAt(i)).color.setBackgroundColor(projectThemeColors[i]);
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
            binding.appIcon.setImageResource(R.drawable.default_icon);
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
            uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", getCustomIcon());
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
            uri = FileProvider.getUriForFile(applicationContext, getApplicationContext().getPackageName() + ".provider", getCustomIcon());
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
                case 0 -> pickCustomIcon();
                case 1 -> pickAndCropCustomIcon();
                case 2 -> {
                    if (projectHasCustomIcon) showResetIconConfirmation();
                }
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
            addTask(this);
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
            data.put("my_sc_pkg_name", binding.etPackageName.getText().toString());
            data.put("my_ws_name", binding.etProjectName.getText().toString());
            data.put("my_app_name", binding.etAppName.getText().toString());
            if (updatingExistingProject) {
                data.put("custom_icon", projectHasCustomIcon);
                data.put("sc_ver_code", binding.verCode.getText().toString());
                data.put("sc_ver_name", binding.verName.getText().toString());
                data.put("sketchware_ver", GB.d(getApplicationContext()));
                for (int i = 0; i < themeColorKeys.length; i++) {
                    data.put(themeColorKeys[i], projectThemeColors[i]);
                }
                lC.b(sc_id, data);
            } else {
                data.put("my_sc_reg_dt", new nB().a("yyyyMMddHHmmss"));
                data.put("custom_icon", projectHasCustomIcon);
                data.put("sc_ver_code", binding.verCode.getText().toString());
                data.put("sc_ver_name", binding.verName.getText().toString());
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
