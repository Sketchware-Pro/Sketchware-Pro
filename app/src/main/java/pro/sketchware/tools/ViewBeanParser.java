package pro.sketchware.tools;

import static mod.elfilibustero.sketch.lib.utils.PropertiesUtil.parseReferName;

import android.util.Pair;

import androidx.annotation.NonNull;

import a.a.a.wq;

import com.besome.sketch.beans.ViewBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ViewBeanParser {

    private final XmlPullParser parser;
    private boolean skipRoot;

    private Pair<String, Map<String, String>> rootAttributes;

    private int[] viewsCount = new int[49];

    public ViewBeanParser(String xml) throws XmlPullParserException {
        this(new StringReader(xml));
    }

    public ViewBeanParser(File path) throws XmlPullParserException, FileNotFoundException {
        this(new FileReader(path));
    }

    public ViewBeanParser(Reader reader) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(reader);
    }

    public void setSkipRoot(boolean skipRoot) {
        this.skipRoot = skipRoot;
    }

    public Pair<String, Map<String, String>> getRootAttributes() {
        return rootAttributes;
    }

    public ArrayList<ViewBean> parse() throws XmlPullParserException, IOException {
        Set<String> ids =
                new HashSet<>(
                        Arrays.asList(
                                "root", "_coordinator", "_app_bar", "_toolbar", "_fab", "_drawer"));
        ArrayList<ViewBean> beans = new ArrayList<>();
        Map<String, Map<String, String>> beansAttributes = new HashMap<>();
        Stack<ViewBean> viewStack = new Stack<>();
        int index = 0;
        boolean isRootSkipped = !skipRoot;

        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            switch (parser.getEventType()) {
                case XmlPullParser.START_TAG -> {
                    var name = parser.getName();
                    if (!isRootSkipped) {
                        Map<String, String> attributes = new LinkedHashMap<>();
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            if (!parser.getAttributeName(i).startsWith("xmlns")) {
                                attributes.put(
                                        parser.getAttributeName(i), parser.getAttributeValue(i));
                            }
                        }
                        rootAttributes = Pair.create(name, attributes);
                        isRootSkipped = true;
                        break;
                    }
                    var className = getNameFromTag(name);

                    // Special case for HorizontalScrollView, as ViewBean refers to it as
                    // HScrollView
                    if (className.equals("HorizontalScrollView")) {
                        className = "HScrollView";
                    }

                    int type = ViewBean.getViewTypeByTypeName(className);

                    // Special case for other views that can be considered built-in views by type
                    type = ViewBeanFactory.getConsideredTypeViewByName(className, type);

                    // Get view ID, either from attributes or generate a unique ID
                    String attrId = parser.getAttributeValue(null, "android:id");
                    String id =
                            attrId != null && !ids.contains(parseReferName(attrId, "/"))
                                    ? parseReferName(attrId, "/")
                                    : generateUniqueId(ids, type, className);

                    // Special case for 'include' tag with layout reference, treated as ID in
                    // ViewBean
                    if (className.equals("include")) {
                        String layout = parser.getAttributeValue(null, "layout");
                        if (layout != null) {
                            id = parseReferName(layout, "/");
                        }
                    }

                    ViewBean bean = new ViewBean(id, type);

                    bean.convert = name;

                    ViewBean parent = viewStack.isEmpty() ? null : viewStack.peek();
                    // Set parent ID (or root if no parent)
                    bean.parent = parent != null ? parent.id : "root";
                    bean.parentType =
                            bean.parent.equals("root")
                                    ? ViewBean.VIEW_TYPE_LAYOUT_LINEAR
                                    : parent.type;
                    bean.index = index;
                    Map<String, String> attributes = new LinkedHashMap<>();
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        if (!parser.getAttributeName(i).startsWith("xmlns")) {
                            attributes.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                        }
                    }
                    beansAttributes.put(id, attributes);
                    beans.add(bean);
                    ids.add(id);
                    viewStack.push(bean);
                    index++;
                    break;
                }

                case XmlPullParser.END_TAG -> {
                    if (isRootSkipped && !viewStack.isEmpty()) {
                        viewStack.pop();
                    }
                    break;
                }
            }
            parser.next();
        }
        for (ViewBean bean : beans) {
            var attr = beansAttributes.getOrDefault(bean.id, null);
            if (attr != null) {
                new ViewBeanFactory(bean).applyAttributes(attr);
            }
        }
        return beans;
    }

    private String generateUniqueId(Set<String> ids, int type, String className) {
        String prefix = wq.b(type);
        var name = ViewBean.getViewTypeName(type);
        // Skip these types as they're the only ones with a different view type name: VScrollView
        // (ScrollView) and HScrollView (HorizontalScrollView).
        if (type != ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW
                || type != ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW) {
            // If the prefix is "linear" and the name is different from the className,
            // update the prefix to the lowercase version of className.
            if (prefix == "linear"
                    && type == ViewBean.VIEW_TYPE_LAYOUT_LINEAR
                    && !name.equals(className)) {
                prefix = getSnakeCaseId(className);
            }
        }
        int count = ++viewsCount[type];
        String id = prefix + count;

        while (ids.contains(id)) {
            count = ++viewsCount[type];
            id = prefix + count;
        }

        return id;
    }

    private String getSnakeCaseId(String id) {
        StringBuilder snakeCaseId = new StringBuilder();
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    snakeCaseId.append("_");
                }
                snakeCaseId.append(Character.toLowerCase(c));
            } else {
                snakeCaseId.append(c);
            }
        }
        return snakeCaseId.toString();
    }

    private String getNameFromTag(@NonNull String s) {
        try {
            if (s.contains(".")) {
                return s.substring(s.lastIndexOf(".") + 1);
            }
        } catch (Exception e) {
        }
        return s;
    }
}
