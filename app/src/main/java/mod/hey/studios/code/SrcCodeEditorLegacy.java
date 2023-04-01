package mod.hey.studios.code;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sketchware.remod.R;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;

/**
 * Legacy code editor
 */
public class SrcCodeEditorLegacy extends Activity {

    private CodeEditorLayout codeEditor;
    private SharedPreferences sp;

    private final View.OnClickListener changeTextSize = v -> {
        if (v.getId() == R.id.code_editor_zoomin) {
            codeEditor.increaseTextSize();
        } else if (v.getId() == R.id.code_editor_zoomout) {
            codeEditor.decreaseTextSize();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mod.tsd.ui.AppThemeApply.setUpTheme(this);
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("code_editor_pref", 0);

        setContentView(R.layout.view_code);
        TextView tv_title = findViewById(R.id.text_title);
        codeEditor = findViewById(R.id.text_content);

        if (getIntent().hasExtra("java")) {
            codeEditor.start(ColorScheme.JAVA());
        } else if (getIntent().hasExtra("xml")) {
            codeEditor.start(ColorScheme.XML());
        }

        if (getIntent().hasExtra("title") && getIntent().hasExtra("content")) {
            tv_title.setText(getIntent().getStringExtra("title"));
            codeEditor.setText(FileUtil.readFile(getIntent().getStringExtra("content")));
        }

        findViewById(R.id.code_editor_zoomin).setOnClickListener(changeTextSize);
        findViewById(R.id.code_editor_zoomout).setOnClickListener(changeTextSize);

        codeEditor.onCreateOptionsMenu(findViewById(R.id.codeeditor_more_options));
        codeEditor.getEditText().setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }

    @Override
    public void onBackPressed() {
        boolean exitConfirmationDialogEnabled = sp.getBoolean("exit_confirmation_dialog", false);
        if (exitConfirmationDialogEnabled) {
            new AlertDialog.Builder(this)
                    .setTitle("Save Changes?")
                    .setMessage("Do you want to save your changes? If not, the file will be reverted.")
                    .setPositiveButton(R.string.common_word_save, (dialog, which) -> {
                        FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                        Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNeutralButton("Discard", (dialog, which) -> finish())
                    .setNegativeButton(R.string.common_word_cancel, null)
                    .show();
        } else {
            super.onBackPressed();

            FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        codeEditor.onPause();
    }
}
