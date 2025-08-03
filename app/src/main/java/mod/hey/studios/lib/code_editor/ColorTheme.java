package mod.hey.studios.lib.code_editor;

import android.graphics.Color;

public record ColorTheme(
        int backgroundColor, int textColor,
        int lineHighlightColor, int lineNumbersColor, int primaryColor,
        int classColor, int symbolsColor, int stringsNumbersColor,
        int commentsColor
) {

    public static final ColorTheme DEFAULT = new ColorTheme(
            Color.parseColor("#fafafa"),
            Color.parseColor("#000000"),
            Color.parseColor("#efefef"),
            Color.parseColor("#888888"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#e91e63"),
            Color.parseColor("#009b00")
    );

    public static final ColorTheme DARK = new ColorTheme(
            Color.parseColor("#292929"),
            Color.parseColor("#d8d8d8"),
            Color.parseColor("#393939"),
            Color.parseColor("#646567"),
            Color.parseColor("#D78B40"),
            Color.parseColor("#F7CF80"),
            Color.parseColor("#aaaab3"),
            Color.parseColor("#66a069"),
            Color.parseColor("#707070")
    );

    public static ColorTheme getTheme(boolean isDarkTheme) {
        return isDarkTheme ? DARK : DEFAULT;
    }
}
