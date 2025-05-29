package pro.sketchware.blocks.generator.components.handlers;

import com.github.javaparser.ast.stmt.Statement;
import com.besome.sketch.beans.BlockBean;

import java.util.ArrayList;

import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;

public class OtherStatementHandler implements StatementHandler {

    private final BlockGeneratorCoordinator blockGeneratorCoordinator;

    public OtherStatementHandler(BlockGeneratorCoordinator blockGeneratorCoordinator) {
        this.blockGeneratorCoordinator = blockGeneratorCoordinator;
    }

    @Override
    public boolean canHandle(Statement stmt) {
        return true;
    }

    @Override
    public void handle(Statement stmt) {
        String code = stmt.toString().trim();

        ArrayList<BlockBean> extraBlocks = blockGeneratorCoordinator.extraBlockMatcher().tryExtraBlockMatch(code, true, blockGeneratorCoordinator.blocksCategories().getRegularBlocks());
        if (extraBlocks != null) {
            blockGeneratorCoordinator.blockBeans().addAll(extraBlocks);
            return;
        }

        BlockBean bean = new BlockBean(
                String.valueOf(blockGeneratorCoordinator.idCounter().getAndIncrement()),
                "add source directly %s.inputOnly",
                " ",
                "",
                "addSourceDirectly"
        );
        bean.parameters.add(stmt.toString().trim());
        bean.nextBlock = blockGeneratorCoordinator.idCounter().get();
        blockGeneratorCoordinator.blockBeans().add(bean);
    }

}
