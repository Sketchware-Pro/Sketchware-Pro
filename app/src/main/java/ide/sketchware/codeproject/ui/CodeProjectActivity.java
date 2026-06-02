package ide.sketchware.codeproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.a.a.lC;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticDetail;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.widget.EditorSearcher;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.style.DiagnosticIndicatorStyle;
import ide.sketchware.R;
import ide.sketchware.codeproject.build.CodeProjectBuilder;
import ide.sketchware.codeproject.build.CompilerErrorParser;
import ide.sketchware.codeproject.dependencies.DependencyDeclaration;
import ide.sketchware.codeproject.dependencies.DependencyResolver;
import ide.sketchware.codeproject.model.CodeProject;
import ide.sketchware.databinding.ActivityCodeProjectBinding;
import ide.sketchware.utility.EditorUtils;
import ide.sketchware.utility.FileUtil;

public class CodeProjectActivity extends BaseAppCompatActivity {

    private ActivityCodeProjectBinding binding;
    private CodeProject project;
    private File currentFile;
    private FileExplorerAdapter fileAdapter;
    private volatile boolean isBuilding = false;
    private volatile boolean isSyncing = false;

    private final List<OpenFileTab> openTabs = new ArrayList<>();
    private int activeTabIndex = -1;
    private FileTabAdapter tabAdapter;
    private boolean ignoreTextChange = false;
    private LogcatPanel logcatPanel;
    private boolean logcatVisible = false;
    private BuildErrorAdapter errorAdapter;
    private final Map<String, List<CompilerErrorParser.CompilerError>> fileErrorMap = new HashMap<>();
    private volatile int searchToken = 0;

