package a.a.a;

import android.view.View;
import com.besome.sketch.editor.makeblock.MakeBlockActivity;

public class Xs implements View.OnClickListener {

    public final MakeBlockActivity a;

    public Xs(MakeBlockActivity makeBlockActivity) {
        this.a = makeBlockActivity;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.a.onBackPressed();
        }
    }
}