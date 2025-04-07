package pro.sketchware.activities.editor.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.CollapsibleLayout;
import com.besome.sketch.lib.ui.CollapsibleButton;

import java.util.List;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class CollapsibleCustomComponentLayout extends CollapsibleLayout<CollapsibleButton> {
    private CollapsibleButton delete;

    public CollapsibleCustomComponentLayout(@NonNull Context context) {
        super(context);
    }

    public CollapsibleCustomComponentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected List<CollapsibleButton> initializeButtons(@NonNull Context context) {
        CollapsibleButton export = CollapsibleButton.create(context, 0, R.drawable.ic_mtrl_export, R.string.common_word_export);
        delete = CollapsibleButton.create(context, 1, R.drawable.ic_mtrl_delete, R.string.common_word_delete);
        return List.of(export, delete);
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
