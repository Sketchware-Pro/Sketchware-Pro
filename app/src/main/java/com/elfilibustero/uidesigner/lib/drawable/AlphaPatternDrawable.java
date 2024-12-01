package com.elfilibustero.uidesigner.lib.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlphaPatternDrawable extends Drawable {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int size;
    private final int colorOdd;
    private final int colorEven;

    public static Builder builder() {
        return new Builder();
    }

    public static AlphaPatternDrawable create(int imageViewSizeDp) {
        return new AlphaPatternDrawable(builder(), imageViewSizeDp);
    }

    private AlphaPatternDrawable(Builder builder, int imageViewSizeDp) {
        this.size = Math.max(imageViewSizeDp / 4, 1); // Scale size according to 30dp, here using a 4x4 grid
        this.colorOdd = builder.colorOdd;
        this.colorEven = builder.colorEven;
        configurePaint();
    }

    private void configurePaint() {
        if (size <= 0) return;

        Bitmap bitmap = Bitmap.createBitmap(size * 2, size * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, size, size);

        bitmapPaint.setColor(colorOdd);
        canvas.drawRect(rect, bitmapPaint);

        rect.offset(size, 0);
        bitmapPaint.setColor(colorEven);
        canvas.drawRect(rect, bitmapPaint);

        rect.offset(-size, size);
        bitmapPaint.setColor(colorEven);
        canvas.drawRect(rect, bitmapPaint);

        rect.offset(size, 0);
        bitmapPaint.setColor(colorOdd);
        canvas.drawRect(rect, bitmapPaint);

        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.REPEAT, BitmapShader.TileMode.REPEAT));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.drawRect(bounds, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha() == 255 ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
    }

    public static final class Builder {

        private int size = 40;
        private int colorOdd = 0xFFbdbdbd;
        private int colorEven = 0xFF9e9e9e;

        public Builder size(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("Size must be greater than 0.");
            }
            this.size = size;
            return this;
        }

        public Builder colorOdd(int color) {
            this.colorOdd = color;
            return this;
        }

        public Builder colorEven(int color) {
            this.colorEven = color;
            return this;
        }

        public AlphaPatternDrawable build(int imageViewSizeDp) {
            return new AlphaPatternDrawable(this, imageViewSizeDp);
        }
    }
}