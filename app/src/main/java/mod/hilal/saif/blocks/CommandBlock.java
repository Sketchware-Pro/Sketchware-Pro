package mod.hilal.saif.blocks;

import android.util.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import mod.hilal.saif.lib.FileUtil;

public class CommandBlock {
    public static String applyCommands(String str, String str2) {
        String str3;
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        new ArrayList();
        try {
            if (!FileUtil.isExistFile(concat) || FileUtil.readFile(concat).equals("") || FileUtil.readFile(concat).equals("[]")) {
                return str2;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            int i = 0;
            String str4 = str2;
            while (i < arrayList.size()) {
                if (getFirstLine((String) ((HashMap) arrayList.get(i)).get("input")).contains(str)) {
                    str3 = N(str4, (HashMap) arrayList.get(i));
                } else {
                    str3 = str4;
                }
                i++;
                str4 = str3;
            }
            return str4;
        } catch (Exception e) {
            return str2;
        }
    }

    public static String N(String str, HashMap<String, Object> hashMap) {
        double d;
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        String str2 = (String) hashMap.get("reference");
        double doubleValue = ((Double) hashMap.get("distance")).doubleValue();
        double doubleValue2 = ((Double) hashMap.get("after")).doubleValue();
        double doubleValue3 = ((Double) hashMap.get("before")).doubleValue();
        String str3 = (String) hashMap.get("command");
        String exceptFirstLine = getExceptFirstLine((String) hashMap.get("input"));
        if (str3.equals("find-replace")) {
            return str.replace(str2, exceptFirstLine);
        }
        if (str3.equals("find-replace-first")) {
            try {
                return str.replaceFirst(str2, exceptFirstLine);
            } catch (Exception e) {
                return str;
            }
        } else if (str3.equals("find-replace-all")) {
            try {
                return str.replaceAll(str2, exceptFirstLine);
            } catch (Exception e2) {
                return str;
            }
        } else {
            int index = getIndex(arrayList, str2);
            if (index == -1) {
                return str;
            }
            if (str3.equals("insert")) {
                if ((((double) index) + doubleValue) - doubleValue3 < 0.0d) {
                    arrayList.add(0, exceptFirstLine);
                } else if ((((double) index) + doubleValue) - doubleValue3 > ((double) (arrayList.size() - 1))) {
                    arrayList.add(exceptFirstLine);
                } else {
                    arrayList.add((int) ((((double) index) + doubleValue) - doubleValue3), exceptFirstLine);
                }
            }
            if (str3.equals("add")) {
                if (((double) index) + doubleValue + doubleValue2 + 1.0d < 0.0d) {
                    arrayList.add(0, exceptFirstLine);
                } else if (((double) index) + doubleValue + doubleValue2 + 1.0d > ((double) (arrayList.size() - 1))) {
                    arrayList.add(exceptFirstLine);
                } else {
                    arrayList.add((int) (((double) index) + doubleValue + doubleValue2 + 1.0d), exceptFirstLine);
                }
            }
            if (str3.equals("replace")) {
                if (doubleValue3 == 0.0d && doubleValue2 == 0.0d) {
                    int i = (int) (((double) index) + doubleValue);
                    if (i < 0) {
                        i = 0;
                    }
                    if (i > arrayList.size() - 1) {
                        i = arrayList.size() - 1;
                    }
                    arrayList.set(i, exceptFirstLine);
                } else {
                    int i2 = (int) (((double) index) + doubleValue);
                    if (i2 <= 0) {
                        int i3 = ((int) doubleValue2) + 1;
                        if (i3 > arrayList.size() - 1) {
                            i3 = arrayList.size() - 1;
                        }
                        arrayList.subList(1, i3).clear();
                        arrayList.set(0, exceptFirstLine);
                    } else if (i2 >= arrayList.size() - 1) {
                        int size = arrayList.size() - 1;
                        int i4 = (int) (((double) size) - doubleValue3);
                        if (i4 < 0) {
                            i4 = 0;
                        }
                        arrayList.set(size, exceptFirstLine);
                        arrayList.subList(i4, size).clear();
                    } else {
                        if (doubleValue3 < 0.0d) {
                            doubleValue3 = 0.0d;
                        }
                        if (doubleValue2 < 0.0d) {
                            d = 0.0d;
                        } else {
                            d = doubleValue2;
                        }
                        int i5 = i2 + 1;
                        int i6 = ((int) d) + i2;
                        if (i6 > arrayList.size() - 1) {
                            i6 = arrayList.size() - 1;
                        }
                        arrayList.subList(i5, i6).clear();
                        arrayList.set(i2, exceptFirstLine);
                        int i7 = (int) (((double) i2) - doubleValue3);
                        if (i7 < 0) {
                            i7 = 0;
                        }
                        arrayList.subList(i7, i2).clear();
                    }
                }
            }
            String str4 = "";
            for (int i8 = 0; i8 < arrayList.size(); i8++) {
                if (str4 == "") {
                    str4 = (String) arrayList.get(i8);
                } else {
                    str4 = str4.concat("\n").concat((String) arrayList.get(i8));
                }
            }
            return str4;
        }
    }

    public static String getExceptFirstLine(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        if (arrayList.size() <= 0) {
            return str;
        }
        arrayList.remove(0);
        String str2 = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (str2 == "") {
                str2 = (String) arrayList.get(i);
            } else {
                str2 = str2.concat("\n").concat((String) arrayList.get(i));
            }
        }
        return str2;
    }

