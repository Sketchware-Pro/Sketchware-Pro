package com.besome.sketch.design.hierarchy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Gravity;

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
            if (prop != null) {
                prop.selectView(bean);
                prop.e();
            }
            hide();
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
    
    public LayoutHierarchyAdapter getAdapter() {
        return adapter;
    }
    
    private ViewProperty getViewProperty(Context context) {
        if (context instanceof DesignActivity designAct) return designAct.findViewById(R.id.view_property);
        return null;
    }
}