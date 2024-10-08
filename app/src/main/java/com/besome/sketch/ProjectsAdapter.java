package com.besome.sketch;

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
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.BottomSheetProjectOptionsBinding;
import com.sketchware.remod.databinding.MyprojectsItemBinding;
import com.sketchware.remod.databinding.MyprojectsItemSpecialBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a.a.a.ZA;
import a.a.a.aB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.backup.BackupRestoreManager;
import mod.hey.studios.util.Helper;

public class ProjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ProjectsFragment projectsFragment;
    private final Activity activity;
    private List<HashMap<String, Object>> shownProjects = new ArrayList<>();
    private List<HashMap<String, Object>> allProjects;
    private int shownSpecialActions = 1;

    public ProjectsAdapter(ProjectsFragment projectsFragment, List<HashMap<String, Object>> allProjects) {
        this.projectsFragment = projectsFragment;
        this.allProjects = allProjects;
        this.activity = projectsFragment.requireActivity();
    }

    public void setAllProjects(List<HashMap<String, Object>> projects) {
        allProjects = projects;
    }

    private void maybeAdjustSpecialActions() {
        if (shownProjects.isEmpty()) {
            if (!allProjects.isEmpty()) {
                if (shownSpecialActions == 2) {
                    shownSpecialActions = 1;
                    notifyItemRemoved(0);
                }
            } else {
                if (shownSpecialActions == 1) {
                    notifyItemChanged(0);
                    notifyItemInserted(1);
                    shownSpecialActions = 2;
                }
            }
        }
    }

    public void filterData(String query) {
        List<HashMap<String, Object>> newProjects;
        if (query.isEmpty()) {
            if (shownProjects.isEmpty()) {
                int projectCount;
                if ((projectCount = allProjects.size()) > 0) {
                    shownProjects = allProjects;
                    notifyItemChanged(0);
                    notifyItemRangeInserted(1, projectCount);
                }
            }
            maybeAdjustSpecialActions();
            newProjects = allProjects;
        } else {
            newProjects = new ArrayList<>();
            for (HashMap<String, Object> project : allProjects) {
                if (matchesQuery(project, query)) {
                    newProjects.add(project);
                }
            }
        }

        if (shownSpecialActions > 0) {
            notifyItemRangeRemoved(0, shownSpecialActions);
        }
        var result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
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
                var oldScId = yB.c(shownProjects.get(oldItemPosition), "sc_id");
                var newScId = yB.c(newProjects.get(newItemPosition), "sc_id");
                return oldScId.equalsIgnoreCase(newScId);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                var oldMap = shownProjects.get(oldItemPosition);
                var newMap = newProjects.get(newItemPosition);
                var keysToCheck = new String[]{"my_app_name", "my_ws_name", "sc_ver_name", "sc_ver_code", "my_sc_pkg_name"};
                for (var key : keysToCheck) {
                    if (!yB.c(oldMap, key).equals(yB.c(newMap, key))) {
                        return false;
                    }
                }
                boolean hasCustomIcon = yB.a(newMap, "custom_icon");
                boolean hadCustomIcon = yB.a(oldMap, "custom_icon");
                boolean hasChanged = hadCustomIcon != hasCustomIcon;
                if (hadCustomIcon && hasCustomIcon) {
                    hasChanged = true;
                }
                return !hasChanged;
            }
        }, true);
        shownProjects = newProjects;
        result.dispatchUpdatesTo(this);
        if (shownSpecialActions > 0) {
            notifyItemRangeInserted(0, shownSpecialActions);
        }

        if (query.isEmpty()) {
            if (shownSpecialActions == 0) {
                shownSpecialActions = 1;
                notifyItemInserted(0);
            }
        } else {
            if (shownSpecialActions > 0) {
                shownSpecialActions = 0;
                notifyItemRemoved(0);
            }
        }
    }

    @Override
    public int getItemCount() {
        return shownSpecialActions + shownProjects.size();
    }

    private boolean matchesQuery(HashMap<String, Object> projectMap, String searchQuery) {
        searchQuery = searchQuery.toLowerCase();

        String scId = yB.c(projectMap, "sc_id").toLowerCase();
        if (scId.contains(searchQuery)) return true;

        String appName = yB.c(projectMap, "my_ws_name").toLowerCase();
        if (appName.contains(searchQuery)) return true;

        String projectName = yB.c(projectMap, "my_app_name").toLowerCase();
        if (projectName.contains(searchQuery)) return true;

        String packageName = yB.c(projectMap, "my_sc_pkg_name").toLowerCase();
        return packageName.contains(searchQuery);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int truePosition) {
        if (viewHolder instanceof ProjectViewHolder holder) {
            int position = truePosition - shownSpecialActions;
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
                Uri uri;
                String iconFolder = wq.e() + File.separator + scId;
                if (Build.VERSION.SDK_INT >= 24) {
                    String providerPath = activity.getPackageName() + ".provider";
                    uri = FileProvider.getUriForFile(activity, providerPath, new File(iconFolder, "icon.png"));
                } else {
                    uri = Uri.fromFile(new File(iconFolder, "icon.png"));
                }

                holder.binding.imgIcon.setImageURI(uri);
            }

            holder.binding.appName.setText(yB.c(projectMap, "my_ws_name"));
            holder.binding.projectName.setText(yB.c(projectMap, "my_app_name"));
            holder.binding.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String version = yB.c(projectMap, "sc_ver_name") + "(" + yB.c(projectMap, "sc_ver_code") + ")";
            holder.binding.projectVersion.setText(version);
            holder.binding.tvPublished.setVisibility(View.VISIBLE);
            holder.binding.tvPublished.setText(yB.c(projectMap, "sc_id"));
            holder.itemView.setTag("custom");

            holder.binding.projectOne.setOnClickListener(v -> {
                if (!mB.a()) {
                    projectsFragment.toDesignActivity(yB.c(projectMap, "sc_id"));
                }
            });

            holder.binding.appIconLayout.setOnClickListener(v -> {
                mB.a(v);
                toProjectSettingOrRequestPermission(projectMap, position);
            });

            holder.binding.expand.setOnClickListener(v -> {
                showProjectOptionsBottomSheet(projectMap, truePosition);
            });

            holder.binding.projectOne.setOnLongClickListener(v -> {
                showProjectOptionsBottomSheet(projectMap, truePosition);
                return true;
            });
        } else if (viewHolder instanceof SpecialActionViewHolder holder) {
            boolean isNewProjectView = allProjects.isEmpty() && truePosition == 0;
            holder.setIsNewProjectAction(isNewProjectView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position - shownSpecialActions < 0 ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {
            var binding = MyprojectsItemSpecialBinding.inflate(inflater, parent, false);
            return new SpecialActionViewHolder(binding);
        }
        var binding = MyprojectsItemBinding.inflate(inflater, parent, false);
        return new ProjectViewHolder(binding);
    }

    private static class ProjectViewHolder extends RecyclerView.ViewHolder {
        public final MyprojectsItemBinding binding;

        public ProjectViewHolder(MyprojectsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class SpecialActionViewHolder extends RecyclerView.ViewHolder {
        public final MyprojectsItemSpecialBinding binding;
        private boolean isNewProjectAction = true;

        public SpecialActionViewHolder(@NonNull MyprojectsItemSpecialBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.projectOne.setOnClickListener(v -> {
                if (isNewProjectAction()) {
                    projectsFragment.toProjectSettingsActivity();
                } else {
                    projectsFragment.restoreProject();
                }
            });
        }

        public void setIsNewProjectAction(boolean b) {
            isNewProjectAction = b;
            binding.ivCreateNew.setImageResource(isNewProjectAction ? R.drawable.plus_96 : R.drawable.data_backup_96);
            binding.tvCreateNew.setText(activity.getString(isNewProjectAction ? R.string.myprojects_list_menu_title_create_a_new_project : R.string.myprojects_list_menu_title_restore_projects));
        }

        public boolean isNewProjectAction() {
            return isNewProjectAction;
        }
    }

    private void deleteProject(int truePosition) {
        final ZA c = new ZA(activity);
        c.show();

        var sc_id = yB.c(shownProjects.get(truePosition - shownSpecialActions), "sc_id");
        new Thread(() -> {
            lC.a(activity, sc_id);
            activity.runOnUiThread(() -> {
                c.dismiss();
                shownProjects.removeIf(project -> yB.c(project, "sc_id").equals(sc_id));
                allProjects.removeIf(project -> yB.c(project, "sc_id").equals(sc_id));
                notifyItemRemoved(truePosition);

                maybeAdjustSpecialActions();
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
        String sc_id = yB.c(project, "sc_id");
        String appName = yB.c(project, "my_ws_name");
        (new BackupRestoreManager(activity)).backup(sc_id, appName);
    }

    private void toExportProjectActivity(HashMap<String, Object> project) {
        Intent intent = new Intent(activity, ExportProjectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(project, "sc_id"));
        activity.startActivity(intent);
    }

    private void showProjectOptionsBottomSheet(HashMap<String, Object> projectMap, int position) {
        BottomSheetDialog projectOptionsBSD = new BottomSheetDialog(activity);
        var binding = BottomSheetProjectOptionsBinding.inflate(LayoutInflater.from(activity));
        var view = binding.getRoot();
        projectOptionsBSD.setContentView(view);
        projectOptionsBSD.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

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
            aB dialog = new aB(projectsFragment.requireActivity());
            dialog.a(R.drawable.icon_delete);
            dialog.b(Helper.getResString(R.string.delete_project_dialog_title));
            dialog.a(Helper.getResString(R.string.delete_project_dialog_message).replace("%1$s", yB.c(projectMap, "my_app_name")));

            dialog.b(Helper.getResString(R.string.common_word_delete), v1 -> {
                deleteProject(position);
                notifyItemChanged(position);
                dialog.dismiss();
            });
            dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
            dialog.show();
        });
        projectOptionsBSD.show();
    }
}
