package mod.hey.studios.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.google.android.material.color.MaterialColors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class CompileLogHelper {
    private static final String TAG = "CompileLogHelper";
    private static final Pattern ERROR_PATTERN = Pattern.compile("----------\n([0-9]+\\. ERROR)", Pattern.MULTILINE);
    private static final Pattern WARNING_PATTERN = Pattern.compile("----------\n([0-9]+\\. WARNING)", Pattern.MULTILINE);
    private static final Pattern XML_PATTERN = Pattern.compile("error:", Pattern.MULTILINE);

    public static SpannableString getColoredLogs(Context context, String logs) {
        int errorColor = MaterialColors.getColor(context, R.attr.colorError, TAG);
        int warningColor = MaterialColors.getColor(context, R.attr.colorAmber, TAG);

        SpannableString spannable = new SpannableString(logs);

        Matcher errorMatcher = ERROR_PATTERN.matcher(logs);
        applyStyle(spannable, errorMatcher, errorColor);

        Matcher warningMatcher = WARNING_PATTERN.matcher(logs);
        applyStyle(spannable, warningMatcher, warningColor);

        Matcher xmlMatcher = XML_PATTERN.matcher(logs);
        applyStyleForXml(spannable, xmlMatcher, errorColor);

        return spannable;
    }

    private static void applyStyle(SpannableString spannable, Matcher matcher, int color) {
        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(color), matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private static void applyStyleForXml(SpannableString spannable, Matcher matcher, int color) {
        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
