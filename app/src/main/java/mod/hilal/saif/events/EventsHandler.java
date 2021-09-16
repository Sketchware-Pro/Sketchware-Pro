package mod.hilal.saif.events;

import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Gx;
import a.a.a.oq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;

public class EventsHandler {

    /**
     * Used in {@link oq#a()}
     *
     * @return Array of Activity Events.
     * @apiNote Custom Activity Events can be added by writing to the file
     * /Internal storage/.sketchware/data/system/events.json and specifying an empty string for "var"
     */
    public static String[] getActivityEvents() {
        String path = getPath("events");
        ArrayList<String> array = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < len; i++) {
                        String c = arr.getJSONObject(i).getString("var");
                        if (c.equals("")) {
                            array.add(arr.getJSONObject(i).getString("name"));
                        }
                    }
                }
            }
            array.add("onTabLayoutNewTabAdded");
            array.add("onContextItemSelected");
            array.add("onCreateContextMenu");
            array.add("onOptionsItemSelected");
            array.add("onCreateOptionsMenu");
            array.add("onRestoreInstanceState");
            array.add("onSaveInstanceState");
            array.add("onDestroy");
            array.add("onStop");
            array.add("onPause");
            array.add("onResume");
            array.add("onStart");
            array.add("onPostCreate");
            array.add("onBackPressed");
            array.add("onActivityResult");
            array.add("initializeLogic");
            array.add("Import");
            ///array.add("AndroidManifest");

            Collections.reverse(array);

            return array.toArray(new String[0]);
        } catch (Exception e) {
            return new String[]{"Import", "initializeLogic", "onActivityResult", "onBackPressed", "onPostCreate", "onStart", "onResume",
                    "onPause", "onStop", "onDestroy", "onSaveInstanceState", "onRestoreInstanceState",
                    "onCreateOptionsMenu", "onOptionsItemSelected", "onCreateContextMenu",
                    "onContextItemSelected", "onTabLayoutNewTabAdded"};
        }
    }

    /**
     * Used in {@link mod.agus.jcoderz.editor.event.ManageEvent#a(Gx, ArrayList)} to retrieve extra
     * Events for Components, such as custom ones.
     */
    public static void addEvents(Gx gx, ArrayList<String> list) {
        if (gx.a("Clickable")) {
            list.add(" onLongClick");
        }
        if (gx.a("SwipeRefreshLayout")) {
            list.add("onSwipeRefreshLayout");
        }
        if (gx.a("AsyncTask")) {
            list.add("onPreExecute");
            list.add("doInBackground");
            list.add("onProgressUpdate");
            list.add("onPostExecute");
        }
        String path = getPath("events");

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < len; i++) {
                        if (gx.a(arr.getJSONObject(i).getString("var"))) {
                            list.add(arr.getJSONObject(i).getString("name"));
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Used in {@link mod.agus.jcoderz.editor.event.ManageEvent#b(Gx, ArrayList)} to get extra
     * listeners for Components and Widgets, such as custom ones.
     */
    public static void addListeners(Gx gx, ArrayList<String> list) {
        ArrayList<String> temp = new ArrayList<>();
        if (gx.a("Clickable")) {
            temp.add(" onLongClickListener");
        }
        if (gx.a("SwipeRefreshLayout")) {
            temp.add("onSwipeRefreshLayoutListener");
        }
        if (gx.a("AsyncTask")) {
            temp.add("AsyncTaskClass");
        }

        String path = getPath("events");
        try {

            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                ArrayList<HashMap<String, Object>> data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                final int len = data.size();
                if (len > 0) {
                    for (int i = 0; i < len; i++) {
                        if (gx.a((String) data.get(i).get("var"))) {
                            if (!temp.contains((String) data.get(i).get("listener"))) {
                                temp.add((String) data.get(i).get("listener"));
                            }
                        }
                    }
                }
            }
            list.addAll(temp);
        } catch (Exception ignored) {
        }
    }

    /**
     * Used in {@link mod.agus.jcoderz.editor.event.ManageEvent#c(String, ArrayList)} to get extra
     * listeners' Events, such as custom ones.
     */
    public static void addEventsToListener(String name, ArrayList<String> list) {
        switch (name) {
            case " onLongClickListener":
                list.add(" onLongClick");
                break;

            case "onSwipeRefreshLayoutListener":
                list.add("onSwipeRefreshLayout");
                break;

            case "AsyncTaskClass":
                list.add("onPreExecute");
                list.add("doInBackground");
                list.add("onProgressUpdate");
                list.add("onPostExecute");
                break;

            default:
                String path = getPath("events");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < (len); i++) {
                                String c = arr.getJSONObject(i).getString("listener");
                                if (name.equals(c)) {
                                    list.add(arr.getJSONObject(i).getString("name"));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to read Custom Event '" + name + "''s Listeners", e);
                }
        }
    }

    public static int getIcon(String name) {
        switch (name) {
            case "Import":
            case "onActivityResult":
            case "initializeLogic":
            case "onBackPressed":
            case "onPostCreate":
            case "onStart":
            case "onResume":
            case "onPause":
            case "onStop":
            case "onDestroy":
            case "onTabLayoutNewTabAdded":
                return Resources.drawable.widget_source;

            case " onLongClick":
                return Resources.drawable.check_upload_apk_48dp;

            case "onSwipeRefreshLayout":
                return Resources.drawable.widget_swipe_refresh;

            case "onPreExecute":
                return Resources.drawable.event_on_stop_tracking_touch_48dp;

            case "doInBackground":
                return Resources.drawable.event_on_animation_start_48dp;

            case "onProgressUpdate":
                return Resources.drawable.event_on_page_started_48dp;

            case "onPostExecute":
                return Resources.drawable.event_on_progress_changed_48dp;

            default:
                String path = getPath("events");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (name.equals(c)) {
                                    return Integer.parseInt(arr.getJSONObject(i).getString("icon"));
                                }
                            }
                        }
                    }
                    return Resources.drawable.android_icon;
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to get Custom Event '" + name + "''w icon", e);
                    return Resources.drawable.android_icon;
                }
        }
    }

    public static String getDesc(String name) {
        switch (name) {
            case "Import":
                return "add custom imports";

            case "onActivityResult":
                return "onActivityResult";

            case "initializeLogic":
                return "initializeLogic";

            case "onSwipeRefreshLayout":
                return "On SwipeRefreshLayout swipe";

            case " onLongClick":
                return "onLongClick";

            case "onTabLayoutNewTabAdded":
                return "return the name of current tab";

            case "onPreExecute":
                return "This method contains the code which is executed before the background processing starts.";

            case "doInBackground":
                return "This method contains the code which needs to be executed in background.";

            case "onProgressUpdate":
                return "This method receives progress updates from doInBackground method.";

            case "onPostExecute":
                return "This method is called after doInBackground method completes processing.";

            default:
                String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (name.equals(c)) {
                                    return arr.getJSONObject(i).getString("description");
                                }
                            }
                        }
                    }
                    return "No_Description";
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to parse description of Custom Event " +
                            "'" + name + "'", e);
                    return "description error: " + e.getMessage();
                }
        }
    }

    public static String getEventCode(String name, String param) {
        switch (name) {
            case "Import":
                // Changed from: "...vF\n${param}\n//3b..."
                return "//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF\r\n" +
                        param + "\r\n" +
                        "//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ";

            case "onActivityResult":
            case "initializeLogic":
                return "";

            case "onSwipeRefreshLayout":
                // Changed from: "@Override \npublic void..."
                return "@Override\r\n" +
                        "public void onRefresh() {\n" +
                        param + "\r\n" +
                        "}";

            case " onLongClick":
                // Changed from: "@Override\r\n public boolean..."
                return "@Override\r\n" +
                        "public boolean onLongClick(View _view) {\r\n" +
                        param + "\r\n" +
                        "return true;\r\n" +
                        "}";

            case "onTabLayoutNewTabAdded":
                // Changed from: "public  CharSequence  onTabLayoutNewTabAdded( int   _position ){..."
                return "public CharSequence onTabLayoutNewTabAdded(int _position) {\r\n" +
                        param + "\r\n" +
                        "return null;\r\n" +
                        "}";

            case "onPreExecute":
                return "@Override\r\n" +
                        "protected void onPreExecute() {\r\n" +
                        param + "\r\n" +
                        "}";

            case "doInBackground":
                return "@Override\r\n" +
                        "protected String doInBackground(String... params) {\r\n" +
                        "String _param = params[0];\r\n" +
                        param + "\r\n" +
                        "}";

            case "onProgressUpdate":
                return "@Override\r\n" +
                        "protected void onProgressUpdate(Integer... values) {\r\n" +
                        "int _value = values[0];\r\n" +
                        param + "\r\n" +
                        "}";

            case "onPostExecute":
                return "@Override\r\n" +
                        "protected void onPostExecute(String _result) {\r\n" +
                        param + "\r\n" +
                        "}";

            default:
                String path = getPath("events");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (name.equals(c)) {
                                    return String.format(arr.getJSONObject(i).getString("code"), param);
                                }
                            }
                        }
                    }
                    return "//no code";
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to parse Custom Event '" + name + "''s code", e);
                    return "//code error: " + e.getMessage();
                }
        }
    }

    public static String getBlocks(String name) {
        switch (name) {
            case "Import":
            case "initializeLogic":
            case "onSwipeRefreshLayout":
            case " onLongClick":
            case "onPreExecute":
                return "";

            case "onActivityResult":
                return "%d.requestCode %d.resultCode %m.intent";

            case "onTabLayoutNewTabAdded":
            case "onProgressUpdate":
                return "%d";

            case "doInBackground":
            case "onPostExecute":
                return "%s";

            default:
                String path = getPath("events");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (name.equals(c)) {
                                    return arr.getJSONObject(i).getString("parameters");
                                }
                            }
                        }
                    }
                    return "";
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to get blocks of Custom Event '" + name + "'", e);
                    return "";
                }
        }
    }

    public static String getSpec(String name, String event) {
        switch (event) {
            case "Import":
                return "create new import";

            case "onActivityResult":
                return "OnActivityResult %d.requestCode %d.resultCode %m.intent.data";

            case "initializeLogic":
                return "initializeLogic";

            case "onSwipeRefreshLayout":
                return "when " + name + " refresh";

            case " onLongClick":
                return "when " + name + " long clicked";

            case "onTabLayoutNewTabAdded":
                return name + " return tab title %d.position";

            case "onPreExecute":
                return name + " onPreExecute ";

            case "doInBackground":
                return name + " doInBackground %s.param";

            case "onProgressUpdate":
                return name + " onProgressUpdate progress %d.value";

            case "onPostExecute":
                return name + " onPostExecute result %s.result";

            default:
                String path = getPath("events");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (event.equals(c)) {
                                    return arr.getJSONObject(i).getString("headerSpec").replace("###", name);
                                }
                            }
                        }
                    }
                    return "no spec";
                } catch (Exception e) {
                    return "spec error: " + e.getMessage();
                }
        }
    }

    ///listeners codes
    public static String getListenerCode(String name, String var, String param) {
        switch (name) {
            case " onLongClickListener":
                return var + ".setOnLongClickListener(new View.OnLongClickListener() {\r\n" +
                        param + "\r\n" +
                        "});";

            case "onSwipeRefreshLayoutListener":
                return var + ".setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {\r\n" +
                        param + "\r\n" +
                        "});";

            case "AsyncTaskClass":
                return "private class " + var + " extends AsyncTask<String, Integer, String> {\r\n" +
                        param + "\r\n" +
                        "}";

            default:
                String path = getPath("listeners");

                try {
                    if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                        JSONArray arr = new JSONArray(FileUtil.readFile(path));
                        final int len = arr.length();
                        if (len > 0) {
                            for (int i = 0; i < len; i++) {
                                String c = arr.getJSONObject(i).getString("name");
                                if (name.equals(c)) {
                                    return String.format(arr.getJSONObject(i).getString("code").replace("###", var), param);
                                }
                            }
                        }
                    }
                    return "//no listener code";
                } catch (Exception e) {
                    LogUtil.e("EventsHandler", "Failed to parse Custom Event '" + name + "''s listener code", e);
                    return "//code listener error: " + e.getMessage();
                }
        }
    }

    public static void getImports(ArrayList<String> list, String name) {
        String path = getPath("listeners");

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < len; i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            if (!arr.getJSONObject(i).getString("imports").equals("")) {
                                ArrayList<String> temp = new ArrayList<>(Arrays.asList(arr.getJSONObject(i).getString("imports").split("\n")));
                                list.addAll(temp);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e("EventsHandler", "Failed to parse imports of Custom Event '" + name + "'", e);
        }
    }

    public static String getPath(String type) {
        if (type.equals("activity_event_array")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/activity_events.json");
        }
        if (type.equals("events")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        }
        if (type.equals("listeners")) {
            return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/listeners.json");
        }
        return "";
    }
}
