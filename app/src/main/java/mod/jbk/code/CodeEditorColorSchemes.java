package mod.jbk.code;

import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;

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
            draculaScheme = new TextMateColorScheme(ThemeReader.readThemeSync("dracula.json",
                    assets.open("textmate/themes/dracula.json")));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to read Darcula theme from assets, using default scheme as default theme");
            draculaScheme = new EditorColorScheme();
        }
        DRACULA = draculaScheme;

        EditorColorScheme gitHubScheme;
        try {
            gitHubScheme = new TextMateColorScheme(ThemeReader.readThemeSync("GitHub.tmTheme",
                    assets.open("textmate/themes/GitHub.tmTheme")));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to read Darcula theme from assets, using default scheme as default theme");
            gitHubScheme = new EditorColorScheme();
        }
        GITHUB = gitHubScheme;
    }
}
