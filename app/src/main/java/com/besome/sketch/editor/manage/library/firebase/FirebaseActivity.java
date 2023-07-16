package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
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
        if (J != null) {
            J.a();
        }
        w.setText(i == 2 ? xB.b().a(getApplicationContext(), R.string.common_word_review)
                : xB.b().a(getApplicationContext(), R.string.common_word_step, Integer.valueOf(i + 1)));
        x.setText(i == 2 ? xB.b().a(getApplicationContext(), R.string.common_word_save)
                : xB.b().a(getApplicationContext(), R.string.common_word_next));
        C.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        v.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
        z.setText(G[i]);
        A.setText(H[i]);
        B.removeAllViews();
        if (i == 0) {
            u.setVisibility(View.VISIBLE);
            lv lvVar = new lv(this);
            B.addView(lvVar);
            lvVar.setData(K);
            J = lvVar;
        } else if (i == 1) {
            u.setVisibility(View.VISIBLE);
            mv mvVar = new mv(this);
            B.addView(mvVar);
            mvVar.setData(K);
            J = mvVar;
        } else if (i == 2) {
            u.setVisibility(View.GONE);
            kv kvVar = new kv(this);
            B.addView(kvVar);
            kvVar.setData(K);
            J = kvVar;
        }
        E.setVisibility(J.getDocUrl().isEmpty() ? View.GONE : View.VISIBLE);
        F.setVisibility(i > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    public final void l() {
        L = new ArrayList<>();
        for (HashMap<String, Object> project : lC.a()) {
            String projectSc_id = yB.c(project, "sc_id");
            if (!t.equals(projectSc_id)) {
                iC iCVar = new iC(projectSc_id);
                iCVar.i();
                if (iCVar.d().useYn.equals("Y")) {
                    project.put("firebase_setting", iCVar.d().clone());
                    L.add(project);
                }
            }
        }
        if (L.size() > 0) {
            Collections.sort(L, new a());
        }
        M.notifyDataSetChanged();
    }

    public final void m() {
        if (J.isValid()) {
            J.a(K);
            if (I < 2) {
                f(++I);
            } else {
                Intent intent = new Intent();
                intent.putExtra("firebase", K);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    public final void n() {
        String docUrl = J.getDocUrl();
        if (!docUrl.isEmpty()) {
            if (GB.h(getApplicationContext())) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("googlechrome://navigate?url=" + docUrl));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    q();
                }
            } else {
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
            }
        }
    }

    public final void o() {
        if (GB.h(getApplicationContext())) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("googlechrome://navigate?url=https://console.firebase.google.com"));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                q();
            }
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (I > 0) {
            f(--I);
        } else {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
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
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        setContentView(R.layout.manage_library_firebase);
        if (bundle != null) {
            t = bundle.getString("sc_id");
        } else {
            t = getIntent().getStringExtra("sc_id");
        }
        n = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_title);
        o = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_title);
        p = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_title);
        q = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step1_desc);
        r = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step2_desc);
        s = xB.b().a(getApplicationContext(), R.string.design_library_firebase_setting_step3_desc);
        G = new String[]{n, o, p};
        H = new String[]{q, r, s};
        u = findViewById(R.id.cv_console);
        u.setOnClickListener(this);
        y = findViewById(R.id.tv_goto_console);
        y.setText(xB.b().a(getApplicationContext(), R.string.design_library_firebase_button_goto_firebase_console));
        v = findViewById(R.id.tv_prevbtn);
        v.setText(xB.b().a(getApplicationContext(), R.string.common_word_prev));
        v.setOnClickListener(this);
        x = findViewById(R.id.tv_nextbtn);
        x.setText(xB.b().a(getApplicationContext(), R.string.common_word_next));
        x.setOnClickListener(this);
        w = findViewById(R.id.tv_toptitle);
        z = findViewById(R.id.tv_step_title);
        A = findViewById(R.id.tv_step_desc);
        D = findViewById(R.id.icon);
        D.setImageResource(R.drawable.widget_firebase);
        C = findViewById(R.id.img_backbtn);
        C.setOnClickListener(this);
        E = findViewById(R.id.btn_open_doc);
        E.setText(xB.b().a(getApplicationContext(), R.string.common_word_go_to_documentation));
        E.setOnClickListener(this);
        F = findViewById(R.id.btn_import);
        F.setText(xB.b().a(getApplicationContext(), R.string.design_library_button_import_from_other_project));
        F.setOnClickListener(v -> p());
        B = findViewById(R.id.layout_container);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        K = getIntent().getParcelableExtra("firebase");
        f(I);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", t);
        super.onSaveInstanceState(bundle);
    }

    public final void p() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.design_library_title_select_project));
        aBVar.a(R.drawable.widget_firebase);
        View a2 = wB.a(this, R.layout.manage_library_popup_project_selector);
        RecyclerView recyclerView = a2.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        M = new b();
        recyclerView.setAdapter(M);
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
                t = itemView.findViewById(R.id.project_layout);
                v = itemView.findViewById(R.id.project_name);
                u = itemView.findViewById(R.id.img_icon);
                w = itemView.findViewById(R.id.app_name);
                x = itemView.findViewById(R.id.package_name);
                y = itemView.findViewById(R.id.project_version);
                z = itemView.findViewById(R.id.img_selected);
                t.setOnClickListener(v -> {
                    if (!mB.a()) {
                        c = getLayoutPosition();
                        c(c);
                    }
                });
            }

            public final void c(int i) {
                if (L.size() > 0) {
                    for (HashMap<String, Object> next : L) {
                        next.put("selected", false);
                    }
                    L.get(i).put("selected", true);
                    M.notifyDataSetChanged();
                }
            }
        }

        public b() {
        }

        @Override
        public void onBindViewHolder(@NonNull a holder, int position) {
            Uri fromFile;
            HashMap<String, Object> hashMap = L.get(position);
            String c = yB.c(hashMap, "sc_id");
            holder.u.setImageResource(R.drawable.default_icon);
            if (yB.a(hashMap, "custom_icon")) {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", new File(wq.e() + File.separator + c, "icon.png"));
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
            return L.size();
        }
    }
}
