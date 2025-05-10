package pro.sketchware.blocks.generator.matchers;

import android.util.Log;
import android.util.Pair;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.besome.sketch.beans.BlockBean;

import a.a.a.Fx;
import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ExtraBlockMatcher {

    private final BlockParamUtil blockParamUtil;
    private final AtomicInteger idCounter;
    private final ExpressionBlockBuilder expressionBlockBuilder;

    public ExtraBlockMatcher(
            BlockParamUtil blockParamUtil,
            AtomicInteger idCounter,
            ExpressionBlockBuilder expressionBlockBuilder
    ) {
        this.blockParamUtil = blockParamUtil;
        this.idCounter = idCounter;
        this.expressionBlockBuilder = expressionBlockBuilder;
    }

    public ArrayList<BlockBean> tryExtraBlockMatch(String input, int id, int nextId, ArrayList<HashMap<String, Object>> targetBlocks) {
        String norm = input.trim();
        for (var map : targetBlocks) {
            String code = TranslatorUtils.safeGetString(map.get("code")).trim();
            String spec = TranslatorUtils.safeGetString(map.get("spec")).trim();
            BlockCodeReformater blockCodeReformater = new BlockCodeReformater(code, norm);
            norm = blockCodeReformater.getFormattedInputCode();
            code = blockCodeReformater.getFormattedBlockCode();
            Pair<ArrayList<String>, ArrayList<String>> blockParamInfo = blockParamUtil.getBlockParamInfo(spec, code, norm);
            ArrayList<String> params = blockParamInfo.first;
            ArrayList<String> paramsHolders = blockParamInfo.second;
            if (params.size() != paramsHolders.size() || blockParamUtil.isParamOnly(code)) {
                continue;
            }
            String formatted = params.isEmpty() ? code : String.format(code, params.toArray());
            boolean isCodeEquivalent = formatted.equals(norm);
            Log.d("BlocksGenerator", "comparing block " + map.get("name") + " : " + formatted + " and " + norm + " result : " + isCodeEquivalent + " params : " + params + " paramsHolders :" + paramsHolders + " original block code " + code + "map content: " + map);
            if (isCodeEquivalent && blockParamUtil.isMatchesParamsTypes(params, paramsHolders)) {
                BlockBean b = new BlockBean(
                        String.valueOf(id),
                        TranslatorUtils.safeGetString(map.get("spec")),
                        TranslatorUtils.safeGetString(map.get("type")),
                        TranslatorUtils.safeGetString(map.get("typeName")),
                        TranslatorUtils.safeGetString(map.get("name"))
                );

                ArrayList<BlockBean> resultBlocks = new ArrayList<>();

                boolean isCompatibleParams = true;

                for (int i = 0; i < params.size(); i++) {
                    String paramHolder = paramsHolders.size() > i ? paramsHolders.get(i) : "%s";
                    String paramValue = params.get(i);

                    boolean isStringParam = TranslatorUtils.isLiteralString(paramValue);
                    boolean isDirectValue = paramHolder.equals("%s.inputOnly") || paramHolder.startsWith("%m.") ||
                                            (paramHolder.startsWith("%s") && isStringParam || paramValue.matches("^-?\\d+(\\.\\d+)?$"));

                    if (isDirectValue) {
                        if (isStringParam) {
                            b.parameters.add(paramValue.substring(1, paramValue.length() - 1));
                        } else {
                            if (Fx.viewParamsTypes.contains(paramHolder)) {
                                paramValue = paramValue.substring("binding.".length());
                            }
                            b.parameters.add(paramValue);
                        }
                    } else {
                        Expression expr = StaticJavaParser.parseExpression(paramValue);
                        String methodName = "";
                        if (expr.isMethodCallExpr()) {
                            methodName = expr.asMethodCallExpr().getNameAsString();
                        }
                        RequiredBlockType reqType = blockParamUtil.getRequiredBlockType(methodName, code, i, b);
                        if (reqType == null) {
                            isCompatibleParams = false;
                            break;
                        }
                        int paramBlockId = idCounter.getAndIncrement();
                        ArrayList<BlockBean> paramBlocks = expressionBlockBuilder.build(
                                expr,
                                paramBlockId,
                                reqType,
                                paramValue
                        );
                        if (!paramBlocks.isEmpty()) {
                            BlockBean last = paramBlocks.get(paramBlocks.size() - 1);
                            last.nextBlock = -1;
                            resultBlocks.addAll(paramBlocks);
                            b.parameters.add("@" + last.id);
                        } else {
                            b.parameters.add(paramValue);
                        }
                    }
                }

                if (isCompatibleParams) {
                    b.nextBlock = nextId == -1 ? -1 : idCounter.get();
                    resultBlocks.add(b);
                    return resultBlocks;
                }
            }
        }
        return null;
    }

}
