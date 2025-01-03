package pro.sketchware.activities.resources.editors.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import pro.sketchware.activities.resources.editors.models.StyleModel;

public class StylesEditorManager {

    public boolean isDataLoadingFailed;

    public String getAttributesCode(StyleModel style) {
        StringBuilder attributesCode = new StringBuilder();

        for (Map.Entry<String, String> entry : style.getAttributes().entrySet()) {
            attributesCode
                    .append("<item name=\"")
                    .append(entry.getKey())
                    .append("\">")
                    .append(entry.getValue())
                    .append("</item>\n");
        }

        return attributesCode.toString().trim();
    }

    public Map<String, String> convertAttributesToMap(String attributesCode) {
        Map<String, String> attributesMap = new HashMap<>();


        String[] lines = attributesCode.split("\n");
        for (String line : lines) {
            if (line.startsWith("<item name=\"") && line.endsWith("</item>")) {
                int nameStart = line.indexOf("\"") + 1;
                int nameEnd = line.indexOf("\"", nameStart);
                String name = line.substring(nameStart, nameEnd);

                int valueStart = line.indexOf(">", nameEnd) + 1;
                int valueEnd = line.lastIndexOf("</item>");
                String value = line.substring(valueStart, valueEnd).trim();

                attributesMap.put(name, value);
            }
        }

        return attributesMap;
    }

    public String convertStylesToXML(ArrayList<StyleModel> stylesList) {
        StringBuilder xmlContent = new StringBuilder();

        xmlContent.append("<resources>\n\n");

        for (StyleModel style : stylesList) {
            xmlContent.append("    <style name=\"").append(style.getStyleName()).append("\"");

            if (style.getParent() != null && !style.getParent().isEmpty()) {
                xmlContent.append(" parent=\"").append(style.getParent()).append("\"");
            }

            xmlContent.append(">\n");

            Map<String, String> attributes = style.getAttributes();
            for (String attrName : attributes.keySet()) {
                String attrValue = attributes.get(attrName);
                xmlContent.append("        <item name=\"").append(attrName).append("\">").append(attrValue).append("</item>\n");
            }

            xmlContent.append("    </style>\n\n");
        }

        xmlContent.append("</resources>");

        return xmlContent.toString();
    }

    public ArrayList<StyleModel> parseStylesFile(String content) {
        isDataLoadingFailed = false;
        ArrayList<StyleModel> styles = new ArrayList<>();
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(content));
            Document document = builder.parse(inputSource);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("style");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String styleName = element.getAttribute("name");

                    String parent = element.hasAttribute("parent") ? element.getAttribute("parent") : null;

                    HashMap<String, String> attributes = new HashMap<>();
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNode;
                            String attrName = childElement.getAttribute("name");
                            String attrValue = childElement.getTextContent().trim();
                            attributes.put(attrName, attrValue);
                        }
                    }

                    styles.add(new StyleModel(styleName, parent, attributes));
                }
            }

        } catch (Exception ignored) {
            isDataLoadingFailed = !content.trim().isEmpty();
        }

        return styles;
    }

}
