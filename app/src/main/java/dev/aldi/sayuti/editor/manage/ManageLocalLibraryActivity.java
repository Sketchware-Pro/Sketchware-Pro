package dev.aldi.sayuti.editor.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a._A;
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
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();

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
        findViewById(2131232362).setVisibility(View.GONE);
        initToolbar();
        loadFiles();
    }

    private void loadFiles() {
        main_list.clear();
        project_used_libs.clear();
        lookup_list.clear();
        local_libs_path = FileUtil.getExternalStorageDir().concat("/.sketchware/libs/local_libs/");
        local_lib_file = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id.concat("/local_library"));
        if (!FileUtil.isExistFile(local_lib_file) || FileUtil.readFile(local_lib_file).equals("")) {
            FileUtil.writeFile(local_lib_file, "[]");
        }
        project_used_libs = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(local_libs_path, arrayList);
        arrayList.sort(String.CASE_INSENSITIVE_ORDER);

        for (String _name : arrayList) {
            if (FileUtil.isDirectory(_name)) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", Uri.parse(_name).getLastPathSegment());
                if (FileUtil.isExistFile(_name + File.separator + "alias") && !FileUtil.readFile(_name + File.separator + "alias").equals("")) {
                    hashMap.put("alias", FileUtil.readFile(_name + File.separator + "alias"));
                }
                main_list.add(hashMap);
            }
        }
        listview.setAdapter(new LibraryAdapter(main_list));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
    }

    private void ShowAliasEditorDialog(String LibraryPath) {

        final EditText _aliasEdittext = new EditText(this);
        _aliasEdittext.setSingleLine(true);
        _aliasEdittext.setHint("Alias For " + LibraryPath);
        _aliasEdittext.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        if (FileUtil.isExistFile(LibraryPath + File.separator + "alias") && !FileUtil.readFile(LibraryPath + File.separator + "alias").equals("")) {
            _aliasEdittext.setText(FileUtil.readFile(LibraryPath + File.separator + "alias"));
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins((int) getDip(8), (int) getDip(2), (int) getDip(8), (int) getDip(2));
        _aliasEdittext.setLayoutParams(lp);

        final AlertDialog.Builder _dialog = new AlertDialog.Builder(this);
        _dialog.setCancelable(false)
                .setView(_aliasEdittext)
                .setTitle("Set Library Alias")
                .setMessage("Add or remove custom alias to the selected library for quick recognizing.\nCurrent Selection: " + LibraryPath)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setNeutralButton("Remove", (dialog, which) -> {
                    if (FileUtil.isExistFile(local_libs_path.concat(LibraryPath).concat("/alias"))) {
                        FileUtil.deleteFile(local_libs_path.concat(LibraryPath).concat("/alias"));
                        bB.a(ManageLocalLibraryActivity.this, "Removed alias successfully", 0).show();
                        loadFiles();
                    }
                    dialog.dismiss();
                });

        AlertDialog dialog = _dialog.create();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            if (!_aliasEdittext.getText().toString().trim().equals("")) {
                FileUtil.writeFile(local_libs_path.concat(LibraryPath).concat("/alias"), _aliasEdittext.getText().toString());
                bB.a(ManageLocalLibraryActivity.this, "Alias Added successfully", 0).show();
                loadFiles();
                dialog.dismiss();
            } else {
                bB.a(ManageLocalLibraryActivity.this, "Type Something First! Duh", 0).show();
            }
        });
        dialog.show();
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

            final CheckBox library_selected = (CheckBox) convertView.findViewById(2131232370);
            final String libraryName = main_list.get(position).get("name").toString();
            String alias = "";
            if (main_list.get(position).containsKey("alias")) {
                alias = main_list.get(position).get("alias").toString();
            }
            library_selected.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            library_selected.setText(alias.equals("") ? libraryName : alias + " (" + libraryName + ")");
            library_selected.setSelected(true);
            library_selected.setSingleLine(true);

            library_selected.setOnCheckedChangeListener((buttonView, isChecked) -> {
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

                if (!isChecked) {
                    project_used_libs.remove(hashMap);
                } else {
                    for (int n = 0; n < project_used_libs.size(); n++) {
                        if (project_used_libs.get(n).get("name").toString().equals(libraryName)) {
                            project_used_libs.remove(hashMap);
                        }
                    }
                    project_used_libs.add(hashMap);
                }
                FileUtil.writeFile(local_lib_file, new Gson().toJson(project_used_libs));
            });

            lookup_list = new Gson().fromJson(FileUtil.readFile(local_lib_file), Helper.TYPE_MAP_LIST);
            for (int n = 0; n < lookup_list.size(); n++) {
                library_selected.setChecked((Boolean) libraryName.equals(lookup_list.get(n).get("name").toString()));
            }
            ((ImageView) convertView.findViewById(2131231132)).setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);
                popupMenu.getMenu().add(0, 0, 0, "Alias");
                popupMenu.getMenu().add(0, 1, 1, "Delete");
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case (0): { //Alias
                            ShowAliasEditorDialog(libraryName);
                            break;
                        }
                        case (1): { //Delete
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
}

