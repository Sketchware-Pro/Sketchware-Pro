package com.besome.sketch.editor.view.item;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

import com.besome.sketch.beans.ViewBean;

public class ItemVerticalScrollView extends FrameLayout implements sy, ty {

    public ViewBean a = null;
    public boolean b = false;
    public boolean c = false;
    public Paint d;
    public float e = -1.0F;
    public boolean f = true;
    public final Rect g = new Rect();

    public ItemVerticalScrollView(Context var1) {
        super(var1);
        this.a(var1);
    }

    public int a(Rect var1) {
        int var2 = this.getChildCount();
        byte var3 = 0;
        if (var2 == 0) {
            return 0;
        } else {
            int var4 = this.getHeight();
            int var5 = this.getScrollY();
            var2 = var5 + var4;
            int var6 = this.getVerticalFadingEdgeLength();
            int var7 = var5;
            if (var1.top > 0) {
                var7 = var5 + var6;
            }

            var5 = var2;
            if (var1.bottom < this.getChildAt(0).getHeight()) {
                var5 = var2 - var6;
            }

            if (var1.bottom > var5 && var1.top > var7) {
                if (var1.height() > var4) {
                    var2 = var1.top - var7;
                } else {
                    var2 = var1.bottom - var5;
                }

                var2 = Math.min(var2 + 0, this.getChildAt(0).getBottom() - var5);
            } else {
                var2 = var3;
                if (var1.top < var7) {
                    var2 = var3;
                    if (var1.bottom < var5) {
                        if (var1.height() > var4) {
                            var2 = 0 - (var5 - var1.bottom);
                        } else {
                            var2 = 0 - (var7 - var1.top);
                        }

                        var2 = Math.max(var2, -this.getScrollY());
                    }
                }
            }

            return var2;
        }
    }

    public void a() {
        int var1 = 0;

        int var4;
        for (int var2 = 0; var1 < this.getChildCount(); var2 = var4) {
            View var3 = this.getChildAt(var1);
            var4 = var2;
            if (var3 instanceof sy) {
                ((sy) var3).getBean().index = var2;
                var4 = var2 + 1;
            }

            ++var1;
        }

    }

    public final void a(int var1) {
        if (var1 != 0) {
            this.scrollBy(0, var1);
        }

    }

