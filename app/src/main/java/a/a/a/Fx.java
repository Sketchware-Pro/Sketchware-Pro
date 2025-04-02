package a.a.a;

import android.util.Pair;

import com.besome.sketch.beans.BlockBean;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.pranav.viewbinding.ViewBindingBuilder;

public class Fx {

    private static final Pattern PARAM_PATTERN = Pattern.compile("%m(?!\\.[\\w]+)");
    public final boolean isViewBindingEnabled;
    private final ArrayList<String> viewParamsTypes = new ArrayList<>(List.of(
            "%m.view", "%m.layout", "%m.textview", "%m.button", "%m.edittext", "%m.imageview", "%m.recyclerview",
            "%m.listview", "%m.gridview", "%m.cardview", "%m.viewpager", "%m.webview", "%m.videoview", "%m.progressbar",
            "%m.seekbar", "%m.switch", "%m.checkbox", "%m.spinner", "%m.tablayout", "%m.bottomnavigation", "%m.adview",
            "%m.swiperefreshlayout", "%m.textinputlayout", "%m.ratingbar", "%m.datepicker", "%m.otpview", "%m.lottie",
            "%m.badgeview", "%m.codeview", "%m.patternview", "%m.signinbutton", "%m.youtubeview"
    ));
    public String[] operators = {"repeat", "+", "-", "*", "/", "%", ">", "=", "<", "&&", "||", "not"};
    public String[] arithmetic = {"+", "-", "*", "/", "%", ">", "=", "<", "&&", "||"};
    public String moreBlock = "";
    public String activityName;
    public jq buildConfig;
    public ArrayList<BlockBean> eventBlocks;
    public Map<String, BlockBean> blockMap;

    public Fx(String activityName, jq buildConfig, ArrayList<BlockBean> eventBlocks, boolean isViewBindingEnabled) {
        this.activityName = activityName;
        this.buildConfig = buildConfig;
        this.eventBlocks = eventBlocks;
        this.isViewBindingEnabled = isViewBindingEnabled;
    }

    public String a() {
        blockMap = new HashMap<>();
        ArrayList<BlockBean> beans = eventBlocks;

        if (beans != null && !beans.isEmpty()) {
            for (BlockBean bean : eventBlocks) {
                blockMap.put(bean.id, bean);
            }

            return generateBlock(eventBlocks.get(0), "");
        } else {
            return "";
        }
    }

    public final String generateBlock(BlockBean bean, String var2) {
        ArrayList<String> params = getBlockParams(bean);

        String opcode = getBlockCode(bean, params);

        String code = opcode;

        if (b(bean.opCode, var2)) {
            code = "(" + opcode + ")";
        }

        if (bean.nextBlock >= 0) {
            code += (code.isEmpty() ? "" : "\r\n") + a(String.valueOf(bean.nextBlock), moreBlock);
        }

        return code;
    }

