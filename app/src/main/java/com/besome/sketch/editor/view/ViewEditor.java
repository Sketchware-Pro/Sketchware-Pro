package com.besome.sketch.editor.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.beans.WidgetCollectionBean;
import com.besome.sketch.editor.view.item.ItemHorizontalScrollView;
import com.besome.sketch.editor.view.item.ItemVerticalScrollView;
import com.besome.sketch.editor.view.palette.IconAdView;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.editor.view.palette.IconLinearHorizontal;
import com.besome.sketch.editor.view.palette.IconLinearVertical;
import com.besome.sketch.editor.view.palette.IconMapView;
import com.besome.sketch.editor.view.palette.PaletteFavorite;
import com.besome.sketch.editor.view.palette.PaletteWidget;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.Iw;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a._x;
import a.a.a.aB;
import a.a.a.ay;
import a.a.a.bB;
import a.a.a.cC;
import a.a.a.cy;
import a.a.a.jC;
import a.a.a.oB;
import a.a.a.sy;
import a.a.a.uy;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.editor.view.IdGenerator;
import mod.hey.studios.util.Helper;
import mod.hey.studios.util.ProjectFile;

@SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
public class ViewEditor extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {

    private final int[] G = new int[2];
    private final Handler s = new Handler();
    public boolean isLayoutChanged = true;
    public PaletteWidget paletteWidget;
    private ObjectAnimator animatorTranslateX;
    private boolean isAnimating = false;
    private boolean C = false;
    private boolean D = false;
    private sy H;
    private int I = 50;
    private int J = 30;
    private boolean isVibrationEnabled;
    private cy L;
    private Iw M;
    private _x draggingListener;
    private ay O;
    private ProjectFileBean projectFileBean;
    private boolean S = true;
    private boolean T = false;
    private LinearLayout paletteGroup;
    private PaletteGroupItem basicPalette;
    private PaletteGroupItem favoritePalette;
    private String a;
    private LinearLayout aa;
    private String b;
    private int screenType;
    private boolean da = true;
    private int[] e = new int[20];
    private float f = 0;
    private int displayWidth;
    private int displayHeight;
    private PaletteFavorite paletteFavorite;
    private LinearLayout k;
    private TextView l;
    private ImageView imgPhoneTopBg;
    private LinearLayout n;
    private ViewPane viewPane;
    private Vibrator vibrator;
    private View r = null;
    private boolean t = false;
    private float u = 0;
    private float v = 0;
    private int scaledTouchSlop = 0;
    private ViewDummy dummyView;
    private ImageView deleteIcon;
    private ObjectAnimator animatorTranslateY;
    private final Runnable ea = this::e;

    public ViewEditor(Context context) {
        super(context);
        initialize(context);
    }

    public ViewEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void animateUpDown() {
        animatorTranslateY = ObjectAnimator.ofFloat(deleteIcon, "TranslationY", 0.0f);
        animatorTranslateY.setDuration(500L);
        animatorTranslateY.setInterpolator(new DecelerateInterpolator());
        animatorTranslateX = ObjectAnimator.ofFloat(deleteIcon, "TranslationY", deleteIcon.getHeight());
        animatorTranslateX.setDuration(300L);
        animatorTranslateX.setInterpolator(new DecelerateInterpolator());
        isAnimating = true;
    }

