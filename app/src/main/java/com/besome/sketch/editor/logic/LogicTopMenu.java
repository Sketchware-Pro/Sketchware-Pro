package com.besome.sketch.editor.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.LogicEditorTopMenuBinding;
import pro.sketchware.utility.ThemeUtils;

public class LogicTopMenu extends LinearLayout {

    private final Context context;
    public boolean isDeleteActive;
    public boolean isCopyActive;
    public boolean isFavoriteActive;
    public boolean isDetailActive;
    private int colorSurfaceContainerHigh;
    private int colorDefault;
    private int colorOnDrag;
    private LogicEditorTopMenuBinding binding;

    public LogicTopMenu(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public LogicTopMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize() {
        binding = LogicEditorTopMenuBinding.inflate(LayoutInflater.from(context), this, true);
        binding.tvDelete.setText(Helper.getResString(R.string.common_word_delete));
        binding.tvCopy.setText(Helper.getResString(R.string.common_word_duplicate));
        binding.tvFavorite.setText(Helper.getResString(R.string.common_word_collection));
        binding.tvDetail.setText(Helper.getResString(R.string.common_word_detail));

        colorSurfaceContainerHigh = ThemeUtils.getColor(this, R.attr.colorSurfaceContainerHigh);
        colorDefault = ContextCompat.getColor(context, R.color.view_property_tab_deactive_text);
        colorOnDrag = ContextCompat.getColor(context, android.R.color.white);
    }

    public void setCopyActive(boolean active) {
        isCopyActive = active;
        updateLayoutAppearance(binding.layoutCopy, active, R.color.scolor_green_normal, binding.tvCopy, binding.ivCopy);
    }

    public void setDeleteActive(boolean active) {
        isDeleteActive = active;
        updateLayoutAppearance(binding.layoutDelete, active, R.color.scolor_red_02, binding.tvDelete, binding.ivTrash);
    }

    public void setDetailActive(boolean active) {
        isDetailActive = active;
        updateLayoutAppearance(binding.layoutDetail, active, R.color.scolor_green_violet, binding.tvDetail, binding.ivDetail);
    }

    public void setFavoriteActive(boolean active) {
        isFavoriteActive = active;
        updateLayoutAppearance(binding.layoutFavorite, active, R.color.scolor_blue_01, binding.tvFavorite, binding.ivBookmark);
    }

    public boolean isInsideCopyArea(float x, float y) {
        return isInsideArea(binding.layoutCopy, x, y);
    }

    public boolean isInsideDeleteArea(float x, float y) {
        return isInsideArea(binding.layoutDelete, x, y);
    }

    public boolean isInsideDetailArea(float x, float y) {
        return isInsideArea(binding.layoutDetail, x, y);
    }

    public boolean isInsideFavoriteArea(float x, float y) {
        return isInsideArea(binding.layoutFavorite, x, y);
    }

    public void toggleLayoutVisibility(boolean isBlockCollection) {
        binding.layoutFavorite.setVisibility(isBlockCollection ? VISIBLE : GONE);
        binding.layoutCopy.setVisibility(isBlockCollection ? VISIBLE : GONE);
        binding.layoutDetail.setVisibility(isBlockCollection ? GONE : VISIBLE);
    }

    private void updateLayoutAppearance(
            MaterialCardView layout, boolean active, int activeColorRes, TextView textView, ImageView icon) {
        if (active) {
            layout.setCardBackgroundColor(ContextCompat.getColor(context, activeColorRes));
            textView.setTextColor(colorOnDrag);
            icon.setColorFilter(colorOnDrag);
        } else {
            layout.setCardBackgroundColor(colorSurfaceContainerHigh);
            textView.setTextColor(colorDefault);
            icon.setColorFilter(colorDefault);
        }
    }

    private boolean isInsideArea(View layout, float x, float y) {
        if (layout.getVisibility() == GONE) {
            return false;
        }

        int[] location = new int[2];
        layout.getLocationOnScreen(location);

        return x > location[0] && x < (location[0] + layout.getWidth())
                && y > location[1] && y < (location[1] + layout.getHeight());
    }
}
