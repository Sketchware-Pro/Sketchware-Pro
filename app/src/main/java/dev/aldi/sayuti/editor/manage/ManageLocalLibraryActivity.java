package dev.aldi.sayuti.editor.manage;

import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.createLibraryMap;
import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.deleteSelectedLocalLibraries;
import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.getAllLocalLibraries;
import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.getLocalLibFile;
import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.getLocalLibraries;
import static dev.aldi.sayuti.editor.manage.LocalLibrariesUtil.rewriteLocalLibFile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.util.Helper;
import mod.jbk.util.AddMarginOnApplyWindowInsetsListener;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageLocallibrariesBinding;
import pro.sketchware.databinding.ViewItemLocalLibBinding;
import pro.sketchware.databinding.ViewItemLocalLibSearchBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

import a.a.a.MA;
import a.a.a.mB;

public class ManageLocalLibraryActivity extends BaseAppCompatActivity {
    private ArrayList<HashMap<String, Object>> projectUsedLibs;

    private boolean notAssociatedWithProject;
    private boolean searchBarExpanded;
    private BuildSettings buildSettings;
    private ManageLocallibrariesBinding binding;
    private String scId;

    private LibraryAdapter adapter = new LibraryAdapter();
    private SearchAdapter searchAdapter = new SearchAdapter();

