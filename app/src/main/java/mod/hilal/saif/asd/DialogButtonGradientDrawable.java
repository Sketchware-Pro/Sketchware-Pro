package mod.hilal.saif.asd;

import android.graphics.drawable.GradientDrawable;

public class DialogButtonGradientDrawable extends GradientDrawable {

    public GradientDrawable getIns(float cornerRadius, int strokeWidth, int strokeColor, int color) {
        setCornerRadius(cornerRadius);
        setStroke(strokeWidth, strokeColor);
        setColor(color);
        return this;
    }
}
