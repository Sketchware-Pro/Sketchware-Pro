package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.bB;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.library.LibraryDownloader;
import mod.hey.studios.util.Helper;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener, LibraryDownloader.OnCompleteListener {

    private final ArrayList<HashMap<String, Object>> main_list = new ArrayList<>();
    public String sc_id = "";
    public Toolbar toolbar;
    private ListView listview;
    private String local_lib_file = "";
    private String local_libs_path = "";
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();
    SwipeRefreshLayout refreshList;

    @SuppressLint({"ResourceType", "SetTextI18n"})
    public void initToolbar() {
        ((TextView) findViewById(2131232458)).setText("External library manager");
        ImageView back_icon = findViewById(2131232457);
        Helper.applyRippleToToolbarView(back_icon);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        ImageView import_library_icon = findViewById(2131232459);
        import_library_icon.setPadding(getDip(2), getDip(2), getDip(2), getDip(2));
        import_library_icon.setImageResource(2131166368);
        import_library_icon.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(import_library_icon);
        import_library_icon.setOnClickListener(this);
    }

    private int getDip(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Dexer")
                .setMessage("Would you like to use Dx or D8 to dex the library?\nD8 supports Java 8, whereas Dx does not. Limitation: D8 only works on Android 8 and above.")
                .setPositiveButton("D8", (dialogInterface, i) -> new LibraryDownloader(ManageLocalLibraryActivity.this, true).showDialog(ManageLocalLibraryActivity.this))
                .setNegativeButton("Dx", (dialogInterface, i) -> new LibraryDownloader(ManageLocalLibraryActivity.this, false).showDialog(ManageLocalLibraryActivity.this))
                .setNeutralButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onComplete() {
        loadFiles();
    }

    @SuppressLint("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427786);
        if (getIntent().hasExtra("sc_id")) {
            sc_id = getIntent().getStringExtra("sc_id");
        }
        listview = findViewById(2131232364);
        refreshList = new SwipeRefreshLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        refreshList.setLayoutParams(lp);
        ViewGroup parentLayout = ((ViewGroup) listview.getParent());
        parentLayout.removeView(listview);
        refreshList.addView(listview);
        parentLayout.addView(refreshList);
        findViewById(2131232362).setVisibility(View.GONE);
        initToolbar();
        refreshList.setRefreshing(true);
        loadFiles();

        refreshList.setOnRefreshListener(() -> {
            listview.setVisibility(View.GONE);
            loadFiles();
        });
    }

    private void loadFiles() {
        new Thread() {
            @Override
            public void run() {
                main_list.clear();
                project_used_libs.clear();
                local_libs_path = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
                local_lib_file = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/local_library"));
                if (!sc_id.equals("system")) {
                    try {
                        project_used_libs = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
                        if (project_used_libs == null) project_used_libs = new ArrayList<>();
                    } catch (Exception e) {
                        FileUtil.writeFile(local_lib_file, new Gson().toJson(new ArrayList<>()));
                    }
                }
                ArrayList<String> localLibsList = new ArrayList<>();
                FileUtil.listDir(local_libs_path, localLibsList);
                localLibsList.sort(String.CASE_INSENSITIVE_ORDER);

                for (String _name : localLibsList) {
                    if (FileUtil.isDirectory(_name)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", Uri.parse(_name).getLastPathSegment());
                        main_list.add(hashMap);
                    }
                }

                if (listview == null) return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listview.setVisibility(View.VISIBLE);
                        listview.setAdapter(new LibraryAdapter(new ArrayList<>(main_list)));
                        refreshList.setRefreshing(false);
                    }
                });      
            }
        }.start();
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

        @SuppressLint({"ResourceType", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = (convertView != null) ? convertView : getLayoutInflater().inflate(2131427824, null);

            final CheckBox library_selected = convertView.findViewById(2131232370);
            final String libraryName = main_list.get(position).get("name").toString();
          
            library_selected.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            library_selected.setText(libraryName);
            library_selected.setSelected(true); //For Marquee
            library_selected.setSingleLine(true);
            library_selected.setChecked(libContainsInProject(project_used_libs, libraryName));
            if (sc_id.equals("system")) {
                library_selected.setButtonDrawable(null);
            } else {
                library_selected.setOnClickListener(v -> {
                    boolean isChecked = library_selected.isChecked();
                    if (!isChecked) {
                        for (int n = 0; n < project_used_libs.size(); n++) {
                            if (project_used_libs.get(n).get("name").toString().equals(libraryName)) {
                                project_used_libs.remove(n);
                            }
                        }
                    } else {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name", libraryName);

                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/config"))) {
                            hashMap.put("packageName", FileUtil.readFile(local_libs_path.concat(libraryName).concat("/config")));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/res"))) {
                            hashMap.put("resPath", local_libs_path.concat(libraryName).concat("/res"));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/classes.jar"))) {
                            hashMap.put("jarPath", local_libs_path.concat(libraryName).concat("/classes.jar"));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/classes.dex"))) {
                            hashMap.put("dexPath", local_libs_path.concat(libraryName).concat("/classes.dex"));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/AndroidManifest.xml"))) {
                            hashMap.put("manifestPath", local_libs_path.concat(libraryName).concat("/AndroidManifest.xml"));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/proguard.txt"))) {
                            hashMap.put("pgRulesPath", local_libs_path.concat(libraryName).concat("/proguard.txt"));
                        }
                        if (FileUtil.isExistFile(local_libs_path.concat(libraryName).concat("/assets"))) {
                            hashMap.put("assetsPath", local_libs_path.concat(libraryName).concat("/assets"));
                        }
                        project_used_libs.add(hashMap);
                    }
                    FileUtil.writeFile(local_lib_file, new Gson().toJson(project_used_libs));
                });
            }

            convertView.findViewById(2131231132).setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(0, 0, 0, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case (0): { //Delete
                            FileUtil.deleteFile(local_libs_path.concat(libraryName));
                            bB.a(ManageLocalLibraryActivity.this, "Deleted successfully", 0).show();
                            loadFiles();
                            break;
                        }
                    }
                    return true;
                });
                popupMenu.show();
            });
            return convertView;
        }
    }

    private boolean libContainsInProject(ArrayList<HashMap<String, Object>> projectLibsList, String libName) {
        for (int i = 0; i < projectLibsList.size(); i++) {
            if (libName.equals(projectLibsList.get(i).get("name").toString())) return true;
        }
        return false;
    }
}

