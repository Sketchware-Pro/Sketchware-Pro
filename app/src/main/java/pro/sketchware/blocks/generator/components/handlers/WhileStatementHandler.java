package pro.sketchware.blocks.generator.components.handlers;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;

import java.util.List;

public class WhileStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public WhileStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof WhileStmt;
    }

    @Override
    public void handle(Statement stmt) {
        WhileStmt ws = (WhileStmt) stmt;
        Expression expr = ws.getCondition();
        List<BlockBean> blockBeans = blockGeneratorCoordinator.blockBeans();
        boolean isForeverBlock = false;
        if (expr.isBooleanLiteralExpr()) {
            isForeverBlock = expr.toString().equals("true");
        }
        BlockBean wb = new BlockBean(String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()),
                isForeverBlock ? "forever" : "while %b",
                "c",
                "",
                isForeverBlock ? "forever" : "whileLoop"
        );

        if (!isForeverBlock) {
            List<BlockBean> condTree = blockGeneratorCoordinator.binaryExprOperatorsTreeBuilder().build(ws.getCondition(), new RequiredBlockType("b"));
            blockBeans.addAll(condTree);
            BlockBean condRoot = condTree.get(condTree.size() - 1);
            condRoot.nextBlock = -1;
            wb.parameters.add("@" + condRoot.id);
        }
        blockBeans.add(wb);
        if (ws.getBody().isBlockStmt()) {
            var whileStatements = ws.getBody().asBlockStmt().getStatements();
            if (whileStatements.isEmpty()) {
                wb.subStack1 = -1;
            } else {
                wb.subStack1 = blockGeneratorCoordinator.idCounter().get();
                blockGeneratorCoordinator.processStatements(whileStatements);
            }
        } else {
            wb.subStack1 = -1;
        }
        wb.nextBlock = blockGeneratorCoordinator.idCounter().get();
    }

}
