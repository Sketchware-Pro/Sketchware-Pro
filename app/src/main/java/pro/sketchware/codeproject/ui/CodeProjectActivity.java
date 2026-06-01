package pro.sketchware.codeproject.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import a.a.a.lC;
import io.github.rosemoe.sora.event.ContentChangeEvent;
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

        // Save current tab content to buffer (not disk)
        saveCurrentTabToBuffer();

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

        // Save current tab content to buffer
        if (activeTabIndex >= 0 && activeTabIndex < openTabs.size()) {
            openTabs.get(activeTabIndex).setContent(binding.editor.getText().toString());
        }

        activeTabIndex = index;
        OpenFileTab tab = openTabs.get(index);
        currentFile = tab.getFile();

        // Load content into editor
        ignoreTextChange = true;
        binding.editor.setText(tab.getContent());
        ignoreTextChange = false;

        // Configure language
        String name = currentFile.getName().toLowerCase();
        if (name.endsWith(".xml")) {
            EditorUtils.loadXmlConfig(binding.editor);
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
        if (activeTabIndex >= 0 && activeTabIndex < openTabs.size()) {
            openTabs.get(activeTabIndex).setContent(binding.editor.getText().toString());
        }
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

        openTabs.remove(position);

        if (openTabs.isEmpty()) {
            activeTabIndex = -1;
            currentFile = null;
            ignoreTextChange = true;
            binding.editor.setText("");
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
            String content = binding.editor.getText().toString();
            tab.setContent(content);
            FileUtil.writeFile(tab.getFile().getAbsolutePath(), content);
            tab.setModified(false);
            tabAdapter.notifyTabChanged(activeTabIndex);
        }
    }

    private void saveAllModifiedTabs() {
        // Save current editor content to active tab buffer first
        saveCurrentTabToBuffer();

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
                        Toast.makeText(CodeProjectActivity.this, R.string.code_project_build_success, Toast.LENGTH_SHORT).show();
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
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawer(binding.navView);
        } else {
            saveAllModifiedTabs();
            super.onBackPressed();
        }
    }
}
