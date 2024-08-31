package com.besome.sketch.editor.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

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
        wB.a(var1, this, R.layout.logic_editor_top_menu);
        a = findViewById(R.id.layout_delete);
        b = findViewById(R.id.tv_delete);
        c = findViewById(R.id.layout_copy);
        d = findViewById(R.id.tv_copy);
        e = findViewById(R.id.layout_favorite);
        f = findViewById(R.id.tv_favorite);
        g = findViewById(R.id.layout_detail);
        h = findViewById(R.id.tv_detail);
        m = findViewById(R.id.iv_trash);
        n = findViewById(R.id.iv_copy);
        o = findViewById(R.id.iv_bookmark);
        p = findViewById(R.id.iv_detail);
        b.setText(xB.b().a(getContext(), R.string.common_word_delete));
        d.setText(xB.b().a(getContext(), R.string.common_word_copy));
        f.setText(xB.b().a(getContext(), R.string.common_word_collection));
        h.setText(xB.b().a(getContext(), R.string.common_word_detail));
    }

    public void a(boolean var1) {
        j = var1;
        if (var1) {
            c.setBackgroundColor(getResources().getColor(R.color.scolor_green_normal));
            d.setTextColor(0xffffffff);
            n.setImageResource(R.drawable.copy_48_white);
        } else {
            c.setBackgroundColor(0xfffefefe);
            d.setTextColor(0xff7d7d7d);
            n.setImageResource(R.drawable.copy_48_gray);
        }

    }

    public boolean a() {
        return j;
    }

    public boolean a(float var1, float var2) {
        int var3 = c.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
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
            a.setBackgroundColor(getResources().getColor(R.color.scolor_red_02));
            b.setTextColor(-1);
            m.setImageResource(R.drawable.ic_trashcan_white_48dp);
        } else {
            a.setBackgroundColor(0xfffefefe);
            b.setTextColor(0xff7d7d7d);
            m.setImageResource(R.drawable.icon_delete);
        }

    }

    public boolean b() {
        return i;
    }

    public boolean b(float var1, float var2) {
        int var3 = a.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
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
            g.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            h.setTextColor(0xffffffff);
            p.setImageResource(R.drawable.block_96_white);
        } else {
            g.setBackgroundColor(0xfffefefe);
            h.setTextColor(0xff7d7d7d);
            p.setImageResource(R.drawable.block_flled_grey);
        }

    }

    public boolean c() {
        return l;
    }

    public boolean c(float var1, float var2) {
        int var3 = g.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
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
            e.setBackgroundColor(getResources().getColor(R.color.scolor_blue_01));
            f.setTextColor(0xffffffff);
            o.setImageResource(R.drawable.bookmark_48_white);
        } else {
            e.setBackgroundColor(0xfffefefe);
            f.setTextColor(0xff7d7d7d);
            o.setImageResource(R.drawable.ic_bookmark_red_48dp);
        }

    }

    public boolean d() {
        return k;
    }

    public boolean d(float var1, float var2) {
        int var3 = e.getVisibility();
        boolean var4 = false;
        if (var3 == GONE) {
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
            e.setVisibility(VISIBLE);
            c.setVisibility(VISIBLE);
            g.setVisibility(GONE);
        } else {
            e.setVisibility(GONE);
            c.setVisibility(GONE);
            g.setVisibility(VISIBLE);
        }

    }
}
