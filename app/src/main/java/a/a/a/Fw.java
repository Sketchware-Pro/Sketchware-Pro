package a.a.a;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.v;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.view.ManageViewActivity;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint("ResourceType")
public class Fw extends qA {
    public RecyclerView f;
    public String g;
    public String h = "N";
    public ArrayList<ProjectFileBean> i;
    public Fw.a j = null;
    public Boolean k = false;
    public TextView l;
    public int[] m = new int[19];

    public Fw() {
    }

    public final String a(int var1, String var2) {
        String var3 = wq.b(var1);
        StringBuilder var4 = new StringBuilder();
        var4.append(var3);
        int[] var5 = m;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var9 = var4.toString();
        ArrayList<ViewBean> var12 = jC.a(g).d(var2);
        var2 = var9;

        while (true) {
            boolean var7 = false;
            Iterator<ViewBean> var10 = var12.iterator();

            boolean var13;
            while (true) {
                var13 = var7;
                if (!var10.hasNext()) {
                    break;
                }

                if (var2.equals(var10.next().id)) {
                    var13 = true;
                    break;
                }
            }

            if (!var13) {
                return var2;
            }

            StringBuilder var8 = new StringBuilder();
            var8.append(var3);
            int[] var11 = m;
            var6 = var11[var1] + 1;
            var11[var1] = var6;
            var8.append(var6);
            var2 = var8.toString();
        }
    }

    public final ArrayList<ViewBean> a(String var1) {
        return rq.f(var1);
    }

    public void a(ProjectFileBean var1) {
        i.add(var1);
        j.c();
    }

    public void a(boolean var1) {
        k = var1;
        e();
        j.c();
    }

    public final void b(ProjectFileBean var1) {
        ProjectFileBean var2 = i.get(j.c);
        var2.keyboardSetting = var1.keyboardSetting;
        var2.orientation = var1.orientation;
        var2.options = var1.options;
        if (var1.hasActivityOption(4)) {
            ((ManageViewActivity) getActivity()).b(ProjectFileBean.getDrawerName(var2.fileName));
        } else {
            ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(var2.fileName));
        }

