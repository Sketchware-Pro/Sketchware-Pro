package mod.bobur;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlToSvgConverter {

    public static String xml2svg(String xmlContent) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(xmlContent.getBytes()));

            // Prepare the SVG output
            StringWriter svg = new StringWriter();
            svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");

            // Get width and height attributes
            Element root = document.getDocumentElement();
            String width = parseDimension(root.getAttribute("android:width"));
            String height = parseDimension(root.getAttribute("android:height"));
            if (!width.isEmpty() && !height.isEmpty()) {
                svg.append("width=\"").append(width).append("px\" height=\"").append(height).append("px\" ");
            }
            // Set viewBox to match width and height to ensure proper scaling
            svg.append("viewBox=\"0 0 ").append(width.isEmpty() ? "100" : width)
                    .append(" ").append(height.isEmpty() ? "100" : height).append("\" ");
            svg.append(">\n");

            // Process root element
            processElement(root, svg);

            svg.append("</svg>");
            return svg.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            case "shape":
                handleShape(element, svg);
                break;
            case "gradient":
                handleGradient(element, svg);
                break;
            case "layer-list":
                handleLayerList(element, svg);
                break;
            default:
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

    private static void handleShape(Element shape, StringWriter svg) {
        String shapeType = shape.getAttribute("android:shape");
        switch (shapeType) {
            case "rectangle":
                handleRectangleShape(shape, svg);
                break;
            case "":
                handleRectangleShape(shape, svg);
                break;
            case "oval":
                handleOvalShape(shape, svg);
                break;
            case "line":
                handleLineShape(shape, svg);
                break;
            case "ring":
                handleRingShape(shape, svg);
                break;
            default:
                break;
        }
    }

    private static void handleRectangleShape(Element shape, StringWriter svg) {
        // Parse solid color
        String color = getChildAttribute(shape, "solid", "android:color");

        // Parse stroke attributes
        String strokeColor = getChildAttribute(shape, "stroke", "android:color");
        String strokeWidth = parseDimension(getChildAttribute(shape, "stroke", "android:width"));

        // Parse corner radius
        String topLeftRadius = parseDimension(getChildAttribute(shape, "corners", "android:topLeftRadius"));
        String topRightRadius = parseDimension(getChildAttribute(shape, "corners", "android:topRightRadius"));
        String bottomLeftRadius = parseDimension(getChildAttribute(shape, "corners", "android:bottomLeftRadius"));
        String bottomRightRadius = parseDimension(getChildAttribute(shape, "corners", "android:bottomRightRadius"));

        // If individual corners are specified, use them; otherwise, apply a single radius to all corners
        if (topLeftRadius.isEmpty()) topLeftRadius = parseDimension(getChildAttribute(shape, "corners", "android:radius"));
        if (topRightRadius.isEmpty()) topRightRadius = parseDimension(getChildAttribute(shape, "corners", "android:radius"));
        if (bottomLeftRadius.isEmpty()) bottomLeftRadius = parseDimension(getChildAttribute(shape, "corners", "android:radius"));
        if (bottomRightRadius.isEmpty()) bottomRightRadius = parseDimension(getChildAttribute(shape, "corners", "android:radius"));

        // Start <rect> tag for rectangle shape
        svg.append("<rect width=\"100%\" height=\"100%\" ");
        if (!color.isEmpty()) {
            svg.append("fill=\"").append(color).append("\" ");
        }
        if (!strokeColor.isEmpty() && !strokeWidth.isEmpty()) {
            svg.append("stroke=\"").append(strokeColor).append("\" ");
            svg.append("stroke-width=\"").append(strokeWidth).append("\" ");
        }

        // Apply rounded corners if specified
        if (!topLeftRadius.isEmpty() || !topRightRadius.isEmpty() || !bottomLeftRadius.isEmpty() || !bottomRightRadius.isEmpty()) {
            svg.append("rx=\"").append(topLeftRadius).append("\" ry=\"").append(topLeftRadius).append("\" ");
        }

        svg.append("/>\n");
    }

    private static void handleOvalShape(Element shape, StringWriter svg) {
        String color = getChildAttribute(shape, "solid", "android:color");
        String strokeColor = getChildAttribute(shape, "stroke", "android:color");
        String strokeWidth = parseDimension(getChildAttribute(shape, "stroke", "android:width"));

        svg.append("<ellipse cx=\"50%\" cy=\"50%\" rx=\"50%\" ry=\"50%\" ");
        if (!color.isEmpty()) {
            svg.append("fill=\"").append(color).append("\" ");
        }
        if (!strokeColor.isEmpty() && !strokeWidth.isEmpty()) {
            svg.append("stroke=\"").append(strokeColor).append("\" ");
            svg.append("stroke-width=\"").append(strokeWidth).append("\" ");
        }
        svg.append("/>\n");
    }

    private static void handleLineShape(Element shape, StringWriter svg) {
        String strokeColor = getChildAttribute(shape, "stroke", "android:color");
        String strokeWidth = parseDimension(getChildAttribute(shape, "stroke", "android:width"));

        svg.append("<line x1=\"0\" y1=\"50%\" x2=\"100%\" y2=\"50%\" ");
        if (!strokeColor.isEmpty()) {
            svg.append("stroke=\"").append(strokeColor).append("\" ");
        }
        if (!strokeWidth.isEmpty()) {
            svg.append("stroke-width=\"").append(strokeWidth).append("\" ");
        }
        svg.append("/>\n");
    }

    private static void handleRingShape(Element shape, StringWriter svg) {
        String color = getChildAttribute(shape, "solid", "android:color");
        String strokeColor = getChildAttribute(shape, "stroke", "android:color");
        String strokeWidth = parseDimension(getChildAttribute(shape, "stroke", "android:width"));

        svg.append("<circle cx=\"50%\" cy=\"50%\" r=\"40%\" ");
        if (!color.isEmpty()) {
            svg.append("fill=\"").append(color).append("\" ");
        } else {
            svg.append("fill=\"none\" ");
        }
        if (!strokeColor.isEmpty() && !strokeWidth.isEmpty()) {
            svg.append("stroke=\"").append(strokeColor).append("\" ");
            svg.append("stroke-width=\"").append(strokeWidth).append("\" ");
        }
        svg.append("/>\n");
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

    private static void handleGradient(Element gradient, StringWriter svg) {
        String startColor = parseHexColor(gradient.getAttribute("android:startColor"));
        String endColor = parseHexColor(gradient.getAttribute("android:endColor"));
        String angle = gradient.getAttribute("android:angle");

        String gradientId = "grad" + System.currentTimeMillis();
        svg.append("<defs><linearGradient id=\"").append(gradientId).append("\" ");

        switch (angle) {
            case "0":
                svg.append("x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"0%\" ");
                break;
            case "90":
                svg.append("x1=\"0%\" y1=\"0%\" x2=\"0%\" y2=\"100%\" ");
                break;
            case "180":
                svg.append("x1=\"100%\" y1=\"0%\" x2=\"0%\" y2=\"0%\" ");
                break;
            case "270":
                svg.append("x1=\"0%\" y1=\"100%\" x2=\"0%\" y2=\"0%\" ");
                break;
            default:
                svg.append("x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\" "); // default diagonal gradient
                break;
        }
        svg.append(">\n");

        if (!startColor.isEmpty()) {
            svg.append("<stop offset=\"0%\" style=\"stop-color:").append(startColor).append(";\" />\n");
        }
        if (!endColor.isEmpty()) {
            svg.append("<stop offset=\"100%\" style=\"stop-color:").append(endColor).append(";\" />\n");
        }
        svg.append("</linearGradient></defs>\n");

        svg.append("<rect width=\"100%\" height=\"100%\" fill=\"url(#").append(gradientId).append(")\" />\n");
    }

    private static void handleLayerList(Element layerList, StringWriter svg) {
        svg.append("<g>\n");
        NodeList items = layerList.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            processElement((Element) items.item(i), svg);
        }
        svg.append("</g>\n");
    }

    private static String parseHexColor(String color) {
        if (color.startsWith("#")) {
            if (color.length() == 9) {
                int alpha = Integer.parseInt(color.substring(1, 3), 16);
                int red = Integer.parseInt(color.substring(3, 5), 16);
                int green = Integer.parseInt(color.substring(5, 7), 16);
                int blue = Integer.parseInt(color.substring(7, 9), 16);
                float alphaFloat = alpha / 255f; // Convert alpha to float (0.0 to 1.0)
                return String.format("rgba(%d, %d, %d, %.2f)", red, green, blue, alphaFloat);
            }else if (color.length() == 7) {
                return color;
            }
        }
        return color;
    }


    private static String parseDimension(String value) {
        return value.replaceAll("[^\\d.]", "");
    }

    private static String getChildAttribute(Element parent, String childTag, String attributeName) {
        NodeList children = parent.getElementsByTagName(childTag);
        if (children.getLength() > 0) {
            Element child = (Element) children.item(0);
            return child.getAttribute(attributeName);
        }
        return "";
    }
}

