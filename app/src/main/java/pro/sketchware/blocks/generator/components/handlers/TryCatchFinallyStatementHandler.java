package pro.sketchware.blocks.generator.components.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TryStmt;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;

public class TryCatchFinallyStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public TryCatchFinallyStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof TryStmt;
    }

    @Override
    public void handle(Statement stmt) {
        TryStmt ts = (TryStmt) stmt;

        int tryId = blockGeneratorCoordinator.idCounter().getAndIncrement();

        BlockBean tryBean = new BlockBean(
                String.valueOf(tryId),
                "try",
                "e",
                "",
                "tryCatch"
        );

        if (!ts.getTryBlock().isEmpty()) {
            tryBean.subStack1 = blockGeneratorCoordinator.idCounter().get();
            var tryStatements = ts.getTryBlock().getStatements();
            blockGeneratorCoordinator.processStatements(tryStatements);
        } else {
            tryBean.subStack1 = -1;
        }

        if (!ts.getCatchClauses().isEmpty()) {
            var catchStmt = ts.getCatchClauses().get(0).getBody().getStatements();
            if (!catchStmt.isEmpty()) {
                tryBean.subStack2 = blockGeneratorCoordinator.idCounter().get();
                blockGeneratorCoordinator.processStatements(catchStmt);
            } else {
                tryBean.subStack2 = -1;
            }
        } else {
            tryBean.subStack2 = -1;
        }

        tryBean.nextBlock = blockGeneratorCoordinator.idCounter().get();

        blockGeneratorCoordinator.blockBeans().add(tryBean);

        ts.getFinallyBlock().ifPresent(finallyBlock -> {
            int finId = blockGeneratorCoordinator.idCounter().getAndIncrement();
            BlockBean finBean = new BlockBean(
                    String.valueOf(finId),
                    "finally",
                    "c",
                    "",
                    "finally"
            );

            if (!finallyBlock.getStatements().isEmpty()) {
                finBean.subStack1 = blockGeneratorCoordinator.idCounter().get();
                var finStatements = finallyBlock.getStatements();
                blockGeneratorCoordinator.processStatements(finStatements);
            } else {
                finBean.subStack1 = -1;
            }

            finBean.nextBlock = blockGeneratorCoordinator.idCounter().get();
            blockGeneratorCoordinator.blockBeans().add(finBean);

            for (int i = 0; i < blockGeneratorCoordinator.noNextBlocks().size(); i++) {
                String id = blockGeneratorCoordinator.noNextBlocks().get(i);
                if (id.equals(tryBean.id)) {
                    blockGeneratorCoordinator.noNextBlocks().set(i, finBean.id);
                }
                // JavaParser handles try–catch–finally as a single statement
                // so we must manually replace the tryCatch block ID with the finally block ID (if present)
            }
        });
    }

}
