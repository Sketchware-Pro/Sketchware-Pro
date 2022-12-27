package a.a.a;

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
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

public class Fw extends qA {

    private static final int REQUEST_CODE_PRESET_ACTIVITY = 276;
    private static final int REQUEST_CODE_ADD_VIEW_ACTIVITY = 265;
    private RecyclerView activitiesList;
    private Boolean k = false;
    private TextView tvGuide;
    private final int[] m = new int[19];
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

        String drawerName = ProjectFileBean.getDrawerName(newProjectFile.fileName);
        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
            ((ManageViewActivity) getActivity()).b(drawerName);
        } else {
            ((ManageViewActivity) getActivity()).c(drawerName);
        }

        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) || projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
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
                if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(projectFileBean.fileName));
                }
            }
        }
    }

    public void g() {
        if (activitiesFiles != null) {
            if (activitiesFiles.size() == 0) {
                tvGuide.setVisibility(View.VISIBLE);
                activitiesList.setVisibility(View.GONE);
            } else {
                activitiesList.setVisibility(View.VISIBLE);
                tvGuide.setVisibility(View.GONE);
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
        if (requestCode == REQUEST_CODE_ADD_VIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                b(data.getParcelableExtra("project_file"));
                projectFilesAdapter.c(projectFilesAdapter.layoutPosition);
            }
        } else if (requestCode == REQUEST_CODE_PRESET_ACTIVITY && resultCode == Activity.RESULT_OK) {
            ProjectFileBean projectFileBean = data.getParcelableExtra("preset_data");
            b(projectFileBean);
            c(projectFileBean);
            projectFilesAdapter.c(projectFilesAdapter.layoutPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_view_list, parent, false);
        activitiesFiles = new ArrayList<>();
        activitiesList = root.findViewById(R.id.list_activities);
        activitiesList.setHasFixedSize(true);
        activitiesList.setLayoutManager(new LinearLayoutManager(getContext()));
        projectFilesAdapter = new ProjectFilesAdapter(activitiesList);
        activitiesList.setAdapter(projectFilesAdapter);
        tvGuide = root.findViewById(R.id.tv_guide);
        tvGuide.setText(xB.b().a(getActivity(), R.string.design_manager_view_description_guide_create_activity));
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
        public int layoutPosition;

        public ProjectFilesAdapter(RecyclerView recyclerView) {
            layoutPosition = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
                recyclerView.a(new RecyclerView.m() {
                    @Override
                    // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
                    public void a(RecyclerView recyclerView, int dx, int dy) {
                        super.a(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (((ManageViewActivity) getActivity()).s.isEnabled()) {
                                ((ManageViewActivity) getActivity()).s.c();
                            }
                        } else if (dy < -2 && ((ManageViewActivity) getActivity()).s.isEnabled()) {
                            ((ManageViewActivity) getActivity()).s.f();
                        }
                    }
                });
            }
        }

        @Override
        // RecyclerView.Adapter#getItemCount()
        public int a() {
            return activitiesFiles != null ? activitiesFiles.size() : 0;
        }

        @Override
        // RecyclerView.Adapter#onBindViewHolder(VH, int)
        public void b(ViewHolder viewHolder, int position) {
            viewHolder.imgActivity.setVisibility(View.VISIBLE);
            viewHolder.deleteImgContainer.setVisibility(View.GONE);
            if (position == 0) {
                viewHolder.checkBox.setVisibility(View.GONE);
            } else {
                viewHolder.deleteImgContainer.setVisibility(k ? View.VISIBLE : View.GONE);
                viewHolder.imgActivity.setVisibility(k ? View.GONE : View.VISIBLE);
            }

            ProjectFileBean projectFileBean = activitiesFiles.get(position);
            viewHolder.imgActivity.setImageResource(getImageResByOptions(projectFileBean.options));
            viewHolder.tvScreenName.setText(projectFileBean.getXmlName());
            viewHolder.tvActivityName.setText(projectFileBean.getJavaName());
            viewHolder.imgDelete.setImageResource(projectFileBean.isSelected ? R.drawable.ic_checkmark_green_48dp : R.drawable.ic_trashcan_white_48dp);
        }

        @Override
        // RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_view_list_item, parent, false));
        }

        private int getImageResByOptions(int options) {
            String option = String.format("%4s", Integer.toBinaryString(options)).replace(' ', '0');
            Resources resources = getContext().getResources();
            return resources.getIdentifier("activity_" + option, "drawable", getContext().getPackageName());
        }

        public class ViewHolder extends RecyclerView.v {
            public final ImageView imgPresetSettings;
            public final CheckBox checkBox;
            public final View viewItem;
            public final ImageView imgActivity;
            public final TextView tvScreenName;
            public final TextView tvActivityName;
            public final LinearLayout deleteImgContainer;
            public final ImageView imgDelete;

            public ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.chk_select);
                viewItem = itemView.findViewById(R.id.view_item);
                imgActivity = itemView.findViewById(R.id.img_activity);
                tvScreenName = itemView.findViewById(R.id.tv_screen_name);
                tvActivityName = itemView.findViewById(R.id.tv_activity_name);
                deleteImgContainer = itemView.findViewById(R.id.delete_img_container);
                imgDelete = itemView.findViewById(R.id.img_delete);
                imgPresetSettings = itemView.findViewById(R.id.img_preset_setting);
                checkBox.setVisibility(View.GONE);
                viewItem.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = j();
                        if (Fw.this.k) {
                            if (layoutPosition != 0) {
                                activitiesFiles.get(layoutPosition).isSelected = checkBox.isChecked();
                                ProjectFilesAdapter.this.c(layoutPosition);
                            }
                        } else {
                            Intent intent = new Intent(getContext(), AddViewActivity.class);
                            intent.putExtra("project_file", activitiesFiles.get(layoutPosition));
                            intent.putExtra("request_code", REQUEST_CODE_ADD_VIEW_ACTIVITY);
                            startActivityForResult(intent, REQUEST_CODE_ADD_VIEW_ACTIVITY);
                        }
                    }
                });
                viewItem.setOnLongClickListener(view -> {
                    ((ManageViewActivity) getActivity()).a(true);
                    layoutPosition = j();
                    checkBox.setChecked(!checkBox.isChecked());
                    activitiesFiles.get(layoutPosition).isSelected = checkBox.isChecked();
                    return true;
                });
                imgPresetSettings.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = j();
                        Intent intent = new Intent(getContext(), PresetSettingActivity.class);
                        intent.putExtra("request_code", REQUEST_CODE_PRESET_ACTIVITY);
                        intent.putExtra("edit_mode", true);
                        startActivityForResult(intent, REQUEST_CODE_PRESET_ACTIVITY);
                    }
                });
            }
        }
    }
}
