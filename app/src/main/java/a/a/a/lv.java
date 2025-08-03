//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

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
        a(var1);
    }

    @Override
    public void a() {
    }

    private void a(Context var1) {
        wB.a(var1, this, 2131427542);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(2131232232)).setText(xB.b().a(var1, 2131625238));
        ((TextView) findViewById(2131232199)).setText(xB.b().a(var1, 2131625232));
        ((TextView) findViewById(2131232197)).setText(xB.b().a(var1, 2131625231));
        a = findViewById(2131230999);
        b = findViewById(2131230992);
        c = findViewById(2131230991);
    }

    @Override
    public void a(ProjectLibraryBean libraryBean) {
        libraryBean.data = a.getText().toString().trim();
        libraryBean.reserved1 = b.getText().toString().trim();
        libraryBean.reserved2 = c.getText().toString().trim();
    }

    public void b() {
        bB.a(getContext(), xB.b().a(getContext(), 2131625224), 1).show();
    }

    @Override
    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/firebase-project-settings.html";
    }

    @Override
    public boolean isValid() {
        if (a.getText().toString().trim().length() == 0) {
            a.requestFocus();
            b();
            return false;
        } else if (b.getText().toString().trim().length() == 0) {
            b.requestFocus();
            b();
            return false;
        } else if (c.getText().toString().trim().length() == 0) {
            c.requestFocus();
            b();
            return false;
        } else {
            return true;
        }
    }

    public void setData(ProjectLibraryBean libraryBean) {
        String data = libraryBean.data;
        if (data != null && data.length() > 0) {
            a.setText(data);
        }

        String reserved1 = libraryBean.reserved1;
        if (reserved1 != null && reserved1.length() > 0) {
            b.setText(reserved1);
        }

        String reserved2 = libraryBean.reserved2;
        if (reserved2 != null && reserved2.length() > 0) {
            c.setText(reserved2);
        }

    }
}
