package a.a.a;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.event.AddEventActivity;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.besome.sketch.editor.event.CollapsibleEventLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mod.hey.studios.moreblock.ImportMoreblockHelper;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;

public class rs extends qA implements View.OnClickListener, MoreblockImporterDialog.CallBack {
    public ArrayList<ProjectResourceBean> A;
    public c C;
    public ArrayList<MoreBlockCollectionBean> D;
    public ProjectFileBean f;
    public a g;
    public b h;
    public RecyclerView j;
    public RecyclerView k;
    public FloatingActionButton l;
    public HashMap<Integer, ArrayList<EventBean>> m;
    public ArrayList<EventBean> n;
    public ArrayList<EventBean> o;
    public ArrayList<EventBean> p;
    public ArrayList<EventBean> q;
    public ArrayList<EventBean> r;
    public TextView s;
    public TextView t;
    public TextView u;
    public String v;
    public ArrayList<Pair<Integer, String>> w;
    public ArrayList<Pair<Integer, String>> x;
    public ArrayList<ProjectResourceBean> y;
    public ArrayList<ProjectResourceBean> z;
    public boolean i = false;
    public oB B = new oB();

    class b extends RecyclerView.a<b.a> {
        public int c = -1;
        public ArrayList<EventBean> d = new ArrayList<>();

        class a extends RecyclerView.v {
            public ImageView A;
            public ImageView B;
            public LinearLayout C;
            public LinearLayout D;
            public LinearLayout E;
            public CollapsibleEventLayout F;
            public LinearLayout t;
            public ImageView u;
            public TextView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public TextView z;

            public a(View view) {
                super(view);
                this.t = (LinearLayout) view.findViewById(2131230931);
                this.u = (ImageView) view.findViewById(2131231151);
                this.v = (TextView) view.findViewById(2131232193);
                this.w = (TextView) view.findViewById(2131232192);
                this.x = (TextView) view.findViewById(2131231972);
                this.y = (TextView) view.findViewById(2131231970);
                this.z = (TextView) view.findViewById(2131231971);
                this.A = (ImageView) view.findViewById(2131231156);
                this.B = (ImageView) view.findViewById(2131231169);
                this.C = (LinearLayout) view.findViewById(2131231477);
                this.D = (LinearLayout) view.findViewById(2131231048);
                this.E = (LinearLayout) view.findViewById(2131231047);
                this.F = new CollapsibleEventLayout(rs.this.getContext());
                this.F.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                this.E.addView(this.F);
                this.F.setButtonOnClickListener(v -> {
                    if (!mB.a()) {
                        rs.b.this.c = j();
                        EventBean eventBean = (rs.this.m.get(rs.this.g.c)).get(rs.b.this.c);
                        if (view instanceof CollapsibleButton) {
                            int i = ((CollapsibleButton) view).b;
                            if (i == 2) {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = false;
                                eventBean.isCollapsed = false;
                                rs.b bVar = rs.b.this;
                                bVar.c(bVar.c);
                                rs.b bVar2 = rs.b.this;
                                rs.this.b(bVar2.c);
                            } else {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = true;
                                rs.b bVar3 = rs.b.this;
                                bVar3.c(bVar3.c);
                            }
                        } else {
                            int id = view.getId();
                            if (id == 2131230923) {
                                eventBean.isConfirmation = false;
                                rs.b.this.c(rs.b.this.c);
                            } else if (id == 2131230927) {
                                int i2 = eventBean.buttonPressed;
                                if (i2 == 0) {
                                    eventBean.isConfirmation = false;
                                    eventBean.isCollapsed = true;
                                    rs.this.c(eventBean);
                                    rs.b.this.c(rs.b.this.c);
                                } else if (i2 == 1) {
                                    eventBean.isConfirmation = false;
                                    if (rs.this.g.c != 4) {
                                        rs.this.a(eventBean);
                                    } else {
                                        rs.this.b(eventBean);
                                    }
                                }
                                rs.this.l.f();
                            }
                        }
                    }
                });
                this.A.setOnClickListener(v -> {
                    rs.b.this.c = j();
                    EventBean eventBean = rs.this.m.get(rs.this.g.c).get(rs.b.this.c);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        E();
                    } else {
                        eventBean.isCollapsed = true;
                        D();
                    }
                });
                view.setOnLongClickListener(v -> {
                    rs.b.this.c = j();
                    EventBean eventBean = rs.this.m.get(rs.this.g.c).get(rs.b.this.c);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        E();
                    } else {
                        eventBean.isCollapsed = true;
                        D();
                    }
                    return true;
                });
                view.setOnClickListener(v -> {
                    if (!mB.a()) {
                        rs.b.this.c = j();
                        EventBean eventBean = rs.this.m.get(rs.this.g.c).get(rs.b.this.c);
                        rs.this.a(eventBean.targetId, eventBean.eventName, z.getText().toString());
                    }
                });
            }

