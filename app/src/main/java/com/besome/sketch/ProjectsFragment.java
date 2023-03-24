package com.besome.sketch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.DA;
import a.a.a.DB;
import a.a.a.lC;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupRestoreManager;

public class ProjectsFragment extends DA implements View.OnClickListener {


    private static final int REQUEST_CODE_RESTORE_PROJECT = 700;
    private static final int REQUEST_CODE_DESIGN_ACTIVITY = 204;
    public static final int REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY = 206;


    private SwipeRefreshLayout swipeRefresh;
    private SearchView projectsSearchView;
    private RecyclerView myProjects;
    ArrayList<HashMap<String, Object>> projectsList = new ArrayList<>();
    private ProjectsAdapter projectsAdapter;
    private DB preference;


    private void initialize(ViewGroup parent) {
        preference = new DB(getContext(), "project");
        swipeRefresh = parent.findViewById(R.id.swipe_refresh);
        projectsSearchView = new SearchView(requireActivity());

        getActivity().findViewById(R.id.create_new_project).setOnClickListener(this);

        swipeRefresh.setOnRefreshListener(() -> {
            if (swipeRefresh.d()) swipeRefresh.setRefreshing(false);
            if (!c()) ((MainActivity) getActivity()).s(); //Check & Ask for storage permission
            refreshProjectsList();
        });

        myProjects = parent.findViewById(R.id.myprojects);
        myProjects.setHasFixedSize(true);


        projectsSearchView.setOnQueryTextListener(new SearchView.c() {
            @Override
            public boolean onQueryTextChange(String s) {
                projectsAdapter.filterData(s);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                myProjects.getAdapter().c();
                return false;
            }
        });

        refreshProjectsList();
    }

    public void refreshProjectsList() {
        // Don't load project list without having permissions
        if (!c()) return;

        projectsList = lC.a();
        if (projectsList.size() > 0) {
            Collections.sort(projectsList, new ProjectComparator(preference.d("sortBy")));
        }

        showHideSearchBox(projectsList.isEmpty());
        projectsAdapter = new ProjectsAdapter(getActivity(), projectsList);
        myProjects.setAdapter(projectsAdapter);
        projectsAdapter.filterData(projectsSearchView.getQuery().toString());
    }

    @Override
    public void b(int requestCode) {
        if (requestCode == REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY) {
            toProjectSettingsActivity();
        } else if (requestCode == REQUEST_CODE_RESTORE_PROJECT) {
            restoreProject();
        }
    }

    public static void toDesignActivity(Activity activity, String sc_id) {
        Intent intent = new Intent(activity, DesignActivity.class);
        ProjectTracker.setScId(sc_id);
        intent.putExtra("sc_id", sc_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivityForResult(intent, REQUEST_CODE_DESIGN_ACTIVITY);
    }

    @Override
    public void c(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void d() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }
    }

    @Override
    public void e() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }
    }

    public int getProjectsCount() {
        return projectsList.size();
    }


    private void showHideSearchBox(boolean hide) {
        projectsSearchView.setVisibility(hide ? View.GONE : View.VISIBLE);
    }


    private void toProjectSettingsActivity() {
        Intent intent = new Intent(getActivity(), MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY);
    }

    private void restoreProject() {
        (new BackupRestoreManager(getActivity(), this)).restore();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                refreshProjectsList();
                if (data.getBooleanExtra("is_new", false)) {
                    toDesignActivity(getActivity(), data.getStringExtra("sc_id"));
                }
            }
        } else if (requestCode == REQUEST_CODE_RESTORE_PROJECT) {
            if (resultCode == Activity.RESULT_OK) {
                refreshProjectsList();
                restoreProject();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if ((viewId == R.id.create_new_project) && super.a(REQUEST_CODE_PROJECT_SETTINGS_ACTIVITY)) {
            toProjectSettingsActivity();
        } else if (viewId == R.id.cv_restore_projects && super.a(REQUEST_CODE_RESTORE_PROJECT)) {
            restoreProject();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.myprojects, parent, false);
        initialize(viewGroup);
        return viewGroup;
    }
}
