package com.besome.sketch.tools;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.besome.sketch.common.SrcViewerActivity;
import com.besome.sketch.lib.base.BaseActivity;
import com.sketchware.remod.Resources;

import mod.hey.studios.util.CompileLogHelper;

public class CompileLogActivity extends BaseActivity {
    ViewGroup rootLayout;
    ImageView menu;
    TextView tv_compile_log;
    HorizontalScrollView err_hScroll;
    ScrollView err_vScroll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.compile_log);
        rootLayout = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        menu = findViewById(Resources.id.ig_toolbar_load_file);
        tv_compile_log = findViewById(Resources.id.tv_compile_log);
        err_hScroll = (HorizontalScrollView) rootLayout.findViewWithTag("err_hScroll");
        err_vScroll = (ScrollView) rootLayout.findViewWithTag("err_vScroll");

        ((TextView) findViewById(Resources.id.tx_toolbar_title)).setText(
                (getIntent().getBooleanExtra("showingLastError", false))
                        ? "Last Compile Log" : "Compilation Log");

        findViewById(Resources.id.ig_toolbar_back).setOnClickListener(v -> onBackPressed());
        menu.setImageResource(Resources.drawable.ic_more_vert_white_24dp);
        menu.setVisibility(View.VISIBLE);
        PopupMenu options = new PopupMenu(getApplicationContext(), menu);
        options.getMenu().add("Wrap Text").setCheckable(true);
        options.getMenu().add("Monospaced Font").setCheckable(true).setChecked(true);
        options.getMenu().add("Font Size");
        options.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) menuItem -> {
            switch (menuItem.getTitle().toString()) {

                case "Wrap Text": {
                    menuItem.setChecked(!menuItem.isChecked());
                    toggleWrapText(menuItem.isChecked());
                    break;
                }
                case "Monospaced Font": {
                    menuItem.setChecked(!menuItem.isChecked());
                    toggleMonospacedText(menuItem.isChecked());
                    break;
                }
                case "Font Size": {
                    changeFontSizeDialog();
                    break;
                }
            }
            return true;

        });

        menu.setOnClickListener(v ->
                options.show());


        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {

            tv_compile_log.setText(CompileLogHelper.colorErrsAndWarnings(intent.getStringExtra("error")));
            tv_compile_log.setTextIsSelectable(true);
        }
    }

    private void toggleWrapText(boolean isChecked) {
        if (isChecked) {
            err_vScroll.removeAllViews();
            if (tv_compile_log.getParent() != null) {
                ((ViewGroup) tv_compile_log.getParent()).removeView(tv_compile_log);
            }
            err_vScroll.addView(tv_compile_log);

        } else {
            err_vScroll.removeAllViews();
            if (tv_compile_log.getParent() != null) {
                ((ViewGroup) tv_compile_log.getParent()).removeView(tv_compile_log);
            }
            err_hScroll.removeAllViews();
            err_hScroll.addView(tv_compile_log);
            err_vScroll.addView(err_hScroll);
        }
    }

    private void toggleMonospacedText(boolean isChecked) {
        if (isChecked) {
            tv_compile_log.setTypeface(Typeface.MONOSPACE);
        } else {
            tv_compile_log.setTypeface(Typeface.DEFAULT);
        }
    }

    private void changeFontSizeDialog() {
        NumberPicker picker = new NumberPicker(this);
        picker.setMinValue(10); //Must not be less than setValue(), which is currently 11 in compile_log.xml
        picker.setMaxValue(70);
        picker.setWrapSelectorWheel(false);
        picker.setValue((int) (tv_compile_log.getTextSize() / getResources().getDisplayMetrics().scaledDensity));

        LinearLayout layout = new LinearLayout(this);
        layout.addView(picker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(this)
                .setTitle("Select Font Size")
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    tv_compile_log.setTextSize((float) picker.getValue());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
