//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

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
import java.util.ArrayList;
import java.util.Iterator;

public class Pw extends RelativeLayout implements View.OnClickListener {
    public String a = "";
    public String b = "";
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public ArrayList<ProjectFileBean> i;
    public ViewGroup j;
    public Kw k;

    public Pw(Context var1, boolean var2) {
        super(var1);
        this.a(var1, var2);
    }

    public final RadioButton a(String var1) {
        RadioButton var2 = new RadioButton(this.getContext());
        var2.setText(var1);
        var2.setTag(var1);
        LinearLayout.LayoutParams var3 = new LinearLayout.LayoutParams(-1, (int)(wB.a(this.getContext(), 1.0F) * 40.0F));
        var2.setGravity(19);
        var2.setLayoutParams(var3);
        return var2;
    }

    public final void a() {
        aB var1 = new aB((Activity)this.getContext());
        var1.b(this.c.getText().toString());
        var1.a(this.f);
        View var2 = wB.a(this.getContext(), 2131427643);
        this.j = (ViewGroup)var2.findViewById(2131231668);
        this.j.addView(this.a("none"));
        Iterator var3 = this.i.iterator();

        while(var3.hasNext()) {
            RadioButton var4 = this.a(((ProjectFileBean)var3.next()).fileName);
            this.j.addView(var4);
        }

        int var5 = this.j.getChildCount();
        ViewGroup var7 = this.j;
        int var6 = 0;
        ((RadioButton)var7.getChildAt(0)).setChecked(true);

        while(var6 < var5) {
            RadioButton var8 = (RadioButton)this.j.getChildAt(var6);
            if (var8.getTag().toString().equals(this.b)) {
                var8.setChecked(true);
                break;
            }

            ++var6;
        }

        var1.a(var2);
        var1.b(xB.b().a(this.getContext(), 2131625035), new Nw(this, var1));
        var1.a(xB.b().a(this.getContext(), 2131624974), new Ow(this, var1));
        var1.show();
    }

    public final void a(Context var1, boolean var2) {
        wB.a(this.getContext(), this, 2131427648);
        this.c = (TextView)this.findViewById(2131232055);
        this.d = (TextView)this.findViewById(2131232270);
        this.g = this.findViewById(2131231626);
        this.h = this.findViewById(2131231628);
        this.e = (ImageView)this.findViewById(2131231155);
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
            String var3 = this.a;
            byte var2 = -1;
            if (var3.hashCode() == 1118712953 && var3.equals("property_custom_view_listview")) {
                var2 = 0;
            }

            if (var2 == 0) {
                this.a();
            }

        }
    }

    public void setCustomView(ArrayList<ProjectFileBean> var1) {
        this.i = var1;
    }

    public void setKey(String var1) {
        this.a = var1;
        int var2 = this.getResources().getIdentifier(var1, "string", this.getContext().getPackageName());
        if (var2 > 0) {
            this.c.setText(xB.b().a(this.getResources(), var2));
            this.f = 2131165638;
            if (this.h.getVisibility() == 0) {
                ImageView var3 = (ImageView)this.findViewById(2131231151);
                TextView var4 = (TextView)this.findViewById(2131232195);
                var3.setImageResource(this.f);
                var4.setText(xB.b().a(this.getContext(), var2));
            } else {
                this.e.setImageResource(this.f);
            }
        }

    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        this.k = var1;
    }

    public void setOrientationItem(int var1) {
        if (var1 == 0) {
            this.g.setVisibility(8);
            this.h.setVisibility(0);
        } else {
            this.g.setVisibility(0);
            this.h.setVisibility(8);
        }

    }

    public void setValue(String var1) {
        String var2;
        label11: {
            if (var1 != null) {
                var2 = var1;
                if (var1.length() > 0) {
                    break label11;
                }
            }

            var2 = "none";
        }

        this.b = var2;
        this.d.setText(var2);
    }
}
