package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.AnalogClock;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

@Deprecated
public class ItemAnalogClock extends AnalogClock implements sy {

    public ViewBean O;
    public boolean P;
    public boolean Q;
    public Paint R;
    public int S;

    @Deprecated
    public ItemAnalogClock(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        S = (int) wB.a(context, 1.0f);
        R = new Paint(Paint.ANTI_ALIAS_FLAG);
        R.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return O;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        O = viewBean;
    }

    @Override
    public boolean getFixed() {
        return Q;
    }

    public void setFixed(boolean z) {
        Q = z;
    }

    public boolean getSelection() {
        return P;
    }

    @Override
    public void setSelection(boolean z) {
        P = z;
        invalidate();
    }

    @Override
    @Deprecated
    public void onDraw(Canvas canvas) {
        if (P) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), R);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * S, i2 * S, i3 * S, S * i4);
    }
}
