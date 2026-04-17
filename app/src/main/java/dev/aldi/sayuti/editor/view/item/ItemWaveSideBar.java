package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;

import a.a.a.wB;

public class ItemWaveSideBar extends AppCompatTextView implements ItemView {

    private final Paint paint;
    private final Rect rect;
    private final float paddingFactor;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean hasFixed;

    public ItemWaveSideBar(Context context) {
        super(context);
        paddingFactor = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        rect = new Rect();

        setDrawingCacheEnabled(true);
        setTypeface(null, Typeface.BOLD);
        setText("A\nB\nC\nD\nE\nF\nG\nH\nI\nJ\nK\nL\nM\nN\nO\nP\nQ\nR\nS\nT\nU\nV\nW\nX\nY\nZ");
        setTextSize(18.0f);
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
        return hasFixed;
    }

    @Override
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
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * paddingFactor), (int) (top * paddingFactor), (int) (right * paddingFactor), (int) (bottom * paddingFactor));
    }

    public void setSidebarTextAlignment(int alignment) {
        int gravity = Gravity.CENTER_VERTICAL;
        switch (alignment) {
            case 1 -> gravity |= Gravity.LEFT;
            case 2 -> gravity |= Gravity.RIGHT;
            default -> gravity |= Gravity.CENTER_HORIZONTAL;
        }
        setGravity(gravity);
    }

    public void setSidebarPosition(int position) {
        // 0 = right, 1 = left
        // Standard TextView doesn't have a 'position' attribute, so we simulate it with translation for preview
        setTranslationX((position == 1 ? -1 : 1) * paddingFactor * 8);
    }

    public void setSidebarMaxOffset(float offset) {
        // Map max_offset to horizontal padding for preview
        int padding = (int) (offset / 2);
        setPadding(padding, getPaddingTop(), padding, getPaddingBottom());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null || text.length() == 0 || text.toString().equals("none")) {
            return;
        }
        super.setText(text, type);
    }
}
