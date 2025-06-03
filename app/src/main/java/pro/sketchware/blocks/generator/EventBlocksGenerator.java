package pro.sketchware.blocks.generator;

import android.util.Log;

import com.besome.sketch.beans.BlockBean;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import dev.aldi.sayuti.block.ExtraBlockFile;
import pro.sketchware.blocks.generator.components.handlers.*;
import pro.sketchware.blocks.generator.components.interfaces.StatementHandler;
import pro.sketchware.blocks.generator.components.BlockGeneratorCoordinator;
import pro.sketchware.blocks.generator.components.resources.SwVanillaBlocksLoader;
import pro.sketchware.blocks.generator.components.utils.TranslatorUtils;

public class EventBlocksGenerator {

    private BlockGeneratorCoordinator blockGeneratorCoordinator;

    private ArrayList<BlockBean> preLoadedBlockBeans = new ArrayList<>();

    private final ArrayList<StatementHandler> handlers = new ArrayList<>();

    private final String javaCode;
    private final String javaName;
    private final String xmlName;
    private final String eventTitle;
    private final String sc_id;
    private String errorMessage = "";

    public EventBlocksGenerator(String sc_id, String javaName, String xmlName, String eventTitle, String javaCode) {
        this.javaCode = javaCode;
        this.javaName = javaName;
        this.xmlName = xmlName;
        this.eventTitle = eventTitle;
        this.sc_id = sc_id;
    }

    private void initialize() {
        blockGeneratorCoordinator = new BlockGeneratorCoordinator(sc_id, javaName, xmlName, eventTitle, javaCode);
        blockGeneratorCoordinator.setStatementProcessor(this::processStatement);

        handlers.add(new IfStatementHandler(blockGeneratorCoordinator));
        handlers.add(new WhileStatementHandler(blockGeneratorCoordinator));
        handlers.add(new ForStatementHandler(blockGeneratorCoordinator));
        handlers.add(new TryCatchFinallyStatementHandler(blockGeneratorCoordinator));
        handlers.add(new SwitchStatementHandler(blockGeneratorCoordinator));
        handlers.add(new ExpressionStatementHandler(blockGeneratorCoordinator));
        handlers.add(new OtherStatementHandler(blockGeneratorCoordinator));

        blockGeneratorCoordinator.blocksCategories().loadBlocks(blockGeneratorCoordinator.projectResourcesHelper().getMoreBlocks());
        blockGeneratorCoordinator.blocksCategories().loadBlocks(new SwVanillaBlocksLoader().getSwVanillaBlocks(blockGeneratorCoordinator.isViewBindingEnabled()));
        blockGeneratorCoordinator.blocksCategories().loadBlocks(ExtraBlockFile.getExtraBlockData());
    }

    public void setPreLoadedBlockBeans(ArrayList<BlockBean> preLoadedBlockBeans) {
        this.preLoadedBlockBeans = preLoadedBlockBeans;
    }

    public ArrayList<BlockBean> getEventBlockBeans() {
        errorMessage = "";
        if (javaCode.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String TAG = "BlocksGenerator";
        Log.d(TAG, "loading for code : " + javaCode);
        try {
            initialize();
            BlockStmt body = StaticJavaParser.parseBlock("{" + javaCode + "}");
            NodeList<Statement> statements = body.getStatements();
            for (int i = 0; i < statements.size(); i++) {
                if (i == statements.size() - 1) {
                    blockGeneratorCoordinator.noNextBlocks().add(String.valueOf(blockGeneratorCoordinator.idCounter().get()));
                }
                Statement stmt = statements.get(i);
                processStatement(stmt);
            }
            TranslatorUtils.reorderBlocks(blockGeneratorCoordinator.blockBeans());
            TranslatorUtils.removeUnnecessaryNextIds(blockGeneratorCoordinator.blockBeans(), blockGeneratorCoordinator.noNextBlocks());
            Log.d(TAG, "result : " + new GsonBuilder().setPrettyPrinting().create().toJson(blockGeneratorCoordinator.blockBeans()));
            return blockGeneratorCoordinator.blockBeans();
        } catch (Exception e) {
            errorMessage = Log.getStackTraceString(e);
            Log.wtf(TAG, errorMessage);
            return new ArrayList<>();
        }
    }

    public void processStatement(Statement stmt) {
        for (StatementHandler handler : handlers) {
            if (handler.canHandle(stmt)) {
                handler.handle(stmt);
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
