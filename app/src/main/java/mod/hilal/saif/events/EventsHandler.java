package mod.hilal.saif.events;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import a.a.a.Gx;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class EventsHandler {

    // Array of Event Activities
    // .sketchware/data/system/activity_events.json
    public static String[] getActivityEvents() {
        String path = getPath("events");
        ArrayList<String> array = new ArrayList<>();
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
			/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			for(int i= 0; i < (int)(data.size()); i++) {
				String ss = (String)data.get(i).get("var");
				if(ss.equals("")){
					array.add((String)data.get(i).get("name"));
				}
			}*/

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
            array.add("Import");
            ///array.add("AndroidManifest");

            Collections.reverse(array);

            return array.toArray(new String[0]);
        } catch (Exception e) {
            return new String[]{"Import", "onBackPressed", "onPostCreate", "onStart", "onResume",
                    "onPause", "onStop", "onDestroy", "onSaveInstanceState", "onRestoreInstanceState",
                    "onCreateOptionsMenu", "onOptionsItemSelected", "onCreateContextMenu",
                    "onContextItemSelected", "onTabLayoutNewTabAdded"};
        }
    }

    // Add Events, widgets and components
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
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					if(gx.a((String)data.get(i).get("var"))){
						list.add(data.get(i).get("name"));
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        if (gx.a(arr.getJSONObject(i).getString("var"))) {
                            list.add(arr.getJSONObject(i).getString("name"));
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    //// add listeners to widgets and Components
    public static void addListeners(Gx gx, ArrayList<String> list) {
        String path = getPath("events");
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data;
        if (gx.a("Clickable")) {
            temp.add(" onLongClickListener");
        }
        if (gx.a("SwipeRefreshLayout")) {
            temp.add("onSwipeRefreshLayoutListener");
        }
        if (gx.a("AsyncTask")) {
            temp.add("AsyncTaskClass");
        }

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                final int len = data.size();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        if (gx.a((String) data.get(i).get("var"))) {
                            if (!temp.contains((String) data.get(i).get("listener"))) {
                                temp.add((String) data.get(i).get("listener"));
                            }
                        }
                    }
                }
				/*
				JSONArray arr = new JSONArray (FileUtil.readFile(path));
				for(int i= 0; i < (int)(arr.length()); i++) {
					if(gx.a(arr.getJSONObject(i).getString("var"))){
						if(!temp.contains(arr.getJSONObject(i).getString("listener"))){
							list.add(arr.getJSONObject(i).getString("listener"));
						}
					}
				}*/
            }
            for (int i = 0; i < (int) (temp.size()); i++) {
                list.add(temp.get(i));
            }
        } catch (Exception ignored) {
        }
    }

    public static void addEventsToListener(String name, ArrayList<String> list) {
        if (name.equals(" onLongClickListener")) {
            list.add(" onLongClick");
        }
        if (name.equals("onSwipeRefreshLayoutListener")) {
            list.add("onSwipeRefreshLayout");
        }
        if (name.equals("AsyncTaskClass")) {
            list.add("onPreExecute");
            list.add("doInBackground");
            list.add("onProgressUpdate");
            list.add("onPostExecute");
        }
        String path = getPath("events");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("listener");
					if(name.equals(c)){
						list.add(data.get(i).get("name"));
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("listener");
                        if (name.equals(c)) {
                            list.add(arr.getJSONObject(i).getString("name"));
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    // Events' properties
    public static int getIcon(String name) {
        if (name.equals("Import")) {
            return 2131166270;
        }

        if (name.equals(" onLongClick")) {
            return 2131165408;
        }

        if (name.equals("onBackPressed")) {
            return 2131166270;
        }
        if (name.equals("onPostCreate")) {
            return 2131166270;
        }
        if (name.equals("onStart")) {
            return 2131166270;
        }
        if (name.equals("onResume")) {
            return 2131166270;
        }
        if (name.equals("onPause")) {
            return 2131166270;
        }
        if (name.equals("onStop")) {
            return 2131166270;
        }
        if (name.equals("onDestroy")) {
            return 2131166270;
        }
        if (name.equals("onSwipeRefreshLayout")) {
            return 2131166320;
        }
        if (name.equals("onPreExecute")) {
            return 2131165600;
        }
        if (name.equals("doInBackground")) {
            return 2131165557;
        }
        if (name.equals("onProgressUpdate")) {
            return 2131165588;
        }
        if (name.equals("onPostExecute")) {
            return 2131165591;
        }
        if (name.equals("onTabLayoutNewTabAdded")) {
            return 2131166270;
        }

        String path = getPath("events");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
			/*if(FileUtil.isExistFile(path)){
				data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(name.equals(c)){
						return Integer.valueOf((String)data.get(i).get("icon"));
					}}} return 2131165312 ;*/

            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            return Integer.parseInt(arr.getJSONObject(i).getString("icon"));
                        }
                    }
                }
            }
            return 2131165312;
        } catch (Exception e) {
            return 2131165312;
        }
    }

    public static String getDesc(String name) {
        if (name.equals("Import")) {
            return "add custom imports";
        }
        if (name.equals("onSwipeRefreshLayout")) {
            return "On SwipeRefreshLayout swipe";
        }

        if (name.equals(" onLongClick")) {
            return "onLongClick";
        }

        if (name.equals("onTabLayoutNewTabAdded")) {
            return "return the name of current tab";
        }

        if (name.equals("onPreExecute")) {
            return "This method contains the code which is executed before the background processing starts.";
        }
        if (name.equals("doInBackground")) {
            return "This method contains the code which needs to be executed in background.";
        }
        if (name.equals("onProgressUpdate")) {
            return "This method receives progress updates from doInBackground method.";
        }
        if (name.equals("onPostExecute")) {
            return "This method is called after doInBackground method completes processing.";
        }
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            return arr.getJSONObject(i).getString("description");
                        }
                    }
                }
            }
            return "No_Description";
        } catch (Exception e) {
            return "description error";
        }
    }

    public static String getEventCode(String name, String param) {
        if (name.equals("Import")) {
            return j("//Ul5kmZqmO867OV0QTGOpjwX7MXmgzxzQBSZTf0Y16PnDXkhLsZfvF\n%s\n//3b5IqsVG57gNqLi7FBO2MeOW6iI7tOustUGwcA7HKXm0o7lovZ", param);
        }

        if (name.equals("onSwipeRefreshLayout")) {
            return j("@Override \npublic void onRefresh() {\n%s\n}", param);
        }

        if (name.equals(" onLongClick")) {
            return j("@Override\r\n	public boolean onLongClick(View _view) {\r\n%s\r\nreturn true;\r\n	}", param);
        }

        if (name.equals("onTabLayoutNewTabAdded")) {
            return j("public  CharSequence  onTabLayoutNewTabAdded( int   _position ){\r\n%s\r\n\r\nreturn null;\r\n}", param);
        }

        if (name.equals("onPreExecute")) {
            return j("@Override\r\nprotected void onPreExecute() {\r\n%s\r\n}", param);
        }
        if (name.equals("doInBackground")) {
            return j("@Override\r\nprotected String doInBackground(String... params) {\r\nString _param = params[0];\r\n%s\r\n}", param);
        }
        if (name.equals("onProgressUpdate")) {
            return j("@Override\r\nprotected void onProgressUpdate(Integer... values) {\r\nint _value = values[0];\r\n%s\r\n}", param);
        }
        if (name.equals("onPostExecute")) {
            return j("@Override\r\nprotected void onPostExecute(String _result) {\r\n%s\r\n}", param);
        }
        String path = getPath("events");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(name.equals(c)){
						return j((String)data.get(i).get("code"),param);
					}
				}*/
                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            return j(arr.getJSONObject(i).getString("code"), param);
                        }
                    }
                }
            }
            return j("//no code", param);
        } catch (Exception e) {
            return j("//code error", param);
        }
    }

    public static String getBlocks(String name) {
        if (name.equals("Import")) {
            return "";
        }
        if (name.equals("onSwipeRefreshLayout")) {
            return "";
        }
        if (name.equals(" onLongClick")) {
            return "";
        }
        if (name.equals("onTabLayoutNewTabAdded")) {
            return "%d";
        }
        if (name.equals("onPreExecute")) {
            return "";
        }
        if (name.equals("doInBackground")) {
            return "%s";
        }
        if (name.equals("onProgressUpdate")) {
            return "%d";
        }
        if (name.equals("onPostExecute")) {
            return "%s";
        }

        String path = getPath("events");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(name.equals(c)){
						return (String)data.get(i).get("parameters");
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            return arr.getJSONObject(i).getString("parameters");
                        }
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSpec(String name, String event) {
        if (event.equals("Import")) {
            return "create new import";
        }

        if (event.equals("onSwipeRefreshLayout")) {
            return "when " + name + " refresh";
        }
        if (event.equals(" onLongClick")) {
            return "when " + name + " long clicked";
        }
        if (event.equals("onTabLayoutNewTabAdded")) {
            return name + " return tab title %d.position";
        }
        if (event.equals("onPreExecute")) {
            return name + " onPreExecute ";
        }
        if (event.equals("doInBackground")) {
            return name + " doInBackground %s.param";
        }
        if (event.equals("onProgressUpdate")) {
            return name + " onProgressUpdate progress %d.value";
        }
        if (event.equals("onPostExecute")) {
            return name + " onPostExecute result %s.result";
        }


        String path = getPath("events");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(event.equals(c)){
						return ((String)data.get(i).get("headerSpec")).replace("###",name);
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (event.equals(c)) {
                            return arr.getJSONObject(i).getString("headerSpec").replace("###", name);
                        }
                    }
                }
            }
            return "no spec";
        } catch (Exception e) {
            return "spec error";
        }
    }

    ///listeners codes
    public static String getListenerCode(String name, String var, String param) {
        if (name.equals(" onLongClickListener")) {
            return j(var + ".setOnLongClickListener(new View.OnLongClickListener() {\r\n %s\r\n });", param);
        }
        if (name.equals("onSwipeRefreshLayoutListener")) {
            return j(var + ".setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {\r\n%s\r\n});", param);
        }

        if (name.equals("AsyncTaskClass")) {
            return j("private class " + var + " extends AsyncTask<String, Integer, String> {\r\n%s\r\n}", param);
        }
        String path = getPath("listeners");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(name.equals(c)){
						return j(((String)data.get(i).get("code")).replace("###",var),param);
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            return j(arr.getJSONObject(i).getString("code").replace("###", var), param);
                        }
                    }
                }
            }
            return j("//no listener code", param);
        } catch (Exception e) {
            return j("//code listener error", param);
        }
    }

    public static void getImports(ArrayList<String> list, String name) {
        String path = getPath("listeners");
        //ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        try {
            if (FileUtil.isExistFile(path) && !FileUtil.readFile(path).equals("") && !FileUtil.readFile(path).equals("[]")) {
				/*data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				for(int i= 0; i < (int)(data.size()); i++) {
					String c = (String)data.get(i).get("name");
					if(name.equals(c)){
						if (!((String)data.get(i).get("imports")).equals("")){
							ArrayList<String> temp = new ArrayList<String>(Arrays.asList(((String)data.get(i).get("imports")).split("\n")));
							for(int k= 0; k < (int)(temp.size()); k++) {
								list.add(temp.get(k));
							}
						}
					}
				}*/

                JSONArray arr = new JSONArray(FileUtil.readFile(path));
                final int len = arr.length();
                if (len > 0) {
                    for (int i = 0; i < (int) (len); i++) {
                        String c = arr.getJSONObject(i).getString("name");
                        if (name.equals(c)) {
                            if (!arr.getJSONObject(i).getString("imports").equals("")) {
                                ArrayList<String> temp = new ArrayList<>(Arrays.asList(arr.getJSONObject(i).getString("imports").split("\n")));
                                for (int k = 0; k < (int) (temp.size()); k++) {
                                    list.add(temp.get(k));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static String j(String code, String param) {
        return !code.isEmpty() ? String.format(code, param) : "";
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
