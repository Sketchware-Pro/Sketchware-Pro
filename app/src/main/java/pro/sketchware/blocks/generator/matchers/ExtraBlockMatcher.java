package pro.sketchware.blocks.generator.matchers;

import android.util.Log;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.besome.sketch.beans.BlockBean;

import a.a.a.Fx;
import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
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

    public ArrayList<BlockBean> tryExtraBlockMatch(String input, boolean hasNext, ArrayList<HashMap<String, Object>> targetBlocks) {
        int startId = idCounter.get();
        String norm = input.trim();
        ArrayList<BlockBean> fallbackBean = new ArrayList<>();

        for (var map : targetBlocks) {
            String code = TranslatorUtils.safeGetString(map.get("code")).trim();
            String spec = TranslatorUtils.safeGetString(map.get("spec")).trim();

            BlockCodeMatcher matcher = new BlockCodeMatcher(spec, code, norm, blockParamUtil);

            Log.d("BlocksGenerator", String.format("""
                    
                    |---------------------------------------
                         |------block name "%s" : %s and %s
                         |------result : %s
                         |------params : %s
                         |------paramsHolders :%s
                         |------block map content: %s
                    |---------------------------------------
                    """,
                    map.get("name"), matcher.getFormattedBlockCode(),
                    matcher.getFormattedInputCode(),
                    matcher.isMatch() ? "matching ✅" : "failed ❌",
                    matcher.getParams(),
                    matcher.getParamsHolders(),
                    map
            ));

            if (matcher.isMatch()) {
                BlockBean b = new BlockBean(
                        String.valueOf(idCounter.getAndIncrement()),
                        TranslatorUtils.safeGetString(map.get("spec")),
                        TranslatorUtils.safeGetString(map.get("type")),
                        TranslatorUtils.safeGetString(map.get("typeName")),
                        TranslatorUtils.safeGetString(map.get("name"))
                );

                ArrayList<BlockBean> resultBlocks = new ArrayList<>();
                boolean isCompatibleParams = true;
                boolean isFallBackBlock = false;

                for (int i = 0; i < matcher.getParams().size(); i++) {
                    String paramHolder = matcher.getParamsHolders().size() > i ? matcher.getParamsHolders().get(i) : "%s";
                    String paramValue = matcher.getParams().get(i);

                    boolean isStringParam = TranslatorUtils.isLiteralString(paramValue);
                    boolean isDirectValue = paramHolder.equals("%s.inputOnly") || paramHolder.startsWith("%m.") ||
                            (paramHolder.startsWith("%s") && isStringParam || paramValue.matches("^-?\\d+(\\.\\d+)?$"));

                    if (isDirectValue) {
                        if (isStringParam) {
                            b.parameters.add(paramValue.substring(1, paramValue.length() - 1));
                        } else {
                            String bindingStarting = "binding.";
                            if (Fx.viewParamsTypes.contains(paramHolder) && paramValue.startsWith(bindingStarting)) {
                                paramValue = paramValue.substring(bindingStarting.length());
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
                        ArrayList<BlockBean> paramBlocks = expressionBlockBuilder.build(
                                expr,
                                reqType,
                                paramValue
                        );
                        if (!paramBlocks.isEmpty()) {
                            BlockBean last = paramBlocks.get(paramBlocks.size() - 1);
                            last.nextBlock = -1;
                            resultBlocks.addAll(paramBlocks);
                            if (!isFallBackBlock) {
                                isFallBackBlock = TranslatorUtils.isASDBlock(last);
                            }
                            b.parameters.add("@" + last.id);
                        } else {
                            b.parameters.add(paramValue);
                        }
                    }
                }

                if (isFallBackBlock) {
                    b.nextBlock = hasNext ? idCounter.get() : -1;
                    fallbackBean.clear();
                    fallbackBean.addAll(resultBlocks);
                    fallbackBean.add(b);
                    idCounter.set(idCounter.get() - fallbackBean.size());
                } else if (isCompatibleParams) {
                    b.nextBlock = hasNext ? idCounter.get() : -1;
                    resultBlocks.add(b);
                    return resultBlocks;
                }
            }
        }

        if (!fallbackBean.isEmpty()) {
            idCounter.set(idCounter.get() + fallbackBean.size());
            return fallbackBean;
        } else {
            idCounter.set(startId);
            return null;
        }
    }

}
