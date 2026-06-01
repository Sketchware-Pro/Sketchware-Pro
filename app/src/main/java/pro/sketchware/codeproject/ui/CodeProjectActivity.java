package pro.sketchware.codeproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a.a.a.lC;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import pro.sketchware.R;
import pro.sketchware.codeproject.build.CodeProjectBuilder;
import pro.sketchware.codeproject.model.CodeProject;
import pro.sketchware.databinding.ActivityCodeProjectBinding;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.FileUtil;

public class CodeProjectActivity extends BaseAppCompatActivity {

    private ActivityCodeProjectBinding binding;
    private CodeProject project;
    private File currentFile;
    private FileExplorerAdapter fileAdapter;
    private volatile boolean isBuilding = false;

    private final List<OpenFileTab> openTabs = new ArrayList<>();
    private int activeTabIndex = -1;
    private FileTabAdapter tabAdapter;
    private boolean ignoreTextChange = false;
    private LogcatPanel logcatPanel;
    private boolean logcatVisible = false;
    private BuildErrorAdapter errorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCodeProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String scId = getIntent().getStringExtra("sc_id");
        if (scId == null) {
            finish();
            return;
        }

        HashMap<String, Object> metadata = lC.b(scId);
        if (metadata == null) {
            finish();
            return;
        }

        project = CodeProject.fromMetadata(metadata);

