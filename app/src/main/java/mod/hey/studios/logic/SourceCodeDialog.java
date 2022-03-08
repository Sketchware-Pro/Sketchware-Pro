package mod.hey.studios.logic;

import static mod.SketchwareUtil.getDip;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;

import com.sketchware.remod.Resources;

import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class SourceCodeDialog {

    public static void show(Context context, String code) {
        CodeEditor codeEditor = new CodeEditor(context);
        codeEditor.setColorScheme(new EditorColorScheme());
        codeEditor.setEditable(false);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(code);
        codeEditor.setTextSize(12);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setWordwrap(false);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Source code")
                .setIcon(Resources.drawable.code_icon)
                .setPositiveButton(Resources.string.common_word_close, null)
                .create();

        dialog.setView(codeEditor,
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                (int) getDip(8));
        dialog.show();
    }
}
