package pro.sketchware.blocks.generator.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import pro.sketchware.blocks.generator.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;

public class OtherStatementHandler implements StatementHandler {

    private final ExtraBlockMatcher extraBlockMatcher;

    public OtherStatementHandler(ExtraBlockMatcher extraBlockMatcher) {
        this.extraBlockMatcher = extraBlockMatcher;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return true;
    }

    @Override
    public void handle(Statement stmt, int id, HandlerContext context) {
        int next = context.idCounter().get();
        String code = stmt.toString().trim();

        ArrayList<BlockBean> extraBlocks = extraBlockMatcher.tryExtraBlockMatch(code, id, next, context.blocksCategories().getRegularBlocks());
        if (extraBlocks != null) {
            context.blockBeans().addAll(extraBlocks);
            return;
        }

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
