package a.a.a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
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
    public AnimatorSet r;
    public AnimatorSet s;
    public ValueAnimator t;
    public ValueAnimator u;
    public ProjectsAdapter projectsAdapter;
    public DB w;
    public FloatingActionButton floatingActionButton;
    public ro y;

    private void toProjectSettingOrRequestPermission(int position) {
        if (super.c()) {
            Intent intent = new Intent(getContext(), MyProjectSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("sc_id", yB.c(projectsList.get(position), "sc_id"));
            intent.putExtra("is_update", true);
            intent.putExtra("advanced_open", false);
            intent.putExtra("index", position);
            startActivityForResult(intent, 206);
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
        r = new AnimatorSet();
        s = new AnimatorSet();
        t = ValueAnimator.ofFloat(wB.a(getContext(), 96.0F), wB.a(getContext(), 48.0F));
        t.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            cvManagePublish.getLayoutParams().height = (int) value;
            cvManagePublish.requestLayout();
        });
        u = ValueAnimator.ofFloat(wB.a(getContext(), 48.0F), wB.a(getContext(), 96.0F));
        u.addUpdateListener(valueAnimator -> {
            float value = (Float) valueAnimator.getAnimatedValue();
            cvManagePublish.getLayoutParams().height = (int) value;
            cvManagePublish.requestLayout();
        });
        r.playTogether(t,
                ObjectAnimator.ofFloat(tvManagePublish, View.TRANSLATION_Y, 0.0F, -100.0F),
                ObjectAnimator.ofFloat(tvManagePublish, View.ALPHA, 1.0F, 0.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_X, 1.0F, 0.5F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_Y, 1.0F, 0.5F));
        s.playTogether(u,
                ObjectAnimator.ofFloat(tvManagePublish, View.TRANSLATION_Y, -100.0F, 0.0F),
                ObjectAnimator.ofFloat(tvManagePublish, View.ALPHA, 0.0F, 1.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_X, 0.5F, 1.0F),
                ObjectAnimator.ofFloat(ivManagePublish, View.SCALE_Y, 0.5F, 1.0F));
        r.setDuration(300L);
        s.setDuration(300L);
        g();
    }

    public void a(boolean isEmpty) {
        projectsList = lC.a();
        if (projectsList.size() > 0) {
            Collections.sort(projectsList,
                    (first, second) -> Integer.compare(Integer.parseInt(yB.c(first, "sc_id")),
                            Integer.parseInt(yB.c(second, "sc_id"))) * -1);
        }

        myProjects.getAdapter().c();
        if (isEmpty) showCreateNewProjectLayout();

    }

    public void b(int requestCode) {
        if (requestCode == 206) {
            toProjectSettingsActivity();
        } else if (requestCode == 700) {
            restoreProject();
        }
    }

    private void toDesignActivity(String sc_id) {
        Intent intent = new Intent(getContext(), DesignActivity.class);
        ProjectTracker.setScId(sc_id);
        intent.putExtra("sc_id", sc_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 204);
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
        startActivityForResult(intent, 206);
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

            requestCode = projectsAdapter.c;
        } else {
            if (requestCode == 508) {
                if (resultCode == -1) {
                    data = new Intent(getContext(), ExportProjectActivity.class);
                    data.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    data.putExtra("sc_id", yB.c(projectsList.get(projectsAdapter.c), "sc_id"));
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
                        case 204:
                            if (super.a(requestCode) && !super.e.h()) {
                                xo.k();
                            }

                            return;
                        case 205:
                            return;
                        case 206:
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
                                case 700:
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
                if (viewId == R.id.layout_manage_publish && super.a(700)) {
                    restoreProject();
                }

                return;
            }

        }
        if (super.a(206)) {
            return;
        }

        toProjectSettingsActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.myprojects, parent, false);
        y = new ro(getContext());
        a(viewGroup);
        w = new DB(getContext(), "P25");
        return viewGroup;
    }

    @SuppressLint("StaticFieldLeak")
    public static class c extends MA {
        public final GC f;
        public int c;
        public String d;
        public String e;

        public c(GC projectsFragment, Context var2, int position) {
            super(var2);
            f = projectsFragment;
            e = "";
            c = position;
            projectsFragment.b();
            projectsFragment.a(this);
        }

        public void a() {
            if (c < f.projectsList.size()) {
                f.projectsList.remove(c);
                f.projectsAdapter.e(c);
                f.projectsAdapter.a(c, f.projectsAdapter.a());
            }

            f.a();
        }

        public void a(String var1) {
            f.a();
        }

        public void b() {
            if (c < f.projectsList.size()) {
                d = yB.c(f.projectsList.get(c), "sc_id");
                lC.a(super.a, d);
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {
        public final GC d;
        public int c;

        public ProjectsAdapter(GC var1, RecyclerView recyclerView) {
            d = var1;
            c = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new RecyclerView.m() {
                    public void a(RecyclerView recyclerView1, int var2, int var3) {
                        super.a(recyclerView1, var2, var3);
                        boolean var4;
                        GC var5;
                        if (var3 > 4) {
                            if (d.q) {
                                return;
                            }

                            d.r.start();
                            var5 = d;
                            var4 = true;
                        } else {
                            if (var3 >= -4 || !d.q) {
                                return;
                            }

                            d.s.start();
                            var5 = d;
                            var4 = false;
                        }

                        var5.q = var4;
                    }
                });
            }

        }

        public int a() {
            return projectsList.size();
        }

        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = d.projectsList.get(position);
            String scId = yB.c(projectMap, "sc_id");
            float rotation;
            int visibility;
            if (yB.a(projectMap, "expand")) {
                visibility = 0;
                rotation = -180.0F;
            } else {
                visibility = 8;
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
                    Context var9 = d.getContext();
                    String providerPath = d.getContext().getPackageName() + ".provider";
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
            viewHolder.tvPublished.setVisibility(0);
            viewHolder.tvPublished.setText(yB.c(projectMap, "sc_id"));
            viewHolder.b.setTag("custom");
        }

        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.myprojects_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public final ProjectsAdapter F;
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

            public ViewHolder(ProjectsAdapter var1, View itemView) {
                super(itemView);
                F = var1;
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
                projectButtonLayout = new MyProjectButtonLayout(var1.d.getContext());
                projectOption.addView(projectButtonLayout);
                projectButtonLayout.setButtonOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        if (F.c <= F.d.projectsList.size()) {
                            HashMap<String, Object> var7 = GC.this.projectsList.get(F.c);
                            int var3;
                            ProjectsAdapter projectsAdapter;
                            if (view instanceof MyProjectButton) {
                                var3 = ((MyProjectButton) view).b;
                                if (var3 != 0) {
                                    if (var3 != 1) {
                                        if (var3 != 2) {
                                            if (var3 != 3) {
                                                if (var3 == 4) {
                                                    projectsAdapter = F;
                                                    showProjectSettingDialog(projectsAdapter.c);
                                                }
                                            } else {
                                                var7.put("confirmation", true);
                                                projectButtonLayout.b();
                                            }
                                        } else {
                                            projectsAdapter = F;
                                            toExportProjectActivity(projectsAdapter.c);
                                        }
                                    } else {
                                        projectsAdapter = F;
                                        backupProject(projectsAdapter.c);
                                    }
                                } else {
                                    projectsAdapter = F;
                                    toProjectSettingOrRequestPermission(projectsAdapter.c);
                                }

                            } else {
                                int viewId = view.getId();
                                if (viewId != R.id.confirm_no) {
                                    if (viewId == R.id.confirm_yes) {
                                        var7.put("confirmation", false);
                                        var7.put("expand", false);
                                        GC var4 = F.d;
                                        (new c(var4, var4.getContext(), F.c)).execute();
                                    }
                                } else {
                                    var7.put("confirmation", false);
                                    projectsAdapter = F;
                                    projectsAdapter.c(projectsAdapter.c);
                                }

                            }
                        }
                    }
                });
                projectOne.setOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        String var3 = yB.c(F.d.projectsList.get(F.c), "sc_id");
                        F.d.toDesignActivity(var3);
                    }
                });
                projectOne.setOnLongClickListener(view -> {
                    F.c = j();
                    if (yB.a(F.d.projectsList.get(F.c), "expand")) {
                        D();
                    } else {
                        E();
                    }

                    return true;
                });
                appIconLayout.setOnClickListener(view -> {
                    mB.a(view);
                    F.c = j();
                    toProjectSettingOrRequestPermission(F.c);
                });
                expand.setOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        if (yB.a(F.d.projectsList.get(F.c), "expand")) {
                            D();
                        } else {
                            E();
                        }

                    }
                });
            }

            public void D() {
                F.d.projectsList.get(F.c).put("expand", false);
                gB.a(expand, 0.0F, null);
                gB.a(projectOptionLayout, 300, new AnimatorListener() {
                    public void onAnimationCancel(Animator var1) {
                    }

                    public void onAnimationEnd(Animator var1) {
                        projectOptionLayout.setVisibility(8);
                    }

                    public void onAnimationRepeat(Animator var1) {
                    }

                    public void onAnimationStart(Animator var1) {
                    }
                });
            }

            public void E() {
                projectOptionLayout.setVisibility(0);
                F.d.projectsList.get(F.c).put("expand", true);
                gB.a(expand, -180.0F, null);
                gB.b(projectOptionLayout, 300, null);
            }
        }
    }
}
