package mod.agus.jcoderz.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.AnalogClock;
import com.besome.sketch.beans.ViewBean;

@Deprecated
public class ItemAnalogClock extends AnalogClock implements sy {
    public ViewBean O;
    public boolean P;
    public boolean Q;
    public Paint R;
    public float S;

@Deprecated
    public ItemAnalogClock(Context context) {
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

    public ViewBean getBean() {
        return this.O;
    }

    public boolean getFixed() {
        return this.Q;
    }

    public boolean getSelection() {
        return this.P;
    }

    @Deprecated
    public void onDraw(Canvas canvas) {
        if (this.P) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.R);
        }
        super.onDraw(canvas);
    }

    public void setBean(ViewBean viewBean) {
        this.O = viewBean;
    }

    public void setFixed(boolean z) {
        this.Q = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f = this.S;
        super.setPadding((int) (((float) i) * f), (int) (((float) i2) * f), (int) (((float) i3) * f), (int) (f * ((float) i4)));
    }

    public void setSelection(boolean z) {
        this.P = z;
        invalidate();
    }
}
