package pro.sketchware.activities.main.fragments.projects;

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
import androidx.core.view.MenuProvider;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DiffUtil;

import com.besome.sketch.adapters.ProjectsAdapter;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import a.a.a.DA;
import a.a.a.DB;
import a.a.a.lC;
import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupRestoreManager;
import pro.sketchware.R;
import pro.sketchware.activities.main.activities.MainActivity;
import pro.sketchware.databinding.MyprojectsBinding;
import pro.sketchware.databinding.SortProjectDialogBinding;

public class ProjectsFragment extends DA {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final List<HashMap<String, Object>> projectsList = new ArrayList<>();
    private MyprojectsBinding binding;
    private ProjectsAdapter projectsAdapter;
    public final ActivityResultLauncher<Intent> openProjectSettings = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String sc_id = data.getStringExtra("sc_id");
                        if (data.getBooleanExtra("is_new", false)) {
                            toDesignActivity(sc_id);
                            addProject(sc_id);
                        } else {
                            updateProject(sc_id);
                        }
                    }
                }
            }
    );
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
        startActivity(intent);
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

    public void toProjectSettingsActivity() {
        Intent intent = new Intent(getActivity(), MyProjectSettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openProjectSettings.launch(intent);
    }

    public void restoreProject() {
        new BackupRestoreManager(getActivity(), this).restore();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        binding = MyprojectsBinding.inflate(inflater, parent, false);
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
        fab.setOnClickListener((v) -> toProjectSettingsActivity());
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(fab);

        binding.swipeRefresh.setOnRefreshListener(this::refreshProjectsList);

        projectsAdapter = new ProjectsAdapter(this, projectsList);
        binding.myprojects.setAdapter(projectsAdapter);

        binding.myprojects.post(this::refreshProjectsList); // wait for RecyclerView to be ready

        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    fab.shrink();
                } else if (scrollY < oldScrollY) {
                    fab.extend();
                }
            }
        });

        binding.specialAction.getRoot().setOnClickListener(v -> restoreProject());

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.projects_fragment_menu, menu);
                projectsSearchView = (SearchView) menu.findItem(R.id.searchProjects).getActionView();
                if (projectsSearchView != null) {
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
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.sortProject) {
                    showProjectSortingDialog();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    public void refreshProjectsList() {
        // Check if the fragment is still attached to the activity
        if (!isAdded()) return;

        // Don't load project list without having permissions
        if (!c()) {
            if (binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
            ((MainActivity) requireActivity()).s(); // ask for permissions
            return;
        }

        executorService.execute(() -> {
            List<HashMap<String, Object>> loadedProjects = lC.a();
            loadedProjects.sort(new ProjectComparator(preference.d("sortBy")));

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProjectDiffCallback(projectsList, loadedProjects));

            requireActivity().runOnUiThread(() -> {
                if (binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
                if (binding.loading3balls.getVisibility() == View.VISIBLE) {
                    binding.loading3balls.setVisibility(View.GONE);
                    binding.myprojects.setVisibility(View.VISIBLE);
                }
                projectsList.clear();
                projectsList.addAll(loadedProjects);
                diffResult.dispatchUpdatesTo(projectsAdapter);
                if (projectsSearchView != null)
                    projectsAdapter.filterData(projectsSearchView.getQuery().toString());
            });
        });
    }

    private void addProject(String sc_id) {
        executorService.execute(() -> {
            HashMap<String, Object> newProject = lC.b(sc_id);
            if (newProject != null) {
                requireActivity().runOnUiThread(() -> {
                    projectsList.add(0, newProject);
                    projectsAdapter.notifyItemInserted(0);
                    binding.myprojects.scrollToPosition(0);
                });
            }
        });
    }

    private void updateProject(String sc_id) {
        executorService.execute(() -> {
            HashMap<String, Object> updatedProject = lC.b(sc_id);
            if (updatedProject != null) {
                int index = IntStream.range(0, projectsList.size()).filter(i -> projectsList.get(i).get("sc_id").equals(sc_id)).findFirst().orElse(-1);
                if (index != -1) {
                    projectsList.set(index, updatedProject);
                    requireActivity().runOnUiThread(() -> projectsAdapter.notifyItemChanged(index));
                }
            }
        });
    }

    private void showProjectSortingDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle("Sort options");

        SortProjectDialogBinding dialogBinding = SortProjectDialogBinding.inflate(LayoutInflater.from(requireActivity()));
        RadioButton sortByName = dialogBinding.sortByName;
        RadioButton sortByID = dialogBinding.sortByID;
        RadioButton sortOrderAsc = dialogBinding.sortOrderAsc;
        RadioButton sortOrderDesc = dialogBinding.sortOrderDesc;

        int storedValue = preference.a("sortBy", ProjectComparator.DEFAULT);
        if ((storedValue & ProjectComparator.SORT_BY_NAME) == ProjectComparator.SORT_BY_NAME) {
            sortByName.setChecked(true);
        } else if ((storedValue & ProjectComparator.SORT_BY_ID) == ProjectComparator.SORT_BY_ID) {
            sortByID.setChecked(true);
        }
        if ((storedValue & ProjectComparator.SORT_ORDER_ASCENDING) == ProjectComparator.SORT_ORDER_ASCENDING) {
            sortOrderAsc.setChecked(true);
        } else if ((storedValue & ProjectComparator.SORT_ORDER_DESCENDING) == ProjectComparator.SORT_ORDER_DESCENDING) {
            sortOrderDesc.setChecked(true);
        }

        dialog.setView(dialogBinding.getRoot());
        dialog.setPositiveButton("Save", (v, which) -> {
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
            v.dismiss();
            refreshProjectsList();
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }

    private static class ProjectDiffCallback extends DiffUtil.Callback {
        private final List<HashMap<String, Object>> oldList;
        private final List<HashMap<String, Object>> newList;

        public ProjectDiffCallback(List<HashMap<String, Object>> oldList, List<HashMap<String, Object>> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            String oldId = (String) oldList.get(oldItemPosition).get("sc_id");
            String newId = (String) newList.get(newItemPosition).get("sc_id");
            return oldId.equals(newId);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            HashMap<String, Object> oldItem = oldList.get(oldItemPosition);
            HashMap<String, Object> newItem = newList.get(newItemPosition);
            return oldItem.equals(newItem);
        }
    }
}
