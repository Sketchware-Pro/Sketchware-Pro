package com.besome.sketch.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.lib.ui.LoadingDialog;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.backup.BackupRestoreManager;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.main.fragments.projects.ProjectsFragment;
import pro.sketchware.databinding.BottomSheetProjectOptionsBinding;
import pro.sketchware.databinding.MyprojectsItemBinding;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {
    private final ProjectsFragment projectsFragment;
    private final Activity activity;
    private List<HashMap<String, Object>> shownProjects = new ArrayList<>();
    private List<HashMap<String, Object>> allProjects;

    public ProjectsAdapter(ProjectsFragment projectsFragment, List<HashMap<String, Object>> allProjects) {
        this.projectsFragment = projectsFragment;
        this.activity = projectsFragment.requireActivity();
        this.allProjects = allProjects;
    }

    public void setAllProjects(List<HashMap<String, Object>> projects) {
        allProjects = projects;
    }

    public void filterData(String query) {
        List<HashMap<String, Object>> newProjects = query.isEmpty() ? allProjects : new ArrayList<>();
        if (!query.isEmpty()) {
            for (HashMap<String, Object> project : allProjects) {
                if (matchesQuery(project, query)) {
                    newProjects.add(project);
                }
            }
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return shownProjects.size();
            }

            @Override
            public int getNewListSize() {
                return newProjects.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                String oldScId = yB.c(shownProjects.get(oldItemPosition), "sc_id");
                String newScId = yB.c(newProjects.get(newItemPosition), "sc_id");
                return oldScId.equalsIgnoreCase(newScId);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                HashMap<String, Object> oldMap = shownProjects.get(oldItemPosition);
                HashMap<String, Object> newMap = newProjects.get(newItemPosition);
                for (String key : Arrays.asList("my_app_name", "my_ws_name", "sc_ver_name", "sc_ver_code", "my_sc_pkg_name")) {
                    if (!yB.c(oldMap, key).equals(yB.c(newMap, key))) {
                        return false;
                    }
                }
                boolean oldCustomIcon = yB.a(oldMap, "custom_icon");
                boolean newCustomIcon = yB.a(newMap, "custom_icon");
                return oldCustomIcon == newCustomIcon;
            }
        }, true);
        shownProjects = newProjects;
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return shownProjects.size();
    }

    private boolean matchesQuery(HashMap<String, Object> projectMap, String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        for (String key : Arrays.asList("sc_id", "my_ws_name", "my_app_name", "my_sc_pkg_name")) {
            if (yB.c(projectMap, key).toLowerCase().contains(searchQuery)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        HashMap<String, Object> projectMap = shownProjects.get(position);
        String scId = yB.c(projectMap, "sc_id");

        holder.binding.imgIcon.setImageResource(R.drawable.default_icon);

        if (yB.c(projectMap, "sc_ver_code").isEmpty()) {
            projectMap.put("sc_ver_code", "1");
            projectMap.put("sc_ver_name", "1.0");
            lC.b(scId, projectMap);
        }

        if (yB.b(projectMap, "sketchware_ver") <= 0) {
            projectMap.put("sketchware_ver", 61);
            lC.b(scId, projectMap);
        }

        if (yB.a(projectMap, "custom_icon")) {
            String iconFolder = wq.e() + File.separator + scId;
            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                String providerPath = activity.getPackageName() + ".provider";
                uri = FileProvider.getUriForFile(activity, providerPath, new File(iconFolder, "icon.png"));
            } else {
                uri = Uri.fromFile(new File(iconFolder, "icon.png"));
            }
            holder.binding.imgIcon.setImageURI(uri);
        }

        String version = " - " + yB.c(projectMap, "sc_ver_name") + " (" + yB.c(projectMap, "sc_ver_code") + ")";
        holder.binding.appName.setText(yB.c(projectMap, "my_ws_name") + version);
        holder.binding.projectName.setText(yB.c(projectMap, "my_app_name"));
        holder.binding.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
        holder.binding.tvPublished.setVisibility(View.VISIBLE);
        holder.binding.tvPublished.setText(scId);
        holder.itemView.setTag("custom");

        holder.binding.getRoot().setOnClickListener(v -> {
            if (!mB.a()) {
                projectsFragment.toDesignActivity(scId);
            }
        });

        View.OnClickListener showProjectSettingsDialog = v -> {
            mB.a(v);
            int currentPosition = holder.getAbsoluteAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                showProjectOptionsBottomSheet(projectMap, currentPosition);
            }
        };

        holder.binding.expand.setOnClickListener(showProjectSettingsDialog);
        holder.binding.imgIcon.setOnClickListener(v -> toProjectSettingOrRequestPermission(projectMap, position));
        holder.binding.getRoot().setOnLongClickListener(v -> {
            showProjectOptionsBottomSheet(projectMap, holder.getAbsoluteAdapterPosition());
            return true;
        });
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyprojectsItemBinding binding = MyprojectsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProjectViewHolder(binding);
    }

    private void deleteProject(HashMap<String, Object> projectMap, int position) {
        LoadingDialog progressDialog = new LoadingDialog(activity);
        progressDialog.show();

        String scId = yB.c(projectMap, "sc_id");
        new Thread(() -> {
            lC.a(activity, scId);
            activity.runOnUiThread(() -> {
                progressDialog.dismiss();
                shownProjects.remove(position);
                notifyItemRemoved(position);
                allProjects.remove(projectMap);
            });
        }).start();
    }

    private void toProjectSettingOrRequestPermission(HashMap<String, Object> project, int index) {
        Intent intent = new Intent(activity, MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(project, "sc_id"));
        intent.putExtra("is_update", true);
        intent.putExtra("index", index);
        projectsFragment.openProjectSettings.launch(intent);
    }

    private void showProjectSettingDialog(HashMap<String, Object> project) {
        new ProjectSettingsDialog(activity, yB.c(project, "sc_id")).show();
    }

    private void backupProject(HashMap<String, Object> project) {
        String scId = yB.c(project, "sc_id");
        String appName = yB.c(project, "my_ws_name");
        new BackupRestoreManager(activity).backup(scId, appName);
    }

    private void toExportProjectActivity(HashMap<String, Object> project) {
        Intent intent = new Intent(activity, ExportProjectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(project, "sc_id"));
        activity.startActivity(intent);
    }

    private void showProjectOptionsBottomSheet(HashMap<String, Object> projectMap, int position) {
        BottomSheetDialog projectOptionsBSD = new BottomSheetDialog(activity);
        BottomSheetProjectOptionsBinding binding = BottomSheetProjectOptionsBinding.inflate(LayoutInflater.from(activity));
        projectOptionsBSD.setContentView(binding.getRoot());

        binding.projectSettings.setOnClickListener(v -> {
            toProjectSettingOrRequestPermission(projectMap, position);
            projectOptionsBSD.dismiss();
        });

        binding.projectBackup.setOnClickListener(v -> {
            backupProject(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.exportSign.setOnClickListener(v -> {
            toExportProjectActivity(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.projectConfig.setOnClickListener(v -> {
            showProjectSettingDialog(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.projectDelete.setOnClickListener(v -> {
            projectOptionsBSD.dismiss();
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
            dialog.setIcon(R.drawable.icon_delete);
            dialog.setTitle(Helper.getResString(R.string.delete_project_dialog_title));
            dialog.setMessage(Helper.getResString(R.string.delete_project_dialog_message).replace("%1$s", yB.c(projectMap, "my_app_name")));
            dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v1, which) -> {
                deleteProject(projectMap, position);
                v1.dismiss();
            });
            dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
            dialog.show();
        });
        projectOptionsBSD.show();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        final MyprojectsItemBinding binding;

        ProjectViewHolder(MyprojectsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
