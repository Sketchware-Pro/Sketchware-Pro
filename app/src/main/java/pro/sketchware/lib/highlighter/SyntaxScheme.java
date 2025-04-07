package pro.sketchware.lib.highlighter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.R;
import pro.sketchware.SketchApplication;
import pro.sketchware.utility.ThemeUtils;

public class SyntaxScheme {

    public static final String COMMENTS_COLOR_LIGHT = "#880000";
    public static final String NOT_WORD_COLOR_LIGHT = "#656600";
    public static final String NUMBERS_COLOR_LIGHT = "#006766";
    public static final String PRIMARY_COLOR_LIGHT = "#000000";
    public static final String QUOTES_COLOR_LIGHT = "#008800";
    public static final String SECONDARY_COLOR_LIGHT = "#010088";
    public static final String VARIABLE_COLOR_LIGHT = "#660066";

    public static final String COMMENTS_COLOR_DARK = "#808080";
    public static final String NOT_WORD_COLOR_DARK = "#cc7832";
    public static final String NUMBERS_COLOR_DARK = "#6897bb";
    public static final String PRIMARY_COLOR_DARK = "#ffffff";
    public static final String QUOTES_COLOR_DARK = "#98C379";
    public static final String SECONDARY_COLOR_DARK = "#cc7832";
    public static final String VARIABLE_COLOR_DARK = "#9876aa";

    private static final String[] mJavaPattern = new String[12];
    private static final String[] mXmlPattern = new String[4];

    public int color;
    public Pattern pattern;

    public SyntaxScheme(Pattern pattern, int color) {
        this.pattern = pattern;
        this.color = color;
        initializeJavaPattern();
        initializeXmlPattern();
    }

    public static ArrayList<SyntaxScheme> JAVA() {
        ArrayList<SyntaxScheme> arrayList = new ArrayList<>();
        boolean isDarkMode = ThemeUtils.isDarkThemeEnabled(SketchApplication.getContext());

        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[0] + mJavaPattern[1]), getColor(PRIMARY_COLOR_LIGHT, PRIMARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[2] + mJavaPattern[3] + mJavaPattern[4]), getColor(SECONDARY_COLOR_LIGHT, SECONDARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[5]), getColor(NUMBERS_COLOR_LIGHT, NUMBERS_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[11]), getColor(NOT_WORD_COLOR_LIGHT, NOT_WORD_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[6]), getColor(PRIMARY_COLOR_LIGHT, PRIMARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[10]), getColor(VARIABLE_COLOR_LIGHT, VARIABLE_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[7]), getColor("#9e880d", "#bbb529", isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[8]), getColor(QUOTES_COLOR_LIGHT, QUOTES_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[9]), getColor(COMMENTS_COLOR_LIGHT, COMMENTS_COLOR_DARK, isDarkMode)));

        return arrayList;
    }

    public static ArrayList<SyntaxScheme> XML(Context context) {
        ArrayList<SyntaxScheme> arrayList = new ArrayList<>();
        boolean isDarkMode = ThemeUtils.isDarkThemeEnabled(context);

        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[0] + mJavaPattern[1]), getColor(PRIMARY_COLOR_LIGHT, PRIMARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mXmlPattern[2]), getColor(SECONDARY_COLOR_LIGHT, SECONDARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mXmlPattern[0]), getColor(VARIABLE_COLOR_LIGHT, VARIABLE_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[11]), getColor(NOT_WORD_COLOR_LIGHT, NOT_WORD_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mXmlPattern[3]), getColor(SECONDARY_COLOR_LIGHT, SECONDARY_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mXmlPattern[1]), getColor(COMMENTS_COLOR_LIGHT, COMMENTS_COLOR_DARK, isDarkMode)));
        arrayList.add(new SyntaxScheme(Pattern.compile(mJavaPattern[8]), getColor(QUOTES_COLOR_LIGHT, QUOTES_COLOR_DARK, isDarkMode)));

        return arrayList;
    }

    private static int getColor(String lightColor, String darkColor, boolean isDarkMode) {
        return Color.parseColor(isDarkMode ? darkColor : lightColor);
    }

    public static void setXMLHighlighter(EditText editText) {

        int violet = ThemeUtils.getColor(editText, R.attr.colorViolet);
        int onSurface = ThemeUtils.getColor(editText, R.attr.colorOnSurface);
        int green = ThemeUtils.getColor(editText, R.attr.colorGreen);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ForegroundColorSpan[] spans = s.getSpans(0, s.length(), ForegroundColorSpan.class);
                for (ForegroundColorSpan span : spans) {
                    s.removeSpan(span);
                }

                s.setSpan(new ForegroundColorSpan(onSurface), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String text = s.toString();
                Pattern pattern = Pattern.compile("(\\b\\w+\\b)(\\s*=\\s*)(\"[^\"]*\")?");
                Matcher matcher = pattern.matcher(text);

                while (matcher.find()) {
                    s.setSpan(new ForegroundColorSpan(violet), matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    s.setSpan(new ForegroundColorSpan(onSurface), matcher.start(2), matcher.end(2), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (matcher.group(3) != null) {
                        s.setSpan(new ForegroundColorSpan(green), matcher.start(3), matcher.end(3), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        });
    }

    private void initializeJavaPattern() {
        mJavaPattern[0] = "\\b(out|print|println|valueOf|toString|concat|equals|for|while|switch|getText\\b";
        mJavaPattern[1] = "|println|printf|print|out|parseInt|round|sqrt|charAt|compareTo|compareToIgnoreCase|concat|contains|contentEquals|equals|length|toLowerCase|trim|toUpperCase|toString|valueOf|substring|startsWith|split|replace|replaceAll|lastIndexOf|size)\\b";
        mJavaPattern[2] = "\\b(public|private|protected|void|switch|case|class|import|package|extends|Activity|TextView|EditText|LinearLayout|CharSequence|String|int|onCreate|ArrayList|float|if|else|for|static|Intent|Button|SharedPreferences\\b";
        mJavaPattern[3] = "|abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|interface|long|native|new|package|private|protected|";
        mJavaPattern[4] = "public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|true|false|null)\\b";
        mJavaPattern[5] = "\\b0x[0-9a-f]{6,8}|\\b([0-9]+)\\b";
        mJavaPattern[6] = "(\\w+)(\\()+";
        mJavaPattern[7] = "(?:@)\\w+\\b";
        mJavaPattern[8] = "\"(.*)\"|'(.*)'";
        mJavaPattern[9] = "/\\*(?:.|[\\n\\r])*?\\*/|//.*";
        mJavaPattern[10] = "\\b(?:[A-Z])[a-zA-Z0-9]+\\b";
        mJavaPattern[11] = "(?!\\s)\\W";
    }

    private void initializeXmlPattern() {
        mXmlPattern[0] = "\\w+:\\w+";
        mXmlPattern[1] = "<!--(?:.|[\\n\\r])*?-->|//\\*(?:.|[\\n\\r])*?\\*//|//.*";
        mXmlPattern[2] = "<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>|</([A-Za-z][A-Za-z0-9]*)\\b[^>]*>|(.+?):(.+?);";
        mXmlPattern[3] = "[<>/]";
    }

    public SyntaxScheme getPrimarySyntax() {
        if (ThemeUtils.isDarkThemeEnabled(SketchApplication.getContext())) {
            return new SyntaxScheme(Pattern.compile(mJavaPattern[6]), Color.parseColor(PRIMARY_COLOR_DARK));
        } else {
            return new SyntaxScheme(Pattern.compile(mJavaPattern[6]), Color.parseColor(PRIMARY_COLOR_LIGHT));
        }
    }

}
