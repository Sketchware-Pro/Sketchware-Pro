package pro.sketchware.blocks.generator.builders;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.resources.BlocksCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BooleanTreeBuilder {

    private final BlockParamUtil blockParamUtil;
    private final BlocksCategories blocksCategories;
    private final AtomicInteger idCounter;
    private final HashMap<String, RequiredBlockType> swVanillaBooleanBlocks = new HashMap<>();

    public BooleanTreeBuilder(BlockParamUtil blockParamUtil, BlocksCategories blocksCategories, java.util.concurrent.atomic.AtomicInteger idCounter) {
        this.blockParamUtil = blockParamUtil;
        this.blocksCategories = blocksCategories;
        this.idCounter = idCounter;
        loadSwVanillaBooleanBlocks();
    }

    public List<BlockBean> build(Expression expr, RequiredBlockType requiredBlockType) {
        List<BlockBean> list = new ArrayList<>();
        expr = getEnclosedInner(expr);
        if (expr instanceof BinaryExpr bin) {
            String op = bin.getOperator().asString();
            if (op.equals("==")) op = "=";
            BlockBean opB = new BlockBean(String.valueOf(idCounter.getAndIncrement()), "", "b", "", op);
            if (swVanillaBooleanBlocks.containsKey(op)) {
                Expression left = getEnclosedInner(bin.getLeft());
                Expression right = getEnclosedInner(bin.getRight());
                boolean simpleLeft = left.isIntegerLiteralExpr() || left.isDoubleLiteralExpr() || left.isLiteralStringValueExpr();
                boolean simpleRight = right.isIntegerLiteralExpr() || right.isDoubleLiteralExpr() || right.isLiteralStringValueExpr();
                if (simpleLeft && simpleRight) {
                    opB.parameters.add(extractLiteralValue(left));
                    opB.parameters.add(extractLiteralValue(right));
                } else if (simpleLeft) {
                    list.addAll(build(right, swVanillaBooleanBlocks.get(op)));
                    opB.parameters.add(extractLiteralValue(left));
                    opB.parameters.add("@" + list.get(list.size() - 1).id);
                } else if (simpleRight) {
                    list.addAll(build(left, swVanillaBooleanBlocks.get(op)));
                    opB.parameters.add("@" + list.get(list.size() - 1).id);
                    opB.parameters.add(extractLiteralValue(right));
                } else {
                    List<BlockBean> leftList = build(left, swVanillaBooleanBlocks.get(op));
                    List<BlockBean> rightList = build(right, swVanillaBooleanBlocks.get(op));

                    list.addAll(leftList);
                    list.addAll(rightList);

                    opB.parameters.add("@" + leftList.get(leftList.size() - 1).id);
                    opB.parameters.add("@" + rightList.get(rightList.size() - 1).id);
                }

            }
            opB.nextBlock = -1;
            list.add(opB);
            return list;
        }
        if (expr.isUnaryExpr()) {
            var un = expr.asUnaryExpr();
            list.addAll(build(un.getExpression(), new RequiredBlockType("b")));
            int uid = idCounter.getAndIncrement();
            BlockBean ub = new BlockBean(String.valueOf(uid), "!@" + list.get(list.size() - 1).id, "b", "", "not");
            ub.parameters.add("@" + list.get(list.size() - 1).id);
            ub.subStack1 = Integer.parseInt(list.get(list.size() - 1).id);
            ub.subStack2 = -1;
            ub.nextBlock = -1;
            list.add(ub);
            return list;
        }
        list.addAll(new ExpressionBlockBuilder(blockParamUtil, blocksCategories, idCounter)
                .build(expr, idCounter.getAndIncrement(), requiredBlockType, expr.toString()));
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

    private void loadSwVanillaBooleanBlocks() {
        RequiredBlockType b = new RequiredBlockType("b");
        RequiredBlockType d = new RequiredBlockType("d");

        swVanillaBooleanBlocks.put("+", d);
        swVanillaBooleanBlocks.put("-", d);
        swVanillaBooleanBlocks.put("=", d);
        swVanillaBooleanBlocks.put("*", d);
        swVanillaBooleanBlocks.put("/", d);
        swVanillaBooleanBlocks.put("%", d);
        swVanillaBooleanBlocks.put(">", d);
        swVanillaBooleanBlocks.put("<", d);
        swVanillaBooleanBlocks.put("", d);

        swVanillaBooleanBlocks.put("||", b);
        swVanillaBooleanBlocks.put("&&", b);
    }

}
