package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.EventBlocksGenerator;
import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.resources.ProjectResourcesHelper;
import pro.sketchware.blocks.generator.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatementHandler implements StatementHandler {

    private final ProjectResourcesHelper projectResourcesHelper;
    private final ExpressionBlockBuilder expressionBlockBuilder;
    private final EventBlocksGenerator parent;

    public SwitchStatementHandler(EventBlocksGenerator parent, ProjectResourcesHelper projectResourcesHelper, ExpressionBlockBuilder expressionBlockBuilder) {
        this.parent = parent;
        this.projectResourcesHelper = projectResourcesHelper;
        this.expressionBlockBuilder = expressionBlockBuilder;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof SwitchStmt;
    }

    @Override
    public void handle(Statement stmt, HandlerContext context) {
        SwitchStmt switchStmt = (SwitchStmt) stmt;
        int switchId = context.idCounter().getAndIncrement();

        Expression selector = switchStmt.getSelector();
        ParamInfo paramInfo = getSwitchParamInfo(context, selector);

        boolean isStringSwitch = paramInfo.isStringParam();

        BlockBean switchBean = new BlockBean(
                String.valueOf(switchId),
                "switch " + (isStringSwitch ? "%s" : "%d"),
                "c",
                "",
                isStringSwitch ? "switchStr" : "switchNum"
        );

        switchBean.parameters.add(paramInfo.paramValue());

        context.blockBeans().addAll(paramInfo.paramBlocks());

        List<SwitchEntry> entries = switchStmt.getEntries();

        if (!entries.isEmpty()) {
            switchBean.subStack1 = context.idCounter().get();
        }

        for (int j = 0; j < entries.size(); j++) {
            if (j == entries.size() - 1) {
                context.noNextBlocks().add(
                        String.valueOf(context.idCounter().get())
                );
            }

            int caseId = context.idCounter().getAndIncrement();
            String name;
            String spec;
            String type;

            SwitchEntry entry = entries.get(j);

            NodeList<Statement> caseStatements = entry.getStatements();
            boolean isFallThrough = caseStatements.isEmpty();
            boolean isDefaultBlock = false;

            if (entry.getLabels().isEmpty()) {
                name = "defaultSwitch";
                spec = "default";
                type = "c";
                isDefaultBlock = true;
            } else if (isStringSwitch) {
                name = isFallThrough ? "caseStrAnd" : "caseStr";
                spec = isFallThrough ? "case %s and" : "case %s";
                type = isFallThrough ? " " : "c";
            } else {
                name = isFallThrough ? "caseNumAnd" : "caseNum";
                spec = isFallThrough ? "case %d and" : "case %d";
                type = isFallThrough ? " " : "c";
            }

            BlockBean caseBean = new BlockBean(
                    String.valueOf(caseId),
                    spec,
                    type,
                    "",
                    name
            );

            if (!isDefaultBlock) {
                Expression caseLabel = entry.getLabels().get(0);
                ParamInfo caseParamInfo = getCaseParamInfo(context, caseLabel, isStringSwitch);

                caseBean.parameters.add(caseParamInfo.paramValue());
                context.blockBeans().addAll(caseParamInfo.paramBlocks());
            }

            if (caseStatements.size() == 1 && caseStatements.get(0).isBlockStmt()) {
                var inner = caseStatements.get(0).asBlockStmt().getStatements();
                caseStatements = new NodeList<>();
                for (var s : inner) {
                    if (s.isBreakStmt()) continue;
                    caseStatements.add(s);
                }
            }

            if (!caseStatements.isEmpty()) {
                caseBean.subStack1 = context.idCounter().get();
                for (int i = 0; i < caseStatements.size(); i++) {
                    if (i == caseStatements.size() - 1) {
                        context.noNextBlocks().add(
                                String.valueOf(context.idCounter().get())
                        );
                    }
                    parent.processStatement(caseStatements.get(i));
                }
            } else {
                caseBean.subStack1 = -1;
            }

            caseBean.nextBlock = context.idCounter().get();
            context.blockBeans().add(caseBean);
        }

        switchBean.nextBlock = context.idCounter().get();
        context.blockBeans().add(switchBean);
    }

    private ParamInfo getSwitchParamInfo(HandlerContext context, Expression expression) {
        expression = getEnclosedInner(expression);
        String exprValue = expression.toString();
        if (TranslatorUtils.isLiteralNumber(exprValue)) {
            return new ParamInfo(false, exprValue);
        }
        if (TranslatorUtils.isLiteralString(exprValue)) {
            return new ParamInfo(true, exprValue.substring(1, exprValue.length() - 1));
        }
        if (expression.isNameExpr()) {
            boolean isStringVar = !projectResourcesHelper.getFields(projectResourcesHelper.DOUBLE_FIELDS).contains(exprValue);
            int blockId = context.idCounter().getAndIncrement();
            BlockBean varBlock = new BlockBean(
                    String.valueOf(blockId),
                    exprValue,
                    isStringVar ? "s" : "d",
                    "",
                    "getVar"
            );
            return new ParamInfo(isStringVar, "@" + blockId, varBlock);

        }

        ArrayList<BlockBean> paramBlocks = new ArrayList<>(expressionBlockBuilder.build(expression, new RequiredBlockType("d"), ""));

        if (!(paramBlocks.size() == 1 && TranslatorUtils.isASDBlock(paramBlocks.get(0)))) {
            return new ParamInfo(false, "@" + paramBlocks.get(paramBlocks.size() - 1).id, paramBlocks);
        } else {
            paramBlocks.clear();
            paramBlocks.addAll(expressionBlockBuilder.build(expression, new RequiredBlockType("s"), ""));
            return new ParamInfo(true, "@" + paramBlocks.get(paramBlocks.size() - 1).id, paramBlocks);
        }
    }

    private ParamInfo getCaseParamInfo(HandlerContext context, Expression expression, boolean isStringSwitch) {
        expression = getEnclosedInner(expression);
        String exprValue = expression.toString();
        if (TranslatorUtils.isLiteralNumber(exprValue)) {
            return new ParamInfo(isStringSwitch, exprValue);
        }
        if (TranslatorUtils.isLiteralString(exprValue)) {
            return new ParamInfo(isStringSwitch, exprValue.substring(1, exprValue.length() - 1));
        }
        if (expression.isNameExpr()) {
            int blockId = context.idCounter().getAndIncrement();
            BlockBean varBlock = new BlockBean(
                    String.valueOf(blockId),
                    exprValue,
                    isStringSwitch ? "s" : "d",
                    "",
                    "getVar"
            );
            return new ParamInfo(isStringSwitch, "@" + blockId, varBlock);
        }

        ArrayList<BlockBean> paramBlocks = new ArrayList<>(expressionBlockBuilder.build(expression, new RequiredBlockType(isStringSwitch ? "s" : "d"), ""));
        return new ParamInfo(isStringSwitch, "@" + paramBlocks.get(paramBlocks.size() - 1).id, paramBlocks);
    }

    private Expression getEnclosedInner(Expression expr) {
        while (true) {
            if (expr.isEnclosedExpr()) {
                expr = expr.asEnclosedExpr().getInner();
            } else if (expr.isCastExpr()) {
                expr = expr.asCastExpr().getExpression();
            } else break;
        }
        return expr;
    }

    private record ParamInfo(boolean isStringParam, String paramValue, ArrayList<BlockBean> paramBlocks) {

        public ParamInfo(boolean isStringParam, String paramValue) {
            this(isStringParam, paramValue, new ArrayList<>());
        }

        public ParamInfo(boolean isStringParam, String paramValue, BlockBean paramBlock) {
            this(isStringParam, paramValue, new ArrayList<>(List.of(paramBlock)));
        }

    }

}
