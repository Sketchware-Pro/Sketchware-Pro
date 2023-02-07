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
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditorLegacy;
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
        //noinspection Java8ListSort
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
        if (v == fab) {
            createNewDialog(isInMainDirectory());
        }
    }

    private void createNewDialog(final boolean isFolder) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);
        final TextView title = view.findViewById(R.id.dialog_create_new_file_layoutTitle);
        final View fileType = view.findViewById(R.id.dialog_radio_filetype);
        final EditText filename = view.findViewById(R.id.dialog_edittext_name);
        final TextView cancel = view.findViewById(R.id.dialog_text_cancel);
        final TextView save = view.findViewById(R.id.dialog_text_save);

        title.setText(isFolder ? "Create a new folder" : "Create a new file");
        fileType.setVisibility(View.GONE);
        if (!isFolder) {
            filename.setText(".xml");
        }
        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(v -> {
            if (filename.getText().toString().isEmpty()) {
                SketchwareUtil.toastError("Invalid name");
                return;
            }

            String name = filename.getText().toString();
            String path;
            if (isFolder) {
                path = fpu.getPathResource(numProj) + "/" + name;
            } else {
                path = new File(temp + File.separator + name).getAbsolutePath();
            }

            if (FileUtil.isExistFile(path)) {
                SketchwareUtil.toastError("File exists already");
                return;
            }
            if (isFolder) {
                FileUtil.makeDir(path);
            } else {
                FileUtil.writeFile(path, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            }
            handleAdapter(temp);
            SketchwareUtil.toast("Created file successfully");
            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        filename.requestFocus();

        if (!isFolder) {
            filename.setSelection(0);
        }
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
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenu().add("Delete");
                popupMenu.setOnMenuItemClickListener(item -> {
                    showDeleteDialog(position);
                    return true;
                });
                popupMenu.show();
            } else {
                ImageView more = view.findViewById(R.id.more);
                if (more != null) {
                    more.performClick();
                }
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
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = Environment.getExternalStorageDirectory();
        properties.offset = Environment.getExternalStorageDirectory();
        properties.extensions = null;
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a resource file");
        dialog.setDialogSelectionListener(selections -> {
            for (String path : selections) {
                try {
                    FileUtil.copyDirectory(new File(path), new File(temp + File.separator + Uri.parse(path).getLastPathSegment()));
                } catch (IOException e) {
                    SketchwareUtil.toastError("Couldn't import resource! [" + e.getMessage() + "]");
                }
            }
            handleAdapter(temp);
            handleFab();
        });
    }

    private void showRenameDialog(final String path) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_input_layout, null);
        final EditText newName = view.findViewById(R.id.edittext_change_name);
        final TextView cancel = view.findViewById(R.id.text_cancel);
        final TextView save = view.findViewById(R.id.text_save);

        try {
            newName.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (IndexOutOfBoundsException e) {
            newName.setText(path);
        }

        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(v -> {
            if (!newName.getText().toString().isEmpty()) {
                if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + newName.getText().toString())) {
                    SketchwareUtil.toast("Renamed successfully");
                } else {
                    SketchwareUtil.toastError("Renaming failed");
                }
                handleAdapter(temp);
                handleFab();
            }
            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        newName.requestFocus();
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
                    SketchwareUtil.toast("Deleted");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void goEdit(int position) {
        if (frc.listFileResource.get(position).endsWith("xml")) {
            Intent intent = new Intent();
            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
            } else {
                intent.setClass(getApplicationContext(), SrcCodeEditor.class);
            }
            intent.putExtra("title", Uri.parse(frc.listFileResource.get(position)).getLastPathSegment());
            intent.putExtra("content", frc.listFileResource.get(position));
            intent.putExtra("xml", "");
            startActivity(intent);
        } else {
            SketchwareUtil.toast("Only XML files can be edited");
        }
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
                convertView = getLayoutInflater().inflate(R.layout.manage_java_item_hs, parent, false);
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
                                SketchwareUtil.toast("Only XML files can be edited");
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
                            showRenameDialog(frc.listFileResource.get(position));
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
