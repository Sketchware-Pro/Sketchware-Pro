package mod.hilal.saif.blocks;

import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.hey.studios.util.Helper;
import pro.sketchware.utility.FileUtil;

/**
 * Sample usage of CommandBlock:
 * /*-JX4UA2y_f1OckjjvxWI.bQwRei-sLEsBmds7ArsRfi0xSFEP3Php97kjdMCs5ed
 * >void initialize(Bundle _savedInstanceState
 * >2
 * >12
 * >0
 * >replace
 * setSupportActionBar(_toolbar);
 * getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 * BpWI8U4flOpx8Ke66QTlZYBA_NEusQ7BN-D0wvZs7ArsRfi0.EP3Php97kjdMCs
 */
public class CommandBlock {

    public static String applyCommands(String fileName, String c) {
        String str = c;
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        ArrayList<HashMap<String, Object>> data;
        try {
            //writeLog("try");
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).isEmpty() && !FileUtil.readFile(path).equals("[]")) {
                //writeLog("the file is found and has content :)");
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                //writeLog("get gson from file is done");
                for (int i = 0; i < data.size(); i++) {
                    //writeLog("element : " + i);
                    if (getInputName((String) data.get(i).get("input")).equals(fileName)) {
                        //writeLog("element : " + i + " > find a target !!");
                        str = N(str, data.get(i));
                        //writeLog("element : " + i + " > N is done :l");
                    }
                }
                //writeLog("process is succeeded");
                return str;
            } else {
                //writeLog("file isn't found :(");
                return c;
            }
        } catch (Exception e) { /*writeLog("error :("); */
            return c;
        }
    }

    private static String N(String c, HashMap<String, Object> map) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        String res = "";
        String reference = (String) map.get("reference");
        double distance = (double) map.get("distance");
        double after = (double) map.get("after");
        double before = (double) map.get("before");
        String command = (String) map.get("command");
        String input = getExceptFirstLine((String) map.get("input"));

        if (command.equals("find-replace")) {
            return c.replace(reference, input);
        }
        if (command.equals("find-replace-first")) {
            try {
                return c.replaceFirst(reference, input);
            } catch (Exception e) {
                return c;
            }
        }

        if (command.equals("find-replace-all")) {
            try {
                return c.replaceAll(reference, input);
            } catch (Exception e) {
                return c;
            }
        }

        int index = getIndex(a, reference);
        if (index == -1) {
            return c;
        }

        if (command.equals("insert")) {
            if ((index + distance - before) < 0) {
                a.add(0, input);
            } else if ((index + distance - before) > (a.size() - 1)) {
                a.add(input);
            } else {
                a.add((int) (index + distance - before), input);
            }
        }
        if (command.equals("add")) {
            if ((index + distance + after + 1) < 0) {
                a.add(0, input);
            } else if ((index + distance + after + 1) > (a.size() - 1)) {
                a.add(input);
            } else {
                a.add((int) (index + distance + after + 1), input);
            }
        }

        //old method
        /*
        if(command.equals("replace")){
            int xxx = (int)(index + distance - before -1);
            if ( xxx <0 ){ xxx = 0; }

            int ff = (int)(index + distance - before);
            if ( ff <0 ){ ff = 0; }
            int tt = (int)(index + distance + after +1);
            if ( tt > a.size() ){ tt = a.size(); }
            a.subList(ff , tt).clear();

            if (xxx > a.size()-2){
                a.add(input);
            } else {
                a.add((int)(xxx+1) , input);
            }
        }
        */

        if (command.equals("replace")) {
            if (before == 0 && after == 0) {
                int lineToChange = (int) (index + distance);
                if (lineToChange < 0) {
                    lineToChange = 0;
                }
                if (lineToChange > (a.size() - 1)) {
                    lineToChange = a.size() - 1;
                }
                a.set(lineToChange, input);
            } else {
                int lineToChange = (int) (index + distance);
                if (lineToChange <= 0) { // ignore backend
                    lineToChange = 0;
                    int from = 1;
                    int to = (int) after + 1;
                    if (to > (a.size() - 1)) {
                        to = a.size() - 1;
                    }
                    a.subList(from, to).clear();
                    a.set(0, input);
                } else if (lineToChange >= (a.size() - 1)) { //ignore frontend
                    lineToChange = a.size() - 1;
                    int from = (int) (lineToChange - before);
                    int to = lineToChange;
                    if (from < 0) {
                        from = 0;
                    }
                    a.set(lineToChange, input);
                    a.subList(from, to).clear();
                } else {  //handle everything
                    if (before < 0) {
                        before = 0;
                    }
                    if (after < 0) {
                        after = 0;
                    }
                    int from = lineToChange + 1;
                    int to = lineToChange + (int) after;
                    if (to > (a.size() - 1)) {
                        to = a.size() - 1;
                    }
                    a.subList(from, to).clear();
                    a.set(lineToChange, input);
                    from = (int) (lineToChange - before);
                    to = lineToChange;
                    if (from < 0) {
                        from = 0;
                    }
                    a.subList(from, to).clear();
                }
            }
        }

        for (int i = 0; i < a.size(); i++) {
            if (res.isEmpty()) {
                res = a.get(i);
            } else {
                res = res.concat("\n").concat(a.get(i));
            }
        }

        return res;
    }

    public static String getExceptFirstLine(String c) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        String res = "";
        if (!a.isEmpty()) {
            a.remove(0);
        } else {
            return c;
        }
        for (int i = 0; i < a.size(); i++) {
            if (res.isEmpty()) {
                res = a.get(i);
            } else {
                res = res.concat("\n").concat(a.get(i));
            }
        }
        return res;
    }

    private static String getFirstLine(String c) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        if (!a.isEmpty()) {
            return a.get(0);
        } else {
            return "";
        }
    }

    public static String getInputName(String input) {
        String firstLine = getFirstLine(input);
        if (firstLine.startsWith(">")) {
            firstLine = firstLine.substring(1).trim();
        }
        return firstLine;
    }

    public static String CBForXml(String c) {
        String OC = c;
        String RC = OC;
        String SID = "/*AXAVajPNTpbJjsz-NGVTp08YDzfI-04kA7ZsuCl4GHqTQQiuWL45sV6Vf4gwK";
        String EID = "Ui5_PNTJb21WO6OuGwQ3psk3su1LIvyXo_OAol-kVQBC5jtN_DcPLaRCJ0yXp*/";
        try {
            //commands list
            ArrayList<HashMap<String, Object>> Cs = new ArrayList<>();
            //get command blocks from java code and add them to the list
            getCBs(Cs, RC, SID, EID);
            //remove commands lines from java file
            RC = rCCs(RC, SID, EID);
            //write temporary file
            WTF(Cs);
            return RC;
        } catch (Exception e) {
            writeLog(e.toString());
            return rCCs(c, SID, EID);
        }
    }

    // Write Temporary File
    private static void WTF(ArrayList<HashMap<String, Object>> list) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).isEmpty() && !FileUtil.readFile(path).equals("[]")) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            }
        } catch (Exception ignored) {
        }
        data.addAll(list);
        FileUtil.writeFile(path, new Gson().toJson(data));
    }

    public static void x() {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/commands");
        if (FileUtil.isExistFile(path)) {
            FileUtil.deleteFile(path);
        }
    }

    public static String CB(String c) {
        String OC = c;
        String RC = OC;
        String SID = "/*-JX4UA2y_f1OckjjvxWI.bQwRei-sLEsBmds7ArsRfi0xSFEP3Php97kjdMCs5ed";
        String EID = "BpWI8U4flOpx8Ke66QTlZYBA_NEusQ7BN-D0wvZs7ArsRfi0.EP3Php97kjdMCs*/";
        try {
            //commands list
            ArrayList<HashMap<String, Object>> Cs = new ArrayList<>();
            //get command blocks from java code and add them to the list
            getCBs(Cs, RC, SID, EID);
            //remove commands lines from java file
            RC = rCCs(RC, SID, EID);
            //command blocks for xml
            RC = CBForXml(RC);
            //apply commands
            RC = aCs(Cs, RC);
            return RC;
        } catch (Exception e) {
            writeLog(e.toString());
            return rCCs(c, SID, EID);
        }
    }

    private static void writeLog(String s) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/temp/log.txt");
        String text = "";
        if (FileUtil.isExistFile(path)) {
            text = FileUtil.readFile(path);
        }
        FileUtil.writeFile(path, text.concat("\n=>").concat(s));
    }

    private static void getCBs(ArrayList<HashMap<String, Object>> arr, String c, String sid, String eid) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        boolean b = false;
        int n = -1;
        for (int i = 0; i < a.size(); i++) {
            if (b) {
                if (a.get(i).contains(eid)) {
                    Pair<Integer, Integer> p = new Pair<>(n, i);
                    aC(a, arr, p);
                    b = false;
                    n = -1;
                }
            } else {
                if (a.get(i).contains(sid)) {
                    n = i;
                    b = true;
                }
            }
        }
    }

    private static String assemble(ArrayList<String> a) {
        String res = "";
        for (int i = 0; i < a.size(); i++) {
            if (res.isEmpty()) {
                res = a.get(i);
            } else {
                res = res.concat("\n").concat(a.get(i));
            }
        }
        return res;
    }

    private static String aCs(ArrayList<HashMap<String, Object>> arr, String c) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        String res = "";
        for (int i = 0; i < arr.size(); i++) {
            String reference = (String) arr.get(i).get("reference");
            int distance = (int) arr.get(i).get("distance");
            int after = (int) arr.get(i).get("after");
            int before = (int) arr.get(i).get("before");
            String command = (String) arr.get(i).get("command");
            String input = (String) arr.get(i).get("input");

            if (command.equals("find-replace")) {
                String temp = assemble(a);
                temp = temp.replace(reference, input);
                a = new ArrayList<>(Arrays.asList(temp.split("\n")));
                continue;
            }
            if (command.equals("find-replace-first")) {
                try {
                    String temp = assemble(a);
                    temp = temp.replaceFirst(reference, input);
                    a = new ArrayList<>(Arrays.asList(temp.split("\n")));
                    continue;
                } catch (Exception e) {
                    continue;
                }
            }

            if (command.equals("find-replace-all")) {
                try {
                    String temp = assemble(a);
                    temp = temp.replaceAll(reference, input);
                    a = new ArrayList<>(Arrays.asList(temp.split("\n")));
                    continue;
                } catch (Exception e) {
                    continue;
                }
            }

            int index = getIndex(a, reference);
            if (index == -1) {
                continue;
            }


            if (command.equals("insert")) {
                if ((index + distance - before) < 0) {
                    a.add(0, input);
                } else if ((index + distance - before) > (a.size() - 1)) {
                    a.add(input);
                } else {
                    a.add(index + distance - before, input);
                }
                continue;
            }
            if (command.equals("add")) {
                if ((index + distance + after + 1) < 0) {
                    a.add(0, input);
                } else if ((index + distance + after + 1) > (a.size() - 1)) {
                    a.add(input);
                } else {
                    a.add(index + distance + after + 1, input);
                }
                continue;
            }

            ///old method
        /*
        if(command.equals("replace")){
            boolean isZ = false;
            int xxx = (int)(index + distance - before -1);
            if ( xxx <=0 ){ isZ = true; xxx = 0; }

            int ff = (int)(index + distance - before);
            if ( ff <0 ){ ff = 0; }
            int tt = (int)(index + distance + after +1);
            if ( tt > a.size() ){ tt = a.size(); }
            a.subList(ff , tt).clear();
            //// fix bug
            if (xxx > a.size()-2){
                a.add(input);
            } else if(isZ) {
                a.add(0, input);
            } else {
                a.add((int)(xxx+1) , input);
            } continue ;}*/

            if (command.equals("replace")) {
                if (before == 0 && after == 0) {
                    int lineToChange = index + distance;
                    if (lineToChange < 0) {
                        lineToChange = 0;
                    }
                    if (lineToChange > (a.size() - 1)) {
                        lineToChange = a.size() - 1;
                    }
                    a.set(lineToChange, input);
                } else {
                    int lineToChange = index + distance;
                    if (lineToChange <= 0) { // ignore backend
                        lineToChange = 0;
                        int from = 1;
                        int to = after + 1;
                        if (to > (a.size() - 1)) {
                            to = a.size() - 1;
                        }
                        a.subList(from, to).clear();
                        a.set(0, input);
                    } else if (lineToChange >= (a.size() - 1)) { //ignore frontend
                        lineToChange = a.size() - 1;
                        int from = lineToChange - before;
                        int to = lineToChange;
                        if (from < 0) {
                            from = 0;
                        }
                        a.set(lineToChange, input);
                        a.subList(from, to).clear();
                    } else {  //handle everything
                        if (before < 0) {
                            before = 0;
                        }
                        if (after < 0) {
                            after = 0;
                        }
                        int from = lineToChange + 1;
                        int to = lineToChange + after;
                        if (to > (a.size() - 1)) {
                            to = a.size() - 1;
                        }
                        a.subList(from, to).clear();
                        a.set(lineToChange, input);
                        from = lineToChange - before;
                        to = lineToChange;
                        if (from < 0) {
                            from = 0;
                        }
                        a.subList(from, to).clear();
                    }
                }
            }
        }

        for (int i = 0; i < a.size(); i++) {
            if (res.isEmpty()) {
                res = a.get(i);
            } else {
                res = res.concat("\n").concat(a.get(i));
            }
        }

        return res;
    }

    private static int getIndex(ArrayList<String> a, String r) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).contains(r)) {
                return i;
            }
        }
        return -1;
    }

    private static String rCCs(String c, String sid, String eid) {
        ArrayList<String> a = new ArrayList<>(Arrays.asList(c.split("\n")));
        ArrayList<String> r = new ArrayList<>();
        String res = "";
        int w = 1;
        for (int i = 0; i < a.size(); i++) {
            if (w == 1) {
                if (!a.get(i).contains(sid)) {
                    r.add(a.get(i));
                }
            }

            if (a.get(i).contains(sid)) {
                w = 0;
            }
            if (a.get(i).contains(eid)) {
                w = 1;
            }
        }

        for (int i = 0; i < r.size(); i++) {
            if (res.isEmpty()) {
                res = r.get(i);
            } else {
                res = res.concat("\n").concat(r.get(i));
            }
        }

        return res;
    }

    private static void aC(ArrayList<String> arr, ArrayList<HashMap<String, Object>> arr2, Pair<Integer, Integer> p) {
        String ref;
        int dis;
        int af;
        int be;
        String c;
        String input = "";

        String v = arr.get(p.first + 1);
        String kk = v.substring(v.indexOf(">") + 1);
        ArrayList<String> aa = new Gson().fromJson(kk, Helper.TYPE_STRING);
        ref = aa.get(0);

        v = arr.get(p.first + 2);
        dis = Integer.parseInt(v.substring(v.indexOf(">") + 1));

        v = arr.get(p.first + 3);
        af = Integer.parseInt(v.substring(v.indexOf(">") + 1));

        v = arr.get(p.first + 4);
        be = Integer.parseInt(v.substring(v.indexOf(">") + 1));

        v = arr.get(p.first + 5);
        c = v.substring(v.indexOf(">") + 1);

        for (int i = 0; i < (p.second - p.first - 6); i++) {
            if (i == 0) {
                input = arr.get(p.first + i + 6);
            } else {
                input = input.concat("\n").concat(arr.get(p.first + i + 6));
            }
        }

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("reference", ref);
        hm.put("distance", dis);
        hm.put("after", af);
        hm.put("before", be);
        hm.put("command", c);
        hm.put("input", input);
        arr2.add(hm);
    }
}
