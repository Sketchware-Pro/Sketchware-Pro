package mod.agus.jcoderz.editor.manage.block.palette;

import android.graphics.Color;

import dev.aldi.sayuti.block.ExtraBlockFile;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaletteSelector {
    public ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    public int start = 9;

    public ArrayList<HashMap<String, Object>> getPaletteSelector() {
        String paletteBlockFile = ExtraBlockFile.getPaletteBlockFile();
        if (!paletteBlockFile.equals("")) {
            try {
                JSONArray palettes = new JSONArray(paletteBlockFile);
                for (int i = 0; i < palettes.length(); i++) {
                    JSONObject item = palettes.getJSONObject(i);
                    setPaletteData(start, item.get("name").toString(), Color.parseColor(item.get("color").toString()));
                    start++;
                }
            } catch (JSONException e) {
            }
        }
        return list;
    }

    public void setPaletteData(int index, String name, int color) {
        HashMap<String, Object> palette = new HashMap<>();
        palette.put("index", Integer.valueOf(index));
        palette.put("text", name);
        palette.put("color", Integer.valueOf(color));
        list.add(palette);
    }
}
