package mod.jbk.code;

import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.registry.IGrammarSource;
import org.eclipse.tm4e.core.registry.IThemeSource;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import mod.jbk.util.LogUtil;

public class CodeEditorLanguages {

    private static final String TAG = "CodeEditorLanguages";

    public static final Language KOTLIN;
    public static final Language XML;

    static {
        AssetManager assets = SketchApplication.getContext().getAssets();

        Language kotlinLanguage;
        try {
            kotlinLanguage = TextMateLanguage.create(
                    IGrammarSource.fromInputStream(
                            assets.open("textmate/kotlin.tmLanguage"),
                            "kotlin.tmLanguage",
                            null
                    ),
                    IThemeSource.fromInputStream(
                            assets.open("textmate/themes/dracula.json"),
                            "dracula.json",
                            null
                    )
            );
        } catch (Exception | NoSuchMethodError e) {
            LogUtil.e(TAG, "Failed to create Kotlin TextMate language, using empty one as default Kotlin language", e);
            kotlinLanguage = new EmptyLanguage();
        }
        KOTLIN = kotlinLanguage;

        Language xmlLanguage;
        try {
            xmlLanguage = TextMateLanguage.create(
                    IGrammarSource.fromInputStream(
                            assets.open("textmate/xml.tmLanguage.json"),
                            "xml.tmLanguage.json",
                            null
                    ),
                    IThemeSource.fromInputStream(
                            assets.open("textmate/themes/dracula.json"),
                            "dracula.json",
                            null
                    )
            );
        } catch (Exception | NoSuchMethodError e) {
            LogUtil.e(TAG, "Failed to create XML TextMate language, using empty one as default XML language", e);
            xmlLanguage = new EmptyLanguage();
        }
        XML = xmlLanguage;
    }
}
