package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatButton;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;
import a.a.a.xB;
import pro.sketchware.R;

public class ItemSignInButton extends AppCompatButton implements sy {
    private final Paint paint;
    private final float paddingFactor;
    private final Rect rect;
    private final Context context;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean hasFixed;
    private ButtonSize size;
    private ColorScheme scheme;

    public ItemSignInButton(Context context) {
        super(context);
        this.context = context;
        paddingFactor = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        rect = new Rect();

        setDrawingCacheEnabled(true);
        setFocusable(false);

        setAllCaps(false);
        size = ButtonSize.STANDARD;
        scheme = ColorScheme.LIGHT;
        setSize(size);
        setColorScheme(scheme);
    }

    public void setSize(ButtonSize size) {
        this.size = size;
        setStyle(size, scheme);
    }

    public void setColorScheme(ColorScheme scheme) {
        this.scheme = scheme;
        setStyle(size, scheme);
    }

    private void setStyle(ButtonSize size, ColorScheme scheme) {
        int resSize = size.getText();
        String text = "";
        if (resSize != 0) {
            text = xB.b().a(context, resSize);
        }
        int color = scheme.getTextColor();
        int background = scheme.getBackgroundResource();
        if (size == ButtonSize.ICON_ONLY) {
            background = scheme.getBackgroundResourceIconOnly();
        }
        setText(text);
        setTextColor(color);
        setBackgroundResource(background);
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
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * paddingFactor), (int) (top * paddingFactor), (int) (right * paddingFactor), (int) (bottom * paddingFactor));
    }

    public enum ButtonSize {
        ICON_ONLY(0),
        WIDE(R.string.common_signin_button_text_long),
        STANDARD(R.string.common_signin_button_text);

        private final int text;

        ButtonSize(int resId) {
            text = resId;
        }

        public int getText() {
            return text;
        }
    }

    public enum ColorScheme {
        DARK(0xFFFFFFFF, R.drawable.item_button_signin_dark, R.drawable.item_button_signin_dark_ic_only),
        LIGHT(0x80000000, R.drawable.item_button_signin_light, R.drawable.item_button_signin_light_ic_only);

        private final int textColor;
        private final int backgroundResId;
        private final int backgroundIcOnlyResId;

        ColorScheme(int textColor, int backgroundResId, int backgroundIcOnlyResId) {
            this.textColor = textColor;
            this.backgroundResId = backgroundResId;
            this.backgroundIcOnlyResId = backgroundIcOnlyResId;
        }

        public int getTextColor() {
            return textColor;
        }

        public int getBackgroundResource() {
            return backgroundResId;
        }

        public int getBackgroundResourceIconOnly() {
            return backgroundIcOnlyResId;
        }
    }
}
