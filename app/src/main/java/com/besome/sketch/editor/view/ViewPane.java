package com.besome.sketch.editor.view;

import a.a.a.Gx;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;
import a.a.a.zB;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
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
import dev.aldi.sayuti.editor.view.ExtraViewPane;
import java.util.ArrayList;
import mod.agus.jcoderz.editor.view.ViewPanes;
import mod.hey.studios.util.ProjectFile;

public class ViewPane extends RelativeLayout {
    public ViewGroup a;
    public int b;
    public ArrayList<Object[]> c;
    public Object[] d;
    public TextView e;
    public kC f;
    public String sc_id;

    public ViewPane(Context context) {
        super(context);
        this.a = null;
        this.b = 99;
        this.c = new ArrayList<>();
        this.d = null;
        a(context);
    }

    public final void a(Context context) {
        setBackgroundColor(-1);
        a();
        c();
    }

    public void b() {
        a(true);
        this.c = new ArrayList<>();
        ((ty) this.a).setChildScrollEnabled(true);
    }

    public final void c() {
        this.e = new TextView(getContext());
        this.e.setBackgroundResource(2131165670);
        this.e.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.e.setVisibility(8);
    }

    public void d() {
        this.a.removeAllViews();
    }

    public void e() {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag == null) {
            return;
        }
        removeView(findViewWithTag);
    }

    public void f(ViewBean viewBean) {
        ViewGroup viewGroup = (ViewGroup) this.a.findViewWithTag(viewBean.parent);
        viewGroup.removeView(this.a.findViewWithTag(viewBean.id));
        if (viewGroup instanceof ty) {
            ((ty) viewGroup).a();
        }
    }

    public sy g(ViewBean viewBean) {
        View findViewWithTag;
        String str = viewBean.preId;
        if (str != null && str.length() > 0 && !viewBean.preId.equals(viewBean.id)) {
            this.a.findViewWithTag(viewBean.preId).setTag(viewBean.id);
            viewBean.preId = "";
        }
        if (viewBean.id.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(viewBean.id);
        } else {
            findViewWithTag = this.a.findViewWithTag(viewBean.id);
        }
        b(findViewWithTag, viewBean);
        return (sy) findViewWithTag;
    }

    public ItemFloatingActionButton getFab() {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag == null) {
            return null;
        }
        return (ItemFloatingActionButton) findViewWithTag;
    }

    public kC getResourceManager() {
        return this.f;
    }

    public ViewGroup getRoot() {
        return this.a;
    }

    public void setResourceManager(kC kCVar) {
        this.f = kCVar;
    }

    public sy d(ViewBean viewBean) {
        View findViewWithTag = this.a.findViewWithTag(viewBean.id);
        if (viewBean.id.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(viewBean.id);
        }
        String str = viewBean.preParent;
        if (str != null && str.length() > 0 && !viewBean.parent.equals(viewBean.preParent)) {
            ViewGroup viewGroup = (ViewGroup) this.a.findViewWithTag(viewBean.preParent);
            viewGroup.removeView(findViewWithTag);
            ((ty) viewGroup).a();
            a(findViewWithTag);
        } else if (viewBean.index != viewBean.preIndex) {
            ((ViewGroup) this.a.findViewWithTag(viewBean.parent)).removeView(findViewWithTag);
            a(findViewWithTag);
        }
        viewBean.preId = "";
        viewBean.preIndex = -1;
        viewBean.preParent = "";
        viewBean.preParentType = -1;
        findViewWithTag.setVisibility(0);
        return (sy) findViewWithTag;
    }

    public void e(ViewBean viewBean) {
        this.d = null;
        c(viewBean);
        ((ty) this.a).setChildScrollEnabled(false);
    }

    public final int b(View view) {
        int i = 0;
        while (view != null && view != this.a) {
            i++;
            view = (View) view.getParent();
        }
        return i * 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.besome.sketch.editor.view.item.ItemSpinner] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.besome.sketch.editor.view.item.ItemCheckBox] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.besome.sketch.editor.view.item.ItemVerticalScrollView] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.besome.sketch.editor.view.item.ItemSwitch] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.besome.sketch.editor.view.item.ItemSeekBar] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.besome.sketch.editor.view.item.ItemCalendarView] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.besome.sketch.editor.view.item.ItemAdView] */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.besome.sketch.editor.view.item.ItemMapView] */
    /* JADX WARN: Type inference failed for: r0v19, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.besome.sketch.editor.view.item.ItemHorizontalScrollView] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.besome.sketch.editor.view.item.ItemButton] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.besome.sketch.editor.view.item.ItemTextView] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.besome.sketch.editor.view.item.ItemEditText] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.besome.sketch.editor.view.item.ItemImageView] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.besome.sketch.editor.view.item.ItemWebView] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.besome.sketch.editor.view.item.ItemProgressBar] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.besome.sketch.editor.view.item.ItemListView] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.widget.RelativeLayout, com.besome.sketch.editor.view.ViewPane] */
    public View b(ViewBean viewBean) {
        ItemLinearLayout itemLinearLayout;
        int i = viewBean.type;
        switch (i) {
            case 0:
                itemLinearLayout = new ItemLinearLayout(getContext());
                break;
            case 1:
            case 16:
            default:
                itemLinearLayout = ViewPanes.a(i, getContext());
                break;
            case 2:
                itemLinearLayout = new ItemHorizontalScrollView(getContext());
                break;
            case 3:
                itemLinearLayout = new ItemButton(getContext());
                break;
            case 4:
                itemLinearLayout = new ItemTextView(getContext());
                break;
            case 5:
                itemLinearLayout = new ItemEditText(getContext());
                break;
            case 6:
                itemLinearLayout = new ItemImageView(getContext());
                break;
            case 7:
                itemLinearLayout = new ItemWebView(getContext());
                break;
            case 8:
                itemLinearLayout = new ItemProgressBar(getContext());
                break;
            case 9:
                itemLinearLayout = new ItemListView(getContext());
                break;
            case 10:
                itemLinearLayout = new ItemSpinner(getContext());
                break;
            case 11:
                itemLinearLayout = new ItemCheckBox(getContext());
                break;
            case 12:
                itemLinearLayout = new ItemVerticalScrollView(getContext());
                break;
            case 13:
                itemLinearLayout = new ItemSwitch(getContext());
                break;
            case 14:
                itemLinearLayout = new ItemSeekBar(getContext());
                break;
            case 15:
                itemLinearLayout = new ItemCalendarView(getContext());
                break;
            case 17:
                itemLinearLayout = new ItemAdView(getContext());
                break;
            case 18:
                itemLinearLayout = new ItemMapView(getContext());
                break;
        }
        int i2 = this.b + 1;
        this.b = i2;
        itemLinearLayout.setId(i2);
        itemLinearLayout.setTag(viewBean.id);
        itemLinearLayout.setBean(viewBean);
        b(itemLinearLayout, viewBean);
        return itemLinearLayout;
    }

    public void c(ViewBean viewBean) {
        a(viewBean, (ItemLinearLayout) this.a);
    }

    public ViewPane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = null;
        this.b = 99;
        this.c = new ArrayList<>();
        this.d = null;
        a(context);
    }

    public void setScId(String str) {
        this.sc_id = str;
    }

    public void a() {
        ViewBean viewBean = new ViewBean("root", 0);
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.height = -1;
        layoutBean.orientation = 1;
        viewBean.parentType = 0;
        View b = b(viewBean);
        ((ItemLinearLayout) b).setFixed(true);
        this.a = (ViewGroup) b;
        this.a.setBackgroundColor(-1118482);
        addView(b);
    }

    public final void b(View view, ViewBean viewBean) {
        ImageBean imageBean;
        String str;
        ExtraViewPane.a(view, viewBean, this, this.f);
        if (viewBean.id.charAt(0) == '_') {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin = (int) wB.a(getContext(), viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) wB.a(getContext(), viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) wB.a(getContext(), viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) wB.a(getContext(), viewBean.layout.marginBottom);
            int i = viewBean.layout.layoutGravity;
            if ((i & 3) == 3) {
                layoutParams.addRule(9);
            }
            if ((i & 48) == 48) {
                layoutParams.addRule(10);
            }
            if ((i & 5) == 5) {
                layoutParams.addRule(11);
            }
            if ((i & 80) == 80) {
                layoutParams.addRule(12);
            }
            if ((i & 1) == 1) {
                layoutParams.addRule(14);
            }
            if ((i & 16) == 16) {
                layoutParams.addRule(15);
            }
            if ((i & 17) == 17) {
                layoutParams.addRule(13);
            }
            view.setLayoutParams(layoutParams);
            if (viewBean.getClassInfo().b("FloatingActionButton") && (imageBean = viewBean.image) != null && (str = imageBean.resName) != null && str.length() > 0) {
                try {
                    Bitmap decodeFile = BitmapFactory.decodeFile(this.f.f(viewBean.image.resName));
                    int round = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((FloatingActionButton) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth() * round, decodeFile.getHeight() * round, true));
                } catch (Exception unused) {
                }
            }
            view.setRotation(viewBean.image.rotate);
            view.setAlpha(viewBean.alpha);
            view.setTranslationX(wB.a(getContext(), viewBean.translationX));
            view.setTranslationY(wB.a(getContext(), viewBean.translationY));
            view.setScaleX(viewBean.scaleX);
            view.setScaleY(viewBean.scaleY);
            view.setVisibility(0);
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
                if (this.f.h(str2) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
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
        if (classInfo.b("LinearLayout")) {
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
                    textView.setGravity(17);
                } else {
                    textView.setGravity(i2);
                }
            }
        }
        if (classInfo.b("EditText")) {
            a((EditText) view, viewBean);
        }
        if (classInfo.b("ImageView")) {
            if (this.f.h(viewBean.image.resName) == ProjectResourceBean.PROJECT_RES_TYPE_RESOURCE) {
                ((ImageView) view).setImageResource(getContext().getResources().getIdentifier(viewBean.image.resName, "drawable", getContext().getPackageName()));
            } else if (viewBean.image.resName.equals("default_image")) {
                ((ImageView) view).setImageResource(2131165522);
            } else {
                try {
                    Bitmap decodeFile3 = BitmapFactory.decodeFile(this.f.f(viewBean.image.resName));
                    int round3 = Math.round(getResources().getDisplayMetrics().density / 2.0f);
                    ((ImageView) view).setImageBitmap(Bitmap.createScaledBitmap(decodeFile3, decodeFile3.getWidth() * round3, decodeFile3.getHeight() * round3, true));
                } catch (Exception unused2) {
                    ((ImageView) view).setImageResource(2131165522);
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
        view.setVisibility(0);
    }

    public sy a(String str) {
        View findViewWithTag;
        if (str.charAt(0) == '_') {
            findViewWithTag = findViewWithTag(str);
        } else {
            findViewWithTag = this.a.findViewWithTag(str);
        }
        if (findViewWithTag != null && (findViewWithTag instanceof sy)) {
            return (sy) findViewWithTag;
        }
        return null;
    }

    public void a(ViewBean viewBean, int i, int i2) {
        Object[] objArr = this.d;
        if (objArr != null) {
            View view = (View) objArr[1];
            if (view instanceof LinearLayout) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) objArr[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 0;
                return;
            } else if (view instanceof ItemVerticalScrollView) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) objArr[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 12;
                viewBean.layout.height = -2;
                return;
            } else if (view instanceof ItemHorizontalScrollView) {
                viewBean.preIndex = viewBean.index;
                viewBean.index = ((Integer) objArr[2]).intValue();
                viewBean.preParent = viewBean.parent;
                viewBean.parent = view.getTag().toString();
                viewBean.parentType = 2;
                viewBean.layout.width = -2;
                return;
            } else {
                return;
            }
        }
        viewBean.preIndex = viewBean.index;
        viewBean.preParent = viewBean.parent;
        viewBean.parent = "root";
        viewBean.parentType = 0;
        viewBean.index = -1;
    }

    public View a(ViewBean viewBean) {
        View findViewWithTag = findViewWithTag("_fab");
        if (findViewWithTag != null) {
            return findViewWithTag;
        }
        ItemFloatingActionButton itemFloatingActionButton = new ItemFloatingActionButton(getContext());
        itemFloatingActionButton.setTag("_fab");
        itemFloatingActionButton.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        itemFloatingActionButton.setMainColor(ProjectFile.getColor(this.sc_id, "color_accent"));
        itemFloatingActionButton.setFixed(true);
        if (viewBean == null) {
            ViewBean viewBean2 = new ViewBean("_fab", 16);
            LayoutBean layoutBean = viewBean2.layout;
            layoutBean.marginLeft = 16;
            layoutBean.marginTop = 16;
            layoutBean.marginRight = 16;
            layoutBean.marginBottom = 16;
            layoutBean.layoutGravity = 85;
            itemFloatingActionButton.setBean(viewBean2);
        } else {
            itemFloatingActionButton.setBean(viewBean);
        }
        addView(itemFloatingActionButton);
        b(itemFloatingActionButton, itemFloatingActionButton.getBean());
        return itemFloatingActionButton;
    }

    public void a(boolean z) {
        this.e.setVisibility(8);
        ViewParent parent = this.e.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this.e);
        }
        if (z) {
            this.d = null;
        }
    }

    public void a(int i, int i2, int i3, int i4) {
        Object[] a2 = a(i, i2);
        if (a2 == null) {
            a(true);
        } else if (this.d != a2) {
            a(true);
            ViewGroup viewGroup = (ViewGroup) a2[1];
            viewGroup.addView(this.e, ((Integer) a2[2]).intValue());
            if (viewGroup instanceof LinearLayout) {
                this.e.setLayoutParams(new LinearLayout.LayoutParams(i3, i4));
            } else if (viewGroup instanceof FrameLayout) {
                this.e.setLayoutParams(new FrameLayout.LayoutParams(i3, i4));
            } else {
                this.e.setLayoutParams(new RelativeLayout.LayoutParams(i3, i4));
            }
            this.e.setVisibility(0);
            this.d = a2;
        }
    }

    public Object[] a(int i, int i2) {
        Object[] objArr = null;
        int i3 = -1;
        for (int i4 = 0; i4 < this.c.size(); i4++) {
            Object[] objArr2 = this.c.get(i4);
            Rect rect = (Rect) objArr2[0];
            if (i >= rect.left && i < rect.right && i2 >= rect.top && i2 < rect.bottom && i3 < ((Integer) objArr2[3]).intValue()) {
                i3 = ((Integer) objArr2[3]).intValue();
                objArr = objArr2;
            }
        }
        return objArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0288  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void a(ViewBean viewBean, ItemLinearLayout itemLinearLayout) {
        int i;
        int measuredHeight;
        int i2;
        char c;
        int i3;
        int i4;
        int i5 = 2;
        int[] iArr = new int[2];
        itemLinearLayout.getLocationOnScreen(iArr);
        int layoutGravity = itemLinearLayout.getLayoutGravity();
        int i6 = layoutGravity & 7;
        int i7 = layoutGravity & 112;
        int i8 = iArr[0];
        int i9 = 1;
        int i10 = iArr[1];
        a(new Rect(i8, i10, ((int) (itemLinearLayout.getWidth() * getScaleX())) + i8, ((int) (itemLinearLayout.getHeight() * getScaleY())) + i10), itemLinearLayout, -1, b(itemLinearLayout));
        int paddingLeft = iArr[0] + ((int) (itemLinearLayout.getPaddingLeft() * getScaleX()));
        int paddingTop = iArr[1] + ((int) (itemLinearLayout.getPaddingTop() * getScaleY()));
        int childCount = itemLinearLayout.getChildCount();
        int i11 = paddingLeft;
        int i12 = 0;
        int i13 = 0;
        while (i12 < childCount) {
            View childAt = itemLinearLayout.getChildAt(i12);
            if (childAt == null || childAt.getTag() == null || !((viewBean == null || viewBean.id == null || !childAt.getTag().equals(viewBean.id)) && childAt.getVisibility() == 0)) {
                i = childCount;
            } else {
                int[] iArr2 = new int[i5];
                childAt.getLocationOnScreen(iArr2);
                if (itemLinearLayout.getOrientation() == 0) {
                    int i14 = ((LinearLayout.LayoutParams) childAt.getLayoutParams()).leftMargin;
                    int i15 = ((LinearLayout.LayoutParams) childAt.getLayoutParams()).rightMargin;
                    if (i6 == i9) {
                        if (i12 == 0) {
                            int scaleX = iArr2[0] - ((int) (i14 * getScaleX()));
                            int i16 = iArr[i9];
                            i = childCount;
                            a(new Rect(i11, i16, scaleX, ((int) (itemLinearLayout.getMeasuredHeight() * getScaleY())) + i16), itemLinearLayout, 0, b(itemLinearLayout) + 1);
                            i11 = scaleX;
                        } else {
                            i = childCount;
                        }
                        i4 = ((int) ((i14 + childAt.getMeasuredWidth() + i15) * getScaleX())) + i11;
                        measuredHeight = iArr[1];
                        i2 = i13 + 1;
                        a(new Rect(i11, measuredHeight, i4, ((int) (itemLinearLayout.getMeasuredHeight() * getScaleY())) + measuredHeight), itemLinearLayout, i13, b(itemLinearLayout) + 1);
                    } else {
                        i = childCount;
                        if (i6 == 5) {
                            int scaleX2 = iArr2[0] - ((int) (i14 * getScaleX()));
                            int i17 = iArr[1];
                            a(new Rect(i11, i17, scaleX2, ((int) (itemLinearLayout.getMeasuredHeight() * getScaleY())) + i17), itemLinearLayout, i13, b(itemLinearLayout) + 1);
                            measuredHeight = i17;
                            i4 = (int) ((iArr2[0] + childAt.getMeasuredWidth() + i15) * getScaleX());
                            i2 = i13 + 1;
                        } else {
                            i4 = ((int) ((i14 + childAt.getMeasuredWidth() + i15) * getScaleX())) + i11;
                            measuredHeight = iArr[1];
                            i2 = i13 + 1;
                            a(new Rect(i11, measuredHeight, i4, ((int) (itemLinearLayout.getMeasuredHeight() * getScaleY())) + measuredHeight), itemLinearLayout, i13, b(itemLinearLayout) + 1);
                        }
                    }
                } else {
                    i = childCount;
                    int i18 = ((LinearLayout.LayoutParams) childAt.getLayoutParams()).topMargin;
                    int i19 = ((LinearLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin;
                    if (i7 == 16) {
                        if (i12 == 0) {
                            i3 = iArr2[1] - ((int) (i18 * getScaleY()));
                            int i20 = iArr[0];
                            c = 0;
                            a(new Rect(i20, paddingTop, ((int) (itemLinearLayout.getMeasuredWidth() * getScaleX())) + i20, i3), itemLinearLayout, 0, b(itemLinearLayout) + 1);
                        } else {
                            c = 0;
                            i3 = paddingTop;
                        }
                        measuredHeight = i3 + ((int) ((i18 + childAt.getMeasuredHeight() + i19) * getScaleY()));
                        i4 = iArr[c];
                        Rect rect = new Rect(i4, i3, ((int) (itemLinearLayout.getMeasuredWidth() * getScaleX())) + i4, measuredHeight);
                        i2 = i13 + 1;
                        a(rect, itemLinearLayout, i13, b(itemLinearLayout) + 1);
                    } else if (i7 == 80) {
                        int scaleY = iArr2[1] - ((int) (i18 * getScaleY()));
                        i11 = iArr[0];
                        a(new Rect(i11, paddingTop, ((int) (itemLinearLayout.getMeasuredWidth() * getScaleX())) + i11, scaleY), itemLinearLayout, i13, b(itemLinearLayout) + 1);
                        measuredHeight = (int) ((iArr2[1] + childAt.getMeasuredHeight() + i19) * getScaleY());
                        i2 = i13 + 1;
                        if (childAt instanceof ItemLinearLayout) {
                            a(viewBean, (ItemLinearLayout) childAt);
                        } else if (childAt instanceof ItemHorizontalScrollView) {
                            a(viewBean, (ViewGroup) childAt);
                        } else if (childAt instanceof ItemVerticalScrollView) {
                            a(viewBean, (ViewGroup) childAt);
                        }
                        i13 = i2;
                        paddingTop = measuredHeight;
                    } else {
                        measuredHeight = paddingTop + ((int) ((i18 + childAt.getMeasuredHeight() + i19) * getScaleY()));
                        int i21 = iArr[0];
                        a(new Rect(i21, paddingTop, ((int) (itemLinearLayout.getMeasuredWidth() * getScaleX())) + i21, measuredHeight), itemLinearLayout, i13, b(itemLinearLayout) + 1);
                        i11 = i21;
                        i2 = i13 + 1;
                        if (childAt instanceof ItemLinearLayout) {
                        }
                        i13 = i2;
                        paddingTop = measuredHeight;
                    }
                }
                i11 = i4;
                if (childAt instanceof ItemLinearLayout) {
                }
                i13 = i2;
                paddingTop = measuredHeight;
            }
            i12++;
            childCount = i;
            i5 = 2;
            i9 = 1;
        }
    }

    public final void a(ViewBean viewBean, ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null && childAt.getTag() != null && ((viewBean == null || viewBean.id == null || !childAt.getTag().equals(viewBean.id)) && childAt.getVisibility() == 0)) {
                i++;
                if (childAt instanceof ItemLinearLayout) {
                    a(viewBean, (ItemLinearLayout) childAt);
                } else if (childAt instanceof ItemHorizontalScrollView) {
                    a(viewBean, (ViewGroup) childAt);
                } else if (childAt instanceof ItemVerticalScrollView) {
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

    public final void a(Rect rect, View view, int i, int i2) {
        this.c.add(new Object[]{rect, view, Integer.valueOf(i), Integer.valueOf(i2)});
    }

    public void a(View view) {
        ViewBean bean = ((sy) view).getBean();
        ViewGroup viewGroup = (ViewGroup) this.a.findViewWithTag(bean.parent);
        viewGroup.addView(view, bean.index);
        if (viewGroup instanceof ty) {
            ((ty) viewGroup).a();
        }
    }

    public final void a(View view, ViewBean viewBean) {
        LayoutBean layoutBean = viewBean.layout;
        int i = layoutBean.width;
        int i2 = layoutBean.height;
        if (i > 0) {
            i = (int) wB.a(getContext(), viewBean.layout.width);
        }
        if (i2 > 0) {
            i2 = (int) wB.a(getContext(), viewBean.layout.height);
        }
        view.setBackgroundColor(viewBean.layout.backgroundColor);
        if (viewBean.id.equals("root")) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = (int) wB.a(getContext(), viewBean.layout.marginLeft);
            layoutParams.topMargin = (int) wB.a(getContext(), viewBean.layout.marginTop);
            layoutParams.rightMargin = (int) wB.a(getContext(), viewBean.layout.marginRight);
            layoutParams.bottomMargin = (int) wB.a(getContext(), viewBean.layout.marginBottom);
            LayoutBean layoutBean2 = viewBean.layout;
            view.setPadding(layoutBean2.paddingLeft, layoutBean2.paddingTop, layoutBean2.paddingRight, layoutBean2.paddingBottom);
            view.setLayoutParams(layoutParams);
        } else if (viewBean.parentType == 0) {
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, i2);
            layoutParams2.leftMargin = (int) wB.a(getContext(), viewBean.layout.marginLeft);
            layoutParams2.topMargin = (int) wB.a(getContext(), viewBean.layout.marginTop);
            layoutParams2.rightMargin = (int) wB.a(getContext(), viewBean.layout.marginRight);
            layoutParams2.bottomMargin = (int) wB.a(getContext(), viewBean.layout.marginBottom);
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
            layoutParams3.leftMargin = (int) wB.a(getContext(), viewBean.layout.marginLeft);
            layoutParams3.topMargin = (int) wB.a(getContext(), viewBean.layout.marginTop);
            layoutParams3.rightMargin = (int) wB.a(getContext(), viewBean.layout.marginRight);
            layoutParams3.bottomMargin = (int) wB.a(getContext(), viewBean.layout.marginBottom);
            LayoutBean layoutBean4 = viewBean.layout;
            view.setPadding(layoutBean4.paddingLeft, layoutBean4.paddingTop, layoutBean4.paddingRight, layoutBean4.paddingBottom);
            int i4 = viewBean.layout.layoutGravity;
            if (i4 != 0) {
                layoutParams3.gravity = i4;
            }
            view.setLayoutParams(layoutParams3);
        }
    }

    public final void a(TextView textView, ViewBean viewBean) {
        String str = viewBean.text.text;
        if (str != null && str.length() > 0 && str.indexOf("\\n") >= 0) {
            str = viewBean.text.text.replaceAll("\\\\n", "\n");
        }
        textView.setText(str);
        textView.setTypeface(null, viewBean.text.textType);
        textView.setTextColor(viewBean.text.textColor);
        textView.setTextSize(viewBean.text.textSize);
        textView.setLines(viewBean.text.line);
        textView.setSingleLine(viewBean.text.singleLine != 0);
    }

    public final void a(EditText editText, ViewBean viewBean) {
        editText.setHint(viewBean.text.hint);
        editText.setHintTextColor(viewBean.text.hintColor);
    }
}
