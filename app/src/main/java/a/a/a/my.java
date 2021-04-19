package a.a.a;

import com.besome.sketch.editor.view.ViewProperty;
import com.besome.sketch.lib.ui.CustomHorizontalScrollView;

public class my implements CustomHorizontalScrollView.a {

    public final ViewProperty a;

    public my(ViewProperty viewProperty) {
        this.a = viewProperty;
    }

    @Override // com.besome.sketch.lib.ui.CustomHorizontalScrollView.a
    public void a(int i, int i2, int i3, int i4) {
        if (Math.abs(i - i3) <= 5) {
            return;
        }
        if (i > i3) {
            if (this.a.v) {
                this.a.v = false;
                this.a.b();
                this.a.u.start();
            }
        } else if (!(this.a.v)) {
            this.a.v = true;
            this.a.b();
            this.a.t.start();
        }
    }
}