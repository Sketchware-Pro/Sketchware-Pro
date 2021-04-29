package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class PropertyStringPairSelectorItem extends RelativeLayout implements View.OnClickListener {

    public String a = "";
    public String b = "";
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public ViewGroup i;
    public Kw j;

    public PropertyStringPairSelectorItem(Context context, boolean z) {
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
            char type = 65535;
            if (str.equals("property_progressbar_style")) {
                type = 0;
            }
            if (type == 0) {
                f = Resources.drawable.style_48dp;
            }
            if (h.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(f);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            e.setImageResource(f);
        }
    }

    public String getValue() {
        return b;
    }

    public void setValue(String value) {
        b = value;
        d.setText(value);
    }

    public void onClick(View v) {
        if (!mB.a()) {
            a();
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        j = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            g.setVisibility(GONE);
            h.setVisibility(VISIBLE);
            return;
        }
        g.setVisibility(VISIBLE);
        h.setVisibility(GONE);
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
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_single);
        i = view.findViewById(Resources.id.rg_content);
        int counter = 0;
        for (Pair<String, String> pair : sq.b(a)) {
            i.addView(a(pair));
        }
        int childCount = i.getChildCount();
        while (true) {
            if (counter >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) i.getChildAt(counter);
            if (radioButton.getTag().toString().equals(b)) {
                radioButton.setChecked(true);
                break;
            }
            counter++;
        }
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), new OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = i.getChildCount();
                int counter = 0;
                while (true) {
                    if (counter >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) i.getChildAt(counter);
                    if (radioButton.isChecked()) {
                        setValue(radioButton.getTag().toString());
                        break;
                    }
                    counter++;
                }
                if (j != null) {
                    j.a(a, b);
                }
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final RadioButton a(Pair<String, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (wB.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
