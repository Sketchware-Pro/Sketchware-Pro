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
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.QB;
import a.a.a.Tt;
import a.a.a.Ut;
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
    public int w = 0;
    public int x = 0;
    public Button y;
    public QB z;

    public ManageFontImportActivity() {
    }

    public final boolean b(String var1) {
        Iterator var2 = this.u.iterator();

        do {
            if (!var2.hasNext()) {
                return false;
            }
        } while (!((ProjectResourceBean) var2.next()).resName.equals(var1));

        return true;
    }

    public final void f(int var1) {
        String var2 = ((ProjectResourceBean) this.v.get(var1)).resFullName;
        this.A.setTypeface(Typeface.createFromFile(var2));
        this.A.setText(xB.b().a(this.getApplicationContext(), 2131625256));
    }

    public final ArrayList<String> l() {
        ArrayList var1 = new ArrayList();
        var1.add("app_icon");
        Iterator var2 = this.v.iterator();

        while (var2.hasNext()) {
            var1.add(((ProjectResourceBean) var2.next()).resName);
        }

        return var1;
    }

    public final ArrayList<String> m() {
        ArrayList var1 = new ArrayList();
        var1.add("app_icon");
        Iterator var2 = this.u.iterator();

        while (var2.hasNext()) {
            var1.add(((ProjectResourceBean) var2.next()).resName);
        }

        return var1;
    }

    public final boolean n() {
        ArrayList var1 = new ArrayList();
        Iterator var2 = this.v.iterator();

        while (var2.hasNext()) {
            ProjectResourceBean var3 = (ProjectResourceBean) var2.next();
            if (var3.isDuplicateCollection) {
                var1.add(var3.resName);
            }
        }

        if (var1.size() > 0) {
            String var8 = xB.b().a(this.getApplicationContext(), 2131624950);
            Iterator var4 = var1.iterator();

            String var6;
            StringBuilder var7;
            for (var6 = ""; var4.hasNext(); var6 = var7.toString()) {
                String var5 = (String) var4.next();
                String var9 = var6;
                if (var6.length() > 0) {
                    StringBuilder var10 = new StringBuilder();
                    var10.append(var6);
                    var10.append(", ");
                    var9 = var10.toString();
                }

                var7 = new StringBuilder();
                var7.append(var9);
                var7.append(var5);
            }

            Context var12 = this.getApplicationContext();
            StringBuilder var11 = new StringBuilder();
            var11.append(var8);
            var11.append("\n[");
            var11.append(var6);
            var11.append("]");
            bB.a(var12, var11.toString(), 1).show();
            return true;
        } else {
            return false;
        }
    }

    public final boolean o() {
        return this.z.b();
    }

    public void onBackPressed() {
        this.setResult(-1);
        super.onBackPressed();
    }

    public void onClick(View var1) {
        int var2 = var1.getId();
        if (var2 != 2131230816) {
            if (var2 != 2131231113) {
                if (var2 == 2131232151 && !this.n()) {
                    Intent var5 = new Intent();
                    var5.putParcelableArrayListExtra("results", this.v);
                    this.setResult(-1, var5);
                    this.finish();
                }
            } else {
                this.onBackPressed();
            }
        } else {
            String var6 = this.r.getText().toString();
            if (!this.o()) {
                ProjectResourceBean var7 = (ProjectResourceBean) this.v.get(this.x);
                this.r.setText(var7.resName);
                return;
            }

            if (!this.s.isChecked()) {
                ProjectResourceBean var3 = (ProjectResourceBean) this.v.get(this.x);
                var3.resName = var6;
                var3.isDuplicateCollection = false;
                this.z.a(this.l());
                this.t.notifyDataSetChanged();
            } else {
                ProjectResourceBean var4;
                for (var2 = 0; var2 < this.v.size(); var4.isDuplicateCollection = false) {
                    var4 = (ProjectResourceBean) this.v.get(var2);
                    StringBuilder var8 = new StringBuilder();
                    var8.append(var6);
                    var8.append("_");
                    ++var2;
                    var8.append(var2);
                    var4.resName = var8.toString();
                }

                this.z.a(this.l());
                this.t.notifyDataSetChanged();
            }
        }

    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        if (!super.j()) {
            this.finish();
        }

        this.setContentView(2131427522);
        this.k = (ImageView) this.findViewById(2131231113);
        this.k.setOnClickListener(this);
        this.l = (TextView) this.findViewById(2131231930);
        this.m = (TextView) this.findViewById(2131232258);
        this.n = (TextView) this.findViewById(2131232151);
        this.n.setText(xB.b().a(this.getApplicationContext(), 2131625002).toUpperCase());
        this.n.setOnClickListener(this);
        this.o = (TextView) this.findViewById(2131232138);
        this.o.setText(xB.b().a(this.getApplicationContext(), 2131625262));
        this.t = new a(this);
        this.p = (RecyclerView) this.findViewById(2131231662);
        this.p.setHasFixedSize(true);
        this.p.setAdapter(this.t);
        LinearLayoutManager var2 = new LinearLayoutManager(this.getApplicationContext(), 0, false);
        this.p.setLayoutManager(var2);
        this.u = this.getIntent().getParcelableArrayListExtra("project_fonts");
        this.v = this.getIntent().getParcelableArrayListExtra("selected_collections");
        this.w = this.v.size();
        this.l.setText(String.valueOf(1));
        this.m.setText(String.valueOf(this.w));
        this.q = (EasyDeleteEditText) this.findViewById(2131230990);
        this.r = this.q.getEditText();
        this.r.setText(((ProjectResourceBean) this.v.get(0)).resName);
        this.r.setPrivateImeOptions("defaultInputmode=english;");
        this.q.setHint(xB.b().a(this, 2131625259));
        this.z = new QB(this.getApplicationContext(), this.q.getTextInputLayout(), uq.b, this.m(), this.l());
        this.s = (CheckBox) this.findViewById(2131230892);
        this.s.setOnCheckedChangeListener(new Tt(this));
        this.y = (Button) this.findViewById(2131230816);
        this.y.setText(xB.b().a(this.getApplicationContext(), 2131625255));
        this.y.setOnClickListener(this);
        this.A = (TextView) this.findViewById(2131231793);
    }

    public void onPostCreate(Bundle var1) {
        super.onPostCreate(var1);
        this.p();
        this.f(0);
        this.t.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        if (!super.j()) {
            this.finish();
        }

        super.d.setScreenName(ManageFontImportActivity.class.getSimpleName().toString());
        super.d.send((new HitBuilders.ScreenViewBuilder()).build());
    }

    public final void p() {
        ArrayList var1 = new ArrayList();
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.v.iterator();

        ProjectResourceBean var4;
        while (var3.hasNext()) {
            var4 = (ProjectResourceBean) var3.next();
            if (this.b(var4.resName)) {
                var4.isDuplicateCollection = true;
                var1.add(var4);
            } else {
                var4.isDuplicateCollection = false;
                var2.add(var4);
            }
        }

        if (var1.size() > 0) {
            bB.b(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131625277), 1).show();
        } else {
            bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131625278), 0).show();
        }

        this.v = new ArrayList();
        Iterator var5 = var1.iterator();

        while (var5.hasNext()) {
            var4 = (ProjectResourceBean) var5.next();
            this.v.add(var4);
        }

        Iterator var7 = var2.iterator();

        while (var7.hasNext()) {
            ProjectResourceBean var6 = (ProjectResourceBean) var7.next();
            this.v.add(var6);
        }

    }

    public class a extends RecyclerView.Adapter<a> {
        public final ManageFontImportActivity c;

        public a(ManageFontImportActivity var1) {
            this.c = var1;
        }

        public int getItemCount() {
            return this.c.v.size();
        }

        public void onBindViewHolder(a var1, int var2) {
            if (((ProjectResourceBean) this.c.v.get(var2)).isDuplicateCollection) {
                var1.u.setImageResource(2131165704);
            } else {
                var1.u.setImageResource(2131165801);
            }

            if (var2 == this.c.x) {
                var1.v.setBackgroundResource(2131165348);
            } else {
                var1.v.setBackgroundResource(2131165345);
            }

            var1.v.setImageResource(2131165755);
            var1.w.setText(((ProjectResourceBean) this.c.v.get(var2)).resName);
        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            return new a(this, LayoutInflater.from(var1.getContext()).inflate(2131427530, var1, false));
        }

        public class a extends RecyclerView.ViewHolder {
            public LinearLayout t;
            public ImageView u;
            public ImageView v;
            public TextView w;
            public final a x;

            public a(a var1, View var2) {
                super(var2);
                this.x = var1;
                this.t = (LinearLayout) var2.findViewById(2131231359);
                this.u = (ImageView) var2.findViewById(2131231126);
                this.v = (ImageView) var2.findViewById(2131231102);
                this.w = (TextView) var2.findViewById(2131232055);
                this.v.setOnClickListener(new Ut(this, var1));
            }
        }
    }
}
