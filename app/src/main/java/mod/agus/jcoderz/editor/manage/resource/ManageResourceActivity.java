package mod.agus.jcoderz.editor.manage.resource;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

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

@SuppressLint("SetTextI18n")
public class ManageResourceActivity extends Activity implements View.OnClickListener {

    private CustomAdapter adapter;
    private FilePickerDialog dialog;
    private FloatingActionButton fab;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private GridView gridView;
    private String numProj;
    private String temp;
    private ImageView load_file;

    private void checkDir() {
        if (FileUtil.isExistFile(fpu.getPathResource(numProj))) {
            temp = fpu.getPathResource(numProj);
            handleAdapter(temp);
            handleFab();
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

    private void handleAdapter(String str) {
        ArrayList<String> resourceFile = frc.getResourceFile(str);
        Collections.sort(resourceFile, String.CASE_INSENSITIVE_ORDER);
        adapter = new CustomAdapter(resourceFile);
        gridView.setAdapter(adapter);
    }

    private boolean isInMainDirectory() {
        return temp.equals(fpu.getPathResource(numProj));
    }

    private void handleFab() {
        if (isInMainDirectory()) {
            if (load_file != null) {
                load_file.setVisibility(View.GONE);
            }
        } else if (load_file != null) {
            load_file.setVisibility(View.VISIBLE);
        }
    }

    private void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Resource Manager");
        ImageView back = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        load_file = findViewById(R.id.ig_toolbar_load_file);
        Helper.applyRippleToToolbarView(load_file);
        load_file.setOnClickListener(view -> dialog.show());
    }

    @Override
    public void onBackPressed() {
        try {
            temp = temp.substring(0, temp.lastIndexOf("/"));
            if (temp.contains("resource")) {
                handleAdapter(temp);
                handleFab();
                return;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v != fab) {
            return;
        }
        createNewDialog(isInMainDirectory());
    }

    private void createNewDialog(final boolean isFolder) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);
        final EditText editText = inflate.findViewById(R.id.dialog_edittext_name);
        TextView cancel = inflate.findViewById(R.id.dialog_text_cancel);
        TextView save = inflate.findViewById(R.id.dialog_text_save);
        inflate.findViewById(R.id.dialog_radio_filetype).setVisibility(View.GONE);
        ((TextView) inflate.findViewById(R.id.dialog_create_new_file_layoutTitle)).setText(isFolder ? "Create a new folder" : "Create a new file");
        if (!isFolder) editText.setText(".xml");
        cancel.setOnClickListener(v -> create.dismiss());
        save.setOnClickListener(v -> {
            String path;
            if (editText.getText().toString().isEmpty()) {
                bB.b(getApplicationContext(), "Invalid name", bB.TOAST_NORMAL).show();
                return;
            }
            String name = editText.getText().toString();
            if (isFolder) {
                path = fpu.getPathResource(numProj) + "/" + name;
            } else {
                path = new File(temp + File.separator + name).getAbsolutePath();
            }
            if (FileUtil.isExistFile(path)) {
                bB.b(getApplicationContext(), "File exists already", bB.TOAST_NORMAL).show();
                return;
            }
            if (isFolder) {
                FileUtil.makeDir(path);
            } else {
                FileUtil.writeFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            }
            handleAdapter(temp);
            bB.a(getApplicationContext(), "Created file successfully", bB.TOAST_NORMAL).show();
            create.dismiss();
        });
        create.setOnDismissListener(dialogInterface -> SketchwareUtil.hideKeyboard());
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        if (!isFolder) editText.setSelection(0);
        SketchwareUtil.showKeyboard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_file);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        Helper.fixFileprovider();
        gridView = findViewById(R.id.list_file);
        gridView.setNumColumns(1);
        fab = findViewById(R.id.fab_plus);
        TextView tv = findViewById(R.id.text_info);
        tv.setVisibility(View.GONE);
        frc = new FileResConfig(numProj);
        fpu = new FilePathUtil();
        setupDialog();
        checkDir();
        fab.setOnClickListener(this);
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, view);
                popupMenu.getMenu().add("Delete");
                popupMenu.setOnMenuItemClickListener(item -> {
                    showDeleteDialog(position);
                    return true;
                });
                popupMenu.show();
            }
            return true;
        });
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (FileUtil.isDirectory(frc.listFileResource.get(position))) {
                temp = frc.listFileResource.get(position);
                handleAdapter(temp);
                handleFab();
                return;
            }
            goEdit(position);
        });
        initToolbar();
    }

    private void setupDialog() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = 1;
        properties.selection_type = 2;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.extensions = null;
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a resource file");
        dialog.setDialogSelectionListener(selections -> {
            for (String path : selections) {
                try {
                    FileUtil.copyDirectory(new File(path), new File(temp + File.separator + Uri.parse(path).getLastPathSegment()));
                } catch (IOException e) {
                    bB.b(getApplicationContext(), "Couldn't import resource! [" + e.getMessage() + "]", bB.TOAST_NORMAL).show();
                }
            }
            handleAdapter(temp);
            handleFab();
        });
    }

    private void showDialog(final String path) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog_input_layout, null);
        final EditText editText = inflate.findViewById(R.id.edittext_change_name);
        try {
            editText.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (IndexOutOfBoundsException e) {
            editText.setText(path);
        }
        inflate.findViewById(R.id.text_cancel).setOnClickListener(v -> create.dismiss());
        inflate.findViewById(R.id.text_save).setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + editText.getText().toString())) {
                    bB.a(getApplicationContext(), "Renamed successfully", bB.TOAST_NORMAL).show();
                } else {
                    bB.b(getApplicationContext(), "Renaming failed", bB.TOAST_NORMAL).show();
                }
                handleAdapter(temp);
                handleFab();
            }
            create.dismiss();
        });
        create.setOnDismissListener(dialogInterface -> SketchwareUtil.hideKeyboard());
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    private void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(Uri.fromFile(new File(adapter.getItem(position))).getLastPathSegment())
                .setMessage("Are you sure you want to delete this "
                        + (FileUtil.isDirectory(adapter.getItem(position)) ? "folder" : "file") + "? "
                        + "This action cannot be reverted!")
                .setPositiveButton("Delete", (dialog, which) -> {
                    FileUtil.deleteFile(frc.listFileResource.get(position));
                    handleAdapter(temp);
                    bB.a(getApplicationContext(), "Deleted", bB.TOAST_NORMAL).show();
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    private void goEdit(int position) {
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
        bB.a(getApplicationContext(), "Only XML files can be edited", bB.TOAST_NORMAL).show();
    }

    private class CustomAdapter extends BaseAdapter {

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
                convertView = getLayoutInflater().inflate(R.layout.manage_java_item_hs, null);
            }
            TextView name = convertView.findViewById(R.id.title);
            ImageView icon = convertView.findViewById(R.id.icon);
            ImageView more = convertView.findViewById(R.id.more);
            more.setVisibility(FileUtil.isDirectory(getItem(position)) ? View.GONE : View.VISIBLE);
            name.setText(Uri.parse(getItem(position)).getLastPathSegment());

            if (FileUtil.isDirectory(getItem(position))) {
                icon.setImageResource(R.drawable.ic_folder_48dp);
            } else {
                try {
                    if (FileUtil.isImageFile(getItem(position))) {
                        Glide.with(ManageResourceActivity.this).load(new File(getItem(position))).into(icon);
                    } else {
                        icon.setImageResource(R.drawable.file_48_blue);
                    }
                } catch (Exception ignored) {
                    icon.setImageResource(R.drawable.file_48_blue);
                }
            }

            more.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, v);
                popupMenu.inflate(R.menu.popup_menu_double);
                popupMenu.getMenu().getItem(0).setVisible(false);
                popupMenu.setOnMenuItemClickListener(item -> {
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
                });
                popupMenu.show();
            });
            return convertView;
        }
    }
}
