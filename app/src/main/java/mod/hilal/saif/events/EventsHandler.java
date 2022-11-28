package mod.hilal.saif.events;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a.a.a.Gx;
import a.a.a.oq;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import mod.jbk.util.OldResourceIdMapper;

public class EventsHandler {

    public static final String CUSTOM_EVENTS_FILE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/system/events.json";
    public static final String CUSTOM_LISTENERE_FILE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/data/system/listeners.json";
    private static ArrayList<HashMap<String, Object>> cachedCustomEvents = readCustomEvents();
    private static ArrayList<HashMap<String, Object>> cachedCustomListeners = readCustomListeners();

    /**
     * This is a utility class, don't instantiate it.
     */
    private EventsHandler() {
    }

    /**
     * Used in {@link oq#a()}
     *
     * @return Array of Activity Events.
     * @apiNote Custom Activity Events can be added by writing to the file
     * /Internal storage/.sketchware/data/system/events.json and specifying an empty string for "var"
     */
    public static String[] getActivityEvents() {
        ArrayList<String> array = new ArrayList<>();

        array.add("Import");
        array.add("initializeLogic");
        array.add("onActivityResult");
        array.add("onBackPressed");
        array.add("onPostCreate");
        array.add("onStart");
        array.add("onResume");
        array.add("onPause");
        array.add("onStop");
        array.add("onDestroy");
        array.add("onSaveInstanceState");
        array.add("onRestoreInstanceState");
        array.add("onCreateOptionsMenu");
        array.add("onOptionsItemSelected");
        array.add("onCreateContextMenu");
        array.add("onContextItemSelected");
        array.add("onTabLayoutNewTabAdded");

        for (int i = cachedCustomEvents.size() - 1; i >= 0; i--) {
            HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
            if (customEvent != null) {
                Object var = customEvent.get("var");

                if (var instanceof String) {
                    if (var.equals("")) {
                        Object name = customEvent.get("name");

                        if (name instanceof String) {
                            array.add((String) name);
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Found invalid var data type in Custom Event #" + (i + 1));
                }
            } else {
                SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
            }
        }

        return array.toArray(new String[0]);
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

        for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
            HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
            if (customEvent != null) {
                Object var = customEvent.get("var");

                if (var instanceof String) {
                    if (gx.a((String) var)) {
                        Object name = customEvent.get("name");

                        if (name instanceof String) {
                            list.add((String) name);
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Found invalid var data type in Custom Event #" + (i + 1));
                }
            } else {
                SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
            }
        }
    }

    /**
     * Used in {@link mod.agus.jcoderz.editor.event.ManageEvent#b(Gx, ArrayList)} to get extra
     * listeners for Components and Widgets, such as custom ones.
     */
    public static void addListeners(Gx gx, ArrayList<String> list) {
        if (gx.a("Clickable")) {
            list.add(" onLongClickListener");
        }
        if (gx.a("SwipeRefreshLayout")) {
            list.add("onSwipeRefreshLayoutListener");
        }
        if (gx.a("AsyncTask")) {
            list.add("AsyncTaskClass");
        }

        for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
            HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
            if (customEvent != null) {
                Object var = customEvent.get("var");

                if (var instanceof String) {
                    if (gx.a((String) var)) {
                        Object listener = customEvent.get("listener");

                        if (listener instanceof String) {
                            if (!list.contains((String) listener)) {
                                list.add((String) listener);
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid listener data type in Custom Event #" + (i + 1));
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Found invalid var data type in Custom Event #" + (i + 1));
                }
            } else {
                SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
            }
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
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object listener = customEvent.get("listener");

                        if (listener instanceof String) {
                            if (name.equals(listener)) {
                                Object eventName = customEvent.get("name");

                                if (eventName instanceof String) {
                                    list.add((String) eventName);
                                } else {
                                    SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid listener data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }
                break;
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
                return R.drawable.widget_source;

            case " onLongClick":
                return R.drawable.check_upload_apk_48dp;

            case "onSwipeRefreshLayout":
                return R.drawable.widget_swipe_refresh;

            case "onPreExecute":
                return R.drawable.event_on_stop_tracking_touch_48dp;

            case "doInBackground":
                return R.drawable.event_on_animation_start_48dp;

            case "onProgressUpdate":
                return R.drawable.event_on_page_started_48dp;

            case "onPostExecute":
                return R.drawable.event_on_progress_changed_48dp;

            default:
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object icon = customEvent.get("icon");

                                if (icon instanceof String) {
                                    try {
                                        return OldResourceIdMapper.getDrawableFromOldResourceId(Integer.parseInt((String) icon));
                                    } catch (NumberFormatException e) {
                                        SketchwareUtil.toastError("Found invalid icon data type in Custom Event #" + (i + 1));
                                        return R.drawable.android_icon;
                                    }
                                } else {
                                    SketchwareUtil.toastError("Found invalid icon data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }

                return R.drawable.android_icon;
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
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object description = customEvent.get("description");

                                if (description instanceof String) {
                                    return (String) description;
                                } else {
                                    SketchwareUtil.toastError("Found invalid description data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }

                return "No_Description";
        }
    }

    public static String getEventCode(String targetId, String name, String param) {
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
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object code = customEvent.get("code");

                                if (code instanceof String) {
                                    return String.format(((String) code).replace("###", targetId), param);
                                } else {
                                    SketchwareUtil.toastError("Found invalid code data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }

                return "//no code";
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
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object parameters = customEvent.get("parameters");

                                if (parameters instanceof String) {
                                    return (String) parameters;
                                } else {
                                    SketchwareUtil.toastError("Found invalid parameters data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }

                return "";
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
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (event.equals(eventName)) {
                                Object headerSpec = customEvent.get("headerSpec");

                                if (headerSpec instanceof String) {
                                    return ((String) headerSpec).replace("###", name);
                                } else {
                                    SketchwareUtil.toastError("Found invalid header spec data type in Custom Event #" + (i + 1));
                                }
                            }
                        } else {
                            SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid (null) Custom Event at position " + i);
                    }
                }

                return "no spec";
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
                for (int i = 0, cachedCustomListenersSize = cachedCustomListeners.size(); i < cachedCustomListenersSize; i++) {
                    HashMap<String, Object> customListener = cachedCustomListeners.get(i);
                    Object eventName = customListener.get("name");

                    if (eventName instanceof String) {
                        if (name.equals(eventName)) {
                            Object code = customListener.get("code");

                            if (code instanceof String) {
                                return String.format(((String) code).replace("###", var), param);
                            } else {
                                SketchwareUtil.toastError("Found invalid code data type in Custom Event #" + (i + 1));
                            }
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                    }
                }

                return "//no listener code";
        }
    }

    public static void getImports(ArrayList<String> list, String name) {
        for (int i = 0, cachedCustomListenersSize = cachedCustomListeners.size(); i < cachedCustomListenersSize; i++) {
            HashMap<String, Object> customEvent = cachedCustomListeners.get(i);
            Object eventName = customEvent.get("name");

            if (eventName instanceof String) {
                if (name.equals(eventName)) {
                    Object imports = customEvent.get("imports");

                    if (imports instanceof String) {
                        if (!imports.equals("")) {
                            list.addAll(new ArrayList<>(Arrays.asList(((String) imports).split("\n"))));
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid import data type in Custom Event #" + (i + 1));
                    }
                }
            } else {
                SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
            }
        }
    }

    public static void refreshCachedCustomEvents() {
        cachedCustomEvents = readCustomEvents();
    }

    public static void refreshCachedCustomListeners() {
        cachedCustomListeners = readCustomListeners();
    }

    private static ArrayList<HashMap<String, Object>> readCustomEvents() {
        ArrayList<HashMap<String, Object>> customEvents = new ArrayList<>();

        if (FileUtil.isExistFile(CUSTOM_EVENTS_FILE_PATH)) {
            String customEventsContent = FileUtil.readFile(CUSTOM_EVENTS_FILE_PATH);

            if (!customEventsContent.equals("") && !customEventsContent.equals("[]")) {
                try {
                    customEvents = new Gson().fromJson(customEventsContent, Helper.TYPE_MAP_LIST);

                    if (customEvents == null) {
                        LogUtil.e("EventsHandler", "Failed to parse Custom Events file! Now using none");
                        customEvents = new ArrayList<>();
                    }
                } catch (JsonParseException e) {
                    LogUtil.e("EventsHandler", "Failed to parse Custom Events file! Now using none");
                }
            }
        }

        return customEvents;
    }

    private static ArrayList<HashMap<String, Object>> readCustomListeners() {
        ArrayList<HashMap<String, Object>> customListeners = new ArrayList<>();

        if (FileUtil.isExistFile(CUSTOM_LISTENERE_FILE_PATH)) {
            String customListenersContent = FileUtil.readFile(CUSTOM_LISTENERE_FILE_PATH);

            if (!customListenersContent.equals("") && !customListenersContent.equals("[]")) {
                try {
                    customListeners = new Gson().fromJson(customListenersContent, Helper.TYPE_MAP_LIST);

                    if (customListeners == null) {
                        LogUtil.e("EventsHandler", "Failed to parse Custom Listeners file! Now using none");
                        customListeners = new ArrayList<>();
                    }
                } catch (JsonParseException e) {
                    LogUtil.e("EventsHandler", "Failed to parse Custom Listeners file! Now using none");
                }
            }
        }

        return customListeners;
    }
}
