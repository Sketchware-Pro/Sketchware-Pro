package pro.sketchware.blocks.generator;

import android.util.Log;

import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dev.aldi.sayuti.block.ExtraBlockFile;
import pro.sketchware.blocks.generator.builders.BooleanTreeBuilder;
import pro.sketchware.blocks.generator.builders.ExpressionBlockBuilder;
import pro.sketchware.blocks.generator.matchers.ExtraBlockMatcher;
import pro.sketchware.blocks.generator.handlers.*;
import pro.sketchware.blocks.generator.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.records.HandlerContext;
import pro.sketchware.blocks.generator.resources.BlocksCategories;
import pro.sketchware.blocks.generator.resources.ProjectResourcesHelper;
import pro.sketchware.blocks.generator.resources.SwVanillaBlocksLoader;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.utils.TranslatorUtils;

public class EventBlocksGenerator {

    private final HandlerContext handlerContext;
    private final BlockParamUtil blockParamUtil;
    private final ProjectResourcesHelper projectResourcesHelper;
    private final BlocksCategories blocksCategories;

    private ArrayList<BlockBean> preLoadedBlockBeans = new ArrayList<>();
    private final ArrayList<BlockBean> blockBeans = new ArrayList<>();
    private final ArrayList<String> noNextBlocks = new ArrayList<>();

    private final ArrayList<StatementHandler> handlers = new ArrayList<>();

    private final AtomicInteger idCounter = new AtomicInteger(10);

    private final String javaCode;
    private final String sc_id;
    private String errorMessage = "";

    public EventBlocksGenerator(String sc_id, String javaName, String xmlName, String javaCode) {
        this.javaCode = javaCode;
        this.sc_id = sc_id;
        projectResourcesHelper = new ProjectResourcesHelper(sc_id, javaName, xmlName);
        blockParamUtil = new BlockParamUtil(projectResourcesHelper);
        blocksCategories = new BlocksCategories();

        handlerContext = new HandlerContext(
                blockParamUtil,
                blocksCategories,
                idCounter,
                blockBeans,
                noNextBlocks
        );
    }

    private void initialize() {
        BooleanTreeBuilder booleanTreeBuilder = new BooleanTreeBuilder(blockParamUtil, blocksCategories, idCounter);
        ExpressionBlockBuilder expressionBlockBuilder = new ExpressionBlockBuilder(booleanTreeBuilder, blockParamUtil, blocksCategories, idCounter);
        ExtraBlockMatcher extraBlockMatcher = new ExtraBlockMatcher(blockParamUtil, idCounter, expressionBlockBuilder);

        handlers.add(new IfStatementHandler(booleanTreeBuilder, this));
        handlers.add(new WhileStatementHandler(booleanTreeBuilder, this));
        handlers.add(new ForStatementHandler(this, expressionBlockBuilder));
        handlers.add(new ExpressionStatementHandler(extraBlockMatcher, expressionBlockBuilder));
        handlers.add(new OtherStatementHandler(extraBlockMatcher));

        projectResourcesHelper.initialize(javaCode);
        blocksCategories.loadBlocks(TranslatorUtils.getPreLoadedBlocks(preLoadedBlockBeans, sc_id));
        blocksCategories.loadBlocks(projectResourcesHelper.getMoreBlocks());
        blocksCategories.loadBlocks(new SwVanillaBlocksLoader().getSwVanillaBlocks());
        blocksCategories.loadBlocks(ExtraBlockFile.getExtraBlockData());
    }

    public void setPreLoadedBlockBeans(ArrayList<BlockBean> preLoadedBlockBeans) {
        this.preLoadedBlockBeans = preLoadedBlockBeans;
    }

    public ArrayList<BlockBean> getEventBlockBeans() {
        errorMessage = "";
        idCounter.set(10);
        blockBeans.clear();
        noNextBlocks.clear();
        if (javaCode.trim().isEmpty()) {
            return blockBeans;
        }
        String TAG = "BlocksGenerator";
        Log.d(TAG, "loading for code : " + javaCode);
        try {
            initialize();
            BlockStmt body = StaticJavaParser.parseBlock("{" + javaCode + "}");
            NodeList<Statement> statements = body.getStatements();
            for (int i = 0; i < statements.size(); i++) {
                if (i == statements.size() - 1) {
                    noNextBlocks.add(String.valueOf(idCounter.get()));
                }
                Statement stmt = statements.get(i);
                processStatement(stmt);
            }
            TranslatorUtils.reorderBlocks(blockBeans);
            TranslatorUtils.removeUnnecessaryNextIds(blockBeans, noNextBlocks);
            Log.d(TAG, "result : " + new GsonBuilder().setPrettyPrinting().create().toJson(blockBeans));
        } catch (Exception e) {
            errorMessage = Log.getStackTraceString(e);
            Log.wtf(TAG, errorMessage);
        }
        return blockBeans;
    }

    private int nextId() {
        return idCounter.getAndIncrement();
    }

    public void processStatement(Statement stmt) {
        int id = nextId();
        for (StatementHandler handler : handlers) {
            if (handler.canHandle(stmt)) {
                handler.handle(stmt, id, handlerContext);
                return;
            }
        }
    }

    public boolean isSuccessfullyBuild() {
        return errorMessage.isEmpty();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
