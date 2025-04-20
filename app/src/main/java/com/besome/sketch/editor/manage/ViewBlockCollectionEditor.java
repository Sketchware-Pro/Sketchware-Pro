package com.besome.sketch.editor.manage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.editor.view.LogicEditorScrollView;

public class ViewBlockCollectionEditor extends LogicEditorScrollView {
    private BlockPane blockPane;
    private boolean layoutParamsToBeSet;

    public ViewBlockCollectionEditor(Context context) {
        this(context, null);
    }

    public ViewBlockCollectionEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        layoutParamsToBeSet = true;
        initialize(context);
    }

    private void initialize(Context context) {
        blockPane = new BlockPane(context);
        blockPane.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(blockPane);
    }

    public BlockPane getBlockPane() {
        return blockPane;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (layoutParamsToBeSet) {
            blockPane.getLayoutParams().width = right - left;
            blockPane.getLayoutParams().height = bottom - top;
            blockPane.b();
            layoutParamsToBeSet = false;
        }
    }
}
