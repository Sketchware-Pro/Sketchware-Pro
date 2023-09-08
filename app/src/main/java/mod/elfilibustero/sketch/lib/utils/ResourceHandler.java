package mod.elfilibustero.sketch.lib.utils;

import a.a.a.jC;
import a.a.a.XmlBuilderHelper;
import a.a.a.yq;

import android.content.Context;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mod.agus.jcoderz.lib.FileUtil;
import mod.elfilibustero.sketch.beans.ResourceXmlBean;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;

public class ResourceHandler {
    private Context context;
    private String injectResourceDir;
    private String sc_id;
    private yq sourceCodeMaker;

    public ResourceHandler(Context context, String sc_id) {
        this.context = context;
        this.sc_id = sc_id;
        this.sourceCodeMaker = new yq(context, sc_id);
        injectResourceDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/injection/resource/";
    }

    public List<Map<String, Object>> parseResourceFile(String file) {
        List<Map<String, Object>> list = new ArrayList<>();

        String path = injectResourceDir + file;
        if (!FileUtil.isExistFile(path)) {
            return list;
        }
        String content = FileUtil.readFile(path);
        if (Helper.isJson(content)) {
            list = new Gson().fromJson(content, Helper.TYPE_GENERIC_MAP_LIST);
        }
        return list;
    }

    public String getXmlString() {
        XmlBuilderHelper stringsFileBuilder = new XmlBuilderHelper();
        List<Map<String, Object>> strings = parseResourceFile(ResourceXmlBean.getResFileName(ResourceXmlBean.RES_TYPE_STRING));
        if (strings == null || strings.isEmpty()) {
            strings = getDefaultString();
        }
        for (Map<String, Object> string : strings) {
            if (string.containsKey("name") && string.containsKey("value")) {
                String name = (String) string.get("name");
                String value = (String) string.get("value");
                if (string.containsKey("translatable")) {
                    stringsFileBuilder.addString(name, value, (boolean) string.get("translatable"));
                }
            }
        }
        return stringsFileBuilder.toCode();
    }

    public String getXmlColor() {
        XmlBuilderHelper colorsFileBuilder = new XmlBuilderHelper();
        List<Map<String, Object>> colors = parseResourceFile(ResourceXmlBean.getResFileName(ResourceXmlBean.RES_TYPE_COLOR));
        if (colors == null || colors.isEmpty()) {
            colors = getDefaultColor();
        }
        for (Map<String, Object> color : colors) {
            if (color.containsKey("name") && color.containsKey("value")) {
                String name = (String) color.get("name");
                String value = (String) color.get("value");
                colorsFileBuilder.addColor(name, value);
            }
        }
        return colorsFileBuilder.toCode();
    }

