package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.besome.sketch.editor.logic.BlockPane;

public class ViewLogicEditor extends LogicEditorScrollView {
    private final BlockPane blockPane;
    private final int[] locationOnScreen = new int[2];
    private boolean k = true;

    public ViewLogicEditor(Context context) {
        this(context, null);
    }

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewLogicEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ViewLogicEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        if (k) {
            blockPane.getLayoutParams().width = right - left;
            blockPane.getLayoutParams().height = bottom - top;
            blockPane.b();
            k = false;
        }
    }

    public boolean a(float x, float y) {
        getLocationOnScreen(locationOnScreen);
        int[] iArr = locationOnScreen;
        if (x > iArr[0] && x < iArr[0] + getWidth()) {
            int[] iArr2 = locationOnScreen;
            return y > iArr2[1] && y < iArr2[1] + getHeight();
        }
        return false;
    }
}
