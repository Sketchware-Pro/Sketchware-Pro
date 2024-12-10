package com.besome.sketch.design.structure;

import static pro.sketchware.SketchApplication.getContext;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pro.sketchware.databinding.LayoutStructureItemBinding;

public class LayoutStructureAdapter extends ListAdapter<ViewBean, LayoutStructureAdapter.LayoutStructureAdapterViewHolder> {

    private LayoutStructureItemListener layoutStructureItemListener;
    private int selectedItemPosition;
    private final ArrayList<ViewBean> viewBeansList;
    private final Map<Integer, Boolean> expandedStateMap;

    public LayoutStructureAdapter(ArrayList<ViewBean> viewBeansList) {
        super(new LayoutStructureAdapterDiffCallback());
        this.viewBeansList = viewBeansList;
        this.expandedStateMap = new HashMap<>();
    }

    public void setLayoutStructureItemListener(LayoutStructureItemListener layoutStructureItemListener) {
        this.layoutStructureItemListener = layoutStructureItemListener;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
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
        boolean isViewGroup = bean.getClassInfo().a("ViewGroup");

        boolean isExpanded = Boolean.TRUE.equals(expandedStateMap.getOrDefault(position, false));
        holder.binding.recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.binding.expandToggle.setRotation(isExpanded ? 180f : 0f);

        holder.binding.getRoot().setOnClickListener(v -> {
            if (isViewGroup) {
                ArrayList<ViewBean> childViews = new ArrayList<>();
                for (ViewBean viewBean : viewBeansList) {
                    if (viewBean.parent != null && viewBean.parent.equals(bean.id)) {
                        childViews.add(viewBean);
                    }
                }
                LayoutStructureAdapter adapter = new LayoutStructureAdapter(viewBeansList);
                holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                holder.binding.recyclerView.setAdapter(adapter);
                adapter.submitList(childViews);

                boolean currentlyExpanded = Boolean.TRUE.equals(expandedStateMap.getOrDefault(position, false));
                if (currentlyExpanded) {
                    holder.binding.recyclerView.setVisibility(View.GONE);
                    ObjectAnimator.ofFloat(holder.binding.expandToggle, "rotation", 180f, 0f).start();
                } else {
                    holder.binding.recyclerView.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(holder.binding.expandToggle, "rotation", 0f, 180f).start();
                }
                expandedStateMap.put(position, !currentlyExpanded);
            } else {
                setSelectedItemPosition(getViewBeanPosition(bean));
                if (layoutStructureItemListener != null) {
                    layoutStructureItemListener.onClick(bean);
                }
            }
        });

        holder.binding.expandToggle.setVisibility(isViewGroup ? View.VISIBLE : View.GONE);
        holder.binding.viewIcon.setImageResource(imgRes);
        holder.binding.viewName.setText(bean.id);
    }

    private int getViewBeanPosition(ViewBean viewBean) {
        for (int i = 0, viewBeansListSize = viewBeansList.size(); i < viewBeansListSize; i++) {
            ViewBean bean = viewBeansList.get(i);
            if (bean.isEqual(viewBean)) return i;
        }
        return -1;
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
            return oldItem.isEqual(newItem);
        }
    }
}