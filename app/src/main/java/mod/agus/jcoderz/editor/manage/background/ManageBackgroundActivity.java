package mod.agus.jcoderz.editor.manage.background;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import a.a.a.bB;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.activity.SrcCodeEditor;
import mod.hey.studios.util.Helper;

public class ManageBackgroundActivity extends Activity {

    public CustomAdapter adapter;
    public FilePickerDialog dialog;
    public FloatingActionButton fab;
    public FilePathUtil fpu;
    public FileResConfig frc;
    public GridView gridView;
    public Toolbar k;
    public ArrayList<String> list;
    public String numProj;
    public DialogProperties properties;
    public TextView tv;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427785);
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
            list = new ArrayList<>();
        }
        gridView = (GridView) findViewById(2131232359);
        fab = (FloatingActionButton) findViewById(2131232360);
        tv = (TextView) findViewById(2131232361);
        tv.setText("Add broadcast or service files to project");
        frc = new FileResConfig(numProj);
        fpu = new FilePathUtil();
        setupDialog();
        checkDir();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(ManageBackgroundActivity.this, view);
                popupMenu.inflate(2131492893);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getTitle().toString()) {
                            case "Add to AndroidManifest":
                                String readFile = FileUtil.readFile(list.get(position));
                                if (readFile.contains("extends Service")) {
                                    writeToManifest(list.get(position), "Service");
                                } else if (readFile.contains("extends BroadcastReceiver")) {
                                    writeToManifest(list.get(position), "BroadcastReceiver");
                                }
                                break;

                            case "Edit":
                                Intent intent = new Intent();
                                intent.setClass(ManageBackgroundActivity.this, SrcCodeEditor.class);
                                intent.putExtra("title", Uri.parse(list.get(position)).getLastPathSegment());
                                intent.putExtra("content", list.get(position));
                                intent.putExtra("java", "");
                                startActivity(intent);
                                break;

                            case "Delete":
                                StringBuilder sb = new StringBuilder();
                                if (getIntent().hasExtra("pkgName")) {
                                    sb.append(getIntent().getStringExtra("pkgName"));
                                } else {
                                    sb.append(frc.getPackageNameProject());
                                }
                                sb.append(".");
                                sb.append(Uri.parse(list.get(position)).getLastPathSegment().replace(".java", ""));
                                if (frc.getServiceManifestList().contains(sb.toString())) {
                                    frc.getServiceManifestList().remove(sb.toString());
                                    FileUtil.writeFile(fpu.getManifestService(numProj), new Gson().toJson(frc.listServiceManifest));
                                    bB.a(getApplicationContext(), "Old File has been deleted, Manifest Changed default.", 1).show();
                                } else if (frc.getBroadcastManifestList().contains(sb.toString())) {
                                    frc.getBroadcastManifestList().remove(sb.toString());
                                    FileUtil.writeFile(fpu.getManifestBroadcast(numProj), new Gson().toJson(frc.listBroadcastManifest));
                                    bB.a(getApplicationContext(), "Old File has been deleted, Manifest Changed default.", 1).show();
                                } else {
                                    bB.a(getApplicationContext(), "Name file changed successfully.", 0).show();
                                }
                                FileUtil.deleteFile(list.get(position));
                                checkDir();
                                break;

                            case "Rename":
                                showDialog(list.get(position));
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
        initToolbar();
    }

    public void initToolbar() {
        ((TextView) findViewById(2131232458)).setText("Background service Manager");
        ImageView back = (ImageView) findViewById(2131232457);
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
    }

    public void checkDir() {
        if (!FileUtil.isExistFile(fpu.getPathBroadcast(numProj))) {
            FileUtil.makeDir(fpu.getPathBroadcast(numProj));
            checkDir();
        } else if (FileUtil.isExistFile(fpu.getPathService(numProj))) {
            list.clear();
            list.addAll(frc.getBroadcastFile());
            list.addAll(frc.getServiceFile());
            adapter = new CustomAdapter(list);
            gridView.setAdapter((ListAdapter) adapter);
            updateUI();
        } else {
            FileUtil.makeDir(fpu.getPathService(numProj));
            checkDir();
        }
    }

    public void setupDialog() {
        properties = new DialogProperties();
        properties.selection_mode = 1;
        properties.selection_type = 0;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.extensions = new String[]{"java"};
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a Java file");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    String readFile = FileUtil.readFile(path);
                    if (readFile.contains("package")) {
                        String substring = readFile.substring(readFile.indexOf("package"), readFile.indexOf(";"));
                        StringBuilder sb = new StringBuilder();
                        sb.append("package ");
                        if (getIntent().hasExtra("pkgName")) {
                            sb.append(getIntent().getStringExtra("pkgName"));
                        } else {
                            sb.append(frc.getPackageNameProject());
                        }
                        final String replace = readFile.replace(substring, sb.toString());
                        if (readFile.contains("extends Service")) {
                            FileUtil.writeFile(fpu.getPathService(numProj).concat(File.separator).concat(Uri.parse(path).getLastPathSegment()), replace);
                        } else if (readFile.contains("extends BroadcastReceiver")) {
                            FileUtil.writeFile(fpu.getPathBroadcast(numProj).concat(File.separator).concat(Uri.parse(path).getLastPathSegment()), replace);
                        } else {
                            bB.a(ManageBackgroundActivity.this, "Invalid Java class", 0).show();
                            return;
                        }
                        checkDir();
                    } else {
                        bB.a(ManageBackgroundActivity.this, "Invalid Java file", 1).show();
                    }
                }
            }
        });
    }

    public void updateUI() {
        if (list.size() != 0) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }
    }

    public void showDialog(final String str) {
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(2131427790, null);
        final EditText editText = inflate.findViewById(2131232375);
        ((TextView) inflate.findViewById(2131232377)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                if (getIntent().hasExtra("pkgName")) {
                    sb.append(getIntent().getStringExtra("pkgName"));
                } else {
                    sb.append(frc.getPackageNameProject());
                }
                sb.append(".");
                sb.append(Uri.parse(str).getLastPathSegment().replace(".java", ""));
                if (!editText.getText().toString().isEmpty()) {
                    FileUtil.renameFile(str, str.substring(0, str.lastIndexOf("/")) + "/" + editText.getText().toString() + ".java");
                    checkDir();
                    if (frc.getServiceManifestList().contains(sb.toString())) {
                        frc.getServiceManifestList().remove(sb.toString());
                        FileUtil.writeFile(fpu.getManifestService(numProj), new Gson().toJson(frc.listServiceManifest));
                        bB.a(getApplicationContext(), "Old File has been renamed, Manifest Changed default.", 1).show();
                    } else if (frc.getBroadcastManifestList().contains(sb.toString())) {
                        frc.getBroadcastManifestList().remove(sb.toString());
                        FileUtil.writeFile(fpu.getManifestBroadcast(numProj), new Gson().toJson(frc.listBroadcastManifest));
                        bB.a(getApplicationContext(), "Old File has been renamed, Manifest Changed default.", 1).show();
                    } else {
                        bB.a(getApplicationContext(), "Name file changed successfully.", 0).show();
                    }
                }
                create.dismiss();
            }
        });
        ((TextView) inflate.findViewById(2131232376)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.dismiss();
            }
        });
        create.setView(inflate);
        create.show();
    }

    public void writeToManifest(String str, String str2) {
        String substring = Uri.parse(str).getLastPathSegment().substring(0, Uri.parse(str).getLastPathSegment().lastIndexOf("."));
        StringBuilder sb = new StringBuilder();
        if (getIntent().hasExtra("pkgName")) {
            sb.append(getIntent().getStringExtra("pkgName"));
        } else {
            sb.append(frc.getPackageNameProject());
        }
        sb.append(".");
        sb.append(substring);
        if (str2.equals("Service")) {
            if (frc.getServiceManifestList().contains(sb.toString())) {
                bB.a(getApplicationContext(), "File already added.", 0).show();
                return;
            }
            frc.getServiceManifestList().add(sb.toString());
            FileUtil.writeFile(fpu.getManifestService(numProj), new Gson().toJson(frc.listServiceManifest));
            bB.a(getApplicationContext(), "Add to manifest successfully.", 0).show();
        } else if (!str2.equals("BroadcastReceiver")) {
            //TODO: Add logic?
        } else {
            if (frc.getBroadcastManifestList().contains(sb.toString())) {
                bB.a(getApplicationContext(), "File already added.", 0).show();
                return;
            }
            frc.getBroadcastManifestList().add(sb.toString());
            FileUtil.writeFile(fpu.getManifestBroadcast(numProj), new Gson().toJson(frc.listBroadcastManifest));
            bB.a(getApplicationContext(), "Add to manifest successfully.", 0).show();
        }
    }

    public class CustomAdapter extends BaseAdapter {

        private final ArrayList<String> data;

        public CustomAdapter(ArrayList<String> arrayList) {
            data = arrayList;
        }

        public int getCount() {
            return data.size();
        }

        public String getItem(int i) {
            return data.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(2131427788, null);
            }
            TextView tv = (TextView) convertView.findViewById(2131232369);
            tv.setText(Uri.parse(data.get(position)).getLastPathSegment());
            return convertView;
        }
    }
}