package pro.sketchware.blocks.generator.components.handlers;

import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;

import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;

public class ExpressionStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public ExpressionStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof ExpressionStmt;
    }

    @Override
    public void handle(Statement stmt) {
        Expression expr = ((ExpressionStmt) stmt).getExpression();
        String code = stmt.toString().trim();

        ArrayList<BlockBean> extraBlocks = blockGeneratorCoordinator.extraBlockMatcher().tryExtraBlockMatch(code, true, blockGeneratorCoordinator.blocksCategories().getRegularBlocks());
        if (extraBlocks != null) {
            blockGeneratorCoordinator.blockBeans().addAll(extraBlocks);
            return;
        }
        ArrayList<BlockBean> exprBlocks = blockGeneratorCoordinator.expressionBlockBuilder().build(expr, null, code);
        BlockBean bean = exprBlocks.get(exprBlocks.size() - 1);
        bean.nextBlock = blockGeneratorCoordinator.idCounter().incrementAndGet();
        blockGeneratorCoordinator.blockBeans().addAll(exprBlocks);
    }

}
