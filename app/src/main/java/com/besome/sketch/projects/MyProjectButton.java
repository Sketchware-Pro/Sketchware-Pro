package com.besome.sketch.projects;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.wB;

public class MyProjectButton extends LinearLayout {

    /* Referenced by a.a.a.IC */
    public int b;
    public ImageView icon;
    public TextView name;

    public MyProjectButton(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));

        wB.a(context, this, Resources.layout.myproject_button);
        icon = findViewById(Resources.id.icon);
        name = findViewById(Resources.id.name);
    }
}
