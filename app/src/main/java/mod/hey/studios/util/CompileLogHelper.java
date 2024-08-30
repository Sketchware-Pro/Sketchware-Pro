package mod.hey.studios.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.google.android.material.color.MaterialColors;
import com.sketchware.remod.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileLogHelper {
    private static final String TAG = "CompileLogHelper";
    private static final Pattern ERROR_PATTERN = Pattern.compile("----------\n([0-9]+\\. ERROR)", Pattern.MULTILINE);
    private static final Pattern WARNING_PATTERN = Pattern.compile("----------\n([0-9]+\\. WARNING)", Pattern.MULTILINE);
    private static final Pattern XML_PATTERN = Pattern.compile("error:", Pattern.MULTILINE);

    public static SpannableStringBuilder getColoredLogs(Context context, String logs) {
        int errorColor = MaterialColors.getColor(context, R.attr.colorError, TAG);
        int warningColor = MaterialColors.getColor(context, R.attr.colorAmber, TAG);

        SpannableStringBuilder spannable = new SpannableStringBuilder(logs);

        Matcher errorMatcher = ERROR_PATTERN.matcher(logs);
        applyStyle(spannable, errorMatcher, ERROR_COL);

        Matcher warningMatcher = WARNING_PATTERN.matcher(logs);
        applyStyle(spannable, warningMatcher, WARNING_COL);

        Matcher xmlMatcher = XML_PATTERN.matcher(logs);
        applyStyle(spannable, xmlMatcher, ERROR_COL);

        return spannable;
    }

    private static void applyStyle(SpannableStringBuilder spannable, Matcher matcher, int color) {
        while (matcher.find()) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(color), matcher.start(1), matcher.end(1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
