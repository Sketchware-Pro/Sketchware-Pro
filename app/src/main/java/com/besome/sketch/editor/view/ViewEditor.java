package com.besome.sketch.editor.view;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.Iw;
import a.a.a.Op;
import a.a.a._x;
import a.a.a.aB;
import a.a.a.ay;
import a.a.a.bB;
import a.a.a.cC;
import a.a.a.cy;
import a.a.a.ey;
import a.a.a.fy;
import a.a.a.gy;
import a.a.a.hy;
import a.a.a.iy;
import a.a.a.jC;
import a.a.a.oB;
import a.a.a.sy;
import a.a.a.uy;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.sketch.SketchApplication;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.beans.WidgetCollectionBean;
import com.besome.sketch.editor.view.item.ItemHorizontalScrollView;
import com.besome.sketch.editor.view.item.ItemVerticalScrollView;
import com.besome.sketch.editor.view.palette.IconAdView;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.editor.view.palette.IconLinearHorizontal;
import com.besome.sketch.editor.view.palette.IconLinearVertical;
import com.besome.sketch.editor.view.palette.IconMapView;
import com.besome.sketch.editor.view.palette.PaletteFavorite;
import com.besome.sketch.editor.view.palette.PaletteWidget;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import mod.hey.studios.editor.view.IdGenerator;
import mod.hey.studios.util.ProjectFile;

