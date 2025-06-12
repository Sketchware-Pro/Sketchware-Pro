package pro.sketchware.xml;

import java.util.HashMap;

public class XmlBuilderHelper {

    public XmlBuilder rootBuilder = new XmlBuilder("resources");
    public HashMap<String, XmlBuilder> styleBuilders = new HashMap<>();

    public String toCode() {
        return rootBuilder.toCode();
    }

    public void addInteger(String name, int value) {
        XmlBuilder integerBuilder = new XmlBuilder("integer", true);
        integerBuilder.addAttribute("", "name", name);
        integerBuilder.setNodeValue(String.valueOf(value));
        rootBuilder.addChildNode(integerBuilder);
    }

    public void addColor(String name, String value) {
        XmlBuilder colorBuilder = new XmlBuilder("color", true);
        colorBuilder.addAttribute("", "name", name);
        colorBuilder.setNodeValue(value);
        rootBuilder.addChildNode(colorBuilder);
    }

    public void addItemToStyle(String styleName, String itemName, String itemValue) {
        XmlBuilder styleBuilder = styleBuilders.get(styleName);
        if (styleBuilder != null) {
            XmlBuilder itemBuilder = new XmlBuilder("item", true);
            itemBuilder.addAttribute("", "name", itemName);
            itemBuilder.setNodeValue(itemValue);
            styleBuilder.addChildNode(itemBuilder);
        }
    }

    public void addString(String name, String value, boolean isTranslatable) {
        XmlBuilder stringBuilder = new XmlBuilder("string", true);
        stringBuilder.addAttribute("", "name", name);
        if (!isTranslatable) {
            stringBuilder.addAttribute("", "translatable", "false");
        }

        String trimmedValue = value.trim();
        value = trimmedValue;
        if (!trimmedValue.isEmpty()) {
            name = trimmedValue;
            if (trimmedValue.charAt(0) == '"') {
                name = trimmedValue.substring(1);
            }

            value = name;
            if (name.charAt(name.length() - 1) == '"') {
                value = name.substring(0, name.length() - 1);
            }
        }

        stringBuilder.setNodeValue(value);
        rootBuilder.addChildNode(stringBuilder);
    }

    public void addNonTranslatableString(String name, String value) {
        addString(name, value, false);
    }

    public void addStyle(String styleName, String parentStyleName) {
        XmlBuilder styleBuilder = new XmlBuilder("style", true);
        styleBuilder.addAttribute("", "name", styleName);
        if (parentStyleName != null && !parentStyleName.isEmpty()) {
            styleBuilder.addAttribute("", "parent", parentStyleName);
        }

        rootBuilder.addChildNode(styleBuilder);
        styleBuilders.put(styleName, styleBuilder);
    }
}
