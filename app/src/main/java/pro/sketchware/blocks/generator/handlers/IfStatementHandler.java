package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.IfStmt;
import com.besome.sketch.beans.BlockBean;
import pro.sketchware.blocks.generator.builders.BinaryExprOperatorsTreeBuilder;
import pro.sketchware.blocks.generator.EventBlocksGenerator;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;
import pro.sketchware.blocks.generator.records.RequiredBlockType;

import java.util.List;

public class IfStatementHandler implements StatementHandler {

    private final BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder;
    private final EventBlocksGenerator parent;

    public IfStatementHandler(BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder, EventBlocksGenerator parent) {
        this.binaryExprOperatorsTreeBuilder = binaryExprOperatorsTreeBuilder;
        this.parent = parent;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return stmt instanceof IfStmt;
    }

    @Override
    public void handle(Statement stmt, int id, HandlerContext context) {
        IfStmt ifStmt = (IfStmt) stmt;
        List<BlockBean> blockBeans = context.blockBeans();
        List<String> noNextBlocks = context.noNextBlocks();

        List<BlockBean> condTree = binaryExprOperatorsTreeBuilder.build(ifStmt.getCondition(), new RequiredBlockType("b"));
        blockBeans.addAll(condTree);
        BlockBean condRoot = condTree.get(condTree.size() - 1);
        condRoot.nextBlock = -1;

        boolean hasElse = ifStmt.getElseStmt().isPresent();
        String spec = hasElse ? "if %b then" : "if %b";
        String opCode = hasElse ? "ifElse" : "if";
        String type = hasElse ? "e" : "c";

        BlockBean ifBean = new BlockBean(
                String.valueOf(id), spec, type, "", opCode
        );
        ifBean.parameters.add("@" + condRoot.id);

        if (ifStmt.getThenStmt().isBlockStmt()) {
            var thenStatements = ifStmt.getThenStmt().asBlockStmt().getStatements();
            if (thenStatements.isEmpty()) {
                ifBean.subStack1 = -1;
            } else {
                ifBean.subStack1 = context.idCounter().get();
                for (int i = 0; i < thenStatements.size(); i++) {
                    if (i == thenStatements.size() - 1) {
                        noNextBlocks.add(String.valueOf(context.idCounter().get()));
                    }
                    Statement s = thenStatements.get(i);
                    parent.processStatement(s);
                }
            }
        } else {
            ifBean.subStack1 = -1;
        }
        if (hasElse) {
            Statement es = ifStmt.getElseStmt().get();
            if (es.isIfStmt()) {
                int elseId = context.idCounter().getAndIncrement();
                ifBean.subStack2 = elseId;
                handle(es, elseId, context);
            } else if (es.isBlockStmt()) {
                var elseStatements = es.asBlockStmt().getStatements();
                if (elseStatements.isEmpty()) {
                    ifBean.subStack2 = -1;
                } else {
                    ifBean.subStack2 = context.idCounter().get();
                    for (int i = 0; i < elseStatements.size(); i++) {
                        if (i == elseStatements.size() - 1) {
                            noNextBlocks.add(String.valueOf(context.idCounter().get()));
                        }
                        Statement s = elseStatements.get(i);
                        parent.processStatement(s);
                    }
                }
            } else {
                ifBean.subStack2 = -1;
            }
        } else {
            ifBean.subStack2 = -1;
        }

        ifBean.nextBlock = context.idCounter().get();
        int pos = blockBeans.indexOf(condRoot) + 1;
        blockBeans.add(pos, ifBean);
    }

}
