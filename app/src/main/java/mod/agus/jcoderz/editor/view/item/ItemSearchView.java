package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemSearchView extends EditText implements sy {

    public ViewBean c;
    public boolean d;
    public boolean e;
    public Paint f;
    public int g;
    public Drawable h;
    private Rect rect;

    public ItemSearchView(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        g = (int) wB.a(context, 1.0f);
        f = new Paint(Paint.ANTI_ALIAS_FLAG);
        f.setColor(0x9599d5d0);
        rect = new Rect();
        setDrawingCacheEnabled(true);
        setFocusable(false);
        h = getBackground();
    }

    @Override
    public ViewBean getBean() {
        return c;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        c = viewBean;
    }

    @Override
    public boolean getFixed() {
        return e;
    }

    public void setFixed(boolean z) {
        e = z;
    }

    public boolean getSelection() {
        return d;
    }

    @Override
    public void setSelection(boolean z) {
        d = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (d) {
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, f);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setBackgroundColor(int i) {
        if (i == 0xffffff) {
            setBackground(h);
        } else {
            super.setBackgroundColor(i);
        }
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * g, i2 * g, i3 * g, g * i4);
    }
}
