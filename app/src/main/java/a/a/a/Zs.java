package a.a.a;

import android.view.View;
import com.besome.sketch.editor.makeblock.MakeBlockActivity;

public class Zs implements View.OnClickListener {

    public final aB a;
    public final MakeBlockActivity b;

    public Zs(MakeBlockActivity makeBlockActivity, aB aBVar) {
        this.b = makeBlockActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}