    public final void a(Context var1) {
        this.setDrawingCacheEnabled(true);
        this.setMinimumWidth((int) wB.a(var1, 32.0F));
        this.setMinimumHeight((int) wB.a(var1, 32.0F));
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(this.getContext(), 2.0F));
    }

    public final boolean a(View var1, int var2, int var3) {
        var1.getDrawingRect(this.g);
        this.offsetDescendantRectToMyCoords(var1, this.g);
        boolean var4;
        if (this.g.bottom + var2 >= this.getScrollY() && this.g.top - var2 <= this.getScrollY() + var3) {
            var4 = true;
        } else {
            var4 = false;
        }

        return var4;
    }

    public void addView(View var1, int var2) {
        int var3 = this.getChildCount();
        if (var2 > var3) {
            super.addView(var1);
        } else {
            byte var4 = -1;
            int var5 = 0;

            int var6;
            while (true) {
                var6 = var4;
                if (var5 >= var3) {
                    break;
                }

                if (this.getChildAt(var5).getVisibility() == 8) {
                    var6 = var5;
                    break;
                }

                ++var5;
            }

            if (var6 >= 0 && var2 >= var6) {
                super.addView(var1, var2 + 1);
            } else {
                super.addView(var1, var2);
            }
        }
    }

    public ViewBean getBean() {
        return this.a;
    }

    public boolean getFixed() {
        return this.c;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void measureChild(View var1, int var2, int var3) {
        ViewGroup.LayoutParams var4 = var1.getLayoutParams();
        var2 = FrameLayout.getChildMeasureSpec(var2, this.getPaddingLeft() + this.getPaddingRight(), var4.width);
        int var5 = this.getPaddingTop();
        int var6 = this.getPaddingBottom();
        var1.measure(var2, MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(var3) - (var5 + var6)), 0));
    }

    public void measureChildWithMargins(View var1, int var2, int var3, int var4, int var5) {
        ViewGroup.MarginLayoutParams var6 = (ViewGroup.MarginLayoutParams) var1.getLayoutParams();
        int var7 = FrameLayout.getChildMeasureSpec(var2, this.getPaddingLeft() + this.getPaddingRight() + var6.leftMargin + var6.rightMargin + var3, var6.width);
        var2 = this.getPaddingTop();
        var3 = this.getPaddingBottom();
        int var8 = var6.topMargin;
        int var9 = var6.bottomMargin;
        var1.measure(var7, MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(var4) - (var2 + var3 + var8 + var9 + var5)), 0));
    }

    public void onDraw(Canvas var1) {
        if (!this.c) {
            int var2 = this.getScrollX();
            int var3 = this.getScrollX() + this.getMeasuredWidth();
            int var4 = this.getScrollY();
            int var5 = this.getScrollY() + this.getMeasuredHeight();
            if (this.b) {
                this.d.setColor(-1785080368);
                var1.drawRect(new Rect(var2, var4, var3, var5), this.d);
            }

            this.d.setColor(-1428881408);
            float var6 = (float) var2;
            float var7 = (float) var4;
            float var8 = (float) var3;
            var1.drawLine(var6, var7, var8, var7, this.d);
            float var9 = (float) var5;
            var1.drawLine(var6, var7, var6, var9, this.d);
            var1.drawLine(var8, var7, var8, var9, this.d);
            var1.drawLine(var6, var9, var8, var9, this.d);
        }

        super.onDraw(var1);
    }

    public boolean onInterceptTouchEvent(MotionEvent var1) {
        if (!this.f) {
            return false;
        } else if (this.getChildCount() <= 0) {
            return false;
        } else {
            View var2 = this.getChildAt(0);
            int var3 = var1.getAction();
            float var4 = var1.getY();
            if (var3 != 0) {
                if (var3 != 1) {
                    if (var3 == 2) {
                        if (this.e < 0.0F) {
                            this.e = var4;
                        }

                        var3 = (int) (this.e - var4);
                        this.e = var4;
                        if (var3 <= 0) {
                            if (this.getScrollY() <= 0) {
                                var3 = 0;
                            }

                            var3 = Math.max(0 - this.getScrollY(), var3);
                        } else {
                            int var5 = var2.getBottom() - this.getScrollY() - this.getHeight() + this.getPaddingRight();
                            if (var5 > 0) {
                                var3 = Math.min(var5, var3);
                            } else {
                                var3 = 0;
                            }
                        }

                        if (var3 != 0) {
                            this.scrollBy(0, var3);
                        }
                    }
                } else {
                    this.e = -1.0F;
                }
            } else {
                this.e = var4;
            }

            return false;
        }
    }

    public void onMeasure(int var1, int var2) {
        super.onMeasure(var1, var2);
        if (MeasureSpec.getMode(var2) != 0) {
            if (this.getChildCount() > 0) {
                View var3 = this.getChildAt(0);
                FrameLayout.LayoutParams var4 = (FrameLayout.LayoutParams) var3.getLayoutParams();
                var2 = this.getPaddingLeft();
                int var5 = this.getPaddingRight();
                int var6 = this.getPaddingTop();
                int var7 = this.getPaddingBottom();
                var7 = this.getMeasuredHeight() - (var6 + var7);
                if (var3.getMeasuredHeight() < var7) {
                    var3.measure(FrameLayout.getChildMeasureSpec(var1, var2 + var5, var4.width), MeasureSpec.makeMeasureSpec(var7, 1073741824));
                }
            }

        }
    }

    public void onSizeChanged(int var1, int var2, int var3, int var4) {
        super.onSizeChanged(var1, var2, var3, var4);
        View var5 = this.findFocus();
        if (var5 != null && this != var5 && this.a(var5, 0, var4)) {
            var5.getDrawingRect(this.g);
            this.offsetDescendantRectToMyCoords(var5, this.g);
            this.a(this.a(this.g));
        }

    }

    public void removeView(View var1) {
        super.removeView(var1);
        this.setScrollY(0);
    }

    public void setBean(ViewBean var1) {
        this.a = var1;
    }

    public void setChildScrollEnabled(boolean var1) {
        for (int var2 = 0; var2 < this.getChildCount(); ++var2) {
            View var3 = this.getChildAt(var2);
            if (var3 instanceof ty) {
                ((ty) var3).setChildScrollEnabled(var1);
            }

            if (var3 instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) var3).setScrollEnabled(var1);
            }

            if (var3 instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) var3).setScrollEnabled(var1);
            }
        }

    }

    public void setFixed(boolean var1) {
        this.c = var1;
    }

    public void setPadding(int var1, int var2, int var3, int var4) {
        super.setPadding((int) wB.a(this.getContext(), (float) var1), (int) wB.a(this.getContext(), (float) var2), (int) wB.a(this.getContext(), (float) var3), (int) wB.a(this.getContext(), (float) var4));
    }

    public void setScrollEnabled(boolean var1) {
        this.f = var1;
    }

    public void setSelection(boolean var1) {
        this.b = var1;
        this.invalidate();
    }
}
