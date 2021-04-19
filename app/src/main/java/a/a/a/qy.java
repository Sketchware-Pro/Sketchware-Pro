package a.a.a;

import android.view.View;
import android.widget.EditText;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewProperty;
import com.sketchware.remod.R;
import java.util.ArrayList;
import java.util.Iterator;

public class qy implements View.OnClickListener {

    public final NB a;
    public final EditText b;
    public final aB c;
    public final ViewProperty d;

    public qy(ViewProperty viewProperty, NB nb, EditText editText, aB aBVar) {
        this.d = viewProperty;
        this.a = nb;
        this.b = editText;
        this.c = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a() && this.a.b()) {
            String obj = this.b.getText().toString();
            ArrayList<ViewBean> b2 = jC.a(this.d.b).b(this.d.c.getXmlName(), (ViewBean) this.d.e.get(this.d.f.a()));
            Iterator<ViewBean> it = b2.iterator();
            while (it.hasNext()) {
                ViewBean next = it.next();
                String str = next.layout.backgroundResource;
                String str2 = next.image.resName;
                if (str != null && !str.equals("NONE") && jC.d(this.d.b).l(str) && !Op.g().b(str)) {
                    try {
                        Op.g().a(this.d.b, jC.d(this.d.b).g(str));
                    } catch (Exception e) {
                        e.printStackTrace();
                        bB.b(this.d.getContext(), e.getMessage(), 0).show();
                    }
                }
                if (str2 != null && !str2.equals("default_image") && !str2.equals("NONE") && jC.d(this.d.b).l(str2) && !Op.g().b(str2)) {
                    try {
                        Op.g().a(this.d.b, jC.d(this.d.b).g(str2));
                    } catch (Exception e2) {
                        bB.b(this.d.getContext(), e2.getMessage(), 0).show();
                    }
                }
            }
            try {
                Rp.h().a(obj, b2, true);
                if (this.d.o != null) {
                    this.d.o.a();
                }
                bB.a(this.d.getContext(), xB.b().a(this.d.getContext(), R.string.common_message_complete_save), 0).show();
                this.c.dismiss();
            } catch (Exception unused) { // changed to Exception because yy is never thrown.
                bB.a(this.d.getContext(), "duplicated name!!", 0).show();
            }
        }
    }
}