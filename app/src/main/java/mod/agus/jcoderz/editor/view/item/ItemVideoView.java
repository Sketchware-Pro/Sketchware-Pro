package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;
import pro.sketchware.R;

public class ItemVideoView extends LinearLayout implements sy {

    private final Paint paint;
    private final int paddingFactor;
    private final Rect rect;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean isFixed;

    public ItemVideoView(Context context) {
        super(context);
        paddingFactor = (int) wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        rect = new Rect();
        setDrawingCacheEnabled(true);
        ImageView background = new ImageView(context);
        background.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        background.setImageResource(R.drawable.item_video_view);
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        background.setPadding(0, 0, 0, 0);
        addView(background);
        setGravity(Gravity.CENTER);
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
        return isFixed;
    }

    public void setFixed(boolean z) {
        isFixed = z;
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
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left * paddingFactor, top * paddingFactor, right * paddingFactor, paddingFactor * bottom);
    }
}
