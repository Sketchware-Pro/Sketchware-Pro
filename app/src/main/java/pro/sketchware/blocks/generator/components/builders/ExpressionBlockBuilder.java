package pro.sketchware.blocks.generator.components.builders;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;

import java.util.ArrayList;

public class ExpressionBlockBuilder {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public ExpressionBlockBuilder(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    public ArrayList<BlockBean> build(Expression expr, RequiredBlockType requiredBlockType, String codeLine) {
        ArrayList<BlockBean> res = new ArrayList<>();
        String opCode, spec, type;
        if (requiredBlockType != null) {
            if (expr instanceof NameExpr nameExpr) {
                res.add(blockGeneratorCoordinator.projectResourcesHelper().getNameExprBlockBean(String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()), nameExpr.getNameAsString(), requiredBlockType));
                return res;
            } else if (expr.isBooleanLiteralExpr() || requiredBlockType.blockType().equals("b") || requiredBlockType.blockType().equals("d")) {
                return blockGeneratorCoordinator.binaryExprOperatorsTreeBuilder().build(expr, requiredBlockType);
            } else {
                ArrayList<BlockBean> extra = blockGeneratorCoordinator.extraBlockMatcher().tryExtraBlockMatch(expr.toString(), false, blockGeneratorCoordinator.blocksCategories().getStringBlocks());
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
                String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()),
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
