package pro.sketchware.activities.resources.editors.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class StringsEditorManager {

    public boolean isDataLoadingFailed;

    public void convertXmlStringsToListMap(final String xmlString, final ArrayList<HashMap<String, Object>> listMap) {
        isDataLoadingFailed = false;
        try {
            listMap.clear();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(new InputSource(input));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("string");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    HashMap<String, Object> map = getStringHashMap((Element) node);
                    listMap.add(map);
                }
            }
        } catch (Exception ignored) {
            isDataLoadingFailed = !xmlString.trim().isEmpty();
        }
    }

    private HashMap<String, Object> getStringHashMap(Element node) {
        HashMap<String, Object> map = new HashMap<>();
        String key = node.getAttribute("name");
        String value = node.getTextContent();
        map.put("key", key);
        map.put("text", value);
        return map;
    }

    public boolean isXmlStringsExist(ArrayList<HashMap<String, Object>> listMap, String value) {
        for (Map<String, Object> map : listMap) {
            if (map.containsKey("key") && value.equals(map.get("key"))) {
                return true;
            }
        }
        return false;
    }

    public String convertListMapToXmlStrings(final ArrayList<HashMap<String, Object>> listMap) {
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<resources>\n\n");
        for (HashMap<String, Object> map : listMap) {
            String key = (String) map.get("key");
            Object textObj = map.get("text");
            String text = textObj instanceof String ? (String) textObj : textObj.toString();
            String escapedText = escapeXml(text);
            xmlString.append("    <string name=\"").append(key).append("\"");
            if (map.containsKey("translatable")) {
                String translatable = (String) map.get("translatable");
                xmlString.append(" translatable=\"").append(translatable).append("\"");
            }
            xmlString.append(">").append(escapedText).append("</string>\n");
        }
        xmlString.append("\n</resources>");
        return xmlString.toString();
    }

    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;")
                .replace("\n", "&#10;")
                .replace("\r", "&#13;");
    }

}
