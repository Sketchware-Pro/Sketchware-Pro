package a.a.a;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.Iterator;

public class xw extends qA {
    public RecyclerView f;
    public String g;
    public ArrayList<ProjectFileBean> h;
    public a i = null;
    public Boolean j = false;
    public TextView k;
    public int[] l = new int[19];

    public final String a(int var1, String var2) {
        String var3 = wq.b(var1);
        StringBuilder var4 = new StringBuilder();
        var4.append(var3);
        int[] var5 = this.l;
        int var6 = var5[var1] + 1;
        var5[var1] = var6;
        var4.append(var6);
        String var10 = var4.toString();
        ArrayList var13 = jC.a(this.g).d(var2);
        var2 = var10;

        while (true) {
            boolean var7 = false;
            Iterator var11 = var13.iterator();

            while (true) {
                var6 = var7;
                if (!var11.hasNext()) {
                    break;
                }

                if (var2.equals(((ViewBean) var11.next()).id)) {
                    var6 = 1;
                    break;
                }
            }

            if (!var6) {
                return var2;
            }

            StringBuilder var12 = new StringBuilder();
            var12.append(var3);
            int[] var9 = this.l;
            var6 = var9[var1] + 1;
            var9[var1] = var6;
            var12.append(var6);
            var2 = var12.toString();
        }
    }

    public final ArrayList<ViewBean> a(String var1, int var2) {
        ArrayList var3 = new ArrayList();
        ArrayList var4;
        if (var2 != 277) {
            if (var2 != 278) {
                var4 = var3;
            } else {
                var4 = rq.d(var1);
            }
        } else {
            var4 = rq.b(var1);
        }

        return var4;
    }

    public void a(ProjectFileBean var1) {
        this.h.add(var1);
        this.i.notifyDataSetChanged();
    }

    public void a(String var1) {
        Iterator var2 = this.h.iterator();

        boolean var4;
        while (true) {
            if (var2.hasNext()) {
                ProjectFileBean var3 = (ProjectFileBean) var2.next();
                if (var3.fileType != 2 || !var3.fileName.equals(var1)) {
                    continue;
                }

                var4 = true;
                break;
            }

            var4 = false;
            break;
        }

        if (!var4) {
            this.h.add(new ProjectFileBean(2, var1));
            this.i.notifyDataSetChanged();
        }

    }

    public void a(boolean var1) {
        this.j = var1;
        this.e();
        this.i.notifyDataSetChanged();
    }

    public void b(String var1) {
        for (ProjectFileBean var3 : this.h) {
            if (var3.fileType == 2 && var3.fileName.equals(var1)) {
                this.h.remove(var3);
                break;
            }
        }

        this.i.notifyDataSetChanged();
    }

    public ArrayList<ProjectFileBean> c() {
        return this.h;
    }

    public void d() {
        ArrayList var1 = jC.b(this.g).c();
        if (var1 != null) {
            for (ProjectFileBean var2 : var1) {
                this.h.add(var2);
            }

        }
    }

    public final void e() {
        for (Iterator var1 = this.h.iterator(); var1.hasNext(); ((ProjectFileBean) var1.next()).isSelected = false) {
        }

    }

    public void f() {
        int var1 = this.h.size();

        while (true) {
            int var2 = var1 - 1;
            if (var2 < 0) {
                this.i.notifyDataSetChanged();
                return;
            }

            var1 = var2;
            if (((ProjectFileBean) this.h.get(var2)).isSelected) {
                this.h.remove(var2);
                var1 = var2;
            }
        }
    }

    public void g() {
        ArrayList var1 = this.h;
        if (var1 != null) {
            if (var1.size() == 0) {
                this.k.setVisibility(0);
                this.f.setVisibility(8);
            } else {
                this.k.setVisibility(8);
                this.f.setVisibility(0);
            }
        }

    }

