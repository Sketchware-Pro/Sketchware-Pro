package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.besome.sketch.InitActivity;
import com.google.ads.consent.ConsentInformation;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class sB {
    public static void a(Context context, boolean z) {
        new DB(context, "P2").a();
        new DB(context, "P3").a();
        new DB(context, "P1").a();
        new DB(context, "P12").a();
        new DB(context, "P99").a();
        new DB(context, "P25").a();
        new DB(context, "P26").a();
        new DB(context, "P16").a("P16I0");
        ConsentInformation a2 = ConsentInformation.a(context);
        if (a2 != null) {
            a2.i();
        }
        String str = File.separator;
        oB oBVar = new oB();
        oBVar.b(wq.r());
        oBVar.b(context.getFilesDir() + File.separator + "libs");
        if (!z) {
            oBVar.b(wq.q());
            oBVar.b(wq.s());
            Iterator<HashMap<String, Object>> it = lC.a().iterator();
            while (it.hasNext()) {
                String c = yB.c(it.next(), "sc_id");
                new DB(context, "D01_" + c).a();
                new DB(context, "D02_" + c).a();
                new DB(context, "D03_" + c).a();
                new DB(context, "D04_" + c).a();
                new DB(context, "D05_" + c).a();
            }
            new DB(context, "P15").a();
        }
        bB.a(context, xB.b().a(context, 2131625762), 0).show();
        lC.a(context, true);
        Intent intent = new Intent(context, InitActivity.class);
        intent.setFlags(335544320);
        Activity activity = (Activity) context;
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
