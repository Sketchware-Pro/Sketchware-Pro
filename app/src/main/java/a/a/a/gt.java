package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"ViewConstructor", "ResourceType"})
public class gt extends LinearLayout {

    public Activity a;
    public HashMap<Integer, ArrayList<gt.b>> b;
    public ArrayList<gt.b> c;
    public ArrayList<gt.b> d;
    public ArrayList<gt.b> e;
    public RecyclerView f;
    public RecyclerView g;
    public gt.a h;
    public gt.c i;
    public LinearLayout j;
    public TextView k;
    public Dialog l;
    public gt.b m;

    public gt(Activity var1) {
        super(var1);
        a(var1);
    }

    private void setPreview(gt.b var1) {
        m = var1;
        k.setText(a(var1.a, var1.b));
    }

    public final String a(String var1, String var2) {
        byte var4;
        byte var5;
        label77:
        {
            int var3 = var1.hashCode();
            var4 = 0;
            if (var3 != 98) {
                if (var3 != 100) {
                    if (var3 == 115 && var1.equals("s")) {
                        var5 = 2;
                        break label77;
                    }
                } else if (var1.equals("d")) {
                    var5 = 1;
                    break label77;
                }
            } else if (var1.equals("b")) {
                var5 = 0;
                break label77;
            }

            var5 = -1;
        }

        if (var5 != 0) {
            if (var5 != 1) {
                if (var5 != 2) {
                    var1 = kq.b(var2);
                } else {
                    var1 = xB.b().a(getContext(), 2131625592);
                }
            } else {
                var1 = xB.b().a(getContext(), 2131625591);
            }
        } else {
            var1 = xB.b().a(getContext(), 2131625586);
        }

        label67:
        {
            switch (var2.hashCode()) {
                case -823676088:
                    if (var2.equals("varInt")) {
                        var5 = var4;
                        break label67;
                    }
                    break;
                case -823672651:
                    if (var2.equals("varMap")) {
                        var5 = 3;
                        break label67;
                    }
                    break;
                case -823666294:
                    if (var2.equals("varStr")) {
                        var5 = 2;
                        break label67;
                    }
                    break;
                case 181944945:
                    if (var2.equals("listInt")) {
                        var5 = 4;
                        break label67;
                    }
                    break;
                case 181948382:
                    if (var2.equals("listMap")) {
                        var5 = 6;
                        break label67;
                    }
                    break;
                case 181954739:
                    if (var2.equals("listStr")) {
                        var5 = 5;
                        break label67;
                    }
                    break;
                case 235637425:
                    if (var2.equals("varBool")) {
                        var5 = 1;
                        break label67;
                    }
            }

            var5 = -1;
        }

        switch (var5) {
            case 0:
                var1 = xB.b().a(getContext(), 2131625591);
                break;
            case 1:
                var1 = xB.b().a(getContext(), 2131625586);
                break;
            case 2:
                var1 = xB.b().a(getContext(), 2131625592);
                break;
            case 3:
                var1 = xB.b().a(getContext(), 2131625590);
                break;
            case 4:
                var1 = xB.b().a(getContext(), 2131625588);
                break;
            case 5:
                var1 = xB.b().a(getContext(), 2131625589);
                break;
            case 6:
                var1 = xB.b().a(getContext(), 2131625587);
        }

        return var1;
    }

