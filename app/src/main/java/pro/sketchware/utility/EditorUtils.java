package pro.sketchware.utility;

import static pro.sketchware.utility.ThemeUtils.isDarkThemeEnabled;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

import io.github.rosemoe.sora.lang.Language;
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
    public static EditorColorScheme getMaterialStyledScheme(CodeEditor editor) {
        var scheme = editor.getColorScheme();
        var primary = MaterialColors.getColor(editor, R.attr.colorPrimary);
        var surface = MaterialColors.getColor(editor, R.attr.colorSurface);
        var surfaceContainer = MaterialColors.getColor(editor, R.attr.colorSurfaceContainer);
        var surfaceContainerLow = MaterialColors.getColor(editor, R.attr.colorSurfaceContainerLow);
        var surfaceContainerHighest = MaterialColors.getColor(editor, R.attr.colorSurfaceContainerHighest);
        var onSurface = MaterialColors.getColor(editor, R.attr.colorOnSurface);
        var onSurfaceVariant = MaterialColors.getColor(editor, R.attr.colorOnSurfaceVariant);
        scheme.setColor(EditorColorScheme.KEYWORD, primary);
        scheme.setColor(EditorColorScheme.FUNCTION_NAME, primary);
        scheme.setColor(EditorColorScheme.WHOLE_BACKGROUND, surface);
        scheme.setColor(EditorColorScheme.CURRENT_LINE, surfaceContainerLow);
        scheme.setColor(EditorColorScheme.LINE_NUMBER_PANEL, surfaceContainer);
        scheme.setColor(EditorColorScheme.LINE_NUMBER_BACKGROUND, surfaceContainer);
        scheme.setColor(EditorColorScheme.LINE_DIVIDER, surfaceContainerHighest);
        scheme.setColor(EditorColorScheme.TEXT_NORMAL, onSurface);
        scheme.setColor(EditorColorScheme.SELECTION_INSERT, onSurfaceVariant);
        return scheme;
    }

    @NonNull
    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/jetbrainsmono-regular.ttf");
    }

    public static void loadJavaConfig(CodeEditor editor) {
        loadConfigByLanguage(editor, new JavaLanguage(), false);
    }

    public static void loadXmlConfig(CodeEditor editor) {
        loadConfigByLanguage(editor, CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML), true);
    }

    // todo: use dynamic color scheme for textmate language too
    private static void loadConfigByLanguage(CodeEditor editor, Language language, boolean isTextMate) {
        editor.setEditorLanguage(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(editor.getContext())) {
                editor.setColorScheme(isTextMate ?
                        CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA) : new SchemeDarcula());
            } else {
                editor.setColorScheme(isTextMate ?
                        CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB) : new EditorColorScheme());
            }
        } else {
            editor.setColorScheme(isTextMate ?
                    CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB) : new EditorColorScheme());
        }
        editor.setColorScheme(getMaterialStyledScheme(editor));
        editor.setPinLineNumber(true);
    }
}
