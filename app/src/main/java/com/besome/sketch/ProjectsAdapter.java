package com.besome.sketch;

import static com.besome.sketch.ProjectsFragment.REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
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

import a.a.a.ZA;
import a.a.a.gB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.yB;
import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.backup.BackupRestoreManager;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private final Activity activity;
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    private final ArrayList<HashMap<String, Object>> projectsList;

    public ProjectsAdapter(Activity context, ArrayList<HashMap<String, Object>> projectsList) {
        this.projectsList = projectsList;
        this.activity = context;
        filterData("");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterData(String query) {
        if (query.isEmpty()) {
            this.data.clear();
            this.data.addAll(projectsList);
            notifyDataSetChanged();
            return;
        }

        ArrayList<HashMap<String, Object>> filteredProjectsList = new ArrayList<>();
        for (HashMap<String, Object> project : projectsList)
            if (matchesQuery(project, query)) filteredProjectsList.add(project);

        this.data = filteredProjectsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private boolean matchesQuery(HashMap<String, Object> projectMap, String searchQuery) {
        if (searchQuery.isEmpty()) return true;

        String scId = yB.c(projectMap, "sc_id").toLowerCase();
        if (searchQuery.contains(scId) || scId.contains(searchQuery)) return true;

        String appName = yB.c(projectMap, "my_ws_name").toLowerCase();
        if (searchQuery.contains(appName) || appName.contains(searchQuery)) return true;

        String projectName = yB.c(projectMap, "my_app_name").toLowerCase();
        if (searchQuery.contains(projectName) || projectName.contains(searchQuery)) return true;

        String packageName = yB.c(projectMap, "my_sc_pkg_name").toLowerCase();
        return searchQuery.contains(packageName) || packageName.contains(searchQuery);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder viewHolder, int position) {
        HashMap<String, Object> projectMap = data.get(position);
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
        viewHolder.projectOptionLayout.setVisibility(visibility);
        viewHolder.expand.setRotation(rotation);
        if (yB.a(projectMap, "confirmation")) {
            viewHolder.projectButtonLayout.b();
        } else {
            viewHolder.projectButtonLayout.a();
        }

        viewHolder.imgIcon.setImageResource(R.drawable.default_icon);
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

            viewHolder.imgIcon.setImageURI(uri);
        }

        viewHolder.appName.setText(yB.c(projectMap, "my_ws_name"));
        viewHolder.projectName.setText(yB.c(projectMap, "my_app_name"));
        viewHolder.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
        String version = yB.c(projectMap, "sc_ver_name") + "(" + yB.c(projectMap, "sc_ver_code") + ")";
        viewHolder.projectVersion.setText(version);
        viewHolder.tvPublished.setVisibility(View.VISIBLE);
        viewHolder.tvPublished.setText(yB.c(projectMap, "sc_id"));
        viewHolder.itemView.setTag("custom");


        viewHolder.projectButtonLayout.setButtonOnClickListener(v -> {
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
                        viewHolder.projectButtonLayout.b();
                        break;

                    case 4:
                        showProjectSettingDialog(projectMap);
                        break;
                }
                return;
            }

            if (v.getId() == R.id.confirm_yes) {
                projectMap.put("confirmation", false);
                projectMap.put("expand", false);
                deleteProject(projectMap);
            } else if (v.getId() == R.id.confirm_no) {
                projectMap.put("confirmation", false);
                notifyItemChanged(position);
            }

        });

        viewHolder.projectView.setOnClickListener(v -> {
            if (!mB.a()) {
                ProjectsFragment.toDesignActivity(activity, yB.c(projectMap, "sc_id"));
            }
        });
        viewHolder.projectView.setOnLongClickListener(v -> {
            if (yB.a(projectMap, "expand")) {
                viewHolder.collapse(projectMap);
            } else {
                viewHolder.expand(projectMap);
            }

            return true;
        });

        viewHolder.appIconLayout.setOnClickListener(v -> {
            mB.a(v);
            toProjectSettingOrRequestPermission(projectMap, position);
        });

        viewHolder.expand.setOnClickListener(v -> {
            if (!mB.a()) {
                if (yB.a(projectMap, "expand")) {
                    viewHolder.collapse(projectMap);
                } else {
                    viewHolder.expand(projectMap);
                }
            }
        });
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myprojects_item, parent, false));
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
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

    private void deleteProject(HashMap<String, Object> project) {
        final ZA c = new ZA(activity); //Now loading

        new Thread(() -> {
            activity.runOnUiThread(c::show);
            lC.a(activity, yB.c(project, "sc_id"));
            activity.runOnUiThread(() -> {
                c.dismiss();
                notifyItemRemoved(data.indexOf(project));
                data.remove(project);
                projectsList.remove(project);
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
        (new ProjectSettingsDialog(activity, yB.c(project, "sc_id"))).show();
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

