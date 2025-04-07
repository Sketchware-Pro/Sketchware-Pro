package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

public class ItemVerticalScrollView extends FrameLayout implements sy, ty {

    private final Rect g = new Rect();
    private final Rect rect = new Rect();
    private ViewBean viewBean;
    private boolean isSelected = false;
    private boolean isFixed = false;
    private Paint paint;
    private float e = -1.0F;
    private boolean f = true;

    public ItemVerticalScrollView(Context context) {
        super(context);
        initialize(context);
    }

    private int a(Rect rect) {
        int var2 = getChildCount();
        byte var3 = 0;
        if (var2 == 0) {
            return 0;
        } else {
            int var4 = getHeight();
            int var5 = getScrollY();
            var2 = var5 + var4;
            int var6 = getVerticalFadingEdgeLength();
            int var7 = var5;
            if (rect.top > 0) {
                var7 = var5 + var6;
            }

            var5 = var2;
            if (rect.bottom < getChildAt(0).getHeight()) {
                var5 = var2 - var6;
            }

            if (rect.bottom > var5 && rect.top > var7) {
                if (rect.height() > var4) {
                    var2 = rect.top - var7;
                } else {
                    var2 = rect.bottom - var5;
                }

                var2 = Math.min(var2, getChildAt(0).getBottom() - var5);
            } else {
                var2 = var3;
                if (rect.top < var7) {
                    if (rect.bottom < var5) {
                        if (rect.height() > var4) {
                            var2 = -(var5 - rect.bottom);
                        } else {
                            var2 = -(var7 - rect.top);
                        }

                        var2 = Math.max(var2, -getScrollY());
                    }
                }
            }

            return var2;
        }
    }

    public void a() {
        int var1 = 0;

        int var4;
        for (int var2 = 0; var1 < getChildCount(); var2 = var4) {
            View var3 = getChildAt(var1);
            var4 = var2;
            if (var3 instanceof sy) {
                ((sy) var3).getBean().index = var2;
                var4 = var2 + 1;
            }

            ++var1;
        }

    }

    private void a(int position) {
        if (position != 0) {
            scrollBy(0, position);
        }
    }

