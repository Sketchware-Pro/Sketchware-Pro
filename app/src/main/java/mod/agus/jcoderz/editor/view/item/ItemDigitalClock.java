package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.DigitalClock;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

@Deprecated
public class ItemDigitalClock extends DigitalClock implements sy {
    public ViewBean O;
    public boolean P;
    public boolean Q;
    public Paint R;
    public float S;

    @Deprecated
    public ItemDigitalClock(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.S = wB.a(context, 1.0f);
        this.R = new Paint(1);
        this.R.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return this.O;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.O = viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.Q;
    }

    public void setFixed(boolean z) {
        this.Q = z;
    }

    public boolean getSelection() {
        return this.P;
    }

    @Override
    public void setSelection(boolean z) {
        this.P = z;
        invalidate();
    }

    @Override
    @Deprecated
    public void onDraw(Canvas canvas) {
        if (this.P) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.R);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        float f = this.S;
        super.setPadding((int) (((float) i) * f), (int) (((float) i2) * f), (int) (((float) i3) * f), (int) (f * ((float) i4)));
    }
}
