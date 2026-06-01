package pro.sketchware.codeproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

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
        binding.fileList.setLayoutManager(new LinearLayoutManager(this));
        binding.fileList.setAdapter(fileAdapter);
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
                        binding.toolbar.setSubtitle(project.getProjectName());
                        promptInstallApk(apk);
                    }
                    isBuilding = false;
                    setBuildMenuEnabled(true);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    if (!isFinishing()) {
                        binding.toolbar.setSubtitle(project.getProjectName());
                        Toast.makeText(CodeProjectActivity.this,
                                getString(R.string.code_project_build_failed) + ": " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    isBuilding = false;
                    setBuildMenuEnabled(true);
                });
            }
        }).start();
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
        if (logcatVisible) {
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
