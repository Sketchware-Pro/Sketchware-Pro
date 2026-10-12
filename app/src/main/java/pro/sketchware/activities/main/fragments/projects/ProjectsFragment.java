package pro.sketchware.activities.main.fragments.projects;

import pro.sketchware.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DiffUtil;

import com.besome.sketch.adapters.ProjectsAdapter;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.manage.library.ProjectComparator;
import com.besome.sketch.lib.ui.LoadingDialog;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.transition.MaterialFadeThrough;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import a.a.a.DA;
import a.a.a.DB;
import a.a.a.lC;
import a.a.a.yB;
import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupFactory;
import mod.hey.studios.project.backup.BackupRestoreManager;
import pro.sketchware.activities.main.activities.MainActivity;
import pro.sketchware.databinding.MyprojectsBinding;
import pro.sketchware.databinding.SortProjectDialogBinding;
import pro.sketchware.utility.AnalyticsHelper;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

public class ProjectsFragment extends DA {
    private static final String FOLDER_KEY_PREFIX = "folder:";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    /* Every project, sorted. */
    private final List<HashMap<String, Object>> allProjects = new ArrayList<>();
    /* What the list currently shows: folders of the current folder, then its projects (or search results). */
    private final List<HashMap<String, Object>> projectsList = new ArrayList<>();
    private final Set<String> selectedIds = new LinkedHashSet<>();
    private MyprojectsBinding binding;
    private ProjectsAdapter projectsAdapter;
    private ProjectFolderStore folderStore;
    private DB preference;
    private SearchView projectsSearchView;
    private MenuProvider menuProvider;
    private OnBackPressedCallback backCallback;

    /* null = root */
    private String currentFolderId;
    /* null = search everywhere */
    private String searchScopeFolderId;
    private String searchQuery = "";
    private boolean selectionMode;

