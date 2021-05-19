package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;
import de.hdodenhof.circleimageview.CircleImageView;

public class ItemCircleImageView extends CircleImageView implements sy {
    public ViewBean c;
    public boolean d;
    public boolean e;
    public Paint f;
    public float g;

    public ItemCircleImageView(Context context) {
        super(context);
        a(context);
    }

    public void a(Context context) {
        this.g = wB.a(context, 1.0f);
        this.f = new Paint(1);
        this.f.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.c;
    }

    public void setBean(ViewBean viewBean) {
        this.c = viewBean;
    }

    public boolean getFixed() {
        return this.e;
    }

    public void setFixed(boolean z) {
        this.e = z;
    }

    public boolean getSelection() {
        return this.d;
    }

    public void setSelection(boolean z) {
        this.d = z;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (this.d) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f);
        }
        ItemCircleImageView.super.onDraw(canvas);
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.g;
        ItemCircleImageView.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
