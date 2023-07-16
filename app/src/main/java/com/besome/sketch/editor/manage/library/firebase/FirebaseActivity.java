package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iC;
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
import mod.hey.studios.util.Helper;

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
            this.w.setText(xB.b().a(getApplicationContext(), R.string.common_word_review));
            this.x.setText(xB.b().a(getApplicationContext(), R.string.common_word_save));
        } else {
            this.w.setText(xB.b().a(getApplicationContext(), R.string.common_word_step, Integer.valueOf(i + 1)));
            this.x.setText(xB.b().a(getApplicationContext(), R.string.common_word_next));
        }
        if (i == 0) {
            this.C.setVisibility(View.VISIBLE);
            this.v.setVisibility(View.GONE);
        } else {
            this.C.setVisibility(View.GONE);
            this.v.setVisibility(View.VISIBLE);
        }
        this.z.setText(this.G[i]);
        this.A.setText(this.H[i]);
        this.B.removeAllViews();
        if (i == 0) {
            this.u.setVisibility(View.VISIBLE);
            lv lvVar = new lv(this);
            this.B.addView(lvVar);
            lvVar.setData(this.K);
            this.J = lvVar;
        } else if (i == 1) {
            this.u.setVisibility(View.VISIBLE);
            mv mvVar = new mv(this);
            this.B.addView(mvVar);
            mvVar.setData(this.K);
            this.J = mvVar;
        } else if (i == 2) {
            this.u.setVisibility(View.GONE);
            kv kvVar = new kv(this);
            this.B.addView(kvVar);
            kvVar.setData(this.K);
            this.J = kvVar;
        }
        if (this.J.getDocUrl().isEmpty()) {
            this.E.setVisibility(View.GONE);
        } else {
            this.E.setVisibility(View.VISIBLE);
        }
        if (i > 0) {
            this.F.setVisibility(View.GONE);
        } else {
            this.F.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
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
        this.M.notifyDataSetChanged();
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
            setResult(Activity.RESULT_OK, intent);
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(parse);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                q();
                return;
            }
        }
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
    }

    public final void o() {
        if (GB.h(getApplicationContext())) {
            try {
                Uri parse = Uri.parse("googlechrome://navigate?url=https://console.firebase.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(parse);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                q();
                return;
            }
        }
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
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
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_open_doc:
                n();
                return;
            case R.id.cv_console:
                o();
                return;
            case R.id.img_backbtn:
            case R.id.tv_prevbtn:
                onBackPressed();
                return;
            case R.id.tv_nextbtn:
                m();
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        setContentView(R.layout.manage_library_firebase);
        if (bundle != null) {
            this.t = bundle.getString("sc_id");
        } else {
            this.t = getIntent().getStringExtra("sc_id");
        }
        this.n = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_title);
        this.o = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_title);
        this.p = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_title);
        this.q = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_desc);
        this.r = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_desc);
        this.s = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_desc);
        this.G = new String[]{this.n, this.o, this.p};
        this.H = new String[]{this.q, this.r, this.s};
        this.u = (CardView) findViewById(R.id.cv_console);
        this.u.setOnClickListener(this);
        this.y = (TextView) findViewById(R.id.tv_goto_console);
        this.y.setText(xB.b().a(getApplicationContext(), R.string.design_library_firebase_button_goto_firebase_console));
        this.v = (TextView) findViewById(R.id.tv_prevbtn);
        this.v.setText(xB.b().a(getApplicationContext(), R.string.common_word_prev));
        this.v.setOnClickListener(this);
        this.x = (TextView) findViewById(R.id.tv_nextbtn);
        this.x.setText(xB.b().a(getApplicationContext(), R.string.common_word_next));
        this.x.setOnClickListener(this);
        this.w = (TextView) findViewById(R.id.tv_toptitle);
        this.z = (TextView) findViewById(R.id.tv_step_title);
        this.A = (TextView) findViewById(R.id.tv_step_desc);
        this.D = (ImageView) findViewById(R.id.icon);
        this.D.setImageResource(R.drawable.widget_firebase);
        this.C = (ImageView) findViewById(R.id.img_backbtn);
        this.C.setOnClickListener(this);
        this.E = (Button) findViewById(R.id.btn_open_doc);
        this.E.setText(xB.b().a(getApplicationContext(), R.string.common_word_go_to_documentation));
        this.E.setOnClickListener(this);
        this.F = (Button) findViewById(R.id.btn_import);
        this.F.setText(xB.b().a(getApplicationContext(), R.string.design_library_button_import_from_other_project));
        this.F.setOnClickListener(v -> p());
        this.B = (LinearLayout) findViewById(R.id.layout_container);
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
        aBVar.b(xB.b().a(getApplicationContext(), R.string.design_library_title_select_project));
        aBVar.a(R.drawable.widget_firebase);
        View a2 = wB.a((Context) this, R.layout.manage_library_popup_project_selector);
        RecyclerView recyclerView = (RecyclerView) a2.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.M = new b();
        recyclerView.setAdapter(this.M);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        l();
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            if (!mB.a()) {
                if (M.c >= 0) {
                    K = (ProjectLibraryBean) L.get(M.c).get("firebase_setting");
                    I = 2;
                    f(I);
                    aBVar.dismiss();
                }
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void q() {
        aB aBVar = new aB(this);
        aBVar.a(R.drawable.chrome_96);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.title_compatible_chrome_browser));
        aBVar.a(xB.b().a(getApplicationContext(), R.string.message_compatible_chrome_brower));
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_ok), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public class b extends RecyclerView.Adapter<b.a> {
        public int c = -1;

        class a extends RecyclerView.ViewHolder {
            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            public a(@NonNull View itemView) {
                super(itemView);
                this.t = (LinearLayout) itemView.findViewById(R.id.project_layout);
                this.v = (TextView) itemView.findViewById(R.id.project_name);
                this.u = (CircleImageView) itemView.findViewById(R.id.img_icon);
                this.w = (TextView) itemView.findViewById(R.id.app_name);
                this.x = (TextView) itemView.findViewById(R.id.package_name);
                this.y = (TextView) itemView.findViewById(R.id.project_version);
                this.z = (ImageView) itemView.findViewById(R.id.img_selected);
                this.t.setOnClickListener(v -> {
                    if (!mB.a()) {
                        c = getLayoutPosition();
                        c(c);
                    }
                });
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
                FirebaseActivity.this.M.notifyDataSetChanged();
            }
        }

        public b() {
        }

        @Override
        public void onBindViewHolder(@NonNull a holder, int position) {
            Uri fromFile;
            HashMap hashMap = (HashMap) FirebaseActivity.this.L.get(position);
            String c = yB.c(hashMap, "sc_id");
            holder.u.setImageResource(R.drawable.default_icon);
            if (yB.a(hashMap, "custom_icon")) {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.getUriForFile(FirebaseActivity.this.getApplicationContext(), FirebaseActivity.this.getPackageName() + ".provider", new File(wq.e() + File.separator + c, "icon.png"));
                } else {
                    fromFile = Uri.fromFile(new File(wq.e() + File.separator + c, "icon.png"));
                }
                holder.u.setImageURI(fromFile);
            }
            holder.w.setText(yB.c(hashMap, "my_app_name"));
            holder.v.setText(yB.c(hashMap, "my_ws_name"));
            holder.x.setText(yB.c(hashMap, "my_sc_pkg_name"));
            holder.y.setText(String.format("%s(%s)", yB.c(hashMap, "sc_ver_name"), yB.c(hashMap, "sc_ver_code")));
            if (yB.a(hashMap, "selected")) {
                holder.z.setVisibility(View.VISIBLE);
            } else {
                holder.z.setVisibility(View.GONE);
            }
        }

        @Override
        @NonNull
        public a onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new a(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return FirebaseActivity.this.L.size();
        }
    }
}
