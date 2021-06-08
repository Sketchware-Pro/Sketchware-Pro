package mod.hey.studios.lib.code_editor;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Class to store syntax highlighting patterns and colors.
 *
 * @author Hey! Studios DEV - 28.07.2020
 */

public class ColorScheme {

    public static final int JAVA = 1;
    public static final int XML = 2;

    final Pattern pattern;
    final int color;
    final int textStyle;

    public ColorScheme(Pattern pattern, int color) {
        this(pattern, color, 0);
    }

    public ColorScheme(Pattern pattern, int color, int textStyle) {
        this.pattern = pattern;
        this.color = color;
        this.textStyle = textStyle;
    }

    public static ArrayList<ColorScheme> JAVA() {
        ColorScheme s = new ColorScheme(null, JAVA);
        ArrayList<ColorScheme> a = new ArrayList<>();
        a.add(s);
        return a;
    }

    public static ArrayList<ColorScheme> XML() {
        ColorScheme s = new ColorScheme(null, XML);
        ArrayList<ColorScheme> a = new ArrayList<>();
        a.add(s);
        return a;
    }

    protected static ArrayList<ColorScheme> TYPE_JAVA(ColorTheme theme) {
        ArrayList<ColorScheme> list = new ArrayList<>();

        list.add(new ColorScheme(Pattern.compile("\\b(String|int|abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|true|false|null)\\b"),
                theme.PRIMARY_COLOR));

        list.add(new ColorScheme(Pattern.compile("\\b([A-Z]\\w+)\\b"),
                theme.CLASS_COLOR));

        list.add(new ColorScheme(Pattern.compile("[(),;<>{}*]*"),
                theme.SYMBOLS_COLOR));

        list.add(new ColorScheme(Pattern.compile("\"(.*?)\"|'(.*?)'|\\b([0-9]+)\\b"),
                theme.STRINGS_NUMBERS_COLOR));

        list.add(new ColorScheme(Pattern.compile("/\\*(?:.|[\\n\\r])*?\\*/|//.*"),
                theme.COMMENTS_COLOR));

        return list;
    }

    protected static ArrayList<ColorScheme> TYPE_XML(ColorTheme theme) {
        ArrayList<ColorScheme> list = new ArrayList<>();

        list.add(new ColorScheme(Pattern.compile("<(/)?[A-Za-z_.]+(/)?(>)?"),
                theme.CLASS_COLOR));

        list.add(new ColorScheme(Pattern.compile("[(),;<>{}*]*"),
                theme.SYMBOLS_COLOR));

        list.add(new ColorScheme(Pattern.compile("\"(.*?)\"|'(.*?)'"),
                theme.STRINGS_NUMBERS_COLOR));

        list.add(new ColorScheme(Pattern.compile("<!--(?:.|[\\n\\r])*?-->"),
                theme.COMMENTS_COLOR));

        return list;
    }
}
