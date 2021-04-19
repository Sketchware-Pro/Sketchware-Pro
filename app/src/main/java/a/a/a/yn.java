package a.a.a;

import android.view.View;
import com.besome.sketch.MainActivity;

public class yn implements View.OnClickListener {

    public final MainActivity a;

    public yn(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public void onClick(View view) {
        this.a.x.c();
        nd.a(this.a, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 9501);
    }
}