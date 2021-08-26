package mod.hey.studios.activity.managers.java;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

import a.a.a.aB;
import a.a.a.xB;

public class ManageJavaActivity extends Activity {
    
    private static final String ACTIVITY_TEMPLATE =
    "package %s;\n" +
    "\n" +
    "import android.app.Activity;\n" +
    "import android.os.Bundle;\n" +
    "\n" +
    "public class %s extends Activity {\n" +
    "\n" +
    "    @Override\n" +
    "    protected void onCreate(Bundle savedInstanceState) {\n" +
    "        super.onCreate(savedInstanceState);\n" +
    "    }" +
    "\n" +
    "}\n";
    private static final String CLASS_TEMPLATE =
    "package %s;\n" +
    "\n" +
    "public class %s {\n" +
    "    \n" +
    "}\n";
    
    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FloatingActionButton fab;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private GridView gridView;
    private MyAdapter adapter;
    private String sc_id;
    private TextView noteNoFiles;
    private TextView title;
    private final ArrayList<String> selectedPaths = new ArrayList<>();
    private ImageView loadFile;
    private ImageView back;
    private boolean copyMode = false;
    private String[] copiedPaths;
    private boolean cut = false;
    private long lastModifiedTime = 0;
    private String lastCurrentPath = "";
    
