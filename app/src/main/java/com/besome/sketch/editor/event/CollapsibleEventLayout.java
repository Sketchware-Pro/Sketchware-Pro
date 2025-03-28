package com.besome.sketch.editor.event;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.annotation.NonNull;

import com.besome.sketch.lib.base.CollapsibleLayout;
import com.besome.sketch.lib.ui.CollapsibleButton;
import com.google.android.material.color.MaterialColors;

import pro.sketchware.R;

import java.util.List;

import mod.hey.studios.util.Helper;

public class CollapsibleEventLayout extends CollapsibleLayout<CollapsibleButton> {
    private CollapsibleButton delete;
    private CollapsibleButton addToCollection;

    public CollapsibleEventLayout(Context context) {
        super(context);
    }

    public CollapsibleEventLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected List<CollapsibleButton> initializeButtons(@NonNull Context context) {
        CollapsibleButton reset = CollapsibleButton.create(context, 0, R.drawable.ic_mtrl_reset, R.string.common_word_reset);
        
        delete = CollapsibleButton.create(context, 1, R.drawable.ic_mtrl_delete, R.string.common_word_delete);
        var colorError = MaterialColors.getColor(delete, R.attr.colorError);
        var iconFilter = new PorterDuffColorFilter(colorError, PorterDuff.Mode.SRC_ATOP);
        delete.getLabel().setTextColor(colorError);
        delete.getIcon().setColorFilter(iconFilter);
        
        addToCollection = CollapsibleButton.create(context, 2, R.drawable.ic_mtrl_bookmark, R.string.logic_list_menu_add_to_collection);
        addToCollection.setVisibility(GONE);
        return List.of(reset, delete, addToCollection);
    }

    @Override
    protected CharSequence getWarningMessage() {
        return Helper.getResString(this, R.string.common_message_confirm);
    }

    @Override
    protected CharSequence getYesLabel() {
        return Helper.getResString(this, R.string.common_word_continue);
    }

    @Override
    protected CharSequence getNoLabel() {
        return Helper.getResString(this, R.string.common_word_cancel);
    }

    public void hideDelete() {
        delete.setVisibility(GONE);
    }

    public void hideAddToCollection() {
        addToCollection.setVisibility(GONE);
    }

    public void showDelete() {
        delete.setVisibility(VISIBLE);
    }

    public void showAddToCollection() {
        addToCollection.setVisibility(VISIBLE);
    }
}