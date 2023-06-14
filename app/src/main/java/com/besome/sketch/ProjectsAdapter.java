package com.besome.sketch;

import static com.besome.sketch.ProjectsFragment.REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.besome.sketch.projects.MyProjectButton;
import com.besome.sketch.projects.MyProjectButtonLayout;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import a.a.a.ZA;
import a.a.a.gB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.backup.BackupRestoreManager;

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
        if (shownProjects.size() == 0) {
            if (allProjects.size() > 0) {
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
            // prevent scrolling to the very bottom on start
            if (shownProjects.size() == 0) {
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
            newProjects = allProjects.stream()
                    .filter(project -> matchesQuery(project, query))
                    .collect(Collectors.toList());
        }

        // remove them so DiffUtil isn't confused
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
                return true;
            }
        }, true /* sort behavior can be changed */);
        shownProjects = newProjects;
        result.dispatchUpdatesTo(this);
        // add them again after DiffUtil's done
        if (shownSpecialActions > 0) {
            notifyItemRangeInserted(0, shownSpecialActions);
        }

        // hide Restore Projects when searching
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

            float rotation;
            int visibility;
            if (yB.a(projectMap, "expand")) {
                visibility = View.VISIBLE;
                rotation = -180.0F;
            } else {
                visibility = View.GONE;
                rotation = 0.0F;
            }
            holder.projectOptionLayout.setVisibility(visibility);
            holder.expand.setRotation(rotation);
            if (yB.a(projectMap, "confirmation")) {
                holder.projectButtonLayout.b();
            } else {
                holder.projectButtonLayout.a();
            }

            holder.imgIcon.setImageResource(R.drawable.default_icon);
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

                holder.imgIcon.setImageURI(uri);
            }

            holder.appName.setText(yB.c(projectMap, "my_ws_name"));
            holder.projectName.setText(yB.c(projectMap, "my_app_name"));
            holder.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String version = yB.c(projectMap, "sc_ver_name") + "(" + yB.c(projectMap, "sc_ver_code") + ")";
            holder.projectVersion.setText(version);
            holder.tvPublished.setVisibility(View.VISIBLE);
            holder.tvPublished.setText(yB.c(projectMap, "sc_id"));
            holder.itemView.setTag("custom");

            holder.projectButtonLayout.setButtonOnClickListener(v -> {
                if (mB.a()) return;
                if (v instanceof MyProjectButton) {
                    switch (((MyProjectButton) v).b) {
                        case 0:
                            toProjectSettingOrRequestPermission(projectMap, position);
                            break;

                        case 1:
                            backupProject(projectMap);
                            break;

                        case 2:
                            toExportProjectActivity(projectMap);
                            break;

                        case 3:
                            projectMap.put("confirmation", true);
                            holder.projectButtonLayout.b();
                            break;

                        case 4:
                            showProjectSettingDialog(projectMap);
                            break;
                    }
                    return;
                }

                if (v.getId() == R.id.confirm_yes) {
                    deleteProject(holder.getBindingAdapterPosition());
                } else if (v.getId() == R.id.confirm_no) {
                    projectMap.put("confirmation", false);
                    notifyItemChanged(holder.getBindingAdapterPosition());
                }
            });

            holder.projectView.setOnClickListener(v -> {
                if (!mB.a()) {
                    projectsFragment.toDesignActivity(yB.c(projectMap, "sc_id"));
                }
            });
            holder.projectView.setOnLongClickListener(v -> {
                if (yB.a(projectMap, "expand")) {
                    holder.collapse(projectMap);
                } else {
                    holder.expand(projectMap);
                }

                return true;
            });

            holder.appIconLayout.setOnClickListener(v -> {
                mB.a(v);
                toProjectSettingOrRequestPermission(projectMap, position);
            });

            holder.expand.setOnClickListener(v -> {
                if (!mB.a()) {
                    if (yB.a(projectMap, "expand")) {
                        holder.collapse(projectMap);
                    } else {
                        holder.expand(projectMap);
                    }
                }
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
            return new SpecialActionViewHolder(inflater.inflate(R.layout.myprojects_item_special, parent, false));
        }
        return new ProjectViewHolder(inflater.inflate(R.layout.myprojects_item, parent, false));
    }

    private class ProjectViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvPublished;
        public final ImageView expand;
        public final MyProjectButtonLayout projectButtonLayout;
        public final LinearLayout projectOptionLayout;
        public final LinearLayout projectOption;
        public final LinearLayout projectView;
        public final View appIconLayout;
        public final CircleImageView imgIcon;
        public final TextView projectName;
        public final TextView appName;
        public final TextView packageName;
        public final TextView projectVersion;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            projectView = itemView.findViewById(R.id.project_one);
            projectName = itemView.findViewById(R.id.project_name);
            appIconLayout = itemView.findViewById(R.id.app_icon_layout);
            imgIcon = itemView.findViewById(R.id.img_icon);
            appName = itemView.findViewById(R.id.app_name);
            packageName = itemView.findViewById(R.id.package_name);
            projectVersion = itemView.findViewById(R.id.project_version);
            tvPublished = itemView.findViewById(R.id.tv_published);
            expand = itemView.findViewById(R.id.expand);
            projectOptionLayout = itemView.findViewById(R.id.project_option_layout);
            projectOption = itemView.findViewById(R.id.project_option);
            projectButtonLayout = new MyProjectButtonLayout(activity);
            projectOption.addView(projectButtonLayout);
        }

        public void collapse(HashMap<String, Object> project) {
            project.put("expand", false);
            gB.a(expand, 0.0F, null);
            gB.a(projectOptionLayout, 300, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    projectOptionLayout.setVisibility(View.GONE);
                }
            });
        }

        public void expand(HashMap<String, Object> project) {
            projectOptionLayout.setVisibility(View.VISIBLE);
            project.put("expand", true);
            gB.a(expand, -180.0F, null);
            gB.b(projectOptionLayout, 300, null);
        }
    }

    private class SpecialActionViewHolder extends RecyclerView.ViewHolder {
        public final ImageView icon;
        public final TextView title;

        private boolean isNewProjectAction = true;

        public SpecialActionViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_create_new);
            title = itemView.findViewById(R.id.tv_create_new);
            itemView.findViewById(R.id.project_one).setOnClickListener(v -> {
                if (isNewProjectAction()) {
                    projectsFragment.toProjectSettingsActivity();
                } else {
                    projectsFragment.restoreProject();
                }
            });
        }

        public void setIsNewProjectAction(boolean b) {
            isNewProjectAction = b;
            icon.setImageResource(isNewProjectAction ? R.drawable.plus_96 : R.drawable.data_backup_96);
            title.setText(activity.getString(isNewProjectAction ? R.string.myprojects_list_menu_title_create_a_new_project : R.string.myprojects_list_menu_title_restore_projects));
        }

        public boolean isNewProjectAction() {
            return isNewProjectAction;
        }
    }

    private void deleteProject(int truePosition) {
        final ZA c = new ZA(activity); //Now loading
        c.show();

        var sc_id = yB.c(shownProjects.get(truePosition - shownSpecialActions), "sc_id");
        new Thread(() -> {
            lC.a(activity, sc_id);
            activity.runOnUiThread(() -> {
                c.dismiss();
                Predicate<HashMap<String, Object>> remover = (project -> yB.c(project, "sc_id").equals(sc_id));
                shownProjects.removeIf(remover);
                allProjects.removeIf(remover);
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
        intent.putExtra("advanced_open", false);
        intent.putExtra("index", index);
        activity.startActivityForResult(intent, REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY);
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
}
