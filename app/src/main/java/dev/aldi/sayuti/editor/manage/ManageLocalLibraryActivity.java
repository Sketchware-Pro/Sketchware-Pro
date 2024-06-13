package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.sketchware.remod.databinding.LibraryDownloaderDialogBinding;
import com.sketchware.remod.databinding.ManageLocallibrariesBinding;
import com.sketchware.remod.databinding.ViewItemLocalLibBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.util.AddMarginOnApplyWindowInsetsListener;
import mod.pranav.dependency.resolver.DependencyResolver;

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
        var dialogBinding = LibraryDownloaderDialogBinding.inflate(getLayoutInflater());
        View view = dialogBinding.getRoot();

        var downloadButton = dialogBinding.downloadButton;
        var dependencyInput = dialogBinding.dependencyInput;

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(view)
                .setCancelable(true)
                .create();
        downloadButton.setOnClickListener(v1 -> {
            String url = Objects.requireNonNull(dependencyInput.getText()).toString();
            if (url.isEmpty()) {
                SketchwareUtil.toastError("Please enter a dependency");
                return;
            }
            var parts = url.split(":");
            if (parts.length != 3) {
                SketchwareUtil.toastError("Invalid dependency format");
                return;
            }

            downloadButton.setEnabled(false);
            dependencyInput.setEnabled(false);
            downloadButton.setText("Downloading...");
            dialog.setCancelable(false);

            var group = parts[0];
            var artifact = parts[1];
            var version = parts[2];
            var resolver = new DependencyResolver(group, artifact, version, dialogBinding.skipSubDependenciesCheckBox.isChecked(), buildSettings);
            var handler = new Handler(Looper.getMainLooper());

            class SetTextRunnable implements Runnable {
                private final String message;

                SetTextRunnable(String message) {
                    this.message = message;
                }

                @Override
                public void run() {
                    dialogBinding.progressText.setText(message);
                }
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                BuiltInLibraries.maybeExtractAndroidJar(progress -> handler.post(new SetTextRunnable(progress)));
                BuiltInLibraries.maybeExtractCoreLambdaStubsJar();

                resolver.resolveDependency(new DependencyResolver.DependencyResolverCallback() {
                    @Override
                    public void invalidPackaging(@NonNull String dep) {
                        handler.post(() -> {
                            new SetTextRunnable("Invalid packaging for dependency \"" + dep + "\"");
                            downloadButton.setText("Download");
                            downloadButton.setEnabled(true);
                            dependencyInput.setEnabled(true);
                        });
                    }

                    @Override
                    public void dexing(@NonNull String dep) {
                        handler.post(new SetTextRunnable("Dexing dependency \"" + dep + "\"..."));
                    }

                    @Override
                    public void dexingFailed(@NonNull String dependency, @NonNull Exception e) {
                        handler.post(() -> {
                            dialog.dismiss();
                            SketchwareUtil.showAnErrorOccurredDialog(ManageLocalLibraryActivity.this,
                                    "Dexing dependency \"" + dependency + "\" failed: " + Log.getStackTraceString(e));
                        });
                    }

                    @Override
                    public void log(@NonNull String msg) {
                        handler.post(new SetTextRunnable(msg));
                    }

                    @Override
                    public void downloading(@NonNull String dep) {
                        handler.post(new SetTextRunnable("Downloading \"" + dep + "\"..."));
                    }

                    @Override
                    public void startResolving(@NonNull String dep) {
                        handler.post(new SetTextRunnable("Searching for dependency \"" + dep + "\"..."));
                    }

                    @Override
                    public void onTaskCompleted(@NonNull List<String> dependencies) {
                        handler.post(() -> {
                            dialog.dismiss();
                            if (!notAssociatedWithProject) {
                                log("Enabling downloaded dependencies");
                                var fileContent = FileUtil.readFile(local_lib_file);
                                var enabledLibs = new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
                                enabledLibs.addAll(dependencies.stream().map(ManageLocalLibraryActivity::createLibraryMap).collect(Collectors.toUnmodifiableList()));
                                FileUtil.writeFile(local_lib_file, new Gson().toJson(enabledLibs));
                            }
                            loadFiles();
                        });
                    }

                    @Override
                    public void onDependencyNotFound(@NonNull String dep) {
                        handler.post(() -> {
                            downloadButton.setText("Download");
                            downloadButton.setEnabled(true);
                            dependencyInput.setEnabled(true);
                            dialogBinding.progressText.setText("Dependency \"" + dep + "\" not found");
                        });
                    }

                    @Override
                    public void onDependencyResolveFailed(@NonNull Exception e) {
                        handler.post(new SetTextRunnable(e.getMessage()));
                        downloadButton.setText("Download");
                        downloadButton.setEnabled(true);
                        dependencyInput.setEnabled(true);
                    }

                    @Override
                    public void onDependencyResolved(@NonNull String dep) {
                        handler.post(new SetTextRunnable("Dependency \"" + dep + "\" resolved"));
                    }
                });
            });
        });
        dialog.show();
    }

    private void loadFiles() {
        project_used_libs.clear();
        lookup_list.clear();
        if (!notAssociatedWithProject) {
            String fileContent;
            if (!FileUtil.isExistFile(local_lib_file) || (fileContent = FileUtil.readFile(local_lib_file)).equals("")) {
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

    public static HashMap<String, Object> createLibraryMap(String name) {
        String configPath = local_libs_path + name + "/config";
        String resPath = local_libs_path + name + "/res";
        String jarPath = local_libs_path + name + "/classes.jar";
        String dexPath = local_libs_path + name + "/classes.dex";
        String manifestPath = local_libs_path + name + "/AndroidManifest.xml";
        String pgRulesPath = local_libs_path + name + "/proguard.txt";
        String assetsPath = local_libs_path + name + "/assets";

        HashMap<String, Object> localLibrary = new HashMap<>();
        localLibrary.put("name", name);
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
                HashMap<String, Object> localLibrary = createLibraryMap(name);

                if (!binding.checkboxContent.isChecked()) {
                    int i = -1;
                    for (int j = 0; j < project_used_libs.size(); j++) {
                        HashMap<String, Object> nLocalLibrary = project_used_libs.get(j);
                        if (name.equals(nLocalLibrary.get("name"))) {
                            i = j;
                            break;
                        }
                    }
                    project_used_libs.remove(i);
                } else {
                    for (HashMap<String, Object> usedLibrary : project_used_libs) {
                        if (Objects.requireNonNull(usedLibrary.get("name")).toString().equals(name)) {
                            project_used_libs.remove(usedLibrary);
                            break;
                        }
                    }
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

        class ViewHolder extends RecyclerView.ViewHolder {
            private final ViewItemLocalLibBinding listBinding;

            public ViewHolder(@NonNull ViewItemLocalLibBinding listBinding) {
                super(listBinding.getRoot());
                this.listBinding = listBinding;
            }
        }
    }
}