    public final void a() {
        e.add(new gt.b(this, "m", "intent", 2131166254));
        e.add(new gt.b(this, "m", "file", 2131166268));
        e.add(new gt.b(this, "m", "calendar", 2131166238));
        e.add(new gt.b(this, "m", "vibrator", 2131166277));
        e.add(new gt.b(this, "m", "timer", 2131166276));
        e.add(new gt.b(this, "m", "dialog", 2131166235));
        e.add(new gt.b(this, "m", "mediaplayer", 2131166259));
        e.add(new gt.b(this, "m", "soundpool", 2131166269));
        e.add(new gt.b(this, "m", "objectanimator", 2131166262));
        e.add(new gt.b(this, "m", "firebase", 2131166245));
        e.add(new gt.b(this, "m", "firebaseauth", 2131166245));
        e.add(new gt.b(this, "m", "firebasestorage", 2131166245));
        e.add(new gt.b(this, "m", "camera", 2131166240));
        e.add(new gt.b(this, "m", "filepicker", 2131166244));
        e.add(new gt.b(this, "m", "requestnetwork", 2131166261));
        e.add(new gt.b(this, "m", "texttospeech", 2131166274));
        e.add(new gt.b(this, "m", "speechtotext", 2131166271));
        e.add(new gt.b(this, "m", "locationmanager", 2131166258));
        e.add(new gt.b(this, "m", "videoad", 2131166295));
        e.add(new gt.b(this, "m", "progressdialog", 2131166296));
        e.add(new gt.b(this, "m", "timepickerdialog", 2131166276));
        e.add(new gt.b(this, "m", "notification", 2131166298));
    }

    public final void a(Activity var1) {
        a = var1;
        wB.a(var1, this, 2131427770);
        k = findViewById(2131232082);
        j = (LinearLayout) wB.a(var1, 2131427771);
        f = j.findViewById(2131232303);
        g = j.findViewById(2131232304);
        ((TextView) j.findViewById(2131232195)).setText(xB.b().a(var1, 2131625507));
        b = new HashMap<>();
        c = new ArrayList<>();
        d = new ArrayList<>();
        e = new ArrayList<>();
        b();
        c();
        a();
        b.put(0, c);
        b.put(1, d);
        b.put(2, e);
        f.setHasFixedSize(true);
        LinearLayoutManager var4 = new LinearLayoutManager(var1, 1, false);
        f.setLayoutManager(var4);
        h = new gt.a(this);
        h.a(b);
        f.setAdapter(h);
        g.setHasFixedSize(true);
        var4 = new LinearLayoutManager(var1, 1, false);
        g.setLayoutManager(var4);
        i = new gt.c(this);
        g.setAdapter(i);
        h.d = 0;
        i.a(b.get(0));
        setPreview(c.get(0));
        l = new Dialog(var1);
        if (j.getParent() != null) {
            ((ViewGroup) j.getParent()).removeView(j);
        }

        l.setContentView(j);
        findViewById(2131230931).setOnClickListener(view -> {
            if (!mB.a()) {
                d();
            }
        });
    }

    public final void b() {
        c.add(new gt.b(this, "b", "", 2131165876));
        c.add(new gt.b(this, "d", "", 2131166000));
        c.add(new gt.b(this, "s", "", 2131165191));
        c.add(new gt.b(this, "m", "varMap", 2131165785));
        c.add(new gt.b(this, "m", "listInt", 2131165783));
        c.add(new gt.b(this, "m", "listStr", 2131165783));
        c.add(new gt.b(this, "m", "listMap", 2131165783));
    }

    public final void c() {
        d.add(new gt.b(this, "m", "view", 2131165939));
        d.add(new gt.b(this, "m", "textview", 2131166275));
        d.add(new gt.b(this, "m", "imageview", 2131166253));
        d.add(new gt.b(this, "m", "checkbox", 2131166241));
        d.add(new gt.b(this, "m", "switch", 2131166273));
        d.add(new gt.b(this, "m", "listview", 2131166257));
        d.add(new gt.b(this, "m", "spinner", 2131166272));
        d.add(new gt.b(this, "m", "webview", 2131166278));
        d.add(new gt.b(this, "m", "seekbar", 2131166267));
        d.add(new gt.b(this, "m", "progressbar", 2131166263));
        d.add(new gt.b(this, "m", "calendarview", 2131166238));
        d.add(new gt.b(this, "m", "radiobutton", 2131166264));
        d.add(new gt.b(this, "m", "ratingbar", 2131166177));
        d.add(new gt.b(this, "m", "videoview", 2131166259));
        d.add(new gt.b(this, "m", "searchview", 2131165849));
        d.add(new gt.b(this, "m", "gridview", 2131165662));
        d.add(new gt.b(this, "m", "actv", 2131166242));
        d.add(new gt.b(this, "m", "mactv", 2131166242));
        d.add(new gt.b(this, "m", "viewpager", 2131166265));
        d.add(new gt.b(this, "m", "badgeview", 2131166031));
    }

