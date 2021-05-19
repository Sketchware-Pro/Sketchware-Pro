package com.besome.sketch.editor.view.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.besome.sketch.beans.ViewBean;
import com.google.android.flexbox.FlexItem;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

public class ItemHorizontalScrollView extends FrameLayout implements sy, ty {
    public final Rect h = new Rect();
    public ViewBean a = null;
    public boolean b = false;
    public boolean c = false;
    public Paint d;
    public float e = -1.0f;
    public boolean f = true;
    public int g = 0;

    public ItemHorizontalScrollView(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0f));
        setMinimumHeight((int) wB.a(context, 32.0f));
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(getContext(), 2.0f));
    }

    @SuppressLint("WrongConstant")
    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        int childCount = getChildCount();
        if (i > childCount) {
            super.addView(view);
            return;
        }
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            } else if (getChildAt(i3).getVisibility() == 8) {
                i2 = i3;
                break;
            } else {
                i3++;
            }
        }
        if (i2 < 0 || i < i2) {
            super.addView(view, i);
        } else {
            super.addView(view, i + 1);
        }
    }

    public ViewBean getBean() {
        return this.a;
    }

    public void setBean(ViewBean viewBean) {
        this.a = viewBean;
    }

    public boolean getFixed() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

    @SuppressLint("WrongConstant")
    public void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - (getPaddingLeft() + getPaddingRight())), 0), FrameLayout.getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams.height));
    }

    @SuppressLint("WrongConstant")
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - ((((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2)), 0), FrameLayout.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    public void onDraw(Canvas canvas) {
        if (!this.c) {
            int scrollX = getScrollX();
            int scrollX2 = getScrollX() + getMeasuredWidth();
            int scrollY = getScrollY();
            int scrollY2 = getScrollY() + getMeasuredHeight();
            if (this.b) {
                this.d.setColor(-1785080368);
                canvas.drawRect(new Rect(scrollX, scrollY, scrollX2, scrollY2), this.d);
            }
            this.d.setColor(-1428881408);
            float f2 = (float) scrollX;
            float f3 = (float) scrollY;
            float f4 = (float) scrollX2;
            canvas.drawLine(f2, f3, f4, f3, this.d);
            float f5 = (float) scrollY2;
            canvas.drawLine(f2, f3, f2, f5, this.d);
            canvas.drawLine(f4, f3, f4, f5, this.d);
            canvas.drawLine(f2, f5, f4, f5, this.d);
        }
        super.onDraw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i;
        if (!this.f || getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        if (action == 0) {
            this.e = x;
        } else if (action == 1) {
            this.e = -1.0f;
        } else if (action == 2) {
            if (this.e < FlexItem.FLEX_GROW_DEFAULT) {
                this.e = x;
            }
            int i2 = (int) (this.e - x);
            this.e = x;
            if (i2 <= 0) {
                if (getScrollX() <= 0) {
                    i2 = 0;
                }
                i = Math.max(0 - getScrollX(), i2);
            } else {
                int right = ((childAt.getRight() - getScrollX()) - getWidth()) + getPaddingRight();
                i = right > 0 ? Math.min(right, i2) : 0;
            }
            if (i != 0) {
                scrollBy(i, 0);
            }
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                i5 = getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin;
                i4 = getPaddingTop() + getPaddingBottom() + layoutParams.topMargin;
                i3 = layoutParams.bottomMargin;
            } else {
                i5 = getPaddingLeft() + getPaddingRight();
                i4 = getPaddingTop();
                i3 = getPaddingBottom();
            }
            int i6 = i4 + i3;
            int measuredWidth = getMeasuredWidth() - i5;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), FrameLayout.getChildMeasureSpec(i2, i6, layoutParams.height));
            }
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && a(findFocus, getRight() - getLeft())) {
            findFocus.getDrawingRect(this.h);
            offsetDescendantRectToMyCoords(findFocus, this.h);
            a(a(this.h));
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        setScrollX(0);
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
    }

    public void setChildScrollEnabled(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            ty childAt = (ty) getChildAt(i);
            if (childAt != null) {
                childAt.setChildScrollEnabled(z);
            }
            if (childAt instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) childAt).setScrollEnabled(z);
            }
            if (childAt instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) childAt).setScrollEnabled(z);
            }
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) wB.a(getContext(), (float) i), (int) wB.a(getContext(), (float) i2), (int) wB.a(getContext(), (float) i3), (int) wB.a(getContext(), (float) i4));
    }

    public void setScrollEnabled(boolean z) {
        this.f = z;
    }

    public void a() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            sy childAt = (sy) getChildAt(i2);
            if (childAt != null) {
                childAt.getBean().index = i;
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
        } else if (rect.left >= scrollX || rect.right >= i3) {
            return 0;
        } else {
            if (rect.width() > width) {
                i = 0 - (i3 - rect.right);
            } else {
                i = 0 - (scrollX - rect.left);
            }
            return Math.max(i, -getScrollX());
        }
    }
}
