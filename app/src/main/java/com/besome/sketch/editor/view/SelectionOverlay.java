package com.besome.sketch.editor.view;

import android.graphics.Canvas;
import android.graphics.Paint;

/** Accent fill plus outline drawn over the selected widget in the view preview. */
public final class SelectionOverlay {
    private static final Paint FILL = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint STROKE = new Paint(Paint.ANTI_ALIAS_FLAG);

    static {
        FILL.setColor(0x33ff6b5b);
        STROKE.setColor(0xffff6b5b);
        STROKE.setStyle(Paint.Style.STROKE);
        STROKE.setStrokeWidth(3f);
    }

    private SelectionOverlay() {
    }

    public static void draw(Canvas canvas, float left, float top, float right, float bottom, Paint ignored) {
        canvas.drawRect(left, top, right, bottom, FILL);
        float inset = STROKE.getStrokeWidth() / 2f;
        canvas.drawRect(left + inset, top + inset, right - inset, bottom - inset, STROKE);
    }
}
