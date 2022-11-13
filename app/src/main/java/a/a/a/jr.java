//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.PropertyActivity;
import com.besome.sketch.editor.view.ViewEditor;
import com.besome.sketch.editor.view.ViewProperty;
import com.besome.sketch.editor.view.palette.PaletteWidget.a;
import com.besome.sketch.editor.view.palette.PaletteWidget.b;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
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
        this.setHasOptionsMenu(true);
        int var2 = this.getResources().getConfiguration().orientation;
        this.g = (ViewEditor)var1.findViewById(2131232319);
        this.g.setScreenType(var2);
        this.m = (ViewProperty)this.getActivity().findViewById(2131232326);
        this.m.setOnPropertyListener(new cr(this));
        this.m.setOnPropertyValueChangedListener(new dr(this));
        this.m.setOnEventClickListener(new er(this));
        this.m.setOnPropertyTargetChangeListener(new fr(this));
        this.g.setOnWidgetSelectedListener(new gr(this));
        this.g.setOnDraggingListener(new hr(this));
        this.g.setOnHistoryChangeListener(new ir(this));
        this.g.setFavoriteData(Rp.h().f());
    }

    public void a(ProjectFileBean var1) {
        this.f = var1;
        this.h = var1.hasActivityOption(1);
        this.i = var1.hasActivityOption(2);
        this.k = var1.hasActivityOption(8);
        this.j = var1.hasActivityOption(4);
        this.l = var1.orientation;
        this.g.a(this.r, var1);
        this.g.h();
        this.m.a(this.r, this.f);
        this.e();
        this.i();
        this.k();
    }

    public final void a(ViewBean var1) {
        this.g.k();
        if (this.k) {
            this.g.a(var1);
        }

    }

    public final void a(String var1) {
        ViewBean var2;
        if (var1.equals("_fab")) {
            var2 = jC.a(this.r).h(this.f.getXmlName());
        } else {
            var2 = jC.a(this.r).c(this.f.getXmlName(), var1);
        }

        this.c(var2);
        this.m.e();
    }

    public final void a(String var1, String var2, String var3) {
        Intent var4 = new Intent(this.getContext(), LogicEditorActivity.class);
        var4.setFlags(536870912);
        var4.putExtra("sc_id", this.r);
        var4.putExtra("id", var1);
        var4.putExtra("event", var2);
        var4.putExtra("project_file", this.f);
        var4.putExtra("event_text", var3);
        this.getContext().startActivity(var4);
    }

    public void a(ArrayList<ViewBean> var1) {
        this.g.h();
        this.g.a(eC.a(var1));
    }

    public void a(boolean var1) {
        this.f();
        if (!this.p || !var1) {
            this.c();
            if (var1) {
                this.n.start();
            } else if (this.p) {
                this.o.start();
            }

            this.p = var1;
        }
    }

    public void b(ViewBean var1) {
        Intent var2 = new Intent(this.getContext(), PropertyActivity.class);
        var2.setFlags(536870912);
        var2.putExtra("sc_id", this.r);
        var2.putExtra("bean", var1);
        var2.putExtra("project_file", this.f);
        EventBuilder var3 = new EventBuilder();
        var3.setCategory("editor");
        var3.setAction("property");
        var3.setLabel("Custom");
        this.startActivityForResult(var2, 213);
    }

    public final void b(ArrayList<ViewBean> var1) {
        this.l();
        this.a(var1);
    }

    public void b(boolean var1) {
        this.g.setIsAdLoaded(var1);
        this.g.requestLayout();
    }

    public final void c() {
        if (this.n.isRunning()) {
            this.n.cancel();
        }

        if (this.o.isRunning()) {
            this.o.cancel();
        }

    }

    public void c(ViewBean var1) {
        this.g.e(var1);
    }

    public void c(boolean var1) {
        ViewProperty var2 = this.m;
        byte var3;
        if (var1) {
            var3 = 0;
        } else {
            var3 = 8;
        }

        var2.setVisibility(var3);
    }

    public ProjectFileBean d() {
        return this.f;
    }

    public final void e() {
        this.g.d();
        this.g.setPaletteLayoutVisible(0);
        this.g.a(a.a, "");
        this.g.a(a.b, "");
        this.g.a(b.b, "", "TextView", "TextView");
        ProjectFileBean var1 = this.f;
        if (var1 != null && var1.fileType == 7) {
            this.g.a(a.c, "");
            this.g.a(a.d, "");
            this.g.a(b.c, "", "EditText", "Edit Text");
            this.g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
            this.g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
            this.g.a(b.a, "", "Button", "Button");
            this.g.a(b.d, "", "ImageView", "default_image");
            this.g.a(b.g, "", "CheckBox", "CheckBox");
            this.g.extraWidget("", "RadioButton", "RadioButton");
            this.g.a(b.i, "", "Switch", "Switch");
            this.g.a(b.j, "", "SeekBar", "SeekBar");
            this.g.a(b.m, "", "ProgressBar", "ProgressBar");
            this.g.extraWidget("", "RatingBar", "RatingBar");
            this.g.extraWidget("", "AnalogClock", "AnalogClock");
            this.g.extraWidget("", "TimePicker", "TimePicker");
            this.g.extraWidget("", "DatePicker", "DatePicker");
            this.g.a(b.f, "", "Spinner", "Spinner");
            this.g.a(b.h, "", "WebView", "WebView");
            this.g.extraWidget("", "VideoView", "VideoView");
            this.g.extraWidget("", "BadgeView", "BadgeView");
            this.g.a(b.l, "", "AdView", "AdView");
        } else {
            var1 = this.f;
            if (var1 != null && var1.fileType == 6) {
                this.g.a(a.c, "");
                this.g.a(a.d, "");
                this.g.a(b.c, "", "EditText", "Edit Text");
                this.g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
                this.g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
                this.g.a(b.a, "", "Button", "Button");
                this.g.a(b.d, "", "ImageView", "default_image");
                this.g.a(b.g, "", "CheckBox", "CheckBox");
                this.g.extraWidget("", "RadioButton", "RadioButton");
                this.g.a(b.i, "", "Switch", "Switch");
                this.g.a(b.j, "", "SeekBar", "SeekBar");
                this.g.a(b.m, "", "ProgressBar", "ProgressBar");
                this.g.extraWidget("", "RatingBar", "RatingBar");
                this.g.extraWidget("", "AnalogClock", "AnalogClock");
                this.g.extraWidget("", "TimePicker", "TimePicker");
                this.g.extraWidget("", "DatePicker", "DatePicker");
                this.g.a(b.f, "", "Spinner", "Spinner");
                this.g.a(b.h, "", "WebView", "WebView");
                this.g.extraWidget("", "VideoView", "VideoView");
                this.g.extraWidget("", "BadgeView", "BadgeView");
                this.g.a(b.l, "", "AdView", "AdView");
            } else {
                this.g.a(a.c, "");
                this.g.a(a.d, "");
                this.g.extraWidgetLayout("", "RadioGroup");
                this.g.i.extraTitle("AndroidX", 0);
                this.g.extraWidgetLayout("", "TabLayout");
                this.g.extraWidgetLayout("", "BottomNavigationView");
                this.g.extraWidgetLayout("", "CollapsingToolbarLayout");
                this.g.extraWidgetLayout("", "CardView");
                this.g.extraWidgetLayout("", "TextInputLayout");
                this.g.extraWidgetLayout("", "SwipeRefreshLayout");
                this.g.a(b.c, "", "EditText", "Edit Text");
                this.g.extraWidget("", "AutoCompleteTextView", "AutoCompleteTextView");
                this.g.extraWidget("", "MultiAutoCompleteTextView", "MultiAutoCompleteTextView");
                this.g.a(b.a, "", "Button", "Button");
                this.g.extraWidget("", "MaterialButton", "MaterialButton");
                this.g.a(b.d, "", "ImageView", "default_image");
                this.g.extraWidget("", "CircleImageView", "default_image");
                this.g.a(b.g, "", "CheckBox", "CheckBox");
                this.g.extraWidget("", "RadioButton", "RadioButton");
                this.g.a(b.i, "", "Switch", "Switch");
                this.g.a(b.j, "", "SeekBar", "SeekBar");
                this.g.a(b.m, "", "ProgressBar", "ProgressBar");
                this.g.extraWidget("", "RatingBar", "RatingBar");
                this.g.extraWidget("", "SearchView", "SearchView");
                this.g.extraWidget("", "VideoView", "VideoView");
                this.g.a(b.h, "", "WebView", "WebView");
                this.g.i.extraTitle("List", 1);
                this.g.a(b.e, "", "ListView", "ListView");
                this.g.extraWidget("", "GridView", "GridView");
                this.g.extraWidget("", "RecyclerView", "RecyclerView");
                this.g.a(b.f, "", "Spinner", "Spinner");
                this.g.extraWidget("", "ViewPager", "ViewPager");
                this.g.i.extraTitle("Library", 1);
                this.g.extraWidget("", "BadgeView", "BadgeView");
                this.g.extraWidget("", "WaveSideBar", "WaveSideBar");
                this.g.extraWidget("", "PatternLockView", "PatternLockView");
                this.g.extraWidget("", "CodeView", "CodeView");
                this.g.extraWidget("", "LottieAnimation", "LottieAnimation");
                this.g.extraWidget("", "OTPView", "OTPView");
                this.g.i.extraTitle("Google", 1);
                this.g.a(b.l, "", "AdView", "AdView");
                this.g.a(b.n, "", "MapView", "MapView");
                this.g.extraWidget("", "SignInButton", "SignInButton");
                this.g.extraWidget("", "YoutubePlayer", "YoutubePlayer");
                this.g.i.extraTitle("Date & Time", 1);
                this.g.extraWidget("", "AnalogClock", "AnalogClock");
                this.g.extraWidget("", "DigitalClock", "DigitalClock");
                this.g.extraWidget("", "TimePicker", "TimePicker");
                this.g.extraWidget("", "DatePicker", "DatePicker");
                this.g.a(b.k, "", "CalendarView", "CalendarView");
            }
        }

    }

    public final void f() {
        if (this.n == null) {
            this.n = ObjectAnimator.ofFloat(this.m, View.TRANSLATION_Y, new float[]{0.0F});
            this.n.setDuration(700L);
            this.n.setInterpolator(new DecelerateInterpolator());
        }

        if (this.o == null) {
            this.o = ObjectAnimator.ofFloat(this.m, View.TRANSLATION_Y, new float[]{wB.a(this.getActivity(), (float)this.m.getHeight())});
            this.o.setDuration(300L);
            this.o.setInterpolator(new DecelerateInterpolator());
        }

    }

    public boolean g() {
        return this.p;
    }

    public final void h() {
        if (!this.q) {
            HistoryViewBean var1 = cC.c(this.r).h(this.f.getXmlName());
            if (var1 == null) {
                this.k();
            } else {
                int var2 = var1.getActionType();
                ViewBean var4;
                sy var5;
                if (var2 == 0) {
                    Iterator var3 = var1.getAddedData().iterator();

                    while(var3.hasNext()) {
                        var4 = (ViewBean)var3.next();
                        jC.a(this.r).a(this.f.getXmlName(), var4);
                    }

                    var5 = this.g.a(var1.getAddedData(), false);
                    this.g.a(var5, false);
                } else {
                    ViewBean var6;
                    if (var2 == 1) {
                        var4 = var1.getPrevUpdateData();
                        var6 = var1.getCurrentUpdateData();
                        if (!var4.id.equals(var6.id)) {
                            var6.preId = var4.id;
                        }

                        if (var6.id.equals("_fab")) {
                            jC.a(this.r).h(this.f.getXmlName()).copy(var6);
                        } else {
                            jC.a(this.r).c(this.f.getXmlName(), var4.id).copy(var6);
                        }

                        var5 = this.g.e(var6);
                        this.g.a(var5, false);
                    } else if (var2 == 2) {
                        Iterator var8 = var1.getRemovedData().iterator();

                        while(var8.hasNext()) {
                            ViewBean var7 = (ViewBean)var8.next();
                            jC.a(this.r).a(this.f, var7);
                        }

                        this.g.b(var1.getRemovedData(), false);
                        this.g.i();
                    } else if (var2 == 3) {
                        var4 = var1.getMovedData();
                        var6 = jC.a(this.r).c(this.f.getXmlName(), var4.id);
                        var6.copy(var4);
                        var5 = this.g.b(var6, false);
                        this.g.a(var5, false);
                    }
                }

                this.k();
            }
        }
    }

    public void i() {
        if (this.f != null) {
            this.b(jC.a(this.r).d(this.f.getXmlName()));
            this.a(jC.a(this.r).h(this.f.getXmlName()));
        }
    }

    public void j() {
        this.g.setFavoriteData(Rp.h().f());
    }

    public void k() {
        if (this.getActivity() != null) {
            this.getActivity().invalidateOptionsMenu();
        }

    }

    public void l() {
        this.g.j();
    }

    public final void m() {
        if (!this.q) {
            HistoryViewBean var1 = cC.c(this.r).i(this.f.getXmlName());
            if (var1 == null) {
                this.k();
            } else {
                int var2 = var1.getActionType();
                ViewBean var4;
                if (var2 == 0) {
                    Iterator var3 = var1.getAddedData().iterator();

                    while(var3.hasNext()) {
                        var4 = (ViewBean)var3.next();
                        jC.a(this.r).a(this.f, var4);
                    }

                    this.g.b(var1.getAddedData(), false);
                    this.g.i();
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
                            jC.a(this.r).h(this.f.getXmlName()).copy(var4);
                        } else {
                            jC.a(this.r).c(this.f.getXmlName(), var5.id).copy(var4);
                        }

                        var6 = this.g.e(var4);
                        this.g.a(var6, false);
                    } else if (var2 == 2) {
                        Iterator var8 = var1.getRemovedData().iterator();

                        while(var8.hasNext()) {
                            ViewBean var7 = (ViewBean)var8.next();
                            jC.a(this.r).a(this.f.getXmlName(), var7);
                        }

                        var6 = this.g.a(var1.getRemovedData(), false);
                        this.g.a(var6, false);
                    } else if (var2 == 3) {
                        var4 = var1.getMovedData();
                        var5 = jC.a(this.r).c(this.f.getXmlName(), var4.id);
                        var5.preIndex = var4.index;
                        var5.index = var4.preIndex;
                        var5.parent = var4.preParent;
                        var5.preParent = var4.parent;
                        var6 = this.g.b(var5, false);
                        this.g.a(var6, false);
                    }
                }

                this.k();
            }
        }
    }

    public void n() {
        ArrayList var1 = eC.a(jC.a(this.r).d(this.f.getXmlName()));
        ViewBean var2;
        if (this.f.hasActivityOption(8)) {
            var2 = jC.a(this.r).h(this.f.getXmlName());
        } else {
            var2 = null;
        }

        this.m.a(var1, var2);
    }

    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
        this.k();
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 == 213) {
            if (var2 == -1) {
                this.c((ViewBean)var3.getParcelableExtra("bean"));
            }

            if (var3 != null && var3.getBooleanExtra("is_edit_image", false)) {
                Iterator var4 = jC.a(this.r).d(this.f.getXmlName()).iterator();

                while(var4.hasNext()) {
                    this.c((ViewBean)var4.next());
                }

                if (this.k) {
                    this.c(jC.a(this.r).h(this.f.getXmlName()));
                }
            }

            this.k();
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        super.onConfigurationChanged(var1);
        this.g.setScreenType(var1.orientation);
        this.g.P = true;
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
    }

    public void onCreateOptionsMenu(Menu var1, MenuInflater var2) {
        super.onCreateOptionsMenu(var1, var2);
        var2.inflate(2131492867, var1);
        var1.findItem(2131231540).setEnabled(false);
        var1.findItem(2131231541).setEnabled(false);
        if (this.f != null) {
            if (cC.c(this.r).f(this.f.getXmlName())) {
                var1.findItem(2131231540).setIcon(2131165827);
                var1.findItem(2131231540).setEnabled(true);
            } else {
                var1.findItem(2131231540).setIcon(2131165826);
                var1.findItem(2131231540).setEnabled(false);
            }

            if (cC.c(this.r).g(this.f.getXmlName())) {
                var1.findItem(2131231541).setIcon(2131165878);
                var1.findItem(2131231541).setEnabled(true);
            } else {
                var1.findItem(2131231541).setIcon(2131165877);
                var1.findItem(2131231541).setEnabled(false);
            }

        }
    }

    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        ViewGroup var4 = (ViewGroup)var1.inflate(2131427425, var2, false);
        this.a(var4);
        if (var3 != null) {
            this.r = var3.getString("sc_id");
        } else {
            this.r = this.getActivity().getIntent().getStringExtra("sc_id");
        }

        return var4;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        switch(var1.getItemId()) {
            case 2131231540:
                this.h();
                break;
            case 2131231541:
                this.m();
        }

        return true;
    }

    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.r);
        super.onSaveInstanceState(var1);
    }

    public void onStop() {
        super.onStop();
        ViewProperty var1 = this.m;
        if (var1 != null) {
            var1.d();
        }

    }

    public void p() {
        Iterator var1 = mod.agus.jcoderz.editor.view.ViewEditor.a();

        while(var1.hasNext()) {
            HashMap var2 = (HashMap)var1.next();
            this.g.extraWidget((String)var2.get("tag"), (String)var2.get("name"), (String)var2.get("text"));
        }

    }

    public void q() {
        Iterator var1 = mod.agus.jcoderz.editor.view.ViewEditor.c();

        while(var1.hasNext()) {
            HashMap var2 = (HashMap)var1.next();
            this.g.extraWidgetLayout((String)var2.get("tag"), (String)var2.get("name"));
        }

    }
}
