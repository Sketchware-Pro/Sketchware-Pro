package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.sketchware.remod.R;

import a.a.a.sy;
import a.a.a.wB;

public class ItemVideoView extends LinearLayout implements sy {

    public ViewBean f24a;
    public boolean b;
    public boolean c;
    public Paint d;
    public int e;
    public ImageView f;

    public ItemVideoView(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        e = (int) wB.a(context, 1.0f);
        d = new Paint(Paint.ANTI_ALIAS_FLAG);
        d.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
        f = new ImageView(getContext());
        f.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        f.setImageResource(R.drawable.item_video_view);
        f.setScaleType(ImageView.ScaleType.FIT_XY);
        f.setPadding(0, 0, 0, 0);
        addView(f);
        setGravity(Gravity.CENTER);
    }

    @Override
    public ViewBean getBean() {
        return f24a;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        f24a = viewBean;
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
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * e, i2 * e, i3 * e, e * i4);
    }
}
