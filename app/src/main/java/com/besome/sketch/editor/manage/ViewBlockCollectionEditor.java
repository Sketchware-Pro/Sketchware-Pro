package com.besome.sketch.editor.manage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.editor.view.LogicEditorScrollView;

public class ViewBlockCollectionEditor extends LogicEditorScrollView {
    public Context i;
    public BlockPane j;
    public boolean k;
    public int[] l;

    public ViewBlockCollectionEditor(Context context) {
        this(context, null);
    }

    public ViewBlockCollectionEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.l = new int[2];
        initialize(context);
    }

    private void initialize(Context context) {
        this.i = context;
        this.j = new BlockPane(this.i);
        this.j.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        addView(this.j);
    }

    public BlockPane getBlockPane() {
        return this.j;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.k) {
            this.j.getLayoutParams().width = right - left;
            this.j.getLayoutParams().height = bottom - top;
            this.j.b();
            this.k = false;
        }
    }
}
