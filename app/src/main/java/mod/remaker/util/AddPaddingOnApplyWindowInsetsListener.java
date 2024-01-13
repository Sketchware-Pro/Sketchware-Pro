package mod.remaker.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/**
 * A {@link OnApplyWindowInsetsListener} that instead of replacing the view's paddings, adds the
 * insets' values to the view's initial paddings.
 */
public class AddPaddingOnApplyWindowInsetsListener implements OnApplyWindowInsetsListener {
    private final int insetsTypeMask;

    /**
     * @param insetsTypeMask Bit mask of {@link WindowInsetsCompat.Type}s to query the insets for.
     */
    public AddPaddingOnApplyWindowInsetsListener(int insetsTypeMask) {
        this.insetsTypeMask = insetsTypeMask;
    }

    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
        var toApply = insets.getInsets(insetsTypeMask);
        int paddingLeft = v.getPaddingLeft() + toApply.left;
        int paddingTop = v.getPaddingTop() + toApply.top;
        int paddingRight = v.getPaddingRight() + toApply.right;
        int paddingBottom = v.getPaddingBottom() + toApply.bottom;

        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        return insets;
    }
}
