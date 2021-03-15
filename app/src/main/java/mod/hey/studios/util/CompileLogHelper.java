package mod.hey.studios.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileLogHelper {

    private static final int ERROR_COL = -65536;
    private static final int WARNING_COL = Color.parseColor("#ffc400");

    public static SpannableString colorErrsAndWarnings(String str) {
        SpannableString spannable = new SpannableString(str);
        Matcher errorMatcher = Pattern.compile("----------\n([0-9]+\\. ERROR)", 8).matcher(str);

        while (errorMatcher.find()) {
            spannable.setSpan(new StyleSpan(1), errorMatcher.start(1), errorMatcher.end(1), 33);
            spannable.setSpan(new ForegroundColorSpan(-65536), errorMatcher.start(1), errorMatcher.end(1), 33);
        }

        Matcher warningMatcher = Pattern.compile("----------\n([0-9]+\\. WARNING)", 8).matcher(str);

        while (warningMatcher.find()) {
            spannable.setSpan(new StyleSpan(1), warningMatcher.start(1), warningMatcher.end(1), 33);
            spannable.setSpan(new ForegroundColorSpan(WARNING_COL), warningMatcher.start(1), warningMatcher.end(1), 33);
        }

        return spannable;
    }
}
