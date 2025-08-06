package pro.sketchware.utility;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Pair;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PropertiesUtil {

    public static final Pattern COLOR_PATTERN = Pattern.compile("(#)([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})");
    public static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    public static final Pattern UNIT_PATTERN = Pattern.compile("(-?\\d+(?:\\.\\d+)?)(dp|sp|px|mm|pt|in)$");
    public static final Pattern PREFIX_PATTERN = Pattern.compile("([@?][^/]+/)(.*)");

    public static boolean isHexColor(String color) {
        if (TextUtils.isEmpty(color)) {
            return false;
        }
        Matcher matcher = HEX_COLOR_PATTERN.matcher(color);
        return matcher.matches();
    }

    public static int parseColor(String color) {
        if (color == null) {
            color = "#FFFFFFFF";
        }
        String hexColor = color.replaceFirst("#", "");
        String formattedColor = String.format("#%8s", hexColor).replaceAll(" ", "F");
        return Color.parseColor(formattedColor);
    }

    public static int resolveSize(String value, int defaultValue) {
        try {
            var reference = getUnitOrPrefix(value);
            if (TextUtils.isEmpty(value) || reference == null) {
                return defaultValue;
            }
            return Integer.parseInt(reference.second);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Pair<String, String> getUnitOrPrefix(String value) {
        Matcher prefixMatcher = PREFIX_PATTERN.matcher(value);
        if (prefixMatcher.find()) {
            return Pair.create(prefixMatcher.group(1), prefixMatcher.group(2));
        }

        Matcher unitMatcher = UNIT_PATTERN.matcher(value);
        if (unitMatcher.find()) {
            return Pair.create(unitMatcher.group(2), unitMatcher.group(1));
        }

        Matcher hexColorMatcher = COLOR_PATTERN.matcher(value);
        if (hexColorMatcher.find()) {
            return Pair.create(hexColorMatcher.group(1), hexColorMatcher.group(2));
        }

        return null;
    }

    public static List<String> generateItems(String prefix, int itemCount) {
        return IntStream.rangeClosed(1, itemCount)
                .mapToObj(i -> prefix + " " + i)
                .collect(Collectors.toList());
    }

    public static String parseReferName(String reference, String sep) {
        if (reference == null) {
            return null;
        }
        int index = reference.indexOf(sep);
        if (index >= 0 && index < reference.length() - 1) {
            return reference.substring(index + 1);
        } else {
            return reference;
        }
    }
}
