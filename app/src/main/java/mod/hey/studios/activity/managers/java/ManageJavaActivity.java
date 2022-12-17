package mod.hey.studios.activity.managers.java;

import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.code.SrcCodeEditorLegacy;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

public class ManageJavaActivity extends Activity {

    // works for both Java & Kotlin files
    private static final String PACKAGE_DECL_REGEX = "package (.*?);?\\n";

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

    private static final String KT_ACTIVITY_TEMPLATE =
            "package %s\n" +
                    "\n" +
                    "import android.app.Activity\n" +
                    "import android.os.Bundle\n" +
                    "\n" +
                    "class %s : Activity() {\n" +
                    "\n" +
                    "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                    "        super.onCreate(savedInstanceState)\n" +
                    "    }" +
                    "\n" +
                    "}\n";

    private static final String KT_CLASS_TEMPLATE =
            "package %s\n" +
                    "\n" +
                    "class %s {\n" +
                    "    \n" +
                    "}\n";

    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private GridView gridView;
    private MyAdapter adapter;
    private String sc_id;
    private TextView noteNoFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_file);

        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();
        frc = new FileResConfig(sc_id);
        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathJava(sc_id)).getPath();
        refresh();
    }

    @Override
    public void onBackPressed() {
        if (Objects.equals(
                Uri.parse(current_path).getPath(),
                Uri.parse(fpu.getPathJava(sc_id)).getPath())) {
            super.onBackPressed();
        } else {
            current_path = current_path.substring(0, current_path.lastIndexOf("/"));
            refresh();
        }
    }

    private void setupUI() {
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);

        FloatingActionButton fab = findViewById(R.id.fab_plus);
        fab.setOnClickListener(v -> showCreateDialog());

        gridView = findViewById(R.id.list_file);
        gridView.setNumColumns(1);
        noteNoFiles = findViewById(R.id.text_info);
        noteNoFiles.setText("No files");

        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        title.setText(R.string.text_title_menu_java);

        loadFile.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(loadFile);
        loadFile.setOnClickListener(v -> showLoadDialog());
    }

    private String getCurrentPkgName() {
        String pkgName = getIntent().getStringExtra("pkgName");

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
            return replace.isEmpty() ? pkgName : pkgName + "." + replace;
        } catch (Exception e) {
            return pkgName;
        }
    }

    private RadioButton createRadioButton(CharSequence text, int id) {
        RadioButton r = new RadioButton(this);
        r.setText(text);
        r.setId(id);

        return r;
    }

    private void showCreateDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View root = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);

        EditText inputName = root.findViewById(R.id.dialog_edittext_name);
        RadioGroup radio_fileType = root.findViewById(R.id.dialog_radio_filetype);

        ((TextView) root.findViewById(R.id.dialog_radio_filetype_class)).setText("Java Class");
        ((TextView) root.findViewById(R.id.dialog_radio_filetype_activity)).setText("Java Activity");

        // Literally the worst way of doing this but editing resources.arsc is much more painful
        final int dialog_radio_filetype_kt_class = 1001;
        final int dialog_radio_filetype_kt_activity = 1001 + 1;
        {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            radio_fileType.addView(
                    createRadioButton("Kotlin Class", dialog_radio_filetype_kt_class),
                    layoutParams
            );

            radio_fileType.addView(
                    createRadioButton("Kotlin Activity", dialog_radio_filetype_kt_activity),
                    layoutParams
            );
        }

        root.findViewById(R.id.dialog_text_cancel)
                .setOnClickListener(Helper.getDialogDismissListener(dialog));
        root.findViewById(R.id.dialog_text_save)
                .setOnClickListener(v -> {
                    if (inputName.getText().toString().isEmpty()) {
                        SketchwareUtil.toastError("Invalid file name");
                        return;
                    }

                    String name = inputName.getText().toString();
                    String packageName = getCurrentPkgName();

                    String extension;
                    String newFileContent;
                    int checkedRadioButtonId = radio_fileType.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.dialog_radio_filetype_class) {
                        newFileContent = String.format(CLASS_TEMPLATE, packageName, name);
                        extension = ".java";
                    } else if (checkedRadioButtonId == R.id.dialog_radio_filetype_activity) {
                        newFileContent = String.format(ACTIVITY_TEMPLATE, packageName, name);
                        extension = ".java";
                    } else if (checkedRadioButtonId == dialog_radio_filetype_kt_class) {
                        newFileContent = String.format(KT_CLASS_TEMPLATE, packageName, name);
                        extension = ".kt";
                    } else if (checkedRadioButtonId == dialog_radio_filetype_kt_activity) {
                        newFileContent = String.format(KT_ACTIVITY_TEMPLATE, packageName, name);
                        extension = ".kt";
                    } else if (checkedRadioButtonId == R.id.radio_button_folder) {
                        FileUtil.makeDir(new File(current_path, name).getAbsolutePath());
                        refresh();
                        SketchwareUtil.toast("Folder was created successfully");
                        dialog.dismiss();
                        return;
                    } else {
                        SketchwareUtil.toast("Select a file type");
                        return;
                    }

                    if (Build.VERSION.SDK_INT < 26 && ".kt".equals(extension)) {
                        SketchwareUtil.toast("Unfortunately you cannot use Kotlin " +
                                "since kotlinc only works on devices with Android 8 and higher, " +
                                "sorry for the inconvenience!", Toast.LENGTH_LONG);
                        return;
                    }

                    FileUtil.writeFile(new File(current_path, name + extension).getAbsolutePath(), newFileContent);
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
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = Environment.getExternalStorageDirectory();
        properties.offset = Environment.getExternalStorageDirectory();
        properties.extensions = new String[]{"java", "kt"};

        FilePickerDialog pickerDialog = new FilePickerDialog(this, properties);

        pickerDialog.setTitle("Select Java/Kotlin file(s)");
        pickerDialog.setDialogSelectionListener(selections -> {

            for (String path : selections) {
                String filename = Uri.parse(path).getLastPathSegment();
                String fileContent = FileUtil.readFile(path);

                if (Build.VERSION.SDK_INT < 26 && filename.endsWith(".kt")) {
                    SketchwareUtil.toast("Unfortunately you cannot use Kotlin " +
                            "since kotlinc only works on devices with Android 8 " +
                            "and higher, skipping " + filename + "!");
                    continue;
                }

                if (fileContent.contains("package ")) {
                    fileContent = fileContent.replaceFirst(PACKAGE_DECL_REGEX,
                            "package " + getCurrentPkgName()
                                    + (filename.endsWith(".java") ? ";" : "")
                                    + "\n");
                }

                FileUtil.writeFile(new File(current_path, filename).getAbsolutePath(),
                        fileContent);
                refresh();
            }
        });

        pickerDialog.show();
    }

    private void showRenameDialog(int position) {
        boolean isFolder = adapter.isFolder(position);
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_input_layout, null);

        EditText filename = root.findViewById(R.id.edittext_change_name);
        filename.setText(adapter.getFileName(position));

        CheckBox renameOccurrences = null;
        if (!isFolder) {
            {
                renameOccurrences = new CheckBox(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(dpToPx(16), 0, dpToPx(16), dpToPx(10));
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

        root.findViewById(R.id.text_cancel).setOnClickListener(Helper.getDialogDismissListener(dialog));
        root.findViewById(R.id.text_save).setOnClickListener(view -> {
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

    private void showDeleteDialog(int position) {
        boolean isInManifest = frc.getJavaManifestList().contains(adapter.getFullName(position));

        new AlertDialog.Builder(this)
                .setTitle(adapter.getFileName(position))
                .setMessage("Are you sure you want to delete this " + (adapter.isFolder(position) ? "folder" : "file") + "? "
                        + (isInManifest ? "This will also remove it from AndroidManifest. " : "")
                        + "This action cannot be reversed!")
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    if (!adapter.isFolder(position) && isInManifest) {
                        frc.getJavaManifestList().remove(adapter.getFullName(position));
                        FileUtil.writeFile(fpu.getManifestJava(sc_id), new Gson().toJson(frc.listJavaManifest));
                    }

                    FileUtil.deleteFile(adapter.getItem(position));
                    refresh();
                    SketchwareUtil.toast("Deleted successfully");
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private void refresh() {
        if (!FileUtil.isExistFile(fpu.getPathJava(sc_id))) {
            FileUtil.makeDir(fpu.getPathJava(sc_id));
            refresh();
        }

        if (!FileUtil.isExistFile(fpu.getManifestJava(sc_id))) {
            FileUtil.writeFile(fpu.getManifestJava(sc_id), "");
            refresh();
        }

        currentTree.clear();
        FileUtil.listDir(current_path, currentTree);
        Helper.sortPaths(currentTree);

        adapter = new MyAdapter();

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
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

        noteNoFiles.setVisibility(currentTree.size() == 0 ? View.VISIBLE : View.GONE);
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
         * Gets the full package name of the Java/Kotlin file.
         *
         * @param position The Java/Kotlin file's position in this adapter's {@code ArrayList}
         * @return The full package name of the Java/Kotlin file
         */
        public String getFullName(int position) {
            String readFile = FileUtil.readFile(getItem(position));

            if (!readFile.contains("package ")) {
                return getFileNameWoExt(position);
            }

            Matcher m = Pattern.compile(PACKAGE_DECL_REGEX).matcher(readFile);
            if (m.find()) {
                return m.group(1) + "." + getFileNameWoExt(position);
            }

            return getFileNameWoExt(position);
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
                intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.manage_java_item_hs, null);
            }

            TextView name = convertView.findViewById(R.id.title);
            ImageView icon = convertView.findViewById(R.id.icon);
            ImageView more = convertView.findViewById(R.id.more);

            name.setText(getFileName(position));

            if (isFolder(position)) {
                icon.setImageResource(R.drawable.ic_folder_48dp);
                icon.setPadding(0, 0, 0, 0);
            } else if (getFileName(position).endsWith(".java")) {
                icon.setImageResource(R.drawable.java_96);
                icon.setPadding(0, 0, 0, 0);
            } else if (getFileName(position).endsWith(".kt")) {
                icon.setImageResource(R.drawable.kotlin_full_color_mark);
                icon.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
            }

            Helper.applyRipple(ManageJavaActivity.this, more);

            more.setOnClickListener(v -> itemContextMenu(more, position, Gravity.RIGHT));

            return convertView;
        }

        private void itemContextMenu(View v, int position, int gravity) {
            PopupMenu popupMenu = new PopupMenu(ManageJavaActivity.this, v, gravity);
            popupMenu.inflate(R.menu.popup_menu_double);

            Menu popupMenuMenu = popupMenu.getMenu();
            popupMenuMenu.clear();

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

            popupMenuMenu.add("Rename");
            popupMenuMenu.add("Delete");

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
                        showRenameDialog(position);
                        break;

                    case "Delete":
                        showDeleteDialog(position);
                        break;

                    default:
                        return false;
                }

                return true;
            });

            popupMenu.show();
        }
    }
}
