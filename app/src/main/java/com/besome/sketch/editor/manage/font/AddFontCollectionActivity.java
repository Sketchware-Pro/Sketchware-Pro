package com.besome.sketch.editor.manage.font;

import static mod.hey.studios.util.Helper.addBasicTextChangedListener;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;

import java.io.File;
import java.util.ArrayList;

import a.a.a.Np;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontAddBinding;
import pro.sketchware.lib.validator.FontNameValidator;

public class AddFontCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public int requestCode;
    private ArrayList<ProjectResourceBean> projectResourceBeanArrayList;
    private ProjectResourceBean projectResourceBean;
    private FontNameValidator fontValidator;
    private ManageFontAddBinding binding;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ManageFontAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        e(getTranslatedString(R.string.design_manager_font_title_edit_font_name));
        d(getTranslatedString(R.string.common_word_save));
        b(getTranslatedString(R.string.common_word_cancel));

        Intent intent = getIntent();
        projectResourceBeanArrayList = intent.getParcelableArrayListExtra("fonts");
        requestCode = intent.getIntExtra("request_code", -1);
        projectResourceBean = intent.getParcelableExtra("edit_target");

        binding.addToCollectionCheckbox.setVisibility(View.GONE);
        binding.buttonsHolder.setVisibility(View.GONE);
        binding.fontPreviewView.setVisibility(View.VISIBLE);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        fontValidator = new FontNameValidator(this, binding.tiInput, uq.b, getExistingFontNames());
        fontValidator = new FontNameValidator(this, binding.tiInput, uq.b, getExistingFontNames(), projectResourceBean.resName);
        binding.edInput.setText(projectResourceBean.resName);
        binding.fontPreviewTxt.setTypeface(Typeface.createFromFile(getFontFilePath(projectResourceBean)));
        addBasicTextChangedListener(binding.edInput, str -> {
            binding.clearInput.setVisibility(str.isEmpty() ? View.GONE : View.VISIBLE);
            binding.buttonsHolder.animate().translationY(str.isEmpty() ? 0 : 50);
        });
    }

    public final ArrayList<String> getExistingFontNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : projectResourceBeanArrayList) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            saveFont();
        }
    }

    public final void saveFont() {
        if (fontValidator.b()) {
            Np.g().a(projectResourceBean, Helper.getText(binding.edInput), true);
            bB.a(this, getTranslatedString(R.string.design_manager_message_edit_complete), 1).show();
            finish();
        }
    }

    public final String getFontFilePath(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "font" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }
}
