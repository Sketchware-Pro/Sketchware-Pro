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
    public CategoryAdapter m;
    public CollectionAdapter n;
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
            if (m.currentItemId == 3 || m.currentItemId == 4) {
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
        return m.currentItemId;
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
                    CollectionAdapter.SoundCollectionViewHolder viewHolder = (CollectionAdapter.SoundCollectionViewHolder) p.d(position);
                    int currentPosition = C.getCurrentPosition() / 1000;
                    viewHolder.currentPosition.setText(String.format("%d:%02d", currentPosition / 60, currentPosition % 60));
                    viewHolder.playbackProgress.setProgress(C.getCurrentPosition() / 1000);
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
        m = new CategoryAdapter(this);
        o.setAdapter(m);
        p = (RecyclerView) findViewById(R.id.collection_list);
        p.setHasFixedSize(true);
        n = new CollectionAdapter(this, p);
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
            f(m.currentItemId);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (n.currentViewType == 0) {
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

        if (m.currentItemId == -1) {
            n.currentViewType = 0;
            n.a(q);
            p.setLayoutManager(new GridLayoutManager(getApplicationContext(), n()));
            m.currentItemId = 0;
            m.c();
        }

        if (n != null) {
            n.c();
        }
    }

    public void r() {
        u = Mp.h().f();
        if (m.currentItemId == 4) {
            n.a(u);
            n.currentViewType = 4;
        }

        n.c();
    }

    public void s() {
        s = Np.g().f();
        if (m.currentItemId == 2) {
            n.a(s);
            n.currentViewType = 2;
        }

        n.c();
    }

    public void t() {
        q = Op.g().f();
        if (m.currentItemId == 0) {
            n.a(q);
            n.currentViewType = 0;
        }

        n.c();
    }

    public void u() {
        v = Pp.h().f();
        if (m.currentItemId == 5) {
            n.a(v);
            n.currentViewType = 5;
        }

        n.c();
    }

    public void v() {
        r = Qp.g().f();
        if (m.currentItemId == 1) {
            n.a(r);
            n.currentViewType = 1;
        }

        n.c();
    }

    public void w() {
        t = Rp.h().f();
        if (m.currentItemId == 3) {
            n.a(t);
            n.currentViewType = 3;
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

    private class CategoryAdapter extends RecyclerView.a<CategoryAdapter.ViewHolder> {

        private int currentItemId;
        public final ManageCollectionActivity d;

        public CategoryAdapter(ManageCollectionActivity var1) {
            d = var1;
            currentItemId = -1;
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return 6;
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(CategoryAdapter.ViewHolder holder, int position) {
            holder.name.setText(ManageCollectionActivity.a(d.getApplicationContext(), position));
            holder.icon.setImageResource(ManageCollectionActivity.g(position));
            ef var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (currentItemId == position) {
                var3 = Ze.a(holder.icon);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                var3 = Ze.a(holder.icon);
                var3.c(1.0F);
                var3.d(1.0F);
                var3.a(300L);
                var3.a(new AccelerateInterpolator());
                var3.c();
                holder.pointerLeft.setVisibility(View.VISIBLE);
                var4 = new ColorMatrix();
                var4.setSaturation(1.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            } else {
                var3 = Ze.a(holder.icon);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                var3 = Ze.a(holder.icon);
                var3.c(0.8F);
                var3.d(0.8F);
                var3.a(300L);
                var3.a(new DecelerateInterpolator());
                var3.c();
                holder.pointerLeft.setVisibility(View.GONE);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
                holder.icon.setColorFilter(var5);
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public CategoryAdapter.ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.common_category_triangle_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.v implements OnClickListener {

            public final ImageView icon;
            public final TextView name;
            public final View pointerLeft;
            public final CategoryAdapter w;

            public ViewHolder(CategoryAdapter var1, View itemView) {
                super(itemView);
                w = var1;
                icon = itemView.findViewById(R.id.img_icon);
                name = itemView.findViewById(R.id.tv_name);
                pointerLeft = itemView.findViewById(R.id.pointer_left);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    if (j() != -1 && j() != w.currentItemId) {
                        if (w.currentItemId == 1) {
                            w.d.a(w.d.r);
                        }

                        w.c(w.currentItemId);
                        w.currentItemId = j();
                        w.c(w.currentItemId);
                        w.d.p.removeAllViews();
                        w.d.n.currentViewType = w.currentItemId;
                        if (w.currentItemId == 0) {
                            w.d.n.a(w.d.q);
                        } else if (w.currentItemId == 1) {
                            w.d.n.a(w.d.r);
                        } else if (w.currentItemId == 2) {
                            w.d.n.a(w.d.s);
                        } else if (w.currentItemId == 3) {
                            w.d.n.a(w.d.t);
                        } else if (w.currentItemId == 4) {
                            w.d.n.a(w.d.u);
                        } else {
                            w.d.n.a(w.d.v);
                        }

                        if (w.d.n.currentViewType == 0) {
                            w.d.p.setLayoutManager(new GridLayoutManager(getApplicationContext(), w.d.n()));
                            w.d.x.f();
                        } else {
                            w.d.p.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
                            if (w.d.n.currentViewType != 1 && w.d.n.currentViewType != 2) {
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

    private class CollectionAdapter extends RecyclerView.a<RecyclerView.v> {

        private int lastSelectedItemPosition;
        private int currentViewType;
        private ArrayList<? extends SelectableBean> currentCollectionTypeItems;
        public final ManageCollectionActivity f;

        public CollectionAdapter(ManageCollectionActivity var1, RecyclerView target) {
            f = var1;
            lastSelectedItemPosition = -1;
            currentViewType = -1;
            // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
            target.a(new RecyclerView.m() {
                @Override
                // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
                public void a(RecyclerView recyclerView, int dx, int dy) {
                    super.a(recyclerView, dx, dy);
                    if (currentViewType == 3 || currentViewType == 4 || currentViewType == 5) {
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
            currentCollectionTypeItems = new ArrayList<>();
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return currentCollectionTypeItems.size();
        }

        public void a(BlockCollectionViewHolder holder, int position) {
            BlockCollectionBean var3 = (BlockCollectionBean) currentCollectionTypeItems.get(position);
            if (f.k) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
                holder.blockIcon.setVisibility(View.GONE);
            } else {
                holder.blockIcon.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (var3.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.blockIcon.setImageResource(f.a((BlockBean) var3.blocks.get(0)));
            holder.name.setText(var3.name);
            holder.checkBox.setChecked(var3.isSelected);
        }

        public void a(FontCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (f.k) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.checkBox.setChecked(bean.isSelected);
            holder.name.setText(bean.resName + ".ttf");

            try {
                holder.preview.setTypeface(Typeface.createFromFile(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + bean.resFullName));
                holder.preview.setText(xB.b().a(f.getApplicationContext(), R.string.design_manager_font_description_example_sentence));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void a(ImageCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            if (f.k) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isNinePatch()) {
                holder.ninePatchIcon.setVisibility(View.VISIBLE);
            } else {
                holder.ninePatchIcon.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            Glide.with(f.getApplicationContext()).load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + bean.resFullName)
                    .asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.image));
            holder.name.setText(bean.resName);
            holder.checkBox.setChecked(bean.isSelected);
        }

        public void a(MoreBlockCollectionViewHolder holder, int position) {
            MoreBlockCollectionBean bean = (MoreBlockCollectionBean) currentCollectionTypeItems.get(position);
            if (f.k) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.name.setText(bean.name);
            holder.checkBox.setChecked(bean.isSelected);
            holder.blockArea.removeAllViews();
            position = 0;
            Rs header = new Rs(f.getBaseContext(), 0, bean.spec, " ", "definedFunc");
            holder.blockArea.addView(header);
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
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "b", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'd') {
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "d", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 's') {
                        block = new Rs(f.getBaseContext(), position + 1, var6.substring(3), "s", "getVar");
                        break;
                    }

                    if (var6.charAt(1) == 'm') {
                        String var8 = var6.substring(var6.lastIndexOf(".") + 1);
                        String var7 = var6.substring(var6.indexOf(".") + 1, var6.lastIndexOf("."));
                        var6 = kq.a(var7);
                        block = new Rs(f.getBaseContext(), position + 1, var8, var6, kq.b(var7), "getVar");
                        break;
                    }
                }

                holder.blockArea.addView(block);
                header.a((Ts) header.V.get(position), block);
                ++position;
            }
        }

        public void a(SoundCollectionViewHolder holder, int position) {
            ProjectResourceBean bean = (ProjectResourceBean) currentCollectionTypeItems.get(position);
            String soundFilePath = wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + bean.resFullName;
            if (f.k) {
                holder.album.setVisibility(View.GONE);
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                f.a(soundFilePath, holder.album);
                holder.album.setVisibility(View.VISIBLE);
                holder.deleteContainer.setVisibility(View.GONE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            int soundPositionInS = bean.curSoundPosition / 1000;
            if (bean.totalSoundDuration == 0) {
                bean.totalSoundDuration = f.b(soundFilePath);
            }

            int totalSoundDurationInS = bean.totalSoundDuration / 1000;
            holder.currentPosition.setText(String.format("%d:%02d", soundPositionInS / 60, soundPositionInS % 60));
            holder.totalDuration.setText(String.format("%d:%02d", totalSoundDurationInS / 60, totalSoundDurationInS % 60));
            holder.checkBox.setChecked(bean.isSelected);
            holder.name.setText(bean.resName);
            if (f.E == position) {
                if (f.C != null && f.C.isPlaying()) {
                    holder.play.setImageResource(R.drawable.ic_pause_blue_circle_48dp);
                } else {
                    holder.play.setImageResource(R.drawable.circled_play_96_blue);
                }
            } else {
                holder.play.setImageResource(R.drawable.circled_play_96_blue);
            }

            holder.playbackProgress.setMax(bean.totalSoundDuration / 100);
            holder.playbackProgress.setProgress(bean.curSoundPosition / 100);
        }

        public void a(WidgetCollectionViewHolder holder, int position) {
            WidgetCollectionBean bean = (WidgetCollectionBean) currentCollectionTypeItems.get(position);
            if (f.k) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
                holder.widgetIcon.setVisibility(View.GONE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
                holder.widgetIcon.setVisibility(View.VISIBLE);
            }

            if (bean.isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.widgetIcon.setImageResource(ViewBean.getViewTypeResId(((ViewBean) bean.widgets.get(0)).type));
            holder.name.setText(bean.name);
            holder.checkBox.setChecked(bean.isSelected);
        }

        public void a(ArrayList<? extends SelectableBean> beans) {
            currentCollectionTypeItems = beans;
            if (beans.size() <= 0) {
                f.w.setVisibility(View.VISIBLE);
            } else {
                f.w.setVisibility(View.GONE);
            }
        }

        @Override
        // RecyclerView.Adapter#getItemViewType(int)
        public int b(int position) {
            position = currentViewType;

            if (position == 0) {
                return 0;
            } else if (position == 1) {
                return 1;
            } else if (position == 2) {
                return 2;
            } else if (position == 3) {
                return 3;
            } else if (position == 4) {
                return 4;
            } else {
                return 5;
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public RecyclerView.v b(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new ImageCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
            } else if (viewType == 1) {
                return new SoundCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_sound_list_item, parent, false));
            } else if (viewType == 2) {
                return new FontCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_font_list_item, parent, false));
            } else if (viewType == 3) {
                return new WidgetCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_widget_list_item, parent, false));
            } else if (viewType == 4) {
                return new BlockCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_block_list_item, parent, false));
            } else {
                return new MoreBlockCollectionViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_collection_more_block_list_item, parent, false));
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(RecyclerView.v holder, int position) {
            // RecyclerView.ViewHolder#getItemViewType()
            int viewType = holder.i();

            if (viewType == 0) {
                a((ImageCollectionViewHolder) holder, position);
            } else if (viewType == 1) {
                a((SoundCollectionViewHolder) holder, position);
            } else if (viewType == 2) {
                a((FontCollectionViewHolder) holder, position);
            } else if (viewType == 3) {
                a((WidgetCollectionViewHolder) holder, position);
            } else if (viewType == 4) {
                a((BlockCollectionViewHolder) holder, position);
            } else if (viewType == 5) {
                a((MoreBlockCollectionViewHolder) holder, position);
            }
        }

        private class BlockCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView blockIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public BlockCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                blockIcon = itemView.findViewById(R.id.img_block);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_block_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.h(lastSelectedItemPosition);
                });
                cardView.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class FontCollectionViewHolder extends RecyclerView.v {

            public final CollectionAdapter A;
            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView fontIcon;
            public final ImageView delete;
            public final TextView name;
            public final TextView preview;
            public final LinearLayout deleteContainer;

            public FontCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                A = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                fontIcon = itemView.findViewById(R.id.img_font);
                fontIcon.setVisibility(View.GONE);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_font_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                preview = itemView.findViewById(R.id.tv_font_preview);
                preview.setText(xB.b().a(var1.f.getApplicationContext(), R.string.common_word_preview));
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.i(lastSelectedItemPosition);
                });
                cardView.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return false;
                });
            }
        }

        private class ImageCollectionViewHolder extends RecyclerView.v {

            public final CheckBox checkBox;
            public final TextView name;
            public final ImageView image;
            public final ImageView delete;
            public final ImageView ninePatchIcon;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public ImageCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                checkBox = itemView.findViewById(R.id.chk_select);
                name = itemView.findViewById(R.id.tv_image_name);
                image = itemView.findViewById(R.id.img);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                ninePatchIcon = itemView.findViewById(R.id.img_nine_patch);
                checkBox.setVisibility(View.GONE);
                image.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.j(lastSelectedItemPosition);
                });
                image.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class MoreBlockCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView delete;
            public final LinearLayout deleteContainer;
            public final TextView name;
            public final RelativeLayout blockArea;
            public final CollectionAdapter z;

            public MoreBlockCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                delete = itemView.findViewById(R.id.img_delete);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                name = itemView.findViewById(R.id.tv_block_name);
                blockArea = itemView.findViewById(R.id.block_area);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.k(lastSelectedItemPosition);
                });
                cardView.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class SoundCollectionViewHolder extends RecyclerView.v {

            public final ProgressBar playbackProgress;
            public final TextView totalDuration;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter D;
            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView album;
            public final ImageView delete;
            public final TextView name;
            public final ImageView play;
            public final TextView currentPosition;

            public SoundCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                D = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                album = itemView.findViewById(R.id.img_album);
                name = itemView.findViewById(R.id.tv_sound_name);
                play = itemView.findViewById(R.id.img_play);
                delete = itemView.findViewById(R.id.img_delete);
                currentPosition = itemView.findViewById(R.id.tv_currenttime);
                playbackProgress = itemView.findViewById(R.id.prog_playtime);
                totalDuration = itemView.findViewById(R.id.tv_endtime);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                play.setOnClickListener(v -> {
                    if (ManageCollectionActivity.this.k) {
                        ManageCollectionActivity.this.F = currentPosition;
                        ManageCollectionActivity.this.G = playbackProgress;
                        ManageCollectionActivity.this.a(currentCollectionTypeItems, j());
                    }
                });
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.l(lastSelectedItemPosition);
                });
                cardView.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        private class WidgetCollectionViewHolder extends RecyclerView.v {

            public final CardView cardView;
            public final CheckBox checkBox;
            public final ImageView widgetIcon;
            public final ImageView delete;
            public final TextView name;
            public final LinearLayout deleteContainer;
            public final CollectionAdapter z;

            public WidgetCollectionViewHolder(CollectionAdapter var1, View itemView) {
                super(itemView);
                z = var1;
                cardView = itemView.findViewById(R.id.layout_item);
                checkBox = itemView.findViewById(R.id.chk_select);
                widgetIcon = itemView.findViewById(R.id.img_widget);
                delete = itemView.findViewById(R.id.img_delete);
                name = itemView.findViewById(R.id.tv_widget_name);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                checkBox.setVisibility(View.GONE);
                cardView.setOnClickListener(v -> {
                    lastSelectedItemPosition = j();
                    if (ManageCollectionActivity.this.k) {
                        checkBox.setChecked(!checkBox.isChecked());
                        currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                        CollectionAdapter.this.c(lastSelectedItemPosition);
                        return;
                    }
                    ManageCollectionActivity.this.m(lastSelectedItemPosition);
                });
                cardView.setOnLongClickListener(v -> {
                    ManageCollectionActivity.this.a(true);
                    lastSelectedItemPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    currentCollectionTypeItems.get(lastSelectedItemPosition).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }
    }
}
