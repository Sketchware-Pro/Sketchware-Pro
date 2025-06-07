package pro.sketchware.blocks.generator.components.builders;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.ast.expr.NameExpr;

import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BinaryExprOperatorsTreeBuilder {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    private final HashMap<String, RequiredBlockType> BXO_BlocksRequiredType = new HashMap<>();

    public BinaryExprOperatorsTreeBuilder(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
        loadBinaryExprOperatorBlocksRequiredType();
    }

    public ArrayList<BlockBean> build(Expression expr, RequiredBlockType requiredBlockType) {
        ArrayList<BlockBean> list = new ArrayList<>();
        expr = getEnclosedInner(expr);
        if (expr instanceof BinaryExpr bin) {
            String op = bin.getOperator().asString();
            if (op.equals("==")) {
                op = "=";
            } else if (op.equals("!=")) {
                Expression left = getEnclosedInner(bin.getLeft());
                Expression right = getEnclosedInner(bin.getRight());

                boolean leftIsLiteralNumber = left.isIntegerLiteralExpr() || left.isDoubleLiteralExpr();
                boolean rightIsLiteralNumber = right.isIntegerLiteralExpr() || right.isDoubleLiteralExpr();

                boolean leftIsName = left.isNameExpr();
                boolean rightIsName = right.isNameExpr();

                boolean isNumericComparison = false;
                if (leftIsLiteralNumber || rightIsLiteralNumber) {
                    isNumericComparison = true;
                } else if (leftIsName || rightIsName) {
                    String varName = leftIsName ? left.asNameExpr().getNameAsString() : right.asNameExpr().getNameAsString();
                    isNumericComparison = blockGeneratorCoordinator.projectResourcesHelper().getFields(blockGeneratorCoordinator.projectResourcesHelper().DOUBLE_FIELDS, blockGeneratorCoordinator.projectResourcesHelper().CUSTOM_VAR_FIELDS).contains(varName);
                }

                op = "NOT_EQUALS_" + (isNumericComparison ? "NUM" : "OBJ");
            }

            if (BXO_BlocksRequiredType.containsKey(op) && getContainerBlockType(op).equals(requiredBlockType.blockType())) {
                BlockBean opB = new BlockBean(String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()), "", requiredBlockType.blockType(), "", op);
                Expression left = getEnclosedInner(bin.getLeft());
                Expression right = getEnclosedInner(bin.getRight());
                boolean simpleLeft = left.isIntegerLiteralExpr() || left.isDoubleLiteralExpr() || left.isLiteralStringValueExpr();
                boolean simpleRight = right.isIntegerLiteralExpr() || right.isDoubleLiteralExpr() || right.isLiteralStringValueExpr();
                if (simpleLeft && simpleRight) {
                    opB.parameters.add(extractLiteralValue(left));
                    opB.parameters.add(extractLiteralValue(right));
                } else if (simpleLeft) {
                    list.addAll(build(right, BXO_BlocksRequiredType.get(op)));
                    opB.parameters.add(extractLiteralValue(left));
                    opB.parameters.add("@" + list.get(list.size() - 1).id);
                } else if (simpleRight) {
                    list.addAll(build(left, BXO_BlocksRequiredType.get(op)));
                    opB.parameters.add("@" + list.get(list.size() - 1).id);
                    opB.parameters.add(extractLiteralValue(right));
                } else {
                    List<BlockBean> leftList = build(left, BXO_BlocksRequiredType.get(op));
                    List<BlockBean> rightList = build(right, BXO_BlocksRequiredType.get(op));

                    list.addAll(leftList);
                    list.addAll(rightList);

                    opB.parameters.add("@" + leftList.get(leftList.size() - 1).id);
                    opB.parameters.add("@" + rightList.get(rightList.size() - 1).id);
                }

                opB.nextBlock = -1;
                list.add(opB);
                return list;
            }
        }
        if (expr.isUnaryExpr()) {
            var un = expr.asUnaryExpr();
            list.addAll(build(un.getExpression(), new RequiredBlockType("b")));
            int uid = blockGeneratorCoordinator.idCounter().getAndIncrement();
            BlockBean ub = new BlockBean(String.valueOf(uid), "!@" + list.get(list.size() - 1).id, "b", "", "not");
            ub.parameters.add("@" + list.get(list.size() - 1).id);
            ub.subStack1 = Integer.parseInt(list.get(list.size() - 1).id);
            ub.subStack2 = -1;
            ub.nextBlock = -1;
            list.add(ub);
            return list;
        }
        if (expr instanceof NameExpr nameExpr) {
            list.add(blockGeneratorCoordinator.projectResourcesHelper().getNameExprBlockBean(String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()), nameExpr.getNameAsString(), requiredBlockType));
            return list;
        }
        String param = null;
        String opCode = null;
        String spec = null;
        if (expr.isBooleanLiteralExpr()) {
            opCode = expr.toString();
            spec = opCode;
        } else if (requiredBlockType.blockType().equals("b")) {
            int start = blockGeneratorCoordinator.idCounter().get();
            ArrayList<BlockBean> extra = blockGeneratorCoordinator.extraBlockMatcher().tryExtraBlockMatch(expr.toString(), false, blockGeneratorCoordinator.blocksCategories().getBooleanBlocks());
            if (extra != null) {
                list.addAll(extra);
                return list;
            } else {
                blockGeneratorCoordinator.idCounter().set(start);
            }
            param = expr.toString();
            opCode = "asdBoolean";
            spec = "boolean %s.inputOnly";
        } else if (requiredBlockType.blockType().equals("d")) {
            int start = blockGeneratorCoordinator.idCounter().get();
            ArrayList<BlockBean> extra = blockGeneratorCoordinator.extraBlockMatcher().tryExtraBlockMatch(expr.toString(), false, blockGeneratorCoordinator.blocksCategories().getDoubleBlocks());
            if (extra != null) {
                list.addAll(extra);
                return list;
            } else {
                blockGeneratorCoordinator.idCounter().set(start);
            }
            param = expr.toString();
            opCode = "asdNumber";
            spec = "number %s.inputOnly";
        }
        if (opCode != null) {
            BlockBean bean = new BlockBean(
                    String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()),
                    spec,
                    requiredBlockType.blockType(),
                    "",
                    opCode
            );
            if (param != null) {
                bean.parameters.add(param);
            }
            bean.subStack1 = -1;
            bean.subStack2 = -1;
            list.add(bean);
        } else {
            list.addAll(blockGeneratorCoordinator.expressionBlockBuilder().build(expr, requiredBlockType, expr.toString()));
        }
        return list;
    }

    private Expression getEnclosedInner(Expression expr) {
        while (expr.isEnclosedExpr()) {
            expr = expr.asEnclosedExpr().getInner();
        }
        return expr;
    }

    private String extractLiteralValue(Expression expr) {
        if (expr.isStringLiteralExpr()) {
            return expr.asStringLiteralExpr().getValue();
        }
        return expr.toString();
    }

    private void loadBinaryExprOperatorBlocksRequiredType() {
        RequiredBlockType b = new RequiredBlockType("b");
        RequiredBlockType d = new RequiredBlockType("d");
        RequiredBlockType s = new RequiredBlockType("s");

        BXO_BlocksRequiredType.put("+", d);
        BXO_BlocksRequiredType.put("-", d);
        BXO_BlocksRequiredType.put("=", d);
        BXO_BlocksRequiredType.put("<=", d);
        BXO_BlocksRequiredType.put(">=", d);
        BXO_BlocksRequiredType.put("*", d);
        BXO_BlocksRequiredType.put("/", d);
        BXO_BlocksRequiredType.put("%", d);
        BXO_BlocksRequiredType.put("^", d);
        BXO_BlocksRequiredType.put(">", d);
        BXO_BlocksRequiredType.put(">>", d);
        BXO_BlocksRequiredType.put(">>>", d);
        BXO_BlocksRequiredType.put("<", d);
        BXO_BlocksRequiredType.put("<<", d);
        BXO_BlocksRequiredType.put("<<<", d);
        BXO_BlocksRequiredType.put("NOT_EQUALS_NUM", d);
        BXO_BlocksRequiredType.put("NOT_EQUALS_OBJ", s);

        BXO_BlocksRequiredType.put("||", b);
        BXO_BlocksRequiredType.put("|", b);
        BXO_BlocksRequiredType.put("&&", b);
        BXO_BlocksRequiredType.put("&", b);
    }

    private String getContainerBlockType(String op) {
        ArrayList<String> doubleBlocks = new ArrayList<>(List.of("+", "-", "*", "/", "%", "^", ">>", ">>>", "<<<", "<<", "|", "&"));
        return doubleBlocks.contains(op) ? "d" : "b";
    }

}
