package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.pw;
import a.a.a.qw;
import a.a.a.rq;
import a.a.a.rw;
import a.a.a.sw;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

public class AddViewActivity extends BaseDialogActivity {
    public LinearLayout A;
    public RelativeLayout B;
    public ImageView C;
    public ImageView D;
    public TextInputLayout E;
    public EditText F;
    public YB G;
    public TextView H;
    public ArrayList<String> I;
    public boolean J;
    public boolean K;
    public boolean L;
    public boolean M;
    public int N;
    public ProjectFileBean O;
    public String P;
    public SelectableButtonBar add_view_type_selector;
    public LinearLayout add_view_type_selector_layout;
    public ArrayList<a> t;
    public RecyclerView u;
    public b v;
    public SelectableButtonBar w;
    public SelectableButtonBar x;
    public LinearLayout y;
    public LinearLayout z;

    public final void a(View var1) {
        var1.animate().translationX((float) (-var1.getMeasuredWidth())).start();
    }

    public final void a(a var1) {
        int var2 = var1.a;
        if (var2 != 0) {
            if (var2 != 1) {
                if (var2 != 2) {
                    if (var2 == 3) {
                        if (var1.d) {
                            e(C);
                        } else {
                            b(C);
                        }
                    }
                } else if (var1.d) {
                    d(A);
                } else {
                    a((View) A);
                }
            } else if (var1.d) {
                if (!J) {
                    z.animate().translationY((float) (-y.getMeasuredHeight())).start();
                } else {
                    e(z);
                }
            } else if (!J) {
                n();
            } else {
                c(z);
            }
        } else if (var1.d) {
            e(y);
            if (K) {
                e(z);
            }
        } else {
            c(y);
            if (K) {
                z.animate().translationY((float) (-y.getMeasuredHeight())).start();
            } else {
                n();
            }
        }

    }

    public boolean a(YB var1) {
        boolean var2;
        var2 = var1.b();

        return var2;
    }

    public final void b(View var1) {
        var1.animate().translationY((float) var1.getMeasuredHeight()).start();
    }

    public final void b(boolean var1) {
        for (int var2 = 0; var2 < t.size(); ++var2) {
            a var3 = (a) t.get(var2);
            if (var3.a == 2) {
                var3.d = var1;
                v.notifyItemChanged(var2);
                break;
            }
        }

    }

    public final void c(View var1) {
        var1.animate().translationY((float) (-var1.getMeasuredHeight())).start();
    }

    public final void c(boolean var1) {
        for (int var2 = 0; var2 < t.size(); ++var2) {
            a var3 = (a) t.get(var2);
            if (var3.a == 1) {
                var3.d = var1;
                v.notifyItemChanged(var2);
                break;
            }
        }

    }

    public final void d(View var1) {
        var1.animate().translationX(0.0F).start();
    }

    public final void e(View var1) {
        var1.animate().translationY(0.0F).start();
    }

    public final ArrayList<ViewBean> f(String var1) {
        return rq.f(var1);
    }

    public final void g(int var1) {
        K = (var1 & 1) == 1;

        J = (var1 & 2) != 2;

        L = (var1 & 8) == 8;

        M = (var1 & 4) == 4;

    }

    public final void n() {
        z.animate().translationY((float) (-(y.getMeasuredHeight() + z.getMeasuredHeight()))).start();
    }

    public final void o() {
        t = new ArrayList<>();
        t.add(new a(this, 0, 2131165864, "StatusBar", J));
        t.add(new a(this, 1, 2131165872, "Toolbar", K));
        t.add(new a(this, 2, 2131165737, "Drawer", M));
        t.add(new a(this, 3, 2131165608, "FAB", L));
        v.notifyDataSetChanged();
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        if (var1 == 276 && var2 == -1) {
            ProjectFileBean var4 = (ProjectFileBean) var3.getParcelableExtra("preset_data");
            P = var4.presetName;
            g(var4.options);
            o();
        }

    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427557);
        e(xB.b().a(getApplicationContext(), 2131625299));
        Intent var2 = getIntent();
        I = var2.getStringArrayListExtra("screen_names");
        N = var2.getIntExtra("request_code", 264);
        O = (ProjectFileBean) var2.getParcelableExtra("project_file");
        if (O != null) {
            e(xB.b().a(getApplicationContext(), 2131625300));
        }

