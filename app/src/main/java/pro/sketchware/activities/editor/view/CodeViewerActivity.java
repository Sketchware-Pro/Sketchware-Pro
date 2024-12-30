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

import pro.sketchware.databinding.ActivityCodeViewerBinding;

public class CodeViewerActivity extends BaseAppCompatActivity {
    
    private ActivityCodeViewerBinding binding;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        var code = getIntent().getStringExtra("code");
        binding.editor.setTypefaceText(Typeface.MONOSPACE);
        binding.editor.setTextSize(14);
        binding.editor.setText(code);
        binding.editor.setEditable(false);
        binding.editor.setWordwrap(false);
        loadColorScheme();
    }
    
    private void loadColorScheme() {
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
}