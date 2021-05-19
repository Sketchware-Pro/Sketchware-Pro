package com.besome.sketch.editor.view;

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

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.Iw;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a._x;
import a.a.a.aB;
import a.a.a.ay;
import a.a.a.bB;
import a.a.a.cC;
import a.a.a.cy;
import a.a.a.jC;
import a.a.a.oB;
import a.a.a.sy;
import a.a.a.uy;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.editor.view.IdGenerator;
import mod.hey.studios.util.ProjectFile;

import static com.besome.sketch.editor.view.ViewEditor.a.ePaletteGroup_basic;
import static com.besome.sketch.editor.view.ViewEditor.a.ePaletteGroup_favorite;

public class ViewEditor extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    public final int c;
    public final Handler s;
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
    public boolean t;
    public float u;
    public float v;
    public int w;
    public ViewDummy x;
    public ImageView y;
    public ObjectAnimator z;

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
        this.ea = new Runnable() {
            @Override
            public void run() {
                e();
            }
        };
        a(context);
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
        this.ea = new Runnable() {
            @Override
            public void run() {
                e();
            }
        };
        a(context);
    }

    public final void f() {
        this.z = ObjectAnimator.ofFloat(this.y, "TranslationY", 0.0f);
        this.z.setDuration(500L);
        this.z.setInterpolator(new DecelerateInterpolator());
        ImageView imageView = this.y;
        this.A = ObjectAnimator.ofFloat(imageView, "TranslationY", (float) imageView.getHeight());
        this.A.setDuration(300L);
        this.A.setInterpolator(new DecelerateInterpolator());
        this.B = true;
    }

    public final void g() {
        this.V = new b(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        this.V.setLayoutParams(layoutParams);
        this.V.a(ePaletteGroup_basic);
        this.V.setSelected(true);
        this.V.setOnClickListener(v -> {
            o();
            this.V.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            this.W.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
            this.V.setSelected(true);
            this.W.setSelected(false);
        });
        this.W = new b(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, -1);
        layoutParams2.weight = 1.0f;
        this.W.setLayoutParams(layoutParams2);
        this.W.a(ePaletteGroup_favorite);
        this.W.setSelected(false);
        this.W.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
        this.W.setOnClickListener(v -> {
            n();
            this.V.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
            this.W.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            this.V.setSelected(false);
            this.W.setSelected(true);
        });
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

    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == 2131230821) {
            m();
        } else if (id2 == 2131231122) {
        }
    }

    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.P) {
            a();
        }
    }

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
            this.s.postDelayed(this.ea, (long) (ViewConfiguration.getLongPressTimeout() / 2));
            return true;
        } else if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (!(actionMasked == 3 || actionMasked == 8)) {
                    return true;
                }
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
            } else if (this.t) {
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
                    int i2 = this.I;
                    int i3 = this.J;
                    if (!a(this.r)) {
                        i2 = this.r.getWidth();
                        i3 = this.r.getHeight();
                    } else {
                        if (this.r instanceof IconLinearHorizontal) {
                            i2 = -1;
                        }
                        if (this.r instanceof IconLinearVertical) {
                            i3 = -1;
                        }
                    }
                    this.p.a((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), i2, i3);
                } else {
                    this.x.setAllow(false);
                    this.p.a(true);
                }
                return true;
            } else if (Math.abs(this.u - motionEvent.getRawX()) < ((float) this.w) && Math.abs(this.v - motionEvent.getRawY()) < ((float) this.w)) {
                return true;
            } else {
                this.r = null;
                this.s.removeCallbacks(this.ea);
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
                    boolean z2 = false;
                    for (int i4 = 0; i4 < uyVar.getData().size(); i4++) {
                        ViewBean viewBean = uyVar.getData().get(i4);
                        if (c(viewBean)) {
                            arrayList.add(viewBean.clone());
                            String str2 = viewBean.layout.backgroundResource;
                            String str3 = viewBean.image.resName;
                            if (!jC.d(this.a).l(str2) && Op.g().b(str2)) {
                                ProjectResourceBean a2 = Op.g().a(str2);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + this.a + File.separator + a2.resFullName);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                jC.d(this.a).b.add(a2);
                                z2 = true;
                            }
                            if (!jC.d(this.a).l(str3) && Op.g().b(str3)) {
                                ProjectResourceBean a3 = Op.g().a(str3);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a3.resFullName, wq.g() + File.separator + this.a + File.separator + a3.resFullName);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                                jC.d(this.a).b.add(a3);
                                z2 = true;
                            }
                        }
                    }
                    if (z2) {
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
                            if (!(arrayList.indexOf(next) == 0 || (str = next.parent) == null || str.length() <= 0)) {
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

    public void setIsAdLoaded(boolean z2) {
        this.da = z2;
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

    public void setPaletteLayoutVisible(int i2) {
        this.i.setLayoutVisible(i2);
    }

    public void setScreenType(int i2) {
        if (i2 == 1) {
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
        float f2 = this.f;
        this.I = (int) (((float) this.I) * f2);
        this.J = (int) (((float) this.J) * f2);
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

    public void b(ArrayList<ViewBean> arrayList, boolean z2) {
        if (z2) {
            cC.c(this.a).b(this.R.getXmlName(), arrayList);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size >= 0) {
                d(arrayList.get(size));
            } else {
                return;
            }
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
        sy g2 = this.p.g(viewBean);
        this.L.a();
        this.L.a(viewBean.id);
        return g2;
    }

    public final boolean c(ViewBean viewBean) {
        int i2;
        int i3 = this.R.fileType;
        if (i3 == 1) {
            int i4 = viewBean.type;
            return (i4 == 0 || i4 == 4 || i4 == 5 || i4 == 3 || i4 == 6 || i4 == 11 || i4 == 13 || i4 == 14 || i4 != 8) ? true : true;
        } else return i3 == 2 && (i2 = viewBean.type) != 0 && i2 != 12 && i2 != 2 && i2 != 4 && i2 != 5 && i2 != 3 && i2 != 6 && i2 != 11 && i2 != 13 && i2 != 14 && i2 == 8;
    }

    public void d(ViewBean viewBean) {
        this.p.f(viewBean);
    }

    public final void e() {
        boolean z2;
        boolean z3;
        View view = this.r;
        if (view != null) {
            if (a(view)) {
                View view2 = this.r;
                if (view2 instanceof uy) {
                    uy uyVar = (uy) view2;
                    Iterator<ViewBean> it = uyVar.getData().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().type == 17) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (!z2 || this.N.a()) {
                        Iterator<ViewBean> it2 = uyVar.getData().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                if (it2.next().type == 18) {
                                    z3 = true;
                                    break;
                                }
                            } else {
                                z3 = false;
                                break;
                            }
                        }
                        if (z3 && !this.N.c()) {
                            bB.b(getContext(), xB.b().a(getContext(), 2131625244), 0).show();
                            return;
                        }
                    } else {
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
                this.q.vibrate(100);
            }
            this.t = true;
            this.x.b(this.r);
            this.x.bringToFront();
            i();
            ViewDummy viewDummy = this.x;
            View view3 = this.r;
            float f2 = this.u;
            float f3 = this.v;
            viewDummy.a(view3, f2, f3, f2, f3);
            this.x.a(this.G);
            if (!a(this.r)) {
                this.r.setVisibility(8);
                b(true);
                this.p.e(((sy) this.r).getBean());
            } else if (this.r instanceof uy) {
                b(true);
                this.p.e(null);
            } else {
                b(false);
                this.p.e(null);
            }
            if (b(this.u, this.v)) {
                this.x.setAllow(true);
                int i2 = this.I;
                int i3 = this.J;
                if (!a(this.r)) {
                    i2 = this.r.getWidth();
                    i3 = this.r.getHeight();
                } else {
                    if (this.r instanceof IconLinearHorizontal) {
                        i2 = -1;
                    }
                    if (this.r instanceof IconLinearVertical) {
                        i3 = -1;
                    }
                }
                this.p.a((int) this.u, (int) this.v, i2, i3);
                return;
            }
            this.x.setAllow(false);
            this.p.a(true);
        }
    }

    public sy b(ViewBean viewBean, boolean z2) {
        if (z2) {
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
        ViewParent parent = view.getParent();
        while (parent != null && parent != this) {
            if ((parent instanceof ItemVerticalScrollView) || (parent instanceof ItemHorizontalScrollView)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public final boolean b(float f2, float f3) {
        int[] iArr = new int[2];
        this.p.getLocationOnScreen(iArr);
        return !(f2 <= ((float) iArr[0])) && !(f2 >= ((float) iArr[0]) + (((float) this.p.getWidth()) * this.p.getScaleX())) && !(f3 <= ((float) iArr[1])) && !(f3 >= ((float) iArr[1]) + (((float) this.p.getHeight()) * this.p.getScaleY()));
    }

    public final void b(String str) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), 2131626470));
        aBVar.a(2131165669);
        aBVar.a(xB.b().a(getContext(), 2131626469));
        aBVar.b(xB.b().a(getContext(), 2131624986), v -> {
            Rp.h().a(str, true);
            setFavoriteData(Rp.h().f());
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), v -> {
            aBVar.dismiss();
        });
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

    public final void b(boolean z2) {
        this.y.bringToFront();
        if (!this.B) {
            f();
        }
        if (this.C != z2) {
            this.C = z2;
            b();
            if (z2) {
                this.z.start();
            } else {
                this.A.start();
            }
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
        if (a2 != null && (syVar = this.H) != a2) {
            if (syVar != null) {
                syVar.setSelection(false);
            }
            a2.setSelection(true);
            this.H = a2;
        }
    }

    public void a() {
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
        int i2 = (int) (this.f * 56.0f);
        boolean z2 = this.g > this.h;
        int i3 = (int) (this.f * (!z2 ? 12.0f : 24.0f));
        int i4 = (int) (this.f * (!z2 ? 20.0f : 10.0f));
        int f2 = GB.f(getContext());
        int a2 = GB.a(getContext());
        int i5 = this.g;
        float f3 = this.f;
        int i6 = i5 - ((int) (120.0f * f3));
        int i7 = (((this.h - f2) - a2) - ((int) (f3 * 48.0f))) - ((int) (f3 * 48.0f));
        if (this.ca == 0 && this.da) {
            i7 -= i2;
        }
        float min = Math.min(((float) i6) / ((float) this.g), ((float) i7) / ((float) this.h));
        float min2 = Math.min(((float) (i6 - (i3 * 2))) / ((float) this.g), ((float) (i7 - (i4 * 2))) / ((float) this.h));
        if (!z2) {
            this.aa.setBackgroundResource(2131165984);
        } else {
            this.aa.setBackgroundResource(2131165983);
        }
        this.aa.setLayoutParams(new FrameLayout.LayoutParams(this.g, this.h));
        this.aa.setScaleX(min);
        this.aa.setScaleY(min);
        int i8 = this.g;
        int i9 = this.h;
        this.aa.setX((float) (-((int) ((((float) i8) - (((float) i8) * min)) / 2.0f))));
        this.aa.setY((float) (-((int) ((((float) i9) - (((float) i9) * min)) / 2.0f))));
        int i10 = this.g;
        int i11 = i3 - ((int) ((((float) i10) - (((float) i10) * min2)) / 2.0f));
        if (this.k.getVisibility() == 0) {
            this.k.setLayoutParams(new FrameLayout.LayoutParams(this.g, f2));
            this.k.setScaleX(min2);
            this.k.setScaleY(min2);
            float f4 = (float) f2;
            float f5 = f4 * min2;
            this.k.setX((float) i11);
            this.k.setY((float) (i4 - ((int) ((f4 - f5) / 2.0f))));
            i4 += (int) f5;
        }
        if (this.n.getVisibility() == 0) {
            this.n.setLayoutParams(new FrameLayout.LayoutParams(this.g, a2));
            this.n.setScaleX(min2);
            this.n.setScaleY(min2);
            float f6 = (float) a2;
            float f7 = f6 * min2;
            this.n.setX((float) i11);
            this.n.setY((float) (i4 - ((int) ((f6 - f7) / 2.0f))));
            i4 += (int) f7;
        }
        int i12 = this.h;
        if (this.k.getVisibility() == 0) {
            i12 -= f2;
        }
        if (this.n.getVisibility() == 0) {
            i12 -= a2;
        }
        this.p.setLayoutParams(new FrameLayout.LayoutParams(this.g, i12));
        this.p.setScaleX(min2);
        this.p.setScaleY(min2);
        float f8 = (float) i12;
        this.p.setX((float) i11);
        this.p.setY((float) (i4 - ((int) ((f8 - (min2 * f8)) / 2.0f))));
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

    public final String a(int i2) {
        String b2 = wq.b(i2);
        StringBuilder sb = new StringBuilder();
        sb.append(b2);
        int[] iArr = this.e;
        int i3 = iArr[i2] + 1;
        iArr[i2] = i3;
        sb.append(i3);
        String sb2 = sb.toString();
        ArrayList<ViewBean> d2 = jC.a(this.a).d(this.b);
        while (true) {
            boolean z2 = false;
            Iterator<ViewBean> it = d2.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (sb2.equals(it.next().id)) {
                        z2 = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z2) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b2);
            int[] iArr2 = this.e;
            int i4 = iArr2[i2] + 1;
            iArr2[i2] = i4;
            sb3.append(i4);
            sb2 = sb3.toString();
        }
    }

    public sy a(ArrayList<ViewBean> arrayList, boolean z2) {
        if (z2) {
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

    public sy a(ViewBean viewBean, boolean z2) {
        if (z2) {
            cC.c(this.a).a(this.R.getXmlName(), viewBean);
            ay ayVar = this.O;
            if (ayVar != null) {
                ayVar.a();
            }
        }
        return b(viewBean);
    }

    public void a(ArrayList<ViewBean> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<ViewBean> it = arrayList.iterator();
            while (it.hasNext()) {
                b(it.next());
            }
        }
    }

    public void a(ViewBean viewBean) {
        this.p.a(viewBean).setOnTouchListener(this);
    }

    public void a(sy syVar, boolean z2) {
        sy syVar2 = this.H;
        if (syVar2 != null) {
            syVar2.setSelection(false);
        }
        this.H = syVar;
        this.H.setSelection(true);
        cy cyVar = this.L;
        if (cyVar != null) {
            cyVar.a(z2, this.H.getBean().id);
        }
    }

    public final boolean a(float f2, float f3) {
        int[] iArr = new int[2];
        this.y.getLocationOnScreen(iArr);
        return !(f2 <= ((float) iArr[0])) && !(f2 >= ((float) (iArr[0] + this.y.getWidth()))) && !(f3 <= ((float) iArr[1])) && !(f3 >= ((float) (iArr[1] + this.y.getHeight())));
    }

    public final void a(boolean z2) {
        if (this.D != z2) {
            this.D = z2;
            if (this.D) {
                this.y.setImageResource(2131165897);
            } else {
                this.y.setImageResource(2131165896);
            }
        }
    }

    public enum a {
        ePaletteGroup_basic,
        ePaletteGroup_favorite
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

        public void onClick(View view) {
        }

        public void a(a aVar) {
            this.a = aVar;
            if (aVar == ePaletteGroup_basic) {
                this.c.setImageResource(2131166113);
            } else {
                this.c.setImageResource(2131166112);
            }
            setOnClickListener(this);
        }
    }
}