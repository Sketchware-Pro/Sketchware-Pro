package mod.bobur;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlToSvgConverter {

    public static String xml2svg(String xml) throws Exception {
        // Parse the XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

        // Retrieve vector attributes
        Element vector = (Element) doc.getElementsByTagName("vector").item(0);
        String width = vector.getAttribute("android:width").replace("dp", "");
        String height = vector.getAttribute("android:height").replace("dp", "");
        String viewportWidth = vector.getAttribute("android:viewportWidth");
        String viewportHeight = vector.getAttribute("android:viewportHeight");

        // Start building the SVG with DOCTYPE and version
        StringBuilder svg = new StringBuilder();
        svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n").append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" ").append("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n").append("<svg xmlns=\"http://www.w3.org/2000/svg\" ").append("xmlns:xlink=\"http://www.w3.org/1999/xlink\" ").append("version=\"1.1\"").append("width=\"").append(width).append("\" ").append("height=\"").append(height).append("\" ").append("viewBox=\"0 0 ").append(viewportWidth).append(" ").append(viewportHeight).append("\">\n");

        // Retrieve and convert paths without unnecessary fill attributes
        NodeList paths = doc.getElementsByTagName("path");
        for (int i = 0; i < paths.getLength(); i++) {
            Element path = (Element) paths.item(i);
            String pathData = path.getAttribute("android:pathData");

            svg.append("<path d=\"").append(pathData).append("\" />\n");
        }

        svg.append("</svg>");
        return svg.toString();
    }
}
