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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
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

@SuppressLint({"ResourceType", "SetTextI18n"})
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

    public void handleAdapter(String str) {
        ArrayList<String> resourceFile = frc.getResourceFile(str);
        Collections.sort(resourceFile, String.CASE_INSENSITIVE_ORDER);
        adapter = new CustomAdapter(resourceFile);
        gridView.setAdapter(adapter);
    }

    private boolean isInMainDirectory() {
        return temp.equals(fpu.getPathResource(numProj));
    }

    public void handleFab() {
        if (isInMainDirectory()) {
            if (load_file != null) {
                load_file.setVisibility(View.GONE);
            }
        } else if (load_file != null) {
            load_file.setVisibility(View.VISIBLE);
        }
    }

    public void initToolbar() {
        ((TextView) findViewById(2131232458)).setText("Resource Manager");
        ImageView back = findViewById(2131232457);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        load_file = findViewById(2131232459);
        Helper.applyRippleToToolbarView(load_file);
        load_file.setOnClickListener(view -> dialog.show());
    }

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
    public void onClick(View view) {
        if (view != fab) {
            return;
        }
        createNewDialog(isInMainDirectory());
    }

    private void createNewDialog(final boolean isFolder) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427800, null);
        final EditText editText = (EditText) inflate.findViewById(2131232463);
        TextView cancel = (TextView) inflate.findViewById(2131232464);
        TextView save = (TextView) inflate.findViewById(2131232465);
        ((RadioGroup) inflate.findViewById(2131232460)).setVisibility(View.GONE);
        ((TextView) inflate.findViewById(2131232509)).setText(isFolder ? "Create a new folder" : "Create a new file");
        if (!isFolder) editText.setText(".xml");
        cancel.setOnClickListener(v -> create.dismiss());
        save.setOnClickListener(v -> {
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
        });
        create.setOnDismissListener(dialogInterface -> SketchwareUtil.hideKeyboard());
        create.setView(inflate);
        create.show();
        editText.requestFocus();
        if (!isFolder) editText.setSelection(0);
        SketchwareUtil.showKeyboard();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427785);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        Helper.fixFileprovider();
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
        dialog.setDialogSelectionListener(selections -> {
            for (String path : selections) {
                try {
                    FileUtil.copyDirectory(new File(path), new File(temp + File.separator + Uri.parse(path).getLastPathSegment()));
                } catch (IOException e) {
                    bB.b(getApplicationContext(), "Couldn't import resource! [" + e.getMessage() + "]", 0).show();
                }
            }
            handleAdapter(temp);
            handleFab();
        });
    }

    public void showDialog(final String path) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427790, null);
        final EditText editText = (EditText) inflate.findViewById(2131232375);
        try {
            editText.setText(path.substring(path.lastIndexOf("/") + 1));
        } catch (IndexOutOfBoundsException e) {
            editText.setText(path);
        }
        ((TextView) inflate.findViewById(2131232376)).setOnClickListener(v -> create.dismiss());
        ((TextView) inflate.findViewById(2131232377)).setOnClickListener(v -> {
            if (!editText.getText().toString().isEmpty()) {
                if (FileUtil.renameFile(path, path.substring(0, path.lastIndexOf("/")) + "/" + editText.getText().toString())) {
                    bB.a(getApplicationContext(), "Renamed successfully", 0).show();
                } else {
                    bB.b(getApplicationContext(), "Renaming failed", 0).show();
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

    public void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(Uri.fromFile(new File(adapter.getItem(position))).getLastPathSegment())
                .setMessage("Are you sure you want to delete this "
                        + (FileUtil.isDirectory(adapter.getItem(position)) ? "folder" : "file") + "? "
                        + "This action cannot be reverted!")
                .setPositiveButton("Delete", (dialog, which) -> {
                    FileUtil.deleteFile(frc.listFileResource.get(position));
                    handleAdapter(temp);
                    bB.a(getApplicationContext(), "Deleted", 0).show();
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
        bB.a(getApplicationContext(), "Only XML files can be edited", 0).show();
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
            return (long) position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427823, null);
            }
            TextView name = (TextView) convertView.findViewById(2131231837);
            ImageView icon = (ImageView) convertView.findViewById(2131231090);
            ImageView more = (ImageView) convertView.findViewById(2131232627);
            more.setVisibility(FileUtil.isDirectory(getItem(position)) ? View.GONE : View.VISIBLE);
            name.setText(Uri.parse(getItem(position)).getLastPathSegment());

            if (FileUtil.isDirectory(getItem(position))) {
                icon.setImageResource(Resources.drawable.ic_folder_48dp);
            } else {
                try {
                    if (FileUtil.isImageFile(getItem(position))) {
                        Glide.with(ManageResourceActivity.this).load(new File(getItem(position))).into(icon);
                    } else {
                        icon.setImageResource(Resources.drawable.file_48_blue);
                    }
                } catch (Exception ignored) {
                    icon.setImageResource(Resources.drawable.file_48_blue);
                }
            }

            more.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageResourceActivity.this, v);
                popupMenu.inflate(2131492893);
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
