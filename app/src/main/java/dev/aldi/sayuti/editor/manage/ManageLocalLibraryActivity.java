package dev.aldi.sayuti.editor.manage;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.library.LibraryDownloader;
import mod.hey.studios.util.Helper;

public class ManageLocalLibraryActivity extends Activity implements View.OnClickListener, LibraryDownloader.OnCompleteListener {

    private final ArrayList<HashMap<String, Object>> main_list = new ArrayList<>();
    private final String local_libs_path = FileUtil.getExternalStorageDir() + "/.sketchware/libs/local_libs/";
    private ListView listview;
    private String configurationFilePath = "";
    private ArrayList<HashMap<String, Object>> lookup_list = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> project_used_libs = new ArrayList<>();

    private void initToolbar() {
        ImageView back = findViewById(Resources.id.ig_toolbar_back);
        TextView title = findViewById(Resources.id.tx_toolbar_title);
        ImageView import_library_icon = findViewById(Resources.id.ig_toolbar_load_file);

        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        title.setText("Local library Manager");
        import_library_icon.setPadding(
                (int) getDip(2),
                (int) getDip(2),
                (int) getDip(2),
                (int) getDip(2)
        );
        import_library_icon.setImageResource(Resources.drawable.download_80px);
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
                .setPositiveButton("D8", (dialogInterface, i) ->
                        new LibraryDownloader(ManageLocalLibraryActivity.this, true)
                                .showDialog(ManageLocalLibraryActivity.this))
                .setNegativeButton("Dx", (dialogInterface, i) ->
                        new LibraryDownloader(ManageLocalLibraryActivity.this, false)
                                .showDialog(ManageLocalLibraryActivity.this))
                .setNeutralButton(Resources.string.common_word_cancel, null)
                .show();
    }

    @Override
    public void onComplete() {
        loadFiles();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_permission);

        SwipeRefreshLayout refreshLayout = new SwipeRefreshLayout(this);
        LinearLayout searchViewContainer = findViewById(Resources.id.managepermissionLinearLayout1);
        searchViewContainer.setVisibility(View.GONE);
        listview = findViewById(Resources.id.main_content);

        ViewGroup mainContent = (ViewGroup) searchViewContainer.getParent();
        ViewGroup root = (ViewGroup) mainContent.getParent();
        root.removeView(mainContent);
        refreshLayout.addView(mainContent);
        root.addView(refreshLayout);

