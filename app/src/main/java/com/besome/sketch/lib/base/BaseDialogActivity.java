package com.besome.sketch.lib.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.mB;
import a.a.a.wB;
import pro.sketchware.R;

public class BaseDialogActivity extends BaseAppCompatActivity {

    public LinearLayout dialogContainerView;
    public ViewGroup dialogContentView;
    public LinearLayout dialogButtonsView;
    public LinearLayout dialogTitleView;
    public TextView dialogTitle;
    public ImageView dialogIcon;
    public TextView dialogDefaultButton;
    public TextView r; // dialogPositiveButton
    public TextView s; // dialogNegativeButton

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.common_dialog_layout);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#66000000"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(0);

        dialogIcon = findViewById(R.id.common_dialog_icon);
        dialogContainerView = findViewById(R.id.common_dialog_container);
        dialogContentView = findViewById(R.id.common_dialog_content);
        dialogButtonsView = findViewById(R.id.common_dialog_button_layout);
        dialogTitle = findViewById(R.id.common_dialog_tv_title);
        dialogTitleView = findViewById(R.id.common_dialog_title_layout);
        dialogDefaultButton = findViewById(R.id.common_dialog_default_button);
        r = findViewById(R.id.common_dialog_ok_button);
        s = findViewById(R.id.common_dialog_cancel_button);
        dialogContainerView.setOnClickListener(v -> {
            if (!mB.a()) {
                finish();
            }
        });
        dialogIcon.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(int layoutResID) {
        wB.a(this, dialogContentView, layoutResID);
    }

    public void a(boolean var1) {
        if (var1) {
            dialogContainerView.setOnClickListener(v -> {
                if (!mB.a()) {
                    finish();
                }
            });
            return;
        }
        dialogContainerView.setOnClickListener(null);
    }

    public void b(String cancelText) {
        s.setText(cancelText);
    }

    public void c(String defaultText) {
        dialogDefaultButton.setText(defaultText);
    }

    public void d(String okText) {
        r.setText(okText);
    }

    public void e(String title) {
        dialogTitle.setText(title);
    }

    public void f(int iconResId) {
        dialogIcon.setImageResource(iconResId);
        dialogIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void l() {
        dialogButtonsView.setVisibility(View.GONE);
    }

    public void m() {
        dialogTitleView.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(View view) {
        dialogContentView.addView(view);
    }
}
