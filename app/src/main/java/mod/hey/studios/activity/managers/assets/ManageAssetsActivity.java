package mod.hey.studios.activity.managers.assets;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

public class ManageAssetsActivity extends Activity {

    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FloatingActionButton fab;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private GridView gridView;
    private ImageView ig_load_file;
    private MyAdapter myadp;
    private String sc_id;
    private TextView tv_nofiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427785);
        if (getIntent().hasExtra("sc_id")) {
            sc_id = getIntent().getStringExtra("sc_id");
        }
        Helper.fixFileprovider();
        setupUI();
        frc = new FileResConfig(sc_id);
        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathAssets(sc_id)).getPath();
        refresh();
    }

    @Override
    public void onBackPressed() {
        if (Uri.parse(current_path).getPath().equals(Uri.parse(fpu.getPathAssets(sc_id)).getPath())) {
            super.onBackPressed();
            return;
        }
        current_path = current_path.substring(0, current_path.lastIndexOf(DialogConfigs.DIRECTORY_SEPERATOR));
        refresh();
    }

    public void setupUI() {
        gridView = findViewById(2131232359);
        gridView.setNumColumns(1);
        fab = findViewById(2131232360);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateDialog();
            }
        });
        tv_nofiles = findViewById(2131232361);
        tv_nofiles.setText("No files");
        ((TextView) findViewById(2131232458)).setText("Asset Manager");
        ImageView imageView = findViewById(2131232457);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));
        ig_load_file = findViewById(2131232459);
        ig_load_file.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(ig_load_file);
        ig_load_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadDialog();
            }
        });
    }

    public String trimPath(String path) {
        return path.endsWith(DialogConfigs.DIRECTORY_SEPERATOR) ? path.substring(0, path.length() - 1) : path;
    }

    public void showCreateDialog() {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427800, null);
        final EditText fileName = inflate.findViewById(2131232463);
        TextView cancel = inflate.findViewById(2131232464);
        TextView save = inflate.findViewById(2131232465);
        final RadioGroup folderOrFile = inflate.findViewById(2131232460);
        inflate.findViewById(2131232462).setVisibility(View.GONE);
        ((RadioButton) inflate.findViewById(2131232461)).setText("File");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileName.getText().toString().isEmpty()) {
                    bB.b(ManageAssetsActivity.this, "Invalid file name", Toast.LENGTH_SHORT).show();
                    return;
                }
                String editable = fileName.getText().toString();
                if (folderOrFile.getCheckedRadioButtonId() == 2131232461) {
                    FileUtil.writeFile(new File(current_path, editable).getAbsolutePath(), "");
                    refresh();
                    toast("File was created successfully");
                    create.dismiss();
                } else if (folderOrFile.getCheckedRadioButtonId() == 2131232624) {
                    FileUtil.makeDir(new File(current_path, editable).getAbsolutePath());
                    refresh();
                    toast("Folder was created successfully");
                    create.dismiss();
                } else {
                    toast("Select a file type");
                }
            }
        });
        create.setView(inflate);
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SketchwareUtil.hideKeyboard();
            }
        });
        create.show();
        fileName.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    public void showLoadDialog() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = 1;
        properties.selection_type = 2;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.extensions = null;
        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select an asset file");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    File file = new File(path);
                    try {
                        FileUtil.copyDirectory(file, new File(current_path, file.getName()));
                        refresh();
                    } catch (IOException e) {
                        bB.b(ManageAssetsActivity.this, "Couldn't import file! [" + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }

    public void showRenameDialog(final int position) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427790, null);
        final EditText newFileName = inflate.findViewById(2131232375);
        newFileName.setText(myadp.getFileName(position));
        TextView cancel = inflate.findViewById(2131232376);
        ((TextView) inflate.findViewById(2131232377)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newFileName.getText().toString().isEmpty()) {
                    FileUtil.renameFile(myadp.getItem(position), new File(current_path, newFileName.getText().toString()).getAbsolutePath());
                    refresh();
                    toast("Renamed successfully");
                }
                create.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        create.setView(inflate);
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                SketchwareUtil.hideKeyboard();
            }
        });
        create.show();
        newFileName.requestFocus();
        SketchwareUtil.showKeyboard();
    }

    public void showDeleteDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(myadp.getFileName(position))
                .setMessage("Are you sure you want to delete this " + (myadp.isFolder(position) ? "folder" : "file") + "? "
                        + "This action cannot be reversed!")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtil.deleteFile(myadp.getItem(position));
                        refresh();
                        toast("Deleted successfully");
                    }
                }).setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void refresh() {
        if (!FileUtil.isExistFile(fpu.getPathAssets(sc_id))) {
            FileUtil.makeDir(fpu.getPathAssets(sc_id));
            refresh();
        }
        currentTree.clear();
        FileUtil.listDir(current_path, currentTree);
        sort(currentTree);
        myadp = new MyAdapter();
        gridView.setAdapter(myadp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (myadp.isFolder(position)) {
                    current_path = myadp.getItem(position);
                    refresh();
                    return;
                }
                myadp.goEditFile(position);
            }
        });
        if (currentTree.size() == 0) {
            tv_nofiles.setVisibility(View.VISIBLE);
        } else {
            tv_nofiles.setVisibility(View.GONE);
        }
    }

    public class MyAdapter extends BaseAdapter {
        
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
            name.setText(getFileName(position));
            icon.setImageResource(isFolder(position) ? 2131165754 : 2131165622);
            Helper.applyRipple(ManageAssetsActivity.this, more);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(ManageAssetsActivity.this, v);
                    if (!MyAdapter.this.isFolder(position)) {
                        popupMenu.getMenu().add(0, 0, 0, "Edit");
                    }
                    popupMenu.getMenu().add(0, 1, 0, "Rename");
                    popupMenu.getMenu().add(0, 2, 0, "Delete");
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
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
                        }
                    });
                    popupMenu.show();
                }
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

    private void toast(String s) {
        bB.a(this, s, 0).show();
    }

    private void sort(ArrayList<String> paths) {
        ArrayList<String> directories = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();
        for (String path : paths) {
            if (FileUtil.isDirectory(path)) {
                directories.add(path);
            } else {
                files.add(path);
            }
        }
        Collections.sort(directories, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(files, String.CASE_INSENSITIVE_ORDER);
        paths.clear();
        paths.addAll(directories);
        paths.addAll(files);
    }
}
