package a.a.a;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import pro.sketchware.SketchApplication;

public class wq {

    public static final String A = "sketchware" + File.separator + "localization" + File.separator + "strings_provided.xml";
    public static final String B = "sketchware" + File.separator + "signed_apk";
    public static final String C = "sketchware" + File.separator + "keystore";
    public static final String D = "sketchware" + File.separator + "keystore" + File.separator + "release_key.jks";
    public static final String a = ".sketchware" + File.separator + "libs";
    public static final String b = ".sketchware" + File.separator + "mysc";
    public static final String c = ".sketchware" + File.separator + "mysc" + File.separator + "list";
    public static final String d = ".sketchware" + File.separator + "data";
    public static final String e = ".sketchware" + File.separator + "bak";
    public static final String f = ".sketchware" + File.separator + "temp" + File.separator + "images";
    public static final String g = ".sketchware" + File.separator + "temp" + File.separator + "sounds";
    public static final String h = ".sketchware" + File.separator + "temp" + File.separator + "fonts";
    public static final String i = ".sketchware" + File.separator + "temp" + File.separator + "proj";
    public static final String j = ".sketchware" + File.separator + "temp" + File.separator + "data";
    public static final String l = ".sketchware" + File.separator + "resources";
    public static final String m = ".sketchware" + File.separator + "resources" + File.separator + "icons";
    public static final String n = ".sketchware" + File.separator + "resources" + File.separator + "images";
    public static final String o = ".sketchware" + File.separator + "resources" + File.separator + "sounds";
    public static final String p = ".sketchware" + File.separator + "resources" + File.separator + "fonts";
    public static final String x = ".sketchware" + File.separator + "collection";
    public static final String y = "sketchware" + File.separator + "localization";
    public static final String z = "sketchware" + File.separator + "localization" + File.separator + "strings.xml";

    public static final String EXTRA_SYSTEM_DATA = ".sketchware" + File.separator + "data" + File.separator + "system";
    public static final String CUSTOM_COMPONENT_FILE = EXTRA_SYSTEM_DATA + File.separator + "component.json";
    public static final String EXTRA_DATA_EXPORT = EXTRA_SYSTEM_DATA + File.separator + "export";

    public static String getAbsolutePathOf(String path) {
        return new File(Environment.getExternalStorageDirectory(), path).getAbsolutePath();
    }

    public static String a() {
        return getAbsolutePathOf(x);
    }

    public static String a(String sc_id) {
        return getAbsolutePathOf(e + File.separator + sc_id);
    }

    public static void a(Context context, String preferenceName) {
        DB dataP17 = new DB(context, "P17_" + preferenceName);
        DB dataP18 = new DB(context, "P18_" + preferenceName);
        DB dataP13 = new DB(context, "P13_" + preferenceName);
        DB dataP14 = new DB(context, "P14_" + preferenceName);
        dataP17.a();
        dataP18.a();
        dataP13.a();
        dataP14.a();
        DB dataD03 = new DB(context, "D03_" + preferenceName);
        DB dataD04 = new DB(context, "D04_" + preferenceName);
        DB dataD01 = new DB(context, "D01_" + preferenceName);
        DB dataD02 = new DB(context, "D02_" + preferenceName);
        dataD03.a();
        dataD04.a();
        dataD01.a();
        dataD02.a();
    }

    public static String b(int type) {
        return switch (type) {
            case 0 -> "linear";
            case 1 -> "relativelayout";
            case 2 -> "hscroll";
            case 3 -> "button";
            case 4 -> "textview";
            case 5 -> "edittext";
            case 6 -> "imageview";
            case 7 -> "webview";
            case 8 -> "progressbar";
            case 9 -> "listview";
            case 10 -> "spinner";
            case 11 -> "checkbox";
            case 12 -> "vscroll";
            case 13 -> "switch";
            case 14 -> "seekbar";
            case 15 -> "calendarview";
            case 16 -> "fab";
            case 17 -> "adview";
            case 18 -> "mapview";
            case 19 -> "radiobutton";
            case 20 -> "ratingbar";
            case 21 -> "videoview";
            case 22 -> "searchview";
            case 23 -> "autocomplete";
            case 24 -> "multiautocomplete";
            case 25 -> "gridview";
            case 26 -> "analogclock";
            case 27 -> "datepicker";
            case 28 -> "timepicker";
            case 29 -> "digitalclock";
            case 30 -> "tablayout";
            case 31 -> "viewpager";
            case 32 -> "bottomnavigation";
            case 33 -> "badgeview";
            case 34 -> "patternlockview";
            case 35 -> "wavesidebar";
            case 36 -> "cardview";
            case 37 -> "collapsingtoolbar";
            case 38 -> "textinputlayout";
            case 39 -> "swiperefreshlayout";
            case 40 -> "radiogroup";
            case 41 -> "materialbutton";
            case 42 -> "signinbutton";
            case 43 -> "circleimageview";
            case 44 -> "lottie";
            case 45 -> "youtube";
            case 46 -> "otpview";
            case 47 -> "codeview";
            case 48 -> "recyclerview";
            default -> "widget";
        };
    }

    public static String b(String sc_id) {
        return getAbsolutePathOf(d + File.separator + sc_id);
    }

    public static String c() {
        return getAbsolutePathOf(".sketchware" + File.separator + "download");
    }

    public static String c(String sc_id) {
        return getAbsolutePathOf(c + File.separator + sc_id);
    }

    public static String d() {
        return getAbsolutePathOf(p);
    }

    public static String d(String sc_id) {
        return getAbsolutePathOf(b + File.separator + sc_id);
    }

    public static String e() {
        return getAbsolutePathOf(m);
    }

    public static String e(String sc_id) {
        return "resource" + File.separator + sc_id + File.separator + "res.zip";
    }

    public static String getExtractedIconPackStoreLocation() {
        return new File(SketchApplication.getContext().getFilesDir(), "iconpack").getAbsolutePath();
    }

    public static String g() {
        return getAbsolutePathOf(n);
    }

    @Deprecated
    public static String i() {
        return getAbsolutePathOf(C);
    }

    public static String j() {
        return getAbsolutePathOf(D);
    }

    public static String k() {
        return getAbsolutePathOf(y);
    }

    public static String l() {
        return getAbsolutePathOf(z);
    }

    public static String m() {
        return getAbsolutePathOf(A);
    }

    public static String n() {
        return getAbsolutePathOf(c);
    }

    public static String o() {
        return getAbsolutePathOf(B);
    }

    public static String q() {
        return getAbsolutePathOf(".sketchware" + File.separator);
    }

    public static String r() {
        return getAbsolutePathOf(a);
    }

    public static String s() {
        return getAbsolutePathOf("sketchware");
    }

    public static String t() {
        return getAbsolutePathOf(o);
    }

    public static String u() {
        return getAbsolutePathOf(h);
    }

    public static String v() {
        return getAbsolutePathOf(f);
    }

    public static String w() {
        return getAbsolutePathOf(g);
    }

    public static String getCustomComponent() {
        return getAbsolutePathOf(CUSTOM_COMPONENT_FILE);
    }

    public static String getExtraDataExport() {
        return getAbsolutePathOf(EXTRA_DATA_EXPORT);
    }


}
