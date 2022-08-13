package a.a.a;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.besome.sketch.editor.event.AddEventActivity;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.besome.sketch.editor.event.CollapsibleEventLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mod.hey.studios.moreblock.ImportMoreblockHelper;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;

public class rs extends qA implements View.OnClickListener, MoreblockImporterDialog.CallBack {
    public ArrayList<ProjectResourceBean> A;
    public MoreBlockAdapter C;
    public ArrayList<MoreBlockCollectionBean> D;
    public ProjectFileBean f;
    public CategoryAdapter g;
    public EventAdapter h;
    public RecyclerView j;
    public RecyclerView k;
    public FloatingActionButton l;
    public HashMap<Integer, ArrayList<EventBean>> m;
    public ArrayList<EventBean> n;
    public ArrayList<EventBean> o;
    public ArrayList<EventBean> p;
    public ArrayList<EventBean> q;
    public ArrayList<EventBean> r;
    public TextView s;
    public TextView t;
    public TextView u;
    public String v;
    public ArrayList<Pair<Integer, String>> w;
    public ArrayList<Pair<Integer, String>> x;
    public ArrayList<ProjectResourceBean> y;
    public ArrayList<ProjectResourceBean> z;
    public boolean i = false;
    public oB B = new oB();

    public static int a(int i) {
        if (i == 4) {
            return R.drawable.more_block_96dp;
        }
        if (i == 1) {
            return R.drawable.multiple_devices_48;
        }
        if (i == 0) {
            return R.drawable.ic_cycle_color_48dp;
        }
        if (i == 3) {
            return R.drawable.ic_drawer_color_48dp;
        }
        return i == 2 ? R.drawable.component_96 : 0;
    }

