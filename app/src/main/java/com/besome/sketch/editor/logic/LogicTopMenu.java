//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.editor.logic;

import a.a.a.wB;
import a.a.a.xB;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogicTopMenu extends LinearLayout {
    public LinearLayout a;
    public TextView b;
    public LinearLayout c;
    public TextView d;
    public LinearLayout e;
    public TextView f;
    public LinearLayout g;
    public TextView h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public ImageView m;
    public ImageView n;
    public ImageView o;
    public ImageView p;

    public LogicTopMenu(Context var1) {
        super(var1);
        this.a(var1);
    }

    public LogicTopMenu(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public final void a(Context var1) {
        wB.a(var1, this, 2131427493);
        this.a = (LinearLayout)((LinearLayout)this).findViewById(2131231337);
        this.b = (TextView)((LinearLayout)this).findViewById(2131231939);
        this.c = (LinearLayout)((LinearLayout)this).findViewById(2131231336);
        this.d = (TextView)((LinearLayout)this).findViewById(2131231927);
        this.e = (LinearLayout)((LinearLayout)this).findViewById(2131231347);
        this.f = (TextView)((LinearLayout)this).findViewById(2131231975);
        this.g = (LinearLayout)((LinearLayout)this).findViewById(2131231339);
        this.h = (TextView)((LinearLayout)this).findViewById(2131231955);
        this.m = (ImageView)((LinearLayout)this).findViewById(2131231233);
        this.n = (ImageView)((LinearLayout)this).findViewById(2131231227);
        this.o = (ImageView)((LinearLayout)this).findViewById(2131231226);
        this.p = (ImageView)((LinearLayout)this).findViewById(2131231229);
        this.b.setText(xB.b().a(((LinearLayout)this).getContext(), 2131624986));
        this.d.setText(xB.b().a(((LinearLayout)this).getContext(), 2131624982));
        this.f.setText(xB.b().a(((LinearLayout)this).getContext(), 2131624978));
        this.h.setText(xB.b().a(((LinearLayout)this).getContext(), 2131624989));
    }

    public void a(boolean var1) {
        this.j = var1;
        if (var1) {
            this.c.setBackgroundColor(((LinearLayout)this).getResources().getColor(2131034288));
            this.d.setTextColor(-1);
            this.n.setImageResource(2131165507);
        } else {
            this.c.setBackgroundColor(-65794);
            this.d.setTextColor(-8553091);
            this.n.setImageResource(2131165506);
        }

    }

    public boolean a() {
        return this.j;
    }

    public boolean a(float var1, float var2) {
        int var3 = this.c.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            this.c.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float)var5[0]) {
                var6 = var4;
                if (var1 < (float)(var5[0] + this.c.getWidth())) {
                    var6 = var4;
                    if (var2 > (float)var5[1]) {
                        var6 = var4;
                        if (var2 < (float)(var5[1] + this.c.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void b(boolean var1) {
        this.i = var1;
        if (var1) {
            this.a.setBackgroundColor(((LinearLayout)this).getResources().getColor(2131034294));
            this.b.setTextColor(-1);
            this.m.setImageResource(2131165875);
        } else {
            this.a.setBackgroundColor(-65794);
            this.b.setTextColor(-8553091);
            this.m.setImageResource(2131165896);
        }

    }

    public boolean b() {
        return this.i;
    }

    public boolean b(float var1, float var2) {
        int var3 = this.a.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            this.a.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float)var5[0]) {
                var6 = var4;
                if (var1 < (float)(var5[0] + this.a.getWidth())) {
                    var6 = var4;
                    if (var2 > (float)var5[1]) {
                        var6 = var4;
                        if (var2 < (float)(var5[1] + this.a.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void c(boolean var1) {
        this.l = var1;
        if (var1) {
            this.g.setBackgroundColor(((LinearLayout)this).getResources().getColor(2131034284));
            this.h.setTextColor(-1);
            this.p.setImageResource(2131165375);
        } else {
            this.g.setBackgroundColor(-65794);
            this.h.setTextColor(-8553091);
            this.p.setImageResource(2131165376);
        }

    }

    public boolean c() {
        return this.l;
    }

    public boolean c(float var1, float var2) {
        int var3 = this.g.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            this.g.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float)var5[0]) {
                var6 = var4;
                if (var1 < (float)(var5[0] + this.g.getWidth())) {
                    var6 = var4;
                    if (var2 > (float)var5[1]) {
                        var6 = var4;
                        if (var2 < (float)(var5[1] + this.g.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void d(boolean var1) {
        this.k = var1;
        if (var1) {
            this.e.setBackgroundColor(((LinearLayout)this).getResources().getColor(2131034284));
            this.f.setTextColor(-1);
            this.o.setImageResource(2131165379);
        } else {
            this.e.setBackgroundColor(-65794);
            this.f.setTextColor(-8553091);
            this.o.setImageResource(2131165700);
        }

    }

    public boolean d() {
        return this.k;
    }

    public boolean d(float var1, float var2) {
        int var3 = this.e.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            this.e.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float)var5[0]) {
                var6 = var4;
                if (var1 < (float)(var5[0] + this.e.getWidth())) {
                    var6 = var4;
                    if (var2 > (float)var5[1]) {
                        var6 = var4;
                        if (var2 < (float)(var5[1] + this.e.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void e(boolean var1) {
        if (var1) {
            this.e.setVisibility(0);
            this.c.setVisibility(0);
            this.g.setVisibility(8);
        } else {
            this.e.setVisibility(8);
            this.c.setVisibility(8);
            this.g.setVisibility(0);
        }

    }
}
