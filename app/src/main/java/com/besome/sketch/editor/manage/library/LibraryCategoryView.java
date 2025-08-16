package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import a.a.a.wB;
import pro.sketchware.R;

public class LibraryCategoryView extends FrameLayout {
    private final ViewGroup container;
    private final TextView title;

    public LibraryCategoryView(@NonNull Context context) {
        super(context);
        wB.a(context, this, R.layout.manage_library_category_item);

        container = findViewById(R.id.container);
        title = findViewById(R.id.title);
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            title.setVisibility(View.GONE);
            return;
        }
        title.setText(charSequence);
    }

    public void addLibraryItem(LibraryItemView libraryItemView, boolean addDivider) {
        container.addView(libraryItemView);
        libraryItemView.showDivider(addDivider);
    }
}
