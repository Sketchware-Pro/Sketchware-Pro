package com.besome.sketch.editor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.core.graphics.ColorUtils;

import com.google.android.material.color.MaterialColors;

import pro.sketchware.R;

/**
 * Container of the View editor's preview that draws a discreet dot grid behind its children.
 * Purely visual: it does not affect layout or touch handling.
 */
public class DotGridFrameLayout extends FrameLayout {
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float gridSpacing;

    public DotGridFrameLayout(Context context) {
        this(context, null);
    }

    public DotGridFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        float density = getResources().getDisplayMetrics().density;
        gridSpacing = 20 * density;
        gridPaint.setColor(ColorUtils.setAlphaComponent(MaterialColors.getColor(this, R.attr.colorOnSurface), 0x26));
        gridPaint.setStrokeWidth(1.5f * density);
        gridPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (float x = gridSpacing / 2; x < getWidth(); x += gridSpacing) {
            for (float y = gridSpacing / 2; y < getHeight(); y += gridSpacing) {
                canvas.drawPoint(x, y, gridPaint);
            }
        }
        super.dispatchDraw(canvas);
    }
}
