package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.pranav.dependency.resolver.DependencyResolver;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener {

    private boolean notAssociatedWithProject = false;
    private RecyclerView recyclerView;
    private String local_lib_file = "";
    private static String local_libs_path = "";
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();

    private void initButtons() {
        ExtendedFloatingActionButton downloadLibraryButton = findViewById(R.id.downloadLibraryButton);
        downloadLibraryButton.setOnClickListener(this);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onClick(View v) {
        var view = getLayoutInflater().inflate(R.layout.library_downloader_dialog, null);

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(view)
                .create();
        EditText editText = view.findViewById(R.id.ed_input);
        MaterialButton downloadButton = view.findViewById(R.id.btn_download);
        TextView text = view.findViewById(R.id.tv_progress);
        downloadButton.setOnClickListener(v1 -> {
            String url = editText.getText().toString();
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
            downloadButton.setText("Downloading...");

            var group = parts[0];
            var artifact = parts[1];
            var version = parts[2];
            var resolver = new DependencyResolver(group, artifact, version);
            var handler = new Handler(Looper.getMainLooper());
            Executors.newSingleThreadExecutor().execute(() -> resolver.resolveDependency(new DependencyResolver.DependencyResolverCallback() {
                @Override
                public void invalidPackaging(@NonNull String dep) {
                    handler.post(() -> text.setText("Invalid packaging for dependency " + dep));
                    downloadButton.setEnabled(true);
                    downloadButton.setText("Download");
                }

                @Override
                public void dexing(@NonNull String dep) {
                    handler.post(() -> text.setText("Dexing dependency " + dep));
                }

                @Override
                public void dexingFailed(@NonNull String dependency, @NonNull Exception e) {
                    handler.post(() -> {
                        dialog.dismiss();
                        SketchwareUtil.showAnErrorOccurredDialog(ManageLocalLibraryActivity.this,
                                "Dexing dependency '" + dependency + "' failed: " + Log.getStackTraceString(e));
                    });
                }

                @Override
                public void log(@NonNull String msg) {
                    handler.post(() -> text.setText(msg));
                }

                @Override
                public void downloading(@NonNull String dep) {
                    handler.post(() -> text.setText("Downloading dependency " + dep));
                }

                @Override
                public void startResolving(@NonNull String dep) {
                    handler.post(() -> text.setText("Resolving dependency " + dep));
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
                        downloadButton.setEnabled(true);
                        downloadButton.setText("Download");
                        text.setText("Dependency " + dep + " not found");
                    });
                }

                @Override
                public void onDependencyResolveFailed(@NonNull Exception e) {
                    downloadButton.setEnabled(true);
                    downloadButton.setText("Download");
                    handler.post(() -> text.setText(e.getMessage()));
                }

                @Override
                public void onDependencyResolved(@NonNull String dep) {
                    handler.post(() -> text.setText("Dependency " + dep + " resolved"));
                }
            }));
        });
        dialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_locallibraries);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        collapsingToolbar.setStatusBarScrimColor(SurfaceColors.SURFACE_2.getColor(this));
        collapsingToolbar.setContentScrimColor(SurfaceColors.SURFACE_2.getColor(this));

        recyclerView = findViewById(R.id.main_content);
        initButtons();
        if (getIntent().hasExtra("sc_id")) {
            String sc_id = Objects.requireNonNull(getIntent().getStringExtra("sc_id"));
            notAssociatedWithProject = sc_id.equals("system");
            local_lib_file = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/local_library"));
        }
        local_libs_path = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
        loadFiles();
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
        Collections.sort(arrayList, String.CASE_INSENSITIVE_ORDER);

        List<String> localLibraryNames = new LinkedList<>();
        for (String filename : arrayList) {
            if (FileUtil.isDirectory(filename)) {
                localLibraryNames.add(Uri.parse(filename).getLastPathSegment());
            }
        }
        LinearLayout noContentLayout = findViewById(R.id.noContentLayout);
        if (localLibraryNames.isEmpty()) {
            noContentLayout.setVisibility(View.VISIBLE);
        } else {
            noContentLayout.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LibraryAdapter(localLibraryNames));
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
            View view = getLayoutInflater().inflate(R.layout.view_item_local_lib, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final String libraryName = localLibraries.get(position);
            holder.enabled.setText(libraryName);

            holder.enabled.setOnClickListener(v -> {
                String name = holder.enabled.getText().toString();
                HashMap<String, Object> localLibrary = createLibraryMap(name);

                if (!holder.enabled.isChecked()) {
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

            holder.enabled.setChecked(false);
            if (!notAssociatedWithProject) {
                lookup_list = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
                for (HashMap<String, Object> localLibrary : lookup_list) {
                    if (holder.enabled.getText().toString().equals(Objects.requireNonNull(localLibrary.get("name")).toString())) {
                        holder.enabled.setChecked(true);
                    }
                }
            } else {
                holder.enabled.setEnabled(false);
            }

            holder.deleteIcon.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(local_libs_path.concat(holder.enabled.getText().toString()));
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
            CheckBox enabled;
            ImageView deleteIcon; // assuming you have this in your XML

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                enabled = itemView.findViewById(R.id.checkbox_content);
                deleteIcon = itemView.findViewById(R.id.img_delete);
            }
        }
    }

}
