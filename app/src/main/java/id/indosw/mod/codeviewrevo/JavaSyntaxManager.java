package id.indosw.mod.codeviewrevo;

import android.content.Context;

import java.util.regex.Pattern;

public class JavaSyntaxManager {

    private static final Pattern PATTERN_ANNOTATION = Pattern.compile("@.[a-zA-Z0-9]+");
    private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");
    private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
    private static final Pattern PATTERN_COMMENT = Pattern.compile("/\\*(.|[\\r\n])*?\\*/|//.*");
    private static final Pattern PATTERN_GENERIC = Pattern.compile("<[a-zA-Z0-9,<>]+>");
    private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile("\\b(abstract|boolean|break|byte|case|catch|char|class|continue|default|do|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|transient|try|void|volatile|while)\\b");
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_OPERATION = Pattern.compile(":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*");
    private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"");
    private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("//TODO[^\n]*");

    public static void applyMonokaiTheme(Context context, CodeViewRevo codeView) {
        codeView.resetSyntaxPatternList();
        codeView.setBackgroundColor(codeView.getResources().getColor(2131034330));
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(2131034335));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(2131034329));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(2131034334));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(2131034335));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(2131034332));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(2131034331));
        codeView.addSyntaxPattern(PATTERN_COMMENT, context.getResources().getColor(2131034336));
        codeView.addSyntaxPattern(PATTERN_ANNOTATION, context.getResources().getColor(2131034332));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(2131034333));
        codeView.addSyntaxPattern(PATTERN_GENERIC, context.getResources().getColor(2131034332));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(2131034332));
        codeView.setTextColor(context.getResources().getColor(2131034331));
        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(2131034328));
        codeView.reHighlightSyntax();
    }
}
