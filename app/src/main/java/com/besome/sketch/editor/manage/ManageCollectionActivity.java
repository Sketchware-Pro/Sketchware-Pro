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
import com.sketchware.remod.R;

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

    public static String a(Context context, int position) {
        String label;
        if (position == 0) {
            label = xB.b().a(context, R.string.common_word_image);
        } else if (position == 1) {
            label = xB.b().a(context, R.string.common_word_sound);
        } else if (position == 2) {
            label = xB.b().a(context, R.string.common_word_font);
        } else if (position == 3) {
            label = xB.b().a(context, R.string.common_word_widget);
        } else if (position == 4) {
            label = xB.b().a(context, R.string.common_word_block);
        } else {
            label = xB.b().a(context, R.string.common_word_moreblock);
        }

        return label;
    }

    public static int g(int position) {
        if (position == 0) {
            position = R.drawable.ic_picture_48dp;
        } else if (position == 1) {
            position = R.drawable.ic_sound_wave_48dp;
        } else if (position == 2) {
            position = R.drawable.ic_font_48dp;
        } else if (position == 3) {
            position = R.drawable.collage_96;
        } else if (position == 4) {
            position = R.drawable.block_96_blue;
        } else {
            position = R.drawable.more_block_96dp;
        }

        return position;
    }

    public void A() {
        a(r);
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", r);
        intent.putExtra("sc_id", y);
        startActivityForResult(intent, 269);
    }

    public int a(BlockBean block) {
        if (block.type.equals("c")) {
            return R.drawable.fav_block_c_96dp;
        } else if (block.type.equals("b")) {
            return R.drawable.fav_block_boolean_96dp;
        } else if (block.type.equals("f")) {
            return R.drawable.fav_block_final_96dp;
        } else if (block.type.equals("e")) {
            return R.drawable.fav_block_e_96dp;
        } else if (block.type.equals("d")) {
            return R.drawable.fav_block_number_96dp;
        } else if (block.type.equals("s")) {
            return R.drawable.fav_block_string_96dp;
        } else {
            return R.drawable.fav_block_command_96dp;
        }
    }

    public void a(String filePath, ImageView target) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(filePath);
        byte[] embeddedPicture = metadataRetriever.getEmbeddedPicture();
        if (embeddedPicture != null) {
            Glide.with(getApplicationContext()).load(embeddedPicture).centerCrop().into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    target.setImageDrawable(glideDrawable);
                }
            });
        } else {
            target.setImageResource(R.drawable.default_album_art_200dp);
            target.setBackgroundResource(R.drawable.bg_outline_album);
        }

        metadataRetriever.release();
    }

    public void a(ArrayList<ProjectResourceBean> var1) {
        A.cancel();
        if (E != -1) {
            var1.get(E).curSoundPosition = 0;
            E = -1;
            D = -1;
            n.c();
        }

        if (C != null && C.isPlaying()) {
            C.pause();
        }
    }

    public final void a(ArrayList<? extends SelectableBean> var1, int position) {
        if (E == position) {
            if (C != null) {
                if (C.isPlaying()) {
                    A.cancel();
                    C.pause();
                    ((ProjectResourceBean) var1.get(E)).curSoundPosition = C.getCurrentPosition();
                    n.c(E);
                    return;
                }

                C.start();
                n(position);
                n.c();
            }
        } else {
            if (C != null && C.isPlaying()) {
                A.cancel();
                C.pause();
                C.release();
            }

            if (D != -1) {
                ((ProjectResourceBean) var1.get(D)).curSoundPosition = 0;
                n.c(D);
            }

            E = position;
            D = position;
            n.c(E);
            C = new MediaPlayer();
            C.setAudioStreamType(3);
            C.setOnPreparedListener(mp -> {
                C.start();
                n(position);
                n.c(E);
            });
            C.setOnCompletionListener(mp -> {
                A.cancel();
                ((ProjectResourceBean) var1.get(E)).curSoundPosition = 0;
                n.c(E);
                E = -1;
                D = -1;
            });

            try {
                C.setDataSource(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + ((ProjectResourceBean) var1.get(E)).resFullName);
                C.prepare();
            } catch (Exception e) {
                E = -1;
                n.c(E);
                e.printStackTrace();
            }
        }
    }

    public final void a(boolean var1) {
        k = var1;
        invalidateOptionsMenu();
        x();
        if (k) {
            a(r);
            H.setVisibility(View.VISIBLE);
        } else {
            H.setVisibility(View.GONE);
            if (m.c == 3 || m.c == 4) {
                x.setVisibility(View.GONE);
            }
        }

        n.c();
    }

    public final int b(String filePath) {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(filePath);
        return (int) Long.parseLong(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }

    public void f(int var1) {
        if (var1 == 0) {
            z();
        } else if (var1 == 1) {
            A();
        } else {
            y();
        }
    }

    public void h(int var1) {
        String blockName = (String) Mp.h().g().get(var1);
        Intent intent = new Intent(getApplicationContext(), ShowBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, 274);
    }

    public void i(int var1) {
        ProjectResourceBean editTarget = s.get(var1);
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", s);
        intent.putExtra("sc_id", y);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, 272);
    }

    public void j(int var1) {
        ProjectResourceBean editTarget = q.get(var1);
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", q);
        intent.putExtra("sc_id", y);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, 268);
    }

    public void k(int var1) {
        String blockName = (String) Pp.h().g().get(var1);
        Intent intent = new Intent(getApplicationContext(), ShowMoreBlockCollectionActivity.class);
        intent.putExtra("block_name", blockName);
        startActivityForResult(intent, 279);
    }

    public void l() {
        for (int i = 0; i < m.a(); i++) {
            if (i == 0) {
                for (ProjectResourceBean bean : q) {
                    if (bean.isSelected) {
                        Op.g().a(bean.resName, false);
                    }
                }

                Op.g().e();
                t();
            } else if (i == 1) {
                for (ProjectResourceBean bean : r) {
                    if (bean.isSelected) {
                        Qp.g().a(bean.resName, false);
                    }
                }

                Qp.g().e();
                v();
            } else if (i == 2) {
                for (ProjectResourceBean bean : s) {
                    if (bean.isSelected) {
                        Np.g().a(bean.resName, false);
                    }
                }

                Np.g().e();
                s();
            } else if (i == 3) {
                for (WidgetCollectionBean bean : t) {
                    if (bean.isSelected) {
                        if (!K) {
                            K = true;
                        }

                        Rp.h().a(bean.name, false);
                    }
                }

                Rp.h().e();
                w();
            } else if (i == 4) {
                for (BlockCollectionBean bean : u) {
                    if (bean.isSelected) {
                        Mp.h().a(bean.name, false);
                    }
                }

                Mp.h().e();
                r();
            } else {
                for (MoreBlockCollectionBean bean : v) {
                    if (bean.isSelected) {
                        Pp.h().a(bean.name, false);
                    }
                }

                Pp.h().e();
                u();
            }
        }

        x();
        a(false);
        int m = m();
        if (m == 0 || m == 1 || m == 2) {
            x.f();
        }

        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_complete_delete), 1).show();
        n.c();
    }

    public void l(int var1) {
        ProjectResourceBean editTarget = r.get(var1);
        a(r);
        Intent intent = new Intent(getApplicationContext(), AddSoundCollectionActivity.class);
        intent.putParcelableArrayListExtra("sounds", r);
        intent.putExtra("sc_id", y);
        intent.putExtra("edit_target", editTarget);
        startActivityForResult(intent, 270);
    }

    public final int m() {
        return m.c;
    }

    public void m(int var1) {
        String widgetName = (String) Rp.h().g().get(var1);
        Intent intent = new Intent(getApplicationContext(), ShowWidgetCollectionActivity.class);
        intent.putExtra("widget_name", widgetName);
        startActivityForResult(intent, 273);
    }

    public final int n() {
        int var1 = (int) ((float) getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density) / 100;
        int var2 = var1;
        if (var1 > 2) {
            var2 = var1 - 1;
        }

        return var2;
    }

    public final void n(int position) {
        A = new Timer();
        B = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (C == null) {
                        A.cancel();
                        return;
                    }
                    ManageCollectionActivity.b.e viewHolder = (ManageCollectionActivity.b.e) p.d(position);
                    int currentPosition = C.getCurrentPosition() / 1000;
                    viewHolder.z.setText(String.format("%d:%02d", currentPosition / 60, currentPosition % 60));
                    viewHolder.A.setProgress(C.getCurrentPosition() / 1000);
                });
            }
        };
        A.schedule(B, 100L, 100L);
    }

    public final void o() {
        l = (Toolbar) findViewById(R.id.toolbar);
        a(l);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_collection));
        d().e(true);
        d().d(true);
        l.setNavigationOnClickListener(v -> {
            if (mB.a()) {
                return;
            }
            onBackPressed();
        });
        w = (TextView) findViewById(R.id.tv_no_collections);
        w.setText(xB.b().a(getApplicationContext(), R.string.event_message_no_events));
        o = (RecyclerView) findViewById(R.id.category_list);
        o.setHasFixedSize(true);
        o.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        ((Bi) o.getItemAnimator()).a(false);
        m = new ManageCollectionActivity.a(this);
        o.setAdapter(m);
        p = (RecyclerView) findViewById(R.id.collection_list);
        p.setHasFixedSize(true);
        n = new ManageCollectionActivity.b(this, p);
        p.setAdapter(n);
        x = (FloatingActionButton) findViewById(R.id.fab);
        x.setOnClickListener(this);
        H = (LinearLayout) findViewById(R.id.layout_btn_group);
        I = (Button) findViewById(R.id.btn_delete);
        J = (Button) findViewById(R.id.btn_cancel);
        I.setText(xB.b().a(getApplicationContext(), R.string.common_word_delete));
        J.setText(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        I.setOnClickListener(this);
        J.setOnClickListener(this);
        if (!j.h()) {
            xo.a(getApplicationContext());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 267:
            case 268:
                t();
                break;

            case 269:
            case 270:
                v();
                break;

            case 271:
            case 272:
                s();
                break;

            case 273:
                w();
                break;

            case 274:
                r();
                break;

            case 279:
                u();
                break;

            default:
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (K) {
            setResult(RESULT_OK);
            finish();
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_cancel && k) {
            a(false);
        } else if (id == R.id.btn_delete && k) {
            l();
        } else if (id == R.id.fab) {
            a(false);
            f(m.c);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (n.d == 0) {
            ((GridLayoutManager) p.getLayoutManager()).d(n());
            p.requestLayout();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!j()) {
            finish();
        }

        setContentView(R.layout.manage_collection);
        o();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_collection_menu, menu);
        menu.findItem(R.id.menu_collection_delete).setVisible(!k);

        return true;
    }

    @Override
    public void onDestroy() {
        a(r);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_collection_delete) {
            a(!k);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (C != null && C.isPlaying()) {
            A.cancel();
            C.pause();
            r.get(E).curSoundPosition = C.getCurrentPosition();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            p();
        } else {
            y = savedInstanceState.getString("sc_id");
        }

        q();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!j()) {
            finish();
        }

        d.setScreenName(ManageCollectionActivity.class.getSimpleName());
        d.send((new ScreenViewBuilder()).build());
        if (n != null) {
            n.c();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", y);
        super.onSaveInstanceState(outState);
    }

    public void p() {
        y = getIntent().getStringExtra("sc_id");
    }

    public void q() {
        q = Op.g().f();
        r = Qp.g().f();
        s = Np.g().f();
        t = Rp.h().f();
        u = Mp.h().f();
        v = Pp.h().f();

        if (m.c == -1) {
            n.d = 0;
            n.a(q);
            p.setLayoutManager(new GridLayoutManager(getApplicationContext(), n()));
            m.c = 0;
            m.c();
        }

        if (n != null) {
            n.c();
        }
    }

    public void r() {
        u = Mp.h().f();
        if (m.c == 4) {
            n.a(u);
            n.d = 4;
        }

        n.c();
    }

    public void s() {
        s = Np.g().f();
        if (m.c == 2) {
            n.a(s);
            n.d = 2;
        }

        n.c();
    }

    public void t() {
        q = Op.g().f();
        if (m.c == 0) {
            n.a(q);
            n.d = 0;
        }

        n.c();
    }

    public void u() {
        v = Pp.h().f();
        if (m.c == 5) {
            n.a(v);
            n.d = 5;
        }

        n.c();
    }

    public void v() {
        r = Qp.g().f();
        if (m.c == 1) {
            n.a(r);
            n.d = 1;
        }

        n.c();
    }

    public void w() {
        t = Rp.h().f();
        if (m.c == 3) {
            n.a(t);
            n.d = 3;
        }

        n.c();
    }

    public final void x() {
        int m = m();

        if (m == 0) {
            for (ProjectResourceBean bean : q) {
                bean.isSelected = false;
            }
        } else if (m == 1) {
            for (ProjectResourceBean bean : r) {
                bean.isSelected = false;
            }
        } else if (m == 2) {
            for (ProjectResourceBean bean : s) {
                bean.isSelected = false;
            }
        } else if (m == 3) {
            for (WidgetCollectionBean bean : t) {
                bean.isSelected = false;
            }
        } else {
            for (BlockCollectionBean bean : u) {
                bean.isSelected = false;
            }
        }
    }

    public void y() {
        Intent intent = new Intent(getApplicationContext(), AddFontCollectionActivity.class);
        intent.putParcelableArrayListExtra("fonts", s);
        intent.putExtra("sc_id", y);
        startActivityForResult(intent, 271);
    }

    public void z() {
        Intent intent = new Intent(getApplicationContext(), AddImageCollectionActivity.class);
        intent.putParcelableArrayListExtra("images", q);
        intent.putExtra("sc_id", y);
        startActivityForResult(intent, 267);
    }

    public class a extends RecyclerView.a<ManageCollectionActivity.a.a2> {
        public int c;
        public final ManageCollectionActivity d;

        public a(ManageCollectionActivity var1) {
            d = var1;
            c = -1;
        }

        public int a() {
            return 6;
        }

        public void b(a2 var1, int var2) {
            var1.u.setText(ManageCollectionActivity.a(d.getApplicationContext(), var2));
            var1.t.setImageResource(ManageCollectionActivity.g(var2));
            ef var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (c == var2) {
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
                var1.v.setVisibility(View.VISIBLE);
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
                var1.v.setVisibility(View.GONE);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
                var1.t.setColorFilter(var5);
            }
        }

        public a2 b(ViewGroup var1, int var2) {
            return new a2(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.common_category_triangle_item, var1, false));
        }

        public class a2 extends v implements OnClickListener {
            public ImageView t;
            public TextView u;
            public View v;
            public final ManageCollectionActivity.a w;

            public a2(ManageCollectionActivity.a var1, View var2) {
                super(var2);
                w = var1;
                t = var2.findViewById(R.id.img_icon);
                u = var2.findViewById(R.id.tv_name);
                v = var2.findViewById(R.id.pointer_left);
                var2.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    if (j() != -1 && j() != w.c) {
                        if (w.c == 1) {
                            w.d.a(w.d.r);
                        }

                        w.c(w.c);
                        w.c = j();
                        w.c(w.c);
                        w.d.p.removeAllViews();
                        w.d.n.d = w.c;
                        if (w.c == 0) {
                            w.d.n.a(w.d.q);
                        } else if (w.c == 1) {
                            w.d.n.a(w.d.r);
                        } else if (w.c == 2) {
                            w.d.n.a(w.d.s);
                        } else if (w.c == 3) {
                            w.d.n.a(w.d.t);
                        } else if (w.c == 4) {
                            w.d.n.a(w.d.u);
                        } else {
                            w.d.n.a(w.d.v);
                        }

                        if (w.d.n.d == 0) {
                            w.d.p.setLayoutManager(new GridLayoutManager(getApplicationContext(), w.d.n()));
                            w.d.x.f();
                        } else {
                            w.d.p.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
                            if (w.d.n.d != 1 && w.d.n.d != 2) {
                                w.d.x.c();
                            } else {
                                w.d.x.f();
                            }
                        }

                        w.d.n.c();
                    }
                }
            }
        }
    }

    public class b extends RecyclerView.a<v> {
        public int c;
        public int d;
        public ArrayList<? extends SelectableBean> e;
        public final ManageCollectionActivity f;

        public b(ManageCollectionActivity var1, RecyclerView var2) {
            f = var1;
            c = -1;
            d = -1;
            // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
            var2.a(new RecyclerView.m() {
                @Override
                // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
                public void a(RecyclerView recyclerView, int dx, int dy) {
                    super.a(recyclerView, dx, dy);
                    if (d == 3 || d == 4 || d == 5) {
                        return;
                    }
                    if (dy > 2) {
                        if (!x.isEnabled()) {
                            return;
                        }
                        x.c();
                    } else if (dy < -2 && x.isEnabled()) {
                        x.f();
                    }
                }
            });
            e = new ArrayList<>();
        }

        public int a() {
            return e.size();
        }

        public void a(ManageCollectionActivity.b.a var1, int var2) {
            BlockCollectionBean var3 = (BlockCollectionBean) e.get(var2);
            if (f.k) {
                var1.y.setVisibility(View.VISIBLE);
                var1.v.setVisibility(View.GONE);
            } else {
                var1.v.setVisibility(View.VISIBLE);
                var1.y.setVisibility(View.GONE);
            }

            if (var3.isSelected) {
                var1.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            var1.v.setImageResource(f.a((BlockBean) var3.blocks.get(0)));
            var1.x.setText(var3.name);
            var1.u.setChecked(var3.isSelected);
        }

        public void a(b2 var1, int var2) {
            ProjectResourceBean bean = (ProjectResourceBean) e.get(var2);
            if (f.k) {
                var1.z.setVisibility(View.VISIBLE);
            } else {
                var1.z.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                var1.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            var1.u.setChecked(bean.isSelected);
            var1.x.setText(bean.resName + ".ttf");

            try {
                var1.y.setTypeface(Typeface.createFromFile(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + bean.resFullName));
                var1.y.setText(xB.b().a(f.getApplicationContext(), R.string.design_manager_font_description_example_sentence));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void a(ManageCollectionActivity.b.c var1, int var2) {
            ProjectResourceBean bean = (ProjectResourceBean) e.get(var2);
            if (f.k) {
                var1.y.setVisibility(View.VISIBLE);
            } else {
                var1.y.setVisibility(View.GONE);
            }

            if (bean.isNinePatch()) {
                var1.x.setVisibility(View.VISIBLE);
            } else {
                var1.x.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                var1.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            Glide.with(f.getApplicationContext()).load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + bean.resFullName)
                    .asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(var1.v));
            var1.u.setText(bean.resName);
            var1.t.setChecked(bean.isSelected);
        }

        public void a(ManageCollectionActivity.b.d var1, int var2) {
            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) e.get(var2);
            if (f.k) {
                var1.w.setVisibility(View.VISIBLE);
            } else {
                var1.w.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                var1.v.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.v.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            var1.x.setText(bean.name);
            var1.u.setChecked(bean.isSelected);
            var1.y.removeAllViews();
            var2 = 0;
            Rs header = new Rs(f.getBaseContext(), 0, bean.spec, " ", "definedFunc");
            var1.y.addView(header);
            Iterator<String> var5 = FB.c(bean.spec).iterator();

            while (true) {
                Rs block;
                while (true) {
                    String var6;
                    do {
                        if (!var5.hasNext()) {
                            header.k();
                            return;
                        }

                        var6 = var5.next();
                    } while (var6.charAt(0) != '%');

                    if (var6.charAt(1) == 'b') {
                        block = new Rs(f.getBaseContext(), var2 + 1, var6.substring(3), "b", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'd') {
                        block = new Rs(f.getBaseContext(), var2 + 1, var6.substring(3), "d", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 's') {
                        block = new Rs(f.getBaseContext(), var2 + 1, var6.substring(3), "s", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'm') {
                        String var8 = var6.substring(var6.lastIndexOf(".") + 1);
                        String var7 = var6.substring(var6.indexOf(".") + 1, var6.lastIndexOf("."));
                        var6 = kq.a(var7);
                        block = new Rs(f.getBaseContext(), var2 + 1, var8, var6, kq.b(var7), "getVar");
                        break;
                    }
                }

                var1.y.addView(block);
                header.a((Ts) header.V.get(var2), block);
                ++var2;
            }
        }

        public void a(ManageCollectionActivity.b.e var1, int var2) {
            ProjectResourceBean bean = (ProjectResourceBean) e.get(var2);
            String soundFilePath = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            if (f.k) {
                var1.v.setVisibility(View.GONE);
                var1.C.setVisibility(View.VISIBLE);
            } else {
                f.a(soundFilePath, var1.v);
                var1.v.setVisibility(View.VISIBLE);
                var1.C.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                var1.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            int soundPositionInS = bean.curSoundPosition / 1000;
            if (bean.totalSoundDuration == 0) {
                bean.totalSoundDuration = f.b(soundFilePath);
            }

            int totalSoundDurationInS = bean.totalSoundDuration / 1000;
            var1.z.setText(String.format("%d:%02d", soundPositionInS / 60, soundPositionInS % 60));
            var1.B.setText(String.format("%d:%02d", totalSoundDurationInS / 60, totalSoundDurationInS % 60));
            var1.u.setChecked(bean.isSelected);
            var1.x.setText(bean.resName);
            if (f.E == var2) {
                if (f.C != null && f.C.isPlaying()) {
                    var1.y.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    var1.y.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                var1.y.setImageResource(R.drawable.circled_play_96_blue);
            }

            var1.A.setMax(bean.totalSoundDuration / 100);
            var1.A.setProgress(bean.curSoundPosition / 100);
        }

        public void a(ManageCollectionActivity.b.f var1, int var2) {
            WidgetCollectionBean bean = (WidgetCollectionBean) e.get(var2);
            if (f.k) {
                var1.y.setVisibility(View.VISIBLE);
                var1.v.setVisibility(View.GONE);
            } else {
                var1.y.setVisibility(View.GONE);
                var1.v.setVisibility(View.VISIBLE);
            }

            if (bean.isSelected) {
                var1.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                var1.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            var1.v.setImageResource(ViewBean.getViewTypeResId(((ViewBean) bean.widgets.get(0)).type));
            var1.x.setText(bean.name);
            var1.u.setChecked(bean.isSelected);
        }

        public void a(ArrayList<? extends SelectableBean> beans) {
            e = beans;
            if (beans.size() <= 0) {
                f.w.setVisibility(View.VISIBLE);
            } else {
                f.w.setVisibility(View.GONE);
            }
        }

        public int b(int var1) {
            var1 = d;

            if (var1 == 0) {
                return 0;
            } else if (var1 == 1) {
                return 1;
            } else if (var1 == 2) {
                return 2;
            } else if (var1 == 3) {
                return 3;
            } else if (var1 == 4) {
                return 4;
            } else {
                return 5;
            }
        }

        public v b(ViewGroup var1, int var2) {
            if (var2 == 0) {
                return new ManageCollectionActivity.b.c(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_image_list_item, var1, false));
            } else if (var2 == 1) {
                return new ManageCollectionActivity.b.e(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_sound_list_item, var1, false));
            } else if (var2 == 2) {
                return new b2(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_font_list_item, var1, false));
            } else if (var2 == 3) {
                return new ManageCollectionActivity.b.f(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_collection_widget_list_item, var1, false));
            } else {
                return (v) (var2 == 4 ? new ManageCollectionActivity.b.a(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_collection_block_list_item, var1, false)) : new ManageCollectionActivity.b.d(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_collection_more_block_list_item, var1, false)));
            }
        }

        public void b(v var1, int var2) {
            int var3 = var1.i();

            if (var3 == 0) {
                a((ManageCollectionActivity.b.c) var1, var2);
            } else if (var3 == 1) {
                a((ManageCollectionActivity.b.e) var1, var2);
            } else if (var3 == 2) {
                a((b2) var1, var2);
            } else if (var3 == 3) {
                a((ManageCollectionActivity.b.f) var1, var2);
            } else if (var3 == 4) {
                a((ManageCollectionActivity.b.a) var1, var2);
            } else if (var3 == 5) {
                a((ManageCollectionActivity.b.d) var1, var2);
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
                z = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.chk_select);
                v = var2.findViewById(R.id.img_block);
                w = var2.findViewById(R.id.img_delete);
                x = var2.findViewById(R.id.tv_block_name);
                y = var2.findViewById(R.id.delete_img_container);
                u.setVisibility(View.GONE);
                t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.h(ManageCollectionActivity.b.this.c);
                });
                t.setOnLongClickListener(v -> {
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
                A = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.chk_select);
                v = var2.findViewById(R.id.img_font);
                v.setVisibility(View.GONE);
                w = var2.findViewById(R.id.img_delete);
                x = var2.findViewById(R.id.tv_font_name);
                z = var2.findViewById(R.id.delete_img_container);
                y = var2.findViewById(R.id.tv_font_preview);
                y.setText(xB.b().a(var1.f.getApplicationContext(), R.string.common_word_preview));
                u.setVisibility(View.GONE);
                t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.i(ManageCollectionActivity.b.this.c);
                });
                t.setOnLongClickListener(v -> {
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
                z = var1;
                t = var2.findViewById(R.id.chk_select);
                u = var2.findViewById(R.id.tv_image_name);
                v = var2.findViewById(R.id.img);
                w = var2.findViewById(R.id.img_delete);
                y = var2.findViewById(R.id.delete_img_container);
                x = var2.findViewById(R.id.img_nine_patch);
                t.setVisibility(View.GONE);
                v.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        t.setChecked(!t.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = t.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.j(ManageCollectionActivity.b.this.c);
                });
                v.setOnLongClickListener(v -> {
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
                z = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.chk_select);
                v = var2.findViewById(R.id.img_delete);
                w = var2.findViewById(R.id.delete_img_container);
                x = var2.findViewById(R.id.tv_block_name);
                y = var2.findViewById(R.id.block_area);
                u.setVisibility(View.GONE);
                t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.k(ManageCollectionActivity.b.this.c);
                });
                t.setOnLongClickListener(v -> {
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
                D = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.chk_select);
                v = var2.findViewById(R.id.img_album);
                x = var2.findViewById(R.id.tv_sound_name);
                y = var2.findViewById(R.id.img_play);
                w = var2.findViewById(R.id.img_delete);
                z = var2.findViewById(R.id.tv_currenttime);
                A = var2.findViewById(R.id.prog_playtime);
                B = var2.findViewById(R.id.tv_endtime);
                C = var2.findViewById(R.id.delete_img_container);
                u.setVisibility(View.GONE);
                y.setOnClickListener(v -> {
                    if (ManageCollectionActivity.this.k) {
                        ManageCollectionActivity.this.F = ManageCollectionActivity.b.e.this.z;
                        ManageCollectionActivity.this.G = ManageCollectionActivity.b.e.this.A;
                        ManageCollectionActivity.this.a(ManageCollectionActivity.b.this.e, j());
                    }
                });
                t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.l(ManageCollectionActivity.b.this.c);
                });
                t.setOnLongClickListener(v -> {
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
                z = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.chk_select);
                v = var2.findViewById(R.id.img_widget);
                w = var2.findViewById(R.id.img_delete);
                x = var2.findViewById(R.id.tv_widget_name);
                y = var2.findViewById(R.id.delete_img_container);
                u.setVisibility(View.GONE);
                t.setOnClickListener(v -> {
                    ManageCollectionActivity.b.this.c = j();
                    if (ManageCollectionActivity.this.k) {
                        u.setChecked(!u.isChecked());
                        ManageCollectionActivity.b.this.e.get(ManageCollectionActivity.b.this.c).isSelected = u.isChecked();
                        ManageCollectionActivity.b.this.c(ManageCollectionActivity.b.this.c);
                        return;
                    }
                    ManageCollectionActivity.this.m(ManageCollectionActivity.b.this.c);
                });
                t.setOnLongClickListener(v -> {
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
