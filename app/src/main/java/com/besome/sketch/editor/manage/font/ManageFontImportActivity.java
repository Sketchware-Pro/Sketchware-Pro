package com.besome.sketch.editor.manage.font;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.xB;

public class ManageFontImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public TextView A;
    public ImageView k;
    public TextView l;
    public TextView m;
    public TextView n;
    public TextView o;
    public RecyclerView p;
    public EasyDeleteEditText q;
    public EditText r;
    public CheckBox s;
    public a t;
    public ArrayList<ProjectResourceBean> u;
    public ArrayList<ProjectResourceBean> v;
    public int w;
    public int x;
    public Button y;
    public QB z;

    public final boolean b(String var1) {
        for (ProjectResourceBean resourceBean : u) {
            if (!resourceBean.resName.equals(var1)) {
                return false;
            }
        }
        return true;
    }

    public final void f(int var1) {
        String var2 = v.get(var1).resFullName;
        A.setTypeface(Typeface.createFromFile(var2));
        A.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_description_example_sentence));
    }

    public final ArrayList<String> l() {
        ArrayList<String> var1 = new ArrayList<>();
        var1.add("app_icon");
        for (ProjectResourceBean projectResourceBean : v) {
            var1.add(projectResourceBean.resName);
        }
        return var1;
    }

    public final ArrayList<String> m() {
        ArrayList<String> var1 = new ArrayList<>();
        var1.add("app_icon");

        for (ProjectResourceBean projectResourceBean : u) {
            var1.add(projectResourceBean.resName);
        }
        return var1;
    }

    public final boolean n() {
        ArrayList<String> var1 = new ArrayList<>();
        for (ProjectResourceBean var3 : v) {
            if (var3.isDuplicateCollection) {
                var1.add(var3.resName);
            }
        }

        if (!var1.isEmpty()) {
            String var8 = xB.b().a(getApplicationContext(), R.string.common_message_name_unavailable);
            Iterator<String> var4 = var1.iterator();

            String var6;
            StringBuilder var7;
            for (var6 = ""; var4.hasNext(); var6 = var7.toString()) {
                String var5 = var4.next();
                String var9 = var6;
                if (!var6.isEmpty()) {
                    var9 = var6 + ", ";
                }

                var7 = new StringBuilder();
                var7.append(var9);
                var7.append(var5);
            }

            Context var12 = getApplicationContext();
            String var11 = var8 + "\n[" + var6 + "]";
            bB.a(var12, var11, 1).show();
            return true;
        } else {
            return false;
        }
    }

    public final boolean o() {
        return z.b();
    }

    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    public void onClick(View var1) {
        int var2 = var1.getId();
        if (var2 != R.id.btn_decide) {
            if (var2 != R.id.img_backbtn) {
                if (var2 == R.id.tv_sendbtn && !n()) {
                    Intent var5 = new Intent();
                    var5.putParcelableArrayListExtra("results", v);
                    setResult(-1, var5);
                    finish();
                }
            } else {
                onBackPressed();
            }
        } else {
            String var6 = r.getText().toString();
            if (!o()) {
                ProjectResourceBean var7 = v.get(x);
                r.setText(var7.resName);
                return;
            }

            if (!s.isChecked()) {
                ProjectResourceBean var3 = v.get(x);
                var3.resName = var6;
                var3.isDuplicateCollection = false;
                z.a(l());
                t.notifyDataSetChanged();
            } else {
                ProjectResourceBean var4;
                for (var2 = 0; var2 < v.size(); var4.isDuplicateCollection = false) {
                    var4 = v.get(var2);
                    StringBuilder var8 = new StringBuilder();
                    var8.append(var6);
                    var8.append("_");
                    ++var2;
                    var8.append(var2);
                    var4.resName = var8.toString();
                }

                z.a(l());
                t.notifyDataSetChanged();
            }
        }

    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        if (!super.j()) {
            finish();
        }

        setContentView(R.layout.manage_font_import);
        k = findViewById(R.id.img_backbtn);
        k.setOnClickListener(this);
        l = findViewById(R.id.tv_currentnum);
        m = findViewById(R.id.tv_totalnum);
        n = findViewById(R.id.tv_sendbtn);
        n.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        n.setOnClickListener(this);
        o = findViewById(R.id.tv_samename);
        o.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_title_apply_same_naming));
        t = new a(this);
        p = findViewById(R.id.recycler_list);
        p.setHasFixedSize(true);
        p.setAdapter(t);
        LinearLayoutManager var2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        p.setLayoutManager(var2);
        u = getIntent().getParcelableArrayListExtra("project_fonts");
        v = getIntent().getParcelableArrayListExtra("selected_collections");
        w = v.size();
        l.setText(String.valueOf(1));
        m.setText(String.valueOf(w));
        q = findViewById(R.id.ed_input);
        r = q.getEditText();
        r.setText(v.get(0).resName);
        r.setPrivateImeOptions("defaultInputmode=english;");
        q.setHint(xB.b().a(this, R.string.design_manager_font_hint_enter_font_name));
        z = new QB(getApplicationContext(), q.getTextInputLayout(), uq.b, m(), l());
        s = findViewById(R.id.chk_samename);
        s.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                z.c(null);
                z.a(v.size());
            } else {
                z.c(v.get(x).resName);
                z.a(1);
            }
        });
        y = findViewById(R.id.btn_decide);
        y.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        y.setOnClickListener(this);
        A = findViewById(R.id.text_font);
    }

    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
        p();
        f(0);
        t.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    public final void p() {
        ArrayList<ProjectResourceBean> var1 = new ArrayList<>();
        ArrayList<ProjectResourceBean> var2 = new ArrayList<>();
        Iterator<ProjectResourceBean> var3 = v.iterator();

        ProjectResourceBean var4;
        while (var3.hasNext()) {
            var4 = var3.next();
            if (b(var4.resName)) {
                var4.isDuplicateCollection = true;
                var1.add(var4);
            } else {
                var4.isDuplicateCollection = false;
                var2.add(var4);
            }
        }

        if (!var1.isEmpty()) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), 1).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), 0).show();
        }

        v = new ArrayList<>();

        for (ProjectResourceBean projectResourceBean : var1) {
            var4 = projectResourceBean;
            v.add(var4);
        }

        for (ProjectResourceBean var6 : var2) {
            v.add(var6);
        }
    }

    public class a extends RecyclerView.Adapter<a> {
        public final ManageFontImportActivity c;

        public a(ManageFontImportActivity var1) {
            c = var1;
        }

        public int getItemCount() {
            return c.v.size();
        }

        public void onBindViewHolder(a var1, int var2) {
            if (c.v.get(var2).isDuplicateCollection) {
                var1.u.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                var1.u.setImageResource(R.drawable.ic_ok_48dp);
            }

            if (var2 == c.x) {
                var1.v.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                var1.v.setBackgroundResource(R.drawable.bg_outline);
            }
            var1.v.setImageResource(R.drawable.ic_font_48dp);
            var1.w.setText(c.v.get(var2).resName);
        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            return new a(this, LayoutInflater.from(var1.getContext()).inflate(R.layout.manage_import_list_item, var1, false));
        }

        public class a extends RecyclerView.ViewHolder {
            public LinearLayout t;
            public ImageView u;
            public ImageView v;
            public TextView w;
            public final a x;

            public a(a var1, View var2) {
                super(var2);
                x = var1;
                t = var2.findViewById(R.id.layout_item);
                u = var2.findViewById(R.id.img_conflict);
                v = var2.findViewById(R.id.img);
                w = var2.findViewById(R.id.tv_name);
                v.setOnClickListener(view -> {
                    if (!a.a.a.mB.a()) {
                        ManageFontImportActivity.this.x = getLayoutPosition();
                        f(ManageFontImportActivity.this.x);
                        l.setText(String.valueOf(getLayoutPosition() + 1));
                        r.setText(ManageFontImportActivity.this.v.get(getLayoutPosition()).resName);
                        if (s.isChecked()) {
                            z.c(null);
                            z.a(ManageFontImportActivity.this.v.size());
                        } else {
                            z.c(ManageFontImportActivity.this.v.get(getLayoutPosition()).resName);
                            z.a(1);
                        }

                        ManageFontImportActivity.this.t.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
