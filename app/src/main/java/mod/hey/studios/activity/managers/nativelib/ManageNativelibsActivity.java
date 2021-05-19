package mod.hey.studios.activity.managers.nativelib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import a.a.a.bB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ManageNativelibsActivity extends Activity implements View.OnClickListener {

    public CustomAdapter adapter;
    public FilePickerDialog dialog;
    public FloatingActionButton fab;
    public FilePathUtil fpu;
    public FileResConfig frc;
    public GridView gridView;
    public Toolbar k;
    public String numProj;
    public DialogProperties properties;
    public String temp;
    public TextView tv;
    private ImageView load_file;

    public void checkDir() {
        if (FileUtil.isExistFile(fpu.getPathNativelibs(numProj))) {
            temp = fpu.getPathNativelibs(numProj);
            handleAdapter(temp);
            handleFab(temp);
            return;
        }
        FileUtil.makeDir(fpu.getPathNativelibs(numProj));
        FileUtil.makeDir(fpu.getPathNativelibs(numProj) + "/armeabi");
        FileUtil.makeDir(fpu.getPathNativelibs(numProj) + "/armeabi-v7a");
        FileUtil.makeDir(fpu.getPathNativelibs(numProj) + "/arm64-v8a");
        FileUtil.makeDir(fpu.getPathNativelibs(numProj) + "/x86");
        checkDir();
    }

    public void handleAdapter(String str) {
        ArrayList<String> nativelibsFile = frc.getNativelibsFile(str);
        Collections.sort(nativelibsFile, String.CASE_INSENSITIVE_ORDER);
        adapter = new CustomAdapter(nativelibsFile);
        gridView.setAdapter(adapter);
    }

    private boolean isInMainDirectory() {
        return temp.equals(fpu.getPathNativelibs(numProj));
    }

    public void handleFab(String str) {
        if (isInMainDirectory()) {
            if (load_file != null) {
                load_file.setVisibility(View.GONE);
            }
            fab.setVisibility(View.VISIBLE);
            return;
        }
        if (load_file != null) {
            load_file.setVisibility(View.VISIBLE);
        }
        fab.setVisibility(View.GONE);
    }

    public void initToolbar() {
        ((TextView) findViewById(2131232458)).setText("Native library Manager");
        ImageView back = findViewById(2131232457);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        load_file = findViewById(2131232459);
        Helper.applyRippleToToolbarView(load_file);
        load_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void onBackPressed() {
        temp = temp.substring(0, temp.lastIndexOf("/"));
        if (temp.contains("native_libs")) {
            handleAdapter(temp);
            handleFab(temp);
            return;
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
            createNewDialog();
        }
    }

    private void createNewDialog() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427800, null);
        final EditText editText = inflate.findViewById(2131232463);
        inflate.findViewById(2131232460).setVisibility(View.GONE);
        ((TextView) inflate.findViewById(2131232509)).setText("Create a new folder");
        inflate.findViewById(2131232464).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        inflate.findViewById(2131232465).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path;
                if (editText.getText().toString().isEmpty()) {
                    SketchwareUtil.toastError("Invalid folder name");
                    return;
                }
                String name = editText.getText().toString();
                path = fpu.getPathNativelibs(numProj) + "/" + name;
                if (FileUtil.isExistFile(path)) {
                    SketchwareUtil.toastError("Folder already exists");
                    return;
                }
                FileUtil.makeDir(path);
                handleAdapter(temp);
                SketchwareUtil.toast("Created folder successfully");
                dialog.dismiss();
            }
        });
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SketchwareUtil.hideKeyboard();
            }
        });
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427785);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        gridView = findViewById(2131232359);
        gridView.setNumColumns(1);
        fab = findViewById(2131232360);
        tv = findViewById(2131232361);
        tv.setVisibility(View.GONE);
        frc = new FileResConfig(numProj);
        fpu = new FilePathUtil();
        setupDialog();
        checkDir();
        fab.setOnClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (FileUtil.isDirectory(frc.listFileNativeLibs.get(position))) {
                    PopupMenu menu = new PopupMenu(ManageNativelibsActivity.this, view);
                    menu.getMenu().add("Delete");
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            FileUtil.deleteFile(frc.listFileNativeLibs.get(position));
                            handleAdapter(temp);
                            SketchwareUtil.toast("Deleted");
                            return true;
                        }
                    });
                    menu.show();
                }
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FileUtil.isDirectory(frc.listFileNativeLibs.get(position))) {
                    temp = frc.listFileNativeLibs.get(position);
                    handleAdapter(temp);
                    handleFab(temp);
                }
            }
        });
        initToolbar();
    }

    public void setupDialog() {
        File externalStorageDir = new File(FileUtil.getExternalStorageDir());
        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = externalStorageDir;
        properties.error_dir = externalStorageDir;
        properties.offset = externalStorageDir;
        properties.extensions = new String[]{"so"};
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a native library (.so)");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    try {
                        FileUtil.copyDirectory(new File(path), new File(temp + File.separator + Uri.parse(path).getLastPathSegment()));
                    } catch (IOException e) {
                        bB.a(getApplicationContext(), "Couldn't import library! [" + e.getMessage() + "]", 0);
                    }
                }
                handleAdapter(temp);
                handleFab(temp);
            }
        });
    }

    public void showDialog(final String path) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427790, null);
        final EditText name = inflate.findViewById(2131232375);
        try {
            name.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (Exception ignored) {
        }
        inflate.findViewById(2131232376).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        inflate.findViewById(2131232377).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty()) {
                    if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + name.getText().toString())) {
                        SketchwareUtil.toast("Renamed successfully");
                    } else {
                        SketchwareUtil.toastError("Renaming failed");
                    }
                    handleAdapter(temp);
                    handleFab(temp);
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SketchwareUtil.hideKeyboard();
            }
        });
        alertDialog.setView(inflate);
        alertDialog.show();
        name.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    public class CustomAdapter extends BaseAdapter {

        private final ArrayList<String> data;

        public CustomAdapter(ArrayList<String> arrayList) {
            data = arrayList;
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427823, null);
            }
            TextView name = convertView.findViewById(2131231837);
            ImageView icon = convertView.findViewById(2131231090);
            ImageView more = convertView.findViewById(2131232627);
            if (FileUtil.isDirectory(getItem(position))) {
                more.setVisibility(View.GONE);
            } else {
                more.setVisibility(View.VISIBLE);
            }
            name.setText(Uri.parse(getItem(position)).getLastPathSegment());
            icon.setImageResource(getImageRes(position));
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(ManageNativelibsActivity.this, v);
                    menu.inflate(2131492893);
                    menu.getMenu().getItem(0).setVisible(false);
                    menu.getMenu().getItem(1).setVisible(false);
                    menu.getMenu().getItem(2).setVisible(false);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String title = item.getTitle().toString();
                            switch (title) {
                                case "Delete":
                                    FileUtil.deleteFile(frc.listFileNativeLibs.get(position));
                                    handleAdapter(temp);
                                    break;

                                case "Rename":
                                    showDialog(frc.listFileNativeLibs.get(position));
                                    break;

                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                    menu.show();
                }
            });
            return convertView;
        }

        private int getImageRes(int position) {
            return FileUtil.isDirectory(getItem(position)) ? 2131165754 : 2131165623;
        }
    }
}
