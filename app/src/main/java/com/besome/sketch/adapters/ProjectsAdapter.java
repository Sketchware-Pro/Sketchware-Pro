package com.besome.sketch.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import a.a.a.DB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.backup.BackupRestoreManager;
import pro.sketchware.R;
import pro.sketchware.activities.main.fragments.projects.ProjectsFragment;
import pro.sketchware.databinding.BottomSheetProjectOptionsBinding;
import pro.sketchware.databinding.MyprojectsFolderItemBinding;
import pro.sketchware.databinding.MyprojectsItemBinding;

/**
 * Shows the items of the current folder of the Projects manager: folders first, then projects.
 * The list itself is owned by {@link ProjectsFragment}; this adapter only renders it.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PROJECT = 0;
    private static final int TYPE_FOLDER = 1;

    private final ProjectsFragment projectsFragment;
    private final Activity activity;
    private final DB preference;
    private final List<HashMap<String, Object>> items;

    public ProjectsAdapter(ProjectsFragment projectsFragment, List<HashMap<String, Object>> items) {
        this.projectsFragment = projectsFragment;
        this.activity = projectsFragment.requireActivity();
        this.items = items;
        this.preference = new DB(activity, "project");
    }

    /**
     * Rows that represent a folder are marked with {@code is_folder = true}.
     */
    public static boolean isFolderRow(HashMap<String, Object> row) {
        return Boolean.TRUE.equals(row.get("is_folder"));
    }

    @DrawableRes
    public static <T> int getShapedBackgroundForList(List<T> list, int position) {
        if (list.size() == 1) {
            return R.drawable.project_item_shape_alone;
        } else if (position == 0) {
            return R.drawable.project_item_shape_top;
        } else if (position == list.size() - 1) {
            return R.drawable.project_item_shape_bottom;
        } else {
            return R.drawable.project_item_shape_middle;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isFolderRow(items.get(position)) ? TYPE_FOLDER : TYPE_PROJECT;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_FOLDER) {
            return new FolderViewHolder(MyprojectsFolderItemBinding.inflate(inflater, parent, false));
        }
        return new ProjectViewHolder(MyprojectsItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HashMap<String, Object> row = items.get(position);
        holder.itemView.setBackgroundResource(getShapedBackgroundForList(items, position));
        if (holder instanceof FolderViewHolder) {
            bindFolder((FolderViewHolder) holder, row);
        } else {
            bindProject((ProjectViewHolder) holder, row);
        }
    }

    private void bindFolder(FolderViewHolder holder, HashMap<String, Object> folder) {
        String folderId = yB.c(folder, "folder_id");
        String folderName = yB.c(folder, "my_ws_name");
        Integer childCount = (Integer) folder.get("child_count");
        int count = childCount == null ? 0 : childCount;

        holder.binding.folderName.setText(folderName);
        holder.binding.folderInfo.setText(count + (count == 1 ? " item" : " items"));
        holder.binding.selectCheckbox.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (projectsFragment.isSelectionMode()) return;
            if (!mB.a()) projectsFragment.openFolder(folderId);
        });
        holder.itemView.setOnLongClickListener(v -> {
            projectsFragment.onFolderLongPressed(folderId, folderName);
            return true;
        });
    }

    private void bindProject(ProjectViewHolder holder, HashMap<String, Object> projectMap) {
        String scId = yB.c(projectMap, "sc_id");
        boolean selectionMode = projectsFragment.isSelectionMode();

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
            File iconFile = new File(iconFolder, "icon.png");
            if (iconFile.exists()) {
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    Glide.with(activity)
                            .load(iconFile)
                            .placeholder(R.drawable.default_icon)
                            .error(R.drawable.default_icon)
                            .into(holder.binding.imgIcon);
                } else {
                    holder.binding.imgIcon.setImageResource(R.drawable.default_icon);
                }
            } else {
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    Glide.with(activity).clear(holder.binding.imgIcon);
                }
                holder.binding.imgIcon.setImageResource(R.drawable.default_icon);
            }
        } else {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                Glide.with(activity).clear(holder.binding.imgIcon);
            }
            holder.binding.imgIcon.setImageResource(R.drawable.default_icon);
        }

        if (isPinned(projectMap)) {
            holder.binding.imgPin.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imgPin.setVisibility(View.INVISIBLE);
        }

        String version = " - " + yB.c(projectMap, "sc_ver_name") + " (" + yB.c(projectMap, "sc_ver_code") + ")";
        holder.binding.appName.setText(yB.c(projectMap, "my_ws_name") + version);
        holder.binding.projectName.setText(yB.c(projectMap, "my_app_name"));
        holder.binding.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
        holder.binding.tvPublished.setVisibility(View.VISIBLE);
        holder.binding.tvPublished.setText(scId);
        holder.itemView.setTag("custom");

        holder.binding.selectCheckbox.setVisibility(selectionMode ? View.VISIBLE : View.GONE);
        holder.binding.selectCheckbox.setChecked(projectsFragment.isSelected(scId));
        holder.binding.expand.setVisibility(selectionMode ? View.GONE : View.VISIBLE);

        holder.binding.getRoot().setOnClickListener(v -> {
            if (projectsFragment.isSelectionMode()) {
                projectsFragment.toggleSelection(scId);
            } else if (!mB.a()) {
                projectsFragment.toDesignActivity(scId);
            }
        });

        holder.binding.getRoot().setOnLongClickListener(v -> {
            projectsFragment.onProjectLongPressed(scId);
            return true;
        });

        holder.binding.expand.setOnClickListener(v -> {
            mB.a(v);
            showProjectOptionsBottomSheet(projectMap);
        });

        holder.binding.imgIcon.setOnClickListener(v -> {
            if (projectsFragment.isSelectionMode()) {
                projectsFragment.toggleSelection(scId);
            } else {
                toProjectSettingOrRequestPermission(projectMap);
            }
        });
    }

    private void toProjectSettingOrRequestPermission(HashMap<String, Object> project) {
        Intent intent = new Intent(activity, MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(project, "sc_id"));
        intent.putExtra("is_update", true);
        projectsFragment.openProjectSettings.launch(intent);
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

    private void changePinState(HashMap<String, Object> projectMap) {
        if (isPinned(projectMap)) {
            preference.a("pinnedProject", "-1", true);
        } else {
            preference.a("pinnedProject", yB.c(projectMap, "sc_id"), true);
        }
        projectsFragment.refreshProjectsList();
    }

    private boolean isPinned(HashMap<String, Object> projectMap) {
        return Objects.equals(yB.c(projectMap, "sc_id"), preference.a("pinnedProject", "-1"));
    }

    private void showProjectOptionsBottomSheet(HashMap<String, Object> projectMap) {
        BottomSheetDialog projectOptionsBSD = new BottomSheetDialog(activity);
        BottomSheetProjectOptionsBinding binding = BottomSheetProjectOptionsBinding.inflate(LayoutInflater.from(activity));
        projectOptionsBSD.setContentView(binding.getRoot());

        binding.title.setText(yB.c(projectMap, "my_ws_name"));
        binding.tvProjectId.setText(yB.c(projectMap, "sc_id"));

        binding.projectSettings.setOnClickListener(v -> {
            toProjectSettingOrRequestPermission(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.projectBackup.setOnClickListener(v -> {
            backupProject(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.projectDuplicate.setOnClickListener(v -> {
            projectOptionsBSD.dismiss();
            projectsFragment.duplicateProject(yB.c(projectMap, "sc_id"));
        });

        binding.pinProject.setOnClickListener(v -> {
            changePinState(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.exportSign.setOnClickListener(v -> {
            toExportProjectActivity(projectMap);
            projectOptionsBSD.dismiss();
        });

        binding.projectConfig.setOnClickListener(v -> {
            new ProjectSettingsDialog(activity, yB.c(projectMap, "sc_id")).show();
            projectOptionsBSD.dismiss();
        });

        binding.projectDelete.setOnClickListener(v -> {
            projectOptionsBSD.dismiss();
            projectsFragment.confirmDeleteProjects(
                    java.util.Collections.singletonList(yB.c(projectMap, "sc_id")),
                    yB.c(projectMap, "my_app_name"));
        });

        if (isPinned(projectMap)) {
            binding.pinIcon.setImageResource(R.drawable.ic_mtrl_unpin);
            binding.pinText.setText("Unpin project");
        } else {
            binding.pinIcon.setImageResource(R.drawable.ic_mtrl_pin);
            binding.pinText.setText("Pin project");
        }

        projectOptionsBSD.show();
    }

    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        final MyprojectsItemBinding binding;

        ProjectViewHolder(MyprojectsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        final MyprojectsFolderItemBinding binding;

        FolderViewHolder(MyprojectsFolderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
