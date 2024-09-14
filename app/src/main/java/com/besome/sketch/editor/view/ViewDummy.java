package com.besome.sketch.editor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sketchware.remod.R;

import a.a.a.Rs;
import a.a.a.wB;

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
        wB.a(context, this, R.layout.dummy);
        this.img_notallowed = (ImageView) findViewById(R.id.img_notallowed);
        this.img_dummy = (ImageView) findViewById(R.id.img_dummy);
        this.layout_dummy = (LinearLayout) findViewById(R.id.layout_dummy);
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
            this.img_notallowed.setVisibility(View.INVISIBLE);
        } else {
            this.img_notallowed.setVisibility(View.VISIBLE);
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

    public void a(Rs rs) {
        switch (rs.b) {
            case "b":
                this.img_dummy.setImageResource(R.drawable.selected_block_boolean);
                break;
            case "d":
            case "n":
                this.img_dummy.setImageResource(R.drawable.selected_block_integer);
                break;
            case "s":
                this.img_dummy.setImageResource(R.drawable.selected_block_string);
                break;
            case "c":
                this.img_dummy.setImageResource(R.drawable.selected_block_loop);
                break;
            case "e":
                this.img_dummy.setImageResource(R.drawable.selected_block_ifelse);
                break;
            case "f":
                this.img_dummy.setImageResource(R.drawable.selected_block_final);
                break;
            case "v":
            case "p":
            case "l":
            case "a":
                this.img_dummy.setImageResource(R.drawable.selected_block_string);
                break;
            default:
                this.img_dummy.setImageResource(R.drawable.selected_block_command);
                break;
        }
        this.img_dummy.setAlpha(0.5f);
        rs.getLocationOnScreen(this.d);
    }

    public void a(View view, float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.layout_dummy.getVisibility() != View.VISIBLE) {
            setDummyVisibility(View.VISIBLE);
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
