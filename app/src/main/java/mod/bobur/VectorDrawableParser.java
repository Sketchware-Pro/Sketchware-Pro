package mod.bobur;

import static com.besome.sketch.design.DesignActivity.sc_id;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import a.a.a.wq;
import pro.sketchware.SketchApplication;
import pro.sketchware.activities.resourceseditor.components.utils.ColorsEditorManager;
import pro.sketchware.utility.FileUtil;

public class VectorDrawableParser {

    public static class Group {
        private String name = "";
        private String rotation = "";
        private String pivotX = "";
        private String pivotY = "";
        private String scaleX = "";
        private String scaleY = "";
        private String translateX = "";
        private String translateY = "";

        public String getName() {
            return name;
        }

        public void setName(String v) {
            name = v == null ? "" : v;
        }

        public String getRotation() {
            return rotation;
        }

        public void setRotation(String v) {
            rotation = v == null ? "" : v;
        }

        public String getPivotX() {
            return pivotX;
        }

        public void setPivotX(String v) {
            pivotX = v == null ? "" : v;
        }

        public String getPivotY() {
            return pivotY;
        }

        public void setPivotY(String v) {
            pivotY = v == null ? "" : v;
        }

        public String getScaleX() {
            return scaleX;
        }

        public void setScaleX(String v) {
            scaleX = v == null ? "" : v;
        }

        public String getScaleY() {
            return scaleY;
        }

        public void setScaleY(String v) {
            scaleY = v == null ? "" : v;
        }

        public String getTranslateX() {
            return translateX;
        }

        public void setTranslateX(String v) {
            translateX = v == null ? "" : v;
        }

        public String getTranslateY() {
            return translateY;
        }

        public void setTranslateY(String v) {
            translateY = v == null ? "" : v;
        }
    }

    public static class ClipPath {
        private String name = "";
        private String pathData = "";
        private String fillType = "";

        public String getName() {
            return name;
        }

        public void setName(String v) {
            name = v == null ? "" : v;
        }

        public String getPathData() {
            return pathData;
        }

        public void setPathData(String v) {
            pathData = v == null ? "" : v;
        }

        public String getFillType() {
            return fillType;
        }

        public void setFillType(String v) {
            fillType = v == null ? "" : v;
        }
    }

    public class Path {
        private String name = "";
        private String pathData = "";
        private String fillColor = "";
        private String fillAlpha = "";
        private String strokeColor = "";
        private String strokeAlpha = "";
        private String strokeWidth = "";
        private String strokeLineCap = "";
        private String strokeLineJoin = "";
        private String strokeMiterLimit = "";
        private String fillType = "";
        private String trimPathStart = "";
        private String trimPathEnd = "";
        private String trimPathOffset = "";

        public String getName() {
            return name;
        }

        public void setName(String v) {
            name = v == null ? "" : v;
        }

        public String getPathData() {
            return pathData;
        }

        public void setPathData(String v) {
            pathData = v == null ? "" : v;
        }

        public String getFillColor() {
            return fillColor;
        }

        public void setFillColor(String v) {
            fillColor = v == null ? "" : v;
        }

        public String getFillAlpha() {
            return fillAlpha;
        }

        public void setFillAlpha(String v) {
            fillAlpha = v == null ? "" : v;
        }

        public String getStrokeColor() {
            return strokeColor;
        }

        public void setStrokeColor(String v) {
            strokeColor = v == null ? "" : v;
        }

        public String getStrokeAlpha() {
            return strokeAlpha;
        }

        public void setStrokeAlpha(String v) {
            strokeAlpha = v == null ? "" : v;
        }

        public String getStrokeWidth() {
            return strokeWidth;
        }

        public void setStrokeWidth(String v) {
            strokeWidth = v == null ? "" : v;
        }

        public String getStrokeLineCap() {
            return strokeLineCap;
        }

        public void setStrokeLineCap(String v) {
            strokeLineCap = v == null ? "" : v;
        }

        public String getStrokeLineJoin() {
            return strokeLineJoin;
        }

        public void setStrokeLineJoin(String v) {
            strokeLineJoin = v == null ? "" : v;
        }

        public String getStrokeMiterLimit() {
            return strokeMiterLimit;
        }

        public void setStrokeMiterLimit(String v) {
            strokeMiterLimit = v == null ? "" : v;
        }

        public String getFillType() {
            return fillType;
        }

        public void setFillType(String v) {
            fillType = v == null ? "" : v;
        }

        public String getTrimPathStart() {
            return trimPathStart;
        }

        public void setTrimPathStart(String v) {
            trimPathStart = v == null ? "" : v;
        }

        public String getTrimPathEnd() {
            return trimPathEnd;
        }

