package com.besome.sketch.editor.manage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.By;
import a.a.a.Fw;
import a.a.a.MA;
import a.a.a.Xf;
import a.a.a.bB;
import a.a.a.eC;
import a.a.a.gg;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.to;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xo;
import a.a.a.xw;

@SuppressLint("ResourceType")
public class ManageViewActivity extends BaseAppCompatActivity implements OnClickListener, ViewPager.e, to {

    public static final int k = 2;
    public String l;
    public Toolbar m;
    public LinearLayout n;
    public Button o;
    public Button p;
    public boolean q = false;
    public String r = "N";
    public FloatingActionButton s;
    public Fw t;
    public xw u;
    public ViewPager v;
    public TabLayout w;
    public int[] x = new int[19];

    public final String a(int var1, String var2) {
        String var3 = wq.b(var1);
        StringBuilder var4 = new StringBuilder();
        var4.append(var3);
        int[] var5 = x;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var9 = var4.toString();
        ArrayList<ViewBean> var12 = jC.a(l).d(var2);
        var2 = var9;

        while (true) {
            boolean var7 = false;
            Iterator<ViewBean> var10 = var12.iterator();

            boolean var13;
            while (true) {
                var13 = var7;
                if (!var10.hasNext()) {
                    break;
                }

                if (var2.equals(var10.next().id)) {
                    var13 = true;
                    break;
                }
            }

            if (!var13) {
                return var2;
            }

            StringBuilder var8 = new StringBuilder();
            var8.append(var3);
            int[] var11 = x;
            var6 = var11[var1] + 1;
            var11[var1] = var6;
            var8.append(var6);
            var2 = var8.toString();
        }
    }

    @Override
    public void a(int var1) {
    }

    @Override
    public void a(int var1, float var2, int var3) {
    }

    public final void a(ProjectFileBean var1, ArrayList<ViewBean> var2) {
        jC.a(l);
        for (ViewBean var3 : eC.a(var2)) {
            var3.id = a(var3.type, var1.getXmlName());
            jC.a(l).a(var1.getXmlName(), var3);
            if (var3.type == 3 && var1.fileType == 0) {
                jC.a(l).a(var1.getJavaName(), 1, var3.type, var3.id, "onClick");
            }
        }
    }

    public void a(boolean var1) {
        q = var1;
        invalidateOptionsMenu();
        if (q) {
            n.setVisibility(0);
        } else {
            n.setVisibility(8);
        }

        t.a(q);
        u.a(q);
    }

    @Override
    public void b(int var1) {
        s.f();
    }

    public void b(String var1) {
        u.a(var1);
        u.g();
    }

    public void c(String var1) {
        u.b(var1);
        u.g();
    }

