package mod.elfilibustero.sketch.lib.utils;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesUtil {

    private static final String HEX_COLOR_PATTERN =
            "^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    public static boolean isHexColor(String color) {
        if (TextUtils.isEmpty(color)) {
            return false;
        }

        Pattern pattern = Pattern.compile(HEX_COLOR_PATTERN);
        Matcher matcher = pattern.matcher(color);

        return matcher.matches();
    }

    public static int parseColor(String color) {
        String hexColor = color.replaceFirst("#", "");
        String formattedColor = String.format("#%8s", hexColor).replaceAll(" ", "F");
        return Color.parseColor(formattedColor);
    }

    public static int resolveSize(String value, int defaultValue) {
        var reference = getUnitOrPrefix(value);
        if (TextUtils.isEmpty(value) || reference == null) {
            return defaultValue;
        }
        return Integer.parseInt(reference.second);
    }

    public static Pair<String, String> getUnitOrPrefix(String value) {
        Pattern prefixPattern = Pattern.compile("([@?][^/]+/)(.*)");

        Pattern unitPattern = Pattern.compile("(-?\\d+)(dp|sp|px|mm|pt|in)$");

        Pattern hexColorPattern =
                Pattern.compile("(#)([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})");

        Matcher prefixMatcher = prefixPattern.matcher(value);
        if (prefixMatcher.find()) {
            return Pair.create(prefixMatcher.group(1), prefixMatcher.group(2));
        }

        Matcher unitMatcher = unitPattern.matcher(value);
        if (unitMatcher.find()) {
            return Pair.create(unitMatcher.group(2), unitMatcher.group(1));
        }

        Matcher hexColorMatcher = hexColorPattern.matcher(value);
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
}
