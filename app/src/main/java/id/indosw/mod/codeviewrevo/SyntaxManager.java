package id.indosw.mod.codeviewrevo;

import android.content.Context;

public class SyntaxManager {

    public static void applyMonokaiTheme(Context context, CodeViewRevo codeView, Language language) {
        if (language == Language.JAVA) {
            JavaSyntaxManager.applyMonokaiTheme(context, codeView);
        }
    }
}