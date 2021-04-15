package mod.hey.studios.editor.view;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;

public class IdGenerator {
    public static String getId(ViewEditor viewEditor, int i, ViewBean viewBean) {
        String lastPath = getLastPath(viewBean.convert);
        if (lastPath.equals("CardView")) {
            i = 36;
        } else if (lastPath.equals("CollapsingToolbarLayout")) {
            i = 37;
        } else if (lastPath.equals("TextInputLayout")) {
            i = 38;
        } else if (lastPath.equals("SwipeRefreshLayout")) {
            i = 39;
        } else if (lastPath.equals("RadioGroup")) {
            i = 40;
        } else if (lastPath.equals("CircleImageView")) {
            i = 43;
        }
        return viewEditor.a(i);
    }

    public static String getLastPath(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }
}
