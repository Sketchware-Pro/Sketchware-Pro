package dev.aldi.sayuti.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import a.a.a.sy;
import a.a.a.wB;

public class ItemRecyclerView extends ListView implements sy {

    private final Paint paint;
    private final float e;
    private boolean hasSelection;
    private boolean hasFixed;
    private ViewBean viewBean;

    public ItemRecyclerView(Context context) {
        super(context);
        e = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("RecyclerView item 1");
        itemList.add("RecyclerView item 2");
        itemList.add("RecyclerView item 3");
        setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, itemList));
    }

    @Override
    public ViewBean getBean() {
        return viewBean;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public boolean getFixed() {
        return hasFixed;
    }

    public void setFixed(boolean z) {
        hasFixed = z;
    }

    public boolean getSelection() {
        return hasSelection;
    }

    @Override
    public void setSelection(boolean z) {
        hasSelection = z;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (hasSelection) {
            paint.setColor(0x9599d5d0);
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), paint);
        } else {
            paint.setColor(0x60000000);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f2 = (float) measuredWidth;
            canvas.drawLine(0.0f, 0.0f, f2, 0.0f, paint);
            float f3 = (float) measuredHeight;
            canvas.drawLine(0.0f, 0.0f, 0.0f, f3, paint);
            canvas.drawLine(f2, 0.0f, f2, f3, paint);
            canvas.drawLine(0.0f, f3, f2, f3, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        float f2 = e;
        super.setPadding(
                (int) (((float) left) * f2),
                (int) (((float) top) * f2),
                (int) (((float) right) * f2),
                (int) (((float) bottom) * f2)
        );
    }
}
