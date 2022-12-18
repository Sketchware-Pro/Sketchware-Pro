//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

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
        int[] var5 = this.m;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var9 = var4.toString();
        ArrayList var12 = jC.a(this.g).d(var2);
        var2 = var9;

        while(true) {
            boolean var7 = false;
            Iterator var10 = var12.iterator();

            boolean var13;
            while(true) {
                var13 = var7;
                if (!var10.hasNext()) {
                    break;
                }

                if (var2.equals(((ViewBean)var10.next()).id)) {
                    var13 = true;
                    break;
                }
            }

            if (!var13) {
                return var2;
            }

            StringBuilder var8 = new StringBuilder();
            var8.append(var3);
            int[] var11 = this.m;
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
        this.i.add(var1);
        this.j.c();
    }

    public void a(boolean var1) {
        this.k = var1;
        this.e();
        this.j.c();
    }

    public final void b(ProjectFileBean var1) {
        ProjectFileBean var2 = (ProjectFileBean)this.i.get(this.j.c);
        var2.keyboardSetting = var1.keyboardSetting;
        var2.orientation = var1.orientation;
        var2.options = var1.options;
        if (var1.hasActivityOption(4)) {
            ((ManageViewActivity)this.getActivity()).b(ProjectFileBean.getDrawerName(var2.fileName));
        } else {
            ((ManageViewActivity)this.getActivity()).c(ProjectFileBean.getDrawerName(var2.fileName));
        }

        if (var1.hasActivityOption(4) || var1.hasActivityOption(8)) {
            jC.c(this.g).c().useYn = "Y";
        }

    }

    public ArrayList<ProjectFileBean> c() {
        return this.i;
    }

    public final void c(ProjectFileBean var1) {
        ProjectFileBean var2 = (ProjectFileBean)this.i.get(this.j.c);
        ArrayList var3 = jC.a(this.g).d(var2.getXmlName());

        for(int var4 = var3.size() - 1; var4 >= 0; --var4) {
            ViewBean var5 = (ViewBean)var3.get(var4);
            jC.a(this.g).a(var2, var5);
        }

        ArrayList var6 = this.a(var1.presetName);
        jC.a(this.g);
        Iterator var8 = eC.a(var6).iterator();

        while(var8.hasNext()) {
            ViewBean var7 = (ViewBean)var8.next();
            var7.id = this.a(var7.type, var2.getXmlName());
            jC.a(this.g).a(var2.getXmlName(), var7);
            if (var7.type == 3 && var2.fileType == 0) {
                jC.a(this.g).a(var2.getJavaName(), 1, var7.type, var7.id, "onClick");
            }
        }

    }

    public void d() {
        this.g = this.getActivity().getIntent().getStringExtra("sc_id");
        this.h = this.getActivity().getIntent().getStringExtra("compatUseYn");
        ArrayList var1 = jC.b(this.g).b();
        if (var1 != null) {
            Iterator var2 = var1.iterator();
            boolean var3 = false;

            while(var2.hasNext()) {
                ProjectFileBean var4 = (ProjectFileBean)var2.next();
                if (var4.fileName.equals("main")) {
                    this.i.add(0, var4);
                    var3 = true;
                } else {
                    this.i.add(var4);
                }
            }

            if (!var3) {
                this.i.add(0, new ProjectFileBean(0, "main"));
            }

        }
    }

    public final void e() {
        for(Iterator var1 = this.i.iterator(); var1.hasNext(); ((ProjectFileBean)var1.next()).isSelected = false) {
        }

    }

    public void f() {
        int var1 = this.i.size();

        while(true) {
            int var2 = var1 - 1;
            if (var2 < 0) {
                this.j.c();
                return;
            }

            ProjectFileBean var3 = (ProjectFileBean)this.i.get(var2);
            var1 = var2;
            if (var3.isSelected) {
                this.i.remove(var2);
                var1 = var2;
                if (var3.hasActivityOption(4)) {
                    ((ManageViewActivity)this.getActivity()).c(ProjectFileBean.getDrawerName(var3.fileName));
                    var1 = var2;
                }
            }
        }
    }

    public void g() {
        ArrayList var1 = this.i;
        if (var1 != null) {
            if (var1.size() == 0) {
                this.l.setVisibility(0);
                this.f.setVisibility(8);
            } else {
                this.f.setVisibility(0);
                this.l.setVisibility(8);
            }
        }

    }

    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
        if (var1 == null) {
            this.d();
        } else {
            this.g = var1.getString("sc_id");
            this.h = var1.getString("compatUseYn");
            this.i = var1.getParcelableArrayList("activities");
        }

        this.j.c();
        this.g();
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        Fw.a var4;
        if (var1 == 265) {
            if (var2 == -1) {
                this.b((ProjectFileBean)var3.getParcelableExtra("project_file"));
                var4 = this.j;
                var4.c(var4.c);
            }
        } else if (var1 == 276 && var2 == -1) {
            ProjectFileBean var5 = (ProjectFileBean)var3.getParcelableExtra("preset_data");
            this.b(var5);
            this.c(var5);
            var4 = this.j;
            var4.c(var4.c);
        }

    }

    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        ViewGroup var4 = (ViewGroup)var1.inflate(2131427442, var2, false);
        this.i = new ArrayList();
        this.f = (RecyclerView)var4.findViewById(2131231442);
        this.f.setHasFixedSize(true);
        this.f.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.j = new Fw.a(this, this.f);
        this.f.setAdapter(this.j);
        this.l = (TextView)var4.findViewById(2131231997);
        this.l.setText(xB.b().a(this.getActivity(), 2131625290));
        return var4;
    }

    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.g);
        var1.putString("compatUseYn", this.h);
        var1.putParcelableArrayList("activities", this.i);
        super.onSaveInstanceState(var1);
    }

    public class a extends androidx.recyclerview.widget.RecyclerView.a<Fw.a.a> {
        public int c;
        public final Fw d;

        public a(Fw var1, RecyclerView var2) {
            this.d = var1;
            this.c = -1;
            if (var2.getLayoutManager() instanceof LinearLayoutManager) {
                var2.a(new Bw(this, var1));
            }

        }

        public int a() {
            return this.d.i != null ? this.d.i.size() : 0;
        }

        public void a(Fw.a.a var1, int var2) {
            var1.v.setVisibility(0);
            var1.y.setVisibility(8);
            if (var2 == 0) {
                var1.t.setVisibility(8);
            } else if (this.d.k) {
                var1.y.setVisibility(0);
                var1.v.setVisibility(8);
            } else {
                var1.y.setVisibility(8);
                var1.v.setVisibility(0);
            }

            ProjectFileBean var3 = (ProjectFileBean)this.d.i.get(var2);
            var1.v.setImageResource(this.f(var3.options));
            var1.w.setText(var3.getXmlName());
            var1.x.setText(var3.getJavaName());
            if (var3.isSelected) {
                var1.z.setImageResource(2131165707);
            } else {
                var1.z.setImageResource(2131165875);
            }

        }

        public Fw.a.a b(ViewGroup var1, int var2) {
            return new Fw.a.a(this, LayoutInflater.from(var1.getContext()).inflate(2131427571, var1, false));
        }

        public final int f(int var1) {
            String var2 = String.format("%4s", Integer.toBinaryString(var1)).replace(' ', '0');
            Resources var3 = this.d.getContext().getResources();
            StringBuilder var4 = new StringBuilder();
            var4.append("activity_");
            var4.append(var2);
            return var3.getIdentifier(var4.toString(), "drawable", this.d.getContext().getPackageName());
        }

        public class a extends v {
            public ImageView A;
            public final Fw.a B;
            public CheckBox t;
            public View u;
            public ImageView v;
            public TextView w;
            public TextView x;
            public LinearLayout y;
            public ImageView z;

            public a(Fw.a var1, View var2) {
                super(var2);
                this.B = var1;
                this.t = (CheckBox)var2.findViewById(2131230893);
                this.u = var2.findViewById(2131232322);
                this.v = (ImageView)var2.findViewById(2131231104);
                this.w = (TextView)var2.findViewById(2131232144);
                this.x = (TextView)var2.findViewById(2131231863);
                this.y = (LinearLayout)var2.findViewById(2131230959);
                this.z = (ImageView)var2.findViewById(2131231132);
                this.A = (ImageView)var2.findViewById(2131231168);
                this.t.setVisibility(8);
                this.u.setOnClickListener(new Cw(this, var1));
                this.u.setOnLongClickListener(new Dw(this, var1));
                this.A.setOnClickListener(new Ew(this, var1));
            }
        }
    }
}
