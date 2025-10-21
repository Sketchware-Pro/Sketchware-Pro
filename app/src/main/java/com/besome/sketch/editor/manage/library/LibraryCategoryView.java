package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pro.sketchware.databinding.ManageLibraryCategoryItemBinding;

public class LibraryCategoryView extends FrameLayout {

    private final ManageLibraryCategoryItemBinding binding;

    public LibraryCategoryView(@NonNull Context context) {
        super(context);
        binding = ManageLibraryCategoryItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        if (charSequence == null) {
            binding.title.setVisibility(View.GONE);
            return;
        }
        binding.title.setText(charSequence);
    }

    public void addLibraryItem(LibraryItemView libraryItemView, boolean addDivider) {
        binding.container.addView(libraryItemView);
        libraryItemView.showDivider(addDivider);
    }
}
