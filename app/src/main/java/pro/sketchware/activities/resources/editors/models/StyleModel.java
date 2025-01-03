package pro.sketchware.activities.resources.editors.models;

import java.util.HashMap;
import java.util.Map;

public class StyleModel {

    private String styleName;
    private String parent;
    private Map<String, String> attributes;

    public StyleModel(String styleName, String parent, Map<String, String> attributes) {
        this.styleName = styleName;
        this.parent = parent;
        this.attributes = attributes;
    }

    public StyleModel(String styleName, String parent) {
        this.styleName = styleName;
        this.parent = parent;
        this.attributes = new HashMap<>();
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getParent() {
        return parent == null ? "" : parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String attrName, String attrValue) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(attrName, attrValue);
    }

    public String getAttribute(String attrName) {
        if (attributes != null) {
            return attributes.get(attrName);
        }
        return null;
    }
}
