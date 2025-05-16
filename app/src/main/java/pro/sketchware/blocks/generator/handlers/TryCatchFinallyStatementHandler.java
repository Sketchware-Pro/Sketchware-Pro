package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TryStmt;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.EventBlocksGenerator;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;

public class TryCatchFinallyStatementHandler implements StatementHandler {

    private final EventBlocksGenerator parent;

    public TryCatchFinallyStatementHandler(EventBlocksGenerator parent) {
        this.parent = parent;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof TryStmt;
    }

    @Override
    public void handle(Statement stmt, HandlerContext context) {
        TryStmt ts = (TryStmt) stmt;

        int tryId = context.idCounter().getAndIncrement();

        BlockBean tryBean = new BlockBean(
                String.valueOf(tryId),
                "try",
                "e",
                "",
                "tryCatch"
        );

        if (!ts.getTryBlock().isEmpty()) {
            tryBean.subStack1 = context.idCounter().get();
            var tryStatements = ts.getTryBlock().getStatements();
            for (int i = 0; i < tryStatements.size(); i++) {
                if (i == tryStatements.size() - 1) {
                    context.noNextBlocks().add(String.valueOf(context.idCounter().get()));
                }
                parent.processStatement(tryStatements.get(i));
            }
        } else {
            tryBean.subStack1 = -1;
        }

        if (!ts.getCatchClauses().isEmpty()) {
            var catchStmt = ts.getCatchClauses().get(0).getBody().getStatements();
            if (!catchStmt.isEmpty()) {
                tryBean.subStack2 = context.idCounter().get();
                for (int i = 0; i < catchStmt.size(); i++) {
                    if (i == catchStmt.size() - 1) {
                        context.noNextBlocks().add(String.valueOf(context.idCounter().get()));
                    }
                    parent.processStatement(catchStmt.get(i));
                }
            } else {
                tryBean.subStack2 = -1;
            }
        } else {
            tryBean.subStack2 = -1;
        }

        tryBean.nextBlock = context.idCounter().get();

        context.blockBeans().add(tryBean);

        ts.getFinallyBlock().ifPresent(finallyBlock -> {
            int finId = context.idCounter().getAndIncrement();
            BlockBean finBean = new BlockBean(
                    String.valueOf(finId),
                    "finally",
                    "c",
                    "",
                    "finally"
            );

            if (!finallyBlock.getStatements().isEmpty()) {
                finBean.subStack1 = context.idCounter().get();
                var finStatements = finallyBlock.getStatements();
                for (int i = 0; i < finStatements.size(); i++) {
                    if (i == finStatements.size() - 1) {
                        context.noNextBlocks().add(String.valueOf(context.idCounter().get()));
                    }
                    parent.processStatement(finStatements.get(i));
                }
            } else {
                finBean.subStack1 = -1;
            }

            finBean.nextBlock = context.idCounter().get();
            context.blockBeans().add(finBean);

            for (int i = 0; i < context.noNextBlocks().size(); i++) {
                String id = context.noNextBlocks().get(i);
                if (id.equals(tryBean.id)) {
                    context.noNextBlocks().set(i, finBean.id);
                }
                // JavaParser handles try–catch–finally as a single statement
                // so we must manually replace the tryCatch block ID with the finally block ID (if present)
            }
        });
    }

}