    public String getXmlStyle() {
        XmlBuilderHelper stylesFileBuilder = new XmlBuilderHelper();
        List<Map<String, Object>> styles = parseResourceFile(ResourceXmlBean.getResFileName(ResourceXmlBean.RES_TYPE_STYLE));
        if (styles == null || styles.isEmpty()) {
            styles = getDefaultStyle();
        }
        for (Map<String, Object> style : styles) {
            if (style.containsKey("name") && style.containsKey("parent")) {
                String name = (String) style.get("name");
                String parent = (String) style.get("parent");
                stylesFileBuilder.addStyle(name, parent);
                if (style.containsKey("items")) {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) style.get("items");
                    for (Map<String, Object> item : items) {
                        String itemName = (String) item.get("name");
                        String itemValue = (String) item.get("value");
                        stylesFileBuilder.addItemToStyle(name, itemName, itemValue);
                    }
                }
            }
        }
        return stylesFileBuilder.toCode();
    }

    public List<Map<String, Object>> getDefaultColor() {
        List<Map<String, Object>> colors = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("name", "colorAccent");
        item.put("value", formatColor(sourceCodeMaker.colorAccent));
        colors.add(item);
        item = new HashMap<>();
        item.put("name", "colorPrimary");
        item.put("value", formatColor(sourceCodeMaker.colorPrimary));
        colors.add(item);
        item = new HashMap<>();
        item.put("name", "colorPrimaryDark");
        item.put("value", formatColor(sourceCodeMaker.colorPrimaryDark));
        colors.add(item);
        item = new HashMap<>();
        item.put("name", "colorControlHighlight");
        item.put("value", formatColor(sourceCodeMaker.colorControlHighlight));
        colors.add(item);
        item = new HashMap<>();
        item.put("name", "colorControlNormal");
        item.put("value", formatColor(sourceCodeMaker.colorControlNormal));
        colors.add(item);
        return colors;
    }

    public List<Map<String, Object>> getDefaultString() {
        List<Map<String, Object>> strings = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("name", "app_name");
        item.put("value", sourceCodeMaker.applicationName);
        item.put("translatable", false);
        strings.add(item);
        return strings;
    }

    public List<Map<String, Object>> getDefaultStyle() {
        List<Map<String, Object>> styles = new ArrayList<>();
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> style;
        Map<String, Object> item;
        ProjectLibraryBean appCompat = jC.c(sc_id).c();
        if (appCompat.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            ProjectSettings projectSettings = new ProjectSettings(sc_id);
            boolean useNewMaterialComponentsTheme = projectSettings.getValue(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE);
            style = new HashMap<>();
            style.put("name", "AppTheme");
            style.put("parent", "Theme.MaterialComponents.Light.NoActionBar" + (useNewMaterialComponentsTheme ? "" : ".Bridge"));

            items = new ArrayList<>();
            item = new HashMap<>();
            item.put("name", "colorPrimary");
            item.put("value", "@color/colorPrimary");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "colorPrimaryDark");
            item.put("value", "@color/colorPrimaryDark");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "colorAccent");
            item.put("value", "@color/colorAccent");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "colorControlHighlight");
            item.put("value", "@color/colorControlHighlight");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "colorControlNormal");
            item.put("value", "@color/colorControlNormal");
            items.add(item);

            style.put("items", items);
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "AppTheme.FullScreen");
            style.put("parent", "AppTheme");

            items = new ArrayList<>();
            item = new HashMap<>();
            item.put("name", "android:windowFullscreen");
            item.put("value", "true");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "android:windowContentOverlay");
            item.put("value", "@null");
            items.add(item);

            style.put("items", items);
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "AppTheme.AppBarOverlay");
            style.put("parent", "ThemeOverlay.MaterialComponents.Dark.ActionBar");

            style.put("items", new ArrayList<>());
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "AppTheme.PopupOverlay");
            style.put("parent", "ThemeOverlay.MaterialComponents.Light");

            style.put("items", new ArrayList<>());
            styles.add(style);
        } else {
            style = new HashMap<>();
            style.put("name", "AppTheme");
            style.put("parent", "@android:style/Theme.Material.Light.DarkActionBar");

            item = new HashMap<>();
            item.put("name", "android:colorPrimary");
            item.put("value", "@color/colorPrimary");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "android:colorPrimaryDark");
            item.put("value", "@color/colorPrimaryDark");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "android:colorAccent");
            item.put("value", "@color/colorAccent");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "android:colorControlHighlight");
            item.put("value", "@color/colorControlHighlight");
            items.add(item);

            item = new HashMap<>();
            item.put("name", "android:colorControlNormal");
            item.put("value", "@color/colorControlNormal");
            items.add(item);

            style.put("items", items);
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "FullScreen");
            style.put("parent", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");

            style.put("items", items);
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "NoActionBar");
            style.put("parent", "@android:style/Theme.Material.Light.NoActionBar");

            style.put("items", items);
            styles.add(style);

            style = new HashMap<>();
            style.put("name", "NoStatusBar");
            style.put("parent", "AppTheme");

            items = new ArrayList<>();
            item = new HashMap<>();
            item.put("name", "android:windowFullscreen");
            item.put("value", "true");
            items.add(item);

            style.put("items", items);
            styles.add(style);
        }
        return styles;
    }

    private static String formatColor(int color) {
        return String.format("#%08X", color & 0xFFFFFFFF);
    }
}
