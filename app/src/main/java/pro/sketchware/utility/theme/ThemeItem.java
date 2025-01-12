package pro.sketchware.utility.theme;

public class ThemeItem {
    private final String name;
    private final int themeId;
    private final int styleId;

    public ThemeItem(String name, int styleId, int themeId) {
        this.name = name;
        this.themeId = themeId;
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public int getThemeId() {
        return themeId;
    }

    public int getStyleId() {
        return styleId;
    }
}