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
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

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
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.Iw;
import a.a.a.Op;
import a.a.a.Rp;
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
import dev.aldi.sayuti.editor.view.palette.IconBottomNavigationView;
import dev.aldi.sayuti.editor.view.palette.IconCardView;
import dev.aldi.sayuti.editor.view.palette.IconCollapsingToolbar;
import dev.aldi.sayuti.editor.view.palette.IconMaterialButton;
import dev.aldi.sayuti.editor.view.palette.IconRecyclerView;
import dev.aldi.sayuti.editor.view.palette.IconSwipeRefreshLayout;
import dev.aldi.sayuti.editor.view.palette.IconTabLayout;
import dev.aldi.sayuti.editor.view.palette.IconTextInputLayout;
import dev.aldi.sayuti.editor.view.palette.IconViewPager;
import mod.agus.jcoderz.beans.ViewBeans;
import mod.hey.studios.util.Helper;
import mod.hey.studios.util.ProjectFile;
import pro.sketchware.R;
import pro.sketchware.utility.ThemeUtils;
import pro.sketchware.widgets.IconCustomWidget;
import pro.sketchware.widgets.WidgetsCreatorManager;

@SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
public class ViewEditor extends RelativeLayout implements View.OnClickListener, View.OnTouchListener {
    private final int[] G = new int[2];
    private final Handler handler = new Handler();
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
    private DraggingListener draggingListener;
    private ay O;
    private ProjectFileBean projectFileBean;
    private boolean S = true;
    private boolean T = false;
    private LinearLayout paletteGroup;
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
    private TextView fileName;
    private ImageView imgPhoneTopBg;
    private LinearLayout toolbar;
    private ViewPane viewPane;
    private Vibrator vibrator;
    private View r = null;
    private boolean t = false;
    private float u = 0;
    private float v = 0;
    private int scaledTouchSlop = 0;
    private ViewDummy dummyView;
    private ImageView deleteIcon;
    private TextView deleteText;
    private MaterialCardView deleteView;
    private ObjectAnimator animatorTranslateY;
    private final Runnable ea = this::e;

    private int colorSurfaceContainerHighest;
    private int colorCoolGreenContainer;
    private int colorCoolGreen;
    private int colorError;
    private int colorErrorContainer;

    public WidgetsCreatorManager widgetsCreatorManager;

    public ViewEditor(Context context) {
        this(context, null);
    }

    public ViewEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public static void shakeView(View view) {
        ObjectAnimator
                .ofFloat(view, "translationX", 0, 35, -35, 35, -35, 25, -25, 12, -12, 0)
                .setDuration(200)
                .start();
    }

    private void animateUpDown() {
        animatorTranslateY = ObjectAnimator.ofFloat(deleteView, "TranslationY", 0.0f);
        animatorTranslateY.setDuration(500L);
        animatorTranslateY.setInterpolator(new OvershootInterpolator());
        animatorTranslateX = ObjectAnimator.ofFloat(deleteView, "TranslationY", deleteView.getHeight() * 2);
        animatorTranslateX.setDuration(500L);
        animatorTranslateX.setInterpolator(new OvershootInterpolator());
        isAnimating = true;
    }

    private void addPaletteGroupItems() {
        LinearLayout.LayoutParams paletteLayoutParams =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        paletteLayoutParams.weight = 1f;

        PaletteGroupItem basicPalette = new PaletteGroupItem(getContext());
        basicPalette.setLayoutParams(paletteLayoutParams);
        basicPalette.setPaletteGroup(PaletteGroup.BASIC);
        basicPalette.setSelected(true);

        PaletteGroupItem favoritePalette = new PaletteGroupItem(getContext());
        favoritePalette.setLayoutParams(paletteLayoutParams);
        favoritePalette.setPaletteGroup(PaletteGroup.FAVORITE);
        favoritePalette.setSelected(false);
        favoritePalette.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();

        basicPalette.setOnClickListener(v -> {
            showPaletteWidget();
            basicPalette.animate().scaleX(1).scaleY(1).alpha(1).start();
            favoritePalette.animate().scaleX(0.9f).scaleY(0.9f).alpha(0.6f).start();
            basicPalette.setSelected(true);
            favoritePalette.setSelected(false);
        });

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
        viewPane.updateRootLayout(a, projectFileBean.getXmlName());
        viewPane.clearViewPane();
        l();
        i();
    }

