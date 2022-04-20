package a.a.a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.besome.sketch.MainActivity;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.export.ExportProjectActivity;
import com.besome.sketch.lib.ui.CircleImageView;
import com.besome.sketch.projects.MyProjectButton;
import com.besome.sketch.projects.MyProjectButtonLayout;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.besome.sketch.publish.account.PublishAccountSettingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mod.hey.studios.project.ProjectSettingsDialog;
import mod.hey.studios.project.ProjectTracker;
import mod.hey.studios.project.backup.BackupRestoreManager;

@SuppressLint("ResourceType")
public class GC extends DA implements View.OnClickListener {

    public SwipeRefreshLayout f;
    public ArrayList<HashMap<String, Object>> g;
    public RecyclerView h;
    public CardView i;
    public LinearLayout j;
    public ImageView k;
    public TextView l;
    public CardView m;
    public LinearLayout n;
    public ImageView o;
    public TextView p;
    public Boolean q;
    public AnimatorSet r;
    public AnimatorSet s;
    public ValueAnimator t;
    public ValueAnimator u;
    public ProjectsAdapter v;
    public DB w;
    public FloatingActionButton x;
    public ro y;

    // $FF: synthetic method
    public static EA k(GC var0) {
        return var0.d;
    }

    // $FF: synthetic method
    public static EA l(GC var0) {
        return var0.d;
    }

    // $FF: synthetic method
    public static Zo n(GC var0) {
        return var0.e;
    }

    public final void a(int position, boolean advancedOpen) {
        a(position, advancedOpen, 206);
    }

    public final void a(int position, boolean advancedOpen, int requestCode) {
        if (super.c()) {
            Intent intent = new Intent(getContext(), MyProjectSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("sc_id", yB.c(g.get(position), "sc_id"));
            intent.putExtra("is_update", true);
            intent.putExtra("advanced_open", advancedOpen);
            intent.putExtra("index", position);
            startActivityForResult(intent, requestCode);
        } else if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    public final void a(ViewGroup parent) {
        f = parent.findViewById(2131231774);
        f.setOnRefreshListener(() -> {
            if (f.d()) f.setRefreshing(false);

            if (c()) {
                g();
            } else if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).s();
            }

        });
        x = getActivity().findViewById(2131231054);
        x.setOnClickListener(this);
        h = parent.findViewById(2131231557);
        h.setHasFixedSize(true);
        h.setLayoutManager(new LinearLayoutManager(getContext()));
        v = new ProjectsAdapter(this, h);
        h.setAdapter(v);
        h.setItemAnimator(new ci());
        i = parent.findViewById(2131230945);
        j = parent.findViewById(2131230936);
        k = j.findViewById(2131231228);
        l = j.findViewById(2131231929);
        j.setOnClickListener(this);
        q = false;
        m = parent.findViewById(2131230947);
        n = parent.findViewById(2131231371);
        o = parent.findViewById(2131231231);
        p = parent.findViewById(2131232041);
        p.setText("Restore project");
        n.setOnClickListener(this);
        ((TextView) parent.findViewById(2131231929)).setText(xB.b().a(getContext(), 2131625662));
        r = new AnimatorSet();
        s = new AnimatorSet();
        t = ValueAnimator.ofFloat(wB.a(getContext(), 96.0F), wB.a(getContext(), 48.0F));
        t.addUpdateListener(var11 -> {
            m.getLayoutParams().height = (int) var11.getAnimatedValue();
            m.requestLayout();
        });
        u = ValueAnimator.ofFloat(wB.a(getContext(), 48.0F), wB.a(getContext(), 96.0F));
        u.addUpdateListener(var1 -> {
            m.getLayoutParams().height = (int) var1.getAnimatedValue();
            m.requestLayout();
        });
        r.playTogether(t,
                ObjectAnimator.ofFloat(p, View.TRANSLATION_Y, 0.0F, -100.0F),
                ObjectAnimator.ofFloat(p, View.ALPHA, 1.0F, 0.0F),
                ObjectAnimator.ofFloat(o, View.SCALE_X, 1.0F, 0.5F),
                ObjectAnimator.ofFloat(o, View.SCALE_Y, 1.0F, 0.5F));
        s.playTogether(u,
                ObjectAnimator.ofFloat(p, View.TRANSLATION_Y, -100.0F, 0.0F),
                ObjectAnimator.ofFloat(p, View.ALPHA, 0.0F, 1.0F),
                ObjectAnimator.ofFloat(o, View.SCALE_X, 0.5F, 1.0F),
                ObjectAnimator.ofFloat(o, View.SCALE_Y, 0.5F, 1.0F));
        r.setDuration(300L);
        s.setDuration(300L);
        g();
    }