    private interface OnLocalLibrarySelectedStateChangedListener {
        void invoke(LocalLibrary library);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ManageLocallibrariesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.contextualToolbarContainer, (v, windowInsets) -> {
            var insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, 0);
            return windowInsets;
        });

        if (getIntent().hasExtra("sc_id")) {
            scId = Objects.requireNonNull(getIntent().getStringExtra("sc_id"));
            buildSettings = new BuildSettings(scId);
            notAssociatedWithProject = scId.equals("system");
        }

        adapter.setOnLocalLibrarySelectedStateChangedListener(item -> {
            long selectedItemCount = getSelectedLocalLibrariesCount();
            if (selectedItemCount > 0 && adapter.isSelectionModeEnabled) {
                binding.contextualToolbar.setTitle(String.valueOf(selectedItemCount));
                expandContextualToolbar();
            } else {
                adapter.isSelectionModeEnabled = false;
                collapseContextualToolbar();
            }
        });

        binding.librariesList.setAdapter(adapter);
        binding.searchList.setAdapter(searchAdapter);

        binding.searchBar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        binding.contextualToolbar.setNavigationOnClickListener(v -> hideContextualToolbarAndClearSelection());
        binding.contextualToolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_select_all) {
                setLocalLibrariesSelected(true);
                binding.contextualToolbar.setTitle(String.valueOf(getSelectedLocalLibrariesCount()));
                return true;
            } else if (id == R.id.action_delete_selected_local_libraries) {
                new Thread(() -> {
                    deleteSelectedLocalLibraries(adapter.getLocalLibraries());

                    runOnUiThread(() -> {
                        SketchwareUtil.toast("Deleted successfully");
                        runLoadLocalLibrariesTask();
                        adapter.isSelectionModeEnabled = false;
                        collapseContextualToolbar();
                    });
                }).start();
                return true;
            }
            return false;
        });

        binding.downloadLibraryButton.setOnClickListener(v -> {
            if (getSupportFragmentManager().findFragmentByTag("library_downloader_dialog") != null) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putBoolean("notAssociatedWithProject", notAssociatedWithProject);
            bundle.putSerializable("buildSettings", buildSettings);
            bundle.putString("localLibFile", getLocalLibFile(scId).getAbsolutePath());

            LibraryDownloaderDialogFragment fragment = new LibraryDownloaderDialogFragment();
            fragment.setArguments(bundle);
            fragment.setOnLibraryDownloadedTask(this::runLoadLocalLibrariesTask);
            fragment.show(getSupportFragmentManager(), "library_downloader_dialog");
        });

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                searchAdapter.filter(getAdapterLocalLibraries(), value);
            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
            }
        });

        runLoadLocalLibrariesTask();
    }

    private void runLoadLocalLibrariesTask() {
        k();
        new Handler().postDelayed(() -> {
            new LoadLocalLibrariesTask(this).execute();
        }, 500L);
    }

    private List<LocalLibrary> getAdapterLocalLibraries() {
        return adapter.getLocalLibraries();
    }

    private void hideContextualToolbarAndClearSelection() {
        adapter.isSelectionModeEnabled = false;
        if (collapseContextualToolbar()) {
            setLocalLibrariesSelected(false);
        }
    }

    public void setLocalLibrariesSelected(boolean selected) {
        for (LocalLibrary library : getAdapterLocalLibraries()) {
            library.setSelected(selected);
        }
        adapter.notifyDataSetChanged();
    }

    private void expandContextualToolbar() {
        searchBarExpanded = true;
        binding.searchBar.expand(binding.contextualToolbarContainer, binding.appBarLayout);
    }

    private boolean collapseContextualToolbar() {
        searchBarExpanded = false;
        return binding.searchBar.collapse(binding.contextualToolbarContainer, binding.appBarLayout);
    }

    private long getSelectedLocalLibrariesCount() {
        long count = 0;
        for (LocalLibrary library : getAdapterLocalLibraries()) {
            if (library.isSelected()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onBackPressed() {
        if (searchBarExpanded) {
            hideContextualToolbarAndClearSelection();
        } else if (binding.searchView.isShowing()) {
            binding.searchView.hide();
        } else {
            super.onBackPressed();
        }
    }

    // This method is running from the background thread.
    // So, every UI operation must be called inside `runOnUiThread`.
    private void loadLibraries() {
        var localLibraries = getAllLocalLibraries();
        if (!notAssociatedWithProject) {
            projectUsedLibs = getLocalLibraries(scId);
        }

        runOnUiThread(() -> {
            adapter.setLocalLibraries(localLibraries);
            binding.noContentLayout.setVisibility(localLibraries.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private static class LoadLocalLibrariesTask extends MA {
        private final WeakReference<ManageLocalLibraryActivity> activity;

        public LoadLocalLibrariesTask(ManageLocalLibraryActivity activity) {
            super(activity);
            this.activity = new WeakReference<>(activity);
            activity.addTask(this);
        }

        @Override
        public void a() {
            activity.get().h();
        }

        @Override
        public void a(String idk) {
            activity.get().h();
        }

        @Override
        public void b() {
            try {
                activity.get().loadLibraries();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
        private final List<LocalLibrary> localLibraries = new ArrayList<>();
        public boolean isSelectionModeEnabled;
        private @Nullable OnLocalLibrarySelectedStateChangedListener onLocalLibrarySelectedStateChangedListener;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ViewItemLocalLibBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final var binding = holder.binding;
            final var library = localLibraries.get(position);

            binding.libraryName.setText(library.getName());
            binding.librarySize.setText(library.getSize());
            binding.libraryName.setSelected(true);
            bindSelectedState(binding.card, library);

            binding.card.setOnClickListener(v -> {
                if (isSelectionModeEnabled) {
                    toggleLocalLibrary(binding.card, library, onLocalLibrarySelectedStateChangedListener);
                } else {
                    binding.materialSwitch.performClick();
                }
            });

            binding.card.setOnLongClickListener(v -> {
                if (isSelectionModeEnabled) {
                    return false;
                }

                isSelectionModeEnabled = true;
                toggleLocalLibrary(binding.card, library, onLocalLibrarySelectedStateChangedListener);
                return true;
            });

            binding.materialSwitch.setOnClickListener(v -> {
                onItemClicked(binding, library.getName());
            });

            binding.materialSwitch.setChecked(false);
            if (!notAssociatedWithProject) {
                for (Map<String, Object> libraryMap : projectUsedLibs) {
                    if (library.getName().equals(libraryMap.get("name").toString())) {
                        binding.materialSwitch.setChecked(true);
                    }
                }
            } else {
                binding.materialSwitch.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return localLibraries.isEmpty() ? 0 : localLibraries.size();
        }

        public void setOnLocalLibrarySelectedStateChangedListener(
                @Nullable OnLocalLibrarySelectedStateChangedListener onLocalLibrarySelectedStateChangedListener) {
            this.onLocalLibrarySelectedStateChangedListener = onLocalLibrarySelectedStateChangedListener;
        }

        private void toggleLocalLibrary(MaterialCardView card, LocalLibrary library,
                @Nullable OnLocalLibrarySelectedStateChangedListener onLocalLibrarySelectedStateChangedListener) {
            library.setSelected(!library.isSelected());
            bindSelectedState(card, library);
            if (onLocalLibrarySelectedStateChangedListener != null) {
                onLocalLibrarySelectedStateChangedListener.invoke(library);
            }
        }

        private void bindSelectedState(MaterialCardView card, LocalLibrary library) {
            card.setChecked(library.isSelected());
        }

        private void onItemClicked(ViewItemLocalLibBinding binding, String name) {
            HashMap<String, Object> localLibrary;
            if (!binding.materialSwitch.isChecked()) {
                // Remove the library from the list
                int indexToRemove = -1;
                for (int i = 0; i < projectUsedLibs.size(); i++) {
                    Map<String, Object> libraryMap = projectUsedLibs.get(i);
                    if (name.equals(libraryMap.get("name").toString())) {
                        indexToRemove = i;
                        break;
                    }
                }
                if (indexToRemove != -1) {
                    projectUsedLibs.remove(indexToRemove);
                }
            } else {
                // Add the library to the list
                // Here, we need to find the dependency string if it exists
                String dependency = null;
                for (Map<String, Object> libraryMap : projectUsedLibs) {
                    if (name.equals(libraryMap.get("name").toString())) {
                        dependency = (String) libraryMap.get("dependency");
                        break;
                    }
                }
                localLibrary = createLibraryMap(name, dependency);
                projectUsedLibs.add(localLibrary);
            }
            rewriteLocalLibFile(scId, new Gson().toJson(projectUsedLibs));
        }

        public void setLocalLibraries(List<LocalLibrary> localLibraries) {
            this.localLibraries.clear();
            this.localLibraries.addAll(localLibraries);
            notifyDataSetChanged();
        }

        public List<LocalLibrary> getLocalLibraries() {
            return localLibraries;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewItemLocalLibBinding binding;

            public ViewHolder(@NonNull ViewItemLocalLibBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
        private final List<LocalLibrary> filteredLocalLibraries = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var binding = ViewItemLocalLibSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final var binding = holder.binding;
            final var library = filteredLocalLibraries.get(position);

            binding.libraryName.setText(library.getName());
            binding.librarySize.setText(library.getSize());
            binding.libraryName.setSelected(true);

            binding.getRoot().setOnClickListener(v -> {
                binding.materialSwitch.performClick();
            });

            binding.materialSwitch.setOnClickListener(v -> {
                onItemClicked(binding, library.getName());
                adapter.notifyItemChanged(position);
            });

            binding.materialSwitch.setChecked(false);
            if (!notAssociatedWithProject) {
                for (Map<String, Object> libraryMap : projectUsedLibs) {
                    if (library.getName().equals(libraryMap.get("name").toString())) {
                        binding.materialSwitch.setChecked(true);
                    }
                }
            } else {
                binding.materialSwitch.setEnabled(false);
            }
        }

        @Override
        public int getItemCount() {
            return filteredLocalLibraries.isEmpty() ? 0 : filteredLocalLibraries.size();
        }

        private void onItemClicked(ViewItemLocalLibSearchBinding binding, String name) {
            HashMap<String, Object> localLibrary;
            if (!binding.materialSwitch.isChecked()) {
                // Remove the library from the list
                int indexToRemove = -1;
                for (int i = 0; i < projectUsedLibs.size(); i++) {
                    Map<String, Object> libraryMap = projectUsedLibs.get(i);
                    if (name.equals(libraryMap.get("name").toString())) {
                        indexToRemove = i;
                        break;
                    }
                }
                if (indexToRemove != -1) {
                    projectUsedLibs.remove(indexToRemove);
                }
            } else {
                // Add the library to the list
                // Here, we need to find the dependency string if it exists
                String dependency = null;
                for (Map<String, Object> libraryMap : projectUsedLibs) {
                    if (name.equals(libraryMap.get("name").toString())) {
                        dependency = (String) libraryMap.get("dependency");
                        break;
                    }
                }
                localLibrary = createLibraryMap(name, dependency);
                projectUsedLibs.add(localLibrary);
            }
            rewriteLocalLibFile(scId, new Gson().toJson(projectUsedLibs));
        }

        public void filter(List<LocalLibrary> localLibraries, String query) {
            filteredLocalLibraries.clear();
            if (query.isEmpty()) {
                filteredLocalLibraries.addAll(localLibraries);
            } else {
                for (LocalLibrary library : localLibraries) {
                    if (library.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredLocalLibraries.add(library);
                    }
                }
            }
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewItemLocalLibSearchBinding binding;

            public ViewHolder(@NonNull ViewItemLocalLibSearchBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
