package pro.sketchware.codeproject.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.io.File;
import java.util.HashMap;

import a.a.a.lC;
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
        EditorUtils.loadJavaConfig(binding.editor);
    }

    private void openFile(File file) {
        if (file == null || !file.exists()) return;

        saveCurrentFile();

        currentFile = file;
        String content = FileUtil.readFile(file.getAbsolutePath());
        // TODO: Undo history is lost on file switch because setText() resets the undo stack.
        // A per-file undo manager could preserve history across file switches in a future version.
        binding.editor.setText(content);

        String name = file.getName().toLowerCase();
        if (name.endsWith(".xml")) {
            EditorUtils.loadXmlConfig(binding.editor);
        } else {
            EditorUtils.loadJavaConfig(binding.editor);
        }

        binding.toolbar.setSubtitle(file.getName());
        binding.drawerLayout.closeDrawers();
    }

    private void saveCurrentFile() {
        if (currentFile != null) {
            String content = binding.editor.getText().toString();
            FileUtil.writeFile(currentFile.getAbsolutePath(), content);
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

        saveCurrentFile();
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
            saveCurrentFile();
            super.onBackPressed();
        }
    }
}
