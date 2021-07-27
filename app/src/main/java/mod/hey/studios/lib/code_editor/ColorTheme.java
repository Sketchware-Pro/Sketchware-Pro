package mod.hey.studios.lib.code_editor;

import android.graphics.Color;

public class ColorTheme {

    public static ColorTheme DEFAULT
            = new ColorTheme(
            "light", //theme name
            Color.parseColor("#fafafa"), //bg color
            Color.parseColor("#000000"), //text color
            Color.parseColor("#efefef"), //line highlight color
            Color.parseColor("#888888"), //line numbers color
            Color.parseColor("#0096FF"), //primary color
            Color.parseColor("#0096FF"), //class color
            Color.parseColor("#0096FF"), //symbols color
            Color.parseColor("#e91e63"), //strings&numbers color
            Color.parseColor("#009b00"));//comments color
    public static ColorTheme DARK
            = new ColorTheme(
            "dark", //theme name
            Color.parseColor("#292929"), //bg color
            Color.parseColor("#d8d8d8"), //text color
            Color.parseColor("#393939"), //line highlight color
            Color.parseColor("#646567"), //line numbers color
            Color.parseColor("#D78B40"), //primary color
            Color.parseColor("#F7CF80"), //class color
            Color.parseColor("#aaaab3"), //symbols color
            Color.parseColor("#66a069"), //strings&numbers color
            Color.parseColor("#707070"));//comments color
    //Theme
    final String themeName;
    //Editor
    final int BACKGROUND_COLOR;
    final int TEXT_COLOR;
    final int LINE_HIGHLIGHT_COLOR;
    final int LINE_NUMBERS_COLOR;
    //Syntax
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
