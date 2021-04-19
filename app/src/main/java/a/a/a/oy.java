package a.a.a;

import android.view.View;
import android.widget.AdapterView;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewProperty;

public class oy implements AdapterView.OnItemSelectedListener {

    public final ViewProperty a;

    public oy(ViewProperty viewProperty) {
        this.a = viewProperty;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.a.f.a(i);
        ViewProperty viewProperty = this.a;
        viewProperty.b( ((ViewBean) viewProperty.e.get(i)));
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}