package mod.hey.studios.code;

import io.github.rosemoe.editor.langs.universal.LanguageDescription;

public class KotlinDescription implements LanguageDescription {

    @Override
    public boolean isOperator(char[] characters, int length) {
        if (length == 1) {
            char c = characters[0];
            return (c == '+' || c == '-' || c == '{' || c == '}' || c == '[' || c == ']' ||
                    c == '(' || c == ')' || c == '|' || c == ':' || c == '.' || c == ',' ||
                    c == ';' || c == '*' || c == '/' || c == '&' || c == '^' || c == '%' ||
                    c == '!' || c == '~' || c == '<' || c == '>' || c == '=' || c == '?');
        }
        return false;
    }

    @Override
    public boolean isLineCommentStart(char a, char b) {
        return a == '/' && b == '/';
    }

    @Override
    public boolean isLongCommentStart(char a, char b) {
        return a == '/' && b == '*';
    }

    @Override
    public boolean isLongCommentEnd(char a, char b) {
        return a == '*' && b == '/';
    }

    @Override
    public String[] getKeywords() {
        /*
         * https://kotlinlang.org/docs/keyword-reference.html
         */
        return new String[]{
                // Hard keywords
                "as", "break", "class", "continue", "do",
                "else", "false", "for", "fun", "if", "in",
                "interface", "is", "null", "object",
                "package", "return", "super", "this",
                "throw", "true", "try", "typealias",
                "typeof", "val", "var", "when", "while",

                // Soft keywords
                "by", "catch", "constructor", "delegate",
                "dynamic", "field", "file", "finally",
                "get", "import", "init", "param", "property",
                "receiver", "set", "setparam", "value", "where",

                // Modifier keywords
                "actual", "abstract", "annotation", "companion",
                "const", "crossinline", "data", "enum", "expect",
                "external", "final", "infix", "inline", "inner",
                "internal", "lateinit", "noinline", "open",
                "operator", "out", "override", "private", "protected",
                "public", "reified", "sealed", "suspend", "tailrec",
                "vararg",

                // Special identifiers
                "field", "it"
        };
    }

    @Override
    public boolean useTab() {
        return false;
    }

    @Override
    public int getOperatorAdvance(String operator) {
        switch (operator) {
            case "{":
                return 4;
            case "}":
                return -4;
        }
        return 0;
    }

    @Override
    public boolean isSupportBlockLine() {
        return true;
    }

    @Override
    public boolean isBlockStart(String p1) {
        return p1.equals("{");
    }

    @Override
    public boolean isBlockEnd(String p1) {
        return p1.equals("}");
    }

}
