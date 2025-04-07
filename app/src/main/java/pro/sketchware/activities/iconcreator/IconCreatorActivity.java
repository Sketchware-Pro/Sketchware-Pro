package pro.sketchware.activities.iconcreator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.ColorPickerDialog;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

import a.a.a.HB;
import a.a.a.iB;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ActivityIconCreatorBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

public class IconCreatorActivity extends BaseAppCompatActivity {

    private final int REQUEST_CODE_PICK_CROPPED_ICON = 216;
    private final int REQUEST_CODE_PICK_ICON = 207;
    private float cardRadius = 20;
    private ActivityIconCreatorBinding binding;
    private GradientDrawable.Orientation gradDirection = GradientDrawable.Orientation.BOTTOM_TOP;
    private int bgClr = 0xffffff;
    private int badgeClr = 0x000000;
    private int txtClr = 0x000000;
    private int badgeTxtClr = 0xffffff;
    private int gradClr0 = 0xffffff;
    private int gradClr1 = 0x000000;
    private int imgColor = 0xffffff;
    private int patternColor = 0xffffff;
    private int selectedTextureType;
    private boolean eff_score;
    private boolean eff_texture;
    private String sc_id;
    private String iconFilePath;
    private String texturesFilePath;
    private Bitmap appIconBitmap;

