package a.a.a;

import android.view.View;

import com.besome.sketch.editor.manage.font.AddFontCollectionActivity;

public class Nt implements View.OnClickListener {
    public final AddFontCollectionActivity a;

    public Nt(AddFontCollectionActivity addFontCollectionActivity) {
        this.a = addFontCollectionActivity;
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        AddFontCollectionActivity.a(this.a);
    }
}
