package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).h();
        }
    }

    public void addTask(MA var1) {
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).addTask(var1);
        }
    }

    public void b() {
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).k();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = getActivity();
        b = a.getApplicationContext();
        d = new EA(b);
        e = new Zo(b);
        c = new Tracker();
    }

}
