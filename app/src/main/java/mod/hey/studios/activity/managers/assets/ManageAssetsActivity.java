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
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import a.a.a.bB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

@SuppressLint("ResourceType")
public class ManageAssetsActivity extends Activity {

    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FilePathUtil fpu;
    private GridView gridView;
    private MyAdapter myadp;
    private String sc_id;
    private TextView tv_nofiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427785);

        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();

        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathAssets(sc_id)).getPath();

        refresh();
    }

    private void setupUI() {
        gridView = findViewById(2131232359);
        gridView.setNumColumns(1);

        FloatingActionButton fab = findViewById(2131232360);
        fab.setOnClickListener(v -> showCreateDialog());

        tv_nofiles = findViewById(2131232361);
        tv_nofiles.setText("No files");

        ((TextView) findViewById(2131232458)).setText("Asset Manager");
        ImageView imageView = findViewById(2131232457);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));

        ImageView ig_load_file = findViewById(2131232459);
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

    private void showCreateDialog() {
        final AlertDialog create = new AlertDialog.Builder(this).create();

        View inflate = getLayoutInflater().inflate(2131427800, null);
        final EditText fileName = inflate.findViewById(2131232463);
        TextView cancel = inflate.findViewById(2131232464);
        TextView save = inflate.findViewById(2131232465);
        final RadioGroup folderOrFile = inflate.findViewById(2131232460);

        inflate.findViewById(2131232462).setVisibility(View.GONE);
        ((RadioButton) inflate.findViewById(2131232461)).setText("File");

        cancel.setOnClickListener(v -> create.dismiss());
        save.setOnClickListener(v -> {
            if (fileName.getText().toString().isEmpty()) {
                bB.b(ManageAssetsActivity.this, "Invalid file name", Toast.LENGTH_SHORT).show();
                return;
            }

            String editable = fileName.getText().toString();

            switch (folderOrFile.getCheckedRadioButtonId()) {
                case 2131232461:
                    FileUtil.writeFile(new File(current_path, editable).getAbsolutePath(), "");
                    break;

                case 2131232624:
                    FileUtil.makeDir(new File(current_path, editable).getAbsolutePath());
                    break;

                default:
                    SketchwareUtil.toast("Select a file type");
                    return;
            }

            // This piece of code will be executed when the switch is 2131232461 or 2131232624
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
                    bB.b(ManageAssetsActivity.this, "Couldn't import file! [" + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showRenameDialog(final int position) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427790, null);
        final EditText newFileName = inflate.findViewById(2131232375);
        newFileName.setText(myadp.getFileName(position));
        TextView cancel = inflate.findViewById(2131232376);

        inflate.findViewById(2131232377).setOnClickListener(v -> {
            if (!newFileName.getText().toString().isEmpty()) {
                FileUtil.renameFile(myadp.getItem(position), new File(current_path, newFileName.getText().toString()).getAbsolutePath());
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
                .setTitle(myadp.getFileName(position))
                .setMessage("Are you sure you want to delete this " + (myadp.isFolder(position) ? "folder" : "file") + "? "
                        + "This action cannot be reversed!")
                .setPositiveButton(Resources.string.common_word_delete, (dialog, which) -> {
                    FileUtil.deleteFile(myadp.getItem(position));
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

        myadp = new MyAdapter();

        gridView.setAdapter(myadp);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (myadp.isFolder(position)) {
                current_path = myadp.getItem(position);
                refresh();
                return;
            }

            myadp.goEditFile(position);
        });

        if (currentTree.size() == 0) {
            tv_nofiles.setVisibility(View.VISIBLE);
        } else {
            tv_nofiles.setVisibility(View.GONE);
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
                convertView = getLayoutInflater().inflate(2131427823, null);
            }

            TextView name = convertView.findViewById(2131231837);
            ImageView icon = convertView.findViewById(2131231090);
            ImageView more = convertView.findViewById(2131232627);

            name.setText(getFileName(position));
            icon.setImageResource(isFolder(position) ? 2131165754 : 2131165622);
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
                viewIntent.addFlags(268435456);

                startActivity(viewIntent);
            }
        }
    }
}