        if (var1.hasActivityOption(4) || var1.hasActivityOption(8)) {
            jC.c(g).c().useYn = "Y";
        }

    }

    public ArrayList<ProjectFileBean> c() {
        return i;
    }

    public final void c(ProjectFileBean var1) {
        ProjectFileBean var2 = i.get(j.c);
        ArrayList<ViewBean> var3 = jC.a(g).d(var2.getXmlName());

        for (int var4 = var3.size() - 1; var4 >= 0; --var4) {
            ViewBean var5 = var3.get(var4);
            jC.a(g).a(var2, var5);
        }

        ArrayList<ViewBean> var6 = a(var1.presetName);
        jC.a(g);
        Iterator<ViewBean> var8 = eC.a(var6).iterator();

        while (var8.hasNext()) {
            ViewBean var7 = var8.next();
            var7.id = a(var7.type, var2.getXmlName());
            jC.a(g).a(var2.getXmlName(), var7);
            if (var7.type == 3 && var2.fileType == 0) {
                jC.a(g).a(var2.getJavaName(), 1, var7.type, var7.id, "onClick");
            }
        }

    }

    public void d() {
        g = getActivity().getIntent().getStringExtra("sc_id");
        h = getActivity().getIntent().getStringExtra("compatUseYn");
        ArrayList<ProjectFileBean> var1 = jC.b(g).b();
        if (var1 != null) {
            Iterator<ProjectFileBean> var2 = var1.iterator();
            boolean var3 = false;

            while (var2.hasNext()) {
                ProjectFileBean var4 = var2.next();
                if (var4.fileName.equals("main")) {
                    i.add(0, var4);
                    var3 = true;
                } else {
                    i.add(var4);
                }
            }

            if (!var3) {
                i.add(0, new ProjectFileBean(0, "main"));
            }

        }
    }

    public final void e() {
        for (Iterator<ProjectFileBean> var1 = i.iterator(); var1.hasNext(); var1.next().isSelected = false) {
        }

    }

    public void f() {
        int var1 = i.size();

        while (true) {
            int var2 = var1 - 1;
            if (var2 < 0) {
                j.c();
                return;
            }

            ProjectFileBean var3 = i.get(var2);
            var1 = var2;
            if (var3.isSelected) {
                i.remove(var2);
                var1 = var2;
                if (var3.hasActivityOption(4)) {
                    ((ManageViewActivity) getActivity()).c(ProjectFileBean.getDrawerName(var3.fileName));
                    var1 = var2;
                }
            }
        }
    }

    public void g() {
        if (i != null) {
            if (i.size() == 0) {
                l.setVisibility(View.VISIBLE);
                f.setVisibility(View.GONE);
            } else {
                f.setVisibility(View.VISIBLE);
                l.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
        if (var1 == null) {
            d();
        } else {
            g = var1.getString("sc_id");
            h = var1.getString("compatUseYn");
            i = var1.getParcelableArrayList("activities");
        }

        j.c();
        g();
    }

    @Override
    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        Fw.a var4;
        if (var1 == 265) {
            if (var2 == -1) {
                b(var3.getParcelableExtra("project_file"));
                var4 = j;
                var4.c(var4.c);
            }
        } else if (var1 == 276 && var2 == -1) {
            ProjectFileBean var5 = var3.getParcelableExtra("preset_data");
            b(var5);
            c(var5);
            var4 = j;
            var4.c(var4.c);
        }

    }

    @Override
    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        ViewGroup var4 = (ViewGroup) var1.inflate(2131427442, var2, false);
        i = new ArrayList<>();
        f = var4.findViewById(2131231442);
        f.setHasFixedSize(true);
        f.setLayoutManager(new LinearLayoutManager(getContext()));
        j = new Fw.a(this, f);
        f.setAdapter(j);
        l = var4.findViewById(2131231997);
        l.setText(xB.b().a(getActivity(), 2131625290));
        return var4;
    }

    @Override
    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", g);
        var1.putString("compatUseYn", h);
        var1.putParcelableArrayList("activities", i);
        super.onSaveInstanceState(var1);
    }

    public class a extends androidx.recyclerview.widget.RecyclerView.a<Fw.a.a> {
        public final Fw d;
        public int c;

        public a(Fw var1, RecyclerView var2) {
            d = var1;
            c = -1;
            if (var2.getLayoutManager() instanceof LinearLayoutManager) {
                var2.a(new Bw(this, var1));
            }

        }

        @Override
        public int a() {
            return d.i != null ? d.i.size() : 0;
        }

        @Override
        public void b(Fw.a.a var1, int var2) {
            var1.v.setVisibility(View.VISIBLE);
            var1.y.setVisibility(View.GONE);
            if (var2 == 0) {
                var1.t.setVisibility(View.GONE);
            } else if (d.k) {
                var1.y.setVisibility(View.VISIBLE);
                var1.v.setVisibility(View.GONE);
            } else {
                var1.y.setVisibility(View.GONE);
                var1.v.setVisibility(View.VISIBLE);
            }

            ProjectFileBean var3 = d.i.get(var2);
            var1.v.setImageResource(f(var3.options));
            var1.w.setText(var3.getXmlName());
            var1.x.setText(var3.getJavaName());
            if (var3.isSelected) {
                var1.z.setImageResource(2131165707);
            } else {
                var1.z.setImageResource(2131165875);
            }

        }

        @Override
        public Fw.a.a b(ViewGroup var1, int var2) {
            return new Fw.a.a(this, LayoutInflater.from(var1.getContext()).inflate(2131427571, var1, false));
        }

        public final int f(int var1) {
            String var2 = String.format("%4s", Integer.toBinaryString(var1)).replace(' ', '0');
            Resources var3 = d.getContext().getResources();
            String var4 = "activity_" + var2;
            return var3.getIdentifier(var4, "drawable", d.getContext().getPackageName());
        }

        public class a extends v {
            public final Fw.a B;
            public ImageView A;
            public CheckBox t;
            public View u;
            public ImageView v;
            public TextView w;
            public TextView x;
            public LinearLayout y;
            public ImageView z;

            public a(Fw.a var1, View var2) {
                super(var2);
                B = var1;
                t = var2.findViewById(2131230893);
                u = var2.findViewById(2131232322);
                v = var2.findViewById(2131231104);
                w = var2.findViewById(2131232144);
                x = var2.findViewById(2131231863);
                y = var2.findViewById(2131230959);
                z = var2.findViewById(2131231132);
                A = var2.findViewById(2131231168);
                t.setVisibility(View.GONE);
                u.setOnClickListener(new Cw(this, var1));
                u.setOnLongClickListener(new Dw(this, var1));
                A.setOnClickListener(new Ew(this, var1));
            }
        }
    }
}
