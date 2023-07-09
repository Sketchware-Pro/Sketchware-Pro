package com.besome.sketch.editor.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.lib.ui.CollapsibleButton;
import com.besome.sketch.lib.base.CollapsibleLayout;
import com.sketchware.remod.R;

import java.util.List;

import mod.hey.studios.util.Helper;

public class CollapsibleComponentLayout extends CollapsibleLayout<CollapsibleButton> {
    private CollapsibleButton delete;

    public CollapsibleComponentLayout(@NonNull Context context) {
        super(context);
    }

    public CollapsibleComponentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected List<CollapsibleButton> initializeButtons(@NonNull Context context) {
        delete = CollapsibleButton.create(context, 0, R.drawable.delete_96, R.string.common_word_delete);
        return List.of(delete);
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

    public CollapsibleButton getDeleteButton() {
        return delete;
    }
}
