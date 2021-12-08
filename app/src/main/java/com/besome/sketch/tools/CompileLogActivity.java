package com.besome.sketch.tools;

import android.app.AlertDialog;
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
import android.widget.TextView;

import com.besome.sketch.lib.base.BaseActivity;
import com.sketchware.remod.Resources;

import mod.hey.studios.util.CompileLogHelper;
import mod.hey.studios.util.Helper;

public class CompileLogActivity extends BaseActivity {

    private TextView tv_compile_log;
    private HorizontalScrollView err_hScroll;
    private ScrollView err_vScroll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.compile_log);

        View rootLayout = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        ImageView back = findViewById(Resources.id.ig_toolbar_back);
        TextView title = findViewById(Resources.id.tx_toolbar_title);
        ImageView menu = findViewById(Resources.id.ig_toolbar_load_file);
        tv_compile_log = findViewById(Resources.id.tv_compile_log);
        err_hScroll = rootLayout.findViewWithTag("err_hScroll");
        err_vScroll = rootLayout.findViewWithTag("err_vScroll");

        back.setOnClickListener(v -> onBackPressed());
        Helper.applyRippleToToolbarView(back);

        if (getIntent().getBooleanExtra("showingLastError", false)) {
            title.setText("Last compile log");
        } else {
            title.setText("Compile log");
        }

        menu.setImageResource(Resources.drawable.ic_more_vert_white_24dp);
        menu.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(menu);

        final String wrapTextLabel = "Wrap text";
        final String monospacedFontLabel = "Monospaced font";
        final String fontSizeLabel = "Font size";

        PopupMenu options = new PopupMenu(getApplicationContext(), menu);
        options.getMenu().add(wrapTextLabel).setCheckable(true);
        options.getMenu().add(monospacedFontLabel).setCheckable(true).setChecked(true);
        options.getMenu().add(fontSizeLabel);

        options.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getTitle().toString()) {
                case wrapTextLabel:
                    menuItem.setChecked(!menuItem.isChecked());
                    toggleWrapText(menuItem.isChecked());
                    break;

                case monospacedFontLabel:
                    menuItem.setChecked(!menuItem.isChecked());
                    toggleMonospacedText(menuItem.isChecked());
                    break;

                case fontSizeLabel:
                    changeFontSizeDialog();
                    break;

                default:
                    return false;
            }

            return true;
        });

        menu.setOnClickListener(v -> options.show());

        String error = getIntent().getStringExtra("error");
        if (error == null) {
            finish();
        } else {
            tv_compile_log.setText(CompileLogHelper.colorErrsAndWarnings(error));
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
                .setTitle("Select font size")
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialog, which) ->
                        tv_compile_log.setTextSize((float) picker.getValue()))
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
