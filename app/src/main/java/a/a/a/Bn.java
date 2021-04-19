package a.a.a;

import android.view.View;
import com.besome.sketch.MainActivity;

public class Bn implements View.OnClickListener {

    public final aB a;
    public final MainActivity b;

    public Bn(MainActivity mainActivity, aB aBVar) {
        this.b = mainActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}