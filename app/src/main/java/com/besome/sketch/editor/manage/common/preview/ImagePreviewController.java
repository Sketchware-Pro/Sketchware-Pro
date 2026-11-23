package com.besome.sketch.editor.manage.common.preview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.util.Locale;

import a.a.a.iB;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImagePreviewBinding;
import pro.sketchware.utility.SvgUtils;

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
    /** True when the selected file is a vector (SVG) and previews must avoid bitmap decoding. */
    private boolean svgMode = false;
    private SvgUtils svgUtils;

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
     * Loads a preview image inside CollectionImportActivity. Rasters use
     * Glide; SVG files use the Coil/SvgDecoder loader.
     */
    public void loadCollectionPreview(String filePath) {
        this.imageFilePath = filePath;
        this.svgMode = isSvg(filePath);
        if (svgMode) {
            loadSvgIntoSelected(filePath);
            return;
        }
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
        boolean isSvgFile = isSvg(filePath);
        this.svgMode = isSvgFile;
        binding.tvAddPhoto.setVisibility(View.GONE);
        binding.tvDesc.setVisibility(View.INVISIBLE);
        binding.layoutImgInform.setVisibility(View.GONE);
        binding.tvImgcnt.setVisibility(View.GONE);

        if (isSvgFile) {
            // Vector files have no bitmap to transform: hide the rotate/flip
            // controls and preview the SVG through the Coil/SvgDecoder loader.
            exifOrientation = 0;
            resetTransformations();
            binding.layoutImgModify.setVisibility(View.GONE);
            loadSvgIntoSelected(filePath);
            return;
        }

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
        if (svgMode || imageFilePath == null || imageFilePath.isEmpty()) {
            return;
        }
        rotationDegrees = (rotationDegrees + 90) % 360;
        refreshTransformedPreview();
    }

    public void flipHorizontally() {
        if (svgMode || imageFilePath == null || imageFilePath.isEmpty()) {
            return;
        }
        if (rotationDegrees != 90 && rotationDegrees != 270) {
            scaleX *= -1;
        } else {
            scaleY *= -1;
        }
        refreshTransformedPreview();
    }

    public void flipVertically() {
        if (svgMode || imageFilePath == null || imageFilePath.isEmpty()) {
            return;
        }
        if (rotationDegrees != 90 && rotationDegrees != 270) {
            scaleY *= -1;
        } else {
            scaleX *= -1;
        }
        refreshTransformedPreview();
    }

    private void refreshTransformedPreview() {
        if (imageFilePath == null) return;
        if (svgMode) {
            loadSvgIntoSelected(imageFilePath);
            return;
        }
        Bitmap base = iB.a(imageFilePath, 1024, 1024);
        if (base == null) {
            // Undecodable raster file (corrupt or unknown format): never let a
            // null bitmap crash the transform step below.
            Log.w("ImagePreviewController", "Could not decode image: " + imageFilePath);
            showErrorPlaceholder();
            return;
        }
        Bitmap transformed = iB.a(iB.a(base, exifOrientation), rotationDegrees, scaleX, scaleY);
        binding.imgSelected.setImageBitmap(transformed != null ? transformed : base);
    }

    /**
     * Renders an SVG file into the selected-image box using the shared
     * Coil/SvgDecoder pipeline (see {@link SvgUtils}).
     */
    private void loadSvgIntoSelected(String filePath) {
        binding.imgSelected.setImageDrawable(null);
        if (filePath == null || !new File(filePath).exists()) {
            showErrorPlaceholder();
            return;
        }
        if (svgUtils == null) {
            svgUtils = new SvgUtils(context.getApplicationContext());
        }
        svgUtils.loadImage(binding.imgSelected, filePath);
    }

    private void showErrorPlaceholder() {
        binding.imgSelected.setImageResource(R.drawable.ic_remove_grey600_24dp);
    }

    /** True when the given path points at an SVG file (case-insensitive). */
    private static boolean isSvg(String path) {
        return path != null && path.toLowerCase(Locale.ROOT).endsWith(".svg");
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
