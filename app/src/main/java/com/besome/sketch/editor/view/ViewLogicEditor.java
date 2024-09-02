package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.besome.sketch.editor.logic.BlockPane;

public class ViewLogicEditor extends LogicEditorScrollView {

    public Context context;

    public BlockPane blockPane;
    public boolean k;

    public int[] locationOnScreen;

    public ViewLogicEditor(Context context) {
        super(context);
        this.k = true;
        this.locationOnScreen = new int[2];
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        this.blockPane = new BlockPane(this.context);
        this.blockPane.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
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

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.locationOnScreen = new int[2];
        initialize(context);
    }

    public boolean a(float x, float y) {
        getLocationOnScreen(this.locationOnScreen);
        int[] iArr = this.locationOnScreen;
        if (x > iArr[0] && x < iArr[0] + getWidth()) {
            int[] iArr2 = this.locationOnScreen;
            if (y > iArr2[1] && y < iArr2[1] + getHeight()) {
                return true;
            }
        }
        return false;
    }
}
