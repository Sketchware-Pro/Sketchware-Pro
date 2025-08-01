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
        View view = LayoutInflater.from(context).inflate(R.layout.theme_preset_item, parent, false);
        return new ThemePresetViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ThemePresetViewHolder holder, int position) {
        ThemeManager.ThemePreset theme = themePresets.get(position);
        
        holder.themeName.setText(theme.name);
        
        // Define as cores do tema
        holder.colorPrimary.setBackgroundColor(theme.colorPrimary);
        holder.colorPrimaryDark.setBackgroundColor(theme.colorPrimaryDark);
        holder.colorAccent.setBackgroundColor(theme.colorAccent);
        holder.colorControlHighlight.setBackgroundColor(theme.colorControlHighlight);
        holder.colorControlNormal.setBackgroundColor(theme.colorControlNormal);
        
        // Define o estado selecionado
        holder.itemView.setSelected(position == selectedPosition);
        holder.themeColorsPreview.setSelected(position == selectedPosition);
        
        holder.itemView.setOnClickListener(v -> {
            int previousSelected = selectedPosition;
            selectedPosition = holder.getAbsoluteAdapterPosition();
            
            // Notifica mudanças apenas nos itens afetados
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
        TextView themeName;
        View colorPrimary;
        View colorPrimaryDark;
        View colorAccent;
        View colorControlHighlight;
        View colorControlNormal;
        LinearLayout themeColorsPreview;
        
        public ThemePresetViewHolder(@NonNull View itemView) {
            super(itemView);
            themeName = itemView.findViewById(R.id.theme_name);
            colorPrimary = itemView.findViewById(R.id.color_primary);
            colorPrimaryDark = itemView.findViewById(R.id.color_primary_dark);
            colorAccent = itemView.findViewById(R.id.color_accent);
            colorControlHighlight = itemView.findViewById(R.id.color_control_highlight);
            colorControlNormal = itemView.findViewById(R.id.color_control_normal);
            themeColorsPreview = itemView.findViewById(R.id.theme_colors_preview);
        }
    }
} 