package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

public class ItemHorizontalScrollView extends FrameLayout implements sy, ty {

    private final Rect rect = new Rect();
    private ViewBean viewBean;
    private boolean isSelected;
    private boolean isFixed = false;
    private Paint drawPaint;
    private float lastTouchX = -1.0f;
    private boolean isScrollEnabled = false;

    public ItemHorizontalScrollView(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0f));
        setMinimumHeight((int) wB.a(context, 32.0f));
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setStrokeWidth(wB.a(getContext(), 2.0f));
    }

    @Override
    public void addView(View child, int index) {
        int childCount = getChildCount();
        if (index > childCount) {
            addView(child);
            return;
        }
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            if (getChildAt(i2).getVisibility() == View.GONE) {
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

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public boolean getSelection() {
        return isSelected;
    }

    @Override
    public void setSelection(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }

    @Override
    public void measureChild(View view, int parentWidthSpec, int parentHeightSpec) {
        ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(parentWidthSpec) - (getPaddingLeft() + getPaddingRight())), MeasureSpec.UNSPECIFIED), FrameLayout.getChildMeasureSpec(parentHeightSpec, getPaddingTop() + getPaddingBottom(), viewLayoutParams.height));
    }

    @Override
    public void measureChildWithMargins(View view, int parentWidthSpec, int horizontalMarginUsed, int parentHeightSpec, int verticalMarginUsed) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(parentWidthSpec) - ((((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + horizontalMarginUsed)), MeasureSpec.UNSPECIFIED), FrameLayout.getChildMeasureSpec(parentHeightSpec, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + verticalMarginUsed, marginLayoutParams.height));
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        if (!isFixed) {
            float currentScrollX = getScrollX();
            float endScrollX = getScrollX() + getMeasuredWidth();
            float currentScrollY = getScrollY();
            float endScrollY = getScrollY() + getMeasuredHeight();
            if (isSelected) {
                drawPaint.setColor(0x9599d5d0);
                canvas.drawRect(currentScrollX, currentScrollY, endScrollX, endScrollY, drawPaint);
            }
            drawPaint.setColor(0xaad50000);

            canvas.drawLine(currentScrollX, currentScrollY, endScrollX, currentScrollY, drawPaint);
            canvas.drawLine(currentScrollX, currentScrollY, currentScrollX, endScrollY, drawPaint);
            canvas.drawLine(endScrollX, currentScrollY, endScrollX, endScrollY, drawPaint);
            canvas.drawLine(currentScrollX, endScrollY, endScrollX, endScrollY, drawPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent touchEvent) {
        int scrollOffset;
        if (!isScrollEnabled || getChildCount() <= 0) {
            return false;
        }
        View view = getChildAt(0);
        int eventAction = touchEvent.getAction();
        float currentTouchX = touchEvent.getX();
        if (eventAction == 0) {
            lastTouchX = currentTouchX;
        } else if (eventAction == 1) {
            lastTouchX = -1.0f;
        } else if (eventAction == 2) {
            if (lastTouchX < 0.0f) {
                lastTouchX = currentTouchX;
            }
            int i = (int) (lastTouchX - currentTouchX);
            lastTouchX = currentTouchX;
            if (i <= 0) {
                if (getScrollX() <= 0) {
                    i = 0;
                }
                scrollOffset = Math.max(-getScrollX(), i);
            } else {
                int paddingRight = ((view.getRight() - getScrollX()) - getWidth()) + getPaddingRight();
                scrollOffset = paddingRight > 0 ? Math.min(paddingRight, i) : 0;
            }
            if (scrollOffset != 0) {
                scrollBy(scrollOffset, 0);
            }
        }
        return false;
    }

    @Override
    public void onMeasure(int parentWidthSpec, int parentHeightSpec) {
        int horizontalPadding;
        int topPadding;
        int bottomPadding;
        super.onMeasure(parentWidthSpec, parentHeightSpec);
        if (View.MeasureSpec.getMode(parentWidthSpec) != MeasureSpec.UNSPECIFIED && getChildCount() > 0) {
            View firstChild = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) firstChild.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                horizontalPadding = getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin;
                topPadding = getPaddingTop() + getPaddingBottom() + layoutParams.topMargin;
                bottomPadding = layoutParams.bottomMargin;
            } else {
                horizontalPadding = getPaddingLeft() + getPaddingRight();
                topPadding = getPaddingTop();
                bottomPadding = getPaddingBottom();
            }
            int finalIndex = topPadding + bottomPadding;
            int visibleWidth = getMeasuredWidth() - horizontalPadding;
            if (firstChild.getMeasuredWidth() < visibleWidth) {
                firstChild.measure(
                        View.MeasureSpec.makeMeasureSpec(visibleWidth, MeasureSpec.EXACTLY),
                        FrameLayout.getChildMeasureSpec(parentHeightSpec, finalIndex, layoutParams.height)
                );
            }
        }
    }

    @Override
    public void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        View focusedView = findFocus();
        if (focusedView == null || this == focusedView || !a(focusedView, getRight() - getLeft())) {
            return;
        }
        focusedView.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(focusedView, rect);
        scrollHorizontally(calculateScrollOffsetForTargetRect(rect));
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
    public void setChildScrollEnabled(boolean isEnabled) {
        for (int i = 0; i < getChildCount(); i++) {
            KeyEvent.Callback firstChild = getChildAt(i);
            if (firstChild instanceof ty) {
                ((ty) firstChild).setChildScrollEnabled(isEnabled);
            }
            if (firstChild instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) firstChild).setScrollEnabled(isEnabled);
            }
            if (firstChild instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) firstChild).setScrollEnabled(isEnabled);
            }
        }
    }

    @Override
    public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        super.setPadding((int) wB.a(getContext(), (float) paddingLeft), (int) wB.a(getContext(), (float) paddingTop), (int) wB.a(getContext(), (float) paddingRight), (int) wB.a(getContext(), (float) paddingBottom));
    }

    public void setScrollEnabled(boolean isEnabled) {
        isScrollEnabled = isEnabled;
    }

    @Override
    public void a() {
        int i = 0;
        for (int j = 0; j < getChildCount(); j++) {
            KeyEvent.Callback firstChild = getChildAt(j);
            if (firstChild instanceof sy) {
                ((sy) firstChild).getBean().index = i;
                i++;
            }
        }
    }

    public final boolean a(View view, int index) {
        view.getDrawingRect(rect);
        offsetDescendantRectToMyCoords(view, rect);
        return rect.right + index >= getScrollX() && rect.left - index <= getScrollX() + getWidth();
    }

    private void scrollHorizontally(int i) {
        if (i != 0) {
            scrollBy(i, 0);
        }
    }

    private int calculateScrollOffsetForTargetRect(Rect targetRect) {
        if (getChildCount() == 0) {
            return 0;
        }

        int width = getWidth();
        int currentScrollX = getScrollX();
        int rightScrollBound = currentScrollX + width;
        int fadeLength = getHorizontalFadingEdgeLength();

        View firstChild = getChildAt(0);
        if (firstChild == null) {
            return 0;
        }

        if (targetRect.left > 0) {
            currentScrollX += fadeLength;
        }
        if (targetRect.right < firstChild.getWidth()) {
            rightScrollBound -= fadeLength;
        }
        if (targetRect.right > rightScrollBound && targetRect.left > currentScrollX) {
            int scrollAmount = (targetRect.width() > width)
                    ? targetRect.left - currentScrollX
                    : targetRect.right - rightScrollBound;

            return Math.min(scrollAmount, Math.max(0, firstChild.getRight() - rightScrollBound));
        }

        if (targetRect.left < currentScrollX && targetRect.right < rightScrollBound) {
            int scrollAmount = (targetRect.width() > width)
                    ? -(rightScrollBound - targetRect.right)
                    : -(currentScrollX - targetRect.left);

            return Math.max(scrollAmount, -currentScrollX);
        }

        return 0;
    }
}
