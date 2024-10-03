package com.besome.sketch.lib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.YA;
import a.a.a.wB;

public class SelectableButtonBar extends LinearLayout {

    public ArrayList<Integer> a;
    public ArrayList<String> b;
    public YA c;
    public int d;

    public SelectableButtonBar(Context var1) {
        super(var1);
        a(var1);
    }

    public SelectableButtonBar(Context var1, AttributeSet var2) {
        super(var1, var2);
        a(var1);
    }

    public void a() {
        removeAllViews();
        int var1 = b.size();

        for (int var2 = 0; var2 < var1; ++var2) {
            LinearLayout var3 = b(a.get(var2), b.get(var2));
            if (var2 == 0) {
                var3.setBackgroundResource(R.drawable.selector_btnbar_left);
            } else if (var2 == var1 - 1) {
                var3.setBackgroundResource(R.drawable.selector_btnbar_right);
            } else {
                var3.setBackgroundResource(R.drawable.selector_btnbar_center);
            }

            addView(var3);
        }

        setSelectedItemByIndex(d);
    }

    public void a(int var1, String var2) {
        a.add(var1);
        b.add(var2);
    }

    public final void a(Context var1) {
        a = new ArrayList<>();
        b = new ArrayList<>();
    }

    public final LinearLayout b(int var1, String var2) {
        a var3 = new a(this, getContext(), var1, var2);
        LinearLayout.LayoutParams var4 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        var4.weight = 1.0F;
        var3.setLayoutParams(var4);
        var3.setOnClickListener(view -> setSelectedItemByKey(var3.b));
        return var3;
    }

    public YA getListener() {
        return c;
    }

    public int getSelectedItemKey() {
        return ((a) getChildAt(d)).b;
    }

    public void setListener(YA var1) {
        c = var1;
    }

    public void setSelectedItemByIndex(int var1) {
        d = var1;

        for (int var2 = 0; var2 < getChildCount(); ++var2) {
            a var3 = (a) getChildAt(var2);
            var3.setSelected(false);
            if (var2 == var1) {
                var3.setSelected(true);
            }
        }

    }

    public void setSelectedItemByKey(int var1) {
        for (int var2 = 0; var2 < getChildCount(); ++var2) {
            a var3 = (a) getChildAt(var2);
            var3.setSelected(false);
            if (var3.b == var1) {
                var3.setSelected(true);
                d = var2;
                YA var4 = c;
                if (var4 != null) {
                    var4.a(var1);
                }
            }
        }

    }

    public class a extends LinearLayout {
        public int a;
        public int b;
        public String c;
        public TextView d;
        public final SelectableButtonBar e;

        public a(SelectableButtonBar var1, Context var2, int var3, int var4, String var5) {
            super(var2);
            e = var1;
            a(var2, var3, var4, var5);
        }

        public a(SelectableButtonBar var1, Context var2, int var3, String var4) {
            this(var1, var2, 0, var3, var4);
        }

        public final void a(Context var1, int var2, int var3, String var4) {
            a = var2;
            b = var3;
            c = var4;
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(17);
            var2 = (int) wB.a(getContext(), 4.0F);
            setPadding(var2, var2, var2, var2);
            d = new TextView(var1);
            LinearLayout.LayoutParams var5 = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setLayoutParams(var5);
            d.setText(var4);
            d.setTextColor(0xff008dcd);
            d.setLines(1);
            d.setTextSize(2, 12.0F);
            addView(d);
        }

        public void setSelected(boolean var1) {
            super.setSelected(var1);
            if (var1) {
                d.setTextColor(0xffffffff);
            } else {
                d.setTextColor(0xff008dcd);
            }

        }
    }
}
