package com.besome.sketch.editor.view.item;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.besome.sketch.beans.ViewBean;

public class ItemHorizontalScrollView extends FrameLayout implements sy, ty {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;
    public float e;

    public boolean scrollEnabled;
    public int g;
    public final Rect h;

    public ItemHorizontalScrollView(Context context) {
        super(context);
        this.viewBean = null;
        this.selected = false;
        this.fixed = false;
        this.e = -1.0f;
        this.scrollEnabled = true;
        this.g = 0;
        this.h = new Rect();
        a(context);
    }

    public final void a(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0f));
        setMinimumHeight((int) wB.a(context, 32.0f));
        this.paint = new Paint(1);
        this.paint.setStrokeWidth(wB.a(getContext(), 2.0f));
    }

    @Override
    public void addView(View child, int index) {
        int childCount = getChildCount();
        if (index > childCount) {
            super.addView(child);
            return;
        }
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            if (getChildAt(i2).getVisibility() == 8) {
                i = i2;
                break;
            }
            i2++;
        }
        if (i >= 0 && index >= i) {
            super.addView(child, index + 1);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public ViewBean getBean() {
        return this.viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.fixed;
    }

    public boolean getSelection() {
        return this.selected;
    }

    @Override
    public void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        child.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(parentWidthMeasureSpec) - (getPaddingLeft() + getPaddingRight())), 0), FrameLayout.getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height));
    }

    @Override
    public void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        child.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(parentWidthMeasureSpec) - ((((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + widthUsed)), 0), FrameLayout.getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + heightUsed, marginLayoutParams.height));
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!this.fixed) {
            int scrollX = getScrollX();
            int scrollX2 = getScrollX() + getMeasuredWidth();
            int scrollY = getScrollY();
            int scrollY2 = getScrollY() + getMeasuredHeight();
            if (this.selected) {
                this.paint.setColor(-1785080368);
                canvas.drawRect(new Rect(scrollX, scrollY, scrollX2, scrollY2), this.paint);
            }
            this.paint.setColor(-1428881408);
            float scrollX3 = scrollX;
            float f = scrollY;
            float f2 = scrollX2;
            canvas.drawLine(scrollX3, f, f2, f, this.paint);
            float f3 = scrollY2;
            canvas.drawLine(scrollX3, f, scrollX3, f3, this.paint);
            canvas.drawLine(f2, f, f2, f3, this.paint);
            canvas.drawLine(scrollX3, f3, f2, f3, this.paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int min;
        if (!this.scrollEnabled || getChildCount() <= 0) {
            return false;
        }
        View child = getChildAt(0);
        int action = ev.getAction();
        float x = ev.getX();
        if (action == 0) {
            this.e = x;
        } else if (action == 1) {
            this.e = -1.0f;
        } else if (action == 2) {
            if (this.e < 0.0f) {
                this.e = x;
            }
            int i = (int) (this.e - x);
            this.e = x;
            if (i <= 0) {
                if (getScrollX() <= 0) {
                    i = 0;
                }
                min = Math.max(0 - getScrollX(), i);
            } else {
                int right = ((child.getRight() - getScrollX()) - getWidth()) + getPaddingRight();
                min = right > 0 ? Math.min(right, i) : 0;
            }
            if (min != 0) {
                scrollBy(min, 0);
            }
        }
        return false;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft;
        int paddingTop;
        int paddingBottom;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                paddingLeft = getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin;
                paddingTop = getPaddingTop() + getPaddingBottom() + layoutParams.topMargin;
                paddingBottom = layoutParams.bottomMargin;
            } else {
                paddingLeft = getPaddingLeft() + getPaddingRight();
                paddingTop = getPaddingTop();
                paddingBottom = getPaddingBottom();
            }
            int i = paddingTop + paddingBottom;
            int measuredWidth = getMeasuredWidth() - paddingLeft;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), FrameLayout.getChildMeasureSpec(heightMeasureSpec, i, layoutParams.height));
            }
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View findFocus = findFocus();
        if (findFocus == null || this == findFocus || !a(findFocus, getRight() - getLeft())) {
            return;
        }
        findFocus.getDrawingRect(this.h);
        offsetDescendantRectToMyCoords(findFocus, this.h);
        a(a(this.h));
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        setScrollX(0);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public void setChildScrollEnabled(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            KeyEvent.Callback childAt = getChildAt(i);
            if (childAt instanceof ty) {
                ((ty) childAt).setChildScrollEnabled(z);
            }
            if (childAt instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) childAt).setScrollEnabled(z);
            }
            if (childAt instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) childAt).setScrollEnabled(z);
            }
        }
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) wB.a(getContext(), left), (int) wB.a(getContext(), top), (int) wB.a(getContext(), right), (int) wB.a(getContext(), bottom));
    }

    public void setScrollEnabled(boolean enabled) {
        this.scrollEnabled = enabled;
    }

    @Override
    public void setSelection(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    @Override
    public void a() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            KeyEvent.Callback childAt = getChildAt(i2);
            if (childAt instanceof sy) {
                ((sy) childAt).getBean().index = i;
                i++;
            }
        }
    }

    public final boolean a(View view, int i) {
        view.getDrawingRect(this.h);
        offsetDescendantRectToMyCoords(view, this.h);
        return this.h.right + i >= getScrollX() && this.h.left - i <= getScrollX() + getWidth();
    }

    public final void a(int i) {
        if (i != 0) {
            scrollBy(i, 0);
        }
    }

    public int a(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int width = getWidth();
        int scrollX = getScrollX();
        int i3 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength;
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i3 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i3 && rect.left > scrollX) {
            if (rect.width() > width) {
                i2 = rect.left - scrollX;
            } else {
                i2 = rect.right - i3;
            }
            return Math.min(i2 + 0, getChildAt(0).getRight() - i3);
        }
        if (rect.left >= scrollX || rect.right >= i3) {
            return 0;
        }
        if (rect.width() > width) {
            i = 0 - (i3 - rect.right);
        } else {
            i = 0 - (scrollX - rect.left);
        }
        return Math.max(i, -getScrollX());
    }
}
