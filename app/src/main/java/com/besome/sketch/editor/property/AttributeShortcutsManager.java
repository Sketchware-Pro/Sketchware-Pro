package com.besome.sketch.editor.property;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.utility.FileUtil;

public class AttributeShortcutsManager {
    private static final String FILE_NAME = "attribute_shortcuts.json";
    private final String filePath;
    private final Gson gson;
    private List<ShortcutItem> shortcuts;

    public AttributeShortcutsManager() {
        filePath = FileUtil.getExternalStorageDir() + "/.sketchware/resources/" + FILE_NAME;
        gson = new Gson();
        load();
    }

    private void load() {
        if (FileUtil.isExistFile(filePath)) {
            String content = FileUtil.readFile(filePath);
            shortcuts = gson.fromJson(content, new TypeToken<List<ShortcutItem>>() {
            }.getType());
        }
        if (shortcuts == null) {
            shortcuts = new ArrayList<>();
        }
    }

    public void save() {
        FileUtil.writeFile(filePath, gson.toJson(shortcuts));
    }

    public List<ShortcutItem> getShortcuts() {
        return shortcuts;
    }

    public void addShortcut(String name, String value) {
        for (ShortcutItem item : shortcuts) {
            if (item.name.equals(name)) {
                item.value = value;
                save();
                return;
            }
        }
        shortcuts.add(new ShortcutItem(name, value));
        save();
    }

    public void removeShortcut(String name) {
        if (shortcuts.removeIf(item -> item.name.equals(name))) {
            save();
        }
    }

    public static class ShortcutItem {
        public String name;
        public String value;

        public ShortcutItem(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
