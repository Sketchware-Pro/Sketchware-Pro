package a.a.a;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.firebase.FirebaseActivity;

public class mv extends LinearLayout implements nv {

    public EditText a;

    public mv(FirebaseActivity firebaseActivity) {
        super(firebaseActivity);
        a(firebaseActivity);
    }

    @Override
    public void a() {
        mB.a(getContext(), a);
    }

    private void a(Context context) {
        wB.a(context, this, 2131427544);
        gB.b(this, 600, 200, null);
        ((TextView) findViewById(2131232241)).setText(xB.b().a(context, 2131625239));
        a = findViewById(2131231003);
    }

    @Override
    public void a(ProjectLibraryBean var1) {
        String var2 = a.getText().toString().trim();
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

    @Override
    public String getDocUrl() {
        return "https://docs.sketchware.io/docs/firebase-storage.html";
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public void setData(ProjectLibraryBean libraryBean) {
        String reserved3 = libraryBean.reserved3;
        if (reserved3 != null && reserved3.length() > 0) {
            a.setText(libraryBean.reserved3);
        }
    }
}
