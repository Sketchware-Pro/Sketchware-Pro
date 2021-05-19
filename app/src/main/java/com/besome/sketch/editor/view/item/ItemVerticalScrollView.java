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

public class ItemVerticalScrollView extends FrameLayout implements sy, ty {
    public final Rect g = new Rect();
    public ViewBean a = null;
    public boolean b = false;
    public boolean c = false;
    public Paint d;
    public float e = -1.0f;
    public boolean f = true;

    public ItemVerticalScrollView(Context context) {
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
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), view.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i2) - (getPaddingTop() + getPaddingBottom())), 0));
    }

    @SuppressLint("WrongConstant")
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(FrameLayout.getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i3) - ((((getPaddingTop() + getPaddingBottom()) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4)), 0));
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
        float y = motionEvent.getY();
        if (action == 0) {
            this.e = y;
        } else if (action == 1) {
            this.e = -1.0f;
        } else if (action == 2) {
            if (this.e < FlexItem.FLEX_GROW_DEFAULT) {
                this.e = y;
            }
            int i2 = (int) (this.e - y);
            this.e = y;
            if (i2 <= 0) {
                if (getScrollY() <= 0) {
                    i2 = 0;
                }
                i = Math.max(0 - getScrollY(), i2);
            } else {
                int bottom = ((childAt.getBottom() - getScrollY()) - getHeight()) + getPaddingRight();
                i = bottom > 0 ? Math.min(bottom, i2) : 0;
            }
            if (i != 0) {
                scrollBy(0, i);
            }
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            int paddingLeft = getPaddingLeft() + getPaddingRight();
            int measuredHeight = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(FrameLayout.getChildMeasureSpec(i, paddingLeft, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            }
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus != null && this != findFocus && a(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.g);
            offsetDescendantRectToMyCoords(findFocus, this.g);
            a(a(this.g));
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        setScrollY(0);
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

    public final boolean a(View view, int i, int i2) {
        view.getDrawingRect(this.g);
        offsetDescendantRectToMyCoords(view, this.g);
        return this.g.bottom + i >= getScrollY() && this.g.top - i <= getScrollY() + i2;
    }

    public final void a(int i) {
        if (i != 0) {
            scrollBy(0, i);
        }
    }

    public int a(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = rect.top - scrollY;
            } else {
                i2 = rect.bottom - i3;
            }
            return Math.min(i2 + 0, getChildAt(0).getBottom() - i3);
        } else if (rect.top >= scrollY || rect.bottom >= i3) {
            return 0;
        } else {
            if (rect.height() > height) {
                i = 0 - (i3 - rect.bottom);
            } else {
                i = 0 - (scrollY - rect.top);
            }
            return Math.max(i, -getScrollY());
        }
    }
}
