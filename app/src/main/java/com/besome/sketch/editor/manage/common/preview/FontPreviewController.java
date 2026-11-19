package com.besome.sketch.editor.manage.common.preview;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontPreviewBinding;

/**
 * Reusable controller that inflates and manages the font preview card
 * for both AddResourceActivity and ManageCollectionImportActivity.
 */
public class FontPreviewController {

    private final ManageFontPreviewBinding binding;

    public FontPreviewController(LayoutInflater inflater, ViewGroup container, boolean showByDefault) {
        binding = ManageFontPreviewBinding.inflate(inflater, container, true);
        binding.fontPreviewCard.setVisibility(showByDefault ? View.VISIBLE : View.GONE);
    }

    public void setOnSelectClickListener(View.OnClickListener listener) {
        binding.btnSelectFont.setOnClickListener(listener);
    }

    /**
     * Loads a font file from the given path and updates the preview text.
     *
     * @param fontFile      The font file to load.
     * @param isImportMode  True if used in collection import mode, false for add/edit mode.
     * @return True if the font is valid and loaded successfully, false otherwise.
     */
    public boolean loadFont(File fontFile, boolean isImportMode) {
        if (fontFile == null || !fontFile.exists()) {
            binding.fontPreviewCard.setVisibility(View.GONE);
            return false;
        }
        try {
            Typeface typeface = Typeface.createFromFile(fontFile);
            if (typeface == null || typeface.equals(Typeface.DEFAULT)) {
                return false;
            }
            binding.fontPreviewCard.setVisibility(View.VISIBLE);
            binding.fontPreviewTxt.setTypeface(typeface);
            binding.fontPreviewTxt.setText(isImportMode
                    ? R.string.design_manager_font_description_example_sentence
                    : R.string.msg_this_is_how_your_font);
            return true;
        } catch (Exception e) {
            binding.fontPreviewCard.setVisibility(View.GONE);
            return false;
        }
    }

    public void hidePreview() {
        binding.fontPreviewCard.setVisibility(View.GONE);
    }
    
    public void hideSelectButton() {
        binding.btnSelectFont.setVisibility(View.GONE);
    }

    public ManageFontPreviewBinding getBinding() {
        return binding;
    }
}
