package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.DatePicker;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemDatePicker extends DatePicker implements sy {

    public ViewBean f21a;
    public boolean b;
    public boolean c;
    public Paint d;
    public int e;

    public ItemDatePicker(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        e = (int) wB.a(context, 1.0f);
        d = new Paint(Paint.ANTI_ALIAS_FLAG);
        d.setColor(0x9599d5d0);
        setFocusable(false);
        setClickable(false);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return f21a;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        f21a = viewBean;
    }

    @Override
    public boolean getFixed() {
        return c;
    }

    public void setFixed(boolean z) {
        c = z;
    }

    public boolean getSelection() {
        return b;
    }

    @Override
    public void setSelection(boolean z) {
        b = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), d);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * e, i2 * e, i3 * e, e * i4);
    }
}
