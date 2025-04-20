package mod.hilal.saif.components;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;

import com.besome.sketch.beans.ComponentBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import a.a.a.Lx;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import mod.jbk.util.OldResourceIdMapper;
import pro.sketchware.R;
import pro.sketchware.SketchApplication;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
//responsible code :
//ComponentBean == sketchware / beans √
//Manage components == agus /component √
//Manage events components == agus/editor/event √
//TypeVarComponent == agus / lib √
//TypeClassComponent == agus/lib √
//importClass== dev.aldi.sayuti.editor.manage

public class ComponentsHandler {

    private static ArrayList<HashMap<String, Object>> cachedCustomComponents = readCustomComponents();

    /**
     * This is a utility class, don't instantiate it
     */
    private ComponentsHandler() {
    }

    /**
     * Called at {@link ComponentBean#getComponentTypeByTypeName(String)}.
     */
    //√ give typeName and return id
    public static int id(final String name) {
        if (name.equals("AsyncTask")) {
            return 36;
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object typeName = component.get("typeName");

                if (typeName instanceof String) {
                    if (name.equals(typeName)) {
                        Object id = component.get("id");

                        if (id instanceof String) {
                            try {
                                return Integer.parseInt((String) id);
                            } catch (NumberFormatException e) {
                                SketchwareUtil.toastError("Invalid ID entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        } else {
                            SketchwareUtil.toastError("Invalid ID entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid type name entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return -1;
    }

    /**
     * Called at {@link ComponentBean#getComponentTypeName(int)}.
     */
    // √ give id and return typeName
    public static String typeName(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        int idInt = Integer.parseInt((String) componentId);

                        if (idInt == id) {
                            Object componentTypeName = component.get("typeName");

                            if (componentTypeName instanceof String) {
                                return (String) componentTypeName;
                            } else {
                                SketchwareUtil.toastError("Invalid type name entry at Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry at Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry at Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "";
    }

    /**
     * Called at {@link ComponentBean#getComponentName(Context, int)}.
     */
    //√ give id and return name
    public static String name(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        int idInt = Integer.parseInt((String) componentId);

                        if (idInt == id) {
                            Object componentName = component.get("name");

                            if (componentName instanceof String) {
                                return (String) componentName;
                            } else {
                                SketchwareUtil.toastError("Invalid name entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }
        return "component";
    }

    /**
     * Called at {@link ComponentBean#getIconResource(int)}.
     */
    //√ give id and return icon
    public static int icon(int id) {
        if (id == 36) {
            return R.drawable.ic_cycle_color_48dp;
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object idObject = component.get("id");

                if (idObject instanceof String) {
                    try {
                        int componentId = Integer.parseInt((String) idObject);

                        if (componentId == id) {
                            Object iconObject = component.get("icon");

                            if (iconObject instanceof String) {
                                try {
                                    return OldResourceIdMapper.getDrawableFromOldResourceId(Integer.parseInt((String) iconObject));
                                } catch (NumberFormatException e) {
                                    SketchwareUtil.toastError("Invalid icon entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                    break;
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return R.drawable.color_new_96;
    }

    /**
     * @return Descriptions of Components, both built-in ones and Custom Components'
     */
    // give id and return description
    //goto ComponentAddActivity
    //remove lines: 2303 to 2307
    //call this method using v0 as id and move result to v0
    public static String description(int id) {
        int componentBeanDescriptionResId = ComponentBean.getDescStrResource(id);
        if (componentBeanDescriptionResId != 0) {
            return xB.b().a(SketchApplication.getContext(), componentBeanDescriptionResId);
        } else {
            return description2(id);
        }
    }

    /**
     * @return Component description of a Custom Component
     */
    public static String description2(int id) {
        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        int idInt = Integer.parseInt((String) componentId);

                        if (idInt == id) {
                            Object componentDescription = component.get("description");

                            if (componentDescription instanceof String) {
                                return (String) component.get("description");
                            } else {
                                SketchwareUtil.toastError("Invalid description entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "new component";
    }

    /**
     * Called at {@link ComponentBean#getComponentDocsUrlByTypeName(int)}.
     */
    // √give id and return docs url
    public static String docs(int id) {
        if (id != 36) {
            for (int i = 0; i < cachedCustomComponents.size(); i++) {
                HashMap<String, Object> component = cachedCustomComponents.get(i);
                if (component != null) {
                    Object componentId = component.get("id");
                    if (componentId instanceof String) {
                        try {
                            int componentIdInteger = Integer.parseInt((String) componentId);

                            if (componentIdInteger == id) {
                                Object componentUrl = component.get("url");

                                if (componentUrl instanceof String) {
                                    return (String) componentUrl;
                                } else {
                                    SketchwareUtil.toastError("Invalid URL entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                        }
                    } else {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
                }
            }
        }

        return "";
    }

    /**
     * Called at {@link ComponentBean#buildClassInfo()}.
     */
    //√ give id and return buildclass
    public static String c(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        int componentIdInteger = Integer.parseInt((String) componentId);

                        if (componentIdInteger == id) {
                            Object componentBuildClass = component.get("buildClass");

                            if (componentBuildClass instanceof String) {
                                return (String) componentBuildClass;
                            } else {
                                SketchwareUtil.toastError("Invalid build class entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "";
    }

    // mod •••••••••••••••••••••••••••••••

    /**
     * Adds Custom Components to available Components section.
     * Used at {@link com.besome.sketch.editor.component.ComponentAddActivity#onPostCreate(Bundle)}.
     */
    // √ add components to sk
    //structure : list.add(new ComponentBean(27));
    public static void add(ArrayList<ComponentBean> list) {
        list.add(new ComponentBean(36));

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        list.add(new ComponentBean(Integer.parseInt((String) componentId)));
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }
    }

    //√√√ give id and return variable name
    public static String var(int id) {
        if (id == 36) {
            return "#";
        }

        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentId = component.get("id");

                if (componentId instanceof String) {
                    try {
                        int componentIdInteger = Integer.parseInt((String) componentId);

                        if (componentIdInteger == id) {
                            Object componentVarName = component.get("varName");

                            if (componentVarName instanceof String) {
                                return (String) componentVarName;
                            } else {
                                SketchwareUtil.toastError("Invalid variable name entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid ID entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "";
    }

    /**
     * @param name The desired Custom Component's <code>typeName</code>
     * @return A Custom Component's <code>class</code>
     */
    //√√ give typeName and return class
    public static String c(String name) {
        if (name.equals("AsyncTask")) {
            return "Component.AsyncTask";
        }

        for (int i = 0, customComponentsSize = cachedCustomComponents.size(); i < customComponentsSize; i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentTypeName = component.get("typeName");

                if (componentTypeName instanceof String) {
                    if (name.equals(componentTypeName)) {
                        Object componentClass = component.get("class");

                        if (componentClass instanceof String) {
                            return (String) componentClass;
                        } else {
                            SketchwareUtil.toastError("Invalid class entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                            break;
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid type name entry for Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "Component";
    }

    /**
     * Used at {@link Lx#a(String, String, Lx.AccessModifier, String...)}
     * to get Custom Components' fields.
     */
    //√√
    public static String extraVar(String name, String code, String varName) {
        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentName = component.get("name");

                if (componentName instanceof String) {
                    if (name.equals(componentName)) {
                        Object componentAdditionalVar = component.get("additionalVar");

                        if (componentAdditionalVar instanceof String) {
                            if (TextUtils.isEmpty(((String) componentAdditionalVar))) {
                                return code;
                            } else {
                                return code + "\r\n" +
                                        ((String) componentAdditionalVar).replace("###", varName);
                            }
                        } else {
                            SketchwareUtil.toastError("Invalid additional variable entry at Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid name entry at Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return code;
    }

    //√√
    public static String defineExtraVar(String name, String varName) {
        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentName = component.get("name");

                if (componentName instanceof String) {
                    if (name.equals(componentName)) {
                        Object componentDefineAdditionalVar = component.get("defineAdditionalVar");

                        if (componentDefineAdditionalVar instanceof String) {
                            if (TextUtils.isEmpty((String) componentDefineAdditionalVar)) {
                                break;
                            } else {
                                return ((String) componentDefineAdditionalVar).replace("###", varName);
                            }
                        } else {
                            SketchwareUtil.toastError("Invalid additional variable entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid name entry in Custom Component #" + (i + 1));
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }

        return "";
    }

    public static void getImports(String name, ArrayList<String> arrayList) {
        for (int i = 0; i < cachedCustomComponents.size(); i++) {
            HashMap<String, Object> component = cachedCustomComponents.get(i);
            if (component != null) {
                Object componentVarName = component.get("varName");

                if (componentVarName instanceof String) {
                    if (name.equals(componentVarName)) {
                        Object componentImports = component.get("imports");

                        if (componentImports instanceof String componentImportsString) {
                            String[] componentImportsArray = componentImportsString.split("\n");
                            arrayList.addAll(Arrays.asList(componentImportsArray));
                        } else {
                            SketchwareUtil.toastError("Invalid imports entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                            break;
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid variable name entry in Custom Component #" + (i + 1), Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid (null) Custom Component at position " + i);
            }
        }
    }

    public static String getPath() {
        return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/component.json");
    }

    /**
     * @return List of Custom Components. Will never return null, but will warn the user about
     * an invalid Custom Components JSON file.
     */
    private static ArrayList<HashMap<String, Object>> readCustomComponents() {
        ArrayList<HashMap<String, Object>> data;
        if (FileUtil.isExistFile(getPath())) {
            try {
                data = new Gson().fromJson(FileUtil.readFile(getPath()), Helper.TYPE_MAP_LIST);
            } catch (Exception e) {
                data = new ArrayList<>();
                SketchwareUtil.toastError("Couldn't read Custom Components file: " + e.getMessage());
            }
            if (data == null) {
                SketchwareUtil.toastError("Found invalid Custom Components file");
                data = new ArrayList<>();
            }
        } else {
            data = new ArrayList<>();
        }

        return data;
    }

    public static void refreshCachedCustomComponents() {
        cachedCustomComponents = readCustomComponents();
    }

    public static boolean isValidComponent(Map<String, Object> map) {
        return map.containsKey("name")
                && map.containsKey("id")
                && map.containsKey("icon")
                && map.containsKey("varName")
                && map.containsKey("typeName")
                && map.containsKey("buildClass")
                && map.containsKey("class")
                && map.containsKey("description")
                && map.containsKey("url")
                && map.containsKey("additionalVar")
                && map.containsKey("defineAdditionalVar")
                && map.containsKey("imports");
    }

    public static boolean isValidComponentList(List<? extends Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            if (!isValidComponent(map)) {
                return false;
            }
        }
        return true;
    }

    public static Pair<Optional<String>, List<HashMap<String, Object>>> readComponents(String filePath) {
        String content = FileUtil.readFile(filePath);
        if (content.isEmpty() || content.equals("[]")) {
            return new Pair<>(Optional.of(Helper.getResString(R.string.common_message_selected_file_empty)), Collections.emptyList());
        }

        var components = new Gson().fromJson(content, Helper.TYPE_MAP_LIST);
        if (components == null || components.isEmpty() || !isValidComponentList(components)) {
            return new Pair<>(Optional.of(Helper.getResString(R.string.publish_message_dialog_invalid_json)), Collections.emptyList());
        }

        return new Pair<>(Optional.empty(), components);
    }
}
