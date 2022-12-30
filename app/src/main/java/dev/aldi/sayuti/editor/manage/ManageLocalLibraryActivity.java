package dev.aldi.sayuti.editor.manage;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.library.LibraryDownloader;
import mod.hey.studios.util.Helper;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener, LibraryDownloader.OnCompleteListener {

    private boolean notAssociatedWithProject = false;
    private ListView listview;
    private String local_lib_file = "";
    private String local_libs_path = "";
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
        new AlertDialog.Builder(this)
                .setTitle("Dexer")
                .setMessage("Would you like to use Dx or D8 to dex the library?\n" +
                        "D8 supports Java 8, whereas Dx does not. Limitation: D8 only works on Android 8 and above.")
                .setPositiveButton("D8", (dialog, which) -> new LibraryDownloader(this,
                        true).showDialog(this))
                .setNegativeButton("Dx", (dialog, which) -> new LibraryDownloader(this,
                        false).showDialog(this))
                .setNeutralButton("Cancel", null)
                .show();
    }

    @Override
    public void onComplete() {
        loadFiles();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_permission);

        listview = findViewById(R.id.main_content);
        findViewById(R.id.managepermissionLinearLayout1).setVisibility(View.GONE);
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
        //noinspection Java8ListSort
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
