package mod.jbk.code;

import android.content.res.AssetManager;

import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.jbk.util.LogUtil;
import pro.sketchware.SketchApplication;

public class CodeEditorColorSchemes {
    public static final String THEME_DEFAULT = "Default.json";
    public static final String THEME_DRACULA = "dracula.json";
    public static final String THEME_GITHUB = "GitHub.tmTheme";
    public static final String THEME_ECLIPSE = "Eclipse.json";
    public static final String THEME_XCODE = "Xcode.json";
    public static final String THEME_SOLARIZED_LIGHT = "SolarizedLight.json";
    public static final String THEME_MONOKAI = "Monokai.json";
    public static final String THEME_ONE_DARK = "OneDark.json";

    /**
     * All loadable TextMate themes; {@code SrcCodeEditor#KNOWN_TEXTMATE_THEMES} mirrors this order.
     */
    public static final String[] THEMES = {
            THEME_DEFAULT, THEME_DRACULA, THEME_GITHUB, THEME_ECLIPSE,
            THEME_XCODE, THEME_SOLARIZED_LIGHT, THEME_MONOKAI, THEME_ONE_DARK
    };
    private static final String TAG = "CodeEditorColorSchemes";

    static {
        AssetManager assets = SketchApplication.getContext().getAssets();

        FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(assets));
        ThemeRegistry registry = ThemeRegistry.getInstance();

        for (String theme : THEMES) {
            String path = "textmate/themes/" + theme;
            try {
                registry.loadTheme(new ThemeModel(IThemeSource.fromInputStream(
                        FileProviderRegistry.getInstance().tryGetInputStream(path), path, null
                ), theme));
            } catch (Exception e) {
                LogUtil.e(TAG, "Failed to load theme '" + theme + "'", e);
            }
        }
    }

    public static EditorColorScheme loadTextMateColorScheme(String theme) {
        EditorColorScheme scheme;

        try {
            ThemeRegistry.getInstance().setTheme(theme);
            scheme = TextMateColorScheme.create(ThemeRegistry.getInstance());
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to load theme '" + theme + "', using defaults", e);
            scheme = new EditorColorScheme();
        }

        return scheme;
    }

    /**
     * Position of a theme in {@link #THEMES}, or 0 if unknown.
     */
    public static int themeIndex(String theme) {
        for (int i = 0; i < THEMES.length; i++) {
            if (THEMES[i].equals(theme)) {
                return i;
            }
        }
        return 0;
    }
}
