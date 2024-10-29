package mod.agus.jcoderz.editor.manage.block.makeblock;

import android.os.Environment;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import pro.sketchware.utility.FileUtil;

/**
 * Helper used in {@link com.besome.sketch.editor.LogicEditorActivity} to get menus for custom
 * Menus defined in {@link mod.hilal.saif.activities.tools.BlockSelectorActivity}.
 */
public class BlockMenu {

    public static Pair<String, String[]> getMenu(String name) {
        Pair<String, String[]> result = null;
        Pair<String, String[]> fallback = new Pair<>("Select a " + name + " Variable", new String[0]);
        File file = new File(Environment.getExternalStorageDirectory(), ".sketchware/resources/block/My Block/menu.json");
        if (!file.exists()) {
            result = fallback;
        } else {
            try {
                JSONArray jSONArray = new JSONArray(FileUtil.readFile(file.getAbsolutePath()));
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject.getString("name").equals(name)) {
                        result = new Pair<>(jSONObject.getString("title"), jsonarrayToArr(jSONObject.getJSONArray("data")));
                        break;
                    }
                }
            } catch (Exception ignored) {
            }
            if (result == null) {
                result = fallback;
            }
        }

        return result;
    }

    private static String[] jsonarrayToArr(JSONArray jSONArray) throws JSONException {
        String[] strArr = new String[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            strArr[i] = jSONArray.getString(i);
        }
        return strArr;
    }
}
