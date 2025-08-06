package pro.sketchware.activities.main.fragments.projects_store.classes;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public HorizontalItemDecoration(int spacing) {
        this.spacing = spacing / 2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
    }
}