        public void setTrimPathEnd(String v) {
            trimPathEnd = v == null ? "" : v;
        }

        public String getTrimPathOffset() {
            return trimPathOffset;
        }

        public void setTrimPathOffset(String v) {
            trimPathOffset = v == null ? "" : v;
        }

        public String getResolvedFillColor() {
            return resolveColorForSvg(fillColor);
        }

        public String getResolvedStrokeColor() {
            return resolveColorForSvg(strokeColor);
        }

        public boolean hasFill() {
            String f = getResolvedFillColor();
            return !isEmpty(f) && !"none".equalsIgnoreCase(f);
        }

        public boolean hasStroke() {
            String s = getResolvedStrokeColor();
            return !isEmpty(s) && !"none".equalsIgnoreCase(s);
        }
    }

    private String width = "";
    private String height = "";
    private String viewportWidth = "";
    private String viewportHeight = "";
    private String tint = "";
    private String tintMode = "";
    private String alpha = "";
    private String autoMirrored = "";

    private final ArrayList<Group> groups = new ArrayList<>();
    private final ArrayList<Path> paths = new ArrayList<>();
    private final ArrayList<ClipPath> clipPaths = new ArrayList<>();

    private ColorsEditorManager colors;
    private String colorsXmlPath = "";

    public VectorDrawableParser(String vectorDrawableAsString) {
        if (!isValidVector(vectorDrawableAsString)) {
            throw new IllegalArgumentException("Not a valid VectorDrawable XML.");
        }
        parse(vectorDrawableAsString);
    }