    private int lastPos = 0;
    private int scrollState = 0;
    private int duration = 300;
    private Handler handler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_file);
        
        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();
        frc = new FileResConfig(sc_id);
        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathJava(sc_id)).getPath().concat("/".concat(getIntent().getStringExtra("pkgName").replace(".", "/")));
        refresh();
    }
    
    @Override
    public void onBackPressed() {
        if (isSelecting()) {
            selectedPaths.clear();
            refresh();
            return;
        }
        if (Objects.equals(
        Uri.parse(current_path).getPath(),
        Uri.parse(fpu.getPathJava(sc_id)).getPath()
        )) {
            super.onBackPressed();
            return;
        }
        
        current_path = current_path.substring(0, current_path.lastIndexOf("/"));
        refresh();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    
    private void setupUI() {
        back = findViewById(Resources.id.ig_toolbar_back);
        title = findViewById(Resources.id.tx_toolbar_title);
        title.setEllipsize(TextUtils.TruncateAt.START);
        title.setSingleLine(true);
        title.setOnLongClickListener(v -> {
            try {
                ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", getCurrentPkgName()));
                SketchwareUtil.toast("Copied!");
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed to copy!");
            }
            return true;
        });
        
        loadFile = findViewById(Resources.id.ig_toolbar_load_file);
        loadFile.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(loadFile);
        loadFile.setOnClickListener(v -> {
            if (copyMode) {
                showAddFilesDialog(copiedPaths);
                return;
            }
            showLoadDialog();
        });
        
        fab = findViewById(Resources.id.fab_plus);
        fab.setOnClickListener(v -> showCreateDialog());
        
        gridView = findViewById(Resources.id.list_file);
        gridView.setNumColumns(1);
        
        noteNoFiles = findViewById(Resources.id.text_info);
        noteNoFiles.setText("No files");
        
        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(v -> {
            if (copyMode) {
                copyMode = false;
                copiedPaths = new String[0];
                refresh();
                return;
            }
            finish();
        });
        /* title.setText("Java Manager"); */
        
    }
    
    private String getCurrentPkgName() {
        try {
            String trimmedPath = Helper.trimPath(fpu.getPathJava(sc_id));
            String substring = current_path.substring(current_path.indexOf(trimmedPath) + trimmedPath.length());
            
            if (substring.endsWith("/")) {
                substring = substring.substring(0, substring.length() - 1);
            }
            if (substring.startsWith("/")) {
                substring = substring.substring(1);
            }
            
            String replace = substring.replace("/", ".");
            return !replace.isEmpty() ? replace : "";
        } catch (Exception e) {
        }
        return "";
    }
    
    private void showCreateDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View root = getLayoutInflater().inflate(Resources.layout.dialog_create_new_file_layout, null);
        
        final EditText inputName = root.findViewById(Resources.id.dialog_edittext_name);
        final RadioGroup radio_fileType = root.findViewById(Resources.id.dialog_radio_filetype);
        
        root.findViewById(Resources.id.dialog_text_cancel)
        .setOnClickListener(Helper.getDialogDismissListener(dialog));
        root.findViewById(Resources.id.dialog_text_save)
        .setOnClickListener(v -> {
            if (inputName.getText().toString().isEmpty()) {
                SketchwareUtil.toastError("Invalid file name");
                return;
            }
            
            String name = inputName.getText().toString();
            String packageName = getCurrentPkgName();
            
            String newFileContent;
            switch (radio_fileType.getCheckedRadioButtonId()) {
                case Resources.id.dialog_radio_filetype_class:
                newFileContent = String.format(CLASS_TEMPLATE, packageName, name);
                break;
                
                case Resources.id.dialog_radio_filetype_activity:
                newFileContent = String.format(ACTIVITY_TEMPLATE, packageName, name);
                break;
                
                case Resources.id.radio_button_folder:
                FileUtil.makeDir(new File(current_path, name).getAbsolutePath());
                refresh();
                SketchwareUtil.toast("Folder was created successfully");
                dialog.dismiss();
                return;
                
                default:
                SketchwareUtil.toast("Select a file type");
                return;
            }
            if (newFileContent.startsWith("package ;")) {
                newFileContent = newFileContent.replaceFirst("package ;", "//empty package");
            }
            
            FileUtil.writeFile(new File(current_path, name + ".java").getAbsolutePath(), newFileContent);
            refresh();
            SketchwareUtil.toast("File was created successfully");
            dialog.dismiss();
        });
        
        dialog.setView(root);
        dialog.show();
        
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        inputName.requestFocus();
    }
    
    private void showLoadDialog() {
        DialogProperties properties = new DialogProperties();
        
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = Environment.getExternalStorageDirectory();
        properties.offset = Environment.getExternalStorageDirectory();
        properties.extensions = new String[]{".java"};
        
        FilePickerDialog pickerDialog = new FilePickerDialog(this, properties);
        
        pickerDialog.setTitle("Select a Java file");
        pickerDialog.setDialogSelectionListener(selections -> {
            copyMode = false;
            showAddFilesDialog(selections);
        });
        
        pickerDialog.show();
    }
    
    private void showAddFilesDialog(String[] selections) {
        final aB dialog = new aB(this);
        dialog.a(Resources.drawable.rename_96_blue);
        dialog.b(getCurrentPkgName().isEmpty() ? "empty package" : getCurrentPkgName());
        if (copyMode) {
            dialog.a("Do you want to rename package name of copied java files with CurrentPkgName?");
        } else {
            dialog.a("Do you want to rename package name of selected java files with CurrentPkgName?");
        }
        
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_no),
        v -> {
            dialog.dismiss();
            loadFiles(selections, false);
        });
        dialog.b("Yes",
        v -> {
            dialog.dismiss();
            loadFiles(selections, true);
        });
        dialog.configureDefaultButton(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
        Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
    
    private void loadFiles(String[] selections, final boolean rename) {
        final StringBuilder err = new StringBuilder();
        final ProgressDialog dlg = new ProgressDialog(this);
        if (copyMode) {
            dlg.setMessage("copying Java files...");
        } else {
            dlg.setMessage("Adding Java files...");
        }
        dlg.setCancelable(false);
        dlg.show();
        new AsyncTask<String,Integer,String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    for (String path : params) {
                        if (copyMode && cut) {
                            String filename = new File(path).getName();
                            String output = new File(current_path, filename).getAbsolutePath();
                            if (FileUtil.isExistFile(output) && !FileUtil.isDirectory(path) && !FileUtil.isDirectory(output))
                            FileUtil.deleteFile(output);
                            
                            FileUtil.renameFile(path, output);
                            if (rename)
                            addFiles(output, current_path, "", err, rename);
                            
                            if (FileUtil.isExistFile(path))
                            err.append(filename + " is moved incorrectly!");
                        } else {
                            addFiles(path, current_path, "", err, rename);
                        }
                    }
                } catch (Exception e) {
                    err.append("Error: " + e.toString());
                    err.append("\n");
                }
                return "";
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
            }
            @Override
            protected void onPostExecute(String result) {
                dlg.dismiss();
                String error = err.toString();
                if (error.isEmpty()) {
                    SketchwareUtil.toast("Operation Completed!");
                } else {
                    if (error.length() > 256) {
                        SketchwareUtil.toastError("Operation Completed with some errors...");
                    } else {
                        try {
                            SketchwareUtil.toastError(error.substring(0, error.length() - 1));
                        } catch (Exception e) {
                            SketchwareUtil.toastError("Operation Completed with some errors...");
                        }
                    }
                }
                copyMode = false;
                refresh();
            }
        }.execute(selections);
    }
    
    private void addFiles(String path, String currPath, String currName, StringBuilder err, final boolean rename) {
        if (FileUtil.isExistFile(path)) {
            String filename = Uri.parse(path).getLastPathSegment();
            try {
                if (FileUtil.isDirectory(path)) {
                    String newPath = new File(currPath, filename).getAbsolutePath();
                    for (File file : new File(path).listFiles()) {
                        addFiles(file.getAbsolutePath(), newPath, currName + "." + filename, err, rename);
                    }
                    FileUtil.makeDir(newPath);
                } else {
                    if (filename.endsWith(".java")) {
                        String fileContent = FileUtil.readFile(path);
                        if (!rename) {
                            FileUtil.writeFile(new File(currPath, filename).getAbsolutePath(), fileContent);
                            return;
                        }
                        String pkg = getCurrentPkgName() + currName;
                        if (pkg.startsWith(".")) {
                            pkg = pkg.substring(1);
                        }
                        if (fileContent.startsWith("//empty package")) {
                            if (pkg.equals("")) {
                                FileUtil.writeFile(new File(currPath, filename).getAbsolutePath(), fileContent);
                            } else {
                                FileUtil.writeFile(new File(currPath, filename).getAbsolutePath(),
                                fileContent.replaceFirst("//empty package", "package " + pkg + ";"));
                            }
                        } else {
                            if (Pattern.compile("package [.a-zA-Z$0-9]+;").matcher(fileContent).find()) {
                                if (pkg.equals("")) {
                                    Matcher m = Pattern.compile("package [.a-zA-Z$0-9]+;").matcher(fileContent);
                                    boolean find = m.find();
                                    fileContent = fileContent.substring(m.start());
                                    FileUtil.writeFile(new File(currPath, filename).getAbsolutePath(),
                                    fileContent.replaceFirst("package [.a-zA-Z$0-9]+;", "//empty package"));
                                } else {
                                    FileUtil.writeFile(new File(currPath, filename).getAbsolutePath(),
                                    fileContent.replaceFirst("package [.a-zA-Z$0-9]+;", "package " + pkg + ";"));
                                }
                            } else {
                                err.append("File " + filename + " is not a valid Java file");
                                err.append("\n");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                err.append(filename + " get error: " + e.toString());
                err.append("\n");
            }
        }
    }
    
    private void showRenameDialog(final int position) {
        boolean isFolder = adapter.isFolder(position);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LinearLayout root = (LinearLayout) getLayoutInflater().inflate(Resources.layout.dialog_input_layout, null);
        
        final EditText filename = root.findViewById(Resources.id.edittext_change_name);
        filename.setText(adapter.getFileName(position));
        
        CheckBox renameOccurrences = null;
        if (!isFolder) {
            {
                renameOccurrences = new CheckBox(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(
                (int) getDip(16),
                0,
                (int) getDip(16),
                (int) getDip(10)
                );
                renameOccurrences.setLayoutParams(params);
            }
            /* Little "hack" to change margin of filename */
            {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) filename.getLayoutParams();
                params.bottomMargin = 0;
                filename.setLayoutParams(params);
            }
            renameOccurrences.setText("Rename occurrences of \"" + adapter.getFileNameWoExt(position) + "\" in file");
            root.addView(renameOccurrences, 2);
        }
        CheckBox finalRenameOccurrences = renameOccurrences;
        
        root.findViewById(Resources.id.text_cancel)
        .setOnClickListener(Helper.getDialogDismissListener(dialog));
        root.findViewById(Resources.id.text_save)
        .setOnClickListener(view -> {
            if (!filename.getText().toString().isEmpty()) {
                if (!adapter.isFolder(position)) {
                    if (frc.getJavaManifestList().contains(adapter.getFullName(position))) {
                        frc.getJavaManifestList().remove(adapter.getFullName(position));
                        FileUtil.writeFile(fpu.getManifestJava(sc_id), new Gson().toJson(frc.listJavaManifest));
                        SketchwareUtil.toast("NOTE: Removed Activity from manifest");
                    }
                    
                    if (finalRenameOccurrences != null && finalRenameOccurrences.isChecked()) {
                        String fileContent = FileUtil.readFile(adapter.getItem(position));
                        FileUtil.writeFile(adapter.getItem(position),
                        fileContent.replaceAll(adapter.getFileNameWoExt(position),
                        FileUtil.getFileNameNoExtension(filename.getText().toString())));
                    }
                }
                
                FileUtil.renameFile(adapter.getItem(position), new File(current_path, filename.getText().toString()).getAbsolutePath());
                refresh();
                SketchwareUtil.toast("Renamed successfully");
            }
            
            dialog.dismiss();
        });
        
        dialog.setView(root);
        dialog.show();
        
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        filename.requestFocus();
    }
    
    private void showDeleteDialog(final String[] paths) {
        if (!(paths.length > 0)) {
            copyMode = false;
            refresh();
            return;
        }
        
        for (String path : paths) {
            boolean isFolder = FileUtil.isDirectory(path);
            boolean isMainDir = fpu.getPathJava(sc_id).concat("/").concat(getIntent().getStringExtra("pkgName").replace(".", "/")).concat("/").startsWith(path.concat("/"));
            if (isFolder && isMainDir) {
                SketchwareUtil.toastError("You can't delete Main Folder!");
                copyMode = false;
                refresh();
                return;
            }
        }
        final boolean isInManifest = frc.getJavaManifestList().contains(getFullName(paths[0]));
        
        final aB dialog = new aB(this);
        dialog.a(Resources.drawable.delete_96);
        dialog.b(paths.length > 1 ? "Delete" : new File(paths[0]).getName());
        dialog.a((paths.length > 1 ? "Are you sure you want to delete selected files?"
        : ("Are you sure you want to delete this " + (FileUtil.isDirectory(paths[0]) ? "folder" : "file") + "?"
        + (isInManifest ? "\nThis will also remove it from AndroidManifest. " : "")))
        + "\nThis action cannot be reversed!");
        
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_delete),
        v -> {
            dialog.dismiss();
            delete(paths);
        });
        
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
        Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
    
    private void delete(final String[] paths) {
        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setMessage("Deleting Java files...");
        dlg.setCancelable(false);
        dlg.show();
        new AsyncTask<String,Integer,String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    for (String path : params) {
                        boolean isPathInManifest = frc.getJavaManifestList().contains(getFullName(path));
                        if (!FileUtil.isDirectory(path) && isPathInManifest) {
                            frc.getJavaManifestList().remove(getFullName(path));
                            FileUtil.writeFile(fpu.getManifestJava(sc_id), new Gson().toJson(frc.listJavaManifest));
                        }
                        
                        FileUtil.deleteFile(path);
                    }
                } catch (Exception e) {
                }
                return "";
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
            }
            @Override
            protected void onPostExecute(String result) {
                dlg.dismiss();
                refresh();
                SketchwareUtil.toast("Deleted successfully");
            }
        }.execute(paths);
    }
    
    private void refresh() {
        /* moving all java files from JavaFolder to (JavaFolder + pkgName) if (JavaFolder + pkgName) does not exist */
        String javaPath = fpu.getPathJava(sc_id);
        String currPkgName = getIntent().getStringExtra("pkgName");
        String pkgPath = currPkgName.replace(".", "/");
        String javaPkgPath = javaPath.concat("/".concat(pkgPath));
        String lastpkgNamePath = Environment.getExternalStorageDirectory() + "/.sketchware/data/" + sc_id + "/java_package_name";
        if (!FileUtil.isExistFile(lastpkgNamePath)) {
            /* java_package_name was created to check whatever the PackageName has changed or not
            * if changed, the main folder will be renamed to the Current PackageName
            * so deleting java_package_name is not a good idea
            */
            FileUtil.writeFile(lastpkgNamePath, "");
        }
        String pkgName = FileUtil.readFile(lastpkgNamePath).replace(".", "/");
        if (!pkgName.equals(pkgPath)) {
            if (!pkgName.equals("")) {
                javaPath = javaPath + "/" + pkgName;
            }
            if (FileUtil.isExistFile(javaPath)) {
                String tPath = javaPath.substring(0, javaPath.lastIndexOf("/"));
                FileUtil.renameFile(javaPath, tPath.concat("/.temp"));
                FileUtil.makeDir(javaPkgPath);
                FileUtil.renameFile(tPath.concat("/.temp"), javaPkgPath);
            } else {
                FileUtil.makeDir(javaPkgPath);
            }
            FileUtil.writeFile(lastpkgNamePath, currPkgName);
            refresh();
        }
        
        if (!FileUtil.isExistFile(fpu.getManifestJava(sc_id))) {
            FileUtil.writeFile(fpu.getManifestJava(sc_id), "");
            refresh();
        }
        
        if (getCurrentPkgName().isEmpty()) {
            title.setText("root");
        } else {
            title.setText(getCurrentPkgName());
        }
        
        // refresh selectedPaths
        if (!lastCurrentPath.equals(current_path)) {
            selectedPaths.clear();
        }
        
        if (copyMode) {
            loadFile.setImageResource(Resources.drawable.icon_checkbox_white_96);
            loadFile.setAlpha(0.355f);
            loadFile.animate().setDuration(150).alpha(1);
            
            back.setImageResource(0x7f070209); // ic_cancel_white_96dp
            back.setAlpha(0.355f);
            back.animate().setDuration(150).alpha(1);
        } else {
            loadFile.setImageResource(Resources.drawable.add_file_48);
            loadFile.setAlpha(0.355f);
            loadFile.animate().setDuration(150).alpha(1);
            
            back.setImageResource(Resources.drawable.arrow_back_white_48dp);
            back.setAlpha(0.355f);
            back.animate().setDuration(150).alpha(1);
        }
        
        if (!lastCurrentPath.equals(current_path) || new File(current_path).lastModified() != lastModifiedTime) {
            lastCurrentPath = current_path;
            lastModifiedTime = new File(current_path).lastModified();
            currentTree.clear();
            FileUtil.listDir(current_path, currentTree);
            currentTree.add(0, "..");
            Helper.sortPaths(currentTree);
            if (scrollState == 1) {
                setFabVisibility(true);
                lastPos = 0;
            }
            
            adapter = new MyAdapter();
            gridView.setAdapter(adapter);
        } else {
            ((BaseAdapter)adapter).notifyDataSetChanged();
        }
        
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                if (!(Objects.equals(
                Uri.parse(current_path).getPath(),
                Uri.parse(fpu.getPathJava(sc_id)).getPath()))) {
                    current_path = current_path.substring(0, current_path.lastIndexOf("/"));
                    refresh();
                }
                return;
            }
            if (adapter.isFolder(position)) {
                current_path = adapter.getItem(position);
                refresh();
                return;
            }
            adapter.goEditFile(position);
        });
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            adapter.itemContextMenu(view, position, Gravity.CENTER);
            return true;
        });
        
        gridView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView abs, int _scrollState) {
                // nothing
            }
            
            @Override
            public void onScroll(AbsListView abs, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!(firstVisibleItem == lastPos)) {
                    if (scrollState == 0) {
                        if (lastPos < firstVisibleItem) {
                            setFabVisibility(false);
                        }
                    } else {
                        if (scrollState == 1) {
                            if (lastPos > firstVisibleItem) {
                                setFabVisibility(true);
                            }
                        }
                    }
                    lastPos = firstVisibleItem;
                }
            }
        });
        
        noteNoFiles.setVisibility(currentTree.size() > 1 ? View.GONE : View.VISIBLE);
    }
    
    private void setFabVisibility(final boolean VISIBLE) {
        if (VISIBLE) {
            fab.setVisibility(View.VISIBLE);
            fab.animate().setDuration(duration).alpha(1).scaleX(1).scaleY(1);
            scrollState = 0;
        } else {
            fab.animate().setDuration(duration).alpha(0).scaleX(0).scaleY(0);
            scrollState = 1;
            handler.removeCallbacksAndMessages(null); // reset
            handler.postDelayed(new Runnable() {
                public void run() {
                    fab.setVisibility(View.GONE);
                }
            }, duration);
        }
    }
    
    private void select(String path) {
        if (isSelected(path)) {
            selectedPaths.remove(selectedPaths.indexOf(path));
        } else {
            selectedPaths.add(path);
        }
        refresh();
    }
    
    private void selectAll() {
        if (selectedPaths.size() == (currentTree.size() - 1)) {
            selectedPaths.clear();
        } else {
            selectedPaths.clear();
            selectedPaths.addAll(currentTree);
            selectedPaths.remove(0);
        }
        refresh();
    }
    
    private boolean isSelected(String path) {
        return selectedPaths.indexOf(path) != -1;
    }
    
    private boolean isSelecting() {
        return selectedPaths.size() > 0;
    }
    
    public String getFullName(String path) {
        String readFile = FileUtil.readFile(path);
        
        if (!readFile.contains("package ") || !readFile.contains(";")) {
            return getFileNameWoExt(path);
        }
        int packageIndex = readFile.indexOf("package ") + 8;
        
        return readFile.substring(packageIndex, readFile.indexOf(";", packageIndex)) + "." + getFileNameWoExt(path);
    }
    
    public String getFileNameWoExt(String path) {
        return FileUtil.getFileNameNoExtension(path);
    }
    
    public static void rippleEffect(View v, int rc, int rd, int bc, int s) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(bc);
        shape.setCornerRadii(new float[]{(float)rd,(float)rd,(float)rd,(float)rd,(float)rd,(float)rd,(float)rd,(float)rd});
        
        RippleDrawable ripda = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{rc}), shape, null);
        v.setBackgroundDrawable(ripda);
        try {
            if(Build.VERSION.SDK_INT >= 21) {
                v.setElevation(s);
            }
        } catch (Exception e) {
        }
    }
    
    private class MyAdapter extends BaseAdapter {
        
        @Override
        public String getItem(int position) {
            return currentTree.get(position);
        }
        
        @Override
        public int getCount() {
            return currentTree.size();
        }
        
        /**
        * Gets the full package name of the Java file.
        *
        * @param position The Java file's position in this adapter's {@code ArrayList}
        * @return The full package name of the Java file
        */
        public String getFullName(int position) {
            String readFile = FileUtil.readFile(getItem(position));
            
            if (!readFile.contains("package ") || !readFile.contains(";")) {
                return getFileNameWoExt(position);
            }
            int packageIndex = readFile.indexOf("package ") + 8;
            
            return readFile.substring(packageIndex, readFile.indexOf(";", packageIndex)) + "." + getFileNameWoExt(position);
        }
        
        /**
        * @param position The Java file's position in this adapter's {@code ArrayList}
        * @return The file's filename with extension
        * @see ManageJavaActivity.MyAdapter#getFileNameWoExt(int)
        */
        public String getFileName(int position) {
            String item = getItem(position);
            return item.substring(item.lastIndexOf("/") + 1);
        }
        
        /**
        * @param position The Java file's position in this adapter's {@code ArrayList}
        * @return The file's filename without extension
        * @see ManageJavaActivity.MyAdapter#getFileName(int)
        */
        public String getFileNameWoExt(int position) {
            return FileUtil.getFileNameNoExtension(getItem(position));
        }
        
        public boolean isFolder(int position) {
            return FileUtil.isDirectory(getItem(position));
        }
        
        public void goEditFile(int position) {
            Intent intent = new Intent();
            
            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), mod.hey.studios.activity.SrcCodeEditor.class);
            } else {
                intent.setClass(getApplicationContext(), mod.hey.studios.code.SrcCodeEditor.class);
            }
            
            intent.putExtra("java", "");
            intent.putExtra("title", getFileName(position));
            intent.putExtra("content", getItem(position));
            
            startActivity(intent);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(Resources.layout.manage_java_item_hs, null);
                if (lastPos == 0) {
                    convertView.setAlpha(0.866f);
                    convertView.animate().setDuration(100).alpha(1);
                } else {
                    convertView.setAlpha(0.425f);
                    convertView.animate().setDuration(256).alpha(1);
                }
            }
            refreshView(convertView, position);
            
            return convertView;
        }
        
        private void refreshView(View convertView, int position) {
            if (convertView != null) {
                TextView name = convertView.findViewById(Resources.id.title);
                ImageView icon = convertView.findViewById(Resources.id.icon);
                ImageView more = convertView.findViewById(Resources.id.more);
                
                name.setText(getFileName(position));
                icon.setImageResource(isFolder(position) ? Resources.drawable.ic_folder_48dp : Resources.drawable.java_96);
                
                Helper.applyRipple(ManageJavaActivity.this, more);
                
                if (position == 0) {
                    more.setVisibility(View.GONE);
                    rippleEffect(convertView, 0xFF90CAF9, 0, 0xFFFFFFFF, (int)getDip(4));
                    
                    icon.setOnClickListener(v -> selectAll());
                } else {
                    more.setVisibility(View.VISIBLE);
                    more.setOnClickListener(v -> itemContextMenu(more, position, Gravity.RIGHT));
                    
                    icon.setOnClickListener(v -> select(getItem(position)));
                    
                    if (isSelected(getItem(position))) {
                        rippleEffect(convertView, 0xFF42A5F5, 0, 0xFF42A5F5, (int)getDip(4));
                    } else {
                        rippleEffect(convertView, 0xFF90CAF9, 0, 0xFFFFFFFF, (int)getDip(4));
                    }
                }
            }
        }
        
        private void itemContextMenu(View v, int position, int gravity) {
            PopupMenu popupMenu = new PopupMenu(ManageJavaActivity.this, v, gravity);
            popupMenu.inflate(Resources.menu.popup_menu_double);
            
            Menu popupMenuMenu = popupMenu.getMenu();
            popupMenuMenu.clear();
            
            if (isSelecting()) {
                popupMenuMenu.add("Copy");
                popupMenuMenu.add("Cut");
                popupMenuMenu.add("Delete");
            } else {
                
                boolean isActivityInManifest = frc.getJavaManifestList().contains(getFullName(position));
                boolean isServiceInManifest = frc.getServiceManifestList().contains(getFullName(position));
                
                if (!isFolder(position)) {
                    if (isActivityInManifest) {
                        popupMenuMenu.add("Remove Activity from manifest");
                    } else if (!isServiceInManifest) {
                        popupMenuMenu.add("Add as Activity to manifest");
                    }
                    
                    if (isServiceInManifest) {
                        popupMenuMenu.add("Remove Service from manifest");
                    } else if (!isActivityInManifest) {
                        popupMenuMenu.add("Add as Service to manifest");
                    }
                    
                    popupMenuMenu.add("Edit");
                    popupMenuMenu.add("Edit with...");
                }
                
                popupMenuMenu.add("Copy");
                popupMenuMenu.add("Cut");
                popupMenuMenu.add("Rename");
                popupMenuMenu.add("Delete");
                
            }
            
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getTitle().toString()) {
                    case "Add as Activity to manifest":
                    frc.getJavaManifestList().add(getFullName(position));
                    FileUtil.writeFile(fpu.getManifestJava(sc_id), new Gson().toJson(frc.listJavaManifest));
                    SketchwareUtil.toast("Successfully added " + getFileNameWoExt(position) + " as Activity to AndroidManifest");
                    break;
                    
                    case "Remove Activity from manifest":
                    if (frc.getJavaManifestList().remove(getFullName(position))) {
                        FileUtil.writeFile(fpu.getManifestJava(sc_id), new Gson().toJson(frc.listJavaManifest));
                        SketchwareUtil.toast("Successfully removed Activity " + getFileNameWoExt(position) + " from AndroidManifest");
                    } else {
                        SketchwareUtil.toast("Activity was not defined in AndroidManifest.");
                    }
                    break;
                    
                    case "Add as Service to manifest":
                    frc.getServiceManifestList().add(getFullName(position));
                    FileUtil.writeFile(fpu.getManifestService(sc_id), new Gson().toJson(frc.listServiceManifest));
                    SketchwareUtil.toast("Successfully added " + getFileNameWoExt(position) + " as Service to AndroidManifest");
                    break;
                    
                    case "Remove Service from manifest":
                    if (frc.getServiceManifestList().remove(getFullName(position))) {
                        FileUtil.writeFile(fpu.getManifestService(sc_id), new Gson().toJson(frc.listServiceManifest));
                        SketchwareUtil.toast("Successfully removed Service " + getFileNameWoExt(position) + " from AndroidManifest");
                    } else {
                        SketchwareUtil.toast("Service was not defined in AndroidManifest.");
                    }
                    break;
                    
                    case "Edit":
                    goEditFile(position);
                    break;
                    
                    case "Edit with...":
                    Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                    launchIntent.setDataAndType(Uri.fromFile(new File(getItem(position))), "text/plain");
                    startActivity(launchIntent);
                    break;
                    
                    case "Rename":
                    if (FileUtil.isDirectory(getItem(position)) && fpu.getPathJava(sc_id).concat("/").concat(getIntent().getStringExtra("pkgName").replace(".", "/")).concat("/").startsWith(getItem(position).concat("/"))) {
                        SketchwareUtil.toastError("You can't rename Main Folder!");
                        copyMode = false;
                        refresh();
                        return false;
                    }
                    showRenameDialog(position);
                    break;
                    
                    case "Delete":
                    if (!isSelecting()) {
                        select(getItem(position));
                    }
                    showDeleteDialog(selectedPaths.toArray(new String[0]));
                    selectedPaths.clear();
                    refresh();
                    break;
                    
                    case "Copy":
                    if (!isSelecting()) {
                        select(getItem(position));
                    }
                    copiedPaths = selectedPaths.toArray(new String[0]);
                    copyMode = true;
                    cut = false;
                    selectedPaths.clear();
                    refresh();
                    break;
                    
                    case "Cut":
                    if (!isSelecting()) {
                        select(getItem(position));
                    }
                    copiedPaths = selectedPaths.toArray(new String[0]);
                    
                    for (String path : copiedPaths) {
                        boolean isFolder = FileUtil.isDirectory(path);
                        boolean isMainDir = fpu.getPathJava(sc_id).concat("/").concat(getIntent().getStringExtra("pkgName").replace(".", "/")).concat("/").startsWith(path.concat("/"));
                        if (isFolder && isMainDir) {
                            SketchwareUtil.toastError("You can't cut Main Folder!");
                            copyMode = false;
                            selectedPaths.clear();
                            refresh();
                            return false;
                        }
                    }
                    copyMode = true;
                    cut = true;
                    selectedPaths.clear();
                    refresh();
                    break;
                    
                    default:
                    return false;
                }
                
                return true;
            });
            if (position != 0)
            popupMenu.show();
        }
    }
}
