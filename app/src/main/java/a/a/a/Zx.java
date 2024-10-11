package a.a.a;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ColorBean;
import com.besome.sketch.editor.view.ColorGroupItem;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Zx extends PopupWindow {

    public b a;
    public ArrayList<ColorBean> b = new ArrayList();
    public ArrayList<ColorBean[]> c = new ArrayList();
    public LinearLayout d;
    public XB e;
    public EditText f;
    public TextView g;
    public TextView h;
    public HorizontalScrollView i;
    public RecyclerView j;
    public int k;
    public int l;
    public int m = -1;
    public DB n;
    public View o;
    public Activity p;

    public Zx(View var1, Activity var2, int var3, boolean var4, boolean var5) {
        super(var2);
        a(var1, var2, var3, var4, var5);
    }

    public final void a() {
        aB var1 = new aB(p);
        var1.a(2131165524);
        var1.b(xB.b().a(p, 2131625755));
        var1.a(xB.b().a(p, 2131625753));
        var1.b(xB.b().a(p, 2131624986), new Tx(this, var1));
        var1.a(xB.b().a(p, 2131624974), new Ux(this, var1));
        var1.show();
    }

    public void a(b var1) {
        a = var1;
    }

    public void a(View var1, Activity var2, int var3, boolean var4, boolean var5) {
        p = var2;
        o = var1;
        n = new DB(var2, "P24");
        a(var4, var5);

        for (int var6 = 0; var6 < c.size(); ++var6) {
            ColorBean[] var7 = c.get(var6);

            for (int var8 = 0; var8 < var7.length; ++var8) {
                if (var7[var8].colorCode == var3) {
                    k = var6;
                    l = var6;
                    m = var8;
                    break;
                }
            }
        }

        super.setFocusable(true);
        super.setOutsideTouchable(true);
        super.setContentView(var1);
        int[] var11 = GB.c(var2);
        super.setWidth(var11[0]);
        super.setHeight(var11[1]);
        i = var1.findViewById(2131231351);
        d = var1.findViewById(2131231327);
        j = var1.findViewById(2131230905);
        j.setHasFixedSize(true);
        LinearLayoutManager var12 = new LinearLayoutManager(var2.getApplicationContext());
        j.setLayoutManager(var12);
        j.setAdapter(new ColorsAdapter(this));
        j.setItemAnimator(new DefaultItemAnimator());
        f = var1.findViewById(2131231026);
        ((TextInputLayout) var1.findViewById(2131231807)).setHint(xB.b().a(var2, 2131625752));
        g = var1.findViewById(2131231932);
        e = new XB(var2, var1.findViewById(2131231807), g);
        f.setPrivateImeOptions("defaultInputmode=english;");
        h = var1.findViewById(2131231864);
        h.setText(xB.b().a(var2, 2131624970).toUpperCase());
        h.setOnClickListener(new Px(this));
        j.getAdapter().notifyItemChanged(m);
        d.removeAllViews();

        for (var3 = 0; var3 < b.size(); ++var3) {
            ColorGroupItem var13 = new ColorGroupItem(var2);
            ColorBean var9 = b.get(var3);
            var13.b.setOnClickListener(new Qx(this, var3, var2));
            var13.b.setText(var9.colorName);
            var13.b.setTextColor(var9.displayNameColor);
            var13.b.setBackgroundColor(var9.colorCode);
            d.addView(var13);
            if (var3 == k) {
                var13.c.setImageResource(var9.icon);
                var13.c.setVisibility(0);
            } else {
                var13.c.setVisibility(8);
            }

            var13.b.setOnLongClickListener(new Rx(this, var3));
        }

        Animation var10 = var1.getAnimation();
        if (var10 != null) {
            var10.setAnimationListener(new Sx(this));
        }

    }

    public final void a(String var1) {
        aB var2 = new aB(p);
        var2.a(2131165524);
        var2.b(xB.b().a(p, 2131625756));
        var2.a(xB.b().a(p, 2131625754));
        var2.b(xB.b().a(p, 2131624986), new Vx(this, var1, var2));
        var2.a(xB.b().a(p, 2131624974), new Wx(this, var2));
        var2.show();
    }

    public void a(boolean var1, boolean var2) {
        b.add(new ColorBean("#FFF6F6F6", "CUSTOM", "#212121", 2131165412));
        b.add(sq.p[0]);
        b.add(sq.q[0]);
        b.add(sq.r[0]);
        b.add(sq.s[0]);
        b.add(sq.t[0]);
        b.add(sq.u[0]);
        b.add(sq.v[0]);
        b.add(sq.w[0]);
        b.add(sq.x[0]);
        b.add(sq.y[0]);
        b.add(sq.z[0]);
        b.add(sq.A[0]);
        b.add(sq.B[0]);
        b.add(sq.C[0]);
        b.add(sq.D[0]);
        b.add(sq.E[0]);
        b.add(sq.F[0]);
        b.add(sq.G[0]);
        b.add(sq.H[0]);
        b.add(sq.I[0]);
        b.add(sq.J[0]);
        c.add(b());
        c.add(sq.p);
        c.add(sq.q);
        c.add(sq.r);
        c.add(sq.s);
        c.add(sq.t);
        c.add(sq.u);
        c.add(sq.v);
        c.add(sq.w);
        c.add(sq.x);
        c.add(sq.y);
        c.add(sq.z);
        c.add(sq.A);
        c.add(sq.B);
        c.add(sq.C);
        c.add(sq.D);
        c.add(sq.E);
        c.add(sq.F);
        c.add(sq.G);
        c.add(sq.H);
        c.add(sq.I);
        c.add(sq.J);
        if (var1) {
            b.add(sq.K[0]);
            c.add(sq.K);
        }

        if (var2) {
            b.add(sq.L[0]);
            c.add(sq.L);
        }

    }

    public final void b(String var1) {
        String var2 = n.f("P24I1");
        if (var2.contains(var1)) {
            String var3 = var1 +
                    ",";
            var1 = var2.replaceAll(var3, "");
            n.a("P24I1", var1);
            c.set(0, b());
            d();
        }

    }

    public final ColorBean[] b() {
        String var1 = n.f("P24I1");
        ColorBean[] var4;
        if (!var1.isEmpty()) {
            String[] var2 = var1.split(",");
            ColorBean[] var12 = new ColorBean[var2.length];
            int var3 = 0;

            while (true) {
                var4 = var12;
                if (var3 >= var2.length) {
                    break;
                }

                label52:
                {
                    label61:
                    {
                        int var5;
                        int var6;
                        int var7;
                        int var8;
                        boolean var10001;
                        try {
                            var5 = Color.parseColor(var2[var3]);
                            var6 = Color.red(var5);
                            var7 = Color.green(var5);
                            var8 = Color.blue(var5);
                        } catch (Exception var11) {
                            var10001 = false;
                            break label61;
                        }

                        byte var14;
                        if (var6 > 240) {
                            var14 = 1;
                        } else {
                            var14 = 0;
                        }

                        var6 = var14;
                        if (var7 > 240) {
                            var6 = var14 + 1;
                        }

                        var5 = var6;
                        if (var8 > 240) {
                            var5 = var6 + 1;
                        }

                        if (var5 >= 2) {
                            label44:
                            {
                                ColorBean var13;
                                try {
                                    var13 = new ColorBean(var2[var3], "CUSTOM", "#212121", 2131165412);
                                } catch (Exception var9) {
                                    var10001 = false;
                                    break label44;
                                }

                                var12[var3] = var13;
                                break label52;
                            }
                        } else {
                            try {
                                var12[var3] = new ColorBean(var2[var3], "CUSTOM", "#ffffff", 2131165414);
                                break label52;
                            } catch (Exception var10) {
                                var10001 = false;
                            }
                        }
                    }

                    n.a();
                    var12 = new ColorBean[0];
                }

                ++var3;
            }
        } else {
            var4 = new ColorBean[0];
        }

        return var4;
    }

    public final void c() {
        if (k < d.getChildCount()) {
            View var1 = d.getChildAt(k);
            i.smoothScrollTo((int) var1.getX(), 0);
            j.scrollToPosition(m);
        }
    }

    public final void c(String var1) {
        String var2 = n.f("P24I1");
        if (var2.contains(var1)) {
            bB.b(p, xB.b().a(p, 2131625750), 0).show();
        } else {
            String var3 = var1 +
                    "," +
                    var2;
            var1 = var3;
            n.a("P24I1", var1);
            c.set(0, b());
            d();
            k = 0;
            c();
        }
    }

    public final void d() {
        l = 0;
        k = 0;
        m = 0;
        j.getAdapter().notifyDataSetChanged();
    }

    private class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {
        private final Zx c;

        public ColorsAdapter(Zx var1) {
            c = var1;
        }

        public int getItemCount() {
            return c.c.get(c.l).length;
        }

        public void onBindViewHolder(ColorViewHolder var1, int var2) {
            TextView var3 = var1.u;
            ColorBean var4 = ((ColorBean[]) c.c.get(c.l))[var2];
            boolean var5;
            var5 = c.l == 0;

            var3.setText(var4.getColorCode(var5));
            if (var2 == 0) {
                var1.v.setText(((ColorBean[]) c.c.get(c.l))[0].colorName);
            } else {
                var1.v.setText("");
            }

            var1.u.setTextColor(((ColorBean[]) c.c.get(c.l))[var2].displayNameColor);
            var1.v.setTextColor(((ColorBean[]) c.c.get(c.l))[var2].displayNameColor);
            var1.t.setBackgroundColor(((ColorBean[]) c.c.get(c.l))[var2].colorCode);
            if (var2 == c.m && c.l == c.k) {
                var1.w.setImageResource(((ColorBean[]) c.c.get(c.l))[var2].icon);
                var1.w.setVisibility(0);
            } else {
                var1.w.setVisibility(8);
            }

        }

        public ColorViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
            return new ColorViewHolder(this, LayoutInflater.from(var1.getContext()).inflate(2131427375, var1, false));
        }

        private class ColorViewHolder extends RecyclerView.ViewHolder {
            public View t;
            public TextView u;
            public TextView v;
            public ImageView w;
            public final ColorsAdapter x;

            public ColorViewHolder(ColorsAdapter var1, View var2) {
                super(var2);
                x = var1;
                t = var2.findViewById(2131231326);
                u = var2.findViewById(2131231915);
                v = var2.findViewById(2131231916);
                w = var2.findViewById(2131231182);
                var2.setOnClickListener(new Xx(this, var1));
                var2.setOnLongClickListener(new Yx(this, var1));
            }
        }
    }

    public interface b {
        void a(int var1);
    }
}
