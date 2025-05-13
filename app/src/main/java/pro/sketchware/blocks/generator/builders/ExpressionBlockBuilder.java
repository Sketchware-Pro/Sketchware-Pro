package pro.sketchware.blocks.generator.builders;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.resources.BlocksCategories;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpressionBlockBuilder {

    private final BooleanTreeBuilder booleanTreeBuilder;
    private final BlockParamUtil blockParamUtil;
    private final BlocksCategories blocksCategories;
    private final AtomicInteger idCounter;

    public ExpressionBlockBuilder(
            BooleanTreeBuilder booleanTreeBuilder, BlockParamUtil blockParamUtil,
            BlocksCategories blocksCategories,
            AtomicInteger idCounter
    ) {
        this.booleanTreeBuilder = booleanTreeBuilder;
        this.blockParamUtil = blockParamUtil;
        this.blocksCategories = blocksCategories;
        this.idCounter = idCounter;
    }

    public ArrayList<BlockBean> build(Expression expr, int id, RequiredBlockType requiredBlockType, String codeLine) {
        ArrayList<BlockBean> res = new ArrayList<>();
        String opCode, spec, type;
        if (requiredBlockType != null) {
            if (expr instanceof NameExpr nameExpr) {
                opCode = "getVar";
                spec = nameExpr.getNameAsString();
                type = requiredBlockType.blockType();
                BlockBean bean = new BlockBean(
                        String.valueOf(id),
                        spec,
                        type,
                        "",
                        opCode
                );
                bean.subStack1 = -1;
                bean.subStack2 = -1;
                res.add(bean);
                return res;
            } else if (expr.isBooleanLiteralExpr() || requiredBlockType.blockType().equals("b")) {
                return booleanTreeBuilder.build(expr, requiredBlockType);
            } else if (requiredBlockType.blockType().equals("d")) {
                ArrayList<BlockBean> extra = new ExtraBlockMatcher(blockParamUtil, idCounter, this)
                        .tryExtraBlockMatch(expr.toString(), id, -1, blocksCategories.getDoubleBlocks());
                if (extra != null) return extra;
                opCode = "asdNumber";
                spec = "number %s.inputOnly";
                type = "d";
            } else {
                ArrayList<BlockBean> extra = new ExtraBlockMatcher(blockParamUtil, idCounter, this)
                        .tryExtraBlockMatch(expr.toString(), id, -1, blocksCategories.getStringBlocks());
                if (extra != null) return extra;
                opCode = "asdString";
                spec = "string %s.inputOnly";
                type = "s";
            }
        } else {
            opCode = "addSourceDirectly";
            spec = "add source directly %s.inputOnly";
            type = " ";
        }

        BlockBean bean = new BlockBean(
                String.valueOf(id),
                spec,
                type,
                "",
                opCode
        );
        bean.parameters.add(opCode.equals("addSourceDirectly") ? codeLine.trim() : expr.toString());
        bean.subStack1 = -1;
        bean.subStack2 = -1;
        res.add(bean);
        return res;
    }

}
