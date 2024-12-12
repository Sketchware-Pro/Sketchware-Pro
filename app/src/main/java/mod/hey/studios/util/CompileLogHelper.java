package mod.hey.studios.util;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.ThemeUtils;

public class CompileLogHelper {
    private static final String TAG = "CompileLogHelper";
    private static final Pattern ERROR_PATTERN = Pattern.compile("----------\n([0-9]+\\. ERROR)", Pattern.MULTILINE);
    private static final Pattern WARNING_PATTERN = Pattern.compile("----------\n([0-9]+\\. WARNING)", Pattern.MULTILINE);
    private static final Pattern XML_PATTERN = Pattern.compile("error:", Pattern.MULTILINE);
    private static final Pattern linePattern = Pattern.compile("\\(at line (\\d+)\\)");

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

    public static SpannableString getColoredLogsNew(Context context, String logs, boolean hidePath) {
        try {
            int errorColor = MaterialColors.getColor(context, R.attr.colorError, TAG);
            int fileColor = MaterialColors.getColor(context, R.attr.colorOnSurfaceVariant, TAG);
            int warningColor = MaterialColors.getColor(context, R.attr.colorAmber, TAG);
            Map<String, String> pathMapping = new HashMap<>();
            String processedLogs = logs;
            if (hidePath) {
                Pattern filePattern = Pattern.compile("/storage/[^\\s\n]+");
                Matcher fileMatcher = filePattern.matcher(logs);
                StringBuffer sb = new StringBuffer();

                while (fileMatcher.find()) {
                    String fullPath = fileMatcher.group();
                    String fileName = new File(fullPath).getName();
                    pathMapping.put(fileName, fullPath);
                    fileMatcher.appendReplacement(sb, fileName);
                }
                fileMatcher.appendTail(sb);
                processedLogs = sb.toString();
            }

            SpannableString spannable = new SpannableString(processedLogs);

            Matcher errorMatcher = ERROR_PATTERN.matcher(processedLogs);
            applyStyle(spannable, errorMatcher, errorColor);

            Matcher warningMatcher = WARNING_PATTERN.matcher(processedLogs);
            applyStyle(spannable, warningMatcher, warningColor);

            Pattern filePattern = Pattern.compile(hidePath ? "[\\w.-]+\\.(java|xml)" : "/storage/[^\\s\n]+");
            Matcher fileMatcher = filePattern.matcher(processedLogs);
            Pattern linePattern = Pattern.compile("\\(at line (\\d+)\\)");

            while (fileMatcher.find()) {
                final String displayText = fileMatcher.group();
                final String fullPath = hidePath ? pathMapping.get(displayText) : displayText;

                String surroundingText = logs.substring(Math.max(0, logs.indexOf(fullPath) - 50),
                        Math.min(logs.length(), logs.indexOf(fullPath) + 150));
                Matcher lineMatcher = linePattern.matcher(surroundingText);
                final String lineNumber = lineMatcher.find() ? lineMatcher.group(1) : "";

                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showCode(context, fullPath, displayText, Double.parseDouble(lineNumber));
                    }
                };

                spannable.setSpan(clickableSpan, fileMatcher.start(), fileMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), fileMatcher.start(), fileMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(fileColor), fileMatcher.start(), fileMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return spannable;
        } catch (Exception e) {
             return getColoredLogs(context, logs);
        }
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

    private static void showCode(Context context, String filePath, String fileName, double line) {
        String source = FileUtil.readFile(filePath);
        CodeEditor editor = new CodeEditor(context);
        editor.setTypefaceText(Typeface.MONOSPACE);
        editor.setEditable(true);
        editor.setTextSize(14);
        editor.setText(source);
        editor.post(() -> {
            editor.setHighlightCurrentLine(true);
            editor.jumpToLine((int) line);
        });
        editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

        if (fileName.endsWith(".xml")) {
            editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
            if (ThemeUtils.isDarkThemeEnabled(context)) {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
            } else {
                editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
            }
        } else {
            editor.setEditorLanguage(new JavaLanguage());
            if (ThemeUtils.isDarkThemeEnabled(context)) {
                editor.setColorScheme(new SchemeDarcula());
            } else {
                editor.setColorScheme(new EditorColorScheme());
            }
        }
        var dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(fileName)
                .setCancelable(false)
                .setPositiveButton("Dismiss", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.setView(editor, (int) getDip(24), (int) getDip(20), (int) getDip(24), (int) getDip(0));
        dialog.show();
    }
}