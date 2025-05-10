package pro.sketchware.blocks.generator.utils;

import android.util.Log;
import android.util.Pair;

import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.Fx;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.resources.ProjectResourcesHelper;

public class BlockParamUtil {

    private final ProjectResourcesHelper projectResourcesHelper;

    private final Pattern paramHolderPattern = Pattern.compile(TranslatorUtils.getParamHolderRegex());

    public BlockParamUtil(ProjectResourcesHelper projectResourcesHelper) {
        this.projectResourcesHelper = projectResourcesHelper;
    }

    public Pair<ArrayList<String>, ArrayList<String>> getBlockParamInfo(String blockSpec, String blockCode, String input) {
        ArrayList<String> params = extractParams(blockCode, input);
        ArrayList<String> paramsHolders = extractParamsHolders(blockSpec);
        ArrayList<String> secondParamsHolders = extractParamsHolders(blockCode);
        return new Pair<>(reorderParamsByHolders(secondParamsHolders, params), paramsHolders);
    }

    private ArrayList<String> extractParams(String pattern, String input) {
        ArrayList<String> results = new ArrayList<>();
        Matcher m = paramHolderPattern.matcher(pattern);
        StringBuilder regexBuilder = new StringBuilder();
        int last = 0;

        while (m.find()) {
            regexBuilder.append(Pattern.quote(pattern.substring(last, m.start())))
                    .append("(.+)");
            last = m.end();
        }
        regexBuilder.append(Pattern.quote(pattern.substring(last)));

        Pattern fullPattern = Pattern.compile(regexBuilder.toString());
        Matcher fm = fullPattern.matcher(input);

        if (fm.find()) {
            for (int i = 1; i <= fm.groupCount(); i++) {
                results.add(TranslatorUtils.safeGetString(fm.group(i)).trim());
            }
        }
        return results;
    }

    public ArrayList<String> extractParamsHolders(String pattern) {
        ArrayList<String> results = new ArrayList<>();
        Matcher m = paramHolderPattern.matcher(pattern);

        while (m.find()) {
            results.add(pattern.substring(m.start(), m.end()));
        }
        return results;
    }

    public boolean isParamOnly(String input) {
        input = input.trim();

        return input.matches("^" + TranslatorUtils.getParamHolderRegex() + "$");
    }

    private ArrayList<String> reorderParamsByHolders(ArrayList<String> paramsHolders, ArrayList<String> params) {
        try {
            ArrayList<String> result = new ArrayList<>(paramsHolders.size());
            for (int i = 0; i < paramsHolders.size(); i++) result.add("");

            Pattern indexPattern = Pattern.compile("%(\\d+)\\$[sdb]");
            int paramIndex = 0;

            for (int i = 0; i < paramsHolders.size(); i++) {
                String holder = paramsHolders.get(i);
                Matcher m = indexPattern.matcher(holder);
                if (m.find()) {
                    int targetIndex = Integer.parseInt(TranslatorUtils.safeGetString(m.group(1))) - 1;
                    if (targetIndex < result.size()) {
                        result.set(targetIndex, params.get(i));
                    }
                } else {
                    while (paramIndex < result.size() && !result.get(paramIndex).isEmpty()) {
                        paramIndex++;
                    }
                    if (paramIndex < result.size()) {
                        result.set(paramIndex, params.get(i));
                        paramIndex++;
                    }
                }
            }

            return result;
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }

    public boolean isMatchesParamsTypes(ArrayList<String> params, ArrayList<String> paramsHolders) {
        for (int i = 0; i < params.size(); i++) {
            if (!isMatchesParamType(params.get(i), paramsHolders.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isMatchesParamType(String param, String paramHolder) {
        if (paramHolder.equals("%s.inputOnly")) {
            return true;
        }
        if (TranslatorUtils.isCastExpression(param)) {
            return false;
        }
        if (param.startsWith("binding.")) {
            param = param.substring("binding.".length());
        }
        if (Fx.viewParamsTypes.contains(paramHolder)) {
            return projectResourcesHelper.getFields(projectResourcesHelper.WIDGET_FIELDS).contains(param);
        }
        return switch (paramHolder) {
            case "%s" ->
                    TranslatorUtils.isLiteralString(param) || !projectResourcesHelper.getAllFieldsExcept(projectResourcesHelper.STRING_FIELDS).contains(param);
            case "%d" ->
                    TranslatorUtils.isLiteralNumber(param) || !projectResourcesHelper.getAllFieldsExcept(projectResourcesHelper.DOUBLE_FIELDS).contains(param);
            case "%b" ->
                    TranslatorUtils.isLiteralBoolean(param) || !projectResourcesHelper.getAllFieldsExcept(projectResourcesHelper.BOOLEAN_FIELDS).contains(param);
            case "%m.varMap" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.MAP_FIELDS).contains(param);
            case "%m.list" -> projectResourcesHelper.getFields(
                    projectResourcesHelper.LIST_STRING_FIELDS,
                    projectResourcesHelper.LIST_INT_FIELDS,
                    projectResourcesHelper.LIST_MAP_FIELDS,
                    projectResourcesHelper.LIST_CUSTOM_FIELDS
            ).contains(param);
            case "%m.varBool" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.BOOLEAN_FIELDS).contains(param);
            case "%m.varStr" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.STRING_FIELDS).contains(param);
            case "%m.varInt" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.DOUBLE_FIELDS).contains(param);
            case "%m.listStr" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.LIST_STRING_FIELDS).contains(param);
            case "%m.listInt" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.LIST_INT_FIELDS).contains(param);
            case "%m.listMap" ->
                    projectResourcesHelper.getFields(projectResourcesHelper.LIST_MAP_FIELDS).contains(param);
            default -> false;
        };
    }

    public RequiredBlockType getRequiredBlockType(String methodName, String blockCode, int targetPosition, BlockBean block) {
        Pair<String, String> moreBlockType = projectResourcesHelper.getMoreBlockType(methodName);
        RequiredBlockType foundedType = null;
        RequiredBlockType expectedRequireType = null;
        if (moreBlockType != null) {
            expectedRequireType = new RequiredBlockType(moreBlockType.first, moreBlockType.second);
        }
        List<String> holders = getBlockParamInfo(block.spec, blockCode, "").second;
        if (targetPosition < holders.size()) {
            String holder = holders.get(targetPosition);
            if (holder.equals("%s")) {
                foundedType = new RequiredBlockType("s");
            } else if (holder.equals("%d")) {
                foundedType = new RequiredBlockType("d");
            } else if (holder.equals("%b")) {
                foundedType = new RequiredBlockType("b");
            } else if (holder.matches("%m\\.(\\w+)")) {
                String typeName = holder.substring(3);
                foundedType = new RequiredBlockType("v", typeName);
            }
        }
        if (foundedType != null && expectedRequireType != null) {
            if (foundedType.equals(expectedRequireType)) {
                return expectedRequireType;
            } else {
                Log.wtf("BlocksGenerator",  block.opCode + " has non-compatible param at " + targetPosition + " type with moreBlock : " + methodName + " expected " + expectedRequireType + " founded " + foundedType);
                return null;
            }
        } else {
            return foundedType;
        }
    }

}
