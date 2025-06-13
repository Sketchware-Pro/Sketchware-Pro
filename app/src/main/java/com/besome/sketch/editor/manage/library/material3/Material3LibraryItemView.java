package com.besome.sketch.editor.manage.library.material3;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.LibraryItemView;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

@SuppressLint("ViewConstructor")
public class Material3LibraryItemView extends LibraryItemView {


    public Material3LibraryItemView(Context context) {
        super(context);
    }

    @Override
    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {
        icon.setImageResource(R.drawable.ic_mtrl_material3);
        title.setText(Helper.getResString(R.string.design_library_title_material3));
        description.setText("Modern Material design with adaptive dynamic theming");
        assert projectLibraryBean != null;
        boolean isEnabled = new Material3LibraryManager(projectLibraryBean).isMaterial3Enabled();
        enabled.setText(isEnabled ? "ON" : "OFF");
        enabled.setSelected(isEnabled);
    }
}
