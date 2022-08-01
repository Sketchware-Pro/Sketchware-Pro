package com.besome.sketch.editor.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.manage.ViewSelectorActivity;
import com.sketchware.remod.R;

import a.a.a.aB;
import a.a.a.by;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

/* loaded from: classes.dex */
public class ProjectFileSelector extends LinearLayout implements View.OnClickListener {
    public String a;
    public TextView b;
    public ImageView c;
    public by d;
    public int e = -1;
    public String f;
    public String g;
    public boolean h;
    public aB i;

    public ProjectFileSelector(Context context) {
        super(context);
        c(context);
    }

    public String getFileName() {
        if (this.e == 0) {
            return this.f;
        }
        return this.g;
    }

    public int getFileType() {
        return this.e;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        if (this.e == 0) {
            c();
        } else {
            b();
        }
    }

    public void setFileType(int i) {
        this.e = i;
        if (this.e == 0) {
            setShownText(this.f);
        } else {
            setShownText(this.g);
        }
    }

    public void setJavaFileName(String str) {
        this.g = str;
        setShownText(this.g);
    }

    public void setOnSelectedFileChangeListener(by byVar) {
        this.d = byVar;
    }

    public void setScId(String str) {
        this.a = str;
    }

    public void setShownText(String str) {
        if (this.e == 1) {
            this.b.setText(str);
        } else if (str.indexOf("_drawer_") == 0) {
            this.b.setText(str.substring(1, str.indexOf(".xml")));
        } else {
            this.b.setText(str);
        }
    }

    public void setXmlFileName(ProjectFileBean projectFileBean) {
        if (projectFileBean == null) {
            this.f = "main.xml";
            setShownText(this.f);
            return;
        }
        int i = projectFileBean.fileType;
        if (i == 0) {
            this.g = projectFileBean.getJavaName();
            this.f = projectFileBean.getXmlName();
            this.h = false;
        } else if (i == 1) {
            this.h = true;
        } else if (i == 2) {
            this.h = true;
        }
        int i2 = this.e;
        if (i2 == 0) {
            this.d.a(0, projectFileBean);
        } else if (i2 == 1) {
            this.d.a(1, projectFileBean);
        }
        this.f = projectFileBean.getXmlName();
        setShownText(this.f);
    }

    /* loaded from: classes.dex */
    class a extends RecyclerView.a<a.a2> {

        /* loaded from: classes.dex */
        class a2 extends RecyclerView.v {
            public TextView t;
            public TextView u;

            public a2(View view) {
                super(view);
                this.t = (TextView) view.findViewById(R.id.tv_filename);
                this.u = (TextView) view.findViewById(R.id.tv_linked_filename);
                view.setOnClickListener(v -> {
                    ProjectFileBean projectFileBean = jC.b(ProjectFileSelector.this.a).b().get(j());
                    setJavaFileName(projectFileBean.getJavaName());
                    if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                        ProjectFileSelector.this.f = projectFileBean.getXmlName();
                    }
                    ProjectFileSelector.this.d.a(1, projectFileBean);
                    ProjectFileSelector.this.i.dismiss();
                });
            }
        }

        public a() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        /* renamed from: a */
        public void b(a2 aVar, int i) {
            aVar.t.setVisibility(View.VISIBLE);
            aVar.u.setVisibility(View.VISIBLE);
            ProjectFileBean projectFileBean = jC.b(ProjectFileSelector.this.a).b().get(i);
            String javaName = projectFileBean.getJavaName();
            String xmlName = projectFileBean.getXmlName();
            aVar.t.setText(javaName);
            aVar.u.setText(xmlName);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public a2 b(ViewGroup viewGroup, int i) {
            return new a2(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_selector_popup_select_java_list_item, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return jC.b(ProjectFileSelector.this.a).b().size();
        }
    }

    public void b(Bundle bundle) {
        bundle.putInt("file_selector_current_file_type", this.e);
        bundle.putString("file_selector_current_xml", this.f);
        bundle.putString("file_selector_current_java", this.g);
        bundle.putBoolean("file_selector_is_custom_xml", this.h);
    }

    public final void c(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        b(context);
        a(context);
        setGravity(Gravity.CENTER_VERTICAL);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        setOnClickListener(this);
        this.e = 0;
        this.f = "main.xml";
        this.g = "MainActivity.java";
        setShownText(this.f);
    }

    public void a(Bundle bundle) {
        this.e = bundle.getInt("file_selector_current_file_type");
        this.f = bundle.getString("file_selector_current_xml");
        this.g = bundle.getString("file_selector_current_java");
        this.h = bundle.getBoolean("file_selector_is_custom_xml");
        setFileType(this.e);
    }

    public ProjectFileSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c(context);
    }

    public final void b(Context context) {
        this.b = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = (int) wB.a(context, 8.0f);
        layoutParams.weight = 1.0f;
        this.b.setGravity(Gravity.LEFT | Gravity.CENTER);
        this.b.setLayoutParams(layoutParams);
        addView(this.b);
    }

    public final void a(Context context) {
        int a2 = (int) wB.a(context, 24.0f);
        this.c = new ImageView(context);
        this.c.setLayoutParams(new LinearLayout.LayoutParams(a2, a2));
        this.c.setImageResource(R.drawable.ic_arrow_drop_down_grey600_24dp);
        addView(this.c);
    }

    public void b() {
        this.i = new aB((Activity) getContext());
        this.i.b(xB.b().a(getContext(), R.string.design_file_selector_title_java));
        this.i.a(R.drawable.java_96);
        View a2 = wB.a(getContext(), R.layout.file_selector_popup_select_java);
        RecyclerView recyclerView = (RecyclerView) a2.findViewById(R.id.file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(new a());
        this.i.a(a2);
        this.i.show();
    }

    public void a() {
        ProjectFileBean a2;
        if (this.d == null) {
            return;
        }
        if (this.e == 0) {
            if (!this.f.equals("main.xml") && jC.b(this.a).b(this.f) == null) {
                setXmlFileName(null);
            }
            a2 = jC.b(this.a).b(this.f);
        } else {
            if (!this.g.equals("MainActivity.java") && jC.b(this.a).a(this.g) == null) {
                setJavaFileName("MainActivity.java");
            }
            a2 = jC.b(this.a).a(this.g);
        }
        this.d.a(this.e, a2);
    }

    public void c() {
        Intent intent = new Intent(getContext(), ViewSelectorActivity.class);
        intent.putExtra("sc_id", this.a);
        intent.putExtra("current_xml", this.f);
        intent.putExtra("is_custom_view", this.h);
        ((Activity) getContext()).startActivityForResult(intent, 263);
    }
}
