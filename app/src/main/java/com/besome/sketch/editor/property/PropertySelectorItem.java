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

public class PropertySelectorItem extends RelativeLayout implements View.OnClickListener {

    public String a = "";
    public int b = -1;
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public ViewGroup i;
    public Kw j;

    public PropertySelectorItem(Context context, boolean z) {
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
            switch (a) {
                case "property_orientation":
                    type = 0;
                    break;

                case "property_text_style":
                    type = 1;
                    break;

                case "property_text_size":
                    type = 2;
                    break;

                case "property_ime_option":
                    type = 3;
                    break;

                case "property_input_type":
                    type = 4;
                    break;

                case "property_spinner_mode":
                    type = 5;
                    break;

                case "property_choice_mode":
                    type = 6;
                    break;

                case "property_first_day_of_week":
                    type = 7;
                    break;
            }
            switch (type) {
                case 0:
                    f = Resources.drawable.grid_3_48;
                    break;

                case 1:
                    f = Resources.drawable.abc_96_color;
                    break;

                case 2:
                    f = Resources.drawable.text_width_96;
                    break;

                case 3:
                case 4:
                    f = Resources.drawable.keyboard_48;
                    break;

                case 5:
                    f = Resources.drawable.pull_down_48;
                    break;

                case 6:
                    f = Resources.drawable.multiple_choice_48;
                    break;

                case 7:
                    f = Resources.drawable.monday_48;
                    break;
            }
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
        d.setText(sq.a(a, value));
    }

    public void onClick(View v) {
        if (!mB.a()) {
            char type = 65535;
            switch (a) {
                case "property_orientation":
                    type = 0;
                    break;

                case "property_text_style":
                    type = 1;
                    break;

                case "property_text_size":
                    type = 2;
                    break;

                case "property_ime_option":
                    type = 3;
                    break;

                case "property_input_type":
                    type = 4;
                    break;

                case "property_spinner_mode":
                    type = 5;
                    break;

                case "property_choice_mode":
                    type = 6;
                    break;

                case "property_first_day_of_week":
                    type = 7;
                    break;
            }
            switch (type) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    a();
            }
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
        TextView desc = view.findViewById(Resources.id.desc);
        int counter = 0;
        if (a.equals("property_ime_option")) {
            desc.setText(xB.b().a(getContext(), Resources.string.property_description_edittext_ime_options));
            desc.setVisibility(VISIBLE);
        } else {
            desc.setVisibility(GONE);
        }
        for (Pair<Integer, String> pair : sq.a(a)) {
            i.addView(a(pair));
        }
        int childCount = i.getChildCount();
        while (true) {
            if (counter >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) i.getChildAt(counter);
            if (Integer.parseInt(radioButton.getTag().toString()) == b) {
                radioButton.setChecked(true);
                break;
            }
            counter++;
        }
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), new OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                while (true) {
                    if (counter >= i.getChildCount()) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) i.getChildAt(counter);
                    if (radioButton.isChecked()) {
                        setValue(Integer.parseInt(radioButton.getTag().toString()));
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

    public final RadioButton a(Pair<Integer, String> pair) {
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
