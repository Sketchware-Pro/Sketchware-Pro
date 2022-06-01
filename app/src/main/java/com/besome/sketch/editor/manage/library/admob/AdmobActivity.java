package com.besome.sketch.editor.manage.library.admob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.Iu;
import a.a.a.Ku;
import a.a.a.Nu;
import a.a.a.Tu;
import a.a.a.Uu;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.iC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import mod.hey.studios.util.Helper;

public class AdmobActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public final int k = 0;
    public final int l = 1;
    public final int m = 2;
    public final int n = 3;
    public TextView A;
    public ImageView B;
    public TextView C;
    public ImageView D;
    public TextView E;
    public TextView F;
    public LinearLayout G;
    public String[] H;
    public String[] I;
    public int J = 0;
    public Uu K;
    public Button N;
    public Button O;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    public CardView x;
    public TextView y;
    public TextView z;
    private ProjectLibraryBean L;
    private ArrayList<HashMap<String, Object>> P = new ArrayList<>();
    private ProjectsAdapter Q;

    private void f(int var1) {
        if (var1 == 3) {
            z.setText(xB.b().a(getApplicationContext(), 2131625029));
            A.setText(xB.b().a(getApplicationContext(), 2131625031));
        } else {
            z.setText(xB.b().a(getApplicationContext(), 2131625040, var1 + 1));
            A.setText(xB.b().a(getApplicationContext(), 2131625008));
        }

        if (var1 == 0) {
            B.setVisibility(View.VISIBLE);
            y.setVisibility(View.GONE);
        } else {
            B.setVisibility(View.GONE);
            y.setVisibility(View.VISIBLE);
        }

        E.setText(H[var1]);
        F.setText(I[var1]);
        G.removeAllViews();
        if (var1 != 0) {
            if (var1 != 1) {
                if (var1 != 2) {
                    if (var1 == 3) {
                        x.setVisibility(View.GONE);
                        Ku var2 = new Ku(this);
                        G.addView(var2);
                        var2.setData(L);
                        K = var2;
                    }
                } else {
                    x.setVisibility(View.GONE);
                    Tu var3 = new Tu(this);
                    G.addView(var3);
                    var3.setData(L);
                    K = var3;
                }
            } else {
                x.setVisibility(View.GONE);
                Nu var4 = new Nu(this);
                G.addView(var4);
                var4.setData(L);
                K = var4;
            }
        } else {
            x.setVisibility(View.VISIBLE);
            Iu setAdUnitItem = new Iu(this);
            G.addView(setAdUnitItem);
            setAdUnitItem.setData(L);
            K = setAdUnitItem;
        }

        if (K.getDocUrl().isEmpty()) {
            N.setVisibility(View.GONE);
        } else {
            N.setVisibility(View.VISIBLE);
        }

        if (var1 > 0) {
            O.setVisibility(View.GONE);
        } else {
            O.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(2130771982, 2130771983);
    }

    public void l() {
    }

    private void m() {
        P = new ArrayList<>();

        for (HashMap<String, Object> stringObjectHashMap : lC.a()) {
            String var3 = yB.c(stringObjectHashMap, "sc_id");
            if (!w.equals(var3)) {
                iC var4 = new iC(var3);
                var4.i();
                if (var4.b().useYn.equals("Y")) {
                    stringObjectHashMap.put("admob_setting", var4.b().clone());
                    P.add(stringObjectHashMap);
                }
            }
        }

        if (P.size() > 0) {
            //noinspection Java8ListSort
            Collections.sort(P, new ProjectComparator());
        }

        Q.c();
    }

    private void n() {
        if (K.isValid()) {
            K.a(L);
            int var1 = J;
            if (var1 < 3) {
                ++var1;
                J = var1;
                f(var1);
            } else {
                Intent intent = new Intent();
                intent.putExtra("admob", L);
                setResult(-1, intent);
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        int var1 = J;
        if (var1 > 0) {
            --var1;
            J = var1;
            f(var1);
        } else {
            setResult(0);
            finish();
        }

    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case 2131230841:
                p();
                break;
            case 2131232059:
                n();
                break;
            case 2131232081:
                onBackPressed();
        }

    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(2130771982, 2130771983);
        setContentView(2131427532);
        if (savedInstanceState != null) {
            w = savedInstanceState.getString("sc_id");
        } else {
            w = getIntent().getStringExtra("sc_id");
        }

        o = xB.b().a(getApplicationContext(), 2131625185);
        p = xB.b().a(getApplicationContext(), 2131625187);
        q = xB.b().a(getApplicationContext(), 2131625189);
        r = xB.b().a(getApplicationContext(), 2131625191);
        s = xB.b().a(getApplicationContext(), 2131625184);
        t = xB.b().a(getApplicationContext(), 2131625186);
        u = xB.b().a(getApplicationContext(), 2131625188);
        v = xB.b().a(getApplicationContext(), 2131625190);
        H = new String[]{o, p, q, r};
        I = new String[]{s, t, u, v};
        x = findViewById(2131230944);
        x.setOnClickListener(this);
        C = findViewById(2131231987);
        C.setText(xB.b().a(getApplicationContext(), 2131625161));
        y = findViewById(2131232081);
        y.setText(xB.b().a(getApplicationContext(), 2131625014));
        y.setOnClickListener(this);
        D = findViewById(2131231090);
        D.setImageResource(2131166234);
        z = findViewById(2131232257);
        A = findViewById(2131232059);
        A.setText(xB.b().a(getApplicationContext(), 2131625008));
        A.setOnClickListener(this);
        B = findViewById(2131231113);
        B.setOnClickListener(Helper.getBackPressedClickListener(this));
        E = findViewById(2131232179);
        F = findViewById(2131232176);
        N = findViewById(2131230841);
        N.setText(xB.b().a(getApplicationContext(), 2131624999));
        N.setOnClickListener(this);
        O = findViewById(2131230832);
        O.setText(xB.b().a(getApplicationContext(), 2131625201));
        O.setOnClickListener(view -> s());
        G = findViewById(2131231331);
    }

    @Override
    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
        L = getIntent().getParcelableExtra("admob");
        f(J);
    }

    @Override
    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", w);
        super.onSaveInstanceState(var1);
    }

    private void p() {
        if (!K.getDocUrl().isEmpty()) {
            if (GB.h(getApplicationContext())) {
                try {
                    Uri var1 = Uri.parse(K.getDocUrl());
                    Intent var2 = new Intent("android.intent.action.VIEW");
                    var2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    var2.setData(var1);
                    var2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    var2.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    var2.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    startActivity(var2);
                } catch (Exception var3) {
                    var3.printStackTrace();
                    u();
                }
            } else {
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
            }

        }
    }

    private void s() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), 2131625252));
        dialog.a(2131166234);
        View var2 = wB.a(this, 2131427550);
        @SuppressLint("ResourceType") RecyclerView recyclerView = var2.findViewById(2131231440);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Q = new ProjectsAdapter();
        recyclerView.setAdapter(Q);
        recyclerView.setItemAnimator(new ci());
        m();
        dialog.a(var2);
        dialog.b(xB.b().a(getApplicationContext(), 2131625035), view -> {
            if (!mB.a()) {
                if (Q.c >= 0) {
                    HashMap<String, Object> projectMap = P.get(Q.c);
                    L = (ProjectLibraryBean) projectMap.get("admob_setting");
                    J = 3;
                    f(J);
                    dialog.dismiss();
                }
            }

        });
        dialog.a(xB.b().a(getApplicationContext(), 2131624974), view -> dialog.dismiss());
        dialog.show();
    }

    private void u() {
        aB dialog = new aB(this);
        dialog.a(2131165415);
        dialog.b(xB.b().a(getApplicationContext(), 2131626412));
        dialog.a(xB.b().a(getApplicationContext(), 2131625629));
        dialog.b(xB.b().a(getApplicationContext(), 2131625010), view -> {
            if (!mB.a()) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), 2131624974), view -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    public class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {

        public int c;

        public ProjectsAdapter() {
            c = -1;
        }

        public int a() {
            return P.size();
        }

        @SuppressLint("ResourceType")
        public void b(ViewHolder var1, int var2) {
            HashMap<String, Object> var3 = P.get(var2);
            String var4 = yB.c(var3, "sc_id");
            var1.u.setImageResource(2131165521);
            if (yB.a(var3, "custom_icon")) {
                Uri var8;
                if (VERSION.SDK_INT >= 24) {
                    Context var5 = getApplicationContext();
                    StringBuilder var6 = new StringBuilder();
                    var6.append(getPackageName());
                    var6.append(".provider");
                    String var7 = var6.toString();
                    var6 = new StringBuilder();
                    var6.append(wq.e());
                    var6.append(File.separator);
                    var6.append(var4);
                    var8 = FileProvider.a(var5, var7, new File(var6.toString(), "icon.png"));
                } else {
                    String var9 = wq.e() + File.separator + var4;
                    var8 = Uri.fromFile(new File(var9, "icon.png"));
                }

                var1.u.setImageURI(var8);
            }

            var1.w.setText(yB.c(var3, "my_app_name"));
            var1.v.setText(yB.c(var3, "my_ws_name"));
            var1.x.setText(yB.c(var3, "my_sc_pkg_name"));
            var4 = String.format("%s(%s)", yB.c(var3, "sc_ver_name"), yB.c(var3, "sc_ver_code"));
            var1.y.setText(var4);
            if (yB.a(var3, "selected")) {
                var1.z.setVisibility(View.VISIBLE);
            } else {
                var1.z.setVisibility(View.GONE);
            }

        }

        @Override
        public ViewHolder b(ViewGroup var1, int var2) {
            return new ViewHolder(LayoutInflater.from(var1.getContext()).inflate(2131427549, var1, false));
        }

        public class ViewHolder extends v {

            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            @SuppressLint("ResourceType")
            public ViewHolder(View var2) {
                super(var2);
                t = var2.findViewById(2131231613);
                v = var2.findViewById(2131231614);
                u = var2.findViewById(2131231151);
                w = var2.findViewById(2131230780);
                x = var2.findViewById(2131231579);
                y = var2.findViewById(2131231618);
                z = var2.findViewById(2131231181);
                t.setOnClickListener(view -> {
                    if (!mB.a()) {
                        ProjectsAdapter.this.c = ProjectsAdapter.ViewHolder.this.j();
                        c(ProjectsAdapter.this.c);
                    }
                });
            }

            private void c(int var1) {
                if (P.size() > 0) {

                    for (HashMap<String, Object> stringObjectHashMap : P) {
                        stringObjectHashMap.put("selected", false);
                    }

                    P.get(var1).put("selected", true);
                    Q.c();
                }
            }
        }
    }

}
