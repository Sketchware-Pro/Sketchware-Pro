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

public class ViewProperties extends RelativeLayout {

    public Spinner a;
    public ArrayList<String> b = new ArrayList<>();
    public a c;
    public Jw d = null;

    public ViewProperties(Context context) {
        super(context);
        a(context);
    }

    public ViewProperties(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public void setOnPropertyTargetChangeListener(Jw jw) {
        d = jw;
    }

    public final void a(Context context) {
        wB.a(context, this, Resources.layout.view_properties);
        ((TextView) findViewById(Resources.id.btn_editproperties)).setText(xB.b().a(context, Resources.string.design_button_properties));
        a = findViewById(Resources.id.spn_widget);
        c = new a(context, b);
        a.setAdapter(c);
        a.setSelection(0);
        a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                c.a(position);
                a(b.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public final void a(String str) {
        if (d != null) {
            d.a(str);
        }
    }

    static class a extends BaseAdapter {

        public Context a;
        public int b;
        public ArrayList<String> c;

        public a(Context context, ArrayList<String> arrayList) {
            a = context;
            c = arrayList;
        }

        public void a(int i) {
            b = i;
        }

        public int getCount() {
            if (c == null) {
                return 0;
            }
            return c.size();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, b == i);
        }

        public Object getItem(int position) {
            return c.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return a(position, convertView, parent, false);
        }

        public final ViewIdSpinnerItem a(int position, View convertView, ViewGroup parent, boolean z) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (convertView != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) convertView;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(a);
                viewIdSpinnerItem.setTextSize(Resources.dimen.text_size_body_small);
            }
            viewIdSpinnerItem.a(0, c.get(position), z);
            viewIdSpinnerItem.a(false, 0xff404040, 0xff404040);
            return viewIdSpinnerItem;
        }
    }
}
