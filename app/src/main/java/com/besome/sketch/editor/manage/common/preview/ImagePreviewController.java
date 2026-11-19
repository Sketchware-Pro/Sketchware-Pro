package com.besome.sketch.editor.manage.common.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import a.a.a.iB;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImagePreviewBinding;

/**
 * Reusable controller for displaying image previews, handling Exif orientation,
 * rotation, horizontal/vertical flipping, and multi-selection overlays.
 */
public class ImagePreviewController {

    private final Context context;
    private final ManageImagePreviewBinding binding;
    private final boolean isImportMode;

    private String imageFilePath = null;
    private int rotationDegrees = 0;
    private int exifOrientation = 0;
    private int scaleX = 1;
    private int scaleY = 1;

    public ImagePreviewController(Context context, LayoutInflater inflater, ViewGroup container, boolean isImportMode) {
        this.context = context;
        this.isImportMode = isImportMode;
        this.binding = ManageImagePreviewBinding.inflate(inflater, container, true);

        if (isImportMode) {
            binding.layoutPlaceholder.setVisibility(View.GONE);
            binding.layoutImgModify.setVisibility(View.GONE);
            binding.layoutImgInform.setVisibility(View.GONE);
        } else {
            binding.imgRotate.setOnClickListener(v -> rotate90Degrees());
            binding.imgHorizontal.setOnClickListener(v -> flipHorizontally());
            binding.imgVertical.setOnClickListener(v -> flipVertically());
        }
    }

    public void setOnImageClickListener(View.OnClickListener listener) {
        binding.imgSelected.setOnClickListener(listener);
    }

    public void setImageViewEnabled(boolean enabled) {
        binding.imgSelected.setEnabled(enabled);
    }

    /**
     * Loads a preview image inside ManageCollectionImportActivity using Glide.
     */
    public void loadCollectionPreview(String filePath) {
        this.imageFilePath = filePath;
        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(filePath)
                .centerCrop()
                .error(R.drawable.ic_remove_grey600_24dp)
                .into(new BitmapImageViewTarget(binding.imgSelected).getView());
    }

    /**
     * Loads a local image file in AddResourceActivity and enables transformation controls.
     */
    public void loadEditableImage(String filePath) {
        this.imageFilePath = filePath;
        binding.tvAddPhoto.setVisibility(View.GONE);
        binding.tvDesc.setVisibility(View.INVISIBLE);
        binding.layoutImgInform.setVisibility(View.GONE);
        binding.tvImgcnt.setVisibility(View.GONE);
        binding.layoutImgModify.setVisibility(View.VISIBLE);

        try {
            exifOrientation = iB.a(filePath);
        } catch (Exception e) {
            exifOrientation = 0;
        }
        refreshTransformedPreview();
    }

    /** Hides transformation controls when a collection image is being renamed. */
    public void hideTransformControls() {
        binding.layoutImgModify.setVisibility(View.GONE);
    }

    public void showMultipleImagesOverlay(int totalCount) {
        binding.layoutImgInform.setVisibility(View.VISIBLE);
        binding.layoutImgModify.setVisibility(View.GONE);
        binding.tvImgcnt.setVisibility(View.VISIBLE);
        binding.tvImgcnt.setText("+ " + (totalCount - 1) + " more");
    }

    public void resetTransformations() {
        rotationDegrees = 0;
        scaleX = 1;
        scaleY = 1;
    }

    public void setTransformations(int rotation, int flipHorizontal, int flipVertical) {
        this.rotationDegrees = rotation;
        this.scaleX = flipHorizontal;
        this.scaleY = flipVertical;
    }

    public void rotate90Degrees() {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            rotationDegrees = (rotationDegrees + 90) % 360;
            refreshTransformedPreview();
        }
    }

    public void flipHorizontally() {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            if (rotationDegrees != 90 && rotationDegrees != 270) {
                scaleX *= -1;
            } else {
                scaleY *= -1;
            }
            refreshTransformedPreview();
        }
    }

    public void flipVertically() {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            if (rotationDegrees != 90 && rotationDegrees != 270) {
                scaleY *= -1;
            } else {
                scaleX *= -1;
            }
            refreshTransformedPreview();
        }
    }

    private void refreshTransformedPreview() {
        if (imageFilePath == null) return;
        binding.imgSelected.setImageBitmap(
                iB.a(iB.a(iB.a(imageFilePath, 1024, 1024), exifOrientation),
                        rotationDegrees, scaleX, scaleY)
        );
    }

    public void playEmptySelectionAnimation() {
        binding.tvDesc.startAnimation(AnimationUtils.loadAnimation(context, R.anim.ani_1));
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public int getRotationDegrees() {
        return rotationDegrees;
    }

    public int getScaleX() {
        return scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }
}
