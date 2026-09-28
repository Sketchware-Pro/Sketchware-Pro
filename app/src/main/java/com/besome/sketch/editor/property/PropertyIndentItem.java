package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
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
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyInputItemBinding;
import pro.sketchware.databinding.PropertyPopupInputIndentBinding;
import pro.sketchware.lib.validator.MinMaxInputValidator;

@SuppressLint("ViewConstructor")
public class PropertyIndentItem extends RelativeLayout implements View.OnClickListener {

    public PropertyInputItemBinding binding;
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
                binding.propertyMenuItem.imgIcon.setImageResource(icon);
                binding.propertyMenuItem.tvTitle.setText(Helper.getResString(identifier));
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
            propertyItem.setOnClickListener(null);
            propertyMenuItem.setOnClickListener(this);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
            propertyItem.setOnClickListener(this);
            propertyMenuItem.setOnClickListener(null);
        }
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        binding = PropertyInputItemBinding.inflate(LayoutInflater.from(context), this, true);
        tvName = binding.tvName;
        tvValue = binding.tvValue;
        imgLeftIcon = binding.imgLeftIcon;
        propertyItem = binding.propertyItem;
        propertyMenuItem = binding.propertyMenuItem.getRoot();
//        if (z) {
//            propertyMenuItem.setSoundEffectsEnabled(true);
//            propertyMenuItem.setOnClickListener(this);
//        }
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

        PropertyPopupInputIndentBinding popupBinding = PropertyPopupInputIndentBinding.inflate(LayoutInflater.from(getContext()));
        View view = popupBinding.getRoot();

        popupBinding.tiAll.setHint(String.format(Helper.getResString(R.string.property_enter_value), propertyType.toLowerCase()));
        popupBinding.chkPtyAll.setText(String.format("%s on all sides", propertyType));

        MinMaxInputValidator ti_all = new MinMaxInputValidator(context, popupBinding.tiAll, 0, 999);
        MinMaxInputValidator ti_left = new MinMaxInputValidator(context, popupBinding.tiLeft, 0, 999);
        MinMaxInputValidator ti_right = new MinMaxInputValidator(context, popupBinding.tiRight, 0, 999);
        MinMaxInputValidator ti_top = new MinMaxInputValidator(context, popupBinding.tiTop, 0, 999);
        MinMaxInputValidator ti_bottom = new MinMaxInputValidator(context, popupBinding.tiBottom, 0, 999);

        ti_left.a(String.valueOf(j));
        ti_top.a(String.valueOf(k));
        ti_right.a(String.valueOf(l));
        ti_bottom.a(String.valueOf(m));

        if (j == k && k == l && l == m) { // All sides are equal
            ti_all.a(String.valueOf(j));
            popupBinding.chkPtyAll.setChecked(true);
        } else {
            popupBinding.individualPaddingView.setVisibility(VISIBLE);
            popupBinding.allPaddingView.setVisibility(GONE);
        }

        popupBinding.chkPtyAll.setOnClickListener(v -> {
            if (popupBinding.chkPtyAll.isChecked()) {
                popupBinding.individualPaddingView.setVisibility(GONE);
                popupBinding.allPaddingView.setVisibility(VISIBLE);
                popupBinding.etLeft.setText(Helper.getText(popupBinding.etAll));
                popupBinding.etLeft.clearFocus();
                popupBinding.etTop.clearFocus();
                popupBinding.etRight.clearFocus();
                popupBinding.etBottom.clearFocus();
            } else {
                popupBinding.individualPaddingView.setVisibility(VISIBLE);
                popupBinding.allPaddingView.setVisibility(GONE);
                popupBinding.etAll.clearFocus();
            }
        });

        popupBinding.etAll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ti_left.a(Helper.getText(popupBinding.etAll));
                ti_top.a(Helper.getText(popupBinding.etAll));
                ti_right.a(Helper.getText(popupBinding.etAll));
                ti_bottom.a(Helper.getText(popupBinding.etAll));
            }
        });

        popupBinding.tvDpAll.setVisibility(View.GONE);
        popupBinding.tvDpBottom.setVisibility(View.GONE);
        popupBinding.tvDpLeft.setVisibility(View.GONE);
        popupBinding.tvDpRight.setVisibility(View.GONE);
        popupBinding.tvDpTop.setVisibility(View.GONE);

        popupBinding.tiAll.setSuffixText("dp");
        popupBinding.tiBottom.setSuffixText("dp");
        popupBinding.tiLeft.setSuffixText("dp");
        popupBinding.tiRight.setSuffixText("dp");
        popupBinding.tiTop.setSuffixText("dp");

        dialog.setView(view);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (v, which) -> {
            if (popupBinding.chkPtyAll.isChecked()) {
                if (ti_all.b() && ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                    int left = Integer.parseInt(Helper.getText(popupBinding.etLeft));
                    int top = Integer.parseInt(Helper.getText(popupBinding.etTop));
                    int right = Integer.parseInt(Helper.getText(popupBinding.etRight));
                    int bottom = Integer.parseInt(Helper.getText(popupBinding.etBottom));
                    a(left, top, right, bottom);
                    if (valueChangeListener != null) {
                        valueChangeListener.a(key, new int[]{left, top, right, bottom});
                        v.dismiss();
                    }
                }
            } else if (ti_left.b() && ti_right.b() && ti_top.b() && ti_bottom.b()) {
                int left = Integer.parseInt(Helper.getText(popupBinding.etLeft));
                int top = Integer.parseInt(Helper.getText(popupBinding.etTop));
                int right = Integer.parseInt(Helper.getText(popupBinding.etRight));
                int bottom = Integer.parseInt(Helper.getText(popupBinding.etBottom));
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
