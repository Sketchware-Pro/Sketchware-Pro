package a.a.a;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.besome.sketch.MainActivity;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.besome.sketch.projects.MyProjectButton;
import com.besome.sketch.projects.MyProjectButtonLayout;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupRestoreManager;

@SuppressLint("ResourceType")
public class GC extends DA implements View.OnClickListener {

    public static final int REQUEST_CODE_DESIGN_ACTIVITY = 204;
    public static final int REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY = 206;
    public static final int REQUEST_CODE_RESTORE_PROJECT = 700;

    public SwipeRefreshLayout swipeRefresh;
    public ArrayList<HashMap<String, Object>> projectsList;
    public RecyclerView myProjects;
    public CardView cvCreateNew;
    public LinearLayout createNewProject;
    public ImageView ivCreateNew;
    public TextView tvCreateNew;
    public CardView cvManagePublish;
    public LinearLayout layoutManagePublish;
    public ImageView ivManagePublish;
    public TextView tvManagePublish;
    public Boolean q;
    public AnimatorSet collapseAnimatorSet;
    public AnimatorSet expandAnimatorSet;
    public ValueAnimator collapseValueAnimator;
    public ValueAnimator expandValueAnimator;
    public ProjectsAdapter projectsAdapter;
    public FloatingActionButton floatingActionButton;

