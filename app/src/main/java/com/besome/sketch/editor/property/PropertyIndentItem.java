package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupInputIndentBinding;
import pro.sketchware.lib.validator.MinMaxInputValidator;

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
                    icon = R.drawable.ic_mtrl_padding;
                    break;

                case "property_margin":
                    icon = R.drawable.ic_mtrl_margin;
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
        String propertyType = Helper.getText(tvName);

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(propertyType);
        dialog.setIcon(icon);

        PropertyPopupInputIndentBinding binding = PropertyPopupInputIndentBinding.inflate(LayoutInflater.from(getContext()));
        View view = binding.getRoot();

        binding.tiAll.setHint(String.format(Helper.getResString(R.string.property_enter_value), propertyType.toLowerCase()));
        binding.chkPtyAll.setText(String.format("%s on all sides", propertyType));

        MinMaxInputValidator ti_all = new MinMaxInputValidator(context, binding.tiAll, 0, 999);
        MinMaxInputValidator ti_left = new MinMaxInputValidator(context, binding.tiLeft, 0, 999);
        MinMaxInputValidator ti_right = new MinMaxInputValidator(context, binding.tiRight, 0, 999);
        MinMaxInputValidator ti_top = new MinMaxInputValidator(context, binding.tiTop, 0, 999);
        MinMaxInputValidator ti_bottom = new MinMaxInputValidator(context, binding.tiBottom, 0, 999);

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
                binding.etLeft.setText(Helper.getText(binding.etAll));
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
                ti_left.a(Helper.getText(binding.etAll));
                ti_top.a(Helper.getText(binding.etAll));
                ti_right.a(Helper.getText(binding.etAll));
                ti_bottom.a(Helper.getText(binding.etAll));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tvDpAll.setVisibility(View.GONE);
            binding.tvDpBottom.setVisibility(View.GONE);
            binding.tvDpLeft.setVisibility(View.GONE);
            binding.tvDpRight.setVisibility(View.GONE);
            binding.tvDpTop.setVisibility(View.GONE);

            binding.tiAll.setSuffixText("dp");
            binding.tiBottom.setSuffixText("dp");
            binding.tiLeft.setSuffixText("dp");
            binding.tiRight.setSuffixText("dp");
            binding.tiTop.setSuffixText("dp");
        }

        dialog.setView(view);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (v, which) -> {
            if (binding.chkPtyAll.isChecked()) {
                if (ti_all.b() && ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                    int left = Integer.parseInt(Helper.getText(binding.etLeft));
                    int top = Integer.parseInt(Helper.getText(binding.etTop));
                    int right = Integer.parseInt(Helper.getText(binding.etRight));
                    int bottom = Integer.parseInt(Helper.getText(binding.etBottom));
                    a(left, top, right, bottom);
                    if (valueChangeListener != null) {
                        valueChangeListener.a(key, new int[]{left, top, right, bottom});
                        v.dismiss();
                    }
                }
            } else if (ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                int left = Integer.parseInt(Helper.getText(binding.etLeft));
                int top = Integer.parseInt(Helper.getText(binding.etTop));
                int right = Integer.parseInt(Helper.getText(binding.etRight));
                int bottom = Integer.parseInt(Helper.getText(binding.etBottom));
                a(left, top, right, bottom);
                if (valueChangeListener != null) {
                    valueChangeListener.a(key, new int[]{left, top, right, bottom});
                    v.dismiss();
                }
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }
}
