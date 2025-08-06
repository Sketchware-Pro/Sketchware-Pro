package mod.hilal.saif.events;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a.a.a.Gx;
import a.a.a.oq;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import mod.jbk.util.OldResourceIdMapper;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

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
        return switch (name) {
            case "Import", "onActivityResult", "initializeLogic", "onBackPressed", "onPostCreate",
                 "onStart", "onResume", "onPause", "onStop", "onDestroy",
                 "onTabLayoutNewTabAdded" -> R.drawable.ic_mtrl_code;
            case " onLongClick" -> R.drawable.ic_mtrl_touch_long;
            case "onSwipeRefreshLayout" -> R.drawable.ic_mtrl_refresh;
            case "onPreExecute" -> R.drawable.ic_mtrl_track_started;
            case "doInBackground" -> R.drawable.ic_mtrl_sprint;
            case "onProgressUpdate" -> R.drawable.ic_mtrl_progress;
            case "onPostExecute" -> R.drawable.ic_mtrl_progress_check;
            default -> {
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object icon = customEvent.get("icon");

                                if (icon instanceof String) {
                                    try {
                                        yield OldResourceIdMapper.getDrawableFromOldResourceId(Integer.parseInt((String) icon));
                                    } catch (NumberFormatException e) {
                                        SketchwareUtil.toastError("Found invalid icon data type in Custom Event #" + (i + 1));
                                        yield R.drawable.android_icon;
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

                yield R.drawable.android_icon;
            }
        };
    }

    public static String getDesc(String name) {
        return switch (name) {
            case "Import" -> "add custom imports";
            case "onActivityResult" -> "onActivityResult";
            case "initializeLogic" -> "initializeLogic";
            case "onSwipeRefreshLayout" -> "On SwipeRefreshLayout swipe";
            case " onLongClick" -> "onLongClick";
            case "onTabLayoutNewTabAdded" -> "return the name of current tab";
            case "onPreExecute" ->
                    "This method contains the code which is executed before the background processing starts.";
            case "doInBackground" ->
                    "This method contains the code which needs to be executed in background.";
            case "onProgressUpdate" ->
                    "This method receives progress updates from doInBackground method.";
            case "onPostExecute" ->
                    "This method is called after doInBackground method completes processing.";
            default -> {
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object description = customEvent.get("description");

                                if (description instanceof String) {
                                    yield (String) description;
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

                yield "No_Description";
            }
        };
    }

    public static String getEventCode(String targetId, String name, String param) {
        return switch (name) {
            case "Import" ->
                // Changed from: "...vF\n${param}\n//3b..."
                    "//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF\r\n" +
                            param + "\r\n" +
                            "//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ";
            case "onActivityResult", "initializeLogic" -> "";
            case "onSwipeRefreshLayout" ->
                // Changed from: "@Override \npublic void..."
                    "@Override\r\n" +
                            "public void onRefresh() {\n" +
                            param + "\r\n" +
                            "}";
            case " onLongClick" ->
                // Changed from: "@Override\r\n public boolean..."
                    "@Override\r\n" +
                            "public boolean onLongClick(View _view) {\r\n" +
                            param + "\r\n" +
                            "return true;\r\n" +
                            "}";
            case "onTabLayoutNewTabAdded" ->
                // Changed from: "public  CharSequence  onTabLayoutNewTabAdded( int   _position ){..."
                    "public CharSequence onTabLayoutNewTabAdded(int _position) {\r\n" +
                            (param.isEmpty() ? "return \"\";\r\n" :
                                    param + "\r\n"
                            ) + "}";
            case "onPreExecute" -> "@Override\r\n" +
                    "protected void onPreExecute() {\r\n" +
                    param + "\r\n" +
                    "}";
            case "doInBackground" -> "@Override\r\n" +
                    "protected String doInBackground(String... params) {\r\n" +
                    "String _param = params[0];\r\n" +
                    param + "\r\n" +
                    "}";
            case "onProgressUpdate" -> "@Override\r\n" +
                    "protected void onProgressUpdate(Integer... values) {\r\n" +
                    "int _value = values[0];\r\n" +
                    param + "\r\n" +
                    "}";
            case "onPostExecute" -> "@Override\r\n" +
                    "protected void onPostExecute(String _result) {\r\n" +
                    param + "\r\n" +
                    "}";
            default -> {
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object code = customEvent.get("code");

                                if (code instanceof String) {
                                    yield String.format(((String) code).replace("###", targetId), param);
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

                yield "//no code";
            }
        };
    }

    public static String getBlocks(String name) {
        return switch (name) {
            case "Import", "initializeLogic", "onSwipeRefreshLayout", " onLongClick",
                 "onPreExecute" -> "";
            case "onActivityResult" -> "%d.requestCode %d.resultCode %m.intent";
            case "onTabLayoutNewTabAdded", "onProgressUpdate" -> "%d";
            case "doInBackground", "onPostExecute" -> "%s";
            default -> {
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (name.equals(eventName)) {
                                Object parameters = customEvent.get("parameters");

                                if (parameters instanceof String) {
                                    yield (String) parameters;
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

                yield "";
            }
        };
    }

    public static String getSpec(String name, String event) {
        return switch (event) {
            case "Import" -> "create new import";
            case "onActivityResult" ->
                    "OnActivityResult %d.requestCode %d.resultCode %m.intent.data";
            case "initializeLogic" -> "initializeLogic";
            case "onSwipeRefreshLayout" -> "when " + name + " refresh";
            case " onLongClick" -> "when " + name + " long clicked";
            case "onTabLayoutNewTabAdded" -> name + " return tab title %d.position";
            case "onPreExecute" -> name + " onPreExecute ";
            case "doInBackground" -> name + " doInBackground %s.param";
            case "onProgressUpdate" -> name + " onProgressUpdate progress %d.value";
            case "onPostExecute" -> name + " onPostExecute result %s.result";
            default -> {
                for (int i = 0, cachedCustomEventsSize = cachedCustomEvents.size(); i < cachedCustomEventsSize; i++) {
                    HashMap<String, Object> customEvent = cachedCustomEvents.get(i);
                    if (customEvent != null) {
                        Object eventName = customEvent.get("name");

                        if (eventName instanceof String) {
                            if (event.equals(eventName)) {
                                Object headerSpec = customEvent.get("headerSpec");

                                if (headerSpec instanceof String) {
                                    yield ((String) headerSpec).replace("###", name);
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

                yield "no spec";
            }
        };
    }

    /// listeners codes
    public static String getListenerCode(String name, String var, String param) {
        return switch (name) {
            case " onLongClickListener" ->
                    var + ".setOnLongClickListener(new View.OnLongClickListener() {\r\n" +
                            param + "\r\n" +
                            "});";
            case "onSwipeRefreshLayoutListener" ->
                    var + ".setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {\r\n" +
                            param + "\r\n" +
                            "});";
            case "AsyncTaskClass" ->
                    "private class " + var + " extends AsyncTask<String, Integer, String> {\r\n" +
                            param + "\r\n" +
                            "}";
            default -> {
                for (int i = 0, cachedCustomListenersSize = cachedCustomListeners.size(); i < cachedCustomListenersSize; i++) {
                    HashMap<String, Object> customListener = cachedCustomListeners.get(i);
                    Object eventName = customListener.get("name");

                    if (eventName instanceof String) {
                        if (name.equals(eventName)) {
                            Object code = customListener.get("code");

                            if (code instanceof String) {
                                yield String.format(((String) code).replace("###", var), param);
                            } else {
                                SketchwareUtil.toastError("Found invalid code data type in Custom Event #" + (i + 1));
                            }
                        }
                    } else {
                        SketchwareUtil.toastError("Found invalid name data type in Custom Event #" + (i + 1));
                    }
                }

                yield "//no listener code";
            }
        };
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

            if (!customEventsContent.isEmpty() && !customEventsContent.equals("[]")) {
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

            if (!customListenersContent.isEmpty() && !customListenersContent.equals("[]")) {
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
