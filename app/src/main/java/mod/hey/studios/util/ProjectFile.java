package mod.hey.studios.util;

import android.graphics.Color;

import a.a.a.lC;
import a.a.a.yB;

public class ProjectFile {
    public static final String COLOR_ACCENT = "color_accent";
    public static final String COLOR_PRIMARY = "color_primary";
    public static final String COLOR_PRIMARY_DARK = "color_primary_dark";
    public static final String COLOR_CONTROL_HIGHLIGHT = "color_control_highlight";
    public static final String COLOR_CONTROL_NORMAL = "color_control_normal";

    public static int getColor(String sc_id, String color) {
        return yB.a(lC.b(sc_id), color, getDefaultColor(color));
        /*
        Old in-progress by Mike?
        HashMap<String, Object> hashMap = lC.b(sc_id);

        return 0;//yB.a(hashMap, color, getDefaultColor(color));
        */
    }

	/*
	 // comment by Jbk0
	 // the smali files say something completely different lol

	 package a.a.a;
	 import java.util.Map;

	 public class yB {
	 public static int a(Map<String, Object> paramMap, String paramString, int paramInt) {

	 return 0;

	 }
	 }
	*/

    public static int getDefaultColor(String color) {
        return switch (color) {
            case "color_primary_dark" -> Color.parseColor("#ff1976d2");
            case "color_control_highlight" -> Color.parseColor("#202196f3");
            default -> Color.parseColor("#ff2196f3");
        };
    }
}
