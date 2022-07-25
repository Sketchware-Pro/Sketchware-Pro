package com.besome.sketch.editor.manage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.beans.WidgetCollectionBean;
import com.besome.sketch.editor.manage.font.AddFontCollectionActivity;
import com.besome.sketch.editor.manage.image.AddImageCollectionActivity;
import com.besome.sketch.editor.manage.sound.AddSoundCollectionActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import a.a.a.Bi;
import a.a.a.FB;
import a.a.a.Mp;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Pp;
import a.a.a.Qp;
import a.a.a.Rp;
import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.Ze;
import a.a.a.bB;
import a.a.a.ef;
import a.a.a.kq;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xo;

public class ManageCollectionActivity extends BaseAppCompatActivity implements OnClickListener {

    public Timer A = new Timer();
    public TimerTask B;
    public MediaPlayer C;
    public int D = -1;
    public int E = -1;
    public TextView F = null;
    public ProgressBar G = null;
    public LinearLayout H;
    public Button I;
    public Button J;
    public boolean K = false;
    public boolean k = false;
    public Toolbar l;
    public ManageCollectionActivity.a m;
    public ManageCollectionActivity.b n;
    public RecyclerView o;
    public RecyclerView p;
    public ArrayList<ProjectResourceBean> q;
    public ArrayList<ProjectResourceBean> r;
    public ArrayList<ProjectResourceBean> s;
    public ArrayList<WidgetCollectionBean> t;
    public ArrayList<BlockCollectionBean> u;
    public ArrayList<MoreBlockCollectionBean> v;
    public TextView w;
    public FloatingActionButton x;
    public String y;
    public String z = "";

    public ManageCollectionActivity() {
    }

    public static String a(Context var0, int var1) {
        String var2;
        if (var1 == 0) {
            var2 = xB.b().a(var0, 2131625001);
        } else if (var1 == 1) {
            var2 = xB.b().a(var0, 2131625039);
        } else if (var1 == 2) {
            var2 = xB.b().a(var0, 2131624998);
        } else if (var1 == 3) {
            var2 = xB.b().a(var0, 2131625049);
        } else if (var1 == 4) {
            var2 = xB.b().a(var0, 2131624973);
        } else {
            var2 = xB.b().a(var0, 2131625007);
        }

        return var2;
    }

    public static int g(int var0) {
        if (var0 == 0) {
            var0 = 2131165811;
        } else if (var0 == 1) {
            var0 = 2131165861;
        } else if (var0 == 2) {
            var0 = 2131165755;
        } else if (var0 == 3) {
            var0 = 2131165450;
        } else if (var0 == 4) {
            var0 = 2131165374;
        } else {
            var0 = 2131165968;
        }

        return var0;
    }

    public void A() {
        ArrayList var1 = this.r;
        this.a(var1);
        Intent var2 = new Intent(this.getApplicationContext(), AddSoundCollectionActivity.class);
        var2.putParcelableArrayListExtra("sounds", var1);
        var2.putExtra("sc_id", this.y);
        this.startActivityForResult(var2, 269);
    }

    public int a(BlockBean var1) {
        if (var1.type.equals("c")) {
            return 2131165615;
        } else if (var1.type.equals("b")) {
            return 2131165614;
        } else if (var1.type.equals("f")) {
            return 2131165618;
        } else if (var1.type.equals("e")) {
            return 2131165617;
        } else if (var1.type.equals("d")) {
            return 2131165619;
        } else {
            return var1.type.equals("s") ? 2131165620 : 2131165616;
        }
    }

