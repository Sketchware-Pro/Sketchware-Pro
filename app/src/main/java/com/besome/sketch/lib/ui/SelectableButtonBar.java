package com.besome.sketch.lib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import a.a.a.YA;
import a.a.a.cB;
import a.a.a.wB;

public class SelectableButtonBar extends LinearLayout {
    public ArrayList<Integer> a;
    public ArrayList<String> b;
    public YA c;
    public int d = 0;

    public SelectableButtonBar(Context var1) {
        super(var1);
        this.a(var1);
    }

    public SelectableButtonBar(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public void a() {
        ((LinearLayout) this).removeAllViews();
        int var1 = this.b.size();

        for (int var2 = 0; var2 < var1; ++var2) {
            LinearLayout var3 = this.b((Integer) this.a.get(var2), (String) this.b.get(var2));
            if (var2 == 0) {
                ((View) var3).setBackgroundResource(2131166076);
            } else if (var2 == var1 - 1) {
                ((View) var3).setBackgroundResource(2131166077);
            } else {
                ((View) var3).setBackgroundResource(2131166075);
            }

            ((LinearLayout) this).addView(var3);
        }

        this.setSelectedItemByIndex(this.d);
    }

    public void a(int var1, String var2) {
        this.a.add(var1);
        this.b.add(var2);
    }

    public final void a(Context var1) {
        this.a = new ArrayList();
        this.b = new ArrayList();
    }

    public final LinearLayout b(int var1, String var2) {
        a var3 = new a(this, ((LinearLayout) this).getContext(), var1, var2);
        LinearLayout.LayoutParams var4 = new LinearLayout.LayoutParams(0, -2);
        var4.weight = 1.0F;
        ((LinearLayout) var3).setLayoutParams(var4);
        ((LinearLayout) var3).setOnClickListener(new cB(this));
        return var3;
    }

    public YA getListener() {
        return this.c;
    }

    public int getSelectedItemKey() {
        return ((a) ((LinearLayout) this).getChildAt(this.d)).b;
    }

    public void setListener(YA var1) {
        this.c = var1;
    }

    public void setSelectedItemByIndex(int var1) {
        this.d = var1;

        for (int var2 = 0; var2 < ((LinearLayout) this).getChildCount(); ++var2) {
            a var3 = (a) ((LinearLayout) this).getChildAt(var2);
            var3.setSelected(false);
            if (var2 == var1) {
                var3.setSelected(true);
            }
        }

    }

    public void setSelectedItemByKey(int var1) {
        for (int var2 = 0; var2 < ((LinearLayout) this).getChildCount(); ++var2) {
            a var3 = (a) ((LinearLayout) this).getChildAt(var2);
            var3.setSelected(false);
            if (var3.b == var1) {
                var3.setSelected(true);
                this.d = var2;
                YA var4 = this.c;
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
            this.e = var1;
            this.a(var2, var3, var4, var5);
        }

        public a(SelectableButtonBar var1, Context var2, int var3, String var4) {
            this(var1, var2, 0, var3, var4);
        }

        public final void a(Context var1, int var2, int var3, String var4) {
            this.a = var2;
            this.b = var3;
            this.c = var4;
            ((LinearLayout) this).setOrientation(0);
            ((LinearLayout) this).setGravity(17);
            var2 = (int) wB.a(((LinearLayout) this).getContext(), 4.0F);
            ((LinearLayout) this).setPadding(var2, var2, var2, var2);
            this.d = new TextView(var1);
            LinearLayout.LayoutParams var5 = new LinearLayout.LayoutParams(-2, -2);
            this.d.setLayoutParams(var5);
            this.d.setText(var4);
            this.d.setTextColor(-16740915);
            this.d.setLines(1);
            this.d.setTextSize(2, 12.0F);
            ((LinearLayout) this).addView(this.d);
        }

        public void setSelected(boolean var1) {
            super.setSelected(var1);
            if (var1) {
                this.d.setTextColor(-1);
            } else {
                this.d.setTextColor(-16740915);
            }

        }
    }
}