    private void initialize(Context var1) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(var1, 32.0F));
        setMinimumHeight((int) wB.a(var1, 32.0F));
        paint = new Paint(1);
        paint.setStrokeWidth(wB.a(getContext(), 2.0F));
    }

    private boolean a(View view, int var2, int var3) {
        view.getDrawingRect(g);
        offsetDescendantRectToMyCoords(view, g);
        return g.bottom + var2 >= getScrollY() && g.top - var2 <= getScrollY() + var3;
    }

    public void addView(View view, int index) {
        int childCount = getChildCount();
        if (index > childCount) {
            super.addView(view);
        } else {
            byte var4 = -1;
            int var5 = 0;

            int var6;
            while (true) {
                var6 = var4;
                if (var5 >= childCount) {
                    break;
                }

                if (getChildAt(var5).getVisibility() == View.GONE) {
                    var6 = var5;
                    break;
                }

                ++var5;
            }

            if (var6 >= 0 && index >= var6) {
                super.addView(view, index + 1);
            } else {
                super.addView(view, index);
            }
        }
    }

    public ViewBean getBean() {
        return viewBean;
    }

    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    public boolean getFixed() {
        return isFixed;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public boolean getSelection() {
        return isSelected;
    }

    public void setSelection(boolean hasSelection) {
        isSelected = hasSelection;
        invalidate();
    }

    public void measureChild(View view, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        parentWidthMeasureSpec = FrameLayout.getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
        view.measure(parentWidthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - (getPaddingTop() + getPaddingBottom())), MeasureSpec.UNSPECIFIED));
    }

    public void measureChildWithMargins(View view, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = FrameLayout.getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + widthUsed, layoutParams.width);
        parentWidthMeasureSpec = getPaddingTop();
        widthUsed = getPaddingBottom();
        view.measure(childMeasureSpec, MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - (parentWidthMeasureSpec + widthUsed + layoutParams.topMargin + layoutParams.bottomMargin + heightUsed)), MeasureSpec.UNSPECIFIED));
    }

    public void onDraw(Canvas canvas) {
        if (!isFixed) {
            int scrollX = getScrollX();
            int measuredWidthX = getScrollX() + getMeasuredWidth();
            int scrollY = getScrollY();
            int measuredHeightY = getScrollY() + getMeasuredHeight();
            if (isSelected) {
                paint.setColor(0x9599d5d0);
                rect.set(scrollX, scrollY, measuredWidthX, measuredHeightY);
                canvas.drawRect(rect, paint);
            }
            paint.setColor(0xaad50000);
            canvas.drawLine((float) scrollX, (float) scrollY, (float) measuredWidthX, (float) scrollY, paint);
            canvas.drawLine((float) scrollX, (float) scrollY, (float) scrollX, (float) measuredHeightY, paint);
            canvas.drawLine((float) measuredWidthX, (float) scrollY, (float) measuredWidthX, (float) measuredHeightY, paint);
            canvas.drawLine((float) scrollX, (float) measuredHeightY, (float) measuredWidthX, (float) measuredHeightY, paint);
        }
        super.onDraw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!f) {
            return false;
        } else if (getChildCount() <= 0) {
            return false;
        } else {
            View var2 = getChildAt(0);
            int var3 = motionEvent.getAction();
            float motionEventY = motionEvent.getY();
            if (var3 != 0) {
                if (var3 != 1) {
                    if (var3 == 2) {
                        if (e < 0.0F) {
                            e = motionEventY;
                        }

                        var3 = (int) (e - motionEventY);
                        e = motionEventY;
                        if (var3 <= 0) {
                            if (getScrollY() <= 0) {
                                var3 = 0;
                            }

                            var3 = Math.max(-getScrollY(), var3);
                        } else {
                            int var5 = var2.getBottom() - getScrollY() - getHeight() + getPaddingRight();
                            if (var5 > 0) {
                                var3 = Math.min(var5, var3);
                            } else {
                                var3 = 0;
                            }
                        }

                        if (var3 != 0) {
                            scrollBy(0, var3);
                        }
                    }
                } else {
                    e = -1.0F;
                }
            } else {
                e = motionEventY;
            }

            return false;
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED) {
            if (getChildCount() > 0) {
                View child = getChildAt(0);
                ViewGroup.LayoutParams var4 = child.getLayoutParams();
                heightMeasureSpec = getPaddingLeft();
                int measuringSize = getMeasuredHeight() - (getPaddingTop() + getPaddingBottom());
                if (child.getMeasuredHeight() < measuringSize) {
                    child.measure(FrameLayout.getChildMeasureSpec(widthMeasureSpec, heightMeasureSpec + getPaddingRight(), var4.width), MeasureSpec.makeMeasureSpec(measuringSize, MeasureSpec.EXACTLY));
                }
            }
        }
    }

    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        View focusedView = findFocus();
        if (focusedView != null && this != focusedView && a(focusedView, 0, oldHeight)) {
            focusedView.getDrawingRect(g);
            offsetDescendantRectToMyCoords(focusedView, g);
            a(a(g));
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        setScrollY(0);
    }

    public void setChildScrollEnabled(boolean childScrollEnabled) {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child instanceof ty) {
                ((ty) child).setChildScrollEnabled(childScrollEnabled);
            }

            if (child instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) child).setScrollEnabled(childScrollEnabled);
            }

            if (child instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) child).setScrollEnabled(childScrollEnabled);
            }
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) wB.a(getContext(), (float) left), (int) wB.a(getContext(), (float) top), (int) wB.a(getContext(), (float) right), (int) wB.a(getContext(), (float) bottom));
    }

    public void setScrollEnabled(boolean isScrollEnabled) {
        f = isScrollEnabled;
    }
}
