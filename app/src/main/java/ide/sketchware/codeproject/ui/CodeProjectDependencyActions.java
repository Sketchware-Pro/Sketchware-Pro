package ide.sketchware.codeproject.ui;

import android.app.Activity;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import ide.sketchware.R;
import ide.sketchware.codeproject.dependencies.DependencyDeclaration;
import ide.sketchware.codeproject.model.CodeProject;
import ide.sketchware.utility.FileUtil;
import ide.sketchware.utility.SketchwareUtil;

final class CodeProjectDependencyActions {

    interface DependencyChangedListener {
        void onDependencyChanged();

        void onSyncRequested();
    }

    private CodeProjectDependencyActions() {
    }

    static void showAddDialog(Activity activity, CodeProject project, DependencyChangedListener listener) {
        EditText input = new EditText(activity);
        input.setSingleLine(true);
        input.setHint(R.string.code_project_dependency_hint);
        new MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.code_project_add_dependency)
                .setView(input)
                .setPositiveButton(R.string.code_project_sync_deps, (dialog, which) -> {
                    if (append(activity, project, input.getText().toString())) {
                        listener.onDependencyChanged();
                        listener.onSyncRequested();
                    }
                })
                .setNeutralButton(R.string.code_project_add_dependency, (dialog, which) -> {
                    if (append(activity, project, input.getText().toString())) {
                        listener.onDependencyChanged();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private static boolean append(Activity activity, CodeProject project, String notation) {
        DependencyDeclaration dependency = DependencyDeclaration.parse(notation);
        if (dependency == null) {
            SketchwareUtil.toast(activity.getString(R.string.code_project_invalid_dependency));
            return false;
        }

        File depsFile = new File(project.getSourcePath(), "dependencies.txt");
        String existing = depsFile.exists() ? FileUtil.readFile(depsFile.getAbsolutePath()) : "";
        if (!existing.endsWith("\n") && !existing.isEmpty()) {
            existing += "\n";
        }
        FileUtil.writeFile(depsFile.getAbsolutePath(), existing + dependency + "\n");
        SketchwareUtil.toast(activity.getString(R.string.code_project_dependency_added));
        return true;
    }
}
