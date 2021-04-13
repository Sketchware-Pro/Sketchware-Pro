package com.besome.sketch.editor.view;

import a.a.a.Iw;
import a.a.a.Jw;
import a.a.a.Kw;
import a.a.a.Lw;
import a.a.a.NB;
import a.a.a.Qs;
import a.a.a.Rp;
import a.a.a.aB;
import a.a.a.ly;
import a.a.a.my;
import a.a.a.ny;
import a.a.a.oy;
import a.a.a.py;
import a.a.a.qy;
import a.a.a.ry;
import a.a.a.wB;
import a.a.a.xB;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.ctrls.ViewIdSpinnerItem;
import com.besome.sketch.editor.property.ViewPropertyItems;
import com.besome.sketch.lib.ui.CustomHorizontalScrollView;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewProperty extends LinearLayout implements Kw {
    public final String a = "see_all";
    public String b;
    public ProjectFileBean c;
    public Spinner d;
    public ArrayList<ViewBean> e = new ArrayList<>();
    public c f;
    public Jw g = null;
    public CustomHorizontalScrollView h;
    public LinearLayout i;
    public LinearLayout j;
    public ViewPropertyItems k;
    public b l;
    public View m;
    public ViewEvents n;
    public Iw o = null;
    public Lw p;
    public LinearLayout q;
    public int r;
    public ImageView s;
    public ObjectAnimator t;
    public ObjectAnimator u;
    public boolean v = true;

    /* access modifiers changed from: package-private */
    public class c extends BaseAdapter {
        public Context a;
        public int b;
        public ArrayList<ViewBean> c;

        public c(Context context, ArrayList<ViewBean> arrayList) {
            this.a = context;
            this.c = arrayList;
        }

        public void a(int i) {
            this.b = i;
        }

        public int getCount() {
            ArrayList<ViewBean> arrayList = this.c;
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, this.b == i, true);
        }

        public Object getItem(int i) {
            return this.c.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, false, false);
        }

        public int a() {
            return this.b;
        }

        public final ViewIdSpinnerItem a(int i, View view, ViewGroup viewGroup, boolean z, boolean z2) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (view != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) view;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(this.a);
                viewIdSpinnerItem.setTextSize(2131099882);
            }
            viewIdSpinnerItem.setDropDown(z2);
            ViewBean viewBean = this.c.get(i);
            viewIdSpinnerItem.a(ViewBean.getViewTypeResId(viewBean.type), viewBean.id, z);
            return viewIdSpinnerItem;
        }
    }

    public ViewProperty(Context context) {
        super(context);
        a(context);
    }

    public void a(String str, Object obj) {
    }

    public void setOnEventClickListener(Qs qs) {
        this.n.setOnEventClickListener(qs);
    }

    public void setOnPropertyListener(Iw iw) {
        this.o = iw;
    }

    public void setOnPropertyTargetChangeListener(Jw jw) {
        this.g = jw;
    }

    public void setOnPropertyValueChangedListener(Lw lw) {
        this.p = lw;
        this.k.setOnPropertyValueChangedListener(new py(this));
    }

    public final void b() {
        if (this.t.isRunning()) {
            this.t.cancel();
        }
        if (this.u.isRunning()) {
            this.u.cancel();
        }
    }

    public final void c() {
        if (this.t == null) {
            this.t = ObjectAnimator.ofFloat(this.j, View.TRANSLATION_Y, 0.0f);
            this.t.setDuration(400L);
            this.t.setInterpolator(new DecelerateInterpolator());
        }
        if (this.u == null) {
            this.u = ObjectAnimator.ofFloat(this.j, View.TRANSLATION_Y, wB.a(getContext(), 84.0f));
            this.u.setDuration(200L);
            this.u.setInterpolator(new DecelerateInterpolator());
        }
    }

    public void d() {
        ViewPropertyItems viewPropertyItems = this.k;
        if (viewPropertyItems != null) {
            viewPropertyItems.b();
        }
    }

    @SuppressLint("WrongConstant")
    public void e() {
        for (int i2 = 0; i2 < this.q.getChildCount(); i2++) {
            a aVar = (a) this.q.getChildAt(i2);
            if (this.r == ((Integer) aVar.getTag()).intValue()) {
                aVar.setSelected(true);
                aVar.c.setTextColor(-1);
                aVar.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            } else {
                aVar.setSelected(false);
                aVar.c.setTextColor(-14868183);
                aVar.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
            }
        }
        if (this.f.a() < this.e.size()) {
            ViewBean viewBean = this.e.get(this.f.a());
            int i3 = this.r;
            if (i3 == 0) {
                this.m.setVisibility(0);
                this.j.setVisibility(0);
                this.k.a(this.b, viewBean);
                a(viewBean);
                this.n.setVisibility(8);
            } else if (i3 == 1) {
                this.m.setVisibility(0);
                this.k.e(viewBean);
                this.j.setVisibility(8);
            } else if (i3 == 2) {
                this.m.setVisibility(8);
                this.n.setVisibility(0);
                this.n.a(this.b, this.c, viewBean);
            }
        }
    }

    @SuppressLint("ResourceType")
    public final void f() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), 2131626474));
        aBVar.a(2131165700);
        View a2 = wB.a(getContext(), 2131427640);
        ((TextView) a2.findViewById(2131231977)).setText(xB.b().a(getContext(), 2131626473));
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        NB nb = new NB(getContext(), a2.findViewById(2131231816), Rp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new qy(this, nb, editText, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new ry(this, aBVar));
        aBVar.show();
    }

    /* access modifiers changed from: package-private */
    public class a extends LinearLayout implements View.OnClickListener {
        public int a;
        public View b;
        public TextView c;

        public a(Context context) {
            super(context);
            a(context);
        }

        @SuppressLint("ResourceType")
        public final void a(Context context) {
            wB.a(context, this, 2131427631);
            this.b = findViewById(2131231624);
            this.c = (TextView) findViewById(2131232195);
        }

        public void onClick(View view) {
            ViewProperty.this.r = ((Integer) view.getTag()).intValue();
            ViewProperty.this.e();
        }

        public void a(int i, int i2) {
            this.a = i;
            setTag(Integer.valueOf(i));
            this.c.setText(xB.b().a(getContext(), i2));
            setOnClickListener(this);
        }
    }

    /* access modifiers changed from: package-private */
    public class b extends LinearLayout implements View.OnClickListener {
        public String a;
        public View b;
        public ImageView c;
        public TextView d;
        public TextView e;
        public ViewBean f;

        public b(Context context) {
            super(context);
            a(context);
        }

        @SuppressLint("ResourceType")
        public final void a(Context context) {
            wB.a(context, this, 2131427630);
            this.b = findViewById(2131231628);
            this.c = (ImageView) findViewById(2131231151);
            this.d = (TextView) findViewById(2131232195);
            this.e = (TextView) findViewById(2131232182);
        }

        public void onClick(View view) {
            if (ViewProperty.this.o != null) {
                ViewProperty.this.o.a(ViewProperty.this.c.getXmlName(), this.f);
            }
        }

        @SuppressLint("WrongConstant")
        public void a(String str, int i, int i2) {
            this.b.setVisibility(0);
            this.a = str;
            this.c.setImageResource(i);
            this.d.setText(xB.b().a(getContext(), i2));
            this.d.setTextColor(-27365);
            setOnClickListener(this);
        }

        public void a(ViewBean viewBean) {
            this.f = viewBean;
        }
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        wB.a(context, this, 2131427777);
        this.q = (LinearLayout) findViewById(2131231388);
        this.q.setOnClickListener(new ly(this));
        this.h = (CustomHorizontalScrollView) findViewById(2131231077);
        this.m = findViewById(2131231627);
        this.i = (LinearLayout) findViewById(2131231623);
        this.j = (LinearLayout) findViewById(2131231389);
        this.n = (ViewEvents) findViewById(2131232320);
        this.h.setOnScrollChangedListener(new my(this));
        this.s = (ImageView) findViewById(2131231177);
        this.s.setOnClickListener(new ny(this));
        this.d = (Spinner) findViewById(2131231756);
        this.f = new c(context, this.e);
        this.d.setAdapter((SpinnerAdapter) this.f);
        this.d.setSelection(0);
        this.d.setOnItemSelectedListener(new oy(this));
        a();
        c();
        this.k = new ViewPropertyItems(getContext());
        this.k.setOrientation(0);
        this.i.addView(this.k);
    }

    @SuppressLint("WrongConstant")
    public final void b(ViewBean viewBean) {
        Jw jw = this.g;
        if (jw != null) {
            jw.a(viewBean.id);
        }
        if ("_fab".equals(viewBean.id)) {
            this.s.setVisibility(8);
        } else {
            this.s.setVisibility(0);
        }
        this.k.setProjectFileBean(this.c);
        e();
    }

    public ViewProperty(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        this.b = str;
        this.c = projectFileBean;
    }

    public void a(String str) {
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (this.e.get(i2).id.equals(str)) {
                this.d.setSelection(i2);
                return;
            }
        }
    }

    public void a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        this.e.clear();
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.e.add(it.next());
        }
        if (viewBean != null) {
            this.e.add(0, viewBean);
        }
        this.f.notifyDataSetChanged();
    }

    public final void a() {
        a(0, 2131625824);
        a(1, 2131625826);
        a(2, 2131625825);
    }

    public final void a(int i2, int i3) {
        a aVar = new a(getContext());
        aVar.a(i2, i3);
        aVar.setTag(Integer.valueOf(i2));
        this.q.addView(aVar);
    }

    public void a(ViewBean viewBean) {
        b bVar = this.l;
        if (bVar == null) {
            this.l = new b(getContext());
            this.l.a("see_all", 2131165468, 2131625033);
            this.l.a(viewBean);
            this.j.addView(this.l);
            return;
        }
        bVar.a(viewBean);
    }
}
