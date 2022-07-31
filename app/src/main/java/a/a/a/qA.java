//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.besome.sketch.SketchApplication;
import com.besome.sketch.lib.base.BaseActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.Tracker;

public class qA extends Fragment {
    public Activity a;
    @Deprecated
    public Context b;
    public Tracker c;
    public EA d;
    public Zo e;

    public qA() {
    }

    public void a() {
        FragmentActivity var1 = this.getActivity();
        if (var1 instanceof BaseActivity) {
            ((BaseActivity)var1).a();
        } else if (var1 instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity)var1).h();
        }

    }

    public void a(MA var1) {
        FragmentActivity var2 = this.getActivity();
        if (var2 instanceof BaseActivity) {
            ((BaseActivity)var2).a(var1);
        } else if (var2 instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity)var2).a(var1);
        }

    }

    public void b() {
        FragmentActivity var1 = this.getActivity();
        if (var1 instanceof BaseActivity) {
            ((BaseActivity)var1).b();
        } else if (var1 instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity)var1).k();
        }

    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.a = this.getActivity();
        this.b = this.a.getApplicationContext();
        this.d = new EA(this.b);
        this.e = new Zo(this.b);
        this.c = ((SketchApplication)this.getActivity().getApplication()).a();
        this.c.enableAdvertisingIdCollection(true);
        this.c.enableExceptionReporting(true);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }
}