public class ViewEditor extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    public ObjectAnimator A;
    public boolean B;
    public boolean C;
    public boolean D;
    public boolean E;
    public FrameLayout F;
    public int[] G;
    public sy H;
    public int I;
    public int J;
    public boolean K;
    public cy L;
    public Iw M;
    public _x N;
    public ay O;
    public boolean P;
    public Tracker Q;
    public ProjectFileBean R;
    public boolean S;
    public boolean T;
    public LinearLayout U;
    public b V;
    public b W;
    public String a;
    public LinearLayout aa;
    public String b;
    public int ba;
    public final int c;
    public int ca;
    public int d;
    public boolean da;
    public int[] e;
    public Runnable ea;
    public float f;
    public int g;
    public int h;
    public PaletteWidget i;
    public PaletteFavorite j;
    public LinearLayout k;
    public TextView l;
    public ImageView m;
    public LinearLayout n;
    public TextView o;
    public ViewPane p;
    public Vibrator q;
    public View r;
    public final Handler s;
    public boolean t;
    public float u;
    public float v;
    public int w;
    public ViewDummy x;
    public ImageView y;
    public ObjectAnimator z;

    enum a {
        a,
        b
    }

    public ViewEditor(Context context) {
        super(context);
        this.c = 99;
        this.d = 99;
        this.e = new int[20];
        this.f = 0.0f;
        this.r = null;
        this.s = new Handler();
        this.t = false;
        this.u = 0.0f;
        this.v = 0.0f;
        this.w = 0;
        this.B = false;
        this.C = false;
        this.D = false;
        this.E = true;
        this.G = new int[2];
        this.I = 50;
        this.J = 30;
        this.P = true;
        this.S = true;
        this.T = false;
        this.ba = 0;
        this.da = true;
        this.ea = new gy(this);
        a(context);
    }

    public final void f() {
        this.z = ObjectAnimator.ofFloat(this.y, "TranslationY", 0.0f);
        this.z.setDuration(500L);
        this.z.setInterpolator(new DecelerateInterpolator());
        ImageView imageView = this.y;
        this.A = ObjectAnimator.ofFloat(imageView, "TranslationY", imageView.getHeight());
        this.A.setDuration(300L);
        this.A.setInterpolator(new DecelerateInterpolator());
        this.B = true;
    }

    public final void g() {
        this.V = new b(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        this.V.setLayoutParams(layoutParams);
        this.V.a(a.a);
        this.V.setSelected(true);
        this.V.setOnClickListener(new ey(this));
        this.W = new b(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -1);
        layoutParams2.weight = 1.0f;
        this.W.setLayoutParams(layoutParams2);
        this.W.a(a.b);
        this.W.setSelected(false);
        this.W.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
        this.W.setOnClickListener(new fy(this));
        this.U.addView(this.V);
        this.U.addView(this.W);
    }

    public ProjectFileBean getProjectFile() {
        return this.R;
    }

    public void h() {
        this.p.setResourceManager(jC.d(this.a));
    }

    public void i() {
        sy syVar = this.H;
        if (syVar != null) {
            syVar.setSelection(false);
            this.H = null;
        }
        cy cyVar = this.L;
        if (cyVar != null) {
            cyVar.a(false, "");
        }
    }

    public void j() {
        this.p.d();
        l();
        i();
    }

    public void k() {
        this.p.e();
    }

    public void l() {
        this.e = new int[99];
    }

    public final void m() {
        Iw iw = this.M;
        if (iw != null) {
            iw.a(this.b, this.H.getBean());
        }
    }

    public final void n() {
        this.i.setVisibility(8);
        this.j.setVisibility(0);
    }

    public final void o() {
        this.i.setVisibility(0);
        this.j.setVisibility(8);
    }

    @Override
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == 2131230821) {
            m();
        } else if (id2 != 2131231122) {
        }
    }

    @Override
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.P) {
            a();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        _x _xVar;
        String str;
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.getPointerId(motionEvent.getActionIndex()) > 0) {
            return true;
        }
        if (view == this.p) {
            if (actionMasked == 0) {
                i();
                this.r = null;
            }
            return true;
        } else if (actionMasked == 0) {
            this.t = false;
            this.u = motionEvent.getRawX();
            this.v = motionEvent.getRawY();
            this.r = view;
            if ((view instanceof sy) && ((sy) view).getFixed()) {
                return true;
            }
            if (b(view) && (_xVar = this.N) != null) {
                _xVar.b();
            }
            this.s.postDelayed(this.ea, ViewConfiguration.getLongPressTimeout() / 2);
            return true;
        } else if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked == 3 || actionMasked == 8) {
                    this.i.setScrollEnabled(true);
                    this.j.setScrollEnabled(true);
                    _x _xVar2 = this.N;
                    if (_xVar2 != null) {
                        _xVar2.d();
                    }
                    b(false);
                    this.x.setDummyVisibility(8);
                    this.p.b();
                    this.s.removeCallbacks(this.ea);
                    this.t = false;
                    return true;
                }
                return true;
            } else if (!this.t) {
                if (Math.abs(this.u - motionEvent.getRawX()) >= this.w || Math.abs(this.v - motionEvent.getRawY()) >= this.w) {
                    this.r = null;
                    this.s.removeCallbacks(this.ea);
                    return true;
                }
                return true;
            } else {
                this.s.removeCallbacks(this.ea);
                this.x.a(view, motionEvent.getRawX(), motionEvent.getRawY(), this.u, this.v);
                if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.x.setAllow(true);
                    a(true);
                    return true;
                }
                if (this.D) {
                    a(false);
                }
                if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                    this.x.setAllow(true);
                    int i = this.I;
                    int i2 = this.J;
                    if (!a(this.r)) {
                        i = this.r.getWidth();
                        i2 = this.r.getHeight();
                    } else {
                        if (this.r instanceof IconLinearHorizontal) {
                            i = -1;
                        }
                        if (this.r instanceof IconLinearVertical) {
                            i2 = -1;
                        }
                    }
                    this.p.a((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), i, i2);
                } else {
                    this.x.setAllow(false);
                    this.p.a(true);
                }
                return true;
            }
        } else if (!this.t) {
            View view2 = this.r;
            if (view2 instanceof sy) {
                a((sy) view2, true);
            }
            _x _xVar3 = this.N;
            if (_xVar3 != null) {
                _xVar3.d();
            }
            this.x.setDummyVisibility(8);
            this.r = null;
            this.p.b();
            this.s.removeCallbacks(this.ea);
            return true;
        } else {
            if (this.x.getAllow()) {
                if (this.D) {
                    View view3 = this.r;
                    if (view3 instanceof sy) {
                        ArrayList<ViewBean> b2 = jC.a(this.a).b(this.b, ((sy) view3).getBean());
                        for (int size = b2.size() - 1; size >= 0; size--) {
                            jC.a(this.a).a(this.R, b2.get(size));
                        }
                        b(b2, true);
                    }
                }
                if (this.D) {
                    View view4 = this.r;
                    if (view4 instanceof uy) {
                        b(((uy) view4).getName());
                    }
                }
                this.p.a(false);
                View view5 = this.r;
                if (view5 instanceof uy) {
                    uy uyVar = (uy) view5;
                    ArrayList<ViewBean> arrayList = new ArrayList<>();
                    oB oBVar = new oB();
                    boolean z = false;
                    for (int i3 = 0; i3 < uyVar.getData().size(); i3++) {
                        ViewBean viewBean = uyVar.getData().get(i3);
                        if (c(viewBean)) {
                            arrayList.add(viewBean.clone());
                            String str2 = viewBean.layout.backgroundResource;
                            String str3 = viewBean.image.resName;
                            if (!jC.d(this.a).l(str2) && Op.g().b(str2)) {
                                ProjectResourceBean a2 = Op.g().a(str2);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + this.a + File.separator + a2.resFullName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                jC.d(this.a).b.add(a2);
                                z = true;
                            }
                            if (!jC.d(this.a).l(str3) && Op.g().b(str3)) {
                                ProjectResourceBean a3 = Op.g().a(str3);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a3.resFullName, wq.g() + File.separator + this.a + File.separator + a3.resFullName);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                jC.d(this.a).b.add(a3);
                                z = true;
                            }
                        }
                    }
                    if (z) {
                        bB.a(getContext(), xB.b().a(getContext(), 2131626471), 0).show();
                    }
                    if (arrayList.size() > 0) {
                        HashMap hashMap = new HashMap();
                        this.p.a(arrayList.get(0), (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                        Iterator<ViewBean> it = arrayList.iterator();
                        while (it.hasNext()) {
                            ViewBean next = it.next();
                            if (jC.a(this.a).h(this.R.getXmlName(), next.id)) {
                                hashMap.put(next.id, a(next.type));
                            } else {
                                String str4 = next.id;
                                hashMap.put(str4, str4);
                            }
                            next.id = (String) hashMap.get(next.id);
                            if (arrayList.indexOf(next) != 0 && (str = next.parent) != null && str.length() > 0) {
                                next.parent = (String) hashMap.get(next.parent);
                            }
                            jC.a(this.a).a(this.b, next);
                        }
                        a(a(arrayList, true), true);
                    }
                } else if (view5 instanceof IconBase) {
                    ViewBean bean = ((IconBase) view5).getBean();
                    bean.id = IdGenerator.getId(this, bean.type, bean);
                    this.p.a(bean, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    jC.a(this.a).a(this.b, bean);
                    HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
                    eventBuilder.setCategory("editor");
                    eventBuilder.setAction("widget");
                    eventBuilder.setLabel("Custom");
                    this.Q.send(eventBuilder.build());
                    if (bean.type == 3 && this.R.fileType == 0) {
                        jC.a(this.a).a(this.R.getJavaName(), 1, bean.type, bean.id, "onClick");
                    }
                    a(a(bean, true), true);
                } else if (view5 instanceof sy) {
                    ViewBean bean2 = ((sy) view5).getBean();
                    this.p.a(bean2, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    a(b(bean2, true), true);
                }
            } else {
                View view6 = this.r;
                if (view6 instanceof sy) {
                    view6.setVisibility(0);
                }
            }
            this.i.setScrollEnabled(true);
            this.j.setScrollEnabled(true);
            _x _xVar4 = this.N;
            if (_xVar4 != null) {
                _xVar4.d();
            }
            b(false);
            this.x.setDummyVisibility(8);
            this.r = null;
            this.p.b();
            this.s.removeCallbacks(this.ea);
            this.t = false;
            return true;
        }
    }

    public void setFavoriteData(ArrayList<WidgetCollectionBean> arrayList) {
        c();
        Iterator<WidgetCollectionBean> it = arrayList.iterator();
        while (it.hasNext()) {
            WidgetCollectionBean next = it.next();
            a(next.name, next.widgets);
        }
    }

    public void setIsAdLoaded(boolean z) {
        this.da = z;
    }

    public void setOnDraggingListener(_x _xVar) {
        this.N = _xVar;
    }

    public void setOnHistoryChangeListener(ay ayVar) {
        this.O = ayVar;
    }

    public void setOnPropertyClickListener(Iw iw) {
        this.M = iw;
    }

    public void setOnWidgetSelectedListener(cy cyVar) {
        this.L = cyVar;
    }

    public void setPaletteLayoutVisible(int i) {
        this.i.setLayoutVisible(i);
    }

    public void setScreenType(int i) {
        if (i == 1) {
            this.ca = 0;
        } else {
            this.ca = 1;
        }
    }

    public final void a(Context context) {
        wB.a(context, this, 2131427774);
        this.Q = ((SketchApplication) context.getApplicationContext()).a();
        this.Q.enableAdvertisingIdCollection(true);
        this.Q.enableExceptionReporting(true);
        this.i = (PaletteWidget) findViewById(2131231591);
        this.j = (PaletteFavorite) findViewById(2131231588);
        this.x = (ViewDummy) findViewById(2131230982);
        this.y = (ImageView) findViewById(2131231094);
        this.F = (FrameLayout) findViewById(2131231715);
        this.U = (LinearLayout) findViewById(2131231589);
        g();
        findViewById(2131230821).setOnClickListener(this);
        findViewById(2131231122).setOnClickListener(this);
        this.f = wB.a(context, 1.0f);
        float f = this.f;
        this.I = (int) (this.I * f);
        this.J = (int) (this.J * f);
        this.g = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        this.aa = new LinearLayout(context);
        this.aa.setOrientation(1);
        this.aa.setGravity(17);
        this.aa.setLayoutParams(new FrameLayout.LayoutParams(this.g, this.h));
        this.F.addView(this.aa);
        this.k = new LinearLayout(context);
        this.k.setBackgroundColor(-16743230);
        this.k.setOrientation(0);
        this.k.setGravity(16);
        this.k.setLayoutParams(new FrameLayout.LayoutParams(this.g, (int) (this.f * 25.0f)));
        this.l = new TextView(context);
        this.l.setTextColor(-1);
        this.l.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.l.setPadding((int) (this.f * 8.0f), 0, 0, 0);
        this.l.setGravity(16);
        this.k.addView(this.l);
        this.m = new ImageView(context);
        this.m.setImageResource(2131166021);
        this.m.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.m.setScaleType(ImageView.ScaleType.FIT_END);
        this.k.addView(this.m);
        this.F.addView(this.k);
        this.n = new LinearLayout(context);
        this.n.setBackgroundColor(-16740915);
        this.n.setOrientation(0);
        this.n.setGravity(16);
        this.n.setLayoutParams(new FrameLayout.LayoutParams(this.g, (int) (this.f * 48.0f)));
        this.o = new TextView(context);
        this.o.setTextColor(-1);
        this.o.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.o.setPadding((int) (this.f * 16.0f), 0, 0, 0);
        this.o.setGravity(16);
        this.o.setTextSize(15.0f);
        this.o.setText("Toolbar");
        this.o.setTypeface(null, 1);
        this.n.addView(this.o);
        this.F.addView(this.n);
        this.p = new ViewPane(getContext());
        this.p.setLayoutParams(new FrameLayout.LayoutParams(this.g, this.h));
        this.F.addView(this.p);
        this.p.setOnTouchListener(this);
        this.q = (Vibrator) context.getSystemService("vibrator");
        this.K = new DB(context, "P12").a("P12I0", true);
        this.w = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void b(ArrayList<ViewBean> arrayList, boolean z) {
        if (z) {
            cC.c(this.a).b(this.R.getXmlName(), arrayList);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            d(arrayList.get(size));
        }
    }

    public final void c() {
        this.j.a();
    }

    public void d() {
        this.i.a();
        this.i.b();
    }

    public sy e(ViewBean viewBean) {
        sy g = this.p.g(viewBean);
        this.L.a();
        this.L.a(viewBean.id);
        return g;
    }

    class b extends LinearLayout implements View.OnClickListener {
        public a a;
        public View b;
        public ImageView c;

        public b(Context context) {
            super(context);
            a(context);
        }

        public final void a(Context context) {
            wB.a(context, this, 2131427608);
            this.b = findViewById(2131231073);
            this.c = (ImageView) findViewById(2131231148);
        }

        @Override
        public void onClick(View view) {
        }

        public void a(a aVar) {
            this.a = aVar;
            if (aVar == a.a) {
                this.c.setImageResource(2131166113);
            } else {
                this.c.setImageResource(2131166112);
            }
            setOnClickListener(this);
        }
    }

    public final boolean c(ViewBean viewBean) {
        int i;
        int i2 = this.R.fileType;
        if (i2 == 1) {
            int i3 = viewBean.type;
            if (i3 != 0 && i3 != 4 && i3 != 5 && i3 != 3 && i3 != 6 && i3 != 11 && i3 != 13 && i3 != 14 && i3 == 8) {
                return true;
            }
        } else if (i2 == 2 && (i = viewBean.type) != 0 && i != 12 && i != 2 && i != 4 && i != 5 && i != 3 && i != 6 && i != 11 && i != 13 && i != 14 && i == 8) {
            return true;
        }
        return true;
    }

    public void d(ViewBean viewBean) {
        this.p.f(viewBean);
    }

    public final void e() {
        boolean z;
        boolean z2;
        View view = this.r;
        if (view == null) {
            return;
        }
        if (a(view)) {
            View view2 = this.r;
            if (view2 instanceof uy) {
                uy uyVar = (uy) view2;
                Iterator<ViewBean> it = uyVar.getData().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (it.next().type == 17) {
                        z = true;
                        break;
                    }
                }
                if (z && !this.N.a()) {
                    bB.b(getContext(), xB.b().a(getContext(), 2131625244), 0).show();
                    return;
                }
                Iterator<ViewBean> it2 = uyVar.getData().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    } else if (it2.next().type == 18) {
                        z2 = true;
                        break;
                    }
                }
                if (z2 && !this.N.c()) {
                    bB.b(getContext(), xB.b().a(getContext(), 2131625244), 0).show();
                    return;
                }
            } else if ((view2 instanceof IconAdView) && !this.N.a()) {
                bB.b(getContext(), xB.b().a(getContext(), 2131625244), 0).show();
                return;
            } else if ((this.r instanceof IconMapView) && !this.N.c()) {
                bB.b(getContext(), xB.b().a(getContext(), 2131625244), 0).show();
                return;
            }
        }
        this.i.setScrollEnabled(false);
        this.j.setScrollEnabled(false);
        _x _xVar = this.N;
        if (_xVar != null) {
            _xVar.b();
        }
        if (this.K) {
            this.q.vibrate(100L);
        }
        this.t = true;
        this.x.b(this.r);
        this.x.bringToFront();
        i();
        ViewDummy viewDummy = this.x;
        View view3 = this.r;
        float f = this.u;
        float f2 = this.v;
        viewDummy.a(view3, f, f2, f, f2);
        this.x.a(this.G);
        if (a(this.r)) {
            if (this.r instanceof uy) {
                b(true);
                this.p.e(null);
            } else {
                b(false);
                this.p.e(null);
            }
        } else {
            this.r.setVisibility(8);
            b(true);
            this.p.e(((sy) this.r).getBean());
        }
        if (b(this.u, this.v)) {
            this.x.setAllow(true);
            int i = this.I;
            int i2 = this.J;
            if (!a(this.r)) {
                i = this.r.getWidth();
                i2 = this.r.getHeight();
            } else {
                if (this.r instanceof IconLinearHorizontal) {
                    i = -1;
                }
                if (this.r instanceof IconLinearVertical) {
                    i2 = -1;
                }
            }
            this.p.a((int) this.u, (int) this.v, i, i2);
            return;
        }
        this.x.setAllow(false);
        this.p.a(true);
    }

    public sy b(ViewBean viewBean, boolean z) {
        if (z) {
            cC.c(this.a).b(this.R.getXmlName(), viewBean);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        return this.p.d(viewBean);
    }

    public sy b(ViewBean viewBean) {
        View b2 = this.p.b(viewBean);
        this.p.a(b2);
        String b3 = wq.b(viewBean.type);
        if (viewBean.id.indexOf(b3) == 0 && viewBean.id.length() > b3.length()) {
            try {
                int intValue = Integer.valueOf(viewBean.id.substring(b3.length())).intValue();
                if (this.e[viewBean.type] < intValue) {
                    this.e[viewBean.type] = intValue;
                }
            } catch (NumberFormatException unused) {
            }
        }
        b2.setOnTouchListener(this);
        return (sy) b2;
    }

    public final boolean b(View view) {
        for (ViewParent parent = view.getParent(); parent != null && parent != this; parent = parent.getParent()) {
            if ((parent instanceof ItemVerticalScrollView) || (parent instanceof ItemHorizontalScrollView)) {
                return true;
            }
        }
        return false;
    }

    public final boolean b(float f, float f2) {
        int[] iArr = new int[2];
        this.p.getLocationOnScreen(iArr);
        return f > ((float) iArr[0]) && f < ((float) iArr[0]) + (((float) this.p.getWidth()) * this.p.getScaleX()) && f2 > ((float) iArr[1]) && f2 < ((float) iArr[1]) + (((float) this.p.getHeight()) * this.p.getScaleY());
    }

    public ViewEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = 99;
        this.d = 99;
        this.e = new int[20];
        this.f = 0.0f;
        this.r = null;
        this.s = new Handler();
        this.t = false;
        this.u = 0.0f;
        this.v = 0.0f;
        this.w = 0;
        this.B = false;
        this.C = false;
        this.D = false;
        this.E = true;
        this.G = new int[2];
        this.I = 50;
        this.J = 30;
        this.P = true;
        this.S = true;
        this.T = false;
        this.ba = 0;
        this.da = true;
        this.ea = new gy(this);
        a(context);
    }

    public final void b(String str) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), 2131626470));
        aBVar.a(2131165669);
        aBVar.a(xB.b().a(getContext(), 2131626469));
        aBVar.b(xB.b().a(getContext(), 2131624986), new hy(this, str, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new iy(this, aBVar));
        aBVar.show();
    }

    public final void b() {
        if (this.z.isRunning()) {
            this.z.cancel();
        }
        if (this.A.isRunning()) {
            this.A.cancel();
        }
    }

    public void setPreviewColors(String str) {
        this.k.setBackgroundColor(ProjectFile.getColor(str, "color_primary_dark"));
        this.m.setBackgroundColor(ProjectFile.getColor(str, "color_primary_dark"));
        this.n.setBackgroundColor(ProjectFile.getColor(str, "color_primary"));
    }

    public final void b(boolean z) {
        this.y.bringToFront();
        if (!this.B) {
            f();
        }
        if (this.C == z) {
            return;
        }
        this.C = z;
        b();
        if (z) {
            this.z.start();
        } else {
            this.A.start();
        }
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        this.a = str;
        setPreviewColors(str);
        this.R = projectFileBean;
        this.b = projectFileBean.getXmlName();
        if (projectFileBean.fileType == 2) {
            this.l.setText(projectFileBean.fileName.substring(1));
        } else {
            this.l.setText(projectFileBean.getXmlName());
        }
        k();
        if (projectFileBean.fileType == 0) {
            this.S = projectFileBean.hasActivityOption(1);
            this.T = projectFileBean.hasActivityOption(2);
            if (projectFileBean.hasActivityOption(8)) {
                a(jC.a(str).h(projectFileBean.getXmlName()));
            }
        } else {
            this.S = false;
            this.T = false;
        }
        this.P = true;
        if (this.p != null) {
            this.p.setScId(str);
        }
    }

    public void a(String str) {
        sy syVar;
        sy a2 = this.p.a(str);
        if (a2 == null || (syVar = this.H) == a2) {
            return;
        }
        if (syVar != null) {
            syVar.setSelection(false);
        }
        a2.setSelection(true);
        this.H = a2;
    }

    public void a() {
        float f;
        float f2;
        float f3;
        float f4;
        if (this.S) {
            this.n.setVisibility(0);
        } else {
            this.n.setVisibility(8);
        }
        if (this.T) {
            this.k.setVisibility(8);
        } else {
            this.k.setVisibility(0);
        }
        this.p.setVisibility(0);
        this.g = getResources().getDisplayMetrics().widthPixels;
        this.h = getResources().getDisplayMetrics().heightPixels;
        int i = (int) (this.f * 56.0f);
        boolean z = this.g > this.h;
        int i2 = (int) (this.f * (!z ? 12.0f : 24.0f));
        int i3 = (int) (this.f * (!z ? 20.0f : 10.0f));
        int f5 = GB.f(getContext());
        int a2 = GB.a(getContext());
        int i4 = this.g;
        float f6 = this.f;
        int i5 = i4 - ((int) (120.0f * f6));
        int i6 = (((this.h - f5) - a2) - ((int) (f6 * 48.0f))) - ((int) (f6 * 48.0f));
        if (this.ca == 0 && this.da) {
            i6 -= i;
        }
        float min = Math.min(i5 / this.g, i6 / this.h);
        float min2 = Math.min((i5 - (i2 * 2)) / this.g, (i6 - (i3 * 2)) / this.h);
        if (!z) {
            this.aa.setBackgroundResource(2131165984);
        } else {
            this.aa.setBackgroundResource(2131165983);
        }
        this.aa.setLayoutParams(new FrameLayout.LayoutParams(this.g, this.h));
        this.aa.setScaleX(min);
        this.aa.setScaleY(min);
        int i7 = this.g;
        int i8 = this.h;
        this.aa.setX(-((int) ((i7 - (i7 * min)) / 2.0f)));
        this.aa.setY(-((int) ((i8 - (i8 * min)) / 2.0f)));
        int i9 = this.g;
        int i10 = i2 - ((int) ((i9 - (i9 * min2)) / 2.0f));
        if (this.k.getVisibility() == 0) {
            this.k.setLayoutParams(new FrameLayout.LayoutParams(this.g, f5));
            this.k.setScaleX(min2);
            this.k.setScaleY(min2);
            this.k.setX(i10);
            this.k.setY(i3 - ((int) ((f3 - f4) / 2.0f)));
            i3 += (int) (f5 * min2);
        }
        if (this.n.getVisibility() == 0) {
            this.n.setLayoutParams(new FrameLayout.LayoutParams(this.g, a2));
            this.n.setScaleX(min2);
            this.n.setScaleY(min2);
            this.n.setX(i10);
            this.n.setY(i3 - ((int) ((f - f2) / 2.0f)));
            i3 += (int) (a2 * min2);
        }
        int i11 = this.h;
        if (this.k.getVisibility() == 0) {
            i11 -= f5;
        }
        if (this.n.getVisibility() == 0) {
            i11 -= a2;
        }
        this.p.setLayoutParams(new FrameLayout.LayoutParams(this.g, i11));
        this.p.setScaleX(min2);
        this.p.setScaleY(min2);
        float f7 = i11;
        this.p.setX(i10);
        this.p.setY(i3 - ((int) ((f7 - (min2 * f7)) / 2.0f)));
        this.P = false;
    }

    public void a(PaletteWidget.a aVar, String str) {
        View a2 = this.i.a(aVar, str);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }

    public void extraWidgetLayout(String str, String str2) {
        View extraWidgetLayout = this.i.extraWidgetLayout(str, str2);
        extraWidgetLayout.setClickable(true);
        extraWidgetLayout.setOnTouchListener(this);
    }

    public void a(PaletteWidget.b bVar, String str, String str2, String str3) {
        View a2 = this.i.a(bVar, str, str2, str3);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }

    public void extraWidget(String str, String str2, String str3) {
        View extraWidget = this.i.extraWidget(str, str2, str3);
        extraWidget.setClickable(true);
        extraWidget.setOnTouchListener(this);
    }

    public boolean a(View view) {
        return view instanceof IconBase;
    }

    public final void a(String str, ArrayList<ViewBean> arrayList) {
        View a2 = this.j.a(str, arrayList);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }

    public final String a(int i) {
        String b2 = wq.b(i);
        StringBuilder sb = new StringBuilder();
        sb.append(b2);
        int[] iArr = this.e;
        int i2 = iArr[i] + 1;
        iArr[i] = i2;
        sb.append(i2);
        String sb2 = sb.toString();
        ArrayList<ViewBean> d = jC.a(this.a).d(this.b);
        while (true) {
            boolean z = false;
            Iterator<ViewBean> it = d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (sb2.equals(it.next().id)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b2);
            int[] iArr2 = this.e;
            int i3 = iArr2[i] + 1;
            iArr2[i] = i3;
            sb3.append(i3);
            sb2 = sb3.toString();
        }
    }

    public sy a(ArrayList<ViewBean> arrayList, boolean z) {
        if (z) {
            cC.c(this.a).a(this.R.getXmlName(), arrayList);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        sy syVar = null;
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ViewBean next = it.next();
            if (arrayList.indexOf(next) == 0) {
                syVar = b(next);
            } else {
                b(next);
            }
        }
        return syVar;
    }

    public sy a(ViewBean viewBean, boolean z) {
        if (z) {
            cC.c(this.a).a(this.R.getXmlName(), viewBean);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        return b(viewBean);
    }

    public void a(ArrayList<ViewBean> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        Iterator<ViewBean> it = arrayList.iterator();
        while (it.hasNext()) {
            b(it.next());
        }
    }

    public void a(ViewBean viewBean) {
        this.p.a(viewBean).setOnTouchListener(this);
    }

    public void a(sy syVar, boolean z) {
        sy syVar2 = this.H;
        if (syVar2 != null) {
            syVar2.setSelection(false);
        }
        this.H = syVar;
        this.H.setSelection(true);
        cy cyVar = this.L;
        if (cyVar != null) {
            cyVar.a(z, this.H.getBean().id);
        }
    }

    public final boolean a(float f, float f2) {
        int[] iArr = new int[2];
        this.y.getLocationOnScreen(iArr);
        return f > ((float) iArr[0]) && f < ((float) (iArr[0] + this.y.getWidth())) && f2 > ((float) iArr[1]) && f2 < ((float) (iArr[1] + this.y.getHeight()));
    }

    public final void a(boolean z) {
        if (this.D == z) {
            return;
        }
        this.D = z;
        if (this.D) {
            this.y.setImageResource(2131165897);
        } else {
            this.y.setImageResource(2131165896);
        }
    }
}
