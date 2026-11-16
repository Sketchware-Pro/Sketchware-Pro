package pro.sketchware.ai.generator;

import android.util.Xml;

import androidx.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;

/**
 * Lays out code readably for display. Models often answer with whole layouts or method bodies on
 * a single line.
 * <p>
 * Formatting is best effort: input that can't be parsed is returned unchanged.
 */
final class CodeFormatter {

    private static final String INDENT = "    ";

    private CodeFormatter() {
    }

    /**
     * Puts every element on its own line, and every attribute on its own line below it.
     */
    @NonNull
    static String formatXml(@NonNull String xml) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(xml.replaceFirst("^\\s*<\\?xml[^>]*\\?>", "").trim()));
            StringBuilder out = new StringBuilder();
            int depth = 0;
            for (int event = parser.next(); event != XmlPullParser.END_DOCUMENT; event = parser.next()) {
                if (event == XmlPullParser.START_TAG) {
                    boolean empty = parser.isEmptyElementTag();
                    String indent = INDENT.repeat(depth);
                    out.append(indent).append('<').append(parser.getName());
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        out.append('\n').append(indent).append(INDENT)
                                .append(parser.getAttributeName(i)).append("=\"")
                                .append(escapeXml(parser.getAttributeValue(i))).append('"');
                    }
                    if (empty) {
                        out.append(" />\n");
                        // The parser still reports the matching END_TAG
                        parser.next();
                    } else {
                        out.append(">\n");
                        depth++;
                    }
                } else if (event == XmlPullParser.END_TAG) {
                    depth--;
                    out.append(INDENT.repeat(Math.max(depth, 0))).append("</").append(parser.getName()).append(">\n");
                }
            }
            return out.toString().trim();
        } catch (Exception e) {
            return xml.trim();
        }
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;");
    }

    /**
     * Breaks Java statements onto separate lines and indents blocks. Code that already spans
     * several lines is only re-indented.
     */
    @NonNull
    static String formatJava(@NonNull String code) {
        StringBuilder out = new StringBuilder();
        StringBuilder line = new StringBuilder();
        int depth = 0;
        int parentheses = 0;
        char quote = 0;

        String source = code.replace("\r\n", "\n").trim();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            if (quote != 0) {
                line.append(c);
                if (c == '\\' && i + 1 < source.length()) {
                    line.append(source.charAt(++i));
                } else if (c == quote) {
                    quote = 0;
                }
                continue;
            }

            switch (c) {
                case '"', '\'' -> {
                    quote = c;
                    line.append(c);
                }
                case '(' -> {
                    parentheses++;
                    line.append(c);
                }
                case ')' -> {
                    parentheses = Math.max(parentheses - 1, 0);
                    line.append(c);
                }
                case '{' -> {
                    line.append(c);
                    // Braces inside parentheses belong to anonymous classes or array literals
                    // passed as arguments, which still get their own indented lines
                    flushLine(out, line, depth);
                    depth++;
                }
                case '}' -> {
                    flushLine(out, line, depth);
                    depth = Math.max(depth - 1, 0);
                    line.append(c);
                    // Keep "} else {", "});" and "} catch (..) {" together
                    if (!nextNonBlankStartsContinuation(source, i + 1)) {
                        flushLine(out, line, depth);
                    }
                }
                case ';' -> {
                    line.append(c);
                    // The semicolons of a for loop header don't end the line
                    if (parentheses == 0) {
                        flushLine(out, line, depth);
                    }
                }
                case '\n' -> flushLine(out, line, depth);
                default -> {
                    if (!(line.length() == 0 && Character.isWhitespace(c))) {
                        line.append(c);
                    }
                }
            }
        }
        flushLine(out, line, depth);
        return out.toString().trim();
    }

    private static boolean nextNonBlankStartsContinuation(String source, int from) {
        for (int i = from; i < source.length(); i++) {
            char c = source.charAt(i);
            if (!Character.isWhitespace(c)) {
                return c == ')' || c == ';' || c == ',' || source.startsWith("else", i)
                        || source.startsWith("catch", i) || source.startsWith("finally", i)
                        || source.startsWith("while", i);
            }
        }
        return false;
    }

    private static void flushLine(StringBuilder out, StringBuilder line, int depth) {
        String text = line.toString().trim();
        line.setLength(0);
        if (!text.isEmpty()) {
            out.append(INDENT.repeat(depth)).append(text).append('\n');
        }
    }
}
