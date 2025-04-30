package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.besome.sketch.beans.BlockBean;

import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;

public class OtherStatementHandler implements StatementHandler {

    @Override
    public boolean canHandle(Statement stmt) {
        return true;
    }

    @Override
    public void handle(Statement stmt, int id, HandlerContext context) {
        int next = context.idCounter().get();
        BlockBean bean = new BlockBean(
                String.valueOf(id),
                "add source directly %s.inputOnly",
                " ",
                "",
                "addSourceDirectly"
        );
        bean.parameters.add(stmt.toString().trim());
        bean.nextBlock = next;
        context.blockBeans().add(bean);
    }

}
