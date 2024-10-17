package mod.hasrat.lib;

import android.os.SystemClock;
import android.view.View;

public abstract class DebouncedClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 100L;
    private long lastClickTime = 0;

    @Override
    public final void onClick(View v) {
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - lastClickTime > MIN_CLICK_INTERVAL) {
            lastClickTime = currentTime;
            onDebouncedClick(v);
        }
    }

    protected abstract void onDebouncedClick(View v);

}
