package pro.sketchware.blocks.generator.builders;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.resources.ProjectResourcesHelper;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.records.RequiredBlockType;
import pro.sketchware.blocks.generator.resources.BlocksCategories;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpressionBlockBuilder {

    private final ProjectResourcesHelper projectResourcesHelper;
    private final BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder;
    private final BlockParamUtil blockParamUtil;
    private final BlocksCategories blocksCategories;
    private final AtomicInteger idCounter;

    public ExpressionBlockBuilder(
            ProjectResourcesHelper projectResourcesHelper, BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder, BlockParamUtil blockParamUtil,
            BlocksCategories blocksCategories,
            AtomicInteger idCounter
    ) {
        this.projectResourcesHelper = projectResourcesHelper;
        this.binaryExprOperatorsTreeBuilder = binaryExprOperatorsTreeBuilder;
        this.blockParamUtil = blockParamUtil;
        this.blocksCategories = blocksCategories;
        this.idCounter = idCounter;
    }

    public ArrayList<BlockBean> build(Expression expr, RequiredBlockType requiredBlockType, String codeLine) {
        ArrayList<BlockBean> res = new ArrayList<>();
        String opCode, spec, type;
        if (requiredBlockType != null) {
            if (expr instanceof NameExpr nameExpr) {
                res.add(projectResourcesHelper.getNameExprBlockBean(String.valueOf(idCounter.getAndIncrement()), nameExpr.getNameAsString(), requiredBlockType));
                return res;
            } else if (expr.isBooleanLiteralExpr() || requiredBlockType.blockType().equals("b") || requiredBlockType.blockType().equals("d")) {
                return binaryExprOperatorsTreeBuilder.build(expr, requiredBlockType);
            } else {
                ArrayList<BlockBean> extra = new ExtraBlockMatcher(blockParamUtil, idCounter, this)
                        .tryExtraBlockMatch(expr.toString(), false, blocksCategories.getStringBlocks());
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
                String.valueOf(idCounter.getAndIncrement()),
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
