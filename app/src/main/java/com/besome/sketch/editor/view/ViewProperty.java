package com.besome.sketch.editor.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.ctrls.ViewIdSpinnerItem;
import com.besome.sketch.editor.property.ViewPropertyItems;
import com.besome.sketch.lib.ui.CustomHorizontalScrollView;
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.Iw;
import a.a.a.Jw;
import a.a.a.Kw;
import a.a.a.Lw;
import a.a.a.NB;
import a.a.a.Op;
import a.a.a.Qs;
import a.a.a.Rp;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

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

    public ViewProperty(Context context) {
        super(context);
        a(context);
    }

    public ViewProperty(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public void a(String str, Object obj) {
    }

    public void setOnEventClickListener(Qs qs) {
        n.setOnEventClickListener(qs);
    }

    public void setOnPropertyListener(Iw iw) {
        o = iw;
    }

    public void setOnPropertyTargetChangeListener(Jw jw) {
        g = jw;
    }

    public void setOnPropertyValueChangedListener(Lw lw) {
        p = lw;
        k.setOnPropertyValueChangedListener(new Lw() {
            @Override
            public void a(ViewBean viewBean) {
                if (p != null) {
                    p.a(viewBean);
                }
            }
        });
    }

    public final void b() {
        if (t.isRunning()) {
            t.cancel();
        }
        if (u.isRunning()) {
            u.cancel();
        }
    }

    public final void c() {
        if (t == null) {
            t = ObjectAnimator.ofFloat(j, View.TRANSLATION_Y, 0.0f);
            t.setDuration(400L);
            t.setInterpolator(new DecelerateInterpolator());
        }
        if (u == null) {
            u = ObjectAnimator.ofFloat(j, View.TRANSLATION_Y, wB.a(getContext(), 84.0f));
            u.setDuration(200L);
            u.setInterpolator(new DecelerateInterpolator());
        }
    }

    public void d() {
        ViewPropertyItems viewPropertyItems = k;
        if (viewPropertyItems != null) {
            viewPropertyItems.b();
        }
    }

    public void e() {
        for (int i2 = 0; i2 < q.getChildCount(); i2++) {
            a aVar = (a) q.getChildAt(i2);
            if (r == (Integer) aVar.getTag()) {
                aVar.setSelected(true);
                aVar.c.setTextColor(-1);
                aVar.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            } else {
                aVar.setSelected(false);
                aVar.c.setTextColor(-14868183);
                aVar.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
            }
        }
        if (f.a() < e.size()) {
            ViewBean viewBean = e.get(f.a());
            if (r == 0) {
                m.setVisibility(VISIBLE);
                j.setVisibility(VISIBLE);
                k.a(b, viewBean);
                a(viewBean);
                n.setVisibility(GONE);
            } else if (r == 1) {
                m.setVisibility(VISIBLE);
                k.e(viewBean);
                j.setVisibility(GONE);
            } else if (r == 2) {
                m.setVisibility(GONE);
                n.setVisibility(VISIBLE);
                n.a(b, c, viewBean);
            }
        }
    }

    public final void f() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), Resources.string.view_widget_favorites_save_title));
        aBVar.a(Resources.drawable.ic_bookmark_red_48dp);
        View view = wB.a(getContext(), Resources.layout.property_popup_save_to_favorite);
        ((TextView) view.findViewById(Resources.id.tv_favorites_guide)).setText(xB.b().a(getContext(), Resources.string.view_widget_favorites_save_guide_new));
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        NB nb = new NB(getContext(), view.findViewById(Resources.id.ti_input), Rp.h().g());
        aBVar.a(view);
        aBVar.b(xB.b().a(getContext(), 2131625031), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a() && nb.b()) {
                    String obj = editText.getText().toString();
                    ArrayList<ViewBean> viewBeans = jC.a(b).b(c.getXmlName(), e.get(f.a()));
                    for (ViewBean viewBean : viewBeans) {
                        String backgroundResource = viewBean.layout.backgroundResource;
                        String resName = viewBean.image.resName;
                        if (backgroundResource != null && !backgroundResource.equals("NONE") && jC.d(b).l(backgroundResource) && !Op.g().b(backgroundResource)) {
                            try {
                                Op.g().a(b, jC.d(b).g(backgroundResource));
                            } catch (Exception e) {
                                e.printStackTrace();
                                bB.b(getContext(), e.getMessage(), 0).show();
                            }
                        }
                        if (resName != null && !resName.equals("default_image") && !resName.equals("NONE") && jC.d(b).l(resName) && !Op.g().b(resName)) {
                            try {
                                Op.g().a(b, jC.d(b).g(resName));
                            } catch (Exception e2) {
                                bB.b(getContext(), e2.getMessage(), 0).show();
                            }
                        }
                    }
                    Rp.h().a(obj, viewBeans, true);
                    if (o != null) {
                        o.a();
                    }
                    bB.a(getContext(), xB.b().a(getContext(), Resources.string.common_message_complete_save), Toast.LENGTH_SHORT).show();
                    aBVar.dismiss();
                }
            }
        });
        aBVar.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(Context context) {
        wB.a(context, this, Resources.layout.view_property);
        q = findViewById(Resources.id.layout_property_group);
        //OnClickListener was empty inside Sketchware 3.10.0, replaced with null to save a class
        q.setOnClickListener(null);
        h = findViewById(Resources.id.hcv_property);
        m = findViewById(Resources.id.property_layout);
        i = findViewById(Resources.id.property_contents);
        j = findViewById(Resources.id.layout_property_see_all);
        n = findViewById(Resources.id.view_event);
        h.setOnScrollChangedListener(new CustomHorizontalScrollView.a() {
            @Override
            public void a(int i, int i1, int i2, int i3) {
                if (Math.abs(i - i3) <= 5) {
                    return;
                }
                if (i > i3) {
                    if (v) {
                        v = false;
                        b();
                        u.start();
                    }
                } else if (!(v)) {
                    v = true;
                    b();
                    t.start();
                }
            }
        });
        s = findViewById(Resources.id.img_save);
        s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    f();
                }
            }
        });
        d = findViewById(Resources.id.spn_widget);
        f = new c(context, e);
        d.setAdapter(f);
        d.setSelection(0);
        d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f.a(position);
                b(e.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        a();
        c();
        k = new ViewPropertyItems(getContext());
        k.setOrientation(HORIZONTAL);
        i.addView(k);
    }

    public final void b(ViewBean viewBean) {
        Jw jw = g;
        if (jw != null) {
            jw.a(viewBean.id);
        }
        if ("_fab".equals(viewBean.id)) {
            s.setVisibility(GONE);
        } else {
            s.setVisibility(VISIBLE);
        }
        k.setProjectFileBean(c);
        e();
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        b = str;
        c = projectFileBean;
    }

    public void a(String str) {
        for (int i = 0; i < e.size(); i++) {
            if (e.get(i).id.equals(str)) {
                d.setSelection(i);
                return;
            }
        }
    }

    public void a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        e.clear();
        e.addAll(arrayList);
        if (viewBean != null) {
            e.add(0, viewBean);
        }
        f.notifyDataSetChanged();
    }

    public final void a() {
        a(0, Resources.string.property_group_basic);
        a(1, Resources.string.property_group_recent);
        a(2, Resources.string.property_group_event);
    }

    public final void a(int i2, int i3) {
        a aVar = new a(getContext());
        aVar.a(i2, i3);
        aVar.setTag(i2);
        q.addView(aVar);
    }

    public void a(ViewBean viewBean) {
        b bVar = l;
        if (bVar == null) {
            l = new b(getContext());
            l.a("see_all", Resources.drawable.color_more_96, Resources.string.common_word_see_all);
            l.a(viewBean);
            j.addView(l);
            return;
        }
        bVar.a(viewBean);
    }

    class c extends BaseAdapter {

        public Context a;
        public int b;
        public ArrayList<ViewBean> c;

        public c(Context context, ArrayList<ViewBean> arrayList) {
            a = context;
            c = arrayList;
        }

        public void a(int i) {
            b = i;
        }

        @Override
        public int getCount() {
            if (c == null) {
                return 0;
            }
            return c.size();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, b == i, true);
        }

        @Override
        public Object getItem(int position) {
            return c.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return a(position, convertView, parent, false, false);
        }

        public int a() {
            return b;
        }

        public final ViewIdSpinnerItem a(int position, View convertView, ViewGroup parent, boolean z, boolean z2) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (convertView != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) convertView;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(a);
                viewIdSpinnerItem.setTextSize(Resources.dimen.text_size_body_small);
            }
            viewIdSpinnerItem.setDropDown(z2);
            ViewBean viewBean = c.get(position);
            viewIdSpinnerItem.a(ViewBean.getViewTypeResId(viewBean.type), viewBean.id, z);
            return viewIdSpinnerItem;
        }
    }

    class a extends LinearLayout implements View.OnClickListener {

        public int a;
        public View b;
        public TextView c;

        public a(Context context) {
            super(context);
            a(context);
        }

        public final void a(Context context) {
            wB.a(context, this, Resources.layout.property_group_item);
            b = findViewById(Resources.id.property_group_item);
            c = findViewById(Resources.id.tv_title);
        }

        public void onClick(View view) {
            r = (Integer) view.getTag();
            e();
        }

        public void a(int i, int i2) {
            a = i;
            setTag(i);
            c.setText(xB.b().a(getContext(), i2));
            setOnClickListener(this);
        }
    }

    class b extends LinearLayout implements View.OnClickListener {

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

        public final void a(Context context) {
            wB.a(context, this, Resources.layout.property_grid_item);
            b = findViewById(Resources.id.property_menu_item);
            c = findViewById(Resources.id.img_icon);
            d = findViewById(Resources.id.tv_title);
            e = findViewById(Resources.id.tv_sub_title);
        }

        public void onClick(View view) {
            if (o != null) {
                o.a(ViewProperty.this.c.getXmlName(), f);
            }
        }

        public void a(String str, int i, int i2) {
            b.setVisibility(VISIBLE);
            a = str;
            c.setImageResource(i);
            d.setText(xB.b().a(getContext(), i2));
            d.setTextColor(0xffff951b);
            setOnClickListener(this);
        }

        public void a(ViewBean viewBean) {
            f = viewBean;
        }
    }
}
