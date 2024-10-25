package mod.hilal.saif.events;

import java.util.ArrayList;
import java.util.Arrays;

import mod.agus.jcoderz.lib.FileUtil;

public class LogicHandler {

    public static String base(String codes) {
        try {
            int n = 1;
            String newStr = "";
            ArrayList<String> arr;
            ArrayList<String> arr2 = new ArrayList<>();
            arr = new ArrayList<>(Arrays.asList(codes.split("\n")));
            for (int i = 0; i < arr.size(); i++) {
                if (n == 1) {
                    if (!arr.get(i).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF")) { //it doesn't contain the starting code.
                        arr2.add(arr.get(i));
                    }
                }
                if (arr.get(i).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF")) { // if it contains the starting code, then stop injection.
                    n = 0;
                }
                if (arr.get(i).contains("//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ")) { // if it contains the ending code, keep going.
                    n = 1;
                }
            }
            //assemble
            for (int i = 0; i < arr2.size(); i++) {
                if (newStr.isEmpty()) {
                    newStr = arr2.get(i);
                } else {
                    newStr = newStr.concat("\n").concat(arr2.get(i));
                }
            }
            return newStr;
        } catch (Exception e) {
            return codes;
        }
    }

    public static String imports(String codes) {
        try {
            int n = 0;
            String newStr = "";
            ArrayList<String> arr;
            ArrayList<String> arr2 = new ArrayList<>();
            arr = new ArrayList<>(Arrays.asList(codes.split("\n")));
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains("//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ")) {//end
                    break;
                }
                if (n == 1) {
                    arr2.add(arr.get(i));
                }
                if (arr.get(i).contains("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF")) {//start
                    n = 1;
                }
            }
            //assemble
            for (int i = 0; i < arr2.size(); i++) {
                if (newStr.isEmpty()) {
                    newStr = arr2.get(i);
                } else {
                    newStr = newStr.concat("\n").concat(arr2.get(i));
                }
            }
            return newStr;
        } catch (Exception e) {
            return codes;
        }
    }

    public static void android(String codes, String javaName) {
        try {
            int n = 0;
            String newStr = "";
            ArrayList<String> arr;
            ArrayList<String> arr2 = new ArrayList<>();
            arr = new ArrayList<>(Arrays.asList(codes.split("\n")));
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains("//F45d45FDs4f56iq")) {
                    break;
                }
                if (n == 1) {
                    arr2.add(arr.get(i));
                }
                if (arr.get(i).contains("//AndroidManifest_Start")) {
                    n = 1;
                }
            }
            //assemble
            for (int i = 0; i < arr2.size(); i++) {
                if (newStr.isEmpty()) {
                    newStr = arr2.get(i);
                } else {
                    newStr = newStr.concat("\n").concat(arr2.get(i));
                }
            }
            FileUtil.writeFile(newStr, FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/temp/").concat(javaName));
        } catch (Exception ignored) {
        }
    }

    public static String deVar(String codes) {
        try {
            int n = 0;
            String newStr = "";
            ArrayList<String> arr;
            ArrayList<String> arr2 = new ArrayList<>();
            arr = new ArrayList<>(Arrays.asList(codes.split("\n")));
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains("//F45d45FDs4f56ic")) {
                    break;
                }
                if (n == 1) {
                    arr2.add(arr.get(i));
                }
                if (arr.get(i).contains("//Define Variables_Start")) {
                    n = 1;
                }
            }
            //assemble
            for (int i = 0; i < arr2.size(); i++) {
                if (newStr.isEmpty()) {
                    newStr = arr2.get(i);
                } else {
                    newStr = newStr.concat("\n").concat(arr2.get(i));
                }
            }
            return newStr;
        } catch (Exception e) {
            return codes;
        }
    }

    public static void V() {
    }
}
