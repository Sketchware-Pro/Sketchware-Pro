package a.a.a;

import android.view.View;
import com.besome.sketch.MainActivity;

public class xn implements View.OnClickListener {

    public final aB a;
    public final MainActivity b;

    public xn(MainActivity mainActivity, aB aBVar) {
        this.b = mainActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
        this.b.s();
    }
}