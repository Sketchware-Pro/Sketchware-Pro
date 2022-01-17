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

    public String b;
    public ProjectFileBean c;
    public Spinner spnWidget;
    public ArrayList<ViewBean> e = new ArrayList<>();
    public c f;
    public Jw g = null;
    public CustomHorizontalScrollView hcvProperty;
    public LinearLayout propertyContents;
    public LinearLayout layoutPropertySeeAll;
    public ViewPropertyItems viewPropertyItems;
    public b l;
    public View propertyLayout;
    public ViewEvents viewEvent;
    public Iw o = null;
    public Lw p;
    public LinearLayout layoutPropertyGroup;
    public int r;
    public ImageView imgSave;
    public ObjectAnimator t;
    public ObjectAnimator u;
    public boolean v = true;
    private String a = "see_all";

    public ViewProperty(Context context) {
        super(context);
        initialize(context);
    }

    public ViewProperty(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public void a(String str, Object obj) {
    }

    public void setOnEventClickListener(Qs onEventClickListener) {
        viewEvent.setOnEventClickListener(onEventClickListener);
    }

    public void setOnPropertyListener(Iw onPropertyListener) {
        o = onPropertyListener;
    }

    public void setOnPropertyTargetChangeListener(Jw onPropertyTargetChangeListener) {
        g = onPropertyTargetChangeListener;
    }

    public void setOnPropertyValueChangedListener(Lw onPropertyValueChangedListener) {
        p = onPropertyValueChangedListener;
        viewPropertyItems.setOnPropertyValueChangedListener(new Lw() {
            @Override
            public void a(ViewBean viewBean) {
                if (p != null) {
                    p.a(viewBean);
                }
            }
        });
    }

    private void b() {
        if (t.isRunning()) {
            t.cancel();
        }
        if (u.isRunning()) {
            u.cancel();
        }
    }

    private void c() {
        if (t == null) {
            t = ObjectAnimator.ofFloat(layoutPropertySeeAll, View.TRANSLATION_Y, 0.0f);
            t.setDuration(400L);
            t.setInterpolator(new DecelerateInterpolator());
        }
        if (u == null) {
            u = ObjectAnimator.ofFloat(layoutPropertySeeAll, View.TRANSLATION_Y, wB.a(getContext(), 84.0f));
            u.setDuration(200L);
            u.setInterpolator(new DecelerateInterpolator());
        }
    }

    public void d() {
        ViewPropertyItems viewPropertyItems = this.viewPropertyItems;
        if (viewPropertyItems != null) {
            viewPropertyItems.b();
        }
    }

    public void e() {
        for (int i2 = 0; i2 < layoutPropertyGroup.getChildCount(); i2++) {
            a aVar = (a) layoutPropertyGroup.getChildAt(i2);
            if (r == (Integer) aVar.getTag()) {
                aVar.setSelected(true);
                aVar.tvTitle.setTextColor(-1);
                aVar.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).start();
            } else {
                aVar.setSelected(false);
                aVar.tvTitle.setTextColor(-14868183);
                aVar.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f).start();
            }
        }
        if (f.getLayoutPosition() < e.size()) {
            ViewBean viewBean = e.get(f.getLayoutPosition());
            if (r == 0) {
                propertyLayout.setVisibility(VISIBLE);
                layoutPropertySeeAll.setVisibility(VISIBLE);
                viewPropertyItems.a(b, viewBean);
                a(viewBean);
                viewEvent.setVisibility(GONE);
            } else if (r == 1) {
                propertyLayout.setVisibility(VISIBLE);
                viewPropertyItems.e(viewBean);
                layoutPropertySeeAll.setVisibility(GONE);
            } else if (r == 2) {
                propertyLayout.setVisibility(GONE);
                viewEvent.setVisibility(VISIBLE);
                viewEvent.a(b, c, viewBean);
            }
        }
    }

    private void f() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), Resources.string.view_widget_favorites_save_title));
        dialog.a(Resources.drawable.ic_bookmark_red_48dp);
        View view = wB.a(getContext(), Resources.layout.property_popup_save_to_favorite);
        ((TextView) view.findViewById(Resources.id.tv_favorites_guide)).setText(xB.b().a(getContext(), Resources.string.view_widget_favorites_save_guide_new));
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        NB nb = new NB(getContext(), view.findViewById(Resources.id.ti_input), Rp.h().g());
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), 2131625031), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a() && nb.b()) {
                    String obj = editText.getText().toString();
                    ArrayList<ViewBean> viewBeans = jC.a(b).b(c.getXmlName(), e.get(f.getLayoutPosition()));
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
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void initialize(Context context) {
        wB.a(context, this, Resources.layout.view_property);
        layoutPropertyGroup = findViewById(Resources.id.layout_property_group);
        //OnClickListener was empty inside Sketchware 3.10.0, replaced with null to save a class
        layoutPropertyGroup.setOnClickListener(null);
        hcvProperty = findViewById(Resources.id.hcv_property);
        propertyLayout = findViewById(Resources.id.property_layout);
        propertyContents = findViewById(Resources.id.property_contents);
        layoutPropertySeeAll = findViewById(Resources.id.layout_property_see_all);
        viewEvent = findViewById(Resources.id.view_event);
        hcvProperty.setOnScrollChangedListener(new CustomHorizontalScrollView.a() {
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
        imgSave = findViewById(Resources.id.img_save);
        imgSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    f();
                }
            }
        });
        spnWidget = findViewById(Resources.id.spn_widget);
        f = new c(context, e);
        spnWidget.setAdapter(f);
        spnWidget.setSelection(0);
        spnWidget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        viewPropertyItems = new ViewPropertyItems(getContext());
        viewPropertyItems.setOrientation(HORIZONTAL);
        propertyContents.addView(viewPropertyItems);
    }

    private void b(ViewBean viewBean) {
        Jw jw = g;
        if (jw != null) {
            jw.a(viewBean.id);
        }
        if ("_fab".equals(viewBean.id)) {
            imgSave.setVisibility(GONE);
        } else {
            imgSave.setVisibility(VISIBLE);
        }
        viewPropertyItems.setProjectFileBean(c);
        e();
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        b = str;
        c = projectFileBean;
    }

    public void a(String str) {
        for (int i = 0; i < e.size(); i++) {
            if (e.get(i).id.equals(str)) {
                spnWidget.setSelection(i);
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

    private void a() {
        a(0, Resources.string.property_group_basic);
        a(1, Resources.string.property_group_recent);
        a(2, Resources.string.property_group_event);
    }

    private void a(int i2, int i3) {
        a aVar = new a(getContext());
        aVar.a(i2, i3);
        aVar.setTag(i2);
        layoutPropertyGroup.addView(aVar);
    }

    public void a(ViewBean viewBean) {
        b bVar = l;
        if (bVar == null) {
            l = new b(getContext());
            l.a("see_all", Resources.drawable.color_more_96, Resources.string.common_word_see_all);
            l.a(viewBean);
            layoutPropertySeeAll.addView(l);
            return;
        }
        bVar.a(viewBean);
    }

    class c extends BaseAdapter {

        public Context a;
        public int layoutPosition;
        public ArrayList<ViewBean> viewBeanList;

        public c(Context context, ArrayList<ViewBean> arrayList) {
            a = context;
            viewBeanList = arrayList;
        }

        public void a(int position) {
            layoutPosition = position;
        }

        @Override
        public int getCount() {
            if (viewBeanList == null) return 0;
            return viewBeanList.size();
        }

        public View getDropDownView(int position, View view, ViewGroup viewGroup) {
            return a(position, view, viewGroup, layoutPosition == position, true);
        }

        @Override
        public ViewBean getItem(int position) {
            return viewBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return a(position, convertView, parent, false, false);
        }

        public int getLayoutPosition() {
            return layoutPosition;
        }

        private ViewIdSpinnerItem a(int position, View convertView, ViewGroup parent, boolean z, boolean z2) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (convertView != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) convertView;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(a);
                viewIdSpinnerItem.setTextSize(Resources.dimen.text_size_body_small);
            }
            viewIdSpinnerItem.setDropDown(z2);
            ViewBean viewBean = viewBeanList.get(position);
            viewIdSpinnerItem.a(ViewBean.getViewTypeResId(viewBean.type), viewBean.id, z);
            return viewIdSpinnerItem;
        }
    }

    class a extends LinearLayout implements View.OnClickListener {

        public int a;
        public View propertyGroupItem;
        public TextView tvTitle;

        public a(Context context) {
            super(context);
            a(context);
        }

        private void a(Context context) {
            wB.a(context, this, Resources.layout.property_group_item);
            propertyGroupItem = findViewById(Resources.id.property_group_item);
            tvTitle = findViewById(Resources.id.tv_title);
        }

        public void onClick(View view) {
            r = (Integer) view.getTag();
            e();
        }

        public void a(int i, int i2) {
            a = i;
            setTag(i);
            tvTitle.setText(xB.b().a(getContext(), i2));
            setOnClickListener(this);
        }
    }

    class b extends LinearLayout implements View.OnClickListener {

        public String a;
        public View propertyMenuItem;
        public ImageView imgIcon;
        public TextView tvTitle;
        public TextView tvSubtitle;
        public ViewBean viewBean;

        public b(Context context) {
            super(context);
            wB.a(context, this, Resources.layout.property_grid_item);
            propertyMenuItem = findViewById(Resources.id.property_menu_item);
            imgIcon = findViewById(Resources.id.img_icon);
            tvTitle = findViewById(Resources.id.tv_title);
            tvSubtitle = findViewById(Resources.id.tv_sub_title);
        }

        @Override
        public void onClick(View view) {
            if (o != null) {
                o.a(ViewProperty.this.c.getXmlName(), viewBean);
            }
        }

        public void a(String str, int i, int i2) {
            propertyMenuItem.setVisibility(VISIBLE);
            a = str;
            imgIcon.setImageResource(i);
            tvTitle.setText(xB.b().a(getContext(), i2));
            tvTitle.setTextColor(0xffff951b);
            setOnClickListener(this);
        }

        public void a(ViewBean viewBean) {
            this.viewBean = viewBean;
        }
    }
}
