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

import com.sketchware.remod.R;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyIndentItem extends RelativeLayout implements View.OnClickListener {

    /**
     * Left margin in dp
     */
    public int j;
    /**
     * Top margin in dp
     */
    public int k;
    /**
     * Right margin in dp
     */
    public int l;
    /**
     * Bottom margin in dp
     */
    public int m;
    private Context context;
    private String key = "";
    private View propertyItem;
    private View propertyMenuItem;
    private ImageView imgLeftIcon;
    private int icon;
    private TextView tvName;
    private TextView tvValue;
    private Kw valueChangeListener;

    public PropertyIndentItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            switch (this.key) {
                case "property_padding":
                    icon = R.drawable.collect_48;
                    break;

                case "property_margin":
                    icon = R.drawable.insert_white_space_48;
                    break;
            }
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public String getValue() {
        return "";
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            switch (key) {
                case "property_padding":
                case "property_margin":
                    showDialog();
                    break;
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        wB.a(context, this, R.layout.property_input_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
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
        tvValue.setText("left: " + j + ", top: " + k + ", right: " + l + ", bottom: " + m);
    }

    private void showDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), R.layout.property_popup_input_indent);
        CheckBox chk_pty_all = view.findViewById(R.id.chk_pty_all);
        chk_pty_all.setText(Helper.getResString(R.string.common_word_all));
        EditText et_all = view.findViewById(R.id.et_all);
        EditText et_left = view.findViewById(R.id.et_left);
        EditText et_top = view.findViewById(R.id.et_top);
        EditText et_right = view.findViewById(R.id.et_right);
        EditText et_bottom = view.findViewById(R.id.et_bottom);
        TB ti_all = new TB(context, view.findViewById(R.id.ti_all), 0, 999);
        TB ti_left = new TB(context, view.findViewById(R.id.ti_left), 0, 999);
        TB ti_right = new TB(context, view.findViewById(R.id.ti_right), 0, 999);
        TB ti_top = new TB(context, view.findViewById(R.id.ti_top), 0, 999);
        TB ti_bottom = new TB(context, view.findViewById(R.id.ti_bottom), 0, 999);
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
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (chk_pty_all.isChecked()) {
                if (ti_all.b() && ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                    int left = Integer.parseInt(et_left.getText().toString());
                    int top = Integer.parseInt(et_top.getText().toString());
                    int right = Integer.parseInt(et_right.getText().toString());
                    int bottom = Integer.parseInt(et_bottom.getText().toString());
                    a(left, top, right, bottom);
                    if (valueChangeListener != null) {
                        valueChangeListener.a(key, new int[]{left, top, right, bottom});
                        dialog.dismiss();
                    }
                }
            } else if (ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                int left = Integer.parseInt(et_left.getText().toString());
                int top = Integer.parseInt(et_top.getText().toString());
                int right = Integer.parseInt(et_right.getText().toString());
                int bottom = Integer.parseInt(et_bottom.getText().toString());
                a(left, top, right, bottom);
                if (valueChangeListener != null) {
                    valueChangeListener.a(key, new int[]{left, top, right, bottom});
                    dialog.dismiss();
                }
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
