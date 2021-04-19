package a.a.a;

import android.view.View;
import com.besome.sketch.MainActivity;

public class wn implements View.OnClickListener {

    public final MainActivity a;

    public wn(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        this.a.invalidateOptionsMenu();
    }
}