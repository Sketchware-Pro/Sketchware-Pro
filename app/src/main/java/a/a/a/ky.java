package a.a.a;

import android.view.View;
import android.widget.AdapterView;
import com.besome.sketch.editor.view.ViewProperties;

public class ky implements AdapterView.OnItemSelectedListener {

    public final ViewProperties a;

    public ky(ViewProperties viewProperties) {
        this.a = viewProperties;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        this.a.c.a(i);
        ViewProperties viewProperties = this.a;
        viewProperties.a( ((String) viewProperties.b.get(i)));
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}