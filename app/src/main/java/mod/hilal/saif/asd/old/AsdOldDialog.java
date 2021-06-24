package mod.hilal.saif.asd.old;

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

import com.besome.sketch.editor.LogicEditorActivity;

import java.util.Timer;
import java.util.TimerTask;
import com.sketchware.remod.Resources;

import a.a.a.Ss;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hilal.saif.asd.DialogButtonGradientDrawable;

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
        setContentView(Resources.layout.view_code);
        codeEditor = findViewById(Resources.id.text_content);
        TextView zoom_in = findViewById(Resources.id.code_editor_zoomin);
        zoom_in.setOnClickListener(v -> codeEditor.increaseTextSize());
        TextView zoom_out = findViewById(Resources.id.code_editor_zoomout);
        zoom_out.setOnClickListener(view -> codeEditor.decreaseTextSize());
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        base = (ViewGroup) codeEditor.getParent();
        base.setBackground(new DialogButtonGradientDrawable()
                .getIns((int) getDip(4), 0, Color.WHITE, Color.WHITE));
        TextView title = findViewById(Resources.id.text_title);
        title.setText("Code Editor");
        addControll();
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        codeEditor.start(ColorScheme.JAVA());
        codeEditor.setText(str);
        editor = codeEditor.getEditText();
        codeEditor.onCreateOptionsMenu(findViewById(Resources.id.codeeditor_more_options));
        editor.setInputType(655361);
        editor.setImeOptions(1);
    }

    public void addControll() {
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
        cancel.setText(Resources.string.common_word_cancel);
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
        save.setText(Resources.string.common_word_save);
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
        uu = new TimerTask() {
            @Override
            public void run() {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (codeEditor.dark_theme) {
                            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
                            save.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -13421773, -13421773));
                            cancel.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -13421773, -13421773));
                            return;
                        }
                        linearLayout.setBackgroundColor(-1);
                        save.setBackground(new GradientDrawable() {
                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) getDip(4), 0, -14575885, -14575885));
                        cancel.setBackground(new GradientDrawable() {
                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) getDip(4), 0, -14575885, -14575885));
                    }
                });
            }
        };
        _timer.scheduleAtFixedRate(uu, (long) 500, (long) 500);
    }

    public void saveLis(LogicEditorActivity activity, boolean isNumber, Ss ss, AsdOldDialog asdOldDialog) {
        save.setOnClickListener(new AsdOldHandlerCodeEditor(activity, isNumber, ss, asdOldDialog, editor));
    }

    public void cancelLis(LogicEditorActivity activity, AsdOldDialog asdOldDialog) {
        cancel.setOnClickListener(new AsdOldHandlerCodeEditorCancel(asdOldDialog));
    }

    public void setCon(String con) {
        str = con;
    }
}
