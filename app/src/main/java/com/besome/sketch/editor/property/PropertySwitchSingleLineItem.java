package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class PropertySwitchSingleLineItem extends LinearLayout implements View.OnClickListener {

    public String a = "";
    public boolean b = false;
    public TextView c;
    public Switch d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public Kw i;

    public PropertySwitchSingleLineItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public final void a(Context context, boolean z) {
        wB.a(context, this, Resources.layout.property_switch_item_singleline);
        c = findViewById(Resources.id.tv_name);
        d = findViewById(Resources.id.switch_value);
        e = findViewById(Resources.id.img_left_icon);
        g = findViewById(Resources.id.property_item);
        h = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public String getKey() {
        return a;
    }

    public void setKey(String str) {
        mB.a(this);
        a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            c.setText(xB.b().a(getResources(), identifier));
            char type = 65535;
            switch (a) {
                case "property_checked":
                    type = 3;
                    break;

                case "property_single_line":
                    type = 0;
                    break;

                case "property_enabled":
                    type = 1;
                    break;

                case "property_clickable":
                    type = 2;
            }
            if (type == 0) {
                f = Resources.drawable.horizontal_line_48;
            } else if (type == 1) {
                f = Resources.drawable.light_on_48;
            } else if (type == 2) {
                f = Resources.drawable.natural_user_interface2_48;
            } else if (type == 3) {
                f = Resources.drawable.ok_48;
            }
            if (h.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(f);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            e.setImageResource(f);
        }
    }

    public boolean getValue() {
        return b;
    }

    public void setValue(boolean value) {
        b = value;
        d.setChecked(value);
    }

    public void onClick(View view) {
        setValue(!b);
        if (i != null) {
            i.a(a, b);
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
}
