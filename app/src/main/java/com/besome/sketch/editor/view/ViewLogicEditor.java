package com.besome.sketch.editor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.besome.sketch.editor.logic.BlockPane;
import com.google.android.material.color.MaterialColors;

import androidx.core.graphics.ColorUtils;

import pro.sketchware.R;

public class ViewLogicEditor extends LogicEditorScrollView {
    private final BlockPane blockPane;
    private final int[] posArea = new int[2];
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float gridSpacing;
    private boolean isFirst = true;

    public ViewLogicEditor(Context context) {
        this(context, null);
    }

    public ViewLogicEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewLogicEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ViewLogicEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        blockPane = new BlockPane(context);
        blockPane.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(blockPane);

        float density = getResources().getDisplayMetrics().density;
        gridSpacing = 20 * density;
        gridPaint.setColor(ColorUtils.setAlphaComponent(MaterialColors.getColor(this, R.attr.colorOnSurface), 0x26));
        gridPaint.setStrokeWidth(1.5f * density);
        gridPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * Draws a discreet dot grid behind the blocks. The canvas is already translated by the
     * scroll offset here, so the dots move together with the content. Purely visual.
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        float startX = (float) Math.floor(getScrollX() / gridSpacing) * gridSpacing;
        float startY = (float) Math.floor(getScrollY() / gridSpacing) * gridSpacing;
        float endX = getScrollX() + getWidth();
        float endY = getScrollY() + getHeight();
        for (float x = startX; x <= endX; x += gridSpacing) {
            for (float y = startY; y <= endY; y += gridSpacing) {
                canvas.drawPoint(x, y, gridPaint);
            }
        }
        super.dispatchDraw(canvas);
    }

    public BlockPane getBlockPane() {
        return blockPane;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isFirst) {
            blockPane.getLayoutParams().width = right - left;
            blockPane.getLayoutParams().height = bottom - top;
            blockPane.b();
            isFirst = false;
        }
    }

    public boolean hitTest(float x, float y) {
        getLocationOnScreen(posArea);
        if (!(x > posArea[0])) return false;
        if (!(x < posArea[0] + getWidth())) return false;
        if (!(y > posArea[1])) return false;
        if (!(y < posArea[1] + getHeight())) return false;
        return true;
    }
}