    private final ActivityResultLauncher<Intent> settingsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Reload project metadata
                    String scId = getIntent().getStringExtra("sc_id");
                    if (scId != null) {
                        HashMap<String, Object> metadata = lC.b(scId);
                        if (metadata != null) {
                            project = CodeProject.fromMetadata(metadata);
                            binding.toolbar.setTitle(project.getProjectName());
                        }
                    }
                }
            });

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
        setupSearchPanel();
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
                    boolean deleted = deleteRecursive(file);
                    if (!deleted) {
                        Toast.makeText(this, R.string.code_project_operation_failed, Toast.LENGTH_SHORT).show();
                    }
                    // Always check tabs — partial delete may have removed some files
                    for (int i = openTabs.size() - 1; i >= 0; i--) {
                        if (!openTabs.get(i).getFile().exists()) {
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
                // Clear stale inline error highlights on first edit (positions are now invalid)
                if (!fileErrorMap.isEmpty()) {
                    clearInlineErrors();
                }
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

        // Stop any active search to avoid stale match positions against new content
        if (binding.searchPanel.getRoot().getVisibility() == View.VISIBLE) {
            binding.editor.getSearcher().stopSearch();
        }

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

        // Apply inline error diagnostics if this file has errors
        List<CompilerErrorParser.CompilerError> errors = findErrorsForFile(currentFile);
        if (errors != null && !errors.isEmpty()) {
            setDiagnosticsForCurrentFile(errors);
        } else {
            binding.editor.setDiagnostics(new DiagnosticsContainer());
        }

        // Re-run search on new file if search panel is active
        if (binding.searchPanel.getRoot().getVisibility() == View.VISIBLE) {
            EditText searchInput = binding.searchPanel.getRoot().findViewById(R.id.search_input);
            String query = searchInput.getText().toString();
            if (!query.isEmpty()) {
                binding.editor.getSearcher().search(query,
                    new EditorSearcher.SearchOptions(EditorSearcher.SearchOptions.TYPE_NORMAL, true));
            }
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
        } else if (id == R.id.action_search) {
            toggleSearchPanel();
            return true;
        } else if (id == R.id.action_settings) {
            openProjectSettings();
            return true;
        } else if (id == R.id.action_sync_deps) {
            syncDependencies();
            return true;
        }
        return false;
    }

    private void buildProject() {
        if (isBuilding || isSyncing) return;
        isBuilding = true;
        setBuildMenuEnabled(false);
        hideErrorPanel();
        clearInlineErrors();

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
                        clearInlineErrors();
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
                        applyInlineErrors(errorMsg);
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
            MenuItem syncItem = menu.findItem(R.id.action_sync_deps);
            if (syncItem != null) {
                syncItem.setEnabled(enabled);
            }
        }
    }

    /**
     * Parses the compiler error output, groups errors by file path, and applies
     * inline diagnostics (wavy underlines) to the currently active editor tab.
     */
    private void applyInlineErrors(String errorOutput) {
        List<CompilerErrorParser.CompilerError> errors = CompilerErrorParser.parse(errorOutput);
        if (errors.isEmpty()) return;

        fileErrorMap.clear();

        // Group errors by file path
        for (CompilerErrorParser.CompilerError error : errors) {
            String key = error.filePath;
            List<CompilerErrorParser.CompilerError> list = fileErrorMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                fileErrorMap.put(key, list);
            }
            list.add(error);
        }

        // Apply diagnostics to the currently active file
        if (currentFile != null) {
            List<CompilerErrorParser.CompilerError> currentErrors = findErrorsForFile(currentFile);
            if (currentErrors != null && !currentErrors.isEmpty()) {
                setDiagnosticsForCurrentFile(currentErrors);
            }
        }
    }

    /**
     * Finds errors for a given file by checking both the absolute path and the filename
     * suffix against the stored error map keys.
     */
    private List<CompilerErrorParser.CompilerError> findErrorsForFile(File file) {
        if (file == null) return null;

        String absolutePath = file.getAbsolutePath();

        // Direct match
        List<CompilerErrorParser.CompilerError> errors = fileErrorMap.get(absolutePath);
        if (errors != null) return errors;

        // Try matching by path suffix (compiler may report relative paths)
        String fileName = "/" + file.getName();
        for (Map.Entry<String, List<CompilerErrorParser.CompilerError>> entry : fileErrorMap.entrySet()) {
            String errorPath = entry.getKey();
            // Match if the absolute path ends with the error path (relative path reported)
            if (errorPath.length() > 1 && (absolutePath.endsWith("/" + errorPath) || absolutePath.equals(errorPath))) {
                return entry.getValue();
            }
            // Match if the error path ends with /filename (require separator to prevent
            // cross-package false positives like com/foo/Util.java matching com/bar/Util.java)
            if (errorPath.endsWith(fileName)) {
                // Additional check: compare relative paths from source root
                String sourceRoot = project.getSourcePath();
                String relativeFromSource = absolutePath.startsWith(sourceRoot)
                        ? absolutePath.substring(sourceRoot.length()) : absolutePath;
                if (errorPath.endsWith(relativeFromSource) || relativeFromSource.endsWith(errorPath)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Creates DiagnosticRegion objects for each error and sets them on the editor
     * with wavy underline style. Tapping an error region shows the error message as a tooltip.
     */
    private void setDiagnosticsForCurrentFile(List<CompilerErrorParser.CompilerError> fileErrors) {
        DiagnosticsContainer container = new DiagnosticsContainer();
        Content text = binding.editor.getText();
        int lineCount = text.getLineCount();

        for (int i = 0; i < fileErrors.size(); i++) {
            CompilerErrorParser.CompilerError error = fileErrors.get(i);
            int line = error.lineNumber - 1; // Convert to 0-indexed

            // Skip if line is out of range
            if (line < 0 || line >= lineCount) continue;

            int columnCount = text.getColumnCount(line);
            int startIndex;
            int endIndex;

            if (error.columnNumber > 0) {
                // Use column info for precise start
                int col = Math.min(error.columnNumber - 1, columnCount);
                startIndex = text.getCharIndex(line, col);
                endIndex = text.getCharIndex(line, columnCount);
            } else {
                // Highlight entire line
                startIndex = text.getCharIndex(line, 0);
                endIndex = text.getCharIndex(line, columnCount);
            }

            // Ensure we have a valid range (at least 1 char wide, within bounds)
            if (endIndex <= startIndex) {
                endIndex = Math.min(startIndex + 1, text.length());
                if (endIndex <= startIndex) continue; // skip empty last line
            }

            short severity = error.isWarning
                    ? DiagnosticRegion.SEVERITY_WARNING
                    : DiagnosticRegion.SEVERITY_ERROR;

            DiagnosticDetail detail = new DiagnosticDetail(
                    error.message,     // briefMessage
                    error.message,     // detailedMessage
                    null,              // quickfixes
                    null               // extraData
            );

            container.addDiagnostic(new DiagnosticRegion(
                    startIndex, endIndex, severity, (long) i, detail));
        }

        binding.editor.setDiagnosticIndicatorStyle(DiagnosticIndicatorStyle.WAVY_LINE);
        binding.editor.setDiagnostics(container);
    }

    /**
     * Clears all inline error diagnostics from the editor and the error map.
     */
    private void clearInlineErrors() {
        fileErrorMap.clear();
        binding.editor.setDiagnostics(new DiagnosticsContainer());
    }

    @Override
    public void onBackPressed() {
        if (binding.searchPanel.getRoot().getVisibility() == View.VISIBLE) {
            hideSearchPanel();
        } else if (binding.errorPanel.getVisibility() == View.VISIBLE) {
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

    // ==================== Search & Replace ====================

    private void setupSearchPanel() {
        View searchPanel = binding.searchPanel.getRoot();

        EditText searchInput = searchPanel.findViewById(R.id.search_input);
        EditText replaceInput = searchPanel.findViewById(R.id.replace_input);
        View replaceContainer = searchPanel.findViewById(R.id.replace_container);
        View btnToggleReplace = searchPanel.findViewById(R.id.btn_toggle_replace);
        View btnFindNext = searchPanel.findViewById(R.id.btn_find_next);
        View btnReplace = searchPanel.findViewById(R.id.btn_replace);
        View btnReplaceAll = searchPanel.findViewById(R.id.btn_replace_all);
        View btnCloseSearch = searchPanel.findViewById(R.id.btn_close_search);
        View btnSearchInProject = searchPanel.findViewById(R.id.btn_search_in_project);
        android.widget.TextView matchCount = searchPanel.findViewById(R.id.match_count);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                if (query.isEmpty()) {
                    binding.editor.getSearcher().stopSearch();
                    matchCount.setText("");
                } else {
                    binding.editor.getSearcher().search(query,
                            new EditorSearcher.SearchOptions(EditorSearcher.SearchOptions.TYPE_NORMAL, true));
                    // Delay to allow async search to complete before reading match count
                    binding.editor.postDelayed(() -> updateMatchCount(matchCount), 100);
                }
            }
        });

        btnFindNext.setOnClickListener(v -> {
            if (binding.editor.getSearcher().hasQuery()) {
                binding.editor.getSearcher().gotoNext();
                updateMatchCount(matchCount);
            }
        });

        btnToggleReplace.setOnClickListener(v -> {
            if (replaceContainer.getVisibility() == View.GONE) {
                replaceContainer.setVisibility(View.VISIBLE);
            } else {
                replaceContainer.setVisibility(View.GONE);
            }
        });

        btnReplace.setOnClickListener(v -> {
            if (binding.editor.getSearcher().hasQuery()) {
                String replaceText = replaceInput.getText().toString();
                binding.editor.getSearcher().replaceCurrentMatch(replaceText);
                updateMatchCount(matchCount);
            }
        });

        btnReplaceAll.setOnClickListener(v -> {
            if (binding.editor.getSearcher().hasQuery()) {
                String replaceText = replaceInput.getText().toString();
                binding.editor.getSearcher().replaceAll(replaceText);
                updateMatchCount(matchCount);
            }
        });

        btnCloseSearch.setOnClickListener(v -> hideSearchPanel());

        btnSearchInProject.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (!query.isEmpty()) {
                searchInProject(query);
            }
        });
    }

    private void toggleSearchPanel() {
        View panel = binding.searchPanel.getRoot();
        if (panel.getVisibility() == View.VISIBLE) {
            hideSearchPanel();
        } else {
            panel.setVisibility(View.VISIBLE);
            EditText searchInput = panel.findViewById(R.id.search_input);
            searchInput.requestFocus();
        }
    }

    private void hideSearchPanel() {
        View panel = binding.searchPanel.getRoot();
        panel.setVisibility(View.GONE);
        binding.editor.getSearcher().stopSearch();
        android.widget.TextView matchCount = panel.findViewById(R.id.match_count);
        matchCount.setText("");
    }

    private void updateMatchCount(android.widget.TextView matchCount) {
        binding.editor.post(() -> {
            try {
                if (binding.editor.getSearcher().hasQuery()) {
                    int total = binding.editor.getSearcher().getMatchedPositionCount();
                    if (total > 0) {
                        int current = binding.editor.getSearcher().getCurrentMatchedPositionIndex() + 1;
                        matchCount.setText(getString(R.string.code_project_match_count, current, total));
                    } else {
                        matchCount.setText(getString(R.string.code_project_no_results));
                    }
                } else {
                    matchCount.setText("");
                }
            } catch (Exception e) {
                matchCount.setText("");
            }
        });
    }

    private void searchInProject(String query) {
        Toast.makeText(this, R.string.code_project_search_in_project, Toast.LENGTH_SHORT).show();

        final int token = ++searchToken;

        new Thread(() -> {
            List<String> results = new ArrayList<>();
            File sourceRoot = new File(project.getSourcePath());
            searchFilesRecursive(sourceRoot, query, results);

            runOnUiThread(() -> {
                if (isFinishing()) return;
                // Discard stale results if a newer search was started
                if (token != searchToken) return;
                if (results.isEmpty()) {
                    Toast.makeText(this, R.string.code_project_no_results, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Reuse error panel to show search results
                errorAdapter.setErrors(results);
                binding.errorPanel.setVisibility(View.VISIBLE);
            });
        }).start();
    }

    private void searchFilesRecursive(File dir, String query, List<String> results) {
        if (dir == null || !dir.exists()) return;
        File[] files = dir.listFiles();
        if (files == null) return;

        String lowerQuery = query.toLowerCase();
        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesRecursive(file, query, results);
            } else {
                String name = file.getName().toLowerCase();
                if (name.endsWith(".java") || name.endsWith(".kt") || name.endsWith(".xml")) {
                    searchInFile(file, lowerQuery, results);
                }
            }
        }
    }

    private void searchInFile(File file, String lowerQuery, List<String> results) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.toLowerCase().contains(lowerQuery)) {
                    results.add(file.getAbsolutePath() + ":" + lineNumber + ": " + line.trim());
                }
            }
        } catch (IOException e) {
            // Skip unreadable files
        }
    }

    // ==================== Project Settings ====================

    private void openProjectSettings() {
        Intent intent = new Intent(this, ProjectSettingsActivity.class);
        intent.putExtra("sc_id", project.getScId());
        settingsLauncher.launch(intent);
    }

    // ==================== Dependency Sync ====================

    private void syncDependencies() {
        if (isBuilding || isSyncing) return;

        // dependencies.txt lives in the editable source tree (visible in the file
        // explorer). Fall back to the legacy project-root location for old projects.
        File depsFile = new File(project.getSourcePath(), "dependencies.txt");
        if (!depsFile.exists()) {
            File legacy = new File(project.getProjectMyscPath(), "dependencies.txt");
            if (legacy.exists()) {
                depsFile = legacy;
            }
        }
        if (!depsFile.exists()) {
            Toast.makeText(this, R.string.code_project_no_deps_file, Toast.LENGTH_SHORT).show();
            return;
        }

        List<DependencyDeclaration> deps = DependencyResolver.parseDependenciesFile(depsFile);
        if (deps.isEmpty()) {
            Toast.makeText(this, R.string.code_project_no_deps_declared, Toast.LENGTH_SHORT).show();
            return;
        }

        isSyncing = true;
        setBuildMenuEnabled(false);

        // Show progress
        android.app.ProgressDialog progress = new android.app.ProgressDialog(this);
        progress.setMessage(getString(R.string.code_project_syncing_deps));
        progress.setCancelable(false);
        progress.show();

        File resolvedDir = new File(project.getLibsPath(), "resolved");
        // Resolve into a temp dir and swap it in only on success, so a failed or
        // partial sync doesn't wipe the last good set of resolved jars.
        File tempDir = new File(project.getLibsPath(), ".resolved_tmp");
        clearResolvedDir(tempDir);
        tempDir.mkdirs();

        new Thread(() -> {
            DependencyResolver resolver = new DependencyResolver(CodeProjectActivity.this);
            try {
                resolver.resolve(deps, tempDir, new DependencyResolver.ResolveListener() {
                    @Override
                    public void onProgress(String message) {
                        runOnUiThread(() -> {
                            if (!isFinishing() && !isDestroyed()) {
                                progress.setMessage(message);
                            }
                        });
                    }

                    @Override
                    public void onComplete(List<File> resolvedJars) {
                        try {
                            // Swap temp -> resolved on the worker thread before touching UI
                            swapResolvedDir(tempDir, resolvedDir);
                            runOnUiThread(() -> {
                                isSyncing = false;
                                setBuildMenuEnabled(true);
                                if (isFinishing() || isDestroyed()) return;
                                progress.dismiss();
                                Toast.makeText(CodeProjectActivity.this,
                                        getString(R.string.code_project_deps_synced, resolvedJars.size()),
                                        Toast.LENGTH_SHORT).show();
                                refreshFileExplorer();
                            });
                        } catch (IOException e) {
                            onError(e.getMessage() != null ? e.getMessage() : "Failed to install resolved dependencies");
                        }
                    }

                    @Override
                    public void onWarning(String warning) {
                        runOnUiThread(() -> {
                            if (isFinishing() || isDestroyed()) return;
                            showErrorPanel(warning);
                        });
                    }

                    @Override
                    public void onError(String error) {
                        // Keep the previous resolved jars intact; just discard temp
                        clearResolvedDir(tempDir);
                        tempDir.delete();
                        runOnUiThread(() -> {
                            isSyncing = false;
                            setBuildMenuEnabled(true);
                            if (isFinishing() || isDestroyed()) return;
                            progress.dismiss();
                            showErrorPanel(error);
                        });
                    }
                });
            } catch (Exception e) {
                // Defensive: resolve() should report via the listener, but never
                // leave the non-cancelable dialog stuck if it throws unexpectedly.
                clearResolvedDir(tempDir);
                tempDir.delete();
                runOnUiThread(() -> {
                    isSyncing = false;
                    setBuildMenuEnabled(true);
                    if (isFinishing() || isDestroyed()) return;
                    progress.dismiss();
                    showErrorPanel(e.getMessage() != null ? e.getMessage() : "Dependency sync failed");
                });
            }
        }).start();
    }

    /**
     * Replaces the contents of {@code resolvedDir} with the freshly-resolved jars
     * from {@code tempDir}, then removes the temp dir. Called only after a
     * successful resolution so a failed sync never wipes the last good set.
     */
    private void swapResolvedDir(File tempDir, File resolvedDir) throws IOException {
        clearResolvedDir(resolvedDir);
        if (!resolvedDir.exists()) {
            resolvedDir.mkdirs();
        }
        File[] files = tempDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    File dest = new File(resolvedDir, f.getName());
                    if (dest.exists()) dest.delete();
                    if (!f.renameTo(dest)) {
                        copyFileOrThrow(f, dest);
                        if (!f.delete()) {
                            throw new IOException("Failed to remove temp dependency: " + f.getName());
                        }
                    }
                }
            }
        }
        clearResolvedDir(tempDir);
        tempDir.delete();
    }

    private void copyFileOrThrow(File source, File dest) throws IOException {
        File parent = dest.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Failed to create dependency output directory");
        }
        try (FileInputStream input = new FileInputStream(source);
             FileOutputStream output = new FileOutputStream(dest, false)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
        }
    }

    /**
     * Removes all .jar files from the resolved-dependencies directory so a fresh
     * sync reflects exactly the current dependencies.txt.
     */
    private void clearResolvedDir(File resolvedDir) {
        if (resolvedDir.exists() && resolvedDir.isDirectory()) {
            File[] files = resolvedDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
        }
    }
}
