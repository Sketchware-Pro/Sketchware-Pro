package pro.sketchware.blocks.generator.resources;

import android.util.Pair;

import com.besome.sketch.beans.ViewBean;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.eC;
import a.a.a.jC;
import pro.sketchware.blocks.generator.utils.TranslatorUtils;

public class ProjectResourcesHelper {

    private final eC projectDataManager;

    public final int STRING_FIELDS = 0;
    public final int DOUBLE_FIELDS = 1;
    public final int BOOLEAN_FIELDS = 2;
    public final int MAP_FIELDS = 3;
    public final int CUSTOM_VAR_FIELDS = 4;

    public final int WIDGET_FIELDS = 5;

    public final int LIST_INT_FIELDS = 6;
    public final int LIST_STRING_FIELDS = 7;
    public final int LIST_MAP_FIELDS = 8;
    public final int LIST_CUSTOM_FIELDS = 9;

    private final ArrayList<String> stringFields = new ArrayList<>();
    private final ArrayList<String> doubleFields = new ArrayList<>();
    private final ArrayList<String> booleanFields = new ArrayList<>();
    private final ArrayList<String> mapFields = new ArrayList<>();
    private final ArrayList<String> customVarFields = new ArrayList<>();

    private final ArrayList<String> widgetFields = new ArrayList<>();

    private final ArrayList<String> listIntFields = new ArrayList<>();
    private final ArrayList<String> listStringFields = new ArrayList<>();
    private final ArrayList<String> listMapFields = new ArrayList<>();
    private final ArrayList<String> listCustomFields = new ArrayList<>();

    private final ArrayList<HashMap<String, Object>> moreBlocks = new ArrayList<>();

    private final String javaName;
    private final String xmlName;
    private final String sc_id;

    public ProjectResourcesHelper(String sc_id, String javaName, String xmlName) {
        this.sc_id = sc_id;
        this.javaName = javaName;
        this.xmlName = xmlName;
        projectDataManager = jC.a(sc_id);
    }

    public void initialize(String javaCode) {
        loadFields();
        loadLocalVariables(javaCode);
        loadWidgetFields();
        loadMoreBlocks();
    }

    private void loadFields() {
        for (Pair<Integer, String> next : projectDataManager.k(javaName)) {
            switch (next.first) {
                case 0 -> booleanFields.add(next.second);
                case 1 -> doubleFields.add(next.second);
                case 2 -> stringFields.add(next.second);
                case 3 -> mapFields.add(next.second);
                default -> customVarFields.add(next.second);
            }
        }

        for (Pair<Integer, String> next2 : projectDataManager.j(javaName)) {
            switch (next2.first) {
                case 1 -> listIntFields.add(next2.second);
                case 2 -> listStringFields.add(next2.second);
                case 3 -> listMapFields.add(next2.second);
                default -> listCustomFields.add(next2.second);
            }
        }
    }

    private void loadLocalVariables(String javaCode) {
        BlockStmt block = StaticJavaParser.parseBlock("{" + javaCode + "}");
        for (Statement stmt : block.getStatements()) {
            if (stmt.isExpressionStmt()) {
                Expression expr = stmt.asExpressionStmt().getExpression();
                if (expr.isVariableDeclarationExpr()) {
                    VariableDeclarationExpr varDeclaration = expr.asVariableDeclarationExpr();
                    for (VariableDeclarator var : varDeclaration.getVariables()) {
                        String varName = var.getNameAsString();
                        String varType = var.getType().toString();

                        switch (varType) {
                            case "String" -> stringFields.add(varName);
                            case "double" -> doubleFields.add(varName);
                            case "boolean" -> booleanFields.add(varName);
                            case "HashMap<String, Object>" -> mapFields.add(varName);
                            case "ArrayList<Double>" -> listIntFields.add(varName);
                            case "ArrayList<String>" -> listStringFields.add(varName);
                            case "ArrayList<HashMap<String, Object>>" -> listMapFields.add(varName);
                            default -> {
                                if (varType.startsWith("ArrayList<") || varType.startsWith("List<")) {
                                    listCustomFields.add(varName);
                                } else {
                                    customVarFields.add(varName);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadWidgetFields() {
        ArrayList<ViewBean> views = jC.a(sc_id).d(xmlName);
        for (ViewBean viewBean : views) {
            String convert = viewBean.convert;
            if (!convert.equals("include")) {
                widgetFields.add(viewBean.id);
            }
        }
    }

    private void loadMoreBlocks() {
        moreBlocks.addAll(new MoreBlockDataLoader(sc_id).getMoreBlocks(javaName));
    }

    public Pair<String, String> getMoreBlockType(String moreBlockName) {
        if (moreBlockName.isEmpty())
            return null;
        for (var blockMap : moreBlocks) {
            if (TranslatorUtils.safeGetString(blockMap.get("moreBlockName")).equals(moreBlockName)) {
                String typeName = TranslatorUtils.safeGetString(blockMap.get("typeName"));
                if (typeName.isEmpty()) {
                    return new Pair<>(TranslatorUtils.safeGetString(blockMap.get("type")), null);
                } else {
                    return new Pair<>(TranslatorUtils.safeGetString(blockMap.get("type")), TranslatorUtils.safeGetString(blockMap.get("typeName")));
                }
            }
        }
        return null;
    }

    public ArrayList<String> getFields(int fieldsType) {
        return switch (fieldsType) {
            case STRING_FIELDS -> stringFields;
            case DOUBLE_FIELDS -> doubleFields;
            case BOOLEAN_FIELDS -> booleanFields;
            case MAP_FIELDS -> mapFields;
            case CUSTOM_VAR_FIELDS -> customVarFields;
            case WIDGET_FIELDS -> widgetFields;
            case LIST_INT_FIELDS -> listIntFields;
            case LIST_STRING_FIELDS -> listStringFields;
            case LIST_MAP_FIELDS -> listMapFields;
            case LIST_CUSTOM_FIELDS -> listCustomFields;
            default -> new ArrayList<>();
        };
    }

    public ArrayList<String> getFields(int... fields) {
        ArrayList<String> result = new ArrayList<>();
        for (int field : fields) {
            result.addAll(getFields(field));
        }
        return result;
    }

    public ArrayList<String> getAllFieldsExcept(int exception) {
        ArrayList<String> result = new ArrayList<>();
        if (exception != STRING_FIELDS) {
            result.addAll(stringFields);
        }
        if (exception != DOUBLE_FIELDS) {
            result.addAll(doubleFields);
        }
        if (exception != BOOLEAN_FIELDS) {
            result.addAll(booleanFields);
        }
        if (exception != MAP_FIELDS) {
            result.addAll(mapFields);
        }
        if (exception != CUSTOM_VAR_FIELDS) {
            result.addAll(customVarFields);
        }
        if (exception != WIDGET_FIELDS) {
            result.addAll(widgetFields);
        }
        if (exception != LIST_INT_FIELDS) {
            result.addAll(listIntFields);
        }
        if (exception != LIST_STRING_FIELDS) {
            result.addAll(listStringFields);
        }
        if (exception != LIST_MAP_FIELDS) {
            result.addAll(listMapFields);
        }
        if (exception != LIST_CUSTOM_FIELDS) {
            result.addAll(listCustomFields);
        }
        return result;
    }

    public ArrayList<HashMap<String, Object>> getMoreBlocks() {
        return moreBlocks;
    }

}
