package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
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

public class PropertyStringSelectorItem extends RelativeLayout implements View.OnClickListener {

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

    public PropertyStringSelectorItem(Context context, boolean z) {
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
            switch (str) {
                case "property_ad_size":
                    type = 0;
                    break;

                case "property_indeterminate":
                    type = 2;
                    break;

                case "property_scale_type":
                    type = 1;
                    break;
            }
            if (type == 0) {
                f = Resources.drawable.widget_admob;
            } else if (type == 1) {
                f = Resources.drawable.enlarge_48;
            } else if (type == 2) {
                f = Resources.drawable.event_on_accuracy_changed_48dp;
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

    public void setValue(String str) {
        b = str;
        d.setText(str);
    }

    @Override
    public void onClick(View view) {
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
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_single);
        i = view.findViewById(Resources.id.rg_content);

        byte type;
        switch (a) {
            case "property_ad_size":
                type = 1;
                break;

            case "property_indeterminate":
                type = 2;
                break;

            case "property_scale_type":
                type = 0;
                break;

            default:
                type = -1;
        }

        String[] items;
        if (type == 0) {
            items = sq.j;
        } else {
            if (type == 1) {
                items = sq.k;
            } else {
                if (type == 2) {
                    items = sq.l;
                } else {
                    items = null;
                }
            }
        }

        for (String s : items) {
            i.addView(a(s));
        }

        for (int counter = 0; counter < i.getChildCount(); counter++) {
            RadioButton childAt = (RadioButton) i.getChildAt(counter);
            if (childAt.getTag().toString().equals(b)) {
                childAt.setChecked(true);
                break;
            }
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

    public final RadioButton a(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setTextSize(2, 12.0f);
        radioButton.setText(str);
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(getContext(), 4.0f);
        layoutParams.bottomMargin = (int) wB.a(getContext(), 4.0f);
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}