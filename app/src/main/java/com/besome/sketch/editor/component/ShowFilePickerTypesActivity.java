package com.besome.sketch.editor.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import a.a.a.xB;

public class ShowFilePickerTypesActivity extends BaseDialogActivity implements View.OnClickListener {
    public RadioGroup t;

    @Override
    public void onClick(View view) {
        String str;
        int id2 = view.getId();
        if (id2 == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id2 != R.id.common_dialog_ok_button) {
        } else {
            if (this.t.getCheckedRadioButtonId() == R.id.radio_all) {
                str = "*/*";
            } else if (this.t.getCheckedRadioButtonId() == R.id.radio_image) {
                str = "image/*";
            } else {
                str = this.t.getCheckedRadioButtonId() == R.id.radio_audio ? "audio/*" : "text/*";
            }
            Intent intent = new Intent();
            intent.putExtra("mime_type", str);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, R.string.component_file_picker_title_select_mime_type));
        d(xB.b().a(getApplicationContext(), R.string.common_word_select));
        b(xB.b().a(getApplicationContext(), R.string.common_word_cancel));
        setContentView(R.layout.show_file_picker_types);
        this.t = (RadioGroup) findViewById(R.id.radio_group);
        ((RadioButton) findViewById(R.id.radio_all)).setText(xB.b().a(this, R.string.component_file_picker_title_select_mime_type_all_files));
        ((RadioButton) findViewById(R.id.radio_image)).setText(xB.b().a(this, R.string.component_file_picker_title_select_mime_type_image_files));
        ((RadioButton) findViewById(R.id.radio_audio)).setText(xB.b().a(this, R.string.component_file_picker_title_select_mime_type_audio_files));
        ((RadioButton) findViewById(R.id.radio_text)).setText(xB.b().a(this, R.string.component_file_picker_title_select_mime_type_text_files));
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ShowFilePickerTypesActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
