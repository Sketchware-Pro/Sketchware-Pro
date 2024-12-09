package com.besome.sketch.design.structure;

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
import pro.sketchware.databinding.LayoutStructureSheetBinding;

public class LayoutStructureSheet extends SideSheetDialog {

    private LayoutStructureSheetBinding binding;
    
    public LayoutStructureSheet(Context context, ArrayList<ViewBean> viewsList) {
        super(context);
        binding = LayoutStructureSheetBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        setSheetEdge(Gravity.END);
        binding.close.setOnClickListener(v -> hide());
        var adapter = new LayoutStructureAdapter();
        adapter.setLayoutStructureItemListener(bean -> {
            var prop = getViewProperty(context);
            if (prop != null) prop.selectView(bean);
        });
        binding.list.setAdapter(adapter);
        adapter.submitList(viewsList);
    }
    
    private ViewProperty getViewProperty(Context context) {
        if (context instanceof DesignActivity designAct) return designAct.findViewById(R.id.view_property);
        return null;
    }
}