package com.besome.sketch.common;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.besome.sketch.beans.SrcCodeBean;
import com.besome.sketch.ctrls.CommonSpinnerItem;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.bB;
import a.a.a.jC;
import a.a.a.xB;
import a.a.a.yq;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.hey.studios.util.Helper;

public class SrcViewerActivity extends AppCompatActivity {

    private String sc_id;
    private Spinner filesListSpinner;
    private ImageView changeFontSize;
    private LinearLayout progressContainer;
    private ArrayList<SrcCodeBean> srcCodeBean;
    private String currentPageFileName;
    private int sourceCodeFontSize = 12;
    private CodeEditor codeViewer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.src_viewer);

        codeViewer = new CodeEditor(this);
        codeViewer.setTypefaceText(Typeface.MONOSPACE);
        codeViewer.setEditable(false);
        codeViewer.setColorScheme(new EditorColorScheme());
        codeViewer.setTextSize(sourceCodeFontSize);
        codeViewer.setEditorLanguage(new JavaLanguage());
        codeViewer.setPinLineNumber(true);

        LinearLayout contentLayout = (LinearLayout) (findViewById(R.id.pager_soruce_code).getParent());
        contentLayout.removeAllViews();
        contentLayout.addView(codeViewer);


        /*
         * currentPageFileName corresponds to the filename of which layout or activity the user is currently in.
         */
        currentPageFileName = getIntent().hasExtra("current") ? getIntent().getStringExtra("current") : "";
        sc_id = (savedInstanceState != null) ? savedInstanceState.getString("sc_id") : getIntent().getStringExtra("sc_id");

        changeFontSize = findViewById(R.id.imgv_src_size);
        changeFontSize.setOnClickListener((v -> showChangeFontSizeDialog()));

        filesListSpinner = findViewById(R.id.spn_src_list);
        filesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeViewer.setText(srcCodeBean.get(position).source);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        LinearLayout layoutSrcList = findViewById(R.id.layout_srclist);
        for (int i = 0; i < layoutSrcList.getChildCount(); i++) {
            View child = layoutSrcList.getChildAt(i);

            if (child instanceof LinearLayout) {
                // Found the LinearLayout containing the ProgressBar and TextView!
                progressContainer = (LinearLayout) child;

                filesListSpinner.setVisibility(View.GONE);
                changeFontSize.setVisibility(View.GONE);
                progressContainer.setVisibility(View.VISIBLE);
            }
        }

        new Thread(() -> {
            srcCodeBean = new yq(getBaseContext(), sc_id).a(jC.b(sc_id), jC.a(sc_id), jC.c(sc_id));

            try {
                runOnUiThread(() -> {
                    if (srcCodeBean == null) {
                        bB.b(getApplicationContext(), Helper.getResString(R.string.common_error_unknown), bB.TOAST_NORMAL).show();
                    } else {
                        filesListSpinner.setAdapter(new FilesListSpinnerAdapter());
                        for (SrcCodeBean src : srcCodeBean) {
                            if (src.srcFileName.equals(currentPageFileName)) {
                                filesListSpinner.setSelection(srcCodeBean.indexOf(src));
                                break;
                            }
                        }
                        codeViewer.setText(srcCodeBean.get(filesListSpinner.getSelectedItemPosition()).source);

                        progressContainer.setVisibility(View.GONE);
                        filesListSpinner.setVisibility(View.VISIBLE);
                        changeFontSize.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception ignored) {
                // May occur if the activity is killed
            }
        }).start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private void showChangeFontSizeDialog() {
        NumberPicker picker = new NumberPicker(this);
        picker.setMinValue(8);
        picker.setMaxValue(30);
        picker.setWrapSelectorWheel(false);
        picker.setValue(sourceCodeFontSize);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(picker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(this)
                .setTitle("Select font size")
                .setIcon(R.drawable.ic_font_48dp)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    sourceCodeFontSize = picker.getValue();
                    codeViewer.setTextSize(sourceCodeFontSize);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public class FilesListSpinnerAdapter extends BaseAdapter {

        private View getCustomSpinnerView(int position, View view, boolean isCurrentlyViewingFile) {
            CommonSpinnerItem spinnerItem = (view != null) ? (CommonSpinnerItem) view :
                    new CommonSpinnerItem(SrcViewerActivity.this);
            spinnerItem.a((srcCodeBean.get(position)).srcFileName, isCurrentlyViewingFile);
            return spinnerItem;
        }

        @Override
        public int getCount() {
            return srcCodeBean.size();
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            boolean isCheckmarkVisible = (filesListSpinner.getSelectedItemPosition() == position);
            return getCustomSpinnerView(position, convertView, isCheckmarkVisible);
        }

        @Override
        public Object getItem(int position) {
            return srcCodeBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomSpinnerView(position, convertView, false);
        }
    }
}
