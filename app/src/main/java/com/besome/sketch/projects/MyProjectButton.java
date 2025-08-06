package com.besome.sketch.projects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import a.a.a.wB;
import pro.sketchware.R;

public class MyProjectButton extends LinearLayout {
    private int id;
    private ImageView icon;
    private TextView name;

    public MyProjectButton(Context context) {
        this(context, null);
    }

    public MyProjectButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public static MyProjectButton create(Context context, int id, @DrawableRes int resId, String label) {
        MyProjectButton button = new MyProjectButton(context);
        button.id = id;
        button.icon.setImageResource(resId);
        button.name.setText(label);
        return button;
    }

    private void initialize(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));

        wB.a(context, this, R.layout.myproject_button);
        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
    }

    public int getButtonId() {
        return id;
    }
}
