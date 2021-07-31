package com.besome.sketch.editor.manage.view;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.xB;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class AddCustomViewActivity extends BaseDialogActivity implements OnClickListener {
   public EditText t;
   public TextView u;
   public ArrayList v;
   public YB w;
   public String x;

   public boolean a(YB var1) {
      return var1.b();
   }

   public final ArrayList f(String var1) {
      return rq.b(var1);
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(var1 == 277) {
         if(var2 == -1) {
            this.x = ((ProjectFileBean)var3.getParcelableExtra("preset_data")).presetName;
         }

      }
   }

   public void onClick(View var1) {
      int var2 = var1.getId();
      if(var2 != 2131230909) {
         if(var2 != 2131230912) {
            if(var2 == 2131230914) {
               if(this.a(this.w)) {
                  Intent var5 = new Intent();
                  var5.putExtra("project_file", new ProjectFileBean(1, this.t.getText().toString()));
                  String var7 = this.x;
                  if(var7 != null) {
                     var5.putExtra("preset_views", this.f(var7));
                  }

                  this.setResult(-1, var5);
                  bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131625276), 0).show();
                  this.finish();
               }
            }
         } else {
            Intent var3 = new Intent(this.getApplicationContext(), PresetSettingActivity.class);
            var3.putExtra("request_code", 277);
            this.startActivityForResult(var3, 277);
         }
      } else {
         this.finish();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2131427562);
      this.e(xB.b().a(this.getApplicationContext(), 2131625298));
      this.f(2131165985);
      this.d(xB.b().a(this.getApplicationContext(), 2131624970));
      this.b(xB.b().a(this.getApplicationContext(), 2131624974));
      this.v = this.getIntent().getStringArrayListExtra("screen_names");
      this.t = (EditText)this.findViewById(2131230990);
      ((TextInputLayout)this.findViewById(2131231816)).setHint(xB.b().a(this.getApplicationContext(), 2131625293));
      this.u = (TextView)this.findViewById(2131231944);
      this.u.setText(xB.b().a(this.getApplicationContext(), 2131625292));
      this.w = new YB(this, (TextInputLayout)this.findViewById(2131231816), uq.b, this.v);
      this.t.setPrivateImeOptions("defaultInputmode=english;");
      this.t.setImeOptions(5);
      this.t.setLines(1);
      super.r.setOnClickListener(this);
      super.s.setOnClickListener(this);
   }
}
