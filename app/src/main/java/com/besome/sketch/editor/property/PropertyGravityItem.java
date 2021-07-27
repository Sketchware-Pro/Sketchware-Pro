package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyGravityItem extends RelativeLayout implements View.OnClickListener {

    public String a = "";
    public int b = -1;
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public Kw i;

    public PropertyGravityItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return a;
    }

    public void setKey(String str) {
        a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            c.setText(xB.b().a(getResources(), identifier));
            f = Resources.drawable.gravity_96;
            if (h.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(f);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            e.setImageResource(f);
        }
    }

    public int getValue() {
        return b;
    }

    public void setValue(int value) {
        b = value;
        d.setText(sq.a(value));
    }

    public void onClick(View v) {
        if (!mB.a()) {
            char c2 = 65535;
            switch (a) {
                case "property_gravity":
                    c2 = 0;
                    break;

                case "property_layout_gravity":
                    c2 = 1;
                    break;
            }
            if (c2 == 0 || c2 == 1) {
                a();
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        i = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            g.setVisibility(GONE);
            h.setVisibility(VISIBLE);
        } else {
            g.setVisibility(VISIBLE);
            h.setVisibility(GONE);
        }
    }

    public final void a(Context context, boolean z) {
        wB.a(context, this, Resources.layout.property_selector_item);
        c = findViewById(Resources.id.tv_name);
        d = findViewById(Resources.id.tv_value);
        e = findViewById(Resources.id.img_left_icon);
        g = findViewById(Resources.id.property_item);
        h = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public final void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(c.getText().toString());
        dialog.a(f);
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_gravity);
        CheckBox chk_left = view.findViewById(Resources.id.chk_left);
        CheckBox chk_right = view.findViewById(Resources.id.chk_right);
        CheckBox chk_hcenter = view.findViewById(Resources.id.chk_hcenter);
        CheckBox chk_top = view.findViewById(Resources.id.chk_top);
        CheckBox chk_bottom = view.findViewById(Resources.id.chk_bottom);
        CheckBox chk_vcenter = view.findViewById(Resources.id.chk_vcenter);
        int i2 = b & 112;
        int i3 = b & 7;
        if (i3 == 1) {
            chk_hcenter.setChecked(true);
        } else {
            if ((i3 & 3) == 3) {
                chk_left.setChecked(true);
            }
            if ((i3 & 5) == 5) {
                chk_right.setChecked(true);
            }
        }
        if (i2 == 16) {
            chk_vcenter.setChecked(true);
        } else {
            if ((i2 & 48) == 48) {
                chk_top.setChecked(true);
            }
            if ((i2 & 80) == 80) {
                chk_bottom.setChecked(true);
            }
        }
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), v -> {
            int value = chk_left.isChecked() ? 3 : 0;
            if (chk_right.isChecked()) {
                value |= 5;
            }
            if (chk_hcenter.isChecked()) {
                value |= 1;
            }
            if (chk_top.isChecked()) {
                value |= 48;
            }
            if (chk_bottom.isChecked()) {
                value |= 80;
            }
            if (chk_vcenter.isChecked()) {
                value |= 16;
            }
            setValue(value);
            if (i != null) {
                i.a(a, b);
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
