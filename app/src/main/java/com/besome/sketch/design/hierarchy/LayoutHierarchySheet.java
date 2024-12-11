package com.besome.sketch.design.hierarchy;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.view.ViewProperty;

import com.google.android.material.sidesheet.SideSheetDialog;

import java.util.ArrayList;

import pro.sketchware.R;
import pro.sketchware.databinding.LayoutHierarchySheetBinding;

public class LayoutHierarchySheet extends SideSheetDialog {

    private LayoutHierarchySheetBinding binding;
    private LayoutHierarchyAdapter adapter;
    
    public LayoutHierarchySheet(Context context, ArrayList<ViewBean> viewsList) {
        super(context);
        binding = LayoutHierarchySheetBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        setSheetEdge(Gravity.END);
        binding.close.setOnClickListener(v -> hide());
        
        adapter = new LayoutHierarchyAdapter(viewsList);
        adapter.setLayoutHierarchyItemListener(bean -> {
            var prop = getViewProperty(context);
            var isViewGroup = bean.getClassInfo().a("ViewGroup");
            if (prop != null) {
                prop.a(bean.id);
                showViewProperty(prop);
            }
            if (!isViewGroup) hide();
        });
        binding.list.setAdapter(adapter);
        
        ArrayList<ViewBean> viewsList2 = new ArrayList<>();
        for (ViewBean viewBean : viewsList) {
            String parent = viewBean.parent;
            if (parent == null || parent.isEmpty() || parent.equals("root")) {
                viewsList2.add(viewBean);
            }
        }
        adapter.submitList(viewsList2);
    }
    
    private void showViewProperty(ViewProperty viewProperty) {
        var animator = ObjectAnimator.ofFloat(viewProperty, View.TRANSLATION_Y, 0.0F);
        animator.setDuration(700L);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
    
    public LayoutHierarchyAdapter getAdapter() {
        return adapter;
    }
    
    private ViewProperty getViewProperty(Context context) {
        if (context instanceof DesignActivity designAct) return designAct.findViewById(R.id.view_property);
        return null;
    }
}