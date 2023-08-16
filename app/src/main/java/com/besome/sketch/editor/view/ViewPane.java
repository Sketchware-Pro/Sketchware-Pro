package com.besome.sketch.editor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.item.ItemAdView;
import com.besome.sketch.editor.view.item.ItemButton;
import com.besome.sketch.editor.view.item.ItemCalendarView;
import com.besome.sketch.editor.view.item.ItemCardView;
import com.besome.sketch.editor.view.item.ItemCheckBox;
import com.besome.sketch.editor.view.item.ItemEditText;
import com.besome.sketch.editor.view.item.ItemFloatingActionButton;
import com.besome.sketch.editor.view.item.ItemHorizontalScrollView;
import com.besome.sketch.editor.view.item.ItemImageView;
import com.besome.sketch.editor.view.item.ItemLinearLayout;
import com.besome.sketch.editor.view.item.ItemListView;
import com.besome.sketch.editor.view.item.ItemMapView;
import com.besome.sketch.editor.view.item.ItemProgressBar;
import com.besome.sketch.editor.view.item.ItemSeekBar;
import com.besome.sketch.editor.view.item.ItemSpinner;
import com.besome.sketch.editor.view.item.ItemSwitch;
import com.besome.sketch.editor.view.item.ItemTextView;
import com.besome.sketch.editor.view.item.ItemVerticalScrollView;
import com.besome.sketch.editor.view.item.ItemWebView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Gx;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;
import a.a.a.zB;
import dev.aldi.sayuti.editor.view.ExtraViewPane;
import dev.aldi.sayuti.editor.view.item.ItemBadgeView;
import dev.aldi.sayuti.editor.view.item.ItemBottomNavigationView;
import dev.aldi.sayuti.editor.view.item.ItemCircleImageView;
import dev.aldi.sayuti.editor.view.item.ItemCodeView;
import dev.aldi.sayuti.editor.view.item.ItemGoogleSignInButton;
import dev.aldi.sayuti.editor.view.item.ItemLottieAnimation;
import dev.aldi.sayuti.editor.view.item.ItemMaterialButton;
import dev.aldi.sayuti.editor.view.item.ItemOTPView;
import dev.aldi.sayuti.editor.view.item.ItemPatternLockView;
import dev.aldi.sayuti.editor.view.item.ItemRecyclerView;
import dev.aldi.sayuti.editor.view.item.ItemTabLayout;
import dev.aldi.sayuti.editor.view.item.ItemViewPager;
import dev.aldi.sayuti.editor.view.item.ItemWaveSideBar;
import dev.aldi.sayuti.editor.view.item.ItemYoutubePlayer;
import mod.agus.jcoderz.beans.ViewBeans;
import mod.agus.jcoderz.editor.view.item.ItemAnalogClock;
import mod.agus.jcoderz.editor.view.item.ItemAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.item.ItemDatePicker;
import mod.agus.jcoderz.editor.view.item.ItemDigitalClock;
import mod.agus.jcoderz.editor.view.item.ItemGridView;
import mod.agus.jcoderz.editor.view.item.ItemMultiAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.item.ItemRadioButton;
import mod.agus.jcoderz.editor.view.item.ItemRatingBar;
import mod.agus.jcoderz.editor.view.item.ItemSearchView;
import mod.agus.jcoderz.editor.view.item.ItemTimePicker;
import mod.agus.jcoderz.editor.view.item.ItemVideoView;
import mod.hey.studios.util.ProjectFile;

public class ViewPane extends RelativeLayout {
    private ViewGroup a;
    private int b;
    private ArrayList<Object[]> c;
    private Object[] d;
    private TextView e;
    private kC f;
    private String sc_id;

    public ViewPane(Context context) {
        super(context);
        a = null;
        b = 99;
        c = new ArrayList<>();
        d = null;
        initialize();
    }

    private void initialize() {
        setBackgroundColor(Color.WHITE);
        a();
        c();
    }

    public void b() {
        a(true);
        c = new ArrayList<>();
        ((ty) a).setChildScrollEnabled(true);
    }

    private void c() {
        e = new TextView(getContext());
        e.setBackgroundResource(R.drawable.highlight);
        e.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        e.setVisibility(GONE);
    }

    public void d() {
        a.removeAllViews();
    }

