package mod.bobur.helpers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * This class is converts XML vector drawables to SVG (Only vector drawables are supported)
 **/

public class XmlToSvgConverter {

    public static String xml2svg(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes()));

            StringWriter svg = new StringWriter();
            svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");

            Element root = document.getDocumentElement();
            String width = parseDimension(root.getAttribute("android:width"));
            String height = parseDimension(root.getAttribute("android:height"));

            if (root.getTagName().equals("vector")) {
                svg.append("width=\"").append(width).append("px\" height=\"").append(height).append("px\" ");
                svg.append("viewBox=\"0 0 ").append(width.isEmpty() ? "100" : width)
                        .append(" ").append(height.isEmpty() ? "100" : height).append("\" ");
            } else {
                return "NOT_SUPPORTED_YET";
            }
            svg.append(">\n");
            processElement(root, svg);

            svg.append("</svg>");
            return svg.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "NOT_SUPPORTED";
        }
    }

    private static void processElement(Element element, StringWriter svg) {
        String tagName = element.getTagName();

        switch (tagName) {
            case "vector":
                handleVector(element, svg);
                break;
            case "path":
                handlePath(element, svg);
                break;
            default:
                svg.append("Error: Not supported tag - ").append(tagName).append("\n");
                break;
        }

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processElement((Element) child, svg);
            }
        }
    }

    private static void handleVector(Element vector, StringWriter svg) {
        String viewportWidth = vector.getAttribute("android:viewportWidth");
        String viewportHeight = vector.getAttribute("android:viewportHeight");

        svg.append("<g ");
        if (!viewportWidth.isEmpty() && !viewportHeight.isEmpty()) {
            svg.append("viewBox=\"0 0 ").append(viewportWidth).append(" ").append(viewportHeight).append("\" ");
        }
        svg.append(">\n");

        NodeList children = vector.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                processElement((Element) child, svg);
            }
        }

        svg.append("</g>\n");
    }

    private static void handlePath(Element path, StringWriter svg) {
        String pathData = path.getAttribute("android:pathData");
        String fillColor = parseHexColor(path.getAttribute("android:fillColor"));
        String strokeColor = parseHexColor(path.getAttribute("android:strokeColor"));
        String strokeWidth = parseDimension(path.getAttribute("android:strokeWidth"));

        svg.append("<path d=\"").append(pathData).append("\" ");
        if (!fillColor.isEmpty()) svg.append("fill=\"").append(fillColor).append("\" ");
        if (!strokeColor.isEmpty()) svg.append("stroke=\"").append(strokeColor).append("\" ");
        if (!strokeWidth.isEmpty()) svg.append("stroke-width=\"").append(strokeWidth).append("\" ");
        svg.append("/>\n");
    }

    private static String parseHexColor(String color) {
        // It gets only color as RGB (not ARGB)
        if (color.startsWith("#")) {
            if (color.length() == 9) {
                return "#" + color.substring(3, 9);
            } else if (color.length() == 7) {
                return color;
            }
        }
        return color;
    }


    private static String parseDimension(String value) {
        return value.replaceAll("[^\\d.]", "");
    }
}
