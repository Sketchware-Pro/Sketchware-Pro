package com.besome.sketch.editor;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.property.ViewPropertyItems;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R.drawable;
import com.sketchware.remod.R.id;
import com.sketchware.remod.R.layout;
import com.sketchware.remod.R.menu;
import com.sketchware.remod.R.style;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import a.a.a.Kw;
import a.a.a.cC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.ro;
import a.a.a.tx;
import a.a.a.zs;

public class PropertyActivity extends BaseAppCompatActivity implements Kw {
    public Toolbar k;
    public LinearLayout l;
    public CustomScrollView m;
    public ProjectFileBean n;
    public ViewBean o;
    public boolean p = false;
    public String q;
    public LinearLayout r;
    public ArrayList<Integer> s;
    public RecyclerView t;
    public ViewPropertyItems u;
    public int v = 0;
    public ro w;

    public PropertyActivity() {
    }

    public void a(String var1, Object var2) {
    }

    public void l() {
        this.u.setProjectFileBean(this.n);
        this.u.a(this.q, this.o);
        this.l.addView(this.u);
    }

    public final void m() {
        ArrayList var1 = jC.d(this.q).m();
        tx var2;
        if (var1.indexOf(this.o.layout.backgroundResource) < 0) {
            this.o.layout.backgroundResource = null;
            var2 = (tx) this.l.findViewWithTag("property_background_resource");
            if (var2 != null) {
                var2.setValue(this.o.layout.backgroundResource);
            }
        }

        ViewBean var6 = this.o;
        if (var6.type == 6 && var1.indexOf(var6.image.resName) < 0) {
            this.o.image.resName = "default_image";
            var2 = (tx) this.l.findViewWithTag("property_image");
            if (var2 != null) {
                var2.setValue(this.o.image.resName);
            }
        }

        Iterator var7 = jC.b(this.q).e().iterator();

        String var3;
        Iterator var8;
        while (var7.hasNext()) {
            var3 = (String) var7.next();
            var8 = jC.a(this.q).d(var3).iterator();

            while (var8.hasNext()) {
                ViewBean var4 = (ViewBean) var8.next();
                if (var4.type == 6 && var1.indexOf(var4.image.resName) < 0) {
                    var4.image.resName = "default_image";
                    this.p = true;
                }

                if (var1.indexOf(var4.layout.backgroundResource) < 0) {
                    var4.layout.backgroundResource = null;
                    this.p = true;
                }
            }
        }

        var7 = jC.b(this.q).d().iterator();

        while (var7.hasNext()) {
            var3 = (String) var7.next();
            Iterator var5 = jC.a(this.q).b(var3).entrySet().iterator();

            while (var5.hasNext()) {
                var8 = ((ArrayList) ((Map.Entry) var5.next()).getValue()).iterator();

                while (var8.hasNext()) {
                    BlockBean var9 = (BlockBean) var8.next();
                    if ("setImage".equals(var9.opCode)) {
                        if (var1.indexOf(var9.parameters.get(1)) < 0) {
                            var9.parameters.set(1, "default_image");
                        }
                    } else if ("setBgResource".equals(var9.opCode) && var1.indexOf(var9.parameters.get(1)) < 0) {
                        var9.parameters.set(1, "NONE");
                    }
                }
            }
        }

    }

    public void n() {
        this.r.setVisibility(8);
    }

