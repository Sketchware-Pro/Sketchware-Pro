package a.a.a;

import android.view.View;
import com.besome.sketch.editor.manage.ShowWidgetCollectionActivity;

public class Ft implements View.OnClickListener {

    public final ShowWidgetCollectionActivity a;

    public Ft(ShowWidgetCollectionActivity showWidgetCollectionActivity) {
        this.a = showWidgetCollectionActivity;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.a.onBackPressed();
        }
    }
}