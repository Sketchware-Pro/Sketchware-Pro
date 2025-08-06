package pro.sketchware.utility;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import pro.sketchware.R;

public class UI {
    public static void loadImageFromUrl(ImageView image, String url) {
        Glide.with(image.getContext()).load(url).into(image);
    }

    public static void advancedCorners(View view, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
        gd.setColor(color);
        view.setBackground(gd);
    }

    public static void shadAnim(View view, String propertyName, double value, double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.start();
    }

    public static void animateLayoutChanges(ViewGroup view) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(300);
        TransitionManager.beginDelayedTransition(view, autoTransition);
    }

    public static void rippleRound(View view, int focus, int pressed, double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setCornerRadius((float) round);
        GG.setColor(focus);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{pressed}), GG, null);
        view.setBackground(RE);
    }

    public static void addSystemWindowInsetToPadding(View view, boolean left, boolean top, boolean right, boolean bottom) {
        int initialLeft = view.getPaddingLeft();
        int initialTop = view.getPaddingTop();
        int initialRight = view.getPaddingRight();
        int initialBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            view.setPadding(
                    initialLeft + (left ? insets.left : 0),
                    initialTop + (top ? insets.top : 0),
                    initialRight + (right ? insets.right : 0),
                    initialBottom + (bottom ? insets.bottom : 0)
            );
            return windowInsets;
        });
    }

    public static void addSystemWindowInsetToMargin(View view, boolean left, boolean top, boolean right, boolean bottom) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int initialLeft = params.leftMargin;
        int initialTop = params.topMargin;
        int initialRight = params.rightMargin;
        int initialBottom = params.bottomMargin;

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            params.setMargins(
                    initialLeft + (left ? insets.left : 0),
                    initialTop + (top ? insets.top : 0),
                    initialRight + (right ? insets.right : 0),
                    initialBottom + (bottom ? insets.bottom : 0)
            );
            view.requestLayout();
            return windowInsets;
        });
    }

    @DrawableRes
    public static <T> int getShapedBackgroundForList(List<T> list, int position) {
        if (list.size() == 1) {
            return R.drawable.shape_alone;
        } else if (position == 0) {
            return R.drawable.shape_top;
        } else if (position == list.size() - 1) {
            return R.drawable.shape_bottom;
        } else {
            return R.drawable.shape_middle;
        }
    }

    @SuppressLint("DiscouragedApi, InternalInsetResource")
    public static int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
