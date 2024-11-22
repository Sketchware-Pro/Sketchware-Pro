package pro.sketchware.lib.iconcreator;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import pro.sketchware.R;

public class PatternBackgroundView extends View {

    private Paint paint;
    private BitmapShader bitmapShader;
    private Matrix matrix;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float rotationDegrees = 0.0f;
    private int opacity = 200;
    private Bitmap patternBitmap;


    public PatternBackgroundView(Context context) {
        super(context);
        init(context);
    }

    public PatternBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        patternBitmap = convertVectorToBitmap(context, R.drawable.pattern_seq, 500, 500);

        paint = new Paint();
        paint.setAlpha(opacity);
        bitmapShader = new BitmapShader(patternBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);

        matrix = new Matrix();
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        invalidate();
    }

    public void setRotation(float degrees) {
        this.rotationDegrees = degrees;
        invalidate();
    }

    public void setColor(int color) {
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        invalidate();
    }

    public void setPattern(Bitmap bitmap) {
        this.patternBitmap = bitmap;
        bitmapShader = new BitmapShader(patternBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        paint.setShader(bitmapShader);

        invalidate();
    }

    public void setPatternFromRes(int id) {
        this.patternBitmap = convertVectorToBitmap(getContext(), id, 500, 500);
        setPattern(patternBitmap);
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
        paint.setAlpha(opacity);
        invalidate();
    }

    public Bitmap convertVectorToBitmap(Context context, int drawableId, int width, int height) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof VectorDrawableCompat || drawable instanceof VectorDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException("not VectorDrawable");
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        matrix.setScale(scaleX, scaleY);
        matrix.postRotate(rotationDegrees, getWidth() / 2f, getHeight() / 2f);
        bitmapShader.setLocalMatrix(matrix);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