    private boolean hasEmptySelectorParam(ArrayList<String> params, String spec) {
        var matcher = PARAM_PATTERN.matcher(spec);
        if (!matcher.find()) {
            var paramMatcher = Pattern.compile("%[bdsm]").matcher(spec);
            int count = 0;
            ArrayList<Integer> selectorParamPositions = new ArrayList<>();
            while (paramMatcher.find()) {
                String param = paramMatcher.group();
                if ("%m".equals(param)) {
                    selectorParamPositions.add(count);
                }
                count++;
            }
            if (!selectorParamPositions.isEmpty()) {
                for (int position : selectorParamPositions) {
                    if (position >= params.size()) {
                        continue;
                    }
                    var param = params.get(position);
                    if (param == null || param.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String escapeString(String input) {
        StringBuilder escapedString = new StringBuilder(4096);
        CharBuffer charBuffer = CharBuffer.wrap(input);

        for (int i = 0; i < charBuffer.length(); ++i) {
            char currentChar = charBuffer.get(i);
            if (currentChar == '"') {
                escapedString.append("\\\"");
            } else if (currentChar == '\\') {
                if (i < charBuffer.length() - 1) {
                    int nextIndex = i + 1;
                    currentChar = charBuffer.get(nextIndex);
                    if (currentChar != 'n' && currentChar != 't') {
                        escapedString.append("\\\\");
                    } else {
                        escapedString.append("\\").append(currentChar);
                        i = nextIndex;
                    }
                } else {
                    escapedString.append("\\\\");
                }
            } else if (currentChar == '\n') {
                escapedString.append("\\n");
            } else {
                escapedString.append(currentChar);
            }
        }

        return escapedString.toString();
    }

    public final String a(String param, int type, String opcode) {
        if (!param.isEmpty() && param.charAt(0) == '@') {
            opcode = a(param.substring(1), opcode);
            if (type == 2 && opcode.isEmpty()) {
                return "\"\"";
            }
            return opcode;
        } else if (type == 2) {
            return "\"" + escapeString(param) + "\"";
        } else if (type == 1) {
            /**
             * Ideally, a.a.aFx#a(BlockBean, String) should be responsible for parsing the input properly.
             * However, upon decompiling this class, it seems to completely ignore this case.
             * This is the solution for now to prevent errors during code generation.
             */
            try {
                if (param.isEmpty()) {
                    return "0";
                }
                if (param.contains(".")) {
                    Double.parseDouble(param);
                    return param + "d";
                }
                Integer.parseInt(param);
                return param;
            } catch (NumberFormatException e) {
                return param;
            }
        } else if (type == 0) {
            //the same with type == 1
            if (param.isEmpty()) {
                return "true";
            }
        }
        return param;
    }

    public final String a(String blockId, String var2) {
        return !blockMap.containsKey(blockId) ? "" : generateBlock(blockMap.get(blockId), var2);
    }

    public final boolean b(String var1, String var2) {
        return Arrays.asList(operators).contains(var2) && Arrays.asList(arithmetic).contains(var1);
    }

    public ArrayList<String> getBlockParams(BlockBean bean) {
        ArrayList<String> params = new ArrayList<>();
        ArrayList<String> paramsTypes = extractParamsTypes(bean.spec);
        for (int i = 0; i < bean.parameters.size(); i++) {
            String param = getParamValue(bean.parameters.get(i), viewParamsTypes.contains(paramsTypes.get(i)));
            int type = getBlockType(bean, i);
            params.add(a(param, type, bean.opCode));
        }
        return params;
    }

    private String getParamValue(String param, boolean isWidgetParam) {
        String bindingStart = "binding.";
        if (isViewBindingEnabled && isWidgetParam && !param.isEmpty() && param.charAt(0) != '@' && !param.startsWith(bindingStart)) {
            return bindingStart + ViewBindingBuilder.generateParameterFromId(param);
        } else {
            return param;
        }
    }

    private ArrayList<String> extractParamsTypes(String input) {
        ArrayList<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile("%\\w+(?:\\.\\w+)?|%\\w"); // Supports %m.word.word, %m.word and %word
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            matches.add(matcher.group().toLowerCase());
        }

        return matches;
    }

    private String getBlockCode(BlockBean bean, ArrayList<String> params) {
        String opcode = "";
        switch (bean.opCode) {
            case "definedFunc":
                int space = bean.spec.indexOf(" ");
                if (bean.parameters.isEmpty()) {
                    opcode = bean.type;
                    moreBlock = "_" + (space < 0 ? bean.spec : bean.spec.substring(0, space)) + "()" + ReturnMoreblockManager.getMbEnd(bean.type);
                } else {
                    ArrayList<String> paramsTypes = extractParamsTypes(bean.spec);
                    opcode = "_" + bean.spec.substring(0, space) + "(";
                    boolean hasStringParam = false;

                    for (int i = 0; i < params.size(); i++) {
                        if (i > 0) opcode += ", ";
                        String param = getParamValue(params.get(i), viewParamsTypes.contains(paramsTypes.get(i)));
                        if (param.isEmpty()) {
                            Gx paramInfo = bean.getParamClassInfo().get(i);
                            if (paramInfo.b("boolean")) {
                                opcode += "true";
                            } else if (paramInfo.b("double")) {
                                opcode += "0";
                            } else if (paramInfo.b("String")) {
                                hasStringParam = true;
                            }
                        } else {
                            opcode += param;
                        }
                    }

                    moreBlock = opcode + ")" + ReturnMoreblockManager.getMbEnd(bean.type);
                    if (hasStringParam) moreBlock = bean.type;
                }

                String op = opcode;
                opcode = moreBlock;
                moreBlock = op;
                break;
            case "getArg":
                opcode = "_" + bean.spec;
                break;
            case "getVar":
                opcode = bean.spec;
                break;
            case "getResStr":
                opcode = "getString(R.string." + bean.spec + ")";
                break;
            case "setVarBoolean", "setVarInt", "setVarString":
                opcode = String.format("%s = %s;", params.get(0), params.get(1));
                break;
            case "increaseInt":
                opcode = String.format("%s++;", params.get(0));
                break;
            case "decreaseInt":
                opcode = String.format("%s--;", params.get(0));
                break;
            case "mapCreateNew":
                opcode = String.format("%s = new HashMap<>();", params.get(0));
                break;
            case "mapPut":
                opcode = String.format("%s.put(%s, %s);", params.get(0), params.get(1), params.get(2));
                break;
            case "mapGet":
                opcode = String.format("%s.get(%s).toString()", params.get(0), params.get(1));
                break;
            case "mapContainKey":
                opcode = String.format("%s.containsKey(%s)", params.get(0), params.get(1));
                break;
            case "mapRemoveKey":
                opcode = String.format("%s.remove(%s);", params.get(0), params.get(1));
                break;
            case "mapSize", "lengthList":
                opcode = String.format("%s.size()", params.get(0));
                break;
            case "mapClear", "clearList":
                opcode = String.format("%s.clear();", params.get(0));
                break;
            case "mapIsEmpty":
                opcode = String.format("%s.isEmpty()", params.get(0));
                break;
            case "mapGetAllKeys":
                opcode = String.format("SketchwareUtil.getAllKeysFromMap(%s, %s);", params.get(0), params.get(1));
                break;
            case "addListInt":
                opcode = String.format("%s.add(Double.valueOf(%s));", params.get(1), params.get(0));
                break;
            case "insertListInt":
                opcode = String.format("%s.add((int)(%s), Double.valueOf(%s));", params.get(2), params.get(1), params.get(0));
                break;
            case "getAtListInt":
                opcode = String.format("%s.get((int)(%s)).doubleValue()", params.get(1), params.get(0));
                break;
            case "indexListInt", "indexListStr":
                opcode = String.format("%s.indexOf(%s)", params.get(1), params.get(0));
                break;
            case "containListInt", "containListStr":
                opcode = String.format("%s.contains(%s)", params.get(0), params.get(1));
                break;
            case "addListStr", "addMapToList":
                opcode = String.format("%s.add(%s);", params.get(1), params.get(0));
                break;
            case "insertListStr":
                opcode = String.format("%s.add((int)(%s), %s);", params.get(2), params.get(1), params.get(0));
                break;
            case "getAtListStr":
                opcode = String.format("%s.get((int)(%s))", params.get(1), params.get(0));
                break;
            case "addListMap":
                opcode = String.format("{\r\nHashMap<String, Object> _item = new HashMap<>();\r\n_item.put(%s, %s);\r\n%s.add(_item);\r\n}", params.get(0), params.get(1), params.get(2));
                break;
            case "insertListMap":
                opcode = String.format("{\r\nHashMap<String, Object> _item = new HashMap<>();\r\n_item.put(%s, %s);\r\n%s.add((int)%s, _item);\r\n}", params.get(0), params.get(1), params.get(3), params.get(2));
                break;
            case "getAtListMap":
                opcode = String.format("%s.get((int)%s).get(%s).toString()", params.get(2), params.get(0), params.get(1));
                break;
            case "setListMap":
                opcode = String.format("%s.get((int)%s).put(%s, %s);", params.get(3), params.get(2), params.get(0), params.get(1));
                break;
            case "containListMap":
                opcode = String.format("%s.get((int)%s).containsKey(%s)", params.get(0), params.get(1), params.get(2));
                break;
            case "insertMapToList":
                opcode = String.format("%s.add((int)%s, %s);", params.get(2), params.get(1), params.get(0));

                break;
            case "getMapInList":
                opcode = String.format("%s = %s.get((int)%s);", params.get(2), params.get(1), params.get(0));
                break;
            case "deleteList":
                opcode = String.format("%s.remove((int)(%s));", params.get(1), params.get(0));
                break;
            case "forever":
                int stack = bean.subStack1;
                opcode = String.format("while(true) {\r\n%s\r\n}", stack >= 0 ? a(String.valueOf(stack), "") : "");
                break;
            case "repeat":
                stack = bean.subStack1;
                opcode = String.format("""
                                for(int _repeat%s = 0; _repeat%s < (int)(%s); _repeat%s++) {
                                %s
                                }""",
                        bean.id, bean.id, params.get(0), bean.id,
                        stack >= 0 ? a(String.valueOf(stack), "") : "");
                break;
            case "if":
                stack = bean.subStack1;
                opcode = String.format("if (%s) {\r\n%s\r\n}", params.get(0), stack >= 0 ? a(String.valueOf(stack), "") : "");
                break;
            case "ifElse":
                stack = bean.subStack1;
                String ifBlock = stack >= 0 ? a(String.valueOf(stack), "") : "";
                stack = bean.subStack2;
                String elseBlock = stack >= 0 ? a(String.valueOf(stack), "") : "";
                opcode = String.format("if (%s) {\r\n%s\r\n} else {\r\n%s\r\n}", params.get(0), ifBlock, elseBlock);
                break;
            case "break":
                opcode = "break;";
                break;
            case "true":
            case "false":
                opcode = bean.opCode;
                break;
            case "not":
                opcode = String.format("!%s", params.get(0));
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
            case ">":
            case "<":
                opcode = String.format("%s %s %s", params.get(0), bean.opCode, params.get(1));
                break;
            case "=":
                opcode = String.format("%s == %s", params.get(0), params.get(1));
                break;
            case "&&":
            case "||":
                opcode = String.format("%s %s %s", params.get(0), bean.opCode, params.get(1));
                break;
            case "random":
                opcode = String.format("SketchwareUtil.getRandom((int)(%s), (int)(%s))", params.get(0), params.get(1));
                break;
            case "stringLength":
                opcode = String.format("%s.length()", params.get(0));
                break;
            case "stringJoin":
                opcode = String.format("%s.concat(%s)", params.get(0), params.get(1));
                break;
            case "stringIndex":
                opcode = String.format("%s.indexOf(%s)", params.get(1), params.get(0));
                break;
            case "stringLastIndex":
                opcode = String.format("%s.lastIndexOf(%s)", params.get(1), params.get(0));
                break;
            case "stringSub":
                opcode = String.format("%s.substring((int)(%s), (int)(%s))", params.get(0), params.get(1), params.get(2));
                break;
            case "stringEquals":
                opcode = String.format("%s.equals(%s)", params.get(0), params.get(1));
                break;
            case "stringContains":
                opcode = String.format("%s.contains(%s)", params.get(0), params.get(1));
                break;
            case "stringReplace":
                opcode = String.format("%s.replace(%s, %s)", params.get(0), params.get(1), params.get(2));
                break;
            case "stringReplaceFirst":
                opcode = String.format("%s.replaceFirst(%s, %s)", params.get(0), params.get(1), params.get(2));
                break;
            case "stringReplaceAll":
                opcode = String.format("%s.replaceAll(%s, %s)", params.get(0), params.get(1), params.get(2));
                break;
            case "toNumber":
                String doub = params.get(0);
                doub = (!doub.equals("\"\"")) ? doub : "\"0\"";
                opcode = String.format("Double.parseDouble(%s)", doub);
                break;
            case "currentTime":
                opcode = "System.currentTimeMillis()";
                break;
            case "trim":
                opcode = String.format("%s.trim()", params.get(0));
                break;
            case "toUpperCase":
                opcode = String.format("%s.toUpperCase()", params.get(0));
                break;
            case "toLowerCase":
                opcode = String.format("%s.toLowerCase()", params.get(0));
                break;
            case "toString":
                opcode = String.format("String.valueOf((long)(%s))", params.get(0));
                break;
            case "toStringWithDecimal":
                opcode = String.format("String.valueOf(%s)", params.get(0));
                break;
            case "toStringFormat":
                opcode = String.format("new DecimalFormat(%s).format(%s)", params.get(1), params.get(0));
                break;
            case "addSourceDirectly":
                String asd = bean.parameters.get(0);
                opcode = (asd != null) ? asd : opcode;
                break;
            case "strToMap":
                opcode = String.format("%s = new Gson().fromJson(%s, new TypeToken<HashMap<String, Object>>(){}.getType());", params.get(1), params.get(0));
                break;
            case "mapToStr", "listMapToStr":
                opcode = String.format("new Gson().toJson(%s)", params.get(0));
                break;
            case "strToListMap":
                opcode = String.format("%s = new Gson().fromJson(%s, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());", params.get(1), params.get(0));

                break;
            case "mathGetDip":
                opcode = String.format("SketchwareUtil.getDip(getApplicationContext(), (int)(%s))", params.get(0));
                break;
            case "mathGetDisplayWidth":
                opcode = "SketchwareUtil.getDisplayWidthPixels(getApplicationContext())";
                break;
            case "mathGetDisplayHeight":
                opcode = "SketchwareUtil.getDisplayHeightPixels(getApplicationContext())";
                break;
            case "mathPi":
                opcode = "Math.PI";
                break;
            case "mathE":
                opcode = "Math.E";
                break;
            case "mathPow":
                opcode = String.format("Math.pow(%s, %s)", params.get(0), params.get(1));
                break;
            case "mathMin":
                opcode = String.format("Math.min(%s, %s)", params.get(0), params.get(1));
                break;
            case "mathMax":
                opcode = String.format("Math.max(%s, %s)", params.get(0), params.get(1));
                break;
            case "mathSqrt":
                opcode = String.format("Math.sqrt(%s)", params.get(0));
                break;
            case "mathAbs":
                opcode = String.format("Math.abs(%s)", params.get(0));
                break;
            case "mathRound":
                opcode = String.format("Math.round(%s)", params.get(0));
                break;
            case "mathCeil":
                opcode = String.format("Math.ceil(%s)", params.get(0));
                break;
            case "mathFloor":
                opcode = String.format("Math.floor(%s)", params.get(0));
                break;
            case "mathSin":
                opcode = String.format("Math.sin(%s)", params.get(0));
                break;
            case "mathCos":
                opcode = String.format("Math.cos(%s)", params.get(0));
                break;
            case "mathTan":
                opcode = String.format("Math.tan(%s)", params.get(0));
                break;
            case "mathAsin":
                opcode = String.format("Math.asin(%s)", params.get(0));
                break;
            case "mathAcos":
                opcode = String.format("Math.acos(%s)", params.get(0));
                break;
            case "mathAtan":
                opcode = String.format("Math.atan(%s)", params.get(0));
                break;
            case "mathExp":
                opcode = String.format("Math.exp(%s)", params.get(0));
                break;
            case "mathLog":
                opcode = String.format("Math.log(%s)", params.get(0));
                break;
            case "mathLog10":
                opcode = String.format("Math.log10(%s)", params.get(0));
                break;
            case "mathToRadian":
                opcode = String.format("Math.toRadians(%s)", params.get(0));
                break;
            case "mathToDegree":
                opcode = String.format("Math.toDegrees(%s)", params.get(0));
                break;
            case "viewOnClick":
                String listener = bean.subStack1 >= 0 ? a(String.valueOf(bean.subStack1), "") : "";
                opcode = String.format("%s.setOnClickListener(new View.OnClickListener() {\n@Override\npublic void onClick(View _view) {\n%s\n}\n});", params.get(0), listener);
                break;
            case "isDrawerOpen":
                if (buildConfig.a(activityName).hasDrawer) {
                    opcode = isViewBindingEnabled ? "binding.Drawer.isDrawerOpen(GravityCompat.START)" : "_drawer.isDrawerOpen(GravityCompat.START)";
                } else {
                    opcode = "";
                }
                break;
            case "openDrawer":
                if (buildConfig.a(activityName).hasDrawer) {
                    opcode = isViewBindingEnabled ? "binding.Drawer.openDrawer(GravityCompat.START);" : "_drawer.openDrawer(GravityCompat.START);";
                } else {
                    opcode = "";
                }
                break;
            case "closeDrawer":
                if (buildConfig.a(activityName).hasDrawer) {
                    opcode = isViewBindingEnabled ? "binding.Drawer.closeDrawer(GravityCompat.START);" : "_drawer.closeDrawer(GravityCompat.START);";
                } else {
                    opcode = "";
                }
                break;
            case "setEnable":
                opcode = String.format("%s.setEnabled(%s);", params.get(0), params.get(1));
                break;
            case "getEnable":
                opcode = String.format("%s.isEnabled()", params.get(0));
                break;
            case "setText":
                opcode = String.format("%s.setText(%s);", params.get(0), params.get(1));
                break;
            case "setTypeface":
                String textStyle = params.get(2);
                Pair<Integer, String>[] styles = sq.a("property_text_style");
                for (Pair<Integer, String> style : styles) {
                    if (style.second.equals(textStyle)) {
                        opcode = String.valueOf(style.first);
                        break;
                    }
                }
                String fontName = params.get(1);
                if ("default_font".equals(fontName)) {
                    opcode = String.format("%s.setTypeface(Typeface.DEFAULT, %s);", params.get(0), opcode);
                } else {
                    opcode = String.format("%s.setTypeface(Typeface.createFromAsset(getAssets(),\"fonts/%s.ttf\"), %s);", params.get(0), fontName, opcode);
                }
                break;
            case "getText":
                opcode = String.format("%s.getText().toString()", params.get(0));
                break;
            case "setBgColor":
                opcode = String.format("%s.setBackgroundColor(%s);", params.get(0), params.get(1));
                break;
            case "setBgResource":
                opcode = params.get(1).equals("NONE") ? "0" : "R.drawable." + params.get(1).replaceAll("\\.9", "");
                opcode = String.format("%s.setBackgroundResource(%s);", params.get(0), opcode);
                break;
            case "setTextColor":
                opcode = String.format("%s.setTextColor(%s);", params.get(0), params.get(1));
                break;
            case "setImage":
                String name = params.get(1).replaceAll("\\.9", "");
                opcode = String.format("%s.setImageResource(R.drawable.%s);", params.get(0), name.toLowerCase());
                break;
            case "setColorFilter":
                opcode = String.format("%s.setColorFilter(%s, PorterDuff.Mode.MULTIPLY);", params.get(0), params.get(1));
                break;
            case "requestFocus":
                opcode = String.format("%s.requestFocus();", params.get(0));
                break;
            case "doToast":
                opcode = String.format("SketchwareUtil.showMessage(getApplicationContext(), %s);", params.get(0));
                break;
            case "copyToClipboard":
                opcode = String.format("((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(\"clipboard\", %s));", params.get(0));
                break;
            case "setTitle":
                opcode = String.format("setTitle(%s);", params.get(0));
                break;
            case "intentSetAction":
                opcode = String.format("%s.setAction(%s);", params.get(0), (params.get(1).equals("\"\"") ? "" : "Intent." + params.get(1)));
                break;
            case "intentSetData":
                opcode = String.format("%s.setData(Uri.parse(%s));", params.get(0), params.get(1));
                break;
            case "intentSetScreen":
                opcode = String.format("%s.setClass(getApplicationContext(), %s.class);", params.get(0), params.get(1));
                break;
            case "intentPutExtra":
                opcode = String.format("%s.putExtra(%s, %s);", params.get(0), params.get(1), params.get(2));
                break;
            case "intentSetFlags":
                opcode = String.format("%s.setFlags(%s);", params.get(0), "Intent.FLAG_ACTIVITY_" + params.get(1));
                break;
            case "intentGetString":
                opcode = String.format("getIntent().getStringExtra(%s)", params.get(0));
                break;
            case "startActivity":
                opcode = String.format("startActivity(%s);", params.get(0));
                break;
            case "finishActivity":
                opcode = "finish();";
                break;
            case "fileSetFileName":
                opcode = String.format("%s = getApplicationContext().getSharedPreferences(%s, Activity.MODE_PRIVATE);", params.get(0), params.get(1));
                break;
            case "fileGetData":
                opcode = String.format("%s.getString(%s, \"\")", params.get(0), params.get(1));
                break;
            case "fileSetData":
                opcode = String.format("%s.edit().putString(%s, %s).commit();", params.get(0), params.get(1), params.get(2));
                break;
            case "fileRemoveData":
                opcode = String.format("%s.edit().remove(%s).commit();", params.get(0), params.get(1));
                break;
            case "calendarGetNow":
                opcode = String.format("%s = Calendar.getInstance();", params.get(0));
                break;
            case "calendarAdd":
                opcode = String.format("%s.add(Calendar.%s, (int)(%s));", params.get(0), params.get(1), params.get(2));
                break;
            case "calendarSet":
                opcode = String.format("%s.set(Calendar.%s, (int)(%s));", params.get(0), params.get(1), params.get(2));
                break;
            case "calendarFormat":
                opcode = String.format("new SimpleDateFormat(%s).format(%s.getTime())", (!params.get(1).equals("\"\"")) ? params.get(1) : "\"yyyy/MM/dd hh:mm:ss\"", params.get(0));
                break;
            case "calendarDiff":
                opcode = String.format("(long)(%s.getTimeInMillis() - %s.getTimeInMillis())", params.get(0), params.get(1));
                break;
            case "calendarGetTime":
                opcode = String.format("%s.getTimeInMillis()", params.get(0));
                break;
            case "calendarSetTime":
                opcode = String.format("%s.setTimeInMillis((long)(%s));", params.get(0), params.get(1));
                break;
            case "setVisible":
                opcode = String.format("%s.setVisibility(View.%s);", params.get(0), params.get(1));
                break;
            case "setClickable":
                opcode = String.format("%s.setClickable(%s);", params.get(0), params.get(1));
                break;
            case "setRotate":
                opcode = String.format("%s.setRotation((float)(%s));", params.get(0), params.get(1));
                break;
            case "getRotate":
                opcode = String.format("%s.getRotation()", params.get(0));
                break;
            case "setAlpha":
                opcode = String.format("%s.setAlpha((float)(%s));", params.get(0), params.get(1));
                break;
            case "getAlpha":
                opcode = String.format("%s.getAlpha()", params.get(0));
                break;
            case "setTranslationX":
                opcode = String.format("%s.setTranslationX((float)(%s));", params.get(0), params.get(1));
                break;
            case "getTranslationX":
                opcode = String.format("%s.getTranslationX()", params.get(0));
                break;
            case "setTranslationY":
                opcode = String.format("%s.setTranslationY((float)(%s));", params.get(0), params.get(1));
                break;
            case "getTranslationY":
                opcode = String.format("%s.getTranslationY()", params.get(0));
                break;
            case "setScaleX":
                opcode = String.format("%s.setScaleX((float)(%s));", params.get(0), params.get(1));
                break;
            case "getScaleX":
                opcode = String.format("%s.getScaleX()", params.get(0));
                break;
            case "setScaleY":
                opcode = String.format("%s.setScaleY((float)(%s));", params.get(0), params.get(1));
                break;
            case "getScaleY":
                opcode = String.format("%s.getScaleY()", params.get(0));
                break;
            case "getLocationX":
                opcode = String.format("SketchwareUtil.getLocationX(%s)", params.get(0));
                break;
            case "getLocationY":
                opcode = String.format("SketchwareUtil.getLocationY(%s)", params.get(0));
                break;
            case "setChecked":
                opcode = String.format("%s.setChecked(%s);", params.get(0), params.get(1));
                break;
            case "getChecked":
                opcode = String.format("%s.isChecked()", params.get(0));
                break;
            case "listSetData":
                opcode = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, %s));", params.get(0), params.get(1));
                break;
            case "listSetCustomViewData":
            case "recyclerSetCustomViewData":
            case "spnSetCustomViewData":
            case "pagerSetCustomViewData":
            case "gridSetCustomViewData":
                var param = params.get(0);
                if (param.isEmpty()) {
                    break;
                }
                var paramAdapter = param;
                if (isViewBindingEnabled && paramAdapter.startsWith("binding.")) {
                    paramAdapter = paramAdapter.substring("binding.".length());
                }
                opcode = String.format("%s.setAdapter(new %s(%s));", param, Lx.a(paramAdapter, isViewBindingEnabled), params.get(1));
                break;
            case "listRefresh":
                opcode = String.format("((BaseAdapter)%s.getAdapter()).notifyDataSetChanged();", params.get(0));
                break;
            case "listSetItemChecked":
                opcode = String.format("%s.setItemChecked((int)(%s), %s);", params.get(0), params.get(1), params.get(2));
                break;
            case "listGetCheckedPosition":
                opcode = String.format("%s.getCheckedItemPosition()", params.get(0));
                break;
            case "listGetCheckedPositions":
                opcode = String.format("%s = SketchwareUtil.getCheckedItemPositionsToArray(%s);", params.get(1), params.get(0));
                break;
            case "listGetCheckedCount":
                opcode = String.format("%s.getCheckedItemCount()", params.get(0));
                break;
            case "listSmoothScrollTo":
                opcode = String.format("%s.smoothScrollToPosition((int)(%s));", params.get(0), params.get(1));
                break;
            case "spnSetData":
                opcode = String.format("%s.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, %s));", params.get(0), params.get(1));
                break;
            case "spnRefresh":
                opcode = String.format("((ArrayAdapter)%s.getAdapter()).notifyDataSetChanged();", params.get(0));
                break;
            case "spnSetSelection":
                opcode = String.format("%s.setSelection((int)(%s));", params.get(0), params.get(1));
                break;
            case "spnGetSelection":
                opcode = String.format("%s.getSelectedItemPosition()", params.get(0));
                break;
            case "webViewLoadUrl":
                opcode = String.format("%s.loadUrl(%s);", params.get(0), params.get(1));
                break;
            case "webViewGetUrl":
                opcode = String.format("%s.getUrl()", params.get(0));
                break;
            case "webViewSetCacheMode":
                opcode = String.format("%s.getSettings().setCacheMode(WebSettings.%s);", params.get(0), params.get(1));
                break;
            case "webViewCanGoBack":
                opcode = String.format("%s.canGoBack()", params.get(0));
                break;
            case "webViewCanGoForward":
                opcode = String.format("%s.canGoForward()", params.get(0));
                break;
            case "webViewGoBack":
                opcode = String.format("%s.goBack();", params.get(0));
                break;
            case "webViewGoForward":
                opcode = String.format("%s.goForward();", params.get(0));
                break;
            case "webViewClearCache":
                opcode = String.format("%s.clearCache(true);", params.get(0));
                break;
            case "webViewClearHistory":
                opcode = String.format("%s.clearHistory();", params.get(0));
                break;
            case "webViewStopLoading":
                opcode = String.format("%s.stopLoading();", params.get(0));
                break;
            case "webViewZoomIn":
                opcode = String.format("%s.zoomIn();", params.get(0));
                break;
            case "webViewZoomOut":
                opcode = String.format("%s.zoomOut();", params.get(0));
                break;
            case "calendarViewGetDate":
                opcode = String.format("%s.getDate()", params.get(0));
                break;
            case "calendarViewSetDate":
                opcode = String.format("%s.setDate((long)(%s), true, true);", params.get(0), params.get(1));
                break;
            case "calendarViewSetMinDate":
                opcode = String.format("%s.setMinDate((long)(%s));", params.get(0), params.get(1));
                break;
            case "calnedarViewSetMaxDate":
                opcode = String.format("%s.setMaxDate((long)(%s));", params.get(0), params.get(1));
                break;
            case "adViewLoadAd":
                opcode = String.format("%s.loadAd(new AdRequest.Builder()%s.build());", params.get(0), buildConfig.t.stream().map(device -> ".addTestDevice(\"" + device + "\")\n").collect(Collectors.joining()));
                break;
            case "mapViewSetMapType":
                opcode = String.format("_%s_controller.setMapType(GoogleMap.%s);", params.get(0), params.get(1));
                break;
            case "mapViewMoveCamera":
                opcode = String.format("_%s_controller.moveCamera(%s, %s);", params.get(0), params.get(1), params.get(2));
                break;
            case "mapViewZoomTo":
                opcode = String.format("_%s_controller.zoomTo(%s);", params.get(0), params.get(1));
                break;
            case "mapViewZoomIn":
                opcode = String.format("_%s_controller.zoomIn();", params.get(0));
                break;
            case "mapViewZoomOut":
                opcode = String.format("_%s_controller.zoomOut();", params.get(0));
                break;
            case "mapViewAddMarker":
                opcode = String.format("_%s_controller.addMarker(%s, %s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                break;
            case "mapViewSetMarkerInfo":
                opcode = String.format("_%s_controller.setMarkerInfo(%s, %s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                break;
            case "mapViewSetMarkerPosition":
                opcode = String.format("_%s_controller.setMarkerPosition(%s, %s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                break;
            case "mapViewSetMarkerColor":
                opcode = String.format("_%s_controller.setMarkerColor(%s, BitmapDescriptorFactory.%s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                break;
            case "mapViewSetMarkerIcon":
                name = params.get(2).endsWith(".9") ? params.get(2).replaceAll("\\.9", "") : params.get(2);
                opcode = String.format("_%s_controller.setMarkerIcon(%s, R.drawable.%s);", params.get(0), params.get(1), name.toLowerCase());
                break;
            case "mapViewSetMarkerVisible":
                opcode = String.format("_%s_controller.setMarkerVisible(%s, %s);", params.get(0), params.get(1), params.get(2));
                break;
            case "vibratorAction":
                opcode = String.format("%s.vibrate((long)(%s));", params.get(0), params.get(1));
                break;
            case "timerAfter":
                String onRun = (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "";

                opcode = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.schedule(%s, (int)(%s));", params.get(0), onRun, params.get(0), params.get(1));
                break;
            case "timerEvery":
                onRun = (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "";

                opcode = String.format("%s = new TimerTask() {\n@Override\npublic void run() {\nrunOnUiThread(new Runnable() {\n@Override\npublic void run() {\n%s\n}\n});\n}\n};\n_timer.scheduleAtFixedRate(%s, (int)(%s), (int)(%s));", params.get(0), onRun, params.get(0), params.get(1), params.get(2));
                break;
            case "timerCancel":
                opcode = String.format("%s.cancel();", params.get(0));
                break;
            case "firebaseAdd":
                opcode = String.format("%s.child(%s).updateChildren(%s);", params.get(0), params.get(1), params.get(2));
                break;
            case "firebasePush":
                opcode = String.format("%s.push().updateChildren(%s);", params.get(0), params.get(1));
                break;
            case "firebaseGetPushKey":
                opcode = String.format("%s.push().getKey()", params.get(0));
                break;
            case "firebaseDelete":
                opcode = String.format("%s.child(%s).removeValue();", params.get(0), params.get(1));
                break;
            case "firebaseGetChildren":
                opcode = String.format("""
                        %s.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot _dataSnapshot) {
                        %s = new ArrayList<>();
                        try {
                        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        for (DataSnapshot _data : _dataSnapshot.getChildren()) {
                        HashMap<String, Object> _map = _data.getValue(_ind);
                        %s.add(_map);
                        }
                        } catch (Exception _e) {
                        _e.printStackTrace();
                        }
                        %s
                        }
                        @Override
                        public void onCancelled(DatabaseError _databaseError) {
                        }
                        });""", params.get(0), params.get(1), params.get(1), (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "");
                break;
            case "firebaseauthCreateUser":
                if (!params.get(1).equals("\"\"") && !params.get(2).equals("\"\"")) {
                    opcode = String.format("%s.createUserWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", params.get(0), params.get(1), params.get(2), activityName, "_" + params.get(0) + "_create_user_listener");
                }
                break;
            case "firebaseauthSignInUser":
                if (!params.get(1).equals("\"\"") && !params.get(2).equals("\"\"")) {
                    opcode = String.format("%s.signInWithEmailAndPassword(%s, %s).addOnCompleteListener(%s.this, %s);", params.get(0), params.get(1), params.get(2), activityName, "_" + params.get(0) + "_sign_in_listener");
                }
                break;
            case "firebaseauthSignInAnonymously":
                opcode = String.format("%s.signInAnonymously().addOnCompleteListener(%s.this, %s);", params.get(0), activityName, "_" + params.get(0) + "_sign_in_listener");
                break;
            case "firebaseauthIsLoggedIn":
                opcode = "(FirebaseAuth.getInstance().getCurrentUser() != null)";
                break;
            case "firebaseauthGetCurrentUser":
                opcode = "FirebaseAuth.getInstance().getCurrentUser().getEmail()";
                break;
            case "firebaseauthGetUid":
                opcode = "FirebaseAuth.getInstance().getCurrentUser().getUid()";
                break;
            case "firebaseauthResetPassword":
                if (!params.get(1).equals("\"\"")) {
                    opcode = String.format("%s.sendPasswordResetEmail(%s).addOnCompleteListener(%s);", params.get(0), params.get(1), "_" + params.get(0) + "_reset_password_listener");
                }
                break;
            case "firebaseauthSignOutUser":
                opcode = "FirebaseAuth.getInstance().signOut();";
                break;
            case "firebaseStartListen":
                opcode = String.format("%s.addChildEventListener(_%s_child_listener);", params.get(0), params.get(0));
                break;
            case "firebaseStopListen":
                opcode = String.format("%s.removeEventListener(_%s_child_listener);", params.get(0), params.get(0));
                break;
            case "gyroscopeStartListen":
                opcode = String.format("%s.registerListener(_%s_sensor_listener, %s.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);", params.get(0), params.get(0), params.get(0));
                break;
            case "gyroscopeStopListen":
                opcode = String.format("%s.unregisterListener(_%s_sensor_listener);", params.get(0), params.get(0));
                break;
            case "dialogSetTitle":
                opcode = String.format("%s.setTitle(%s);", params.get(0), params.get(1));
                break;
            case "dialogSetMessage":
                opcode = String.format("%s.setMessage(%s);", params.get(0), params.get(1));
                break;
            case "dialogShow":
                opcode = String.format("%s.create().show();", params.get(0));
                break;
            case "dialogOkButton":
                String onClick = (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "";

                opcode = String.format("%s.setPositiveButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", params.get(0), params.get(1), onClick);
                break;
            case "dialogCancelButton":
                onClick = (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "";

                opcode = String.format("%s.setNegativeButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", params.get(0), params.get(1), onClick);
                break;
            case "dialogNeutralButton":
                onClick = (bean.subStack1 >= 0) ? a(String.valueOf(bean.subStack1), "") : "";

                opcode = String.format("%s.setNeutralButton(%s, new DialogInterface.OnClickListener() {\n@Override\npublic void onClick(DialogInterface _dialog, int _which) {\n%s\n}\n});", params.get(0), params.get(1), onClick);
                break;
            case "mediaplayerCreate":
                opcode = String.format("%s = MediaPlayer.create(getApplicationContext(), R.raw.%s);", params.get(0), params.get(1).toLowerCase());
                break;
            case "mediaplayerStart":
                opcode = String.format("%s.start();", params.get(0));
                break;
            case "mediaplayerPause":
                opcode = String.format("%s.pause();", params.get(0));
                break;
            case "mediaplayerSeek":
                opcode = String.format("%s.seekTo((int)(%s));", params.get(0), params.get(1));
                break;
            case "mediaplayerGetCurrent":
                opcode = String.format("%s.getCurrentPosition()", params.get(0));
                break;
            case "mediaplayerGetDuration":
                opcode = String.format("%s.getDuration()", params.get(0));
                break;
            case "mediaplayerReset":
                opcode = String.format("%s.reset();", params.get(0));
                break;
            case "mediaplayerRelease":
                opcode = String.format("%s.release();", params.get(0));

                break;
            case "mediaplayerIsPlaying":
                opcode = String.format("%s.isPlaying()", params.get(0));

                break;
            case "mediaplayerSetLooping":
                opcode = String.format("%s.setLooping(%s);", params.get(0), params.get(1));
                break;
            case "mediaplayerIsLooping":
                opcode = String.format("%s.isLooping()", params.get(0));
                break;
            case "soundpoolCreate":
                opcode = String.format("%s = new SoundPool((int)(%s), AudioManager.STREAM_MUSIC, 0);", params.get(0), params.get(1));
                break;
            case "soundpoolLoad":
                opcode = String.format("%s.load(getApplicationContext(), R.raw.%s, 1);", params.get(0), params.get(1));
                break;
            case "soundpoolStreamPlay":
                opcode = String.format("%s.play((int)(%s), 1.0f, 1.0f, 1, (int)(%s), 1.0f);", params.get(0), params.get(1), params.get(2));

                break;
            case "soundpoolStreamStop":
                opcode = String.format("%s.stop((int)(%s));", params.get(0), params.get(1));
                break;
            case "setThumbResource":
                name = params.get(1).replaceAll("\\.9", "");
                opcode = String.format("%s.setThumbResource(R.drawable.%s)", params.get(0), name.toLowerCase());
                break;
            case "setTrackResource":
                name = params.get(1).replaceAll("\\.9", "");
                opcode = String.format("%s.setTrackResource(R.drawable.%s)", params.get(0), name.toLowerCase());

                break;
            case "seekBarSetProgress":
                opcode = String.format("%s.setProgress((int)%s);", params.get(0), params.get(1));

                break;
            case "seekBarGetProgress":
                opcode = String.format("%s.getProgress()", params.get(0));

                break;
            case "seekBarSetMax":
                opcode = String.format("%s.setMax((int)%s);", params.get(0), params.get(1));

                break;
            case "seekBarGetMax":
                opcode = String.format("%s.getMax()", params.get(0));

                break;
            case "objectanimatorSetTarget":
                opcode = String.format("%s.setTarget(%s);", params.get(0), params.get(1));

                break;
            case "objectanimatorSetProperty":
                opcode = String.format("%s.setPropertyName(\"%s\");", params.get(0), params.get(1));
                break;
            case "objectanimatorSetValue":
                opcode = String.format("%s.setFloatValues((float)(%s));", params.get(0), params.get(1));
                break;
            case "objectanimatorSetFromTo":
                opcode = String.format("%s.setFloatValues((float)(%s), (float)(%s));", params.get(0), params.get(1), params.get(2));
                break;
            case "objectanimatorSetDuration":
                opcode = String.format("%s.setDuration((int)(%s));", params.get(0), params.get(1));
                break;
            case "objectanimatorSetRepeatMode":
                opcode = String.format("%s.setRepeatMode(ValueAnimator.%s);", params.get(0), params.get(1));

                break;
            case "objectanimatorSetRepeatCount":
                opcode = String.format("%s.setRepeatCount((int)(%s));", params.get(0), params.get(1));
                break;
            case "objectanimatorSetInterpolator":
                String interpolator = switch (params.get(1)) {
                    case "Accelerate" -> "new AccelerateInterpolator()";
                    case "Decelerate" -> "new DecelerateInterpolator()";
                    case "AccelerateDeccelerate" -> "new AccelerateDecelerateInterpolator()";
                    case "Bounce" -> "new BounceInterpolator()";
                    default -> "new LinearInterpolator()";
                };
                opcode = String.format("%s.setInterpolator(%s);", params.get(0), interpolator);
                break;
            case "objectanimatorStart":
                opcode = String.format("%s.start();", params.get(0));
                break;
            case "objectanimatorCancel":
                opcode = String.format("%s.cancel();", params.get(0));
                break;
            case "objectanimatorIsRunning":
                opcode = String.format("%s.isRunning()", params.get(0));
                break;
            case "interstitialadCreate":
            case "interstitialadLoadAd":
            case "interstitialadShow":
                opcode = "";
                break;
            case "firebasestorageUploadFile":
                if (!params.get(1).equals("\"\"") && !params.get(2).equals("\"\"")) {
                    opcode = String.format("%s.child(%s).putFile(Uri.fromFile(new File(%s))).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {\n@Override\npublic Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {\nreturn %s.child(%s).getDownloadUrl();\n}}).addOnCompleteListener(_%s_upload_success_listener);", params.get(0), params.get(2), params.get(1), params.get(0), params.get(0), params.get(0), params.get(2), params.get(0));
                }
                break;
            case "firebasestorageDownloadFile":
                if (!params.get(1).equals("\"\"") && !params.get(2).equals("\"\"")) {
                    opcode = String.format("_firebase_storage.getReferenceFromUrl(%s).getFile(new File(%s)).addOnSuccessListener(_%s_download_success_listener).addOnFailureListener(_%s_failure_listener).addOnProgressListener(_%s_download_progress_listener);", params.get(1), params.get(2), params.get(0), params.get(0), params.get(0));
                }
                break;
            case "firebasestorageDelete":
                if (!params.get(1).equals("\"\"")) {
                    opcode = String.format("_firebase_storage.getReferenceFromUrl(%s).delete().addOnSuccessListener(_%s_delete_success_listener).addOnFailureListener(_%s_failure_listener);", params.get(1), params.get(0), params.get(0));
                }
                break;
            case "fileutilread":

                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.readFile(%s)", params.get(0));
                }
                break;
            case "fileutilwrite":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.writeFile(%s, %s);", params.get(1), params.get(0));
                }
                break;
            case "fileutilcopy":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.copyFile(%s, %s);", params.get(0), params.get(1));
                }
                break;
            case "fileutilmove":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.moveFile(%s, %s);", params.get(0), params.get(1));
                }
                break;
            case "fileutildelete":

                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.deleteFile(%s);", params.get(0));
                }
                break;
            case "fileutilisexist":

                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.isExistFile(%s)", params.get(0));
                }
                break;
            case "fileutilmakedir":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.makeDir(%s);", params.get(0));
                }
                break;
            case "fileutillistdir":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.listDir(%s, %s);", params.get(0), params.get(1));
                }
                break;
            case "fileutilisdir":
                if (!opcode.equals("\"\"")) {
                    opcode = String.format("FileUtil.isDirectory(%s)", params.get(0));
                }
                break;
            case "fileutilisfile":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.isFile(%s)", params.get(0));
                }
                break;
            case "fileutillength":

                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.getFileLength(%s)", params.get(0));
                }
                break;
            case "fileutilStartsWith":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("%s.startsWith(%s)", params.get(0), params.get(1));
                }
                break;
            case "fileutilEndsWith":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("%s.endsWith(%s)", params.get(0), params.get(1));
                }
                break;
            case "fileutilGetLastSegmentPath":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("Uri.parse(%s).getLastPathSegment()", params.get(0));
                }
                break;
            case "getExternalStorageDir":
                opcode = "FileUtil.getExternalStorageDir()";
                break;
            case "getPackageDataDir":
                opcode = "FileUtil.getPackageDataDir(getApplicationContext())";
                break;
            case "getPublicDir":
                opcode = String.format("FileUtil.getPublicDir(Environment.%s)", params.get(0));
                break;
            case "resizeBitmapFileRetainRatio":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.resizeBitmapFileRetainRatio(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "resizeBitmapFileToSquare":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.resizeBitmapFileToSquare(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "resizeBitmapFileToCircle":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.resizeBitmapFileToCircle(%s, %s);", params.get(0), params.get(1));
                }
                break;
            case "resizeBitmapFileWithRoundedBorder":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.resizeBitmapFileWithRoundedBorder(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "cropBitmapFileFromCenter":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.cropBitmapFileFromCenter(%s, %s, %s, %s);", params.get(0), params.get(1), params.get(3), params.get(2));
                }
                break;
            case "rotateBitmapFile":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.rotateBitmapFile(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "scaleBitmapFile":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.scaleBitmapFile(%s, %s, %s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                }
                break;
            case "skewBitmapFile":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.skewBitmapFile(%s, %s, %s, %s);", params.get(0), params.get(1), params.get(2), params.get(3));
                }
                break;
            case "setBitmapFileColorFilter":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.setBitmapFileColorFilter(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "setBitmapFileBrightness":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.setBitmapFileBrightness(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "setBitmapFileContrast":
                if (!params.get(0).equals("\"\"") && !params.get(1).equals("\"\"")) {
                    opcode = String.format("FileUtil.setBitmapFileContrast(%s, %s, %s);", params.get(0), params.get(1), params.get(2));
                }
                break;
            case "getJpegRotate":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("FileUtil.getJpegRotate(%s)", params.get(0));
                }
                break;
            case "filepickerstartpickfiles":
                opcode = String.format("startActivityForResult(%s, REQ_CD_%s);", params.get(0), params.get(0).toUpperCase());
                break;
            case "camerastarttakepicture":
                opcode = String.format("startActivityForResult(%s, REQ_CD_%s);", params.get(0), params.get(0).toUpperCase());
                break;
            case "setImageFilePath":
                if (!params.get(1).equals("\"\"")) {
                    opcode = String.format("%s.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(%s, 1024, 1024));", params.get(0), params.get(1));
                }
                break;
            case "setImageUrl":
                if (!params.get(1).equals("\"\"")) {
                    opcode = String.format("Glide.with(getApplicationContext()).load(Uri.parse(%s)).into(%s);", params.get(1), params.get(0));
                }
                break;
            case "setHint":
                if (!params.get(0).equals("\"\"")) {
                    opcode = String.format("%s.setHint(%s);", params.get(0), params.get(1));
                }
                break;
            case "setHintTextColor":
                if (!params.get(1).equals("\"\"")) {
                    opcode = String.format("%s.setHintTextColor(%s);", params.get(0), params.get(1));
                }
                break;
            case "requestnetworkSetParams":
                opcode = String.format("%s.setParams(%s, RequestNetworkController.%s);", params.get(0), params.get(1), params.get(2));
                break;
            case "requestnetworkSetHeaders":
                opcode = String.format("%s.setHeaders(%s);", params.get(0), params.get(1));
                break;
            case "requestnetworkStartRequestNetwork":
                opcode = String.format("%s.startRequestNetwork(RequestNetworkController.%s, %s, %s, _%s_request_listener);", params.get(0), params.get(1), params.get(2), params.get(3), params.get(0));
                break;
            case "progressBarSetIndeterminate":
                opcode = String.format("%s.setIndeterminate(%s);", params.get(0), params.get(1));
                break;
            case "textToSpeechSetPitch":
                opcode = String.format("%s.setPitch((float)%s);", params.get(0), params.get(1));
                break;
            case "textToSpeechSetSpeechRate":
                opcode = String.format("%s.setSpeechRate((float)%s);", params.get(0), params.get(1));
                break;
            case "textToSpeechSpeak":
                opcode = String.format("%s.speak(%s, TextToSpeech.QUEUE_ADD, null);", params.get(0), params.get(1));
                break;
            case "textToSpeechIsSpeaking":
                opcode = String.format("%s.isSpeaking()", params.get(0));

                break;
            case "textToSpeechStop":
                opcode = String.format("%s.stop();", params.get(0));

                break;
            case "textToSpeechShutdown":
                opcode = String.format("%s.shutdown();", params.get(0));

                break;
            case "speechToTextStartListening":
                opcode = String.format("Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);\n_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);\n_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());\n%s.startListening(_intent);", params.get(0));

                break;
            case "speechToTextStopListening":
                opcode = String.format("%s.stopListening();", params.get(0));
                break;
            case "speechToTextShutdown":
                opcode = String.format("%s.cancel();\n%s.destroy();", params.get(0), params.get(0));
                break;
            case "bluetoothConnectReadyConnection":
                opcode = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s);", params.get(0), params.get(0), params.get(1));
                break;
            case "bluetoothConnectReadyConnectionToUuid":
                opcode = String.format("%s.readyConnection(_%s_bluetooth_connection_listener, %s, %s);", params.get(0), params.get(0), params.get(1), params.get(2));
                break;
            case "bluetoothConnectStartConnection":
                opcode = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s);", params.get(0), params.get(0), params.get(1), params.get(2));
                break;
            case "bluetoothConnectStartConnectionToUuid":
                opcode = String.format("%s.startConnection(_%s_bluetooth_connection_listener, %s, %s, %s);", params.get(0), params.get(0), params.get(1), params.get(2), params.get(3));
                break;
            case "bluetoothConnectStopConnection":
                opcode = String.format("%s.stopConnection(_%s_bluetooth_connection_listener, %s);", params.get(0), params.get(0), params.get(1));
                break;
            case "bluetoothConnectSendData":
                opcode = String.format("%s.sendData(_%s_bluetooth_connection_listener, %s, %s);", params.get(0), params.get(0), params.get(1), params.get(2));
                break;
            case "bluetoothConnectIsBluetoothEnabled":
                opcode = String.format("%s.isBluetoothEnabled()", params.get(0));
                break;
            case "bluetoothConnectIsBluetoothActivated":
                opcode = String.format("%s.isBluetoothActivated()", params.get(0));
                break;
            case "bluetoothConnectActivateBluetooth":
                opcode = String.format("%s.activateBluetooth();", params.get(0));