    public void removeFab() {
        viewPane.removeFabView();
    }

    public void l() {
        e = new int[99];
    }

    private void m() {
        if (M != null) M.a(b, H.getBean());
    }

    private void showPaletteFavorite() {
        paletteWidget.animate()
                .alpha(0f)
                .setDuration(100)
                .withEndAction(() -> {
                    paletteWidget.setVisibility(View.GONE);
                    paletteFavorite.setAlpha(0f);
                    paletteFavorite.setVisibility(View.VISIBLE);
                    paletteFavorite.animate()
                            .alpha(1f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }

    private void showPaletteWidget() {
        paletteFavorite.animate()
                .alpha(0f)
                .setDuration(100)
                .withEndAction(() -> {
                    paletteFavorite.setVisibility(View.GONE);
                    paletteWidget.setAlpha(0f);
                    paletteWidget.setVisibility(View.VISIBLE);
                    paletteWidget.animate()
                            .alpha(1f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_editproperties) {
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
            if ((view instanceof sy bean) && bean.getFixed()) {
                return true;
            }
            if (isInsideItemScrollView(view) && draggingListener != null) {
                draggingListener.b();
            }
            handler.postDelayed(ea, ViewConfiguration.getLongPressTimeout() / 2);
            return true;
        } else if (actionMasked != MotionEvent.ACTION_UP) {
            if (actionMasked != MotionEvent.ACTION_MOVE) {
                if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_SCROLL) {
                    paletteWidget.setScrollEnabled(true);
                    paletteFavorite.setScrollEnabled(true);
                    if (draggingListener != null) {
                        draggingListener.d();
                    }
                    b(false, false);
                    dummyView.setDummyVisibility(View.GONE);
                    viewPane.clearViews();
                    handler.removeCallbacks(ea);
                    t = false;
                    return true;
                }
                return true;
            } else if (!t) {
                if (Math.abs(u - motionEvent.getRawX()) >= scaledTouchSlop || Math.abs(v - motionEvent.getRawY()) >= scaledTouchSlop) {
                    r = null;
                    handler.removeCallbacks(ea);
                    return true;
                }
                return true;
            } else {
                handler.removeCallbacks(ea);
                dummyView.a(view, motionEvent.getRawX(), motionEvent.getRawY(), u, v);
                if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                    dummyView.setAllow(true);
                    updateDeleteIcon(true, r instanceof IconCustomWidget);
                    return true;
                }
                if (D) updateDeleteIcon(false, r instanceof IconCustomWidget);
                if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                    dummyView.setAllow(true);
                    boolean isNotIcon = !isViewAnIconBase(r);
                    int width = isNotIcon ? r.getWidth() : (r instanceof IconLinearHorizontal ?
                            ViewGroup.LayoutParams.MATCH_PARENT : I);
                    int height = isNotIcon ? r.getHeight() : (r instanceof IconLinearVertical ?
                            ViewGroup.LayoutParams.MATCH_PARENT : J);
                    viewPane.updateView((int) motionEvent.getRawX(), (int) motionEvent.getRawY(), width, height);
                } else {
                    dummyView.setAllow(false);
                    viewPane.resetView(true);
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
            viewPane.clearViews();
            handler.removeCallbacks(ea);
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
                if (D && r instanceof IconCustomWidget) {
                    widgetsCreatorManager.showActionsDialog((int) view.getTag());
                    break lol;
                }
                viewPane.resetView(false);
                if (r instanceof uy uyVar) {
                    ArrayList<ViewBean> arrayList = new ArrayList<>();
                    oB oBVar = new oB();
                    boolean areImagesAdded = false;
                    for (int i3 = 0; i3 < uyVar.getData().size(); i3++) {
                        ViewBean viewBean = uyVar.getData().get(i3);
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
                    if (areImagesAdded) {
                        bB.a(getContext(), xB.b().a(getContext(), R.string.view_widget_favorites_image_auto_added), bB.TOAST_NORMAL).show();
                    }
                    if (!arrayList.isEmpty()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        viewPane.a(arrayList.get(0), (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                        for (ViewBean next : arrayList) {
                            if (jC.a(a).h(projectFileBean.getXmlName(), next.id)) {
                                hashMap.put(next.id, a(next));
                            } else {
                                hashMap.put(next.id, next.id);
                            }
                            next.id = hashMap.get(next.id);
                            if (arrayList.indexOf(next) != 0 && (str = next.parent) != null && !str.isEmpty()) {
                                next.parent = hashMap.get(next.parent);
                            }
                            jC.a(a).a(b, next);
                        }
                        a(a(arrayList, true), true);
                    }
                } else if (r instanceof IconBase icon) {
                    ViewBean bean = icon.getBean();
                    bean.id = a(bean);
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
            b(false, false);
            dummyView.setDummyVisibility(View.GONE);
            r = null;
            viewPane.clearViews();
            handler.removeCallbacks(ea);
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

    public void setOnDraggingListener(DraggingListener dragListener) {
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
        deleteText = findViewById(R.id.text_delete);
        deleteView = findViewById(R.id.delete_view);
        FrameLayout shape = findViewById(R.id.shape);
        paletteGroup = findViewById(R.id.palette_group);

        addPaletteGroupItems();

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
        k.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, (int) (f * 25f)));

        fileName = new TextView(context);
        fileName.setTextColor(Color.WHITE);
        fileName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        fileName.setPadding((int) (f * 8f), 0, 0, 0);
        fileName.setGravity(Gravity.CENTER_VERTICAL);
        k.addView(fileName);

        imgPhoneTopBg = new ImageView(context);
        imgPhoneTopBg.setImageResource(R.drawable.phone_bg_top);
        imgPhoneTopBg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imgPhoneTopBg.setScaleType(ImageView.ScaleType.FIT_END);
        k.addView(imgPhoneTopBg);
        shape.addView(k);

        toolbar = new LinearLayout(context);
        toolbar.setBackgroundColor(0xff008dcd);
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, (int) (f * 48f)));

        TextView tvToolbar = new TextView(context);
        tvToolbar.setTextColor(Color.WHITE);
        tvToolbar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tvToolbar.setPadding((int) (f * 16f), 0, 0, 0);
        tvToolbar.setGravity(Gravity.CENTER_VERTICAL);
        tvToolbar.setTextSize(15f);
        tvToolbar.setText("Toolbar");
        tvToolbar.setTypeface(null, Typeface.BOLD);
        toolbar.addView(tvToolbar);
        shape.addView(toolbar);

        viewPane = new ViewPane(getContext());
        viewPane.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, displayHeight));
        viewPane.setOnTouchListener(this);
        shape.addView(viewPane);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        isVibrationEnabled = new DB(context, "P12").a("P12I0", true);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        paletteWidget.cardView.setOnClickListener(view -> widgetsCreatorManager.showWidgetsCreatorDialog(-1));

        colorSurfaceContainerHighest = ThemeUtils.getColor(deleteView, R.attr.colorSurfaceContainerHighest);
        colorCoolGreenContainer = ThemeUtils.getColor(deleteView, R.attr.colorCoolGreenContainer);
        colorCoolGreen = ThemeUtils.getColor(deleteView, R.attr.colorCoolGreen);
        colorErrorContainer = ThemeUtils.getColor(deleteView, R.attr.colorErrorContainer);
        colorError = ThemeUtils.getColor(deleteView, R.attr.colorOnErrorContainer);

        initialDeleteViewUi();
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

    public void d(ViewBean viewBean) {
        viewPane.removeView(viewBean);
    }

    private void e() {
        if (r == null) return;
        if (isViewAnIconBase(r)) {
            boolean isAppCompatEnabled = jC.c(a).c().isEnabled();
            if (r instanceof uy collectionWidget) {
                var collectionData = collectionWidget.getData();
                boolean isAdViewUsed = false;
                for (ViewBean view : collectionData) {
                    if (view.type == ViewBean.VIEW_TYPE_WIDGET_ADVIEW) {
                        isAdViewUsed = true;
                        break;
                    }
                }
                if (isAdViewUsed && !draggingListener.isAdmobEnabled()) {
                    bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                    return;
                }

                boolean isMapViewUsed = false;
                for (ViewBean view : collectionData) {
                    if (view.type == ViewBean.VIEW_TYPE_WIDGET_MAPVIEW) {
                        isMapViewUsed = true;
                        break;
                    }
                }
                if (isMapViewUsed && !draggingListener.isGoogleMapEnabled()) {
                    bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                    return;
                }
                boolean isAppCompatViewUsed = false;
                for (ViewBean view : collectionData) {
                    switch (view.type) {
                        case ViewBeans.VIEW_TYPE_WIDGET_MATERIALBUTTON,
                             ViewBeans.VIEW_TYPE_WIDGET_RECYCLERVIEW,
                             ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW,
                             ViewBeans.VIEW_TYPE_LAYOUT_TABLAYOUT,
                             ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER,
                             ViewBeans.VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT,
                             ViewBeans.VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT,
                             ViewBeans.VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT,
                             ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW -> isAppCompatViewUsed = true;
                    }
                    if (isAppCompatViewUsed) {
                        break;
                    }
                }

                if (isAppCompatViewUsed && !isAppCompatEnabled) {
                    bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                    return;
                }
            } else if ((r instanceof IconAdView) && !draggingListener.isAdmobEnabled()) {
                bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                return;
            } else if ((r instanceof IconMapView) && !draggingListener.isGoogleMapEnabled()) {
                bB.b(getContext(), xB.b().a(getContext(), R.string.design_library_guide_setup_first), bB.TOAST_NORMAL).show();
                return;
            } else if (((r instanceof IconMaterialButton)
                        || (r instanceof IconRecyclerView)
                        || (r instanceof IconBottomNavigationView)
                        || (r instanceof IconTabLayout)
                        || (r instanceof IconViewPager)
                        || (r instanceof IconCollapsingToolbar)
                        || (r instanceof IconTextInputLayout)
                        || (r instanceof IconSwipeRefreshLayout)
                        || (r instanceof IconCardView))
                        && !isAppCompatEnabled) {
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
            if (r instanceof uy || r instanceof IconCustomWidget) {
                b(true, r instanceof IconCustomWidget);
                viewPane.addRootLayout(null);
            } else {
                b(false, false);
                viewPane.addRootLayout(null);
            }
        } else {
            r.setVisibility(View.GONE);
            b(true, r instanceof IconCustomWidget);
            viewPane.addRootLayout(((sy) r).getBean());
        }
        if (b(u, v)) {
            dummyView.setAllow(true);
            boolean isNotIcon = !isViewAnIconBase(r);
            int width = isNotIcon ? r.getWidth() : r instanceof IconLinearHorizontal ?
                    ViewGroup.LayoutParams.MATCH_PARENT : I;
            int height = isNotIcon ? r.getHeight() : r instanceof IconLinearVertical ?
                    ViewGroup.LayoutParams.MATCH_PARENT : J;
            viewPane.updateView((int) u, (int) v, width, height);
            return;
        }
        dummyView.setAllow(false);
        viewPane.resetView(true);
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

    public sy createAndAddView(ViewBean viewBean) {
        View itemView = viewPane.createItemView(viewBean);
        viewPane.addViewAndUpdateIndex(itemView);
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
            return parent instanceof ItemVerticalScrollView || parent instanceof ItemHorizontalScrollView;
        }
        return false;
    }

    private boolean b(float f, float f2) {
        int[] locationOnScreen = new int[2];
        viewPane.getLocationOnScreen(locationOnScreen);
        return f > locationOnScreen[0] && f < locationOnScreen[0] + (viewPane.getWidth() * viewPane.getScaleX()) && f2 > locationOnScreen[1] && f2 < locationOnScreen[1] + (viewPane.getHeight() * viewPane.getScaleY());
    }

    private void deleteWidgetFromCollection(String str) {
        aB dialog = new aB((Activity) getContext());
        dialog.b(xB.b().a(getContext(), R.string.view_widget_favorites_delete_title));
        dialog.a(R.drawable.ic_mtrl_delete);
        dialog.a(xB.b().a(getContext(), R.string.view_widget_favorites_delete_message));
        dialog.b(xB.b().a(getContext(), R.string.common_word_delete), v -> {
            Rp.h().a(str, true);
            setFavoriteData(Rp.h().f());
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void cancelAnimation() {
        if (animatorTranslateY.isRunning()) animatorTranslateY.cancel();
        if (animatorTranslateX.isRunning()) animatorTranslateX.cancel();
    }

    private void setPreviewColors(String str) {
        k.setBackgroundColor(ProjectFile.getColor(str, ProjectFile.COLOR_PRIMARY_DARK));
        imgPhoneTopBg.setBackgroundColor(ProjectFile.getColor(str, ProjectFile.COLOR_PRIMARY_DARK));
        toolbar.setBackgroundColor(ProjectFile.getColor(str, ProjectFile.COLOR_PRIMARY));
    }

    private void b(boolean z, boolean isCustomWidget) {
        if (isCustomWidget) {
            deleteIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_mtrl_edit));
            deleteText.setText("Drag here to see the Actions");
        } else if (z) {
            deleteIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_mtrl_delete));
            deleteText.setText("Drag here to delete");
            setDeleteViewIconAndTextUi(false);
        }
        deleteView.bringToFront();
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
            fileName.setText(projectFileBean.fileName.substring(1));
        } else {
            fileName.setText(projectFileBean.getXmlName());
        }
        removeFab();
        if (projectFileBean.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
            S = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
            T = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN);
            if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                addFab(jC.a(str).h(projectFileBean.getXmlName()));
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

    public void updateSelection(String tag) {
        sy syVar;
        sy itemView = viewPane.findItemViewByTag(tag);
        if (itemView == null || (syVar = H) == itemView) {
            return;
        }
        if (syVar != null) {
            syVar.setSelection(false);
        }
        itemView.setSelection(true);
        H = itemView;
    }

    private void a() {
        toolbar.setVisibility(S ? View.VISIBLE : View.GONE);
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
        if (screenType == 0 && da) {
            var8 -= (int) (f * 56.0F);
        }

        float var11 = Math.min((float) var9 / (float) displayWidth, (float) var8 / (float) displayHeight);
        float var3 = Math.min((float) (var9 - var4 * 2) / (float) displayWidth, (float) (var8 - var5 * 2) / (float) displayHeight);

        aa.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, displayHeight));
        aa.setScaleX(var11);
        aa.setScaleY(var11);
        aa.setX(-((int) ((displayWidth - displayWidth * var11) / 2.0F)));
        aa.setY(-((int) ((displayHeight - displayHeight * var11) / 2.0F)));
        int var10 = var4 - (int) ((displayWidth - displayWidth * var3) / 2.0F);
        int var13 = var5;
        if (k.getVisibility() == View.VISIBLE) {
            k.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, statusBarHeight));
            k.setScaleX(var3);
            k.setScaleY(var3);
            var11 = statusBarHeight;
            float var12 = var11 * var3;
            k.setX(var10);
            k.setY((var5 - (int) ((var11 - var12) / 2.0F)));
            var13 = var5 + (int) var12;
        }

        var8 = var13;
        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, toolBarHeight));
            toolbar.setScaleX(var3);
            toolbar.setScaleY(var3);
            var11 = (float) toolBarHeight * var3;
            toolbar.setX(var10);
            toolbar.setY((var13 - (int) (((float) toolBarHeight - var11) / 2.0F)));
            var8 = var13 + (int) var11;
        }

        var13 = displayHeight;
        if (k.getVisibility() == View.VISIBLE) {
            var13 = displayHeight - statusBarHeight;
        }

        var5 = var13;
        if (toolbar.getVisibility() == View.VISIBLE) {
            var5 = var13 - toolBarHeight;
        }

        viewPane.setLayoutParams(new FrameLayout.LayoutParams(displayWidth, var5));
        viewPane.setScaleX(var3);
        viewPane.setScaleY(var3);
        var11 = var5;
        viewPane.setX(var10);
        viewPane.setY(var8 - (int) ((var11 - var3 * var11) / 2.0F));
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

    public final String a(ViewBean bean) {
        int i = bean.type;
        String b2 = !bean.isCustomWidget ? wq.b(i) : widgetsCreatorManager.generateCustomWidgetId(bean.convert);
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
                syVar = createAndAddView(view);
            } else {
                createAndAddView(view);
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
        return createAndAddView(viewBean);
    }

    public void a(ArrayList<ViewBean> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        for (ViewBean view : arrayList) {
            createAndAddView(view);
        }
    }

    public void addFab(ViewBean viewBean) {
        viewPane.addFab(viewBean).setOnTouchListener(this);
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
        deleteView.getLocationOnScreen(locationOnScreen);
        return x > locationOnScreen[0] && x < locationOnScreen[0] + deleteView.getWidth() && y > locationOnScreen[1] && y < (locationOnScreen[1] + deleteView.getHeight());
    }

    private void updateDeleteIcon(boolean z, boolean isCustomWidget) {
        if (D == z) return;
        D = z;
        if (D) {
            setSelectedDeleteViewUi(isCustomWidget);
            shakeView(deleteView);
        } else {
            initialDeleteViewUi();
            setDeleteViewIconAndTextUi(isCustomWidget);
        }
        if (isCustomWidget) {
            deleteIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_mtrl_edit));
            deleteText.setText(D ? "Release to see the actions" : "Drag here to see the Actions");
        } else {
            deleteIcon.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_mtrl_delete));
            deleteText.setText(D ? "Release to delete" : "Drag here to delete");
        }
    }

    private void initialDeleteViewUi() {
        deleteView.setCardBackgroundColor(colorSurfaceContainerHighest);
    }

    private void setSelectedDeleteViewUi(boolean isCustomWidget) {
        deleteView.setCardBackgroundColor(isCustomWidget ? colorCoolGreenContainer : colorErrorContainer);
        setDeleteViewIconAndTextUi(isCustomWidget);
    }

    private void setDeleteViewIconAndTextUi(boolean isCustomWidget) {
        if (isCustomWidget) {
            deleteText.setTextColor(colorCoolGreen);
            deleteIcon.setColorFilter(colorCoolGreen);
        } else {
            deleteText.setTextColor(colorError);
            deleteIcon.setColorFilter(colorError);
        }
    }

    enum PaletteGroup {
        BASIC,
        FAVORITE
    }

    static class PaletteGroupItem extends LinearLayout implements View.OnClickListener {
        private ImageView imgGroup;

        public PaletteGroupItem(Context context) {
            super(context);

            wB.a(context, this, R.layout.palette_group_item);
            imgGroup = findViewById(R.id.img_group);
        }

        @Override
        public void onClick(View view) {
        }

        public void setPaletteGroup(PaletteGroup group) {
            imgGroup.setImageResource(group == PaletteGroup.BASIC ?
                    R.drawable.selector_palette_tab_ic_sketchware :
                    R.drawable.selector_palette_tab_ic_bookmark);
            setOnClickListener(this);
        }
    }

    public void CreateCustomWidget(HashMap<String, Object> map) {
        View extraWidget = paletteWidget.CustomWidget(map);
        extraWidget.setClickable(true);
        Object position = map.get("position");
        int tagValue = 0;
        if (position instanceof Integer) {
            tagValue = (Integer) position;
        } else if (position instanceof Double) {
            tagValue = ((Double) position).intValue();
        }
        extraWidget.setTag(tagValue);
        extraWidget.setOnTouchListener(this);
    }
}
