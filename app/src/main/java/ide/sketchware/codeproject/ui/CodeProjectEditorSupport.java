package ide.sketchware.codeproject.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;

import a.a.a.Lx;
import ide.sketchware.R;
import ide.sketchware.activities.preview.LayoutPreviewActivity;
import ide.sketchware.activities.resourceseditor.ResourcesEditorActivity;
import ide.sketchware.codeproject.model.CodeProject;
import ide.sketchware.tools.ViewBeanParser;
import ide.sketchware.utility.SketchwareUtil;
import ide.sketchware.utility.relativelayout.CircularDependencyDetector;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import mod.hey.studios.code.SrcCodeEditor;

final class CodeProjectEditorSupport {

    static final String PREF_PREFIX = "code_project";

    private CodeProjectEditorSupport() {
    }

    static void applyPreferences(Context context, CodeEditor editor, boolean loadTheme) {
        SrcCodeEditor.loadCESettings(context, editor, PREF_PREFIX, loadTheme);
    }

    static void showSettingsDialog(Activity activity, CodeEditor editor) {
        SharedPreferences pref = activity.getSharedPreferences("hsce", Context.MODE_PRIVATE);
        String[] options = {
                activity.getString(R.string.code_project_word_wrap),
                activity.getString(R.string.code_project_auto_complete),
                activity.getString(R.string.code_project_auto_complete_pairs)
        };
        boolean[] checked = {
                pref.getBoolean(PREF_PREFIX + "_ww", false),
                pref.getBoolean(PREF_PREFIX + "_ac", true),
                pref.getBoolean(PREF_PREFIX + "_acsp", true)
        };

        new MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.code_project_editor_settings)
                .setMultiChoiceItems(options, checked, (dialog, which, isChecked) -> {
                    checked[which] = isChecked;
                    if (which == 0) {
                        editor.setWordwrap(isChecked);
                        pref.edit().putBoolean(PREF_PREFIX + "_ww", isChecked).apply();
                    } else if (which == 1) {
                        editor.getComponent(EditorAutoCompletion.class).setEnabled(isChecked);
                        pref.edit().putBoolean(PREF_PREFIX + "_ac", isChecked).apply();
                    } else if (which == 2) {
                        editor.getProps().symbolPairAutoCompletion = isChecked;
                        pref.edit().putBoolean(PREF_PREFIX + "_acsp", isChecked).apply();
                    }
                })
                .setNeutralButton(R.string.code_project_theme, (dialog, which) ->
                        SrcCodeEditor.showSwitchThemeDialog(activity, editor, (themeDialog, selected) -> {
                            SrcCodeEditor.selectTheme(editor, selected);
                            pref.edit().putInt(PREF_PREFIX + "_theme", selected).apply();
                            themeDialog.dismiss();
                        }))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    static String format(Context context, File file, String source) {
        if (file == null) {
            SketchwareUtil.toast(context.getString(R.string.code_project_no_file_open));
            return null;
        }
        String name = file.getName().toLowerCase();
        if (name.endsWith(".xml")) {
            String formatted = SrcCodeEditor.prettifyXml(source, 4, null);
            if (formatted == null) {
                SketchwareUtil.toast(context.getString(R.string.code_project_format_failed));
            }
            return formatted;
        }
        if (name.endsWith(".java")) {
            try {
                return Lx.j(source, true);
            } catch (Exception e) {
                SketchwareUtil.toast(context.getString(R.string.code_project_format_failed));
                return null;
            }
        }
        SketchwareUtil.toast(context.getString(R.string.code_project_format_unsupported));
        return null;
    }

    static void openLayoutPreview(Activity activity, CodeProject project, File file, String xml) {
        if (!isLayoutXmlFile(project, file)) {
            SketchwareUtil.toast(activity.getString(R.string.code_project_layout_preview_unavailable));
            return;
        }
        String validationError = validateLayoutXml(xml);
        if (validationError != null) {
            SketchwareUtil.toast(validationError);
            return;
        }
        Intent intent = new Intent(activity, LayoutPreviewActivity.class);
        intent.putExtra("sc_id", project.getScId());
        intent.putExtra("title", file.getName());
        intent.putExtra("content", file.getAbsolutePath());
        intent.putExtra("xml", xml);
        intent.putExtra(LayoutPreviewActivity.EXTRA_RESOURCE_ROOT, project.getResPath());
        activity.startActivity(intent);
    }

    static void openResourceEditor(Activity activity, CodeProject project) {
        Intent intent = new Intent(activity, ResourcesEditorActivity.class);
        intent.putExtra("sc_id", project.getScId());
        intent.putExtra(ResourcesEditorActivity.EXTRA_RESOURCE_ROOT, project.getResPath());
        activity.startActivity(intent);
    }

    static boolean isLayoutXmlFile(CodeProject project, File file) {
        if (file == null || !file.getName().toLowerCase().endsWith(".xml")) return false;
        File parent = file.getParentFile();
        return parent != null && parent.getName().startsWith("layout")
                && parent.getParentFile() != null
                && parent.getParentFile().getAbsolutePath().equals(new File(project.getResPath()).getAbsolutePath());
    }

    static String validateLayoutXml(String xml) {
        try {
            var parser = new ViewBeanParser(xml);
            parser.setSkipRoot(true);
            ArrayList<ViewBean> parsedLayout = parser.parse();
            for (ViewBean viewBean : parsedLayout) {
                CircularDependencyDetector detector = new CircularDependencyDetector(parsedLayout, viewBean);
                for (String attr : viewBean.parentAttributes.keySet()) {
                    String targetId = viewBean.parentAttributes.get(attr);
                    if (!detector.isLegalAttribute(targetId, attr)) {
                        return "Circular dependency found in \"" + viewBean.name + "\"";
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