    public void o() {
        ViewBean var1;
        if (this.o.id.equals("_fab")) {
            var1 = jC.a(this.q).h(this.n.getXmlName());
        } else {
            var1 = jC.a(this.q).c(this.n.getXmlName(), this.o.preId);
        }

        cC.c(this.q).a(this.n.getXmlName(), var1.clone(), this.o);
        var1.copy(this.o);
        Intent var2 = new Intent();
        var2.putExtra("view_id", this.o.id);
        var2.putExtra("is_edit_image", this.p);
        var2.putExtra("bean", var1);
        this.setResult(-1, var2);
        this.finish();
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 == 209 && var2 == -1 && jC.d(this.q) != null && var3 != null) {
            String var4 = var3.getStringExtra("sc_id");
            ArrayList var5 = var3.getParcelableArrayListExtra("result");
            if (jC.d(var4) != null) {
                jC.d(var4).b(var5);
                this.m();
            }
        }

    }

    public void onBackPressed() {
        this.u.i(this.o);
        this.o();
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(layout.property);
        if (!super.j()) {
            this.finish();
        }

        this.w = new ro(this.getApplicationContext());
        Toolbar var2 = (Toolbar) this.findViewById(id.toolbar);
        this.k = var2;
        this.setSupportActionBar(var2);
        this.findViewById(id.layout_main_logo).setVisibility(8);
        this.getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.k.setNavigationOnClickListener(new zs(this));
        this.k.setPopupTheme(style.ThemeOverlay_ToolbarMenu);
        ArrayList var4 = new ArrayList();
        this.s = var4;
        var4.add(1);
        this.s.add(2);
        this.s.add(3);
        this.s.add(4);
        RecyclerView var6 = (RecyclerView) this.findViewById(id.property_group_list);
        this.t = var6;
        var6.setHasFixedSize(true);
        LinearLayoutManager var7 = new LinearLayoutManager(this.getApplicationContext(), 1, false);
        this.t.setLayoutManager(var7);
        this.t.setAdapter(new a(this));
        this.m = (CustomScrollView) this.findViewById(id.scroll_view);
        this.l = (LinearLayout) this.findViewById(id.content);
        Parcelable var3;
        if (var1 != null) {
            this.q = var1.getString("sc_id");
            this.n = (ProjectFileBean) var1.getParcelable("project_file");
            var3 = var1.getParcelable("bean");
        } else {
            this.q = this.getIntent().getStringExtra("sc_id");
            this.n = (ProjectFileBean) this.getIntent().getParcelableExtra("project_file");
            var3 = this.getIntent().getParcelableExtra("bean");
        }

        this.o = (ViewBean) var3;
        ActionBar var8 = this.getSupportActionBar();
        String var5;
        if (this.o.id.charAt(0) == '_') {
            var5 = this.o.id.substring(1);
        } else {
            var5 = this.o.id;
        }

        var8.setTitle(var5);
        LinearLayout var9 = (LinearLayout) this.findViewById(id.layout_ads);
        this.r = var9;
        var9.setVisibility(8);
        super.j.h();
    }

    public boolean onCreateOptionsMenu(Menu var1) {
        this.getMenuInflater().inflate(menu.property_menu, var1);
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        if (var1.getItemId() == id.menu_add_image_res) {
            this.p();
        }

        return super.onOptionsItemSelected(var1);
    }

    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
        ViewPropertyItems var2 = new ViewPropertyItems(this);
        this.u = var2;
        var2.setOrientation(1);
        this.l();
    }

    public void onResume() {
        super.onResume();
        if (!super.j()) {
            this.finish();
        }

        if (super.j.h()) {
            this.n();
        }

        super.d.setScreenName(PropertyActivity.class.getSimpleName().toString());
        super.d.send((new HitBuilders.ScreenViewBuilder()).build());
    }

    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.q);
        var1.putParcelable("project_file", this.n);
        var1.putParcelable("bean", this.o);
        super.onSaveInstanceState(var1);
    }

    public void p() {
        Intent var1 = new Intent(this.getApplicationContext(), ManageImageActivity.class);
        var1.setFlags(536870912);
        var1.putExtra("sc_id", this.q);
        this.startActivityForResult(var1, 209);
    }

    public class a extends RecyclerView.Adapter<a> {
        public final PropertyActivity c;

        public a(PropertyActivity var1) {
            this.c = var1;
        }

        public int getItemCount() {
            return this.c.s.size();
        }

        public void onBindViewHolder(a var1, int var2) {
            (Integer) this.c.s.get(var2);
            ViewPropertyAnimatorCompat var3;
            ColorMatrix var4;
            ColorMatrixColorFilter var5;
            if (this.c.v == var2) {
                var3 = ViewCompat.animate(var1.t);
                var3.scaleX(1.1F);
                var3.scaleY(1.1F);
                var3.setDuration(300L);
                var3.setInterpolator(new AccelerateInterpolator());
                var3.start();
                var3 = ViewCompat.animate(var1.t);
                var3.scaleX(1.1F);
                var3.scaleY(1.1F);
                var3.setDuration(300L);
                var3.setInterpolator(new AccelerateInterpolator());
                var3.start();
                var1.v.setVisibility(0);
                var4 = new ColorMatrix();
                var4.setSaturation(1.0F);
                var5 = new ColorMatrixColorFilter(var4);
            } else {
                var3 = ViewCompat.animate(var1.t);
                var3.scaleX(1.0F);
                var3.scaleY(1.0F);
                var3.setDuration(300L);
                var3.setInterpolator(new DecelerateInterpolator());
                var3.start();
                var3 = ViewCompat.animate(var1.t);
                var3.scaleX(1.0F);
                var3.scaleY(1.0F);
                var3.setDuration(300L);
                var3.setInterpolator(new DecelerateInterpolator());
                var3.start();
                var1.v.setVisibility(8);
                var4 = new ColorMatrix();
                var4.setSaturation(0.0F);
                var5 = new ColorMatrixColorFilter(var4);
            }

            var1.t.setColorFilter(var5);
        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            return new a(this, LayoutInflater.from(var1.getContext()).inflate(layout.common_category_triangle_item, var1, false));
        }

        public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView t;
            public TextView u;
            public View v;
            public final a w;

            public a(a var1, View var2) {
                super(var2);
                this.w = var1;
                this.t = (ImageView) var2.findViewById(id.img_icon);
                this.u = (TextView) var2.findViewById(id.tv_name);
                View var3 = var2.findViewById(id.pointer_left);
                this.v = var3;
                var3.setBackgroundResource(drawable.triangle_point_left_primary);
                var2.setOnClickListener(this);
            }

            public void onClick(View var1) {
                if (!mB.a()) {
                    if (this.getLayoutPosition() != -1) {
                        int var2 = this.getLayoutPosition();
                        a var4 = this.w;
                        int var3 = var4.c.v;
                        if (var2 != var3) {
                            var4.notifyItemChanged(var3);
                            this.w.c.v = this.getLayoutPosition();
                            var4 = this.w;
                            var4.notifyItemChanged(var4.c.v);
                            var2 = (Integer) this.w.c.s.get(this.w.c.v);
                            if (var2 != 1) {
                                if (var2 != 2) {
                                    if (var2 != 3) {
                                        if (var2 == 4) {
                                            this.w.c.u.f(this.w.c.o);
                                        }
                                    } else {
                                        this.w.c.u.h(this.w.c.o);
                                    }
                                } else {
                                    this.w.c.u.g(this.w.c.o);
                                }
                            } else {
                                this.w.c.u.d(this.w.c.o);
                            }
                        }
                    }

                }
            }
        }
    }
}