    public void e() {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag == null) {
            return;
        }
        removeView(findViewWithTag);
    }

    public void f(ViewBean viewBean) {
        ViewGroup viewGroup = a.findViewWithTag(viewBean.parent);
        viewGroup.removeView(a.findViewWithTag(viewBean.id));
        if (viewGroup instanceof ty) {
            ((ty) viewGroup).a();
        }
    }

    public sy g(ViewBean viewBean) {
        View findViewWithTag;
        String str = viewBean.preId;
        if (str != null && str.length() > 0 && !viewBean.preId.equals(viewBean.id)) {
            a.findViewWithTag(viewBean.preId).setTag(viewBean.id);
            viewBean.preId = "";
        }
        if (viewBean.id.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(viewBean.id);
        } else {
            findViewWithTag = a.findViewWithTag(viewBean.id);
        }
        b(findViewWithTag, viewBean);
        return (sy) findViewWithTag;
    }

    public void setResourceManager(kC kCVar) {
        f = kCVar;
    }

    public sy d(ViewBean viewBean) {
        View findViewWithTag = a.findViewWithTag(viewBean.id);
        if (viewBean.id.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(viewBean.id);
        }
        String str = viewBean.preParent;
        if (str != null && str.length() > 0 && !viewBean.parent.equals(viewBean.preParent)) {
            ViewGroup viewGroup = a.findViewWithTag(viewBean.preParent);
            viewGroup.removeView(findViewWithTag);
            ((ty) viewGroup).a();
            a(findViewWithTag);
        } else if (viewBean.index != viewBean.preIndex) {
            ((ViewGroup) a.findViewWithTag(viewBean.parent)).removeView(findViewWithTag);
            a(findViewWithTag);
        }
        viewBean.preId = "";
        viewBean.preIndex = -1;
        viewBean.preParent = "";
        viewBean.preParentType = -1;
        findViewWithTag.setVisibility(VISIBLE);
        return (sy) findViewWithTag;
    }

    public void e(ViewBean viewBean) {
        d = null;
        c(viewBean);
        ((ty) a).setChildScrollEnabled(false);
    }

    private int b(View view) {
        int i = 0;
        while (view != null && view != a) {
            i++;
            view = (View) view.getParent();
        }
        return i * 2;
    }

    public View b(ViewBean viewBean) {
        View item = switch (viewBean.type) {
            case ViewBean.VIEW_TYPE_LAYOUT_LINEAR,
                    ViewBeans.VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT,
                    ViewBeans.VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT,
                    ViewBeans.VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT,
                    ViewBeans.VIEW_TYPE_LAYOUT_RADIOGROUP -> new ItemLinearLayout(getContext());
            case ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW -> new ItemCardView(getContext());
            case ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW ->
                    new ItemHorizontalScrollView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_BUTTON -> new ItemButton(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW -> new ItemTextView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_EDITTEXT -> new ItemEditText(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW -> new ItemImageView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_WEBVIEW -> new ItemWebView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR -> new ItemProgressBar(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_LISTVIEW -> new ItemListView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_SPINNER -> new ItemSpinner(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_CHECKBOX -> new ItemCheckBox(getContext());
            case ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW -> new ItemVerticalScrollView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_SWITCH -> new ItemSwitch(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_SEEKBAR -> new ItemSeekBar(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW -> new ItemCalendarView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_ADVIEW -> new ItemAdView(getContext());
            case ViewBean.VIEW_TYPE_WIDGET_MAPVIEW -> new ItemMapView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_RADIOBUTTON -> new ItemRadioButton(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_RATINGBAR -> new ItemRatingBar(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_VIDEOVIEW -> new ItemVideoView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_SEARCHVIEW -> new ItemSearchView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW ->
                    new ItemAutoCompleteTextView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW ->
                    new ItemMultiAutoCompleteTextView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_GRIDVIEW -> new ItemGridView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_ANALOGCLOCK -> new ItemAnalogClock(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_DATEPICKER -> new ItemDatePicker(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_TIMEPICKER -> new ItemTimePicker(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_DIGITALCLOCK -> new ItemDigitalClock(getContext());
            case ViewBeans.VIEW_TYPE_LAYOUT_TABLAYOUT -> new ItemTabLayout(getContext());
            case ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER -> new ItemViewPager(getContext());
            case ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW ->
                    new ItemBottomNavigationView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_BADGEVIEW -> new ItemBadgeView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_PATTERNLOCKVIEW ->
                    new ItemPatternLockView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_WAVESIDEBAR -> new ItemWaveSideBar(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_MATERIALBUTTON -> new ItemMaterialButton(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_SIGNINBUTTON ->
                    new ItemGoogleSignInButton(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW ->
                    new ItemCircleImageView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW ->
                    new ItemLottieAnimation(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW ->
                    new ItemYoutubePlayer(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_OTPVIEW -> new ItemOTPView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_CODEVIEW -> new ItemCodeView(getContext());
            case ViewBeans.VIEW_TYPE_WIDGET_RECYCLERVIEW -> new ItemRecyclerView(getContext());
            default -> null;
        };
        item.setId(++b);
        item.setTag(viewBean.id);
        ((sy) item).setBean(viewBean);
        b(item, viewBean);
        return item;
    }

    private void c(ViewBean viewBean) {
        a(viewBean, (ItemLinearLayout) a);
    }

    public ViewPane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a = null;
        b = 99;
        c = new ArrayList<>();
        d = null;
        initialize();
    }

    public void setScId(String str) {
        sc_id = str;
    }

    private void a() {
        ViewBean viewBean = new ViewBean("root", ViewBean.VIEW_TYPE_LAYOUT_LINEAR);
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.orientation = LinearLayout.VERTICAL;
        viewBean.parentType = ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
        View b = b(viewBean);
        ((ItemLinearLayout) b).setFixed(true);
        a = (ViewGroup) b;
        a.setBackgroundColor(0xffeeeeee);
        addView(b);
    }

    private void b(View view, ViewBean viewBean) {
        ImageBean imageBean;
        String str;
        ExtraViewPane.a(view, viewBean, this, f);
        if (viewBean.id.charAt(0) == '_') {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginBottom);
            int i = viewBean.layout.layoutGravity;
            if ((i & Gravity.LEFT) == Gravity.LEFT) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            if ((i & Gravity.TOP) == Gravity.TOP) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            }
            if ((i & Gravity.RIGHT) == Gravity.RIGHT) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            if ((i & Gravity.BOTTOM) == Gravity.BOTTOM) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
            if ((i & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL) {
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            }
            if ((i & Gravity.CENTER_VERTICAL) == Gravity.CENTER_VERTICAL) {
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            }
            if ((i & Gravity.CENTER) == Gravity.CENTER) {
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            }
            view.setLayoutParams(layoutParams);
            if (viewBean.getClassInfo().b("FloatingActionButton") && (imageBean = viewBean.image) != null && (str = imageBean.resName) != null && str.length() > 0) {
                try {
                    Bitmap decodeFile = BitmapFactory.decodeFile(f.f(viewBean.image.resName));
                    int round = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((FloatingActionButton) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth() * round, decodeFile.getHeight() * round, true));
                } catch (Exception ignored) {
                }
            }
            view.setRotation(viewBean.image.rotate);
            view.setAlpha(viewBean.alpha);
            view.setTranslationX(wB.a(getContext(), viewBean.translationX));
            view.setTranslationY(wB.a(getContext(), viewBean.translationY));
            view.setScaleX(viewBean.scaleX);
            view.setScaleY(viewBean.scaleY);
            view.setVisibility(View.VISIBLE);
            return;
        }
        a(view, viewBean);
        view.setRotation(viewBean.image.rotate);
        view.setAlpha(viewBean.alpha);
        view.setTranslationX(wB.a(getContext(), viewBean.translationX));
        view.setTranslationY(wB.a(getContext(), viewBean.translationY));
        view.setScaleX(viewBean.scaleX);
        view.setScaleY(viewBean.scaleY);
        String str2 = viewBean.layout.backgroundResource;
        if (str2 != null) {
            try {
                if (f.h(str2) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                    view.setBackgroundResource(getContext().getResources().getIdentifier(viewBean.layout.backgroundResource, "drawable", getContext().getPackageName()));
                } else {
                    String f = this.f.f(viewBean.layout.backgroundResource);
                    if (f.endsWith(".9.png")) {
                        Bitmap a2 = zB.a(f);
                        byte[] ninePatchChunk = a2.getNinePatchChunk();
                        if (NinePatch.isNinePatchChunk(ninePatchChunk)) {
                            view.setBackground(new NinePatchDrawable(getResources(), a2, ninePatchChunk, new Rect(), null));
                        } else {
                            view.setBackground(new BitmapDrawable(getResources(), f));
                        }
                    } else {
                        Bitmap decodeFile2 = BitmapFactory.decodeFile(f);
                        int round2 = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                        view.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(decodeFile2, decodeFile2.getWidth() * round2, decodeFile2.getHeight() * round2, true)));
                    }
                }
            } catch (Exception e) {
                Log.e("DEBUG", e.getMessage(), e);
            }
        }
        Gx classInfo = viewBean.getClassInfo();
        if (classInfo.a("LinearLayout")) {
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.setOrientation(viewBean.layout.orientation);
            linearLayout.setWeightSum(viewBean.layout.weightSum);
            if (view instanceof ItemLinearLayout) {
                ((ItemLinearLayout) view).setLayoutGravity(viewBean.layout.gravity);
            }
        }
        if (classInfo.a("TextView")) {
            TextView textView = (TextView) view;
            a(textView, viewBean);
            if (!classInfo.b("Button") && !classInfo.b("Switch")) {
                textView.setGravity(viewBean.layout.gravity);
            } else {
                int i2 = viewBean.layout.gravity;
                if (i2 == 0) {
                    textView.setGravity(Gravity.CENTER);
                } else {
                    textView.setGravity(i2);
                }
            }
        }
        if (classInfo.b("EditText")) {
            a((EditText) view, viewBean);
        }
        if (classInfo.b("ImageView")) {
            if (f.h(viewBean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                ((ImageView) view).setImageResource(getContext().getResources().getIdentifier(viewBean.image.resName, "drawable", getContext().getPackageName()));
            } else if (viewBean.image.resName.equals("default_image")) {
                ((ImageView) view).setImageResource(R.drawable.default_image);
            } else {
                try {
                    Bitmap decodeFile3 = BitmapFactory.decodeFile(f.f(viewBean.image.resName));
                    int round3 = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile3, decodeFile3.getWidth() * round3, decodeFile3.getHeight() * round3, true));
                } catch (Exception unused2) {
                    ((ImageView) view).setImageResource(R.drawable.default_image);
                }
            }
            ((ImageView) view).setScaleType(ImageView.ScaleType.valueOf(viewBean.image.scaleType));
        }
        if (classInfo.a("CompoundButton")) {
            ((CompoundButton) view).setChecked(viewBean.checked != 0);
        }
        if (classInfo.b("SeekBar")) {
            SeekBar seekBar = (SeekBar) view;
            seekBar.setProgress(viewBean.progress);
            seekBar.setMax(viewBean.max);
        }
        if (classInfo.b("ProgressBar")) {
            ((ItemProgressBar) view).setProgressBarStyle(viewBean.progressStyle);
        }
        if (classInfo.b("CalendarView")) {
            ((CalendarView) view).setFirstDayOfWeek(viewBean.firstDayOfWeek);
        }
        if (classInfo.b("AdView")) {
            ((ItemAdView) view).setAdSize(viewBean.adSize);
        }
        view.setVisibility(VISIBLE);
    }

    public sy a(String str) {
        View findViewWithTag;
        if (str.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(str);
        } else {
            findViewWithTag = a.findViewWithTag(str);
        }
        if (findViewWithTag != null && (findViewWithTag instanceof sy)) {
            return (sy) findViewWithTag;
        }
        return null;
    }

    public void a(ViewBean viewBean, int i, int i2) {
        if (d != null) {
            View view = (View) d[1];
            if (view instanceof LinearLayout) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = (Integer) d[2];
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 0;
            } else if (view instanceof ItemVerticalScrollView) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = (Integer) d[2];
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW;
                viewBean.layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else if (view instanceof ItemHorizontalScrollView) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = (Integer) d[2];
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW;
                viewBean.layout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            } else if (view instanceof ItemCardView) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = (Integer) d[2];
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW;
                viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        } else {
            viewBean.preIndex = viewBean.index;
            viewBean.preParent = viewBean.parent;
            viewBean.parent = "root";
            viewBean.parentType = ViewBean.VIEW_TYPE_LAYOUT_LINEAR;
            viewBean.index = -1;
        }
    }

    public View a(ViewBean viewBean) {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag != null) {
            return findViewWithTag;
        }
        ItemFloatingActionButton itemFloatingActionButton = new ItemFloatingActionButton(getContext());
        itemFloatingActionButton.setTag("_fab");
        itemFloatingActionButton.setLayoutParams(new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        itemFloatingActionButton.setMainColor(ProjectFile.getColor(sc_id, "color_accent"));
        itemFloatingActionButton.setFixed(true);
        if (viewBean == null) {
            ViewBean viewBean2 = new ViewBean("_fab", ViewBean.VIEW_TYPE_WIDGET_FAB);
            LayoutBean layoutBean = viewBean2.layout;
            layoutBean.marginLeft = 16;
            layoutBean.marginTop = 16;
            layoutBean.marginRight = 16;
            layoutBean.marginBottom = 16;
            layoutBean.layoutGravity = Gravity.RIGHT | Gravity.BOTTOM;
            itemFloatingActionButton.setBean(viewBean2);
        } else {
            itemFloatingActionButton.setBean(viewBean);
        }
        addView(itemFloatingActionButton);
        b(itemFloatingActionButton, itemFloatingActionButton.getBean());
        return itemFloatingActionButton;
    }

    public void a(boolean z) {
        e.setVisibility(View.GONE);
        ViewParent parent = e.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(e);
        }
        if (z) {
            d = null;
        }
    }

    public void a(int i, int i2, int i3, int i4) {
        Object[] a2 = a(i, i2);
        if (a2 == null) {
            a(true);
        } else if (d != a2) {
            a(true);
            ViewGroup viewGroup = (ViewGroup) a2[1];
            viewGroup.addView(e, (Integer) a2[2]);
            if (viewGroup instanceof LinearLayout) {
                e.setLayoutParams(new LinearLayout.LayoutParams(i3, i4));
            } else if (viewGroup instanceof FrameLayout) {
                e.setLayoutParams(new FrameLayout.LayoutParams(i3, i4));
            } else {
                e.setLayoutParams(new RelativeLayout.LayoutParams(i3, i4));
            }
            e.setVisibility(View.VISIBLE);
            d = a2;
        }
    }

    private Object[] a(int i, int i2) {
        Object[] objArr = null;
        int i3 = -1;
        for (int i4 = 0; i4 < c.size(); i4++) {
            Object[] objArr2 = c.get(i4);
            Rect rect = (Rect) objArr2[0];
            if (i >= rect.left && i < rect.right && i2 >= rect.top && i2 < rect.bottom && i3 < (Integer) objArr2[3]) {
                i3 = (Integer) objArr2[3];
                objArr = objArr2;
            }
        }
        return objArr;
    }

    private void a(ViewBean view, ItemLinearLayout linearLayout) {
        int[] linearLayoutLocation = new int[2];
        linearLayout.getLocationOnScreen(linearLayoutLocation);
        int var4;
        int linearLayoutGravity = linearLayout.getLayoutGravity();
        int horizontalLinearLayoutGravity = linearLayoutGravity & Gravity.FILL_HORIZONTAL;
        int verticalLinearLayoutGravity = linearLayoutGravity & Gravity.FILL_VERTICAL;
        int linearLayoutX = linearLayoutLocation[0];
        int var7;
        int linearLayoutY = linearLayoutLocation[1];
        a(new Rect(linearLayoutX, linearLayoutY, (int) ((float) linearLayout.getWidth() * getScaleX()) + linearLayoutX, (int) ((float) linearLayout.getHeight() * getScaleY()) + linearLayoutY), linearLayout, -1, b(linearLayout));
        var4 = linearLayoutY + (int) ((float) linearLayout.getPaddingTop() * getScaleY());
        var7 = linearLayoutX + (int) ((float) linearLayout.getPaddingLeft() * getScaleX());
        int var8 = 0;

        int var13;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            if (child != null && child.getTag() != null && (view == null || view.id == null || !child.getTag().equals(view.id)) && child.getVisibility() == View.VISIBLE) {
                label62:
                {
                    label61:
                    {
                        int[] childLocation = new int[2];
                        child.getLocationOnScreen(childLocation);
                        if (linearLayout.getOrientation() == LinearLayout.HORIZONTAL) {
                            int leftMargin = ((LinearLayout.LayoutParams) child.getLayoutParams()).leftMargin;
                            int rightMargin = ((LinearLayout.LayoutParams) child.getLayoutParams()).rightMargin;
                            if (horizontalLinearLayoutGravity == Gravity.CENTER_HORIZONTAL) {
                                if (i == 0) {
                                    int x = childLocation[0] - (int) ((float) leftMargin * getScaleX());
                                    int y = linearLayoutLocation[1];
                                    a(new Rect(var7, y, x, (int) ((float) linearLayout.getMeasuredHeight() * getScaleY()) + y), linearLayout, 0, b(linearLayout) + 1);
                                    var7 = x;
                                }

                                var4 = (int) ((float) (leftMargin + child.getMeasuredWidth() + rightMargin) * getScaleX()) + var7;
                                int y = linearLayoutLocation[1];
                                var7 = var8 + 1;
                                a(new Rect(var7, y, var4, (int) ((float) linearLayout.getMeasuredHeight() * getScaleY()) + y), linearLayout, var8, b(linearLayout) + 1);
                                var8 = y;
                            } else if (horizontalLinearLayoutGravity == Gravity.RIGHT) {
                                int x = childLocation[0];
                                int y = linearLayoutLocation[1];
                                a(new Rect(var7, y, x - (int) ((float) leftMargin * getScaleX()), (int) ((float) linearLayout.getMeasuredHeight() * getScaleY()) + y), linearLayout, var8, b(linearLayout) + 1);
                                var4 = (int) ((float) (childLocation[0] + child.getMeasuredWidth() + rightMargin) * getScaleX());
                                var7 = var8 + 1;
                                var8 = y;
                            } else {
                                var4 = (int) ((float) (leftMargin + child.getMeasuredWidth() + rightMargin) * getScaleX()) + var7;
                                int y = linearLayoutLocation[1];
                                var7 = var8 + 1;
                                a(new Rect(var7, y, var4, (int) ((float) linearLayout.getMeasuredHeight() * getScaleY()) + y), linearLayout, var8, b(linearLayout) + 1);
                                var8 = y;
                            }
                        } else {
                            int topMargin = ((LinearLayout.LayoutParams) child.getLayoutParams()).topMargin;
                            int bottomMargin = ((LinearLayout.LayoutParams) child.getLayoutParams()).bottomMargin;
                            if (verticalLinearLayoutGravity == Gravity.CENTER_VERTICAL) {
                                if (i == 0) {
                                    int x = linearLayoutLocation[0];
                                    int y = childLocation[1] - (int) ((float) topMargin * getScaleY());
                                    a(new Rect(x, var4, (int) ((float) linearLayout.getMeasuredWidth() * getScaleX()) + x, y), linearLayout, 0, b(linearLayout) + 1);
                                    var4 = y;
                                }

                                int bottom = var4 + (int) ((float) (topMargin + child.getMeasuredHeight() + bottomMargin) * getScaleY());
                                int x = linearLayoutLocation[0];
                                int top = var8 + 1;
                                a(new Rect(x, top, (int) ((float) linearLayout.getMeasuredWidth() * getScaleX()) + x, bottom), linearLayout, var8, b(linearLayout) + 1);
                                var8 = bottom;
                                var7 = top;
                                var4 = x;
                            } else if (verticalLinearLayoutGravity == Gravity.BOTTOM) {
                                int x = linearLayoutLocation[0];
                                int y = childLocation[1];
                                a(new Rect(x, var4, (int) ((float) linearLayout.getMeasuredWidth() * getScaleX()) + x, y - (int) ((float) topMargin * getScaleY())), linearLayout, var8, b(linearLayout) + 1);
                                ++var8;
                                var4 = x;
                                var7 = (int) ((float) (childLocation[1] + child.getMeasuredHeight() + bottomMargin) * getScaleY());
                                var13 = var8;
                                break label61;
                            } else {
                                var7 = var4 + (int) ((float) (topMargin + child.getMeasuredHeight() + bottomMargin) * getScaleY());
                                int x = linearLayoutLocation[0];
                                a(new Rect(x, var4, (int) ((float) linearLayout.getMeasuredWidth() * getScaleX()) + x, var7), linearLayout, var8, b(linearLayout) + 1);
                                var4 = x;
                                ++var8;
                                break label62;
                            }

                        }

                        var13 = var7;
                        var7 = var8;
                    }

                    var8 = var13;
                }

                if (child instanceof ItemLinearLayout) {
                    a(view, (ItemLinearLayout) child);
                } else if (child instanceof ItemHorizontalScrollView) {
                    a(view, (ViewGroup) child);
                } else if (child instanceof ItemVerticalScrollView) {
                    a(view, (ViewGroup) child);
                } else if (child instanceof  ItemCardView) {
                    a(view, (ViewGroup) child);
                }

                var13 = var4;
            } else {
                var13 = var7;
                var7 = var4;
            }

            var4 = var7;
            var7 = var13;
        }

    }

    private void a(ViewBean viewBean, ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null && childAt.getTag() != null && ((viewBean == null || viewBean.id == null || !childAt.getTag().equals(viewBean.id)) && childAt.getVisibility() == View.VISIBLE)) {
                i++;
                if (childAt instanceof ItemLinearLayout) {
                    a(viewBean, (ItemLinearLayout) childAt);
                } else if (childAt instanceof ItemHorizontalScrollView) {
                    a(viewBean, (ViewGroup) childAt);
                } else if (childAt instanceof ItemVerticalScrollView) {
                    a(viewBean, (ViewGroup) childAt);
                } else if (childAt instanceof  ItemCardView) {
                    a(viewBean, (ViewGroup) childAt);
                }
            }
        }
        if (i < 1) {
            int[] iArr = new int[2];
            viewGroup.getLocationOnScreen(iArr);
            int i3 = iArr[0];
            int i4 = iArr[1];
            a(new Rect(i3, i4, ((int) (viewGroup.getWidth() * getScaleX())) + i3, ((int) (viewGroup.getHeight() * getScaleY())) + i4), viewGroup, -1, b(viewGroup));
        }
    }

    private void a(Rect rect, View view, int i, int i2) {
        c.add(new Object[]{rect, view, i, i2});
    }

    public void a(View view) {
        ViewBean bean = ((sy) view).getBean();
        ViewGroup viewGroup = a.findViewWithTag(bean.parent);
        viewGroup.addView(view, bean.index);
        if (viewGroup instanceof ty) {
            ((ty) viewGroup).a();
        }
    }

    private void a(View view, ViewBean viewBean) {
        LayoutBean layoutBean = viewBean.layout;
        int i = layoutBean.width;
        int i2 = layoutBean.height;
        if (i > 0) {
            i = (int) wB.a(getContext(), (float) viewBean.layout.width);
        }
        if (i2 > 0) {
            i2 = (int) wB.a(getContext(), (float) viewBean.layout.height);
        }
        view.setBackgroundColor(viewBean.layout.backgroundColor);
        if (viewBean.id.equals("root")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginBottom);
            LayoutBean layoutBean2 = viewBean.layout;
            view.setPadding(layoutBean2.paddingLeft, layoutBean2.paddingTop, layoutBean2.paddingRight, layoutBean2.paddingBottom);
            view.setLayoutParams(layoutParams);
        } else if (viewBean.parentType == ViewBean.VIEW_TYPE_LAYOUT_LINEAR) {
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, i2);
            layoutParams2.leftMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams2.topMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams2.rightMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams2.bottomMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginBottom);
            LayoutBean layoutBean3 = viewBean.layout;
            view.setPadding(layoutBean3.paddingLeft, layoutBean3.paddingTop, layoutBean3.paddingRight, layoutBean3.paddingBottom);
            int i3 = viewBean.layout.layoutGravity;
            if (i3 != 0) {
                layoutParams2.gravity = i3;
            }
            layoutParams2.weight = viewBean.layout.weight;
            view.setLayoutParams(layoutParams2);
        } else {
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(i, i2);
            layoutParams3.leftMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginLeft);
            layoutParams3.topMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginTop);
            layoutParams3.rightMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginRight);
            layoutParams3.bottomMargin = (int) wB.a(getContext(), (float) viewBean.layout.marginBottom);
            LayoutBean layoutBean4 = viewBean.layout;
            view.setPadding(layoutBean4.paddingLeft, layoutBean4.paddingTop, layoutBean4.paddingRight, layoutBean4.paddingBottom);
            int i4 = viewBean.layout.layoutGravity;
            if (i4 != 0) {
                layoutParams3.gravity = i4;
            }
            view.setLayoutParams(layoutParams3);
        }
    }

    private void a(TextView textView, ViewBean viewBean) {
        String str = viewBean.text.text;
        if (str != null && str.length() > 0 && str.contains("\\n")) {
            str = viewBean.text.text.replaceAll("\\\\n", "\n");
        }
        textView.setText(str);
        textView.setTypeface(null, viewBean.text.textType);
        textView.setTextColor(viewBean.text.textColor);
        textView.setTextSize(viewBean.text.textSize);
        textView.setLines(viewBean.text.line);
        textView.setSingleLine(viewBean.text.singleLine != 0);
    }

    private void a(EditText editText, ViewBean viewBean) {
        editText.setHint(viewBean.text.hint);
        editText.setHintTextColor(viewBean.text.hintColor);
    }
}
