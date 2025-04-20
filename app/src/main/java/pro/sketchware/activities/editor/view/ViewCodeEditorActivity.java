package pro.sketchware.activities.editor.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.content.res.AppCompatResources;

import com.besome.sketch.beans.HistoryViewBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.cC;
import a.a.a.jC;
import io.github.rosemoe.sora.widget.CodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.appcompat.ManageAppCompatActivity;
import pro.sketchware.activities.preview.LayoutPreviewActivity;
import pro.sketchware.databinding.ViewCodeEditorBinding;
import pro.sketchware.managers.inject.InjectRootLayoutManager;
import pro.sketchware.tools.ViewBeanParser;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.relativelayout.CircularDependencyDetector;

public class ViewCodeEditorActivity extends BaseAppCompatActivity {
    private ViewCodeEditorBinding binding;
    private CodeEditor editor;

    private SharedPreferences prefs;

    private String sc_id;

    private String content;

    private boolean isEdited = false;

    private ProjectFileBean projectFile;
    private ProjectLibraryBean projectLibrary;

    private InjectRootLayoutManager rootLayoutManager;

    private final OnBackPressedCallback onBackPressedCallback =
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (isContentModified()) {
                        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(ViewCodeEditorActivity.this);
                        dialog.setIcon(R.drawable.ic_warning_96dp);
                        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
                        dialog.setMessage(Helper.getResString(
                                R.string
                                        .src_code_editor_unsaved_changes_dialog_warning_message));

                        dialog.setPositiveButton(Helper.getResString(R.string.common_word_exit), (v, which) -> {
                            v.dismiss();
                            exitWithEditedContent();
                            finish();
                        });

                        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel),
                                null);
                        dialog.show();
                    } else {
                        if (isEdited) {
                            exitWithEditedContent();
                            finish();
                            return;
                        }
                        setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ViewCodeEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = getSharedPreferences("dce", Activity.MODE_PRIVATE);
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }
        rootLayoutManager = new InjectRootLayoutManager(sc_id);
        String title = getIntent().getStringExtra("title");
        projectFile = jC.b(sc_id).b(title);
        projectLibrary = jC.c(sc_id).c();
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("XML Editor");
        getSupportActionBar().setSubtitle(title);
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (onBackPressedCallback.isEnabled()) {
                onBackPressedCallback.handleOnBackPressed();
            }
        });
        content = getIntent().getStringExtra("content");
        editor = binding.editor;
        editor.setTypefaceText(EditorUtils.getTypeface(this));
        editor.setTextSize(14);
        editor.setText(content);
        EditorUtils.loadXmlConfig(editor);
        if (projectFile.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY
                && projectLibrary.isEnabled()) {
            setNote("Use AppCompat Manager to modify attributes for CoordinatorLayout, Toolbar, and other appcompat layout/widget.");
        }
        binding.close.setOnClickListener(v -> {
            prefs.edit().putInt("note_" + sc_id, 1).apply();
            setNote(null);
        });
        binding.noteCard.setOnClickListener(v -> toAppCompat());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, Menu.NONE, "Undo")
                .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_undo))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 1, Menu.NONE, "Redo")
                .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_redo))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, 2, Menu.NONE, "Save")
                .setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_save))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        if (projectFile.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY
                && projectLibrary.isEnabled()) {
            menu.add(Menu.NONE, 3, Menu.NONE, "Edit AppCompat");
        }
        menu.add(Menu.NONE, 4, Menu.NONE, "Reload color schemes");
        menu.add(Menu.NONE, 5, Menu.NONE, "Layout Preview");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0 -> {
                editor.undo();
                return true;
            }
            case 1 -> {
                editor.redo();
                return true;
            }
            case 2 -> {
                save();
                return true;
            }
            case 3 -> {
                toAppCompat();
                return true;
            }
            case 4 -> {
                EditorUtils.loadXmlConfig(binding.editor);
                return true;
            }
            case 5 -> {
                toLayoutPreview();
                return true;
            }
            default -> {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void toAppCompat() {
        var intent = new Intent(getApplicationContext(), ManageAppCompatActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("file_name", getIntent().getStringExtra("title"));
        startActivity(intent);
    }

    private void toLayoutPreview() {
        var intent = new Intent(getApplicationContext(), LayoutPreviewActivity.class);
        intent.putExtras(getIntent());
        intent.putExtra("xml", editor.getText().toString());
        startActivity(intent);
    }

    private void setNote(String note) {
        if (prefs.getInt("note_" + sc_id, 0) < 1 && (note != null && !note.isEmpty())) {
            binding.noteCard.setVisibility(View.VISIBLE);
        } else {
            binding.noteCard.setVisibility(View.GONE);
            return;
        }
        binding.noteCard.setVisibility(View.VISIBLE);
        binding.note.setText(note);
        binding.note.setSelected(true);
    }

    private void save() {
        try {
            if (isContentModified()) {
                // Parse content to validate circular dependencies
                var parser = new ViewBeanParser(editor.getText().toString());
                parser.setSkipRoot(true);

                var parsedLayout = parser.parse();
                for (ViewBean viewBean : parsedLayout) {
                    CircularDependencyDetector detector = new CircularDependencyDetector(parsedLayout, viewBean);
                    for (String attr : viewBean.parentAttributes.keySet()) {
                        String targetId = viewBean.parentAttributes.get(attr);
                        if (!detector.isLegalAttribute(targetId, attr)) {
                            SketchwareUtil.toastError("Circular dependency found in \"" + viewBean.name + "\"\n" +
                                    "Please resolve the issue before saving");
                            return;
                        }
                    }
                }

                // Update content only after validation
                content = editor.getText().toString();
                if (!isEdited) {
                    isEdited = true;
                }
                SketchwareUtil.toast("Saved");
            } else {
                SketchwareUtil.toast("No changes to save");
            }
        } catch (Exception e) {
            SketchwareUtil.toastError(e.toString());
        }

    }

    private boolean isContentModified() {
        return !content.equals(editor.getText().toString());
    }

    private void exitWithEditedContent() {
        String filename = getIntent().getStringExtra("title");
        try {
            var parser = new ViewBeanParser(content);
            parser.setSkipRoot(true);
            var parsedLayout = parser.parse();
            var root = parser.getRootAttributes();
            rootLayoutManager.set(filename, InjectRootLayoutManager.toRoot(root));
            HistoryViewBean bean = new HistoryViewBean();
            bean.actionOverride(parsedLayout, jC.a(sc_id).d(filename));
            var cc = cC.c(sc_id);
            if (!cc.c.containsKey(filename)) {
                cc.e(filename);
            }
            cc.a(filename);
            cc.a(filename, bean);
            // Replace the view beans with the parsed layout
            jC.a(sc_id).c.put(filename, parsedLayout);
            setResult(RESULT_OK);
        } catch (Exception e) {
            SketchwareUtil.toastError(e.toString());
        }
    }
}
