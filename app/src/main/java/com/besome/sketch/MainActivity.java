package com.besome.sketch;

import a.a.a.An;
import a.a.a.Bn;
import a.a.a.DB;
import a.a.a.GB;
import a.a.a.GC;
import a.a.a.Xf;
import a.a.a.aB;
import a.a.a.gg;
import a.a.a.l;
import a.a.a.sB;
import a.a.a.wn;
import a.a.a.xB;
import a.a.a.xn;
import a.a.a.yn;
import a.a.a.zI;
import a.a.a.zn;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.besome.sketch.acc.LoginActivity;
import com.besome.sketch.acc.MyPageSettingsActivity;
import com.besome.sketch.acc.ProfileActivity;
import com.besome.sketch.bill.InAppActivity;
import com.besome.sketch.bill.SubscribeActivity;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;
import com.besome.sketch.shared.project.SharedProjectDetailActivity;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends BasePermissionAppCompatActivity implements ViewPager.e {
    public ImageView A;
    public FirebaseAnalytics B;
    public int C;
    public boolean D;
    public LinearLayout E;
    public FloatingActionButton F;
    public ConsentForm G = null;
    public final int k = 2;
    public Toolbar l;
    public DrawerLayout m;
    public l n;
    public MainDrawer o;
    public ViewPager p;
    public TabLayout q;
    public String[] r;
    public int[] s = {2131165314, 2131165717, 2131165648};
    public DB t;
    public DB u;
    public DB v;
    public CoordinatorLayout w;
    public Snackbar x;
    public GC y = null;
    public zI z = null;

    public void a(int i) {
    }

    public void a(int i, float f, int i2) {
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void g(int i) {
        GC gc;
        if (i == 9501 && this.p.getCurrentItem() == 0 && (gc = this.y) != null) {
            gc.g();
        }
    }
    
    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void h(int i) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
        startActivityForResult(intent, i);
    }

    public final void k(int i) {
        for (int i2 = 0; i2 < this.r.length; i2++) {
            TabLayout.f c = this.q.c(i2);
            View childAt = ((ViewGroup) this.q.getChildAt(0)).getChildAt(i2);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams.width = -1;
            if (i2 == i) {
                c.b(this.r[i2]);
                c.a((Drawable) null);
                layoutParams.weight = 2.0f;
            } else {
                c.b("");
                c.b(this.s[i2]);
                layoutParams.weight = 1.0f;
            }
            childAt.setLayoutParams(layoutParams);
        }
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void l() {
    }

    public void l(int i) {
        ViewPager viewPager = this.p;
        if (viewPager != null) {
            viewPager.a(i, true);
        }
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void m() {
    }

    @Override
    public Object a(ViewGroup viewGroup, int i) {
        return null;
    }

    public final void m(int i) {
        if (i > 0) {
            Log.d("DEBUG", MainActivity.class.getSimpleName() + " sharedListFragment updateProject =" + i);
        }
    }

    public void n() {
        GC gc = this.y;
        if (gc != null) {
            gc.a(false);
        }
    }
    
    public final void o() {
        ConsentInformation.a(getApplicationContext()).a(new String[]{"pub-7684160946124871"}, (ConsentInfoUpdateListener) new zn(this));
    }
    
    public void onActivityResult(int i, int i2, Intent intent) {
        MainDrawer mainDrawer;
        MainActivity.super.onActivityResult(i, i2, intent);
        invalidateOptionsMenu();
        int i3 = -1;
        if (i == 100) {
            invalidateOptionsMenu();
            if (i2 == -1) {
                if (this.i.g().isEmpty()) {
                    Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent2.setFlags(536870912);
                    startActivityForResult(intent2, 108);
                } else {
                    u();
                }
            }
            MainDrawer mainDrawer2 = this.o;
            if (mainDrawer2 != null) {
                mainDrawer2.i();
            }
        } else if (i != 105) {
            if (i != 111) {
                if (i != 113) {
                    if (i != 212) {
                        if (i == 505 && (mainDrawer = this.o) != null) {
                            mainDrawer.i();
                        }
                    } else if (i2 == -1) {
                        if (!(intent.getStringExtra("save_as_new_id") == null ? "" : intent.getStringExtra("save_as_new_id")).isEmpty() && super.j()) {
                            this.y.g();
                        }
                        if (intent != null) {
                            i3 = intent.getIntExtra("shared_id", -1);
                        }
                        m(i3);
                    }
                } else if (i2 == -1 && intent != null && intent.getBooleanExtra("not_show_popup_anymore", false)) {
                    this.u.a("U1I2", false);
                }
            } else if (i2 == -1) {
                invalidateOptionsMenu();
            }
        } else if (i2 == -1) {
            l(0);
            boolean booleanExtra = intent.getBooleanExtra("onlyConfig", true);
            if (booleanExtra) {
                sB.a(this, booleanExtra);
            } else if (super.f(i)) {
                sB.a(this, booleanExtra);
            }
        }
    }
    
    public void onBackPressed() {
        if (this.o.isShown()) {
            this.m.b();
        } else {
            finish();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        MainActivity.super.onConfigurationChanged(configuration);
        this.n.a(configuration);
    }
    
    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427501);
        this.t = new DB(getApplicationContext(), "P1");
        this.u = new DB(getApplicationContext(), "U1");
        this.v = new DB(getApplicationContext(), "P25");
        this.C = this.u.a("U1I0", -1);
        long e = this.u.e("U1I1");
        if (e <= 0) {
            this.u.a("U1I1", Long.valueOf(System.currentTimeMillis()));
        }
        if (System.currentTimeMillis() - e > 86400000) {
            this.u.a("U1I0", Integer.valueOf(this.C + 1));
        }
        this.D = this.u.a("U1I2", true);
        this.r = new String[2];
        this.r[0] = xB.b().a(this, 2131625608);
        this.r[1] = xB.b().a(this, 2131625610);
        this.l = findViewById(2131231847);
        a(this.l);
        d().d(true);
        d().e(true);
        this.A = (ImageView) findViewById(2131231193);
        this.A.setOnClickListener(new wn(this));
        this.o = (MainDrawer) findViewById(2131231426);
        this.m = findViewById(2131230981);
        this.n = new l(this, this.m, 2131624002, 2131624002);
        this.m.setDrawerListener(this.n);
        d().a("");
        this.p = findViewById(2131232328);
        this.p.setOffscreenPageLimit(2);
        this.p.setAdapter(new a(getSupportFragmentManager(), this));
        this.q = findViewById(2131231781);
        this.q.setupWithViewPager(this.p);
        this.p.a(this);
        this.E = (LinearLayout) findViewById(2131231391);
        this.F = findViewById(2131231054);
        this.w = findViewById(2131231335);
        k(0);
        l(0);
        this.B = FirebaseAnalytics.getInstance(this);
        try {
            String stringExtra = getIntent().getStringExtra("auto_run_activity");
            if (stringExtra != null) {
                if (!"InAppActivity".equals(stringExtra)) {
                    if (!"SubscribeActivity".equals(stringExtra)) {
                        if ("SharedProjectDetailActivity".equals(stringExtra)) {
                            Intent intent = new Intent(getApplicationContext(), SharedProjectDetailActivity.class);
                            intent.setFlags(536870912);
                            intent.putExtra("shared_id", Integer.parseInt(getIntent().getStringExtra("shared_id")));
                            startActivity(intent);
                        }
                    }
                }
                Intent intent2 = new Intent(getApplicationContext(), SubscribeActivity.class);
                intent2.setFlags(536870912);
                startActivityForResult(intent2, 505);
            }
        } catch (Exception unused) {
        }
        if (this.C > 0 && !super.j()) {
            q();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492873, menu);
        if (this.i.a()) {
            menu.findItem(2131231522).setVisible(false);
            menu.findItem(2131231526).setVisible(true);
        } else {
            menu.findItem(2131231522).setVisible(true);
            menu.findItem(2131231526).setVisible(false);
        }
        return true;
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onDestroy() {
        super.onDestroy();
        xB.b().a();
    }
    public void onNewIntent(Intent intent) {
        MainActivity.super.onNewIntent(intent);
        try {
            String stringExtra = intent.getStringExtra("auto_run_activity");
            if (stringExtra != null) {
                if (!"InAppActivity".equals(stringExtra)) {
                    if (!"SubscribeActivity".equals(stringExtra)) {
                        if ("SharedProjectDetailActivity".equals(stringExtra)) {
                            Intent intent2 = new Intent(getApplicationContext(), SharedProjectDetailActivity.class);
                            intent2.setFlags(536870912);
                            intent2.putExtra("shared_id", Integer.parseInt(intent.getStringExtra("shared_id")));
                            startActivity(intent2);
                            return;
                        }
                        return;
                    }
                }
                Intent intent3 = new Intent(getApplicationContext(), InAppActivity.class);
                intent3.setFlags(536870912);
                startActivityForResult(intent3, 505);
            }
        } catch (Exception unused) {
        }
    }
    
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.n.a(menuItem)) {
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 2131231522 || itemId == 2131231526) {
            if (this.i.a()) {
                u();
            } else {
                t();
            }
        }
        return MainActivity.super.onOptionsItemSelected(menuItem);
    }

    public void onPostCreate(Bundle bundle) {
        MainActivity.super.onPostCreate(bundle);
        this.n.b();
        o();
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        Snackbar snackbar;
        super.onResume();
        this.d.setScreenName(MainActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
        long c = GB.c();
        if (c < 100 && c > 0) {
            r();
        }
        if (j() && (snackbar = this.x) != null && snackbar.j()) {
            this.x.c();
        }
    }

    public void onStart() {
        MainActivity.super.onStart();
    }

    public final void p() {
        URL url;
        try {
            url = new URL("http://sketchware.io/terms.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        this.G = new ConsentForm.Builder(this, url).a((ConsentFormListener) new An(this)).d().c().b().a();
        this.G.a();
    }

    public final void q() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131624962));
        aBVar.a(2131165452);
        aBVar.a(xB.b().a(getApplicationContext(), 2131624959));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new xn(this, aBVar));
        aBVar.show();
    }

    public final void r() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131624948));
        aBVar.a(2131165669);
        aBVar.a(xB.b().a(getApplicationContext(), 2131624947));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new Bn(this, aBVar));
        aBVar.show();
    }

    public void s() {
        Snackbar snackbar = this.x;
        if (snackbar == null || !snackbar.j()) {
            this.x = Snackbar.a(this.w, xB.b().a(getApplicationContext(), 2131624958), -2);
            this.x.a(xB.b().a(getApplicationContext(), 2131625036), new yn(this));
            this.x.h().setAlpha(0.5f);
            this.x.f(-256);
            this.x.n();
        }
    }

    public void t() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(536870912);
        startActivityForResult(intent, 100);
    }

    public void u() {
        Intent intent = new Intent(getApplicationContext(), MyPageSettingsActivity.class);
        intent.setFlags(536870912);
        startActivityForResult(intent, 111);
    }

    public void b(int i) {
        GC gc;
        k(i);
        if (i == 0) {
            if (super.j() && (gc = this.y) != null && gc.f() == 0) {
                this.y.g();
            }
            this.E.setVisibility(8);
            this.y.h();
        } else if (i == 1) {
            this.E.setVisibility(8);
            this.F.c();
        }
    }

    public class a extends gg {
        public Context f;
        public GC gc;

        public a(Xf xf, Context context) {
            super(xf);
            this.f = context;
        }

        public int a() {
            return 2;
        }

        public Object a(ViewGroup viewGroup, int i) {
            zI zIVar = (zI) super.a(viewGroup, i);
            if (i == 0) {
                MainActivity.this.y = gc;
            } else if (i == 1) {
                MainActivity.this.z = zIVar;
            }
            return zIVar;
        }

        public Fragment c(int i) {
            if (i != 0) {
                return new zI();
            }
            return new GC();
        }

        public CharSequence a(int i) {
            return MainActivity.this.r[i];
        }
    }

    public void b(String str) {
        ViewPager viewPager = this.p;
        if (viewPager != null) {
            viewPager.setCurrentItem(0);
        }
        GC gc = this.y;
        if (gc != null) {
            gc.g();
            this.y.c(str);
        }
    }
}
