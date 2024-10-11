//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
        this.a(var1, var2, var3, var4, var5);
    }

    public final void a() {
        aB var1 = new aB(this.p);
        var1.a(2131165524);
        var1.b(xB.b().a(this.p, 2131625755));
        var1.a(xB.b().a(this.p, 2131625753));
        var1.b(xB.b().a(this.p, 2131624986), new Tx(this, var1));
        var1.a(xB.b().a(this.p, 2131624974), new Ux(this, var1));
        var1.show();
    }

    public void a(b var1) {
        this.a = var1;
    }

    public void a(View var1, Activity var2, int var3, boolean var4, boolean var5) {
        this.p = var2;
        this.o = var1;
        this.n = new DB(var2, "P24");
        this.a(var4, var5);

        for(int var6 = 0; var6 < this.c.size(); ++var6) {
            ColorBean[] var7 = (ColorBean[])this.c.get(var6);

            for(int var8 = 0; var8 < var7.length; ++var8) {
                if (var7[var8].colorCode == var3) {
                    this.k = var6;
                    this.l = var6;
                    this.m = var8;
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
        this.i = (HorizontalScrollView)var1.findViewById(2131231351);
        this.d = (LinearLayout)var1.findViewById(2131231327);
        this.j = (RecyclerView)var1.findViewById(2131230905);
        this.j.setHasFixedSize(true);
        LinearLayoutManager var12 = new LinearLayoutManager(var2.getApplicationContext());
        this.j.setLayoutManager(var12);
        this.j.setAdapter(new a(this));
        this.j.setItemAnimator(new DefaultItemAnimator());
        this.f = (EditText)var1.findViewById(2131231026);
        ((TextInputLayout)var1.findViewById(2131231807)).setHint(xB.b().a(var2, 2131625752));
        this.g = (TextView)var1.findViewById(2131231932);
        this.e = new XB(var2, (TextInputLayout)var1.findViewById(2131231807), this.g);
        this.f.setPrivateImeOptions("defaultInputmode=english;");
        this.h = (TextView)var1.findViewById(2131231864);
        this.h.setText(xB.b().a(var2, 2131624970).toUpperCase());
        this.h.setOnClickListener(new Px(this));
        this.j.getAdapter().notifyItemChanged(this.m);
        this.d.removeAllViews();

        for(var3 = 0; var3 < this.b.size(); ++var3) {
            ColorGroupItem var13 = new ColorGroupItem(var2);
            ColorBean var9 = (ColorBean)this.b.get(var3);
            var13.b.setOnClickListener(new Qx(this, var3, var2));
            var13.b.setText(var9.colorName);
            var13.b.setTextColor(var9.displayNameColor);
            var13.b.setBackgroundColor(var9.colorCode);
            this.d.addView(var13);
            if (var3 == this.k) {
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
        aB var2 = new aB(this.p);
        var2.a(2131165524);
        var2.b(xB.b().a(this.p, 2131625756));
        var2.a(xB.b().a(this.p, 2131625754));
        var2.b(xB.b().a(this.p, 2131624986), new Vx(this, var1, var2));
        var2.a(xB.b().a(this.p, 2131624974), new Wx(this, var2));
        var2.show();
    }

    public void a(boolean var1, boolean var2) {
        this.b.add(new ColorBean("#FFF6F6F6", "CUSTOM", "#212121", 2131165412));
        this.b.add(sq.p[0]);
        this.b.add(sq.q[0]);
        this.b.add(sq.r[0]);
        this.b.add(sq.s[0]);
        this.b.add(sq.t[0]);
        this.b.add(sq.u[0]);
        this.b.add(sq.v[0]);
        this.b.add(sq.w[0]);
        this.b.add(sq.x[0]);
        this.b.add(sq.y[0]);
        this.b.add(sq.z[0]);
        this.b.add(sq.A[0]);
        this.b.add(sq.B[0]);
        this.b.add(sq.C[0]);
        this.b.add(sq.D[0]);
        this.b.add(sq.E[0]);
        this.b.add(sq.F[0]);
        this.b.add(sq.G[0]);
        this.b.add(sq.H[0]);
        this.b.add(sq.I[0]);
        this.b.add(sq.J[0]);
        this.c.add(this.b());
        this.c.add(sq.p);
        this.c.add(sq.q);
        this.c.add(sq.r);
        this.c.add(sq.s);
        this.c.add(sq.t);
        this.c.add(sq.u);
        this.c.add(sq.v);
        this.c.add(sq.w);
        this.c.add(sq.x);
        this.c.add(sq.y);
        this.c.add(sq.z);
        this.c.add(sq.A);
        this.c.add(sq.B);
        this.c.add(sq.C);
        this.c.add(sq.D);
        this.c.add(sq.E);
        this.c.add(sq.F);
        this.c.add(sq.G);
        this.c.add(sq.H);
        this.c.add(sq.I);
        this.c.add(sq.J);
        if (var1) {
            this.b.add(sq.K[0]);
            this.c.add(sq.K);
        }

        if (var2) {
            this.b.add(sq.L[0]);
            this.c.add(sq.L);
        }

    }

    public final void b(String var1) {
        String var2 = this.n.f("P24I1");
        if (var2.contains(var1)) {
            StringBuilder var3 = new StringBuilder();
            var3.append(var1);
            var3.append(",");
            var1 = var2.replaceAll(var3.toString(), "");
            this.n.a("P24I1", var1);
            this.c.set(0, this.b());
            this.d();
        }

    }

    public final ColorBean[] b() {
        String var1 = this.n.f("P24I1");
        ColorBean[] var4;
        if (!var1.isEmpty()) {
            String[] var2 = var1.split(",");
            ColorBean[] var12 = new ColorBean[var2.length];
            int var3 = 0;

            while(true) {
                var4 = var12;
                if (var3 >= var2.length) {
                    break;
                }

                label52: {
                    label61: {
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
                            label44: {
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

                    this.n.a();
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
        if (this.k < this.d.getChildCount()) {
            View var1 = this.d.getChildAt(this.k);
            this.i.smoothScrollTo((int)var1.getX(), 0);
            this.j.scrollToPosition(this.m);
        }
    }

    public final void c(String var1) {
        String var2 = this.n.f("P24I1");
        if (var2.contains(var1)) {
            bB.b(this.p, xB.b().a(this.p, 2131625750), 0).show();
        } else {
            StringBuilder var3 = new StringBuilder();
            var3.append(var1);
            var3.append(",");
            var3.append(var2);
            var1 = var3.toString();
            this.n.a("P24I1", var1);
            this.c.set(0, this.b());
            this.d();
            this.k = 0;
            this.c();
        }
    }

    public final void d() {
        this.l = 0;
        this.k = 0;
        this.m = 0;
        this.j.getAdapter().notifyDataSetChanged();
    }

    public class a extends RecyclerView.Adapter<a> {
        public final Zx c;

        public a(Zx var1) {
            this.c = var1;
        }

        public int getItemCount() {
            return ((ColorBean[])this.c.c.get(this.c.l)).length;
        }

        public void onBindViewHolder(a var1, int var2) {
            TextView var3 = var1.u;
            ColorBean var4 = ((ColorBean[])this.c.c.get(this.c.l))[var2];
            boolean var5;
            if (this.c.l == 0) {
                var5 = true;
            } else {
                var5 = false;
            }

            var3.setText(var4.getColorCode(var5));
            if (var2 == 0) {
                var1.v.setText(((ColorBean[])this.c.c.get(this.c.l))[0].colorName);
            } else {
                var1.v.setText("");
            }

            var1.u.setTextColor(((ColorBean[])this.c.c.get(this.c.l))[var2].displayNameColor);
            var1.v.setTextColor(((ColorBean[])this.c.c.get(this.c.l))[var2].displayNameColor);
            var1.t.setBackgroundColor(((ColorBean[])this.c.c.get(this.c.l))[var2].colorCode);
            if (var2 == this.c.m && this.c.l == this.c.k) {
                var1.w.setImageResource(((ColorBean[])this.c.c.get(this.c.l))[var2].icon);
                var1.w.setVisibility(0);
            } else {
                var1.w.setVisibility(8);
            }

        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            return new a(this, LayoutInflater.from(var1.getContext()).inflate(2131427375, var1, false));
        }

        public class a extends RecyclerView.ViewHolder {
            public View t;
            public TextView u;
            public TextView v;
            public ImageView w;
            public final a x;

            public a(a var1, View var2) {
                super(var2);
                this.x = var1;
                this.t = var2.findViewById(2131231326);
                this.u = (TextView)var2.findViewById(2131231915);
                this.v = (TextView)var2.findViewById(2131231916);
                this.w = (ImageView)var2.findViewById(2131231182);
                var2.setOnClickListener(new Xx(this, var1));
                var2.setOnLongClickListener(new Yx(this, var1));
            }
        }
    }

    public interface b {
        void a(int var1);
    }
}
