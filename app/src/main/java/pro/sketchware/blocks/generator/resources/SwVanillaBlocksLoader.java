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
        hashMap.put("code", "%2$s.add(Double.valueOf(%1$s));");
        hashMap.put("spec", "add %d to %m.listInt");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertListInt");
        hashMap.put("type", " ");
        hashMap.put("code", "%3$s.add((int) (%2$s), Double.valueOf(%1$s));");
        hashMap.put("spec", "insert %d at %d for %m.listInt");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListInt");
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.get((int) (%1$s)).doubleValue()");
        hashMap.put("spec", "get at %d from %m.listInt");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "indexListInt");
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.indexOf(%1$s)");
        hashMap.put("spec", "%d indexOf %m.listInt");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "indexListStr");
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.indexOf(%1$s)");
        hashMap.put("spec", "%s indexOf %m.listStr");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListInt");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.contains(%s)");
        hashMap.put("spec", "%m.listInt contains %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListStr");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.contains(%s)");
        hashMap.put("spec", "%m.listStr contains %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addListStr");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s.add(%1$s);");
        hashMap.put("spec", "add %s to %m.listStr");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "addMapToList");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s.add(%1$s);");
        hashMap.put("spec", "add %m.varMap to %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertListStr");
        hashMap.put("type", " ");
        hashMap.put("code", "%3$s.add((int) (%2$s), %1$s);");
        hashMap.put("spec", "insert %s at %d to %m.listStr");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListStr");
        hashMap.put("type", "s");
        hashMap.put("code", "%2$s.get((int) (%1$s))");
        hashMap.put("spec", "get at %d from %m.listStr");
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
                _item.put(%1$s, %2$s);
                %4$s.add((int) %3$s, _item);
                }"""
        );
        hashMap.put("spec", "insert key %s value %s at %d to %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAtListMap");
        hashMap.put("type", "s");
        hashMap.put("code", "%3$s.get((int) %1$s).get(%2$s).toString()");
        hashMap.put("spec", "get at %d key %s from %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setListMap");
        hashMap.put("type", " ");
        hashMap.put("code", "%4$s.get((int) %3$s).put(%1$s, %2$s);");
        hashMap.put("spec", "set key %s and value %s at %d in %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "containListMap");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.get((int) %s).containsKey(%s)");
        hashMap.put("spec", "%m.listMap at %d contains key %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "insertMapToList");
        hashMap.put("type", " ");
        hashMap.put("code", "%3$s.add((int) %2$s, %1$s);");
        hashMap.put("spec", "%m.varMap insert at %d in %m.listMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getMapInList");
        hashMap.put("type", " ");
        hashMap.put("code", "%3$s = %2$s.get((int) %1$s);");
        hashMap.put("spec", "get at %d from %m.listMap to %m.varMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "deleteList");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s.remove((int)(%1$s));");
        hashMap.put("spec", "remove %d from %m.list");
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
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.indexOf(%1$s)");
        hashMap.put("spec", "index %s Of %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "stringLastIndex");
        hashMap.put("type", "d");
        hashMap.put("code", "%2$s.lastIndexOf(%1$s)");
        hashMap.put("spec", "lastIndex %s Of %s");
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
        hashMap.put("name", "currentTime");
        hashMap.put("type", "d");
        hashMap.put("code", "System.currentTimeMillis()");
        hashMap.put("spec", "System get currentTimeMillis");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "trim");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.trim()");
        hashMap.put("spec", "trim %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toUpperCase");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.toUpperCase()");
        hashMap.put("spec", "%s toUpperCase");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toLowerCase");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.toLowerCase()");
        hashMap.put("spec", "%s toLowerCase");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toString");
        hashMap.put("type", "s");
        hashMap.put("code", "String.valueOf((long)(%s))");
        hashMap.put("spec", "%s toString");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toStringWithDecimal");
        hashMap.put("type", "s");
        hashMap.put("code", "String.valueOf(%s)");
        hashMap.put("spec", "%s to string with decimal");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "toStringFormat");
        hashMap.put("type", "s");
        hashMap.put("code", "new DecimalFormat(%2$s).format(%1$s)");
        hashMap.put("spec", "%d to decimalFormat %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "strToMap");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s = new Gson().fromJson(%1$s, new TypeToken<HashMap<String, Object>>(){}.getType());");
        hashMap.put("spec", "json %s to %m.varMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapToStr");
        hashMap.put("type", "s");
        hashMap.put("code", "new Gson().toJson(%s)");
        hashMap.put("spec", "%m.varMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listMapToStr");
        hashMap.put("type", "s");
        hashMap.put("code", "new Gson().toJson(%s)");
        hashMap.put("spec", "%m.varListMap");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "strToListMap");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s = new Gson().fromJson(%1$s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());");
        hashMap.put("spec", "json %s to %m.varListMao");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathGetDip");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getDip(getApplicationContext(), (int)(%s))");
        hashMap.put("spec", "getDip %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathGetDisplayWidth");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())");
        hashMap.put("spec", "getDisplayWidthPixels");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathGetDisplayHeight");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())");
        hashMap.put("spec", "getDisplayHeightPixels");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathPi");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.PI");
        hashMap.put("spec", "Math PI");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathE");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.E");
        hashMap.put("spec", "math E");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathPow");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.pow(%s, %s)");
        hashMap.put("spec", "pow %d %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathMin");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.min(%s, %s)");
        hashMap.put("spec", "min %d %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathMax");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.max(%s, %s)");
        hashMap.put("spec", "max %d %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathSqrt");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.sqrt(%s)");
        hashMap.put("spec", "sqrt %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathAbs");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.abs(%s)");
        hashMap.put("spec", "abs %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathRound");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.round(%s)");
        hashMap.put("spec", "round %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathCeil");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.ceil(%s)");
        hashMap.put("spec", "ceil %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathFloor");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.floor(%s)");
        hashMap.put("spec", "floor %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathSin");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.sin(%s)");
        hashMap.put("spec", "sin %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathCos");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.cos(%s)");
        hashMap.put("spec", "cos %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathTan");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.tan(%s)");
        hashMap.put("spec", "tan %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathAsin");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.asin(%s)");
        hashMap.put("spec", "asin %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathAcos");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.acos(%s)");
        hashMap.put("spec", "acos %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathAtan");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.atan(%s)");
        hashMap.put("spec", "atan %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathExp");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.exp(%s)");
        hashMap.put("spec", "exp %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathLog");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.log(%s)");
        hashMap.put("spec", "log %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathLog10");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.log10(%s)");
        hashMap.put("spec", "log10 %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathToRadian");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.toRadians(%s)");
        hashMap.put("spec", "toRadians %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mathToDegree");
        hashMap.put("type", "d");
        hashMap.put("code", "Math.toDegrees(%s)");
        hashMap.put("spec", "toDegrees %d");
        blocks.add(hashMap);

        return blocks;
    }

}