        setupToolbar();
        setupDrawer();
        setupFileExplorer();
        setupEditor();
        setupLogcat();
        setupErrorPanel();
    }

    private void setupToolbar() {
        binding.toolbar.setTitle(project.getProjectName());
        binding.toolbar.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.code_project_open_drawer, R.string.code_project_close_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupFileExplorer() {
        File sourceRoot = new File(project.getSourcePath());
        fileAdapter = new FileExplorerAdapter(sourceRoot, this::openFile);
        fileAdapter.setOnFileLongClickListener(this::onFileLongClick);
        binding.fileList.setLayoutManager(new LinearLayoutManager(this));
        binding.fileList.setAdapter(fileAdapter);

        // Setup "+" button in nav toolbar
        binding.navToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add) {
                showNewItemPopup(binding.navToolbar, new File(project.getSourcePath()));
                return true;
            }
            return false;
        });
    }

    private void onFileLongClick(File file, View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        if (file.isDirectory()) {
            popup.getMenu().add(0, 1, 0, R.string.code_project_new_file);
            popup.getMenu().add(0, 2, 1, R.string.code_project_new_folder);
            popup.getMenu().add(0, 3, 2, R.string.code_project_rename);
            popup.getMenu().add(0, 4, 3, R.string.code_project_delete);
        } else {
            popup.getMenu().add(0, 3, 0, R.string.code_project_rename);
            popup.getMenu().add(0, 4, 1, R.string.code_project_delete);
        }
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                    showNewFileDialog(file);
                    return true;
                case 2:
                    showNewFolderDialog(file);
                    return true;
                case 3:
                    showRenameDialog(file);
                    return true;
                case 4:
                    showDeleteDialog(file);
                    return true;
            }
            return false;
        });
        popup.show();
    }

    private void showNewItemPopup(View anchor, File parentDir) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add(0, 1, 0, R.string.code_project_new_file);
        popup.getMenu().add(0, 2, 1, R.string.code_project_new_folder);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                showNewFileDialog(parentDir);
                return true;
            } else if (item.getItemId() == 2) {
                showNewFolderDialog(parentDir);
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showNewFileDialog(File parentDir) {
        EditText input = new EditText(this);
        input.setHint(R.string.code_project_enter_name);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.code_project_new_file)
                .setView(input)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!isValidFileName(name)) {
                        Toast.makeText(this, R.string.code_project_invalid_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File newFile = new File(parentDir, name);
                    if (newFile.exists()) {
                        Toast.makeText(this, R.string.code_project_file_exists, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        if (!newFile.createNewFile()) {
                            Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    refreshFileExplorer();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showNewFolderDialog(File parentDir) {
        EditText input = new EditText(this);
        input.setHint(R.string.code_project_enter_name);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.code_project_new_folder)
                .setView(input)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!isValidFileName(name)) {
                        Toast.makeText(this, R.string.code_project_invalid_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File newDir = new File(parentDir, name);
                    if (newDir.exists()) {
                        Toast.makeText(this, R.string.code_project_file_exists, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!newDir.mkdirs()) {
                        Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    refreshFileExplorer();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showRenameDialog(File file) {
        EditText input = new EditText(this);
        input.setText(file.getName());
        input.setHint(R.string.code_project_enter_name);
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.code_project_rename)
                .setView(input)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String newName = input.getText().toString().trim();
                    if (!isValidFileName(newName)) {
                        Toast.makeText(this, R.string.code_project_invalid_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File renamed = new File(file.getParentFile(), newName);
                    if (renamed.exists()) {
                        Toast.makeText(this, R.string.code_project_file_exists, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Capture before rename — stat() on the old path won't work after move
                    boolean wasDirectory = file.isDirectory();
                    if (!file.renameTo(renamed)) {
                        Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Update any open tabs pointing to the old file
                    String oldPath = file.getAbsolutePath();
                    for (OpenFileTab tab : openTabs) {
                        String tabPath = tab.getFile().getAbsolutePath();
                        if (tabPath.equals(oldPath)) {
                            tab.setFile(renamed);
                        } else if (wasDirectory && tabPath.startsWith(oldPath + "/")) {
                            // Child of renamed directory — update path
                            String relativePart = tabPath.substring(oldPath.length());
                            tab.setFile(new File(renamed.getAbsolutePath() + relativePart));
                        }
                    }
                    // Refresh currentFile reference and subtitle
                    if (activeTabIndex >= 0 && activeTabIndex < openTabs.size()) {
                        currentFile = openTabs.get(activeTabIndex).getFile();
                        binding.toolbar.setSubtitle(currentFile.getName());
                    }
                    updateTabStrip();
                    refreshFileExplorer();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showDeleteDialog(File file) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.code_project_delete)
                .setMessage(getString(R.string.code_project_confirm_delete, file.getName()))
                .setPositiveButton(R.string.code_project_delete, (dialog, which) -> {
                    if (!deleteRecursive(file)) {
                        Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                    }
                    // Close tabs for this file or any file inside it (if directory)
                    for (int i = openTabs.size() - 1; i >= 0; i--) {
                        String tabPath = openTabs.get(i).getFile().getAbsolutePath();
                        if (tabPath.equals(file.getAbsolutePath()) || tabPath.startsWith(file.getAbsolutePath() + "/")) {
                            closeTab(i);
                        }
                    }
                    refreshFileExplorer();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private boolean deleteRecursive(File fileOrDir) {
        boolean success = true;
        if (fileOrDir.isDirectory()) {
            File[] children = fileOrDir.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (!deleteRecursive(child)) {
                        success = false;
                    }
                }
            }
        }
        if (!fileOrDir.delete()) {
            success = false;
        }
        return success;
    }

    /**
     * Validates a file/folder name. Rejects empty, names with path separators or null bytes.
     */
    private boolean isValidFileName(String name) {
        if (name == null || name.isEmpty()) return false;
        if (name.contains("/") || name.contains("\\") || name.contains("\0")) return false;
        if (name.equals(".") || name.equals("..")) return false;
        return true;
    }

    private void refreshFileExplorer() {
        fileAdapter.refresh(new File(project.getSourcePath()));
    }

    private void setupEditor() {
        binding.editor.setTypefaceText(EditorUtils.getTypeface(this));
        binding.editor.setTextSize(14);
        binding.editor.setEditable(true);
        binding.editor.setWordwrap(false);
        EditorUtils.loadJavaAutoCompleteConfig(binding.editor);
        binding.editor.getComponent(EditorAutoCompletion.class).setEnabled(true);

        tabAdapter = new FileTabAdapter(this::onTabClick, this::onTabClose);
        binding.tabStrip.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.tabStrip.setAdapter(tabAdapter);

        binding.editor.subscribeEvent(ContentChangeEvent.class, (event, unsubscribe) -> {
            if (!ignoreTextChange) {
                markCurrentTabModified();
            }
        });
    }

    private void setupLogcat() {
        logcatPanel = new LogcatPanel();
        logcatPanel.attach(binding.logcatList);
        binding.btnClearLog.setOnClickListener(v -> logcatPanel.clear());
        binding.btnCloseLog.setOnClickListener(v -> toggleLogcat());
    }

    private void setupErrorPanel() {
        errorAdapter = new BuildErrorAdapter();
        errorAdapter.setOnErrorClickListener((filePath, lineNumber) -> {
            File file = new File(filePath);
            // Fallback: if absolute path doesn't exist, try resolving relative to source root
            if (!file.exists()) {
                file = new File(project.getSourcePath(), filePath);
            }
            if (file.exists()) {
                openFile(file);
                // Scroll to line after opening
                binding.editor.post(() -> {
                    if (lineNumber > 0) {
                        binding.editor.setSelection(lineNumber - 1, 0);
                    }
                });
            }
        });
        binding.errorList.setLayoutManager(new LinearLayoutManager(this));
        binding.errorList.setAdapter(errorAdapter);
        binding.btnCloseErrors.setOnClickListener(v -> hideErrorPanel());
    }

    private void showErrorPanel(String errorMessage) {
        String[] lines = errorMessage.split("\\n");
        List<String> errorLines = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                errorLines.add(line);
            }
        }
        errorAdapter.setErrors(errorLines);
        binding.errorPanel.setVisibility(View.VISIBLE);
    }

    private void hideErrorPanel() {
        binding.errorPanel.setVisibility(View.GONE);
        errorAdapter.clear();
    }

    private void toggleLogcat() {
        logcatVisible = !logcatVisible;
        if (logcatVisible) {
            binding.logcatContainer.setVisibility(View.VISIBLE);
            logcatPanel.start();
        } else {
            binding.logcatContainer.setVisibility(View.GONE);
            logcatPanel.stop();
        }
    }

    private void openFile(File file) {
        if (file == null || !file.exists()) return;

        // Check if file is already open
        for (int i = 0; i < openTabs.size(); i++) {
            if (openTabs.get(i).getFile().getAbsolutePath().equals(file.getAbsolutePath())) {
                switchToTab(i);
                binding.drawerLayout.closeDrawers();
                return;
            }
        }

        // Create new tab
        String content = FileUtil.readFile(file.getAbsolutePath());
        OpenFileTab newTab = new OpenFileTab(file, content);
        openTabs.add(newTab);

        // Switch to the new tab
        switchToTab(openTabs.size() - 1);
        binding.drawerLayout.closeDrawers();
    }

    private void switchToTab(int index) {
        if (index < 0 || index >= openTabs.size()) return;

        activeTabIndex = index;
        OpenFileTab tab = openTabs.get(index);
        currentFile = tab.getFile();

        // Load content into editor (Content object preserves undo/redo history)
        ignoreTextChange = true;
        binding.editor.setText(tab.getEditorContent());
        ignoreTextChange = false;

        // Configure language
        String name = currentFile.getName().toLowerCase();
        if (name.endsWith(".xml")) {
            EditorUtils.loadXmlConfig(binding.editor);
        } else if (name.endsWith(".kt")) {
            EditorUtils.loadKotlinConfig(binding.editor);
        } else {
            EditorUtils.loadJavaAutoCompleteConfig(binding.editor);
        }

        // Update UI
        binding.toolbar.setSubtitle(currentFile.getName());
        updateTabStrip();
    }

    private void updateTabStrip() {
        if (openTabs.isEmpty()) {
            binding.tabStrip.setVisibility(View.GONE);
        } else {
            binding.tabStrip.setVisibility(View.VISIBLE);
            tabAdapter.setTabs(openTabs);
            tabAdapter.setActiveIndex(activeTabIndex);
        }
    }

    private void saveCurrentTabToBuffer() {
        // No-op: Content object is shared between editor and tab, always up-to-date
    }

    private void markCurrentTabModified() {
        if (activeTabIndex >= 0 && activeTabIndex < openTabs.size()) {
            OpenFileTab tab = openTabs.get(activeTabIndex);
            if (!tab.isModified()) {
                tab.setModified(true);
                tabAdapter.notifyTabChanged(activeTabIndex);
            }
        }
    }

    private void onTabClick(int position) {
        if (position != activeTabIndex) {
            switchToTab(position);
        }
    }

    private void onTabClose(int position) {
        if (position < 0 || position >= openTabs.size()) return;

        OpenFileTab tab = openTabs.get(position);
        if (tab.isModified()) {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.code_project_unsaved_changes)
                    .setMessage(getString(R.string.code_project_discard_changes, tab.getFile().getName()))
                    .setPositiveButton(R.string.code_project_discard, (dialog, which) -> closeTab(position))
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        } else {
            closeTab(position);
        }
    }

    private void closeTab(int position) {
        openTabs.remove(position);

        if (openTabs.isEmpty()) {
            activeTabIndex = -1;
            currentFile = null;
            ignoreTextChange = true;
            binding.editor.setText(new Content(""));
            ignoreTextChange = false;
            binding.toolbar.setSubtitle(null);
            updateTabStrip();
        } else {
            if (position == activeTabIndex) {
                // Switch to adjacent tab (prefer left, then right)
                int newIndex = (position > 0) ? position - 1 : 0;
                activeTabIndex = -1; // Reset so switchToTab doesn't save stale buffer
                switchToTab(newIndex);
            } else if (position < activeTabIndex) {
                activeTabIndex--;
                updateTabStrip();
            } else {
                updateTabStrip();
            }
        }
    }

    private void saveCurrentFile() {
        if (activeTabIndex >= 0 && activeTabIndex < openTabs.size()) {
            OpenFileTab tab = openTabs.get(activeTabIndex);
            FileUtil.writeFile(tab.getFile().getAbsolutePath(), tab.getContent());
            tab.setModified(false);
            tabAdapter.notifyTabChanged(activeTabIndex);
        }
    }

    private void saveAllModifiedTabs() {
        for (int i = 0; i < openTabs.size(); i++) {
            OpenFileTab tab = openTabs.get(i);
            if (tab.isModified()) {
                FileUtil.writeFile(tab.getFile().getAbsolutePath(), tab.getContent());
                tab.setModified(false);
                tabAdapter.notifyTabChanged(i);
            }
        }
    }

    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_build) {
            buildProject();
            return true;
        } else if (id == R.id.action_save) {
            saveCurrentFile();
            Toast.makeText(this, R.string.code_project_saved, Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_undo) {
            binding.editor.undo();
            return true;
        } else if (id == R.id.action_redo) {
            binding.editor.redo();
            return true;
        } else if (id == R.id.action_logcat) {
            toggleLogcat();
            return true;
        }
        return false;
    }

    private void buildProject() {
        if (isBuilding) return;
        isBuilding = true;
        setBuildMenuEnabled(false);
        hideErrorPanel();

        saveAllModifiedTabs();
        Toast.makeText(this, R.string.code_project_building, Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            CodeProjectBuilder builder = new CodeProjectBuilder(CodeProjectActivity.this, project);
            try {
                File apk = builder.build(new CodeProjectBuilder.BuildProgressListener() {
                    @Override
                    public void onProgress(String message, int step) {
                        runOnUiThread(() -> {
                            if (!isFinishing()) {
                                binding.toolbar.setSubtitle(message);
                            }
                        });
                    }
                });
                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        restoreActiveFileSubtitle();
                        promptInstallApk(apk);
                    }
                    isBuilding = false;
                    setBuildMenuEnabled(true);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        restoreActiveFileSubtitle();
                        String errorMsg = e.getMessage() != null ? e.getMessage() : "Unknown error";
                        showErrorPanel(errorMsg);
                        Toast.makeText(CodeProjectActivity.this,
                                getString(R.string.code_project_build_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                    isBuilding = false;
                    setBuildMenuEnabled(true);
                });
            }
        }).start();
    }

    /**
     * Restores the toolbar subtitle to the currently active file's name. Derived
     * live (not snapshotted) so it stays correct even if the user switched tabs
     * while the build was running.
     */
    private void restoreActiveFileSubtitle() {
        binding.toolbar.setSubtitle(currentFile != null ? currentFile.getName() : null);
    }

    private void promptInstallApk(File apkFile) {
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                .setTitle(R.string.code_project_build_success)
                .setMessage(R.string.code_project_install_prompt)
                .setPositiveButton(R.string.code_project_install, (dialog, which) -> installApk(apkFile))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void installApk(File apkFile) {
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", apkFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, R.string.code_project_no_installer, Toast.LENGTH_SHORT).show();
        }
    }

    private void setBuildMenuEnabled(boolean enabled) {
        Menu menu = binding.toolbar.getMenu();
        if (menu != null) {
            MenuItem buildItem = menu.findItem(R.id.action_build);
            if (buildItem != null) {
                buildItem.setEnabled(enabled);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.errorPanel.getVisibility() == View.VISIBLE) {
            hideErrorPanel();
        } else if (logcatVisible) {
            toggleLogcat();
        } else if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawer(binding.navView);
        } else {
            saveAllModifiedTabs();
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (logcatPanel != null) {
            logcatPanel.stop();
        }
    }
}
