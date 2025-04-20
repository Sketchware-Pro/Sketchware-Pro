package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import a.a.a.sy;
import a.a.a.wB;
import pro.sketchware.R;

public class ItemBottomNavigationView extends BottomNavigationView implements sy {

    private final Paint paint;
    private final Rect rect;
    private final float paddingFactor;
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean hasFixed;

    public ItemBottomNavigationView(Context context) {
        super(context);
        paddingFactor = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        rect = new Rect();

        setDrawingCacheEnabled(true);
        setFocusable(false);
        setClickable(false);
        var menu = getMenu();
        menu.add(Menu.NONE, 1, Menu.NONE, "Home")
                .setIcon(R.drawable.ic_mtrl_home);
        menu.add(Menu.NONE, 2, Menu.NONE, "Chat")
                .setIcon(R.drawable.ic_mtrl_chat);
        menu.add(Menu.NONE, 3, Menu.NONE, "Profile")
                .setIcon(R.drawable.ic_mtrl_profile);
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
    public void onDraw(@NonNull Canvas canvas) {
        if (hasSelection) {
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * paddingFactor),
                (int) (top * paddingFactor),
                (int) (right * paddingFactor),
                (int) (bottom * paddingFactor));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }
}
