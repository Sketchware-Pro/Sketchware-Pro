package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
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
import com.sketchware.remod.databinding.ManageLocallibrariesBinding;
import com.sketchware.remod.databinding.ViewItemLocalLibBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.util.Helper;
import mod.jbk.util.AddMarginOnApplyWindowInsetsListener;

public class ManageLocalLibraryActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean notAssociatedWithProject = false;
    private String local_lib_file = "";
    private static String local_libs_path = "";
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();
    private BuildSettings buildSettings;

    private ManageLocallibrariesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ManageLocallibrariesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.downloadLibraryButton,
                new AddMarginOnApplyWindowInsetsListener(WindowInsetsCompat.Type.navigationBars(), WindowInsetsCompat.CONSUMED));

        initButtons();
        if (getIntent().hasExtra("sc_id")) {
            String sc_id = Objects.requireNonNull(getIntent().getStringExtra("sc_id"));
            buildSettings = new BuildSettings(sc_id);
            notAssociatedWithProject = sc_id.equals("system");
            local_lib_file = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/local_library"));
        }
        local_libs_path = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
        loadFiles();
    }

    private void initButtons() {
        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.downloadLibraryButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onClick(View v) {

        LibraryDownloaderDialogFragment dialogFragment = new LibraryDownloaderDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAssociatedWithProject", notAssociatedWithProject);
        bundle.putSerializable("buildSettings", buildSettings);
        bundle.putString("local_lib_file", local_lib_file);
        dialogFragment.setArguments(bundle);
        if (getSupportFragmentManager().findFragmentByTag("library_downloader_dialog") != null)
            return;
        dialogFragment.setListener(this::loadFiles);
        dialogFragment.show(getSupportFragmentManager(), "library_downloader_dialog");
    }

    private void loadFiles() {
        project_used_libs.clear();
        lookup_list.clear();
        if (!notAssociatedWithProject) {
            String fileContent;
            if (!FileUtil.isExistFile(local_lib_file) || (fileContent = FileUtil.readFile(local_lib_file)).isEmpty()) {
                FileUtil.writeFile(local_lib_file, "[]");
            } else {
                project_used_libs = new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
            }
        }
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(local_libs_path, arrayList);
        arrayList.sort(String.CASE_INSENSITIVE_ORDER);

        List<String> localLibraryNames = new LinkedList<>();
        for (String filename : arrayList) {
            if (FileUtil.isDirectory(filename)) {
                localLibraryNames.add(Uri.parse(filename).getLastPathSegment());
            }
        }
        if (localLibraryNames.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
        binding.librariesList.setLayoutManager(new LinearLayoutManager(this));
        binding.librariesList.setAdapter(new LibraryAdapter(localLibraryNames));
    }

    public static HashMap<String, Object> createLibraryMap(String name, String dependency) {
        String configPath = local_libs_path + name + "/config";
        String resPath = local_libs_path + name + "/res";
        String jarPath = local_libs_path + name + "/classes.jar";
        String dexPath = local_libs_path + name + "/classes.dex";
        String manifestPath = local_libs_path + name + "/AndroidManifest.xml";
        String pgRulesPath = local_libs_path + name + "/proguard.txt";
        String assetsPath = local_libs_path + name + "/assets";

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

        private final List<String> localLibraries;

        public LibraryAdapter(List<String> localLibraries) {
            this.localLibraries = localLibraries;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var listBinding = ViewItemLocalLibBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            var layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            listBinding.getRoot().setLayoutParams(layoutParams);
            return new ViewHolder(listBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            var binding = holder.listBinding;

            final String libraryName = localLibraries.get(position);
            binding.checkboxContent.setText(libraryName);

            binding.checkboxContent.setOnClickListener(v -> {
                String name = binding.checkboxContent.getText().toString();

                HashMap<String, Object> localLibrary;
                if (!binding.checkboxContent.isChecked()) {
                    // Remove the library from the list
                    int indexToRemove = -1;
                    for (int i = 0; i < project_used_libs.size(); i++) {
                        HashMap<String, Object> lib = project_used_libs.get(i);
                        if (name.equals(lib.get("name"))) {
                            indexToRemove = i;
                            break;
                        }
                    }
                    if (indexToRemove != -1) {
                        project_used_libs.remove(indexToRemove);
                    }
                } else {
                    // Add the library to the list
                    // Here, we need to find the dependency string if it exists
                    String dependency = null;
                    for (HashMap<String, Object> lib : lookup_list) {
                        if (name.equals(lib.get("name"))) {
                            dependency = (String) lib.get("dependency");
                            break;
                        }
                    }
                    localLibrary = createLibraryMap(name, dependency);
                    project_used_libs.add(localLibrary);
                }
                FileUtil.writeFile(local_lib_file, new Gson().toJson(project_used_libs));
            });


            binding.checkboxContent.setChecked(false);
            if (!notAssociatedWithProject) {
                lookup_list = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
                for (HashMap<String, Object> localLibrary : lookup_list) {
                    if (binding.checkboxContent.getText().toString().equals(Objects.requireNonNull(localLibrary.get("name")).toString())) {
                        binding.checkboxContent.setChecked(true);
                    }
                }
            } else {
                binding.checkboxContent.setEnabled(false);
            }

            binding.imgDelete.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(local_libs_path.concat(binding.checkboxContent.getText().toString()));
                    SketchwareUtil.toast("Deleted successfully");
                    loadFiles();
                    return true;
                });
                popupMenu.show();
            });
        }

        @Override
        public int getItemCount() {
            return localLibraries.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewItemLocalLibBinding listBinding;

            public ViewHolder(@NonNull ViewItemLocalLibBinding listBinding) {
                super(listBinding.getRoot());
                this.listBinding = listBinding;
            }
        }
    }
}