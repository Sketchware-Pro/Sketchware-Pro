package pro.sketchware.codeproject.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.databinding.ItemFileTabBinding;

public class FileTabAdapter extends RecyclerView.Adapter<FileTabAdapter.ViewHolder> {

    private final List<OpenFileTab> tabs = new ArrayList<>();
    private int activeIndex = -1;
    private final OnTabClickListener clickListener;
    private final OnTabCloseListener closeListener;

    public FileTabAdapter(OnTabClickListener clickListener, OnTabCloseListener closeListener) {
        this.clickListener = clickListener;
        this.closeListener = closeListener;
    }

    public void setTabs(List<OpenFileTab> tabs) {
        this.tabs.clear();
        this.tabs.addAll(tabs);
        notifyDataSetChanged();
    }

    public void setActiveIndex(int index) {
        int oldIndex = this.activeIndex;
        this.activeIndex = index;
        if (oldIndex >= 0 && oldIndex < tabs.size()) {
            notifyItemChanged(oldIndex);
        }
        if (index >= 0 && index < tabs.size()) {
            notifyItemChanged(index);
        }
    }

    public void notifyTabChanged(int index) {
        if (index >= 0 && index < tabs.size()) {
            notifyItemChanged(index);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFileTabBinding binding = ItemFileTabBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OpenFileTab tab = tabs.get(position);
        String title = tab.getFile().getName();
        if (tab.isModified()) {
            title = "\u2022 " + title;
        }
        holder.binding.tabTitle.setText(title);

        int colorSurfaceHighest = MaterialColors.getColor(
                holder.itemView, com.google.android.material.R.attr.colorSurfaceContainerHighest, Color.GRAY);
        int colorOnSurface = MaterialColors.getColor(
                holder.itemView, com.google.android.material.R.attr.colorOnSurface, Color.BLACK);
        int colorOnSurfaceVariant = MaterialColors.getColor(
                holder.itemView, com.google.android.material.R.attr.colorOnSurfaceVariant, Color.DKGRAY);

        if (position == activeIndex) {
            holder.itemView.setBackgroundTintList(ColorStateList.valueOf(colorSurfaceHighest));
            holder.binding.tabTitle.setTextColor(colorOnSurface);
        } else {
            holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            holder.binding.tabTitle.setTextColor(colorOnSurfaceVariant);
        }

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAbsoluteAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && clickListener != null) {
                clickListener.onTabClick(pos);
            }
        });

        holder.binding.tabClose.setOnClickListener(v -> {
            int pos = holder.getAbsoluteAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && closeListener != null) {
                closeListener.onTabClose(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }

    public interface OnTabClickListener {
        void onTabClick(int position);
    }

    public interface OnTabCloseListener {
        void onTabClose(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemFileTabBinding binding;

        ViewHolder(ItemFileTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
