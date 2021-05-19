package com.besome.sketch.editor.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.besome.sketch.lib.base.BaseDialogActivity;

import a.a.a.xB;

public class ShowFilePickerTypesActivity extends BaseDialogActivity implements View.OnClickListener {
    public RadioGroup t;

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.component.ShowFilePickerTypesActivity */
    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("ResourceType")
    public void onClick(View view) {
        String str;
        int id = view.getId();
        if (id == 2131230909) {
            finish();
        } else if (id == 2131230914) {
            if (this.t.getCheckedRadioButtonId() == 2131231633) {
                str = "*/*";
            } else if (this.t.getCheckedRadioButtonId() == 2131231641) {
                str = "image/*";
            } else {
                str = this.t.getCheckedRadioButtonId() == 2131231635 ? "audio/*" : "text/*";
            }
            Intent intent = new Intent();
            intent.putExtra("mime_type", str);
            setResult(-1, intent);
            finish();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625118));
        d(xB.b().a(getApplicationContext(), 2131625035));
        b(xB.b().a(getApplicationContext(), 2131624974));
        setContentView(2131427726);
        this.t = (RadioGroup) findViewById(2131231639);
        ((RadioButton) findViewById(2131231633)).setText(xB.b().a(this, 2131625119));
        ((RadioButton) findViewById(2131231641)).setText(xB.b().a(this, 2131625121));
        ((RadioButton) findViewById(2131231635)).setText(xB.b().a(this, 2131625120));
        ((RadioButton) findViewById(2131231648)).setText(xB.b().a(this, 2131625122));
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
    }
}