    public static String getFirstLine(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        if (arrayList.size() > 0) {
            return (String) arrayList.get(0);
        }
        return "";
    }

    public static String CBForXml(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            getCBs(arrayList, str, "/*AXAVajPNTpbJjsz-NGVTp08YDzfI-04kA7ZsuCl4GHqTQQiuWL45sV6Vf4gwK", "Ui5_PNTJb21WO6OuGwQ3psk3su1LIvyXo_OAol-kVQBC5jtN_DcPLaRCJ0yXp*/");
            String rCCs = rCCs(str, "/*AXAVajPNTpbJjsz-NGVTp08YDzfI-04kA7ZsuCl4GHqTQQiuWL45sV6Vf4gwK", "Ui5_PNTJb21WO6OuGwQ3psk3su1LIvyXo_OAol-kVQBC5jtN_DcPLaRCJ0yXp*/");
            WTF(arrayList);
            return rCCs;
        } catch (Exception e) {
            writeLog(e.toString());
            return rCCs(str, "/*AXAVajPNTpbJjsz-NGVTp08YDzfI-04kA7ZsuCl4GHqTQQiuWL45sV6Vf4gwK", "Ui5_PNTJb21WO6OuGwQ3psk3su1LIvyXo_OAol-kVQBC5jtN_DcPLaRCJ0yXp*/");
        }
    }

    public static void WTF(ArrayList<HashMap<String, Object>> arrayList) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        ArrayList arrayList2 = new ArrayList();
        try {
            if (FileUtil.isExistFile(concat) && !FileUtil.readFile(concat).equals("") && !FileUtil.readFile(concat).equals("[]")) {
                arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
            }
        } catch (Exception e) {
        }
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(arrayList.get(i));
        }
        FileUtil.writeFile(concat, new Gson().toJson(arrayList2));
    }

    public static void x() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        if (FileUtil.isExistFile(concat)) {
            FileUtil.deleteFile(concat);
        }
    }

    public static String CB(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            getCBs(arrayList, str, "/*-JX4UA2y_f1OckjjvxWI.bQwRei-sLEsBmds7ArsRfi0xSFEP3Php97kjdMCs5ed", "BpWI8U4flOpx8Ke66QTlZYBA_NEusQ7BN-D0wvZs7ArsRfi0.EP3Php97kjdMCs*/");
            return aCs(arrayList, CBForXml(rCCs(str, "/*-JX4UA2y_f1OckjjvxWI.bQwRei-sLEsBmds7ArsRfi0xSFEP3Php97kjdMCs5ed", "BpWI8U4flOpx8Ke66QTlZYBA_NEusQ7BN-D0wvZs7ArsRfi0.EP3Php97kjdMCs*/")));
        } catch (Exception e) {
            writeLog(e.toString());
            return rCCs(str, "/*-JX4UA2y_f1OckjjvxWI.bQwRei-sLEsBmds7ArsRfi0xSFEP3Php97kjdMCs5ed", "BpWI8U4flOpx8Ke66QTlZYBA_NEusQ7BN-D0wvZs7ArsRfi0.EP3Php97kjdMCs*/");
        }
    }

    public static void writeLog(String str) {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/log.txt");
        String str2 = "";
        if (FileUtil.isExistFile(concat)) {
            str2 = FileUtil.readFile(concat);
        }
        FileUtil.writeFile(concat, str2.concat("\n=>").concat(str));
    }

    public static void getCBs(ArrayList<HashMap<String, Object>> arrayList, String str, String str2, String str3) {
        ArrayList arrayList2 = new ArrayList(Arrays.asList(str.split("\n")));
        int i = -1;
        boolean z = false;
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            if (z) {
                if (((String) arrayList2.get(i2)).contains(str3)) {
                    aC(arrayList2, arrayList, new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
                    i = -1;
                    z = false;
                }
            } else if (((String) arrayList2.get(i2)).contains(str2)) {
                z = true;
                i = i2;
            }
        }
    }

    public static String assemble(ArrayList<String> arrayList) {
        String str = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (str == "") {
                str = arrayList.get(i);
            } else {
                str = str.concat("\n").concat(arrayList.get(i));
            }
        }
        return str;
    }

    public static String aCs(ArrayList<HashMap<String, Object>> arrayList, String str) {
        ArrayList arrayList2;
        int i = 0;
        ArrayList arrayList3 = new ArrayList(Arrays.asList(str.split("\n")));
        while (i < arrayList.size()) {
            String str2 = (String) arrayList.get(i).get("reference");
            int intValue = ((Integer) arrayList.get(i).get("distance")).intValue();
            int intValue2 = ((Integer) arrayList.get(i).get("after")).intValue();
            int intValue3 = ((Integer) arrayList.get(i).get("before")).intValue();
            String str3 = (String) arrayList.get(i).get("command");
            String str4 = (String) arrayList.get(i).get("input");
            if (str3.equals("find-replace")) {
                arrayList2 = new ArrayList(Arrays.asList(assemble(arrayList3).replace(str2, str4).split("\n")));
            } else if (str3.equals("find-replace-first")) {
                try {
                    arrayList2 = new ArrayList(Arrays.asList(assemble(arrayList3).replaceFirst(str2, str4).split("\n")));
                } catch (Exception e) {
                    arrayList2 = arrayList3;
                }
            } else if (str3.equals("find-replace-all")) {
                try {
                    arrayList2 = new ArrayList(Arrays.asList(assemble(arrayList3).replaceAll(str2, str4).split("\n")));
                } catch (Exception e2) {
                    arrayList2 = arrayList3;
                }
            } else {
                int index = getIndex(arrayList3, str2);
                if (index == -1) {
                    arrayList2 = arrayList3;
                } else if (str3.equals("insert")) {
                    if ((index + intValue) - intValue3 < 0) {
                        arrayList3.add(0, str4);
                        arrayList2 = arrayList3;
                    } else if ((index + intValue) - intValue3 > arrayList3.size() - 1) {
                        arrayList3.add(str4);
                        arrayList2 = arrayList3;
                    } else {
                        arrayList3.add((index + intValue) - intValue3, str4);
                        arrayList2 = arrayList3;
                    }
                } else if (!str3.equals("add")) {
                    if (str3.equals("replace")) {
                        if (intValue3 == 0 && intValue2 == 0) {
                            int i2 = index + intValue;
                            if (i2 < 0) {
                                i2 = 0;
                            }
                            if (i2 > arrayList3.size() - 1) {
                                i2 = arrayList3.size() - 1;
                            }
                            arrayList3.set(i2, str4);
                            arrayList2 = arrayList3;
                        } else {
                            int i3 = intValue + index;
                            if (i3 <= 0) {
                                int i4 = intValue2 + 1;
                                if (i4 > arrayList3.size() - 1) {
                                    i4 = arrayList3.size() - 1;
                                }
                                arrayList3.subList(1, i4).clear();
                                arrayList3.set(0, str4);
                                arrayList2 = arrayList3;
                            } else if (i3 >= arrayList3.size() - 1) {
                                int size = arrayList3.size() - 1;
                                int i5 = size - intValue3;
                                if (i5 < 0) {
                                    i5 = 0;
                                }
                                arrayList3.set(size, str4);
                                arrayList3.subList(i5, size).clear();
                                arrayList2 = arrayList3;
                            } else {
                                int i6 = intValue3 < 0 ? 0 : intValue3;
                                int i7 = intValue2 < 0 ? 0 : intValue2;
                                int i8 = i3 + 1;
                                int i9 = i7 + i3;
                                if (i9 > arrayList3.size() - 1) {
                                    i9 = arrayList3.size() - 1;
                                }
                                arrayList3.subList(i8, i9).clear();
                                arrayList3.set(i3, str4);
                                int i10 = i3 - i6;
                                if (i10 < 0) {
                                    i10 = 0;
                                }
                                arrayList3.subList(i10, i3).clear();
                            }
                        }
                    }
                    arrayList2 = arrayList3;
                } else if (index + intValue + intValue2 + 1 < 0) {
                    arrayList3.add(0, str4);
                    arrayList2 = arrayList3;
                } else if (index + intValue + intValue2 + 1 > arrayList3.size() - 1) {
                    arrayList3.add(str4);
                    arrayList2 = arrayList3;
                } else {
                    arrayList3.add(index + intValue + intValue2 + 1, str4);
                    arrayList2 = arrayList3;
                }
            }
            i++;
            arrayList3 = arrayList2;
        }
        String str5 = "";
        for (int i11 = 0; i11 < arrayList3.size(); i11++) {
            if (str5 == "") {
                str5 = (String) arrayList3.get(i11);
            } else {
                str5 = str5.concat("\n").concat((String) arrayList3.get(i11));
            }
        }
        return str5;
    }

    public static int getIndex(ArrayList arrayList, String str) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (((String) arrayList.get(i)).contains(str)) {
                return i;
            }
        }
        return -1;
    }

    public static String rCCs(String str, String str2, String str3) {
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        ArrayList arrayList2 = new ArrayList();
        boolean z = true;
        for (int i = 0; i < arrayList.size(); i++) {
            if (z && !((String) arrayList.get(i)).contains(str2)) {
                arrayList2.add((String) arrayList.get(i));
            }
            if (((String) arrayList.get(i)).contains(str2)) {
                z = false;
            }
            if (((String) arrayList.get(i)).contains(str3)) {
                z = true;
            }
        }
        String str4 = "";
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            if (str4 == "") {
                str4 = (String) arrayList2.get(i2);
            } else {
                str4 = str4.concat("\n").concat((String) arrayList2.get(i2));
            }
        }
        return str4;
    }

    public static void aC(ArrayList arrayList, ArrayList<HashMap<String, Object>> arrayList2, Pair<Integer, Integer> pair) {
        String concat;
        int i = 0;
        String str = "";
        String str2 = (String) arrayList.get(((Integer) pair.first).intValue() + 1);
        Object obj = (String) ((ArrayList) new Gson().fromJson(str2.substring(str2.indexOf(">") + 1), new TypeToken<ArrayList<String>>() {
        }.getType())).get(0);
        String str3 = (String) arrayList.get(((Integer) pair.first).intValue() + 2);
        int intValue = Integer.valueOf(str3.substring(str3.indexOf(">") + 1)).intValue();
        String str4 = (String) arrayList.get(((Integer) pair.first).intValue() + 3);
        int intValue2 = Integer.valueOf(str4.substring(str4.indexOf(">") + 1)).intValue();
        String str5 = (String) arrayList.get(((Integer) pair.first).intValue() + 4);
        int intValue3 = Integer.valueOf(str5.substring(str5.indexOf(">") + 1)).intValue();
        String str6 = (String) arrayList.get(((Integer) pair.first).intValue() + 5);
        Object substring = str6.substring(str6.indexOf(">") + 1);
        while (i < (((Integer) pair.second).intValue() - ((Integer) pair.first).intValue()) - 6) {
            if (i == 0) {
                concat = (String) arrayList.get(((Integer) pair.first).intValue() + i + 6);
            } else {
                concat = str.concat("\n").concat((String) arrayList.get(((Integer) pair.first).intValue() + i + 6));
            }
            i++;
            str = concat;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("reference", obj);
        hashMap.put("distance", Integer.valueOf(intValue));
        hashMap.put("after", Integer.valueOf(intValue2));
        hashMap.put("before", Integer.valueOf(intValue3));
        hashMap.put("command", substring);
        hashMap.put("input", str);
        arrayList2.add(hashMap);
    }
}
