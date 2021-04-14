package mod.agus.jcoderz.lib;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ColorScheme {
    final int color;
    final Pattern pattern;

    public ColorScheme(Pattern pattern2, int i) {
        this.pattern = pattern2;
        this.color = i;
    }

    public static ArrayList<ColorScheme> JAVA() {
        ArrayList<ColorScheme> arrayList = new ArrayList<>();
        arrayList.add(new ColorScheme(Pattern.compile("\\b(public|private|protected|void|switch|case|class|import|package|extends|Activity|TextView|EditText|LinearLayout|CharSequence|String|int|onCreate|ArrayList|float|if|else|static|Intent|Button|SharedPreferences|abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while|true|false|null)\\b"), Color.parseColor("#42a5f5")));
        arrayList.add(new ColorScheme(Pattern.compile("\\b(out|print|println|valueOf|toString|concat|equals|for|while|switch|getText|println|printf|print|out|parseInt|round|sqrt|charAt|compareTo|compareToIgnoreCase|concat|contains|contentEquals|equals|length|toLowerCase|trim|toUpperCase|toString|valueOf|substring|startsWith|split|replace|replaceAll|lastIndexOf|size)\\b"), Color.parseColor("#5c6bc0")));
        arrayList.add(new ColorScheme(Pattern.compile("\"(.*?)\"|'(.*?)'"), Color.parseColor("#ff1744")));
        arrayList.add(new ColorScheme(Pattern.compile("\\b([0-9]+)\\b"), Color.parseColor("#26a69a")));
        arrayList.add(new ColorScheme(Pattern.compile("/\\*(?:.|[\\n\\r])*?\\*/|//.*"), Color.parseColor("#9e9e9e")));
        arrayList.add(new ColorScheme(Pattern.compile("\\b([A-Z]\\w+)\\b"), Color.parseColor("#42a5f5")));
        arrayList.add(new ColorScheme(Pattern.compile("\\@\\s*(\\w+)"), Color.parseColor("#26a69a")));
        return arrayList;
    }

    public static ArrayList<ColorScheme> XML() {
        ArrayList<ColorScheme> arrayList = new ArrayList<>();
        arrayList.add(new ColorScheme(Pattern.compile("</?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[\\^'\">\\s]+))?)+\\s*|\\s*)/?>"), Color.parseColor("#42a5f5")));
        arrayList.add(new ColorScheme(Pattern.compile("<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>|</([A-Za-z][A-Za-z0-9]*)\\b[^>]*>|(.+?):(.+?);"), Color.parseColor("#5c6bc0")));
        arrayList.add(new ColorScheme(Pattern.compile("(\\S+)=[\"']?((?:.(?![\"']?\\s+(?:\\S+)=|[>\"']))+.)[\"']?|:[ \\t](.+?);"), Color.parseColor("#26a69a")));
        arrayList.add(new ColorScheme(Pattern.compile("<!--(?:.|[\\n\\r])*?-->|//\\*(?:.|[\\n\\r])*?\\*//|//.*"), Color.parseColor("#9e9e9e")));
        arrayList.add(new ColorScheme(Pattern.compile("\"(.*?)\"|'(.*?)'"), Color.parseColor("#ff1744")));
        return arrayList;
    }
}
