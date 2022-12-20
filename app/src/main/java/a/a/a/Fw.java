package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.view.AddViewActivity;
import com.besome.sketch.editor.manage.view.ManageViewActivity;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint("ResourceType")
public class Fw extends qA {

    public RecyclerView f;
    public Boolean k = false;
    public TextView l;
    public int[] m = new int[19];
    private ProjectFilesAdapter projectFilesAdapter = null;
    private String sc_id;
    private String isAppCompatUsed = "N";
    private ArrayList<ProjectFileBean> activitiesFiles;

    public Fw() {
    }

    public final String a(int var1, String var2) {
        String var3 = wq.b(var1);
        StringBuilder var4 = new StringBuilder();
        var4.append(var3);
        int[] var5 = m;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var9 = var4.toString();
        ArrayList<ViewBean> var12 = jC.a(sc_id).d(var2);
        var2 = var9;

        while (true) {
            boolean var7 = false;
            Iterator<ViewBean> var10 = var12.iterator();

            boolean var13;
            while (true) {
                var13 = var7;
                if (!var10.hasNext()) {
                    break;
                }

                if (var2.equals(var10.next().id)) {
                    var13 = true;
                    break;
                }
            }

            if (!var13) {
                return var2;
            }

            StringBuilder var8 = new StringBuilder();
            var8.append(var3);
            int[] var11 = m;
            var6 = var11[var1] + 1;
            var11[var1] = var6;
            var8.append(var6);
            var2 = var8.toString();
        }
    }

    public final ArrayList<ViewBean> a(String var1) {
        return rq.f(var1);
    }

    public void a(ProjectFileBean var1) {
        activitiesFiles.add(var1);
        projectFilesAdapter.c();
    }

    public void a(boolean var1) {
        k = var1;
        e();
        projectFilesAdapter.c();
    }

    public final void b(ProjectFileBean projectFileBean) {
        ProjectFileBean newProjectFile = activitiesFiles.get(projectFilesAdapter.layoutPosition);
        newProjectFile.keyboardSetting = projectFileBean.keyboardSetting;
        newProjectFile.orientation = projectFileBean.orientation;
        newProjectFile.options = projectFileBean.options;
        if (projectFileBean.hasActivityOption(4)) {
            ((ManageViewActivity) getActivity()).b(ProjectFileBean.getDrawerName(newProjectFile.fileName));
        } else {
            ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(newProjectFile.fileName));
        }

