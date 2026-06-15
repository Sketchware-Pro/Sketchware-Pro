package pro.sketchware.activities.editor.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import a.a.a.Lx;
import mod.hey.studios.util.Helper;
import pro.sketchware.databinding.ActivityCodeViewerBinding;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.UI;

public class CodeViewerActivity extends BaseAppCompatActivity {

    public static final String SCHEME_XML = "xml";
    public static final String SCHEME_JAVA = "java";

    private ActivityCodeViewerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);

        binding = ActivityCodeViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        var code = getIntent().getStringExtra("code");
        var scheme = getIntent().getStringExtra("scheme");
        var scId = getIntent().getStringExtra("sc_id");

        // Changes the back navigation arrow to trigger our data return logic
        binding.toolbar.setNavigationOnClickListener(v -> saveAndExit());
        binding.toolbar.setSubtitle(scId);

        binding.editor.setTypefaceText(EditorUtils.getTypeface(this));
        binding.editor.setTextSize(14);
        binding.editor.setText(Lx.j(code, false));
        binding.editor.setWordwrap(false);
        
        // This utility loads syntax colors but defaults Java files to read-only
        loadColorScheme(scheme);

        // Reyaansh's Universal Fix: Overrides the color scheme locking properties
        binding.editor.setEditable(true); 
        binding.editor.setFocusable(true);
        binding.editor.setFocusableInTouchMode(true);

        UI.addSystemWindowInsetToPadding(binding.appBarLayout, true, true, true, false);
        UI.addSystemWindowInsetToMargin(binding.editor, true, false, true, true);
    }

    // Catches the physical phone hardware back button or swipe gesture
    @Override
    public void onBackPressed() {
        saveAndExit();
    }

    // Passes the edited code content block cleanly back to the parent screen
    private void saveAndExit() {
        if (binding.editor != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("code", binding.editor.getText().toString());
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }

    private void loadColorScheme(String scheme) {
        if (scheme.equals(SCHEME_XML)) {
            EditorUtils.loadXmlConfig(binding.editor);
        } else {
            EditorUtils.loadJavaConfig(binding.editor);
        }
    }
}
