package mod.hilal.saif.components;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.Hx;
import mod.agus.jcoderz.lib.FileUtil;

public class ComponentExtraCode {

    public StringBuilder b;
    public Hx hx;

    public ComponentExtraCode(Hx h, StringBuilder st) {
        this.b = st;
        this.hx = h;
    }

    public void s(String str) {
        // Aldi's original Components
        if (str.contains("DatePickerFragment")) {
            hx.l = str;
            return;
        }
        if (str.contains("FragmentStatePagerAdapter")) {
            String temp = hx.k;
            if (temp.equals("")) {
                hx.k = str;
            } else {
                hx.k = temp.concat("\r\n\r\n").concat(str);
            }
            return;
        }
        if (str.contains("extends AsyncTask<String, Integer, String>")) {
            String temp = hx.k;
            if (temp.equals("")) {
                hx.k = str;
            } else {
                hx.k = temp.concat("\r\n\r\n").concat(str);
            }
            return;
        }

        // Hilal's components
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                if (arr.length() > 0) {
                    for (int i = 0; i < arr.length(); i++) {
                        String c = arr.getJSONObject(i).getString("code");
                        String f = getFirstLine(c);
                        if (!arr.getJSONObject(i).isNull("s") && str.contains(f)) {
                            String q = arr.getJSONObject(i).getString("s");
                            if (q.equals("true")) {
                                String temp = hx.k;
                                if (temp.equals("")) {
                                    hx.k = str.replace(f, "");
                                    return;
                                } else {
                                    hx.k = temp.concat("\r\n\r\n").concat(str.replace(f, ""));
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (b.length() > 0 && str.length() > 0) {
                b.append("\r\n");
                b.append("\r\n");
                b.append(str);
            }
        }


        ///others
        if (b.length() > 0 && str.length() > 0) {
            b.append("\r\n");
            b.append("\r\n");
        }
        b.append(str);
    }

    public String getFirstLine(String con) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(con.split("\n")));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String a = list.get(i);
                if (!a.equals("")) {
                    return a.trim();
                }
            }
        }
        return "qTHwdyRjVoEqNjuXx";
    }
}
