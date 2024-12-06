package mod.bobur.helpers;

import android.net.Uri;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import pro.sketchware.utility.FileUtil;

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
            String viewportWidth = root.getAttribute("android:viewportWidth");
            String viewportHeight = root.getAttribute("android:viewportHeight");

            if (root.getTagName().equals("vector")) {
                svg.append("width=\"150").append("px\" ");
                svg.append("height=\"150").append("px\" ");
                svg.append("viewBox=\"0 0 ").append(viewportWidth.isEmpty() ? "100" : viewportWidth)
                        .append(" ").append(viewportHeight.isEmpty() ? "100" : viewportHeight).append("\" ");
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
            case "group":
                handleGroup(element, svg);
                break;
            case "path":
                handlePath(element, svg);
                break;
            default:
                svg.append("<!-- Unsupported tag: ").append(tagName).append(" -->\n");
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

    private static void handleGroup(Element group, StringWriter svg) {
        svg.append("<g ");
        String rotation = group.getAttribute("android:rotation");
        String pivotX = group.getAttribute("android:pivotX");
        String pivotY = group.getAttribute("android:pivotY");
        String scaleX = group.getAttribute("android:scaleX");
        String scaleY = group.getAttribute("android:scaleY");
        String translateX = group.getAttribute("android:translateX");
        String translateY = group.getAttribute("android:translateY");

        if (!rotation.isEmpty() || !scaleX.isEmpty() || !scaleY.isEmpty() ||
                !translateX.isEmpty() || !translateY.isEmpty()) {
            svg.append("transform=\"");
            if (!translateX.isEmpty() || !translateY.isEmpty()) {
                svg.append("translate(").append(translateX.isEmpty() ? "0" : translateX).append(",");
                svg.append(translateY.isEmpty() ? "0" : translateY).append(") ");
            }
            if (!scaleX.isEmpty() || !scaleY.isEmpty()) {
                svg.append("scale(").append(scaleX.isEmpty() ? "1" : scaleX).append(",");
                svg.append(scaleY.isEmpty() ? "1" : scaleY).append(") ");
            }
            if (!rotation.isEmpty()) {
                svg.append("rotate(").append(rotation);
                if (!pivotX.isEmpty() && !pivotY.isEmpty()) {
                    svg.append(" ").append(pivotX).append(" ").append(pivotY);
                }
                svg.append(") ");
            }
            svg.append("\" ");
        }

        svg.append(">\n");
        NodeList children = group.getChildNodes();
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
                return "#" + color.substring(3);
            } else if (color.length() == 7) {
                return color;
            }
        }
        return color;
    }


    public static ArrayList<String> getVectorDrawables(String sc_id) {
        ArrayList<String> cache = new ArrayList<>();
        FileUtil.listDir("/storage/emulated/0/.sketchware/data/" + sc_id + "/files/resource/drawable/", cache);

        ArrayList<String> files = new ArrayList<>();
        for (String vectorPath : cache) {
            String fileName = Uri.parse(vectorPath).getLastPathSegment();
            if (fileName != null && fileName.endsWith(".xml")) {
                try {
                    String content = FileUtil.readFile(vectorPath);
                    if (content.contains("<vector")) { // Check if it's a vector drawable
                        files.add(fileName.substring(0, fileName.length() - 4)); // Exclude ".xml"
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return files;
    }

    public static String getSvgFullPath(String sc_id, String fileName) {
        return "/storage/emulated/0/.sketchware/data/" + sc_id + "/files/resource/drawable/" + fileName + ".xml";
    }

    private static String parseDimension(String value) {
        return value.replaceAll("[^\\d.]", "");
    }
}