            public void D() {
                gB.a(this.A, 0.0f, (Animator.AnimatorListener) null);
                gB.a((ViewGroup) this.D, 200, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        D.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }

            public void E() {
                this.D.setVisibility(0);
                gB.a(this.A, -180.0f, (Animator.AnimatorListener) null);
                gB.b(this.D, 200, null);
            }
        }

        public b() {
        }

        @Override
        public int a() {
            return this.d.size();
        }

        /* JADX WARN: Type inference failed for: r0v13, types: [android.widget.LinearLayout, android.widget.ImageView] */
        @Override
        public void b(a aVar, int i) {
            EventBean eventBean = this.d.get(i);
            aVar.v.setVisibility(0);
            aVar.C.setVisibility(0);
            aVar.B.setVisibility(0);
            aVar.B.setImageResource(oq.a(eventBean.eventName));
            aVar.F.e();
            if (eventBean.eventType == 5) {
                aVar.F.f();
            } else {
                aVar.F.c();
            }
            int i2 = eventBean.eventType;
            if (i2 == 3) {
                if (eventBean.eventName == "initializeLogic") {
                    aVar.F.b();
                }
                aVar.w.setText(eventBean.targetId);
                aVar.x.setBackgroundResource(oq.a(eventBean.eventName));
                aVar.y.setText(eventBean.eventName);
                aVar.z.setText(oq.a(eventBean.eventName, rs.this.getContext()));
                aVar.u.setImageResource(2131166270);
                aVar.B.setVisibility(8);
                aVar.v.setVisibility(8);
            } else {
                aVar.u.setImageResource(EventBean.getEventIconResource(i2, eventBean.targetType));
                int i3 = eventBean.eventType;
                if (i3 == 1) {
                    aVar.v.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (i3 == 4) {
                    aVar.v.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (i3 == 2) {
                    aVar.v.setText(ComponentBean.getComponentName(rs.this.getContext(), eventBean.targetType));
                } else if (i3 == 5) {
                    aVar.u.setImageResource(2131166270);
                    aVar.v.setVisibility(8);
                    aVar.B.setVisibility(8);
                }
                if (eventBean.targetId.equals("_fab")) {
                    aVar.w.setText("fab");
                } else {
                    aVar.w.setText(ReturnMoreblockManager.getMbName(eventBean.targetId));
                }
                aVar.x.setText(EventBean.getEventTypeName(eventBean.eventType));
                aVar.x.setBackgroundResource(EventBean.getEventTypeBgRes(eventBean.eventType));
                aVar.y.setText(eventBean.eventName);
                aVar.z.setText(oq.a(eventBean.eventName, rs.this.getContext()));
                if (eventBean.eventType == 5) {
                    aVar.z.setText(ReturnMoreblockManager.getMbTypeList(eventBean.targetId));
                }
            }
            if (eventBean.isCollapsed) {
                aVar.D.setVisibility(8);
                aVar.A.setRotation(0.0f);
                if (eventBean.isConfirmation) {
                    aVar.F.d();
                } else {
                    aVar.F.a();
                }
            } else {
                aVar.D.setVisibility(0);
                aVar.A.setRotation(-180.0f);
                if (eventBean.isConfirmation) {
                    aVar.F.d();
                } else {
                    aVar.F.a();
                }
            }
            aVar.D.getLayoutParams().height = -2;
        }

        public void a(ArrayList<EventBean> arrayList) {
            if (arrayList.size() == 0) {
                rs.this.s.setVisibility(0);
            } else {
                rs.this.s.setVisibility(8);
            }
            this.d = arrayList;
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427429, viewGroup, false));
        }
    }

    public static int a(int i) {
        if (i == 4) {
            return 2131165968;
        }
        if (i == 1) {
            return 2131165974;
        }
        if (i == 0) {
            return 2131165726;
        }
        if (i == 3) {
            return 2131165737;
        }
        return i == 2 ? 2131165504 : 0;
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 223) {
            return;
        }
        f();
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == 2131231054) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEventActivity.class);
            intent.putExtra("sc_id", this.v);
            intent.putExtra("project_file", this.f);
            intent.putExtra("category_index", this.g.c);
            startActivityForResult(intent, 223);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(2131427427, viewGroup, false);
        a(viewGroup2);
        setHasOptionsMenu(true);
        if (bundle != null) {
            this.v = bundle.getString("sc_id");
        } else {
            this.v = getActivity().getIntent().getStringExtra("sc_id");
        }
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.v);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.a<a.a2> {
        public int c = -1;

        class a2 extends RecyclerView.v implements View.OnClickListener {
            public ImageView t;
            public TextView u;
            public View v;

            public a2(View view) {
                super(view);
                this.t = (ImageView) view.findViewById(2131231151);
                this.u = (TextView) view.findViewById(2131232055);
                this.v = view.findViewById(2131231600);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                a aVar = a.this;
                aVar.c(aVar.c);
                a.this.c = j();
                a aVar2 = a.this;
                aVar2.c(aVar2.c);
                rs rsVar = rs.this;
                rsVar.a((ArrayList) rsVar.m.get(Integer.valueOf(a.this.c)));
                a aVar3 = a.this;
                if (aVar3.c == 4) {
                    rs.this.t.setVisibility(0);
                    rs.this.u.setVisibility(0);
                } else {
                    rs.this.t.setVisibility(8);
                    rs.this.u.setVisibility(8);
                }
                rs.this.h.a((ArrayList) rs.this.m.get(Integer.valueOf(a.this.c)));
                rs.this.h.c();
            }
        }

        public a() {
        }

        @Override
        public void b(a2 aVar, int i) {
            aVar.u.setText(rs.a(rs.this.getContext(), i));
            aVar.t.setImageResource(rs.a(i));
            if (this.c == i) {
                ef a2 = Ze.a(aVar.t);
                a2.c(1.0f);
                a2.d(1.0f);
                a2.a(300L);
                a2.a(new AccelerateInterpolator());
                a2.c();
                ef a3 = Ze.a(aVar.t);
                a3.c(1.0f);
                a3.d(1.0f);
                a3.a(300L);
                a3.a(new AccelerateInterpolator());
                a3.c();
                aVar.v.setVisibility(0);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1.0f);
                aVar.t.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                return;
            }
            ef a4 = Ze.a(aVar.t);
            a4.c(0.8f);
            a4.d(0.8f);
            a4.a(300L);
            a4.a(new DecelerateInterpolator());
            a4.c();
            ef a5 = Ze.a(aVar.t);
            a5.c(0.8f);
            a5.d(0.8f);
            a5.a(300L);
            a5.a(new DecelerateInterpolator());
            a5.c();
            aVar.v.setVisibility(8);
            ColorMatrix colorMatrix2 = new ColorMatrix();
            colorMatrix2.setSaturation(0.0f);
            aVar.t.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
        }

        @Override
        public a2 b(ViewGroup viewGroup, int i) {
            return new a2(LayoutInflater.from(viewGroup.getContext()).inflate(2131427377, viewGroup, false));
        }

        @Override
        public int a() {
            return rs.this.m.size();
        }
    }

    public class c extends RecyclerView.a<c.a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public ViewGroup t;
            public ImageView u;
            public TextView v;
            public ViewGroup w;

            public a(View view) {
                super(view);
                this.t = (ViewGroup) view.findViewById(2131231359);
                this.u = (ImageView) view.findViewById(2131231181);
                this.v = (TextView) view.findViewById(2131231893);
                this.w = (ViewGroup) view.findViewById(2131230793);
                this.u.setVisibility(8);
                this.t.setOnClickListener(v -> {
                    rs.c.this.c = j();
                    rs.c.a.this.c(rs.c.this.c);
                });
                this.w.setOnClickListener(v -> {
                    rs.c.this.c = j();
                    rs.c.a.this.c(rs.c.this.c);
                });
            }

            public final void c(int i) {
                if (rs.this.D.size() <= 0) {
                    return;
                }
                Iterator<MoreBlockCollectionBean> it = rs.this.D.iterator();
                while (it.hasNext()) {
                    it.next().isSelected = false;
                }
                rs.this.D.get(i).isSelected = true;
                rs.this.C.c();
            }
        }

        public c() {
        }

        @Override
        public void b(c.a holder, int position) {
            MoreBlockCollectionBean bean = rs.this.D.get(position);
            if (bean.isSelected) {
                holder.u.setVisibility(View.VISIBLE);
            } else {
                holder.u.setVisibility(View.GONE);
            }
            holder.v.setText(bean.name);
            holder.w.removeAllViews();
            holder.w.addView(ImportMoreblockHelper.optimizedBlockView(getContext(), bean.spec));
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(rs.this.getContext()).inflate(2131427511, viewGroup, false));
        }

        @Override
        public int a() {
            return rs.this.D.size();
        }
    }

    public ProjectFileBean d() {
        return this.f;
    }

    public final void e(String str) {
        if (this.y == null) {
            this.y = new ArrayList<>();
        }
        Iterator<String> it = jC.d(this.v).m().iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Op.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.y.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.y.add(a2);
    }

    public void f() {
        if (this.f == null) {
            return;
        }
        this.n.clear();
        this.o.clear();
        this.p.clear();
        this.q.clear();
        this.r.clear();
        ArrayList<EventBean> g = jC.a(this.v).g(this.f.getJavaName());
        Iterator<Pair<String, String>> it = jC.a(this.v).i(this.f.getJavaName()).iterator();
        while (it.hasNext()) {
            EventBean eventBean = new EventBean(5, -1, (String) it.next().first, "moreBlock");
            eventBean.initValue();
            this.n.add(eventBean);
        }
        EventBean eventBean2 = new EventBean(3, -1, "onCreate", "initializeLogic");
        eventBean2.initValue();
        this.q.add(eventBean2);
        Iterator<EventBean> it2 = g.iterator();
        while (it2.hasNext()) {
            EventBean next = it2.next();
            next.initValue();
            int i = next.eventType;
            if (i == 1) {
                this.o.add(next);
            } else if (i == 2) {
                this.p.add(next);
            } else if (i == 3) {
                this.q.add(next);
            } else if (i == 4) {
                this.r.add(next);
            }
        }
        if (this.g.c == -1) {
            this.h.a(this.m.get(0));
            a aVar = this.g;
            aVar.c = 0;
            if (aVar != null) {
                aVar.c();
            }
        }
        if (this.g.c == 4) {
            this.t.setVisibility(0);
            this.u.setVisibility(0);
        } else {
            this.t.setVisibility(8);
            this.u.setVisibility(8);
        }
        if (this.h == null) {
            return;
        }
        a aVar2 = this.g;
        if (aVar2 != null) {
            aVar2.c();
        }
        this.h.a(this.m.get(Integer.valueOf(this.g.c)));
        this.h.c();
    }

    public final void d(String str) {
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        Iterator<String> it = jC.d(this.v).k().iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Np.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.A.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.A.add(a2);
    }

    public final void b(EventBean eventBean) {
        if (jC.a(this.v).f(this.f.getJavaName(), eventBean.targetId)) {
            bB.b(getContext(), xB.b().a(getContext(), 2131625491), 0).show();
            return;
        }
        jC.a(this.v).n(this.f.getJavaName(), eventBean.targetId);
        bB.a(getContext(), xB.b().a(getContext(), 2131624935), 0).show();
        this.m.get(Integer.valueOf(this.g.c)).remove(this.h.c);
        b bVar = this.h;
        bVar.e(bVar.c);
        b bVar2 = this.h;
        bVar2.a(bVar2.c, bVar2.a());
    }

    public void c() {
        if (this.f == null) {
            return;
        }
        for (Map.Entry<Integer, ArrayList<EventBean>> entry : this.m.entrySet()) {
            Iterator<EventBean> it = entry.getValue().iterator();
            while (it.hasNext()) {
                it.next().initValue();
            }
        }
        this.h.c();
    }

    public final void a(ViewGroup viewGroup) {
        this.s = (TextView) viewGroup.findViewById(2131232063);
        this.k = (RecyclerView) viewGroup.findViewById(2131231046);
        this.j = (RecyclerView) viewGroup.findViewById(2131230876);
        this.l = (FloatingActionButton) viewGroup.findViewById(2131231054);
        this.s.setVisibility(8);
        this.s.setText(xB.b().a(getContext(), 2131625334));
        this.k.setHasFixedSize(true);
        this.k.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        this.j.setHasFixedSize(true);
        this.j.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        ((Bi) this.j.getItemAnimator()).a(false);
        this.g = new a();
        this.j.setAdapter(this.g);
        this.h = new b();
        this.k.setAdapter(this.h);
        this.l.setOnClickListener(this);
        // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
        this.k.a(new RecyclerView.m() {
            @Override
            // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
            public void a(RecyclerView recyclerView, int dx, int dy) {
                super.a(recyclerView, dx, dy);
                if (dy > 2) {
                    if (l.isEnabled()) {
                        // FloatingActionButton#hide()
                        l.c();
                    }
                } else if (dy < -2) {
                    if (l.isEnabled()) {
                        // FloatingActionButton#show()
                        l.f();
                    }
                }
            }
        });
        this.m = new HashMap<>();
        this.n = new ArrayList<>();
        this.o = new ArrayList<>();
        this.p = new ArrayList<>();
        this.q = new ArrayList<>();
        this.r = new ArrayList<>();
        this.m.put(0, this.q);
        this.m.put(1, this.o);
        this.m.put(2, this.p);
        this.m.put(3, this.r);
        this.m.put(4, this.n);
        this.t = (TextView) viewGroup.findViewById(2131232005);
        this.t.setText(xB.b().a(getContext(), 2131625488));
        this.u = (TextView) viewGroup.findViewById(2131232157);
        this.u.setText(xB.b().a(getContext(), 2131625487));
        this.t.setOnClickListener(v -> g());
        this.u.setOnClickListener(v -> {
            /* This is defined in a.a.a.ks, but not compilable, as Shared More Blocks classes were removed.
            Intent intent = new Intent(getActivity(), com.besome.sketch.shared.moreblocks.SharedMoreBlocksListActivity.class);
            intent.setFlags(536870912);
            startActivityForResult(intent, 464);
            */
        });
    }

    public final void e(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    public final void d(MoreBlockCollectionBean moreBlockCollectionBean) {
        this.w = new ArrayList<>();
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        this.z = new ArrayList<>();
        this.A = new ArrayList<>();
        Iterator<BlockBean> it = moreBlockCollectionBean.blocks.iterator();
        while (it.hasNext()) {
            BlockBean next = it.next();
            if (next.opCode.equals("getVar")) {
                if (next.type.equals("b")) {
                    b(0, next.spec);
                } else if (next.type.equals("d")) {
                    b(1, next.spec);
                } else if (next.type.equals("s")) {
                    b(2, next.spec);
                } else if (next.type.equals("a")) {
                    b(3, next.spec);
                } else if (next.type.equals("l")) {
                    if (next.typeName.equals("List Number")) {
                        a(1, next.spec);
                    } else if (next.typeName.equals("List String")) {
                        a(2, next.spec);
                    } else if (next.typeName.equals("List Map")) {
                        a(3, next.spec);
                    }
                }
            }
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str = next.parameters.get(i);
                    if (str.length() > 0 && str.charAt(0) != '@') {
                        if (gx.b("boolean.SelectBoolean")) {
                            b(0, str);
                        } else if (gx.b("double.SelectDouble")) {
                            b(1, str);
                        } else if (gx.b("String.SelectString")) {
                            b(2, str);
                        } else if (gx.b("Map")) {
                            b(3, str);
                        } else if (gx.b("ListInt")) {
                            a(1, str);
                        } else if (gx.b("ListString")) {
                            a(2, str);
                        } else if (gx.b("ListMap")) {
                            a(3, str);
                        } else if (!gx.b("resource_bg") && !gx.b("resource")) {
                            if (gx.b("sound")) {
                                f(str);
                            } else if (gx.b("font")) {
                                d(str);
                            }
                        } else {
                            e(str);
                        }
                    }
                }
            }
        }
        if (this.w.size() <= 0 && this.x.size() <= 0 && this.y.size() <= 0 && this.z.size() <= 0 && this.A.size() <= 0) {
            a(moreBlockCollectionBean);
        } else {
            f(moreBlockCollectionBean);
        }
    }

    public final void e() {
        this.D = Pp.h().f();
        this.C.c();
    }

    public final void b(int i) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), 2131625580));
        aBVar.a(2131165700);
        View a2 = wB.a(getContext(), 2131427640);
        ((TextView) a2.findViewById(2131231977)).setText(xB.b().a(getContext(), 2131625579));
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        NB nb = new NB(getContext(), (TextInputLayout) a2.findViewById(2131231816), Pp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), v -> {
            if (nb.b()) {
                a(editText.getText().toString(), n.get(i));
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void c(EventBean eventBean) {
        eC a2 = jC.a(this.v);
        String javaName = this.f.getJavaName();
        a2.a(javaName, eventBean.targetId + "_" + eventBean.eventName, new ArrayList<>());
        bB.a(getContext(), xB.b().a(getContext(), 2131624937), 0).show();
    }

    public final void c(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        boolean z = false;
        if (str.contains(" ")) {
            str = str.substring(0, str.indexOf(32));
        }
        Iterator<Pair<String, String>> it = jC.a(this.v).i(this.f.getJavaName()).iterator();
        while (true) {
            if (it.hasNext()) {
                if (((String) it.next().first).equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!z) {
            d(moreBlockCollectionBean);
        } else {
            b(moreBlockCollectionBean);
        }
    }

    @Override
    public void onSelected(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    public final void c(String str) {
        if (!Qp.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Qp.g().a(str);
        try {
            this.B.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + this.v + File.separator + a2.resFullName);
            jC.d(this.v).c.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void g() {
        this.D = Pp.h().f();
        new MoreblockImporterDialog(getActivity(), this.D, this).show();
    }

    public final void b(int i, String str) {
        if (this.w == null) {
            this.w = new ArrayList<>();
        }
        Iterator<Pair<Integer, String>> it = jC.a(this.v).k(this.f.getJavaName()).iterator();
        while (it.hasNext()) {
            Pair<Integer, String> next = it.next();
            if (((Integer) next.first).intValue() == i && ((String) next.second).equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = this.w.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (((Integer) next2.first).intValue() == i && ((String) next2.second).equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.w.add(new Pair<>(Integer.valueOf(i), str));
        }
    }

    public final void b(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), 2131625584));
        aBVar.a(2131165968);
        View a2 = wB.a(getContext(), 2131427640);
        ((TextView) a2.findViewById(2131231977)).setText(xB.b().a(getContext(), 2131625578));
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        ZB zb = new ZB(getContext(), (TextInputLayout) a2.findViewById(2131231816), uq.b, uq.a(), jC.a(this.v).a(this.f));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), v -> {
            if (zb.b()) {
                moreBlockCollectionBean.spec = editText.getText().toString() + (moreBlockCollectionBean.spec.contains(" ") ?
                        moreBlockCollectionBean.spec.substring(moreBlockCollectionBean.spec.indexOf(" ")) : "");
                d(moreBlockCollectionBean);
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void f(String str) {
        if (this.z == null) {
            this.z = new ArrayList<>();
        }
        Iterator<String> it = jC.d(this.v).p().iterator();
        while (it.hasNext()) {
            if (it.next().equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Qp.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.z.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.z.add(a2);
    }

    public void a(ProjectFileBean projectFileBean) {
        this.f = projectFileBean;
    }

    public final void f(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), 2131625583));
        aBVar.a(2131165391);
        aBVar.a(xB.b().a(getContext(), 2131625577));
        aBVar.b(xB.b().a(getContext(), 2131624980), v -> {
            for (Pair<Integer, String> pair : w) {
                eC eC = jC.a(this.v);
                eC.c(f.getJavaName(), pair.first, pair.second);
            }
            for (Pair<Integer, String> pair : x) {
                eC eC = jC.a(this.v);
                eC.b(f.getJavaName(), pair.first, pair.second);
            }
            for (ProjectResourceBean bean : y) {
                b(bean.resName);
            }
            for (ProjectResourceBean bean : z) {
                c(bean.resName);
            }
            for (ProjectResourceBean bean : A) {
                a(bean.resName);
            }
            a(moreBlockCollectionBean);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), 2131624974), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(EventBean eventBean) {
        jC.a(this.v).d(this.f.getJavaName(), eventBean.targetId, eventBean.eventName);
        eC a2 = jC.a(this.v);
        String javaName = this.f.getJavaName();
        a2.k(javaName, eventBean.targetId + "_" + eventBean.eventName);
        bB.a(getContext(), xB.b().a(getContext(), 2131624935), 0).show();
        this.m.get(Integer.valueOf(this.g.c)).remove(this.h.c);
        b bVar = this.h;
        bVar.e(bVar.c);
        b bVar2 = this.h;
        bVar2.a(bVar2.c, bVar2.a());
    }

    public final void b(String str) {
        if (!Op.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Op.g().a(str);
        try {
            this.B.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + this.v + File.separator + a2.resFullName);
            jC.d(this.v).b.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a(ArrayList<EventBean> arrayList) {
        Iterator<EventBean> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().initValue();
        }
    }

    public final void a(String str, String str2, String str3) {
        Intent intent = new Intent(getActivity(), LogicEditorActivity.class);
        intent.setFlags(536870912);
        intent.putExtra("sc_id", this.v);
        intent.putExtra("id", str);
        intent.putExtra("event", str2);
        intent.putExtra("project_file", this.f);
        intent.putExtra("event_text", str3);
        startActivity(intent);
    }

    public static String a(Context context, int i) {
        if (i == 4) {
            return xB.b().a(context, 2131625007);
        }
        if (i == 1) {
            return xB.b().a(context, 2131625046);
        }
        if (i == 0) {
            return xB.b().a(context, 2131624969);
        }
        if (i == 3) {
            return xB.b().a(context, 2131624991);
        }
        return i == 2 ? xB.b().a(context, 2131624979) : "";
    }

    public final void a(String str, EventBean eventBean) {
        String b2 = jC.a(this.v).b(this.f.getJavaName(), eventBean.targetId);
        eC a2 = jC.a(this.v);
        String javaName = this.f.getJavaName();
        ArrayList<BlockBean> a3 = a2.a(javaName, eventBean.targetId + "_" + eventBean.eventName);
        Iterator<BlockBean> it = a3.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            BlockBean next = it.next();
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                boolean z3 = z2;
                boolean z4 = z;
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str2 = next.parameters.get(i);
                    if (!gx.b("resource") && !gx.b("resource_bg")) {
                        if (gx.b("sound")) {
                            if (jC.d(this.v).m(str2) && !Qp.g().b(str2)) {
                                try {
                                    Qp.g().a(this.v, jC.d(this.v).j(str2));
                                } catch (Exception unused) {
                                    z3 = true;
                                }
                            }
                        } else {
                            if (gx.b("font") && jC.d(this.v).k(str2) && !Np.g().b(str2)) {
                                Np.g().a(this.v, jC.d(this.v).e(str2));
                            }
                        }
                    } else {
                        if (jC.d(this.v).l(str2) && !Op.g().b(str2)) {
                            Op.g().a(this.v, jC.d(this.v).g(str2));
                        }
                    }
                    z4 = true;
                }
                z = z4;
                z2 = z3;
            }
        }
        if (z) {
            if (z2) {
                bB.b(getContext(), xB.b().a(getContext(), 2131625581), 0).show();
            } else {
                bB.a(getContext(), xB.b().a(getContext(), 2131625582), 0).show();
            }
        }
        try {
            Pp.h().a(str, b2, a3, true);
        } catch (Exception unused2) {
            bB.b(getContext(), xB.b().a(getContext(), 2131624915), 0).show();
        }
    }

    public final void a(int i, String str) {
        if (this.x == null) {
            this.x = new ArrayList<>();
        }
        Iterator<Pair<Integer, String>> it = jC.a(this.v).j(this.f.getJavaName()).iterator();
        while (it.hasNext()) {
            Pair<Integer, String> next = it.next();
            if (((Integer) next.first).intValue() == i && ((String) next.second).equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = this.x.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (((Integer) next2.first).intValue() == i && ((String) next2.second).equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.x.add(new Pair<>(Integer.valueOf(i), str));
        }
    }

    public final void a(String str) {
        if (!Np.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Np.g().a(str);
        try {
            this.B.a(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + a2.resFullName, wq.d() + File.separator + this.v + File.separator + a2.resFullName);
            jC.d(this.v).d.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        String substring = str.contains(" ") ? str.substring(0, str.indexOf(32)) : str;
        jC.a(this.v).a(this.f.getJavaName(), substring, str);
        eC a2 = jC.a(this.v);
        String javaName = this.f.getJavaName();
        a2.a(javaName, substring + "_moreBlock", moreBlockCollectionBean.blocks);
        bB.a(getContext(), xB.b().a(getContext(), 2131624938), 0).show();
        f();
    }
}
