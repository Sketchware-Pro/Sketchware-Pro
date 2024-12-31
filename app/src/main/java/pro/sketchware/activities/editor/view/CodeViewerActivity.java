package pro.sketchware.activities.editor.view;

import static pro.sketchware.utility.ThemeUtils.isDarkThemeEnabled;

import android.os.Build;
import android.os.Bundle;
import android.graphics.Typeface;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;

import mod.hey.studios.util.Helper;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;

import pro.sketchware.databinding.ActivityCodeViewerBinding;

public class CodeViewerActivity extends BaseAppCompatActivity {

    public static final String SCHEME_XML = "xml";
    public static final String SCHEME_JAVA = "java";
    public static final String SCHEME_KOTLIN = "kotlin";
    
    private ActivityCodeViewerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        var code = getIntent().getStringExtra("code");
        var scheme = getIntent().getStringExtra("scheme");
        var scId = getIntent().getStringExtra("scId");
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.toolbar.setSubtitle(scId);
        binding.editor.setTypefaceText(Typeface.MONOSPACE);
        binding.editor.setTextSize(14);
        binding.editor.setText(code);
        binding.editor.setEditable(false);
        binding.editor.setWordwrap(false);
        loadColorScheme(scheme);
    }

    private void loadColorScheme(final String scheme) {
        
        if (scheme.equals(SCHEME_XML)) {
            loadXmlScheme();
        } else if (scheme.equals(SCHEME_JAVA)) {
            loadJavaScheme();
        } else if (scheme.equals(SCHEME_KOTLIN)) {
            loadkotlinScheme();
        }
    }

    private void loadJavaScheme() {
        binding.editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_JAVA));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(this)) {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
            }
        } else {
            binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
        }
    }
    
    private void loadkotlinScheme() {
        binding.editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_KOTLIN));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(this)) {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
            }
        } else {
            binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
        }
    }

    private void loadXmlScheme() {
        binding.editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(this)) {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
            }
        } else {
            binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_QUIETLIGHT));
        }
    }
}