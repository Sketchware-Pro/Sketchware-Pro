package ide.sketchware.codeproject.build;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses compiler output (javac/ecj/kotlinc/AAPT2) into structured error objects.
 * Supports standard formats like:
 *   /path/to/File.java:10:5: error: message
 *   /path/to/File.java:10: error: message
 *   /path/to/File.kt:10:5: warning: message
 *   /path/to/layout.xml:3: error: message
 */
public class CompilerErrorParser {

    public static class CompilerError {
        public final String filePath;
        public final int lineNumber;
        public final int columnNumber; // -1 if not available
        public final String message;
        public final boolean isWarning;

        public CompilerError(String filePath, int lineNumber, int columnNumber,
                             String message, boolean isWarning) {
            this.filePath = filePath;
            this.lineNumber = lineNumber;
            this.columnNumber = columnNumber;
            this.message = message;
            this.isWarning = isWarning;
        }
    }

    // Matches lines like: /path/File.java:10:5: error: some message
    // or: /path/File.java:10: error: some message
    // or: /path/File.java:10: some message (treated as error if no severity prefix)
    private static final Pattern ERROR_PATTERN =
            Pattern.compile("(.*\\.(?:java|kt|xml)):(\\d+)(?::(\\d+))?:?\\s*(?:(error|warning):?\\s*)?(.+)");

    // Kotlin compiler diagnostic format: "e: /path/File.kt: (10, 5): message"
    // or: "w: /path/File.kt: (10, 5): message"
    private static final Pattern KOTLIN_ERROR_PATTERN =
            Pattern.compile("([ew]):\\s*(.*\\.(?:java|kt|xml)):\\s*\\((\\d+),\\s*(\\d+)\\):\\s*(.+)");

    /**
     * Parse compiler output text into a list of structured errors.
     *
     * @param compilerOutput the raw compiler output (may contain multiple lines)
     * @return list of parsed compiler errors/warnings
     */
    public static List<CompilerError> parse(String compilerOutput) {
        List<CompilerError> errors = new ArrayList<>();
        if (compilerOutput == null || compilerOutput.isEmpty()) {
            return errors;
        }

        String[] lines = compilerOutput.split("\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            Matcher matcher = ERROR_PATTERN.matcher(trimmed);
            if (matcher.matches()) {
                String filePath = matcher.group(1);
                int lineNumber;
                try {
                    lineNumber = Integer.parseInt(matcher.group(2));
                } catch (NumberFormatException e) {
                    continue;
                }

                int columnNumber = -1;
                if (matcher.group(3) != null) {
                    try {
                        columnNumber = Integer.parseInt(matcher.group(3));
                    } catch (NumberFormatException ignored) {
                    }
                }

                String severity = matcher.group(4);
                boolean isWarning = "warning".equalsIgnoreCase(severity);
                String message = matcher.group(5).trim();

                errors.add(new CompilerError(filePath, lineNumber, columnNumber, message, isWarning));
            } else {
                Matcher ktMatcher = KOTLIN_ERROR_PATTERN.matcher(trimmed);
                if (ktMatcher.matches()) {
                    String severity = ktMatcher.group(1);
                    String filePath = ktMatcher.group(2);
                    int lineNumber = Integer.parseInt(ktMatcher.group(3));
                    int columnNumber = Integer.parseInt(ktMatcher.group(4));
                    String message = ktMatcher.group(5).trim();
                    boolean isWarning = "w".equals(severity);
                    errors.add(new CompilerError(filePath, lineNumber, columnNumber, message, isWarning));
                }
            }
        }
        return errors;
    }
}
