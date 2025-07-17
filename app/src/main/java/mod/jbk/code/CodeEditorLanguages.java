package mod.jbk.code;

import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.dsl.LanguageDefinitionListBuilder;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
import kotlin.Unit;
import mod.jbk.util.LogUtil;
import pro.sketchware.SketchApplication;

public class CodeEditorLanguages {
    public static final String SCOPE_NAME_KOTLIN = "source.kotlin";
    public static final String SCOPE_NAME_XML = "text.xml";
    private static final String TAG = "CodeEditorLanguages";

    static {
        FileProviderRegistry.getInstance().addFileProvider(new AssetsFileResolver(SketchApplication.getContext().getAssets()));
            Throwable t;
            try {
                GrammarRegistry.getInstance().loadGrammars("textmate/languages.json");
            } catch (Exception e) {
                t = e;
                // fall-through
            } catch (NoSuchMethodError e) {
                t = e;
                LogUtil.e(TAG, "Probably running on a low API device");
                // fall-through
            }
        
    }

    public static Language loadTextMateLanguage(String scopeName) {
        Language language;

        try {
            language = TextMateLanguage.create(scopeName, true);
        } catch (Exception | NoSuchMethodError e) {
            LogUtil.e(TAG, "Failed to create language from scope name '" + scopeName + "', using empty one as default language", e);
            language = new EmptyLanguage();
        }

        return language;
    }
}
