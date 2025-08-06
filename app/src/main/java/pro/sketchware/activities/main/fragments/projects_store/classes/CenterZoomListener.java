package pro.sketchware.activities.main.fragments.projects_store.classes;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CenterZoomListener extends RecyclerView.OnScrollListener {

    private static final float SCALE_DOWN_FACTOR = 0.15f;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) return;

        float midpoint = recyclerView.getWidth() / 2f;
        float d0 = 0f;
        float d1 = midpoint;
        float s0 = 1f;
        float s1 = 1f - SCALE_DOWN_FACTOR;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View child = recyclerView.getChildAt(i);
            float childMidpoint = (child.getLeft() + child.getRight()) / 2f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }
}
