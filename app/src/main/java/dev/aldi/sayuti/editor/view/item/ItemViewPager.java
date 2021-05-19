package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.appcompat.widget.AppCompatTextView;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemViewPager extends AppCompatTextView implements sy {
    public ViewBean d;
    public boolean e;
    public boolean f;
    public Paint g;
    public float h;

    public ItemViewPager(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        setGravity(17);
        setTypeface(null, Typeface.BOLD);
        setText("ViewPager");
        this.h = wB.a(context, 1.0f);
        this.g = new Paint(1);
        this.g.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.d;
    }

    public void setBean(ViewBean viewBean) {
        this.d = viewBean;
    }

    public boolean getFixed() {
        return this.f;
    }

    public void setFixed(boolean z) {
        this.f = z;
    }

    public boolean getSelection() {
        return this.e;
    }

    public void setSelection(boolean z) {
        this.e = z;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (this.e) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.g);
        }
        ItemViewPager.super.onDraw(canvas);
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.h;
        ItemViewPager.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
