package mod.jbk.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/**
 * A {@link OnApplyWindowInsetsListener} that instead of replacing the view's margins, adds the
 * insets' values to the view's initial margins.
 */
public class AddMarginOnApplyWindowInsetsListener implements OnApplyWindowInsetsListener {
    private final int insetsTypeMask;
    private final WindowInsetsCompat returnValue;

    /**
     * @param insetsTypeMask Bit mask of {@link WindowInsetsCompat.Type}s to query the insets for.
     * @param returnValue    What {@link #onApplyWindowInsets(View, WindowInsetsCompat)} should return.
     */
    public AddMarginOnApplyWindowInsetsListener(int insetsTypeMask, WindowInsetsCompat returnValue) {
        this.insetsTypeMask = insetsTypeMask;
        this.returnValue = returnValue;
    }

    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
        var toApply = insets.getInsets(insetsTypeMask);
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams layoutParams) {
            layoutParams.leftMargin += toApply.left;
            layoutParams.topMargin += toApply.top;
            layoutParams.rightMargin += toApply.right;
            layoutParams.bottomMargin += toApply.bottom;
        } else {
            throw new IllegalArgumentException("View's layout params must extend ViewGroup.MarginLayoutParams");
        }
        return returnValue;
    }
}
