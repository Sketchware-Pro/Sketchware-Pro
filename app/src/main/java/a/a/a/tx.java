package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.besome.sketch.beans.ProjectResourceBean;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import mod.hey.studios.util.Helper;

@SuppressLint("ViewConstructor")
public class tx extends RelativeLayout implements View.OnClickListener {

    public String a;
    public String b;
    public String c;
    public boolean d = false;
    public TextView e;
    public TextView f;
    public ImageView g;
    public ImageView h;
    public RadioGroup i;
    public LinearLayout j;
    public View k;
    public View l;
    public int m;
    public Kw n;

    public tx(Context var1, boolean var2, String var3, boolean var4) {
        super(var1);
        a = var3;
        a(var1, var2, var4);
    }

    public final LinearLayout a(String var1, boolean var2) {
        float var3 = wB.a(getContext(), 1.0F);
        LinearLayout var4 = new LinearLayout(getContext());
        var4.setLayoutParams(new LayoutParams(-1, (int) (60.0F * var3)));
        var4.setGravity(19);
        var4.setOrientation(LinearLayout.HORIZONTAL);
        TextView var5 = new TextView(getContext());
        LinearLayout.LayoutParams var6 = new LinearLayout.LayoutParams(0, -2);
        var6.weight = 1.0F;
        var6.rightMargin = (int) (8.0F * var3);
        var5.setLayoutParams(var6);
        var5.setText(var1);
        var4.addView(var5);
        ImageView var11 = new ImageView(getContext());
        var11.setScaleType(ScaleType.CENTER_CROP);
        int var7 = (int) (var3 * 48.0F);
        var11.setLayoutParams(new LayoutParams(var7, var7));
        if (!var1.equalsIgnoreCase("NONE")) {
            if (var2) {
                var11.setImageResource(getContext().getResources().getIdentifier(var1, "drawable", getContext().getPackageName()));
            } else {
                File iconFile = new File(jC.d(a).f(var1));
                if (iconFile.exists()) {
                    Uri uri;
                    if (VERSION.SDK_INT >= 24) {
                        String providerName = getContext().getPackageName() + ".provider";
                        uri = FileProvider.a(getContext(), providerName, iconFile);
                    } else {
                        uri = Uri.fromFile(iconFile);
                    }

                    Glide.with(getContext()).load(uri).signature(kC.n()).error(2131165831).into(var11);
                } else {
                    var11.setImageResource(getContext().getResources().getIdentifier(var1, "drawable", getContext().getPackageName()));
                }
            }

            var11.setBackgroundResource(2131165345);
        } else {
            var11.setBackgroundResource(2131165345);
        }

        var4.addView(var11);
        return var4;
    }

    public final RadioButton a(String var1) {
        RadioButton var2 = new RadioButton(getContext());
        var2.setText("");
        var2.setTag(var1);
        LayoutParams var3 = new LayoutParams(-2, (int) (wB.a(getContext(), 1.0F) * 60.0F));
        var2.setGravity(19);
        var2.setLayoutParams(var3);
        return var2;
    }

    public final void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(e.getText().toString());
        dialog.a(m);
        View var2 = wB.a(getContext(), 2131427641);
        ScrollView var3 = var2.findViewById(2131231692);
        i = var2.findViewById(2131231667);
        j = var2.findViewById(2131230932);
        ArrayList<String> var4 = jC.d(a).m();
        if (d) {
            var4.add(0, "default_image");
        } else {
            var4.add(0, "NONE");
        }

        for (String str : var4) {
            RadioButton radioButton = a(str);
            i.addView(radioButton);
            if (str.equals(c)) {
                radioButton.setChecked(true);
            }

            LinearLayout linearLayout;
            if (str.equals("default_image")) {
                linearLayout = a(str, true);
            } else {
                linearLayout = a(str, false);
            }
            linearLayout.setOnClickListener(view -> {
                int indexOfChild = j.indexOfChild(view);
                ((RadioButton) i.getChildAt(indexOfChild)).setChecked(true);
            });
            j.addView(linearLayout);
        }