    public void parse(String xml) {
        width = "";
        height = "";
        viewportWidth = "";
        viewportHeight = "";
        tint = "";
        tintMode = "";
        alpha = "";
        autoMirrored = "";
        groups.clear();
        paths.clear();
        clipPaths.clear();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element root = doc.getDocumentElement();
            if (root == null) return;
            if (!"vector".equals(root.getTagName())) return;

            width = root.getAttribute("android:width");
            height = root.getAttribute("android:height");
            viewportWidth = root.getAttribute("android:viewportWidth");
            viewportHeight = root.getAttribute("android:viewportHeight");
            tint = root.getAttribute("android:tint");
            tintMode = root.getAttribute("android:tintMode");
            alpha = root.getAttribute("android:alpha");
            autoMirrored = root.getAttribute("android:autoMirrored");

            walk(root);

        } catch (Exception e) {
            throw new IllegalArgumentException("VectorDrawable" + e.getMessage());
        }
    }

    private void walk(Element el) {
        String tag = el.getTagName();

        if ("group".equals(tag)) {
            Group g = new Group();
            g.setName(el.getAttribute("android:name"));
            g.setRotation(el.getAttribute("android:rotation"));
            g.setPivotX(el.getAttribute("android:pivotX"));
            g.setPivotY(el.getAttribute("android:pivotY"));
            g.setScaleX(el.getAttribute("android:scaleX"));
            g.setScaleY(el.getAttribute("android:scaleY"));
            g.setTranslateX(el.getAttribute("android:translateX"));
            g.setTranslateY(el.getAttribute("android:translateY"));
            groups.add(g);
        } else if ("path".equals(tag)) {
            Path p = new Path();
            p.setName(el.getAttribute("android:name"));
            p.setPathData(el.getAttribute("android:pathData"));
            p.setFillColor(el.getAttribute("android:fillColor"));
            p.setFillAlpha(el.getAttribute("android:fillAlpha"));
            p.setStrokeColor(el.getAttribute("android:strokeColor"));
            p.setStrokeAlpha(el.getAttribute("android:strokeAlpha"));
            p.setStrokeWidth(el.getAttribute("android:strokeWidth"));
            p.setStrokeLineCap(el.getAttribute("android:strokeLineCap"));
            p.setStrokeLineJoin(el.getAttribute("android:strokeLineJoin"));
            p.setStrokeMiterLimit(el.getAttribute("android:strokeMiterLimit"));
            p.setFillType(el.getAttribute("android:fillType"));
            p.setTrimPathStart(el.getAttribute("android:trimPathStart"));
            p.setTrimPathEnd(el.getAttribute("android:trimPathEnd"));
            p.setTrimPathOffset(el.getAttribute("android:trimPathOffset"));
            paths.add(p);
        } else if ("clip-path".equals(tag) || "clipPath".equals(tag)) {
            ClipPath cp = new ClipPath();
            cp.setName(el.getAttribute("android:name"));
            cp.setPathData(el.getAttribute("android:pathData"));
            cp.setFillType(el.getAttribute("android:fillType"));
            clipPaths.add(cp);
        }

        NodeList children = el.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {
                walk((Element) n);
            }
        }
    }

    private ColorsEditorManager colors() {
        if (colors == null) colors = new ColorsEditorManager();
        ensureColorsXmlPath();
        if (!isEmpty(colorsXmlPath)) colors.contentPath = colorsXmlPath;
        return colors;
    }

    private void ensureColorsXmlPath() {
        if (!isEmpty(colorsXmlPath)) return;

        String p1 = wq.b(sc_id) + "/files/resource/values/colors.xml";
        if (FileUtil.isExistFile(p1)) {
            colorsXmlPath = p1;
            return;
        }
        String p2 = wq.d(sc_id) + "/app/src/main/res/values/colors.xml";
        if (FileUtil.isExistFile(p2)) {
            colorsXmlPath = p2;
        }
    }

    private String resolveColorForSvg(String value) {
        if (isEmpty(value)) return "";
        String resolved = colors().getColorValue(SketchApplication.getContext(), value, 4);
        if (resolved == null) resolved = "";
        return normalizeColorForSvg(resolved);
    }

    private String normalizeColorForSvg(String c) {
        if (isEmpty(c)) return "";
        if (c.matches("^#([A-Fa-f0-9]{8})$")) {
            String a = c.substring(1, 3);
            String rgb = c.substring(3);
            if ("00".equalsIgnoreCase(a)) return "none";
            return "#" + rgb;
        }
        if ("#00000000".equalsIgnoreCase(c)) return "none";
        return c;
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public String getResolvedTint() {
        if (isEmpty(tint)) return "";
        return resolveColorForSvg(tint);
    }

    private int dpToPx(int dp) {
        return (int) (dp * SketchApplication
                .getContext()
                .getResources()
                .getDisplayMetrics().density);
    }

    private String dpToPx(String dp) {
        return String.valueOf(dpToPx(Integer.parseInt(dp.replaceAll("[^0-9]", ""))));
    }

    public String toSvg() {
        StringBuilder svg = new StringBuilder();

        String w = width.isEmpty() ? "150" : dpToPx(width);
        String h = height.isEmpty() ? "150" : dpToPx(height);
        String vw = viewportWidth.isEmpty() ? "100" : viewportWidth;
        String vh = viewportHeight.isEmpty() ? "100" : viewportHeight;

        svg
                .append("<svg xmlns=\"http://www.w3.org/2000/svg\" ")
                .append("width=\"")
                .append(w)
                .append("px\" ")
                .append("height=\"")
                .append(h)
                .append("px\" ")
                .append("viewBox=\"0 0 ")
                .append(vw)
                .append(" ")
                .append(vh)
                .append("\">\n");

        for (Path p : paths) {
            svg.append("<path d=\"").append(p.getPathData()).append("\" ");

            String tint = getResolvedTint();

            String fill;
            String stroke;

            if (!isEmpty(tint)) {
                fill = p.hasFill() ? tint : "none";
                stroke = p.hasStroke() ? tint : "";
            } else {
                fill = p.getResolvedFillColor();
                stroke = p.getResolvedStrokeColor();
            }

            if (!isEmpty(fill) && !"none".equals(fill)) {
                svg.append("fill=\"").append(fill).append("\" ");
                if (!p.getFillAlpha().isEmpty()) {
                    svg.append("fill-opacity=\"").append(p.getFillAlpha()).append("\" ");
                }
            } else {
                svg.append("fill=\"none\" ");
            }

            if (!isEmpty(stroke)) {
                svg.append("stroke=\"").append(stroke).append("\" ");
                if (!p.getStrokeWidth().isEmpty()) {
                    svg
                            .append("stroke-width=\"")
                            .append(p.getStrokeWidth().replaceAll("[^0-9.]", ""))
                            .append("\" ");
                }
                if (!p.getStrokeAlpha().isEmpty()) {
                    svg.append("stroke-opacity=\"").append(p.getStrokeAlpha()).append("\" ");
                }
                if (!p.getStrokeLineCap().isEmpty()) {
                    svg
                            .append("stroke-linecap=\"")
                            .append(p.getStrokeLineCap().toLowerCase())
                            .append("\" ");
                }
                if (!p.getStrokeLineJoin().isEmpty()) {
                    svg
                            .append("stroke-linejoin=\"")
                            .append(p.getStrokeLineJoin().toLowerCase())
                            .append("\" ");
                }
                if (!p.getStrokeMiterLimit().isEmpty()) {
                    svg
                            .append("stroke-miterlimit=\"")
                            .append(p.getStrokeMiterLimit())
                            .append("\" ");
                }
            }

            svg.append("/>\n");
        }

        svg.append("</svg>");
        return svg.toString();
    }

    public boolean isValidVector(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element root = doc.getDocumentElement();
            return root != null && "vector".equals(root.getTagName());
        } catch (Exception e) {
            return false;
        }
    }
}
