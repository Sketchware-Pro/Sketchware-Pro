package a.a.a;

import com.sketchware.remod.xml.XmlBuilder;

import java.util.HashMap;

public class Mx {

    public XmlBuilder a = new XmlBuilder("resources");
    public HashMap<String, XmlBuilder> b = new HashMap<>();

    public String toCode() {
        return a.toCode();
    }

    public void a(String name, int value) {
        XmlBuilder integerBuilder = new XmlBuilder("integer", true);
        integerBuilder.addAttribute("", "name", name);
        integerBuilder.a(String.valueOf(value));
        a.a(integerBuilder);
    }

    public void a(String name, String value) {
        XmlBuilder colorBuilder = new XmlBuilder("color", true);
        colorBuilder.addAttribute("", "name", name);
        colorBuilder.a(value);
        a.a(colorBuilder);
    }

    public void a(String styleName, String name, String value) {
        XmlBuilder styleBuilder = b.get(styleName);
        if (styleBuilder != null) {
            XmlBuilder styleItemBuilder = new XmlBuilder("item", true);
            styleItemBuilder.addAttribute("", "name", name);
            styleItemBuilder.a(value);
            styleBuilder.a(styleItemBuilder);
        }
    }

    public void a(String name, String value, boolean translatable) {
        XmlBuilder stringBuilder = new XmlBuilder("string", true);
        stringBuilder.addAttribute("", "name", name);
        if (!translatable) {
            stringBuilder.addAttribute("", "translatable", "false");
        }

        String trimmedValue = value.trim();
        value = trimmedValue;
        if (trimmedValue.length() > 0) {
            name = trimmedValue;
            if (trimmedValue.charAt(0) == '"') {
                name = trimmedValue.substring(1);
            }

            value = name;
            if (name.charAt(name.length() - 1) == '"') {
                value = name.substring(0, name.length() - 1);
            }
        }

        stringBuilder.a(value);
        a.a(stringBuilder);
    }

    public void b(String name, String value) {
        a(name, value, false);
    }

    public void c(String name, String parent) {
        XmlBuilder styleBuilder = new XmlBuilder("style", true);
        styleBuilder.addAttribute("", "name", name);
        if (parent.length() > 0) {
            styleBuilder.addAttribute("", "parent", parent);
        }

        a.a(styleBuilder);
        b.put(name, styleBuilder);
    }
}
