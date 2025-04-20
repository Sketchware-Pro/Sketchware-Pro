package com.besome.sketch.common;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.besome.sketch.beans.SrcCodeBean;
import com.besome.sketch.ctrls.CommonSpinnerItem;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.ProjectBuilder;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.yq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.SrcViewerBinding;
import pro.sketchware.utility.EditorUtils;

public class SrcViewerActivity extends BaseAppCompatActivity {

    private SrcViewerBinding binding;
    private String sc_id;
    private ArrayList<SrcCodeBean> sourceCodeBeans;

    private String currentFileName;
    private int editorFontSize = 12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SrcViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentFileName = getIntent().hasExtra("current") ? getIntent().getStringExtra("current") : "";
        sc_id = (savedInstanceState != null) ? savedInstanceState.getString("sc_id") : getIntent().getStringExtra("sc_id");

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), systemBars.top, v.getPaddingRight(), systemBars.bottom);
            return insets;
        });

        configureEditor();

        binding.changeFontSize.setOnClickListener((v -> showChangeFontSizeDialog()));

        binding.filesListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SrcCodeBean bean = sourceCodeBeans.get(position);
                binding.editor.setText(bean.source);
                currentFileName = bean.srcFileName;
                if (currentFileName.endsWith(".xml")) {
                    EditorUtils.loadXmlConfig(binding.editor);
                } else {
                    EditorUtils.loadJavaConfig(binding.editor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        k(); // show loading

        new Thread(() -> {
            var yq = new yq(getBaseContext(), sc_id);
            var fileManager = jC.b(sc_id);
            var dataManager = jC.a(sc_id);
            var libraryManager = jC.c(sc_id);
            yq.a(libraryManager, fileManager, dataManager, false);
            ProjectBuilder builder = new ProjectBuilder(this, yq);
            builder.buildBuiltInLibraryInformation();
            yq.generateDataBindingClasses = true;
            sourceCodeBeans = yq.a(fileManager, dataManager, builder.getBuiltInLibraryManager());

            try {
                runOnUiThread(() -> {
                    if (sourceCodeBeans == null) {
                        bB.b(getApplicationContext(), Helper.getResString(R.string.common_error_unknown), bB.TOAST_NORMAL).show();
                    } else {
                        binding.filesListSpinner.setAdapter(new FilesListSpinnerAdapter());
                        for (SrcCodeBean src : sourceCodeBeans) {
                            if (src.srcFileName.equals(currentFileName)) {
                                binding.filesListSpinner.setSelection(sourceCodeBeans.indexOf(src));
                                break;
                            }
                        }
                        binding.editor.setText(sourceCodeBeans.get(binding.filesListSpinner.getSelectedItemPosition()).source);
                        h(); // hide loading
                    }
                });
            } catch (Exception ignored) {
                // May occur if the activity is killed
            }
        }).start();
    }

    private void configureEditor() {
        binding.editor.setTypefaceText(EditorUtils.getTypeface(this));
        binding.editor.setEditable(false);
        binding.editor.setTextSize(editorFontSize);
        binding.editor.setPinLineNumber(true);

        if (currentFileName.endsWith(".xml")) {
            EditorUtils.loadXmlConfig(binding.editor);
        } else {
            EditorUtils.loadJavaConfig(binding.editor);
        }
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
        picker.setValue(editorFontSize);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(picker, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new MaterialAlertDialogBuilder(this)
                .setTitle("Select font size")
                .setIcon(R.drawable.ic_mtrl_formattext)
                .setView(layout)
                .setPositiveButton("Apply", (dialog, which) -> {
                    editorFontSize = picker.getValue();
                    binding.editor.setTextSize(editorFontSize);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    public class FilesListSpinnerAdapter extends BaseAdapter {

        private View getCustomSpinnerView(int position, View view, boolean isCurrentlyViewingFile) {
            CommonSpinnerItem spinnerItem = (view != null) ? (CommonSpinnerItem) view :
                    new CommonSpinnerItem(SrcViewerActivity.this);
            spinnerItem.a((sourceCodeBeans.get(position)).srcFileName, isCurrentlyViewingFile);
            return spinnerItem;
        }

        @Override
        public int getCount() {
            return sourceCodeBeans.size();
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            boolean isCheckmarkVisible = (binding.filesListSpinner.getSelectedItemPosition() == position);
            return getCustomSpinnerView(position, convertView, isCheckmarkVisible);
        }

        @Override
        public Object getItem(int position) {
            return sourceCodeBeans.get(position);
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