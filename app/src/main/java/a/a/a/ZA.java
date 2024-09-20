package a.a.a;

import android.app.Dialog;
import android.content.Context;
//import android.widget.TextView;

//import com.airbnb.lottie.LottieAnimationView;
import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class ZA extends Dialog {

   

    public ZA(Context context) {
        super(context, R.style.progress);
        setContentView(R.layout.progress);
        super.setCancelable(false);
    }

    public void cancelAnimation() {
        
    }

    public void pauseAnimation() {
        
    }

    public void resumeAnimation() {
        
    }

}
