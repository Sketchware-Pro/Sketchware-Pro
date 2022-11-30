//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
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
import java.util.Iterator;

public class tx extends RelativeLayout implements OnClickListener {
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
        this.a = var3;
        this.a(var1, var2, var4);
    }

    public final LinearLayout a(String var1, boolean var2) {
        float var3 = wB.a(this.getContext(), 1.0F);
        LinearLayout var4 = new LinearLayout(this.getContext());
        var4.setLayoutParams(new LayoutParams(-1, (int) (60.0F * var3)));
        var4.setGravity(19);
        var4.setOrientation(0);
        TextView var5 = new TextView(this.getContext());
        LinearLayout.LayoutParams var6 = new LinearLayout.LayoutParams(0, -2);
        var6.weight = 1.0F;
        var6.rightMargin = (int) (8.0F * var3);
        var5.setLayoutParams(var6);
        var5.setText(var1);
        var4.addView(var5);
        ImageView var11 = new ImageView(this.getContext());
        var11.setScaleType(ScaleType.CENTER_CROP);
        int var7 = (int) (var3 * 48.0F);
        var11.setLayoutParams(new LayoutParams(var7, var7));
        if (!var1.toLowerCase().equals("NONE".toLowerCase())) {
            if (var2) {
                var11.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
            } else {
                File var12 = new File(jC.d(this.a).f(var1));
                if (var12.exists()) {
                    Uri var10;
                    if (VERSION.SDK_INT >= 24) {
                        Context var8 = this.getContext();
                        StringBuilder var9 = new StringBuilder();
                        var9.append(this.getContext().getPackageName());
                        var9.append(".provider");
                        var10 = FileProvider.a(var8, var9.toString(), var12);
                    } else {
                        var10 = Uri.fromFile(var12);
                    }

                    Glide.with(this.getContext()).load(var10).signature(kC.n()).error(2131165831).into(var11);
                } else {
                    var11.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
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
        RadioButton var2 = new RadioButton(this.getContext());
        var2.setText("");
        var2.setTag(var1);
        LayoutParams var3 = new LayoutParams(-2, (int) (wB.a(this.getContext(), 1.0F) * 60.0F));
        var2.setGravity(19);
        var2.setLayoutParams(var3);
        return var2;
    }

    public final void a() {
        aB var1 = new aB((Activity) this.getContext());
        var1.b(this.e.getText().toString());
        var1.a(this.m);
        View var2 = wB.a(this.getContext(), 2131427641);
        ScrollView var3 = (ScrollView) var2.findViewById(2131231692);
        this.i = (RadioGroup) var2.findViewById(2131231667);
        this.j = (LinearLayout) var2.findViewById(2131230932);
        ArrayList var4 = jC.d(this.a).m();
        if (xq.a(this.a) || xq.b(this.a)) {
            if (this.d) {
                var4.add(0, "default_image");
            } else {
                var4.add(0, "NONE");
            }
        }

        Iterator var5 = var4.iterator();

        RadioButton var8;
        RadioButton var9;
        for (var9 = null; var5.hasNext(); var9 = var8) {
            String var6 = (String) var5.next();
            RadioButton var7 = this.a(var6);
            this.i.addView(var7);
            var8 = var9;
            if (var6.equals(this.c)) {
                var7.setChecked(true);
                var8 = var7;
            }

            LinearLayout var10;
            if (xq.a(this.a)) {
                if (var6.equals("default_image")) {
                    var10 = this.a(var6, true);
                } else {
                    var10 = this.a(var6, false);
                }
            } else if (xq.b(this.a)) {
                if (var6.equals("default_image")) {
                    var10 = this.a(var6, true);
                } else {
                    var10 = this.a(var6, false);
                }
            } else {
                var10 = this.a(var6, true);
            }

            var10.setOnClickListener(new px(this));
            this.j.addView(var10);
        }

        var8 = var9;
        if (var9 == null) {
            var8 = (RadioButton) this.i.getChildAt(0);
            var8.setChecked(true);
        }

        var1.a(var2);
        var1.b(xB.b().a(this.getContext(), 2131625035), new qx(this, var1));
        var1.a(xB.b().a(this.getContext(), 2131624974), new rx(this, var1));
        var1.setOnShowListener(new sx(this, var3, var8));
        var1.show();
    }

    public final void a(Context var1, boolean var2, boolean var3) {
        wB.a(var1, this, 2131427647);
        this.e = (TextView) this.findViewById(2131232055);
        this.f = (TextView) this.findViewById(2131232270);
        this.g = (ImageView) this.findViewById(2131232321);
        this.h = (ImageView) this.findViewById(2131231155);
        this.k = this.findViewById(2131231626);
        this.l = this.findViewById(2131231628);
        this.d = var3;
        if (var2) {
            this.setOnClickListener(this);
            this.setSoundEffectsEnabled(true);
        }

    }

    public String getKey() {
        return this.b;
    }

    public void setKey(String var1) {
        this.b = var1;
        int var2 = this.getResources().getIdentifier(var1, "string", this.getContext().getPackageName());
        if (var2 > 0) {
            this.e.setText(xB.b().a(this.getResources(), var2));
            var1 = this.b;
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
                    this.m = 2131166227;
                }
            } else {
                this.m = 2131165811;
            }

            if (this.l.getVisibility() == 0) {
                ImageView var6 = (ImageView) this.findViewById(2131231151);
                TextView var5 = (TextView) this.findViewById(2131232195);
                var6.setImageResource(this.m);
                var5.setText(xB.b().a(this.getContext(), var2));
            } else {
                this.h.setImageResource(this.m);
            }
        }

    }

    public String getValue() {
        return this.c;
    }

    public void setValue(String var1) {
        if (var1 != null && !var1.toLowerCase().equals("NONE".toLowerCase())) {
            this.c = var1;
            this.f.setText(var1);
            if (jC.d(this.a).h(var1) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                this.g.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
            } else if (var1.equals("default_image")) {
                this.g.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
            } else {
                File var2 = new File(jC.d(this.a).f(var1));
                if (var2.exists()) {
                    Uri var5;
                    if (VERSION.SDK_INT >= 24) {
                        Context var4 = this.getContext();
                        StringBuilder var3 = new StringBuilder();
                        var3.append(this.getContext().getPackageName());
                        var3.append(".provider");
                        var5 = FileProvider.a(var4, var3.toString(), var2);
                    } else {
                        var5 = Uri.fromFile(var2);
                    }

                    Glide.with(this.getContext()).load(var5).signature(kC.n()).error(2131165831).into(this.g);
                } else {
                    this.g.setImageResource(this.getContext().getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName()));
                }
            }

        } else {
            this.c = var1;
            this.f.setText("NONE");
            this.g.setImageDrawable((Drawable) null);
            this.g.setBackgroundColor(16777215);
        }
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            this.a();
        }
    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        this.n = var1;
    }

    public void setOrientationItem(int var1) {
        if (var1 == 0) {
            this.k.setVisibility(8);
            this.l.setVisibility(0);
        } else {
            this.k.setVisibility(0);
            this.l.setVisibility(8);
        }

    }
}
