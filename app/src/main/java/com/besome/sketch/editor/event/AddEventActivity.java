package com.besome.sketch.editor.event;

/*
 * I have Inlined some following a.a.a classes : Ls, Ms, Ns
 */

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a.a.a.Bi;
import a.a.a.bB;
import a.a.a.dt;
import a.a.a.gB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import a.a.a.rs;
import a.a.a.wB;
import a.a.a.xB;

public class AddEventActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public ArrayList<EventBean> A;
    public ArrayList<EventBean> B;
    public boolean C;
    public Button D;
    public Button E;
    public int F;
    public TextView G;
    public dt H;
    public String k;
    public ProjectFileBean l;
    public b m;
    public a n;
    public c o;
    public TextView p;
    public RecyclerView q;
    public RecyclerView r;
    public RecyclerView s;
    public LinearLayout t;
    public ScrollView u;
    public HashMap<Integer, ArrayList<EventBean>> v;
    public ArrayList<EventBean> w;
    public ArrayList<EventBean> x;
    public ArrayList<EventBean> y;
    public ArrayList<EventBean> z;

    public void finish() {
        super.finish();
        overridePendingTransition(0x7f01000e, 0x7f01000f);
    }

    public final void l() {
        if (this.B.size() == 0 && !this.C) {
            this.C = true;
            gB.a((ViewGroup) this.s, 300, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    s.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else if (this.B.size() > 0 && this.C) {
            this.C = false;
            this.s.setVisibility(View.VISIBLE);
            gB.b(this.s, 300, null);
        }
    }

    public final void m() {
        ArrayList<ViewBean> d;
        boolean z2;
        ViewBean h;
        boolean z3;
        boolean z4;
        boolean z5;
        this.w.clear();
        this.z.clear();
        this.y.clear();
        this.A.clear();
        this.w.clear();
        this.B.clear();
        String[] a2 = oq.a();
        int length = a2.length;
        int i = 0;
        while (true) {
            boolean z6 = true;
            if (i >= length) {
                break;
            }
            String str = a2[i];
            Iterator<EventBean> it = jC.a(this.k).g(this.l.getJavaName()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    z6 = false;
                    break;
                }
                EventBean next = it.next();
                if (next.eventType == 3 && str.equals(next.eventName)) {
                    break;
                }
            }
            if (!z6) {
                this.z.add(new EventBean(3, 0, str, str));
            }
            i++;
        }
        ArrayList<ViewBean> d2 = jC.a(this.k).d(this.l.getXmlName());
        ArrayList<ComponentBean> e = jC.a(this.k).e(this.l.getJavaName());
        if (d2 != null) {
            Iterator<ViewBean> it2 = d2.iterator();
            while (it2.hasNext()) {
                ViewBean next2 = it2.next();
                String[] c2 = oq.c(next2.getClassInfo());
                if (c2 != null) {
                    for (String str2 : c2) {
                        Iterator<EventBean> it3 = jC.a(this.k).g(this.l.getJavaName()).iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                z5 = false;
                                break;
                            }
                            EventBean next3 = it3.next();
                            if (next3.eventType == 1 && next2.id.equals(next3.targetId) && str2.equals(next3.eventName)) {
                                z5 = true;
                                break;
                            }
                        }
                        if (str2.equals("onBindCustomView") && (next2.customView.equals("") || next2.customView.equals("none"))) {
                            z5 = true;
                        }
                        if (!z5) {
                            this.x.add(new EventBean(1, next2.type, next2.id, str2));
                        }
                    }
                }
            }
        }
        if (e != null) {
            Iterator<ComponentBean> it4 = e.iterator();
            while (it4.hasNext()) {
                ComponentBean next4 = it4.next();
                String[] a3 = oq.a(next4.getClassInfo());
                if (a3 != null) {
                    for (String str3 : a3) {
                        Iterator<EventBean> it5 = jC.a(this.k).g(this.l.getJavaName()).iterator();
                        while (true) {
                            if (!it5.hasNext()) {
                                z4 = false;
                                break;
                            }
                            EventBean next5 = it5.next();
                            if (next5.eventType == 2 && next4.componentId.equals(next5.targetId) && str3.equals(next5.eventName)) {
                                z4 = true;
                                break;
                            }
                        }
                        if (!z4) {
                            this.y.add(new EventBean(2, next4.type, next4.componentId, str3));
                        }
                    }
                }
            }
        }
        if (this.l.hasActivityOption(8) && (h = jC.a(this.k).h(this.l.getXmlName())) != null) {
            String[] c3 = oq.c(h.getClassInfo());
            for (String str4 : c3) {
                Iterator<EventBean> it6 = jC.a(this.k).g(this.l.getJavaName()).iterator();
                while (true) {
                    if (!it6.hasNext()) {
                        z3 = false;
                        break;
                    }
                    EventBean next6 = it6.next();
                    if (next6.eventType == 1 && h.id.equals(next6.targetId) && str4.equals(next6.eventName)) {
                        z3 = true;
                        break;
                    }
                }
                if (!z3) {
                    this.x.add(new EventBean(1, h.type, h.id, str4));
                }
            }
        }
        if (this.l.hasActivityOption(4) && (d = jC.a(this.k).d(this.l.getDrawerXmlName())) != null) {
            Iterator<ViewBean> it7 = d.iterator();
            while (it7.hasNext()) {
                ViewBean next7 = it7.next();
                String[] c4 = oq.c(next7.getClassInfo());
                for (String str5 : c4) {
                    Iterator<EventBean> it8 = jC.a(this.k).g(this.l.getJavaName()).iterator();
                    while (true) {
                        if (!it8.hasNext()) {
                            z2 = false;
                            break;
                        }
                        EventBean next8 = it8.next();
                        if (next8.eventType == 4 && next7.id.equals(next8.targetId) && str5.equals(next8.eventName)) {
                            z2 = true;
                            break;
                        }
                    }
                    if (!z2) {
                        this.A.add(new EventBean(4, next7.type, next7.id, str5));
                    }
                }
            }
        }
        if (this.m.c == -1) {
            this.n.a(this.v.get(Integer.valueOf(this.F)));
            this.m.c = this.F;
            this.p.setText(rs.a(getApplicationContext(), this.F));
            b bVar = this.m;
            if (bVar != null) {
                bVar.c(this.F);
            }
            if (this.F == 4) {
                this.u.setVisibility(View.VISIBLE);
                this.G.setVisibility(View.GONE);
                this.r.setVisibility(View.GONE);
            } else {
                this.u.setVisibility(View.GONE);
                this.r.setVisibility(View.GONE);
            }
        }
        a aVar = this.n;
        if (aVar != null) {
            aVar.c();
        }
    }

    public void onClick(View view) {
        if (!mB.a()) {
            int id2 = view.getId();
            if (id2 != 2131230755) {
                if (id2 == 2131230869) {
                    setResult(0);
                    finish();
                }
            } else if (this.B.size() != 0 || !this.H.a()) {
                if (!this.H.a()) {
                    if (!this.H.b()) {
                        this.n.a(this.v.get(4));
                        this.m.c = 4;
                        this.p.setText(rs.a(getApplicationContext(), 4));
                        this.G.setVisibility(View.GONE);
                        this.u.setVisibility(View.VISIBLE);
                        this.m.c();
                        return;
                    }
                    Pair<String, String> blockInformation = this.H.getBlockInformation();
                    jC.a(this.k).a(this.l.getJavaName(), (String) blockInformation.first, (String) blockInformation.second);
                }
                Iterator<EventBean> it = this.B.iterator();
                while (it.hasNext()) {
                    jC.a(this.k).a(this.l.getJavaName(), it.next());
                }
                if (this.B.size() == 1) {
                    bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625331), 0).show();
                } else if (this.B.size() > 1) {
                    bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625332), 0).show();
                }
                jC.a(this.k).k();
                setResult(-1);
                finish();
            }
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427496);
        Intent intent = getIntent();
        if (bundle == null) {
            this.k = intent.getStringExtra("sc_id");
            this.l = (ProjectFileBean) intent.getParcelableExtra("project_file");
            this.F = intent.getIntExtra("category_index", 0);
        } else {
            this.k = bundle.getString("sc_id");
            this.l = (ProjectFileBean) bundle.getParcelable("project_file");
            this.F = bundle.getInt("category_index");
        }
        this.r = (RecyclerView) findViewById(2131231046);
        this.p = (TextView) findViewById(2131231894);
        this.q = (RecyclerView) findViewById(2131230876);
        this.s = (RecyclerView) findViewById(2131231049);
        this.t = (LinearLayout) findViewById(2131230931);
        this.D = (Button) findViewById(2131230755);
        this.E = (Button) findViewById(2131230869);
        this.G = (TextView) findViewById(2131231017);
        this.u = (ScrollView) findViewById(2131231547);
        this.H = new dt(this);
        this.u.addView(this.H);
        this.u.setVisibility(View.GONE);
        this.D.setOnClickListener(this);
        this.E.setOnClickListener(this);
        this.v = new HashMap<>();
        this.w = new ArrayList<>();
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
        this.A = new ArrayList<>();
        this.v.put(0, this.z);
        this.v.put(1, this.x);
        this.v.put(2, this.y);
        this.v.put(3, this.A);
        this.v.put(4, this.w);
        this.r.setHasFixedSize(true);
        this.r.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.n = new a();
        this.r.setAdapter(this.n);
        this.q.setHasFixedSize(true);
        this.m = new b();
        this.q.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.q.setAdapter(this.m);
        ((Bi) this.q.getItemAnimator()).a(false);
        this.s.setHasFixedSize(true);
        this.o = new c();
        this.s.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        this.s.setAdapter(this.o);
        this.C = true;
        this.s.setVisibility(View.GONE);
        this.G.setVisibility(View.GONE);
        this.B = new ArrayList<>();
        this.r.bringToFront();
        overridePendingTransition(2130771982, 2130771983);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.D.setText(xB.b().a(this, 2131624970));
        this.E.setText(xB.b().a(this, 2131624974));
        this.G.setText(xB.b().a(this, 2131625333));
        this.H.setFuncNameValidator(jC.a(this.k).a(this.l));
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        gB.a(this.t, 500);
        if (this.l != null) {
            m();
        }
        this.d.setScreenName(AddEventActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.k);
        bundle.putParcelable("project_file", this.l);
        bundle.putInt("category_index", this.F);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.a<AddEventActivity.a.aa> {
        public int c = -1;
        public ArrayList<EventBean> d = new ArrayList<>();
        public boolean e;

        /*
         * I have renamed C0031a to aa reason bellow, you can change it if you want.
         */

        public a() {
        }

        /* renamed from: a */
        public void b(aa aVar, int i) {
            this.e = true;
            aVar.t.removeAllViews();
            aVar.t.setVisibility(View.VISIBLE);
            EventBean eventBean = (EventBean) ((ArrayList) AddEventActivity.this.v.get(Integer.valueOf(AddEventActivity.this.m.c))).get(i);
            ImageView imageView = new ImageView(AddEventActivity.this.getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(0, 0, (int) wB.a(AddEventActivity.this.getApplicationContext(), 2.0f), 0);
            int a2 = (int) wB.a(AddEventActivity.this.getApplicationContext(), 16.0f);
            layoutParams.width = a2;
            layoutParams.height = a2;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(oq.a(eventBean.eventName));
            aVar.t.addView(imageView);
            aVar.u.setImageResource(EventBean.getEventIconResource(eventBean.eventType, eventBean.targetType));
            int i2 = eventBean.eventType;
            if (i2 == 3) {
                aVar.v.setText("Activity");
                aVar.t.setVisibility(View.GONE);
            } else if (i2 == 1) {
                aVar.v.setText(ViewBean.getViewTypeName(eventBean.targetType));
            } else if (i2 == 4) {
                aVar.v.setText(ViewBean.getViewTypeName(eventBean.targetType));
            } else if (i2 == 2) {
                aVar.v.setText(ComponentBean.getComponentName(AddEventActivity.this.getApplicationContext(), eventBean.targetType));
            } else if (i2 == 5) {
                aVar.t.setVisibility(View.GONE);
            }
            aVar.w.setText(" : ");
            if (eventBean.targetId.equals("_fab")) {
                aVar.x.setText("fab");
            } else {
                aVar.x.setText(eventBean.targetId);
            }
            aVar.y.setText(oq.a(eventBean.eventName, AddEventActivity.this.getApplicationContext()));
            aVar.z.setChecked(eventBean.isSelected);
            this.e = false;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public aa b(ViewGroup viewGroup, int i) {
            return new aa(LayoutInflater.from(viewGroup.getContext()).inflate(2131427430, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return this.d.size();
        }

        public void a(ArrayList<EventBean> arrayList) {
            if (arrayList.size() == 0) {
                AddEventActivity.this.G.setVisibility(View.VISIBLE);
            } else {
                AddEventActivity.this.G.setVisibility(View.GONE);
                AddEventActivity.this.r.setVisibility(View.VISIBLE);
            }
            this.d = arrayList;
        }

        /* renamed from: com.besome.sketch.editor.event.AddEventActivity$a$a  reason: collision with other inner class name */
        class aa extends RecyclerView.v {
            public LinearLayout t;
            public ImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public CheckBox z;

            public aa(View view) {
                super(view);
                this.t = (LinearLayout) view.findViewById(2131231049);
                this.u = (ImageView) view.findViewById(2131231151);
                this.v = (TextView) view.findViewById(2131232193);
                this.w = (TextView) view.findViewById(2131232152);
                this.x = (TextView) view.findViewById(2131232192);
                this.y = (TextView) view.findViewById(2131231970);
                this.z = (CheckBox) view.findViewById(2131230883);
                view.setOnClickListener(v -> {
                    if (!mB.a()) {
                        a.aa aVar = a.aa.this;
                        AddEventActivity.a.this.c = aVar.j();
                        EventBean eventBean = (EventBean) ((ArrayList) AddEventActivity.this.v.get(Integer.valueOf(AddEventActivity.this.m.c))).get(AddEventActivity.a.this.c);
                        if (eventBean.isSelected) {
                            eventBean.isSelected = false;
                            AddEventActivity.this.B.remove(eventBean);
                            AddEventActivity.this.l();
                            AddEventActivity.c cVar = AddEventActivity.this.o;
                            cVar.e(cVar.a());
                        } else {
                            eventBean.isSelected = true;
                            AddEventActivity.this.B.add(eventBean);
                            AddEventActivity.this.l();
                            AddEventActivity.c cVar2 = AddEventActivity.this.o;
                            cVar2.d(cVar2.a());
                        }
                        AddEventActivity.a aVar2 = AddEventActivity.a.this;
                        if (!aVar2.e) {
                            aVar2.c(aVar2.c);
                        }
                    }

                });
                this.z.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    a.aa aVar = a.aa.this;
                    AddEventActivity.a.this.c = aVar.j();
                    EventBean eventBean = (EventBean) ((ArrayList) AddEventActivity.this.v.get(Integer.valueOf(AddEventActivity.this.m.c))).get(AddEventActivity.a.this.c);
                    if (!eventBean.isSelected && isChecked) {
                        eventBean.isSelected = true;
                        AddEventActivity.this.B.add(eventBean);
                        AddEventActivity.this.l();
                        AddEventActivity.c cVar = AddEventActivity.this.o;
                        cVar.d(cVar.a());
                    } else if (eventBean.isSelected && !isChecked) {
                        eventBean.isSelected = false;
                        AddEventActivity.this.B.remove(eventBean);
                        AddEventActivity.c cVar2 = AddEventActivity.this.o;
                        cVar2.e(cVar2.a());
                        AddEventActivity.this.l();
                    }
                    AddEventActivity.a aVar2 = AddEventActivity.a.this;
                    if (!aVar2.e) {
                        aVar2.c(aVar2.c);
                    }
                });
            }
        }
    }

    class b extends RecyclerView.a<AddEventActivity.b.bb> {
        public int c = -1;

        public b() {
        }

        /* renamed from: a */
        public void b(bb aVar, int i) {
            aVar.u.setImageResource(rs.a(i));
            if (this.c == i) {
                aVar.t.setBackgroundResource(2131165390);
                aVar.u.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
                aVar.t.animate().translationY(0.0f).start();
                return;
            }
            aVar.t.setBackgroundResource(2131165387);
            aVar.u.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
            aVar.t.setTranslationY(wB.a(AddEventActivity.this.getApplicationContext(), 12.0f));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public bb b(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(0x7f0b0064, viewGroup, false);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(viewGroup.getMeasuredWidth() / a(), (int) wB.a(AddEventActivity.this.getApplicationContext(), 44.0f)));
            inflate.setTranslationY(wB.a(AddEventActivity.this.getApplicationContext(), 12.0f));
            inflate.findViewById(0x7f0801af).setAlpha(0.6f);
            inflate.findViewById(0x7f0801af).setScaleX(0.8f);
            inflate.findViewById(0x7f0801af).setScaleY(0.8f);
            return new bb(inflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return AddEventActivity.this.v.size();
        }

        class bb extends RecyclerView.v implements View.OnClickListener {
            public LinearLayout t;
            public ImageView u;

            public bb(View view) {
                super(view);
                this.u = (ImageView) view.findViewById(0x7f0801af);
                this.t = (LinearLayout) view.findViewById(0x7f0800d3);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                if (j() != -1) {
                    int j = j();
                    b bVar = b.this;
                    if (j != bVar.c) {
                        bVar.c = j();
                        b.this.c();
                        AddEventActivity addEventActivity = AddEventActivity.this;
                        addEventActivity.p.setText(rs.a(addEventActivity.getApplicationContext(), b.this.c));
                        b bVar2 = b.this;
                        if (bVar2.c == 4) {
                            AddEventActivity.this.u.setVisibility(View.VISIBLE);
                            AddEventActivity.this.G.setVisibility(View.GONE);
                            return;
                        }
                        AddEventActivity.this.u.setVisibility(View.GONE);
                        AddEventActivity addEventActivity2 = AddEventActivity.this;
                        addEventActivity2.n.a((ArrayList) addEventActivity2.v.get(Integer.valueOf(b.this.c)));
                        AddEventActivity.this.n.c();
                    }
                }
            }
        }
    }

    class c extends RecyclerView.a<AddEventActivity.c.cc> {

        public c() {
        }

        /* renamed from: a */
        public void b(cc aVar, int i) {
            aVar.t.setVisibility(View.VISIBLE);
            EventBean eventBean = (EventBean) AddEventActivity.this.B.get(i);
            int i2 = eventBean.eventType;
            if (i2 == 3) {
                aVar.t.setVisibility(View.GONE);
            } else if (i2 == 5) {
                aVar.t.setVisibility(View.GONE);
            }
            aVar.v.setImageResource(EventBean.getEventIconResource(eventBean.eventType, eventBean.targetType));
            aVar.w.setImageResource(oq.a(eventBean.eventName));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public cc b(ViewGroup viewGroup, int i) {
            return new cc(LayoutInflater.from(viewGroup.getContext()).inflate(0x7f0b006c, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return AddEventActivity.this.B.size();
        }

        class cc extends RecyclerView.v {
            public LinearLayout t;
            public RelativeLayout u;
            public ImageView v;
            public ImageView w;

            public cc(View view) {
                super(view);
                this.u = (RelativeLayout) view.findViewById(0x7f0800d3);
                this.v = (ImageView) view.findViewById(0x7f0801af);
                this.w = (ImageView) view.findViewById(0x7f0801a3);
                this.t = (LinearLayout) view.findViewById(0x7f0802f3);
            }
        }
    }
}