    public void a(String var1, ImageView var2) {
        MediaMetadataRetriever var3 = new MediaMetadataRetriever();
        var3.setDataSource(var1);
        if (var3.getEmbeddedPicture() != null) {
            byte[] var4 = var3.getEmbeddedPicture();
            Glide.with(this.getApplicationContext()).load(var4).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    var2.setImageDrawable(glideDrawable);
                }
            });
        } else {
            var2.setImageResource(2131165520);
            var2.setBackgroundResource(2131165346);
        }

        var3.release();
    }

    public void a(ArrayList<ProjectResourceBean> var1) {
        this.A.cancel();
        int var2 = this.E;
        if (var2 != -1) {
            ((ProjectResourceBean) var1.get(var2)).curSoundPosition = 0;
            this.E = -1;
            this.D = -1;
            this.n.c();
        }

        MediaPlayer var3 = this.C;
        if (var3 != null && var3.isPlaying()) {
            this.C.pause();
        }
    }

    public final void a(ArrayList<? extends SelectableBean> var1, int var2) {
        MediaPlayer var3;
        if (this.E == var2) {
            var3 = this.C;
            if (var3 != null) {
                if (var3.isPlaying()) {
                    this.A.cancel();
                    this.C.pause();
                    ((ProjectResourceBean) var1.get(this.E)).curSoundPosition = this.C.getCurrentPosition();
                    this.n.c(this.E);
                    return;
                }

                this.C.start();
                this.n(var2);
                this.n.c();
            }
        } else {
            var3 = this.C;
            if (var3 != null && var3.isPlaying()) {
                this.A.cancel();
                this.C.pause();
                this.C.release();
            }

            int var4 = this.D;
            if (var4 != -1) {
                ((ProjectResourceBean) var1.get(var4)).curSoundPosition = 0;
                this.n.c(this.D);
            }

            this.E = var2;
            this.D = var2;
            this.n.c(this.E);
            this.C = new MediaPlayer();
            this.C.setAudioStreamType(3);
            this.C.setOnPreparedListener(mp -> {
                C.start();
                n(var2);
                n.c(E);
            });
            this.C.setOnCompletionListener(mp -> {
                A.cancel();
                ((ProjectResourceBean) var1.get(E)).curSoundPosition = 0;
                n.c(E);
                E = -1;
                D = -1;
            });

            try {
                StringBuilder var7 = new StringBuilder();
                var7.append(wq.a());
                var7.append(File.separator);
                var7.append("sound");
                var7.append(File.separator);
                var7.append("data");
                var7.append(File.separator);
                var7.append(((ProjectResourceBean) var1.get(this.E)).resFullName);
                String var6 = var7.toString();
                this.C.setDataSource(var6);
                this.C.prepare();
            } catch (Exception var5) {
                this.E = -1;
                this.n.c(this.E);
                var5.printStackTrace();
            }
        }
    }

    public final void a(boolean var1) {
        this.k = var1;
        this.invalidateOptionsMenu();
        this.x();
        if (this.k) {
            this.a(this.r);
            this.H.setVisibility(0);
        } else {
            this.H.setVisibility(8);
            int var2 = this.m.c;
            if (var2 == 3 || var2 == 4) {
                this.x.setVisibility(8);
            }
        }

        this.n.c();
    }

    public final int b(String var1) {
        MediaMetadataRetriever var2 = new MediaMetadataRetriever();
        var2.setDataSource(var1);
        return (int) Long.parseLong(var2.extractMetadata(9));
    }

    public void f(int var1) {
        if (var1 == 0) {
            this.z();
        } else if (var1 == 1) {
            this.A();
        } else {
            this.y();
        }
    }

    public void h(int var1) {
        String var2 = (String) Mp.h().g().get(var1);
        Intent var3 = new Intent(this.getApplicationContext(), ShowBlockCollectionActivity.class);
        var3.putExtra("block_name", var2);
        this.startActivityForResult(var3, 274);
    }

    public void i(int var1) {
        ArrayList var2 = this.s;
        ProjectResourceBean var3 = (ProjectResourceBean) var2.get(var1);
        Intent var4 = new Intent(this.getApplicationContext(), AddFontCollectionActivity.class);
        var4.putParcelableArrayListExtra("fonts", var2);
        var4.putExtra("sc_id", this.y);
        var4.putExtra("edit_target", var3);
        this.startActivityForResult(var4, 272);
    }

    public void j(int var1) {
        ArrayList var2 = this.q;
        ProjectResourceBean var3 = (ProjectResourceBean) var2.get(var1);
        Intent var4 = new Intent(this.getApplicationContext(), AddImageCollectionActivity.class);
        var4.putParcelableArrayListExtra("images", var2);
        var4.putExtra("sc_id", this.y);
        var4.putExtra("edit_target", var3);
        this.startActivityForResult(var4, 268);
    }

    public void k(int var1) {
        String var2 = (String) Pp.h().g().get(var1);
        Intent var3 = new Intent(this.getApplicationContext(), ShowMoreBlockCollectionActivity.class);
        var3.putExtra("block_name", var2);
        this.startActivityForResult(var3, 279);
    }

    public void l() {
        for (int var1 = 0; var1 < this.m.a(); ++var1) {
            Iterator var2;
            if (var1 == 0) {
                var2 = this.q.iterator();

                while (var2.hasNext()) {
                    ProjectResourceBean var8 = (ProjectResourceBean) var2.next();
                    if (var8.isSelected) {
                        Op.g().a(var8.resName, false);
                    }
                }

                Op.g().e();
                this.t();
            } else {
                ProjectResourceBean var4;
                Iterator var7;
                if (var1 == 1) {
                    var7 = this.r.iterator();

                    while (var7.hasNext()) {
                        var4 = (ProjectResourceBean) var7.next();
                        if (var4.isSelected) {
                            Qp.g().a(var4.resName, false);
                        }
                    }

                    Qp.g().e();
                    this.v();
                } else if (var1 == 2) {
                    var7 = this.s.iterator();

                    while (var7.hasNext()) {
                        var4 = (ProjectResourceBean) var7.next();
                        if (var4.isSelected) {
                            Np.g().a(var4.resName, false);
                        }
                    }

                    Np.g().e();
                    this.s();
                } else if (var1 == 3) {
                    var2 = this.t.iterator();

                    while (var2.hasNext()) {
                        WidgetCollectionBean var6 = (WidgetCollectionBean) var2.next();
                        if (var6.isSelected) {
                            if (!this.K) {
                                this.K = true;
                            }

                            Rp.h().a(var6.name, false);
                        }
                    }

                    Rp.h().e();
                    this.w();
                } else if (var1 == 4) {
                    var2 = this.u.iterator();

                    while (var2.hasNext()) {
                        BlockCollectionBean var5 = (BlockCollectionBean) var2.next();
                        if (var5.isSelected) {
                            Mp.h().a(var5.name, false);
                        }
                    }

                    Mp.h().e();
                    this.r();
                } else {
                    var2 = this.v.iterator();

                    while (var2.hasNext()) {
                        MoreBlockCollectionBean var3 = (MoreBlockCollectionBean) var2.next();
                        if (var3.isSelected) {
                            Pp.h().a(var3.name, false);
                        }
                    }

                    Pp.h().e();
                    this.u();
                }
            }
        }

        this.x();
        this.a(false);
        if (this.m() == 0 || this.m() == 1 || this.m() == 2) {
            this.x.f();
        }

        bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624935), 1).show();
        this.n.c();
    }

    public void l(int var1) {
        ArrayList var2 = this.r;
        ProjectResourceBean var3 = (ProjectResourceBean) var2.get(var1);
        this.a(var2);
        Intent var4 = new Intent(this.getApplicationContext(), AddSoundCollectionActivity.class);
        var4.putParcelableArrayListExtra("sounds", var2);
        var4.putExtra("sc_id", this.y);
        var4.putExtra("edit_target", var3);
        this.startActivityForResult(var4, 270);
    }

    public final int m() {
        return this.m.c;
    }

    public void m(int var1) {
        String var2 = (String) Rp.h().g().get(var1);
        Intent var3 = new Intent(this.getApplicationContext(), ShowWidgetCollectionActivity.class);
        var3.putExtra("widget_name", var2);
        this.startActivityForResult(var3, 273);
    }

    public final int n() {
        int var1 = (int) ((float) this.getResources().getDisplayMetrics().widthPixels / this.getResources().getDisplayMetrics().density) / 100;
        int var2 = var1;
        if (var1 > 2) {
            var2 = var1 - 1;
        }

        return var2;
    }

    public final void n(int var1) {
        this.A = new Timer();
        this.B = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (C == null) {
                        A.cancel();
                        return;
                    }
                    ManageCollectionActivity.b.e eVar = (ManageCollectionActivity.b.e) p.d(var1);
                    int currentPosition = C.getCurrentPosition() / 1000;
                    eVar.z.setText(String.format("%d:%02d", currentPosition / 60, currentPosition % 60));
                    eVar.A.setProgress(C.getCurrentPosition() / 1000);
                });
            }
        };
        this.A.schedule(this.B, 100L, 100L);
    }

    public final void o() {
        this.l = (Toolbar) this.findViewById(2131231847);
        this.a(this.l);
        this.findViewById(2131231370).setVisibility(8);
        this.d().a(xB.b().a(this.getApplicationContext(), 2131625134));
        this.d().e(true);
        this.d().d(true);
        this.l.setNavigationOnClickListener(v -> {
            if (mB.a()) {
                return;
            }
            onBackPressed();
        });
        this.w = (TextView) this.findViewById(2131232061);
        this.w.setText(xB.b().a(this.getApplicationContext(), 2131625334));
        this.o = (RecyclerView) this.findViewById(2131230876);
        this.o.setHasFixedSize(true);
        this.o.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), 1, false));
        ((Bi) this.o.getItemAnimator()).a(false);
        this.m = new ManageCollectionActivity.a(this);
        this.o.setAdapter(this.m);
        this.p = (RecyclerView) this.findViewById(2131230903);
        this.p.setHasFixedSize(true);
        this.n = new ManageCollectionActivity.b(this, this.p);
        this.p.setAdapter(this.n);
        this.x = (FloatingActionButton) this.findViewById(2131231054);
        this.x.setOnClickListener(this);
        this.H = (LinearLayout) this.findViewById(2131231319);
        this.I = (Button) this.findViewById(2131230817);
        this.J = (Button) this.findViewById(2131230810);
        this.I.setText(xB.b().a(this.getApplicationContext(), 2131624986));
        this.J.setText(xB.b().a(this.getApplicationContext(), 2131624974));
        this.I.setOnClickListener(this);
        this.J.setOnClickListener(this);
        if (!super.j.h()) {
            xo.a(this.getApplicationContext());
        }
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        if (var1 != 279) {
            switch (var1) {
                case 267:
                case 268:
                    this.t();
                    break;
                case 269:
                case 270:
                    this.v();
                    break;
                case 271:
                case 272:
                    this.s();
                    break;
                case 273:
                    this.w();
                    break;
                case 274:
                    this.r();
            }
        } else {
            this.u();
        }

        super.onActivityResult(var1, var2, var3);
    }

    public void onBackPressed() {
        if (this.K) {
            this.setResult(-1);
            this.finish();
        }

        super.onBackPressed();
    }

    public void onClick(View var1) {
        int var2 = var1.getId();
        if (var2 != 2131230810) {
            if (var2 != 2131230817) {
                if (var2 == 2131231054) {
                    this.a(false);
                    this.f(this.m.c);
                }
            } else if (this.k) {
                this.l();
            }
        } else if (this.k) {
            this.a(false);
        }
    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        if (this.n.d == 0) {
            ((GridLayoutManager) this.p.getLayoutManager()).d(this.n());
            this.p.requestLayout();
        }
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        if (!super.j()) {
            this.finish();
        }

        this.setContentView(2131427507);
        this.o();
    }

    public boolean onCreateOptionsMenu(Menu var1) {
        this.getMenuInflater().inflate(2131492875, var1);
        if (this.k) {
            var1.findItem(2131231502).setVisible(false);
        } else {
            var1.findItem(2131231502).setVisible(true);
        }

        return true;
    }

    public void onDestroy() {
        this.a(this.r);
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        if (var1.getItemId() == 2131231502) {
            this.a(this.k ^ true);
        }

        return super.onOptionsItemSelected(var1);
    }

    public void onPause() {
        super.onPause();
        MediaPlayer var1 = this.C;
        if (var1 != null && var1.isPlaying()) {
            this.A.cancel();
            this.C.pause();
            ((ProjectResourceBean) this.r.get(this.E)).curSoundPosition = this.C.getCurrentPosition();
        }
    }

    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
        if (var1 == null) {
            this.p();
        } else {
            this.y = var1.getString("sc_id");
        }

        this.q();
    }

    public void onResume() {
        super.onResume();
        if (!super.j()) {
            this.finish();
        }

        super.d.setScreenName(ManageCollectionActivity.class.getSimpleName().toString());
        super.d.send((new ScreenViewBuilder()).build());
        ManageCollectionActivity.b var1 = this.n;
        if (var1 != null) {
            var1.c();
        }
    }

    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.y);
        super.onSaveInstanceState(var1);
    }

    public void p() {
        this.y = this.getIntent().getStringExtra("sc_id");
    }

    public void q() {
        this.q = Op.g().f();
        this.r = Qp.g().f();
        this.s = Np.g().f();
        this.t = Rp.h().f();
        this.u = Mp.h().f();
        this.v = Pp.h().f();
        ManageCollectionActivity.b var1;
        if (this.m.c == -1) {
            var1 = this.n;
            var1.d = 0;
            var1.a(this.q);
            this.p.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), this.n()));
            ManageCollectionActivity.a var2 = this.m;
            var2.c = 0;
            if (var2 != null) {
                var2.c();
            }
        }

        var1 = this.n;
        if (var1 != null) {
            var1.c();
        }
    }

    public void r() {
        this.u = Mp.h().f();
        if (this.m.c == 4) {
            this.n.a(this.u);
            this.n.d = 4;
        }

        this.n.c();
    }

    public void s() {
        this.s = Np.g().f();
        if (this.m.c == 2) {
            this.n.a(this.s);
            this.n.d = 2;
        }

        this.n.c();
    }

    public void t() {
        this.q = Op.g().f();
        if (this.m.c == 0) {
            this.n.a(this.q);
            this.n.d = 0;
        }

        this.n.c();
    }

    public void u() {
        this.v = Pp.h().f();
        if (this.m.c == 5) {
            this.n.a(this.v);
            this.n.d = 5;
        }

        this.n.c();
    }

    public void v() {
        this.r = Qp.g().f();
        if (this.m.c == 1) {
            this.n.a(this.r);
            this.n.d = 1;
        }

        this.n.c();
    }

    public void w() {
        this.t = Rp.h().f();
        if (this.m.c == 3) {
            this.n.a(this.t);
            this.n.d = 3;
        }

        this.n.c();
    }

    public final void x() {
        Iterator var1;
        if (this.m() == 0) {
            for (var1 = this.q.iterator(); var1.hasNext(); ((ProjectResourceBean) var1.next()).isSelected = false) {
            }
        } else if (this.m() == 1) {
            for (var1 = this.r.iterator(); var1.hasNext(); ((ProjectResourceBean) var1.next()).isSelected = false) {
            }
        } else if (this.m() == 2) {
            for (var1 = this.s.iterator(); var1.hasNext(); ((ProjectResourceBean) var1.next()).isSelected = false) {
            }
        } else if (this.m() == 3) {
            for (var1 = this.t.iterator(); var1.hasNext(); ((WidgetCollectionBean) var1.next()).isSelected = false) {
            }
        } else {
            for (var1 = this.u.iterator(); var1.hasNext(); ((BlockCollectionBean) var1.next()).isSelected = false) {
            }
        }
    }

    public void y() {
        Intent var1 = new Intent(this.getApplicationContext(), AddFontCollectionActivity.class);
        var1.putParcelableArrayListExtra("fonts", this.s);
        var1.putExtra("sc_id", this.y);
        this.startActivityForResult(var1, 271);
    }

    public void z() {
        Intent var1 = new Intent(this.getApplicationContext(), AddImageCollectionActivity.class);
        var1.putParcelableArrayListExtra("images", this.q);
        var1.putExtra("sc_id", this.y);
        this.startActivityForResult(var1, 267);
    }

    public class a extends androidx.recyclerview.widget.RecyclerView.a<ManageCollectionActivity.a.a2> {
        public int c;
        public final ManageCollectionActivity d;

        public a(ManageCollectionActivity var1) {
            this.d = var1;
            this.c = -1;
        }

        public int a() {
            return 6;
        }

        public void b(a2 var1, int var2) {
            var1.u.setText(ManageCollectionActivity.a(this.d.getApplicationContext(), var2));
            var1.t.setImageResource(ManageCollectionActivity.g(var2));
            ef var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (this.c == var2) {
                var3 = Ze.a(var1.t);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                var3 = Ze.a(var1.t);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                var1.v.setVisibility(0);
                var4 = new ColorMatrix();
                var4.setSaturation(1.0F);
                var5 = new ColorMatrixColorFilter(var4);
                var1.t.setColorFilter(var5);
            } else {
                var3 = Ze.a(var1.t);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                var3 = Ze.a(var1.t);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                var1.v.setVisibility(8);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
                var1.t.setColorFilter(var5);
            }
        }

        public a2 b(ViewGroup var1, int var2) {
            return new a2(this, LayoutInflater.from(var1.getContext()).inflate(2131427377, var1, false));
        }

        public class a2 extends v implements OnClickListener {
            public ImageView t;
            public TextView u;
            public View v;
            public final ManageCollectionActivity.a w;

            public a2(ManageCollectionActivity.a var1, View var2) {
                super(var2);
                this.w = var1;
                this.t = (ImageView) var2.findViewById(2131231151);
                this.u = (TextView) var2.findViewById(2131232055);
                this.v = var2.findViewById(2131231600);
                var2.setOnClickListener(this);
            }

            public void onClick(View var1) {
                if (!mB.a()) {
                    if (this.j() != -1) {
                        int var2 = this.j();
                        ManageCollectionActivity.a var5 = this.w;
                        int var3 = var5.c;
                        if (var2 != var3) {
                            ManageCollectionActivity var6;
                            if (var3 == 1) {
                                var6 = var5.d;
                                var6.a(var6.r);
                            }

                            var5 = this.w;
                            var5.c(var5.c);
                            this.w.c = this.j();
                            var5 = this.w;
                            var5.c(var5.c);
                            this.w.d.p.removeAllViews();
                            ManageCollectionActivity.b var4 = this.w.d.n;
                            var5 = this.w;
                            var2 = var5.c;
                            var4.d = var2;
                            if (var2 == 0) {
                                var5.d.n.a(this.w.d.q);
                            } else if (var2 == 1) {
                                var5.d.n.a(this.w.d.r);
                            } else if (var2 == 2) {
                                var5.d.n.a(this.w.d.s);
                            } else if (var2 == 3) {
                                var5.d.n.a(this.w.d.t);
                            } else if (var2 == 4) {
                                var5.d.n.a(this.w.d.u);
                            } else {
                                var5.d.n.a(this.w.d.v);
                            }

                            if (this.w.d.n.d == 0) {
                                var6 = this.w.d;
                                var6.p.setLayoutManager(new GridLayoutManager(var6.getApplicationContext(), this.w.d.n()));
                                this.w.d.x.f();
                            } else {
                                var6 = this.w.d;
                                var6.p.setLayoutManager(new LinearLayoutManager(var6.getApplicationContext(), 1, false));
                                if (this.w.d.n.d != 1 && this.w.d.n.d != 2) {
                                    this.w.d.x.c();
                                } else {
                                    this.w.d.x.f();
                                }
                            }

                            this.w.d.n.c();
                        }
                    }
                }
            }
        }
    }

    public class b extends androidx.recyclerview.widget.RecyclerView.a<v> {
        public int c;
        public int d;
        public ArrayList<? extends SelectableBean> e;
        public final ManageCollectionActivity f;

        public b(ManageCollectionActivity var1, RecyclerView var2) {
            this.f = var1;
            this.c = -1;
            this.d = -1;
            var2.a(new RecyclerView.m() {
                @Override
                public void a(RecyclerView recyclerView, int i, int i1) {
                    super.a(recyclerView, i, i1);
                    if (d == 3 || d == 4 || d == 5) {
                        return;
                    }
                    if (i1 > 2) {
                        if (!x.isEnabled()) {
                            return;
                        }
                        x.c();
                    } else if (i1 < -2 && x.isEnabled()) {
                        x.f();
                    }
                }
            });
            this.e = new ArrayList();
        }

        public int a() {
            return this.e.size();
        }

        public void a(ManageCollectionActivity.b.a var1, int var2) {
            BlockCollectionBean var3 = (BlockCollectionBean) this.e.get(var2);
            if (this.f.k) {
                var1.y.setVisibility(0);
                var1.v.setVisibility(8);
            } else {
                var1.v.setVisibility(0);
                var1.y.setVisibility(8);
            }

            if (var3.isSelected) {
                var1.w.setImageResource(2131165707);
            } else {
                var1.w.setImageResource(2131165875);
            }

            var1.v.setImageResource(this.f.a((BlockBean) var3.blocks.get(0)));
            var1.x.setText(var3.name);
            var1.u.setChecked(var3.isSelected);
        }

        public void a(b2 var1, int var2) {
            ProjectResourceBean var3 = (ProjectResourceBean) this.e.get(var2);
            StringBuilder var4 = new StringBuilder();
            var4.append(wq.a());
            var4.append(File.separator);
            var4.append("font");
            var4.append(File.separator);
            var4.append("data");
            var4.append(File.separator);
            var4.append(var3.resFullName);
            String var5 = var4.toString();
            if (this.f.k) {
                var1.z.setVisibility(0);
            } else {
                var1.z.setVisibility(8);
            }

            if (var3.isSelected) {
                var1.w.setImageResource(2131165707);
            } else {
                var1.w.setImageResource(2131165875);
            }

            var1.u.setChecked(var3.isSelected);
            TextView var6 = var1.x;
            var4 = new StringBuilder();
            var4.append(var3.resName);
            var4.append(".ttf");
            var6.setText(var4.toString());

            try {
                var1.y.setTypeface(Typeface.createFromFile(var5));
                var1.y.setText(xB.b().a(this.f.getApplicationContext(), 2131625256));
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        public void a(ManageCollectionActivity.b.c var1, int var2) {
            ProjectResourceBean var3 = (ProjectResourceBean) this.e.get(var2);
            StringBuilder var4 = new StringBuilder();
            var4.append(wq.a());
            var4.append(File.separator);
            var4.append("image");
            var4.append(File.separator);
            var4.append("data");
            var4.append(File.separator);
            var4.append(var3.resFullName);
            String var5 = var4.toString();
            if (this.f.k) {
                var1.y.setVisibility(0);
            } else {
                var1.y.setVisibility(8);
            }

            if (var3.isNinePatch()) {
                var1.x.setVisibility(0);
            } else {
                var1.x.setVisibility(8);
            }

            if (((ProjectResourceBean) this.e.get(var2)).isSelected) {
                var1.w.setImageResource(2131165707);
            } else {
                var1.w.setImageResource(2131165875);
            }

            Glide.with(this.f.getApplicationContext()).load(var5).asBitmap().centerCrop().error(2131165831).into(new BitmapImageViewTarget(var1.v));
            var1.u.setText(((ProjectResourceBean) this.e.get(var2)).resName);
            var1.t.setChecked(((ProjectResourceBean) this.e.get(var2)).isSelected);
        }

        public void a(ManageCollectionActivity.b.d var1, int var2) {
            MoreBlockCollectionBean var3 = (MoreBlockCollectionBean) this.e.get(var2);
            if (this.f.k) {
                var1.w.setVisibility(0);
            } else {
                var1.w.setVisibility(8);
            }

            if (var3.isSelected) {
                var1.v.setImageResource(2131165707);
            } else {
                var1.v.setImageResource(2131165875);
            }

            var1.x.setText(var3.name);
            var1.u.setChecked(var3.isSelected);
            var1.y.removeAllViews();
            var2 = 0;
            Rs var4 = new Rs(this.f.getBaseContext(), 0, var3.spec, " ", "definedFunc");
            var1.y.addView(var4);
            Iterator var5 = FB.c(var3.spec).iterator();

            while (true) {
                Rs var9;
                while (true) {
                    String var6;
                    do {
                        if (!var5.hasNext()) {
                            var4.k();
                            return;
                        }

                        var6 = (String) var5.next();
                    } while (var6.charAt(0) != '%');

                    if (var6.charAt(1) == 'b') {
                        var9 = new Rs(this.f.getBaseContext(), var2 + 1, var6.substring(3), "b", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'd') {
                        var9 = new Rs(this.f.getBaseContext(), var2 + 1, var6.substring(3), "d", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 's') {
                        var9 = new Rs(this.f.getBaseContext(), var2 + 1, var6.substring(3), "s", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'm') {
                        String var8 = var6.substring(var6.lastIndexOf(".") + 1);
                        String var7 = var6.substring(var6.indexOf(".") + 1, var6.lastIndexOf("."));
                        var6 = kq.a(var7);
                        var9 = new Rs(this.f.getBaseContext(), var2 + 1, var8, var6, kq.b(var7), "getVar");
                        break;
                    }
                }

                var1.y.addView(var9);
                var4.a((Ts) var4.V.get(var2), var9);
                ++var2;
            }
        }

        public void a(ManageCollectionActivity.b.e var1, int var2) {
            ProjectResourceBean var3 = (ProjectResourceBean) this.e.get(var2);
            StringBuilder var4 = new StringBuilder();
            var4.append(wq.a());
            var4.append(File.separator);
            var4.append("sound");
            var4.append(File.separator);
            var4.append("data");
            var4.append(File.separator);
            var4.append(var3.resFullName);
            String var7 = var4.toString();
            if (this.f.k) {
                var1.v.setVisibility(8);
                var1.C.setVisibility(0);
            } else {
                this.f.a(var7, var1.v);
                var1.v.setVisibility(0);
                var1.C.setVisibility(8);
            }

            if (var3.isSelected) {
                var1.w.setImageResource(2131165707);
            } else {
                var1.w.setImageResource(2131165875);
            }

            int var5 = ((ProjectResourceBean) this.e.get(var2)).curSoundPosition / 1000;
            if (var3.totalSoundDuration == 0) {
                var3.totalSoundDuration = this.f.b(var7);
            }

            int var6 = var3.totalSoundDuration / 1000;
            var1.z.setText(String.format("%d:%02d", var5 / 60, var5 % 60));
            var1.B.setText(String.format("%d:%02d", var6 / 60, var6 % 60));
            var1.u.setChecked(var3.isSelected);
            var1.x.setText(var3.resName);
            if (this.f.E == var2) {
                if (this.f.C != null && this.f.C.isPlaying()) {
                    var1.y.setImageResource(2131165804);
                } else {
                    var1.y.setImageResource(2131165434);
                }
            } else {
                var1.y.setImageResource(2131165434);
            }

            var1.A.setMax(var3.totalSoundDuration / 100);
            var1.A.setProgress(var3.curSoundPosition / 100);
        }

        public void a(ManageCollectionActivity.b.f var1, int var2) {
            WidgetCollectionBean var3 = (WidgetCollectionBean) this.e.get(var2);
            if (this.f.k) {
                var1.y.setVisibility(0);
                var1.v.setVisibility(8);
            } else {
                var1.y.setVisibility(8);
                var1.v.setVisibility(0);
            }

            if (var3.isSelected) {
                var1.w.setImageResource(2131165707);
            } else {
                var1.w.setImageResource(2131165875);
            }

            var1.v.setImageResource(ViewBean.getViewTypeResId(((ViewBean) var3.widgets.get(0)).type));
            var1.x.setText(var3.name);
            var1.u.setChecked(var3.isSelected);
        }

        public void a(ArrayList<? extends SelectableBean> var1) {
            this.e = var1;
            if (var1.size() <= 0) {
                this.f.w.setVisibility(0);
            } else {
                this.f.w.setVisibility(8);
            }
        }

        public int b(int var1) {
            var1 = this.d;
            if (var1 == 0) {
                return 0;
            } else if (var1 == 1) {
                return 1;
            } else if (var1 == 2) {
                return 2;
            } else if (var1 == 3) {
                return 3;
            } else {
                return var1 == 4 ? 4 : 5;
            }
        }

        public v b(ViewGroup var1, int var2) {
            if (var2 == 0) {
                return new ManageCollectionActivity.b.c(this, LayoutInflater.from(var1.getContext()).inflate(2131427529, var1, false));
            } else if (var2 == 1) {
                return new ManageCollectionActivity.b.e(this, LayoutInflater.from(var1.getContext()).inflate(2131427568, var1, false));
            } else if (var2 == 2) {
                return new b2(this, LayoutInflater.from(var1.getContext()).inflate(2131427524, var1, false));
            } else if (var2 == 3) {
                return new ManageCollectionActivity.b.f(this, LayoutInflater.from(var1.getContext()).inflate(2131427514, var1, false));
            } else {
                return (v) (var2 == 4 ? new ManageCollectionActivity.b.a(this, LayoutInflater.from(var1.getContext()).inflate(2131427508, var1, false)) : new ManageCollectionActivity.b.d(this, LayoutInflater.from(var1.getContext()).inflate(2131427509, var1, false)));
            }
        }

        public void b(v var1, int var2) {
            int var3 = var1.i();
            if (var3 != 0) {
                if (var3 != 1) {
                    if (var3 != 2) {
                        if (var3 != 3) {
                            if (var3 != 4) {
                                if (var3 == 5) {
                                    this.a((ManageCollectionActivity.b.d) var1, var2);
                                }
                            } else {
                                this.a((ManageCollectionActivity.b.a) var1, var2);
                            }
                        } else {
                            this.a((ManageCollectionActivity.b.f) var1, var2);
                        }
                    } else {
                        this.a((b2) var1, var2);
                    }
                } else {
                    this.a((ManageCollectionActivity.b.e) var1, var2);
                }
            } else {
                this.a((ManageCollectionActivity.b.c) var1, var2);
            }
        }

        public class a extends v {
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public ImageView w;
            public TextView x;
            public LinearLayout y;
            public final ManageCollectionActivity.b z;

            public a(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.z = var1;
                this.t = (CardView) var2.findViewById(2131231359);
                this.u = (CheckBox) var2.findViewById(2131230893);
                this.v = (ImageView) var2.findViewById(2131231119);
                this.w = (ImageView) var2.findViewById(2131231132);
                this.x = (TextView) var2.findViewById(2131231893);
                this.y = (LinearLayout) var2.findViewById(2131230959);
                this.u.setVisibility(8);
                this.t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.h(ManageCollectionActivity.b.this.c);
                });
                this.t.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    u.setChecked(!u.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                    return true;
                });
            }
        }

        public class b2 extends v {
            public final ManageCollectionActivity.b A;
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public ImageView w;
            public TextView x;
            public TextView y;
            public LinearLayout z;

            public b2(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.A = var1;
                this.t = (CardView) var2.findViewById(2131231359);
                this.u = (CheckBox) var2.findViewById(2131230893);
                this.v = (ImageView) var2.findViewById(2131231146);
                this.v.setVisibility(8);
                this.w = (ImageView) var2.findViewById(2131231132);
                this.x = (TextView) var2.findViewById(2131231980);
                this.z = (LinearLayout) var2.findViewById(2131230959);
                this.y = (TextView) var2.findViewById(2131231981);
                this.y.setText(xB.b().a(var1.f.getApplicationContext(), 2131625015));
                this.u.setVisibility(8);
                this.t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.i(ManageCollectionActivity.b.this.c);
                });
                this.t.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    u.setChecked(!u.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                    return false;
                });
            }
        }

        public class c extends v {
            public CheckBox t;
            public TextView u;
            public ImageView v;
            public ImageView w;
            public ImageView x;
            public LinearLayout y;
            public final ManageCollectionActivity.b z;

            public c(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.z = var1;
                this.t = (CheckBox) var2.findViewById(2131230893);
                this.u = (TextView) var2.findViewById(2131232003);
                this.v = (ImageView) var2.findViewById(2131231102);
                this.w = (ImageView) var2.findViewById(2131231132);
                this.y = (LinearLayout) var2.findViewById(2131230959);
                this.x = (ImageView) var2.findViewById(2131231161);
                this.t.setVisibility(8);
                this.v.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        t.setChecked(!t.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = t.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.j(ManageCollectionActivity.b.this.c);
                });
                this.v.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    t.setChecked(!t.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = t.isChecked();
                    return true;
                });
            }
        }

        public class d extends v {
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public LinearLayout w;
            public TextView x;
            public RelativeLayout y;
            public final ManageCollectionActivity.b z;

            public d(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.z = var1;
                this.t = (CardView) var2.findViewById(2131231359);
                this.u = (CheckBox) var2.findViewById(2131230893);
                this.v = (ImageView) var2.findViewById(2131231132);
                this.w = (LinearLayout) var2.findViewById(2131230959);
                this.x = (TextView) var2.findViewById(2131231893);
                this.y = (RelativeLayout) var2.findViewById(2131230793);
                this.u.setVisibility(8);
                this.t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.k(ManageCollectionActivity.b.this.c);
                });
                this.t.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    u.setChecked(!u.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                    return true;
                });
            }
        }

        public class e extends v {
            public ProgressBar A;
            public TextView B;
            public LinearLayout C;
            public final ManageCollectionActivity.b D;
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public ImageView w;
            public TextView x;
            public ImageView y;
            public TextView z;

            public e(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.D = var1;
                this.t = (CardView) var2.findViewById(2131231359);
                this.u = (CheckBox) var2.findViewById(2131230893);
                this.v = (ImageView) var2.findViewById(2131231106);
                this.x = (TextView) var2.findViewById(2131232169);
                this.y = (ImageView) var2.findViewById(2131231165);
                this.w = (ImageView) var2.findViewById(2131231132);
                this.z = (TextView) var2.findViewById(2131231931);
                this.A = (ProgressBar) var2.findViewById(2131231607);
                this.B = (TextView) var2.findViewById(2131231967);
                this.C = (LinearLayout) var2.findViewById(2131230959);
                this.u.setVisibility(8);
                this.y.setOnClickListener(v -> {
                    if (ManageCollectionActivity.this.k) {
                        ManageCollectionActivity.this.F = ManageCollectionActivity.b.e.this.z;
                        ManageCollectionActivity.this.G = ManageCollectionActivity.b.e.this.A;
                        ManageCollectionActivity.this.a(ManageCollectionActivity.b.this.e, j());
                    }
                });
                this.t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.l(ManageCollectionActivity.b.this.c);
                });
                this.t.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    u.setChecked(!u.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                    return true;
                });
            }
        }

        public class f extends v {
            public CardView t;
            public CheckBox u;
            public ImageView v;
            public ImageView w;
            public TextView x;
            public LinearLayout y;
            public final ManageCollectionActivity.b z;

            public f(ManageCollectionActivity.b var1, View var2) {
                super(var2);
                this.z = var1;
                this.t = (CardView) var2.findViewById(2131231359);
                this.u = (CheckBox) var2.findViewById(2131230893);
                this.v = (ImageView) var2.findViewById(2131231205);
                this.w = (ImageView) var2.findViewById(2131231132);
                this.x = (TextView) var2.findViewById(2131232289);
                this.y = (LinearLayout) var2.findViewById(2131230959);
                this.u.setVisibility(8);
                this.t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.m(ManageCollectionActivity.b.this.c);
                });
                this.t.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    ManageCollectionActivity.b.this.c = j();
                    u.setChecked(!u.isChecked());
                    ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                    return true;
                });
            }
        }
    }
}
