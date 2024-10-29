package mod.hey.studios.editor.view;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewEditor;

public class IdGenerator {

    public static String getId(ViewEditor viewEditor, int i, ViewBean viewBean) {
        String lastPath = getLastPath(viewBean.convert);
        switch (lastPath) {
            case "CardView":
                i = 36;
                break;

            case "CollapsingToolbarLayout":
                i = 37;
                break;

            case "TextInputLayout":
                i = 38;
                break;

            case "SwipeRefreshLayout":
                i = 39;
                break;

            case "RadioGroup":
                i = 40;
                break;

            case "CircleImageView":
                i = 43;
                break;
        }

        return viewEditor.a(i, viewBean.convert);
    }

    public static String getLastPath(String str) {
        if (!str.contains(".")) {
            return str;
        }

        String[] split = str.split("\\.");
        return split[split.length - 1];
    }
}
