package dev.aldi.sayuti.editor.manage;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.pranav.dependency.resolver.DependencyResolver;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener {

    private boolean notAssociatedWithProject = false;
    private ListView listview;
    private String local_lib_file = "";
    private static String local_libs_path = "";
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();

    private void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Local library Manager");
        ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(back_icon);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        ImageView import_library_icon = findViewById(R.id.ig_toolbar_load_file);
        import_library_icon.setPadding((int) getDip(2), (int) getDip(2), (int) getDip(2), (int) getDip(2));
        import_library_icon.setImageResource(R.drawable.download_80px);
        import_library_icon.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(import_library_icon);
        import_library_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        var view = getLayoutInflater().inflate(R.layout.library_downloader_dialog, null);

        var dialog = new MaterialAlertDialogBuilder(this)
                .setView(view)
                .create();
        EditText editText = view.findViewById(R.id.ed_input);
        var linear = view.findViewById(R.id.btn_download);
        TextView text = view.findViewById(R.id.tv_progress);
        linear.setOnClickListener(v1 -> {
            linear.setVisibility(View.GONE);
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
            var group = parts[0];
            var artifact = parts[1];
            var version = parts[2];
            var resolver = new DependencyResolver(group, artifact, version);
            var handler = new Handler(Looper.getMainLooper());
            Executors.newSingleThreadExecutor().execute(() -> resolver.resolveDependency(new DependencyResolver.DependencyResolverCallback() {
                @Override
                public void invalidPackaging(@NonNull String dep) {
                    handler.post(() -> text.setText("Invalid packaging for dependency " + dep));
                }

                @Override
                public void dexing(@NonNull String dep) {
                    handler.post(() -> text.setText("Dexing dependency " + dep));
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
                        linear.setVisibility(View.VISIBLE);

                        dialog.dismiss();
                        if (!notAssociatedWithProject) {
                            log("Enabling downloaded dependencies");
                            var fileContent = FileUtil.readFile(local_lib_file);
                            var enabledLibs = new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
                            enabledLibs.addAll(dependencies.stream().map(ManageLocalLibraryActivity::createLibraryMap).collect(Collectors.toList()));
                            FileUtil.writeFile(local_lib_file, new Gson().toJson(enabledLibs));
                        }
                        loadFiles();
                    });
                }

                @Override
                public void onDependencyNotFound(@NonNull String dep) {
                    handler.post(() -> {
                        linear.setVisibility(View.VISIBLE);
                        text.setText("Dependency " + dep + " not found");
                    });
                }

                @Override
                public void onDependencyResolveFailed(@NonNull Exception e) {
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
        setContentView(R.layout.manage_permission);

        listview = findViewById(R.id.main_content);
        findViewById(R.id.search_perm).setVisibility(View.GONE);
        initToolbar();

        if (getIntent().hasExtra("sc_id")) {
            String sc_id = getIntent().getStringExtra("sc_id");
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
        listview.setAdapter(new LibraryAdapter(localLibraryNames));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
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

    public class LibraryAdapter extends BaseAdapter {

        private final List<String> localLibraries;

        public LibraryAdapter(List<String> localLibraries) {
            this.localLibraries = localLibraries;
        }

        @Override
        public String getItem(int position) {
            return localLibraries.get(position);
        }

        @Override
        public int getCount() {
            return localLibraries.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_item_local_lib, parent, false);
            }

            final CheckBox enabled = convertView.findViewById(R.id.checkbox_content);
            enabled.setText(localLibraries.get(position));
            enabled.setOnClickListener(v -> {
                String name = enabled.getText().toString();
                HashMap<String, Object> localLibrary = createLibraryMap(name);

                if (!enabled.isChecked()) {
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
                        if (usedLibrary.get("name").toString().equals(name)) {
                            project_used_libs.remove(usedLibrary);
                            break;
                        }
                    }
                    project_used_libs.add(localLibrary);
                }
                FileUtil.writeFile(local_lib_file, new Gson().toJson(project_used_libs));
            });

            enabled.setChecked(false);
            if (!notAssociatedWithProject) {
                lookup_list = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
                for (HashMap<String, Object> localLibrary : lookup_list) {
                    if (enabled.getText().toString().equals(localLibrary.get("name").toString())) {
                        enabled.setChecked(true);
                    }
                }
            } else {
                enabled.setEnabled(false);
            }

            convertView.findViewById(R.id.img_delete).setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(local_libs_path.concat(enabled.getText().toString()));
                    SketchwareUtil.toast("Deleted successfully");
                    loadFiles();
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }
    }
}
