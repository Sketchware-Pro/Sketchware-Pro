package com.besome.sketch.editor.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.editor.event.CollapsibleButton;
import com.besome.sketch.lib.base.CollapsibleLayout;
import com.sketchware.remod.R;

import java.util.List;

public class CollapsibleComponentLayout extends CollapsibleLayout {
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

    public CollapsibleButton getDeleteButton() {
        return delete;
    }
}
