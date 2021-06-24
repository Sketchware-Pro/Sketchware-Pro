package mod.hey.studios.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sketchware.remod.Resources;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;

public class SrcCodeEditor extends Activity {

    private CodeEditorLayout codeEditor;
    private final View.OnClickListener changeTextSize = v -> {
        if (v.getId() == Resources.id.code_editor_zoomin) {
            codeEditor.increaseTextSize();
        } else if (v.getId() == Resources.id.code_editor_zoomout) {
            codeEditor.decreaseTextSize();
        }
    };
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("code_editor_pref", 0);

        setContentView(Resources.layout.view_code);
        TextView tv_title = findViewById(Resources.id.text_title);
        codeEditor = findViewById(Resources.id.text_content);

        if (getIntent().hasExtra("java")) {
            codeEditor.start(ColorScheme.JAVA());
        } else if (getIntent().hasExtra("xml")) {
            codeEditor.start(ColorScheme.XML());
        }

        if (getIntent().hasExtra("title") && getIntent().hasExtra("content")) {
            tv_title.setText(getIntent().getStringExtra("title"));
            codeEditor.setText(FileUtil.readFile(getIntent().getStringExtra("content")));
        }

        findViewById(Resources.id.code_editor_zoomin).setOnClickListener(changeTextSize);
        findViewById(Resources.id.code_editor_zoomout).setOnClickListener(changeTextSize);

        codeEditor.onCreateOptionsMenu(findViewById(Resources.id.codeeditor_more_options));
        codeEditor.getEditText().setInputType(655361);
    }

    @Override
    public void onBackPressed() {
        boolean isDialogEnabled = sp.getBoolean("exit_confirmation_dialog", false);
        if (isDialogEnabled) {
            new AlertDialog.Builder(this)
                    .setTitle("Save Changes?")
                    .setMessage("Do you want to save your changes? If not, the file will be reverted.")
                    .setPositiveButton(Resources.string.common_word_save, (dialog, which) -> {
                        FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                        Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNeutralButton("Discard", (dialog, which) -> finish())
                    .setNegativeButton(Resources.string.common_word_cancel, null)
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