    @Override
    public void d(int var1) {
        try {
            new Handler().postDelayed(() -> (new a(this, getApplicationContext())).execute(), 500L);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public ArrayList<String> l() {
        ArrayList<String> var1 = new ArrayList<>();
        var1.add("debug");
        ArrayList<ProjectFileBean> var2 = t.c();
        ArrayList<ProjectFileBean> var3 = u.c();

        for (ProjectFileBean projectFileBean : var2) {
            var1.add(projectFileBean.fileName);
        }

        for (ProjectFileBean projectFileBean : var3) {
            var1.add(projectFileBean.fileName);
        }

        return var1;
    }

    public final void m() {
        jC.b(l).a(t.c());
        jC.b(l).b(u.c());
        jC.b(l).l();
        jC.b(l).j();
        jC.a(l).a(jC.b(l));
    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        ProjectFileBean var4;
        if (var1 == 264) {
            if (var2 == -1) {
                var4 = var3.getParcelableExtra("project_file");
                t.a(var4);
                if (var4.hasActivityOption(4)) {
                    b(var4.getDrawerName());
                }

                if (var4.hasActivityOption(4) || var4.hasActivityOption(8)) {
                    jC.c(l).c().useYn = "Y";
                }

                if (var3.hasExtra("preset_views")) {
                    a(var4, var3.getParcelableArrayListExtra("preset_views"));
                }
            }
        } else if (var1 == 266 && var2 == -1) {
            var4 = var3.getParcelableExtra("project_file");
            u.a(var4);
            u.g();
            if (var3.hasExtra("preset_views")) {
                a(var4, var3.getParcelableArrayListExtra("preset_views"));
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (q) {
            a(false);
        } else {
            k();

            try {
                if (super.j.h()) {
                    new Handler().postDelayed(() -> (new a(this, getApplicationContext())).execute(), 500L);
                } else {
                    xo.a(getApplicationContext());
                }
            } catch (Exception var3) {
                var3.printStackTrace();
                h();
            }

        }
    }

    @Override
    public void onClick(View var1) {
        if (!mB.a()) {
            int var2 = var1.getId();
            if (var2 != 2131230810) {
                if (var2 != 2131230817) {
                    if (var2 == 2131231054) {
                        a(false);
                        Intent var3;
                        if (v.getCurrentItem() == 0) {
                            var3 = new Intent(this, AddViewActivity.class);
                            var3.putStringArrayListExtra("screen_names", l());
                            var3.putExtra("request_code", 264);
                            startActivityForResult(var3, 264);
                        } else {
                            var3 = new Intent(this, AddCustomViewActivity.class);
                            var3.putStringArrayListExtra("screen_names", l());
                            startActivityForResult(var3, 266);
                        }
                    }
                } else if (q) {
                    t.f();
                    u.f();
                    a(false);
                    t.g();
                    u.g();
                    bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624935), 1).show();
                    s.f();
                }
            } else if (q) {
                a(false);
            }

        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        if (!super.j()) {
            finish();
        }

        setContentView(2131427569);
        m = findViewById(2131231847);
        a(m);
        findViewById(2131231370).setVisibility(8);
        d().a(xB.b().a(getApplicationContext(), 2131625138));
        d().e(true);
        d().d(true);
        m.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });
        n = findViewById(2131231319);
        o = findViewById(2131230817);
        p = findViewById(2131230810);
        o.setText(xB.b().a(getApplicationContext(), 2131624986));
        p.setText(xB.b().a(getApplicationContext(), 2131624974));
        o.setOnClickListener(this);
        p.setOnClickListener(this);
        if (var1 == null) {
            l = getIntent().getStringExtra("sc_id");
            r = getIntent().getStringExtra("compatUseYn");
        } else {
            l = var1.getString("sc_id");
            r = var1.getString("compatUseYn");
        }

        w = findViewById(2131231781);
        v = findViewById(2131232325);
        v.setAdapter(new ManageViewActivity.b(this, getSupportFragmentManager()));
        v.setOffscreenPageLimit(2);
        v.a(this);
        w.setupWithViewPager(v);
        s = findViewById(2131231054);
        s.setOnClickListener(this);
        xo.a((to) this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu var1) {
        getMenuInflater().inflate(2131492881, var1);
        var1.findItem(2131231532).setVisible(!q);

        return true;
    }

    @Override
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem var1) {
        if (var1.getItemId() == 2131231532) {
            a(!q);
        }

        return super.onOptionsItemSelected(var1);
    }

    @Override
    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }

        super.d.setScreenName(ManageViewActivity.class.getSimpleName());
        super.d.send((new ScreenViewBuilder()).build());
    }

    @Override
    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", l);
        var1.putString("compatUseYn", r);
        super.onSaveInstanceState(var1);
    }

    public class a extends MA {
        public final ManageViewActivity c;

        public a(ManageViewActivity var1, Context var2) {
            super(var2);
            c = var1;
            var1.a(this);
        }

        @Override
        public void a() {
            c.h();
            c.setResult(-1);
            c.finish();
        }

        @Override
        public void a(String var1) {
            c.h();
        }

        @Override
        public void b() {
            try {
                publishProgress(xB.b().a(c.getApplicationContext(), 2131624963));
                c.m();
            } catch (Exception var4) {
                var4.printStackTrace();
                try {
                    throw new By(xB.b().a(super.a, 2131624916));
                } catch (By ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }

    public class b extends gg {
        public final ManageViewActivity g;
        public String[] f;

        public b(ManageViewActivity var1, Xf var2) {
            super(var2);
            g = var1;
            f = new String[2];
            f[0] = xB.b().a(var1.getApplicationContext(), 2131625046).toUpperCase();
            f[1] = xB.b().a(var1.getApplicationContext(), 2131624984).toUpperCase();
        }

        @Override
        public int a() {
            return 2;
        }

        @Override
        public CharSequence a(int var1) {
            return f[var1];
        }

        @Override
        public Object a(ViewGroup var1, int var2) {
            Fragment var3 = (Fragment) super.a(var1, var2);
            if (var2 != 0) {
                g.u = (xw) var3;
            } else {
                g.t = (Fw) var3;
            }

            return var3;
        }

        @Override
        public Fragment c(int var1) {
            return var1 != 0 ? new xw() : new Fw();
        }
    }
}
