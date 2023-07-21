package com.besome.sketch.editor.component;

import a.a.a.xB;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.gms.analytics.HitBuilders;

public class ShowFilePickerTypesActivity extends BaseDialogActivity implements View.OnClickListener {
    public RadioGroup t;

    @Override
    public void onClick(View view) {
        String str;
        int id2 = view.getId();
        if (id2 == 2131230909) {
            finish();
        } else if (id2 != 2131230914) {
        } else {
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

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ShowFilePickerTypesActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
