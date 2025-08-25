package com.besome.sketch.editor.view;

import com.besome.sketch.beans.ViewBean;

// 'sy' is used in ViewPane items, example ItemLinearLayout
public interface ItemView {
    ViewBean getBean();

    void setBean(ViewBean viewBean);

    boolean getFixed();

    void setFixed(boolean fixed);

    void setSelection(boolean selection);
}
