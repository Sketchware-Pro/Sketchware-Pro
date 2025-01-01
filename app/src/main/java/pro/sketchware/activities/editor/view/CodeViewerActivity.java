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
import pro.sketchware.utility.EditorUtils;

public class CodeViewerActivity extends BaseAppCompatActivity {

    public static final String SCHEME_XML = "xml";
    public static final String SCHEME_JAVA = "java";
    
    private ActivityCodeViewerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        var code = getIntent().getStringExtra("code");
        var scheme = getIntent().getStringExtra("scheme");
        var scId = getIntent().getStringExtra("sc_id");
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.toolbar.setSubtitle(scId);
        binding.editor.setTypefaceText(Typeface.MONOSPACE);
        binding.editor.setTextSize(14);
        binding.editor.setText(code);
        binding.editor.setEditable(false);
        binding.editor.setWordwrap(false);
        loadColorScheme(scheme);
        binding.editor.setColorScheme(EditorUtils.getMaterialStyledScheme(binding.editor));
    }

    private void loadColorScheme(final String scheme) {
        if (scheme.equals(SCHEME_XML)) {
            loadXmlScheme();
        } else {
            loadJavaScheme();
        }
    }

    private void loadJavaScheme() {
        binding.editor.setEditorLanguage(new JavaLanguage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(this)) {
                binding.editor.setColorScheme(new SchemeDarcula());
            } else {
                binding.editor.setColorScheme(new EditorColorScheme());
            }
        } else {
            binding.editor.setColorScheme(new EditorColorScheme());
        }
    }

    private void loadXmlScheme() {
        binding.editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isDarkThemeEnabled(this)) {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
            }
        } else {
            binding.editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
        }
    }
}