package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import a.a.a.sy;
import a.a.a.wB;

public class ItemGridView extends GridView implements sy {


    public ViewBean f22a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ArrayList<String> f = new ArrayList<>();

    public ItemGridView(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        setNumColumns(3);
        setColumnWidth(-1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        this.f.add("Item 1");
        this.f.add("Item 2");
        this.f.add("Item 3");
        this.f.add("Item 4");
        this.f.add("Item 5");
        this.f.add("Item 6");
        setAdapter(new ArrayAdapter<>(context, 17367043, this.f));
    }

    @Override
    public ViewBean getBean() {
        return this.f22a;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.f22a = viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public boolean getSelection() {
        return this.b;
    }

    @Override
    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (this.b) {
            this.d.setColor(-1785080368);
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        } else {
            this.d.setColor(1610612736);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f2 = (float) measuredWidth;
            canvas.drawLine(0.0f, 0.0f, f2, 0.0f, this.d);
            float f3 = (float) measuredHeight;
            canvas.drawLine(0.0f, 0.0f, 0.0f, f3, this.d);
            canvas.drawLine(f2, 0.0f, f2, f3, this.d);
            canvas.drawLine(0.0f, f3, f2, f3, this.d);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (f2 * ((float) i4)));
    }
}
