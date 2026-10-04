package com.besome.sketch.editor.logic;

import static com.besome.sketch.editor.logic.PaletteSelector.paletteSelectorRecord;
import static com.google.android.material.color.MaterialColors.harmonizeWithPrimary;
import static pro.sketchware.utility.ThemeUtils.getColor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a.a.a.Vs;
import a.a.a.wB;
import pro.sketchware.R;
import pro.sketchware.databinding.PaletteSelectorItemBinding;

public class PaletteSelectorAdapter extends RecyclerView.Adapter<PaletteSelectorAdapter.PaletteSelectorViewHolder> {

    private final PaletteSelector paletteSelector;
    private final List<paletteSelectorRecord> paletteList = new ArrayList<>();
    private final Context context;
    private final Vs onBlockCategorySelectListener;
    private int selectedPosition = -1;

    public PaletteSelectorAdapter(PaletteSelector paletteSelector, Vs onBlockCategorySelectListener) {
        this.paletteSelector = paletteSelector;
        context = paletteSelector.getContext();
        this.onBlockCategorySelectListener = onBlockCategorySelectListener;
    }

    public void setPalettes(List<paletteSelectorRecord> list) {
        paletteList.clear();
        for (paletteSelectorRecord palette : list) {
            if (paletteSelector.matchesSearch(palette.text())) {
                paletteList.add(palette);
            }
        }
        if (!paletteList.isEmpty() && selectedPosition < 0) {
            selectedPosition = 0;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PaletteSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaletteSelectorItemBinding binding = PaletteSelectorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PaletteSelectorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaletteSelectorViewHolder holder, int position) {
        paletteSelectorRecord item = paletteList.get(position);
        int id = item.index();
        String title = item.text();
        int color = harmonizeWithPrimary(context, item.color());

        boolean selected = position == selectedPosition;
        holder.binding.tvCategory.setText(title);

        GradientDrawable dot = new GradientDrawable();
        dot.setShape(GradientDrawable.OVAL);
        dot.setColor(color);
        holder.binding.bg.setBackground(dot);

        GradientDrawable chipBackground = new GradientDrawable();
        chipBackground.setCornerRadius(wB.a(context, 12f));
        chipBackground.setColor(selected ? ColorUtils.setAlphaComponent(color, 0x33) : Color.TRANSPARENT);
        holder.binding.chip.setBackground(chipBackground);

        holder.binding.tvCategory.setTextColor(getColor(context, selected ? R.attr.colorOnSurface : R.attr.colorOnSurfaceVariant));
        holder.binding.tvCategory.setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
        holder.binding.bg.animate().scaleX(selected ? 1.5f : 1f).scaleY(selected ? 1.5f : 1f).setDuration(150).start();

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAbsoluteAdapterPosition();
            notifyDataSetChanged();
            if (onBlockCategorySelectListener != null) {
                onBlockCategorySelectListener.a(id, color);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paletteList.size();
    }

    public void selectPaletteById(int tag) {
        for (int i = 0; i < paletteList.size(); i++) {
            int paletteId = paletteList.get(i).index();
            if (paletteId == tag) {
                selectedPosition = i;
                notifyDataSetChanged();
                if (onBlockCategorySelectListener != null) {
                    onBlockCategorySelectListener.a(paletteId, paletteList.get(i).color());
                }
                break;
            }
        }
    }

    public void selectPosition(int pos) {
        if (pos >= 0 && pos < paletteList.size()) {
            selectedPosition = pos;
            notifyDataSetChanged();
            if (onBlockCategorySelectListener != null) {
                onBlockCategorySelectListener.a(paletteList.get(pos).index(), paletteList.get(pos).color());
            }
        }
    }

    public static class PaletteSelectorViewHolder extends RecyclerView.ViewHolder {
        final PaletteSelectorItemBinding binding;

        public PaletteSelectorViewHolder(PaletteSelectorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
