package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.besome.sketch.MainDrawer;
import com.besome.sketch.acc.LoginActivity;
import com.besome.sketch.bill.SubscribeActivity;
import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.language.LanguageActivity;
import com.sketchware.remod.R;
// import mod.hey.studios.activity.about.AboutModActivity;
import mod.hilal.saif.activities.tools.Tools;
import org.apache.http.HttpStatus;

public class Gn implements View.OnClickListener {

    public final MainDrawer.a a;
    public final MainDrawer.a.b b;

    public Gn(MainDrawer.a.b bVar, MainDrawer.a aVar) {
        this.b = bVar;
        this.a = aVar;
    }

    public void onClick(View view) {
        /*
        if (!mB.a()) {
            MainDrawer.a.b bVar = this.b;
            MainDrawer.a.this.c = bVar.j() - 1;
            MainDrawer.a aVar = MainDrawer.a.this;
            aVar.c(aVar.c);
            Activity activity = (Activity) MainDrawer.this.getContext();
            if (MainDrawer.a.this.c == MainDrawer.b.eMenu_orders.ordinal()) {
                if (MainDrawer.this.g.h()) {
                    Intent intent = new Intent(MainDrawer.this.getContext(), AboutModActivity.class);
                    intent.setFlags(536870912);
                    activity.startActivity(intent);
                } else if (GB.h(MainDrawer.this.f2163a)) {
                    Intent intent2 = new Intent(MainDrawer.this.f2163a, SubscribeActivity.class);
                    intent2.setFlags(536870912);
                    intent2.putExtra("is_ads_use", false);
                    activity.startActivityForResult(intent2, HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED);
                } else {
                    bB.a(MainDrawer.this.f2163a, xB.b().a(MainDrawer.this.getContext(), R.string.common_message_check_network), 0).show();
                }
            } else if (MainDrawer.a.this.c == MainDrawer.b.eMenu_docs.ordinal()) {
                MainDrawer.this.h();
            } else if (MainDrawer.a.this.c == MainDrawer.b.eMenu_system_settings.ordinal()) {
                Intent intent3 = new Intent(activity, SystemSettingActivity.class);
                intent3.setFlags(536870912);
                activity.startActivityForResult(intent3, 107);
            } else if (MainDrawer.a.this.c == MainDrawer.b.eMenu_program_info.ordinal()) {
                Intent intent4 = new Intent(activity, ProgramInfoActivity.class);
                intent4.setFlags(536870912);
                activity.startActivityForResult(intent4, 105);
            } else if (MainDrawer.a.this.c == MainDrawer.b.eMenu_language_settings.ordinal()) {
                Intent intent5 = new Intent(activity, LanguageActivity.class);
                intent5.setFlags(536870912);
                activity.startActivity(intent5);
            } else if (MainDrawer.a.this.c != MainDrawer.b.eMenu_export_urls.ordinal()) {
            } else {
                if (MainDrawer.this.f.a()) {
                    Intent intent6 = new Intent(MainDrawer.this.getContext(), Tools.class);
                    intent6.setFlags(536870912);
                    activity.startActivity(intent6);
                    return;
                }
                Intent intent7 = new Intent(MainDrawer.this.getContext(), LoginActivity.class);
                intent7.setFlags(536870912);
                activity.startActivityForResult(intent7, 111);*/
            }
        }
    //}
//}