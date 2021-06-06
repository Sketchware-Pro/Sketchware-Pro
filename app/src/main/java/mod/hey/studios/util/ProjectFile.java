package mod.hey.studios.util;

import android.graphics.Color;

import a.a.a.lC;
import a.a.a.yB;

public class ProjectFile {

    public static int getColor(String str, String str2) {
        return yB.a(lC.b(str), str2, getDefaultColor(str2));
    }

    private static int getDefaultColor(String str) {
        int var1;

        switch (str) {
            case "color_primary_dark":
                var1 = Color.parseColor("#ff0084c2");
                break;

            case "color_control_highlight":
                var1 = Color.parseColor("#20008dcd");
                break;

            case "color_control_normal":
                var1 = Color.parseColor("#ff57beee");
                break;

            default:
                var1 = Color.parseColor("#ff008dcd");
                break;
        }

        return var1;
    }
}
