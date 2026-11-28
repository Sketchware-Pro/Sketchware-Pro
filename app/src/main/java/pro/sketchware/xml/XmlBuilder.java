package pro.sketchware.xml;

import java.util.ArrayList;

import a.a.a.Jx;

public class XmlBuilder {

    private final ArrayList<XmlBuilder> childNodes;
    private final boolean d;
    private final String rootElementName;
    private final ArrayList<AttributeBuilder> attributes;
    private String g;
    private int indentationLevel;
    private String nodeValue;

    public XmlBuilder(String rootElementName) {
        this(rootElementName, false);
    }

    public XmlBuilder(String rootElementName, boolean z) {
        d = z;
        this.rootElementName = rootElementName;
        indentationLevel = 0;
        attributes = new ArrayList<>();
        childNodes = new ArrayList<>();
    }

    private String addZeroIndent() {
        return addIndent(0);
    }

    private String addIndent(int indentSize) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < indentationLevel + indentSize; i++) {
            str.append("\t");
        }
        return str.toString();
    }

    public void addNamespaceDeclaration(int position, String namespace, String attr, String value) {
        attributes.add(position, new AttributeBuilder(namespace, attr, value));
    }

    public void addChildNode(XmlBuilder xmlBuilder) {
        xmlBuilder.b(indentationLevel + 1);
        childNodes.add(xmlBuilder);
    }

    public void setNodeValue(String value) {
        nodeValue = value;
    }

    public boolean hasAttribute(String attrName) {
        if (attrName == null) return false;
        String prefix = "";
        String name = attrName;
        if (attrName.contains(":")) {
            String[] parts = attrName.split(":", 2);
            prefix = parts[0];
            name = parts[1];
        }
        for (AttributeBuilder ab : attributes) {
            if (ab.attr != null) {
                if (ab.attr.equalsIgnoreCase(name)) {
                    if (prefix.isEmpty() || ab.namespace == null || ab.namespace.equalsIgnoreCase(prefix)) {
                        return true;
                    }
                }
            }
            if (ab.value != null) {
                if (ab.value.contains(attrName + "=") || ab.value.contains(name + "=")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeAttribute(String attrName) {
        if (attrName == null) return;
        String prefix = "";
        String name = attrName;
        if (attrName.contains(":")) {
            String[] parts = attrName.split(":", 2);
            prefix = parts[0];
            name = parts[1];
        }
        for (int i = attributes.size() - 1; i >= 0; i--) {
            AttributeBuilder ab = attributes.get(i);
            if (ab.attr != null && ab.attr.equalsIgnoreCase(name)) {
                if (prefix.isEmpty() || ab.namespace == null || ab.namespace.equalsIgnoreCase(prefix)) {
                    attributes.remove(i);
                    continue;
                }
            }
            if (ab.value != null && (ab.value.contains(attrName + "=") || ab.value.contains(name + "="))) {
                attributes.remove(i);
            }
        }
    }

    public void addAttribute(String namespace, String attr, String value) {
        if (attr != null) {
            String fullKey = (namespace != null && !namespace.isEmpty()) ? (namespace + ":" + attr) : attr;
            removeAttribute(fullKey);
        }
        attributes.add(new AttributeBuilder(namespace, attr, value));
    }

    public void addAttributeValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("([a-zA-Z0-9_]+:[a-zA-Z0-9_]+|[a-zA-Z0-9_]+)\\s*=").matcher(value);
        if (matcher.find()) {
            String attrKey = matcher.group(1);
            removeAttribute(attrKey);
        }
        attributes.add(new AttributeBuilder(value));
    }

    public String toCode() {
        StringBuilder resultCode = new StringBuilder();
        resultCode.append(addZeroIndent());
        resultCode.append("<");
        resultCode.append(rootElementName);
        for (AttributeBuilder attr : attributes) {
            if (attributes.size() <= 1 || d) {
                resultCode.append(" ");
            } else {
                resultCode.append("\r\n");
                resultCode.append(addIndent(1));
                g = "\r\n" + addIndent(1);
            }
            resultCode.append(attr.toCode());
        }
        if (childNodes.size() <= 0) {
            if (nodeValue == null || nodeValue.length() <= 0) {
                resultCode.append(" />");
            } else {
                resultCode.append(">");
                resultCode.append(nodeValue);
                resultCode.append("</");
                resultCode.append(rootElementName);
                resultCode.append(">");
            }
        } else {
            resultCode.append(">");
            resultCode.append("\r\n");
            for (XmlBuilder xmlBuilder : childNodes) {
                resultCode.append(xmlBuilder.toCode());
            }
            resultCode.append(addZeroIndent());
            resultCode.append("</");
            resultCode.append(rootElementName);
            resultCode.append(">");
        }
        resultCode.append("\r\n");
        return resultCode.toString();
    }

    public String c() {
        return Jx.WIDGET_NAME_PATTERN.matcher(rootElementName).replaceAll("");
    }

    private void b(int indentSize) {
        indentationLevel = indentSize;
        if (childNodes != null) {
            for (XmlBuilder nx : childNodes) {
                nx.b(indentSize + 1);
            }
        }
    }

    class AttributeBuilder {

        private final String value;
        private String namespace;
        private String attr;

        private AttributeBuilder(String namespace, String attr, String value) {
            this.namespace = namespace;
            this.attr = attr;
            this.value = value;
        }

        private AttributeBuilder(String value) {
            this.value = value;
        }

        private String toCode() {
            if (namespace != null && !namespace.isEmpty()) {
                return namespace + ":" + attr + "=" + "\"" + value + "\"";
            } else if (attr == null || attr.length() <= 0) {
                return g != null ? value.replaceAll("\n", g) : value;
            } else {
                return attr + "=" + "\"" + value + "\"";
            }
        }
    }
}