        add_view_type_selector = (SelectableButtonBar) findViewById(2131232506);
        add_view_type_selector_layout = (LinearLayout) findViewById(2131232507);
        w = (SelectableButtonBar) findViewById(2131230865);
        x = (SelectableButtonBar) findViewById(2131230864);
        y = (LinearLayout) findViewById(2131231604);
        z = (LinearLayout) findViewById(2131231605);
        A = (LinearLayout) findViewById(2131231601);
        B = (RelativeLayout) findViewById(2131231603);
        C = (ImageView) findViewById(2131231602);
        D = (ImageView) findViewById(2131231154);
        E = (TextInputLayout) findViewById(2131231825);
        F = (EditText) findViewById(2131231007);
        H = (TextView) findViewById(2131232285);
        H.setVisibility(8);
        H.setText(xB.b().a(getApplicationContext(), 2131625295));
        E.setHint(xB.b().a(this, 2131625293));
        F.setPrivateImeOptions("defaultInputmode=english;");
        u = (RecyclerView) findViewById(2131231056);
        v = new b(this);
        u.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        u.setHasFixedSize(true);
        u.setAdapter(v);
        ((TextView) findViewById(2131232145)).setText(xB.b().a(getApplicationContext(), 2131625303));
        ((TextView) findViewById(2131232020)).setText(xB.b().a(getApplicationContext(), 2131625302));
        add_view_type_selector.a(0, "Activity");
        add_view_type_selector.a(1, "Fragment");
        add_view_type_selector.a(2, "DialogFragment");
        add_view_type_selector.a();
        w.a(0, "Portrait");
        w.a(1, "Landscape");
        w.a(2, "Both");
        w.a();
        x.a(0, "Unspecified");
        x.a(1, "Visible");
        x.a(2, "Hidden");
        x.a();
        x.setListener(new pw(this));
        d(xB.b().a(getApplicationContext(), 2131624970));
        b(xB.b().a(getApplicationContext(), 2131624974));
        super.r.setOnClickListener(new qw(this));
        super.s.setOnClickListener(new rw(this));
        if (N == 265) {
            G = new YB(getApplicationContext(), E, uq.b, new ArrayList(), O.fileName);
            F.setText(O.fileName);
            F.setEnabled(false);
            F.setBackgroundResource(2131034318);
            g(O.options);
            add_view_type_selector_layout.setVisibility(8);
            w.setSelectedItemByKey(O.orientation);
            x.setSelectedItemByKey(O.keyboardSetting);
            super.r.setText(xB.b().a(getApplicationContext(), 2131625031).toUpperCase());
        } else {
            K = true;
            J = true;
            G = new YB(getApplicationContext(), E, uq.b, I);
        }

        o();
    }

    public void onResume() {
        super.onResume();
        super.d.setScreenName(AddViewActivity.class.getSimpleName());
        super.d.send((new HitBuilders.ScreenViewBuilder()).build());
    }

    public class a {
        public int a;
        public int b;
        public String c;
        public boolean d;
        public final AddViewActivity e;

        public a(AddViewActivity var1, int var2, int var3, String var4, boolean var5) {
            e = var1;
            a = var2;
            b = var3;
            c = var4;
            d = var5;
        }
    }

    public class b extends RecyclerView.Adapter<a> {
        public int c;
        public boolean d;
        public final AddViewActivity e;

        public b(AddViewActivity var1) {
            e = var1;
            c = -1;
        }

        public int getItemCount() {
            return e.t.size();
        }

        public void onBindViewHolder(a var1, int var2) {
            d = true;
            a var3 = (a) e.t.get(var2);
            var1.t.setImageResource(var3.b);
            var1.u.setText(var3.c);
            var1.v.setChecked(var3.d);
            var2 = var3.a;
            if (var2 == 0) {
                e.J = var3.d;
            } else if (var2 == 1) {
                e.K = var3.d;
            } else if (var2 == 3) {
                e.L = var3.d;
            } else if (var2 == 2) {
                e.M = var3.d;
            }

            AddViewActivity var4 = e;
            if (var4.L || var4.M) {
                e.H.setVisibility(0);
            }

            e.a(var3);
            d = false;
        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            View var3 = wB.a(var1.getContext(), 2131427556);
            var3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new a(this, var3);
        }

        public class a extends RecyclerView.ViewHolder {
            public ImageView t;
            public TextView u;
            public CheckBox v;
            public final b w;

            public a(b var1, View var2) {
                super(var2);
                w = var1;
                t = (ImageView) var2.findViewById(2131231151);
                u = (TextView) var2.findViewById(2131232055);
                v = (CheckBox) var2.findViewById(2131230883);
                v.setOnCheckedChangeListener(new sw(this, var1));
            }
        }
    }
}
