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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.common.preview.FontPreviewController;
import com.besome.sketch.editor.manage.common.preview.ImagePreviewController;
import com.besome.sketch.editor.manage.common.preview.SoundPreviewController;
import com.besome.sketch.lib.base.BaseDialogActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import a.a.a.By;
import a.a.a.HB;
import a.a.a.MA;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.PB;
import a.a.a.Qp;
import a.a.a.WB;
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
    private WB soundValidator;
    private FontNameValidator fontValidator;

    // Image state
    private ArrayList<ProjectResourceBean> existingImages;
    private ArrayList<ProjectResourceBean> savedImagesResult;
    private ArrayList<Uri> pickedImageUris;
    private boolean multipleImagesPicked = false;
    private boolean newImagePicked = false;
    private ProjectResourceBean editingImageBean = null;

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
                ? new WB(this, binding.tiInput, uq.b, existingSoundNames, editingSoundBean.resName)
                : new WB(this, binding.tiInput, uq.b, existingSoundNames);

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
                    Uri itemUri = clipData.getItemAt(i).getUri();
                    if (i == 0) {
                        setSingleImageFromUri(itemUri);
                    }
                    pickedImageUris.add(itemUri);
                }
                multipleImagesPicked = true;
                imagePreviewController.showMultipleImagesOverlay(clipData.getItemCount());
                if (imageValidator != null) {
                    imageValidator.a(clipData.getItemCount());
                }
            }
        }
    }

    private void setSingleImageFromUri(Uri uri) {
        String path;
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
            new ImageSaveAsyncTask(this, imageName, rotation, flipHorizontal, flipVertical).execute();
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

        public ImageSaveAsyncTask(AddResourceActivity activity, String imageName, int rotation, int flipHorizontal, int flipVertical) {
            super(activity.getApplicationContext());
            this.activityRef = new WeakReference<>(activity);
            this.imageName = imageName;
            this.rotation = rotation;
            this.flipHorizontal = flipHorizontal;
            this.flipVertical = flipVertical;
            activity.a(this);
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
                        Op.g().a(activity.sc_id, image);
                    } else {
                        ArrayList<ProjectResourceBean> toAdd = new ArrayList<>();
                        int i = 0;
                        while (i < activity.pickedImageUris.size()) {
                            Uri uri = activity.pickedImageUris.get(i);
                            String itemImageName = imageName + "_" + (++i);
                            String filePath = HB.a(activity.getApplicationContext(), uri);
                            if (filePath == null) {
                                throw new By("Unable to read selected image");
                            }
                            ProjectResourceBean image = new ProjectResourceBean(
                                    ProjectResourceBean.PROJECT_RES_TYPE_FILE, itemImageName, filePath);
                            image.savedPos = 1;
                            image.isNew = true;
                            image.rotate = iB.a(filePath);
                            image.flipVertical = 1;
                            image.flipHorizontal = 1;
                            toAdd.add(image);
                        }
                        Op.g().a(activity.sc_id, toAdd, true);
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
                            Op.g().a(activity.sc_id, image);
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
                    int i = 0;
                    while (i < activity.pickedImageUris.size()) {
                        Uri uri = activity.pickedImageUris.get(i);
                        String itemImageName = imageName + "_" + (++i);
                        String filePath = HB.a(activity.getApplicationContext(), uri);
                        if (filePath == null) {
                            return;
                        }
                        ProjectResourceBean image = new ProjectResourceBean(
                                ProjectResourceBean.PROJECT_RES_TYPE_FILE, itemImageName, filePath);
                        image.savedPos = 1;
                        image.isNew = true;
                        image.rotate = iB.a(filePath);
                        image.flipVertical = 1;
                        image.flipHorizontal = 1;
                        toAdd.add(image);
                    }
                    if (activity.binding.chkCollection.isChecked()) {
                        Op.g().a(activity.sc_id, toAdd, true);
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
