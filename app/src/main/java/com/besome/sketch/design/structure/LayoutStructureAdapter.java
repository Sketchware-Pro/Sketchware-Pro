package com.besome.sketch.design.structure;

import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import pro.sketchware.databinding.LayoutStructureItemBinding;


public class LayoutStructureAdapter extends ListAdapter<ViewBean, LayoutStructureAdapter.LayoutStructureAdapterViewHolder> {
    
    private LayoutStructureItemListener layoutStructureItemListener;
    
    public LayoutStructureAdapter() {
        super(new LayoutStructureAdapterDiffCallback());
    }
    
    public void setLayoutStructureItemListener(LayoutStructureItemListener layoutStructureItemListener) {
        this.layoutStructureItemListener = layoutStructureItemListener;
    }
    
    @NonNull
    @Override
    public LayoutStructureAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = LayoutStructureItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LayoutStructureAdapterViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LayoutStructureAdapterViewHolder holder, int position) {
        var bean = getItem(position);
        var imgRes = ViewBean.getViewTypeResId(bean.type);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (layoutStructureItemListener != null) layoutStructureItemListener.onClick(bean);
        });
        holder.binding.viewIcon.setImageResource(imgRes);
        holder.binding.viewName.setText(bean.id);
    }
    
    public static class LayoutStructureAdapterViewHolder extends RecyclerView.ViewHolder {
        
        public LayoutStructureItemBinding binding;
        
        public LayoutStructureAdapterViewHolder(LayoutStructureItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    
    public static class LayoutStructureAdapterDiffCallback extends DiffUtil.ItemCallback<ViewBean> {
        @Override
        public boolean areItemsTheSame(@NonNull ViewBean oldItem, @NonNull ViewBean newItem) {
            return oldItem.isEqual(newItem);
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull ViewBean oldItem, @NonNull ViewBean newItem) {
            return oldItem.equals(newItem);
        }
    }
}