                break;
            case "bluetoothConnectGetPairedDevices":
                opcode = String.format("%s.getPairedDevices(%s);", params.get(0), params.get(1));

                break;
            case "bluetoothConnectGetRandomUuid":
                opcode = params.get(0) + ".getRandomUUID()";

                break;

            case "locationManagerRequestLocationUpdates":
                String locationRequest = "%s.requestLocationUpdates(LocationManager.%s, %s, %s, _%s_location_listener);";
                if (buildConfig.g) {
                    opcode = String.format("if (ContextCompat.checkSelfPermission(%s.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n" + locationRequest + "\n}", activityName, params.get(0), params.get(1), params.get(2), params.get(3), params.get(0));
                } else {
                    opcode = String.format("if (Build.VERSION.SDK_INT >= 23) {if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {\n" + locationRequest + "\n}\n}\nelse {\n" + locationRequest + "\n}", params.get(0), params.get(1), params.get(2), params.get(3), params.get(0), params.get(0), params.get(1), params.get(2), opcode, params.get(0));
                }
                break;

            case "locationManagerRemoveUpdates":
                opcode = params.get(0) + ".removeUpdates(_" + params.get(0) + "_location_listener);";
                break;
            default:
                opcode = getCodeExtraBlock(bean, "\"\"");
        }
        
        /*
          switch block above should be responsible for handling %m param.
          However, upon decompiling this class, it completely ignore this case.
          This is the solution for now to prevent errors during code generation.
         */
        if (hasEmptySelectorParam(params, bean.spec)) {
            opcode = "";
        }
        return opcode;
    }

    private String getCodeExtraBlock(BlockBean blockBean, String var2) {
        ArrayList<String> parameters = new ArrayList<>();
        ArrayList<String> paramsTypes = extractParamsTypes(blockBean.spec);

        for (int i = 0; i < blockBean.parameters.size(); i++) {
            String parameterValue = getParamValue(blockBean.parameters.get(i), viewParamsTypes.contains(paramsTypes.get(i)));

            switch (getBlockType(blockBean, i)) {
                case 0:
                    if (parameterValue.isEmpty()) {
                        parameters.add("true");
                    } else {
                        parameters.add(a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                case 1:
                    if (parameterValue.isEmpty()) {
                        parameters.add("0");
                    } else {
                        parameters.add(a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                case 2:
                    if (parameterValue.isEmpty()) {
                        parameters.add("\"\"");
                    } else {
                        parameters.add(a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
                    break;

                default:
                    if (parameterValue.isEmpty()) {
                        parameters.add("");
                    } else {
                        parameters.add(a(parameterValue, getBlockType(blockBean, i), blockBean.opCode));
                    }
            }
        }

        if (blockBean.subStack1 >= 0) {
            parameters.add(a(String.valueOf(blockBean.subStack1), var2));
        } else {
            parameters.add(" ");
        }

        if (blockBean.subStack2 >= 0) {
            parameters.add(a(String.valueOf(blockBean.subStack2), var2));
        } else {
            parameters.add(" ");
        }

        ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(blockBean.opCode);

        if (blockInfo.isMissing) {
            blockInfo = BlockLoader.getBlockFromProject(buildConfig.sc_id, blockBean.opCode);
        }

        String formattedCode;
        if (!parameters.isEmpty()) {
            try {
                formattedCode = String.format(blockInfo.getCode(), parameters.toArray(new Object[0]));
            } catch (Exception e) {
                formattedCode = "/* Failed to resolve Custom Block's code: " + e + " */";
            }
        } else {
            formattedCode = blockInfo.getCode();
        }

        return formattedCode;
    }

    private int getBlockType(BlockBean blockBean, int parameterIndex) {
        int blockType;

        Gx paramClassInfo = blockBean.getParamClassInfo().get(parameterIndex);

        if (paramClassInfo.b("boolean")) {
            blockType = 0;
        } else if (paramClassInfo.b("double")) {
            blockType = 1;
        } else if (paramClassInfo.b("String")) {
            blockType = 2;
        } else {
            blockType = 3;
        }

        return blockType;
    }
}
