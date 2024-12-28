package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

import a.a.a.mB;

public class ManageLocalLibraryActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, Object>> lookupList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> projectUsedLibs = new ArrayList<>();
    private final ArrayList<File> localLibraryFiles = new ArrayList<>();

    private static String localLibsPath;
    private boolean notAssociatedWithProject;
    private String localLibFile;
    private BuildSettings buildSettings;
    private ManageLocallibrariesBinding binding;
    private LibraryAdapter adapter = new LibraryAdapter();
    private SearchAdapter searchAdapter = new SearchAdapter();

    static {
        localLibsPath = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ManageLocallibrariesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.downloadLibraryButton, new AddMarginOnApplyWindowInsetsListener(WindowInsetsCompat.Type.navigationBars(), WindowInsetsCompat.CONSUMED));

        if (getIntent().hasExtra("sc_id")) {
            String scId = Objects.requireNonNull(getIntent().getStringExtra("sc_id"));
            buildSettings = new BuildSettings(scId);
            notAssociatedWithProject = scId.equals("system");
            localLibFile = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(scId.concat("/local_library"));
        }

        binding.librariesList.setAdapter(adapter);
        binding.searchList.setAdapter(searchAdapter);

        binding.searchBar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        binding.downloadLibraryButton.setOnClickListener(v -> {
            if (getSupportFragmentManager().findFragmentByTag("library_downloader_dialog") != null) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putBoolean("notAssociatedWithProject", notAssociatedWithProject);
            bundle.putSerializable("buildSettings", buildSettings);
            bundle.putString("localLibFile", localLibFile);

            LibraryDownloaderDialogFragment fragment = new LibraryDownloaderDialogFragment();
            fragment.setArguments(bundle);
            fragment.setListener(this::loadLibraries);
            fragment.show(getSupportFragmentManager(), "library_downloader_dialog");
        });

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString().trim();
                searchAdapter.filter(adapter.getLibraryFiles(), value);
            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (newText.toString().isEmpty()) {
                    loadLibraries();
                }
            }
        });

        loadLibraries();
    }

    @Override
    public void onBackPressed() {
        if (binding.searchView.isShowing()) {
            binding.searchView.hide();
        } else {
            super.onBackPressed();
        }
    }

    private void loadLibraries() {
        projectUsedLibs.clear();
        lookupList.clear();

        if (!notAssociatedWithProject) {
            String fileContent;
            if (!FileUtil.isExistFile(localLibFile) || (fileContent = FileUtil.readFile(localLibFile)).isEmpty()) {
                FileUtil.writeFile(localLibFile, "[]");
            } else {
                projectUsedLibs = new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
            }
        }

        FileUtil.listDirAsFile(localLibsPath, localLibraryFiles);
        localLibraryFiles.sort(new LocalLibrariesComparator());

        List<File> libraryFiles = new LinkedList<>();
        for (File libraryFile : localLibraryFiles) {
            if (libraryFile.isDirectory()) {
                libraryFiles.add(libraryFile);
            }
        }

        adapter.setLibraryFiles(libraryFiles);
        binding.noContentLayout.setVisibility(libraryFiles.isEmpty() ? View.VISIBLE : View.GONE);
    }

    public static HashMap<String, Object> createLibraryMap(String name, String dependency) {
        String configPath = localLibsPath + name + "/config";
        String resPath = localLibsPath + name + "/res";
        String jarPath = localLibsPath + name + "/classes.jar";
        String dexPath = localLibsPath + name + "/classes.dex";
        String manifestPath = localLibsPath + name + "/AndroidManifest.xml";
        String pgRulesPath = localLibsPath + name + "/proguard.txt";
        String assetsPath = localLibsPath + name + "/assets";

        HashMap<String, Object> localLibrary = new HashMap<>();
        localLibrary.put("name", name);
        if (dependency != null) {
            localLibrary.put("dependency", dependency);
        }
        if (FileUtil.isExistFile(configPath)) {
            localLibrary.put("packageName", FileUtil.readFile(configPath));
        }
        if (FileUtil.isExistFile(resPath)) {
            localLibrary.put("resPath", resPath);
        }
        if (FileUtil.isExistFile(jarPath)) {
            localLibrary.put("jarPath", jarPath);
        }
        if (FileUtil.isExistFile(dexPath)) {
            localLibrary.put("dexPath", dexPath);
        }
        if (FileUtil.isExistFile(manifestPath)) {
            localLibrary.put("manifestPath", manifestPath);
        }
        if (FileUtil.isExistFile(pgRulesPath)) {
            localLibrary.put("pgRulesPath", pgRulesPath);
        }
        if (FileUtil.isExistFile(assetsPath)) {
            localLibrary.put("assetsPath", assetsPath);
        }
        return localLibrary;
    }

    public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
        private final List<File> libraryFiles = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var binding = ViewItemLocalLibBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            var binding = holder.binding;

            final File libraryFile = libraryFiles.get(position);
            final String librarySize = FileUtil.formatFileSize(libraryFile.length());
            binding.libraryName.setText(libraryFile.getName());
            binding.librarySize.setText(librarySize);
            binding.libraryName.setSelected(true);

            binding.card.setOnClickListener(v -> binding.checkbox.performClick());
            binding.checkbox.setOnClickListener(v -> onItemClicked(binding));

            binding.checkbox.setChecked(false);
            if (!notAssociatedWithProject) {
                lookupList = new Gson().fromJson(FileUtil.readFile(localLibFile), Helper.TYPE_MAP_LIST);
                for (HashMap<String, Object> localLibrary : lookupList) {
                    if (binding.libraryName.getText().toString().equals(Objects.requireNonNull(localLibrary.get("name")).toString())) {
                        binding.checkbox.setChecked(true);
                    }
                }
            } else {
                binding.checkbox.setEnabled(false);
            }

            binding.imgDelete.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(localLibsPath.concat(binding.libraryName.getText().toString()));
                    SketchwareUtil.toast("Deleted successfully");
                    loadLibraries();
                    return true;
                });
                popupMenu.show();
            });
        }

        @Override
        public int getItemCount() {
            return libraryFiles.isEmpty() ? 0 : libraryFiles.size();
        }

        private void onItemClicked(ViewItemLocalLibBinding binding) {
            String name = binding.libraryName.getText().toString();

            HashMap<String, Object> localLibrary;
            if (!binding.checkbox.isChecked()) {
                // Remove the library from the list
                int indexToRemove = -1;
                for (int i = 0; i < projectUsedLibs.size(); i++) {
                    HashMap<String, Object> lib = projectUsedLibs.get(i);
                    if (name.equals(lib.get("name"))) {
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
                for (HashMap<String, Object> lib : lookupList) {
                    if (name.equals(lib.get("name"))) {
                        dependency = (String) lib.get("dependency");
                        break;
                    }
                }
                localLibrary = createLibraryMap(name, dependency);
                projectUsedLibs.add(localLibrary);
            }
            FileUtil.writeFile(localLibFile, new Gson().toJson(projectUsedLibs));
        }

        public void setLibraryFiles(List<File> libraryFiles) {
            this.libraryFiles.clear();
            this.libraryFiles.addAll(libraryFiles);
            notifyDataSetChanged();
        }

        public List<File> getLibraryFiles() {
            return libraryFiles;
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
        private final List<File> filteredLibraryFiles = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var binding = ViewItemLocalLibSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            var binding = holder.binding;

            final File libraryFile = filteredLibraryFiles.get(position);
            final String librarySize = FileUtil.formatFileSize(libraryFile.length());
            binding.libraryName.setText(libraryFile.getName());
            binding.librarySize.setText(librarySize);
            binding.libraryName.setSelected(true);

            binding.getRoot().setOnClickListener(v -> binding.checkbox.performClick());
            binding.checkbox.setOnClickListener(v -> onItemClicked(binding));

            binding.checkbox.setChecked(false);
            if (!notAssociatedWithProject) {
                lookupList = new Gson().fromJson(FileUtil.readFile(localLibFile), Helper.TYPE_MAP_LIST);
                for (HashMap<String, Object> localLibrary : lookupList) {
                    if (binding.libraryName.getText().toString().equals(Objects.requireNonNull(localLibrary.get("name")).toString())) {
                        binding.checkbox.setChecked(true);
                    }
                }
            } else {
                binding.checkbox.setEnabled(false);
            }

            binding.imgDelete.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(localLibsPath.concat(binding.libraryName.getText().toString()));
                    SketchwareUtil.toast("Deleted successfully");
                    loadLibraries();
                    return true;
                });
                popupMenu.show();
            });
        }

        @Override
        public int getItemCount() {
            return filteredLibraryFiles.isEmpty() ? 0 : filteredLibraryFiles.size();
        }

        private void onItemClicked(ViewItemLocalLibSearchBinding binding) {
            String name = binding.libraryName.getText().toString();

            HashMap<String, Object> localLibrary;
            if (!binding.checkbox.isChecked()) {
                // Remove the library from the list
                int indexToRemove = -1;
                for (int i = 0; i < projectUsedLibs.size(); i++) {
                    HashMap<String, Object> lib = projectUsedLibs.get(i);
                    if (name.equals(lib.get("name"))) {
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
                for (HashMap<String, Object> lib : lookupList) {
                    if (name.equals(lib.get("name"))) {
                        dependency = (String) lib.get("dependency");
                        break;
                    }
                }
                localLibrary = createLibraryMap(name, dependency);
                projectUsedLibs.add(localLibrary);
            }
            FileUtil.writeFile(localLibFile, new Gson().toJson(projectUsedLibs));
        }

        public void filter(List<File> libraryFiles, String query) {
            filteredLibraryFiles.clear();
            if (query.isEmpty()) {
                filteredLibraryFiles.addAll(libraryFiles);
            } else {
                for (File file : libraryFiles) {
                    if (file.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredLibraryFiles.add(file);
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
