package mod.hilal.saif.asd.asdforall;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;
import com.sketchware.remod.Resources;

import a.a.a.Ss;

import static mod.SketchwareUtil.getDip;

public class AsdAll extends Dialog {

    public Activity activity;
    public LinearLayout b;
    public LinearLayout base;
    public boolean boo;
    public View c;
    public TextView codeE;
    public ImageView d;
    public TextView e;
    public ViewGroup edi;
    public TextView f;
    public FrameLayout g;
    public TextView h;
    public TextView i;
    public String j = "";
    public String k = "";
    public String l = "Yes";
    public LogicEditorActivity lea;
    public String m = "No";
    public int n = -1;
    public int o = 0;
    public View.OnClickListener p = null;
    public View.OnClickListener q = null;
    public View r;
    public Space space;
    public Ss ss;

    public AsdAll(Activity activity2) {
        super(activity2);
        activity = activity2;
    }

    public void a(String str) {
        k = str;
    }

    /**
     * Sets the AsdAll input dialog's title.
     *
     * @param str The new dialog title.
     */
    public void b(String str) {
        j = str;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (o == 0) {
            getWindow().setBackgroundDrawableResource(Resources.drawable.custom_dialog_inset_white);
        } else if (o == 1) {
            getWindow().setBackgroundDrawableResource(Resources.drawable.custom_dialog_inset_light_grey);
        } else if (o == 2) {
            getWindow().setBackgroundDrawableResource(Resources.drawable.custom_dialog_inset_black);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        getWindow().setAttributes(attributes);
        setContentView(Resources.layout.dialog);
        space = new Space(getContext());
        space.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                0,
                1.0f
        ));
        base = findViewById(Resources.id.layout_button);
        codeE = new TextView(getContext());
        codeE.setText("Code Editor");
        codeE.setTextColor(Color.WHITE);
        codeE.setTextSize(14.0f);
        codeE.setPadding(
                (int) getDip(12),
                0,
                0,
                0
        );
        codeE.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.0f
        ));
        codeE.setGravity(17);
        codeE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsdAllEditor asdAllEditor = new AsdAllEditor(activity);
                asdAllEditor.setCon(ss.getArgValue().toString());
                asdAllEditor.show();
                asdAllEditor.saveLis(lea, ss, asdAllEditor);
                asdAllEditor.cancelLis(lea, asdAllEditor);
                dismiss();
            }
        });
        base.addView(space, 0);
        base.addView(codeE, 0);
        b = findViewById(Resources.id.sdialog_root);
        d = findViewById(Resources.id.dialog_img);
        e = findViewById(Resources.id.dialog_title);
        f = findViewById(Resources.id.dialog_msg);
        g = findViewById(Resources.id.custom_view);
        r = base;
        h = findViewById(Resources.id.dialog_btn_yes);
        h.setText(l);
        h.setOnClickListener(p);
        i = findViewById(Resources.id.dialog_btn_no);
        i.setText(m);
        i.setOnClickListener(q);
        if (j.isEmpty()) {
            e.setVisibility(View.GONE);
        } else {
            e.setVisibility(View.VISIBLE);
            e.setText(j);
        }
        if (k.isEmpty()) {
            f.setVisibility(View.GONE);
        } else {
            f.setVisibility(View.VISIBLE);
            f.setText(k);
        }
        if (q == null) {
            i.setVisibility(View.GONE);
        }
        if (p == null) {
            h.setVisibility(View.GONE);
        }
        if (n == -1) {
            d.setVisibility(View.GONE);
        } else {
            d.setImageResource(n);
        }
        if (c != null) {
            g.setVisibility(View.VISIBLE);
            g.addView(c);
            return;
        }
        g.setVisibility(View.GONE);
    }

    public void show() {
        super.show();
        if (p == null && q == null && r != null) {
            r.setVisibility(View.GONE);
        }
    }

    public void a(int i) {
        n = i;
    }

    public void b(String str, View.OnClickListener onClickListener) {
        l = str;
        p = onClickListener;
    }

    public void a(View view) {
        c = view;
    }

    public void a(String str, View.OnClickListener onClickListener) {
        m = str;
        q = onClickListener;
    }

    public void carry(LogicEditorActivity logicEditorActivity, Ss ss2, ViewGroup viewGroup) {
        ss = ss2;
        edi = viewGroup;
        lea = logicEditorActivity;
    }
}