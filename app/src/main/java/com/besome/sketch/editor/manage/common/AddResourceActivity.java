package com.besome.sketch.editor.manage.common;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.besome.sketch.beans.CollectionBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.common.preview.FontPreviewController;
import com.besome.sketch.editor.manage.common.preview.ImagePreviewController;
import com.besome.sketch.editor.manage.common.preview.SoundPreviewController;
import com.besome.sketch.lib.base.BaseDialogActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

import a.a.a.By;
import a.a.a.HB;
import a.a.a.MA;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.PB;
import a.a.a.Qp;
import a.a.a.ResNameValidator;
import a.a.a.bB;
import a.a.a.iB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.yy;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageResourceAddBinding;
import pro.sketchware.lib.validator.FontNameValidator;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.SketchwareUtil;

/**
 * Unified dialog activity for adding or editing a project resource (Image, Sound, or Font).
 * Replaces AddImageActivity, AddSoundActivity, and AddFontActivity.
 */
public class AddResourceActivity extends BaseDialogActivity implements View.OnClickListener {

    public ManageResourceAddBinding binding;

    private ResourceType resourceType;
    private String sc_id;
    private String dirPath = "";
    private boolean editing = false;
    private boolean isCollectionMode = false;

    // Preview Controllers
    private ImagePreviewController imagePreviewController;
    private SoundPreviewController soundPreviewController;
    private FontPreviewController fontPreviewController;

    // Validators per type
    private PB imageValidator;
    private ResNameValidator soundValidator;
    private FontNameValidator fontValidator;

    // Image state
    private ArrayList<ProjectResourceBean> existingImages;
    private ArrayList<ProjectResourceBean> savedImagesResult;
    private ArrayList<Uri> pickedImageUris;
    private boolean multipleImagesPicked = false;
    private boolean newImagePicked = false;
    private ProjectResourceBean editingImageBean = null;

    // Multi-image state: one editable draft name per picked image, plus the
    // optional common base name applied to all of them.
    private static final Pattern MULTI_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");
    private ArrayList<String> multiDraftNames;
    private ArrayList<String> multiNames;
    private String multiCommonBaseName = null;
    private boolean multiUsingCommonBaseName = false;
    private int multiSelectedIndex = 0;
    private ImagePreviewController multiPreviewController;

    // Sound state
    private Uri soundUri = null;
    private ProjectResourceBean editingSoundBean = null;

    // Font state
    private Uri fontUri = null;
    private ProjectResourceBean editingFontBean = null;
    private boolean validFontPicked = false;
    private int fontPickGeneration = 0;

