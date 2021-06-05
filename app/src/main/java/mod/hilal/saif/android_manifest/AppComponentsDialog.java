package mod.hilal.saif.android_manifest;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import java.util.Timer;
import java.util.TimerTask;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;

public class AppComponentsDialog extends Dialog {

    private static String APP_COMPONENTS_PATH;
    private final Timer _timer = new Timer();
    private final Activity act;
    private ViewGroup base;
    private TextView cancel;
    private CodeEditorLayout codeEditor;
    private CodeEditorEditText editor;
    private TextView save;

    public AppComponentsDialog(Activity activity, String sc_id) {
        super(activity);
        APP_COMPONENTS_PATH = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(sc_id).concat("/Injection/androidmanifest/app_components.txt");
        this.act = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.view_code);
        codeEditor = findViewById(Resources.id.text_content);
        TextView zoom_in = (TextView) findViewById(Resources.id.code_editor_zoomin);
        zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.increaseTextSize();
            }
        });
        TextView zoom_out = (TextView) findViewById(Resources.id.code_editor_zoomout);
        zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.decreaseTextSize();
            }
        });
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        base = (ViewGroup) codeEditor.getParent();
        base.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4), 0, Color.WHITE, Color.WHITE));
        TextView title = (TextView) findViewById(Resources.id.text_title);
        title.setText("App Components");
        addControl();
        setListeners();
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        codeEditor.start(ColorScheme.XML());
        setCodeEditorText();
        editor = codeEditor.getEditText();
        codeEditor.onCreateOptionsMenu(findViewById(Resources.id.codeeditor_more_options));
        editor.setInputType(655361);
        editor.setImeOptions(1);
    }

    private void addControl() {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.0f));
        linearLayout.setPadding(
                0,
                0,
                0,
                0);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (codeEditor.dark_theme) {
            linearLayout.setBackgroundColor(0xff29292);
        } else {
            linearLayout.setBackgroundColor(Color.WHITE);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        cancel.setText(Resources.string.common_word_cancel);
        cancel.setTextColor(Color.WHITE);
        cancel.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        cancel.setGravity(17);
        if (codeEditor.dark_theme) {
            cancel.setBackgroundColor(0xff333333);
        } else {
            cancel.setBackgroundColor(0xff008dcd);
        }
        cancel.setTextSize(15.0f);
        linearLayout.addView(cancel);
        save = new TextView(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        save.setText(Resources.string.common_word_save);
        save.setTextColor(Color.WHITE);
        save.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8));
        save.setGravity(17);
        if (codeEditor.dark_theme) {
            save.setBackgroundColor(0xff333333);
        } else {
            save.setBackgroundColor(0xff008dcd);
        }
        save.setTextSize(15.0f);
        linearLayout.addView(save);
        if (codeEditor.dark_theme) {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
        } else {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
        }
        save.setElevation(getDip(1));
        cancel.setElevation(getDip(1));
        base.addView(linearLayout);
        TimerTask uu = new TimerTask() {
            @Override
            public void run() {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        if (codeEditor.dark_theme) {
                            linearLayout.setBackgroundColor(0xff292929);
                            save.setBackground(new DialogButtonGradientDrawable()
                                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                            cancel.setBackground(new DialogButtonGradientDrawable()
                                    .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                        } else {
                            linearLayout.setBackgroundColor(Color.WHITE);
                            save.setBackground(new DialogButtonGradientDrawable()
                                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                            cancel.setBackground(new DialogButtonGradientDrawable()
                                    .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                        }
                    }
                });
            }
        };
        _timer.scheduleAtFixedRate(uu, 500, 500);
    }

    private void setListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.writeFile(APP_COMPONENTS_PATH, codeEditor.getText());
                SketchwareUtil.hideKeyboard(editor);
                SketchwareUtil.toast("Saved");
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SketchwareUtil.hideKeyboard(editor);
                dismiss();
            }
        });
    }

    private void setCodeEditorText() {
        if (FileUtil.isExistFile(APP_COMPONENTS_PATH)) {
            String str = FileUtil.readFile(APP_COMPONENTS_PATH);
            codeEditor.setText(str);
        } else {
            codeEditor.setText("");
        }
    }
}
