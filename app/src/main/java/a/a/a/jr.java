package a.a.a;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.besome.sketch.beans.HistoryViewBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.PropertyActivity;
import com.besome.sketch.editor.view.ViewEditor;
import com.besome.sketch.editor.view.ViewProperty;
import com.besome.sketch.editor.view.palette.PaletteWidget;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class jr extends qA {

    public ProjectFileBean f;
    public ViewEditor g;
    public boolean h = true;
    public boolean i = false;
    public boolean j = false;
    public boolean k = false;
    public int l = 0;
    public ViewProperty m;
    public ObjectAnimator n;
    public ObjectAnimator o;
    public boolean p;
    public boolean q = false;
    public String r;

    public jr() {
    }

    private void a(ViewGroup viewGroup) {
        setHasOptionsMenu(true);
        g = viewGroup.findViewById(R.id.view_editor);
        g.setScreenType(getResources().getConfiguration().orientation);
        m = getActivity().findViewById(R.id.view_property);
        m.setOnPropertyListener(new Iw() {
            @Override
            public void a() {
                g.setFavoriteData(Rp.h().f());
            }

            @Override
            public void a(String s, ViewBean viewBean) {
                b(viewBean);
            }
        });
        m.setOnPropertyValueChangedListener(viewBean -> {
            a(viewBean.id);
            m.e();
            k();
        });
        m.setOnEventClickListener(eventBean -> a(eventBean.targetId, eventBean.eventName, eventBean.eventName));
        m.setOnPropertyTargetChangeListener(this::a);
        g.setOnWidgetSelectedListener(new cy() {
            @Override
            public void a() {
                n();
                m.e();
            }

            @Override
            public void a(String viewId) {
                n();
                m.a(viewId);
            }

            @Override
            public void a(boolean var1, String viewId) {
                if (!viewId.isEmpty()) {
                    a();
                    m.a(viewId);
                    m.e();
                }

                jr.this.a(var1);
            }
        });
        g.setOnDraggingListener(new _x() {
            @Override
            public boolean a() {
                return jC.c(r).b().isEnabled();
            }

            @Override
            public void b() {
                q = true;
                ((DesignActivity) getActivity()).b(false);
            }

            @Override
            public boolean c() {
                return jC.c(r).e().isEnabled();
            }

            @Override
            public void d() {
                q = false;
                ((DesignActivity) getActivity()).b(true);
            }
        });
        g.setOnHistoryChangeListener(this::k);
        g.setFavoriteData(Rp.h().f());
    }

    public void a(ProjectFileBean projectFileBean) {
        f = projectFileBean;
        h = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR);
        i = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN);
        k = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB);
        j = projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER);
        l = projectFileBean.orientation;
        g.a(r, projectFileBean);
        g.h();
        m.a(r, f);
        e();
        i();
        k();
    }

    public final void a(ViewBean var1) {
        g.k();
        if (k) g.a(var1);
    }

    public final void a(String viewId) {
        ViewBean viewBean;
        if (viewId.equals("_fab")) {
            viewBean = jC.a(r).h(f.getXmlName());
        } else {
            viewBean = jC.a(r).c(f.getXmlName(), viewId);
        }
        c(viewBean);
        m.e();
    }

    private void a(String eventId, String eventName, String eventName2) {
        Intent intent = new Intent(getContext(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", r);
        intent.putExtra("id", eventId);
        intent.putExtra("event", eventName);
        intent.putExtra("project_file", f);
        intent.putExtra("event_text", eventName2);
        getContext().startActivity(intent);
    }

    public void a(ArrayList<ViewBean> viewBeans) {
        g.h();
        g.a(eC.a(viewBeans));
    }

    public void a(boolean var1) {
        f();
        if (!p || !var1) {
            c();
            if (var1) {
                n.start();
            } else if (p) {
                o.start();
            }

            p = var1;
        }
    }

    public void b(ViewBean viewBean) {
        Intent intent = new Intent(getContext(), PropertyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", r);
        intent.putExtra("bean", viewBean);
        intent.putExtra("project_file", f);
        startActivityForResult(intent, 213);
    }

    public final void b(ArrayList<ViewBean> viewBeans) {
        l();
        a(viewBeans);
    }

    public void b(boolean var1) {
        g.setIsAdLoaded(var1);
        g.requestLayout();
    }

    public final void c() {
        if (n.isRunning()) n.cancel();
        if (o.isRunning()) o.cancel();
    }

    public void c(ViewBean var1) {
        g.e(var1);
    }

    public void c(boolean var1) {
        m.setVisibility(var1 ? View.VISIBLE : View.GONE);
    }

    public ProjectFileBean d() {
        return f;
    }

    public final void e() {
        g.d();
        g.setPaletteLayoutVisible(View.VISIBLE);
        g.a(PaletteWidget.a.a, "");
        g.a(PaletteWidget.a.b, "");
        g.a(PaletteWidget.b.b, "", "TextView", "TextView");
        if (f != null && f.fileType == 7) {
            g.a(PaletteWidget.a.c, "");
            g.a(PaletteWidget.a.d, "");
            g.a(PaletteWidget.b.c, "", "EditText", "Edit Text");
            g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
            g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
            g.a(PaletteWidget.b.a, "", "Button", "Button");
            g.a(PaletteWidget.b.d, "", "ImageView", "default_image");
            g.a(PaletteWidget.b.g, "", "CheckBox", "CheckBox");
            g.extraWidget("", "RadioButton", "RadioButton");
            g.a(PaletteWidget.b.i, "", "Switch", "Switch");
            g.a(PaletteWidget.b.j, "", "SeekBar", "SeekBar");
            g.a(PaletteWidget.b.m, "", "ProgressBar", "ProgressBar");
            g.extraWidget("", "RatingBar", "RatingBar");
            g.extraWidget("", "AnalogClock", "AnalogClock");
            g.extraWidget("", "TimePicker", "TimePicker");
            g.extraWidget("", "DatePicker", "DatePicker");
            g.a(PaletteWidget.b.f, "", "Spinner", "Spinner");
            g.a(PaletteWidget.b.h, "", "WebView", "WebView");
            g.extraWidget("", "VideoView", "VideoView");
            g.extraWidget("", "BadgeView", "BadgeView");
            g.a(PaletteWidget.b.l, "", "AdView", "AdView");
        } else {
            if (f != null && f.fileType == 6) {
                g.a(PaletteWidget.a.c, "");
                g.a(PaletteWidget.a.d, "");
                g.a(PaletteWidget.b.c, "", "EditText", "Edit Text");
                g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
                g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
                g.a(PaletteWidget.b.a, "", "Button", "Button");
                g.a(PaletteWidget.b.d, "", "ImageView", "default_image");
                g.a(PaletteWidget.b.g, "", "CheckBox", "CheckBox");
                g.extraWidget("", "RadioButton", "RadioButton");
                g.a(PaletteWidget.b.i, "", "Switch", "Switch");
                g.a(PaletteWidget.b.j, "", "SeekBar", "SeekBar");
                g.a(PaletteWidget.b.m, "", "ProgressBar", "ProgressBar");
                g.extraWidget("", "RatingBar", "RatingBar");
                g.extraWidget("", "AnalogClock", "AnalogClock");
                g.extraWidget("", "TimePicker", "TimePicker");
                g.extraWidget("", "DatePicker", "DatePicker");
                g.a(PaletteWidget.b.f, "", "Spinner", "Spinner");
                g.a(PaletteWidget.b.h, "", "WebView", "WebView");
                g.extraWidget("", "VideoView", "VideoView");
                g.extraWidget("", "BadgeView", "BadgeView");
                g.a(PaletteWidget.b.l, "", "AdView", "AdView");
            } else {
                g.a(PaletteWidget.a.c, "");
                g.a(PaletteWidget.a.d, "");
                g.extraWidgetLayout("", "RadioGroup");
                g.i.extraTitle("AndroidX", 0);
                g.extraWidgetLayout("", "TabLayout");
                g.extraWidgetLayout("", "BottomNavigationView");
                g.extraWidgetLayout("", "CollapsingToolbarLayout");
                g.extraWidgetLayout("", "CardView");
                g.extraWidgetLayout("", "TextInputLayout");
                g.extraWidgetLayout("", "SwipeRefreshLayout");
                g.a(PaletteWidget.b.c, "", "EditText", "Edit Text");
                g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
                g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
                g.a(PaletteWidget.b.a, "", "Button", "Button");
                g.extraWidget("", "MaterialButton", "MaterialButton");
                g.a(PaletteWidget.b.d, "", "ImageView", "default_image");
                g.extraWidget("", "CircleImageView", "default_image");
                g.a(PaletteWidget.b.g, "", "CheckBox", "CheckBox");
                g.extraWidget("", "RadioButton", "RadioButton");
                g.a(PaletteWidget.b.i, "", "Switch", "Switch");
                g.a(PaletteWidget.b.j, "", "SeekBar", "SeekBar");
                g.a(PaletteWidget.b.m, "", "ProgressBar", "ProgressBar");
                g.extraWidget("", "RatingBar", "RatingBar");
                g.extraWidget("", "SearchView", "SearchView");
                g.extraWidget("", "VideoView", "VideoView");
                g.a(PaletteWidget.b.h, "", "WebView", "WebView");
                g.i.extraTitle("List", 1);
                g.a(PaletteWidget.b.e, "", "ListView", "ListView");
                g.extraWidget("", "GridView", "GridView");
                g.extraWidget("", "RecyclerView", "RecyclerView");
                g.a(PaletteWidget.b.f, "", "Spinner", "Spinner");
                g.extraWidget("", "ViewPager", "ViewPager");
                g.i.extraTitle("Library", 1);
                g.extraWidget("", "BadgeView", "BadgeView");
                g.extraWidget("", "WaveSideBar", "WaveSideBar");
                g.extraWidget("", "PatternLockView", "PatternLockView");
                g.extraWidget("", "CodeView", "CodeView");
                g.extraWidget("", "LottieAnimation", "LottieAnimation");
                g.extraWidget("", "OTPView", "OTPView");
                g.i.extraTitle("Google", 1);
                g.a(PaletteWidget.b.l, "", "AdView", "AdView");
                g.a(PaletteWidget.b.n, "", "MapView", "MapView");
                g.extraWidget("", "SignInButton", "SignInButton");
                g.extraWidget("", "YoutubePlayer", "YoutubePlayer");
                g.i.extraTitle("Date & Time", 1);
                g.extraWidget("", "AnalogClock", "AnalogClock");
                g.extraWidget("", "DigitalClock", "DigitalClock");
                g.extraWidget("", "TimePicker", "TimePicker");
                g.extraWidget("", "DatePicker", "DatePicker");
                g.a(PaletteWidget.b.k, "", "CalendarView", "CalendarView");
            }
        }
    }

    public final void f() {
        if (n == null) {
            n = ObjectAnimator.ofFloat(m, View.TRANSLATION_Y, 0.0F);
            n.setDuration(700L);
            n.setInterpolator(new DecelerateInterpolator());
        }

        if (o == null) {
            o = ObjectAnimator.ofFloat(m, View.TRANSLATION_Y, wB.a(getActivity(), (float) m.getHeight()));
            o.setDuration(300L);
            o.setInterpolator(new DecelerateInterpolator());
        }
    }

    public boolean g() {
        return p;
    }

    public final void h() {
        if (!q) {
            HistoryViewBean var1 = cC.c(r).h(f.getXmlName());
            if (var1 == null) {
                k();
            } else {
                int actionType = var1.getActionType();
                ViewBean var4;
                sy var5;
                if (actionType == HistoryViewBean.ACTION_TYPE_ADD) {
                    for (ViewBean viewBean : var1.getAddedData()) {
                        var4 = viewBean;
                        jC.a(r).a(f.getXmlName(), var4);
                    }
                    var5 = g.a(var1.getAddedData(), false);
                    g.a(var5, false);
                } else {
                    ViewBean var6;
                    if (actionType == HistoryViewBean.ACTION_TYPE_UPDATE) {
                        var4 = var1.getPrevUpdateData();
                        var6 = var1.getCurrentUpdateData();
                        if (!var4.id.equals(var6.id)) {
                            var6.preId = var4.id;
                        }

                        if (var6.id.equals("_fab")) {
                            jC.a(r).h(f.getXmlName()).copy(var6);
                        } else {
                            jC.a(r).c(f.getXmlName(), var4.id).copy(var6);
                        }

                        var5 = g.e(var6);
                        g.a(var5, false);
                    } else if (actionType == HistoryViewBean.ACTION_TYPE_REMOVE) {
                        for (ViewBean var7 : var1.getRemovedData()) {
                            jC.a(r).a(f, var7);
                        }
                        g.b(var1.getRemovedData(), false);
                        g.i();
                    } else if (actionType == HistoryViewBean.ACTION_TYPE_MOVE) {
                        var4 = var1.getMovedData();
                        var6 = jC.a(r).c(f.getXmlName(), var4.id);
                        var6.copy(var4);
                        var5 = g.b(var6, false);
                        g.a(var5, false);
                    }
                }
                k();
            }
        }
    }

    public void i() {
        if (f != null) {
            b(jC.a(r).d(f.getXmlName()));
            a(jC.a(r).h(f.getXmlName()));
        }
    }

    public void j() {
        g.setFavoriteData(Rp.h().f());
    }

    public void k() {
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    public void l() {
        g.j();
    }

    public final void m() {
        if (!q) {
            HistoryViewBean historyViewBean = cC.c(r).i(f.getXmlName());
            if (historyViewBean == null) {
                k();
            } else {
                int actionType = historyViewBean.getActionType();
                ViewBean var4;
                if (actionType == HistoryViewBean.ACTION_TYPE_ADD) {
                    for (ViewBean viewBean : historyViewBean.getAddedData()) {
                        var4 = viewBean;
                        jC.a(r).a(f, var4);
                    }
                    g.b(historyViewBean.getAddedData(), false);
                    g.i();
                } else {
                    ViewBean var5;
                    sy var6;
                    if (actionType == HistoryViewBean.ACTION_TYPE_UPDATE) {
                        var4 = historyViewBean.getPrevUpdateData();
                        var5 = historyViewBean.getCurrentUpdateData();
                        if (!var4.id.equals(var5.id)) {
                            var4.preId = var5.id;
                        }
                        if (var5.id.equals("_fab")) {
                            jC.a(r).h(f.getXmlName()).copy(var4);
                        } else {
                            jC.a(r).c(f.getXmlName(), var5.id).copy(var4);
                        }
                        var6 = g.e(var4);
                        g.a(var6, false);
                    } else if (actionType == HistoryViewBean.ACTION_TYPE_REMOVE) {
                        for (ViewBean var7 : historyViewBean.getRemovedData()) {
                            jC.a(r).a(f.getXmlName(), var7);
                        }
                        var6 = g.a(historyViewBean.getRemovedData(), false);
                        g.a(var6, false);
                    } else if (actionType == HistoryViewBean.ACTION_TYPE_MOVE) {
                        var4 = historyViewBean.getMovedData();
                        var5 = jC.a(r).c(f.getXmlName(), var4.id);
                        var5.preIndex = var4.index;
                        var5.index = var4.preIndex;
                        var5.parent = var4.preParent;
                        var5.preParent = var4.parent;
                        var6 = g.b(var5, false);
                        g.a(var6, false);
                    }
                }
                k();
            }
        }
    }

    public void n() {
        ArrayList<ViewBean> viewBeanArrayList = eC.a(jC.a(r).d(f.getXmlName()));
        ViewBean viewBean;
        if (f.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
            viewBean = jC.a(r).h(f.getXmlName());
        } else {
            viewBean = null;
        }
        m.a(viewBeanArrayList, viewBean);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        k();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 213) {
            if (resultCode == -1) {
                c(data.getParcelableExtra("bean"));
            }

            if (data != null && data.getBooleanExtra("is_edit_image", false)) {
                for (ViewBean viewBean : jC.a(r).d(f.getXmlName())) {
                    c(viewBean);
                }
                if (k) {
                    c(jC.a(r).h(f.getXmlName()));
                }
            }
            k();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        g.setScreenType(newConfiguration.orientation);
        g.P = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.design_view_menu, menu);
        menu.findItem(R.id.menu_view_redo).setEnabled(false);
        menu.findItem(R.id.menu_view_undo).setEnabled(false);
        if (f != null) {
            if (cC.c(r).f(f.getXmlName())) {
                menu.findItem(R.id.menu_view_redo).setIcon(R.drawable.ic_redo_white_48dp);
                menu.findItem(R.id.menu_view_redo).setEnabled(true);
            } else {
                menu.findItem(R.id.menu_view_redo).setIcon(R.drawable.ic_redo_grey_48dp);
                menu.findItem(R.id.menu_view_redo).setEnabled(false);
            }

            if (cC.c(r).g(f.getXmlName())) {
                menu.findItem(R.id.menu_view_undo).setIcon(R.drawable.ic_undo_white_48dp);
                menu.findItem(R.id.menu_view_undo).setEnabled(true);
            } else {
                menu.findItem(R.id.menu_view_undo).setIcon(R.drawable.ic_undo_grey_48dp);
                menu.findItem(R.id.menu_view_undo).setEnabled(false);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.fr_graphic_editor, parent, false);
        a(viewGroup);
        if (bundle != null) {
            r = bundle.getString("sc_id");
        } else {
            r = getActivity().getIntent().getStringExtra("sc_id");
        }

        return viewGroup;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_view_redo) {
            h();
        } else if (itemId == R.id.menu_view_undo) {
            m();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle newInstanceState) {
        newInstanceState.putString("sc_id", r);
        super.onSaveInstanceState(newInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (m != null) {
            m.d();
        }
    }

    public void p() {
        for (Iterator<HashMap<String, String>> it = mod.agus.jcoderz.editor.view.ViewEditor.a(); it.hasNext(); ) {
            HashMap<String, String> map = it.next();
            g.extraWidget(map.get("tag"), map.get("name"), map.get("text"));
        }
    }

    public void q() {
        for (Iterator<HashMap<String, String>> it = mod.agus.jcoderz.editor.view.ViewEditor.c(); it.hasNext(); ) {
            HashMap<String, String> map = it.next();
            g.extraWidgetLayout(map.get("tag"), map.get("name"));
        }
    }
}
