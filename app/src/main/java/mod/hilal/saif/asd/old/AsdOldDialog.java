package mod.hilal.saif.asd.old;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;
import pro.sketchware.R;

public class AsdOldDialog extends Dialog {

    private ViewGroup base;
    private TextView cancel;
    private CodeEditorLayout codeEditor;
    private CodeEditorEditText editor;
    private TextView save;
    private String str;

    public AsdOldDialog(Activity activity) {
        super(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_code);
        codeEditor = findViewById(R.id.text_content);
        TextView zoom_in = findViewById(R.id.code_editor_zoomin);
        zoom_in.setOnClickListener(v -> codeEditor.increaseTextSize());
        TextView zoom_out = findViewById(R.id.code_editor_zoomout);
        zoom_out.setOnClickListener(view -> codeEditor.decreaseTextSize());
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        base = (ViewGroup) codeEditor.getParent();
        base.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4), 0, Color.WHITE, Color.WHITE));
        TextView title = findViewById(R.id.text_title);
        title.setText("Code Editor");
        addControl();
        getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        codeEditor.start(ColorScheme.JAVA());
        codeEditor.setText(str);
        editor = codeEditor.getEditText();
        codeEditor.onCreateOptionsMenu(findViewById(R.id.codeeditor_more_options));
        editor.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editor.setImeOptions(EditorInfo.IME_ACTION_NONE);
    }

    private void addControl() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0)
        );
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (codeEditor.dark_theme) {
            linearLayout.setBackgroundColor(0xff292929);
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
                (int) getDip(8)
        );
        cancel.setText(R.string.common_word_cancel);
        cancel.setTextColor(Color.WHITE);
        cancel.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        cancel.setGravity(17);
        if (codeEditor.dark_theme) {
            cancel.setBackgroundColor(0xff333333);
        } else {
            cancel.setBackgroundColor(0xff008dcd);
        }
        cancel.setTextSize(15f);
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
                (int) getDip(8)
        );
        save.setText(R.string.common_word_save);
        save.setTextColor(Color.WHITE);
        save.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        save.setGravity(17);
        if (codeEditor.dark_theme) {
            save.setBackgroundColor(0xff333333);
        } else {
            save.setBackgroundColor(0xff008dcd);
        }
        save.setTextSize(15f);
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

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (codeEditor.dark_theme) {
                    linearLayout.setBackgroundColor(0xff292929);
                    save.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                    cancel.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                    return;
                }
                linearLayout.setBackgroundColor(Color.WHITE);
                save.setBackground(new DialogButtonGradientDrawable()
                        .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                cancel.setBackground(new DialogButtonGradientDrawable()
                        .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));

                handler.postDelayed(this, 500);
            }
        };

        handler.postDelayed(task, 500);
    }

    public void saveLis(LogicEditorActivity activity, boolean isNumber, Ss ss, AsdOldDialog asdOldDialog) {
        save.setOnClickListener(new AsdOldHandlerCodeEditor(activity, isNumber, ss, asdOldDialog, editor));
    }

    public void cancelLis(LogicEditorActivity activity, AsdOldDialog asdOldDialog) {
        cancel.setOnClickListener(Helper.getDialogDismissListener(asdOldDialog));
    }

    public void setCon(String con) {
        str = con;
    }
}
