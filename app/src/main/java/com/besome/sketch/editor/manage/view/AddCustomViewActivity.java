package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import mod.hey.studios.util.Helper;

public class AddCustomViewActivity extends BaseDialogActivity implements View.OnClickListener {

    public static final int REQ_CD_PRESET_ACTIVITY = 277;
    private EditText customViewName;
    private YB viewNameValidator;
    private String presetName;

    private ArrayList<ViewBean> getPresetData(String presetName) {
        return rq.b(presetName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CD_PRESET_ACTIVITY && resultCode == RESULT_OK) {
            presetName = ((ProjectFileBean) data.getParcelableExtra("preset_data")).presetName;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_dialog_ok_button) {
            if (!viewNameValidator.b()) {
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("project_file", new ProjectFileBean(ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW, customViewName.getText().toString()));
            if (presetName != null) {
                intent.putExtra("preset_views", getPresetData(presetName));
            }

            setResult(RESULT_OK, intent);
            bB.a(getApplicationContext(), Helper.getResString(R.string.design_manager_message_add_complete), 0).show();
            finish();
        } else if (id == R.id.common_dialog_default_button) {
            Intent intent = new Intent(getApplicationContext(), PresetSettingActivity.class);
            intent.putExtra("request_code", REQ_CD_PRESET_ACTIVITY);
            startActivityForResult(intent, REQ_CD_PRESET_ACTIVITY);
        } else if (id == R.id.common_dialog_cancel_button) {
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_screen_custom_view_add);
        e(Helper.getResString(R.string.design_manager_view_title_add_custom_view));
        f(R.drawable.new_window_96);
        d(Helper.getResString(R.string.common_word_add));
        b(Helper.getResString(R.string.common_word_cancel));

        ArrayList<String> alreadyInUseNames = getIntent().getStringArrayListExtra("screen_names");
        customViewName = findViewById(R.id.ed_input);
        ((TextInputLayout) findViewById(R.id.ti_input)).setHint(Helper.getResString(R.string.design_manager_view_hint_enter_view_name));
        TextView description = findViewById(R.id.tv_desc);
        description.setText(Helper.getResString(R.string.design_manager_view_description_guide_use_custom_view));
        viewNameValidator = new YB(this, findViewById(R.id.ti_input), uq.b, alreadyInUseNames);
        customViewName.setPrivateImeOptions("defaultInputmode=english;");
        customViewName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        customViewName.setLines(1);
        super.r.setOnClickListener(this);
        super.s.setOnClickListener(this);
    }
}
