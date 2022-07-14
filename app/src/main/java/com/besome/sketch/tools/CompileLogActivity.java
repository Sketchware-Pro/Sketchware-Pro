package com.besome.sketch.tools;

import static mod.SketchwareUtil.dpToPx;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.sketchware.remod.R;

import mod.SketchwareUtil;
import mod.hey.studios.util.CompileLogHelper;
import mod.hey.studios.util.Helper;
import mod.jbk.diagnostic.CompileErrorSaver;

public class CompileLogActivity extends BaseActivity {

    private TextView tv_compile_log;
    private HorizontalScrollView err_hScroll;
    private ScrollView err_vScroll;
    private CompileErrorSaver compileErrorSaver;

    private SharedPreferences logViewerPreferences;
    private static final String PREFERENCE_WRAPPED_TEXT = "wrapped_text";
    private static final String PREFERENCE_USE_MONOSPACED_FONT = "use_monospaced_font";
    private static final String PREFERENCE_FONT_SIZE = "font_size";

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compile_log);

        logViewerPreferences = getPreferences(Context.MODE_PRIVATE);

        View rootLayout = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView menu = findViewById(R.id.ig_toolbar_load_file);
        tv_compile_log = findViewById(R.id.tv_compile_log);
        err_hScroll = rootLayout.findViewWithTag("err_hScroll");
        err_vScroll = rootLayout.findViewWithTag("err_vScroll");

        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back);

        if (getIntent().getBooleanExtra("showingLastError", false)) {
            title.setText("Last compile log");
        } else {
            title.setText("Compile log");
        }

        String sc_id = getIntent().getStringExtra("sc_id");
        if (sc_id == null) {
            finish();
            return;
        }

        compileErrorSaver = new CompileErrorSaver(sc_id);

        if (compileErrorSaver.logFileExists()) {
            ImageView delete = new ImageView(this);
            delete.setLayoutParams(new LinearLayout.LayoutParams(
                    dpToPx(40),
                    dpToPx(40)));
            delete.setPadding(
                    dpToPx(9),
                    dpToPx(9),
                    dpToPx(9),
                    dpToPx(9)
            );
            delete.setImageResource(R.drawable.ic_delete_white_24dp);
            delete.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            ((ViewGroup) menu.getParent()).addView(delete, ((ViewGroup) menu.getParent()).indexOfChild(menu));
            Helper.applyRippleToToolbarView(delete);
            delete.setOnClickListener(v -> {
                if (compileErrorSaver.logFileExists()) {
                    compileErrorSaver.deleteSavedLogs();
                    getIntent().removeExtra("error");
                    SketchwareUtil.toast("Compile logs have been cleared.");
                } else {
                    SketchwareUtil.toast("No compile logs found.");
                }

                setErrorText();
            });
        }

        menu.setImageResource(R.drawable.ic_more_vert_white_24dp);
        menu.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(menu);

        final String wrapTextLabel = "Wrap text";
        final String monospacedFontLabel = "Monospaced font";
        final String fontSizeLabel = "Font size";

        PopupMenu options = new PopupMenu(this, menu);
        options.getMenu().add(wrapTextLabel).setCheckable(true).setChecked(getWrappedTextPreference());
        options.getMenu().add(monospacedFontLabel).setCheckable(true).setChecked(getMonospacedFontPreference());
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

        applyLogViewerPreferences();

        setErrorText();
    }

    private void setErrorText() {
        String error = getIntent().getStringExtra("error");
        if (error == null) {
            error = compileErrorSaver.getLogsFromFile();
        }

        tv_compile_log.setText(CompileLogHelper.colorErrsAndWarnings(error));
        tv_compile_log.setTextIsSelectable(true);
    }

    private void applyLogViewerPreferences() {
        toggleWrapText(getWrappedTextPreference());
        toggleMonospacedText(getMonospacedFontPreference());
        tv_compile_log.setTextSize(getFontSizePreference());
    }

    private boolean getWrappedTextPreference() {
        return logViewerPreferences.getBoolean(PREFERENCE_WRAPPED_TEXT, false);
    }

    private boolean getMonospacedFontPreference() {
        return logViewerPreferences.getBoolean(PREFERENCE_USE_MONOSPACED_FONT, true);
    }

    private int getFontSizePreference() {
        return logViewerPreferences.getInt(PREFERENCE_FONT_SIZE, 11);
    }

    private void toggleWrapText(boolean isChecked) {
        logViewerPreferences.edit().putBoolean(PREFERENCE_WRAPPED_TEXT, isChecked).apply();

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
        logViewerPreferences.edit().putBoolean(PREFERENCE_USE_MONOSPACED_FONT, isChecked).apply();

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
        picker.setValue(getFontSizePreference());

        LinearLayout layout = new LinearLayout(this);
        layout.addView(picker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(this)
                .setTitle("Select font size")
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    logViewerPreferences.edit().putInt(PREFERENCE_FONT_SIZE, picker.getValue()).apply();

                    tv_compile_log.setTextSize((float) picker.getValue());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