    private void toProjectSettingOrRequestPermission(int position) {
        if (super.c()) {
            Intent intent = new Intent(getContext(), MyProjectSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("sc_id", yB.c(projectsList.get(position), "sc_id"));
            intent.putExtra("is_update", true);
            intent.putExtra("advanced_open", false);
            intent.putExtra("index", position);
            startActivityForResult(intent, REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY);
        } else if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    private void a(ViewGroup parent) {
        swipeRefresh = parent.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            if (swipeRefresh.d()) swipeRefresh.setRefreshing(false);

            if (c()) {
                g();
            } else if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).s();
            }

        });
        floatingActionButton = getActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        myProjects = parent.findViewById(R.id.myprojects);
        myProjects.setHasFixedSize(true);
        myProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        projectsAdapter = new ProjectsAdapter(this, myProjects);
        myProjects.setAdapter(projectsAdapter);
        myProjects.setItemAnimator(new ci());
        cvCreateNew = parent.findViewById(R.id.cv_create_new);
        createNewProject = parent.findViewById(R.id.create_new_project);
        ivCreateNew = createNewProject.findViewById(R.id.iv_create_new);
        tvCreateNew = createNewProject.findViewById(R.id.tv_create_new);
        createNewProject.setOnClickListener(this);
        q = false;
        cvManagePublish = parent.findViewById(R.id.cv_manage_publish);
        layoutManagePublish = parent.findViewById(R.id.layout_manage_publish);
        ivManagePublish = parent.findViewById(R.id.iv_manage_publish);
        tvManagePublish = parent.findViewById(R.id.tv_manage_publish);
        tvManagePublish.setText("Restore project");
        layoutManagePublish.setOnClickListener(this);
        ((TextView) parent.findViewById(R.id.tv_create_new)).setText(xB.b().a(getContext(), R.string.myprojects_list_menu_title_create_a_new_project));
        collapseAnimatorSet = new AnimatorSet();
        expandAnimatorSet = new AnimatorSet();
        collapseValueAnimator = ValueAnimator.ofFloat(wB.a(getContext(), 96.0F), wB.a(getContext(), 48.0F));
        collapseValueAnimator.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            cvManagePublish.getLayoutParams().height = (int) value;
            cvManagePublish.requestLayout();
        });
        expandValueAnimator = ValueAnimator.ofFloat(wB.a(getContext(), 48.0F), wB.a(getContext(), 96.0F));
        expandValueAnimator.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            cvManagePublish.getLayoutParams().height = (int) value;
            cvManagePublish.requestLayout();
        });
        collapseAnimatorSet.playTogether(collapseValueAnimator,
                ObjectAnimator.ofFloat(tvManagePublish, View.TRANSLATION_Y, 0.0F, -100.0F),
                ObjectAnimator.ofFloat(tvManagePublish, View.ALPHA, 1.0F, 0.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_X, 1.0F, 0.5F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_Y, 1.0F, 0.5F));
        expandAnimatorSet.playTogether(expandValueAnimator,
                ObjectAnimator.ofFloat(tvManagePublish, View.TRANSLATION_Y, -100.0F, 0.0F),
                ObjectAnimator.ofFloat(tvManagePublish, View.ALPHA, 0.0F, 1.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_X, 0.5F, 1.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_Y, 0.5F, 1.0F));
        collapseAnimatorSet.setDuration(300L);
        expandAnimatorSet.setDuration(300L);
        g();
    }

    public void a(boolean isEmpty) {
        projectsList = lC.a();
        if (projectsList.size() > 0) {
            //noinspection Java8ListSort
            Collections.sort(projectsList,
                    (first, second) -> Integer.compare(Integer.parseInt(yB.c(first, "sc_id")),
                            Integer.parseInt(yB.c(second, "sc_id"))) * -1);
        }

        myProjects.getAdapter().c();
        if (isEmpty) showCreateNewProjectLayout();

    }

    public void b(int requestCode) {
        if (requestCode == REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY) {
            toProjectSettingsActivity();
        } else if (requestCode == REQUEST_CODE_RESTORE_PROJECT) {
            restoreProject();
        }
    }

    private void toDesignActivity(String sc_id) {
        Intent intent = new Intent(getContext(), DesignActivity.class);
        ProjectTracker.setScId(sc_id);
        intent.putExtra("sc_id", sc_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, REQUEST_CODE_DESIGN_ACTIVITY);
    }

    public void c(int requestCode) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    public void c(String var1) {
        int var2 = 0;

        while (true) {
            if (var2 >= projectsList.size()) {
                var2 = 0;
                break;
            }

            if (yB.c(projectsList.get(var2), "sc_id").equals(var1)) {
                break;
            }

            ++var2;
        }

        toProjectSettingOrRequestPermission(var2);
    }

    public void d() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    public void e() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    public int f() {
        return projectsList.size();
    }

    private void toExportProjectActivity(int position) {
        Intent intent = new Intent(getContext(), ExportProjectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(projectsList.get(position), "sc_id"));
        startActivity(intent);
    }

    public void g() {
        a(true);
    }

    public void showCreateNewProjectLayout() {
        if (projectsList.size() > 0) {
            cvCreateNew.setVisibility(View.GONE);
            floatingActionButton.f();
        } else {
            cvCreateNew.setVisibility(View.VISIBLE);
            floatingActionButton.c();
        }

    }

    private void toProjectSettingsActivity() {
        Intent intent = new Intent(getActivity(), MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY);
    }


    private void restoreProject() {
        (new BackupRestoreManager(getActivity(), this)).restore();
    }

    private void showProjectSettingDialog(int position) {
        (new ProjectSettingsDialog(getActivity(), yB.c(projectsList.get(position), "sc_id"))).show();
    }

    private void backupProject(int position) {
        String sc_id = yB.c(projectsList.get(position), "sc_id");
        String appName = yB.c(projectsList.get(position), "my_ws_name");
        (new BackupRestoreManager(getActivity())).backup(sc_id, appName);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 239) {
            if (resultCode != -1) {
                return;
            }

            requestCode = projectsAdapter.layoutPosition;
        } else {
            if (requestCode == 508) {
                if (resultCode == -1) {
                    data = new Intent(getContext(), ExportProjectActivity.class);
                    data.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    data.putExtra("sc_id", yB.c(projectsList.get(projectsAdapter.layoutPosition), "sc_id"));
                    startActivity(data);
                }

                return;
            }

            if (requestCode == 712) {
                if (resultCode == -1 && data != null && data.hasExtra("index")) {
                    toExportProjectActivity(data.getIntExtra("index", -1));
                }

                return;
            }

            label142:
            {
                if (requestCode != 708) {
                    if (requestCode == 709) {
                        if (resultCode != -1 || data == null || !data.hasExtra("index")) {
                            return;
                        }
                        break label142;
                    }

                    switch (requestCode) {
                        case REQUEST_CODE_DESIGN_ACTIVITY:
                            if (super.a(requestCode) && !super.e.h()) {
                                xo.k();
                            }

                            return;
                        case 205:
                            return;
                        case REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY:
                            if (resultCode == -1) {
                                g();
                                if (data.getBooleanExtra("is_new", false)) {
                                    toDesignActivity(data.getStringExtra("sc_id"));
                                    return;
                                }
                            }

                            return;
                        default:
                            switch (requestCode) {
                                case REQUEST_CODE_RESTORE_PROJECT:
                                    if (resultCode == -1) {
                                        g();
                                    }

                                    return;
                                case 701:
                                    if (resultCode != -1) {
                                        return;
                                    }

                                    g();
                                    if (data == null || !data.hasExtra("index")) {
                                        return;
                                    }
                                    break label142;
                                case 702:
                                    if (resultCode == -1) {
                                        if (data != null && data.hasExtra("index")) {
                                            break label142;
                                        }
                                        break;
                                    }

                                    return;
                                default:
                                    return;
                            }

                    }
                } else if (resultCode != -1) {
                    return;
                }

                restoreProject();
                return;
            }

            requestCode = data.getIntExtra("index", -1);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId != R.id.create_new_project) {
            if (viewId != R.id.fab) {
                if (viewId == R.id.layout_manage_publish && super.a(REQUEST_CODE_RESTORE_PROJECT)) {
                    restoreProject();
                }

                return;
            }

        }
        if (!super.a(REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY)) {
            return;
        }

        toProjectSettingsActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.myprojects, parent, false);
        a(viewGroup);
        return viewGroup;
    }

    @SuppressLint("StaticFieldLeak")
    public class DeleteProjectTask extends MA {
        public int position;
        public String d;
        public String e;

        public DeleteProjectTask(int position) {
            super(getContext());
            e = "";
            this.position = position;
            GC.this.b();
            GC.this.a(this);
        }

        public void a() {
            if (position < projectsList.size()) {
                projectsList.remove(position);
                projectsAdapter.e(position);
                projectsAdapter.a(position, projectsAdapter.a());
            }

            GC.this.a();
        }

        public void a(String idk) {
            GC.this.a();
        }

        public void b() {
            if (position < projectsList.size()) {
                d = yB.c(projectsList.get(position), "sc_id");
                lC.a(super.a, d);
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {
        public final GC projectsFragment;
        public int layoutPosition;

        public ProjectsAdapter(GC var1, RecyclerView recyclerView) {
            projectsFragment = var1;
            layoutPosition = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new RecyclerView.m() {
                    public void a(RecyclerView recyclerView1, int var2, int var3) {
                        super.a(recyclerView1, var2, var3);
                        boolean var4;
                        GC var5;
                        if (var3 > 4) {
                            if (projectsFragment.q) {
                                return;
                            }

                            collapseAnimatorSet.start();
                            var5 = projectsFragment;
                            var4 = true;
                        } else {
                            if (var3 >= -4 || !projectsFragment.q) {
                                return;
                            }

                            expandAnimatorSet.start();
                            var5 = projectsFragment;
                            var4 = false;
                        }

                        var5.q = var4;
                    }
                });
            }

        }

        @Override
        public int a() {
            return projectsList.size();
        }

        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = projectsList.get(position);
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
                if (VERSION.SDK_INT >= 24) {
                    Context var9 = projectsFragment.getContext();
                    String providerPath = projectsFragment.getContext().getPackageName() + ".provider";
                    String iconPath = wq.e() + File.separator + scId;
                    uri = FileProvider.a(var9, providerPath, new File(iconPath, "icon.png"));
                } else {
                    String var11 = wq.e() + File.separator + scId;
                    uri = Uri.fromFile(new File(var11, "icon.png"));
                }

                viewHolder.imgIcon.setImageURI(uri);
            }

            viewHolder.appName.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.projectName.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.packageName.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String var12 = String.format("%s(%s)", yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code"));
            viewHolder.projectVersion.setText(var12);
            viewHolder.tvPublished.setVisibility(View.VISIBLE);
            viewHolder.tvPublished.setText(yB.c(projectMap, "sc_id"));
            viewHolder.b.setTag("custom");
        }

        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.myprojects_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public TextView tvPublished;
            public ImageView expand;
            public MyProjectButtonLayout projectButtonLayout;
            public LinearLayout projectOptionLayout;
            public LinearLayout projectOption;
            public LinearLayout projectOne;
            public View appIconLayout;
            public CircleImageView imgIcon;
            public TextView projectName;
            public TextView appName;
            public TextView packageName;
            public TextView projectVersion;

            public ViewHolder(View itemView) {
                super(itemView);
                projectOne = itemView.findViewById(R.id.project_one);
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
                projectButtonLayout = new MyProjectButtonLayout(getContext());
                projectOption.addView(projectButtonLayout);
                projectButtonLayout.setButtonOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = j();
                        if (layoutPosition <= projectsList.size()) {
                            HashMap<String, Object> projectMap = projectsList.get(layoutPosition);
                            if (view instanceof MyProjectButton) {
                                switch (((MyProjectButton) view).b) {
                                    case 0:
                                        toProjectSettingOrRequestPermission(layoutPosition);
                                        break;

                                    case 1:
                                        backupProject(layoutPosition);
                                        break;

                                    case 2:
                                        toExportProjectActivity(layoutPosition);
                                        break;

                                    case 3:
                                        projectMap.put("confirmation", true);
                                        projectButtonLayout.b();
                                        break;

                                    case 4:
                                        showProjectSettingDialog(layoutPosition);
                                        break;
                                }
                            } else {
                                if (view.getId() == R.id.confirm_yes) {
                                    projectMap.put("confirmation", false);
                                    projectMap.put("expand", false);
                                    (new DeleteProjectTask(layoutPosition)).execute();
                                } else if (view.getId() == R.id.confirm_no) {
                                    projectMap.put("confirmation", false);
                                    ProjectsAdapter.this.c(layoutPosition);
                                }

                            }
                        }
                    }
                });
                projectOne.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = j();
                        toDesignActivity(yB.c(projectsList.get(layoutPosition), "sc_id"));
                    }
                });
                projectOne.setOnLongClickListener(view -> {
                    layoutPosition = j();
                    if (yB.a(projectsList.get(layoutPosition), "expand")) {
                        collapse();
                    } else {
                        expand();
                    }

                    return true;
                });
                appIconLayout.setOnClickListener(view -> {
                    mB.a(view);
                    layoutPosition = j();
                    toProjectSettingOrRequestPermission(layoutPosition);
                });
                expand.setOnClickListener(view -> {
                    if (!mB.a()) {
                        layoutPosition = j();
                        if (yB.a(projectsList.get(layoutPosition), "expand")) {
                            collapse();
                        } else {
                            expand();
                        }

                    }
                });
            }

            public void collapse() {
                projectsList.get(layoutPosition).put("expand", false);
                gB.a(expand, 0.0F, null);
                gB.a(projectOptionLayout, 300, new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator var1) {
                        projectOptionLayout.setVisibility(View.GONE);
                    }
                });
            }

            public void expand() {
                projectOptionLayout.setVisibility(View.VISIBLE);
                projectsList.get(layoutPosition).put("expand", true);
                gB.a(expand, -180.0F, null);
                gB.b(projectOptionLayout, 300, null);
            }
        }
    }
}
