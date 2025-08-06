package pro.sketchware.utility;

import android.util.Pair;

import com.besome.sketch.beans.ViewBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mod.jbk.util.LogUtil;

public class InjectAttributeHandler {

    private final ViewBean viewBean;

    public InjectAttributeHandler(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    public ViewBean getBean() {
        return viewBean;
    }

    public String getAttributeValueOf(String name) {
        return getAttributeByName(name).orElse("");
    }

    public boolean contains(String name) {
        return getAttributeByName(name).isPresent();
    }

    private Optional<String> getAttributeByName(String name) {
        for (Pair<String, String> pair : getAttributes()) {
            if (pair.first.equals(name)) {
                return Optional.of(pair.second);
            }
        }
        return Optional.empty();
    }

    public Set<Pair<String, String>> getAttributes() {
        Set<Pair<String, String>> attributePairs = new HashSet<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(
                    "<tag xmlns:android=\"http://schemas.android.com/apk/res/android\" " +
                            "xmlns:app=\"http://schemas.android.com/apk/res-auto\" " +
                            "xmlns:tools=\"http://schemas.android.com/tools\"" +
                            viewBean.inject + "></tag>"));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        String name = parser.getAttributeName(i);
                        String value = parser.getAttributeValue(i);
                        attributePairs.add(new Pair<>(name, value));
                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException | RuntimeException e) {
            LogUtil.e("InjectAttributeHandler", "Failed to parse inject property of View "
                    + viewBean.id, e);
        }

        return attributePairs;
    }
}