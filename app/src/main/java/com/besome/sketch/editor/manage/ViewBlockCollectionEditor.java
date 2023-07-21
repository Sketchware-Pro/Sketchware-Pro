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
        super(context);
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
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.k) {
            this.j.getLayoutParams().width = i3 - i;
            this.j.getLayoutParams().height = i4 - i2;
            this.j.b();
            this.k = false;
        }
    }

    public ViewBlockCollectionEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.l = new int[2];
        initialize(context);
    }
}