    public void onActivityCreated(Bundle var1) {
        super.onActivityCreated(var1);
        if (var1 == null) {
            this.d();
        } else {
            this.g = var1.getString("sc_id");
            this.h = var1.getParcelableArrayList("custom_views");
        }

        this.f.getAdapter().notifyDataSetChanged();
        this.g();
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        if ((var1 == 277 || var1 == 278) && var2 == -1) {
            ProjectFileBean var4 = (ProjectFileBean) this.h.get(this.i.c);
            ArrayList var5 = jC.a(this.g).d(var4.getXmlName());

            for (int var7 = var5.size() - 1; var7 >= 0; --var7) {
                ViewBean var6 = (ViewBean) var5.get(var7);
                jC.a(this.g).a(var4, var6);
            }

            ArrayList var8 = this.a(((ProjectFileBean) var3.getParcelableExtra("preset_data")).presetName, var1);
            jC.a(this.g);

            for (ViewBean var9 : eC.a(var8)) {
                var9.id = this.a(var9.type, var4.getXmlName());
                jC.a(this.g).a(var4.getXmlName(), var9);
                if (var9.type == 3 && var4.fileType == 0) {
                    jC.a(this.g).a(var4.getJavaName(), 1, var9.type, var9.id, "onClick");
                }
            }

            a var10 = this.i;
            ((RecyclerView.Adapter) var10).notifyItemChanged(var10.c);
        }

    }

    public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
        ViewGroup var4 = (ViewGroup) var1.inflate(2131427442, var2, false);
        this.h = new ArrayList();
        this.f = (RecyclerView) var4.findViewById(2131231442);
        this.f.setHasFixedSize(true);
        this.f.setLayoutManager(new LinearLayoutManager(((Fragment) this).getContext()));
        this.i = new a(this.f);
        this.f.setAdapter(this.i);
        if (var3 == null) {
            this.g = ((Fragment) this).getActivity().getIntent().getStringExtra("sc_id");
        } else {
            this.g = var3.getString("sc_id");
        }

        this.k = (TextView) var4.findViewById(2131231997);
        this.k.setText(xB.b().a(((Fragment) this).getActivity(), 2131625291));
        return var4;
    }

    public void onSaveInstanceState(Bundle var1) {
        var1.putString("sc_id", this.g);
        var1.putParcelableArrayList("custom_views", this.h);
        super.onSaveInstanceState(var1);
    }

    public class a extends RecyclerView.Adapter<a> {
        public int c;
        public final xw d;

        public a(RecyclerView var2) {
            this.d = xw.this;
            this.c = -1;
            if (var2.getLayoutManager() instanceof LinearLayoutManager) {
                var2.addOnScrollListener(new tw(this, xw.this));
            }

        }

        public int getItemCount() {
            int var1;
            if (this.d.h != null) {
                var1 = this.d.h.size();
            } else {
                var1 = 0;
            }

            return var1;
        }

        public void onBindViewHolder(a var1, int var2) {
            if (this.d.j) {
                var1.x.setVisibility(0);
                var1.u.setVisibility(8);
            } else {
                var1.x.setVisibility(8);
                var1.u.setVisibility(0);
            }

            ProjectFileBean var3 = (ProjectFileBean) this.d.h.get(var2);
            var1.u.setImageResource(2131165293);
            var1.t.setChecked(var3.isSelected);
            var2 = var3.fileType;
            if (var2 == 1) {
                var1.w.setText(var3.getXmlName());
            } else if (var2 == 2) {
                var1.t.setVisibility(8);
                var1.u.setImageResource(2131165283);
                var1.w.setText(var3.fileName.substring(1));
            }

            if (var3.isSelected) {
                var1.v.setImageResource(2131165707);
            } else {
                var1.v.setImageResource(2131165875);
            }

        }

        public a onCreateViewHolder(ViewGroup var1, int var2) {
            return new a(LayoutInflater.from(var1.getContext()).inflate(2131427570, var1, false));
        }

        public class a extends RecyclerView.ViewHolder {
            public CheckBox t;
            public ImageView u;
            public ImageView v;
            public TextView w;
            public LinearLayout x;
            public ImageView y;
            public final a z;

            public a(View var2) {
                super(var2);
                this.z = a.this;
                this.t = (CheckBox) var2.findViewById(2131230893);
                this.u = (ImageView) var2.findViewById(2131231104);
                this.w = (TextView) var2.findViewById(2131232144);
                this.x = (LinearLayout) var2.findViewById(2131230959);
                this.v = (ImageView) var2.findViewById(2131231132);
                this.y = (ImageView) var2.findViewById(2131231168);
                this.t.setVisibility(8);
                var2.setOnClickListener(new uw(this, a.this));
                var2.setOnLongClickListener(new vw(this, a.this));
                this.y.setOnClickListener(new ww(this, a.this));
            }
        }
    }
}