        RadioButton radioButton = (RadioButton) i.getChildAt(0);
        radioButton.setChecked(true);

        dialog.a(var2);
        dialog.b(xB.b().a(getContext(), 2131625035), view -> {
            int childCount = i.getChildCount();
            for (int j = 0; j < childCount; j++) {
                RadioButton radioButton1 = (RadioButton) i.getChildAt(j);
                if (radioButton1.isChecked()) {
                    setValue(radioButton1.getTag().toString());
                    if (n != null) {
                        n.a(b, c);
                    }
                    break;
                }
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), 2131624974), Helper.getDialogDismissListener(dialog));
        dialog.setOnShowListener(dialogInterface -> var3.smoothScrollTo(0, (int) radioButton.getY()));
        dialog.show();
    }

    public final void a(Context var1, boolean var2, boolean var3) {
        wB.a(var1, this, 2131427647);
        e = findViewById(2131232055);
        f = findViewById(2131232270);
        g = findViewById(2131232321);
        h = findViewById(2131231155);
        k = findViewById(2131231626);
        l = findViewById(2131231628);
        d = var3;
        if (var2) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public String getKey() {
        return b;
    }

    public void setKey(String var1) {
        b = var1;
        int var2 = getResources().getIdentifier(var1, "string", getContext().getPackageName());
        if (var2 > 0) {
            e.setText(xB.b().a(getResources(), var2));
            var1 = b;
            byte var3 = -1;
            int var4 = var1.hashCode();
            if (var4 != -1949646187) {
                if (var4 == -1545963919 && var1.equals("property_image")) {
                    var3 = 0;
                }
            } else if (var1.equals("property_background_resource")) {
                var3 = 1;
            }

            if (var3 != 0) {
                if (var3 == 1) {
                    m = 2131166227;
                }
            } else {
                m = 2131165811;
            }

            if (l.getVisibility() == View.VISIBLE) {
                ImageView var6 = findViewById(2131231151);
                TextView var5 = findViewById(2131232195);
                var6.setImageResource(m);
                var5.setText(xB.b().a(getContext(), var2));
            } else {
                h.setImageResource(m);
            }
        }

    }

    public String getValue() {
        return c;
    }

    @SuppressLint("SetTextI18n")
    public void setValue(String value) {
        if (value != null && !value.equalsIgnoreCase("NONE")) {
            c = value;
            f.setText(value);
            if (jC.d(a).h(value) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                g.setImageResource(getContext().getResources().getIdentifier(value, "drawable", getContext().getPackageName()));
            } else if (value.equals("default_image")) {
                g.setImageResource(getContext().getResources().getIdentifier(value, "drawable", getContext().getPackageName()));
            } else {
                File iconFile = new File(jC.d(a).f(value));
                if (iconFile.exists()) {
                    Uri uri;
                    if (VERSION.SDK_INT >= 24) {
                        String providerName = getContext().getPackageName() + ".provider";
                        uri = FileProvider.a(getContext(), providerName, iconFile);
                    } else {
                        uri = Uri.fromFile(iconFile);
                    }

                    Glide.with(getContext()).load(uri).signature(kC.n()).error(2131165831).into(g);
                } else {
                    g.setImageResource(getContext().getResources().getIdentifier(value, "drawable", getContext().getPackageName()));
                }
            }

        } else {
            c = value;
            f.setText("NONE");
            g.setImageDrawable(null);
            g.setBackgroundColor(16777215);
        }
    }

    @Override
    public void onClick(View var1) {
        if (!mB.a()) {
            a();
        }
    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        n = var1;
    }

    public void setOrientationItem(int var1) {
        if (var1 == 0) {
            k.setVisibility(View.GONE);
            l.setVisibility(View.VISIBLE);
        } else {
            k.setVisibility(View.VISIBLE);
            l.setVisibility(View.GONE);
        }

    }
}
