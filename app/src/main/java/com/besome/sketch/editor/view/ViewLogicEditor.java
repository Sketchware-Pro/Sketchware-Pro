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
        this.blockPane = new BlockPane(context);
        this.blockPane.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(this.blockPane);
    }

    public BlockPane getBlockPane() {
        return this.blockPane;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.k) {
            this.blockPane.getLayoutParams().width = right - left;
            this.blockPane.getLayoutParams().height = bottom - top;
            this.blockPane.b();
            this.k = false;
        }
    }

    public boolean a(float x, float y) {
        getLocationOnScreen(this.locationOnScreen);
        int[] iArr = this.locationOnScreen;
        if (x > iArr[0] && x < iArr[0] + getWidth()) {
            int[] iArr2 = this.locationOnScreen;
            return y > iArr2[1] && y < iArr2[1] + getHeight();
        }
        return false;
    }
}
