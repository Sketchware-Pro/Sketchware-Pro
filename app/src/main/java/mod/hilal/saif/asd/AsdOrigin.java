package mod.hilal.saif.asd;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.hilal.saif.asd.old.AsdOldDialog;

public class AsdOrigin extends Dialog {

    public Activity activity;
    public LinearLayout b;
    public LinearLayout base;
    public boolean boo;
    public View c;
    public TextView codeE;
    public ImageView d;
    /**
     * dialog_title
     */
    public TextView e;
    public EditText edi;
    /**
     * dialog_msg
     */
    public TextView f;
    public FrameLayout g;
    /**
     * dialog_btn_yes
     */
    public TextView h;
    /**
     * dialog_btn_no
     */
    public TextView i;
    public String j = "";
    public String k = "";
    /**
     * Text of h (dialog_btn_yes)
     */
    public String l = "Yes";
    public LogicEditorActivity lea;
    /**
     * Text of i (dialog_btn_no)
     */
    public String m = "No";
    /**
     * Resource ID of image in the ImageView of this dialog, -1 if none (the ImageView is hidden with View.GONE then)
     */
    public int n = -1;
    public int o = 0;
    /**
     * View.OnClickListener for h (dialog_btn_yes)
     */
    public View.OnClickListener p = null;
    /**
     * View.OnClickListener for i (dialog_btn_no)
     */
    public View.OnClickListener q = null;
    public View r;
    public Space space;
    public Ss ss;

    public AsdOrigin(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void a(String str) {
        k = str;
    }

    public void b(String str) {
        j = str;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (o == 0) {
            getWindow().setBackgroundDrawableResource(2131165514);
        } else if (o == 1) {
            getWindow().setBackgroundDrawableResource(2131165513);
        } else if (o == 2) {
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
                if (ConfigActivity.isLegacyCeEnabled()) {
                    AsdOldDialog asdOldDialog = new AsdOldDialog(activity);
                    asdOldDialog.setCon(edi.getText().toString());
                    asdOldDialog.show();
                    asdOldDialog.saveLis(lea, boo, ss, asdOldDialog);
                    asdOldDialog.cancelLis(lea, asdOldDialog);
                } else {
                    AsdDialog asdDialog = new AsdDialog(activity);
                    asdDialog.setCon(edi.getText().toString());
                    asdDialog.show();
                    asdDialog.saveLis(lea, boo, ss, asdDialog);
                    asdDialog.cancelLis(lea, asdDialog);
                }
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

    public void a(int imageResourceId) {
        n = imageResourceId;
    }

    public void b(String dialog_btn_noText, View.OnClickListener dialog_btn_yesOnClickListener) {
        l = dialog_btn_noText;
        p = dialog_btn_yesOnClickListener;
    }

    public void a(View view) {
        c = view;
    }

    public void a(String dialog_btn_noText, View.OnClickListener dialog_btn_noOnClickListener) {
        m = dialog_btn_noText;
        q = dialog_btn_noOnClickListener;
    }

    public void carry(LogicEditorActivity activity, Ss ss, boolean z, EditText editText) {
        this.ss = ss;
        boo = z;
        edi = editText;
        lea = activity;
    }
}