package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.OB;
import a.a.a.SB;
import a.a.a.TB;
import a.a.a._B;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyInputItem extends RelativeLayout implements View.OnClickListener {

    public Context context;
    public String key = "";
    public String value = "";
    public ImageView imgLeftIcon;
    public int icon;
    public TextView tvName;
    public TextView tvValue;
    public View propertyItem;
    public View propertyMenuItem;
    public String sc_id;
    public ProjectFileBean projectFileBean;
    public Kw valueChangeListener;

    public PropertyInputItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        switch (key) {
            case "property_id":
                icon = Resources.drawable.rename_96_blue;
                break;

            case "property_text":
                icon = Resources.drawable.abc_96;
                break;

            case "property_hint":
                icon = Resources.drawable.help_96_blue;
                break;

            case "property_weight":
            case "property_weight_sum":
                icon = Resources.drawable.one_to_many_48;
                break;

            case "property_rotate":
                icon = Resources.drawable.ic_reset_color_32dp;
                break;

            case "property_lines":
            case "property_max":
            case "property_progress":
                icon = Resources.drawable.numbers_48;
                break;

            case "property_alpha":
                icon = Resources.drawable.opacity_48;
                break;

            case "property_translation_x":
                icon = Resources.drawable.swipe_right_48;
                break;

            case "property_translation_y":
                icon = Resources.drawable.swipe_down_48;
                break;

            case "property_scale_x":
            case "property_scale_y":
                icon = Resources.drawable.resize_48;
                break;

            case "property_inject":
                icon = Resources.drawable.ic_property_inject;
                break;

            case "property_convert":
                icon = Resources.drawable.ic_property_convert;
                break;
        }
        imageView.setImageResource(icon);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(xB.b().a(getResources(), identifier));
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                setIcon(findViewById(Resources.id.img_icon));
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            setIcon(imgLeftIcon);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        tvValue.setText(value);
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            switch (key) {
                case "property_id":
                    b();
                    return;

                case "property_text":
                case "property_hint":
                case "property_inject":
                    b(0, 9999);
                    return;

                case "property_weight":
                case "property_weight_sum":
                case "property_rotate":
                case "property_lines":
                case "property_max":
                case "property_progress":
                    a();
                    return;

                case "property_alpha":
                    a(0, 1);
                    return;

                case "property_translation_x":
                case "property_translation_y":
                    a(-9999, 9999);
                    return;

                case "property_scale_x":
                case "property_scale_y":
                    a(0, 99);
                    return;

                case "property_convert":
                    b(0, 99);
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
            return;
        }
        propertyItem.setVisibility(VISIBLE);
        propertyMenuItem.setVisibility(GONE);
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        wB.a(context, this, Resources.layout.property_input_item);
        tvName = findViewById(Resources.id.tv_name);
        tvValue = findViewById(Resources.id.tv_value);
        imgLeftIcon = findViewById(Resources.id.img_left_icon);
        propertyItem = findViewById(Resources.id.property_item);
        propertyMenuItem = findViewById(Resources.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    private void b() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View a2 = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = a2.findViewById(Resources.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _B validator = new _B(context, a2.findViewById(Resources.id.ti_input), uq.b, uq.a(), jC.a(sc_id).a(projectFileBean), value);
        validator.a(value);
        dialog.a(a2);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.b()) {
                    setValue(editText.getText().toString());
                    if (valueChangeListener != null) valueChangeListener.a(key, value);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void a(String projectId, ProjectFileBean projectFileBean) {
        sc_id = projectId;
        this.projectFileBean = projectFileBean;
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setInputType(4098);
        editText.setText(value);
        TB validator = new TB(
                context,
                view.findViewById(Resources.id.ti_input),
                0,
                (key.equals("property_max") || key.equals("property_progress"))
                        ? 0x7fffffff : 999
        );
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.b()) {
                    setValue(editText.getText().toString());
                    if (valueChangeListener != null) valueChangeListener.a(key, value);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void b(int minValue, int maxValue) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        SB lengthValidator = new SB(context, view.findViewById(Resources.id.ti_input), minValue, maxValue);
        lengthValidator.a(value);
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lengthValidator.b()) {
                    setValue(((EditText) view.findViewById(Resources.id.ed_input)).getText().toString());
                    if (valueChangeListener != null) valueChangeListener.a(key, value);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void a(int minValue, int maxValue) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setInputType(minValue < 0 ? 12290 : 8194);
        editText.setText(value);
        OB validator = new OB(context, view.findViewById(Resources.id.ti_input), minValue, maxValue);
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.b()) {
                    setValue(editText.getText().toString());
                    if (valueChangeListener != null) valueChangeListener.a(key, value);
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
