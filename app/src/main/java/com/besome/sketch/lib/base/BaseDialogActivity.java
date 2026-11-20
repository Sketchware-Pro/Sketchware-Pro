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
import pro.sketchware.databinding.CommonDialogLayoutBinding;

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
        CommonDialogLayoutBinding binding = CommonDialogLayoutBinding.inflate(getLayoutInflater());
        super.setContentView(binding.getRoot());

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#66000000"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(0);

        dialogIcon = binding.commonDialogIcon;
        dialogContainerView = binding.commonDialogContainer;
        dialogContentView = binding.commonDialogContent;
        dialogButtonsView = binding.commonDialogButtonLayout;
        dialogTitle = binding.commonDialogTvTitle;
        dialogTitleView = binding.commonDialogTitleLayout;
        dialogDefaultButton = binding.commonDialogDefaultButton;
        r = binding.commonDialogOkButton;
        s = binding.commonDialogCancelButton;
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
