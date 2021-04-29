package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.Zx;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class PropertyColorItem extends RelativeLayout implements View.OnClickListener {

    public Context a;
    public String b;
    public int c;
    public TextView d;
    public TextView e;
    public View f;
    public ImageView g;
    public int h;
    public View i;
    public View j;
    public Kw k;

    public PropertyColorItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return b;
    }

    public void setKey(String str) {
        b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            d.setText(xB.b().a(getResources(), identifier));
            h = Resources.drawable.color_palette_48;
            if (j.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(h);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            g.setImageResource(h);
        }
    }

    public int getValue() {
        return c;
    }

    public void setValue(int value) {
        c = value;
        if (value == 0) {
            e.setText("TRANSPARENT");
            f.setBackgroundColor(value);
        } else if (value == 0xffffff) {
            e.setText("NONE");
            f.setBackgroundColor(value);
        } else {
            e.setText(String.format("#%08X", value));
            f.setBackgroundColor(value);
        }
    }

    public void onClick(View view) {
        if (!mB.a()) {
            a();
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        k = kw;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            i.setVisibility(GONE);
            j.setVisibility(VISIBLE);
            return;
        }
        i.setVisibility(VISIBLE);
        j.setVisibility(GONE);
    }

    public final void a(Context context, boolean z) {
        a = context;
        wB.a(context, this, Resources.layout.property_color_item);
        d = findViewById(Resources.id.tv_name);
        e = findViewById(Resources.id.tv_value);
        f = findViewById(Resources.id.view_color);
        g = findViewById(Resources.id.img_left_icon);
        i = findViewById(Resources.id.property_item);
        j = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public final void a() {
        boolean z;
        boolean z2;
        View view = wB.a(a, Resources.layout.color_picker);
        view.setAnimation(AnimationUtils.loadAnimation(a, Resources.anim.abc_fade_in));
        if (b.equals("property_background_color")) {
            z2 = true;
            z = true;
        } else {
            z2 = false;
            z = false;
        }
        Zx zx = new Zx(view, (Activity) a, c, z2, z);
        zx.a(new Zx.b() {
            @Override
            public void a(int i) {
                setValue(i);
                if (k != null) {
                    k.a(b, c);
                }
            }
        });
        zx.setAnimationStyle(Resources.anim.abc_fade_in);
        zx.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
