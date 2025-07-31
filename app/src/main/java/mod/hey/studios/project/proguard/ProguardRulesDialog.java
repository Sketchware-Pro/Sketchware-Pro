package mod.hey.studios.project.proguard;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;

public class ProguardRulesDialog extends Dialog implements View.OnClickListener {

    private static final int cancel_id = 626;
    private static final int save_id = 627;
    private final ProguardHandler pg;
    private ViewGroup base;
    private CodeEditorLayout codeEditor;
    private MaterialButton cancel;
    private MaterialButton save;

    public ProguardRulesDialog(Activity act, ProguardHandler pg) {
        super(act);

        this.pg = pg;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.code_editor_zoomin) {
            codeEditor.increaseTextSize();
        } else if (id == R.id.code_editor_zoomout) {
            codeEditor.decreaseTextSize();
        } else if (id == save_id) {
            FileUtil.writeFile(pg.getCustomProguardRules(), codeEditor.getText());
            dismiss();
        } else if (id == cancel_id) {
            dismiss();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_code);

        ((TextView) findViewById(R.id.text_title)).setText("proguard-rules.pro");
        codeEditor = findViewById(R.id.text_content);
        codeEditor.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f));
        base = (ViewGroup) codeEditor.getParent();

        addControl();

        findViewById(R.id.code_editor_zoomin).setOnClickListener(this);
        findViewById(R.id.code_editor_zoomout).setOnClickListener(this);

        codeEditor.start(ColorScheme.JAVA());
        codeEditor.onCreateOptionsMenu(findViewById(R.id.codeeditor_more_options));
        codeEditor.setText(FileUtil.readFile(pg.getCustomProguardRules()));

        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addControl() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setPadding(
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0),
                (int) getDip(0)
        );
        layout.setOrientation(LinearLayout.HORIZONTAL);
        if (codeEditor.dark_theme) {
            layout.setBackgroundColor(Color.parseColor("#FF292929"));
        } else {
            layout.setBackgroundColor(Color.WHITE);
        }

        cancel = new MaterialButton(getContext());
        cancel.setLayoutParams(new LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        ((LayoutParams) cancel.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        cancel.setText(R.string.common_word_cancel);
        cancel.setGravity(Gravity.CENTER);
        cancel.setAlpha(0.8f);
        cancel.setId(cancel_id);
        layout.addView(cancel);
        save = new MaterialButton(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );

        save.setText(R.string.common_word_save);
        save.setId(save_id);
        layout.addView(save);

        base.addView(layout);


        Handler handler = new Handler(Looper.getMainLooper());

        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (codeEditor.dark_theme) {
                    layout.setBackgroundColor(0xff292929);
                } else {
                    layout.setBackgroundColor(Color.WHITE);
                }

                handler.postDelayed(this, 500L);
            }
        };

        handler.postDelayed(task, 500L);
    }

    public GradientDrawable getGD(int var1, int var2, int var3, int var4) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius((float) var1);
        gd.setStroke(var2, var3);
        gd.setColor(var4);
        return gd;
    }
}
