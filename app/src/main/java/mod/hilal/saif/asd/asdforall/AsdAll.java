package mod.hilal.saif.asd.asdforall;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;

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
        requestWindowFeature(1);
        int i2 = o;
        if (i2 == 0) {
            getWindow().setBackgroundDrawableResource(2131165514);
        } else if (i2 == 1) {
            getWindow().setBackgroundDrawableResource(2131165513);
        } else if (i2 == 2) {
            getWindow().setBackgroundDrawableResource(2131165512);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        getWindow().setAttributes(attributes);
        setContentView(2131427410);
        space = new Space(getContext());
        space.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
        base = findViewById(2131231320);
        codeE = new TextView(getContext());
        codeE.setText("Code Editor");
        codeE.setTextColor(-1);
        codeE.setTextSize((float) 14);
        codeE.setPadding((int) getDip(12), (int) getDip(0), (int) getDip(0), (int) getDip(0));
        codeE.setLayoutParams(new LinearLayout.LayoutParams(-2, -1, 0.0f));
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
        b = findViewById(2131231696);
        d = findViewById(2131230974);
        e = findViewById(2131230976);
        f = findViewById(2131230975);
        g = findViewById(2131230941);
        r = findViewById(2131231320);
        h = findViewById(2131230973);
        h.setText(l);
        h.setOnClickListener(p);
        i = findViewById(2131230972);
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
        int i3 = n;
        if (i3 == -1) {
            d.setVisibility(View.GONE);
        } else {
            d.setImageResource(i3);
        }
        if (c != null) {
            g.setVisibility(View.VISIBLE);
            g.addView(c);
            return;
        }
        g.setVisibility(View.GONE);
    }

    public void show() {
        View view;
        super.show();
        if (p == null && q == null && (view = r) != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void a(int i2) {
        n = i2;
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