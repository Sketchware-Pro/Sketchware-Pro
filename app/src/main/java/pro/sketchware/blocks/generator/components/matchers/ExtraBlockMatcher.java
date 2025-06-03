package pro.sketchware.blocks.generator.components.matchers;

import android.util.Log;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.besome.sketch.beans.BlockBean;

import a.a.a.Fx;
import a.a.a.Lx;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;
import pro.sketchware.blocks.generator.components.utils.BlockParamUtils;
import pro.sketchware.blocks.generator.components.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtraBlockMatcher {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public ExtraBlockMatcher(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    public String getModifiedBlockCode(String oldBlockCode, String code, String runTimeName) {
        if (runTimeName.equals("setAdapter")) {
            int viewParamIndex = code.indexOf(".setAdapter");
            if (viewParamIndex != -1) {
                String viewBindingStarting = "binding.";
                String viewParam = code.substring(0, viewParamIndex);

                if (blockGeneratorCoordinator.isViewBindingEnabled() && viewParam.startsWith(viewBindingStarting)) {
                    viewParam = viewParam.substring(viewBindingStarting.length());
                }

                return oldBlockCode.replace(
                        BlockParamUtils.PLACEHOLDER_PARAM,
                        Lx.a(viewParam, blockGeneratorCoordinator.isViewBindingEnabled())
                );
            }
        }
        return oldBlockCode;
    }

    public ArrayList<BlockBean> tryExtraBlockMatch(String input, boolean hasNext, ArrayList<HashMap<String, Object>> targetBlocks) {
        int startId = blockGeneratorCoordinator.idCounter().get();
        String norm = input.trim();
        ArrayList<BlockBean> fallbackBean = new ArrayList<>();

        for (var map : targetBlocks) {
            String code = TranslatorUtils.safeGetString(map.get("code")).trim();
            String spec = TranslatorUtils.safeGetString(map.get("spec")).trim();
            String type = TranslatorUtils.safeGetString(map.get("type"));

            if (map.containsKey("runtimeHandling")) {
                code = getModifiedBlockCode(code, norm, TranslatorUtils.safeGetString(map.get("runtimeHandling")));
            }

            BlockCodeMatcher matcher = new BlockCodeMatcher(type, spec, code, norm, blockGeneratorCoordinator.blockParamUtil());

            Log.d("BlocksGenerator", String.format("""
                            
                            |---------------------------------------
                                 |------block name "%s" : %s and %s
                                 |------result : %s
                                 |------params : %s
                                 |------paramsHolders : %s
                                 |------hasRuntimeHandling : %s
                                 |------BlockCodeMatcherResult : %s
                            |---------------------------------------
                            """,
                    map.get("name"), matcher.getFormattedBlockCode(),
                    matcher.getFormattedInputCode(),
                    matcher.isMatch() ? "matching ✅" : "failed ❌",
                    matcher.getParams(),
                    matcher.getParamsHolders(),
                    map.containsKey("runtimeHandling"),
                    matcher.getResult()
            ));

            if (matcher.isMatch()) {
                BlockBean b = new BlockBean(
                        String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()),
                        TranslatorUtils.safeGetString(map.get("spec")),
                        TranslatorUtils.safeGetString(map.get("type")),
                        TranslatorUtils.safeGetString(map.get("typeName")),
                        TranslatorUtils.safeGetString(map.get("name"))
                );

                ArrayList<BlockBean> resultBlocks = new ArrayList<>();
                boolean isCompatibleParams = true;
                boolean isFallBackBlock = false;

                int paramsSize = matcher.getParams().size();
                for (int i = 0; i < paramsSize; i++) {
                    String paramHolder = matcher.getParamsHolders().get(i);
                    String paramValue = matcher.getParams().get(i);

                    boolean isSubStack1 = type.equals("c") && i == paramsSize - 1 || type.equals("e") && i == paramsSize - 2;
                    boolean isSubStack2 = type.equals("e") && i == paramsSize - 1;

                    if (isSubStack1) {
                        b.subStack1 = blockGeneratorCoordinator.idCounter().get();
                    } else if (isSubStack2) {
                        b.subStack2 = blockGeneratorCoordinator.idCounter().get();
                    }

                    if (isSubStack1 || isSubStack2) {
                        blockGeneratorCoordinator.processStatements(
                                StaticJavaParser.parseBlock("{" + paramValue + "}")
                                        .getStatements()
                        );
                    } else {
                        boolean isStringParam = TranslatorUtils.isLiteralString(paramValue);
                        boolean isDirectValue = paramHolder.equals("%s.inputOnly") || (paramHolder.startsWith("%m.") && !paramValue.startsWith("_")) ||
                                (paramHolder.startsWith("%s") && isStringParam || TranslatorUtils.isLiteralNumber(paramValue));

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
                            RequiredBlockType reqType = blockGeneratorCoordinator.blockParamUtil().getRequiredBlockType(methodName, code, i, b);
                            if (reqType == null) {
                                isCompatibleParams = false;
                                break;
                            }
                            ArrayList<BlockBean> paramBlocks = blockGeneratorCoordinator.expressionBlockBuilder().build(
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

                }

                if (isFallBackBlock) {
                    b.nextBlock = hasNext ? blockGeneratorCoordinator.idCounter().get() : -1;
                    fallbackBean.clear();
                    fallbackBean.addAll(resultBlocks);
                    fallbackBean.add(b);
                    blockGeneratorCoordinator.idCounter().set(blockGeneratorCoordinator.idCounter().get() - fallbackBean.size());
                } else if (isCompatibleParams) {
                    b.nextBlock = hasNext ? blockGeneratorCoordinator.idCounter().get() : -1;
                    resultBlocks.add(b);
                    return resultBlocks;
                }
            }
        }

        if (!fallbackBean.isEmpty()) {
            blockGeneratorCoordinator.idCounter().set(blockGeneratorCoordinator.idCounter().get() + fallbackBean.size());
            return fallbackBean;
        } else {
            blockGeneratorCoordinator.idCounter().set(startId);
            return null;
        }
    }

}
