package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.besome.sketch.ctrls.ViewIdSpinnerItem;
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.Jw;
import a.a.a.wB;
import a.a.a.xB;

public class ViewProperties extends RelativeLayout implements AdapterView.OnItemSelectedListener {

    public Spinner spnWidget;
    public ArrayList<String> viewsIdList = new ArrayList<>();
    public SpinnerItemAdapter spinnerItemAdapter;
    public Jw propertyTargetChangeListener = null;

    public ViewProperties(Context context) {
        super(context);
        initialize(context);
    }

    public ViewProperties(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public void setOnPropertyTargetChangeListener(Jw onPropertyTargetChangeListener) {
        propertyTargetChangeListener = onPropertyTargetChangeListener;
    }

    private void initialize(Context context) {
        wB.a(context, this, Resources.layout.view_properties);
        ((TextView) findViewById(Resources.id.btn_editproperties)).setText(xB.b().a(context, Resources.string.design_button_properties));
        spnWidget = findViewById(Resources.id.spn_widget);
        spinnerItemAdapter = new SpinnerItemAdapter(context, viewsIdList);
        spnWidget.setAdapter(spinnerItemAdapter);
        spnWidget.setSelection(0);
        spnWidget.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerItemAdapter.setLayoutPosition(position);
        if (propertyTargetChangeListener != null) {
            propertyTargetChangeListener.a(viewsIdList.get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    static class SpinnerItemAdapter extends BaseAdapter {

        public Context context;
        public int layoutPosition;
        public ArrayList<String> data;

        public SpinnerItemAdapter(Context context, ArrayList<String> arrayList) {
            this.context = context;
            data = arrayList;
        }

        public void setLayoutPosition(int position) {
            layoutPosition = position;
        }

        @Override
        public int getCount() {
            if (data == null) return 0;
            return data.size();
        }

        @Override
        public View getDropDownView(int position, View view, ViewGroup viewGroup) {
            return createSpinnerItemView(position, view, viewGroup, layoutPosition == position);
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return createSpinnerItemView(position, convertView, parent, false);
        }

        private ViewIdSpinnerItem createSpinnerItemView(int position, View convertView, ViewGroup parent, boolean z) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (convertView != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) convertView;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(context);
                viewIdSpinnerItem.setTextSize(Resources.dimen.text_size_body_small);
            }
            viewIdSpinnerItem.a(0, data.get(position), z);
            viewIdSpinnerItem.a(false, 0xff404040, 0xff404040);
            return viewIdSpinnerItem;
        }
    }
}
