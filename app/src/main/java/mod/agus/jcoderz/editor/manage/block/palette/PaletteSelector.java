package mod.agus.jcoderz.editor.manage.block.palette;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dev.aldi.sayuti.block.ExtraBlockFile;
import mod.SketchwareUtil;

public class PaletteSelector {

    private final ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private int start = 9;

    public ArrayList<HashMap<String, Object>> getPaletteSelector() {
        String paletteBlockFile = ExtraBlockFile.getPaletteBlockFile();
        if (!paletteBlockFile.equals("")) {
            try {
                JSONArray palettes = new JSONArray(paletteBlockFile);
                for (int i = 0; i < palettes.length(); i++) {
                    JSONObject item = palettes.getJSONObject(i);

                    int color;

                    try {
                        color = Color.parseColor(item.get("color").toString());
                    } catch (IllegalArgumentException e) {
                        SketchwareUtil.toastError("Couldn't parse color of Custom Block Palette #" + (i + 1));
                        color = 0xff8a55d7;
                    }

                    setPaletteData(start, item.get("name").toString(), color);
                    start++;
                }
            } catch (JSONException e) {
                SketchwareUtil.toastError("Error occurred while loading Custom Block Palette: " + e);
            }
        }

        return list;
    }

    public void setPaletteData(int index, String name, int color) {
        HashMap<String, Object> palette = new HashMap<>();
        palette.put("index", index);
        palette.put("text", name);
        palette.put("color", color);
        list.add(palette);
    }
}
