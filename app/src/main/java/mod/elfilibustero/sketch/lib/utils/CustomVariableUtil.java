package mod.elfilibustero.sketch.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomVariableUtil {
    public static final Pattern PATTERN_CUSTOM_VARIABLE = Pattern.compile(
            "\\b((private|public|protected|static|final|transient|volatile)\\s+)*"
            + "([\\w$]+(?:\\s*,\\s*[\\w$]+)*"
            + "(?:\\s*<[a-zA-Z0-9.,_ ?<>\\[\\]]*>)?"
            + "(?:\\[\\])*)?\\s+"
            + "([\\w$]+)"
            + "(?:\\s*=\\s*([^;]+))?\\s*"
    );

    private static final int VARIABLE_TYPE = 3;
    private static final int VARIABLE_NAME = 4;
    private static final int VARIABLE_INITIALIZER = 5;
  
    public static String getVariableModifier(String input) {
        Matcher matcher = getMatcher(input);
        if (matcher.find()) {
            String variableType = matcher.group(VARIABLE_TYPE);
            if (variableType != null) {
                int equalIndex = input.indexOf(variableType);
                int varLength = variableType.length();
                if (equalIndex != -1) {
                    return input.substring(0, equalIndex).trim();
                }
            }
        }
        return null;
    }

    public static String getVariableType(String input) {
        Matcher matcher = getMatcher(input);
        if (matcher.find()) {
            return removeSpaces(matcher.group(VARIABLE_TYPE));
        }
        return null;
    }

    public static String getVariableName(String input) {
        Matcher matcher = getMatcher(input);
        if (matcher.find()) {
            return matcher.group(VARIABLE_NAME);
        }
        return null;
    }

    public static String getVariableInitializer(String input) {
        Matcher matcher = getMatcher(input);
        if (matcher.find()) {
            return matcher.group(VARIABLE_INITIALIZER);
        }
        return null;
    }

    private static Matcher getMatcher(String input) {
        return PATTERN_CUSTOM_VARIABLE.matcher(input);
    }
    
    private static String removeSpaces(String variableType) {
        return variableType.replaceAll("\\s", "");
    }
}
