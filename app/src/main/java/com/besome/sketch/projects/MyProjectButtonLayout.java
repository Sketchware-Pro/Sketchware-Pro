package com.besome.sketch.projects;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.besome.sketch.lib.base.CollapsibleLayout;

import java.util.List;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class MyProjectButtonLayout extends CollapsibleLayout<MyProjectButton> {
    public MyProjectButtonLayout(Context context) {
        this(context, null);
    }

    public MyProjectButtonLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected List<MyProjectButton> initializeButtons(@NonNull Context context) {
        return List.of(
                MyProjectButton.create(context, 0, R.drawable.ic_mtrl_settings, Helper.getResString(this, R.string.myprojects_list_menu_title_settings)),
                MyProjectButton.create(context, 1, R.drawable.ic_backup, "Back up"),
                MyProjectButton.create(context, 2, R.drawable.ic_export_grey_48dp, Helper.getResString(this, R.string.myprojects_list_menu_title_sign_export)),
                MyProjectButton.create(context, 3, R.drawable.ic_delete_grey_48dp, Helper.getResString(this, R.string.myprojects_list_menu_title_delete)),
                MyProjectButton.create(context, 4, R.drawable.ic_mtrl_settings, "Config")
        );
    }

    @Override
    protected CharSequence getWarningMessage() {
        return Helper.getResString(this, R.string.myprojects_confirm_project_delete);
    }

    @Override
    protected CharSequence getYesLabel() {
        return Helper.getResString(this, R.string.common_word_delete);
    }

    @Override
    protected CharSequence getNoLabel() {
        return Helper.getResString(this, R.string.common_word_cancel);
    }
}
