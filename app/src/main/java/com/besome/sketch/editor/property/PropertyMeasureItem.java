package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyMeasureItem extends RelativeLayout implements View.OnClickListener {

    public String a = "";
    public int b = -1;
    public TextView c;
    public TextView d;
    public ImageView e;
    public View f;
    public View g;
    public Kw h;
    public boolean i = true;
    public boolean j = true;
    public boolean k = true;
    public int l;

    public PropertyMeasureItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void setIcon(ImageView imageView) {
        if ("property_layout_width".equals(a)) {
            l = Resources.drawable.width_96;
        } else if ("property_layout_height".equals(a)) {
            l = Resources.drawable.height_96;
        }
        imageView.setImageResource(l);
    }

    public String getKey() {
        return a;
    }

    public void setKey(String str) {
        a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            c.setText(xB.b().a(getResources(), identifier));
            if (this.g.getVisibility() == VISIBLE) {
                setIcon(findViewById(Resources.id.img_icon));
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            setIcon(e);
        }
    }

    public int getValue() {
        return b;
    }

    public void setValue(int value) {
        b = value;
        if (!j && value == -2) {
            d.setText(sq.a(a, -1));
        } else if (k || value < 0) {
            d.setText(sq.a(a, value));
        } else {
            d.setText(sq.a(a, -2));
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            a();
        }
    }

    public void setItemEnabled(int itemEnabled) {
        i = (itemEnabled & 1) == 1;
        j = (itemEnabled & 2) == 2;
        k = (itemEnabled & 4) == 4;
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        h = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            f.setVisibility(GONE);
            g.setVisibility(VISIBLE);
        } else {
            f.setVisibility(VISIBLE);
            g.setVisibility(GONE);
        }
    }

    public final void a(Context context, boolean z) {
        wB.a(context, this, Resources.layout.property_selector_item);
        c = findViewById(Resources.id.tv_name);
        d = findViewById(Resources.id.tv_value);
        e = findViewById(Resources.id.img_left_icon);
        f = findViewById(Resources.id.property_item);
        g = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public final void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(c.getText().toString());
        dialog.a(l);
        View view = wB.a(getContext(), Resources.layout.property_popup_measurement);
        EditText ed_input = view.findViewById(Resources.id.ed_input);
        RadioGroup rg_width_height = view.findViewById(Resources.id.rg_width_height);
        TB tb = new TB(getContext(), view.findViewById(Resources.id.ti_input), 0, 999);
        rg_width_height.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == Resources.id.rb_directinput) {
                    ed_input.setEnabled(true);
                    tb.a(ed_input.getText().toString());
                } else {
                    ed_input.setEnabled(false);
                }
            }
        });
        ed_input.setEnabled(false);
        rg_width_height.clearCheck();
        if (b >= 0) {
            if (k) {
                rg_width_height.check(Resources.id.rb_directinput);
                ed_input.setEnabled(true);
                tb.a(String.valueOf(b));
            } else {
                rg_width_height.check(Resources.id.rb_wrapcontent);
            }
        } else if (b == -1) {
            rg_width_height.check(Resources.id.rb_matchparent);
        } else if (j) {
            rg_width_height.check(Resources.id.rb_wrapcontent);
        } else {
            rg_width_height.check(Resources.id.rb_matchparent);
        }
        view.findViewById(Resources.id.tv_matchparent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RadioButton) view.findViewById(Resources.id.rb_matchparent)).setChecked(true);
            }
        });
        RadioButton rb_matchparent = view.findViewById(Resources.id.rb_matchparent);
        View tv_wrapcontent = view.findViewById(Resources.id.tv_wrapcontent);
        if (j) {
            rb_matchparent.setEnabled(true);
            tv_wrapcontent.setClickable(true);
            ((TextView) tv_wrapcontent).setTextColor(0xff757575);
            tv_wrapcontent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rb_matchparent.setChecked(true);
                }
            });
        } else {
            rb_matchparent.setEnabled(false);
            tv_wrapcontent.setClickable(false);
            ((TextView) tv_wrapcontent).setTextColor(0xffdddddd);
        }
        RadioButton rb_directinput = view.findViewById(Resources.id.rb_directinput);
        View direct_input = view.findViewById(Resources.id.direct_input);
        TextView tv_input_dp = view.findViewById(Resources.id.tv_input_dp);
        if (k) {
            rb_directinput.setEnabled(true);
            direct_input.setClickable(true);
            tv_input_dp.setTextColor(0xff757575);
            direct_input.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    rb_directinput.setChecked(true);
                }
            });
        } else {
            rb_directinput.setEnabled(false);
            direct_input.setClickable(false);
            tv_input_dp.setTextColor(0xffdddddd);
        }
        dialog.a(view);
        // this, rg_width_height, tb, ed_input, dialog
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg_width_height.getCheckedRadioButtonId() == Resources.id.rb_matchparent) {
                    setValue(-1);
                } else if (rg_width_height.getCheckedRadioButtonId() == Resources.id.rb_wrapcontent) {
                    setValue(-2);
                } else if (tb.b()) {
                    setValue(Integer.parseInt(ed_input.getText().toString()));
                } else {
                    return;
                }
                if (h != null) {
                    h.a(a, b);
                }
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}