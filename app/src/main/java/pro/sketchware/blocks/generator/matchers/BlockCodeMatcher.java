package pro.sketchware.blocks.generator.matchers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import pro.sketchware.blocks.generator.utils.BlockParamUtil;
import pro.sketchware.blocks.generator.utils.TranslatorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockCodeMatcher {

    private final String formattedBlockCode;
    private final String formattedInputCode;
    private final ArrayList<String> params;
    private final ArrayList<String> paramsHolders;
    private final boolean match;
    private final Pattern holderPattern = Pattern.compile(TranslatorUtils.getParamHolderRegex());

    public BlockCodeMatcher(String spec, String code, String norm, BlockParamUtil blockParamUtil) {
        this.formattedBlockCode = formatAndRestore(code);
        this.formattedInputCode = tryFormat(norm);

        var info = blockParamUtil.getBlockParamInfo(spec, formattedBlockCode, formattedInputCode);
        this.params = info.first;
        this.paramsHolders = info.second;

        boolean validSize = params.size() == paramsHolders.size();
        boolean notParamOnly = !blockParamUtil.isParamOnly(formattedBlockCode);
        String formatted = params.isEmpty() ? formattedBlockCode : String.format(formattedBlockCode, params.toArray());
        boolean codeEqual = formatted.equals(formattedInputCode);
        boolean typeMatch = blockParamUtil.isMatchesParamsTypes(params, paramsHolders);

        this.match = validSize && notParamOnly && codeEqual && typeMatch;
    }

    private String formatAndRestore(String code) {
        Map<String, String> placeholderMap = new HashMap<>();
        Matcher matcher = holderPattern.matcher(code);
        int i = 0;
        StringBuilder tempCode = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            String key = "PLACEHOLDER_" + (i++);
            placeholderMap.put(key, matcher.group());
            tempCode.append(code, lastEnd, matcher.start());
            tempCode.append(key);
            lastEnd = matcher.end();
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

}
