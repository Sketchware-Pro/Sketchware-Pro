package mod.agus.jcoderz.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class HighlighterEditText extends EditText {
    private List<ColorScheme> highlightList = new ArrayList();
    private boolean linesEnabled = true;
    private Paint paint;
    private Rect rect;

    public HighlighterEditText(Context context) {
        super(context);
        initialize();
    }

    public HighlighterEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    private void initialize() {
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-16777216);
        paint.setTextSize(14.0f);

        setHorizontallyScrolling(true);
        setMovementMethod(new ScrollingMovementMethod());
    }

    public HighlighterEditText addColorScheme(ColorScheme colorScheme) {
        highlightList.add(colorScheme);
        return this;
    }

    public HighlighterEditText setColorSchemes(List<ColorScheme> list) {
        highlightList = list;
        return this;
    }

    public void highlight() {
        addTextChangedListener(new TextWatcher() {

            final ColorScheme[] schemes = HighlighterEditText.this.highlightList.toArray(new ColorScheme[0]);

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) return;

                removeSpans(editable, ForegroundColorSpan.class);
                ColorScheme[] colorSchemeArr = schemes;
                for (ColorScheme colorScheme : colorSchemeArr) {
                    Matcher matcher = colorScheme.pattern.matcher(editable);
                    while (matcher.find()) {
                        editable.setSpan(new ForegroundColorSpan(colorScheme.color), matcher.start(), matcher.end(), 33);
                    }
                }
            }


            public void removeSpans(Editable editable, Class<? extends CharacterStyle> cls) {
                for (CharacterStyle characterStyle : (CharacterStyle[]) editable.getSpans(0, editable.length(), cls)) {
                    editable.removeSpan(characterStyle);
                }
            }
        });
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (linesEnabled) {
            int baseline = getBaseline();
            for (int i = 0; i < getLineCount(); i++) {
                canvas.drawText(String.valueOf(i + 1), rect.left, baseline, paint);
                baseline += getLineHeight();
            }
        }
    }

    public boolean isLinesEnabled() {
        return linesEnabled;
    }

    public void setLinesEnabled(boolean z) {
        linesEnabled = z;
    }

    public int getLinesColor() {
        return paint.getColor();
    }

    public void setLinesColor(int i) {
        paint.setColor(i);
    }
}
