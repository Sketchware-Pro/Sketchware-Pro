package mod.hilal.saif.components;

import a.a.a.Hx;
import java.util.ArrayList;
import java.util.Arrays;
import mod.hilal.saif.lib.FileUtil;
import org.json.JSONArray;

public class ComponentExtraCode {
    public StringBuilder b;
    public Hx hx;

    public ComponentExtraCode(Hx hx2, StringBuilder sb) {
        this.b = sb;
        this.hx = hx2;
    }

    public void s(String str) {
        if (str.contains("DatePickerFragment")) {
            this.hx.l = str;
        } else if (str.contains("FragmentStatePagerAdapter")) {
            String str2 = this.hx.k;
            if (str2.equals("")) {
                this.hx.k = str;
                return;
            }
            this.hx.k = str2.concat("\r\n\r\n").concat(str);
        } else if (str.contains("extends AsyncTask<String, Integer, String>")) {
            String str3 = this.hx.k;
            if (str3.equals("")) {
                this.hx.k = str;
                return;
            }
            this.hx.k = str3.concat("\r\n\r\n").concat(str);
        } else {
            String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
            try {
                if (FileUtil.isExistFile(concat) && !FileUtil.readFile(concat).equals("") && !FileUtil.readFile(concat).equals("[]")) {
                    JSONArray jSONArray = new JSONArray(FileUtil.readFile(concat));
                    if (jSONArray.length() > 0) {
                        for (int i = 0; i < jSONArray.length(); i++) {
                            String firstLine = getFirstLine(jSONArray.getJSONObject(i).getString("code"));
                            if (!jSONArray.getJSONObject(i).isNull("s") && str.contains(firstLine) && jSONArray.getJSONObject(i).getString("s").equals("true")) {
                                String str4 = this.hx.k;
                                if (str4.equals("")) {
                                    this.hx.k = str.replace(firstLine, "");
                                    return;
                                }
                                this.hx.k = str4.concat("\r\n\r\n").concat(str.replace(firstLine, ""));
                                return;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (this.b.length() > 0 && str.length() > 0) {
                    this.b.append("\r\n");
                    this.b.append("\r\n");
                    this.b.append(str);
                }
            }
            if (this.b.length() > 0 && str.length() > 0) {
                this.b.append("\r\n");
                this.b.append("\r\n");
            }
            this.b.append(str);
        }
    }

    public String getFirstLine(String str) {
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\n")));
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                String str2 = (String) arrayList.get(i);
                if (!str2.equals("")) {
                    return str2.trim();
                }
            }
        }
        return "qTHwdyRjVoEqNjuXx";
    }
}
