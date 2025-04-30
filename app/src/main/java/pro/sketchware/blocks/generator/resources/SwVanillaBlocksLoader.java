package pro.sketchware.blocks.generator.resources;

import java.util.ArrayList;
import java.util.HashMap;

public class SwVanillaBlocksLoader {

    public ArrayList<HashMap<String, Object>> getSwVanillaBlocks() {
        ArrayList<HashMap<String, Object>> blocks = new ArrayList<>();

        // TODO: need to add every single block from a.a.a.Fx#getBlockCode(...)

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "setVarBoolean");
        hashMap.put("type", " ");
        hashMap.put("code", "%s = %s;");
        hashMap.put("spec", "set %m.varBool to %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setVarInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%s = %s;");
        hashMap.put("spec", "set %m.varInt to %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setVarString");
        hashMap.put("type", " ");
        hashMap.put("code", "%s = %s;");
        hashMap.put("spec", "set %m.varStr to %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapClear");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clear();");
        hashMap.put("spec", "clear %m.varMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "clearList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clear();");
        hashMap.put("spec", "clear %m.list");
        blocks.add(hashMap);

        return blocks;
    }

}