    public final void d() {
        l.show();
    }

    public Pair<String, String> getSelectedItem() {
        return new Pair<>(m.a, m.b);
    }

    public class a extends androidx.recyclerview.widget.RecyclerView.a<gt.a.a> {
        public final gt e;
        public HashMap<Integer, ArrayList<gt.b>> c;
        public int d;

        public a(gt var1) {
            e = var1;
            d = -1;
        }

        @Override
        public int a() {
            return c.size();
        }

        @Override
        public void b(gt.a.a var1, int var2) {
            if (var2 != 0) {
                if (var2 != 1) {
                    if (var2 == 2) {
                        var1.u.setImageResource(2131165504);
                        var1.v.setText(xB.b().a(e.getContext(), 2131624979));
                    }
                } else {
                    var1.u.setImageResource(2131166257);
                    var1.v.setText(xB.b().a(e.getContext(), 2131625046));
                }
            } else {
                var1.u.setImageResource(2131166227);
                var1.v.setText(xB.b().a(e.getContext(), 2131625045));
            }

            if (d == var2) {
                var1.t.setBackgroundColor(-1);
            } else {
                var1.t.setBackgroundColor(e.getResources().getColor(2131034221));
            }

        }

        public void a(HashMap<Integer, ArrayList<gt.b>> var1) {
            c = var1;
        }

        @Override
        public gt.a.a b(ViewGroup var1, int var2) {
            return new gt.a.a(this, LayoutInflater.from(e.getContext()).inflate(2131427769, var1, false));
        }

        public class a extends v {
            public final gt.a w;
            public LinearLayout t;
            public ImageView u;
            public TextView v;

            public a(gt.a var1, View var2) {
                super(var2);
                w = var1;
                t = var2.findViewById(2131230931);
                u = var2.findViewById(2131231090);
                v = var2.findViewById(2131231561);
                var2.setOnClickListener(view -> {
                    w.d = j();
                    gt.this.i.a(gt.this.b.get(w.d));
                    gt.this.i.c();
                    w.c();
                });
            }
        }
    }

    public class b {
        public final gt d;
        public String a;
        public String b;
        public int c;

        public b(gt var1, String var2, String var3, int var4) {
            d = var1;
            a = var2;
            b = var3;
            c = var4;
        }
    }

    public class c extends androidx.recyclerview.widget.RecyclerView.a<gt.c.a> {

        public final gt d;
        public ArrayList<gt.b> c;

        public c(gt var1) {
            d = var1;
        }

        @Override
        public int a() {
            return c.size();
        }

        @Override
        public void b(gt.c.a var1, int var2) {
            gt.b var3 = c.get(var2);
            var1.t.setText(d.a(var3.a, var3.b));
            var1.u.setImageResource(var3.c);
        }

        public void a(ArrayList<gt.b> var1) {
            c = var1;
        }

        @Override
        public gt.c.a b(ViewGroup var1, int var2) {
            return new gt.c.a(this, wB.a(d.getContext(), 2131427772));
        }

        public class a extends v {
            public final gt.c v;
            public TextView t;
            public ImageView u;

            public a(gt.c var1, View var2) {
                super(var2);
                v = var1;
                t = var2.findViewById(2131231561);
                u = var2.findViewById(2131231090);
                var2.setOnClickListener(view -> {
                    setPreview(gt.this.b.get(d).get(j()));
                    gt.this.l.hide();
                });
            }
        }
    }
}
