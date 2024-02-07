package com.besome.sketch.editor.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.besome.sketch.lib.base.BaseDialogActivity;
import com.sketchware.remod.R;

import a.a.a.xB;

public class ShowFilePickerTypesActivity extends BaseDialogActivity implements View.OnClickListener {
    private RadioGroup radioGroup;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            String mimeType;
            if (radioGroup.getCheckedRadioButtonId() == R.id.radio_all) {
                mimeType = "*/*";
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_image) {
                mimeType = "image/*";
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radio_audio) {
                mimeType = "audio/*";
            } else {
                mimeType = "text/*";
            }
            Intent intent = new Intent();
            intent.putExtra("mime_type", mimeType);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        e(getTranslatedString(R.string.component_file_picker_title_select_mime_type));
        d(getTranslatedString(R.string.common_word_select));
        b(getTranslatedString(R.string.common_word_cancel));
        setContentView(R.layout.show_file_picker_types);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ((RadioButton) findViewById(R.id.radio_all)).setText(getTranslatedString(R.string.component_file_picker_title_select_mime_type_all_files));
        ((RadioButton) findViewById(R.id.radio_image)).setText(getTranslatedString(R.string.component_file_picker_title_select_mime_type_image_files));
        ((RadioButton) findViewById(R.id.radio_audio)).setText(getTranslatedString(R.string.component_file_picker_title_select_mime_type_audio_files));
        ((RadioButton) findViewById(R.id.radio_text)).setText(getTranslatedString(R.string.component_file_picker_title_select_mime_type_text_files));
        r.setOnClickListener(this);
        s.setOnClickListener(this);
    }
}