    private void g() {
        basicPalette = new PaletteGroupItem(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1.0f;
        basicPalette.setLayoutParams(layoutParams);
        basicPalette.a(PaletteGroup.BASIC);
        basicPalette.setSelected(true);
        basicPalette.setOnClickListener(v -> {
            showPaletteWidget();
            basicPalette.animate().scaleX(1).scaleY(1).alpha(1).start();
            favoritePalette.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
            basicPalette.setSelected(true);
            favoritePalette.setSelected(false);
        });
        favoritePalette = new PaletteGroupItem(getContext());
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams2.weight = 1.0f;
        favoritePalette.setLayoutParams(layoutParams2);
        favoritePalette.a(PaletteGroup.FAVORITE);
        favoritePalette.setSelected(false);
        favoritePalette.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
        favoritePalette.setOnClickListener(v -> {
            showPaletteFavorite();
            basicPalette.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
            favoritePalette.animate().scaleX(1).scaleY(1).alpha(1).start();
            basicPalette.setSelected(false);
            favoritePalette.setSelected(true);
        });
        paletteGroup.addView(basicPalette);
        paletteGroup.addView(favoritePalette);
    }

    public ProjectFileBean getProjectFile() {
        return projectFileBean;
    }

    public void h() {
        viewPane.setResourceManager(jC.d(a));
    }

    public void i() {
        if (H != null) {
            H.setSelection(false);
            H = null;
        }
        if (L != null) L.a(false, "");
    }

    public void j() {
        viewPane.d();
        l();
        i();
    }

    public void k() {
        viewPane.e();
    }

    public void l() {
        e = new int[99];
    }

    private void m() {
        if (M != null) M.a(b, H.getBean());
    }

    private void showPaletteFavorite() {
        paletteWidget.setVisibility(View.GONE);
        paletteFavorite.setVisibility(View.VISIBLE);
    }

    private void showPaletteWidget() {
        paletteWidget.setVisibility(View.VISIBLE);
        paletteFavorite.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == R.id.btn_editproperties) {
            m();
        }
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isLayoutChanged) a();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        String str;
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.getPointerId(motionEvent.getActionIndex()) > 0) {
            return true;
        }
        if (view == viewPane) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                i();
                r = null;
            }
            return true;
        } else if (actionMasked == MotionEvent.ACTION_DOWN) {
            t = false;
            u = motionEvent.getRawX();
            v = motionEvent.getRawY();
            r = view;
            if ((view instanceof sy) && ((sy) view).getFixed()) {
                return true;
            }
            if (isInsideItemScrollView(view) && draggingListener != null) {
                draggingListener.b();
            }
            s.postDelayed(ea, ViewConfiguration.getLongPressTimeout() / 2);
            return true;
        } else if (actionMasked != MotionEvent.ACTION_UP) {
            if (actionMasked != MotionEvent.ACTION_MOVE) {
                if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_SCROLL) {
                    paletteWidget.setScrollEnabled(true);
                    paletteFavorite.setScrollEnabled(true);
                    if (draggingListener != null) {
                        draggingListener.d();
                    }
                    b(false);
                    dummyView.setDummyVisibility(View.GONE);
                    viewPane.b();
                    s.removeCallbacks(ea);
                    t = false;
                    return true;
                }
                return true;
            } else if (!t) {
                if (Math.abs(u - motionEvent.getRawX()) >= scaledTouchSlop || Math.abs(v - motionEvent.getRawY()) >= scaledTouchSlop) {
                    r = null;
                    s.removeCallbacks(ea);
                    return true;
                }
                return true;
            } else {
                s.removeCallbacks(ea);
                dummyView.a(view, motionEvent.getRawX(), motionEvent.getRawY(), u, v);
                if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                    dummyView.setAllow(true);
                    updateDeleteIcon(true);
                    return true;
                }
                if (D) updateDeleteIcon(false);
                if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                    dummyView.setAllow(true);
                    boolean isNotIcon = !isViewAnIconBase(r);
                    int i = isNotIcon ? r.getWidth() : (r instanceof IconLinearHorizontal ?
                            ViewGroup.LayoutParams.MATCH_PARENT : I);
                    int i2 = isNotIcon ? r.getHeight() : (r instanceof IconLinearVertical ?
                            ViewGroup.LayoutParams.MATCH_PARENT : J);
                    viewPane.a((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), i, i2);
                } else {
                    dummyView.setAllow(false);
                    viewPane.a(true);
                }
                return true;
            }
        } else if (!t) {
            if (r instanceof sy sy) {
                a(sy, true);
            }
            if (draggingListener != null) {
                draggingListener.d();
            }
            dummyView.setDummyVisibility(View.GONE);
            r = null;
            viewPane.b();
            s.removeCallbacks(ea);
            return true;
        } else {
            lol:
            if (dummyView.getAllow()) {
                if (D && r instanceof sy widget) {
                    ArrayList<ViewBean> b2 = jC.a(a).b(b, widget.getBean());
                    for (int size = b2.size() - 1; size >= 0; size--) {
                        jC.a(a).a(projectFileBean, b2.get(size));
                    }
                    b(b2, true);
                    break lol;
                }
                if (D && r instanceof uy collectionWidget) {
                    deleteWidgetFromCollection(collectionWidget.getName());
                    break lol;
                }
                viewPane.a(false);
                if (r instanceof uy uyVar) {
                    ArrayList<ViewBean> arrayList = new ArrayList<>();
                    oB oBVar = new oB();
                    boolean areImagesAdded = false;
                    for (int i3 = 0; i3 < uyVar.getData().size(); i3++) {
                        ViewBean viewBean = uyVar.getData().get(i3);
                        if (c(viewBean)) {
                            arrayList.add(viewBean.clone());
                            String backgroundResource = viewBean.layout.backgroundResource;
                            String resName = viewBean.image.resName;
                            if (!jC.d(a).l(backgroundResource) && Op.g().b(backgroundResource)) {
                                ProjectResourceBean a2 = Op.g().a(backgroundResource);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + a + File.separator + a2.resFullName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                jC.d(a).b.add(a2);
                                areImagesAdded = true;
                            }
                            if (!jC.d(a).l(resName) && Op.g().b(resName)) {
                                ProjectResourceBean a3 = Op.g().a(resName);
                                try {
                                    oBVar.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a3.resFullName, wq.g() + File.separator + a + File.separator + a3.resFullName);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                jC.d(a).b.add(a3);
                                areImagesAdded = true;
                            }
                        }
                    }
                    if (areImagesAdded) {
                        bB.a(getContext(), xB.b().a(getContext(), R.string.view_widget_favorites_image_auto_added), bB.TOAST_NORMAL).show();
                    }
                    if (arrayList.size() > 0) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        viewPane.a(arrayList.get(0), (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                        for (ViewBean next : arrayList) {
                            if (jC.a(a).h(projectFileBean.getXmlName(), next.id)) {
                                hashMap.put(next.id, a(next.type));
                            } else {
                                hashMap.put(next.id, next.id);
                            }
                            next.id = hashMap.get(next.id);
                            if (arrayList.indexOf(next) != 0 && (str = next.parent) != null && str.length() > 0) {
                                next.parent = hashMap.get(next.parent);
                            }
                            jC.a(a).a(b, next);
                        }
                        a(a(arrayList, true), true);
                    }
                } else if (r instanceof IconBase icon) {
                    ViewBean bean = icon.getBean();
                    bean.id = IdGenerator.getId(this, bean.type, bean);
                    viewPane.a(bean, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    jC.a(a).a(b, bean);
                    if (bean.type == 3 && projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                        jC.a(a).a(projectFileBean.getJavaName(), 1, bean.type, bean.id, "onClick");
                    }
                    a(a(bean, true), true);
                } else if (r instanceof sy sy) {
                    ViewBean bean = sy.getBean();
                    viewPane.a(bean, (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    a(b(bean, true), true);
                }
            } else {
                if (r instanceof sy) {
                    r.setVisibility(View.VISIBLE);
                }
            }
            paletteWidget.setScrollEnabled(true);
            paletteFavorite.setScrollEnabled(true);
            if (draggingListener != null) {
                draggingListener.d();
            }
            b(false);
            dummyView.setDummyVisibility(View.GONE);
            r = null;
            viewPane.b();
            s.removeCallbacks(ea);
            t = false;
            return true;
        }
    }

    public void setFavoriteData(ArrayList<WidgetCollectionBean> arrayList) {
        clearCollectionWidget();
        for (WidgetCollectionBean next : arrayList) {
            addFavoriteViews(next.name, next.widgets);
        }
    }

    public void setIsAdLoaded(boolean z) {
        da = z;
    }

    public void setOnDraggingListener(_x dragListener) {
        draggingListener = dragListener;
    }

    public void setOnHistoryChangeListener(ay ayVar) {
        O = ayVar;
    }

    public void setOnPropertyClickListener(Iw iw) {
        M = iw;
    }

    public void setOnWidgetSelectedListener(cy cyVar) {
        L = cyVar;
    }

    public void setPaletteLayoutVisible(int i) {
        paletteWidget.setLayoutVisible(i);
    }

    public void setScreenType(int i) {
        if (i == 1) {
            screenType = 0;
        } else {
            screenType = 1;
        }
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.view_editor);
        paletteWidget = findViewById(R.id.palette_widget);
        paletteFavorite = findViewById(R.id.palette_favorite);
        dummyView = findViewById(R.id.dummy);
        deleteIcon = findViewById(R.id.icon_delete);
        FrameLayout shape = findViewById(R.id.shape);
        paletteGroup = findViewById(R.id.palette_group);
        g();
        findViewById(R.id.btn_editproperties).setOnClickListener(this);
        findViewById(R.id.img_close).setOnClickListener(this);
        f = wB.a(context, 1.0f);
        I = (int) (I * f);
        J = (int) (J * f);
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        displayHeight = getResources().getDisplayMetrics().heightPixels;
        aa = new LinearLayout(context);
        aa.setOrientation(LinearLayout.VERTICAL);
        aa.setGravity(Gravity.CENTER);
        aa.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, displayHeight));
        shape.addView(aa);
        k = new LinearLayout(context);
        k.setBackgroundColor(0xff0084c2);
        k.setOrientation(LinearLayout.HORIZONTAL);
        k.setGravity(Gravity.CENTER_VERTICAL);
        k.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, (int) (f * 25.0f)));
        l = new TextView(context);
        l.setTextColor(Color.WHITE);
        l.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        l.setPadding((int) (f * 8.0f), 0, 0, 0);
        l.setGravity(Gravity.CENTER_VERTICAL);
        k.addView(l);
        imgPhoneTopBg = new ImageView(context);
        imgPhoneTopBg.setImageResource(R.drawable.phone_bg_top);
        imgPhoneTopBg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imgPhoneTopBg.setScaleType(ImageView.ScaleType.FIT_END);
        k.addView(imgPhoneTopBg);
        shape.addView(k);
        n = new LinearLayout(context);
        n.setBackgroundColor(0xff008dcd);
        n.setOrientation(LinearLayout.HORIZONTAL);
        n.setGravity(Gravity.CENTER_VERTICAL);
        n.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, (int) (f * 48.0f)));
        TextView tvToolbar = new TextView(context);
        tvToolbar.setTextColor(Color.WHITE);
        tvToolbar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvToolbar.setPadding((int) (f * 16.0f), 0, 0, 0);
        tvToolbar.setGravity(Gravity.CENTER_VERTICAL);
        tvToolbar.setTextSize(15.0f);
        tvToolbar.setText("Toolbar");
        tvToolbar.setTypeface(null, Typeface.BOLD);
        n.addView(tvToolbar);
        shape.addView(n);
        viewPane = new ViewPane(getContext());
        viewPane.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, displayHeight));
        shape.addView(viewPane);
        viewPane.setOnTouchListener(this);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        isVibrationEnabled = new DB(context, "P12").a("P12I0", true);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void b(ArrayList<ViewBean> arrayList, boolean z) {
        if (z) {
            cC.c(a).b(projectFileBean.getXmlName(), arrayList);
            if (O != null) {
                O.a();
            }
        }
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            d(arrayList.get(size));
        }
    }

    private void clearCollectionWidget() {
        paletteFavorite.a();
    }

    public void removeWidgetsAndLayouts() {
        paletteWidget.removeWidgetLayouts();
        paletteWidget.removeWidgets();
    }

    public sy e(ViewBean viewBean) {
        sy g = viewPane.g(viewBean);
        L.a();
        L.a(viewBean.id);
        return g;
    }

    private boolean c(ViewBean viewBean) {
        int i;
        int i2 = projectFileBean.fileType;
        if (i2 == 1) {
            int i3 = viewBean.type;
            if (i3 != 0 && i3 != 4 && i3 != 5 && i3 != 3 && i3 != 6 && i3 != 11 && i3 != 13 && i3 != 14 && i3 == 8) {
                return true;
            }
        } else if (i2 == 2 && (i = viewBean.type) != 0 && i != 12 && i != 2 && i != 4 && i != 5 && i != 3 && i != 6 && i != 11 && i != 13 && i != 14 && i == 8) {
            return true;
        }
        return true;
    }

    public void d(ViewBean viewBean) {
        viewPane.f(viewBean);
    }

    private void e() {
        if (r == null) return;
        if (isViewAnIconBase(r)) {
            if (r instanceof uy uyVar) {
                boolean isAdViewUsed = false;
                for (ViewBean view : uyVar.getData()) {
                    if (view.type == ViewBean.VIEW_TYPE_WIDGET_ADVIEW) {
                        isAdViewUsed = true;
                        break;
                    }
                }
                if (isAdViewUsed && !draggingListener.a()) {
                    bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                    return;
                }

                boolean isMapViewUsed = false;
                for (ViewBean view : uyVar.getData()) {
                    if (view.type == ViewBean.VIEW_TYPE_WIDGET_MAPVIEW) {
                        isMapViewUsed = true;
                        break;
                    }
                }
                if (isMapViewUsed && !draggingListener.c()) {
                    bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                    return;
                }
            } else if ((r instanceof IconAdView) && !draggingListener.a()) {
                bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                return;
            } else if ((r instanceof IconMapView) && !draggingListener.c()) {
                bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                return;
            }
        }
        paletteWidget.setScrollEnabled(false);
        paletteFavorite.setScrollEnabled(false);
        if (draggingListener != null) draggingListener.b();
        if (isVibrationEnabled) vibrator.vibrate(100L);
        t = true;
        dummyView.b(r);
        dummyView.bringToFront();
        i();
        dummyView.a(r, u, v, u, v);
        dummyView.a(G);
        if (isViewAnIconBase(r)) {
            if (r instanceof uy) {
                b(true);
                viewPane.e(null);
            } else {
                b(false);
                viewPane.e(null);
            }
        } else {
            r.setVisibility(View.GONE);
            b(true);
            viewPane.e(((sy) r).getBean());
        }
        if (b(u, v)) {
            dummyView.setAllow(true);
            boolean isNotIcon = !isViewAnIconBase(r);
            int i = isNotIcon ? r.getWidth() : (r instanceof IconLinearHorizontal ?
                    ViewGroup.LayoutParams.MATCH_PARENT : I);
            int i2 = isNotIcon ? r.getHeight() : (r instanceof IconLinearVertical ?
                    ViewGroup.LayoutParams.MATCH_PARENT : J);
            viewPane.a((int) u, (int) v, i, i2);
            return;
        }
        dummyView.setAllow(false);
        viewPane.a(true);
    }

    public sy b(ViewBean viewBean, boolean z) {
        if (z) {
            cC.c(a).b(projectFileBean.getXmlName(), viewBean);
            if (O != null) {
                O.a();
            }
        }
        return viewPane.d(viewBean);
    }

    public sy b(ViewBean viewBean) {
        View itemView = viewPane.b(viewBean);
        viewPane.a(itemView);
        String generatedId = wq.b(viewBean.type);
        if (viewBean.id.indexOf(generatedId) == 0 && viewBean.id.length() > generatedId.length()) {
            try {
                int intValue = Integer.parseInt(viewBean.id.substring(generatedId.length()));
                if (e[viewBean.type] < intValue) {
                    e[viewBean.type] = intValue;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        itemView.setOnTouchListener(this);
        return (sy) itemView;
    }

    private boolean isInsideItemScrollView(View view) {
        for (ViewParent parent = view.getParent(); parent != null && parent != this; parent = parent.getParent()) {
            if ((parent instanceof ItemVerticalScrollView) || (parent instanceof ItemHorizontalScrollView)) {
                return true;
            }
        }
        return false;
    }

    private boolean b(float f, float f2) {
        int[] locationOnScreen = new int[2];
        viewPane.getLocationOnScreen(locationOnScreen);
        return f > ((float) locationOnScreen[0]) && f < ((float) locationOnScreen[0]) + (((float) viewPane.getWidth()) * viewPane.getScaleX()) && f2 > ((float) locationOnScreen[1]) && f2 < ((float) locationOnScreen[1]) + (((float) viewPane.getHeight()) * viewPane.getScaleY());
    }

    private void deleteWidgetFromCollection(String str) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(xB.b().a(getContext(), R.string.view_widget_favorites_delete_title));
        aBVar.a(R.drawable.high_priority_96_red);
        aBVar.a(xB.b().a(getContext(), R.string.view_widget_favorites_delete_message));
        aBVar.b(xB.b().a(getContext(), R.string.common_word_delete), (d, which) -> {
            Rp.h().a(str, true);
            setFavoriteData(Rp.h().f());
            d.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        aBVar.show();
    }

    private void cancelAnimation() {
        if (animatorTranslateY.isRunning()) animatorTranslateY.cancel();
        if (animatorTranslateX.isRunning()) animatorTranslateX.cancel();
    }

    private void setPreviewColors(String str) {
        k.setBackgroundColor(ProjectFile.getColor(str, "color_primary_dark"));
        imgPhoneTopBg.setBackgroundColor(ProjectFile.getColor(str, "color_primary_dark"));
        n.setBackgroundColor(ProjectFile.getColor(str, "color_primary"));
    }

    private void b(boolean z) {
        deleteIcon.bringToFront();
        if (!isAnimating) {
            animateUpDown();
        }
        if (C == z) return;
        C = z;
        cancelAnimation();
        if (z) {
            animatorTranslateY.start();
        } else {
            animatorTranslateX.start();
        }
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        a = str;
        setPreviewColors(str);
        this.projectFileBean = projectFileBean;
        b = projectFileBean.getXmlName();
        if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_DRAWER) {
            l.setText(projectFileBean.fileName.substring(1));
        } else {
            l.setText(projectFileBean.getXmlName());
        }
        k();
        if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            S = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
            T = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN);
            if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                a(jC.a(str).h(projectFileBean.getXmlName()));
            }
        } else {
            S = false;
            T = false;
        }
        isLayoutChanged = true;
        if (viewPane != null) {
            viewPane.setScId(str);
        }
    }

    public void a(String str) {
        sy syVar;
        sy a2 = viewPane.a(str);
        if (a2 == null || (syVar = H) == a2) {
            return;
        }
        if (syVar != null) {
            syVar.setSelection(false);
        }
        a2.setSelection(true);
        H = a2;
    }

    private void a() {
        n.setVisibility(S ? View.VISIBLE : View.GONE);
        k.setVisibility(T ? View.GONE : View.VISIBLE);

        viewPane.setVisibility(View.VISIBLE);
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        displayHeight = getResources().getDisplayMetrics().heightPixels;
        boolean isLandscapeMode = displayWidth > displayHeight;
        int var4 = (int) (f * (!isLandscapeMode ? 12.0F : 24.0F));
        int var5 = (int) (f * (!isLandscapeMode ? 20.0F : 10.0F));
        int statusBarHeight = GB.f(getContext());
        int toolBarHeight = GB.a(getContext());
        int var9 = displayWidth - (int) (120.0F * f);
        int var8 = displayHeight - statusBarHeight - toolBarHeight - (int) (f * 48.0F) - (int) (f * 48.0F);
        if (screenType == 0) {
            if (da) {
                var8 -= (int) (f * 56.0F);
            }
        }

        float var11 = Math.min((float) var9 / (float) displayWidth, (float) var8 / (float) displayHeight);
        float var3 = Math.min((float) (var9 - var4 * 2) / (float) displayWidth, (float) (var8 - var5 * 2) / (float) displayHeight);
        if (!isLandscapeMode) {
            aa.setBackgroundResource(R.drawable.new_view_pane_background_port);
        } else {
            aa.setBackgroundResource(R.drawable.new_view_pane_background_land);
        }

        aa.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, displayHeight));
        aa.setScaleX(var11);
        aa.setScaleY(var11);
        aa.setX((float) -((int) (((float) displayWidth - (float) displayWidth * var11) / 2.0F)));
        aa.setY((float) -((int) (((float) displayHeight - (float) displayHeight * var11) / 2.0F)));
        int var10 = var4 - (int) (((float) displayWidth - (float) displayWidth * var3) / 2.0F);
        int var13 = var5;
        if (k.getVisibility() == View.VISIBLE) {
            k.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, statusBarHeight));
            k.setScaleX(var3);
            k.setScaleY(var3);
            var11 = (float) statusBarHeight;
            float var12 = var11 * var3;
            k.setX((float) var10);
            k.setY((float) (var5 - (int) ((var11 - var12) / 2.0F)));
            var13 = var5 + (int) var12;
        }

        var8 = var13;
        if (n.getVisibility() == View.VISIBLE) {
            n.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, toolBarHeight));
            n.setScaleX(var3);
            n.setScaleY(var3);
            float var12 = (float) toolBarHeight;
            var11 = var12 * var3;
            n.setX((float) var10);
            n.setY((float) (var13 - (int) ((var12 - var11) / 2.0F)));
            var8 = var13 + (int) var11;
        }

        var13 = displayHeight;
        if (k.getVisibility() == View.VISIBLE) {
            var13 = displayHeight - statusBarHeight;
        }

        var5 = var13;
        if (n.getVisibility() == View.VISIBLE) {
            var5 = var13 - toolBarHeight;
        }

        viewPane.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, var5));
        viewPane.setScaleX(var3);
        viewPane.setScaleY(var3);
        var11 = (float) var5;
        viewPane.setX((float) var10);
        viewPane.setY((float) (var8 - (int) ((var11 - var3 * var11) / 2.0F)));
        isLayoutChanged = false;
    }

    public void addWidgetLayout(PaletteWidget.a aVar, String str) {
        View widget = paletteWidget.a(aVar, str);
        widget.setClickable(true);
        widget.setOnTouchListener(this);
    }

    public void extraWidgetLayout(String str, String str2) {
        View extraWidgetLayout = paletteWidget.extraWidgetLayout(str, str2);
        extraWidgetLayout.setClickable(true);
        extraWidgetLayout.setOnTouchListener(this);
    }

    public void addWidget(PaletteWidget.b bVar, String str, String str2, String str3) {
        View widget = paletteWidget.a(bVar, str, str2, str3);
        widget.setClickable(true);
        widget.setOnTouchListener(this);
    }

    public void extraWidget(String str, String str2, String str3) {
        View extraWidget = paletteWidget.extraWidget(str, str2, str3);
        extraWidget.setClickable(true);
        extraWidget.setOnTouchListener(this);
    }

    private boolean isViewAnIconBase(View view) {
        return view instanceof IconBase;
    }

    private void addFavoriteViews(String str, ArrayList<ViewBean> arrayList) {
        View a2 = paletteFavorite.a(str, arrayList);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
    }

    public final String a(int i) {
        String b2 = wq.b(i);
        StringBuilder sb = new StringBuilder();
        sb.append(b2);
        int i2 = e[i] + 1;
        e[i] = i2;
        sb.append(i2);
        String sb2 = sb.toString();
        ArrayList<ViewBean> d = jC.a(a).d(b);
        while (true) {
            boolean isIdUsed = false;
            for (ViewBean view : d) {
                if (sb2.equals(view.id)) {
                    isIdUsed = true;
                    break;
                }
            }
            if (!isIdUsed) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b2);
            int i3 = e[i] + 1;
            e[i] = i3;
            sb3.append(i3);
            sb2 = sb3.toString();
        }
    }

    public sy a(ArrayList<ViewBean> arrayList, boolean z) {
        if (z) {
            cC.c(a).a(projectFileBean.getXmlName(), arrayList);
            if (O != null) {
                O.a();
            }
        }
        sy syVar = null;
        for (ViewBean view : arrayList) {
            if (arrayList.indexOf(view) == 0) {
                syVar = b(view);
            } else {
                b(view);
            }
        }
        return syVar;
    }

    public sy a(ViewBean viewBean, boolean z) {
        if (z) {
            cC.c(a).a(projectFileBean.getXmlName(), viewBean);
            if (O != null) {
                O.a();
            }
        }
        return b(viewBean);
    }

    public void a(ArrayList<ViewBean> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        for (ViewBean view : arrayList) {
            b(view);
        }
    }

    public void a(ViewBean viewBean) {
        viewPane.a(viewBean).setOnTouchListener(this);
    }

    public void a(sy syVar, boolean z) {
        if (H != null) {
            H.setSelection(false);
        }
        H = syVar;
        H.setSelection(true);
        if (L != null) {
            L.a(z, H.getBean().id);
        }
    }

    private boolean a(float x, float y) {
        int[] locationOnScreen = new int[2];
        deleteIcon.getLocationOnScreen(locationOnScreen);
        return x > ((float) locationOnScreen[0]) && x < ((float) (locationOnScreen[0] + deleteIcon.getWidth())) && y > ((float) locationOnScreen[1]) && y < ((float) (locationOnScreen[1] + deleteIcon.getHeight()));
    }

    private void updateDeleteIcon(boolean z) {
        if (D == z) return;
        D = z;
        deleteIcon.setImageResource(D ? R.drawable.icon_delete_active : R.drawable.icon_delete);
    }

    enum PaletteGroup {
        BASIC,
        FAVORITE
    }

    static class PaletteGroupItem extends LinearLayout implements View.OnClickListener {

        private ImageView imgGroup;

        public PaletteGroupItem(Context context) {
            super(context);
            initialize(context);
        }

        private void initialize(Context context) {
            wB.a(context, this, R.layout.palette_group_item);
            imgGroup = findViewById(R.id.img_group);
        }

        @Override
        public void onClick(View view) {
        }

        public void a(PaletteGroup group) {
            imgGroup.setImageResource(group == PaletteGroup.BASIC ?
                    R.drawable.selector_palette_tab_ic_sketchware :
                    R.drawable.selector_palette_tab_ic_bookmark);
            setOnClickListener(this);
        }
    }
}
