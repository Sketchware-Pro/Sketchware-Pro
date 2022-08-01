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
        if (e == 0) {
            return f;
        } else {
            return g;
        }
    }

    public int getFileType() {
        return e;
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (e == 0) {
                c();
            } else {
                b();
            }
        }
    }

    public void setFileType(int i) {
        e = i;
        if (e == 0) {
            setShownText(f);
        } else {
            setShownText(g);
        }
    }

    public void setJavaFileName(String fileName) {
        g = fileName;
        setShownText(g);
    }

    public void setOnSelectedFileChangeListener(by listener) {
        d = listener;
    }

    public void setScId(String sc_id) {
        a = sc_id;
    }

    public void setShownText(String shownText) {
        if (e == 1) {
            b.setText(shownText);
        } else if (shownText.indexOf("_drawer_") == 0) {
            b.setText(shownText.substring(1, shownText.indexOf(".xml")));
        } else {
            b.setText(shownText);
        }
    }

    public void setXmlFileName(ProjectFileBean projectFileBean) {
        if (projectFileBean == null) {
            f = "main.xml";
        } else {
            int fileType = projectFileBean.fileType;
            if (fileType == 0) {
                g = projectFileBean.getJavaName();
                f = projectFileBean.getXmlName();
                h = false;
            } else if (fileType == 1) {
                h = true;
            } else if (fileType == 2) {
                h = true;
            }

            if (e == 0) {
                d.a(0, projectFileBean);
            } else if (e == 1) {
                d.a(1, projectFileBean);
            }
            f = projectFileBean.getXmlName();
        }
        setShownText(f);
    }

    class a extends RecyclerView.a<a.a2> {

        class a2 extends RecyclerView.v {
            public TextView t;
            public TextView u;

            public a2(View itemView) {
                super(itemView);
                t = itemView.findViewById(R.id.tv_filename);
                u = itemView.findViewById(R.id.tv_linked_filename);
                itemView.setOnClickListener(v -> {
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

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(a2 holder, int position) {
            holder.t.setVisibility(View.VISIBLE);
            holder.u.setVisibility(View.VISIBLE);
            ProjectFileBean projectFileBean = jC.b(ProjectFileSelector.this.a).b().get(position);
            String javaName = projectFileBean.getJavaName();
            String xmlName = projectFileBean.getXmlName();
            holder.t.setText(javaName);
            holder.u.setText(xmlName);
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public a2 b(ViewGroup parent, int viewType) {
            return new a2(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_selector_popup_select_java_list_item, parent, false));
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return jC.b(ProjectFileSelector.this.a).b().size();
        }
    }

    public void b(Bundle bundle) {
        bundle.putInt("file_selector_current_file_type", e);
        bundle.putString("file_selector_current_xml", f);
        bundle.putString("file_selector_current_java", g);
        bundle.putBoolean("file_selector_is_custom_xml", h);
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
        e = 0;
        f = "main.xml";
        g = "MainActivity.java";
        setShownText(f);
    }

    public void a(Bundle bundle) {
        e = bundle.getInt("file_selector_current_file_type");
        f = bundle.getString("file_selector_current_xml");
        g = bundle.getString("file_selector_current_java");
        h = bundle.getBoolean("file_selector_is_custom_xml");
        setFileType(e);
    }

    public ProjectFileSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        c(context);
    }

    public final void b(Context context) {
        b = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = (int) wB.a(context, 8.0f);
        layoutParams.weight = 1.0f;
        b.setGravity(Gravity.LEFT | Gravity.CENTER);
        b.setLayoutParams(layoutParams);
        addView(b);
    }

    public final void a(Context context) {
        int a2 = (int) wB.a(context, 24.0f);
        c = new ImageView(context);
        c.setLayoutParams(new LinearLayout.LayoutParams(a2, a2));
        c.setImageResource(R.drawable.ic_arrow_drop_down_grey600_24dp);
        addView(c);
    }

    public void b() {
        i = new aB((Activity) getContext());
        i.b(xB.b().a(getContext(), R.string.design_file_selector_title_java));
        i.a(R.drawable.java_96);
        View customView = wB.a(getContext(), R.layout.file_selector_popup_select_java);
        RecyclerView recyclerView = customView.findViewById(R.id.file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(new a());
        i.a(customView);
        i.show();
    }

    public void a() {
        if (d != null) {
            ProjectFileBean bean;
            if (e == 0) {
                if (!f.equals("main.xml") && jC.b(a).b(f) == null) {
                    setXmlFileName(null);
                }
                bean = jC.b(a).b(f);
            } else {
                if (!g.equals("MainActivity.java") && jC.b(a).a(g) == null) {
                    setJavaFileName("MainActivity.java");
                }
                bean = jC.b(a).a(g);
            }
            d.a(e, bean);
        }
    }

    public void c() {
        Intent intent = new Intent(getContext(), ViewSelectorActivity.class);
        intent.putExtra("sc_id", a);
        intent.putExtra("current_xml", f);
        intent.putExtra("is_custom_view", h);
        ((Activity) getContext()).startActivityForResult(intent, 263);
    }
}