    public void a(boolean var1) {
        g = lC.a();
        if (g.size() > 0) {
            Collections.sort(g,
                    (first, second) -> Integer.compare(Integer.parseInt(yB.c(first, "sc_id")),
                            Integer.parseInt(yB.c(second, "sc_id"))) * -1);
        }

        h.getAdapter().c();
        if (var1) h();

    }

    public void b(int requestCode) {
        if (requestCode == 206) {
            i();
        } else if (requestCode == 700) {
            j();
        }
    }

    public final void b(String sc_id) {
        Intent intent = new Intent(getContext(), DesignActivity.class);
        ProjectTracker.setScId(sc_id);
        intent.putExtra("sc_id", sc_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 204);
    }

    public void c(int requestCode) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    public void c(String var1) {
        int var2 = 0;

        while (true) {
            if (var2 >= g.size()) {
                var2 = 0;
                break;
            }

            if (yB.c(g.get(var2), "sc_id").equals(var1)) {
                break;
            }

            ++var2;
        }

        a(var2, false);
    }

    public void d() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    public void e() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).s();
        }

    }

    public int f() {
        return g.size();
    }

    public final void f(int position) {
        Intent intent = new Intent(getContext(), ExportProjectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", yB.c(g.get(position), "sc_id"));
        startActivity(intent);
    }

    public void g() {
        a(true);
    }

    public final void g(int position) {
        aB dialog = new aB(getActivity());
        dialog.b(xB.b().a(getActivity(), 2131625988));
        dialog.a(2131165391);
        dialog.a(xB.b().a(getActivity(), 2131625906));
        dialog.b(xB.b().a(getActivity(), 2131625010), view -> {
            Intent intent = new Intent(getActivity(), PublishAccountSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("isNewSetting", true);
            intent.putExtra("index", position);
            startActivityForResult(intent, 702);
            dialog.dismiss();
        });
        dialog.show();
    }

    public void h() {
        if (g.size() > 0) {
            i.setVisibility(View.GONE);
            x.f();
        } else {
            i.setVisibility(View.VISIBLE);
            x.c();
        }

    }

    public final void h(int var1) {
        aB dialog = new aB(getActivity());
        dialog.b(xB.b().a(getActivity(), 2131625995));
        dialog.a(2131165391);
        dialog.a(xB.b().a(getActivity(), 2131625912));
        dialog.b(xB.b().a(getActivity(), 2131625010), view -> {
            Intent intent = new Intent(getActivity(), PublishAccountSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("isNewSetting", true);
            intent.putExtra("index", var1);
            startActivityForResult(intent, 702);
            dialog.dismiss();
        });
        dialog.show();
    }

    public final void i() {
        Intent var1 = new Intent(getActivity(), MyProjectSettingActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(var1, 206);
    }


    public final void j() {
        (new BackupRestoreManager(getActivity(), this)).restore();
    }

    public final void k(int position) {
        (new ProjectSettingsDialog(getActivity(), yB.c(g.get(position), "sc_id"))).show();
    }

    public final void kbckp(int position) {
        String var3 = yB.c(g.get(position), "sc_id");
        String var4 = yB.c(g.get(position), "my_ws_name");
        (new BackupRestoreManager(getActivity())).backup(var3, var4);
    }


    @Override
    public void onActivityResult(int var1, int var2, Intent data) {
        super.onActivityResult(var1, var2, data);
        if (var1 == 239) {
            if (var2 != -1) {
                return;
            }

            var1 = v.c;
        } else {
            if (var1 == 508) {
                if (var2 == -1) {
                    data = new Intent(getContext(), ExportProjectActivity.class);
                    data.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    data.putExtra("sc_id", yB.c(g.get(v.c), "sc_id"));
                    startActivity(data);
                }

                return;
            }

            if (var1 == 712) {
                if (var2 == -1 && data != null && data.hasExtra("index")) {
                    f(data.getIntExtra("index", -1));
                }

                return;
            }

            label142:
            {
                if (var1 != 708) {
                    if (var1 == 709) {
                        if (var2 != -1 || data == null || !data.hasExtra("index")) {
                            return;
                        }
                        break label142;
                    }

                    switch (var1) {
                        case 204:
                            if (super.a(var1) && !super.e.h()) {
                                xo.k();
                            }

                            return;
                        case 205:
                            return;
                        case 206:
                            if (var2 == -1) {
                                g();
                                if (data.getBooleanExtra("is_new", false)) {
                                    b(data.getStringExtra("sc_id"));
                                    return;
                                }
                            }

                            return;
                        default:
                            switch (var1) {
                                case 700:
                                    if (var2 == -1) {
                                        g();
                                    }

                                    return;
                                case 701:
                                    if (var2 != -1) {
                                        return;
                                    }

                                    g();
                                    if (data == null || !data.hasExtra("index")) {
                                        return;
                                    }
                                    break label142;
                                case 702:
                                    if (var2 == -1) {
                                        if (data != null && data.hasExtra("index")) {
                                            break label142;
                                        }
                                        break;
                                    }

                                    return;
                                default:
                                    return;
                            }

                    }
                } else if (var2 != -1) {
                    return;
                }

                j();
                return;
            }

            var1 = data.getIntExtra("index", -1);
        }
    }

    public void onClick(View var1) {
        int var2 = var1.getId();
        if (var2 != 2131230936) {
            if (var2 != 2131231054) {
                if (var2 == 2131231371 && super.a(700)) {
                    j();
                }

                return;
            }

        }
        if (!super.a(206)) {
            return;
        }

        i();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup var4 = (ViewGroup) inflater.inflate(2131427584, parent, false);
        y = new ro(getContext());
        a(var4);
        w = new DB(getContext(), "P25");
        return var4;
    }

    public static class c extends MA {
        public final GC f;
        public int c;
        public String d;
        public String e;

        public c(GC projectsFragment, Context var2, int position) {
            super(var2);
            f = projectsFragment;
            e = "";
            c = position;
            projectsFragment.b();
            projectsFragment.a(this);
        }

        public void a() {
            if (c < f.g.size()) {
                f.g.remove(c);
                f.v.e(c);
                f.v.a(c, f.v.a());
            }

            f.a();
        }

        public void a(String var1) {
            f.a();
        }

        public void b() {
            if (c < f.g.size()) {
                d = yB.c(f.g.get(c), "sc_id");
                lC.a(super.a, d);
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }

    public class ProjectsAdapter extends RecyclerView.a<ProjectsAdapter.ViewHolder> {
        public final GC d;
        public int c;

        public ProjectsAdapter(GC var1, RecyclerView recyclerView) {
            d = var1;
            c = -1;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new RecyclerView.m() {
                    public void a(RecyclerView recyclerView1, int var2, int var3) {
                        super.a(recyclerView1, var2, var3);
                        boolean var4;
                        GC var5;
                        if (var3 > 4) {
                            if (d.q) {
                                return;
                            }

                            d.r.start();
                            var5 = d;
                            var4 = true;
                        } else {
                            if (var3 >= -4 || !d.q) {
                                return;
                            }

                            d.s.start();
                            var5 = d;
                            var4 = false;
                        }

                        var5.q = var4;
                    }
                });
            }

        }

        public int a() {
            return g.size();
        }

        public void b(ViewHolder viewHolder, int position) {
            HashMap<String, Object> projectMap = d.g.get(position);
            String scId = yB.c(projectMap, "sc_id");
            float rotation;
            int visibility;
            if (yB.a(projectMap, "expand")) {
                visibility = 0;
                rotation = -180.0F;
            } else {
                visibility = 8;
                rotation = 0.0F;
            }
            viewHolder.D.setVisibility(visibility);
            viewHolder.B.setRotation(rotation);
            if (yB.a(projectMap, "confirmation")) {
                viewHolder.C.b();
            } else {
                viewHolder.C.a();
            }

            viewHolder.v.setImageResource(2131165521);
            if (yB.c(projectMap, "sc_ver_code").isEmpty()) {
                projectMap.put("sc_ver_code", "1");
                projectMap.put("sc_ver_name", "1.0");
                lC.b(scId, projectMap);
            }

            if (yB.b(projectMap, "sketchware_ver") <= 0) {
                projectMap.put("sketchware_ver", 61);
                lC.b(scId, projectMap);
            }

            if (yB.a(projectMap, "custom_icon")) {
                Uri uri;
                if (VERSION.SDK_INT >= 24) {
                    Context var9 = d.getContext();
                    String providerPath = d.getContext().getPackageName() + ".provider";
                    String iconPath = wq.e() + File.separator + scId;
                    uri = FileProvider.a(var9, providerPath, new File(iconPath, "icon.png"));
                } else {
                    String var11 = wq.e() + File.separator + scId;
                    uri = Uri.fromFile(new File(var11, "icon.png"));
                }

                viewHolder.v.setImageURI(uri);
            }

            viewHolder.x.setText(yB.c(projectMap, "my_ws_name"));
            viewHolder.w.setText(yB.c(projectMap, "my_app_name"));
            viewHolder.y.setText(yB.c(projectMap, "my_sc_pkg_name"));
            String var12 = String.format("%s(%s)", yB.c(projectMap, "sc_ver_name"), yB.c(projectMap, "sc_ver_code"));
            viewHolder.z.setText(var12);
            viewHolder.A.setVisibility(0);
            viewHolder.A.setText(yB.c(projectMap, "sc_id"));
            viewHolder.b.setTag("custom");
        }

        public ViewHolder b(ViewGroup parent, int viewType) {
            return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(2131427585, parent, false));
        }

        public class ViewHolder extends RecyclerView.v {
            public final ProjectsAdapter F;
            public TextView A;
            public ImageView B;
            public MyProjectButtonLayout C;
            public LinearLayout D;
            public LinearLayout E;
            public LinearLayout t;
            public View u;
            public CircleImageView v;
            public TextView w;
            public TextView x;
            public TextView y;
            public TextView z;

            public ViewHolder(ProjectsAdapter var1, View itemView) {
                super(itemView);
                F = var1;
                t = itemView.findViewById(2131231615);
                w = itemView.findViewById(2131231614);
                u = itemView.findViewById(2131230779);
                v = itemView.findViewById(2131231151);
                x = itemView.findViewById(2131230780);
                y = itemView.findViewById(2131231579);
                z = itemView.findViewById(2131231618);
                A = itemView.findViewById(2131232095);
                B = itemView.findViewById(2131231051);
                D = itemView.findViewById(2131231617);
                E = itemView.findViewById(2131231616);
                C = new MyProjectButtonLayout(var1.d.getContext());
                E.addView(C);
                C.setButtonOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        if (F.c <= F.d.g.size()) {
                            HashMap<String, Object> var7 = GC.this.g.get(F.c);
                            int var3;
                            ProjectsAdapter projectsAdapter;
                            if (view instanceof MyProjectButton) {
                                var3 = ((MyProjectButton) view).b;
                                if (var3 != 0) {
                                    if (var3 != 1) {
                                        if (var3 != 2) {
                                            if (var3 != 3) {
                                                if (var3 == 4) {
                                                    projectsAdapter = F;
                                                    projectsAdapter.d.k(projectsAdapter.c);
                                                }
                                            } else {
                                                var7.put("confirmation", true);
                                                C.b();
                                            }
                                        } else {
                                            projectsAdapter = F;
                                            projectsAdapter.d.f(projectsAdapter.c);
                                        }
                                    } else {
                                        projectsAdapter = F;
                                        projectsAdapter.d.kbckp(projectsAdapter.c);
                                    }
                                } else {
                                    projectsAdapter = F;
                                    projectsAdapter.d.a(projectsAdapter.c, false);
                                }

                            } else {
                                int viewId = view.getId();
                                if (viewId != 2131230923) {
                                    if (viewId == 2131230927) {
                                        var7.put("confirmation", false);
                                        var7.put("expand", false);
                                        GC var4 = F.d;
                                        (new c(var4, var4.getContext(), F.c)).execute();
                                    }
                                } else {
                                    var7.put("confirmation", false);
                                    projectsAdapter = F;
                                    projectsAdapter.c(projectsAdapter.c);
                                }

                            }
                        }
                    }
                });
                t.setOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        String var3 = yB.c(F.d.g.get(F.c), "sc_id");
                        F.d.b(var3);
                    }
                });
                t.setOnLongClickListener(view -> {
                    F.c = j();
                    if (yB.a(F.d.g.get(F.c), "expand")) {
                        D();
                    } else {
                        E();
                    }

                    return true;
                });
                u.setOnClickListener(view -> {
                    mB.a(view);
                    F.c = j();
                    F.d.a(F.c, false);
                });
                B.setOnClickListener(view -> {
                    if (!mB.a()) {
                        F.c = j();
                        if (yB.a(F.d.g.get(F.c), "expand")) {
                            D();
                        } else {
                            E();
                        }

                    }
                });
            }

            public void D() {
                F.d.g.get(F.c).put("expand", false);
                gB.a(B, 0.0F, null);
                gB.a(D, 300, new AnimatorListener() {
                    public void onAnimationCancel(Animator var1) {
                    }

                    public void onAnimationEnd(Animator var1) {
                        D.setVisibility(8);
                    }

                    public void onAnimationRepeat(Animator var1) {
                    }

                    public void onAnimationStart(Animator var1) {
                    }
                });
            }

            public void E() {
                D.setVisibility(0);
                F.d.g.get(F.c).put("expand", true);
                gB.a(B, -180.0F, null);
                gB.b(D, 300, null);
            }
        }
    }
}
