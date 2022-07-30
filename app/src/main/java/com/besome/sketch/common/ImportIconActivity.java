package com.besome.sketch.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.WB;
import a.a.a.iB;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;

public class ImportIconActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public Toolbar k;
    public RecyclerView l;
    public Button m;
    public Button n;
    public Button q;
    public Button r;
    public Button s;
    public EditText t;
    public ArrayList<String> v;
    public WB w;
    public a o = null;
    public int p = -1;
    public ArrayList<Pair<String, String>> u = new ArrayList<>();

    public final int m() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 60;
    }

    public final boolean n() {
        return new oB().e(wq.f());
    }

    public final void o() {
        KB.a(this, "icons" + File.separator + "icon_pack.zip", wq.f());
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();

            if (id == R.id.btn_accept) {
                if (w.b() && o.c >= 0) {
                    Intent intent = new Intent();
                    intent.putExtra("iconName", t.getText().toString());
                    intent.putExtra("iconPath", u.get(o.c).second);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            } else if (id == R.id.btn_black) {
                g(0);
            } else if (id == R.id.btn_cancel) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            } else if (id == R.id.btn_grey) {
                g(1);
            } else if (id == R.id.btn_white) {
                g(2);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((GridLayoutManager) l.getLayoutManager()).d(m());
        l.requestLayout();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_icon);

        k = findViewById(R.id.toolbar);
        a(k);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.design_manager_icon_actionbar_title));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        v = getIntent().getStringArrayListExtra("imageNames");
        m = findViewById(R.id.btn_accept);
        m.setText(xB.b().a(getApplicationContext(), R.string.common_word_accept));
        n = findViewById(R.id.btn_cancel);
        n.setText(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        m.setOnClickListener(this);
        n.setOnClickListener(this);
        l = findViewById(R.id.image_list);
        l.setHasFixedSize(true);
        l.setLayoutManager(new GridLayoutManager(getBaseContext(), m()));
        o = new a();
        l.setAdapter(o);
        q = findViewById(R.id.btn_black);
        r = findViewById(R.id.btn_grey);
        s = findViewById(R.id.btn_white);
        q.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_black));
        r.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_grey));
        s.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_import_icon_button_white));
        q.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        t = findViewById(R.id.ed_input);
        ((TextInputLayout) findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.design_manager_icon_hint_enter_icon_name));
        w = new WB(getApplicationContext(), findViewById(R.id.ti_input), uq.b, v);
        t.setPrivateImeOptions("defaultInputmode=english;");
        k();
        new Handler().postDelayed(() -> new c(getApplicationContext()).execute(), 300L);
    }

    @Override
    public void onResume() {
        super.onResume();
        d.setScreenName(ImportIconActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    class a extends RecyclerView.a<a.a2> {

        public int c = -1;

        class a2 extends RecyclerView.v {

            public RelativeLayout t;
            public TextView u;
            public ImageView v;

            public a2(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.icon_bg);
                u = itemView.findViewById(R.id.tv_icon_name);
                v = itemView.findViewById(R.id.img);
                v.setOnClickListener(v -> {
                    if (!mB.a()) {
                        int lastSelectedPosition = ImportIconActivity.a.this.c;
                        ImportIconActivity.a.this.c = j();
                        // RecyclerView.Adapter<VH extends ViewHolder>#notifyItemChanged(int)
                        ImportIconActivity.a.this.c(ImportIconActivity.a.this.c);
                        // RecyclerView.Adapter<VH extends ViewHolder>#notifyItemChanged(int)
                        ImportIconActivity.a.this.c(lastSelectedPosition);
                        ImportIconActivity.this.f(ImportIconActivity.a.this.c);
                    }
                });
            }
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(a2 holder, int position) {
            if (position != c) {
                if (p == 2) {
                    holder.t.setBackgroundColor(0xffbdbdbd);
                } else {
                    holder.t.setBackgroundColor(Color.WHITE);
                }
            } else {
                holder.t.setBackgroundColor(0xffffccbc);
            }
            holder.u.setText(u.get(position).first);
            try {
                holder.v.setImageBitmap(iB.a(u.get(position).second, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public a2 b(ViewGroup parent, int viewType) {
            return new a2(LayoutInflater.from(parent.getContext()).inflate(R.layout.import_icon_list_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return u.size();
        }
    }

    class c extends MA {
        public c(Context context) {
            super(context);
            ImportIconActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            g(0);
        }

        @Override
        public void b() {
            publishProgress("Now processing..");
            if (!n()) {
                o();
            }
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public final void f(int i) {
        t.setText(u.get(i).first);
    }

    public final void g(int i) {
        if (p == i) {
            return;
        }
        p = i;
        if (i == 0) {
            q.setBackgroundColor(0xff33b8f5);
            r.setBackgroundColor(0xffe5e5e5);
            s.setBackgroundColor(0xffe5e5e5);
        } else if (i == 1) {
            q.setBackgroundColor(0xffe5e5e5);
            r.setBackgroundColor(0xff33b8f5);
            s.setBackgroundColor(0xffe5e5e5);
        } else if (i == 2) {
            q.setBackgroundColor(0xffe5e5e5);
            r.setBackgroundColor(0xffe5e5e5);
            s.setBackgroundColor(0xff33b8f5);
        }
        new b(getApplicationContext()).execute();
    }

    public final void l() {
        u = new ArrayList<>();
        int i = p;
        String color = "black";
        if (i != 0) {
            if (i == 1) {
                color = "grey";
            } else if (i == 2) {
                color = "white";
            }
        }
        String iconFolderName = "icon_" + color;
        for (String iconName : new File(wq.f() + File.separator + iconFolderName).list()) {
            u.add(new Pair<>(
                    iconName.substring(0, iconName.indexOf("_" + color)) + "_" + color,
                    wq.f() + File.separator + iconFolderName + File.separator + iconName
            ));
        }
    }

    class b extends MA {
        public b(Context context) {
            super(context);
            ImportIconActivity.this.a(this);
            k();
        }

        @Override
        public void a() {
            h();
            t.setText("");
            o.c = -1;
            o.c();
        }

        @Override
        public void b() {
            publishProgress("Now processing..");
            l();
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
