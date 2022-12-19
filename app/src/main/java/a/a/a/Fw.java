package a.a.a;

import android.annotation.SuppressLint;
import android.app.Activity;
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
        ProjectFileBean projectFileBean = i.get(j.c);

        ArrayList<ViewBean> fileViewBeans = jC.a(g).d(projectFileBean.getXmlName());
        for (int i = fileViewBeans.size() - 1; i >= 0; --i) {
            jC.a(g).a(projectFileBean, fileViewBeans.get(i));
        }

        ArrayList<ViewBean> var6 = a(var1.presetName);
        for (ViewBean viewBean : eC.a(var6)) {
            viewBean.id = a(viewBean.type, projectFileBean.getXmlName());
            jC.a(g).a(projectFileBean.getXmlName(), viewBean);
            if (viewBean.type == 3 && projectFileBean.fileType == 0) {
                jC.a(g).a(projectFileBean.getJavaName(), 1, viewBean.type, viewBean.id, "onClick");
            }
        }
    }

    public void d() {
        g = getActivity().getIntent().getStringExtra("sc_id");
        h = getActivity().getIntent().getStringExtra("compatUseYn");
        ArrayList<ProjectFileBean> projectFiles = jC.b(g).b();
        if (projectFiles != null) {
            boolean isMainActivityFile = false;
            for (ProjectFileBean projectFileBean : projectFiles) {
                if (projectFileBean.fileName.equals("main")) {
                    i.add(0, projectFileBean);
                    isMainActivityFile = true;
                } else {
                    i.add(projectFileBean);
                }
            }
            if (!isMainActivityFile) {
                i.add(0, new ProjectFileBean(0, "main"));
            }
        }
    }

    public final void e() {
        for (ProjectFileBean projectFileBean : i) {
            projectFileBean.isSelected = false;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            d();
        } else {
            g = savedInstanceState.getString("sc_id");
            h = savedInstanceState.getString("compatUseYn");
            i = savedInstanceState.getParcelableArrayList("activities");
        }

        j.c();
        g();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 265) {
            if (resultCode == Activity.RESULT_OK) {
                b(data.getParcelableExtra("project_file"));
                j.c(j.c);
            }
        } else if (requestCode == 276 && resultCode == Activity.RESULT_OK) {
            ProjectFileBean projectFileBean = data.getParcelableExtra("preset_data");
            b(projectFileBean);
            c(projectFileBean);
            j.c(j.c);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) layoutInflater.inflate(2131427442, parent, false);
        i = new ArrayList<>();
        f = root.findViewById(2131231442);
        f.setHasFixedSize(true);
        f.setLayoutManager(new LinearLayoutManager(getContext()));
        j = new Fw.a(this, f);
        f.setAdapter(j);
        l = root.findViewById(2131231997);
        l.setText(xB.b().a(getActivity(), 2131625290));
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle newState) {
        newState.putString("sc_id", g);
        newState.putString("compatUseYn", h);
        newState.putParcelableArrayList("activities", i);
        super.onSaveInstanceState(newState);
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
        public void b(Fw.a.a viewHolder, int position) {
            viewHolder.v.setVisibility(View.VISIBLE);
            viewHolder.y.setVisibility(View.GONE);
            if (position == 0) {
                viewHolder.t.setVisibility(View.GONE);
            } else if (d.k) {
                viewHolder.y.setVisibility(View.VISIBLE);
                viewHolder.v.setVisibility(View.GONE);
            } else {
                viewHolder.y.setVisibility(View.GONE);
                viewHolder.v.setVisibility(View.VISIBLE);
            }

            ProjectFileBean projectFileBean = d.i.get(position);
            viewHolder.v.setImageResource(f(projectFileBean.options));
            viewHolder.w.setText(projectFileBean.getXmlName());
            viewHolder.x.setText(projectFileBean.getJavaName());
            if (projectFileBean.isSelected) {
                viewHolder.z.setImageResource(2131165707);
            } else {
                viewHolder.z.setImageResource(2131165875);
            }
        }

        @Override
        public Fw.a.a b(ViewGroup parent, int viewType) {
            return new Fw.a.a(this, LayoutInflater.from(parent.getContext()).inflate(2131427571, parent, false));
        }

        public final int f(int var1) {
            String var2 = String.format("%4s", Integer.toBinaryString(var1)).replace(' ', '0');
            Resources var3 = d.getContext().getResources();
            String var4 = "activity_" + var2;
            return var3.getIdentifier(var4, "drawable", d.getContext().getPackageName());
        }

        public class a extends RecyclerView.v {
            public final Fw.a B;
            public ImageView A;
            public CheckBox t;
            public View u;
            public ImageView v;
            public TextView w;
            public TextView x;
            public LinearLayout y;
            public ImageView z;

            public a(Fw.a var1, View itemView) {
                super(itemView);
                B = var1;
                t = itemView.findViewById(2131230893);
                u = itemView.findViewById(2131232322);
                v = itemView.findViewById(2131231104);
                w = itemView.findViewById(2131232144);
                x = itemView.findViewById(2131231863);
                y = itemView.findViewById(2131230959);
                z = itemView.findViewById(2131231132);
                A = itemView.findViewById(0x7f0801c0);
                t.setVisibility(View.GONE);
                u.setOnClickListener(new Cw(this, var1));
                u.setOnLongClickListener(new Dw(this, var1));
                A.setOnClickListener(new Ew(this, var1));
            }
        }
    }
}