    public final ActivityResultLauncher<Intent> openProjectSettings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                String sc_id = data.getStringExtra("sc_id");
                if (sc_id == null) return;
                if (data.getBooleanExtra("is_new", false)) {
                    if (currentFolderId != null) {
                        folderStore.assign(sc_id, currentFolderId);
                    }
                    toDesignActivity(sc_id);
                }
                refreshProjectsList();
            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        setReturnTransition(new MaterialFadeThrough());
        setExitTransition(new MaterialFadeThrough());
        setReenterTransition(new MaterialFadeThrough());
    }

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
        folderStore = new ProjectFolderStore();

        ExtendedFloatingActionButton fab = requireActivity().findViewById(R.id.create_new_project);
        fab.setOnClickListener((v) -> ProjectsSheets.showCreateSheet(this));
        Insetter.builder().margin(WindowInsetsCompat.Type.navigationBars()).applyToView(fab);

        binding.swipeRefresh.setOnRefreshListener(this::refreshProjectsList);

        projectsAdapter = new ProjectsAdapter(this, projectsList);
        binding.myprojects.setAdapter(projectsAdapter);
        binding.myprojects.setHasFixedSize(true);

        binding.myprojects.post(this::refreshProjectsList); // wait for RecyclerView to be ready
        UI.addSystemWindowInsetToPadding(binding.specialActionContainer, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.loadingContainer, true, false, true, true);
        UI.addSystemWindowInsetToPadding(binding.titleContainer, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.myprojects, true, false, true, true);

        binding.nestedScroll.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                fab.shrink();
            } else if (scrollY < oldScrollY) {
                fab.extend();
            }
        });

        binding.titleText.setSingleLine(true);
        binding.titleText.setEllipsize(TextUtils.TruncateAt.END);
        binding.titleText.setOnClickListener(v -> {
            if (!selectionMode && currentFolderId != null) navigateUp();
        });

        binding.iconSort.setOnClickListener(v -> showProjectSortingDialog());
        binding.specialAction.getRoot().setOnClickListener(v -> ProjectsSheets.showImportSheet(this));

        backCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                if (selectionMode) {
                    exitSelection();
                } else if (currentFolderId != null) {
                    navigateUp();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backCallback);

        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.projects_fragment_menu, menu);
                MenuItem searchItem = menu.findItem(R.id.searchProjects);
                searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        searchQuery = "";
                        rebuildRows();
                        return true;
                    }
                });
                projectsSearchView = (SearchView) searchItem.getActionView();
                if (projectsSearchView != null) {
                    updateSearchHint();
                    projectsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextChange(String s) {
                            searchQuery = s == null ? "" : s.trim();
                            rebuildRows();
                            return false;
                        }

                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            if (s != null && !s.trim().isEmpty()) {
                                AnalyticsHelper.logFeatureSearch(getContext(), s, "projects");
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.projectsSearchScope) {
                    ProjectsSheets.showSearchScopeSheet(ProjectsFragment.this);
                    return true;
                }
                return false;
            }
        };

        requireActivity().addMenuProvider(menuProvider);
        updateTitle();
        updateBackCallback();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (getActivity() == null) return;
        if (hidden) {
            requireActivity().removeMenuProvider(menuProvider);
        } else {
            requireActivity().addMenuProvider(menuProvider);
        }
        updateBackCallback();
    }

    /* ---------- Loading & building the list ---------- */

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
            loadedProjects.sort(new ProjectComparator(preference.d("sortBy"), preference.a("pinnedProject", "-1")));

            requireActivity().runOnUiThread(() -> {
                if (binding == null || !isAdded()) return;
                if (binding.swipeRefresh.isRefreshing()) binding.swipeRefresh.setRefreshing(false);
                if (binding.loadingContainer.getVisibility() == View.VISIBLE) {
                    binding.loadingContainer.setVisibility(View.GONE);
                    binding.myprojects.setVisibility(View.VISIBLE);
                }
                allProjects.clear();
                allProjects.addAll(loadedProjects);
                rebuildRows();
            });
        });
    }

    /**
     * Recomputes the visible rows from {@link #allProjects}, the current folder and the search state.
     */
    private void rebuildRows() {
        if (binding == null) return;

        List<HashMap<String, Object>> rows = new ArrayList<>();
        boolean searching = !searchQuery.isEmpty();

        if (!searching) {
            for (ProjectFolderStore.Folder folder : folderStore.getChildFolders(currentFolderId)) {
                rows.add(buildFolderRow(folder));
            }
        }

        for (HashMap<String, Object> project : allProjects) {
            String folderId = folderStore.getProjectFolderId(yB.c(project, "sc_id"));
            if (searching) {
                if (matchesQuery(project, searchQuery) && folderStore.isInside(folderId, searchScopeFolderId)) {
                    rows.add(project);
                }
            } else if (Objects.equals(folderId, currentFolderId)) {
                rows.add(project);
            }
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProjectDiffCallback(projectsList, rows));
        projectsList.clear();
        projectsList.addAll(rows);
        diffResult.dispatchUpdatesTo(projectsAdapter);

        updateTitle();
        updateBackCallback();
    }

    private HashMap<String, Object> buildFolderRow(ProjectFolderStore.Folder folder) {
        int count = folderStore.countChildFolders(folder.id);
        for (HashMap<String, Object> project : allProjects) {
            if (folder.id.equals(folderStore.getProjectFolderId(yB.c(project, "sc_id")))) count++;
        }

        HashMap<String, Object> row = new HashMap<>();
        row.put("is_folder", Boolean.TRUE);
        row.put("folder_id", folder.id);
        row.put("sc_id", FOLDER_KEY_PREFIX + folder.id);
        row.put("my_ws_name", folder.name);
        row.put("child_count", count);
        return row;
    }

    private boolean matchesQuery(HashMap<String, Object> projectMap, String searchQuery) {
        String query = searchQuery.toLowerCase();
        for (String key : new String[]{"sc_id", "my_ws_name", "my_app_name", "my_sc_pkg_name"}) {
            if (yB.c(projectMap, key).toLowerCase().contains(query)) {
                return true;
            }
        }
        return false;
    }

    /* ---------- Folder navigation ---------- */

    public ProjectFolderStore getFolderStore() {
        return folderStore;
    }

    @Nullable
    public String getCurrentFolderId() {
        return currentFolderId;
    }

    public void openFolder(String folderId) {
        if (selectionMode) exitSelectionSilently();
        currentFolderId = folderId;
        rebuildRows();
        animateListChange(true);
    }

    public void navigateUp() {
        if (currentFolderId == null) return;
        ProjectFolderStore.Folder current = folderStore.getFolder(currentFolderId);
        if (selectionMode) exitSelectionSilently();
        currentFolderId = current == null ? null : current.parentId;
        rebuildRows();
        animateListChange(false);
    }

    private void animateListChange(boolean forward) {
        if (binding == null) return;
        View list = binding.myprojects;
        list.setAlpha(0f);
        list.setTranslationX(forward ? 48f : -48f);
        list.animate().alpha(1f).translationX(0f).setDuration(220).setInterpolator(new DecelerateInterpolator()).start();
    }

    private void updateTitle() {
        if (binding == null) return;
        if (selectionMode) {
            binding.titleText.setText(selectedIds.size() + " selected");
            return;
        }
        if (currentFolderId == null) {
            binding.titleText.setText(R.string.common_word_projects);
            return;
        }
        // Breadcrumb: Projects / Parent / Current
        StringBuilder path = new StringBuilder();
        String id = currentFolderId;
        int guard = 0;
        while (id != null && guard++ < 1000) {
            ProjectFolderStore.Folder folder = folderStore.getFolder(id);
            if (folder == null) break;
            path.insert(0, folder.name);
            id = folder.parentId;
            if (id != null) path.insert(0, " / ");
        }
        path.insert(0, getString(R.string.common_word_projects) + " / ");
        binding.titleText.setText(path.toString());
    }

    private void updateBackCallback() {
        if (backCallback == null) return;
        backCallback.setEnabled(!isHidden() && (selectionMode || currentFolderId != null));
    }

    /* ---------- Search ---------- */

    public void setSearchScope(@Nullable String folderId) {
        searchScopeFolderId = folderId;
        updateSearchHint();
        rebuildRows();
    }

    @Nullable
    public String getSearchScopeFolderId() {
        return searchScopeFolderId;
    }

    private void updateSearchHint() {
        if (projectsSearchView == null) return;
        ProjectFolderStore.Folder scope = folderStore == null ? null : folderStore.getFolder(searchScopeFolderId);
        projectsSearchView.setQueryHint(scope == null ? "Search all projects" : "Search in " + scope.name);
    }

    /* ---------- Selection ---------- */

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public boolean isSelected(String scId) {
        return selectedIds.contains(scId);
    }

    public void startSelection(String scId) {
        selectionMode = true;
        selectedIds.clear();
        selectedIds.add(scId);
        onSelectionChanged();
    }

    public void toggleSelection(String scId) {
        if (!selectedIds.remove(scId)) {
            selectedIds.add(scId);
        }
        onSelectionChanged();
    }

    public void exitSelection() {
        exitSelectionSilently();
        onSelectionChanged();
    }

    private void exitSelectionSilently() {
        selectionMode = false;
        selectedIds.clear();
    }

    private void onSelectionChanged() {
        updateTitle();
        updateBackCallback();
        if (projectsAdapter != null) projectsAdapter.notifyDataSetChanged();
    }

    public Set<String> getSelectedIds() {
        return new LinkedHashSet<>(selectedIds);
    }

    public void onProjectLongPressed(String scId) {
        if (selectionMode) {
            ProjectsSheets.showSelectionSheet(this);
        } else {
            ProjectsSheets.showProjectLongPressSheet(this, scId);
        }
    }

    public void onFolderLongPressed(String folderId, String folderName) {
        if (selectionMode) {
            ProjectsSheets.showSelectionSheet(this);
        } else {
            ProjectsSheets.showFolderSheet(this, folderId, folderName);
        }
    }

    /**
     * Moves every selected project into {@code folderId} (null = root).
     */
    public void moveSelectedTo(@Nullable String folderId) {
        for (String scId : selectedIds) {
            folderStore.assign(scId, folderId);
        }
        exitSelectionSilently();
        onSelectionChanged();
        rebuildRows();
    }

    /* ---------- Creating, renaming & deleting folders ---------- */

    public void promptCreateFolder() {
        promptFolderName("New folder", "", name -> {
            folderStore.createFolder(name, currentFolderId);
            rebuildRows();
        });
    }

    public void promptRenameFolder(String folderId, String currentName) {
        promptFolderName("Rename folder", currentName, name -> {
            folderStore.renameFolder(folderId, name);
            rebuildRows();
        });
    }

    private void promptFolderName(String title, String initial, Consumer<String> onName) {
        EditText input = new EditText(requireActivity());
        input.setText(initial);
        input.setSelectAllOnFocus(true);
        input.setSingleLine(true);
        input.setHint("Folder name");
        int padding = (int) (24 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding / 2, padding, padding / 2);

        new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(title)
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        onName.accept(name);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    public void confirmDeleteFolder(String folderId, String folderName) {
        Set<String> tree = folderStore.getFolderTreeIds(folderId);
        List<String> projectIds = new ArrayList<>();
        for (HashMap<String, Object> project : allProjects) {
            String scId = yB.c(project, "sc_id");
            if (tree.contains(folderStore.getProjectFolderId(scId))) projectIds.add(scId);
        }

        new MaterialAlertDialogBuilder(requireActivity())
                .setIcon(R.drawable.icon_delete)
                .setTitle("Delete folder \"" + folderName + "\"?")
                .setMessage("This deletes the folder and everything inside it, including "
                        + projectIds.size() + " project(s). This cannot be undone.")
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    dialog.dismiss();
                    deleteFolderInBackground(folderId, projectIds);
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private void deleteFolderInBackground(String folderId, List<String> projectIds) {
        LoadingDialog progressDialog = new LoadingDialog(requireActivity());
        progressDialog.show();
        executorService.execute(() -> {
            for (String scId : projectIds) {
                lC.a(requireActivity(), scId);
                folderStore.removeProject(scId);
            }
            folderStore.removeFolderTree(folderId);
            requireActivity().runOnUiThread(() -> {
                progressDialog.dismiss();
                if (currentFolderId != null && !folderStoreHas(currentFolderId)) {
                    currentFolderId = null;
                }
                refreshProjectsList();
            });
        });
    }

    private boolean folderStoreHas(String folderId) {
        return folderStore.getFolder(folderId) != null;
    }

    /* ---------- Deleting & duplicating projects ---------- */

    public void confirmDeleteProjects(Collection<String> scIds, String singleProjectName) {
        List<String> ids = new ArrayList<>(scIds);
        String message = ids.size() == 1 && singleProjectName != null && !singleProjectName.isEmpty()
                ? "Delete \"" + singleProjectName + "\"? This cannot be undone."
                : "Delete " + ids.size() + " selected projects? This cannot be undone.";

        new MaterialAlertDialogBuilder(requireActivity())
                .setIcon(R.drawable.icon_delete)
                .setTitle(R.string.delete_project_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    dialog.dismiss();
                    deleteProjectsInBackground(ids);
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private void deleteProjectsInBackground(List<String> ids) {
        LoadingDialog progressDialog = new LoadingDialog(requireActivity());
        progressDialog.show();
        executorService.execute(() -> {
            for (String scId : ids) {
                lC.a(requireActivity(), scId);
                folderStore.removeProject(scId);
            }
            requireActivity().runOnUiThread(() -> {
                progressDialog.dismiss();
                exitSelectionSilently();
                refreshProjectsList();
            });
        });
    }

    /**
     * Copies a project with the same backup/restore engine the app already uses.
     * The copy gets a new id, lives in the same folder and is named "<name> copy".
     */
    public void duplicateProject(String scId) {
        HashMap<String, Object> original = lC.b(scId);
        if (original == null) return;
        String name = yB.c(original, "my_ws_name");

        LoadingDialog progressDialog = new LoadingDialog(requireActivity());
        progressDialog.show();
        executorService.execute(() -> {
            String newId = lC.b();
            String error = null;
            boolean success = false;

            BackupFactory backup = new BackupFactory(scId);
            backup.setBackupLocalLibs(true);
            backup.setBackupCustomBlocks(true);
            backup.backup(requireActivity(), name);
            File tempBackup = backup.getOutFile();

            if (tempBackup == null) {
                error = backup.getError();
            } else {
                BackupFactory restore = new BackupFactory(newId);
                restore.setBackupLocalLibs(true);
                restore.restore(tempBackup);
                success = restore.isRestoreSuccess();
                error = restore.getError();
                //noinspection ResultOfMethodCallIgnored
                tempBackup.delete();
            }

            if (success) {
                HashMap<String, Object> copy = lC.b(newId);
                if (copy != null) {
                    copy.put("my_ws_name", name + " copy");
                    lC.b(newId, copy);
                }
                folderStore.assign(newId, folderStore.getProjectFolderId(scId));
            }

            boolean copied = success;
            String failure = error;
            requireActivity().runOnUiThread(() -> {
                progressDialog.dismiss();
                if (copied) {
                    SketchwareUtil.toast("Project duplicated");
                    refreshProjectsList();
                } else {
                    SketchwareUtil.toastError("Couldn't duplicate: " + failure);
                }
            });
        });
    }

    /* ---------- Sorting ---------- */

    private void showProjectSortingDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle(R.string.logic_sort_menu);

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
