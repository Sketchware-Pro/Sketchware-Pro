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
    public static final String[] LANGUAGES = {"kotlin.tmLanguage", "xml.tmLanguage.json"};
    public static final String SCOPE_NAME_KOTLIN = "source.kotlin";
    public static final String SCOPE_NAME_XML = "text.xml";
    private static final String TAG = "CodeEditorLanguages";

    static {
        FileProviderRegistry.getInstance().addFileProvider(
                new AssetsFileResolver(SketchApplication.getContext().getAssets()));

        for (String language : LANGUAGES) {
            LanguageDefinitionListBuilder builder = new LanguageDefinitionListBuilder();
            String languageName = language.substring(0, language.indexOf('.'));
            builder.language(languageName, languageDefinitionBuilder -> {
                languageDefinitionBuilder.grammar = "textmate/" + language;
                languageDefinitionBuilder.defaultScopeName(language.equals(LANGUAGES[1]) ? "text" : "source");
                return Unit.INSTANCE;
            });

            Throwable t;
            try {
                GrammarRegistry.getInstance().loadGrammars(builder);
                continue;
            } catch (Exception e) {
                t = e;
                // fall-through
            } catch (NoSuchMethodError e) {
                t = e;
                LogUtil.e(TAG, "Probably running on a low API device");
                // fall-through
            }
            LogUtil.e(TAG, "Failed to load language '" + language + "'", t);
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
