package a.a.a;

import android.content.Context;
import android.os.Environment;

import com.besome.sketch.SketchApplication;

import java.io.File;

public class wq {

    public static final String A = "sketchware" + File.separator + "localization" + File.separator + "strings_provided.xml";
    public static final String B = "sketchware" + File.separator + "signed_apk";
    public static final String C = "sketchware" + File.separator + "keystore";
    public static final String D = "sketchware" + File.separator + "keystore" + File.separator + "release_key.jks";
    public static final String E = "sketchware" + File.separator + "service_account";
    public static final String F = ".sketchware" + File.separator + "upload";
    public static final String[] G = {"subs_year_01", "subs_50_year_01", "subs_30_year_01", "subs_20_year_01", "subs_month_06", "subs_month_03", "subs_month_01", "subs_50_month_01", "subs_30_month_01", "subs_20_month_01"};
    public static final long[] H = {32140800000L, 32140800000L, 32140800000L, 32140800000L, 16070400000L, 8035200000L, 2678400000L, 2678400000L, 2678400000L, 2678400000L};
    public static final String[] I = {"subs_month_01", "subs_year_01"};
    public static final String[] J = {"subs_50_month_01", "subs_50_year_01"};
    public static final String[] K = {"subs_30_month_01", "subs_30_year_01"};
    public static final String[] L = {"subs_20_month_01", "subs_20_year_01"};
    public static final String[] M = {"F83085529A75E7A8CEDD64013B1A374B", "90C443DFAB7F23424DE7E079787466CD", "F83085529A75E7A8CEDD64013B1A374B", "C99E5B3F179203AE2749F8F9B5A7493A", "100EFD7391FF1BEE4A1E2F960A1B8AF2"};
    public static final String[] N = {"1486507718310013_1788685811425534", "1486507718310013_1804931006467681", "1486507718310013_1805009746459807", "1486507718310013_1805001526460629", "1486507718310013_1805273579766757", "1486507718310013_1805397669754348", "1486507718310013_1805436593083789", "1486507718310013_1805666736394108", "1486507718310013_1805724186388363", "1486507718310013_1809233042704144"};
    public static final String[] O = {"255022168522663_266931247331755", "255022168522663_268282677196612", "255022168522663_268283823863164", "255022168522663_266575314034015", "255022168522663_279474749410738"};
    public static final String[] P = {"Activity", "CustomView"};
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
    public static final String r = ".sketchware" + File.separator + "download" + File.separator + "apk";
    public static final String s = ".sketchware" + File.separator + "download" + File.separator + "data";
    public static final String t = ".sketchware" + File.separator + "tutorial" + File.separator + "images";
    public static final String u = ".sketchware" + File.separator + "tutorial" + File.separator + "sounds";
    public static final String v = ".sketchware" + File.separator + "tutorial" + File.separator + "fonts";
    public static final String w = ".sketchware" + File.separator + "tutorial" + File.separator + "proj";
    public static final String x = ".sketchware" + File.separator + "collection";
    public static final String y = "sketchware" + File.separator + "localization";
    public static final String z = "sketchware" + File.separator + "localization" + File.separator + "strings.xml";

    public static String getAbsolutePathOf(String path) {
        return new File(Environment.getExternalStorageDirectory(), path).getAbsolutePath();
    }

    public static String a() {
        return getAbsolutePathOf(x);
    }

    public static String a(int type) {
        switch (type) {
            case 1:
                return "SL-01";

            case 2:
                return "SL-02";

            case 3:
                return "SL-03";

            case 4:
                return "SL-04";

            default:
                return "";
        }
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

    public static String b() {
        return getAbsolutePathOf(s);
    }

    public static String b(int type) {
        switch (type) {
            case 0:
                return "linear";
            case 1:
                return "relativelayout";
            case 2:
                return "hscroll";
            case 3:
                return "button";
            case 4:
                return "textview";
            case 5:
                return "edittext";
            case 6:
                return "imageview";
            case 7:
                return "webview";
            case 8:
                return "progressbar";
            case 9:
                return "listview";
            case 10:
                return "spinner";
            case 11:
                return "checkbox";
            case 12:
                return "vscroll";
            case 13:
                return "switch";
            case 14:
                return "seekbar";
            case 15:
                return "calendarview";
            case 16:
                return "fab";
            case 17:
                return "adview";
            case 18:
                return "mapview";
            case 19:
                return "radiobutton";
            case 20:
                return "ratingbar";
            case 21:
                return "videoview";
            case 22:
                return "searchview";
            case 23:
                return "autocomplete";
            case 24:
                return "multiautocomplete";
            case 25:
                return "gridview";
            case 26:
                return "analogclock";
            case 27:
                return "datepicker";
            case 28:
                return "timepicker";
            case 29:
                return "digitalclock";
            case 30:
                return "tablayout";
            case 31:
                return "viewpager";
            case 32:
                return "bottomnavigation";
            case 33:
                return "badgeview";
            case 34:
                return "patternlockview";
            case 35:
                return "wavesidebar";
            case 36:
                return "cardview";
            case 37:
                return "collapsingtoolbar";
            case 38:
                return "textinputlayout";
            case 39:
                return "swiperefreshlayout";
            case 40:
                return "radiogroup";
            case 41:
                return "materialbutton";
            case 42:
                return "signinbutton";
            case 43:
                return "circleimageview";
            case 44:
                return "lottie";
            case 45:
                return "youtube";
            case 46:
                return "otpview";
            case 47:
                return "codeview";
            case 48:
                return "recyclerview";
            default:
                return "widget";
        }
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
        return new File(SketchApplication.getContext().getCacheDir(), "iconpack").getAbsolutePath();
    }

    public static String g() {
        return getAbsolutePathOf(n);
    }

    public static String h() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxqe7Fu3i3VfnKRSRRljTsMuk7Br0dXaFdGnMNCzMGLQ72PSTEAUo4sXs5+Utmdf9R2s2tZyArdnehk9+Q72F0XzEZGeVfgzfLky7ffuk04yxUye/FlXBun/s0F7g2496+PyfXCP9jIBdncvQ9kaT8Xn6F/j0s2TqS/6xlCD38eYgCVFyp1mld1vYhGCZBlQvXFVJAoKCzqN2QZVO5KarkyTQSGeudvV/UQsJgyHh5zTZKnla1VIVj1Wl3nBb//s2dsmFnAx3500Y/h//XHveLUS7BkP34AGGWPLuoyJruLNvrZ3uUNDnCgnW4+z8Ilaj2SwCTeqQvvw/suZdExs88QIDAQAB";
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

    public static String p() {
        return getAbsolutePathOf(E);
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

    public static String x() {
        return getAbsolutePathOf(F);
    }

    public static String y() {
        return "16011998";
    }

    public static String[] z() {
        return new String[]{"Blowfish", "Blowfish/CBC/PKCS5Padding"};
    }
}
