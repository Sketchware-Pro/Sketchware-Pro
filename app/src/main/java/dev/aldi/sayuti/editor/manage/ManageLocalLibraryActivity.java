package dev.aldi.sayuti.editor.manage;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
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

import a.a.a.bB;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.library.LibraryDownloader;
import mod.hey.studios.util.Helper;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener, LibraryDownloader.OnCompleteListener {

    private final ArrayList<HashMap<String, Object>> main_list = new ArrayList<>();
    private String sc_id = "";
    private ListView listview;
    private String local_lib_file = "";
    private String local_libs_path = "";
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private int n = 0;
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
                .setPositiveButton("D8", (dialog, which) -> new LibraryDownloader(ManageLocalLibraryActivity.this,
                        true).showDialog(ManageLocalLibraryActivity.this))
                .setNegativeButton("Dx", (dialog, which) -> new LibraryDownloader(ManageLocalLibraryActivity.this,
                        false).showDialog(ManageLocalLibraryActivity.this))
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
            sc_id = getIntent().getStringExtra("sc_id");
        }
        local_libs_path = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
        local_lib_file = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/local_library"));
        loadFiles();
    }

    private void loadFiles() {
        main_list.clear();
        project_used_libs.clear();
        lookup_list.clear();
        String fileContent;
        if (!FileUtil.isExistFile(local_lib_file) || (fileContent = FileUtil.readFile(local_lib_file)).equals("")) {
            FileUtil.writeFile(local_lib_file, "[]");
        } else {
            project_used_libs = new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
        }
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(local_libs_path, arrayList);
        //noinspection Java8ListSort
        Collections.sort(arrayList, String.CASE_INSENSITIVE_ORDER);
        n = 0;
        while (n < arrayList.size()) {
            if (FileUtil.isDirectory(arrayList.get(n))) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", Uri.parse(arrayList.get(n)).getLastPathSegment());
                main_list.add(hashMap);
            }
            n++;
        }
        listview.setAdapter(new LibraryAdapter(main_list));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
    }

    public class LibraryAdapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public LibraryAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_item_local_lib, null);
            }
            final CheckBox checkBox = convertView.findViewById(R.id.checkbox_content);
            checkBox.setText((main_list.get(position)).get("name").toString());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", checkBox.getText().toString());
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/config"))) {
                    hashMap.put("packageName", FileUtil.readFile(local_libs_path.concat(checkBox.getText().toString()).concat("/config")));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/res"))) {
                    hashMap.put("resPath", local_libs_path.concat(checkBox.getText().toString()).concat("/res"));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/classes.jar"))) {
                    hashMap.put("jarPath", local_libs_path.concat(checkBox.getText().toString()).concat("/classes.jar"));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/classes.dex"))) {
                    hashMap.put("dexPath", local_libs_path.concat(checkBox.getText().toString()).concat("/classes.dex"));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/AndroidManifest.xml"))) {
                    hashMap.put("manifestPath", local_libs_path.concat(checkBox.getText().toString()).concat("/AndroidManifest.xml"));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/proguard.txt"))) {
                    hashMap.put("pgRulesPath", local_libs_path.concat(checkBox.getText().toString()).concat("/proguard.txt"));
                }
                if (FileUtil.isExistFile(local_libs_path.concat(checkBox.getText().toString()).concat("/assets"))) {
                    hashMap.put("assetsPath", local_libs_path.concat(checkBox.getText().toString()).concat("/assets"));
                }
                if (!isChecked) {
                    project_used_libs.remove(hashMap);
                } else {
                    n = 0;
                    while (n < project_used_libs.size()) {
                        if (project_used_libs.get(n).get("name").toString().equals(checkBox.getText().toString())) {
                            project_used_libs.remove(hashMap);
                        }
                        n = n + 1;
                    }
                    project_used_libs.add(hashMap);
                }
                FileUtil.writeFile(local_lib_file, new Gson().toJson(project_used_libs));
            });
            lookup_list = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
            n = 0;
            while (n < lookup_list.size()) {
                checkBox.setChecked(false);
                if (checkBox.getText().toString().equals(lookup_list.get(n).get("name").toString())) {
                    checkBox.setChecked(true);
                }
                n = n + 1;
            }
            convertView.findViewById(R.id.img_delete).setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(0, 0, 0, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    FileUtil.deleteFile(local_libs_path.concat(checkBox.getText().toString()));
                    bB.a(ManageLocalLibraryActivity.this, "Deleted successfully", 0).show();
                    loadFiles();
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }
    }
}
