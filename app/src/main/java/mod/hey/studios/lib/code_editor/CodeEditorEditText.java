package mod.hey.studios.lib.code_editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * A lightweight Code Editor with syntax highlighting, auto indentation, word wrap and lines.
 *
 * @author Hey! Studios DEV - 28.07.2020
 */

public class CodeEditorEditText extends EditText {

    private static final int HIGHLIGHTER_COLOR = 0xffefefef;
    private static final boolean LINES = true;
    private final boolean lineHighlightEnabled = true;
    private final Context context;
    private Rect lineBounds;
    private Paint highlightPaint;
    private int lineNumber;
    private Rect rect;
    private Paint paint;

    public CodeEditorEditText(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public CodeEditorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public CodeEditorEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initialize();
    }

    private void initialize() {
        //  setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/droid_sans_mono.ttf"), 0);

        lineBounds = new Rect();
        highlightPaint = new Paint();
        highlightPaint.setColor(HIGHLIGHTER_COLOR);


        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);

        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(getRealTextSize() - scaledDensity * 1.2f);

        paint.setTypeface(Typeface.MONOSPACE);
    }

    @Override
    public float getTextSize() {
        if (context == null) {
            return super.getTextSize();
        }

        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return super.getTextSize() / scaledDensity;
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);

        if (context == null || paint == null) {
            return;
        }

        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(getRealTextSize() - scaledDensity * 2);
    }

    public float getRealTextSize() {
        return super.getTextSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (lineHighlightEnabled) {
            lineNumber = getLayout().getLineForOffset(getSelectionStart());
            getLineBounds(lineNumber, lineBounds);

            canvas.drawRect(lineBounds, highlightPaint);
        }

        if (LINES) {

            int baseline;
            int lineCount = getLineCount();
            int lineNumber = 1;

            for (int i = 0; i < lineCount; ++i) {
                baseline = getLineBounds(i, null);
                if (i == 0) {
                    canvas.drawText("" + lineNumber, rect.left + 10, baseline, paint);
                    ++lineNumber;
                } else if (getText().charAt(getLayout().getLineStart(i) - 1) == '\n') {
                    canvas.drawText("" + lineNumber, rect.left + 10, baseline, paint);
                    ++lineNumber;
                }
            }

            if (lineCount < 100) {
                setPadding(80, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            } else if (lineCount < 1000) {
                setPadding(90, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            } else if (lineCount < 10000) {
                setPadding(100, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            } else if (lineCount < 100000) {
                setPadding(110, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
        }

        super.onDraw(canvas);
    }


    public void setLineHighlightColor(int color) {
        highlightPaint.setColor(color);
        if (lineHighlightEnabled) {
            invalidate();
        }
    }

    public void setLineNumbersColor(int color) {
        paint.setColor(color);
        if (LINES) {
            invalidate();
        }
    }
}
