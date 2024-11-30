package pro.sketchware.widgets;

import static com.besome.sketch.beans.ViewBean.getViewTypeResId;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import java.util.HashMap;
import java.util.Objects;

public class IconCustomWidget extends IconBase {

    private final HashMap<String, Object> widgetMap = new HashMap<>();
    private int type;
    private String Title;

    public IconCustomWidget(Context context) {
        super(context);
    }

    public IconCustomWidget(HashMap<String, Object> map, Context context) {
        super(context);
        widgetMap.putAll(map);
        type = (int) widgetMap.get("type");
        Title = Objects.requireNonNull(widgetMap.get("title")).toString();
        setWidgetImage(getViewTypeResId(type));
        setWidgetName(Objects.requireNonNull(widgetMap.get("title")).toString());
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = type;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = Objects.requireNonNull(widgetMap.get("name")).toString();
        switch (viewBean.type) {
            case 0, 1, 2:
                layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewBean.layout.orientation = VERTICAL;
                break;
            case 3, 13, 4, 11, 19, 41:
                viewBean.text.text = Title;
                break;
            case 5:
                viewBean.text.hint = Title;
                break;
            case 6:
                viewBean.image.resName = "";
                break;
            case 7, 10, 12, 14, 36, 39:
                viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
            case 8:
                viewBean.text.text = Title;
                layoutBean.width = -1;
                break;
        }
        viewBean.inject = Objects.requireNonNull(widgetMap.get("inject")).toString();
        viewBean.isCustomWidget = true;
        return viewBean;
    }

}