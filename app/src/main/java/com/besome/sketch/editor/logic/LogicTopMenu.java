package com.besome.sketch.editor.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;
import a.a.a.xB;

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
        a(var1);
    }

    public LogicTopMenu(Context var1, AttributeSet var2) {
        super(var1, var2);
        a(var1);
    }

    public final void a(Context var1) {
        wB.a(var1, this, 2131427493);
        a = findViewById(2131231337);
        b = findViewById(2131231939);
        c = findViewById(2131231336);
        d = findViewById(2131231927);
        e = findViewById(2131231347);
        f = findViewById(2131231975);
        g = findViewById(2131231339);
        h = findViewById(2131231955);
        m = findViewById(2131231233);
        n = findViewById(2131231227);
        o = findViewById(2131231226);
        p = findViewById(2131231229);
        b.setText(xB.b().a(getContext(), 2131624986));
        d.setText(xB.b().a(getContext(), 2131624982));
        f.setText(xB.b().a(getContext(), 2131624978));
        h.setText(xB.b().a(getContext(), 2131624989));
    }

    public void a(boolean var1) {
        j = var1;
        if (var1) {
            c.setBackgroundColor(getResources().getColor(2131034288));
            d.setTextColor(-1);
            n.setImageResource(2131165507);
        } else {
            c.setBackgroundColor(-65794);
            d.setTextColor(-8553091);
            n.setImageResource(2131165506);
        }

    }

    public boolean a() {
        return j;
    }

    public boolean a(float var1, float var2) {
        int var3 = c.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            c.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + c.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + c.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void b(boolean var1) {
        i = var1;
        if (var1) {
            a.setBackgroundColor(getResources().getColor(2131034294));
            b.setTextColor(-1);
            m.setImageResource(2131165875);
        } else {
            a.setBackgroundColor(-65794);
            b.setTextColor(-8553091);
            m.setImageResource(2131165896);
        }

    }

    public boolean b() {
        return i;
    }

    public boolean b(float var1, float var2) {
        int var3 = a.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            a.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + a.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + a.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void c(boolean var1) {
        l = var1;
        if (var1) {
            g.setBackgroundColor(getResources().getColor(2131034284));
            h.setTextColor(-1);
            p.setImageResource(2131165375);
        } else {
            g.setBackgroundColor(-65794);
            h.setTextColor(-8553091);
            p.setImageResource(2131165376);
        }

    }

    public boolean c() {
        return l;
    }

    public boolean c(float var1, float var2) {
        int var3 = g.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            g.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + g.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + g.getHeight())) {
                            var6 = true;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public void d(boolean var1) {
        k = var1;
        if (var1) {
            e.setBackgroundColor(getResources().getColor(2131034284));
            f.setTextColor(-1);
            o.setImageResource(2131165379);
        } else {
            e.setBackgroundColor(-65794);
            f.setTextColor(-8553091);
            o.setImageResource(2131165700);
        }

    }

    public boolean d() {
        return k;
    }

    public boolean d(float var1, float var2) {
        int var3 = e.getVisibility();
        boolean var4 = false;
        if (var3 == 8) {
            return false;
        } else {
            int[] var5 = new int[2];
            e.getLocationOnScreen(var5);
            boolean var6 = var4;
            if (var1 > (float) var5[0]) {
                var6 = var4;
                if (var1 < (float) (var5[0] + e.getWidth())) {
                    var6 = var4;
                    if (var2 > (float) var5[1]) {
                        var6 = var4;
                        if (var2 < (float) (var5[1] + e.getHeight())) {
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
            e.setVisibility(0);
            c.setVisibility(0);
            g.setVisibility(8);
        } else {
            e.setVisibility(8);
            c.setVisibility(8);
            g.setVisibility(0);
        }

    }
}
