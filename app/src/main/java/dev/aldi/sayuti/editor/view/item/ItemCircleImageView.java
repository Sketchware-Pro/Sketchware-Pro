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

    private final Paint paint;
    private final float g;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean hasFixed;

    public ItemCircleImageView(Context context) {
        super(context);
        g = wB.a(context, 1.0f);
        paint = new Paint(1);
        paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return viewBean;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public boolean getFixed() {
        return hasFixed;
    }

    public void setFixed(boolean z) {
        hasFixed = z;
    }

    public boolean getSelection() {
        return hasSelection;
    }

    @Override
    public void setSelection(boolean z) {
        hasSelection = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (hasSelection) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), paint);
        }
        ItemCircleImageView.super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = g;
        ItemCircleImageView.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
