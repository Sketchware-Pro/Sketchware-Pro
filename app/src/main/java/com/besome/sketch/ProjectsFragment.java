package com.besome.sketch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.MyprojectsBinding;
import com.sketchware.remod.databinding.SortProjectDialogBinding;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.DA;
import a.a.a.DB;
import a.a.a.aB;
import a.a.a.lC;
import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupRestoreManager;
import mod.hey.studios.util.Helper;

public class ProjectsFragment extends DA implements View.OnClickListener {
    private MyprojectsBinding binding;
    private final ArrayList<HashMap<String, Object>> projectsList = new ArrayList<>();
    private ProjectsAdapter projectsAdapter;
    private DB preference;
    private SearchView projectsSearchView;

    @Override
    public void b(int requestCode) {
    }

    public void toDesignActivity(String sc_id) {
        Intent intent = new Intent(requireContext(), DesignActivity.class);
        ProjectTracker.setScId(sc_id);
        intent.putExtra("sc_id", sc_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        requireActivity().startActivity(intent);
    }

    @Override
    public void c(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
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
        synchronized (projectsList) {
            return projectsList.size();
        }
    }

    public void toProjectSettingsActivity() {
        Intent intent = new Intent(getActivity(), MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openProjectSettings.launch(intent);
    }

    public void restoreProject() {
        new BackupRestoreManager(getActivity(), this).restore();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.create_new_project) {
            toProjectSettingsActivity();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.projects_fragment_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        projectsSearchView = (SearchView) menu.findItem(R.id.searchProjects).getActionView();
        if (projectsSearchView == null) return;
        projectsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String s) {
                projectsAdapter.filterData(s);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sortProject) {
            showProjectSortingDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        binding = MyprojectsBinding.inflate(inflater, parent, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // avoid memory leaks
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        preference = new DB(requireContext(), "project");

        ExtendedFloatingActionButton fab = requireActivity().findViewById(R.id.create_new_project);
        fab.setOnClickListener(this);
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(fab);

        binding.swipeRefresh.setOnRefreshListener(this::refreshProjectsList);

        binding.myprojects.setHasFixedSize(true);

        projectsAdapter = new ProjectsAdapter(this, new ArrayList<>(projectsList));
        binding.myprojects.setAdapter(projectsAdapter);

        binding.myprojects.post(this::refreshProjectsList); // wait for recyclerview to be ready

        binding.myprojects.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 2) {
                    fab.shrink();
                } else if (dy < -2) {
                    fab.extend();
                }
            }
        });
    }

    public final ActivityResultLauncher<Intent> openProjectSettings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            refreshProjectsList();
            if (data.getBooleanExtra("is_new", false)) {
                toDesignActivity(data.getStringExtra("sc_id"));
            }
        }
    });

    public void refreshProjectsList() {
        // Don't load project list without having permissions
        if (!c()) {
            if (binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
            ((MainActivity) requireActivity()).s(); // ask for permissions
            return;
        }

        new Thread(() -> {
            synchronized (projectsList) {
                projectsList.clear();
                projectsList.addAll(lC.a());
                projectsList.sort(new ProjectComparator(preference.d("sortBy")));
            }

            requireActivity().runOnUiThread(() -> {
                if (binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
                if (binding.loading3balls.getVisibility() == View.VISIBLE) {
                    binding.loading3balls.setVisibility(View.GONE);
                    binding.myprojects.setVisibility(View.VISIBLE);
                }
                projectsAdapter.setAllProjects(new ArrayList<>(projectsList));
                if (projectsSearchView != null)
                    projectsAdapter.filterData(projectsSearchView.getQuery().toString());
            });
        }).start();
    }


    private void showProjectSortingDialog() {
        aB dialog = new aB(requireActivity());
        dialog.b("Sort options");

        SortProjectDialogBinding dialogBinding = SortProjectDialogBinding.inflate(LayoutInflater.from(requireActivity()));
        RadioButton sortByName = dialogBinding.sortByName;
        RadioButton sortByID = dialogBinding.sortByID;
        RadioButton sortOrderAsc = dialogBinding.sortOrderAsc;
        RadioButton sortOrderDesc = dialogBinding.sortOrderDesc;

        int storedValue = preference.a("sortBy", ProjectComparator.DEFAULT);
        if ((storedValue & ProjectComparator.SORT_BY_NAME) == ProjectComparator.SORT_BY_NAME) {
            sortByName.setChecked(true);
        }
        if ((storedValue & ProjectComparator.SORT_BY_ID) == ProjectComparator.SORT_BY_ID) {
            sortByID.setChecked(true);
        }
        if ((storedValue & ProjectComparator.SORT_ORDER_ASCENDING) == ProjectComparator.SORT_ORDER_ASCENDING) {
            sortOrderAsc.setChecked(true);
        }
        if ((storedValue & ProjectComparator.SORT_ORDER_DESCENDING) == ProjectComparator.SORT_ORDER_DESCENDING) {
            sortOrderDesc.setChecked(true);
        }

        dialog.a(dialogBinding.getRoot());
        dialog.b("Save", v -> {
            int sortValue = 0;
            if (sortByName.isChecked()) {
                sortValue |= ProjectComparator.SORT_BY_NAME;
            }
            if (sortByID.isChecked()) {
                sortValue |= ProjectComparator.SORT_BY_ID;
            }
            if (sortOrderAsc.isChecked()) {
                sortValue |= ProjectComparator.SORT_ORDER_ASCENDING;
            }
            if (sortOrderDesc.isChecked()) {
                sortValue |= ProjectComparator.SORT_ORDER_DESCENDING;
            }
            preference.a("sortBy", sortValue, true);
            dialog.dismiss();
            refreshProjectsList();
        });
        dialog.a("Cancel", Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
