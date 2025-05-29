package pro.sketchware.blocks.generator.components.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.IfStmt;
import com.besome.sketch.beans.BlockBean;
import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.records.RequiredBlockType;

import java.util.List;

public class IfStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public IfStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof IfStmt;
    }

    @Override
    public void handle(Statement stmt) {
        IfStmt ifStmt = (IfStmt) stmt;

        boolean hasElse = ifStmt.getElseStmt().isPresent();
        String spec = hasElse ? "if %b then" : "if %b";
        String opCode = hasElse ? "ifElse" : "if";
        String type = hasElse ? "e" : "c";

        BlockBean ifBean = new BlockBean(
                String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()), spec, type, "", opCode
        );

        List<BlockBean> condTree = blockGeneratorCoordinator.binaryExprOperatorsTreeBuilder().build(ifStmt.getCondition(), new RequiredBlockType("b"));
        blockGeneratorCoordinator.blockBeans().addAll(condTree);
        BlockBean condRoot = condTree.get(condTree.size() - 1);

        ifBean.parameters.add("@" + condRoot.id);

        if (ifStmt.getThenStmt().isBlockStmt()) {
            var thenStatements = ifStmt.getThenStmt().asBlockStmt().getStatements();
            if (thenStatements.isEmpty()) {
                ifBean.subStack1 = -1;
            } else {
                ifBean.subStack1 = blockGeneratorCoordinator.idCounter().get();
                blockGeneratorCoordinator.processStatements(thenStatements);
            }
        } else {
            ifBean.subStack1 = -1;
        }
        if (hasElse) {
            Statement es = ifStmt.getElseStmt().get();
            if (es.isIfStmt()) {
                ifBean.subStack2 = blockGeneratorCoordinator.idCounter().get();
                handle(es);
            } else if (es.isBlockStmt()) {
                var elseStatements = es.asBlockStmt().getStatements();
                if (elseStatements.isEmpty()) {
                    ifBean.subStack2 = -1;
                } else {
                    ifBean.subStack2 = blockGeneratorCoordinator.idCounter().get();
                    blockGeneratorCoordinator.processStatements(elseStatements);
                }
            } else {
                ifBean.subStack2 = -1;
            }
        } else {
            ifBean.subStack2 = -1;
        }

        ifBean.nextBlock = blockGeneratorCoordinator.idCounter().get();
        blockGeneratorCoordinator.blockBeans().add(ifBean);
    }

}
