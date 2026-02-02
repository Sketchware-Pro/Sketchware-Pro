package mod.fnmods.chat.ia.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeTextView extends AppCompatTextView {

    public CodeTextView(Context context) {
        super(context);
        init();
    }

    public CodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(Typeface.MONOSPACE);
        setTextSize(13);
//        setPadding(24, 16, 24, 16);
//        setLineSpacing(1.2f, 1.2f);
    }
    private void highlightPattern(SpannableString s, String regex, String color) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            s.setSpan(
                    new ForegroundColorSpan(Color.parseColor(color)),
                    matcher.start(),
                    matcher.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
    }
    public void setCode(String code, String language) {
        SpannableString spannable = new SpannableString(formatByLanguage(code, language));
        switch (language.toLowerCase()) {
            case "kotlin", "java", "javascript", "python", "c", "cpp", "gradle":
                highlightJava(spannable);
                break;
            case "xml","html":
                highlightXml(spannable);
                break;
            case "json":
                highlightJson(spannable);
                break;
        }

        setText(spannable);
    }
    private String formatByLanguage(String code, String lang) {
        code = normalizeCode(code);

        switch (lang.toLowerCase()) {
            case "xml":
                return formatXml(code);
            case "json":
                return formatJson(code);
            case "java":
            case "kotlin":
            case "gradle":
                return autoIndent(code);
            default:
                return code;
        }
    }
    private String formatXml(String xml) {
        xml = xml.replaceAll("><", ">\n<");
        StringBuilder out = new StringBuilder();
        int indent = 0;

        for (String line : xml.split("\n")) {
            line = line.trim();

            if (line.startsWith("</")) indent--;

            out.append("    ".repeat(Math.max(0, indent)))
                    .append(line)
                    .append("\n");

            if (line.matches("<[^/!?].*[^/]?>")) indent++;
        }
        return out.toString();
    }
    private String formatJson(String json) {
        json = json.replaceAll("(?<!\\\\)\"", "\"");
        return autoIndent(
                json.replace("{", "{\n")
                        .replace("}", "\n}")
                        .replace(",", ",\n")
        );
    }

    private String autoIndent(String code) {
        StringBuilder out = new StringBuilder();
        int indent = 0;

        for (String line : code.split("\n")) {
            line = line.trim();

            if (line.endsWith("}") || line.startsWith("}")) {
                indent = Math.max(0, indent - 1);
            }

            out.append("    ".repeat(indent))
                    .append(line)
                    .append("\n");

            if (line.endsWith("{")) {
                indent++;
            }
        }
        return out.toString();
    }
    private String normalizeCode(String code) {
        // Quitar espacios basura
        code = code.replace("\t", "    ");

        // Evitar líneas infinitas
        code = code.replaceAll("\\r", "");

        // Quitar múltiples líneas vacías
        code = code.replaceAll("\n{3,}", "\n\n");

        return code.trim();
    }

    private void highlightJava(SpannableString s) {

        // Keywords
        highlightPattern(s,
                "\\b(public|private|protected|class|static|void|int|String|new|return|if|else|for|while)\\b",
                "#569CD6");

        // Strings
        highlightPattern(s,
                "\"(.*?)\"",
                "#CE9178");

        // Comments
        highlightPattern(s,
                "//.*",
                "#6A9955");

        // Numbers
        highlightPattern(s,
                "\\b\\d+\\b",
                "#B5CEA8");
    }
    private void highlightXml(SpannableString s) {

        // Tags
        highlightPattern(s,
                "</?[^>]+>",
                "#569CD6");

        // Attributes
        highlightPattern(s,
                "\\b[a-zA-Z_:]+=",
                "#9CDCFE");

        // Values
        highlightPattern(s,
                "\"(.*?)\"",
                "#CE9178");
    }
    private void highlightJson(SpannableString s) {

        // Keys
        highlightPattern(s,
                "\"(.*?)\"(?=\\s*:)",
                "#9CDCFE");

        // Values
        highlightPattern(s,
                ":\\s*\"(.*?)\"",
                "#CE9178");

        // Numbers
        highlightPattern(s,
                "\\b\\d+\\b",
                "#B5CEA8");

        // Booleans
        highlightPattern(s,
                "\\b(true|false|null)\\b",
                "#569CD6");
    }


}