        refreshLayout.setOnRefreshListener(() -> {
            loadFiles();
            refreshLayout.setRefreshing(false);
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listview.getChildCount() == 0) ?
                        0 : listview.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
        });

        if (getIntent().hasExtra("sc_id")) {
            String sc_id = getIntent().getStringExtra("sc_id");
            configurationFilePath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/local_library";
            initToolbar();
            loadFiles();
        } else {
            finish();
        }
    }

    private void loadFiles() {
        main_list.clear();
        project_used_libs.clear();
        lookup_list.clear();
        if (!FileUtil.isExistFile(configurationFilePath) || FileUtil.readFile(configurationFilePath).equals("")) {
            FileUtil.writeFile(configurationFilePath, "[]");
        } else {
            project_used_libs = new Gson().fromJson(FileUtil.readFile(configurationFilePath), Helper.TYPE_MAP_LIST);
        }

        lookup_list = new Gson().fromJson(FileUtil.readFile(configurationFilePath), Helper.TYPE_MAP_LIST);

        ArrayList<String> files = new ArrayList<>();
        FileUtil.listDir(local_libs_path, files);
        Collections.sort(files, String.CASE_INSENSITIVE_ORDER);

        for (String file : files) {
            if (FileUtil.isDirectory(file)) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", Uri.parse(file).getLastPathSegment());
                main_list.add(map);
            }
        }

        listview.setAdapter(new LibraryAdapter(main_list));
        ((BaseAdapter) listview.getAdapter()).notifyDataSetChanged();
    }

    private class LibraryAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

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
                convertView = getLayoutInflater().inflate(Resources.layout.view_item_local_lib, null);
            }
            final CheckBox checkBox = convertView.findViewById(Resources.id.checkbox_content);
            final ImageView delete = convertView.findViewById(Resources.id.img_delete);

            HashMap<String, Object> currentLibrary = main_list.get(position);

            Object name = currentLibrary.get("name");
            if (name instanceof String) {
                checkBox.setText((String) name);
            }
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", checkBox.getText().toString());
                String libraryPath = local_libs_path + checkBox.getText().toString();
                if (FileUtil.isExistFile(libraryPath + "/config")) {
                    hashMap.put("packageName", FileUtil.readFile(libraryPath + "/config"));
                }
                if (FileUtil.isExistFile(libraryPath + "/res")) {
                    hashMap.put("resPath", libraryPath + "/res");
                }
                if (FileUtil.isExistFile(libraryPath + "/classes.jar")) {
                    hashMap.put("jarPath", libraryPath + "/classes.jar");
                }
                if (FileUtil.isExistFile(libraryPath + "/classes.dex")) {
                    hashMap.put("dexPath", libraryPath.concat("/classes.dex"));
                }
                if (FileUtil.isExistFile(libraryPath.concat("/AndroidManifest.xml"))) {
                    hashMap.put("manifestPath", libraryPath.concat("/AndroidManifest.xml"));
                }
                if (FileUtil.isExistFile(libraryPath.concat("/proguard.txt"))) {
                    hashMap.put("pgRulesPath", libraryPath.concat("/proguard.txt"));
                }
                if (FileUtil.isExistFile(libraryPath.concat("/assets"))) {
                    hashMap.put("assetsPath", libraryPath.concat("/assets"));
                }
                if (!isChecked) {
                    project_used_libs.remove(hashMap);
                } else {
                    int n = 0;
                    while (n < project_used_libs.size()) {
                        Object usedLibraryName = project_used_libs.get(n).get("name");
                        if (usedLibraryName instanceof String) {
                            if (checkBox.getText().toString().equals(usedLibraryName)) {
                                project_used_libs.remove(hashMap);
                            }
                        }
                        n++;
                    }
                    project_used_libs.add(hashMap);
                }
                FileUtil.writeFile(configurationFilePath, new Gson().toJson(project_used_libs));
            });

            for (HashMap<String, Object> library : lookup_list) {
                Object usedLibraryName = library.get("name");
                if (usedLibraryName instanceof String) {
                    if (checkBox.getText().toString().equals(usedLibraryName)) {
	                checkBox.setChecked(true);
                    }
                }
            }

            delete.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageLocalLibraryActivity.this, v);

                Menu menu = popupMenu.getMenu();
                menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Rename");
                menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Delete");

                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getTitle().toString()) {
                        case "Delete":
                            checkBox.setChecked(false);
                            FileUtil.deleteFile(local_libs_path.concat(checkBox.getText().toString()));
                            loadFiles();
                            break;

                        case "Rename":
                            final AlertDialog dialog = new AlertDialog.Builder(ManageLocalLibraryActivity.this).create();

                            final View root = getLayoutInflater().inflate(Resources.layout.dialog_input_layout, null);
                            final LinearLayout title = root.findViewById(Resources.id.dialoginputlayoutLinearLayout1);
                            final TextInputLayout tilFilename = root.findViewById(Resources.id.dialoginputlayoutLinearLayout2);
                            final EditText filename = root.findViewById(Resources.id.edittext_change_name);

                            final View titleChildAt1 = title.getChildAt(1);
                            if (titleChildAt1 instanceof TextView) {
                                final TextView titleTextView = (TextView) titleChildAt1;
                                titleTextView.setText("Rename local library");
                            }

                            tilFilename.setHint("New local library name");
                            filename.setText(checkBox.getText().toString());
                            root.findViewById(Resources.id.text_cancel)
                                    .setOnClickListener(Helper.getDialogDismissListener(dialog));
                            root.findViewById(Resources.id.text_save)
                                    .setOnClickListener(view -> {
                                        checkBox.setChecked(false);
                                        File input = new File(local_libs_path.concat(checkBox.getText().toString()));
                                        File output = new File(local_libs_path.concat(filename.getText().toString()));
                                        if (!input.renameTo(output)) {
                                            SketchwareUtil.toastError("Failed to rename library");
                                        }
                                        SketchwareUtil.toast("NOTE: Removed library from used local libraries");
                                        dialog.dismiss();
                                    });
                            dialog.setView(root);
                            dialog.show();

                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            filename.requestFocus();
                            break;

                        default:
                            return false;
                    }

                    return true;
                });
                popupMenu.show();
            });

            return convertView;
        }
    }
}