    public static String a(Context context, int i) {
        if (i == 4) {
            return xB.b().a(context, R.string.common_word_moreblock);
        }
        if (i == 1) {
            return xB.b().a(context, R.string.common_word_view);
        }
        if (i == 0) {
            return xB.b().a(context, R.string.common_word_activity);
        }
        if (i == 3) {
            return xB.b().a(context, R.string.common_word_drawer);
        }
        return i == 2 ? xB.b().a(context, R.string.common_word_component) : "";
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 223) {
            f();
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == R.id.fab) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddEventActivity.class);
            intent.putExtra("sc_id", v);
            intent.putExtra("project_file", f);
            intent.putExtra("category_index", g.index);
            startActivityForResult(intent, 223);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_logic_list, viewGroup, false);
        a(viewGroup2);
        setHasOptionsMenu(true);
        if (bundle != null) {
            v = bundle.getString("sc_id");
        } else {
            v = getActivity().getIntent().getStringExtra("sc_id");
        }
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", v);
        super.onSaveInstanceState(bundle);
    }

    public ProjectFileBean d() {
        return f;
    }

    public final void e(String str) {
        if (y == null) {
            y = new ArrayList<>();
        }
        for (String value : jC.d(v).m()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Op.g().a(str);
        if (a2 != null) {
            boolean z = false;
            Iterator<ProjectResourceBean> it2 = y.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().resName.equals(str)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                y.add(a2);
            }
        }
    }

    public void f() {
        if (f != null) {
            n.clear();
            o.clear();
            p.clear();
            q.clear();
            r.clear();
            for (Pair<String, String> moreBlock : jC.a(v).i(f.getJavaName())) {
                EventBean eventBean = new EventBean(EventBean.EVENT_TYPE_ETC, -1, moreBlock.first, "moreBlock");
                eventBean.initValue();
                n.add(eventBean);
            }
            EventBean eventBean2 = new EventBean(EventBean.EVENT_TYPE_ACTIVITY, -1, "onCreate", "initializeLogic");
            eventBean2.initValue();
            q.add(eventBean2);
            for (EventBean eventBean : jC.a(v).g(f.getJavaName())) {
                eventBean.initValue();
                int i = eventBean.eventType;
                if (i == EventBean.EVENT_TYPE_VIEW) {
                    o.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_COMPONENT) {
                    p.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_ACTIVITY) {
                    q.add(eventBean);
                } else if (i == EventBean.EVENT_TYPE_DRAWER_VIEW) {
                    r.add(eventBean);
                }
            }
            if (g.index == -1) {
                h.a(m.get(0));
                g.index = 0;
                if (g != null) {
                    g.c();
                }
            }
            if (g.index == 4) {
                t.setVisibility(View.VISIBLE);
                u.setVisibility(View.VISIBLE);
            } else {
                t.setVisibility(View.GONE);
                u.setVisibility(View.GONE);
            }
            if (h != null) {
                if (g != null) {
                    g.c();
                }
                h.a(m.get(g.index));
                h.c();
            }
        }
    }

    public final void d(String str) {
        if (A == null) {
            A = new ArrayList<>();
        }
        for (String value : jC.d(v).k()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Np.g().a(str);
        if (a2 != null) {
            boolean z = false;
            Iterator<ProjectResourceBean> it2 = A.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().resName.equals(str)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                A.add(a2);
            }
        }
    }

    public final void b(EventBean eventBean) {
        if (jC.a(v).f(f.getJavaName(), eventBean.targetId)) {
            bB.b(getContext(), xB.b().a(getContext(), R.string.logic_editor_message_currently_used_block), 0).show();
        } else {
            jC.a(v).n(f.getJavaName(), eventBean.targetId);
            bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
            m.get(g.index).remove(h.lastSelectedItem);
            h.e(h.lastSelectedItem);
            h.a(h.lastSelectedItem, h.a());
        }
    }

    public void c() {
        if (f != null) {
            for (Map.Entry<Integer, ArrayList<EventBean>> entry : m.entrySet()) {
                for (EventBean bean : entry.getValue()) {
                    bean.initValue();
                }
            }
            h.c();
        }
    }

    public final void a(ViewGroup viewGroup) {
        s = viewGroup.findViewById(R.id.tv_no_events);
        k = viewGroup.findViewById(R.id.event_list);
        j = viewGroup.findViewById(R.id.category_list);
        l = viewGroup.findViewById(R.id.fab);
        s.setVisibility(View.GONE);
        s.setText(xB.b().a(getContext(), R.string.event_message_no_events));
        k.setHasFixedSize(true);
        k.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        j.setHasFixedSize(true);
        j.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        ((Bi) j.getItemAnimator()).a(false);
        g = new CategoryAdapter();
        j.setAdapter(g);
        h = new EventAdapter();
        k.setAdapter(h);
        l.setOnClickListener(this);
        // RecyclerView#addOnScrollListener(RecyclerView.OnScrollListener)
        k.a(new RecyclerView.m() {
            @Override
            // RecyclerView.OnScrollListener#onScrolled(RecyclerView, int, int)
            public void a(RecyclerView recyclerView, int dx, int dy) {
                super.a(recyclerView, dx, dy);
                if (dy > 2) {
                    if (l.isEnabled()) {
                        // FloatingActionButton#hide()
                        l.c();
                    }
                } else if (dy < -2) {
                    if (l.isEnabled()) {
                        // FloatingActionButton#show()
                        l.f();
                    }
                }
            }
        });
        m = new HashMap<>();
        n = new ArrayList<>();
        o = new ArrayList<>();
        p = new ArrayList<>();
        q = new ArrayList<>();
        r = new ArrayList<>();
        m.put(0, q);
        m.put(1, o);
        m.put(2, p);
        m.put(3, r);
        m.put(4, n);
        t = viewGroup.findViewById(R.id.tv_import);
        t.setText(xB.b().a(getContext(), R.string.logic_button_import_more_block));
        u = viewGroup.findViewById(R.id.tv_shared);
        u.setText(xB.b().a(getContext(), R.string.logic_button_explore_shared_more_block));
        t.setOnClickListener(v -> g());
        u.setOnClickListener(v -> {
            /* This is defined in a.a.a.ks, but not compilable, as Shared More Blocks classes were removed.
            Intent intent = new Intent(getActivity(), com.besome.sketch.shared.moreblocks.SharedMoreBlocksListActivity.class);
            intent.setFlags(536870912);
            startActivityForResult(intent, 464);
            */
        });
    }

    public final void e(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    public final void d(MoreBlockCollectionBean moreBlockCollectionBean) {
        w = new ArrayList<>();
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();
        A = new ArrayList<>();
        for (BlockBean next : moreBlockCollectionBean.blocks) {
            if (next.opCode.equals("getVar")) {
                if (next.type.equals("b")) {
                    b(0, next.spec);
                } else if (next.type.equals("d")) {
                    b(1, next.spec);
                } else if (next.type.equals("s")) {
                    b(2, next.spec);
                } else if (next.type.equals("a")) {
                    b(3, next.spec);
                } else if (next.type.equals("l")) {
                    if (next.typeName.equals("List Number")) {
                        a(1, next.spec);
                    } else if (next.typeName.equals("List String")) {
                        a(2, next.spec);
                    } else if (next.typeName.equals("List Map")) {
                        a(3, next.spec);
                    }
                }
            }
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str = next.parameters.get(i);
                    if (str.length() > 0 && str.charAt(0) != '@') {
                        if (gx.b("boolean.SelectBoolean")) {
                            b(0, str);
                        } else if (gx.b("double.SelectDouble")) {
                            b(1, str);
                        } else if (gx.b("String.SelectString")) {
                            b(2, str);
                        } else if (gx.b("Map")) {
                            b(3, str);
                        } else if (gx.b("ListInt")) {
                            a(1, str);
                        } else if (gx.b("ListString")) {
                            a(2, str);
                        } else if (gx.b("ListMap")) {
                            a(3, str);
                        } else if (!gx.b("resource_bg") && !gx.b("resource")) {
                            if (gx.b("sound")) {
                                f(str);
                            } else if (gx.b("font")) {
                                d(str);
                            }
                        } else {
                            e(str);
                        }
                    }
                }
            }
        }
        if (w.size() <= 0 && x.size() <= 0 && y.size() <= 0 && z.size() <= 0 && A.size() <= 0) {
            a(moreBlockCollectionBean);
        } else {
            f(moreBlockCollectionBean);
        }
    }

    public final void e() {
        D = Pp.h().f();
        C.c();
    }

    public final void b(int i) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(getContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getContext(), R.string.logic_more_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(getContext(), a2.findViewById(R.id.ti_input), Pp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), R.string.common_word_save), v -> {
            if (nb.b()) {
                a(editText.getText().toString(), n.get(i));
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void c(EventBean eventBean) {
        eC a2 = jC.a(v);
        String javaName = f.getJavaName();
        a2.a(javaName, eventBean.targetId + "_" + eventBean.eventName, new ArrayList<>());
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_reset), 0).show();
    }

    public final void c(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        boolean z = false;
        if (str.contains(" ")) {
            str = str.substring(0, str.indexOf(32));
        }
        Iterator<Pair<String, String>> it = jC.a(v).i(f.getJavaName()).iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().first.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!z) {
            d(moreBlockCollectionBean);
        } else {
            b(moreBlockCollectionBean);
        }
    }

    @Override
    public void onSelected(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    public final void c(String str) {
        if (Qp.g().b(str)) {
            ProjectResourceBean a2 = Qp.g().a(str);
            try {
                B.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + v + File.separator + a2.resFullName);
                jC.d(v).c.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void g() {
        D = Pp.h().f();
        new MoreblockImporterDialog(getActivity(), D, this).show();
    }

    public final void b(int i, String str) {
        if (w == null) {
            w = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(v).k(f.getJavaName())) {
            if (next.first == i && next.second.equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = w.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (next2.first == i && next2.second.equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            w.add(new Pair<>(i, str));
        }
    }

    public final void b(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_title_change_block_name));
        aBVar.a(R.drawable.more_block_96dp);
        View a2 = wB.a(getContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getContext(), R.string.logic_more_block_desc_change_block_name));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        ZB zb = new ZB(getContext(), a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(v).a(f));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), R.string.common_word_save), v -> {
            if (zb.b()) {
                moreBlockCollectionBean.spec = editText.getText().toString() + (moreBlockCollectionBean.spec.contains(" ") ?
                        moreBlockCollectionBean.spec.substring(moreBlockCollectionBean.spec.indexOf(" ")) : "");
                d(moreBlockCollectionBean);
                mB.a(getContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), v -> {
            mB.a(getContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void f(String str) {
        if (z == null) {
            z = new ArrayList<>();
        }
        for (String value : jC.d(v).p()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Qp.g().a(str);
        if (a2 != null) {
            boolean z = false;
            Iterator<ProjectResourceBean> it2 = this.z.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().resName.equals(str)) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!z) {
                this.z.add(a2);
            }
        }
    }

    public void a(ProjectFileBean projectFileBean) {
        f = projectFileBean;
    }

    public final void f(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(getActivity());
        aBVar.b(xB.b().a(getContext(), R.string.logic_more_block_title_add_variable_resource));
        aBVar.a(R.drawable.break_warning_96_red);
        aBVar.a(xB.b().a(getContext(), R.string.logic_more_block_desc_add_variable_resource));
        aBVar.b(xB.b().a(getContext(), R.string.common_word_continue), v -> {
            for (Pair<Integer, String> pair : w) {
                eC eC = jC.a(this.v);
                eC.c(f.getJavaName(), pair.first, pair.second);
            }
            for (Pair<Integer, String> pair : x) {
                eC eC = jC.a(this.v);
                eC.b(f.getJavaName(), pair.first, pair.second);
            }
            for (ProjectResourceBean bean : y) {
                b(bean.resName);
            }
            for (ProjectResourceBean bean : z) {
                c(bean.resName);
            }
            for (ProjectResourceBean bean : A) {
                a(bean.resName);
            }
            a(moreBlockCollectionBean);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void a(EventBean eventBean) {
        jC.a(v).d(f.getJavaName(), eventBean.targetId, eventBean.eventName);
        eC a2 = jC.a(v);
        String javaName = f.getJavaName();
        a2.k(javaName, eventBean.targetId + "_" + eventBean.eventName);
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_delete), 0).show();
        m.get(g.index).remove(h.lastSelectedItem);
        h.e(h.lastSelectedItem);
        h.a(h.lastSelectedItem, h.a());
    }

    public final void b(String str) {
        if (Op.g().b(str)) {
            ProjectResourceBean a2 = Op.g().a(str);
            try {
                B.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + v + File.separator + a2.resFullName);
                jC.d(v).b.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(ArrayList<EventBean> arrayList) {
        for (EventBean bean : arrayList) {
            bean.initValue();
        }
    }

    public final void a(String str, String str2, String str3) {
        Intent intent = new Intent(getActivity(), LogicEditorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", v);
        intent.putExtra("id", str);
        intent.putExtra("event", str2);
        intent.putExtra("project_file", f);
        intent.putExtra("event_text", str3);
        startActivity(intent);
    }

    public final void a(String str, EventBean eventBean) {
        String b2 = jC.a(v).b(f.getJavaName(), eventBean.targetId);
        eC a2 = jC.a(v);
        String javaName = f.getJavaName();
        ArrayList<BlockBean> a3 = a2.a(javaName, eventBean.targetId + "_" + eventBean.eventName);
        Iterator<BlockBean> it = a3.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            BlockBean next = it.next();
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                boolean z3 = z2;
                boolean z4 = z;
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str2 = next.parameters.get(i);
                    if (!gx.b("resource") && !gx.b("resource_bg")) {
                        if (gx.b("sound")) {
                            if (jC.d(v).m(str2) && !Qp.g().b(str2)) {
                                try {
                                    Qp.g().a(v, jC.d(v).j(str2));
                                } catch (Exception unused) {
                                    z3 = true;
                                }
                            }
                        } else {
                            if (gx.b("font") && jC.d(v).k(str2) && !Np.g().b(str2)) {
                                Np.g().a(v, jC.d(v).e(str2));
                            }
                        }
                    } else {
                        if (jC.d(v).l(str2) && !Op.g().b(str2)) {
                            Op.g().a(v, jC.d(v).g(str2));
                        }
                    }
                    z4 = true;
                }
                z = z4;
                z2 = z3;
            }
        }
        if (z) {
            if (z2) {
                bB.b(getContext(), xB.b().a(getContext(), R.string.logic_more_block_message_missed_resource_exist), 0).show();
            } else {
                bB.a(getContext(), xB.b().a(getContext(), R.string.logic_more_block_message_resource_added), 0).show();
            }
        }
        try {
            Pp.h().a(str, b2, a3, true);
        } catch (Exception unused2) {
            bB.b(getContext(), xB.b().a(getContext(), R.string.common_error_failed_to_save), 0).show();
        }
    }

    public final void a(int i, String str) {
        if (x == null) {
            x = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(v).j(f.getJavaName())) {
            if (next.first == i && next.second.equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = x.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (next2.first == i && next2.second.equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            x.add(new Pair<>(i, str));
        }
    }

    public final void a(String str) {
        if (Np.g().b(str)) {
            ProjectResourceBean a2 = Np.g().a(str);
            try {
                B.a(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + a2.resFullName, wq.d() + File.separator + v + File.separator + a2.resFullName);
                jC.d(v).d.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void a(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        String substring = str.contains(" ") ? str.substring(0, str.indexOf(32)) : str;
        jC.a(v).a(f.getJavaName(), substring, str);
        eC a2 = jC.a(v);
        String javaName = f.getJavaName();
        a2.a(javaName, substring + "_moreBlock", moreBlockCollectionBean.blocks);
        bB.a(getContext(), xB.b().a(getContext(), R.string.common_message_complete_save), 0).show();
        f();
    }

    private class CategoryAdapter extends RecyclerView.a<CategoryAdapter.ViewHolder> {
        private int index = -1;

        @Override
        public void b(ViewHolder aVar, int i) {
            aVar.name.setText(rs.a(getContext(), i));
            aVar.icon.setImageResource(rs.a(i));
            if (index == i) {
                ef a2 = Ze.a(aVar.icon);
                a2.c(1.0f);
                a2.d(1.0f);
                a2.a(300L);
                a2.a(new AccelerateInterpolator());
                a2.c();
                ef a3 = Ze.a(aVar.icon);
                a3.c(1.0f);
                a3.d(1.0f);
                a3.a(300L);
                a3.a(new AccelerateInterpolator());
                a3.c();
                aVar.pointerLeft.setVisibility(View.VISIBLE);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1.0f);
                aVar.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            } else {
                ef a4 = Ze.a(aVar.icon);
                a4.c(0.8f);
                a4.d(0.8f);
                a4.a(300L);
                a4.a(new DecelerateInterpolator());
                a4.c();
                ef a5 = Ze.a(aVar.icon);
                a5.c(0.8f);
                a5.d(0.8f);
                a5.a(300L);
                a5.a(new DecelerateInterpolator());
                a5.c();
                aVar.pointerLeft.setVisibility(View.GONE);
                ColorMatrix colorMatrix2 = new ColorMatrix();
                colorMatrix2.setSaturation(0.0f);
                aVar.icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
            }
        }

        @Override
        public ViewHolder b(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.common_category_triangle_item, viewGroup, false));
        }

        @Override
        public int a() {
            return rs.this.m.size();
        }

        private class ViewHolder extends RecyclerView.v implements View.OnClickListener {
            public final ImageView icon;
            public final TextView name;
            public final View pointerLeft;

            public ViewHolder(View view) {
                super(view);
                icon = view.findViewById(R.id.img_icon);
                name = view.findViewById(R.id.tv_name);
                pointerLeft = view.findViewById(R.id.pointer_left);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                CategoryAdapter.this.c(index);
                index = j();
                CategoryAdapter.this.c(index);
                rs.this.a(rs.this.m.get(index));
                if (index == 4) {
                    rs.this.t.setVisibility(View.VISIBLE);
                    rs.this.u.setVisibility(View.VISIBLE);
                } else {
                    rs.this.t.setVisibility(View.GONE);
                    rs.this.u.setVisibility(View.GONE);
                }
                rs.this.h.a(rs.this.m.get(index));
                rs.this.h.c();
            }
        }
    }

    private class EventAdapter extends RecyclerView.a<EventAdapter.ViewHolder> {
        private int lastSelectedItem = -1;
        private ArrayList<EventBean> events = new ArrayList<>();

        @Override
        public int a() {
            return events.size();
        }

        @Override
        public void b(ViewHolder aVar, int i) {
            EventBean eventBean = events.get(i);
            aVar.targetType.setVisibility(View.VISIBLE);
            aVar.previewContainer.setVisibility(View.VISIBLE);
            aVar.preview.setVisibility(View.VISIBLE);
            aVar.preview.setImageResource(oq.a(eventBean.eventName));
            aVar.optionsLayout.e();
            if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                aVar.optionsLayout.f();
            } else {
                aVar.optionsLayout.c();
            }
            int i2 = eventBean.eventType;
            if (i2 == 3) {
                if (eventBean.eventName == "initializeLogic") {
                    aVar.optionsLayout.b();
                }
                aVar.targetId.setText(eventBean.targetId);
                aVar.type.setBackgroundResource(oq.a(eventBean.eventName));
                aVar.name.setText(eventBean.eventName);
                aVar.description.setText(oq.a(eventBean.eventName, getContext()));
                aVar.icon.setImageResource(R.drawable.widget_source);
                aVar.preview.setVisibility(View.GONE);
                aVar.targetType.setVisibility(View.GONE);
            } else {
                aVar.icon.setImageResource(EventBean.getEventIconResource(i2, eventBean.targetType));
                int i3 = eventBean.eventType;
                if (i3 == EventBean.EVENT_TYPE_VIEW) {
                    aVar.targetType.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (i3 == EventBean.EVENT_TYPE_DRAWER_VIEW) {
                    aVar.targetType.setText(ViewBean.getViewTypeName(eventBean.targetType));
                } else if (i3 == EventBean.EVENT_TYPE_COMPONENT) {
                    aVar.targetType.setText(ComponentBean.getComponentName(getContext(), eventBean.targetType));
                } else if (i3 == EventBean.EVENT_TYPE_ETC) {
                    aVar.icon.setImageResource(R.drawable.widget_source);
                    aVar.targetType.setVisibility(View.GONE);
                    aVar.preview.setVisibility(View.GONE);
                }
                if (eventBean.targetId.equals("_fab")) {
                    aVar.targetId.setText("fab");
                } else {
                    aVar.targetId.setText(ReturnMoreblockManager.getMbName(eventBean.targetId));
                }
                aVar.type.setText(EventBean.getEventTypeName(eventBean.eventType));
                aVar.type.setBackgroundResource(EventBean.getEventTypeBgRes(eventBean.eventType));
                aVar.name.setText(eventBean.eventName);
                aVar.description.setText(oq.a(eventBean.eventName, getContext()));
                if (eventBean.eventType == EventBean.EVENT_TYPE_ETC) {
                    aVar.description.setText(ReturnMoreblockManager.getMbTypeList(eventBean.targetId));
                }
            }
            if (eventBean.isCollapsed) {
                aVar.optionContainer.setVisibility(View.GONE);
                aVar.menu.setRotation(0.0f);
                if (eventBean.isConfirmation) {
                    aVar.optionsLayout.d();
                } else {
                    aVar.optionsLayout.a();
                }
            } else {
                aVar.optionContainer.setVisibility(View.VISIBLE);
                aVar.menu.setRotation(-180.0f);
                if (eventBean.isConfirmation) {
                    aVar.optionsLayout.d();
                } else {
                    aVar.optionsLayout.a();
                }
            }
            aVar.optionContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        public void a(ArrayList<EventBean> arrayList) {
            if (arrayList.size() == 0) {
                rs.this.s.setVisibility(View.VISIBLE);
            } else {
                rs.this.s.setVisibility(View.GONE);
            }
            events = arrayList;
        }

        @Override
        public ViewHolder b(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fr_logic_list_item, viewGroup, false));
        }

        private class ViewHolder extends RecyclerView.v {
            public final ImageView menu;
            public final ImageView preview;
            public final LinearLayout previewContainer;
            public final LinearLayout optionContainer;
            public final LinearLayout options;
            public final CollapsibleEventLayout optionsLayout;
            public final ImageView icon;
            public final TextView targetType;
            public final TextView targetId;
            public final TextView type;
            public final TextView name;
            public final TextView description;

            public ViewHolder(View view) {
                super(view);
                icon = view.findViewById(R.id.img_icon);
                targetType = view.findViewById(R.id.tv_target_type);
                targetId = view.findViewById(R.id.tv_target_id);
                type = view.findViewById(R.id.tv_event_type);
                name = view.findViewById(R.id.tv_event_name);
                description = view.findViewById(R.id.tv_event_text);
                menu = view.findViewById(R.id.img_menu);
                preview = view.findViewById(R.id.img_preview);
                previewContainer = view.findViewById(R.id.ll_preview);
                optionContainer = view.findViewById(R.id.event_option_layout);
                options = view.findViewById(R.id.event_option);
                optionsLayout = new CollapsibleEventLayout(getContext());
                optionsLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                options.addView(optionsLayout);
                optionsLayout.setButtonOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedItem = j();
                        EventBean eventBean = (rs.this.m.get(rs.this.g.index)).get(lastSelectedItem);
                        if (view instanceof CollapsibleButton) {
                            int i = ((CollapsibleButton) view).b;
                            if (i == 2) {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = false;
                                eventBean.isCollapsed = false;
                                EventAdapter.this.c(lastSelectedItem);
                                rs.this.b(lastSelectedItem);
                            } else {
                                eventBean.buttonPressed = i;
                                eventBean.isConfirmation = true;
                                EventAdapter.this.c(lastSelectedItem);
                            }
                        } else {
                            int id = view.getId();
                            if (id == R.id.confirm_no) {
                                eventBean.isConfirmation = false;
                                EventAdapter.this.c(lastSelectedItem);
                            } else if (id == R.id.confirm_yes) {
                                int i2 = eventBean.buttonPressed;
                                if (i2 == 0) {
                                    eventBean.isConfirmation = false;
                                    eventBean.isCollapsed = true;
                                    rs.this.c(eventBean);
                                    EventAdapter.this.c(lastSelectedItem);
                                } else if (i2 == 1) {
                                    eventBean.isConfirmation = false;
                                    if (rs.this.g.index != 4) {
                                        rs.this.a(eventBean);
                                    } else {
                                        rs.this.b(eventBean);
                                    }
                                }
                                rs.this.l.f();
                            }
                        }
                    }
                });
                menu.setOnClickListener(v -> {
                    lastSelectedItem = j();
                    EventBean eventBean = rs.this.m.get(rs.this.g.index).get(lastSelectedItem);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        E();
                    } else {
                        eventBean.isCollapsed = true;
                        D();
                    }
                });
                view.setOnLongClickListener(v -> {
                    lastSelectedItem = j();
                    EventBean eventBean = rs.this.m.get(rs.this.g.index).get(lastSelectedItem);
                    if (eventBean.isCollapsed) {
                        eventBean.isCollapsed = false;
                        E();
                    } else {
                        eventBean.isCollapsed = true;
                        D();
                    }
                    return true;
                });
                view.setOnClickListener(v -> {
                    if (!mB.a()) {
                        lastSelectedItem = j();
                        EventBean eventBean = rs.this.m.get(rs.this.g.index).get(lastSelectedItem);
                        rs.this.a(eventBean.targetId, eventBean.eventName, description.getText().toString());
                    }
                });
            }

            public void D() {
                gB.a(menu, 0.0f, null);
                gB.a(optionContainer, 200, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        optionContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }

            public void E() {
                optionContainer.setVisibility(View.VISIBLE);
                gB.a(menu, -180.0f, null);
                gB.b(optionContainer, 200, null);
            }
        }
    }

    private class MoreBlockAdapter extends RecyclerView.a<MoreBlockAdapter.ViewHolder> {
        private int lastSelectedItem = -1;

        @Override
        public void b(ViewHolder holder, int position) {
            MoreBlockCollectionBean bean = D.get(position);
            if (bean.isSelected) {
                holder.checkmark.setVisibility(View.VISIBLE);
            } else {
                holder.checkmark.setVisibility(View.GONE);
            }
            holder.name.setText(bean.name);
            holder.blockArea.removeAllViews();
            holder.blockArea.addView(ImportMoreblockHelper.optimizedBlockView(getContext(), bean.spec));
        }

        @Override
        public ViewHolder b(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.manage_collection_popup_import_more_block_list_item, viewGroup, false));
        }

        @Override
        public int a() {
            return D.size();
        }

        private class ViewHolder extends RecyclerView.v {
            public final ViewGroup item;
            public final ImageView checkmark;
            public final TextView name;
            public final ViewGroup blockArea;

            public ViewHolder(View view) {
                super(view);
                item = view.findViewById(R.id.layout_item);
                checkmark = view.findViewById(R.id.img_selected);
                name = view.findViewById(R.id.tv_block_name);
                blockArea = view.findViewById(R.id.block_area);
                checkmark.setVisibility(View.GONE);
                item.setOnClickListener(v -> {
                    lastSelectedItem = j();
                    ViewHolder.this.c(lastSelectedItem);
                });
                blockArea.setOnClickListener(v -> {
                    lastSelectedItem = j();
                    ViewHolder.this.c(lastSelectedItem);
                });
            }

            public final void c(int i) {
                if (D.size() > 0) {
                    for (MoreBlockCollectionBean bean : D) {
                        bean.isSelected = false;
                    }
                    D.get(i).isSelected = true;
                    C.c();
                }
            }
        }
    }
}
