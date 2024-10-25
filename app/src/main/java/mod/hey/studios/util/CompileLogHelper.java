package mod.hey.studios.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileLogHelper {

    private static final int ERROR_COL = Color.parseColor("#fe0000");
    private static final int WARNING_COL = Color.parseColor("#ffc400");

    public static SpannableString colorErrsAndWarnings(String str) {
        SpannableString spannable = new SpannableString(str);
        Matcher errorMatcher = Pattern.compile("----------\n([0-9]+\\. ERROR)", Pattern.MULTILINE).matcher(str);

        while (errorMatcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), errorMatcher.start(1), errorMatcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ERROR_COL), errorMatcher.start(1), errorMatcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        Matcher warningMatcher = Pattern.compile("----------\n([0-9]+\\. WARNING)", Pattern.MULTILINE).matcher(str);

        while (warningMatcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), warningMatcher.start(1), warningMatcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(WARNING_COL), warningMatcher.start(1), warningMatcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        Matcher errorMatcherXml = Pattern.compile("error:", Pattern.MULTILINE).matcher(str);

        while (errorMatcherXml.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), errorMatcherXml.start(), errorMatcherXml.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ERROR_COL), errorMatcherXml.start(), errorMatcherXml.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }
}
