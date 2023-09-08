package mod.elfilibustero.sketch.editor.xml;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.lib.ui.CollapsibleButton;
import com.besome.sketch.lib.base.CollapsibleLayout;
import com.sketchware.remod.R;

import java.util.List;

import mod.hey.studios.util.Helper;

public class CollapsibleXmlLayout extends CollapsibleLayout<CollapsibleButton> {
    private CollapsibleButton edit;
    private CollapsibleButton insert;
    private CollapsibleButton delete;

    public CollapsibleXmlLayout(@NonNull Context context) {
        super(context);
    }

    public CollapsibleXmlLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected List<CollapsibleButton> initializeButtons(@NonNull Context context) {
        edit = CollapsibleButton.create(context, 0, R.drawable.ic_edit_yellow_48dp, R.string.common_word_edit);
        insert = CollapsibleButton.create(context, 1, R.drawable.insert_64, R.string.common_word_insert);
        delete = CollapsibleButton.create(context, 2, R.drawable.delete_96, R.string.common_word_delete);
        return List.of(edit, insert, delete);
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
    
    public CollapsibleButton getEditButton() {
        return edit;
    }
    
    public CollapsibleButton getInsertButton() {
        return insert;
    }

    public CollapsibleButton getDeleteButton() {
        return delete;
    }
}
