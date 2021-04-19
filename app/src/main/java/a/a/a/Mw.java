package a.a.a;

import com.besome.sketch.editor.property.PropertyColorItem;

public class Mw implements Zx.b {

    public final PropertyColorItem a;

    public Mw(PropertyColorItem propertyColorItem) {
        this.a = propertyColorItem;
    }

    @Override // a.a.a.Zx.b
    public void a(int i) {
        this.a.setValue(i);
        if (this.a.k != null) {
            this.a.k.a(this.a.b, Integer.valueOf(this.a.c));
        }
    }
}