    private void saveBitmapTo(Bitmap bitmap, String path) {
        FileUtil.makeDir(path.substring(0, path.lastIndexOf(File.separator)));
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException ignored) {

        }
    }

    private Bitmap captureAppIco(View view) {
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);


        Path path = new Path();
        RectF rect = new RectF(0, 0, view.getWidth(), view.getHeight());
        path.addRoundRect(rect, cardRadius, cardRadius, Path.Direction.CW);
        canvas.clipPath(path);
        view.draw(canvas);

        return bitmap;
    }

    private Bitmap captureForeground(View view, boolean score, boolean pattern, View pattView, View scoreView, View bg) {

        bg.setVisibility(View.INVISIBLE);
        if (!score) scoreView.setVisibility(View.INVISIBLE);
        if (!pattern) pattView.setVisibility(View.INVISIBLE);

        int padding = 75;

        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();

        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            view.draw(canvas);
        }


        int paddedSize = padding * 2;
        Bitmap paddedBitmap = Bitmap.createBitmap(bitmap.getWidth() + paddedSize, bitmap.getHeight() + paddedSize, Bitmap.Config.ARGB_8888);

        Canvas paddedCanvas = new Canvas(paddedBitmap);
        paddedCanvas.drawBitmap(bitmap, padding, padding, null);

        bg.setVisibility(View.VISIBLE);
        if (score) scoreView.setVisibility(View.VISIBLE);
        if (pattern) pattView.setVisibility(View.VISIBLE);

        bitmap.recycle();

        return paddedBitmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIconCreatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    private void initialize() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        sc_id = getIntent().getStringExtra("sc_id");

        binding.appIcoBadge.setVisibility(View.GONE);
        binding.linearGrad.setVisibility(View.GONE);
        binding.appIcoScore.setVisibility(View.GONE);
        binding.textureCont.setVisibility(View.GONE);
        binding.appIcoTexture.setVisibility(View.GONE);


        binding.toolbar.setNavigationOnClickListener(_v -> onBackPressed());

        binding.colorPreviewCard.setOnClickListener(v -> showColorPicker(v, 0, bgClr));
        binding.gradColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 1, gradClr0));
        binding.gradColorPreviewCard2.setOnClickListener(v -> showColorPicker(v, 2, gradClr1));
        binding.txtColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 3, txtClr));
        binding.badgeTxtColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 4, badgeTxtClr));
        binding.badgeBgColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 5, badgeClr));
        binding.imgColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 6, imgColor));
        binding.patternColorPreviewCard.setOnClickListener(v -> showColorPicker(v, 7, patternColor));

        binding.cancel.setOnClickListener(v -> onBackPressed());

        binding.scoreCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.appIcoScore.setVisibility(View.VISIBLE);
                eff_score = true;
            } else {
                binding.appIcoScore.setVisibility(View.GONE);
                eff_score = false;
            }
        });

        binding.textureCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.appIcoTexture.setVisibility(View.VISIBLE);
                binding.textureCont.setVisibility(View.VISIBLE);
                eff_texture = true;
            } else {
                binding.appIcoTexture.setVisibility(View.GONE);
                binding.textureCont.setVisibility(View.GONE);
                eff_texture = false;
            }
            UI.animateLayoutChanges(binding.getRoot());
        });

        binding.appIcoItems.setOnClickListener(v -> showCustomIconOptions());

        binding.save.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyProjectSettingActivity.class);
            saveChanges();
            saveIconToRes();
            intent.putExtra("appIco", captureAppIco(binding.appIcoCard));
            if (binding.adaptiveCheck.isChecked()) {
                intent.putExtra("isIconAdaptive", binding.adaptiveCheck.isChecked());
                saveForegroundToRes();
            }

            setResult(RESULT_OK, intent);
            finish();
        });

        binding.bgType.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.bg_color) {
                    binding.appIcoBg.setBackgroundColor(bgClr);
                    binding.linearClr.setVisibility(View.VISIBLE);
                    binding.linearGrad.setVisibility(View.GONE);
                } else {
                    binding.appIcoBg.setBackground(new GradientDrawable(gradDirection, new int[]{gradClr0, gradClr1}));
                    binding.linearGrad.setVisibility(View.VISIBLE);
                    binding.linearClr.setVisibility(View.GONE);
                }
                UI.animateLayoutChanges(binding.getRoot());
            }
        });

        binding.textureSelect.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.texture1) {
                    loadSelectedTexture(0);
                }

                if (checkedId == R.id.texture2) {
                    loadSelectedTexture(1);
                }

                if (checkedId == R.id.texture3) {
                    loadSelectedTexture(2);
                }
                if (checkedId != R.id.texture4) {
                    texturesFilePath = null;
                }
            }
        });

        binding.texture4.setOnClickListener(view -> pickCustomIcon(712));

        binding.cornersSlider.addOnChangeListener((slider, value, fromUser) -> {
            binding.appIcoCard.setRadius(value);
            cardRadius = value;
        });

        binding.imgSizeSlider.addOnChangeListener((slider, value, fromUser) -> scaleView(binding.appIcoImg, (int) value));

        binding.imgVerticalSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoImg.setTranslationY(value));

        binding.imgHorizontalSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoImg.setTranslationX(value));

        binding.txtSizeSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoText.setTextSize(value));

        binding.txtVerticalSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoText.setTranslationY(value));

        binding.txtHorizontalSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoText.setTranslationX(value));

        binding.textureAlphaSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoTexture.setOpacity((int) value));

        binding.textureScaleSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoTexture.setScale(value, value));

        binding.textureRotationSlider.addOnChangeListener((slider, value, fromUser) -> binding.appIcoTexture.setRotation(value));


        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checked = checkedIds.get(0);
                if (checked == R.id.chip_trbl) {
                    gradDirection = GradientDrawable.Orientation.TR_BL;
                } else if (checked == R.id.chip_lt) {
                    gradDirection = GradientDrawable.Orientation.LEFT_RIGHT;
                } else if (checked == R.id.chip_top) {
                    gradDirection = GradientDrawable.Orientation.BOTTOM_TOP;
                } else if (checked == R.id.chip_bltr) {
                    gradDirection = GradientDrawable.Orientation.BL_TR;
                } else if (checked == R.id.chip_rt) {
                    gradDirection = GradientDrawable.Orientation.RIGHT_LEFT;
                } else if (checked == R.id.chip_tlrb) {
                    gradDirection = GradientDrawable.Orientation.TL_BR;
                } else if (checked == R.id.chip_bt) {
                    gradDirection = GradientDrawable.Orientation.TOP_BOTTOM;
                } else {
                    gradDirection = GradientDrawable.Orientation.TR_BL;
                }
            }

            binding.appIcoBg.setBackground(new GradientDrawable(gradDirection, new int[]{gradClr0, gradClr1}));
        });

        binding.textValueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.appIcoText.setVisibility(View.GONE);
                } else {
                    binding.appIcoText.setVisibility(View.VISIBLE);
                    binding.appIcoText.setText(s);
                }
                UI.animateLayoutChanges(binding.getRoot());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.badgeValueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.appIcoBadge.setVisibility(View.GONE);
                } else {
                    binding.appIcoBadge.setVisibility(View.VISIBLE);
                    binding.badgeText.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadSavedData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            SketchwareUtil.toastError("Received invalid data");
            return;
        }
        Uri uri = data.getData();
        if (requestCode == REQUEST_CODE_PICK_ICON) {
            if (resultCode == RESULT_OK && uri != null) {
                iconFilePath = HB.a(getApplicationContext(), uri);
                appIconBitmap = null;
                Bitmap bitmap = iB.a(iconFilePath, 96, 96);
                try {
                    int attributeInt = new ExifInterface(iconFilePath).getAttributeInt("Orientation", -1);
                    Bitmap newBitmap = iB.a(bitmap, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
                    BitmapDrawable bd = new BitmapDrawable(getResources(), newBitmap);
                    binding.appIcoImg.setBackground(bd);
                } catch (Exception ignored) {

                }
            }
        } else {
            Bundle extras = data.getExtras();
            if (requestCode == REQUEST_CODE_PICK_CROPPED_ICON && resultCode == RESULT_OK && extras != null) {
                try {
                    appIconBitmap = extras.getParcelable("data");
                    iconFilePath = null;
                    BitmapDrawable bd = new BitmapDrawable(getResources(), appIconBitmap);
                    binding.appIcoImg.setBackground(bd);
                } catch (Exception ignored) {
                }
            }

            if (requestCode == 712) {
                if (resultCode == RESULT_OK && uri != null) {
                    setAppIcoTexture(HB.a(getApplicationContext(), uri));
                }
            }
        }
    }

    private void setAppIcoTexture(String texturesFilePath) {
        this.texturesFilePath = texturesFilePath;
        Bitmap bitmap = iB.a(texturesFilePath, 96, 96);
        try {
            int attributeInt = new ExifInterface(texturesFilePath).getAttributeInt("Orientation", -1);
            Bitmap newBitmap = iB.a(bitmap, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
            binding.appIcoTexture.setPattern(newBitmap);
            selectedTextureType = 3;
        } catch (Exception ignored) {

        }
    }

    private void showColorPicker(View v, int r, int oldClr) {
        ColorPickerDialog colorPicker = new ColorPickerDialog(this, oldClr, true, false);

        colorPicker.a(new ColorPickerDialog.b() {
            @Override
            public void a(int colorInt) {
                switch (r) {
                    case (0):
                        bgClr = colorInt;
                        binding.appIcoBg.setBackgroundColor(bgClr);
                        binding.colorPreview.setBackgroundColor(bgClr);
                        break;
                    case (1):
                        gradClr0 = colorInt;
                        binding.appIcoBg.setBackground(new GradientDrawable(gradDirection, new int[]{gradClr0, gradClr1}));
                        binding.gradColorPreview.setBackgroundColor(gradClr0);
                        break;
                    case (2):
                        gradClr1 = colorInt;
                        binding.appIcoBg.setBackground(new GradientDrawable(gradDirection, new int[]{gradClr0, gradClr1}));
                        binding.gradColorPreview2.setBackgroundColor(gradClr1);
                        break;
                    case (3):
                        txtClr = colorInt;
                        binding.appIcoText.setTextColor(txtClr);
                        binding.txtColorPreview.setBackgroundColor(txtClr);
                        break;
                    case (4):
                        badgeTxtClr = colorInt;
                        binding.badgeText.setTextColor(badgeTxtClr);
                        binding.badgeTxtColorPreview.setBackgroundColor(badgeTxtClr);
                        break;
                    case (5):
                        badgeClr = colorInt;
                        binding.appIcoBadge.setBackgroundColor(badgeClr);
                        binding.badgeBgColorPreview.setBackgroundColor(badgeClr);
                        break;
                    case (6):
                        imgColor = colorInt;
                        binding.appIcoImg.getBackground().setColorFilter(new PorterDuffColorFilter(imgColor, PorterDuff.Mode.SRC_ATOP));
                        binding.imgColorPreview.setBackgroundColor(imgColor);
                        break;

                    case (7):
                        patternColor = colorInt;
                        binding.appIcoTexture.setColor(patternColor);
                        binding.patternColorPreview.setBackgroundColor(patternColor);
                        break;
                }

            }

            @Override
            public void a(String var1, int var2) {
            }
        });
        colorPicker.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    private void scaleView(View v, int var) {
        v.getLayoutParams().height = var;
        v.getLayoutParams().width = var;
        v.requestLayout();
    }

    private File getCustomIcon() {
        return new File(getCustomIconPath());
    }

    private String getCustomIconPath() {
        return wq.e() + File.separator + sc_id + File.separator + "icon.png";
    }

    private void pickCustomIcon(int code) {
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
                code);
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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(Helper.getResString(R.string.myprojects_settings_context_menu_title_choose));
        builder.setItems(new String[]{
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery),
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery_with_crop),
                Helper.getResString(R.string.myprojects_settings_context_menu_title_choose_gallery_default)
        }, (dialog, which) -> {
            switch (which) {
                case 0 -> pickCustomIcon(REQUEST_CODE_PICK_ICON);
                case 1 -> pickAndCropCustomIcon();
                case 2 -> binding.appIcoImg.setBackgroundResource(R.drawable.default_image);
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    private void saveIconToRes() {
        saveBitmapTo(captureAppIco(binding.appIcoCard), getIconPath("mipmap-hdpi", "ic_launcher.png"));
        saveBitmapTo(captureAppIco(binding.appIcoCard), getIconPath("mipmap-mdpi", "ic_launcher.png"));
        saveBitmapTo(captureAppIco(binding.appIcoCard), getIconPath("mipmap-xhdpi", "ic_launcher.png"));
        saveBitmapTo(captureAppIco(binding.appIcoCard), getIconPath("mipmap-xxhdpi", "ic_launcher.png"));
        saveBitmapTo(captureAppIco(binding.appIcoCard), getIconPath("mipmap-xxxhdpi", "ic_launcher.png"));

    }

    private void saveForegroundToRes() {
        saveBitmapTo(captureForeground(binding.appIcoItems, eff_score, eff_texture, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-mdpi", "ic_launcher_foreground.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, eff_score, eff_texture, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-hdpi", "ic_launcher_foreground.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, eff_score, eff_texture, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xhdpi", "ic_launcher_foreground.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, eff_score, eff_texture, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xxhdpi", "ic_launcher_foreground.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, eff_score, eff_texture, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xxxhdpi", "ic_launcher_foreground.png"));

        saveBitmapTo(captureForeground(binding.appIcoItems, false, false, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-mdpi", "ic_launcher_monochrome.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, false, false, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-hdpi", "ic_launcher_monochrome.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, false, false, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xhdpi", "ic_launcher_monochrome.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, false, false, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xxhdpi", "ic_launcher_monochrome.png"));
        saveBitmapTo(captureForeground(binding.appIcoItems, false, false, binding.appIcoTexture, binding.appIcoScore, binding.appIcoBg), getIconPath("mipmap-xxxhdpi", "ic_launcher_monochrome.png"));

        saveBitmapTo(captureAppIco(binding.appIcoBg), getIconPath("mipmap-mdpi", "ic_launcher_background.png"));
        saveBitmapTo(captureAppIco(binding.appIcoBg), getIconPath("mipmap-hdpi", "ic_launcher_background.png"));
        saveBitmapTo(captureAppIco(binding.appIcoBg), getIconPath("mipmap-xhdpi", "ic_launcher_background.png"));
        saveBitmapTo(captureAppIco(binding.appIcoBg), getIconPath("mipmap-xxhdpi", "ic_launcher_background.png"));
        saveBitmapTo(captureAppIco(binding.appIcoBg), getIconPath("mipmap-xxxhdpi", "ic_launcher_background.png"));

    }

    private void loadSelectedTexture(int selectedTextureType) {
        this.selectedTextureType = selectedTextureType;

        switch (selectedTextureType) {
            case 0 -> binding.appIcoTexture.setPatternFromRes(R.drawable.pattern_tech);
            case 1 -> binding.appIcoTexture.setPatternFromRes(R.drawable.pattern_seq);
            case 2 -> binding.appIcoTexture.setPatternFromRes(R.drawable.pattern_waves);
        }
    }

    private void saveChanges() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("gradient_background_type", binding.bgGradient.isChecked());
        data.put("background_color", bgClr);
        data.put("gradient_color1", gradClr0);
        data.put("gradient_color2", gradClr1);
        data.put("gradient_direction", gradDirection);
        data.put("score_effect", binding.scoreCheck.isChecked());
        data.put("textures", binding.textureCheck.isChecked());
        data.put("selected_texture", selectedTextureType);
        data.put("texture_color", patternColor);
        data.put("opacity", binding.textureAlphaSlider.getValue());
        data.put("scale", binding.textureScaleSlider.getValue());
        data.put("rotation", binding.textureRotationSlider.getValue());
        data.put("corners", binding.cornersSlider.getValue());
        data.put("icon_color_filter", imgColor);
        data.put("icon_size", binding.imgSizeSlider.getValue());
        data.put("icon_vp", binding.imgVerticalSlider.getValue());
        data.put("icon_hp", binding.imgHorizontalSlider.getValue());
        data.put("text", binding.textValueInput.getText().toString());
        data.put("text_color", txtClr);
        data.put("text_size", binding.txtSizeSlider.getValue());
        data.put("text_vp", binding.txtVerticalSlider.getValue());
        data.put("text_hp", binding.txtHorizontalSlider.getValue());
        data.put("badge", binding.badgeValueInput.getText().toString());
        data.put("badge_text_color", badgeTxtClr);
        data.put("badge_bg_color", badgeClr);
        data.put("adaptive_icon", binding.adaptiveCheck.isChecked());
        FileUtil.writeFile(baseDir() + "data.json", new Gson().toJson(data));
        saveIconsToStorage();
    }

    public void saveIconsToStorage() {
        if (texturesFilePath != null && !texturesFilePath.equals(baseDir() + "texture.png")) {
            FileUtil.copyFile(texturesFilePath, baseDir() + "texture.png");
        }
        if (iconFilePath != null && !iconFilePath.equals(baseDir() + "app_icon.png")) {
            FileUtil.copyFile(iconFilePath, baseDir() + "app_icon.png");
        }
        if (appIconBitmap != null) {
            try {
                String path = baseDir() + "app_icon.png";
                File file = new File(path);
                file.getParentFile().mkdirs();

                FileOutputStream fos = new FileOutputStream(file);
                appIconBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

                iconFilePath = path;

            } catch (Exception ignored) {
            }
        }
    }

    private void loadSavedData() {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> data = new Gson().fromJson(FileUtil.readFileIfExist(baseDir() + "data.json"), type);

        if (data == null) return;

        if (data.containsKey("gradient_background_type") && data.get("gradient_background_type") instanceof Boolean state) {
            binding.bgGradient.setChecked(state);
        }
        if (data.containsKey("background_color") && data.get("background_color") instanceof Double value) {
            bgClr = value.intValue();
        }
        if (data.containsKey("gradient_color1") && data.get("gradient_color1") instanceof Double value) {
            gradClr0 = value.intValue();
        }
        if (data.containsKey("gradient_color2") && data.get("gradient_color2") instanceof Double value) {
            gradClr1 = value.intValue();
        }
        if (data.containsKey("gradient_direction") && data.get("gradient_direction") instanceof String value) {
            switch (value) {
                case "TR_BL" -> binding.chipTrbl.setChecked(true);
                case "LEFT_RIGHT" -> binding.chipLt.setChecked(true);
                case "BOTTOM_TOP" -> binding.chipTop.setChecked(true);
                case "BL_TR" -> binding.chipBltr.setChecked(true);
                case "RIGHT_LEFT" -> binding.chipRt.setChecked(true);
                case "TL_BR" -> binding.chipTlrb.setChecked(true);
                case "TOP_BOTTOM" -> binding.chipBt.setChecked(true);
            }
        }
        if (data.containsKey("score_effect") && data.get("score_effect") instanceof Boolean value) {
            binding.scoreCheck.setChecked(value);
        }
        if (data.containsKey("textures") && data.get("textures") instanceof Boolean value) {
            binding.textureCheck.setChecked(value);
        }
        if (data.containsKey("selected_texture") && data.get("selected_texture") instanceof Double value) {
            switch (value.intValue()) {
                case 0 -> binding.texture1.setChecked(true);
                case 1 -> binding.texture2.setChecked(true);
                case 2 -> binding.texture3.setChecked(true);
                case 3 -> {
                    String textureImgFilePath = baseDir() + "texture.png";
                    if (FileUtil.isExistFile(textureImgFilePath)) {
                        binding.texture4.setChecked(true);
                        setAppIcoTexture(textureImgFilePath);
                    }
                }
            }
        }
        if (data.containsKey("texture_color") && data.get("texture_color") instanceof Double value) {
            patternColor = value.intValue();
        }
        if (data.containsKey("opacity") && data.get("opacity") instanceof Double value) {
            binding.textureAlphaSlider.setValue(value.floatValue());
        }
        if (data.containsKey("scale") && data.get("scale") instanceof Double value) {
            binding.textureScaleSlider.setValue(value.floatValue());
        }
        if (data.containsKey("rotation") && data.get("rotation") instanceof Double value) {
            binding.textureRotationSlider.setValue(value.floatValue());
        }
        if (data.containsKey("corners") && data.get("corners") instanceof Double value) {
            binding.cornersSlider.setValue(value.floatValue());
        }
        if (data.containsKey("icon_color_filter") && data.get("icon_color_filter") instanceof Double value) {
            imgColor = value.intValue();
        }
        if (data.containsKey("icon_size") && data.get("icon_size") instanceof Double value) {
            binding.imgSizeSlider.setValue(value.floatValue());
        }
        if (data.containsKey("icon_vp") && data.get("icon_vp") instanceof Double value) {
            binding.imgVerticalSlider.setValue(value.floatValue());
        }
        if (data.containsKey("icon_hp") && data.get("icon_hp") instanceof Double value) {
            binding.imgHorizontalSlider.setValue(value.floatValue());
        }
        if (data.containsKey("text") && data.get("text") instanceof String value) {
            binding.textValueInput.setText(value);
        }
        if (data.containsKey("text_color") && data.get("text_color") instanceof Double value) {
            txtClr = value.intValue();
        }
        if (data.containsKey("text_size") && data.get("text_size") instanceof Double value) {
            binding.txtSizeSlider.setValue(value.floatValue());
        }
        if (data.containsKey("text_vp") && data.get("text_vp") instanceof Double value) {
            binding.txtVerticalSlider.setValue(value.floatValue());
        }
        if (data.containsKey("text_hp") && data.get("text_hp") instanceof Double value) {
            binding.txtHorizontalSlider.setValue(value.floatValue());
        }
        if (data.containsKey("badge") && data.get("badge") instanceof String value) {
            binding.badgeValueInput.setText(value);
        }
        if (data.containsKey("badge_text_color") && data.get("badge_text_color") instanceof Double value) {
            badgeTxtClr = value.intValue();
        }
        if (data.containsKey("badge_bg_color") && data.get("badge_bg_color") instanceof Double value) {
            badgeClr = value.intValue();
        }
        if (data.containsKey("adaptive_icon") && data.get("adaptive_icon") instanceof Boolean value) {
            binding.adaptiveCheck.setChecked(value);
        }

        if (binding.bgGradient.isChecked()) {
            binding.appIcoBg.setBackground(new GradientDrawable(gradDirection, new int[]{gradClr0, gradClr1}));
        } else {
            binding.appIcoBg.setBackgroundColor(bgClr);
        }
        binding.colorPreview.setBackgroundColor(bgClr);
        binding.gradColorPreview.setBackgroundColor(gradClr0);
        binding.gradColorPreview2.setBackgroundColor(gradClr1);
        binding.appIcoTexture.setColor(patternColor);
        binding.patternColorPreview.setBackgroundColor(patternColor);
        binding.appIcoImg.getBackground().setColorFilter(new PorterDuffColorFilter(imgColor, PorterDuff.Mode.SRC_ATOP));
        binding.imgColorPreview.setBackgroundColor(imgColor);
        binding.appIcoText.setTextColor(txtClr);
        binding.txtColorPreview.setBackgroundColor(txtClr);
        binding.badgeText.setTextColor(badgeTxtClr);
        binding.badgeTxtColorPreview.setBackgroundColor(badgeTxtClr);
        binding.appIcoBadge.setBackgroundColor(badgeClr);
        binding.badgeBgColorPreview.setBackgroundColor(badgeClr);

        String textureImgFilePath = baseDir() + "texture.png";
        if (FileUtil.isExistFile(textureImgFilePath)) {
            setAppIcoTexture(textureImgFilePath);
        }

    }

    private String baseDir() {
        return wq.b(sc_id) + File.separator + "files" + File.separator + "app-icon" + File.separator;
    }

    private String getIconPath(String folder, String fileName) {
        return wq.e() + File.separator + sc_id + File.separator + "temp_icons" + File.separator + folder + File.separator + fileName;
    }
}
