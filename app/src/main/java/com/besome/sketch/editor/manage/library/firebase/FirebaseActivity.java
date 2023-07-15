package com.besome.sketch.editor.manage.library.firebase;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.dv;
import a.a.a.ev;
import a.a.a.fv;
import a.a.a.gv;
import a.a.a.hv;
import a.a.a.iC;
import a.a.a.iv;
import a.a.a.kv;
import a.a.a.lC;
import a.a.a.lv;
import a.a.a.mB;
import a.a.a.mv;
import a.a.a.nv;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class FirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public TextView A;
    public LinearLayout B;
    public ImageView C;
    public ImageView D;
    public Button E;
    public Button F;
    public String[] G;
    public String[] H;
    public nv J;
    public ProjectLibraryBean K;
    public b M;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public String t;
    public CardView u;
    public TextView v;
    public TextView w;
    public TextView x;
    public TextView y;
    public TextView z;
    public final int k = 0;
    public final int l = 1;
    public final int m = 2;
    public int I = 0;
    public ArrayList<HashMap<String, Object>> L = new ArrayList<>();

    class a implements Comparator<HashMap<String, Object>> {
        public a() {
        }

        @Override
        public int compare(HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2) {
            return yB.c(hashMap, "sc_id").compareTo(yB.c(hashMap2, "sc_id")) * (-1);
        }
    }

    public final void f(int i) {
        nv nvVar = this.J;
        if (nvVar != null) {
            nvVar.a();
        }
        if (i == 2) {
            this.w.setText(xB.b().a(getApplicationContext(), 2131625029));
            this.x.setText(xB.b().a(getApplicationContext(), 2131625031));
        } else {
            this.w.setText(xB.b().a(getApplicationContext(), 2131625040, Integer.valueOf(i + 1)));
            this.x.setText(xB.b().a(getApplicationContext(), 2131625008));
        }
        if (i == 0) {
            this.C.setVisibility(0);
            this.v.setVisibility(8);
        } else {
            this.C.setVisibility(8);
            this.v.setVisibility(0);
        }
        this.z.setText(this.G[i]);
        this.A.setText(this.H[i]);
        this.B.removeAllViews();
        if (i == 0) {
            this.u.setVisibility(0);
            lv lvVar = new lv(this);
            this.B.addView(lvVar);
            lvVar.setData(this.K);
            this.J = lvVar;
        } else if (i == 1) {
            this.u.setVisibility(0);
            mv mvVar = new mv(this);
            this.B.addView(mvVar);
            mvVar.setData(this.K);
            this.J = mvVar;
        } else if (i == 2) {
            this.u.setVisibility(8);
            kv kvVar = new kv(this);
            this.B.addView(kvVar);
            kvVar.setData(this.K);
            this.J = kvVar;
        }
        if (this.J.getDocUrl().isEmpty()) {
            this.E.setVisibility(8);
        } else {
            this.E.setVisibility(0);
        }
        if (i > 0) {
            this.F.setVisibility(8);
        } else {
            this.F.setVisibility(0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(2130771982, 2130771983);
    }

    public final void l() {
        this.L = new ArrayList<>();
        Iterator<HashMap<String, Object>> it = lC.a().iterator();
        while (it.hasNext()) {
            HashMap<String, Object> next = it.next();
            String c = yB.c(next, "sc_id");
            if (!this.t.equals(c)) {
                iC iCVar = new iC(c);
                iCVar.i();
                if (iCVar.d().useYn.equals("Y")) {
                    next.put("firebase_setting", iCVar.d().clone());
                    this.L.add(next);
                }
            }
        }
        if (this.L.size() > 0) {
            Collections.sort(this.L, new a());
        }
        this.M.c();
    }

    public final void m() {
        if (this.J.isValid()) {
            this.J.a(this.K);
            int i = this.I;
            if (i < 2) {
                int i2 = i + 1;
                this.I = i2;
                f(i2);
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("firebase", this.K);
            setResult(-1, intent);
            finish();
        }
    }

    public final void n() {
        if (this.J.getDocUrl().isEmpty()) {
            return;
        }
        if (GB.h(getApplicationContext())) {
            try {
                String docUrl = this.J.getDocUrl();
                Uri parse = Uri.parse("googlechrome://navigate?url=" + docUrl);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setData(parse);
                intent.addFlags(1);
                intent.addFlags(2);
                intent.addFlags(64);
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                q();
                return;
            }
        }
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
    }

    public final void o() {
        if (GB.h(getApplicationContext())) {
            try {
                Uri parse = Uri.parse("googlechrome://navigate?url=https://console.firebase.google.com");
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setData(parse);
                intent.addFlags(1);
                intent.addFlags(2);
                intent.addFlags(64);
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                q();
                return;
            }
        }
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
    }

    @Override
    public void onBackPressed() {
        int i = this.I;
        if (i > 0) {
            int i2 = i - 1;
            this.I = i2;
            f(i2);
            return;
        }
        setResult(0);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        switch (view.getId()) {
            case 2131230841:
                n();
                return;
            case 2131230944:
                o();
                return;
            case 2131231113:
            case 2131232081:
                onBackPressed();
                return;
            case 2131232059:
                m();
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(2130771982, 2130771983);
        setContentView(2131427539);
        if (bundle != null) {
            this.t = bundle.getString("sc_id");
        } else {
            this.t = getIntent().getStringExtra("sc_id");
        }
        this.n = xB.b().a(getApplicationContext(), 2131625226);
        this.o = xB.b().a(getApplicationContext(), 2131625228);
        this.p = xB.b().a(getApplicationContext(), 2131625230);
        this.q = xB.b().a(getApplicationContext(), 2131625225);
        this.r = xB.b().a(getApplicationContext(), 2131625227);
        this.s = xB.b().a(getApplicationContext(), 2131625229);
        this.G = new String[]{this.n, this.o, this.p};
        this.H = new String[]{this.q, this.r, this.s};
        this.u = (CardView) findViewById(2131230944);
        this.u.setOnClickListener(this);
        this.y = (TextView) findViewById(2131231987);
        this.y.setText(xB.b().a(getApplicationContext(), 2131625207));
        this.v = (TextView) findViewById(2131232081);
        this.v.setText(xB.b().a(getApplicationContext(), 2131625014));
        this.v.setOnClickListener(this);
        this.x = (TextView) findViewById(2131232059);
        this.x.setText(xB.b().a(getApplicationContext(), 2131625008));
        this.x.setOnClickListener(this);
        this.w = (TextView) findViewById(2131232257);
        this.z = (TextView) findViewById(2131232179);
        this.A = (TextView) findViewById(2131232176);
        this.D = (ImageView) findViewById(2131231090);
        this.D.setImageResource(2131166245);
        this.C = (ImageView) findViewById(2131231113);
        this.C.setOnClickListener(this);
        this.E = (Button) findViewById(2131230841);
        this.E.setText(xB.b().a(getApplicationContext(), 2131624999));
        this.E.setOnClickListener(this);
        this.F = (Button) findViewById(2131230832);
        this.F.setText(xB.b().a(getApplicationContext(), 2131625201));
        this.F.setOnClickListener(new dv(this));
        this.B = (LinearLayout) findViewById(2131231331);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.K = (ProjectLibraryBean) getIntent().getParcelableExtra("firebase");
        f(this.I);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.t);
        super.onSaveInstanceState(bundle);
    }

    public final void p() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625252));
        aBVar.a(2131166245);
        View a2 = wB.a((Context) this, 2131427550);
        RecyclerView recyclerView = (RecyclerView) a2.findViewById(2131231440);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.M = new b();
        recyclerView.setAdapter(this.M);
        recyclerView.setItemAnimator(new ci());
        l();
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625035), new gv(this, aBVar));
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new hv(this, aBVar));
        aBVar.show();
    }

    public final void q() {
        aB aBVar = new aB(this);
        aBVar.a(2131165415);
        aBVar.b(xB.b().a(getApplicationContext(), 2131626412));
        aBVar.a(xB.b().a(getApplicationContext(), 2131625629));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new ev(this, aBVar));
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new fv(this, aBVar));
        aBVar.show();
    }

    public class b extends RecyclerView.a<a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            public a(View view) {
                super(view);
                this.t = (LinearLayout) view.findViewById(2131231613);
                this.v = (TextView) view.findViewById(2131231614);
                this.u = (CircleImageView) view.findViewById(2131231151);
                this.w = (TextView) view.findViewById(2131230780);
                this.x = (TextView) view.findViewById(2131231579);
                this.y = (TextView) view.findViewById(2131231618);
                this.z = (ImageView) view.findViewById(2131231181);
                this.t.setOnClickListener(new iv(this, b.this));
            }

            public final void c(int i) {
                if (FirebaseActivity.this.L.size() <= 0) {
                    return;
                }
                Iterator it = FirebaseActivity.this.L.iterator();
                while (it.hasNext()) {
                    ((HashMap) it.next()).put("selected", false);
                }
                ((HashMap) FirebaseActivity.this.L.get(i)).put("selected", true);
                FirebaseActivity.this.M.c();
            }
        }

        public b() {
        }

        @Override
        public void b(a aVar, int i) {
            Uri fromFile;
            HashMap hashMap = (HashMap) FirebaseActivity.this.L.get(i);
            String c = yB.c(hashMap, "sc_id");
            aVar.u.setImageResource(2131165521);
            if (yB.a(hashMap, "custom_icon")) {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.a(FirebaseActivity.this.getApplicationContext(), FirebaseActivity.this.getPackageName() + ".provider", new File(wq.e() + File.separator + c, "icon.png"));
                } else {
                    fromFile = Uri.fromFile(new File(wq.e() + File.separator + c, "icon.png"));
                }
                aVar.u.setImageURI(fromFile);
            }
            aVar.w.setText(yB.c(hashMap, "my_app_name"));
            aVar.v.setText(yB.c(hashMap, "my_ws_name"));
            aVar.x.setText(yB.c(hashMap, "my_sc_pkg_name"));
            aVar.y.setText(String.format("%s(%s)", yB.c(hashMap, "sc_ver_name"), yB.c(hashMap, "sc_ver_code")));
            if (yB.a(hashMap, "selected")) {
                aVar.z.setVisibility(0);
            } else {
                aVar.z.setVisibility(8);
            }
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427549, viewGroup, false));
        }

        @Override
        public int a() {
            return FirebaseActivity.this.L.size();
        }
    }
}
