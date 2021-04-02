package com.besome.sketch.design;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.common.SrcViewerActivity;
import com.besome.sketch.editor.manage.ManageCollectionActivity;
import com.besome.sketch.editor.manage.font.ManageFontActivity;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.manage.library.ManageLibraryActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.besome.sketch.editor.manage.view.ManageViewActivity;
import com.besome.sketch.editor.view.ProjectFileSelector;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CustomViewPager;
import com.besome.sketch.tools.CompileLogActivity;
import com.besome.sketch.tools.ExportApkActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.HashMap;

import a.a.a.Ay;
import a.a.a.DB;
import a.a.a.Dp;
import a.a.a.Ep;
import a.a.a.GB;
import a.a.a.MA;
import a.a.a.Xf;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.bC;
import a.a.a.br;
import a.a.a.by;
import a.a.a.cC;
import a.a.a.gg;
import a.a.a.jC;
import a.a.a.jr;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nq;
import a.a.a.oB;
import a.a.a.rs;
import a.a.a.to;
import a.a.a.uo;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xo;
import a.a.a.yB;
import a.a.a.yq;
import dev.aldi.sayuti.editor.manage.ManageCustomAttributeActivity;
import dev.aldi.sayuti.editor.manage.ManageLocalLibraryActivity;
import id.indosw.mod.DirectEditorActivity;
import mod.agus.jcoderz.editor.manage.background.ManageBackgroundActivity;
import mod.agus.jcoderz.editor.manage.permission.ManagePermissionActivity;
import mod.agus.jcoderz.editor.manage.resource.ManageResourceActivity;
import mod.hey.studios.activity.managers.assets.ManageAssetsActivity;
import mod.hey.studios.activity.managers.java.ManageJavaActivity;
import mod.hey.studios.activity.managers.nativelib.ManageNativelibsActivity;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.build.BuildSettingsDialog;
import mod.hey.studios.project.DesignActRunnable;
import mod.hey.studios.project.custom_blocks.CustomBlocksDialog;
import mod.hey.studios.project.proguard.ManageProguardActivity;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.ManageStringfogActivity;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hilal.saif.activities.android_manifest.AndroidManifestInjection;
import mod.hosni.fraj.compilerlog.CompileLogSaver;
import mod.jbk.bundle.BundleToolCompiler;

public class DesignActivity extends BaseAppCompatActivity implements OnClickListener, uo {

    public ImageView A;
    public boolean B = false;
    public View C;
    public int D = -1;
    public int E;
    public boolean F = false;
    public AdView G;
    public View H;
    public to I;
    public DesignActivity.f J = null;
    public Toolbar k;
    /**
     * The sc_id of the current opened project, like 605
     */
    public String l;
    public CustomViewPager m;
    public CoordinatorLayout n;
    public DrawerLayout o;
    public LinearLayout p;
    public yq q;
    public DB r;
    public DB s;
    public DB t;
    /**
     * The Run-Button in bottom right corner
     */
    public Button u;
    public ProjectFileSelector v;
    public jr w = null;
    public rs x = null;
    public br y = null;
    public oB z;

    public DesignActivity() {
    }

    public final void A() {
        HashMap<String, Object> var1 = lC.b(this.l);
        if (var1 != null) {
            var1.put("sketchware_ver", GB.d(this.getApplicationContext()));
            lC.b(this.l, var1);
        }
    }

    public void a(boolean var1) {
        jC.a(this.l, var1);
        jC.b(this.l, var1);
        kC var2 = jC.d(this.l, var1);
        jC.c(this.l, var1);
        cC.c(this.l);
        bC.d(this.l);
        if (!var1) {
            var2.f();
            var2.g();
            var2.e();
        }
    }

    public void b(String var1) {
        d().a(var1);
    }

    public void b(boolean var1) {
        if (var1) {
            m.l();
        } else {
            m.k();
        }

    }

