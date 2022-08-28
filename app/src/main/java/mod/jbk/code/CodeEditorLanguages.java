package mod.jbk.code;

import android.content.res.AssetManager;

import com.besome.sketch.SketchApplication;

import org.eclipse.tm4e.core.internal.theme.reader.ThemeReader;

import java.io.InputStreamReader;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
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
            kotlinLanguage = TextMateLanguage.create("kotlin.tmLanguage",
                    assets.open("textmate/kotlin.tmLanguage"),
                    ThemeReader.readThemeSync("dracula.json", assets.open("textmate/themes/dracula.json")));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to create Kotlin TextMate language, using empty one as default Kotlin language", e);
            kotlinLanguage = new EmptyLanguage();
        }
        KOTLIN = kotlinLanguage;

        Language xmlLanguage;
        try {
            xmlLanguage = TextMateLanguage.create("xml.tmLanguage.json",
                    assets.open("textmate/xml.tmLanguage.json"),
                    ThemeReader.readThemeSync("dracula.json", assets.open("textmate/themes/dracula.json")));
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to create XML TextMate language, using empty one as default XML language", e);
            xmlLanguage = new EmptyLanguage();
        }
        XML = xmlLanguage;
    }
}
