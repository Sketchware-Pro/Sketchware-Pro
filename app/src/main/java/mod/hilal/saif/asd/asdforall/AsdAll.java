package mod.hilal.saif.asd.asdforall;

import static mod.SketchwareUtil.getDip;

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
import com.sketchware.remod.R;

import a.a.a.Ss;

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
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_white);
        } else if (o == 1) {
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_light_grey);
        } else if (o == 2) {
            getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_inset_black);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.dialog);
        space = new Space(getContext());
        space.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                0,
                1.0f
        ));
        base = findViewById(R.id.layout_button);
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
        codeE.setOnClickListener(v -> {
            AsdAllEditor asdAllEditor = new AsdAllEditor(activity);
            asdAllEditor.setCon(ss.getArgValue().toString());
            asdAllEditor.show();
            asdAllEditor.saveLis(lea, ss, asdAllEditor);
            asdAllEditor.cancelLis(lea, asdAllEditor);
            dismiss();
        });
        base.addView(space, 0);
        base.addView(codeE, 0);
        b = findViewById(R.id.sdialog_root);
        d = findViewById(R.id.dialog_img);
        e = findViewById(R.id.dialog_title);
        f = findViewById(R.id.dialog_msg);
        g = findViewById(R.id.custom_view);
        r = base;
        h = findViewById(R.id.dialog_btn_yes);
        h.setText(l);
        h.setOnClickListener(p);
        i = findViewById(R.id.dialog_btn_no);
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

    @Override
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
