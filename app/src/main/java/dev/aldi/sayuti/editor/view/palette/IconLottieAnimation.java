package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconLottieAnimation extends IconBase {

    public IconLottieAnimation(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_lottie);
        setWidgetName("Lottie");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 44;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = VERTICAL;
        layoutBean.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "com.airbnb.lottie.LottieAnimationView";
        viewBean.inject = "android:scaleType=\"fitCenter\"\nandroid:layout_centerInParent=\"true\"\napp:lottie_fileName=\"your_file.json\"\napp:lottie_loop=\"true\" \napp:lottie_autoPlay=\"true\"";
        return viewBean;
    }
}
