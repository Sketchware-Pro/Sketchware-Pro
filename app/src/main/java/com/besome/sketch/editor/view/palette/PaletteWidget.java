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
import dev.aldi.sayuti.editor.view.palette.IconBadgeView;
import dev.aldi.sayuti.editor.view.palette.IconBottomNavigationView;
import dev.aldi.sayuti.editor.view.palette.IconCardView;
import dev.aldi.sayuti.editor.view.palette.IconCircleImageView;
import dev.aldi.sayuti.editor.view.palette.IconCodeView;
import dev.aldi.sayuti.editor.view.palette.IconCollapsingToolbar;
import dev.aldi.sayuti.editor.view.palette.IconGoogleSignInButton;
import dev.aldi.sayuti.editor.view.palette.IconLottieAnimation;
import dev.aldi.sayuti.editor.view.palette.IconMaterialButton;
import dev.aldi.sayuti.editor.view.palette.IconOTPView;
import dev.aldi.sayuti.editor.view.palette.IconPatternLockView;
import dev.aldi.sayuti.editor.view.palette.IconRadioGroup;
import dev.aldi.sayuti.editor.view.palette.IconRecyclerView;
import dev.aldi.sayuti.editor.view.palette.IconSwipeRefreshLayout;
import dev.aldi.sayuti.editor.view.palette.IconTabLayout;
import dev.aldi.sayuti.editor.view.palette.IconTextInputLayout;
import dev.aldi.sayuti.editor.view.palette.IconViewPager;
import dev.aldi.sayuti.editor.view.palette.IconWaveSideBar;
import dev.aldi.sayuti.editor.view.palette.IconYoutubePlayer;
import mod.agus.jcoderz.editor.view.palette.IconAnalogClock;
import mod.agus.jcoderz.editor.view.palette.IconAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.palette.IconDatePicker;
import mod.agus.jcoderz.editor.view.palette.IconDigitalClock;
import mod.agus.jcoderz.editor.view.palette.IconGridView;
import mod.agus.jcoderz.editor.view.palette.IconMultiAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.palette.IconRadioButton;
import mod.agus.jcoderz.editor.view.palette.IconRatingBar;
import mod.agus.jcoderz.editor.view.palette.IconSearchView;
import mod.agus.jcoderz.editor.view.palette.IconTimePicker;
import mod.agus.jcoderz.editor.view.palette.IconVideoView;
import mod.hey.studios.util.Helper;

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
        d.setText(Helper.getResString(2131626466));
        e.setText(Helper.getResString(2131626468));
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
        IconBase iconBase;
        Context context = getContext();
        switch (title) {
            case "DatePicker":
                iconBase = new IconDatePicker(context);
                break;

            case "RatingBar":
                iconBase = new IconRatingBar(context);
                break;

            case "SearchView":
                iconBase = new IconSearchView(context);
                break;

            case "DigitalClock":
                iconBase = new IconDigitalClock(context);
                break;

            case "RadioButton":
                iconBase = new IconRadioButton(context);
                break;

            case "GridView":
                iconBase = new IconGridView(context);
                break;

            case "AutoCompleteTextView":
                iconBase = new IconAutoCompleteTextView(context);
                break;

            case "MultiAutoCompleteTextView":
                iconBase = new IconMultiAutoCompleteTextView(context);
                break;

            case "VideoView":
                iconBase = new IconVideoView(context);
                break;

            case "TimePicker":
                iconBase = new IconTimePicker(context);
                break;

            case "AnalogClock":
                iconBase = new IconAnalogClock(context);
                break;

            case "ViewPager":
                iconBase = new IconViewPager(context);
                break;

            case "BadgeView":
                iconBase = new IconBadgeView(context);
                break;

            case "PatternLockView":
                iconBase = new IconPatternLockView(context);
                break;

            case "WaveSideBar":
                iconBase = new IconWaveSideBar(context);
                break;

            case "SignInButton":
                iconBase = new IconGoogleSignInButton(context);
                break;

            case "MaterialButton":
                iconBase = new IconMaterialButton(context);
                break;

            case "CircleImageView":
                iconBase = new IconCircleImageView(context);
                break;

            case "LottieAnimation":
                iconBase = new IconLottieAnimation(context);
                break;

            case "YoutubePlayer":
                iconBase = new IconYoutubePlayer(context);
                break;

            case "OTPView":
                iconBase = new IconOTPView(context);
                break;

            case "CodeView":
                iconBase = new IconCodeView(context);
                break;

            case "RecyclerView":
                iconBase = new IconRecyclerView(context);
                break;

            default:
                iconBase = null;
                break;
        }
        if (tag != null && tag.length() > 0) {
            iconBase.setTag(tag);
        }

        iconBase.setText(title);
        iconBase.setName(name);
        b.addView(iconBase);
        return iconBase;
    }

    public View extraWidgetLayout(String tag, String name) {
        IconBase iconBase;
        Context context = getContext();
        switch (name) {
            case "TabLayout":
                iconBase = new IconTabLayout(context);
                break;

            case "BottomNavigationView":
                iconBase = new IconBottomNavigationView(context);
                break;

            case "CollapsingToolbarLayout":
                iconBase = new IconCollapsingToolbar(context);
                break;

            case "SwipeRefreshLayout":
                iconBase = new IconSwipeRefreshLayout(context);
                break;

            case "RadioGroup":
                iconBase = new IconRadioGroup(context);
                break;

            case "CardView":
                iconBase = new IconCardView(context);
                break;

            case "TextInputLayout":
                iconBase = new IconTextInputLayout(context);
                break;

            default:
                iconBase = null;
                break;
        }
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
