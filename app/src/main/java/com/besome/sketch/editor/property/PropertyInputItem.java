package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
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

    public Context a;
    public String b = "";
    public String c = "";
    public ImageView d;
    public int e;
    public TextView f;
    public TextView g;
    public View h;
    public View i;
    public String j;
    public ProjectFileBean k;
    public Kw l;

    public PropertyInputItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    private void setIcon(ImageView imageView) {
        char type;
        switch (b) {
            case "property_id":
                type = 0;
                break;

            case "property_text":
                type = 1;
                break;

            case "property_hint":
                type = 2;
                break;

            case "property_weight":
                type = 3;
                break;

            case "property_weight_sum":
                type = 4;
                break;

            case "property_rotate":
                type = 5;
                break;

            case "property_lines":
                type = 6;
                break;

            case "property_max":
                type = 7;
                break;

            case "property_progress":
                type = 8;
                break;

            case "property_alpha":
                type = 9;
                break;

            case "property_translation_x":
                type = 10;
                break;

            case "property_translation_y":
                type = 11;
                break;

            case "property_scale_x":
                type = 12;
                break;

            case "property_scale_y":
                type = 13;
                break;

            case "property_inject":
                type = 14;
                break;

            case "property_convert":
                type = 15;
                break;

            default:
                type = 65535;
                break;
        }
        switch (type) {
            case 0:
                e = Resources.drawable.rename_96_blue;
                break;

            case 1:
                e = Resources.drawable.abc_96;
                break;

            case 2:
                e = Resources.drawable.help_96_blue;
                break;

            case 3:
            case 4:
                e = Resources.drawable.one_to_many_48;
                break;

            case 5:
                e = Resources.drawable.ic_reset_color_32dp;
                break;

            case 6:
            case 7:
            case '\b':
                e = Resources.drawable.numbers_48;
                break;

            case '\t':
                e = Resources.drawable.opacity_48;
                break;

            case '\n':
                e = Resources.drawable.swipe_right_48;
                break;

            case 11:
                e = Resources.drawable.swipe_down_48;
                break;

            case '\f':
            case '\r':
                e = Resources.drawable.resize_48;
                break;

            case 14:
                e = Resources.drawable.ic_property_inject;
                break;

            case 15:
                e = Resources.drawable.ic_property_convert;
                break;
        }
        imageView.setImageResource(e);
    }

    public String getKey() {
        return b;
    }

    public void setKey(String str) {
        b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            f.setText(xB.b().a(getResources(), identifier));
            if (i.getVisibility() == VISIBLE) {
                setIcon(findViewById(Resources.id.img_icon));
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            setIcon(d);
        }
    }

    public String getValue() {
        return c;
    }

    public void setValue(String value) {
        c = value;
        g.setText(value);
    }

    public void onClick(View view) {
        if (!mB.a()) {
            char type;
            switch (b) {
                case "property_id":
                    type = 0;
                    break;

                case "property_text":
                    type = 1;
                    break;

                case "property_hint":
                    type = 2;
                    break;

                case "property_weight":
                    type = 3;
                    break;

                case "property_weight_sum":
                    type = 4;
                    break;

                case "property_rotate":
                    type = 5;
                    break;

                case "property_lines":
                    type = 6;
                    break;

                case "property_max":
                    type = 7;
                    break;

                case "property_progress":
                    type = 8;
                    break;

                case "property_alpha":
                    type = 9;
                    break;

                case "property_translation_x":
                    type = 10;
                    break;

                case "property_translation_y":
                    type = 11;
                    break;

                case "property_scale_x":
                    type = 12;
                    break;

                case "property_scale_y":
                    type = 13;
                    break;

                case "property_inject":
                    type = 14;
                    break;

                case "property_convert":
                    type = 15;
                    break;

                default:
                    type = 65535;
                    break;
            }
            switch (type) {
                case 0:
                    b();
                    return;

                case 1:
                case 2:
                case 14:
                    b(0, 9999);
                    return;

                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                    a();
                    return;

                case '\t':
                    a(0, 1);
                    return;

                case '\n':
                case 11:
                    a(-9999, 9999);
                    return;

                case '\f':
                case '\r':
                    a(0, 99);
                    return;

                case 15:
                    b(0, 99);
            }
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        l = kw;
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
        f = findViewById(Resources.id.tv_name);
        g = findViewById(Resources.id.tv_value);
        d = findViewById(Resources.id.img_left_icon);
        h = findViewById(Resources.id.property_item);
        i = findViewById(Resources.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    private void b() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(f.getText().toString());
        dialog.a(e);
        View a2 = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = a2.findViewById(Resources.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        _B _b = new _B(a, a2.findViewById(Resources.id.ti_input), uq.b, uq.a(), jC.a(j).a(k), c);
        _b.a(c);
        dialog.a(a2);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_b.b()) {
                    setValue(editText.getText().toString());
                    if (l != null) {
                        l.a(b, c);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        j = str;
        k = projectFileBean;
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(f.getText().toString());
        dialog.a(e);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setInputType(4098);
        editText.setText(c);
        TB tb = new TB(
                a,
                view.findViewById(Resources.id.ti_input),
                0,
                (b.equals("property_max") || b.equals("property_progress"))
                        ? 0x7fffffff : 999
        );
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb.b()) {
                    setValue(editText.getText().toString());
                    if (l != null) {
                        l.a(b, c);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void b(int i, int i2) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(f.getText().toString());
        dialog.a(e);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        SB sb = new SB(a, view.findViewById(Resources.id.ti_input), i, i2);
        sb.a(c);
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sb.b()) {
                    setValue(((EditText) view.findViewById(Resources.id.ed_input)).getText().toString());
                    if (l != null) {
                        l.a(b, c);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void a(int i, int i2) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(f.getText().toString());
        dialog.a(e);
        View view = wB.a(getContext(), Resources.layout.property_popup_input_text);
        EditText editText = view.findViewById(Resources.id.ed_input);
        editText.setInputType(i < 0 ? 12290 : 8194);
        editText.setText(c);
        OB ob = new OB(a, view.findViewById(Resources.id.ti_input), i, i2);
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_save), new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ob.b()) {
                    setValue(editText.getText().toString());
                    if (l != null) {
                        l.a(b, c);
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
