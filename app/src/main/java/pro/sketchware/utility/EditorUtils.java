package pro.sketchware.utility;

import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class EditorUtils {
    EditorUtils() {
    }
    
    @NonNull
    public static EditorColorScheme getMaterialStyledScheme(final CodeEditor editor) {
        var scheme = editor.getColorScheme();
        var primary = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorPrimary);
        var surface = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorSurface);
        var onSurface = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorOnSurface);
        var onSurfaceVariant = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorOnSurfaceVariant);
        scheme.setColor(EditorColorScheme.KEYWORD, primary);
        scheme.setColor(EditorColorScheme.FUNCTION_NAME, primary);
        scheme.setColor(EditorColorScheme.WHOLE_BACKGROUND, surface);
        scheme.setColor(EditorColorScheme.CURRENT_LINE, surface);
        scheme.setColor(EditorColorScheme.LINE_NUMBER_PANEL, surface);
        scheme.setColor(EditorColorScheme.LINE_NUMBER_BACKGROUND, surface);
        scheme.setColor(EditorColorScheme.TEXT_NORMAL, onSurface);
        scheme.setColor(EditorColorScheme.SELECTION_INSERT, onSurfaceVariant);
        return scheme;
    }
}
