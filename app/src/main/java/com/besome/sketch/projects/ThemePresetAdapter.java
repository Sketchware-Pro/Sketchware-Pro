package com.besome.sketch.projects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.R;
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
        holder.binding.themeColorsPreview.setSelected(position == selectedPosition);
        
        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAbsoluteAdapterPosition();
            
            if (previousSelected != -1) {
                notifyItemChanged(previousSelected);
            }
            notifyItemChanged(selectedPosition);
            
            if (listener != null) {
                listener.onThemePresetClick(theme, selectedPosition);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return themePresets.size();
    }
    
    public void setSelectedPosition(int position) {
        int previousSelected = selectedPosition;
        selectedPosition = position;
        
        if (previousSelected != -1) {
            notifyItemChanged(previousSelected);
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition);
        }
    }
    
    public int getSelectedPosition() {
        return selectedPosition;
    }
    
    static class ThemePresetViewHolder extends RecyclerView.ViewHolder {
        ThemePresetItemBinding binding;
        
        public ThemePresetViewHolder(@NonNull ThemePresetItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 