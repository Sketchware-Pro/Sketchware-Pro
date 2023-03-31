package mod.tsd.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;

public class UiHelper {
    
    public static void setImageViewTint(ImageView imageView, int color) {
        Context context = imageView.getContext();
        imageView.setColorFilter(context.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
    }
    
}
