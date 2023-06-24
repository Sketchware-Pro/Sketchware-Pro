package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import com.besome.sketch.beans.ProjectResourceBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class tx extends RelativeLayout implements View.OnClickListener {
    public String a;
    public String b;
    public String c;
    public boolean d;
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

    public tx(Context context, boolean z, String str, boolean z2) {
        super(context);
        this.d = false;
        this.a = str;
        a(context, z, z2);
    }

    public String getKey() {
        return this.b;
    }

    public String getValue() {
        return this.c;
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        a();
    }

    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.e.setText(xB.b().a(getResources(), identifier));
            String str2 = this.b;
            char c = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -1949646187) {
                if (hashCode == -1545963919 && str2.equals("property_image")) {
                    c = 0;
                }
            } else if (str2.equals("property_background_resource")) {
                c = 1;
            }
            if (c == 0) {
                this.m = 2131165811;
            } else if (c == 1) {
                this.m = 2131166227;
            }
            if (this.l.getVisibility() == 0) {
                ((ImageView) findViewById(2131231151)).setImageResource(this.m);
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            this.h.setImageResource(this.m);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.n = kw;
    }

    public void setOrientationItem(int i) {
        if (i == 0) {
            this.k.setVisibility(8);
            this.l.setVisibility(0);
            return;
        }
        this.k.setVisibility(0);
        this.l.setVisibility(8);
    }

    public void setValue(String str) {
        Uri fromFile;
        if (str != null && !str.toLowerCase().equals("NONE".toLowerCase())) {
            this.c = str;
            this.f.setText(str);
            if (jC.d(this.a).h(str) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                this.g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            } else if (str.equals("default_image")) {
                this.g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            } else {
                File file = new File(jC.d(this.a).f(str));
                if (file.exists()) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        Context context = getContext();
                        fromFile = FileProvider.getUriForFile(context, getContext().getPackageName() + ".provider", file);
                    } else {
                        fromFile = Uri.fromFile(file);
                    }
                    Glide.with(getContext()).load(fromFile).signature((Key) kC.n()).error(2131165831).into(this.g);
                    return;
                }
                this.g.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                return;
            }
        }
        this.c = str;
        this.f.setText("NONE");
        this.g.setImageDrawable(null);
        this.g.setBackgroundColor(16777215);
    }

    public final void a(Context context, boolean z, boolean z2) {
        wB.a(context, this, 2131427647);
        this.e = (TextView) findViewById(2131232055);
        this.f = (TextView) findViewById(2131232270);
        this.g = (ImageView) findViewById(2131232321);
        this.h = (ImageView) findViewById(2131231155);
        this.k = findViewById(2131231626);
        this.l = findViewById(2131231628);
        this.d = z2;
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public final void a() {
        LinearLayout a2;
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.e.getText().toString());
        aBVar.a(this.m);
        View a3 = wB.a(getContext(), 2131427641);
        ScrollView scrollView = (ScrollView) a3.findViewById(2131231692);
        this.i = (RadioGroup) a3.findViewById(2131231667);
        this.j = (LinearLayout) a3.findViewById(2131230932);
        ArrayList<String> m = jC.d(this.a).m();
        if (xq.a(this.a) || xq.b(this.a)) {
            if (this.d) {
                m.add(0, "default_image");
            } else {
                m.add(0, "NONE");
            }
        }
        Iterator<String> it = m.iterator();
        RadioButton radioButton = null;
        while (it.hasNext()) {
            String next = it.next();
            RadioButton a4 = a(next);
            this.i.addView(a4);
            if (next.equals(this.c)) {
                a4.setChecked(true);
                radioButton = a4;
            }
            if (xq.a(this.a)) {
                if (next.equals("default_image")) {
                    a2 = a(next, true);
                } else {
                    a2 = a(next, false);
                }
            } else if (xq.b(this.a)) {
                if (next.equals("default_image")) {
                    a2 = a(next, true);
                } else {
                    a2 = a(next, false);
                }
            } else {
                a2 = a(next, true);
            }
            a2.setOnClickListener(new px(this));
            this.j.addView(a2);
        }
        if (radioButton == null) {
            radioButton = (RadioButton) this.i.getChildAt(0);
            radioButton.setChecked(true);
        }
        aBVar.a(a3);
        aBVar.b(xB.b().a(getContext(), 2131625035), new qx(this, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new rx(this, aBVar));
        aBVar.setOnShowListener(new sx(this, scrollView, radioButton));
        aBVar.show();
    }

    public final RadioButton a(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText("");
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, (int) (wB.a(getContext(), 1.0f) * 60.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final LinearLayout a(String str, boolean z) {
        Uri fromFile;
        float a2 = wB.a(getContext(), 1.0f);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (60.0f * a2)));
        linearLayout.setGravity(19);
        linearLayout.setOrientation(0);
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        layoutParams.rightMargin = (int) (8.0f * a2);
        textView.setLayoutParams(layoutParams);
        textView.setText(str);
        linearLayout.addView(textView);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int i = (int) (a2 * 48.0f);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        if (!str.toLowerCase().equals("NONE".toLowerCase())) {
            if (z) {
                imageView.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
            } else {
                File file = new File(jC.d(this.a).f(str));
                if (file.exists()) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        Context context = getContext();
                        fromFile = FileProvider.getUriForFile(context, getContext().getPackageName() + ".provider", file);
                    } else {
                        fromFile = Uri.fromFile(file);
                    }
                    Glide.with(getContext()).load(fromFile).signature((Key) kC.n()).error(2131165831).into(imageView);
                } else {
                    imageView.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
                }
            }
            imageView.setBackgroundResource(2131165345);
        } else {
            imageView.setBackgroundResource(2131165345);
        }
        linearLayout.addView(imageView);
        return linearLayout;
    }
}
