package mod.hey.studios.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;

@SuppressLint("ResourceType")
public class SrcCodeEditor extends Activity {

    public CodeEditorLayout codeEditor;
    public String content = "";
    public String title = "";
    public TextView tv_title;
    public AlertDialog.Builder mBackDialog;
    public SharedPreferences sp;
    public Boolean isDialogEnabled;

    private final View.OnClickListener changeTextSize = v -> {
        if (v.getId() == 2131232455) {
            codeEditor.increaseTextSize();
        } else if (v.getId() == 2131232456) {
            codeEditor.decreaseTextSize();
        }
    };

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sp = getSharedPreferences("code_editor_pref", 0);
        isDialogEnabled = sp.getBoolean("enable_dialog", false);

        boolean isFirstUse = sp.getBoolean("enable_dialog", false);

        if (!isFirstUse) {
            sp.edit().putString("enable_dialog", "false").apply();
        }

        setContentView(2131427787);
        tv_title = findViewById(2131232366);
        codeEditor = findViewById(2131232367);

        if (getIntent().hasExtra("java")) {
            codeEditor.start(ColorScheme.JAVA());
        } else if (getIntent().hasExtra("xml")) {
            codeEditor.start(ColorScheme.XML());
        }

        if (getIntent().hasExtra("title") && getIntent().hasExtra("content")) {
            tv_title.setText(getIntent().getStringExtra("title"));
            codeEditor.setText(FileUtil.readFile(getIntent().getStringExtra("content")));
        }

        findViewById(2131232455).setOnClickListener(changeTextSize);
        findViewById(2131232456).setOnClickListener(changeTextSize);

        CodeEditorLayout codeEditorLayout = codeEditor;
        codeEditorLayout.onCreateOptionsMenu(findViewById(2131232504));
        codeEditorLayout.getEditText().setInputType(655361);
    }

    @Override
    public void onBackPressed() {
        sp = getSharedPreferences("code_editor_pref", 0);
        isDialogEnabled = sp.getBoolean("enable_dialog", false);

        try {
            if (isDialogEnabled) {
                mBackDialog = new AlertDialog.Builder(this);
                mBackDialog.setTitle("Save Changes?");
                mBackDialog.setMessage("Do you want to save your changes? If not, the file will be reverted.");

                mBackDialog.setPositiveButton("Save", (dialog, which) -> {
                    FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                    Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
                    finish();
                });

                mBackDialog.setNeutralButton("Discard", (dialog, which) -> finish());
                mBackDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                mBackDialog.create().show();

            } else if (!isDialogEnabled) {
                super.onBackPressed();

                FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getApplication(), "ErrorOnBackPressed", Toast.LENGTH_SHORT).show();
            sp.edit().putString("enable_dialog", "false").apply();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        codeEditor.onPause();
    }
}
