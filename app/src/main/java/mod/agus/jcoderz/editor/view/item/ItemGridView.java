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

    private final Paint paint;
    private final int paddingFactor;
    private final Rect rect;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean isFixed;

    public ItemGridView(Context context) {
        super(context);
        paddingFactor = (int) wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(wB.a(getContext(), 2.0f));
        rect = new Rect();
        setDrawingCacheEnabled(true);
        setNumColumns(3);
        setColumnWidth(-1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        ArrayList<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        list.add("Item 6");
        setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list));
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
        return isFixed;
    }

    public void setFixed(boolean z) {
        isFixed = z;
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
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        } else {
            paint.setColor(0x60000000);
            float measuredWidth = getMeasuredWidth();
            float measuredHeight = getMeasuredHeight();
            canvas.drawLine(0.0f, 0.0f, measuredWidth, 0.0f, paint);
            canvas.drawLine(0.0f, 0.0f, 0.0f, measuredHeight, paint);
            canvas.drawLine(measuredWidth, 0.0f, measuredWidth, measuredHeight, paint);
            canvas.drawLine(0.0f, measuredHeight, measuredWidth, measuredHeight, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left * paddingFactor, top * paddingFactor, right * paddingFactor, paddingFactor * bottom);
    }
}
