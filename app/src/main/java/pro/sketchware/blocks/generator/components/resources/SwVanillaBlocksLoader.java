package pro.sketchware.blocks.generator.components.resources;

import java.util.ArrayList;
import java.util.HashMap;

import pro.sketchware.blocks.generator.components.utils.BlockParamUtils;

public class SwVanillaBlocksLoader {

    public ArrayList<HashMap<String, Object>> getSwVanillaBlocks(boolean isViewBindingEnabled) {
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

        hashMap = new HashMap<>();
        hashMap.put("name", "viewOnClick");
        hashMap.put("type", "c");
        hashMap.put("code", """
                %s.setOnClickListener(new View.OnClickListener() {
                
                    @Override
                    public void onClick(View _view) {
                        %s
                    }
                });""");
        hashMap.put("spec", "when %m.view clicked");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "isDrawerOpen");
        hashMap.put("type", "b");
        hashMap.put("code",
                isViewBindingEnabled ? "binding.Drawer.isDrawerOpen(GravityCompat.START)" : "_drawer.isDrawerOpen(GravityCompat.START)"
        );
        hashMap.put("spec", "is drawer open");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "openDrawer");
        hashMap.put("type", " ");
        hashMap.put("code",
                isViewBindingEnabled ? "binding.Drawer.openDrawer(GravityCompat.START);" : "_drawer.openDrawer(GravityCompat.START);"
        );
        hashMap.put("spec", "open drawer");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "closeDrawer");
        hashMap.put("type", " ");
        hashMap.put("code",
                isViewBindingEnabled ? "binding.Drawer.closeDrawer(GravityCompat.START);" : "_drawer.closeDrawer(GravityCompat.START);"
        );
        hashMap.put("spec", "close drawer");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setEnable");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setEnabled(%s);");
        hashMap.put("spec", "%m.view set enabled %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getEnable");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.isEnabled()");
        hashMap.put("spec", "%m.view is enabled");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setText");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setText(%s);");
        hashMap.put("spec", "%m.textview set text %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTypeface");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setTypeface(Typeface.DEFAULT, %s);");
        hashMap.put("spec", "%m.textview set typeface %m.font with style %m.typeface");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTypeface");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);");
        hashMap.put("spec", "%m.textview set typeface %m.font with style %m.typeface");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getText");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.getText().toString()");
        hashMap.put("spec", "%m.textview get text");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setBgColor");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setBackgroundColor(%s);");
        hashMap.put("spec", "%m.view set background color %m.color");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setBgResource");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setBackgroundResource(R.drawable.%s);");
        hashMap.put("spec", "%m.view set background resource %m.drawable");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setBgResource");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setBackgroundResource(%s);");
        hashMap.put("spec", "%m.view set background resource %m.drawable");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTextColor");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setTextColor(%s);");
        hashMap.put("spec", "%m.textview set text color %m.color");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setImage");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setImageResource(R.drawable.%s);");
        hashMap.put("spec", "%m.imageview set image %m.drawable");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setColorFilter");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);");
        hashMap.put("spec", "%m.imageview set color filter %m.color");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "requestFocus");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.requestFocus();");
        hashMap.put("spec", "%m.view request focus");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "doToast");
        hashMap.put("type", " ");
        hashMap.put("code", "SketchwareUtil.showMessage(getApplicationContext(), %s);");
        hashMap.put("spec", "show toast %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "copyToClipboard");
        hashMap.put("type", " ");
        hashMap.put("code", "((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));");
        hashMap.put("spec", "copy to clipboard %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTitle");
        hashMap.put("type", " ");
        hashMap.put("code", "setTitle(%s);");
        hashMap.put("spec", "set title %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentSetAction");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setAction(Intent.%s);");
        hashMap.put("spec", "%m.intent set action %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentSetData");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setData(Uri.parse(%s));");
        hashMap.put("spec", "%m.intent set data %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentSetScreen");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setClass(getApplicationContext(), %s.class);");
        hashMap.put("spec", "%m.intent set screen %m.class");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentPutExtra");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.putExtra(%s, %s);");
        hashMap.put("spec", "%m.intent put extra %s %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentSetFlags");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setFlags(Intent.FLAG_ACTIVITY_%s);");
        hashMap.put("spec", "%m.intent set flags %m.intentAction");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "intentGetString");
        hashMap.put("type", "s");
        hashMap.put("code", "getIntent().getStringExtra(%s)");
        hashMap.put("spec", "get intent string extra %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "startActivity");
        hashMap.put("type", " ");
        hashMap.put("code", "startActivity(%s);");
        hashMap.put("spec", "start activity %m.intent");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "finishActivity");
        hashMap.put("type", " ");
        hashMap.put("code", "finish();");
        hashMap.put("spec", "finish activity");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "fileGetData");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.getString(%s, \"\")");
        hashMap.put("spec", "%m.SharedPreferences get string %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "fileSetData");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.edit().putString(%s, %s).commit();");
        hashMap.put("spec", "%m.SharedPreferences set string %s to %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "fileRemoveData");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.edit().remove(%s).commit();");
        hashMap.put("spec", "%m.SharedPreferences remove string %s");
        blocks.add(hashMap);


        // skipping calendar blocks for now

        hashMap = new HashMap<>();
        hashMap.put("name", "setVisible");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setVisibility(View.%s);");
        hashMap.put("spec", "%m.view set visibility %m.visible");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setClickable");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setClickable(%s);");
        hashMap.put("spec", "%m.view set clickable %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setRotate");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setRotation((float)(%s));");
        hashMap.put("spec", "%m.view set rotation %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getRotate");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getRotation()");
        hashMap.put("spec", "%m.view get rotation");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setAlpha");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setAlpha((float)(%s));");
        hashMap.put("spec", "%m.view set alpha %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getAlpha");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getAlpha()");
        hashMap.put("spec", "%m.view get alpha");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTranslationX");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setTranslationX((float)(%s));");
        hashMap.put("spec", "%m.view set translation x %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getTranslationX");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getTranslationX()");
        hashMap.put("spec", "%m.view get translation x");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setTranslationY");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setTranslationY((float)(%s));");
        hashMap.put("spec", "%m.view set translation y %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getTranslationY");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getTranslationY()");
        hashMap.put("spec", "%m.view get translation y");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setScaleX");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setScaleX((float)(%s));");
        hashMap.put("spec", "%m.view set scale x %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getScaleX");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getScaleX()");
        hashMap.put("spec", "%m.view get scale x");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setScaleY");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setScaleY((float)(%s));");
        hashMap.put("spec", "%m.view set scale y %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getScaleY");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getScaleY()");
        hashMap.put("spec", "%m.view get scale y");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getLocationX");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getLocationX(%s)");
        hashMap.put("spec", "%m.view get location x");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getLocationY");
        hashMap.put("type", "d");
        hashMap.put("code", "SketchwareUtil.getLocationY(%s)");
        hashMap.put("spec", "%m.view get location y");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "setChecked");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setChecked(%s);");
        hashMap.put("spec", "%m.checkbox set checked %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "getChecked");
        hashMap.put("type", "b");
        hashMap.put("code", "%s.isChecked()");
        hashMap.put("spec", "%m.checkbox get checked");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listSetData");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, %s));");
        hashMap.put("spec", "%m.listview set data %m.listStr");
        blocks.add(hashMap);

        blocks.addAll(getAdaptersList(
                "listSetCustomViewData",
                "recyclerSetCustomViewData",
                "spnSetCustomViewData",
                "pagerSetCustomViewData",
                "gridSetCustomViewData"
        ));

        hashMap = new HashMap<>();
        hashMap.put("name", "listRefresh");
        hashMap.put("type", " ");
        hashMap.put("code", "((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();");
        hashMap.put("spec", "%m.listview refresh");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listSetItemChecked");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setItemChecked((int)(%s), %s);");
        hashMap.put("spec", "%m.listview set item at %d checked %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listGetCheckedPosition");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getCheckedItemPosition()");
        hashMap.put("spec", "%m.listview get checked item position");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listGetCheckedPositions");
        hashMap.put("type", " ");
        hashMap.put("code", "%2$s = SketchwareUtil.getCheckedItemPositionsToArray(%1$s);");
        hashMap.put("spec", "%m.listview getCheckedItemPositionsToArray %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listGetCheckedCount");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getCheckedItemCount()");
        hashMap.put("spec", "%m.listview get checked item count");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "listSmoothScrollTo");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.smoothScrollToPosition((int)(%s));");
        hashMap.put("spec", "%m.listview smooth scroll to position %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "spnSetData");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));");
        hashMap.put("spec", "%m.spinner set data %m.list");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "spnRefresh");
        hashMap.put("type", " ");
        hashMap.put("code", "((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();");
        hashMap.put("spec", "%m.spinner refresh");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "spnSetSelection");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setSelection((int)(%s));");
        hashMap.put("spec", "%m.spinner set selection to %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "spnGetSelection");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getSelectedItemPosition()");
        hashMap.put("spec", "%m.spinner get selected item position");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewLoadUrl");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.loadUrl(%s);");
        hashMap.put("spec", "%m.webview load url %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewGetUrl");
        hashMap.put("type", "s");
        hashMap.put("code", "%s.getUrl()");
        hashMap.put("spec", "%m.webview get url");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewSetCacheMode");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.getSettings().setCacheMode(WebSettings.%s);");
        hashMap.put("spec", "%m.webview set cache mode %m.cacheMode");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewCanGoBack");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.canGoBack()");
        hashMap.put("spec", "%m.webview can go back");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewCanGoForward");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.canGoForward()");
        hashMap.put("spec", "%m.webview can go forward");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewGoBack");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.goBack();");
        hashMap.put("spec", "%m.webview go back");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewGoForward");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.goForward();");
        hashMap.put("spec", "%m.webview go forward");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewClearCache");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clearCache(true);");
        hashMap.put("spec", "%m.webview clear cache");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewClearHistory");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.clearHistory();");
        hashMap.put("spec", "%m.webview clear history");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewStopLoading");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.stopLoading();");
        hashMap.put("spec", "%m.webview stop loading");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewZoomIn");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.zoomIn();");
        hashMap.put("spec", "%m.webview zoom in");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "webViewZoomOut");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.zoomOut();");
        hashMap.put("spec", "%m.webview zoom out");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "calendarViewGetDate");
        hashMap.put("type", "d");
        hashMap.put("code", "%s.getDate()");
        hashMap.put("spec", "%m.calendarview get date");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "calendarViewSetDate");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setDate((long)(%s), true, true);");
        hashMap.put("spec", "%m.calendarview set date %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "calendarViewSetMinDate");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setMinDate((long)(%s));");
        hashMap.put("spec", "%m.calendarview set min date %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "calnedarViewSetMaxDate");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.setMaxDate((long)(%s));");
        hashMap.put("spec", "%m.calendarview set max date %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "adViewLoadAd");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.loadAd(new AdRequest.Builder()%s.build());");
        hashMap.put("spec", "%m.adview load ad");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMapType");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMapType(GoogleMap.%s);");
        hashMap.put("spec", "%m.mapview set map type %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewMoveCamera");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.moveCamera(%s, %s);");
        hashMap.put("spec", "%m.mapview move camera to %s with zoom %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewZoomTo");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.zoomTo(%s);");
        hashMap.put("spec", "%m.mapview zoom to %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewZoomIn");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.zoomIn();");
        hashMap.put("spec", "%m.mapview zoom in");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewZoomOut");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.zoomOut();");
        hashMap.put("spec", "%m.mapview zoom out");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewAddMarker");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.addMarker(%s, %s, %s);");
        hashMap.put("spec", "%m.mapview add marker at %s with title %s and snippet %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMarkerInfo");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMarkerInfo(%s, %s, %s);");
        hashMap.put("spec", "%m.mapview set marker info for %s title %s snippet %s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMarkerPosition");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMarkerPosition(%s, %s, %s);");
        hashMap.put("spec", "%m.mapview move marker %s to %s,%s");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMarkerColor");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);");
        hashMap.put("spec", "%m.mapview set marker %s color %s with alpha %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMarkerIcon");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMarkerIcon(%s, R.drawable.%s);");
        hashMap.put("spec", "%m.mapview set marker %s icon %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "mapViewSetMarkerVisible");
        hashMap.put("type", " ");
        hashMap.put("code", "_%s_controller.setMarkerVisible(%s, %s);");
        hashMap.put("spec", "%m.mapview set marker %s visible %b");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "vibratorAction");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.vibrate((long)(%s));");
        hashMap.put("spec", "%m.vibrator vibrate for %d");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "timerAfter");
        hashMap.put("type", "c");
        hashMap.put("code", """
        %1$s = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        %3$s
                    }
                });
            }
        };
        _timer.schedule(%1$s, (int)(%2$s));
        """);
        hashMap.put("spec", "%m.timer after %d delay");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "timerEvery");
        hashMap.put("type", "c");
        hashMap.put("code", """
        %1$s = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        %4$s
                    }
                });
            }
        };
        _timer.scheduleAtFixedRate(%1$s, (int)(%2$s), (int)(%3$s));
        """);
        hashMap.put("spec", "%m.timer every %d delay %d run");
        blocks.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("name", "timerCancel");
        hashMap.put("type", " ");
        hashMap.put("code", "%s.cancel();");
        hashMap.put("spec", "%m.timer cancel");
        blocks.add(hashMap);

        return blocks;
    }

    private ArrayList<HashMap<String, Object>> getAdaptersList(String... Names) {

        ArrayList<HashMap<String, Object>> blocks = new ArrayList<>();
        HashMap<String, Object> hashMap;

        for (String name : Names) {
            hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("type", " ");
            hashMap.put("code", "%s.setAdapter(new " + BlockParamUtils.PLACEHOLDER_PARAM + "(%s));");
            hashMap.put("spec", "%m.listview set list %m.listMap");
            hashMap.put("runtimeHandling", "setAdapter");
            blocks.add(hashMap);
        }
        return blocks;

    }

}
