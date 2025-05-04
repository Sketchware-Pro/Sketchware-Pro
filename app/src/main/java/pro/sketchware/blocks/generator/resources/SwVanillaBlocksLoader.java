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
        hashMap.put("name", "increaseInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%s++;");
        hashMap.put("spec", "%m.varInt increase");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "decreaseInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%s--;");
        hashMap.put("spec", "%m.varInt decrease");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapCreateNew");
        hashMap.put("type", " ");
        hashMap.put("code", "%s = new HashMap<>();");
        hashMap.put("spec", "%m.varMap create new Map");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapPut");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.put(%s, %s);");
        hashMap.put("spec", "%m.varMap put key %s value %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapGet");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.get(%s).toString()");
        hashMap.put("spec", "%m.varMap get key %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapContainKey");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.containsKey(%s)");
        hashMap.put("spec", "%m.varMap contains key %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapRemoveKey");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.remove(%s);");
        hashMap.put("spec", "%m.varMap remove %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapSize");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.size()");
        hashMap.put("spec", "%m.varMap size");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "lengthList");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.size()");
        hashMap.put("spec", "%m.list size");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapClear");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clear();");
        hashMap.put("spec", "%m.varMap clear");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "clearList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clear();");
        hashMap.put("spec", "%m.list clear");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapIsEmpty");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.isEmpty()");
        hashMap.put("spec", "%m.varMap isEmpty");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapGetAllKeys");
        hashMap.put("type", " ");
        hashMap.put("code", "SketchwareUtil.getAllKeysFromMap(%s, %s);");
        hashMap.put("spec", "%m.varMap get all keys from %m.listStr");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addListInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add(Double.valueOf(%s));");
        hashMap.put("spec", "%m.lisInt add %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertListInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add((int)(%s), Double.valueOf(%s));");
        hashMap.put("spec", "%m.listInt insert at %d %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListInt");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.get((int) (%s)).doubleValue()");
        hashMap.put("spec", "get %m.listInt at %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "indexListInt");
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.indexOf(%1$s)");
        hashMap.put("spec", "%d indexOf %m.listInt");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "indexListStr");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.indexOf(%s)");
        hashMap.put("spec", "%m.listStr indexOf %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListInt");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.contains(%s)");
        hashMap.put("spec", "%m.listInt contains %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListStr");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.contains(%s)");
        hashMap.put("spec", "%m.listStr contains %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addListStr");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add(%s);");
        hashMap.put("spec", "%m.listStr add %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addMapToList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add(%s);");
        hashMap.put("spec", "%m.listMap add %m.varMao");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertListStr");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add((int)(%s), %s);");
        hashMap.put("spec", "%m.lisStr insert at %d %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListStr");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.get((int)(%s))");
        hashMap.put("spec", "%m.lisStr get at %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addListMap");
        hashMap.put("type", " ");
        hashMap.put("code", """
                {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put(%s, %s);
                %s.add(_item);
                }"""
        );
        hashMap.put("spec", "new map put key %s value %s to %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertListMap");
        hashMap.put("type", " ");
        hashMap.put("code", """
                {
                HashMap<String, Object> _item = new HashMap<>();
                _item.put(%s, %s);
                %s.add((int)%s, _item);
                }"""
        );
        hashMap.put("spec", "new map put key %s value %s insert at %d to %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListMap");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.get((int) %s).get(%s).toString()");
        hashMap.put("spec", "%m.listMap get %d key %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setListMap");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.get((int)%s).put(%s, %s);");
        hashMap.put("spec", "%m.listMap get %m.varMap put key %s value %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListMap");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.get((int)%s).containsKey(%s)");
        hashMap.put("spec", "%m.listMap get %m.varMap contains key %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertMapToList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.add((int)%s, %s);");
        hashMap.put("spec", "%m.listMap insert at %d %m.varMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getMapInList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s = %s.get((int)%s);");
        hashMap.put("spec", "%m.varMap get from %m.listMap at %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "deleteList");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.remove((int)(%s));");
        hashMap.put("spec", "%m.listInt remove number %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "break");
        hashMap.put("type", "f");
        hashMap.put("code", "break;");
        hashMap.put("spec", "break");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "random");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getRandom((int)(%s), (int)(%s))");
        hashMap.put("spec", "random from %d to %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringLength");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.length()");
        hashMap.put("spec", "%s length");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringJoin");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.concat(%s)");
        hashMap.put("spec", "%s join %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringIndex");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.indexOf(%s)");
        hashMap.put("spec", "%s indexOf %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringLastIndex");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.lastIndexOf(%s)");
        hashMap.put("spec", "%s lastIndexOf %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringSub");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.substring((int)(%s), (int)(%s))");
        hashMap.put("spec", "%s substring from %d to %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringEquals");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.equals(%s)");
        hashMap.put("spec", "%s isEquals %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringContains");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.contains(%s)");
        hashMap.put("spec", "%s contains %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringReplace");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.replace(%s, %s)");
        hashMap.put("spec", "%s replace %s with %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringReplaceFirst");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.replaceFirst(%s, %s)");
        hashMap.put("spec", "%s replaceFirst %s with %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringReplaceAll");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.replaceAll(%s, %s)");
        hashMap.put("spec", "%s replaceAll %s with %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toNumber");
        hashMap.put("type", "d");
        hashMap.put("code", "Double.parseDouble(%s)");
        hashMap.put("spec", "%s to number");
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
