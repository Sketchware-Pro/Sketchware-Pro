package mod.hilal.saif.events;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Gx;
import mod.hilal.saif.lib.FileUtil;

public class EventsHandler {
    public static String[] getActivityEvents() {
        JSONArray jSONArray;
        int length;
        String path = getPath("events");
        ArrayList arrayList = new ArrayList();
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (jSONArray.getJSONObject(i).getString("var").equals("")) {
                        arrayList.add(jSONArray.getJSONObject(i).getString("name"));
                    }
                }
            }
            arrayList.add("onTabLayoutNewTabAdded");
            arrayList.add("onContextItemSelected");
            arrayList.add("onCreateContextMenu");
            arrayList.add("onOptionsItemSelected");
            arrayList.add("onCreateOptionsMenu");
            arrayList.add("onRestoreInstanceState");
            arrayList.add("onSaveInstanceState");
            arrayList.add("onDestroy");
            arrayList.add("onStop");
            arrayList.add("onPause");
            arrayList.add("onResume");
            arrayList.add("onStart");
            arrayList.add("onPostCreate");
            arrayList.add("onBackPressed");
            arrayList.add("Import");
            Collections.reverse(arrayList);
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        } catch (Exception e) {
            return new String[]{"Import", "onBackPressed", "onPostCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy", "onSaveInstanceState", "onRestoreInstanceState", "onCreateOptionsMenu", "onOptionsItemSelected", "onCreateContextMenu", "onContextItemSelected", "onTabLayoutNewTabAdded"};
        }
    }

    public static void addEvents(Gx gx, ArrayList arrayList) {
        JSONArray jSONArray;
        int length;
        if (gx.a("Clickable")) {
            arrayList.add(" onLongClick");
        }
        if (gx.a("SwipeRefreshLayout")) {
            arrayList.add("onSwipeRefreshLayout");
        }
        if (gx.a("AsyncTask")) {
            arrayList.add("onPreExecute");
            arrayList.add("doInBackground");
            arrayList.add("onProgressUpdate");
            arrayList.add("onPostExecute");
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (gx.a(jSONArray.getJSONObject(i).getString("var"))) {
                        arrayList.add(jSONArray.getJSONObject(i).getString("name"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void addListeners(Gx gx, ArrayList arrayList) {
        ArrayList arrayList2;
        int size;
        String path = getPath("events");
        ArrayList arrayList3 = new ArrayList();
        new ArrayList();
        if (gx.a("Clickable")) {
            arrayList3.add(" onLongClickListener");
        }
        if (gx.a("SwipeRefreshLayout")) {
            arrayList3.add("onSwipeRefreshLayoutListener");
        }
        if (gx.a("AsyncTask")) {
            arrayList3.add("AsyncTaskClass");
        }
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (size = (arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType())).size()) > 0) {
                for (int i = 0; i < size; i++) {
                    if (gx.a((String) ((HashMap) arrayList2.get(i)).get("var")) && !arrayList3.contains((String) ((HashMap) arrayList2.get(i)).get("listener"))) {
                        arrayList3.add((String) ((HashMap) arrayList2.get(i)).get("listener"));
                    }
                }
            }
            for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                arrayList.add(arrayList3.get(i2));
            }
        } catch (Exception e) {
        }
    }

    public static void addEventsToListener(String str, ArrayList arrayList) {
        JSONArray jSONArray;
        int length;
        if (str.equals(" onLongClickListener")) {
            arrayList.add(" onLongClick");
        }
        if (str.equals("onSwipeRefreshLayoutListener")) {
            arrayList.add("onSwipeRefreshLayout");
        }
        if (str.equals("AsyncTaskClass")) {
            arrayList.add("onPreExecute");
            arrayList.add("doInBackground");
            arrayList.add("onProgressUpdate");
            arrayList.add("onPostExecute");
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("listener"))) {
                        arrayList.add(jSONArray.getJSONObject(i).getString("name"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static int getIcon(String str) {
        JSONArray jSONArray;
        int length;
        if (str.equals("Import")) {
            return 2131166270;
        }
        if (str.equals(" onLongClick")) {
            return 2131165408;
        }
        if (str.equals("onBackPressed") || str.equals("onPostCreate") || str.equals("onStart") || str.equals("onResume") || str.equals("onPause") || str.equals("onStop") || str.equals("onDestroy")) {
            return 2131166270;
        }
        if (str.equals("onSwipeRefreshLayout")) {
            return 2131166320;
        }
        if (str.equals("onPreExecute")) {
            return 2131165600;
        }
        if (str.equals("doInBackground")) {
            return 2131165557;
        }
        if (str.equals("onProgressUpdate")) {
            return 2131165588;
        }
        if (str.equals("onPostExecute")) {
            return 2131165591;
        }
        if (str.equals("onTabLayoutNewTabAdded")) {
            return 2131166270;
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return Integer.valueOf(jSONArray.getJSONObject(i).getString("icon")).intValue();
                    }
                }
            }
            return 2131165312;
        } catch (Exception e) {
            return 2131165312;
        }
    }

    public static String getDesc(String str) {
        JSONArray jSONArray;
        int length;
        if (str.equals("Import")) {
            return "create new custom import";
        }
        if (str.equals("onSwipeRefreshLayout")) {
            return "onSwipeRefreshLayout";
        }
        if (str.equals(" onLongClick")) {
            return "OnLongClick ";
        }
        if (str.equals("onTabLayoutNewTabAdded")) {
            return "return the name of current tab";
        }
        if (str.equals("onPreExecute")) {
            return "This method contains the code which is executed before the background processing starts.";
        }
        if (str.equals("doInBackground")) {
            return "This method contains the code which needs to be executed in background.";
        }
        if (str.equals("onProgressUpdate")) {
            return "This method receives progress updates from doInBackground method.";
        }
        if (str.equals("onPostExecute")) {
            return "This method is called after doInBackground method completes processing.";
        }
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        try {
            if (FileUtil.isExistFile(concat) && !FileUtil.readFile(concat).equals("") && !FileUtil.readFile(concat).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(concat))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return jSONArray.getJSONObject(i).getString("description");
                    }
                }
            }
            return "No_Description";
        } catch (Exception e) {
            return "description error";
        }
    }

    public static String getEventCode(String str, String str2) {
        JSONArray jSONArray;
        int length;
        if (str.equals("Import")) {
            return j("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF\n%s\n//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ", str2);
        }
        if (str.equals("onSwipeRefreshLayout")) {
            return j("@Override \npublic void onRefresh() {\n%s\n}", str2);
        }
        if (str.equals(" onLongClick")) {
            return j("@Override\r\n\tpublic boolean onLongClick(View _view) {\r\n%s\r\nreturn true;\r\n\t}", str2);
        }
        if (str.equals("onTabLayoutNewTabAdded")) {
            return j("public  CharSequence  onTabLayoutNewTabAdded( int   _position ){\r\n%s\r\n\r\n}", str2);
        }
        if (str.equals("onPreExecute")) {
            return j("@Override\r\nprotected void onPreExecute() {\r\n%s\r\n}", str2);
        }
        if (str.equals("doInBackground")) {
            return j("@Override\r\nprotected String doInBackground(String... params) {\r\nString _param = params[0];\r\n%s\r\n}", str2);
        }
        if (str.equals("onProgressUpdate")) {
            return j("@Override\r\nprotected void onProgressUpdate(Integer... values) {\r\nint _value = values[0];\r\n%s\r\n}", str2);
        }
        if (str.equals("onPostExecute")) {
            return j("@Override\r\nprotected void onPostExecute(String _result) {\r\n%s\r\n}", str2);
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return j(jSONArray.getJSONObject(i).getString("code"), str2);
                    }
                }
            }
            return j("//no code", str2);
        } catch (Exception e) {
            return j("//code error", str2);
        }
    }

    public static String getBlocks(String str) {
        JSONArray jSONArray;
        int length;
        if (str.equals("Import") || str.equals("onSwipeRefreshLayout") || str.equals(" onLongClick")) {
            return "";
        }
        if (str.equals("onTabLayoutNewTabAdded")) {
            return "%d";
        }
        if (str.equals("onPreExecute")) {
            return "";
        }
        if (str.equals("doInBackground")) {
            return "%s";
        }
        if (str.equals("onProgressUpdate")) {
            return "%d";
        }
        if (str.equals("onPostExecute")) {
            return "%s";
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return jSONArray.getJSONObject(i).getString("parameters");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSpec(String str, String str2) {
        JSONArray jSONArray;
        int length;
        if (str2.equals("Import")) {
            return "create new import";
        }
        if (str2.equals("onSwipeRefreshLayout")) {
            return "when " + str + " refresh";
        }
        if (str2.equals(" onLongClick")) {
            return "when " + str + " long clicked";
        }
        if (str2.equals("onTabLayoutNewTabAdded")) {
            return str + " return tab title %d.position";
        }
        if (str2.equals("onPreExecute")) {
            return str + " onPreExecute ";
        }
        if (str2.equals("doInBackground")) {
            return str + " doInBackground %s.param";
        }
        if (str2.equals("onProgressUpdate")) {
            return str + " onProgressUpdate progress %d.value";
        }
        if (str2.equals("onPostExecute")) {
            return str + " onPostExecute result %s.result";
        }
        String path = getPath("events");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str2.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return jSONArray.getJSONObject(i).getString("headerSpec").replace("###", str);
                    }
                }
            }
            return "no spec";
        } catch (Exception e) {
            return "spec error";
        }
    }

    public static String getListenerCode(String str, String str2, String str3) {
        JSONArray jSONArray;
        int length;
        if (str.equals(" onLongClickListener")) {
            return j(str2 + ".setOnLongClickListener(new View.OnLongClickListener() {\r\n %s\r\n });", str3);
        }
        if (str.equals("onSwipeRefreshLayoutListener")) {
            return j(str2 + ".setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {\r\n%s\r\n});", str3);
        }
        if (str.equals("AsyncTaskClass")) {
            return j("private class " + str2 + " extends AsyncTask<String, Integer, String> {\r\n%s\r\n}", str3);
        }
        String path = getPath("listeners");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name"))) {
                        return j(jSONArray.getJSONObject(i).getString("code").replace("###", str2), str3);
                    }
                }
            }
            return j("//no listener code", str3);
        } catch (Exception e) {
            return j("//code listener error", str3);
        }
    }

    public static void getImports(ArrayList arrayList, String str) {
        JSONArray jSONArray;
        int length;
        String path = getPath("listeners");
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]") && (length = (jSONArray = new JSONArray(FileUtil.readFile(path))).length()) > 0) {
                for (int i = 0; i < length; i++) {
                    if (str.equals(jSONArray.getJSONObject(i).getString("name")) && !jSONArray.getJSONObject(i).getString("imports").equals("")) {
                        ArrayList arrayList2 = new ArrayList(Arrays.asList(jSONArray.getJSONObject(i).getString("imports").split("\n")));
                        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                            arrayList.add(arrayList2.get(i2));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static String j(String str, String str2) {
        if (str.isEmpty()) {
            return "";
        }
        return String.format(str, str2);
    }

    public static String getPath(String str) {
        if (str.equals("activity_event_array")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/activity_events.json");
        }
        if (str.equals("events")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        }
        if (str.equals("listeners")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
        }
        return "";
    }
}
