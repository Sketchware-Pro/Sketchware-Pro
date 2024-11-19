package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupInputIndentBinding;

@SuppressLint("ViewConstructor")
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

        PropertyPopupInputIndentBinding binding = PropertyPopupInputIndentBinding.inflate(LayoutInflater.from(getContext()));
        View view = binding.getRoot();

        TB ti_all = new TB(context, binding.tiAll, 0, 999);
        TB ti_left = new TB(context, binding.tiLeft, 0, 999);
        TB ti_right = new TB(context, binding.tiRight, 0, 999);
        TB ti_top = new TB(context, binding.tiTop, 0, 999);
        TB ti_bottom = new TB(context, binding.tiBottom, 0, 999);

        ti_left.a(String.valueOf(j));
        ti_top.a(String.valueOf(k));
        ti_right.a(String.valueOf(l));
        ti_bottom.a(String.valueOf(m));

        if (j == k && k == l && l == m) { // All sides are equal
            ti_all.a(String.valueOf(j));
            binding.chkPtyAll.setChecked(true);
        } else {
            binding.individualPaddingView.setVisibility(VISIBLE);
            binding.allPaddingView.setVisibility(GONE);
        }

        binding.chkPtyAll.setOnClickListener(v -> {
            if (binding.chkPtyAll.isChecked()) {
                binding.individualPaddingView.setVisibility(GONE);
                binding.allPaddingView.setVisibility(VISIBLE);
                binding.etLeft.setText(binding.etAll.getText().toString());
                binding.etLeft.clearFocus();
                binding.etTop.clearFocus();
                binding.etRight.clearFocus();
                binding.etBottom.clearFocus();
            } else {
                binding.individualPaddingView.setVisibility(VISIBLE);
                binding.allPaddingView.setVisibility(GONE);
                binding.etAll.clearFocus();
            }
        });

        binding.etAll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ti_left.a(binding.etAll.getText().toString());
                ti_top.a(binding.etAll.getText().toString());
                ti_right.a(binding.etAll.getText().toString());
                ti_bottom.a(binding.etAll.getText().toString());
            }
        });

        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            if (binding.chkPtyAll.isChecked()) {
                if (ti_all.b() && ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                    int left = Integer.parseInt(binding.etLeft.getText().toString());
                    int top = Integer.parseInt(binding.etTop.getText().toString());
                    int right = Integer.parseInt(binding.etRight.getText().toString());
                    int bottom = Integer.parseInt(binding.etBottom.getText().toString());
                    a(left, top, right, bottom);
                    if (valueChangeListener != null) {
                        valueChangeListener.a(key, new int[]{left, top, right, bottom});
                        dialog.dismiss();
                    }
                }
            } else if (ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                int left = Integer.parseInt(binding.etLeft.getText().toString());
                int top = Integer.parseInt(binding.etTop.getText().toString());
                int right = Integer.parseInt(binding.etRight.getText().toString());
                int bottom = Integer.parseInt(binding.etBottom.getText().toString());
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
