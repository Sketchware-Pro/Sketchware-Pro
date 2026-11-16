package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sketchware generates every More Block as a method whose name starts with an underscore, so
 * <code>loadNotes</code> becomes <code>_loadNotes()</code>. Models often call the method without
 * it anyway, which doesn't compile. This adds the missing underscores.
 * <p>
 * Only calls that are clearly to the Activity's own method are changed: a bare
 * <code>loadNotes(</code>, or one on <code>this</code> or <code>SomeActivity.this</code>. String
 * and character literals are left alone.
 */
final class MethodCallFixer {

    private final Pattern callPattern;

    /**
     * @param methodNames The screen's method names, without the underscore.
     */
    MethodCallFixer(@NonNull Collection<String> methodNames) {
        if (methodNames.isEmpty()) {
            callPattern = null;
            return;
        }
        StringBuilder names = new StringBuilder();
        for (String name : methodNames) {
            if (names.length() > 0) {
                names.append('|');
            }
            names.append(Pattern.quote(name));
        }
        // The name, not preceded by a word character, nor by a "." unless that "." follows "this"
        callPattern = Pattern.compile("(?<!\\w)(?<!(?<!this)\\.)(" + names + ")(\\s*\\()");
    }

    @NonNull
    String fix(@NonNull String code) {
        if (callPattern == null) {
            return code;
        }
        StringBuilder fixed = new StringBuilder(code.length() + 16);
        int segmentStart = 0;
        int i = 0;
        while (i < code.length()) {
            char c = code.charAt(i);
            if (c == '"' || c == '\'') {
                fixed.append(fixSegment(code.substring(segmentStart, i)));
                int end = endOfLiteral(code, i);
                fixed.append(code, i, end);
                i = end;
                segmentStart = end;
            } else {
                i++;
            }
        }
        fixed.append(fixSegment(code.substring(segmentStart)));
        return fixed.toString();
    }

    private String fixSegment(String segment) {
        Matcher matcher = callPattern.matcher(segment);
        return matcher.replaceAll("_$1$2");
    }

    /**
     * @return The index just after the literal that starts at <code>start</code>.
     */
    private int endOfLiteral(String code, int start) {
        char quote = code.charAt(start);
        int i = start + 1;
        while (i < code.length()) {
            char c = code.charAt(i);
            if (c == '\\') {
                i += 2;
            } else if (c == quote || c == '\n') {
                return i + 1;
            } else {
                i++;
            }
        }
        return code.length();
    }
}
