//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import mod.agus.jcoderz.manage.library.ExtLibSelection;

public class Jp {

    /** The library's name inside libs.zip or dexs.zip, like material-1.0.0 */
    public String a;
    /** The library's base package name, like com.google.android.material */
    public String b = "";
    /** Unknown */
    public boolean c;
    /** Unknown, only been {@code false} when enabling AppCompat in Library Manger only */
    public boolean d;

    public Jp(String str) {
        a = str;
        c = qq.c(str);
        d = ExtLibSelection.d(str);
        if (c || d) {
            b = qq.b(str);
        }
    }

    /**
     * @return The library's name inside libs.zip or dexs.zip, like material-1.0.0
     */
    public String a() {
        return a;
    }

    public String b() {
        return b;
    }

    public boolean c() {
        return c;
    }

    public boolean d() {
        return d;
    }
}
