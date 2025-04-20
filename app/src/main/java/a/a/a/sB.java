package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

import mod.hey.studios.util.Helper;
import mod.jbk.build.BuiltInLibraries;
import pro.sketchware.R;
import pro.sketchware.activities.main.activities.MainActivity;

public class sB {
    public static void a(Context context, boolean resetOnlySettings) {
        new DB(context, "P2").a();
        new DB(context, "P3").a();
        new DB(context, "P1").a();
        new DB(context, "P12").a();
        new DB(context, "P99").a();
        new DB(context, "P25").a();
        new DB(context, "P26").a();
        new DB(context, "P16").a("P16I0");

        oB oBVar = new oB();
        oBVar.b(wq.r());
        oBVar.b(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath());

        if (!resetOnlySettings) {
            oBVar.b(wq.q());
            oBVar.b(wq.s());

            for (HashMap<String, Object> project : lC.a()) {
                String sc_id = yB.c(project, "sc_id");
                new DB(context, "D01_" + sc_id).a();
                new DB(context, "D02_" + sc_id).a();
                new DB(context, "D03_" + sc_id).a();
                new DB(context, "D04_" + sc_id).a();
                new DB(context, "D05_" + sc_id).a();
            }
            new DB(context, "P15").a();
        }

        bB.a(context, Helper.getResString(R.string.program_information_reset_system_complete_initialize), bB.TOAST_NORMAL).show();
        lC.a(context, true);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Activity activity = (Activity) context;
        activity.startActivity(intent);
        activity.finishAffinity();
    }
}
