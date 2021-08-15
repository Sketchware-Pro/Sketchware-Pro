package a.a.a;

import a.a.a.GC.c;
import a.a.a.GC.d;
import a.a.a.GC.d.a;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.projects.MyProjectButton;
import java.util.HashMap;
import com.sketchware.remod.Resources;

public class IC implements OnClickListener {
    public final d a;
    public final a b;

    public IC(a aVar, d dVar) {
        this.b = aVar;
        this.a = dVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.b.F.c = this.b.j();
            d dVar = this.b.F;
            if (dVar.c <= GC.o(dVar.d).size()) {
                HashMap hashMap = (HashMap) GC.o(this.b.F.d).get(this.b.F.c);
                int i;
                if (view instanceof MyProjectButton) {
                    i = ((MyProjectButton) view).b;
                    if (i == 0) {
                        dVar = this.b.F;
                        GC.a(dVar.d, dVar.c, false);
                        return;
                    } else if (i == 1) {
                        dVar = this.b.F;
                        GC.rp(dVar.d, dVar.c);
                        return;
                    } else if (i == 2) {
                        dVar = this.b.F;
                        GC gc = dVar.d;
                        int i2 = dVar.c;
                        Intent intent = new Intent(gc.getContext(), ExportProjectActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("sc_id", yB.c((HashMap) gc.g.get(i2), "sc_id"));
                        gc.startActivity(intent);
                        return;
                    } else if (i == 3) {
                        hashMap.put("confirmation", Boolean.valueOf(true));
                        this.b.C.b();
                        return;
                    } else if (i == 4) {
                        dVar = this.b.F;
                        GC.p(dVar.d, dVar.c);
                        return;
                    } else {
                        return;
                    }
                }
                i = view.getId();
                if (i == Resources.id.confirm_no) {
                    hashMap.put("confirmation", Boolean.valueOf(false));
                    dVar = this.b.F;
                    dVar.c(dVar.c);
                } else if (i == Resources.id.confirm_yes) {
                    hashMap.put("confirmation", Boolean.valueOf(false));
                    hashMap.put("expand", Boolean.valueOf(false));
                    GC gc2 = this.b.F.d;
                    new c(gc2, gc2.getContext(), this.b.F.c).execute(new Void[0]);
                }
            }
        }
    }
}