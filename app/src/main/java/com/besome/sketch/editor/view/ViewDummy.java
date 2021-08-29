package com.besome.sketch.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexItem;
import com.google.android.gms.common.GoogleApiAvailabilityLight;

import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.wB;

public class ViewDummy extends RelativeLayout {
    public ImageView a;
    public ImageView b;
    public LinearLayout c;
    public int[] d;
    public int[] e;
    public boolean f;

    public ViewDummy(Context context) {
        super(context);
        this.d = new int[2];
        this.e = new int[2];
        this.f = false;
        a(context);
    }

    public ViewDummy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = new int[2];
        this.e = new int[2];
        this.f = false;
        a(context);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        wB.a(context, this, 2131427411);
        this.a = (ImageView) findViewById(2131231163);
        this.b = (ImageView) findViewById(2131231137);
        this.c = (LinearLayout) findViewById(2131231342);
    }

    public void b(View view) {
        Bitmap a2 = a(view);
        view.getLocationOnScreen(this.d);
        this.b.setImageBitmap(a2);
        this.b.setAlpha(0.5f);
    }

    public boolean getAllow() {
        return this.f;
    }

    @SuppressLint("WrongConstant")
    public void setAllow(boolean z) {
        this.f = z;
        if (z) {
            this.a.setVisibility(4);
        } else {
            this.a.setVisibility(0);
        }
    }

    public void setDummyVisibility(int i) {
        this.c.setVisibility(i);
    }

    public final Bitmap a(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    @SuppressLint("ResourceType")
    public void a(Rs rs) {
        char c2 = 0;
        String str = ((Ts) rs).b;
        int hashCode = str.hashCode();
        if (hashCode != 108) {
            if (hashCode != 110) {
                if (hashCode != 112) {
                    if (hashCode != 115) {
                        if (hashCode != 118) {
                            switch (hashCode) {
                                case 97:
                                    if (str.equals("a")) {
                                        c2 = '\n';
                                        break;
                                    }
                                    break;
                                case 98:
                                    if (str.equals("b")) {
                                        c2 = 0;
                                        break;
                                    }
                                    break;
                                case 99:
                                    if (str.equals("c")) {
                                        c2 = 4;
                                        break;
                                    }
                                    break;
                                case 100:
                                    if (str.equals("d")) {
                                        c2 = 1;
                                        break;
                                    }
                                    break;
                                case 101:
                                    if (str.equals("e")) {
                                        c2 = 5;
                                        break;
                                    }
                                    break;
                                case 102:
                                    if (str.equals("f")) {
                                        c2 = 6;
                                        break;
                                    }
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    this.b.setImageResource(2131166067);
                                    break;
                                case 1:
                                case 2:
                                    this.b.setImageResource(2131166071);
                                    break;
                                case 3:
                                    this.b.setImageResource(2131166073);
                                    break;
                                case 4:
                                    this.b.setImageResource(2131166072);
                                    break;
                                case 5:
                                    this.b.setImageResource(2131166070);
                                    break;
                                case 6:
                                    this.b.setImageResource(2131166069);
                                    break;
                                case 7:
                                case '\b':
                                case '\t':
                                case '\n':
                                    this.b.setImageResource(2131166073);
                                    break;
                                default:
                                    this.b.setImageResource(2131166068);
                                    break;
                            }
                            this.b.setAlpha(0.5f);
                            rs.getLocationOnScreen(this.d);
                        } else if (str.equals("v")) {
                            c2 = 7;
                            switch (c2) {
                            }
                            this.b.setAlpha(0.5f);
                            rs.getLocationOnScreen(this.d);
                        }
                    } else if (str.equals("s")) {
                        c2 = 3;
                        switch (c2) {
                        }
                        this.b.setAlpha(0.5f);
                        rs.getLocationOnScreen(this.d);
                    }
                } else if (str.equals("p")) {
                    c2 = '\b';
                    switch (c2) {
                    }
                    this.b.setAlpha(0.5f);
                    rs.getLocationOnScreen(this.d);
                }
            } else if (str.equals(GoogleApiAvailabilityLight.TRACKING_SOURCE_NOTIFICATION)) {
                c2 = 2;
                switch (c2) {
                }
                this.b.setAlpha(0.5f);
                rs.getLocationOnScreen(this.d);
            }
        } else if (str.equals("l")) {
            c2 = '\t';
            switch (c2) {
            }
            this.b.setAlpha(0.5f);
            rs.getLocationOnScreen(this.d);
        }
        c2 = 65535;
        switch (c2) {
        }
        this.b.setAlpha(0.5f);
        rs.getLocationOnScreen(this.d);
    }

    @SuppressLint("WrongConstant")
    public void a(View view, float f2, float f3, float f4, float f5, float f6, float f7) {
        if (this.c.getVisibility() != 0) {
            setDummyVisibility(0);
        }
        getLocationOnScreen(this.e);
        this.c.setX((((((float) (this.d[0] - this.e[0])) + f2) - f4) - ((float) this.a.getWidth())) + f6);
        this.c.setY(((((float) (this.d[1] - this.e[1])) + f3) - f5) + f7);
    }

    public void a(View view, float f2, float f3, float f4, float f5) {
        a(view, f2, f3, f4, f5, FlexItem.FLEX_GROW_DEFAULT, FlexItem.FLEX_GROW_DEFAULT);
    }

    public void a(int[] iArr) {
        this.b.getLocationOnScreen(iArr);
    }
}
