package mod.hilal.saif.asd.asdforall;

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
import android.view.WindowManager;
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

public class AsdAllEditor extends Dialog {

    public final Activity activity;
    public ViewGroup base;
    public TextView cancel;
    public CodeEditorLayout code_editor;
    public CodeEditorEditText editor;
    public TextView save;
    public String content;
    public TextView title;
    public TextView zoom_in;
    public TextView zoom_out;

    public AsdAllEditor(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_code);
        code_editor = findViewById(R.id.text_content);
        zoom_in = findViewById(R.id.code_editor_zoomin);
        zoom_in.setOnClickListener(v -> code_editor.increaseTextSize());
        zoom_out = findViewById(R.id.code_editor_zoomout);
        zoom_out.setOnClickListener(v -> code_editor.decreaseTextSize());
        code_editor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        ));
        base = (ViewGroup) code_editor.getParent();
        base.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4),
                        0,
                        Color.WHITE,
                        Color.WHITE));
        title = findViewById(R.id.text_title);
        title.setText("Code Editor");
        addControl();
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        );
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        code_editor.start(ColorScheme.JAVA());
        code_editor.setText(content);
        editor = code_editor.getEditText();
        code_editor.onCreateOptionsMenu(findViewById(R.id.codeeditor_more_options));
        editor.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editor.setImeOptions(EditorInfo.IME_ACTION_NONE);
    }

    public void addControl() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.0f
        ));
        linearLayout.setPadding(
                0,
                0,
                0,
                0
        );
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (code_editor.dark_theme) {
            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
        } else {
            linearLayout.setBackgroundColor(-1);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams())
                .setMargins(
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
        if (code_editor.dark_theme) {
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
                1.0f
        ));
        ((LinearLayout.LayoutParams) save.getLayoutParams())
                .setMargins(
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
        if (code_editor.dark_theme) {
            save.setBackgroundColor(0xff333333);
        } else {
            save.setBackgroundColor(0xff008dcd);
        }
        save.setTextSize(15.0f);
        linearLayout.addView(save);
        if (code_editor.dark_theme) {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4),
                            0,
                            0xff333333,
                            0xff333333));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4),
                            0,
                            0xff333333,
                            0xff333333));
        } else {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4),
                            0,
                            0xff2196f3,
                            0xff2196f3));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns((int) getDip(4),
                            0,
                            0xff2196f3,
                            0xff2196f3));
        }
        save.setElevation(getDip(1));
        cancel.setElevation(getDip(1));
        base.addView(linearLayout);
        Handler handler = new Handler(Looper.myLooper());
        Runnable someRunnable = new Runnable() {
            @Override
            public void run() {
                if (code_editor.dark_theme) {
                    linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
                    save.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4),
                                    0,
                                    0xff333333,
                                    0xff333333));
                    cancel.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4),
                                    0,
                                    0xff333333,
                                    0xff333333));
                } else {
                    linearLayout.setBackgroundColor(Color.WHITE);
                    save.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4),
                                    0,
                                    0xff2196f3,
                                    0xff2196f3));
                    cancel.setBackground(new DialogButtonGradientDrawable()
                            .getIns((int) getDip(4),
                                    0,
                                    0xff2196f3,
                                    0xff2196f3));
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(someRunnable, 500);
    }

    public void saveLis(LogicEditorActivity logicEditorActivity, Ss ss) {
        save.setOnClickListener(view -> {
            logicEditorActivity.a(ss, code_editor.getText());
            dismiss();
        });
    }

    public void cancelLis(AsdAllEditor asdAllEditor) {
        cancel.setOnClickListener(Helper.getDialogDismissListener(asdAllEditor));
    }

    public void setCon(String s) {
        content = s;
    }
}
