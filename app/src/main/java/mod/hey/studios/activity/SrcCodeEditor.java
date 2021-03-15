package mod.hey.studios.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;

public class SrcCodeEditor extends Activity implements View.OnClickListener {

    public CodeEditorLayout codeEditor;
    public String content = "";
    public String title = "";
    public TextView tv_title;
    public AlertDialog.Builder mBackDialog;
    public SharedPreferences sharedpref;
    public Boolean isDialogEnabled;

    @Override
    public void onClick(View view) {
        if (view.getId() == 2131232455) {
            codeEditor.increaseTextSize();
        } else if (view.getId() == 2131232456) {
            codeEditor.decreaseTextSize();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.sharedpref = this.getSharedPreferences("code_editor_pref", 0);
        isDialogEnabled = sharedpref.getBoolean("enable_dialog", false);
        Boolean isFirstUse = sharedpref.getBoolean("enable_dialog", false);
        try {
            if (isFirstUse.equals("")) {
                sharedpref.edit().putString("enable_dialog", "false").commit();
            }
        } catch (Exception e) {
            sharedpref.edit().putString("enable_dialog", "false").commit();
        }
        setContentView(2131427787);
        tv_title = (TextView) findViewById(2131232366);
        codeEditor = (CodeEditorLayout) findViewById(2131232367);
        if (getIntent().hasExtra("java")) {
            codeEditor.start(ColorScheme.JAVA());
        } else if (getIntent().hasExtra("xml")) {
            codeEditor.start(ColorScheme.XML());
        }
        if (getIntent().hasExtra("title") && getIntent().hasExtra("content")) {
            tv_title.setText(getIntent().getStringExtra("title"));
            codeEditor.setText(FileUtil.readFile(getIntent().getStringExtra("content")));
        }
        findViewById(2131232455).setOnClickListener(this);
        findViewById(2131232456).setOnClickListener(this);
        CodeEditorLayout codeEditorLayout = codeEditor;
        codeEditorLayout.onCreateOptionsMenu(findViewById(2131232504));
        codeEditorLayout.getEditText().setInputType(655361);
    }

    @Override
    public void onBackPressed() {
        this.sharedpref = this.getSharedPreferences("code_editor_pref", 0);
        isDialogEnabled = sharedpref.getBoolean("enable_dialog", false);
        try {
            if (isDialogEnabled) {
                mBackDialog = new AlertDialog.Builder(this);
                mBackDialog.setTitle("Save Changes ?");
                mBackDialog.setMessage("Do you want to save your changes? If not, the file will be reverted.");
                mBackDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                        Toast.makeText(getApplicationContext(), "File saved", 0).show();
                        finish();
                    }
                });
                mBackDialog.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                mBackDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mBackDialog.create().show();
            } else if (!isDialogEnabled) {
                super.onBackPressed();
                FileUtil.writeFile(getIntent().getStringExtra("content"), codeEditor.getText());
                Toast.makeText(getApplicationContext(), "File saved", 0).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), "ErrorOnBackPressed", Toast.LENGTH_SHORT).show();
            sharedpref.edit().putString("enable_dialog", "false").commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        codeEditor.onPause();
    }
}
