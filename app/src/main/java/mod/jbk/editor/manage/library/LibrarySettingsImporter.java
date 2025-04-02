package mod.jbk.editor.manage.library;

import android.app.Activity;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.lib.ui.CircleImageView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import a.a.a.iC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import pro.sketchware.R;

public class LibrarySettingsImporter {
    private static final ProjectComparator PROJECT_COMPARATOR = new ProjectComparator();

    private final String sc_id;
    private final Function<iC, ProjectLibraryBean> getLibrarySettings;
    private final Set<Consumer<ProjectLibraryBean>> onProjectSelectedListeners = new HashSet<>();
    private Activity activity;
    private ProjectAdapter adapter;
    private List<? extends Map<String, Object>> projects;

    public LibrarySettingsImporter(String sc_id, Function<iC, ProjectLibraryBean> getLibrarySettings) {
        this.sc_id = sc_id;
        this.getLibrarySettings = getLibrarySettings;
    }

    public void addOnProjectSelectedListener(Consumer<ProjectLibraryBean> listener) {
        onProjectSelectedListeners.add(listener);
    }

    public void showDialog(Activity activity) {
        this.activity = activity;
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        dialog.setTitle(xB.b().a(activity, R.string.design_library_title_select_project));
        dialog.setIcon(R.drawable.widget_firebase);
        LinearLayout root = (LinearLayout) wB.a(activity, R.layout.manage_library_popup_project_selector);
        LottieAnimationView animationView = root.findViewById(R.id.animation_view);
        RecyclerView recyclerView = root.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new Thread(() -> {
            loadProjects();
            activity.runOnUiThread(() -> {
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
                root.removeView(animationView);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new ProjectAdapter();
                recyclerView.setAdapter(adapter);
            });
        }).start();
        dialog.setView(root);
        dialog.setPositiveButton(xB.b().a(activity, R.string.common_word_select), (v, which) -> {
            if (!mB.a()) {
                if (adapter.selectedProjectIndex >= 0) {
                    var settings = (ProjectLibraryBean) projects.get(adapter.selectedProjectIndex).get("settings");
                    for (var listener : onProjectSelectedListeners) {
                        listener.accept(settings);
                    }
                    v.dismiss();
                }
            }
        });
        dialog.setNegativeButton(xB.b().a(activity, R.string.common_word_cancel), null);
        dialog.show();
    }

    private void loadProjects() {
        projects = lC.a().stream()
                .filter(project -> {
                    var projectSc_id = yB.c(project, "sc_id");
                    if (!sc_id.equals(projectSc_id)) {
                        var projectLibraryHandler = new iC(projectSc_id);
                        projectLibraryHandler.i();
                        var settings = getLibrarySettings.apply(projectLibraryHandler);
                        if (settings.useYn.equals("Y")) {
                            project.put("settings", settings);
                            return true;
                        }
                    }
                    return false;
                })
                .sorted(PROJECT_COMPARATOR)
                .collect(Collectors.toList());
    }

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
        private int selectedProjectIndex = -1;

        @Override
        public int getItemCount() {
            return projects.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ProjectAdapter.ViewHolder viewHolder, int position) {
            Map<String, Object> projectMap = projects.get(position);
            String sc_id = yB.c(projectMap, "sc_id");
            if (yB.a(projectMap, "custom_icon")) {
                String iconPath = wq.e() + File.separator + sc_id;
                Uri iconUri;
                if (Build.VERSION.SDK_INT >= 24) {
                    iconUri = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getPackageName() + ".provider", new File(iconPath, "icon.png"));
                } else {
                    iconUri = Uri.fromFile(new File(iconPath, "icon.png"));
                }
                viewHolder.imgIcon.setImageURI(iconUri);
            } else {
                viewHolder.imgIcon.setImageResource(R.drawable.default_icon);
            }

            viewHolder.appName.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.projectName.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.pkgName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            viewHolder.projectVersion.setText(String.format("%s(%s)", yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code")));
            viewHolder.imgSelected.setVisibility(yB.a(projectMap, "selected") ? View.VISIBLE : View.GONE);
        }

        @Override
        @NonNull
        public ProjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ProjectAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_library_popup_project_list_item, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public final LinearLayout projectLayout;
            public final CircleImageView imgIcon;
            public final TextView projectName;
            public final TextView appName;
            public final TextView pkgName;
            public final TextView projectVersion;
            public final ImageView imgSelected;

            public ViewHolder(View itemView) {
                super(itemView);
                projectLayout = itemView.findViewById(R.id.project_layout);
                projectName = itemView.findViewById(R.id.project_name);
                imgIcon = itemView.findViewById(R.id.img_icon);
                appName = itemView.findViewById(R.id.app_name);
                pkgName = itemView.findViewById(R.id.package_name);
                projectVersion = itemView.findViewById(R.id.project_version);
                imgSelected = itemView.findViewById(R.id.img_selected);
                projectLayout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a() && v.getId() == R.id.project_layout) {
                    selectedProjectIndex = getLayoutPosition();
                    selectProject(selectedProjectIndex);
                }
            }

            private void selectProject(int index) {
                if (!projects.isEmpty()) {
                    for (Map<String, Object> projectMap : projects) {
                        projectMap.put("selected", false);
                    }

                    projects.get(index).put("selected", true);
                    notifyDataSetChanged();
                }
            }
        }
    }
}
