package mod.agus.jcoderz.editor.manage.resource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import a.a.a.bB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class ManageResourceActivity extends Activity implements View.OnClickListener {

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
        if (FileUtil.isExistFile(fpu.getPathResource(numProj))) {
            temp = fpu.getPathResource(numProj);
            handleAdapter(temp);
            handleFab(temp);
            return;
        }
        FileUtil.makeDir(fpu.getPathResource(numProj));
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/anim");
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/drawable");
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/drawable-xhdpi");
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/layout");
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/menu");
        FileUtil.makeDir(fpu.getPathResource(numProj) + "/values");
        checkDir();
    }

    public void handleAdapter(String str) {
        ArrayList<String> resourceFile = frc.getResourceFile(str);
        Collections.sort(resourceFile, String.CASE_INSENSITIVE_ORDER);
        adapter = new CustomAdapter(resourceFile);
        gridView.setAdapter(adapter);
    }

    private boolean isInMainDirectory() {
        return temp.equals(fpu.getPathResource(numProj));
    }

    public void handleFab(String str) {
        if (isInMainDirectory()) {
            if (load_file != null) {
                load_file.setVisibility(View.GONE);
            }
        } else if (load_file != null) {
            load_file.setVisibility(View.VISIBLE);
        }
    }

    public void initToolbar() {
        ((TextView) findViewById(Resources.id.tx_toolbar_title)).setText("Resource Manager");
        ImageView back = findViewById(Resources.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        load_file = findViewById(Resources.id.ig_toolbar_load_file);
        Helper.applyRippleToToolbarView(load_file);
        load_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    public void onBackPressed() {
        try {
            temp = temp.substring(0, temp.lastIndexOf("/"));
            if (temp.contains("resource")) {
                handleAdapter(temp);
                handleFab(temp);
                return;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view != fab) {
            return;
        }
        createNewDialog(isInMainDirectory());
    }

    private void createNewDialog(final boolean isFolder) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.dialog_create_new_file_layout, null);
        final EditText editText = (EditText) inflate.findViewById(Resources.id.dialog_edittext_name);
        TextView cancel = (TextView) inflate.findViewById(Resources.id.dialog_text_cancel);
        TextView save = (TextView) inflate.findViewById(Resources.id.dialog_text_save);
        ((RadioGroup) inflate.findViewById(Resources.id.dialog_radio_filetype)).setVisibility(View.GONE);
        ((TextView) inflate.findViewById(Resources.id.dialog_create_new_file_layoutTitle)).setText(isFolder ? "Create a new folder" : "Create a new file");
        if (!isFolder) editText.setText(".xml");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path;
                if (editText.getText().toString().isEmpty()) {
                    bB.b(getApplicationContext(), "Invalid name", 0).show();
                    return;
                }
                String name = editText.getText().toString();
                if (isFolder) {
                    path = fpu.getPathResource(numProj) + "/" + name;
                } else {
                    path = new File(temp + File.separator + name).getAbsolutePath();
                }
                if (FileUtil.isExistFile(path)) {
                    bB.b(getApplicationContext(), "File exists already", 0).show();
                    return;
                }
                if (isFolder) {
                    FileUtil.makeDir(path);
                } else {
                    FileUtil.writeFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                }
                handleAdapter(temp);
                bB.a(getApplicationContext(), "Created file successfully", 0).show();
                create.dismiss();
            }
        });
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SketchwareUtil.hideKeyboard();
            }
        });
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        if (!isFolder) editText.setSelection(0);
        SketchwareUtil.showKeyboard();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_file);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        Helper.fixFileprovider();
        gridView = findViewById(Resources.id.list_file);
        gridView.setNumColumns(1);
        fab = findViewById(Resources.id.fab_plus);
        tv = findViewById(Resources.id.text_info);
        tv.setVisibility(View.GONE);
        frc = new FileResConfig(numProj);
        fpu = new FilePathUtil();
        setupDialog();
        checkDir();
        fab.setOnClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                    PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, view);
                    popupMenu.getMenu().add("Delete");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            showDeleteDialog(position);
                            return true;
                        }
                    });
                    popupMenu.show();
                }
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                    temp = frc.listFileResource.get(position);
                    handleAdapter(temp);
                    handleFab(temp);
                    return;
                }
                goEdit(position);
            }
        });
        initToolbar();
    }

    public void setupDialog() {
        properties = new DialogProperties();
        properties.selection_mode = 1;
        properties.selection_type = 2;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.extensions = null;
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a resource file");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    try {
                        FileUtil.copyDirectory(new File(path), new File(temp + File.separator + Uri.parse(path).getLastPathSegment()));
                    } catch (IOException e) {
                        bB.b(getApplicationContext(), "Couldn't import resource! [" + e.getMessage() + "]", 0).show();
                    }
                }
                handleAdapter(temp);
                handleFab(temp);
            }
        });
    }

    public void showDialog(final String path) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.dialog_input_layout, null);
        final EditText editText = (EditText) inflate.findViewById(Resources.id.edittext_change_name);
        try {
            editText.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (IndexOutOfBoundsException e) {
            editText.setText(path);
        }
        ((TextView) inflate.findViewById(Resources.id.text_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        ((TextView) inflate.findViewById(Resources.id.text_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + editText.getText().toString())) {
                        bB.a(getApplicationContext(), "Renamed successfully", 0).show();
                    } else {
                        bB.b(getApplicationContext(), "Renaming failed", 0).show();
                    }
                    handleAdapter(temp);
                    handleFab(temp);
                }
                create.dismiss();
            }
        });
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SketchwareUtil.hideKeyboard();
            }
        });
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    public void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(Uri.fromFile(new File(adapter.getItem(position))).getLastPathSegment())
                .setMessage("Are you sure you want to delete this "
                        + (FileUtil.isDirectory(adapter.getItem(position)) ? "folder" : "file") + "? "
                        + "This action cannot be reverted!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtil.deleteFile(frc.listFileResource.get(position));
                        handleAdapter(temp);
                        bB.a(getApplicationContext(), "Deleted", 0).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    public void goEdit(int position) {
        if (frc.listFileResource.get(position).endsWith("xml")) {
            Intent intent = new Intent();
            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), mod.hey.studios.activity.SrcCodeEditor.class);
            } else {
                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            }
            intent.putExtra("title", Uri.parse(frc.listFileResource.get(position)).getLastPathSegment());
            intent.putExtra("content", frc.listFileResource.get(position));
            intent.putExtra("xml", "");
            startActivity(intent);
            return;
        }
        SketchwareUtil.toast("Only XML files can be edited");
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

        public int getImageRes(int position) {
            return FileUtil.isDirectory(getItem(position)) ? 2131165754 : 2131165623;
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(Resources.layout.manage_java_item_hs, null);
            }
            TextView name = (TextView) convertView.findViewById(Resources.id.title);
            ImageView icon = (ImageView) convertView.findViewById(Resources.id.icon);
            ImageView more = (ImageView) convertView.findViewById(Resources.id.more);
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
                    PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, v);
                    popupMenu.inflate(Resources.menu.popup_menu_double);
                    popupMenu.getMenu().getItem(0).setVisible(false);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getTitle().toString()) {
                                case "Edit with...":
                                    if (frc.listFileResource.get(position).endsWith("xml")) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(new File(frc.listFileResource.get(position))), "text/plain");
                                        startActivity(intent);
                                    } else {
                                        bB.a(getApplicationContext(), "Only XML files can be edited", 0).show();
                                    }
                                    break;

                                case "Edit":
                                    goEdit(position);
                                    break;

                                case "Delete":
                                    FileUtil.deleteFile(frc.listFileResource.get(position));
                                    handleAdapter(temp);
                                    break;

                                case "Rename":
                                    showDialog(frc.listFileResource.get(position));
                                    break;

                                default:
                                    return false;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
            return convertView;
        }
    }
}