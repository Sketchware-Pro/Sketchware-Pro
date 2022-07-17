package com.besome.sketch.editor.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
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
import mod.hey.studios.util.Helper;

public class ProjectFileSelector extends LinearLayout implements View.OnClickListener {

    public String f;
    public String g;
    private String sc_id;
    private TextView b;
    private by d;
    private int e = -1;
    private boolean h;
    private aB dialog;

    public ProjectFileSelector(Context context) {
        super(context);
        c(context);
    }

    public ProjectFileSelector(Context context, AttributeSet attr) {
        super(context, attr);
        c(context);
    }

    public void a() {
        if (d != null) {
            ProjectFileBean projectFileBean;
            if (e == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                if (!f.equals("main.xml") && jC.b(sc_id).b(f) == null) {
                    setXmlFileName(null);
                }

                projectFileBean = jC.b(sc_id).b(f);
            } else {
                if (!g.equals("MainActivity.java") && jC.b(sc_id).a(g) == null) {
                    setJavaFileName("MainActivity.java");
                }

                projectFileBean = jC.b(sc_id).a(g);
            }

            d.a(e, projectFileBean);
        }
    }

    private void a(Context context) {
        int height_width = (int) wB.a(context, 24.0F);
        ImageView c = new ImageView(context);
        c.setLayoutParams(new LayoutParams(height_width, height_width));
        c.setImageResource(R.drawable.ic_arrow_drop_down_grey600_24dp);
        addView(c);
    }

    public void a(Bundle bundle) {
        e = bundle.getInt("file_selector_current_file_type");
        f = bundle.getString("file_selector_current_xml");
        g = bundle.getString("file_selector_current_java");
        h = bundle.getBoolean("file_selector_is_custom_xml");
        setFileType(e);
    }

    public void b() {
        dialog = new aB((Activity) getContext());
        dialog.b(Helper.getResString(R.string.design_file_selector_title_java));
        dialog.a(R.drawable.java_96);
        View view = wB.a(getContext(), R.layout.file_selector_popup_select_java);
        RecyclerView var2 = view.findViewById(R.id.file_list);
        var2.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        var2.setAdapter(new FileAdapter());
        dialog.a(view);
        dialog.show();
    }

    private void b(Context context) {
        b = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(0, -1);
        layoutParams.leftMargin = (int) wB.a(context, 8.0F);
        layoutParams.weight = 1.0F;
        b.setGravity(19);
        b.setLayoutParams(layoutParams);
        addView(b);
    }

    public void b(Bundle bundle) {
        bundle.putInt("file_selector_current_file_type", e);
        bundle.putString("file_selector_current_xml", f);
        bundle.putString("file_selector_current_java", g);
        bundle.putBoolean("file_selector_is_custom_xml", h);
    }

    public void c() {
        Intent intent = new Intent(getContext(), ViewSelectorActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("current_xml", f);
        intent.putExtra("is_custom_view", h);
        ((Activity) getContext()).startActivityForResult(intent, 263);
    }

    private void c(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        b(context);
        a(context);
        setGravity(16);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        setOnClickListener(this);
        e = ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY;
        f = "main.xml";
        g = "MainActivity.java";
        setShownText(f);
    }

    public String getFileName() {
        return e == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY ? f : g;
    }

    public int getFileType() {
        return e;
    }

    public void setFileType(int var1) {
        e = var1;
        if (e == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            setShownText(f);
        } else {
            setShownText(g);
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (e == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                c();
            } else {
                b();
            }
        }
    }

    public void setJavaFileName(String javaFileName) {
        g = javaFileName;
        setShownText(g);
    }

    public void setOnSelectedFileChangeListener(by onSelectedFileChangeListener) {
        d = onSelectedFileChangeListener;
    }

    public void setScId(String scId) {
        sc_id = scId;
    }

    public void setShownText(String fileName) {
        if (e == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW) {
            b.setText(fileName);
        } else if (fileName.indexOf("_drawer_") == 0) {
            b.setText(fileName.substring(1, fileName.indexOf(".xml")));
        } else {
            b.setText(fileName);
        }

    }

    public void setXmlFileName(ProjectFileBean projectFileBean) {
        if (projectFileBean == null) {
            f = "main.xml";
            setShownText(f);
        } else {
            int fileType = projectFileBean.fileType;
            if (fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                g = projectFileBean.getJavaName();
                f = projectFileBean.getXmlName();
                h = false;
            } else if (fileType == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW || fileType == ProjectFileBean.PROJECT_FILE_TYPE_DRAWER) {
                h = true;
            }

            fileType = e;
            if (fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                d.a(ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, projectFileBean);
            } else if (fileType == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW) {
                d.a(ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW, projectFileBean);
            }

            f = projectFileBean.getXmlName();
            setShownText(f);
        }
    }

    private class FileAdapter extends RecyclerView.a<FileAdapter.ViewHolder> {

        public FileAdapter() {
        }

        @Override
        public int a() {
            return jC.b(sc_id).b().size();
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            viewHolder.tvFilename.setVisibility(View.VISIBLE);
            viewHolder.tvLinkedFilename.setVisibility(View.VISIBLE);
            ProjectFileBean projectFileBean = jC.b(sc_id).b().get(position);
            viewHolder.tvFilename.setText(projectFileBean.getJavaName());
            viewHolder.tvLinkedFilename.setText(projectFileBean.getXmlName());
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_selector_popup_select_java_list_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {

            public TextView tvFilename;
            public TextView tvLinkedFilename;

            public ViewHolder(View itemView) {
                super(itemView);
                tvFilename = itemView.findViewById(R.id.tv_filename);
                tvLinkedFilename = itemView.findViewById(R.id.tv_linked_filename);
                itemView.setOnClickListener(view -> {
                    ProjectFileBean projectFileBean = jC.b(sc_id).b().get(j());
                    setJavaFileName(projectFileBean.getJavaName());
                    if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                        ProjectFileSelector.this.f = projectFileBean.getXmlName();
                    }
                    ProjectFileSelector.this.d.a(ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, projectFileBean);
                    ProjectFileSelector.this.dialog.dismiss();
                });
            }
        }
    }
}
