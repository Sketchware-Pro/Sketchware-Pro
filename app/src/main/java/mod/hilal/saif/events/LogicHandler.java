package mod.hilal.saif.events;

import java.util.ArrayList;
import java.util.Arrays;
import mod.hilal.saif.lib.FileUtil;

public class LogicHandler {
    public static String base(String str) {
        try {
            new ArrayList();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(Arrays.asList(str.split("\n")));
            int i = 0;
            boolean z = true;
            while (i < arrayList2.size()) {
                if (z && !((String) arrayList2.get(i)).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF")) {
                    arrayList.add((String) arrayList2.get(i));
                }
                if (((String) arrayList2.get(i)).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF")) {
                    z = false;
                }
                boolean z2 = ((String) arrayList2.get(i)).contains("//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ") ? true : z;
                i++;
                z = z2;
            }
            String str2 = "";
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str2 == "") {
                    str2 = (String) arrayList.get(i2);
                } else {
                    str2 = str2.concat("\n").concat((String) arrayList.get(i2));
                }
            }
            return str2;
        } catch (Exception e) {
            return str;
        }
    }

    public static String imports(String str) {
        try {
            new ArrayList();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(Arrays.asList(str.split("\n")));
            int i = 0;
            boolean z = false;
            while (i < arrayList2.size() && !((String) arrayList2.get(i)).contains("//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ")) {
                if (z) {
                    arrayList.add((String) arrayList2.get(i));
                }
                boolean z2 = ((String) arrayList2.get(i)).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF") ? true : z;
                i++;
                z = z2;
            }
            String str2 = "";
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str2 == "") {
                    str2 = (String) arrayList.get(i2);
                } else {
                    str2 = str2.concat("\n").concat((String) arrayList.get(i2));
                }
            }
            return str2;
        } catch (Exception e) {
            return str;
        }
    }

    public static void android(String str, String str2) {
        try {
            new ArrayList();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(Arrays.asList(str.split("\n")));
            int i = 0;
            boolean z = false;
            while (i < arrayList2.size() && !((String) arrayList2.get(i)).contains("//F45d45FDs4f56iq")) {
                if (z) {
                    arrayList.add((String) arrayList2.get(i));
                }
                boolean z2 = ((String) arrayList2.get(i)).contains("//AndroidManifest_Start") ? true : z;
                i++;
                z = z2;
            }
            String str3 = "";
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str3 == "") {
                    str3 = (String) arrayList.get(i2);
                } else {
                    str3 = str3.concat("\n").concat((String) arrayList.get(i2));
                }
            }
            FileUtil.writeFile(str3, FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/temp/").concat(str2));
        } catch (Exception e) {
        }
    }

    public static String deVar(String str) {
        try {
            new ArrayList();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(Arrays.asList(str.split("\n")));
            int i = 0;
            boolean z = false;
            while (i < arrayList2.size() && !((String) arrayList2.get(i)).contains("//F45d45FDs4f56ic")) {
                if (z) {
                    arrayList.add((String) arrayList2.get(i));
                }
                boolean z2 = ((String) arrayList2.get(i)).contains("//Define Variables_Start") ? true : z;
                i++;
                z = z2;
            }
            String str2 = "";
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (str2 == "") {
                    str2 = (String) arrayList.get(i2);
                } else {
                    str2 = str2.concat("\n").concat((String) arrayList.get(i2));
                }
            }
            return str2;
        } catch (Exception e) {
            return str;
        }
    }

    public static void V() {
    }
}
