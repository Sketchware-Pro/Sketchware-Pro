package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.View;
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

public class PropertySizeItem extends RelativeLayout implements View.OnClickListener {

    public Context a;
    public String b = "";
    public int c = 1;
    public TextView d;
    public TextView e;
    public ImageView f;
    public int g;
    public View h;
    public View i;
    public Kw j;

    public PropertySizeItem(Context context, boolean z) {
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
            d.setText(xB.b().a(getResources(), identifier));
            g = Resources.drawable.expand_48;
            if (i.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(g);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            f.setImageResource(g);
        }
    }

    public int getValue() {
        return c;
    }

    public void setValue(int value) {
        c = value;
        TextView textView = e;
        textView.setText(c + " dp");
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            char type = 65535;
            if ("property_divider_height".equals(b)) {
                type = 0;
            }
            if (type == 0) {
                a();
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        j = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            h.setVisibility(GONE);
            i.setVisibility(VISIBLE);
            return;
        }
        h.setVisibility(VISIBLE);
        i.setVisibility(GONE);
    }

    public final void a(Context context, boolean z) {
        a = context;
        wB.a(context, this, Resources.layout.property_input_item);
        d = findViewById(Resources.id.tv_name);
        e = findViewById(Resources.id.tv_value);
        f = findViewById(Resources.id.img_left_icon);
        h = findViewById(Resources.id.property_item);
        i = findViewById(Resources.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    public final void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(d.getText().toString());
        dialog.a(g);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_size);
        TB tb = new TB(a, view.findViewById(Resources.id.ti_input), 0, 999);
        tb.a(String.valueOf(c));
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb.b()) {
                    setValue(Integer.parseInt(((EditText) view.findViewById(Resources.id.et_input)).getText().toString()));
                    if (j != null) {
                        j.a(b, c);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
