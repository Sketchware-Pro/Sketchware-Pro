package com.besome.sketch.editor.manage.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;

public class ManageImageImportActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public ImageView A;
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

    public final ArrayList<String> l() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.v.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final ArrayList<String> m() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.u.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final boolean n() {
        ArrayList arrayList = new ArrayList();
        Iterator<ProjectResourceBean> it = this.v.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isDuplicateCollection) {
                arrayList.add(next.resName);
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        String a2 = xB.b().a(getApplicationContext(), 2131624950);
        Iterator it2 = arrayList.iterator();
        String str = "";
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (str.length() > 0) {
                str = str + ", ";
            }
            str = str + str2;
        }
        bB.a(getApplicationContext(), a2 + "\n[" + str + "]", 1).show();
        return true;
    }

    public final boolean o() {
        return this.z.b();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    public void onClick(View view) {
        if (!mB.a()) {
            int id2 = view.getId();
            if (id2 == 2131230816) {
                String obj = this.r.getText().toString();
                if (!o()) {
                    this.r.setText(this.v.get(this.x).resName);
                } else if (!this.s.isChecked()) {
                    ProjectResourceBean projectResourceBean = this.v.get(this.x);
                    projectResourceBean.resName = obj;
                    projectResourceBean.isDuplicateCollection = false;
                    this.z.a(l());
                    this.t.c();
                } else {
                    int i = 0;
                    while (i < this.v.size()) {
                        ProjectResourceBean projectResourceBean2 = this.v.get(i);
                        StringBuilder sb = new StringBuilder();
                        sb.append(obj);
                        sb.append("_");
                        i++;
                        sb.append(i);
                        projectResourceBean2.resName = sb.toString();
                        projectResourceBean2.isDuplicateCollection = false;
                    }
                    this.z.a(l());
                    this.t.c();
                }
            } else if (id2 == 2131231113) {
                onBackPressed();
            } else if (id2 == 2131232151 && !n()) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("results", this.v);
                setResult(-1, intent);
                finish();
            }
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.j()) {
            finish();
        }
        setContentView(2131427527);
        this.k = (ImageView) findViewById(2131231113);
        this.k.setOnClickListener(this);
        this.l = (TextView) findViewById(2131231930);
        this.m = (TextView) findViewById(2131232258);
        this.n = (TextView) findViewById(2131232151);
        this.n.setText(xB.b().a(getApplicationContext(), 2131625002).toUpperCase());
        this.n.setOnClickListener(this);
        this.o = (TextView) findViewById(2131232138);
        this.o.setText(xB.b().a(getApplicationContext(), 2131625273));
        this.t = new a();
        this.p = (RecyclerView) findViewById(2131231662);
        this.p.setHasFixedSize(true);
        this.p.setAdapter(this.t);
        this.p.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.u = getIntent().getParcelableArrayListExtra("project_images");
        this.v = getIntent().getParcelableArrayListExtra("selected_collections");
        this.w = this.v.size();
        this.l.setText(String.valueOf(1));
        this.m.setText(String.valueOf(this.w));
        this.q = (EasyDeleteEditText) findViewById(2131230990);
        this.r = this.q.getEditText();
        this.r.setText(this.v.get(0).resName);
        this.r.setPrivateImeOptions("defaultInputmode=english;");
        this.q.setHint(xB.b().a(this, 2131625268));
        this.z = new QB(getApplicationContext(), this.q.getTextInputLayout(), uq.b, m(), l());
        this.s = (CheckBox) findViewById(2131230892);
        this.s.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                z.c(null);
                ManageImageImportActivity manageImageImportActivity = ManageImageImportActivity.this;
                manageImageImportActivity.z.a(manageImageImportActivity.v.size());
                return;
            }
            ManageImageImportActivity manageImageImportActivity2 = ManageImageImportActivity.this;
            manageImageImportActivity2.z.c(((ProjectResourceBean) manageImageImportActivity2.v.get(this.x)).resName);
            z.a(1);
        });
        this.y = (Button) findViewById(2131230816);
        this.y.setText(xB.b().a(getApplicationContext(), 2131625255));
        this.y.setOnClickListener(this);
        this.A = (ImageView) findViewById(2131231102);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        p();
        f(0);
        this.t.c();
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageImageImportActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void p() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<ProjectResourceBean> it = this.v.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (b(next.resName)) {
                next.isDuplicateCollection = true;
                arrayList.add(next);
            } else {
                next.isDuplicateCollection = false;
                arrayList2.add(next);
            }
        }
        if (arrayList.size() > 0) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625277), 1).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625278), 0).show();
        }
        this.v = new ArrayList<>();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.v.add((ProjectResourceBean) it2.next());
        }
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            this.v.add((ProjectResourceBean) it3.next());
        }
    }

    public final void f(int i) {
        Glide.with(getApplicationContext()).load(this.v.get(i).resFullName).asBitmap().centerCrop().error(2131165831).into(new BitmapImageViewTarget(this.A) {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(bitmap, glideAnimation);
            }
        });
    }

    public final boolean b(String str) {
        Iterator<ProjectResourceBean> it = this.u.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    class a extends RecyclerView.a<ManageImageImportActivity.a.aa> {

        public a() {
        }

        /* renamed from: a */
        public void b(aa aVar, int i) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) ManageImageImportActivity.this.v.get(i);
            String str = projectResourceBean.resFullName;
            if (projectResourceBean.isDuplicateCollection) {
                aVar.u.setImageResource(2131165704);
            } else {
                aVar.u.setImageResource(2131165801);
            }
            if (i == ManageImageImportActivity.this.x) {
                aVar.v.setBackgroundResource(2131165348);
            } else {
                aVar.v.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Glide.with(ManageImageImportActivity.this.getApplicationContext()).load(str).asBitmap().centerCrop().error(2131165831).into(new BitmapImageViewTarget(aVar.v) {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    super.onResourceReady((Bitmap) bitmap, glideAnimation);
                }

            });
            aVar.w.setText(((ProjectResourceBean) ManageImageImportActivity.this.v.get(i)).resName);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public aa b(ViewGroup viewGroup, int i) {
            return new aa(LayoutInflater.from(viewGroup.getContext()).inflate(2131427530, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return ManageImageImportActivity.this.v.size();
        }

        /* renamed from: com.besome.sketch.editor.manage.image.ManageImageImportActivity$a$a  reason: collision with other inner class name */
        class aa extends RecyclerView.v {
            public LinearLayout t;
            public ImageView u;
            public ImageView v;
            public TextView w;

            public aa(View view) {
                super(view);
                this.t = (LinearLayout) view.findViewById(2131231359);
                this.u = (ImageView) view.findViewById(2131231126);
                this.v = (ImageView) view.findViewById(2131231102);
                this.w = (TextView) view.findViewById(2131232055);
                this.v.setOnClickListener(v -> {
                    if (!mB.a()) {
                        ManageImageImportActivity.a.aa aVar = ManageImageImportActivity.a.aa.this;
                        ManageImageImportActivity.this.x = aVar.j();
                        ManageImageImportActivity manageImageImportActivity = ManageImageImportActivity.this;
                        manageImageImportActivity.f(manageImageImportActivity.x);
                        ManageImageImportActivity.this.l.setText(String.valueOf(ManageImageImportActivity.this.x + 1));
                        ManageImageImportActivity.this.r.setText(((ProjectResourceBean) ManageImageImportActivity.this.v.get(ManageImageImportActivity.this.x)).resName);
                        if (ManageImageImportActivity.this.s.isChecked()) {
                            ManageImageImportActivity.this.z.c(null);
                            ManageImageImportActivity manageImageImportActivity2 = ManageImageImportActivity.this;
                            manageImageImportActivity2.z.a(manageImageImportActivity2.v.size());
                        } else {
                            ManageImageImportActivity manageImageImportActivity3 = ManageImageImportActivity.this;
                            manageImageImportActivity3.z.c(((ProjectResourceBean) manageImageImportActivity3.v.get(ManageImageImportActivity.this.x)).resName);
                            ManageImageImportActivity.this.z.a(1);
                        }
                        ManageImageImportActivity.this.t.c();
                    }
                });
            }
        }
    }
}