package pro.sketchware.blocks.generator.components.matchers;

import android.util.Pair;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;

import pro.sketchware.blocks.generator.components.utils.BlockParamUtils;
import pro.sketchware.blocks.generator.components.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockCodeMatcher {

    private final String formattedBlockCode;
    private final String formattedInputCode;
    private ArrayList<String> params;
    private ArrayList<String> paramsHolders;
    private boolean match;
    private final Pattern holderPattern = Pattern.compile(TranslatorUtils.getParamHolderRegex());
    private Expression blockCodeExpr;
    private Expression inputExpr;

    private String result;

    public BlockCodeMatcher(String blockType, String spec, String code, String norm, BlockParamUtils blockParamUtils) {
        String normalizedCode = normalizeWhitespace(code);
        String normalizedInput = normalizeWhitespace(norm);

        this.formattedBlockCode = formatAndRestore(normalizedCode, blockType);
        this.formattedInputCode = tryFormat(normalizedInput);

        try {
            Pair<ArrayList<String>, ArrayList<String>> info;
            if (blockCodeExpr != null && inputExpr != null) {
                info = blockParamUtils.getBlockParamInfo(blockType, spec, formattedBlockCode, blockCodeExpr, inputExpr);
            } else {
                info = blockParamUtils.getBlockParamInfo(blockType, spec, formattedBlockCode, formattedInputCode);
            }
            this.params = info.first;
            this.paramsHolders = info.second;

            boolean validSize = params.size() == paramsHolders.size();
            boolean notParamOnly = !blockParamUtils.isParamOnly(formattedBlockCode);
            String formatted = params.isEmpty() ? formattedBlockCode : String.format(formattedBlockCode, params.toArray());
            boolean codeEqual = formatted.equals(formattedInputCode);
            boolean typeMatch = blockParamUtils.isMatchesParamsTypes(params, paramsHolders);

            this.result = String.format("validSize = %s ---|--- notParamOnly = %s ---|--- codeEqual = %s ---|--- typeMatch = %s",
                    validSize, notParamOnly, codeEqual, typeMatch);

            this.match = validSize && notParamOnly && codeEqual && typeMatch;
        } catch (Exception e) {
            this.match = false;
            this.result = "Failed with exception : " + e.getMessage();
        }
    }

    private String normalizeWhitespace(String input) {
        return input.replaceAll("\\s+", " ").trim();
    }

    private String formatAndRestore(String code, String blockType) {
        Matcher countMatcher = holderPattern.matcher(code);
        int total = 0;
        while (countMatcher.find()) total++;

        Map<String, String> placeholderMap = new HashMap<>();
        Matcher matcher = holderPattern.matcher(code);
        StringBuilder tempCode = new StringBuilder();
        int lastEnd = 0;
        int index = 0;

        while (matcher.find()) {
            String key = BlockParamUtils.PLACEHOLDER_PARAM + index;
            if ("c".equals(blockType) && index == total - 1) {
                key += "();";
            } else if ("e".equals(blockType) && index >= total - 2) {
                key += "();";
            }
            placeholderMap.put(key, matcher.group());

            tempCode.append(code, lastEnd, matcher.start());
            tempCode.append(key);
            lastEnd = matcher.end();
            index++;
        }
        tempCode.append(code.substring(lastEnd));

        String formatted;
        try {
            formatted = tryFormat(tempCode.toString());
        } catch (Exception e) {
            return code.trim();
        }

        for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
            formatted = formatted.replace(entry.getKey(), entry.getValue());
        }

        return formatted;
    }

    private String tryFormat(String code) {
        try {
            Expression expr = StaticJavaParser.parseExpression(code);
            if (expr instanceof MethodCallExpr) {
                if (blockCodeExpr == null) {
                    blockCodeExpr = expr;
                } else if (inputExpr == null) {
                    inputExpr = expr;
                }
            }
            return expr.toString();
        } catch (Exception ignored) {}

        try {
            Statement stmt = StaticJavaParser.parseStatement(code);
            return stmt.toString();
        } catch (Exception ignored) {}

        try {
            BlockStmt block = StaticJavaParser.parseBlock("{" + code + "}");
            return block.toString();
        } catch (Exception ignored) {}

        return code.trim();
    }

    public boolean isMatch() {
        return match;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public ArrayList<String> getParamsHolders() {
        return paramsHolders;
    }

    public String getFormattedBlockCode() {
        return formattedBlockCode;
    }

    public String getFormattedInputCode() {
        return formattedInputCode;
    }

    public String getResult() {
        return result;
    }

}
