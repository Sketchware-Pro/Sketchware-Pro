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
import android.view.WindowManager;
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
import com.sketchware.remod.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import mod.SketchwareUtil;
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
        setContentView(R.layout.manage_file);

        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();

        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathAssets(sc_id)).getPath();

        refresh();
    }

    @SuppressLint("SetTextI18n")
    private void setupUI() {
        gridView = findViewById(R.id.list_file);
        gridView.setNumColumns(1);

        FloatingActionButton fab = findViewById(R.id.fab_plus);
        fab.setOnClickListener(v -> showCreateDialog());

        tv_noFileExist = findViewById(R.id.text_info);
        tv_noFileExist.setText("No files");

        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Asset Manager");
        ImageView imageView = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));

        ImageView ig_load_file = findViewById(R.id.ig_toolbar_load_file);
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
        } else {
            current_path = current_path.substring(0, current_path.lastIndexOf(DialogConfigs.DIRECTORY_SEPERATOR));
            refresh();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showCreateDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();

        final View view = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);
        final RadioGroup folderOrFile = view.findViewById(R.id.dialog_radio_filetype);
        final RadioButton file = view.findViewById(R.id.dialog_radio_filetype_class);
        final RadioButton activity = view.findViewById(R.id.dialog_radio_filetype_activity);
        final EditText filename = view.findViewById(R.id.dialog_edittext_name);
        final TextView cancel = view.findViewById(R.id.dialog_text_cancel);
        final TextView save = view.findViewById(R.id.dialog_text_save);

        file.setText("File");
        activity.setVisibility(View.GONE);

        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(v -> {
            if (filename.getText().toString().isEmpty()) {
                SketchwareUtil.toastError("Invalid filename");
                return;
            }

            String editable = filename.getText().toString();

            int checkedRadioButtonId = folderOrFile.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.dialog_radio_filetype_class) {
                FileUtil.writeFile(new File(current_path, editable).getAbsolutePath(), "");
            } else if (checkedRadioButtonId == R.id.radio_button_folder) {
                FileUtil.makeDir(new File(current_path, editable).getAbsolutePath());
            } else {
                SketchwareUtil.toast("Select a file type");
                return;
            }

            refresh();
            SketchwareUtil.toast("File was created successfully");
            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        filename.requestFocus();
    }

    private void showLoadDialog() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = Environment.getExternalStorageDirectory();
        properties.offset = Environment.getExternalStorageDirectory();
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
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_input_layout, null);
        final EditText newFileName = view.findViewById(R.id.edittext_change_name);
        final TextView cancel = view.findViewById(R.id.text_cancel);
        final TextView save = view.findViewById(R.id.text_save);

        newFileName.setText(myAdapter.getFileName(position));

        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(v -> {
            if (!newFileName.getText().toString().isEmpty()) {
                FileUtil.renameFile(myAdapter.getItem(position), new File(current_path, newFileName.getText().toString()).getAbsolutePath());
                refresh();
                SketchwareUtil.toast("Renamed successfully");
            }

            dialog.dismiss();
        });

        dialog.setView(view);
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        newFileName.requestFocus();
    }

    private void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(myAdapter.getFileName(position))
                .setMessage("Are you sure you want to delete this " + (myAdapter.isFolder(position) ? "folder" : "file") + "? "
                        + "This action cannot be reversed!")
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    FileUtil.deleteFile(myAdapter.getItem(position));
                    refresh();
                    SketchwareUtil.toast("Deleted successfully");
                })
                .setNegativeButton(R.string.common_word_cancel, null)
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
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            view.findViewById(R.id.more).performClick();
            return true;
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
                convertView = getLayoutInflater().inflate(R.layout.manage_java_item_hs, parent, false);
            }

            TextView name = convertView.findViewById(R.id.title);
            ImageView icon = convertView.findViewById(R.id.icon);
            ImageView more = convertView.findViewById(R.id.more);

            name.setText(getFileName(position));
            if (isFolder(position)) {
                icon.setImageResource(R.drawable.ic_folder_48dp);
            } else {
                try {
                    if (FileUtil.isImageFile(getItem(position))) {
                        Glide.with(ManageAssetsActivity.this).load(new File(getItem(position))).into(icon);
                    } else {
                        icon.setImageResource(R.drawable.file_48_blue);
                    }
                } catch (Exception ignored) {
                    icon.setImageResource(R.drawable.file_48_blue);
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
