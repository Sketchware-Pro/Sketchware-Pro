package com.besome.sketch.common;

import android.annotation.SuppressLint;
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
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.bB;
import a.a.a.jC;
import a.a.a.xB;
import a.a.a.yq;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

@SuppressLint("ResourceType")
public class SrcViewerActivity extends AppCompatActivity {
    public String sc_id;
    public Spinner filesListSpinner;
    public ArrayList<SrcCodeBean> srcCodeBean;
    public String currentPageFileName;
    public int sourceCodeFontSize = 12;
    public CodeEditor codeViewer;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427737);  //src_viewer.xml

        codeViewer = new CodeEditor(this);
        codeViewer.setTypefaceText(Typeface.MONOSPACE);
        codeViewer.setEditable(false);
        codeViewer.setColorScheme(new EditorColorScheme());
        codeViewer.setTextSize(sourceCodeFontSize);
        codeViewer.setEditorLanguage(new JavaLanguage());

        LinearLayout contentLayout = (LinearLayout) (findViewById(2131231586).getParent());
        contentLayout.removeAllViews();
        contentLayout.addView(codeViewer);


        /*
         * currentPageFileName corresponds to the filename of which layout or activity the user is currently in.
         */
        currentPageFileName = getIntent().hasExtra("current") ? getIntent().getStringExtra("current") : "";
        sc_id = (bundle != null) ? bundle.getString("sc_id") : getIntent().getStringExtra("sc_id");

        ImageView changeFontSize = findViewById(2131231209);
        changeFontSize.setOnClickListener((v -> showChangeFontSizeDialog()));

        filesListSpinner = findViewById(2131231754);
        filesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeViewer.setText(srcCodeBean.get(position).source);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        codeViewer.setText("Generating source code. Please Wait!");


        new Thread(() -> {
            srcCodeBean = (new yq(getBaseContext(), sc_id)).a(jC.b(sc_id), jC.a(sc_id), jC.c(sc_id));

            try {
                runOnUiThread(() -> {

                    if (srcCodeBean == null) {
                        bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624916), 0).show();
                    } else {
                        filesListSpinner.setAdapter(new filesListSpinnerAdapter());
                        for (SrcCodeBean src : srcCodeBean) {
                            if (src.srcFileName.equals(currentPageFileName)) {
                                filesListSpinner.setSelection(srcCodeBean.indexOf(src));
                                return;
                            }
                        }
                        codeViewer.setText(srcCodeBean.get(filesListSpinner.getSelectedItemPosition()).source);
                    }
                });
            } catch (Exception ignored) {
                //May occur if the activity is killed
            }
        }).start();
    }


    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.sc_id);
        super.onSaveInstanceState(bundle);
    }

    public void showChangeFontSizeDialog() {
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
                .setTitle("Change Font Size")
                .setIcon(Resources.drawable.ic_font_48dp)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    sourceCodeFontSize = picker.getValue();
                    codeViewer.setTextSize(sourceCodeFontSize);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public class filesListSpinnerAdapter extends BaseAdapter {

        public filesListSpinnerAdapter() {
        }

        private View getCustomSpinnerView(int position, View _view, boolean isCheckmarkVisible) {
            CommonSpinnerItem spinnerItem = (_view != null) ? (CommonSpinnerItem) _view :
                    new CommonSpinnerItem(SrcViewerActivity.this);
            spinnerItem.a((srcCodeBean.get(position)).srcFileName, isCheckmarkVisible);
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
        public View getView(int position, View convertView, ViewGroup var3) {
            return getCustomSpinnerView(position, convertView, false);
        }

    }

}
