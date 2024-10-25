package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import a.a.a.sy;

public class ItemFloatingActionButton extends FloatingActionButton implements sy {

    private int maincolor;
    private ViewBean viewBean;
    private boolean isSelected;
    private boolean isFixed;

    public ItemFloatingActionButton(Context context) {
        super(context);
        setCompatElevation(0.0F);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return viewBean;
    }

    @Override
    public boolean getFixed() {
        return isFixed;
    }

    public boolean getSelection() {
        return isSelected;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isSelected) {
            setBackgroundTintList(ColorStateList.valueOf(0x9599d5d0));
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.color_accent));
            if (maincolor != 0) {
                colorStateList = ColorStateList.valueOf(maincolor);
            }

            setBackgroundTintList(colorStateList);
        }

        super.onDraw(canvas);
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public void setMainColor(int color) {
        maincolor = color;
    }

    @Override
    public void setSelection(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
