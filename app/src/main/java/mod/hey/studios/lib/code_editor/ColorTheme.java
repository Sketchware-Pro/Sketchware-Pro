package mod.hey.studios.lib.code_editor;

import android.graphics.Color;

public class ColorTheme {

    public static ColorTheme DEFAULT
            = new ColorTheme(
            "light",
            Color.parseColor("#fafafa"),
            Color.parseColor("#000000"),
            Color.parseColor("#efefef"),
            Color.parseColor("#888888"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#0096FF"),
            Color.parseColor("#e91e63"),
            Color.parseColor("#009b00"));
    public static ColorTheme DARK
            = new ColorTheme(
            "dark",
            Color.parseColor("#292929"),
            Color.parseColor("#d8d8d8"),
            Color.parseColor("#393939"),
            Color.parseColor("#646567"),
            Color.parseColor("#D78B40"),
            Color.parseColor("#F7CF80"),
            Color.parseColor("#aaaab3"),
            Color.parseColor("#66a069"),
            Color.parseColor("#707070"));
    final String themeName;
    final int BACKGROUND_COLOR;
    final int TEXT_COLOR;
    final int LINE_HIGHLIGHT_COLOR;
    final int LINE_NUMBERS_COLOR;
    final int PRIMARY_COLOR;
    final int CLASS_COLOR;
    final int SYMBOLS_COLOR;
    final int STRINGS_NUMBERS_COLOR;
    final int COMMENTS_COLOR;

    public ColorTheme(String themeName, int bACKGROUND_COLOR, int tEXT_COLOR, int lINE_HIGHLIGHT_COLOR, int lINE_NUMBERS_COLOR, int pRIMARY_COLOR, int cLASS_COLOR, int sYMBOLS_COLOR, int sTRINGS_NUMBERS_COLOR, int cOMMENTS_COLOR) {
        this.themeName = themeName;
        BACKGROUND_COLOR = bACKGROUND_COLOR;
        TEXT_COLOR = tEXT_COLOR;
        LINE_HIGHLIGHT_COLOR = lINE_HIGHLIGHT_COLOR;
        LINE_NUMBERS_COLOR = lINE_NUMBERS_COLOR;
        PRIMARY_COLOR = pRIMARY_COLOR;
        CLASS_COLOR = cLASS_COLOR;
        SYMBOLS_COLOR = sYMBOLS_COLOR;
        STRINGS_NUMBERS_COLOR = sTRINGS_NUMBERS_COLOR;
        COMMENTS_COLOR = cOMMENTS_COLOR;
    }

    public static ColorTheme getTheme(boolean b) {
        return b ? DARK : DEFAULT;
    }
}
