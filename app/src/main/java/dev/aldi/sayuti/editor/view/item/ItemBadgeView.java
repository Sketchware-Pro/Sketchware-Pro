package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.sketchware.remod.R;

import a.a.a.sy;
import a.a.a.wB;

public class ItemBadgeView extends LinearLayout implements sy {


    private final Paint paint;
    private final float e;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean hasFixed;

    public ItemBadgeView(Context context) {
        super(context);
        e = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
        ImageView imageview = new ImageView(getContext());
        imageview.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageview.setImageResource(R.drawable.item_badge_view);
        imageview.setScaleType(ImageView.ScaleType.FIT_XY);
        imageview.setPadding(0, 0, 0, 0);
        addView(imageview);
        setGravity(17);
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
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
