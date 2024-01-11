package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import mod.hey.studios.util.Helper;

@SuppressLint("ViewConstructor")
public class Pw extends RelativeLayout implements View.OnClickListener {

    private String a = "";
    private String b = "";
    private TextView c;
    private TextView d;
    private ImageView e;
    private int f;
    private View g;
    private View h;
    private Kw k;
    private ArrayList<ProjectFileBean> i;

    public Pw(Context context, boolean idk) {
        super(context);
        this.a(idk);
    }

    private RadioButton a(String fileName) {
        RadioButton radioButton = new RadioButton(this.getContext());
        radioButton.setText(fileName);
        radioButton.setTag(fileName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (wB.a(this.getContext(), 1.0F) * 40.0F));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private void a() {
        aB dialog = new aB((Activity) this.getContext());
        dialog.b(this.c.getText().toString());
        dialog.a(this.f);
        View rootView = wB.a(this.getContext(), R.layout.property_popup_selector_single);
        ViewGroup j = (ViewGroup) rootView.findViewById(R.id.rg_content);
        j.addView(this.a("none"));

        for (ProjectFileBean projectFileBean : this.i) {
            RadioButton var4 = this.a(projectFileBean.fileName);
            j.addView(var4);
        }

        ((RadioButton) j.getChildAt(0)).setChecked(true);

        for (int i = 0, childCount = j.getChildCount(); i < childCount; i++) {
            RadioButton radioButton = (RadioButton) j.getChildAt(i);
            if (radioButton.getTag().toString().equals(b)) {
                radioButton.setChecked(true);
            }
        }

        dialog.a(rootView);
        dialog.b(xB.b().a(this.getContext(), 2131625035), (d, which) -> {
            for (int i = 0, childCount = j.getChildCount(); i < childCount; i++) {
                RadioButton radioButton = (RadioButton) j.getChildAt(i);
                if (radioButton.isChecked()) {
                    setValue(radioButton.getTag().toString());
                }
            }
            if (k != null) {
                k.a(a, b);
            }
            d.dismiss();
        });
        dialog.a(xB.b().a(this.getContext(), 2131624974), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
    }

    private void a(boolean var2) {
        wB.a(this.getContext(), this, R.layout.property_selector_item);
        this.c = (TextView) this.findViewById(R.id.tv_name);
        this.d = (TextView) this.findViewById(R.id.tv_value);
        this.g = this.findViewById(R.id.property_item);
        this.h = this.findViewById(R.id.property_menu_item);
        this.e = (ImageView) this.findViewById(R.id.img_left_icon);
        if (var2) {
            this.setOnClickListener(this);
            this.setSoundEffectsEnabled(true);
        }

    }

    public String getKey() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            if ("property_custom_view_listview".equals(a)) {
                this.a();
            }
        }
    }

    public void setCustomView(ArrayList<ProjectFileBean> customView) {
        this.i = customView;
    }

    public void setKey(String key) {
        this.a = key;
        int var2 = this.getResources().getIdentifier(key, "string", this.getContext().getPackageName());
        if (var2 > 0) {
            this.c.setText(xB.b().a(this.getResources(), var2));
            this.f = 2131165638;
            if (this.h.getVisibility() == View.VISIBLE) {
                ImageView var3 = (ImageView) this.findViewById(R.id.img_icon);
                TextView var4 = (TextView) this.findViewById(R.id.tv_title);
                var3.setImageResource(this.f);
                var4.setText(xB.b().a(this.getContext(), var2));
            } else {
                this.e.setImageResource(this.f);
            }
        }

    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        k = var1;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            this.g.setVisibility(View.GONE);
            this.h.setVisibility(View.VISIBLE);
        } else {
            this.g.setVisibility(View.VISIBLE);
            this.h.setVisibility(View.GONE);
        }

    }

    public void setValue(String value) {
        String var2;
        label11:
        {
            if (value != null) {
                var2 = value;
                if (value.length() > 0) {
                    break label11;
                }
            }

            var2 = "none";
        }

        this.b = var2;
        this.d.setText(var2);
    }
}
