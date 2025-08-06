package com.besome.sketch.lib.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.a.a.YA;
import a.a.a.wB;
import pro.sketchware.R;

public class SelectableButtonBar extends LinearLayout {

    private final List<Integer> keys = new ArrayList<>();
    private final List<String> values = new ArrayList<>();
    private YA selectionListener;
    private int selectedItemIndex;

    public SelectableButtonBar(Context context) {
        super(context);
    }

    public SelectableButtonBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void a() {
        removeAllViews();
        int valuesSize = values.size();

        for (int i = 0; i < valuesSize; ++i) {
            LinearLayout layout = createButton(keys.get(i), values.get(i));
            if (i == 0) {
                layout.setBackgroundResource(R.drawable.selector_btnbar_left);
            } else if (i == valuesSize - 1) {
                layout.setBackgroundResource(R.drawable.selector_btnbar_right);
            } else {
                layout.setBackgroundResource(R.drawable.selector_btnbar_center);
            }

            addView(layout);
        }

        setSelectedItemByIndex(selectedItemIndex);
    }

    public void a(int key, String value) {
        keys.add(key);
        values.add(value);
    }

    private LinearLayout createButton(int key, String value) {
        SelectableButton selectableButton = new SelectableButton(getContext(), key, value);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0F;
        selectableButton.setLayoutParams(layoutParams);
        selectableButton.setOnClickListener(view -> setSelectedItemByKey(selectableButton.key));
        return selectableButton;
    }

    public YA getListener() {
        return selectionListener;
    }

    public void setListener(YA var1) {
        selectionListener = var1;
    }

    public int getSelectedItemKey() {
        return ((SelectableButton) getChildAt(selectedItemIndex)).key;
    }

    public void setSelectedItemByIndex(int selectedIndex) {
        selectedItemIndex = selectedIndex;

        for (int i = 0; i < getChildCount(); ++i) {
            SelectableButton selectableButton = (SelectableButton) getChildAt(i);
            selectableButton.setSelected(false);
            if (i == selectedIndex) {
                selectableButton.setSelected(true);
            }
        }
    }

    public void setSelectedItemByKey(int key) {
        for (int i = 0; i < getChildCount(); ++i) {
            SelectableButton selectableButton = (SelectableButton) getChildAt(i);
            selectableButton.setSelected(false);
            if (selectableButton.key == key) {
                selectableButton.setSelected(true);
                selectedItemIndex = i;
                if (selectionListener != null) {
                    selectionListener.a(key);
                }
            }
        }
    }

    @SuppressLint("ViewConstructor")
    public static class SelectableButton extends LinearLayout {

        private int key;
        private TextView textView;

        public SelectableButton(Context context, int key, String value) {
            super(context);
            initialize(context, key, value);
        }

        private void initialize(Context context, int key, String value) {
            this.key = key;
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER);
            int padding = (int) wB.a(getContext(), 4.0F);
            setPadding(padding, padding, padding, padding);
            textView = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);
            textView.setText(value);
            textView.setTextColor(0xff008dcd);
            textView.setLines(1);
            textView.setTextSize(2, 12.0F);
            addView(textView);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            if (selected) {
                textView.setTextColor(0xffffffff);
            } else {
                textView.setTextColor(0xff008dcd);
            }
        }
    }
}
