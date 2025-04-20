package com.besome.sketch.editor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import a.a.a.Rs;
import a.a.a.wB;
import pro.sketchware.R;

public class ViewDummy extends RelativeLayout {
    public ImageView img_notallowed;
    public ImageView img_dummy;
    public LinearLayout layout_dummy;
    public int[] d;
    public int[] e;
    public boolean allowed;

    public ViewDummy(Context context) {
        super(context);
        d = new int[2];
        e = new int[2];
        allowed = false;
        initialize(context);
    }

    public ViewDummy(Context context, AttributeSet attrs) {
        super(context, attrs);
        d = new int[2];
        e = new int[2];
        allowed = false;
        initialize(context);
    }

    public final void initialize(Context context) {
        wB.a(context, this, R.layout.dummy);
        img_notallowed = findViewById(R.id.img_notallowed);
        img_dummy = findViewById(R.id.img_dummy);
        layout_dummy = findViewById(R.id.layout_dummy);
    }

    public void b(View view) {
        Bitmap bitmap = createBitmapOfView(view);
        view.getLocationOnScreen(d);
        img_dummy.setImageBitmap(bitmap);
        img_dummy.setAlpha(0.5f);
    }

    public boolean getAllow() {
        return allowed;
    }

    public void setAllow(boolean allowed) {
        this.allowed = allowed;
        if (allowed) {
            img_notallowed.setVisibility(View.INVISIBLE);
        } else {
            img_notallowed.setVisibility(View.VISIBLE);
        }
    }

    public void setDummyVisibility(int visibility) {
        layout_dummy.setVisibility(visibility);
    }

    public final Bitmap createBitmapOfView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    public void a(Rs rs) {
        switch (rs.b) {
            case "b":
                img_dummy.setImageResource(R.drawable.selected_block_boolean);
                break;
            case "d":
            case "n":
                img_dummy.setImageResource(R.drawable.selected_block_integer);
                break;
            case "s", "v", "p", "l", "a":
                img_dummy.setImageResource(R.drawable.selected_block_string);
                break;
            case "c":
                img_dummy.setImageResource(R.drawable.selected_block_loop);
                break;
            case "e":
                img_dummy.setImageResource(R.drawable.selected_block_ifelse);
                break;
            case "f":
                img_dummy.setImageResource(R.drawable.selected_block_final);
                break;
            default:
                img_dummy.setImageResource(R.drawable.selected_block_command);
                break;
        }
        img_dummy.setAlpha(0.5f);
        rs.getLocationOnScreen(d);
    }

    public void a(View view, float f, float f2, float f3, float f4, float f5, float f6) {
        if (layout_dummy.getVisibility() != View.VISIBLE) {
            setDummyVisibility(View.VISIBLE);
        }
        getLocationOnScreen(e);
        layout_dummy.setX(((((d[0] - e[0]) + f) - f3) - img_notallowed.getWidth()) + f5);
        layout_dummy.setY((((d[1] - e[1]) + f2) - f4) + f6);
    }

    public void a(View view, float f, float f2, float f3, float f4) {
        a(view, f, f2, f3, f4, 0.0f, 0.0f);
    }

    public void a(int[] iArr) {
        img_dummy.getLocationOnScreen(iArr);
    }
}
