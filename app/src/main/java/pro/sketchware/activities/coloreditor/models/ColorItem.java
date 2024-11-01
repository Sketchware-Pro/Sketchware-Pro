package pro.sketchware.activities.coloreditor.models;

public class ColorItem {
    private String colorName;
    private String colorValue;

    public ColorItem(String colorName, String colorValue) {
        this.colorName = colorName;
        this.colorValue = colorValue;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorValue() {
        return colorValue;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }
}
