package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyIndentItem extends RelativeLayout implements View.OnClickListener {

    public Context a;
    public String b = "";
    public String c = "";
    public View d;
    public View e;
    public ImageView f;
    public int g;
    public TextView h;
    public TextView i;
    public int j;
    public int k;
    public int l;
    public int m;
    public Kw n;

    public PropertyIndentItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return b;
    }

    public void setKey(String str) {
        b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            h.setText(xB.b().a(getResources(), identifier));
            char c2 = 65535;
            switch (b) {
                case "property_padding":
                    c2 = 1;
                    break;

                case "property_margin":
                    c2 = 0;
                    break;
            }
            if (c2 == 0) {
                g = Resources.drawable.insert_white_space_48;
            } else if (c2 == 1) {
                g = Resources.drawable.collect_48;
            }
            if (e.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(g);
                ((TextView) findViewById(Resources.id.tv_title))
                        .setText(xB.b().a(getContext(), identifier));
                return;
            }
            f.setImageResource(g);
        }
    }

    public String getValue() {
        return c;
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            char c2 = 65535;
            switch (b) {
                case "property_padding":
                    c2 = 1;
                    break;

                case "property_margin":
                    c2 = 0;
                    break;
            }
            if (c2 == 0 || c2 == 1) {
                a();
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        n = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            d.setVisibility(GONE);
            e.setVisibility(VISIBLE);
        } else {
            d.setVisibility(VISIBLE);
            e.setVisibility(GONE);
        }
    }

    public final void a(Context context, boolean z) {
        a = context;
        wB.a(context, this, Resources.layout.property_input_item);
        h = findViewById(Resources.id.tv_name);
        i = findViewById(Resources.id.tv_value);
        f = findViewById(Resources.id.img_left_icon);
        d = findViewById(Resources.id.property_item);
        e = findViewById(Resources.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    public void a(int left, int top, int right, int bottom) {
        j = left;
        k = top;
        l = right;
        m = bottom;
        i.setText("left: " + j + ", top: " + k + ", right: " + l + ", bottom: " + m);
    }

    public final void a() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(h.getText().toString());
        aBVar.a(g);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_indent);
        CheckBox chk_pty_all = view.findViewById(Resources.id.chk_pty_all);
        chk_pty_all.setText(xB.b().a(getContext(), Resources.string.common_word_all));
        EditText et_all = view.findViewById(Resources.id.et_all);
        EditText et_left = view.findViewById(Resources.id.et_left);
        EditText et_top = view.findViewById(Resources.id.et_top);
        EditText et_right = view.findViewById(Resources.id.et_right);
        EditText et_bottom = view.findViewById(Resources.id.et_bottom);
        TB ti_all = new TB(a, view.findViewById(Resources.id.ti_all), 0, 999);
        TB ti_left = new TB(a, view.findViewById(Resources.id.ti_left), 0, 999);
        TB ti_right = new TB(a, view.findViewById(Resources.id.ti_right), 0, 999);
        TB ti_top = new TB(a, view.findViewById(Resources.id.ti_top), 0, 999);
        TB ti_bottom = new TB(a, view.findViewById(Resources.id.ti_bottom), 0, 999);
        ti_left.a(String.valueOf(j));
        ti_top.a(String.valueOf(k));
        ti_right.a(String.valueOf(l));
        ti_bottom.a(String.valueOf(m));
        if (j == k && k == l && l == m) {
            ti_all.a(String.valueOf(j));
            chk_pty_all.setChecked(true);
            et_all.setEnabled(true);
            et_left.clearFocus();
            et_top.clearFocus();
            et_right.clearFocus();
            et_bottom.clearFocus();
            et_left.setEnabled(false);
            et_top.setEnabled(false);
            et_right.setEnabled(false);
            et_bottom.setEnabled(false);
        } else {
            et_all.clearFocus();
            et_all.setEnabled(false);
            et_left.setEnabled(true);
            et_top.setEnabled(true);
            et_right.setEnabled(true);
            et_bottom.setEnabled(true);
        }
        chk_pty_all.setOnClickListener(v -> {
            if (chk_pty_all.isChecked()) {
                et_all.setEnabled(true);
                et_left.clearFocus();
                et_top.clearFocus();
                et_right.clearFocus();
                et_bottom.clearFocus();
                et_left.setEnabled(false);
                et_top.setEnabled(false);
                et_right.setEnabled(false);
                et_bottom.setEnabled(false);
            } else {
                et_all.clearFocus();
                et_all.setEnabled(false);
                et_left.setEnabled(true);
                et_top.setEnabled(true);
                et_right.setEnabled(true);
                et_bottom.setEnabled(true);
            }
        });
        et_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ti_left.a(et_all.getText().toString());
                ti_top.a(et_all.getText().toString());
                ti_right.a(et_all.getText().toString());
                ti_bottom.a(et_all.getText().toString());
            }
        });
        aBVar.a(view);
        aBVar.b(xB.b().a(getContext(), Resources.string.common_word_save), v -> {
            if (chk_pty_all.isChecked()) {
                if (ti_all.b() && ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                    int left = Integer.parseInt(et_left.getText().toString());
                    int top = Integer.parseInt(et_top.getText().toString());
                    int right = Integer.parseInt(et_right.getText().toString());
                    int bottom = Integer.parseInt(et_bottom.getText().toString());
                    a(left, top, right, bottom);
                    if (n != null) {
                        n.a(b, new int[]{left, top, right, bottom});
                        aBVar.dismiss();
                    }
                }
            } else if (ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                int left = Integer.parseInt(et_left.getText().toString());
                int top = Integer.parseInt(et_top.getText().toString());
                int right = Integer.parseInt(et_right.getText().toString());
                int bottom = Integer.parseInt(et_bottom.getText().toString());
                a(left, top, right, bottom);
                if (n != null) {
                    n.a(b, new int[]{left, top, right, bottom});
                    aBVar.dismiss();
                }
            }
        });
        aBVar.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }
}
