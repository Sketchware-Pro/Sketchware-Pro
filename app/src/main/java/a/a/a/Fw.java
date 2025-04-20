package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.view.AddViewActivity;
import com.besome.sketch.editor.manage.view.ManageViewActivity;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;

import java.util.ArrayList;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageViewListItemBinding;

public class Fw extends qA {

    private static final int REQUEST_CODE_PRESET_ACTIVITY = 276;
    private static final int REQUEST_CODE_ADD_VIEW_ACTIVITY = 265;
    private final int[] m = new int[19];
    private RecyclerView activitiesList;
    private Boolean k = false;
    private TextView tvGuide;
    private ProjectFilesAdapter projectFilesAdapter = null;
    private String sc_id;
    private String isAppCompatUsed = "N";
    private ArrayList<ProjectFileBean> activitiesFiles;

    public final String a(int beanType, String xmlName) {
        String baseName = wq.b(beanType);
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(baseName);
        int[] nameCounters = m;
        int counter = nameCounters[beanType] + 1;
        nameCounters[beanType] = counter;
        nameBuilder.append(counter);
        String newName = nameBuilder.toString();
        ArrayList<ViewBean> viewBeans = jC.a(sc_id).d(xmlName);
        xmlName = newName;

        while (true) {
            boolean nameExists = false;
            for (ViewBean viewBean : viewBeans) {
                if (xmlName.equals(viewBean.id)) {
                    nameExists = true;
                    break;
                }
            }

            if (!nameExists) {
                return xmlName;
            }

            nameBuilder = new StringBuilder();
            nameBuilder.append(baseName);
            counter = nameCounters[beanType] + 1;
            nameCounters[beanType] = counter;
            nameBuilder.append(counter);
            xmlName = nameBuilder.toString();
        }
    }

    public final ArrayList<ViewBean> a(String var1) {
        return rq.f(var1);
    }

    public void a(ProjectFileBean var1) {
        activitiesFiles.add(var1);
        projectFilesAdapter.notifyDataSetChanged();
    }

    public void a(boolean var1) {
        k = var1;
        e();
        projectFilesAdapter.notifyDataSetChanged();
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
        int i = activitiesFiles.size();
        while (true) {
            i--;
            if (i >= 0) {
                ProjectFileBean projectFileBean = activitiesFiles.get(i);
                if (projectFileBean.isSelected) {
                    activitiesFiles.remove(i);
                    if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                        ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(projectFileBean.fileName));
                    }
                }
            } else {
                projectFilesAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void g() {
        if (activitiesFiles != null) {
            if (activitiesFiles.isEmpty()) {
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

        projectFilesAdapter.notifyDataSetChanged();
        g();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_VIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                b(data.getParcelableExtra("project_file"));
                projectFilesAdapter.notifyItemChanged(projectFilesAdapter.layoutPosition);
            }
        } else if (requestCode == REQUEST_CODE_PRESET_ACTIVITY && resultCode == Activity.RESULT_OK) {
            ProjectFileBean projectFileBean = data.getParcelableExtra("preset_data");
            b(projectFileBean);
            c(projectFileBean);
            projectFilesAdapter.notifyItemChanged(projectFilesAdapter.layoutPosition);
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

    public class ProjectFilesAdapter extends RecyclerView.Adapter<ProjectFilesAdapter.ViewHolder> {
        public int layoutPosition = -1;

        public ProjectFilesAdapter(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (((ManageViewActivity) requireActivity()).s.isEnabled()) {
                                ((ManageViewActivity) requireActivity()).s.hide();
                            }
                        } else if (dy < -2 && ((ManageViewActivity) requireActivity()).s.isEnabled()) {
                            ((ManageViewActivity) requireActivity()).s.show();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return activitiesFiles != null ? activitiesFiles.size() : 0;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            ProjectFileBean projectFileBean = activitiesFiles.get(position);

            // Displaying selection state
            viewHolder.binding.chkSelect.setChecked(projectFileBean.isSelected);
            viewHolder.binding.chkSelect.setVisibility(position == 0 ? View.GONE : (k ? View.VISIBLE : View.GONE));
            viewHolder.binding.imgActivity.setVisibility(k && position != 0 ? View.GONE : View.VISIBLE);

            viewHolder.binding.imgActivity.setImageResource(getImageResByOptions(projectFileBean.options));
            viewHolder.binding.tvScreenName.setText(projectFileBean.getXmlName());
            viewHolder.binding.tvActivityName.setText(projectFileBean.getJavaName());
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageViewListItemBinding binding = ManageViewListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        private int getImageResByOptions(int options) {
            String option = String.format("%4s", Integer.toBinaryString(options)).replace(' ', '0');
            Resources resources = getContext().getResources();
            return resources.getIdentifier("activity_" + option, "drawable", getContext().getPackageName());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ManageViewListItemBinding binding;

            public ViewHolder(ManageViewListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.viewItem.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = getLayoutPosition();
                        ProjectFileBean projectFileBean = activitiesFiles.get(layoutPosition);

                        if (Fw.this.k) {
                            if (layoutPosition != 0) {
                                projectFileBean.isSelected = !projectFileBean.isSelected;
                                binding.chkSelect.setChecked(projectFileBean.isSelected);
                                notifyItemChanged(layoutPosition);
                            }
                        } else {
                            Intent intent = new Intent(getContext(), AddViewActivity.class);
                            intent.putExtra("project_file", projectFileBean);
                            intent.putExtra("request_code", REQUEST_CODE_ADD_VIEW_ACTIVITY);
                            startActivityForResult(intent, REQUEST_CODE_ADD_VIEW_ACTIVITY);
                        }
                    }
                });

                binding.viewItem.setOnLongClickListener(view -> {
                    if (getLayoutPosition() == 0) {
                        Toast.makeText(getContext(), "Main activity cannot be deleted", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    ((ManageViewActivity) getActivity()).a(true);
                    layoutPosition = getLayoutPosition();
                    ProjectFileBean projectFileBean = activitiesFiles.get(layoutPosition);
                    projectFileBean.isSelected = !projectFileBean.isSelected;
                    binding.chkSelect.setChecked(projectFileBean.isSelected);
                    notifyItemChanged(layoutPosition);
                    return true;
                });

                binding.imgPresetSetting.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = getLayoutPosition();
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
