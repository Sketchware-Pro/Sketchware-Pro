package com.besome.sketch.editor.manage.library.admob;

import android.annotation.SuppressLint;
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

@SuppressLint("ResourceType")
public class AdmobActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public final int k = 0;
    public final int l = 1;
    public final int m = 2;
    public final int n = 3;
    public TextView A;
    public ImageView B;
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
    public TextView C;
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

    private void f(int position) {
        if (position == 3) {
            z.setText(Helper.getResString(2131625029));
            A.setText(Helper.getResString(2131625031));
        } else {
            z.setText(xB.b().a(this, 2131625040, position + 1));
            A.setText(Helper.getResString(2131625008));
        }

        if (position == 0) {
            B.setVisibility(View.VISIBLE);
            y.setVisibility(View.GONE);
        } else {
            B.setVisibility(View.GONE);
            y.setVisibility(View.VISIBLE);
        }

        E.setText(H[position]);
        F.setText(I[position]);
        G.removeAllViews();
        switch (position) {
            case 0:
                Iu setAdUnitItem = new Iu(this);
                G.addView(setAdUnitItem);
                setAdUnitItem.setData(L);
                K = setAdUnitItem;
                break;

            case 1:
                x.setVisibility(View.GONE);
                Nu var4 = new Nu(this);
                G.addView(var4);
                var4.setData(L);
                K = var4;
                break;

            case 2:
                x.setVisibility(View.GONE);
                Tu var3 = new Tu(this);
                G.addView(var3);
                var3.setData(L);
                K = var3;
                break;

            case 3:
                x.setVisibility(View.GONE);
                Ku var2 = new Ku(this);
                G.addView(var2);
                var2.setData(L);
                K = var2;
                break;
        }

        if (K.getDocUrl().isEmpty()) {
            N.setVisibility(View.GONE);
        } else {
            N.setVisibility(View.VISIBLE);
        }

        if (position > 0) {
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
            int position = J;
            if (position < 3) {
                ++position;
                J = position;
                f(position);
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
        int position = J;
        if (position > 0) {
            --position;
            J = position;
            f(position);
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
            case 2131230944:
                o();
                break;
            case 2131232059:
                n();
                break;
            case 2131232081:
                onBackPressed();
        }
    }

    private void o() {
        if (GB.h(this)) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("https://apps.admob.com/v2/home"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                u();
            }
        } else {
            bB.a(this, Helper.getResString(2131624932), 0).show();
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

        H = new String[]{
                Helper.getResString(2131625185),
                Helper.getResString(2131625187),
                Helper.getResString(2131625189),
                Helper.getResString(2131625191)
        };
        I = new String[]{
                Helper.getResString(2131625184),
                Helper.getResString(2131625186),
                Helper.getResString(2131625188),
                Helper.getResString(2131625190)
        };
        x = findViewById(2131230944);
        x.setOnClickListener(this);
        C = findViewById(2131231987);
        C.setText(Helper.getResString(2131625161));
        y = findViewById(2131232081);
        y.setText(Helper.getResString(2131625014));
        y.setOnClickListener(this);
        D = findViewById(2131231090);
        D.setImageResource(2131166234);
        z = findViewById(2131232257);
        A = findViewById(2131232059);
        A.setText(Helper.getResString(2131625008));
        A.setOnClickListener(this);
        B = findViewById(2131231113);
        B.setOnClickListener(Helper.getBackPressedClickListener(this));
        E = findViewById(2131232179);
        F = findViewById(2131232176);
        N = findViewById(2131230841);
        N.setText(Helper.getResString(2131624999));
        N.setOnClickListener(this);
        O = findViewById(2131230832);
        O.setText(Helper.getResString(2131625201));
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
            if (GB.h(this)) {
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
                bB.a(this, Helper.getResString(2131624932), 0).show();
            }

        }
    }

    private void s() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(2131625252));
        dialog.a(2131166234);
        View rootView = wB.a(this, 2131427550);
        RecyclerView recyclerView = rootView.findViewById(2131231440);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Q = new ProjectsAdapter();
        recyclerView.setAdapter(Q);
        recyclerView.setItemAnimator(new ci());
        m();
        dialog.a(rootView);
        dialog.b(Helper.getResString(2131625035), view -> {
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
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void u() {
        aB dialog = new aB(this);
        dialog.a(2131165415);
        dialog.b(Helper.getResString(2131626412));
        dialog.a(Helper.getResString(2131625629));
        dialog.b(Helper.getResString(2131625010), view -> {
            if (!mB.a()) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }


    public class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {

        public int c;

        public ProjectsAdapter() {
            c = -1;
        }

        @Override
        public int a() {
            return P.size();
        }

        @Override
        @SuppressLint("ResourceType")
        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = P.get(position);
            String var4 = yB.c(projectMap, "sc_id");
            String iconDir = wq.e() + File.separator + var4;
            viewHolder.u.setImageResource(2131165521);
            if (yB.a(projectMap, "custom_icon")) {
                Uri iconUri;
                if (VERSION.SDK_INT >= 24) {
                    iconUri = FileProvider.a(getApplicationContext(), getPackageName() + ".provider", new File(iconDir, "icon.png"));
                } else {
                    iconUri = Uri.fromFile(new File(iconDir, "icon.png"));
                }

                viewHolder.u.setImageURI(iconUri);
            }

            viewHolder.w.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.v.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.x.setText(yB.c(projectMap, "my_sc_pkg_name"));
            var4 = String.format("%s(%s)", yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code"));
            viewHolder.y.setText(var4);

            viewHolder.z.setVisibility(yB.a(projectMap, "selected") ? View.VISIBLE : View.GONE);
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427549, parent, false));
        }

        public class ViewHolder extends v implements View.OnClickListener {

            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            @SuppressLint("ResourceType")
            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(2131231613);
                v = itemView.findViewById(2131231614);
                u = itemView.findViewById(2131231151);
                w = itemView.findViewById(2131230780);
                x = itemView.findViewById(2131231579);
                y = itemView.findViewById(2131231618);
                z = itemView.findViewById(2131231181);
                t.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (!mB.a() && view.getId() == 2131231613) {
                    ProjectsAdapter.this.c = ProjectsAdapter.ViewHolder.this.j();
                    c(ProjectsAdapter.this.c);
                }
            }

            private void c(int index) {
                if (P.size() > 0) {

                    for (HashMap<String, Object> stringObjectHashMap : P) {
                        stringObjectHashMap.put("selected", false);
                    }

                    P.get(index).put("selected", true);
                    Q.c();
                }
            }
        }
    }

}
