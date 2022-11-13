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

    public final void a(ViewGroup var1) {
        setHasOptionsMenu(true);
        g = var1.findViewById(2131232319);
        g.setScreenType(getResources().getConfiguration().orientation);
        m = getActivity().findViewById(2131232326);
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
            jr.this.a(viewBean.id);
            m.e();
            k();
        });
        m.setOnEventClickListener(eventBean -> jr.this.a(eventBean.targetId, eventBean.eventName, eventBean.eventName));
        m.setOnPropertyTargetChangeListener(this::a);
        g.setOnWidgetSelectedListener(new cy() {
            @Override
            public void a() {
                n();
                m.e();
            }

            @Override
            public void a(String var1) {
                n();
                m.a(var1);
            }

            @Override
            public void a(boolean var1, String var2) {
                if (!var2.isEmpty()) {
                    this.a();
                    m.a(var2);
                    m.e();
                }

                jr.this.a(var1);
            }
        });
        this.g.setOnDraggingListener(new _x() {
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
        this.g.setOnHistoryChangeListener(this::k);
        this.g.setFavoriteData(Rp.h().f());
    }

    public void a(ProjectFileBean var1) {
        f = var1;
        h = var1.hasActivityOption(1);
        i = var1.hasActivityOption(2);
        k = var1.hasActivityOption(8);
        j = var1.hasActivityOption(4);
        l = var1.orientation;
        g.a(r, var1);
        g.h();
        m.a(r, f);
        e();
        i();
        k();
    }

    public final void a(ViewBean var1) {
        g.k();
        if (k) {
            g.a(var1);
        }

    }

    public final void a(String var1) {
        ViewBean var2;
        if (var1.equals("_fab")) {
            var2 = jC.a(r).h(f.getXmlName());
        } else {
            var2 = jC.a(r).c(f.getXmlName(), var1);
        }

        c(var2);
        m.e();
    }

    public final void a(String var1, String var2, String var3) {
        Intent var4 = new Intent(getContext(), LogicEditorActivity.class);
        var4.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var4.putExtra("sc_id", r);
        var4.putExtra("id", var1);
        var4.putExtra("event", var2);
        var4.putExtra("project_file", f);
        var4.putExtra("event_text", var3);
        getContext().startActivity(var4);
    }

    public void a(ArrayList<ViewBean> var1) {
        g.h();
        g.a(eC.a(var1));
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

    public void b(ViewBean var1) {
        Intent var2 = new Intent(getContext(), PropertyActivity.class);
        var2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var2.putExtra("sc_id", r);
        var2.putExtra("bean", var1);
        var2.putExtra("project_file", f);
        startActivityForResult(var2, 213);
    }

    public final void b(ArrayList<ViewBean> var1) {
        l();
        a(var1);
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
        g.setPaletteLayoutVisible(0);
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
                int var2 = var1.getActionType();
                ViewBean var4;
                sy var5;
                if (var2 == 0) {
                    for (ViewBean viewBean : var1.getAddedData()) {
                        var4 = viewBean;
                        jC.a(r).a(f.getXmlName(), var4);
                    }
                    var5 = g.a(var1.getAddedData(), false);
                    g.a(var5, false);
                } else {
                    ViewBean var6;
                    if (var2 == 1) {
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
                    } else if (var2 == 2) {
                        for (ViewBean var7 : var1.getRemovedData()) {
                            jC.a(r).a(f, var7);
                        }
                        g.b(var1.getRemovedData(), false);
                        g.i();
                    } else if (var2 == 3) {
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
            HistoryViewBean var1 = cC.c(r).i(f.getXmlName());
            if (var1 == null) {
                k();
            } else {
                int var2 = var1.getActionType();
                ViewBean var4;
                if (var2 == 0) {
                    for (ViewBean viewBean : var1.getAddedData()) {
                        var4 = viewBean;
                        jC.a(r).a(f, var4);
                    }
                    g.b(var1.getAddedData(), false);
                    g.i();
                } else {
                    ViewBean var5;
                    sy var6;
                    if (var2 == 1) {
                        var4 = var1.getPrevUpdateData();
                        var5 = var1.getCurrentUpdateData();
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
                    } else if (var2 == 2) {
                        for (ViewBean var7 : var1.getRemovedData()) {
                            jC.a(r).a(f.getXmlName(), var7);
                        }
                        var6 = g.a(var1.getRemovedData(), false);
                        g.a(var6, false);
                    } else if (var2 == 3) {
                        var4 = var1.getMovedData();
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
        ArrayList<ViewBean> var1 = eC.a(jC.a(r).d(f.getXmlName()));
        ViewBean var2;
        if (f.hasActivityOption(8)) {
            var2 = jC.a(r).h(f.getXmlName());
        } else {
            var2 = null;
        }
        m.a(var1, var2);
    }

    @Override
    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
        k();
    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 == 213) {
            if (var2 == -1) {
                c(var3.getParcelableExtra("bean"));
            }

            if (var3 != null && var3.getBooleanExtra("is_edit_image", false)) {
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
    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        g.setScreenType(var1.orientation);
        g.P = true;
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
    }

    @Override
    public void onCreateOptionsMenu(Menu var1, MenuInflater var2) {
        super.onCreateOptionsMenu(var1, var2);
        var2.inflate(2131492867, var1);
        var1.findItem(2131231540).setEnabled(false);
        var1.findItem(2131231541).setEnabled(false);
        if (f != null) {
            if (cC.c(r).f(f.getXmlName())) {
                var1.findItem(2131231540).setIcon(2131165827);
                var1.findItem(2131231540).setEnabled(true);
            } else {
                var1.findItem(2131231540).setIcon(2131165826);
                var1.findItem(2131231540).setEnabled(false);
            }

            if (cC.c(r).g(f.getXmlName())) {
                var1.findItem(2131231541).setIcon(2131165878);
                var1.findItem(2131231541).setEnabled(true);
            } else {
                var1.findItem(2131231541).setIcon(2131165877);
                var1.findItem(2131231541).setEnabled(false);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        ViewGroup var4 = (ViewGroup) var1.inflate(2131427425, var2, false);
        a(var4);
        if (var3 != null) {
            r = var3.getString("sc_id");
        } else {
            r = getActivity().getIntent().getStringExtra("sc_id");
        }

        return var4;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem var1) {
        switch (var1.getItemId()) {
            case 2131231540:
                h();
                break;
            case 2131231541:
                m();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", r);
        super.onSaveInstanceState(var1);
    }

    @Override
    public void onStop() {
        super.onStop();
        ViewProperty var1 = m;
        if (var1 != null) {
            var1.d();
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