        if (projectFileBean.hasActivityOption(4) || projectFileBean.hasActivityOption(8)) {
            jC.c(sc_id).c().useYn = "Y";
        }
    }

    public ArrayList<ProjectFileBean> c() {
        return activitiesFiles;
    }

    public final void c(ProjectFileBean var1) {
        ProjectFileBean projectFileBean = activitiesFiles.get(projectFilesAdapter.layoutPosition);

        ArrayList<ViewBean> fileViewBeans = jC.a(sc_id).d(projectFileBean.getXmlName());
        for (int i = fileViewBeans.size() - 1; i >= 0; --i) {
            jC.a(sc_id).a(projectFileBean, fileViewBeans.get(i));
        }

        ArrayList<ViewBean> var6 = a(var1.presetName);
        for (ViewBean viewBean : eC.a(var6)) {
            viewBean.id = a(viewBean.type, projectFileBean.getXmlName());
            jC.a(sc_id).a(projectFileBean.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean.fileType == 0) {
                jC.a(sc_id).a(projectFileBean.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    public void d() {
        sc_id = getActivity().getIntent().getStringExtra("sc_id");
        isAppCompatUsed = getActivity().getIntent().getStringExtra("compatUseYn");
        ArrayList<ProjectFileBean> projectFiles = jC.b(sc_id).b();
        if (projectFiles != null) {
            boolean isMainActivityFile = false;
            for (ProjectFileBean projectFileBean : projectFiles) {
                if (projectFileBean.fileName.equals("main")) {
                    activitiesFiles.add(0, projectFileBean);
                    isMainActivityFile = true;
                } else {
                    activitiesFiles.add(projectFileBean);
                }
            }
            if (!isMainActivityFile) {
                activitiesFiles.add(0, new ProjectFileBean(0, "main"));
            }
        }
    }

    public final void e() {
        for (ProjectFileBean projectFileBean : activitiesFiles) {
            projectFileBean.isSelected = false;
        }
    }

    public void f() {
        for (int i = 0, filesSize = activitiesFiles.size(); i < filesSize; i++) {
            if (i < 0) {
                projectFilesAdapter.c();
                return;
            }
            ProjectFileBean projectFileBean = activitiesFiles.get(i);
            if (projectFileBean.isSelected) {
                activitiesFiles.remove(projectFileBean);
                if (projectFileBean.hasActivityOption(4)) {
                    ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(projectFileBean.fileName));
                }
            }
        }
    }

    public void g() {
        if (activitiesFiles != null) {
            if (activitiesFiles.size() == 0) {
                l.setVisibility(View.VISIBLE);
                f.setVisibility(View.GONE);
            } else {
                f.setVisibility(View.VISIBLE);
                l.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            d();
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            isAppCompatUsed = savedInstanceState.getString("compatUseYn");
            activitiesFiles = savedInstanceState.getParcelableArrayList("activities");
        }

        projectFilesAdapter.c();
        g();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 265) {
            if (resultCode == Activity.RESULT_OK) {
                b(data.getParcelableExtra("project_file"));
                projectFilesAdapter.c(projectFilesAdapter.layoutPosition);
            }
        } else if (requestCode == 276 && resultCode == Activity.RESULT_OK) {
            ProjectFileBean projectFileBean = data.getParcelableExtra("preset_data");
            b(projectFileBean);
            c(projectFileBean);
            projectFilesAdapter.c(projectFilesAdapter.layoutPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) layoutInflater.inflate(2131427442, parent, false);
        activitiesFiles = new ArrayList<>();
        f = root.findViewById(2131231442);
        f.setHasFixedSize(true);
        f.setLayoutManager(new LinearLayoutManager(getContext()));
        projectFilesAdapter = new ProjectFilesAdapter(this, f);
        f.setAdapter(projectFilesAdapter);
        l = root.findViewById(2131231997);
        l.setText(xB.b().a(getActivity(), 2131625290));
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle newState) {
        newState.putString("sc_id", sc_id);
        newState.putString("compatUseYn", isAppCompatUsed);
        newState.putParcelableArrayList("activities", activitiesFiles);
        super.onSaveInstanceState(newState);
    }

    public class ProjectFilesAdapter extends RecyclerView.a<ProjectFilesAdapter.ViewHolder> {
        public final Fw d;
        public int layoutPosition;

        public ProjectFilesAdapter(Fw var1, RecyclerView recyclerView) {
            d = var1;
            layoutPosition = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new Bw(this, var1));
            }

        }

        @Override
        public int a() {
            return d.activitiesFiles != null ? d.activitiesFiles.size() : 0;
        }

        @Override
        public void b(ViewHolder viewHolder, int position) {
            viewHolder.v.setVisibility(View.VISIBLE);
            viewHolder.y.setVisibility(View.GONE);
            if (position == 0) {
                viewHolder.t.setVisibility(View.GONE);
            } else if (d.k) {
                viewHolder.y.setVisibility(View.VISIBLE);
                viewHolder.v.setVisibility(View.GONE);
            } else {
                viewHolder.y.setVisibility(View.GONE);
                viewHolder.v.setVisibility(View.VISIBLE);
            }

            ProjectFileBean projectFileBean = d.activitiesFiles.get(position);
            viewHolder.v.setImageResource(getImageResByOptions(projectFileBean.options));
            viewHolder.w.setText(projectFileBean.getXmlName());
            viewHolder.x.setText(projectFileBean.getJavaName());
            if (projectFileBean.isSelected) {
                viewHolder.z.setImageResource(2131165707);
            } else {
                viewHolder.z.setImageResource(2131165875);
            }
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(2131427571, parent, false));
        }

        private int getImageResByOptions(int options) {
            String option = String.format("%4s", Integer.toBinaryString(options)).replace(' ', '0');
            Resources resources = getContext().getResources();
            return resources.getIdentifier("activity_" + option, "drawable", getContext().getPackageName());
        }

        public class ViewHolder extends RecyclerView.v {
            public final ProjectFilesAdapter B;
            public ImageView A;
            public CheckBox t;
            public View u;
            public ImageView v;
            public TextView w;
            public TextView x;
            public LinearLayout y;
            public ImageView z;

            public ViewHolder(ProjectFilesAdapter var1, View itemView) {
                super(itemView);
                B = var1;
                t = itemView.findViewById(2131230893);
                u = itemView.findViewById(2131232322);
                v = itemView.findViewById(2131231104);
                w = itemView.findViewById(2131232144);
                x = itemView.findViewById(2131231863);
                y = itemView.findViewById(2131230959);
                z = itemView.findViewById(2131231132);
                A = itemView.findViewById(0x7f0801c0);
                t.setVisibility(View.GONE);
                u.setOnClickListener(new Cw(this, var1));
                u.setOnLongClickListener(new Dw(this, var1));
                A.setOnClickListener(new Ew(this, var1));
            }
        }
    }
}
