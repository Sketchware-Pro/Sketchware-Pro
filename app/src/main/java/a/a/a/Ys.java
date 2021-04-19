package a.a.a;

import android.view.View;
import com.besome.sketch.editor.makeblock.MakeBlockActivity;

public class Ys implements View.OnClickListener {

    public final aB a;
    public final MakeBlockActivity b;

    public Ys(MakeBlockActivity makeBlockActivity, aB aBVar) {
        this.b = makeBlockActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.a.dismiss();
            this.b.finish();
        }
    }
}