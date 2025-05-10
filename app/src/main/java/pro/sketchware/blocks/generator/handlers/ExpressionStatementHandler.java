package pro.sketchware.blocks.generator.handlers;

import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;

import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;

public class ExpressionStatementHandler implements StatementHandler {

    private final ExtraBlockMatcher extraBlockMatcher;
    private final ExpressionBlockBuilder expressionBlockBuilder;

    public ExpressionStatementHandler(ExtraBlockMatcher extraBlockMatcher, ExpressionBlockBuilder expressionBlockBuilder) {
        this.extraBlockMatcher = extraBlockMatcher;
        this.expressionBlockBuilder = expressionBlockBuilder;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof ExpressionStmt;
    }

    @Override
    public void handle(Statement stmt, int id, HandlerContext context) {
        Expression expr = ((ExpressionStmt) stmt).getExpression();
        String code = stmt.toString().trim();
        int next = context.idCounter().get();

        ArrayList<BlockBean> extraBlocks = extraBlockMatcher.tryExtraBlockMatch(code, id, next, context.blocksCategories().getRegularBlocks());
        if (extraBlocks != null) {
            context.blockBeans().addAll(extraBlocks);
            return;
        }
        ArrayList<BlockBean> exprBlocks = expressionBlockBuilder.build(expr, id, null, code);
        BlockBean bean = exprBlocks.get(exprBlocks.size() - 1);
        bean.nextBlock = next;
        context.blockBeans().addAll(exprBlocks);
    }

}
