package mod.hey.studios.activity.managers.assets;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import mod.SketchwareUtil;
import mod.agus.jcoderz.editor.manage.resource.ManageResourceActivity;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

public class ManageAssetsActivity extends Activity {

    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FilePathUtil fpu;
    private GridView gridView;
    private MyAdapter myAdapter;
    private String sc_id;
    private TextView tv_noFileExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_file);

        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();

        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathAssets(sc_id)).getPath();

        refresh();
    }

    @SuppressLint("SetTextI18n")
    private void setupUI() {
        gridView = findViewById(Resources.id.list_file);
        gridView.setNumColumns(1);

        FloatingActionButton fab = findViewById(Resources.id.fab_plus);
        fab.setOnClickListener(v -> showCreateDialog());

        tv_noFileExist = findViewById(Resources.id.text_info);
        tv_noFileExist.setText("No files");

        ((TextView) findViewById(Resources.id.tx_toolbar_title)).setText("Asset Manager");
        ImageView imageView = findViewById(Resources.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));

        ImageView ig_load_file = findViewById(Resources.id.ig_toolbar_load_file);
        ig_load_file.setVisibility(View.VISIBLE);

        Helper.applyRippleToToolbarView(ig_load_file);

        ig_load_file.setOnClickListener(v -> showLoadDialog());
    }

    @Override
    public void onBackPressed() {
        if (Objects.equals(
                Uri.parse(current_path).getPath(),
                Uri.parse(fpu.getPathAssets(sc_id)).getPath()
        )) {
            super.onBackPressed();
            return;
        }

        current_path = current_path.substring(0, current_path.lastIndexOf(DialogConfigs.DIRECTORY_SEPERATOR));
        refresh();
    }

    @SuppressLint("SetTextI18n")
    private void showCreateDialog() {
        final AlertDialog create = new AlertDialog.Builder(this).create();

        View inflate = getLayoutInflater().inflate(Resources.layout.dialog_create_new_file_layout, null);
        final EditText fileName = inflate.findViewById(Resources.id.dialog_edittext_name);
        TextView cancel = inflate.findViewById(Resources.id.dialog_text_cancel);
        TextView save = inflate.findViewById(Resources.id.dialog_text_save);
        final RadioGroup folderOrFile = inflate.findViewById(Resources.id.dialog_radio_filetype);

        inflate.findViewById(Resources.id.dialog_radio_filetype_activity).setVisibility(View.GONE);
        ((RadioButton) inflate.findViewById(Resources.id.dialog_radio_filetype_class)).setText("File");

        cancel.setOnClickListener(v -> create.dismiss());
        save.setOnClickListener(v -> {
            if (fileName.getText().toString().isEmpty()) {
                SketchwareUtil.toastError("Invalid file name");
                return;
            }

            String editable = fileName.getText().toString();

            switch (folderOrFile.getCheckedRadioButtonId()) {
                case Resources.id.dialog_radio_filetype_class:
                    FileUtil.writeFile(new File(current_path, editable).getAbsolutePath(), "");
                    break;

                case Resources.id.radio_button_folder:
                    FileUtil.makeDir(new File(current_path, editable).getAbsolutePath());
                    break;

                default:
                    SketchwareUtil.toast("Select a file type");
                    return;
            }

            // This piece of code will be executed when the switch is
            // Resources.id.dialog_radio_filetype_class or Resources.id.radio_button_folder
            refresh();
            SketchwareUtil.toast("File was created successfully");
            create.dismiss();
        });

        create.setView(inflate);
        create.setOnDismissListener(dialog -> SketchwareUtil.hideKeyboard());
        create.show();

        fileName.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    private void showLoadDialog() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = 1;
        properties.selection_type = 2;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.extensions = null;

        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select an asset file");
        dialog.setDialogSelectionListener(selections -> {
            for (String path : selections) {
                File file = new File(path);
                try {
                    FileUtil.copyDirectory(file, new File(current_path, file.getName()));
                    refresh();
                } catch (IOException e) {
                    SketchwareUtil.toastError("Couldn't import file! [" + e.getMessage() + "]");
                }
            }
        });

        dialog.show();
    }

    private void showRenameDialog(final int position) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(Resources.layout.dialog_input_layout, null);
        final EditText newFileName = inflate.findViewById(Resources.id.edittext_change_name);
        newFileName.setText(myAdapter.getFileName(position));
        TextView cancel = inflate.findViewById(Resources.id.text_cancel);

        inflate.findViewById(Resources.id.text_save).setOnClickListener(v -> {
            if (!newFileName.getText().toString().isEmpty()) {
                FileUtil.renameFile(myAdapter.getItem(position), new File(current_path, newFileName.getText().toString()).getAbsolutePath());
                refresh();
                SketchwareUtil.toast("Renamed successfully");
            }

            create.dismiss();
        });

        cancel.setOnClickListener(v -> create.dismiss());
        create.setView(inflate);
        create.setOnDismissListener(dialog -> SketchwareUtil.hideKeyboard());
        create.show();

        newFileName.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    private void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(myAdapter.getFileName(position))
                .setMessage("Are you sure you want to delete this " + (myAdapter.isFolder(position) ? "folder" : "file") + "? "
                        + "This action cannot be reversed!")
                .setPositiveButton(Resources.string.common_word_delete, (dialog, which) -> {
                    FileUtil.deleteFile(myAdapter.getItem(position));
                    refresh();
                    SketchwareUtil.toast("Deleted successfully");
                })
                .setNegativeButton(Resources.string.common_word_cancel, null)
                .create()
                .show();
    }

    private void refresh() {
        if (!FileUtil.isExistFile(fpu.getPathAssets(sc_id))) {
            FileUtil.makeDir(fpu.getPathAssets(sc_id));
            refresh();
        }

        currentTree.clear();
        FileUtil.listDir(current_path, currentTree);
        Helper.sortPaths(currentTree);

        myAdapter = new MyAdapter();

        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (myAdapter.isFolder(position)) {
                current_path = myAdapter.getItem(position);
                refresh();
                return;
            }

            myAdapter.goEditFile(position);
        });

        if (currentTree.size() == 0) {
            tv_noFileExist.setVisibility(View.VISIBLE);
        } else {
            tv_noFileExist.setVisibility(View.GONE);
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return currentTree.size();
        }

        @Override
        public String getItem(int position) {
            return currentTree.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(Resources.layout.manage_java_item_hs, null);
            }

            TextView name = convertView.findViewById(Resources.id.title);
            ImageView icon = convertView.findViewById(Resources.id.icon);
            ImageView more = convertView.findViewById(Resources.id.more);

            name.setText(getFileName(position));
            if (isFolder(position)) {
                icon.setImageResource(Resources.drawable.ic_folder_48dp);
            } else {
                try {
                    if (FileUtil.isImageFile(getItem(position))) {
                        Glide.with(ManageAssetsActivity.this).load(new File(getItem(position))).into(icon);
                    } else {
                        icon.setImageResource(Resources.drawable.file_48_blue);
                    }
                } catch (Exception ignored) {
                    icon.setImageResource(Resources.drawable.file_48_blue);
                }
            }
            Helper.applyRipple(ManageAssetsActivity.this, more);
            more.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(ManageAssetsActivity.this, v);

                if (!isFolder(position)) {
                    popupMenu.getMenu().add(0, 0, 0, "Edit");
                }

                popupMenu.getMenu().add(0, 1, 0, "Rename");
                popupMenu.getMenu().add(0, 2, 0, "Delete");

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case 0:
                            goEditFile(position);
                            break;

                        case 1:
                            showRenameDialog(position);
                            break;

                        case 2:
                            showDeleteDialog(position);
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

        public String getFileName(int position) {
            String item = getItem(position);
            return item.substring(item.lastIndexOf(DialogConfigs.DIRECTORY_SEPERATOR) + 1);
        }

        public boolean isFolder(int position) {
            return FileUtil.isDirectory(getItem(position));
        }

        public void goEditFile(int position) {
            if (getItem(position).endsWith(".json") || getItem(position).endsWith(".txt")) {
                Intent launchIntent = new Intent();

                launchIntent.setClass(getApplicationContext(), SrcCodeEditor.class);
                launchIntent.putExtra("title", getFileName(position));
                launchIntent.putExtra("content", getItem(position));

                startActivity(launchIntent);
            } else {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);

                viewIntent.setDataAndType(Uri.fromFile(new File(getItem(position))), "*/*");
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(viewIntent);
            }
        }
    }
}
