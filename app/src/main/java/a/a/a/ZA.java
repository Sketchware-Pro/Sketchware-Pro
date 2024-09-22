package a.a.a;

import android.app.Dialog;
import android.content.Context;

import com.sketchware.remod.R;

public class ZA extends Dialog {
    public ZA(Context context) {
        super(context, R.style.progress);
        setContentView(R.layout.progress);
        super.setCancelable(false);
    }
}
