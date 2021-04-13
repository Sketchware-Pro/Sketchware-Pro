package com.besome.sketch.editor.view;

import a.a.a.Jw;
import a.a.a.ky;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.besome.sketch.ctrls.ViewIdSpinnerItem;
import java.util.ArrayList;

public class ViewProperties extends RelativeLayout {
    public Spinner a;
    public ArrayList<String> b = new ArrayList<>();
    public a c;
    public Jw d = null;

    /* access modifiers changed from: package-private */
    public class a extends BaseAdapter {
        public Context a;
        public int b;
        public ArrayList<String> c;

        public a(Context context, ArrayList<String> arrayList) {
            this.a = context;
            this.c = arrayList;
        }

        public void a(int i) {
            this.b = i;
        }

        public int getCount() {
            ArrayList<String> arrayList = this.c;
            if (arrayList == null) {
                return 0;
            }
            return arrayList.size();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, this.b == i);
        }

        public Object getItem(int i) {
            return this.c.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return a(i, view, viewGroup, false);
        }

        public final ViewIdSpinnerItem a(int i, View view, ViewGroup viewGroup, boolean z) {
            ViewIdSpinnerItem viewIdSpinnerItem;
            if (view != null) {
                viewIdSpinnerItem = (ViewIdSpinnerItem) view;
            } else {
                viewIdSpinnerItem = new ViewIdSpinnerItem(this.a);
                viewIdSpinnerItem.setTextSize(2131099882);
            }
            viewIdSpinnerItem.a(0, this.c.get(i), z);
            viewIdSpinnerItem.a(false, -12566464, -12566464);
            return viewIdSpinnerItem;
        }
    }

    public ViewProperties(Context context) {
        super(context);
        a(context);
    }

    public void setOnPropertyTargetChangeListener(Jw jw) {
        this.d = jw;
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        wB.a(context, this, 2131427776);
        ((TextView) findViewById(2131230821)).setText(xB.b().a(context, 2131625140));
        this.a = (Spinner) findViewById(2131231756);
        this.c = new a(context, this.b);
        this.a.setAdapter((SpinnerAdapter) this.c);
        this.a.setSelection(0);
        this.a.setOnItemSelectedListener(new ky(this));
    }

    public ViewProperties(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final void a(String str) {
        Jw jw = this.d;
        if (jw != null) {
            jw.a(str);
        }
    }
}
