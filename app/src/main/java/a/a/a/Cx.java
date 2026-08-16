package a.a.a;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;

public class Cx {

    public static Cx a;
    public static int b = 10;
    public HashMap<String, ArrayList<String>> c;
    public DB d;

    public static Cx a() {
        if (a == null) {
            synchronized (Cx.class) {
                if (a == null) {
                    a = new Cx();
                }
            }
        }
        return a;
    }

    public ArrayList<String> a(String key) {
        return c != null ? c.get(key) : null;
    }

    public void a(Context context) {
        if (c == null) {
            c = new HashMap<>();
        }
        c.clear();
        if (d == null) {
            d = new DB(context, "P26");
        }
    }

    public void a(String key, String value) {
        if (c == null) {
            c = new HashMap<>();
        }
        ArrayList<String> list = c.get(key);
        if (list == null) {
            list = new ArrayList<>();
            c.put(key, list);
        }
        list.remove(value);
        list.add(0, value);
        if (list.size() > b) {
            list.remove(list.size() - 1);
        }
    }

    public void b() {
        if (c == null || d == null) {
            return;
        }
        for (String key : c.keySet()) {
            ArrayList<String> list = c.get(key);
            if (list != null && !list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String item : list) {
                    sb.append(item).append(",");
                }
                d.a(key, sb.toString());
            }
        }
    }

    public void b(String key) {
        if (c == null || d == null || c.get(key) != null) {
            return;
        }
        String storedValue = d.f(key);
        if (storedValue == null || storedValue.isEmpty()) {
            return;
        }
        String[] parts = storedValue.split(",");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].isEmpty()) {
                a(key, parts[i]);
            }
        }
    }
}
