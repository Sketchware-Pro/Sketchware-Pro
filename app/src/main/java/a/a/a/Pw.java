package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
        a(idk);
    }

    private RadioButton a(String fileName) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(fileName);
        radioButton.setTag(fileName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (wB.a(getContext(), 1.0F) * 40.0F));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(c.getText().toString());
        dialog.a(f);
        View rootView = wB.a(getContext(), R.layout.property_popup_selector_single);
        ViewGroup j = (ViewGroup) rootView.findViewById(R.id.rg_content);
        j.addView(a("none"));

        for (ProjectFileBean projectFileBean : i) {
            RadioButton var4 = a(projectFileBean.fileName);
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
        dialog.b(xB.b().a(getContext(), 2131625035), view -> {
            for (int i = 0, childCount = j.getChildCount(); i < childCount; i++) {
                RadioButton radioButton = (RadioButton) j.getChildAt(i);
                if (radioButton.isChecked()) {
                    setValue(radioButton.getTag().toString());
                }
            }
            if (k != null) {
                k.a(a, b);
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), 2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void a(boolean var2) {
        wB.a(getContext(), this, R.layout.property_selector_item);
        c = (TextView) findViewById(R.id.tv_name);
        d = (TextView) findViewById(R.id.tv_value);
        g = findViewById(R.id.property_item);
        h = findViewById(R.id.property_menu_item);
        e = (ImageView) findViewById(R.id.img_left_icon);
        if (var2) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }

    }

    public String getKey() {
        return a;
    }

    public String getValue() {
        return b;
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            if ("property_custom_view_listview".equals(a)) {
                a();
            }
        }
    }

    public void setCustomView(ArrayList<ProjectFileBean> customView) {
        i = customView;
    }

    public void setKey(String key) {
        a = key;
        int var2 = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (var2 > 0) {
            c.setText(xB.b().a(getResources(), var2));
            f = 2131165638;
            if (h.getVisibility() == View.VISIBLE) {
                ImageView var3 = (ImageView) findViewById(R.id.img_icon);
                TextView var4 = (TextView) findViewById(R.id.tv_title);
                var3.setImageResource(f);
                var4.setText(xB.b().a(getContext(), var2));
            } else {
                e.setImageResource(f);
            }
        }

    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        k = var1;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            g.setVisibility(View.GONE);
            h.setVisibility(View.VISIBLE);
        } else {
            g.setVisibility(View.VISIBLE);
            h.setVisibility(View.GONE);
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

        b = var2;
        d.setText(var2);
    }
}
