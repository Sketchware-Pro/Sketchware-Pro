package com.besome.sketch.editor.view.palette;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.lib.ui.CustomScrollView;

import a.a.a.wB;
import a.a.a.xB;

public class PaletteWidget extends LinearLayout {

    public LinearLayout a;
    public LinearLayout b;
    public View c;
    public TextView d;
    public TextView e;
    public CustomScrollView f;

    public PaletteWidget(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private int getDip(int var1) {
        return (int) TypedValue.applyDimension(1, (float) var1, getContext().getResources().getDisplayMetrics());
    }

    public View a(PaletteWidget.a layoutType, String tag) {
        LinearLayout layout;
        switch (layoutType) {
            case a:
                layout = new IconLinearHorizontal(getContext());
                break;

            case b:
                layout = new IconLinearVertical(getContext());
                break;

            case c:
                layout = new IconScrollViewHorizontal(getContext());
                break;

            case d:
                layout = new IconScrollViewVertical(getContext());
                break;

            default:
                layout = null;
        }

        if (tag != null && tag.length() > 0) {
            layout.setTag(tag);
        }

        a.addView(layout);
        return layout;
    }

    public View a(PaletteWidget.b widgetType, String tag, String text, String resourceName) {
        IconBase iconBase;
        switch (widgetType) {
            case a:
                iconBase = new IconButton(getContext());
                break;

            case c:
                iconBase = new IconEditText(getContext());
                break;

            case b:
                iconBase = new IconTextView(getContext());
                break;

            case d:
                iconBase = new IconImageView(getContext());
                ((IconImageView) iconBase).setResourceName(resourceName);
                break;

            case e:
                iconBase = new IconListView(getContext());
                break;

            case f:
                iconBase = new IconSpinner(getContext());
                break;

            case g:
                iconBase = new IconCheckBox(getContext());
                break;

            case h:
                iconBase = new IconWebView(getContext());
                break;

            case i:
                iconBase = new IconSwitch(getContext());
                break;

            case j:
                iconBase = new IconSeekBar(getContext());
                break;

            case k:
                iconBase = new IconCalendarView(getContext());
                break;

            case l:
                iconBase = new IconAdView(getContext());
                break;

            case m:
                iconBase = new IconProgressBar(getContext());
                break;

            case n:
                iconBase = new IconMapView(getContext());
                break;

            default:
                iconBase = null;
        }

        if (tag != null && tag.length() > 0) {
            iconBase.setTag(tag);
        }

        iconBase.setText(text);
        iconBase.setName(resourceName);
        b.addView(iconBase);
        return iconBase;
    }

    public void a() {
        this.a.removeAllViews();
    }

    @SuppressLint("ResourceType")
    private void initialize(Context context) {
        wB.a(context, this, 2131427610);
        a = findViewById(2131231307);
        b = findViewById(2131232333);
        c = findViewById(2131230979);
        d = findViewById(2131232028);
        e = findViewById(2131232288);
        d.setText(xB.b().a(context, 2131626466));
        e.setText(xB.b().a(context, 2131626468));
        f = findViewById(2131231695);
    }

    public void b() {
        b.removeAllViews();
    }

    public void extraTitle(String title, int var2) {
        LinearLayout var3 = var2 == 0 ? a : b;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(-1, getDip(1)));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.parseColor("#12000000"));
        var3.addView(linearLayout);
        TextView textView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.setMargins(getDip(4), getDip(4), getDip(4), getDip(4));
        textView.setLayoutParams(layoutParams);
        textView.setText(title);
        textView.setTextSize(12.0F);
        textView.setTextColor(Color.parseColor("#FF009688"));
        var3.addView(textView);
    }

    public View extraWidget(String tag, String title, String name) {
        IconBase iconBase = (IconBase) mod.agus.jcoderz.editor.view.palette.PaletteWidget.a(tag, title, name, getContext());
        if (tag != null && tag.length() > 0) {
            iconBase.setTag(tag);
        }

        iconBase.setText(title);
        iconBase.setName(name);
        b.addView(iconBase);
        return iconBase;
    }

    public View extraWidgetLayout(String tag, String name) {
        IconBase iconBase = (IconBase) mod.agus.jcoderz.editor.view.palette.PaletteWidget.b(getContext(), tag, name);
        if (tag != null && tag.length() > 0) {
            iconBase.setTag(tag);
        }

        a.addView(iconBase);
        return iconBase;
    }

    public void setLayoutVisible(int visibility) {
        a.setVisibility(visibility);
        c.setVisibility(visibility);
        d.setVisibility(visibility);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        if (scrollEnabled) {
            f.b();
        } else {
            f.a();
        }

    }

    public void setWidgetVisible(int var1) {
        b.setVisibility(var1);
        e.setVisibility(var1);
    }

    public enum a {
        a, //eLinearHorizontal
        b, //eLinearVertical
        c, //eScrollHorizontal
        d //eScrollVertical
    }

    public enum b {
        a, //eButton
        b, //eTextView
        c, //eEditText
        d, //eImageView
        e, //eListView
        f, //eSpinner
        g, //eCheckBox
        h, //eWebView
        i, //eSwitch
        j, //eSeekBar
        k, //eCalenderView
        l, //eAddView
        m, //eProgressBar
        n, //eMapView
        o //eRadioButton
    }
}
