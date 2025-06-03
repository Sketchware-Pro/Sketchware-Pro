package pro.sketchware.blocks.generator.components;

import mod.hey.studios.project.ProjectSettings;
import pro.sketchware.blocks.generator.components.builders.BinaryExprOperatorsTreeBuilder;
import pro.sketchware.blocks.generator.components.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.components.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.components.resources.ProjectResourcesHelper;
import pro.sketchware.blocks.generator.components.resources.BlocksCategories;
import pro.sketchware.blocks.generator.components.utils.BlockParamUtils;
import pro.sketchware.blocks.generator.components.interfaces.StatementProcessor;

import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockGeneratorCoordinator {

    private final BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder;
    private final ProjectResourcesHelper projectResourcesHelper;
    private final ExpressionBlockBuilder expressionBlockBuilder;
    private final ExtraBlockMatcher extraBlockMatcher;
    private final BlocksCategories blocksCategories;
    private StatementProcessor statementProcessor;
    private final BlockParamUtils blockParamUtils;
    private final ProjectSettings projectSettings;

    private final ArrayList<BlockBean> blockBeans = new ArrayList<>();
    private final ArrayList<String> noNextBlocks = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(10);

    public BlockGeneratorCoordinator(String sc_id, String javaName, String xmlName, String eventTitle, String javaCode) {
        projectResourcesHelper = new ProjectResourcesHelper(sc_id, javaName, xmlName, eventTitle, javaCode);
        blockParamUtils = new BlockParamUtils(projectResourcesHelper);
        blocksCategories = new BlocksCategories();
        binaryExprOperatorsTreeBuilder = new BinaryExprOperatorsTreeBuilder(this);
        expressionBlockBuilder = new ExpressionBlockBuilder(this);
        extraBlockMatcher = new ExtraBlockMatcher(this);

        projectSettings = new ProjectSettings(sc_id);
    }

    public void setStatementProcessor(StatementProcessor statementProcessor) {
        this.statementProcessor = statementProcessor;
    }

    public void processStatements(NodeList<Statement> statements) {
        if (statementProcessor != null) {
            for (int i = 0; i < statements.size(); i++) {
                if (i == statements.size() - 1) {
                    noNextBlocks().add(String.valueOf(idCounter().get()));
                }
                Statement stmt = statements.get(i);
                statementProcessor.processStatement(stmt);
            }
        }
    }

    public BlockParamUtils blockParamUtil() {
        return blockParamUtils;
    }

    public ProjectResourcesHelper projectResourcesHelper() {
        return projectResourcesHelper;
    }

    public BinaryExprOperatorsTreeBuilder binaryExprOperatorsTreeBuilder() {
        return binaryExprOperatorsTreeBuilder;
    }

    public ExpressionBlockBuilder expressionBlockBuilder() {
        return expressionBlockBuilder;
    }

    public ExtraBlockMatcher extraBlockMatcher() {
        return extraBlockMatcher;
    }

    public BlocksCategories blocksCategories() {
        return blocksCategories;
    }

    public AtomicInteger idCounter() {
        return idCounter;
    }

    public ArrayList<BlockBean> blockBeans() {
        return blockBeans;
    }

    public ArrayList<String> noNextBlocks() {
        return noNextBlocks;
    }

    public boolean isViewBindingEnabled() {
        return projectSettings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, "false").equals("true");
    }

}
