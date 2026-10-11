package com.besome.sketch.editor.view;

import android.view.View;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;

// 'sy' is used in ViewPane items, example ItemLinearLayout
public interface ItemView {
    ViewBean getBean();

    void setBean(ViewBean viewBean);

    boolean getFixed();

    void setFixed(boolean fixed);

    void setSelection(boolean selection);

    /**
     * Shared addView logic for all Item* container classes.
     * Handles GONE view index shifting and prevents IndexOutOfBoundsException.
     *
     * @param parent    the ViewGroup (this)
     * @param child     the child view to add
     * @param index     the requested insertion index
     * @param superAdd  delegate that calls super.addView(child, index) on the concrete class
     */
    static void addViewWithGoneOffset(ViewGroup parent, View child, int index, SuperAddView superAdd) {
        int childCount = parent.getChildCount();
        if (index < 0 || index >= childCount) {
            superAdd.addView(child, -1);
            return;
        }

        int goneIndex = -1;
        for (int i = 0; i < childCount; i++) {
            if (parent.getChildAt(i).getVisibility() == View.GONE) {
                goneIndex = i;
                break;
            }
        }

        if (goneIndex >= 0 && index >= goneIndex) {
            int targetIndex = index + 1;
            if (targetIndex >= childCount) {
                superAdd.addView(child, -1);
            } else {
                superAdd.addView(child, targetIndex);
            }
        } else {
            superAdd.addView(child, index);
        }
    }

    @FunctionalInterface
    interface SuperAddView {
        void addView(View child, int index);
    }
}
