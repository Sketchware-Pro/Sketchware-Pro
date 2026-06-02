package ide.sketchware.codeproject.dependencies;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Parses Maven POM XML files to extract packaging type and dependency information.
 */
public class PomParser {

    /**
     * Contains parsed information from a POM file.
     */
    public static class PomInfo {
        public String packaging; // "jar" or "aar"
        public List<DependencyDeclaration> dependencies;

        public PomInfo() {
            this.packaging = "jar";
            this.dependencies = new ArrayList<>();
        }
    }

    /**
     * Parses a POM XML input stream and extracts packaging and dependency information.
     *
     * @param pomXml the input stream of the POM XML
     * @return parsed POM information
     * @throws Exception if XML parsing fails
     */
    public static PomInfo parse(InputStream pomXml) throws Exception {
        PomInfo info = new PomInfo();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        // Harden against XXE — POM XML comes from remote repositories
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);
        try {
            factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        } catch (Exception ignored) {
            // Some features may be unsupported on certain parsers; secure-processing is the key one
        }
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(pomXml);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();

        // Extract packaging
        NodeList packagingNodes = root.getElementsByTagName("packaging");
        if (packagingNodes.getLength() > 0) {
            String packagingValue = packagingNodes.item(0).getTextContent().trim();
            if (!packagingValue.isEmpty()) {
                info.packaging = packagingValue;
            }
        }

        // Parse <properties> for placeholder resolution (e.g. ${kotlin.version})
        Map<String, String> properties = parseProperties(root);
        // Built-in project coordinates for ${project.version} style placeholders
        // (only if the POM didn't declare its own same-named properties)
        String projectVersion = getChildText(root, "version");
        if (projectVersion != null && !projectVersion.isEmpty()) {
            if (!properties.containsKey("project.version")) {
                properties.put("project.version", projectVersion);
            }
            if (!properties.containsKey("version")) {
                properties.put("version", projectVersion);
            }
        }

        // Extract dependency management versions for resolution
        Map<String, String> managedVersions = parseDependencyManagement(root, properties);

        // Extract dependencies
        NodeList dependenciesNodes = root.getElementsByTagName("dependencies");
        for (int i = 0; i < dependenciesNodes.getLength(); i++) {
            Node depNode = dependenciesNodes.item(i);
            if (depNode.getNodeType() != Node.ELEMENT_NODE) continue;

            // Skip if parent is dependencyManagement
            Node parent = depNode.getParentNode();
            if (parent != null && "dependencyManagement".equals(parent.getNodeName())) {
                continue;
            }

            Element depsElement = (Element) depNode;
            NodeList depList = depsElement.getElementsByTagName("dependency");

            for (int j = 0; j < depList.getLength(); j++) {
                Node depItem = depList.item(j);
                if (depItem.getNodeType() != Node.ELEMENT_NODE) continue;

                // Make sure this dependency element is a direct child of the dependencies element
                if (depItem.getParentNode() != depsElement) continue;

                Element depElement = (Element) depItem;
                String groupId = getChildText(depElement, "groupId");
                String artifactId = getChildText(depElement, "artifactId");
                String version = getChildText(depElement, "version");
                String scope = getChildText(depElement, "scope");
                String optional = getChildText(depElement, "optional");

                // Skip test, provided, system, and runtime scopes
                if ("test".equals(scope) || "provided".equals(scope)
                        || "system".equals(scope)) {
                    continue;
                }

                // Skip optional dependencies
                if ("true".equals(optional)) {
                    continue;
                }

                // Skip if groupId or artifactId is missing
                if (groupId == null || groupId.isEmpty() || artifactId == null || artifactId.isEmpty()) {
                    continue;
                }

                // Resolve version from dependency management if not specified
                if (version == null || version.isEmpty()) {
                    String key = groupId + ":" + artifactId;
                    version = managedVersions.get(key);
                }

                // Substitute ${...} property placeholders from the same POM
                version = resolvePlaceholders(version, properties);

                // Skip if version still unresolved (or contains unresolved placeholders)
                if (version == null || version.isEmpty() || version.contains("${")) {
                    continue;
                }

                // Construct the declaration; skip (don't abort) on invalid coordinates
                try {
                    info.dependencies.add(new DependencyDeclaration(groupId, artifactId, version));
                } catch (IllegalArgumentException ignored) {
                    // Malformed coordinate from a remote POM — skip it safely
                }
            }
        }

        return info;
    }

    /**
     * Parses the dependencyManagement section to extract managed versions.
     */
    private static Map<String, String> parseDependencyManagement(Element root, Map<String, String> properties) {
        Map<String, String> managedVersions = new HashMap<>();

        NodeList dmNodes = root.getElementsByTagName("dependencyManagement");
        for (int i = 0; i < dmNodes.getLength(); i++) {
            Node dmNode = dmNodes.item(i);
            if (dmNode.getNodeType() != Node.ELEMENT_NODE) continue;

            Element dmElement = (Element) dmNode;
            NodeList depList = dmElement.getElementsByTagName("dependency");

            for (int j = 0; j < depList.getLength(); j++) {
                Node depItem = depList.item(j);
                if (depItem.getNodeType() != Node.ELEMENT_NODE) continue;

                Element depElement = (Element) depItem;
                String groupId = getChildText(depElement, "groupId");
                String artifactId = getChildText(depElement, "artifactId");
                String version = resolvePlaceholders(getChildText(depElement, "version"), properties);

                if (groupId != null && artifactId != null && version != null
                        && !version.contains("${")) {
                    managedVersions.put(groupId + ":" + artifactId, version);
                }
            }
        }

        return managedVersions;
    }

    /**
     * Parses the top-level &lt;properties&gt; section into a key→value map.
     */
    private static Map<String, String> parseProperties(Element root) {
        Map<String, String> properties = new HashMap<>();
        NodeList propsNodes = root.getElementsByTagName("properties");
        for (int i = 0; i < propsNodes.getLength(); i++) {
            Node propsNode = propsNodes.item(i);
            if (propsNode.getNodeType() != Node.ELEMENT_NODE) continue;
            // Only the project-level <properties> (direct child of root)
            if (propsNode.getParentNode() != root) continue;

            NodeList children = propsNode.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node child = children.item(j);
                if (child.getNodeType() != Node.ELEMENT_NODE) continue;
                String name = child.getNodeName();
                String value = child.getTextContent();
                if (name != null && value != null) {
                    properties.put(name.trim(), value.trim());
                }
            }
        }
        return properties;
    }

    /**
     * Substitutes ${...} placeholders in a value using the given properties map.
     * Returns the input unchanged if it has no placeholders or they can't be resolved.
     */
    private static String resolvePlaceholders(String value, Map<String, String> properties) {
        if (value == null || !value.contains("${")) {
            return value;
        }
        String result = value;
        // Resolve up to a few levels of nested placeholders
        for (int pass = 0; pass < 5 && result.contains("${"); pass++) {
            int start = result.indexOf("${");
            int end = result.indexOf('}', start);
            if (end < 0) break;
            String key = result.substring(start + 2, end);
            String replacement = properties.get(key);
            if (replacement == null) {
                break; // unresolved — leave ${...} so caller skips it
            }
            result = result.substring(0, start) + replacement + result.substring(end + 1);
        }
        return result;
    }

    /**
     * Gets the text content of a direct child element by tag name.
     */
    private static String getChildText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            // Only consider direct children
            if (node.getParentNode() == parent) {
                String text = node.getTextContent();
                return text != null ? text.trim() : null;
            }
        }
        return null;
    }
}
