package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatButton;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemMaterialButton extends AppCompatButton implements sy {
    public ViewBean c;
    public boolean d;
    public boolean e;
    public Paint f;
    public float g;
    public Drawable h;

    public ItemMaterialButton(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.g = wB.a(context, 1.0f);
        this.f = new Paint(1);
        this.f.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        setFocusable(false);
        this.h = getBackground();
    }

    @Override
    public ViewBean getBean() {
        return this.c;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.c = viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.e;
    }

    public void setFixed(boolean z) {
        this.e = z;
    }

    public boolean getSelection() {
        return this.d;
    }

    @Override
    public void setSelection(boolean z) {
        this.d = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (this.d) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f);
        }
        ItemMaterialButton.super.onDraw(canvas);
    }

    @Override
    public void setBackgroundColor(int i) {
        if (i == 16777215) {
            setBackground(this.h);
        } else {
            ItemMaterialButton.super.setBackgroundColor(i);
        }
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.g;
        ItemMaterialButton.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
