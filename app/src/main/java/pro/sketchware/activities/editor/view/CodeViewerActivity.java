package pro.sketchware.activities.editor.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import a.a.a.Lx;
import mod.hey.studios.util.Helper;
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
        binding.editor.setTypefaceText(EditorUtils.getTypeface(this));
        binding.editor.setTextSize(14);
        binding.editor.setText(Lx.j(code, false));
        binding.editor.setEditable(false);
        binding.editor.setWordwrap(false);
        loadColorScheme(scheme);
    }

    private void loadColorScheme(final String scheme) {
        if (scheme.equals(SCHEME_XML)) {
            EditorUtils.loadXmlConfig(binding.editor);
        } else {
            EditorUtils.loadJavaConfig(binding.editor);
        }
    }
}