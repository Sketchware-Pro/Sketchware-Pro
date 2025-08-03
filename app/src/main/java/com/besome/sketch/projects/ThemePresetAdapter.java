package com.besome.sketch.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.databinding.ThemePresetItemBinding;

import java.util.List;

public class ThemePresetAdapter extends RecyclerView.Adapter<ThemePresetAdapter.ThemePresetViewHolder> {

    private final Context context;
    private final List<ThemeManager.ThemePreset> themePresets;
    private final OnThemePresetClickListener listener;
    private int selectedPosition = -1;

    public interface OnThemePresetClickListener {
        void onThemePresetClick(ThemeManager.ThemePreset theme, int position);
    }

    public ThemePresetAdapter(Context context, List<ThemeManager.ThemePreset> themePresets, OnThemePresetClickListener listener) {
        this.context = context;
        this.themePresets = themePresets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ThemePresetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ThemePresetItemBinding binding = ThemePresetItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ThemePresetViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemePresetViewHolder holder, int position) {
        ThemeManager.ThemePreset theme = themePresets.get(position);

        holder.binding.themeName.setText(theme.name);

        holder.binding.colorPrimary.setBackgroundColor(theme.colorPrimary);
        holder.binding.colorPrimaryDark.setBackgroundColor(theme.colorPrimaryDark);
        holder.binding.colorAccent.setBackgroundColor(theme.colorAccent);
        holder.binding.colorControlHighlight.setBackgroundColor(theme.colorControlHighlight);
        holder.binding.colorControlNormal.setBackgroundColor(theme.colorControlNormal);

        holder.itemView.setSelected(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            unselectThePreviousTheme(holder.getAbsoluteAdapterPosition());

            if (listener != null) {
                listener.onThemePresetClick(theme, selectedPosition);
            }
        });
    }

    public void unselectThePreviousTheme(int newPosition) {
        if (selectedPosition == newPosition) {
            return;
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition);
        }
        selectedPosition = newPosition;
        if (newPosition != -1) {
            notifyItemChanged(newPosition);
        }
    }

    @Override
    public int getItemCount() {
        return themePresets.size();
    }

    public static class ThemePresetViewHolder extends RecyclerView.ViewHolder {
        ThemePresetItemBinding binding;

        public ThemePresetViewHolder(@NonNull ThemePresetItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 