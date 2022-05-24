package com.besome.sketch.help;

import a.a.a.DB;
import a.a.a.MA;
import a.a.a.bB;
import a.a.a.rB;
import a.a.a.uz;
import a.a.a.vz;
import a.a.a.xB;
import a.a.a.yB;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import com.besome.sketch.editor.property.PropertySwitchItem;
import com.besome.sketch.lib.base.BaseAdsAppCompatActivity;
import java.util.HashMap;
import java.util.List;

public class SystemSettingActivity extends BaseAdsAppCompatActivity {

    public Toolbar n;
    public LinearLayout o;
    public final int p = 0;
    public final int q = 1;
    public final int r = 2;
    public final int s = 3;
    public final int t = 5;
    public DB u;
    public Intent v = new Intent();
    public HashMap<String, Object> w;

    public SystemSettingActivity() {
    }

    public final void a(int key, int resName, int resDescription, boolean value) {
        a(key, xB.b().a(getApplicationContext(), resName), xB.b().a(getApplicationContext(), resDescription), value);
    }

    public final void a(int key, String name, String description, boolean value) {
        PropertySwitchItem switchItem = new PropertySwitchItem(this);
        switchItem.setKey(key);
        switchItem.setName(name);
        switchItem.setDesc(description);
        switchItem.setValue(value);
        o.addView(switchItem);
        if (key == 2) {
            if (!super.i.a()) {
                switchItem.setEnabled(false);
                switchItem.setValue(false);
                switchItem.setTextColor(0xffc6c6c6);
            }

            switchItem.setSwitchChangedListener(new vz(this));
        }

        if (key == 3 && !super.i.a()) {
            switchItem.setEnabled(false);
            switchItem.setValue(false);
            switchItem.setTextColor(-3750202);
        }

    }

    public void g(int var1) {
    }

    public void h(int requestCode) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    public void l() {
    }

    public void m() {
    }

    @Override
    public void onBackPressed() {
        if (!p()) {
            setResult(-1, v);
            finish();
        }
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427742);
        n = (Toolbar)findViewById(2131231847);
        a(n);
        d().d(true);
        d().e(true);
        findViewById(2131231370).setVisibility(8);
        d().a(xB.b().a(this, 2131625606));
        n.setNavigationOnClickListener(new uz(this));
        o = (LinearLayout)findViewById(2131230932);
        u = new DB(getApplicationContext(), "P12");
        a(0, 2131626402, 2131626397, u.a("P12I0", true));
        a(1, 2131626400, 2131626396, u.a("P12I2", false));
        a(2, 2131626404, 2131626399, u.a("P12I4", true));
        a(3, 2131626401, 2131626399, u.a("P12I5", true));
        if (super.i.a() && super.i.p()) {
            (new SystemSettingActivity.a(this, getApplicationContext())).execute();
        }

        o();
    }

    public void onResume() {
        super.onResume();
        n();
    }

    public final boolean p() {
        int var1 = 0;

        boolean var2;
        boolean var4;
        for(var2 = false; var1 < o.getChildCount(); var2 = var4) {
            View var3 = o.getChildAt(var1);
            var4 = var2;
            if (var3 instanceof PropertySwitchItem) {
                PropertySwitchItem var6 = (PropertySwitchItem)var3;
                int var5 = var6.getKey();
                if (var5 != 0) {
                    if (var5 != 1) {
                        if (var5 != 2) {
                            if (var5 != 3) {
                                if (var5 != 5) {
                                    var4 = var2;
                                } else {
                                    String var7;
                                    if (var6.getValue()) {
                                        var7 = "Y";
                                    } else {
                                        var7 = "N";
                                    }

                                    var4 = var2;
                                    if (!yB.c(w, "subscribe").equals(var7)) {
                                        (new SystemSettingActivity.b(this, getApplicationContext(), var7)).execute(new Void[0]);
                                        var4 = true;
                                    }
                                }
                            } else {
                                u.a("P12I5", var6.getValue());
                                var4 = var2;
                            }
                        } else {
                            u.a("P12I4", var6.getValue());
                            var4 = var2;
                        }
                    } else {
                        u.a("P12I2", var6.getValue());
                        var4 = var2;
                    }
                } else {
                    u.a("P12I0", var6.getValue());
                    var4 = var2;
                }
            }

            ++var1;
        }

        return var2;
    }

    public class a extends MA {
        public List<Object> c;
        public final SystemSettingActivity d;

        public a(SystemSettingActivity var1, Context var2) {
            super(var2);
            d = var1;
            var1.a(this);
            var1.k();
        }

        public void a() {
            d.h();
            boolean var2 = true;
            boolean var3;
            if (c != null) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (c.size() != 1) {
                var2 = false;
            }

            if (var3 & var2) {
                d.w = c.get(0);
                String var4 = xB.b().a(d.getApplicationContext(), 2131626403);
                String var6 = xB.b().a(d.getApplicationContext(), 2131626398);
                boolean var5 = "Y".equals(yB.c(d.w, "subscribe"));
                d.a(5, var4, var6, var5);
            }

        }

        public void a(String var1) {
            d.h();
        }

        public void b() {
            rB var1 = new rB();
            HashMap<String, Object> var2 = new HashMap<>();
            var2.put("user_id", SystemSettingActivity.d(d).h());
            c = var1.k(var2);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public class b extends MA {
        public String c;
        public String d;
        public final SystemSettingActivity e;

        public b(SystemSettingActivity var1, Context var2, String var3) {
            super(var2);
            e = var1;
            var1.a(this);
            d = var3;
            var1.k();
        }

        public void a() {
            e.h();
            if ("success".equals(c)) {
                bB.a(super.a, xB.b().a(e.getApplicationContext(), 2131624939), 0).show();
            }

            e.setResult(-1, e.v);
            e.finish();
        }

        public void a(String var1) {
            e.h();
            e.setResult(-1, e.v);
            e.finish();
        }

        public void b() {
            rB var1 = new rB();
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("login_id", SystemSettingActivity.h(e).e());
            userMap.put("session_id", SystemSettingActivity.i(e).f());
            userMap.put("subscribe", d);
            c = var1.Tb(userMap);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
