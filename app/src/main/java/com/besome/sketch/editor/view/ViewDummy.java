package com.besome.sketch.editor.view;

import a.a.a.Rs;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewDummy extends RelativeLayout {

    public ImageView img_notallowed;

    public ImageView img_dummy;

    public LinearLayout layout_dummy;
    public int[] d;
    public int[] e;

    public boolean allowed;

    public ViewDummy(Context context) {
        super(context);
        this.d = new int[2];
        this.e = new int[2];
        this.allowed = false;
        initialize(context);
    }

    public final void initialize(Context context) {
        wB.a(context, this, 2131427411); // R.layout.dummy
        this.img_notallowed = (ImageView) findViewById(2131231163);
        this.img_dummy = (ImageView) findViewById(2131231137);
        this.layout_dummy = (LinearLayout) findViewById(2131231342);
    }

    public void b(View view) {
        Bitmap bitmap = createBitmapOfView(view);
        view.getLocationOnScreen(this.d);
        this.img_dummy.setImageBitmap(bitmap);
        this.img_dummy.setAlpha(0.5f);
    }

    public boolean getAllow() {
        return this.allowed;
    }

    public void setAllow(boolean allowed) {
        this.allowed = allowed;
        if (allowed) {
            this.img_notallowed.setVisibility(4);
        } else {
            this.img_notallowed.setVisibility(0);
        }
    }

    public void setDummyVisibility(int visibility) {
        this.layout_dummy.setVisibility(visibility);
    }

    public final Bitmap createBitmapOfView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    public ViewDummy(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.d = new int[2];
        this.e = new int[2];
        this.allowed = false;
        initialize(context);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void a(Rs rs) {
        char c;
        String str = rs.b;
        int hashCode = str.hashCode();
        if (hashCode == 108) {
            if (str.equals("l")) {
                c = '\t';
            }
            c = 65535;
        } else if (hashCode == 110) {
            if (str.equals("n")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == 112) {
            if (str.equals("p")) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode == 115) {
            if (str.equals("s")) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode != 118) {
            switch (hashCode) {
                case 97:
                    if (str.equals("a")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 98:
                    if (str.equals("b")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 99:
                    if (str.equals("c")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 100:
                    if (str.equals("d")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 101:
                    if (str.equals("e")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 102:
                    if (str.equals("f")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } else {
            if (str.equals("v")) {
                c = 7;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                this.img_dummy.setImageResource(2131166067); // R.drawable.selected_block_boolean
                break;
            case 1:
            case 2:
                this.img_dummy.setImageResource(2131166071); // R.drawable.selected_block_integer
                break;
            case 3:
                this.img_dummy.setImageResource(2131166073); // R.drawable.selected_block_string
                break;
            case 4:
                this.img_dummy.setImageResource(2131166072); // R.drawable.selected_block_loop
                break;
            case 5:
                this.img_dummy.setImageResource(2131166070); // R.drawable.selected_block_ifelse
                break;
            case 6:
                this.img_dummy.setImageResource(2131166069); // R.drawable.selected_block_final
                break;
            case 7:
            case '\b':
            case '\t':
            case '\n':
                this.img_dummy.setImageResource(2131166073); // R.drawable.selected_block_string
                break;
            default:
                this.img_dummy.setImageResource(2131166068); // R.drawable.selected_block_command
                break;
        }
        this.img_dummy.setAlpha(0.5f);
        rs.getLocationOnScreen(this.d);
    }

    public void a(View view, float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.layout_dummy.getVisibility() != 0) {
            setDummyVisibility(0);
        }
        getLocationOnScreen(this.e);
        this.layout_dummy.setX(((((this.d[0] - this.e[0]) + f) - f3) - this.img_notallowed.getWidth()) + f5);
        this.layout_dummy.setY((((this.d[1] - this.e[1]) + f2) - f4) + f6);
    }

    public void a(View view, float f, float f2, float f3, float f4) {
        a(view, f, f2, f3, f4, 0.0f, 0.0f);
    }

    public void a(int[] iArr) {
        this.img_dummy.getLocationOnScreen(iArr);
    }
}
