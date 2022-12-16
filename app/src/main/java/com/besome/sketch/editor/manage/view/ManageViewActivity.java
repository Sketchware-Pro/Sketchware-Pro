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

import com.besome.sketch.beans.EventBean;
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
    private String sc_id;
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
        ArrayList<ViewBean> var12 = jC.a(sc_id).d(var2);
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
        jC.a(sc_id);
        for (ViewBean viewBean : eC.a(var2)) {
            viewBean.id = a(viewBean.type, var1.getXmlName());
            jC.a(sc_id).a(var1.getXmlName(), viewBean);
            if (viewBean.type == ViewBean.VIEW_TYPE_WIDGET_BUTTON && var1.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                jC.a(sc_id).a(var1.getJavaName(), EventBean.EVENT_TYPE_VIEW, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    public void a(boolean var1) {
        q = var1;
        invalidateOptionsMenu();
        if (q) {
            n.setVisibility(View.VISIBLE);
        } else {
            n.setVisibility(View.GONE);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> l() {
        ArrayList<String> projectLayoutFiles = new ArrayList<>();
        projectLayoutFiles.add("debug");
        ArrayList<ProjectFileBean> activitiesFiles = t.c();
        ArrayList<ProjectFileBean> customViewsFiles = u.c();

        for (ProjectFileBean projectFileBean : activitiesFiles) {
            projectLayoutFiles.add(projectFileBean.fileName);
        }

        for (ProjectFileBean projectFileBean : customViewsFiles) {
            projectLayoutFiles.add(projectFileBean.fileName);
        }

        return projectLayoutFiles;
    }

    public final void m() {
        jC.b(sc_id).a(t.c());
        jC.b(sc_id).b(u.c());
        jC.b(sc_id).l();
        jC.b(sc_id).j();
        jC.a(sc_id).a(jC.b(sc_id));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProjectFileBean projectFileBean;
        if (requestCode == 264) {
            if (resultCode == RESULT_OK) {
                projectFileBean = data.getParcelableExtra("project_file");
                t.a(projectFileBean);
                if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    b(projectFileBean.getDrawerName());
                }

                if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) || projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    jC.c(sc_id).c().useYn = "Y";
                }

                if (data.hasExtra("preset_views")) {
                    a(projectFileBean, data.getParcelableArrayListExtra("preset_views"));
                }
            }
        } else if (requestCode == 266 && resultCode == RESULT_OK) {
            projectFileBean = data.getParcelableExtra("project_file");
            u.a(projectFileBean);
            u.g();
            if (data.hasExtra("preset_views")) {
                a(projectFileBean, data.getParcelableArrayListExtra("preset_views"));
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
            } catch (Exception e) {
                e.printStackTrace();
                h();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int viewId = v.getId();
            if (viewId != 2131230810) {
                if (viewId != 2131230817) {
                    if (viewId == 2131231054) {
                        a(false);
                        Intent intent;
                        if (this.v.getCurrentItem() == 0) {
                            intent = new Intent(this, AddViewActivity.class);
                            intent.putStringArrayListExtra("screen_names", l());
                            intent.putExtra("request_code", 264);
                            startActivityForResult(intent, 264);
                        } else {
                            intent = new Intent(this, AddCustomViewActivity.class);
                            intent.putStringArrayListExtra("screen_names", l());
                            startActivityForResult(intent, 266);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }

        setContentView(2131427569);
        m = findViewById(2131231847);
        a(m);
        findViewById(2131231370).setVisibility(View.GONE);
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
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
            r = getIntent().getStringExtra("compatUseYn");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            r = savedInstanceState.getString("compatUseYn");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492881, menu);
        menu.findItem(2131231532).setVisible(!q);
        return true;
    }

    @Override
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131231532) {
            a(!q);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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
    public void onSaveInstanceState(Bundle newState) {
        newState.putString("sc_id", sc_id);
        newState.putString("compatUseYn", r);
        super.onSaveInstanceState(newState);
    }

    public class a extends MA {
        public final ManageViewActivity c;

        public a(ManageViewActivity var1, Context var2) {
            super(var2);
            c = var1;
            ManageViewActivity.this.a(this);
        }

        @Override
        public void a() {
            c.h();
            setResult(RESULT_OK);
            finish();
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
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new By(xB.b().a(super.a, 2131624916));
                } catch (By ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
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
