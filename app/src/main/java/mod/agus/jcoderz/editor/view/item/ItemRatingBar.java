package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.RatingBar;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemRatingBar extends RatingBar implements sy {

    public ViewBean b;
    public boolean c;
    public boolean d;
    public Paint e;
    public int f;

    public ItemRatingBar(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        f = (int) wB.a(context, 1.0f);
        e = new Paint(Paint.ANTI_ALIAS_FLAG);
        e.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return b;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        b = viewBean;
    }

    @Override
    public boolean getFixed() {
        return d;
    }

    public void setFixed(boolean z) {
        d = z;
    }

    public boolean getSelection() {
        return c;
    }

    @Override
    public void setSelection(boolean z) {
        c = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (c) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), e);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * f, i2 * f, i3 * f, f * i4);
    }
}
