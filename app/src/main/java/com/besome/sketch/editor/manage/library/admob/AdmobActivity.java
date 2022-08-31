package com.besome.sketch.editor.manage.library.admob;

import android.app.Activity;
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
import com.sketchware.remod.R;

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
            z.setText(Helper.getResString(R.string.common_word_review));
            A.setText(Helper.getResString(R.string.common_word_save));
        } else {
            z.setText(xB.b().a(this, R.string.common_word_step, position + 1));
            A.setText(Helper.getResString(R.string.common_word_next));
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
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
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
                setResult(Activity.RESULT_OK, intent);
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
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onClick(View var1) {
        switch (var1.getId()) {
            case R.id.btn_open_doc:
                p();
                break;
            case R.id.cv_console:
                o();
                break;
            case R.id.tv_nextbtn:
                n();
                break;
            case R.id.tv_prevbtn:
                onBackPressed();
        }
    }

    private void o() {
        if (GB.h(this)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
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
            bB.a(this, Helper.getResString(R.string.common_message_check_network), 0).show();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        setContentView(R.layout.manage_library_admob);
        if (savedInstanceState != null) {
            w = savedInstanceState.getString("sc_id");
        } else {
            w = getIntent().getStringExtra("sc_id");
        }

        H = new String[]{
                Helper.getResString(R.string.design_library_admob_setting_step1_title),
                Helper.getResString(R.string.design_library_admob_setting_step2_title),
                Helper.getResString(R.string.design_library_admob_setting_step3_title),
                Helper.getResString(R.string.design_library_admob_setting_step4_title)
        };
        I = new String[]{
                Helper.getResString(R.string.design_library_admob_setting_step1_desc),
                Helper.getResString(R.string.design_library_admob_setting_step2_desc),
                Helper.getResString(R.string.design_library_admob_setting_step3_desc),
                Helper.getResString(R.string.design_library_admob_setting_step4_desc)
        };
        x = findViewById(R.id.cv_console);
        x.setOnClickListener(this);
        C = findViewById(R.id.tv_goto_console);
        C.setText(Helper.getResString(R.string.design_library_admob_button_goto_setting));
        y = findViewById(R.id.tv_prevbtn);
        y.setText(Helper.getResString(R.string.common_word_prev));
        y.setOnClickListener(this);
        D = findViewById(R.id.icon);
        D.setImageResource(R.drawable.widget_admob);
        z = findViewById(R.id.tv_toptitle);
        A = findViewById(R.id.tv_nextbtn);
        A.setText(Helper.getResString(R.string.common_word_next));
        A.setOnClickListener(this);
        B = findViewById(R.id.img_backbtn);
        B.setOnClickListener(Helper.getBackPressedClickListener(this));
        E = findViewById(R.id.tv_step_title);
        F = findViewById(R.id.tv_step_desc);
        N = findViewById(R.id.btn_open_doc);
        N.setText(Helper.getResString(R.string.common_word_go_to_documentation));
        N.setOnClickListener(this);
        O = findViewById(R.id.btn_import);
        O.setText(Helper.getResString(R.string.design_library_button_import_from_other_project));
        O.setOnClickListener(view -> s());
        G = findViewById(R.id.layout_container);
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
                    Intent var2 = new Intent(Intent.ACTION_VIEW);
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
                bB.a(this, Helper.getResString(R.string.common_message_check_network), 0).show();
            }

        }
    }

    private void s() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.design_library_title_select_project));
        dialog.a(R.drawable.widget_admob);
        View rootView = wB.a(this, R.layout.manage_library_popup_project_selector);
        RecyclerView recyclerView = rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Q = new ProjectsAdapter();
        recyclerView.setAdapter(Q);
        recyclerView.setItemAnimator(new ci());
        m();
        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_select), view -> {
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
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void u() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), view -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
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
        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = P.get(position);
            String var4 = yB.c(projectMap, "sc_id");
            String iconDir = wq.e() + File.separator + var4;
            viewHolder.u.setImageResource(R.drawable.default_icon);
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
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        public class ViewHolder extends v implements View.OnClickListener {

            public LinearLayout t;
            public CircleImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public ImageView z;

            public ViewHolder(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.project_layout);
                v = itemView.findViewById(R.id.project_name);
                u = itemView.findViewById(R.id.img_icon);
                w = itemView.findViewById(R.id.app_name);
                x = itemView.findViewById(R.id.package_name);
                y = itemView.findViewById(R.id.project_version);
                z = itemView.findViewById(R.id.img_selected);
                t.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (!mB.a() && view.getId() == R.id.project_layout) {
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
