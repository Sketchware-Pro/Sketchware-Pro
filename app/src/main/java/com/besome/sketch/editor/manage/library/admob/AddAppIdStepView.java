package com.besome.sketch.editor.manage.library.admob;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Uu;
import a.a.a.bB;
import a.a.a.gB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryAdmobAppIdBinding;
import pro.sketchware.databinding.ManageLibrarySettingAdmobAppIdAddBinding;

public class AddAppIdStepView extends LinearLayout implements Uu, View.OnClickListener {
    private ManageLibraryAdmobAppIdBinding binding;
    private String appId;

    public AddAppIdStepView(Context context) {
        super(context);
        initialize(context);
    }

    @Override
    public String getDocUrl() {
        return "";
    }

    @Override
    public boolean isValid() {
        if (!isEmpty(Helper.getText(binding.textId))) {
            return true;
        } else {
            bB.a(getContext(), Helper.getResString(R.string.design_library_admob_setting_message_add_app_id), 1).show();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_edit) {
            showAddAppIdDialog();
        }
    }

    @Override
    public void setData(ProjectLibraryBean projectLibraryBean) {
        setAppId(projectLibraryBean.appId);

    }

    private void initialize(Context context) {
        binding = ManageLibraryAdmobAppIdBinding.inflate(LayoutInflater.from(context), this, true);
        binding.getRoot();
        gB.b(this, 600, 200, null);
        binding.textTitle.setText(Helper.getResString(R.string.design_library_admob_title_app_id));
        binding.textEdit.setOnClickListener(this);
    }

    private void setAppId(String appId) {
        this.appId = appId;
        binding.textId.setText(appId);
    }

    @Override
    public void a(ProjectLibraryBean projectLibraryBean) {
        projectLibraryBean.appId = appId;
    }

    private void showAddAppIdDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(xB.b().a(getContext(), R.string.design_library_admob_dialog_set_app_id));
        dialog.setIcon(R.drawable.ic_mtrl_add);
        ManageLibrarySettingAdmobAppIdAddBinding addBinding = ManageLibrarySettingAdmobAppIdAddBinding.inflate(LayoutInflater.from(getContext()));
        EditText edAppId = addBinding.edAppId;
        edAppId.setText(appId);
        edAppId.setPrivateImeOptions("defaultInputmode=english;");
        dialog.setView(addBinding.getRoot());
        dialog.setPositiveButton(xB.b().a(getContext(), R.string.common_word_add), (v, which) -> {
            String id = Helper.getText(edAppId);
            if (!isEmpty(id)) {
                setAppId(id);
                v.dismiss();
            } else {
                edAppId.requestFocus();
            }
        });
        dialog.setNegativeButton(xB.b().a(getContext(), R.string.common_word_cancel), null);
        dialog.show();
    }
}
