package a.a.a;

import com.besome.sketch.projects.MyProjectSettingActivity;

public class uC implements Zx.b {

    public final int a;
    public final MyProjectSettingActivity b;

    public uC(MyProjectSettingActivity myProjectSettingActivity, int i) {
        this.b = myProjectSettingActivity;
        this.a = i;
    }

    @Override // a.a.a.Zx.b
    public void a(int i) {
        this.b.v[this.a] = i;
        this.b.x();
    }
}