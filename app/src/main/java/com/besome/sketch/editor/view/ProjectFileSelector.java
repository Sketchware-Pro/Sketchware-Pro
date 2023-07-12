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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.manage.ViewSelectorActivity;
import com.sketchware.remod.R;

import a.a.a.aB;
import a.a.a.by;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class ProjectFileSelector extends LinearLayout implements View.OnClickListener {
    private String sc_id;
    private TextView fileName;
    private by selectedFileChangeListener;
    private int currentFileType = -1;
    public String currentXmlFileName;
    public String currentJavaFileName;
    private boolean currentFileIsCustomView;
    private aB availableFilesDialog;

    public ProjectFileSelector(Context context) {
        super(context);
        initialize(context);
    }

    public ProjectFileSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public String getFileName() {
        if (currentFileType == 0) {
            return currentXmlFileName;
        } else {
            return currentJavaFileName;
        }
    }

    public int getFileType() {
        return currentFileType;
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (currentFileType == 0) {
                showAvailableViews();
            } else {
                showAvailableJavaFiles();
            }
        }
    }

    public void setFileType(int i) {
        currentFileType = i;
        if (currentFileType == 0) {
            setShownText(currentXmlFileName);
        } else {
            setShownText(currentJavaFileName);
        }
    }

    public void setJavaFileName(String fileName) {
        currentJavaFileName = fileName;
        setShownText(currentJavaFileName);
    }

    public void setOnSelectedFileChangeListener(by listener) {
        selectedFileChangeListener = listener;
    }

    public void setScId(String sc_id) {
        this.sc_id = sc_id;
    }

    public void setShownText(String shownText) {
        if (currentFileType == 1) {
            fileName.setText(shownText);
        } else if (shownText.indexOf("_drawer_") == 0) {
            fileName.setText(shownText.substring(1, shownText.indexOf(".xml")));
        } else {
            fileName.setText(shownText);
        }
    }

    public void setXmlFileName(ProjectFileBean projectFileBean) {
        if (projectFileBean == null) {
            currentXmlFileName = "main.xml";
        } else {
            int fileType = projectFileBean.fileType;
            if (fileType == 0) {
                currentJavaFileName = projectFileBean.getJavaName();
                currentXmlFileName = projectFileBean.getXmlName();
                currentFileIsCustomView = false;
            } else if (fileType == 1) {
                currentFileIsCustomView = true;
            } else if (fileType == 2) {
                currentFileIsCustomView = true;
            }

            if (currentFileType == 0) {
                selectedFileChangeListener.a(0, projectFileBean);
            } else if (currentFileType == 1) {
                selectedFileChangeListener.a(1, projectFileBean);
            }
            currentXmlFileName = projectFileBean.getXmlName();
        }
        setShownText(currentXmlFileName);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("file_selector_current_file_type", currentFileType);
        bundle.putString("file_selector_current_xml", currentXmlFileName);
        bundle.putString("file_selector_current_java", currentJavaFileName);
        bundle.putBoolean("file_selector_is_custom_xml", currentFileIsCustomView);
    }

    private void initialize(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        initializeFileName(context);
        initializeDropdown(context);
        setGravity(Gravity.CENTER_VERTICAL);
        TypedValue background = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, background, true);
        setBackgroundResource(background.resourceId);
        setOnClickListener(this);
        currentFileType = 0;
        currentXmlFileName = "main.xml";
        currentJavaFileName = "MainActivity.java";
        setShownText(currentXmlFileName);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        currentFileType = bundle.getInt("file_selector_current_file_type");
        currentXmlFileName = bundle.getString("file_selector_current_xml");
        currentJavaFileName = bundle.getString("file_selector_current_java");
        currentFileIsCustomView = bundle.getBoolean("file_selector_is_custom_xml");
        setFileType(currentFileType);
    }

    private void initializeFileName(Context context) {
        fileName = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = (int) wB.a(context, 8.0f);
        layoutParams.weight = 1.0f;
        fileName.setGravity(Gravity.LEFT | Gravity.CENTER);
        fileName.setLayoutParams(layoutParams);
        addView(fileName);
    }

    private void initializeDropdown(Context context) {
        int s = (int) wB.a(context, 24.0f);
        ImageView dropdown = new ImageView(context);
        dropdown.setLayoutParams(new LinearLayout.LayoutParams(s, s));
        dropdown.setImageResource(R.drawable.ic_arrow_drop_down_grey600_24dp);
        addView(dropdown);
    }

    private void showAvailableJavaFiles() {
        availableFilesDialog = new aB((Activity) getContext());
        availableFilesDialog.b(xB.b().a(getContext(), R.string.design_file_selector_title_java));
        availableFilesDialog.a(R.drawable.java_96);
        View customView = wB.a(getContext(), R.layout.file_selector_popup_select_java);
        RecyclerView recyclerView = customView.findViewById(R.id.file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new JavaFileAdapter());
        availableFilesDialog.a(customView);
        availableFilesDialog.show();
    }

    public void syncState() {
        if (selectedFileChangeListener != null) {
            ProjectFileBean bean;
            if (currentFileType == 0) {
                if (!currentXmlFileName.equals("main.xml") && jC.b(sc_id).b(currentXmlFileName) == null) {
                    setXmlFileName(null);
                }
                bean = jC.b(sc_id).b(currentXmlFileName);
            } else {
                if (!currentJavaFileName.equals("MainActivity.java") && jC.b(sc_id).a(currentJavaFileName) == null) {
                    setJavaFileName("MainActivity.java");
                }
                bean = jC.b(sc_id).a(currentJavaFileName);
            }
            selectedFileChangeListener.a(currentFileType, bean);
        }
    }

    private void showAvailableViews() {
        Intent intent = new Intent(getContext(), ViewSelectorActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("current_xml", currentXmlFileName);
        intent.putExtra("is_custom_view", currentFileIsCustomView);
        ((DesignActivity) getContext()).changeOpenFile.launch(intent);
    }

    private class JavaFileAdapter extends RecyclerView.Adapter<JavaFileAdapter.ViewHolder> {

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView javaFileName;
            public final TextView xmlFileName;

            public ViewHolder(View itemView) {
                super(itemView);
                javaFileName = itemView.findViewById(R.id.tv_filename);
                xmlFileName = itemView.findViewById(R.id.tv_linked_filename);
                itemView.setOnClickListener(v -> {
                    ProjectFileBean projectFileBean = jC.b(sc_id).b().get(getLayoutPosition());
                    setJavaFileName(projectFileBean.getJavaName());
                    if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                        currentXmlFileName = projectFileBean.getXmlName();
                    }
                    selectedFileChangeListener.a(1, projectFileBean);
                    availableFilesDialog.dismiss();
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.javaFileName.setVisibility(View.VISIBLE);
            holder.xmlFileName.setVisibility(View.VISIBLE);
            ProjectFileBean projectFileBean = jC.b(sc_id).b().get(position);
            String javaName = projectFileBean.getJavaName();
            String xmlName = projectFileBean.getXmlName();
            holder.javaFileName.setText(javaName);
            holder.xmlFileName.setText(xmlName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.file_selector_popup_select_java_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return jC.b(sc_id).b().size();
        }
    }
}
