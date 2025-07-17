package dev.aldi.sayuti.editor.manage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class DependencyHistoryManager {
    private static final String PREFS_NAME = "dependency_history";
    private static final String KEY_DEPENDENCIES = "downloaded_dependencies";
    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    public DependencyHistoryManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public List<String> getHistory() {
        String json = prefs.getString(KEY_DEPENDENCIES, "[]");
        Type type = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void addDependency(String dependency) {
        List<String> history = getHistory();
        if (!history.contains(dependency)) {
            history.add(0, dependency);
            if (history.size() > 50) {
                history = history.subList(0, 50);
            }
            saveHistory(history);
        }
    }

    public void removeDependency(String dependency) {
        List<String> history = getHistory();
        history.remove(dependency);
        saveHistory(history);
    }

    private void saveHistory(List<String> history) {
        String json = gson.toJson(history);
        prefs.edit().putString(KEY_DEPENDENCIES, json).apply();
    }
}