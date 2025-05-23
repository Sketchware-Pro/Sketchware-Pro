package pro.sketchware.blocks.generator.matchers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.blocks.generator.utils.TranslatorUtils;

public class BlockCodeReformater {

    private final String formattedCode1;
    private final String formattedCode2;

    private final Pattern holderPattern = Pattern.compile(TranslatorUtils.getParamHolderRegex());

    public BlockCodeReformater(String code1, String code2) {
        this.formattedCode1 = formatAndRestore(code1);
        this.formattedCode2 = tryFormat(code2);
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

    public String getFormattedBlockCode() {
        return formattedCode1;
    }

    public String getFormattedInputCode() {
        return formattedCode2;
    }

}