    /**
     * Shows a Snackbar indicating that a problem occurred while compiling. Clicking the action button won't open a new activity.
     *
     * @param errorId The ID of the error message. Can be 900, 901, 1001, 1002, 1003, or any other value (the others don't get a specific error text).
     */
    public final void c(String errorId) {
        Snackbar snackbar = Snackbar.a(this.n, nq.a(this.getApplicationContext(), errorId), -2 /* BaseTransientBottomBar.LENGTH_INDEFINITE */);
        snackbar.a(xB.b().a(this.getApplicationContext(), 2131625038), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    snackbar.c();
                }
            }
        });
        //REMOVED: Looks bad.
        //snackbar.h().setAlpha(0.8F);
        /* Set the text color to yellow */
        snackbar.f(Color.YELLOW);
        snackbar.n();
    }

    /**
     * Shows a Snackbar indicating that a problem occurred while compiling. The user can click on "SHOW" to get to {@link CompileLogActivity}.
     *
     * @param error The error, to be later displayed as text in {@link CompileLogActivity}
     */
    public final void d(String error) {
        new CompileLogSaver(q.b).setLastLog(error);
        Snackbar snackbar = Snackbar.a(this.n, "Show compile log", -2 /* BaseTransientBottomBar.LENGTH_INDEFINITE */);
        snackbar.a(xB.b().a(this.getApplicationContext(), 2131625038), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    snackbar.c();
                    Intent intent = new Intent(getApplicationContext(), CompileLogActivity.class);
                    intent.putExtra("error", error);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        });
        //REMOVED: Looks bad.
        //snackbar.h().setAlpha(0.8F);
        /* Set the text color to yellow */
        snackbar.f(Color.YELLOW);
        snackbar.n();
    }

    public void e(int var1) {
        if (var1 == 188) {
            (new DesignActivity.a(this.getApplicationContext())).execute();
        }

    }

    public void finish() {
        jC.a();
        cC.a();
        bC.a();
        setResult(0, this.getIntent());
        super.finish();
    }

    public final void l() {
        boolean var1 = jC.c(this.l).g();
        boolean var2 = jC.b(this.l).g();
        boolean var3 = jC.d(this.l).q();
        boolean var4 = jC.a(this.l).d();
        boolean var5 = jC.a(this.l).c();
        if (var1 || var2 || var3 || var4 || var5) {
            s();
        }

    }

    public final void m() {
        if (this.G != null) {
            try {
                Builder var1 = new Builder();
                AdRequest var3 = var1.build();
                G.loadAd(var3);
            } catch (Exception ignored) {
            }
        }
    }

    public void n() {
        q.b(jC.b(this.l), jC.a(this.l), jC.c(this.l));
    }

    /**
     * Opens the debug APK to install.
     */
    public final void o() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(this.q.H));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(this.q.H)), "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!super.j.h() && (requestCode == 208 || requestCode == 209 || requestCode == 217 || requestCode == 226 || requestCode == 228 || requestCode == 233 || requestCode == 240 || requestCode == 223 || requestCode == 224) && !super.j.h()) {
            xo.k();
        }

        if (requestCode != 188) {
            if (requestCode != 217) {
                ProjectFileSelector var7;
                if (requestCode != 226) {
                    if (requestCode != 228) {
                        if (requestCode != 233) {
                            if (requestCode != 263) {
                                if (requestCode != 462) {
                                    jr var6;
                                    if (requestCode != 208) {
                                        if (requestCode != 209) {
                                            if (requestCode != 223) {
                                                if (requestCode == 224 && resultCode == -1) {
                                                    br var4 = this.y;
                                                    if (var4 != null) {
                                                        var4.d();
                                                    }
                                                }
                                            } else if (resultCode == -1) {
                                                rs var5 = this.x;
                                                if (var5 != null) {
                                                    var5.f();
                                                }
                                            }
                                        } else if (resultCode == -1) {
                                            var6 = this.w;
                                            if (var6 != null) {
                                                var6.i();
                                            }
                                        }
                                    } else if (resultCode == -1) {
                                        var7 = this.v;
                                        if (var7 != null) {
                                            var7.a();
                                        }

                                        var6 = this.w;
                                        if (var6 != null) {
                                            var6.n();
                                        }
                                    }
                                } else if (resultCode == -1 && data.getBooleanExtra("req_update_design_activity", false)) {
                                    w.j();
                                }
                            } else if (resultCode == -1) {
                                ProjectFileBean var8 = data.getParcelableExtra("project_file");
                                v.setXmlFileName(var8);
                            }
                        } else if (resultCode == -1) {
                            w.j();
                        }
                    }
                } else if (resultCode == -1) {
                    var7 = this.v;
                    if (var7 != null) {
                        var7.a();
                    }
                }
            }
        } else {
            (new DesignActivity.a(this.getApplicationContext())).execute();
        }

    }

    public void onBackPressed() {
        if (this.o.f(8388613)) {
            o.a(8388613);
        } else if (this.w.g()) {
            w.a(false);
        } else {
            int var1 = this.E;
            if (var1 > 0) {
                E = var1 - 1;
                m.setCurrentItem(this.E);
            } else if (this.t.c("P12I2")) {
                k();
                if (!super.j.h()) {
                    xo.a(this.I);
                    xo.a(this.getApplicationContext(), 242);
                } else {
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new e(getApplicationContext()).execute();
                        }
                    }, 500L);
                }

            } else {
                q();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (v.getId() == 2131230824) {
                (new DesignActivity.a(this.getApplicationContext())).execute();
            } else if (v.getId() == 2131232628) {
                (new BuildSettingsDialog(this, this.l)).show();
            }
        }

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (this.F) {
                H.setVisibility(View.VISIBLE);
                p.setVisibility(View.VISIBLE);
            } else {
                H.setVisibility(View.GONE);
                p.setVisibility(View.GONE);
            }
        } else {
            H.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427393);
        if (!super.j()) {
            finish();
        }

        if (savedInstanceState == null) {
            l = this.getIntent().getStringExtra("sc_id");
        } else {
            l = savedInstanceState.getString("sc_id");
        }

        r = new DB(this.getApplicationContext(), "P1");
        s = new DB(this.getApplicationContext(), "P2");
        t = new DB(this.getApplicationContext(), "P12");
        z = new oB();
        k = this.findViewById(2131231847);
        a(this.k);
        findViewById(2131231370).setVisibility(View.GONE);
        d().d(true);
        d().e(true);
        k.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        k.setPopupTheme(2131689886);
        getSupportFragmentManager().a(new Xf.c() {
            @Override
            public void onBackStackChanged() {
            }
        });
        p = this.findViewById(2131231310);
        o = this.findViewById(2131230981);
        o.setDrawerLockMode(1);
        n = this.findViewById(2131231335);
        u = this.findViewById(2131230824);
        u.setText(xB.b().a(this, 2131625030));
        u.setOnClickListener(this);
        findViewById(2131232628).setOnClickListener(this);
        A = this.findViewById(2131231164);
        C = this.findViewById(2131231319);
        v = this.findViewById(2131231060);
        v.setScId(this.l);
        v.setOnSelectedFileChangeListener(new by() {
            @Override
            public void a(int i, ProjectFileBean projectFileBean) {
                if (i == 0) {
                    if (w != null && projectFileBean != null) {
                        int i2 = projectFileBean.orientation;
                        if (i2 == 0) {
                            A.setImageResource(2131165846);
                        } else if (i2 == 1) {
                            A.setImageResource(2131165845);
                        } else {
                            A.setImageResource(2131165847);
                        }
                        w.a(projectFileBean);
                    }
                } else if (i == 1) {
                    rs rsVar = x;
                    if (rsVar != null) {
                        if (projectFileBean != null) {
                            rsVar.a(projectFileBean);
                            x.f();
                        } else {
                            return;
                        }
                    }
                    br brVar = y;
                    if (brVar != null && projectFileBean != null) {
                        brVar.a(projectFileBean);
                        y.d();
                    }
                }
            }
        });
        m = this.findViewById(2131232328);
        m.setAdapter(new DesignActivity.g(this.getSupportFragmentManager(), this));
        m.setOffscreenPageLimit(3);
        m.a(new ViewPager.e() {

            @Override
            public void a(int i) {
            }

            @Override
            public void a(int i, float v, int i1) {
            }

            @Override
            public void b(int i) {
                br brVar;
                if (E == 1) {
                    rs rsVar = x;
                    if (rsVar != null) {
                        rsVar.c();
                    }
                } else if (E == 2 && (brVar = y) != null) {
                    brVar.c();
                }
                if (i == 0) {
                    jr jrVar = w;
                    if (jrVar != null) {
                        jrVar.c(true);
                        A.setVisibility(View.VISIBLE);
                        v.setFileType(0);
                        v.a();
                    }
                } else if (i == 1) {
                    if (w != null) {
                        A.setVisibility(View.GONE);
                        w.c(false);
                        v.setFileType(1);
                        v.a();
                        rs rsVar2 = x;
                        if (rsVar2 != null) {
                            rsVar2.f();
                        }
                    }
                } else {
                    jr jrVar2 = w;
                    if (jrVar2 != null) {
                        jrVar2.c(false);
                        A.setVisibility(View.GONE);
                        v.setFileType(1);
                        v.a();
                        br brVar2 = y;
                        if (brVar2 != null) {
                            brVar2.d();
                        }
                    }
                }
                E = i;
            }
        });
        I = new to() {
            @Override
            public void d(int i) {
                Log.d("later_ads", "on later ads load completed is called");
                if (i == 242) {
                    new e(getApplicationContext()).execute();
                } else if (i == 243) {
                    new c(getApplicationContext()).execute();
                }
            }
        };
        H = this.findViewById(2131232313);
        m.getAdapter().b();
        ((TabLayout) this.findViewById(2131231781)).setupWithViewPager(this.m);
        if (!super.j.h()) {
            xo.a((uo) this);
            xo.c(this.getApplicationContext());
            p();
        } else {
            H.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492866, menu);
        return true;
    }

    public void onDestroy() {
        AdView var1 = this.G;
        if (var1 != null) {
            try {
                var1.destroy();
            } catch (Exception ignored) {
            }
        }

        xo.j();
        xo.i();
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int var2 = item.getItemId();
        if (var2 != 2131230965) {
            if (var2 == 2131230971) {
                k();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new d(getApplicationContext()).execute();
                    }
                }, 500L);
            }
        } else if (!this.o.f(8388613)) {
            o.h(8388613);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPause() {
        AdView var1 = this.G;
        if (var1 != null) {
            try {
                var1.pause();
            } catch (Exception ignored) {
            }
        }

        super.onPause();
    }

    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        k();
        HashMap<String, Object> var2 = lC.b(this.l);
        D = yB.b(var2, "sketchware_ver");
        b(yB.c(var2, "my_ws_name"));
        String var3 = wq.d(this.l);
        q = new yq(this.getApplicationContext(), var3, var2);
        m();

        try {
            Handler var6 = new Handler();
            var6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new b(getBaseContext(), savedInstanceState).execute();
                }
            }, 500L);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }

        AdView var1 = this.G;
        if (var1 != null) {
            try {
                var1.resume();
            } catch (Exception ignored) {
            }
        }

        super.d.setScreenName(DesignActivity.class.getSimpleName());
        super.d.send((new ScreenViewBuilder()).build());
        long var2 = GB.c();
        if (var2 < 100L && var2 > 0L) {
            r();
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", this.l);
        v.b(outState);
        super.onSaveInstanceState(outState);
        if (!super.j()) {
            finish();
        }

        DesignActivity.f var2 = this.J;
        if (var2 != null && !var2.isCancelled()) {
            J.cancel(true);
        }

        if (!this.B) {
            J = new DesignActivity.f(this.getApplicationContext());
            J.execute();
        }
    }

    public final void p() {
        p.removeAllViews();
        G = new AdView(this);
        G.setAdListener(new AdListener() {
            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(int i) {
                p.setVisibility(View.GONE);
                F = false;
                jr jrVar = w;
                if (jrVar != null) {
                    jrVar.b(false);
                }
                super.onAdFailedToLoad(i);
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                F = true;
                super.onAdLoaded();
            }
        });
        LayoutParams var1 = new LayoutParams(-1, -2);
        G.setLayoutParams(var1);
        p.addView(this.G);
        G.setAdSize(AdSize.BANNER);
        G.setAdUnitId("ca-app-pub-7978947291427601/3558354213");
    }

    public final void q() {
        aB var1 = new aB(this);
        var1.b(xB.b().a(this.getApplicationContext(), 2131625311));
        var1.a(2131165604);
        var1.a(xB.b().a(this.getApplicationContext(), 2131625310));
        var1.b(xB.b().a(this.getApplicationContext(), 2131625309), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    var1.dismiss();
                    try {
                        k();
                        if (!j.h()) {
                            xo.a(I);
                            xo.a(getApplicationContext(), 242);
                            return;
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new e(getApplicationContext()).execute();
                            }
                        }, 500);
                    } catch (Exception e) {
                        e.printStackTrace();
                        h();
                    }
                }
            }
        });
        var1.a(xB.b().a(this.getApplicationContext(), 2131624996), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    var1.dismiss();
                    try {
                        k();
                        if (!j.h()) {
                            xo.a(I);
                            xo.a(getApplicationContext(), 243);
                            return;
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                new c(getApplicationContext()).execute();
                            }
                        }, 500);
                    } catch (Exception e) {
                        e.printStackTrace();
                        h();
                    }
                }
            }
        });
        var1.show();
    }

    public final void r() {
        aB var1 = new aB(this);
        var1.b(xB.b().a(this.getApplicationContext(), 2131625047));
        var1.a(2131165391);
        var1.a(xB.b().a(this.getApplicationContext(), 2131624947));
        var1.b(xB.b().a(this.getApplicationContext(), 2131625010), new OnClickListener() {
            @Override
            public void onClick(View v) {
                var1.dismiss();
            }
        });
        var1.show();
    }

    public final void s() {
        B = true;
        aB var1 = new aB(this);
        var1.a(2131165516);
        var1.b(xB.b().a(this.getApplicationContext(), 2131625313));
        var1.a(xB.b().a(this.getApplicationContext(), 2131625312));
        var1.b(xB.b().a(this.getApplicationContext(), 2131625028), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    boolean g = jC.c(l).g();
                    boolean g2 = jC.b(l).g();
                    boolean q = jC.d(l).q();
                    boolean d = jC.a(l).d();
                    boolean c = jC.a(l).c();
                    if (g) {
                        jC.c(l).h();
                    }
                    if (g2) {
                        jC.b(l).h();
                    }
                    if (q) {
                        jC.d(l).r();
                    }
                    if (d) {
                        jC.a(l).h();
                    }
                    if (c) {
                        jC.a(l).f();
                    }
                    if (g) {
                        jC.b(l).a(jC.c(l));
                        jC.a(l).a(jC.c(l).d());
                    }
                    if (g2 || g) {
                        jC.a(l).a(jC.b(l));
                    }
                    if (q) {
                        jC.a(l).b(jC.d(l));
                        jC.a(l).c(jC.d(l));
                        jC.a(l).a(jC.d(l));
                    }
                    DesignActivity.this.v.a();
                    B = false;
                    var1.dismiss();
                }
            }
        });
        var1.a(xB.b().a(this.getApplicationContext(), 2131625009), new OnClickListener() {
            @Override
            public void onClick(View v) {
                B = false;
                var1.dismiss();
            }
        });
        var1.setCancelable(false);
        var1.show();
    }

    public void t() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageCollectionActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 233);
    }

    public void toAndroidManifest() {
        Intent var1 = new Intent(this.getApplicationContext(), AndroidManifestInjection.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        var1.putExtra("file_name", this.v.g);
        startActivity(var1);
    }

    public void toAppCompatInjection() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageCustomAttributeActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        var1.putExtra("file_name", this.v.f);
        startActivity(var1);
    }

    public void toAssets() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageAssetsActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toBroadcast() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageBackgroundActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        var1.putExtra("pkgName", this.q.e);
        startActivity(var1);
    }

    public void toCustomBlocks() {
        CustomBlocksDialog.show(this, this.l);
    }

    public void toExportApk() {
        Intent var1 = new Intent(this.getApplicationContext(), ExportApkActivity.class);
        var1.putExtra("scId", this.l);
        startActivity(var1);
    }

    public void toJava() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageJavaActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        var1.putExtra("pkgName", this.q.e);
        startActivity(var1);
    }

    public void toLocalLibrary() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageLocalLibraryActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toNativelibs() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageNativelibsActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toPermission() {
        Intent var1 = new Intent(this.getApplicationContext(), ManagePermissionActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toProguard() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageProguardActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toResource() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageResourceActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void toStringfog() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageStringfogActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivity(var1);
    }

    public void u() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageFontActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 228);
    }

    public void v() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageImageActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 209);
    }

    public void w() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageLibraryActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 226);
    }

    public void x() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageViewActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 208);
    }

    public void y() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageSoundActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        startActivityForResult(var1, 217);
    }

    public void z() {
        Intent var1 = new Intent(this.getApplicationContext(), SrcViewerActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        boolean var10001;
        if (this.m.getCurrentItem() == 0) {
            try {
                var1.putExtra("current", this.w.d().getXmlName());
            } catch (Exception var3) {
                var10001 = false;
            }
        } else if (this.m.getCurrentItem() != 1) {
            var1.putExtra("current", "");
        } else {
            try {
                var1.putExtra("current", this.x.d().getJavaName());
            } catch (Exception var4) {
                var10001 = false;
            }
        }

        startActivityForResult(var1, 240);
    }

    public void zz() {
        Intent var1 = new Intent(this.getApplicationContext(), DirectEditorActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.putExtra("sc_id", this.l);
        boolean var10001;
        if (this.m.getCurrentItem() == 0) {
            try {
                var1.putExtra("current", this.w.d().getXmlName());
            } catch (Exception var3) {
                var10001 = false;
            }
        } else if (this.m.getCurrentItem() != 1) {
            var1.putExtra("current", "");
        } else {
            try {
                var1.putExtra("current", this.x.d().getJavaName());
            } catch (Exception var4) {
                var10001 = false;
            }
        }

        startActivityForResult(var1, 240);
    }

    public class a extends MA implements OnCancelListener {

        /**
         * The actual BuildingDialog class
         */
        public Ep c;
        /**
         * Boolean indicating if building got cancelled and we should stop continuing
         */
        public boolean d = false;

        public a(Context context) {
            super(context);
            DesignActivity.this.a((MA) this);
            c = new Ep(DesignActivity.this);
            d();
            c.a(false);
        }

        @Override
        public void a() {
            DesignActivity.this.q.b();
            c();
            DesignActivity.this.u.setText(xB.b().a(DesignActivity.this.getApplicationContext(), 2131625030));
            DesignActivity.this.u.setClickable(true);
            DesignActivity.this.getWindow().clearFlags(128);
        }

        /**
         * Shows a Toast about APK build having failed, closes the dialog,
         * reverts u (the "Run"-Button) and clears the FLAG_KEEP_SCREEN_ON flag.
         *
         * @param str Ignored parameter, for some reason
         */
        @Override
        public void a(String str) {
            DesignActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    q.b();
                    c();
                    bB.b(getApplicationContext(), "APK build failed", Toast.LENGTH_SHORT).show();
                    u.setText(xB.b().a(getApplicationContext(), 2131625030));
                    u.setClickable(true);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            c(values[0]);
        }

        @Override
        public void b() {
            if (this.d) {
                cancel(true);
            } else {
                try {
                    DesignActivity.this.q.c();
                    DesignActivity.this.q.c(super.a);
                    DesignActivity.this.q.a();
                    DesignActivity.this.q.a(super.a, wq.e("600"));
                    if (yB.a(lC.b(DesignActivity.this.l), "custom_icon")) {
                        yq var8 = DesignActivity.this.q;
                        String var2 = wq.e() +
                                File.separator +
                                DesignActivity.this.l +
                                File.separator +
                                "icon.png";
                        var8.a(var2);
                    }

                    kC kC = jC.d(DesignActivity.this.l);
                    StringBuilder sb = new StringBuilder();
                    sb.append(DesignActivity.this.q.w);
                    sb.append(File.separator);
                    sb.append("drawable-xhdpi");
                    kC.b(sb.toString());
                    kC = jC.d(DesignActivity.this.l);
                    sb = new StringBuilder();
                    sb.append(DesignActivity.this.q.w);
                    sb.append(File.separator);
                    sb.append("raw");
                    kC.c(sb.toString());
                    kC = jC.d(DesignActivity.this.l);
                    sb = new StringBuilder();
                    sb.append(DesignActivity.this.q.A);
                    sb.append(File.separator);
                    sb.append("fonts");
                    kC.a(sb.toString());
                    DesignActivity.this.n();
                    DesignActivity.this.q.f();
                    DesignActivity.this.q.e();
                    Dp mDp = new Dp(this, super.a, DesignActivity.this.q);
                    mDp.e = DesignActivity.this;
                    publishProgress(xB.b().a(super.a, 2131625316));
                    mDp.i();
                    mDp.j();
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    boolean usingAapt2 = new BuildSettings(q.b)
                            .getValue(BuildSettings.SETTING_RESOURCE_PROCESSOR,
                                    BuildSettings.SETTING_RESOURCE_PROCESSOR_AAPT
                            ).equals(BuildSettings.SETTING_RESOURCE_PROCESSOR_AAPT2);
                    publishProgress(usingAapt2 ? "AAPT2 is running..." : "AAPT is running...");
                    mDp.a();
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    publishProgress("Java is compiling...");
                    mDp.f();
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    StringfogHandler stringfogHandler = new StringfogHandler(DesignActivity.this.q.b);
                    stringfogHandler.start(this, mDp);
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    ProguardHandler proguardHandler = new ProguardHandler(DesignActivity.this.q.b);
                    proguardHandler.start(this, mDp);
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    publishProgress(mDp.getDxRunningText());
                    mDp.c();
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    publishProgress("Merging libraries' DEX files...");
                    mDp.h();
                    if (this.d) {
                        cancel(true);
                        return;
                    }

                    boolean buildingAAB = new BuildSettings(q.b)
                            .getValue(BuildSettings.SETTING_OUTPUT_FORMAT,
                                    BuildSettings.SETTING_OUTPUT_FORMAT_APK)
                            .equals(BuildSettings.SETTING_OUTPUT_FORMAT_AAB);
                    if (buildingAAB) {
                        BundleToolCompiler compiler = new BundleToolCompiler(mDp, this);
                        publishProgress("Creating app module...");
                        compiler.copyFilesToMainModuleDirectory();
                        publishProgress("Compressing app module...");
                        compiler.zipMainModule();
                        publishProgress("Building app bundle...");
                        compiler.buildBundle();
                        publishProgress("Building APK Set...");
                        compiler.buildApkSet();
                        publishProgress("Extracting Install-APK from APK Set...");
                        compiler.extractInstallApkFromApkSet();
                        publishProgress("Signing Install-APK...");
                        compiler.signInstallApk();
                        o();
                    } else {
                        publishProgress("Building APK...");
                        mDp.g();
                        if (this.d) {
                            cancel(true);
                            return;
                        }

                        publishProgress("Signing APK...");
                        mDp.k();
                        if (this.d) {
                            cancel(true);
                            return;
                        }

                        DesignActivity.this.o();
                    }
                } catch (Ay e) {
                    //Never thrown? Haven't found a reference to it in any classes except {@link DesignActivity} and {@link PublishActivity} (and of course {@link Ay})
                    Log.e("DesignActivity$a", Log.getStackTraceString(e), e);
                    //This seems kinda odd
                    c(e.getMessage());
                } catch (OutOfMemoryError error) {
                    Log.e("DesignActivity$a", "OutOfMemoryError: " + error.getMessage(), error);
                    System.gc();
                    DesignActivity.this.d(error.getMessage());
                    //throw new By(xB.b().a(super.a, 2131624953));
                } catch (Throwable e) {
                    Log.e("DesignActivity$a", Log.getStackTraceString(e), e);
                    DesignActivity.this.d(e.getMessage());
                }
            }
        }

        /**
         * Dismiss this dialog, if DesignActivity hasn't been destroyed.
         */
        public void c() {
            if (!DesignActivity.this.isDestroyed()) {
                if (this.c.isShowing()) {
                    c.dismiss();
                }
            }
        }

        /**
         * Updates the dialog's progress text.
         *
         * @param progressText The new text to display as progress
         */
        public void c(String progressText) {
            DesignActivity.this.runOnUiThread(new DesignActRunnable(this.c, progressText));
        }

        /**
         * Try to set this dialog's OnCancelListener as this, then show, unless already showing.
         */
        public void d() {
            if (!this.c.isShowing()) {
                c.setOnCancelListener(this);
                c.show();
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (!this.c.a()) {
                c.a(true);
                d();
                publishProgress("Canceling build...");
                d = true;
            }
        }

        @Override
        public void onCancelled() {
            super.onCancelled();
            DesignActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    u.setText(xB.b().a(getApplicationContext(), 2131625030));
                    u.setClickable(true);
                    getWindow().clearFlags(128);
                }
            });
            DesignActivity.this.q.b();
            c();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            DesignActivity.this.u.setText("Building APK file...");
            DesignActivity.this.u.setClickable(false);
            DesignActivity.this.r.a("P1I10", true);
            DesignActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public class b extends MA {

        public Bundle c;

        public b(Context var2, Bundle var3) {
            super(var2);
            DesignActivity.this.a(this);
            c = var3;
        }

        public void a() {
            Bundle var1 = this.c;
            if (var1 != null) {
                DesignActivity.this.v.a(var1);
                if (this.c.getInt("file_selector_current_file_type") == 0) {
                    DesignActivity.this.A.setVisibility(View.VISIBLE);
                } else {
                    DesignActivity.this.A.setVisibility(View.GONE);
                }
            }

            DesignActivity.this.v.a();
            DesignActivity.this.h();
            if (this.c == null) {
                DesignActivity.this.l();
            }

        }

        public void a(String var1) {
            DesignActivity.this.h();
        }

        public void b() {
            DesignActivity.this.a(this.c != null);

        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class c extends MA {

        public c(Context var2) {
            super(var2);
            DesignActivity.this.a(this);
        }

        public void a() {
            DesignActivity.this.h();
            DesignActivity.this.finish();
        }

        public void a(String var1) {
            DesignActivity.this.h();
            DesignActivity.this.finish();
        }

        public void b() {
            publishProgress("Now processing..");
            jC.d(DesignActivity.this.l).v();
            jC.d(DesignActivity.this.l).w();
            jC.d(DesignActivity.this.l).u();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class d extends MA {

        public d(Context var2) {
            super(var2);
            DesignActivity.this.a(this);
        }

        public void a() {
            bB.a(super.a, xB.b().a(super.a, 2131624938), 0).show();
            DesignActivity.this.A();
            DesignActivity.this.h();
            jC.d(DesignActivity.this.l).f();
            jC.d(DesignActivity.this.l).g();
            jC.d(DesignActivity.this.l).e();
        }

        public void a(String var1) {
            bB.b(super.a, xB.b().a(super.a, 2131624915), 0).show();
            DesignActivity.this.h();
        }

        public void b() {
            publishProgress("Now saving..");
            jC.d(DesignActivity.this.l).a();
            jC.b(DesignActivity.this.l).m();
            jC.a(DesignActivity.this.l).j();
            jC.d(DesignActivity.this.l).x();
            jC.c(DesignActivity.this.l).l();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class e extends MA {

        public e(Context var2) {
            super(var2);
            DesignActivity.this.a(this);
        }

        public void a() {
            bB.a(super.a, xB.b().a(super.a, 2131624938), 0).show();
            DesignActivity.this.A();
            DesignActivity.this.h();
            DesignActivity.this.finish();
        }

        public void a(String var1) {
            bB.b(super.a, xB.b().a(super.a, 2131624915), 0).show();
            DesignActivity.this.h();
        }

        public void b() {
            publishProgress("Now saving..");
            jC.d(DesignActivity.this.l).a();
            jC.b(DesignActivity.this.l).m();
            jC.a(DesignActivity.this.l).j();
            jC.d(DesignActivity.this.l).x();
            jC.c(DesignActivity.this.l).l();
            jC.d(DesignActivity.this.l).h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class f extends MA {

        public f(Context var2) {
            super(var2);
            DesignActivity.this.a(this);
        }

        public void a() {
        }

        public void a(String var1) {
        }

        public void b() {
            jC.a(DesignActivity.this.l).k();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }

        public void onCancelled() {
            super.onCancelled();
        }
    }

    public class g extends gg {

        public final int f = 3;
        public String[] g;
        public Context h;

        public g(Xf var2, Context var3) {
            super(var2);
            h = var3;
            g = new String[3];
            g[0] = xB.b().a(var3, 2131625319);
            g[1] = xB.b().a(var3, 2131625318);
            g[2] = xB.b().a(var3, 2131625317);
        }

        public int a() {
            return 3;
        }

        public CharSequence a(int var1) {
            return this.g[var1];
        }

        public Object a(ViewGroup var1, int var2) {
            Fragment var3 = (Fragment) super.a(var1, var2);
            if (var2 == 0) {
                DesignActivity.this.w = (jr) var3;
            } else if (var2 == 1) {
                DesignActivity.this.x = (rs) var3;
            } else {
                DesignActivity.this.y = (br) var3;
            }

            return var3;
        }

        public Fragment c(int var1) {
            if (var1 == 0) {
                return new jr();
            } else {
                return (Fragment) (var1 == 1 ? new rs() : new br());
            }
        }
    }
}
