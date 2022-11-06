package mod.agus.jcoderz.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import a.a.a.sy;
import a.a.a.wB;

public class ItemGridView extends GridView implements sy {

    public ViewBean f22a;
    public boolean b;
    public boolean c;
    public Paint d;
    public int e;
    public ArrayList<String> f = new ArrayList<>();
    private Rect rect;

    public ItemGridView(Context context) {
        super(context);
        a(context);
    }

    @Deprecated
    public void a(Context context) {
        e = (int) wB.a(context, 1.0f);
        d = new Paint(Paint.ANTI_ALIAS_FLAG);
        d.setStrokeWidth(wB.a(getContext(), 2.0f));
        rect = new Rect();
        setDrawingCacheEnabled(true);
        setNumColumns(3);
        setColumnWidth(-1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        f.add("Item 1");
        f.add("Item 2");
        f.add("Item 3");
        f.add("Item 4");
        f.add("Item 5");
        f.add("Item 6");
        setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, f));
    }

    @Override
    public ViewBean getBean() {
        return f22a;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        f22a = viewBean;
    }

    @Override
    public boolean getFixed() {
        return c;
    }

    public void setFixed(boolean z) {
        c = z;
    }

    public boolean getSelection() {
        return b;
    }

    @Override
    public void setSelection(boolean z) {
        b = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (b) {
            d.setColor(0x9599d5d0);
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, d);
        } else {
            d.setColor(0x60000000);
            float measuredWidth = getMeasuredWidth();
            float measuredHeight = getMeasuredHeight();
            canvas.drawLine(0.0f, 0.0f, measuredWidth, 0.0f, d);
            canvas.drawLine(0.0f, 0.0f, 0.0f, measuredHeight, d);
            canvas.drawLine(measuredWidth, 0.0f, measuredWidth, measuredHeight, d);
            canvas.drawLine(0.0f, measuredHeight, measuredWidth, measuredHeight, d);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i * e, i2 * e, i3 * e, e * i4);
    }
}
