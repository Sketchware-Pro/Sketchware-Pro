package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.besome.sketch.editor.logic.BlockPane;

public class ViewLogicEditor extends LogicEditorScrollView {
    public Context i;
    public BlockPane j;
    public boolean k;
    public int[] l;

    public ViewLogicEditor(Context context) {
        super(context);
        this.k = true;
        this.l = new int[2];
        a(context);
    }

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.k = true;
        this.l = new int[2];
        a(context);
    }

    @Override
    public void a(Context context) {
        this.i = context;
        this.j = new BlockPane(this.i);
        this.j.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        addView(this.j);
    }

    public BlockPane getBlockPane() {
        return this.j;
    }

    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        if (this.k) {
            this.j.getLayoutParams().width = i4 - i2;
            this.j.getLayoutParams().height = i5 - i3;
            this.j.b();
            this.k = false;
        }
    }

    public boolean a(float f, float f2) {
        getLocationOnScreen(this.l);
        int[] iArr = this.l;
        if (f > ((float) iArr[0]) && f < ((float) (iArr[0] + getWidth()))) {
            int[] iArr2 = this.l;
            return f2 > ((float) iArr2[1]) && f2 < ((float) (iArr2[1] + getHeight()));
        }
        return true;
    }
}
