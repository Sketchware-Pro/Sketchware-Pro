package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconLottieAnimation extends IconBase {
    public IconLottieAnimation(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconLottieAnimation.super.a(context);
        setWidgetImage(2131166355);
        setWidgetName("Lottie");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 44;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -2;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "com.airbnb.lottie.LottieAnimationView";
        viewBean.inject = "android:scaleType=\"fitCenter\"\nandroid:layout_centerInParent=\"true\"\napp:lottie_fileName=\"your_file.json\"\napp:lottie_loop=\"true\" \napp:lottie_autoPlay=\"true\"";
        return viewBean;
    }
}
