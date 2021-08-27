package mod.hey.studios.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;

import com.sketchware.remod.Resources;

import io.github.rosemoe.editor.langs.java.JavaLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;
import io.github.rosemoe.editor.widget.EditorColorScheme;

public class SourceCodeDialog {

    public static void show(Context context, String code) {
        CodeEditor codeEditor = new CodeEditor(context);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(code);
        codeEditor.setTextSize(14);
        codeEditor.setWordwrap(false);
        codeEditor.setAutoCompletionEnabled(false);
        codeEditor.setEditable(false);
        codeEditor.setColorScheme(new EditorColorScheme());

        new AlertDialog.Builder(context)
                .setTitle("Source code")
                .setIcon(Resources.drawable.code_icon)
                .setView(codeEditor)
                .setPositiveButton(Resources.string.common_word_close, null)
                .show();
    }
}
