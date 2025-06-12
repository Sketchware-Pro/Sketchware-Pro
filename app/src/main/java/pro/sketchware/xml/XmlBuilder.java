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

    public void addAttribute(String namespace, String attr, String value) {
        attributes.add(new AttributeBuilder(namespace, attr, value));
    }

    public void addAttributeValue(String value) {
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
                return value.replaceAll("\n", g);
            } else {
                return attr + "=" + "\"" + value + "\"";
            }
        }
    }
}