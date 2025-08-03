//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.animation.Animator;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;

public class mv extends LinearLayout implements nv {
    public EditText a;

    public mv(FirebaseActivity var1) {
        super(var1);
        this.a((Context)var1);
    }

    public void a() {
        mB.a(((LinearLayout)this).getContext(), this.a);
    }

    public final void a(Context var1) {
        wB.a(var1, this, 2131427544);
        gB.b(this, 600, 200, (Animator.AnimatorListener)null);
        ((TextView)((LinearLayout)this).findViewById(2131232241)).setText(xB.b().a(var1, 2131625239));
        this.a = (EditText)((LinearLayout)this).findViewById(2131231003);
    }

    public void a(ProjectLibraryBean var1) {
        String var2 = this.a.getText().toString().trim();
        if (!var2.isEmpty()) {
            var1.reserved3 = var2;
            if (var2.startsWith("gs://")) {
                var1.reserved3 = var2.replaceFirst("gs://", "");
            }

            if (var2.endsWith("/")) {
                var1.reserved3 = var2.substring(0, var2.lastIndexOf("/"));
            }
        }

    }

    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/firebase-storage.html";
    }

    public boolean isValid() {
        return true;
    }

    public void setData(ProjectLibraryBean var1) {
        String var2 = var1.reserved3;
        if (var2 != null && var2.length() > 0) {
            this.a.setText(var1.reserved3);
        }

    }
}
