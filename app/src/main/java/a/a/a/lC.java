package a.a.a;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class lC {
    public static DB a;

    public static class a implements Comparator<String> {
        public int a(Integer num, Integer num2) {
            return num.compareTo(num2);
        }


        @Override
        public int compare(String o1, String o2) {
            return 0;
        }
    }

    public static ArrayList<HashMap<String, Object>> a() {
        String str = "project";
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        oB oBVar = new oB();
        File[] listFiles = new File(wq.n()).listFiles();
        if (listFiles == null) {
            return arrayList;
        }
        for (File file : listFiles) {
            try {
                if (new File(file, str).exists()) {
                    String path = file.getAbsolutePath() + File.separator + str;
                    HashMap<String, Object> a = vB.a(oBVar.a(oBVar.h(path)));
                    if (yB.c(a, "sc_id").equals(file.getName())) {
                        arrayList.add(a);
                    }
                }
            } catch (Throwable e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        }
        return arrayList;
    }

    public static HashMap a(String str) {
        for (HashMap<String, Object> stringObjectHashMap : a()) {
            if (yB.c(stringObjectHashMap, "my_sc_pkg_name").equals(str) && yB.b(stringObjectHashMap, "proj_type") == 1) {
                return stringObjectHashMap;
            }
        }
        return null;
    }

    public static void a(Context context, String str) {
        File file = new File(wq.c(str));
        if (file.exists()) {
            oB oBVar = new oB();
            oBVar.a(file);
            oBVar.b(wq.d(str));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(wq.g());
            stringBuilder.append(File.separator);
            stringBuilder.append(str);
            oBVar.b(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(wq.t());
            stringBuilder.append(File.separator);
            stringBuilder.append(str);
            oBVar.b(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(wq.d());
            stringBuilder.append(File.separator);
            stringBuilder.append(str);
            oBVar.b(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(wq.e());
            stringBuilder.append(File.separator);
            stringBuilder.append(str);
            oBVar.b(stringBuilder.toString());
            oBVar.b(wq.b(str));
            oBVar.b(wq.a(str));
            stringBuilder = new StringBuilder();
            stringBuilder.append("D01_");
            stringBuilder.append(str);
            new DB(context, stringBuilder.toString()).a();
            stringBuilder = new StringBuilder();
            stringBuilder.append("D02_");
            stringBuilder.append(str);
            new DB(context, stringBuilder.toString()).a();
            stringBuilder = new StringBuilder();
            stringBuilder.append("D03_");
            stringBuilder.append(str);
            new DB(context, stringBuilder.toString()).a();
            stringBuilder = new StringBuilder();
            stringBuilder.append("D04_");
            stringBuilder.append(str);
            new DB(context, stringBuilder.toString()).a();
        }
    }

    public static void a(Context context, boolean z) {
        if (a == null) {
            a = new DB(context, "P15");
        }
    }

    public static void a(String str, HashMap<String, Object> hashMap) {
        File file = new File(wq.n());
        if (!file.exists()) {
            file.mkdirs();
        }
        str = wq.c(str);
        str = str + File.separator + "project";
        String a = vB.a(hashMap);
        oB oBVar = new oB();
        try {
            oBVar.a(str, oBVar.d(a));
        } catch (Throwable e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }

    public static String b() {
        int parseInt = Integer.parseInt("600") + 1;
        for (HashMap<String, Object> stringObjectHashMap : a()) {
            parseInt = Math.max(parseInt, Integer.parseInt(yB.c(stringObjectHashMap, "sc_id")) + 1);
        }
        return String.valueOf(parseInt);
    }

    public static HashMap<String, Object> b(String str) {
        Throwable e;
        oB oBVar = new oB();
        HashMap<String, Object> hashMap = null;
        try {
            String c = wq.c(str);
            if (!new File(c).exists()) {
                return null;
            }
            String path = c + File.separator + "project";
            HashMap<String, Object> a = vB.a(oBVar.a(oBVar.h(path)));
            try {
                return !yB.c(a, "sc_id").equals(str) ? null : a;
            } catch (Exception e2) {
                e = e2;
                hashMap = a;
                Log.e("ERROR", e.getMessage(), e);
                return hashMap;
            }
        } catch (Exception e3) {
            e = e3;
            Log.e("ERROR", e.getMessage(), e);
            return hashMap;
        }
    }

    public static void b(String str, HashMap<String, Object> hashMap) {
        HashMap<String, Object> hashMap2 = hashMap;
        String str2 = "color_control_highlight";
        String str3 = "color_primary_dark";
        String str4 = "color_primary";
        String str5 = "color_accent";
        String str6 = "sketchware_ver";
        String str7 = "sc_ver_name";
        String str8 = "sc_ver_code";
        String str9 = "my_app_name";
        String str10 = "my_ws_name";
        String str11 = "my_sc_pkg_name";
        String str12 = "published_dt";
        String str13 = "proj_type";
        String str14 = "custom_icon";
        String str15 = "color_control_normal";
        String str18 = "isIconAdaptive";
        File file = new File(wq.c(str));
        if (file.exists()) {
            String path = file + File.separator + "project";
            oB oBVar = new oB();
            try {
                HashMap<String, Object> a = vB.a(oBVar.a(oBVar.h(path)));
                String str17 = path;
                if (yB.c(a, "sc_id").equals(str)) {
                    if (hashMap2.containsKey(str18)) {
                        a.put(str18, hashMap2.get(str18));
                    }
                    if (hashMap2.containsKey(str14)) {
                        a.put(str14, hashMap2.get(str14));
                    }
                    if (hashMap2.containsKey(str13)) {
                        a.put(str13, hashMap2.get(str13));
                    }
                    if (hashMap2.containsKey(str12)) {
                        a.put(str12, hashMap2.get(str12));
                    }
                    a.put(str11, hashMap2.get(str11));
                    a.put(str10, hashMap2.get(str10));
                    a.put(str9, hashMap2.get(str9));
                    a.put(str8, hashMap2.get(str8));
                    a.put(str7, hashMap2.get(str7));
                    a.put(str6, hashMap2.get(str6));
                    a.put(str5, hashMap2.get(str5));
                    a.put(str4, hashMap2.get(str4));
                    a.put(str3, hashMap2.get(str3));
                    path = str2;
                    a.put(path, hashMap2.get(path));
                    path = str15;
                    a.put(path, hashMap2.get(path));
                    oBVar.a(str17, oBVar.d(vB.a(a)));
                }
            } catch (Throwable e) {
                Log.e("DEBUG", e.getMessage(), e);
            }
        }
    }

    public static String c() {
        ArrayList<HashMap<String, Object>> a = a();
        ArrayList<String> arrayList  = new ArrayList<>();
        Iterator it = a.iterator();
        while (true) {
            String str = "NewProject";
            if (!it.hasNext()) {
                break;
            }
            String c = yB.c((Map<String, Object>) it.next(), "my_ws_name");
            if (c.equals(str)) {
                arrayList.add(c);
            } else if (c.indexOf(str) == 0) {
                try {
                    arrayList.add(String.valueOf(Integer.parseInt(c.substring(10))));
                } catch (Exception ignored) {
                }
            }
        }
        arrayList.sort(new a());
        it = ((ArrayList<?>) arrayList).iterator();
        int i = 0;
        while (it.hasNext()) {
            int intValue = (Integer) it.next();
            int i2 = i + 1;
            if (intValue != i2) {
                if (intValue != i) {
                    break;
                }
            } else {
                i = i2;
            }
        }
        String str = arrayList.get(i);
        if (i == 0) {
            return str;
        }
        return str +  (i + 1);
    }

    public static void d() {
        for (String str : a.c().keySet()) {
            a(str, a.g(str));
        }
        a.a();
    }
}
