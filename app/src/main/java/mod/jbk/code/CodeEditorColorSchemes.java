package mod.jbk.code;

import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.jbk.util.LogUtil;

public class CodeEditorColorSchemes {

    private static final String TAG = "CodeEditorColorSchemes";

    public static final EditorColorScheme DRACULA;
    public static final EditorColorScheme GITHUB;

    static {
        AssetManager assets = SketchApplication.getContext().getAssets();

        EditorColorScheme draculaScheme;
        try {
            draculaScheme = new TextMateColorScheme(IThemeSource.fromInputStream(
                    assets.open("textmate/themes/dracula.json"),
                    "dracula.json",
                    null));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to read Darcula theme from assets, using default scheme as default theme");
            draculaScheme = new EditorColorScheme();
        }
        DRACULA = draculaScheme;

        EditorColorScheme gitHubScheme;
        try {
            gitHubScheme = new TextMateColorScheme(IThemeSource.fromInputStream(
                    assets.open("textmate/themes/GitHub.tmTheme"),
                    "GitHub.tmTheme",
                    null));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to read Darcula theme from assets, using default scheme as default theme");
            gitHubScheme = new EditorColorScheme();
        }
        GITHUB = gitHubScheme;
    }
}