    // ---------------------------------------------------------------------
    // Activity Result Launchers
    // ---------------------------------------------------------------------

    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                enablePickerTriggers();
                Intent data = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK && data != null) {
                    switch (resourceType) {
                        case IMAGE -> handleImagePickedResult(data);
                        case SOUND -> {
                            if (data.getData() != null) {
                                handleSoundPickedResult(data.getData(), false);
                            }
                        }
                        case FONT -> {
                            if (data.getData() != null) {
                                handleFontPickedResult(data.getData());
                            }
                        }
                    }
                }
            });

    // ---------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageResourceAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        d(getString(R.string.common_word_save));
        b(getString(R.string.common_word_cancel));
        r.setOnClickListener(this);
        s.setOnClickListener(this);

        Intent intent = getIntent();
        resourceType = ResourceType.fromIntent(intent);
        sc_id = intent.getStringExtra("sc_id");
        dirPath = intent.getStringExtra("dir_path");
        isCollectionMode = intent.getBooleanExtra(ResourceType.EXTRA_COLLECTION_MODE, false);

        if (isCollectionMode) {
            binding.chkCollection.setVisibility(View.GONE);
        }

        LayoutInflater inflater = getLayoutInflater();
        switch (resourceType) {
            case IMAGE -> initImageMode(intent, inflater);
            case SOUND -> initSoundMode(intent, inflater);
            case FONT -> initFontMode(intent, inflater);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (resourceType == ResourceType.IMAGE && editing && editingImageBean != null) {
            e(getString(R.string.design_manager_image_title_edit_image));
            if (!isCollectionMode) {
                editingImageBean.isEdited = true;
                imagePreviewController.setTransformations(
                        editingImageBean.rotate,
                        editingImageBean.flipHorizontal,
                        editingImageBean.flipVertical
                );
            }

            imageValidator = new PB(this, binding.tiInput, uq.b, getReservedImageNames(), editingImageBean.resName);
            imageValidator.a(1);

            binding.edInput.setText(editingImageBean.resName);
            if (!isCollectionMode) {
                binding.edInput.setEnabled(false);
                binding.chkCollection.setEnabled(false);
            }

            String targetPath = isCollectionMode
                    ? getCollectionResourcePath("image", editingImageBean)
                    : (editingImageBean.savedPos == 0
                        ? dirPath + File.separator + editingImageBean.resFullName
                        : editingImageBean.resFullName);

            if (!isCollectionMode && targetPath.endsWith(".xml")) {
                // A previously saved SVG resource is stored as a converted
                // vector XML plus its source SVG. Preview the source, which
                // the bitmap pipeline cannot read on its own.
                String svgSource = new FilePathUtil().getSvgFullPath(sc_id, editingImageBean.resName);
                if (new File(svgSource).exists()) {
                    targetPath = svgSource;
                }
            }

            imagePreviewController.loadEditableImage(targetPath);
            if (isCollectionMode) {
                // Collection image editing only changes the resource name.
                imagePreviewController.hideTransformControls();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (soundPreviewController != null) {
            soundPreviewController.pausePlayback();
        }
    }

    @Override
    public void finish() {
        if (soundPreviewController != null) {
            soundPreviewController.releaseMediaPlayer();
        }
        super.finish();
    }

    // ---------------------------------------------------------------------
    // Mode Initializers
    // ---------------------------------------------------------------------

    private void initImageMode(Intent intent, LayoutInflater inflater) {
        e(getString(R.string.design_manager_image_title_add_image));
        binding.tiInput.setHint(getString(R.string.design_manager_image_hint_enter_image_name));

        imagePreviewController = new ImagePreviewController(this, inflater, binding.previewContainer, false);
        imagePreviewController.setOnImageClickListener(v -> {
            // Project resources can be replaced while editing. Collection edits are rename-only.
            if (!editing || !isCollectionMode) {
                imagePreviewController.setImageViewEnabled(false);
                launchPicker();
            }
        });

        existingImages = intent.getParcelableArrayListExtra("images");
        editingImageBean = intent.getParcelableExtra("edit_target");
        editing = editingImageBean != null;

        imageValidator = isCollectionMode && editing
                ? new PB(this, binding.tiInput, uq.b, getReservedImageNames(), editingImageBean.resName)
                : new PB(this, binding.tiInput, uq.b, getReservedImageNames());
        imageValidator.a(1);

        if (dirPath != null && !dirPath.isEmpty()) {
            new oB().f(dirPath);
        }
        savedImagesResult = new ArrayList<>();

        // Multi-image selection: navigate the picked images and rename them
        // individually, or apply one common base name to all of them.
        binding.btnPrevImage.setOnClickListener(v -> navigateMultiImage(-1));
        binding.btnNextImage.setOnClickListener(v -> navigateMultiImage(1));
        binding.chkSamename.setText(R.string.design_manager_image_title_apply_same_naming);
        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!multipleImagesPicked) {
                return;
            }
            if (isChecked) {
                showMultiCommonNameDialog();
                multiUsingCommonBaseName = true;
                // Restore the cached base name; the very first use starts
                // out empty. The value is kept across navigation and
                // check/uncheck, mirroring CollectionImportActivity.
                String cached = multiCommonBaseName != null ? multiCommonBaseName : "";
                binding.edInput.setText(cached);
                if (imageValidator != null) {
                    // Re-validate as a base name (base_1..base_N check).
                    imageValidator.b(cached);
                }
            } else {
                multiUsingCommonBaseName = false;
                // Keep the cached base name so re-checking restores it.
                binding.edInput.setText(
                        multiDraftNames != null ? multiDraftNames.get(multiSelectedIndex) : "");
                applyMultiNameError();
            }
        });

        // Per-item names are validated in-house; the watcher is registered
        // after the PB validator so its verdict overrides the validator's
        // single-base-name check.
        binding.edInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!multipleImagesPicked) {
                    return;
                }
                String text = s.toString().trim();
                if (multiUsingCommonBaseName) {
                    // The shared base name is validated by the PB validator's
                    // base_1..base_N check; keep its error state.
                    multiCommonBaseName = text;
                    return;
                }
                multiDraftNames.set(multiSelectedIndex, text);
                applyMultiNameError();
            }
        });
    }

    private void initSoundMode(Intent intent, LayoutInflater inflater) {
        e(getString(R.string.design_manager_sound_title_add_sound));
        binding.tiInput.setHint(getString(R.string.design_manager_sound_hint_enter_sound_name));

        soundPreviewController = new SoundPreviewController(this, inflater, binding.previewContainer, false);
        soundPreviewController.setOnAlbumCardClickListener(v -> {
            // Project resources can be replaced while editing. Collection edits are rename-only.
            if (!editing || !isCollectionMode) {
                soundPreviewController.setAlbumCardEnabled(false);
                launchPicker();
            }
        });

        ArrayList<String> existingSoundNames;
        if (isCollectionMode) {
            existingSoundNames = getResourceNames(intent.getParcelableArrayListExtra("sounds"));
            editingSoundBean = intent.getParcelableExtra("edit_target");
        } else {
            existingSoundNames = intent.getStringArrayListExtra("sound_names");
            if (intent.getIntExtra("request_code", -1) == 270) {
                editingSoundBean = intent.getParcelableExtra("project_resource");
                // The original sound editor used an empty validator list while editing.
                existingSoundNames = new ArrayList<>();
            }
        }
        if (existingSoundNames == null) {
            existingSoundNames = new ArrayList<>();
        }

        editing = editingSoundBean != null;
        soundValidator = isCollectionMode && editing
                ? new ResNameValidator(this, binding.tiInput, uq.b, existingSoundNames, editingSoundBean.resName)
                : new ResNameValidator(this, binding.tiInput, uq.b, existingSoundNames);

        if (editing) {
            e(getString(R.string.design_manager_sound_title_edit_sound));
            binding.edInput.setText(editingSoundBean.resName);
            if (!isCollectionMode) {
                binding.edInput.setEnabled(false);
                binding.chkCollection.setEnabled(false);
            }

            String soundPath = isCollectionMode
                    ? getCollectionResourcePath("sound", editingSoundBean)
                    : (editingSoundBean.isNew
                        ? editingSoundBean.resFullName
                        : getExistingSoundFilePath(editingSoundBean));

            handleSoundPickedResult(Uri.fromFile(new File(soundPath)), true);
        }
    }

    private void initFontMode(Intent intent, LayoutInflater inflater) {
        e(Helper.getResString(R.string.design_manager_font_title_add_font));
        binding.tiInput.setHint(Helper.getResString(R.string.common_word_font_name));

        fontPreviewController = new FontPreviewController(inflater, binding.previewContainer, false);

        ArrayList<String> fontNames;
        if (isCollectionMode) {
            fontNames = getResourceNames(intent.getParcelableArrayListExtra("fonts"));
            editingFontBean = intent.getParcelableExtra("edit_target");
        } else {
            fontNames = intent.getStringArrayListExtra("font_names");
            if (intent.getIntExtra("request_code", -1) == 272) {
                editingFontBean = intent.getParcelableExtra("resource_bean");
            }
        }
        if (fontNames == null) {
            fontNames = new ArrayList<>();
        }

        editing = editingFontBean != null;
        fontValidator = editing
                ? new FontNameValidator(this, binding.tiInput, uq.b, fontNames, editingFontBean.resName)
                : new FontNameValidator(this, binding.tiInput, uq.b, fontNames);

        if (editing) {
            e(Helper.getResString(R.string.design_manager_font_title_edit_font));
            binding.edInput.setText(editingFontBean.resName);
            if (isCollectionMode) {
                fontPreviewController.hideSelectButton();
                fontPreviewController.loadFont(
                        new File(getCollectionResourcePath("font", editingFontBean)), false);
            } else {
                binding.chkCollection.setEnabled(false);
            }
        }

        fontPreviewController.setOnSelectClickListener(v -> {
            // Project font editing may replace the font file; collection editing only renames it.
            if ((!editing || !isCollectionMode) && !mB.a()) {
                launchPicker();
            }
        });
    }

    private ArrayList<String> getResourceNames(ArrayList<ProjectResourceBean> resources) {
        ArrayList<String> names = new ArrayList<>();
        if (isCollectionMode) {
            names.add("app_icon");
        }
        if (resources != null) {
            for (ProjectResourceBean resource : resources) {
                if (resource != null && resource.resName != null) {
                    names.add(resource.resName);
                }
            }
        }
        return names;
    }

    private String getCollectionResourcePath(String type, ProjectResourceBean bean) {
        return wq.a() + File.separator + type + File.separator + "data"
                + File.separator + bean.resFullName;
    }

    // ---------------------------------------------------------------------
    // File Picking & Result Handling
    // ---------------------------------------------------------------------

    private void launchPicker() {
        try {
            Intent intent;
            switch (resourceType) {
                case IMAGE -> {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    if (!editing) {
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    }
                }
                case SOUND -> {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                }
                default -> {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                }
            }
            filePickerLauncher.launch(Intent.createChooser(intent, getString(R.string.common_word_choose)));
        } catch (ActivityNotFoundException unused) {
            enablePickerTriggers();
            bB.b(this, getString(R.string.common_error_activity_not_found), bB.TOAST_NORMAL).show();
        }
    }

    private void enablePickerTriggers() {
        if (imagePreviewController != null) {
            imagePreviewController.setImageViewEnabled(true);
        }
        if (soundPreviewController != null) {
            soundPreviewController.setAlbumCardEnabled(true);
        }
    }

    private void handleImagePickedResult(Intent data) {
        imagePreviewController.resetTransformations();
        if (data.getClipData() == null) {
            newImagePicked = true;
            multipleImagesPicked = false;
            setSingleImageFromUri(data.getData());
            if (imageValidator != null) {
                imageValidator.a(1);
            }
        } else {
            ClipData clipData = data.getClipData();
            if (clipData.getItemCount() == 1) {
                newImagePicked = true;
                multipleImagesPicked = false;
                setSingleImageFromUri(clipData.getItemAt(0).getUri());
                if (imageValidator != null) {
                    imageValidator.a(1);
                }
            } else {
                pickedImageUris = new ArrayList<>();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    pickedImageUris.add(clipData.getItemAt(i).getUri());
                }
                multipleImagesPicked = true;
                multiSelectedIndex = 0;
                multiUsingCommonBaseName = false;
                multiCommonBaseName = null;
                multiNames = null;
                multiDraftNames = buildMultiDraftNames();
                setSingleImageFromUri(pickedImageUris.get(0));
                if (imageValidator != null) {
                    imageValidator.a(clipData.getItemCount());
                }
                showMultiImageMode();
            }
        }
    }

    private void setSingleImageFromUri(Uri uri) {
        String path = null;
        if (uri != null && (path = HB.a(this, uri)) != null) {
            imagePreviewController.loadEditableImage(path);
            if (binding.edInput.getText() == null || binding.edInput.getText().length() == 0) {
                int extIndex = path.endsWith(".9.png") ? path.lastIndexOf(".9.png") : path.lastIndexOf(".");
                if (extIndex > path.lastIndexOf("/")) {
                    String extracted = path.substring(path.lastIndexOf("/") + 1, extIndex)
                            .toLowerCase(Locale.ROOT)
                            .replaceAll("[^a-z0-9_]", "_");
                    binding.edInput.setText(extracted);
                }
            }
        }
    }

    /** True when the given local path points at an SVG file. */
    private static boolean isSvgName(String name) {
        return name != null && name.toLowerCase(Locale.ROOT).endsWith(".svg");
    }

    // ---------------------------------------------------------------------
    // Multi-image naming
    // ---------------------------------------------------------------------

    /** Seeds one editable draft name per picked image from its file name. */
    private ArrayList<String> buildMultiDraftNames() {
        ArrayList<String> names = new ArrayList<>(pickedImageUris.size());
        for (Uri uri : pickedImageUris) {
            String path = HB.a(this, uri);
            String raw = path != null ? FileUtil.getFileNameNoExtension(path) : null;
            if (raw == null || raw.isEmpty()) {
                raw = "image";
            }
            String cleaned = raw.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]", "_");
            if (cleaned.isEmpty() || !Character.isLetter(cleaned.charAt(0))) {
                cleaned = "image" + cleaned;
            }
            // Keep the drafts unique so the user is not forced to fix
            // collisions up front.
            String unique = cleaned;
            int suffix = 2;
            while (names.contains(unique)) {
                unique = cleaned + "_" + suffix++;
            }
            names.add(unique);
        }
        return names;
    }

    /** Swaps the single-image preview for the multi-image section. */
    private void showMultiImageMode() {
        binding.previewContainer.setVisibility(View.GONE);
        binding.layoutMultiImage.setVisibility(View.VISIBLE);
        binding.chkSamename.setVisibility(View.VISIBLE);
        if (multiPreviewController == null) {
            multiPreviewController = new ImagePreviewController(
                    this, getLayoutInflater(), binding.multiPreviewContainer, true);
        }
        updateMultiImageUI();
    }

    private void navigateMultiImage(int delta) {
        if (!multipleImagesPicked) {
            return;
        }
        int next = multiSelectedIndex + delta;
        if (next < 0 || next >= pickedImageUris.size()) {
            return;
        }
        multiSelectedIndex = next;
        updateMultiImageUI();
    }

    private void updateMultiImageUI() {
        int count = pickedImageUris.size();
        binding.tvMultiCount.setText((multiSelectedIndex + 1) + " / " + count);

        boolean canPrev = multiSelectedIndex > 0;
        boolean canNext = multiSelectedIndex < count - 1;
        binding.btnPrevImage.setEnabled(canPrev);
        binding.btnPrevImage.setAlpha(canPrev ? 1f : 0.3f);
        binding.btnNextImage.setEnabled(canNext);
        binding.btnNextImage.setAlpha(canNext ? 1f : 0.3f);

        if (multiPreviewController != null) {
            String path = HB.a(this, pickedImageUris.get(multiSelectedIndex));
            if (path != null) {
                multiPreviewController.resetTransformations();
                multiPreviewController.loadEditableImage(path);
                // The multi flow ignores per-image transforms, so the
                // rotate/flip controls are not offered.
                multiPreviewController.hideTransformControls();
            }
        }

        String text = multiUsingCommonBaseName
                ? (multiCommonBaseName != null ? multiCommonBaseName : "")
                : multiDraftNames.get(multiSelectedIndex);
        if (!text.equals(Helper.getText(binding.edInput))) {
            binding.edInput.setText(text);
        }
        binding.edInput.requestFocus();
        Editable editable = binding.edInput.getText();
        if (editable != null) {
            binding.edInput.setSelection(editable.length());
        }
        applyMultiNameError();
    }

    /**
     * Shows or clears the name error for the currently shown multi item.
     * Common-base mode keeps the PB validator's base_1..base_N verdict;
     * per-item mode validates the individual draft name in-house, because
     * the PB validator only knows a single base name.
     */
    private void applyMultiNameError() {
        if (!multipleImagesPicked || multiUsingCommonBaseName) {
            return;
        }
        String error = getMultiNameError(Helper.getText(binding.edInput), multiSelectedIndex, false);
        if (error != null) {
            binding.tiInput.setErrorEnabled(true);
            binding.tiInput.setError(error);
        } else {
            binding.tiInput.setErrorEnabled(false);
            binding.tiInput.setError(null);
        }
    }

    /**
     * Shape-only checks (length, character set, reserved keywords). A common
     * base name only needs these: the numbered names {@code base_1..N} are
     * checked against existing resources separately.
     *
     * @return a localized error message, or {@code null} when the shape is
     * valid.
     */
    private String getMultiNameShapeError(String name) {
        if (name == null) {
            return getString(R.string.common_message_name_unavailable);
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (trimmed.length() < 3) {
            return getString(R.string.invalid_value_min_lenth, 3);
        }
        if (trimmed.length() > 70) {
            return getString(R.string.invalid_value_max_lenth, 70);
        }
        if ("default_image".equals(trimmed) || "NONE".equalsIgnoreCase(trimmed)) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (!Character.isLetter(trimmed.charAt(0))) {
            return getString(R.string.logic_editor_message_variable_name_must_start_letter);
        }
        if (!MULTI_NAME_PATTERN.matcher(trimmed).matches()) {
            return getString(R.string.invalid_value_rule_4);
        }
        if (isReservedKeyword(trimmed)) {
            return getString(R.string.logic_editor_message_reserved_keywords);
        }
        return null;
    }

    /**
     * Validates one multi-pick name with the same rules as the legacy
     * validators: 3..70 chars, lowercase identifier, no reserved keywords,
     * no collision with existing resources, no collision with the other
     * draft names.
     *
     * @return a localized error message, or {@code null} when the name is
     * usable.
     */
    private String getMultiNameError(String name, int itemIndex, boolean allowDuplicates) {
        String shapeError = getMultiNameShapeError(name);
        if (shapeError != null) {
            return shapeError;
        }
        String trimmed = name.trim();
        if (getReservedImageNames().contains(trimmed)) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (!allowDuplicates && isDuplicateInMultiDrafts(trimmed, itemIndex)) {
            return getString(R.string.common_message_name_unavailable);
        }
        return null;
    }

    private boolean isReservedKeyword(String name) {
        for (String keyword : uq.b) {
            if (name.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateInMultiDrafts(String name, int excludeIndex) {
        if (name == null || multiDraftNames == null) {
            return false;
        }
        for (int i = 0; i < multiDraftNames.size(); i++) {
            if (i == excludeIndex) {
                continue;
            }
            if (name.equals(multiDraftNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Computes the final name of every picked image. Common-base mode
     * numbers them {@code base_1, base_2, ...}; per-item mode uses the
     * drafts as typed. Returns {@code null} after toasting the error and
     * navigating to the offending item when a name is not usable yet.
     */
    private ArrayList<String> buildMultiFinalNames() {
        if (multiUsingCommonBaseName) {
            String base = multiCommonBaseName != null ? multiCommonBaseName.trim() : "";
            String baseError = getMultiNameShapeError(base);
            if (baseError != null) {
                bB.b(this, baseError, bB.TOAST_WARNING).show();
                return null;
            }
            ArrayList<String> names = new ArrayList<>(pickedImageUris.size());
            for (int i = 0; i < pickedImageUris.size(); i++) {
                String name = base + "_" + (i + 1);
                if (getReservedImageNames().contains(name)) {
                    bB.b(this,
                            getString(R.string.common_message_name_unavailable) + " [" + name + "]",
                            bB.TOAST_WARNING).show();
                    return null;
                }
                names.add(name);
            }
            return names;
        }

        ArrayList<String> names = new ArrayList<>(pickedImageUris.size());
        for (int i = 0; i < multiDraftNames.size(); i++) {
            String name = multiDraftNames.get(i);
            String error = getMultiNameError(name, i, false);
            if (error != null) {
                // Bring the offending item into view so it can be fixed.
                multiSelectedIndex = i;
                updateMultiImageUI();
                String label = (name != null && !name.trim().isEmpty())
                        ? name.trim()
                        : String.valueOf(i + 1);
                bB.b(this, error + "\n[" + label + "]", bB.TOAST_WARNING).show();
                return null;
            }
            names.add(name.trim());
        }
        // Final gate: catch any collision that survived (e.g. drafts that
        // only differ by whitespace).
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (String name : names) {
            if (!seen.add(name)) {
                bB.b(this,
                        getString(R.string.common_message_name_unavailable) + " [" + name + "]",
                        bB.TOAST_WARNING).show();
                return null;
            }
        }
        return names;
    }

    private void showMultiCommonNameDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.design_manager_image_title_apply_same_naming)
                .setMessage("All selected images will share one base name and be numbered "
                        + "automatically (base_1, base_2, ...). Unchecking restores each "
                        + "image's individual name.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void handleSoundPickedResult(Uri uri, boolean isLocalFile) {
        soundUri = uri;
        String displayFileName = isLocalFile ? uri.getLastPathSegment() : getFilenameOfPickedFile(uri);
        boolean loaded = soundPreviewController.loadSoundFromUri(uri, displayFileName, isLocalFile, true);
        if (loaded && (binding.edInput.getText() == null || binding.edInput.getText().length() == 0)) {
            String extractedName = FileUtil.getFileNameNoExtension(displayFileName)
                    .toLowerCase(Locale.ROOT)
                    .replaceAll("[^a-z0-9_]", "_");
            binding.edInput.setText(extractedName);
        }
    }

    private void handleFontPickedResult(Uri intentData) {
        final int currentGeneration = ++fontPickGeneration;
        validFontPicked = false;
        String filenameExtension = FileUtil.getFileExtension(
                SketchwareUtil.getSafDocumentDisplayName(intentData).orElse(".ttf"));
        SketchwareUtil.copySafDocumentToTempFile(intentData, this, filenameExtension, tempFontFile -> {
            if (currentGeneration != fontPickGeneration) {
                return;
            }
            fontUri = Uri.fromFile(tempFontFile);
            if (!fontPreviewController.loadFont(tempFontFile, false)) {
                validFontPicked = false;
                SketchwareUtil.toastError("Warning: Font doesn't seem to be valid");
                return;
            }
            validFontPicked = true;
            String extractedFontName = SketchwareUtil.getSafDocumentDisplayName(intentData)
                    .orElse("invalid.ttf")
                    .toLowerCase(Locale.ROOT);
            int dotIndex = extractedFontName.lastIndexOf(".");
            if (dotIndex > 0) {
                extractedFontName = extractedFontName.substring(0, dotIndex);
            }
            extractedFontName = extractedFontName.replaceAll("[^a-z0-9_]", "_");
            binding.edInput.requestFocus();
            binding.edInput.setText(extractedFontName);
        }, e -> runOnUiThread(() -> {
            if (currentGeneration != fontPickGeneration) {
                return;
            }
            validFontPicked = false;
            fontPreviewController.hidePreview();
            SketchwareUtil.toast("Couldn't read picked font: " + e.getMessage());
            LogUtil.e("AddResourceActivity", "Failed to read picked font", e);
        }));
    }

    // ---------------------------------------------------------------------
    // Saving Logic per Resource Type
    // ---------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_dialog_cancel_button) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            switch (resourceType) {
                case IMAGE -> saveImage();
                case SOUND -> saveSound();
                case FONT -> saveFont();
            }
        }
    }

    private void saveImage() {
        if (multipleImagesPicked) {
            multiNames = buildMultiFinalNames();
            if (multiNames == null) {
                return;
            }
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                k();
                new ImageSaveAsyncTask(this, Helper.getText(binding.edInput).trim(),
                        0, 1, 1, multiNames).execute();
            }, 500L);
            return;
        }
        if (!imageValidator.b()) {
            return;
        }
        if (!newImagePicked && imagePreviewController.getImageFilePath() == null) {
            imagePreviewController.playEmptySelectionAnimation();
            return;
        }
        String imageName = Helper.getText(binding.edInput).trim();
        int rotation = imagePreviewController.getRotationDegrees();
        int flipHorizontal = imagePreviewController.getScaleX();
        int flipVertical = imagePreviewController.getScaleY();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            k();
            new ImageSaveAsyncTask(this, imageName, rotation, flipHorizontal, flipVertical, null)
                    .execute();
        }, 500L);
    }

    private void saveSound() {
        if (!soundValidator.b()) {
            return;
        }

        if (isCollectionMode && editing) {
            try {
                Qp.g().a(editingSoundBean, Helper.getText(binding.edInput), true);
                showCollectionSaveSuccess();
                finish();
            } catch (Exception e) {
                if (!handleCollectionException(e)) {
                    throw e;
                }
            }
            return;
        }

        if (!soundPreviewController.isSoundPlayable() || soundUri == null) {
            soundPreviewController.playEmptySelectionAnimation();
            return;
        }

        String soundName = Helper.getText(binding.edInput);
        String ext = FileUtil.getFileExtension(getFilenameOfPickedFile(soundUri));

        SketchwareUtil.copySafDocumentToTempFile(soundUri, this, ext, tempFile -> {
            String soundFilePath = tempFile.getAbsolutePath();
            ProjectResourceBean resourceBean = new ProjectResourceBean(
                    ProjectResourceBean.PROJECT_RES_TYPE_FILE, soundName, soundFilePath);
            resourceBean.savedPos = 1;
            resourceBean.isNew = true;

            try {
                if (isCollectionMode || binding.chkCollection.isChecked()) {
                    Qp.g().a(sc_id, resourceBean);
                }
            } catch (Exception e) {
                if (handleCollectionException(e)) {
                    return;
                }
                throw e;
            }

            if (isCollectionMode) {
                showCollectionSaveSuccess();
            } else {
                Intent intent = new Intent();
                intent.putExtra("project_resource", resourceBean);
                setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }, e -> {
            SketchwareUtil.toastError("Error while loading sound: " + e.getMessage());
            LogUtil.e("AddResourceActivity", "Failed to load sound", e);
        });
    }

    private void saveFont() {
        if (fontValidator != null && !fontValidator.b()) {
            return;
        }

        if (isCollectionMode && editing) {
            try {
                Np.g().a(editingFontBean, Helper.getText(binding.edInput), true);
                showCollectionSaveSuccess();
                finish();
            } catch (Exception e) {
                Log.e("AddResourceActivity", "Failed to rename collection font", e);
                if (!handleCollectionException(e)) {
                    throw e;
                }
            }
            return;
        }

        if (!validFontPicked || fontUri == null) {
            bB.b(this, Helper.getResString(R.string.design_manager_message_no_font_selected), Toast.LENGTH_LONG).show();
            return;
        }

        String fontName = Helper.getText(binding.edInput);
        String pickedFontFilePath = fontUri.getPath();
        ProjectResourceBean resourceBean = new ProjectResourceBean(
                ProjectResourceBean.PROJECT_RES_TYPE_FILE, fontName, pickedFontFilePath);
        resourceBean.savedPos = 1;
        resourceBean.isNew = true;

        try {
            if (isCollectionMode || binding.chkCollection.isChecked()) {
                Np.g().a(sc_id, resourceBean);
            }
        } catch (Exception e) {
            Log.e("AddResourceActivity", "Failed to add font to collection", e);
            if (handleCollectionException(e)) {
                return;
            }
            throw e;
        }

        if (isCollectionMode) {
            showCollectionSaveSuccess();
        } else {
            Intent intent = new Intent();
            intent.putExtra("resource_bean", resourceBean);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private void showCollectionSaveSuccess() {
        int message = editing
                ? R.string.design_manager_message_edit_complete
                : R.string.design_manager_message_add_complete;
        bB.a(this, getString(message), bB.TOAST_NORMAL).show();
    }

    @SuppressWarnings("ConstantConditions")
    private boolean handleCollectionException(Exception e) {
        if (e instanceof yy) {
            switch (e.getMessage()) {
                case "duplicate_name" ->
                        bB.b(this, getString(R.string.collection_duplicated_name), Toast.LENGTH_LONG).show();
                case "file_no_exist" ->
                        bB.b(this, getString(R.string.collection_no_exist_file), Toast.LENGTH_LONG).show();
                case "fail_to_copy" ->
                        bB.b(this, getString(R.string.collection_failed_to_copy), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // Helpers & Async Image Saving Task
    // ---------------------------------------------------------------------

    /**
     * Adds the given images to the image collection. SVG files are copied
     * verbatim and registered directly, because the legacy
     * {@code a.a.a.Op} path re-encodes new images to PNG through a bitmap
     * transform that cannot read vector files. Rasters keep the legacy path.
     */
    private void addImagesToCollection(ProjectResourceBean image) throws By {
        ArrayList<ProjectResourceBean> items = new ArrayList<>();
        items.add(image);
        addImagesToCollection(items);
    }

    private void addImagesToCollection(ArrayList<ProjectResourceBean> items) throws By {
        ArrayList<ProjectResourceBean> svgItems = new ArrayList<>();
        ArrayList<ProjectResourceBean> rasterItems = new ArrayList<>();
        for (ProjectResourceBean item : items) {
            if (isSvgName(item.resFullName)) {
                svgItems.add(item);
            } else {
                rasterItems.add(item);
            }
        }
        if (!svgItems.isEmpty()) {
            Op op = Op.g();
            if (op.e == null) {
                op.a();
            }
            // Validate everything before writing anything, mirroring the
            // raster batch's all-or-nothing semantics. SVGs are registered
            // first so their names are visible to the raster batch's
            // duplicate check.
            for (ProjectResourceBean item : svgItems) {
                for (CollectionBean existing : op.e) {
                    if (existing.name.equals(item.resName)) {
                        throw new By(getString(R.string.collection_duplicated_name) + "[" + item.resName + "]");
                    }
                }
                if (!new File(item.resFullName).exists()) {
                    throw new By(getString(R.string.collection_no_exist_file));
                }
            }
            op.c.f(op.b);
            for (ProjectResourceBean item : svgItems) {
                String dataName = item.resName + ".svg";
                op.c.a(item.resFullName, op.b + File.separator + dataName);
                op.e.add(new CollectionBean(item.resName, dataName));
            }
            op.e();
        }
        if (!rasterItems.isEmpty()) {
            Op.g().a(sc_id, rasterItems, true);
        }
    }

    private static int safeExifOrientation(String filePath) {
        try {
            return iB.a(filePath);
        } catch (Exception e) {
            // Vector files (SVG) and other formats without EXIF must not
            // abort the save task.
            return 0;
        }
    }

    private ArrayList<String> getReservedImageNames() {
        ArrayList<String> names = new ArrayList<>();
        if (isCollectionMode) {
            names.add("app_icon");
        }
        if (existingImages != null) {
            for (ProjectResourceBean existingImage : existingImages) {
                if (existingImage != null && existingImage.resName != null) {
                    names.add(existingImage.resName);
                }
            }
        }
        return names;
    }

    private String getExistingSoundFilePath(ProjectResourceBean bean) {
        String fullName = bean.resFullName;
        String extension = fullName.substring(fullName.lastIndexOf("."));
        return dirPath + File.separator + bean.resName + extension;
    }

    private String getFilenameOfPickedFile(Uri input) {
        Optional<String> displayName = SketchwareUtil.getSafDocumentDisplayName(input);
        if (!displayName.isPresent()) return "Unknown filename";
        String name = displayName.get();
        if (name.contains(".")) {
            return name;
        }
        Optional<String> mimeType = SketchwareUtil.doSingleStringContentQuery(input, MediaStore.MediaColumns.MIME_TYPE);
        String ext = mimeType.map(s -> s.split("/")[1]).orElse("");
        return name + "." + ext;
    }

    private static class ImageSaveAsyncTask extends MA {
        private final WeakReference<AddResourceActivity> activityRef;
        private final String imageName;
        private final int rotation;
        private final int flipHorizontal;
        private final int flipVertical;
        /** User-chosen final names for a multi pick, or null for the
         * legacy {@code base_1..base_N} behaviour. */
        private final ArrayList<String> multiNames;

        public ImageSaveAsyncTask(AddResourceActivity activity, String imageName, int rotation,
                                  int flipHorizontal, int flipVertical,
                                  ArrayList<String> multiNames) {
            super(activity.getApplicationContext());
            this.activityRef = new WeakReference<>(activity);
            this.imageName = imageName;
            this.rotation = rotation;
            this.flipHorizontal = flipHorizontal;
            this.flipVertical = flipVertical;
            this.multiNames = multiNames;
            activity.a(this);
        }

        /** Name of the {@code index}-th picked image: the user-chosen name,
         * or the legacy numbered fallback. */
        private String itemImageName(int index) {
            if (multiNames != null && index < multiNames.size()) {
                return multiNames.get(index);
            }
            return imageName + "_" + (index + 1);
        }

        @Override
        public void a() {
            AddResourceActivity activity = this.activityRef.get();
            if (activity == null) return;
            activity.h();
            if (activity.isCollectionMode) {
                activity.showCollectionSaveSuccess();
            } else {
                Intent intent = new Intent();
                intent.putExtra("sc_id", activity.sc_id);
                if (activity.editing) {
                    intent.putExtra("image", activity.editingImageBean);
                } else {
                    intent.putExtra("images", activity.savedImagesResult);
                }
                activity.setResult(RESULT_OK, intent);
            }
            activity.finish();
        }

        @Override
        @SuppressWarnings("ConstantValue")
        public void b() throws By {
            AddResourceActivity activity = this.activityRef.get();
            if (activity == null) return;
            ImagePreviewController preview = activity.imagePreviewController;
            try {
                publishProgress("Now processing..");

                if (activity.isCollectionMode) {
                    if (activity.editing) {
                        Op.g().a(
                                activity.editingImageBean,
                                imageName,
                                false);
                    } else if (!activity.multipleImagesPicked) {
                        ProjectResourceBean image = new ProjectResourceBean(
                                ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                                imageName,
                                preview.getImageFilePath());
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = rotation;
                        image.flipVertical = flipVertical;
                        image.flipHorizontal = flipHorizontal;
                        activity.addImagesToCollection(image);
                    } else {
                        ArrayList<ProjectResourceBean> toAdd = new ArrayList<>();
                        for (int i = 0; i < activity.pickedImageUris.size(); i++) {
                            Uri uri = activity.pickedImageUris.get(i);
                            String itemImageName = itemImageName(i);
                            String filePath = HB.a(activity.getApplicationContext(), uri);
                            if (filePath == null) {
                                throw new By("Unable to read selected image");
                            }
                            ProjectResourceBean image = new ProjectResourceBean(
                                    ProjectResourceBean.PROJECT_RES_TYPE_FILE, itemImageName, filePath);
                            image.savedPos = 1;
                            image.isNew = true;
                            image.rotate = safeExifOrientation(filePath);
                            image.flipVertical = 1;
                            image.flipHorizontal = 1;
                            toAdd.add(image);
                        }
                        activity.addImagesToCollection(toAdd);
                    }
                    return;
                }

                if (!activity.multipleImagesPicked) {
                    if (!activity.editing) {
                        ProjectResourceBean image = new ProjectResourceBean(
                                ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                                imageName,
                                preview.getImageFilePath());
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = rotation;
                        image.flipVertical = flipVertical;
                        image.flipHorizontal = flipHorizontal;
                        if (activity.binding.chkCollection.isChecked()) {
                            activity.addImagesToCollection(image);
                        }
                        activity.savedImagesResult.add(image);
                    } else if (!activity.newImagePicked) {
                        ProjectResourceBean image = activity.editingImageBean;
                        image.rotate = rotation;
                        image.flipHorizontal = flipHorizontal;
                        image.flipVertical = flipVertical;
                        image.isEdited = true;
                    } else {
                        ProjectResourceBean image = activity.editingImageBean;
                        image.resFullName = preview.getImageFilePath();
                        image.savedPos = 1;
                        image.rotate = rotation;
                        image.flipVertical = flipVertical;
                        image.flipHorizontal = flipHorizontal;
                        image.isEdited = true;
                    }
                } else {
                    ArrayList<ProjectResourceBean> toAdd = new ArrayList<>();
                    for (int i = 0; i < activity.pickedImageUris.size(); i++) {
                        Uri uri = activity.pickedImageUris.get(i);
                        String itemImageName = itemImageName(i);
                        String filePath = HB.a(activity.getApplicationContext(), uri);
                        if (filePath == null) {
                            return;
                        }
                        ProjectResourceBean image = new ProjectResourceBean(
                                ProjectResourceBean.PROJECT_RES_TYPE_FILE, itemImageName, filePath);
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = safeExifOrientation(filePath);
                        image.flipVertical = 1;
                        image.flipHorizontal = 1;
                        toAdd.add(image);
                    }
                    if (activity.binding.chkCollection.isChecked()) {
                        activity.addImagesToCollection(toAdd);
                    }
                    activity.multipleImagesPicked = false;
                    activity.savedImagesResult.addAll(toAdd);
                }
            } catch (Exception e) {
                if (e instanceof yy yyEx) {
                    String errorMessage = yyEx.getMessage();
                    int code = switch (errorMessage) {
                        case "fail_to_copy" -> R.string.collection_failed_to_copy;
                        case "file_no_exist" -> R.string.collection_no_exist_file;
                        case "duplicate_name" -> R.string.collection_duplicated_name;
                        default -> 0;
                    };
                    String message = code != 0 ? activity.getString(code) : "";
                    ArrayList<String> dupList = yyEx.a();
                    if (dupList != null && !dupList.isEmpty()) {
                        message += "[" + String.join(", ", dupList) + "]";
                    }
                    throw new By(message);
                }
                e.printStackTrace();
                throw new By(e.getMessage());
            }
        }

        @Override
        public void a(String str) {
            AddResourceActivity activity = activityRef.get();
            if (activity != null) {
                activity.h();
            }
        }
    }
}
