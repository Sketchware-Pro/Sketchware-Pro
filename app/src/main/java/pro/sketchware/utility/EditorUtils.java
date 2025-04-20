package pro.sketchware.utility;

import static pro.sketchware.utility.ThemeUtils.isDarkThemeEnabled;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;
import pro.sketchware.R;

public class EditorUtils {
    EditorUtils() {
    }

    @NonNull
    public static EditorColorScheme getMaterialStyledScheme(final CodeEditor editor) {
        var scheme = editor.getColorScheme();
        var primary = MaterialColors.getColor(editor, R.attr.colorPrimary);
        var surface = MaterialColors.getColor(editor, R.attr.colorSurface);
        var onSurface = MaterialColors.getColor(editor, R.attr.colorOnSurface);
        var onSurfaceVariant = MaterialColors.getColor(editor, R.attr.colorOnSurfaceVariant);
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

    @NonNull
    public static Typeface getTypeface(final Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/jetbrainsmono-regular.ttf");
    }

    public static void loadJavaConfig(final CodeEditor editor) {
        editor.setEditorLanguage(new JavaLanguage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(editor.getContext())) {
                editor.setColorScheme(new SchemeDarcula());
            } else {
                editor.setColorScheme(new EditorColorScheme());
            }
        } else {
            editor.setColorScheme(new EditorColorScheme());
        }
        editor.setColorScheme(getMaterialStyledScheme(editor));
    }

    public static void loadXmlConfig(final CodeEditor editor) {
        editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(editor.getContext())) {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
            }
        } else {
            editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
        }
        editor.setColorScheme(getMaterialStyledScheme(editor));
    }
}
