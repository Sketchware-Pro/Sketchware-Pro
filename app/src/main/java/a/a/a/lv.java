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

public class lv extends LinearLayout implements nv {
    public EditText a;
    public EditText b;
    public EditText c;

    public lv(FirebaseActivity var1) {
        super(var1);
        this.a((Context)var1);
    }

    public void a() {
    }

    public final void a(Context var1) {
        wB.a(var1, this, 2131427542);
        gB.b(this, 600, 200, (Animator.AnimatorListener)null);
        ((TextView)((LinearLayout)this).findViewById(2131232232)).setText(xB.b().a(var1, 2131625238));
        ((TextView)((LinearLayout)this).findViewById(2131232199)).setText(xB.b().a(var1, 2131625232));
        ((TextView)((LinearLayout)this).findViewById(2131232197)).setText(xB.b().a(var1, 2131625231));
        this.a = (EditText)((LinearLayout)this).findViewById(2131230999);
        this.b = (EditText)((LinearLayout)this).findViewById(2131230992);
        this.c = (EditText)((LinearLayout)this).findViewById(2131230991);
    }

    public void a(ProjectLibraryBean var1) {
        var1.data = this.a.getText().toString().trim();
        var1.reserved1 = this.b.getText().toString().trim();
        var1.reserved2 = this.c.getText().toString().trim();
    }

    public void b() {
        bB.a(((LinearLayout)this).getContext(), xB.b().a(((LinearLayout)this).getContext(), 2131625224), 1).show();
    }

    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/firebase-project-settings.html";
    }

    public boolean isValid() {
        if (this.a.getText().toString().trim().length() <= 0) {
            this.a.requestFocus();
            this.b();
            return false;
        } else if (this.b.getText().toString().trim().length() <= 0) {
            this.b.requestFocus();
            this.b();
            return false;
        } else if (this.c.getText().toString().trim().length() <= 0) {
            this.c.requestFocus();
            this.b();
            return false;
        } else {
            return true;
        }
    }

    public void setData(ProjectLibraryBean var1) {
        String var2 = var1.data;
        if (var2 != null && var2.length() > 0) {
            this.a.setText(var1.data);
        }

        var2 = var1.reserved1;
        if (var2 != null && var2.length() > 0) {
            this.b.setText(var1.reserved1);
        }

        var2 = var1.reserved2;
        if (var2 != null && var2.length() > 0) {
            this.c.setText(var1.reserved2);
        }

    }
}
