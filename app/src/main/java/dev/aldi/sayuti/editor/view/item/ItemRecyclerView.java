package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import a.a.a.sy;
import a.a.a.wB;

public class ItemRecyclerView extends ListView implements sy {

    public ViewBean f18a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ArrayList<String> f = new ArrayList<>();

    public ItemRecyclerView(Context context) {
        super(context);
        a(context);
    }

    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        this.f.add("RecyclerView item 1");
        this.f.add("RecyclerView item 2");
        this.f.add("RecyclerView item 3");
        setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, this.f));
    }

    public ViewBean getBean() {
        return this.f18a;
    }

    public void setBean(ViewBean viewBean) {
        this.f18a = viewBean;
    }

    public boolean getFixed() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

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

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }
}
