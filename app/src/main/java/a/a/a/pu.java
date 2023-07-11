package a.a.a;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.common.ImportIconActivity;
import com.besome.sketch.editor.manage.image.AddImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class pu extends qA implements View.OnClickListener {
    public String f;
    public oB h;
    public RecyclerView i;
    public ArrayList<ProjectResourceBean> j;
    public LinearLayout l;
    public Button m;
    public Button n;
    public TextView o;
    public FloatingActionButton q;
    public String g = "";
    public a k = null;
    public boolean p = false;

    public ArrayList<ProjectResourceBean> d() {
        return this.j;
    }

    public final int e() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    public void f() {
        this.f = getActivity().getIntent().getStringExtra("sc_id");
        this.g = jC.d(this.f).l();
        ArrayList<ProjectResourceBean> arrayList = jC.d(this.f).b;
        if (arrayList != null) {
            Iterator<ProjectResourceBean> it = arrayList.iterator();
            while (it.hasNext()) {
                ProjectResourceBean next = it.next();
                if (next.flipVertical == 0) {
                    next.flipVertical = 1;
                }
                if (next.flipHorizontal == 0) {
                    next.flipHorizontal = 1;
                }
                next.savedPos = 0;
                this.j.add(next);
            }
        }
    }

    public final void g() {
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void h() {
        int size = this.j.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (this.j.get(size).isSelected) {
                    this.j.remove(size);
                }
            } else {
                this.k.c();
                return;
            }
        }
    }

    public void i() {
        String str;
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isNew || next.isEdited) {
                try {
                    String str2 = this.g + File.separator + next.resName;
                    String str3 = next.isNinePatch() ? str2 + ".9.png" : str2 + ".png";
                    if (next.savedPos == 1) {
                        str = next.resFullName;
                    } else if (next.savedPos == 0) {
                        str = a(next);
                    } else {
                        str = next.resFullName;
                    }
                    iB.a(str, str3, next.rotate, next.flipHorizontal, next.flipVertical);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < this.j.size(); i++) {
            ProjectResourceBean projectResourceBean = this.j.get(i);
            if (projectResourceBean.isNew || projectResourceBean.isEdited) {
                String str4 = projectResourceBean.resName;
                this.j.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, projectResourceBean.resName, projectResourceBean.isNinePatch() ? str4 + ".9.png" : str4 + ".png"));
            }
        }
        jC.d(this.f).b(this.j);
        jC.d(this.f).y();
        jC.a(this.f).b(jC.d(this.f));
        jC.a(this.f).k();
    }

    public void j() {
        if (this.j.size() == 0) {
            this.o.setVisibility(0);
            this.i.setVisibility(8);
            return;
        }
        this.i.setVisibility(0);
        this.o.setVisibility(8);
    }

    public final void k() {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", this.j);
        intent.putExtra("sc_id", this.f);
        intent.putExtra("dir_path", this.g);
        startActivityForResult(intent, 267);
    }

    public final void l() {
        Intent intent = new Intent(getActivity(), ImportIconActivity.class);
        intent.setFlags(536870912);
        intent.putStringArrayListExtra("imageNames", c());
        startActivityForResult(intent, 210);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            f();
        } else {
            this.f = bundle.getString("sc_id");
            this.g = bundle.getString("dir_path");
            this.j = bundle.getParcelableArrayList("images");
        }
        this.h = new oB();
        this.h.f(this.g);
        this.k.c();
        j();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 210) {
            if (i2 == -1) {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, intent.getStringExtra("iconName"), intent.getStringExtra("iconPath"));
                projectResourceBean.savedPos = 2;
                projectResourceBean.isNew = true;
                a(projectResourceBean, true);
                bB.a(getActivity(), xB.b().a(getActivity(), 2131625276), 0).show();
            }
        } else if (i == 267) {
            if (i2 == -1) {
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("images");
                Iterator it = parcelableArrayListExtra.iterator();
                while (it.hasNext()) {
                    this.j.add((ProjectResourceBean) it.next());
                }
                this.k.b(this.j.size() - parcelableArrayListExtra.size(), parcelableArrayListExtra.size());
                j();
                ((ManageImageActivity) getActivity()).l().e();
                bB.a(getActivity(), xB.b().a(getActivity(), 2131625276), 0).show();
            }
        } else if (i == 268 && i2 == -1) {
            ProjectResourceBean projectResourceBean2 = (ProjectResourceBean) intent.getParcelableExtra("image");
            kC.z();
            Iterator<ProjectResourceBean> it2 = this.j.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ProjectResourceBean next = it2.next();
                if (next.resName.equals(projectResourceBean2.resName)) {
                    next.copy(projectResourceBean2);
                    this.k.c(this.j.indexOf(next));
                    break;
                }
            }
            j();
            ((ManageImageActivity) getActivity()).l().e();
            bB.a(getActivity(), xB.b().a(getActivity(), 2131625279), 0).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        int id2 = view.getId();
        if (id2 == 2131230804) {
            if (this.p) {
                h();
                a(false);
                j();
                bB.a(getActivity(), xB.b().a(getActivity(), 2131624935), 1).show();
                this.q.f();
            }
        } else if (id2 == 2131230810) {
            if (this.p) {
                a(false);
            }
        } else if (id2 != 2131231054) {
        } else {
            a(false);
            k();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) this.i.getLayoutManager()).d(e());
        this.i.requestLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(2131492878, menu);
        if (this.p) {
            menu.findItem(2131231513).setVisible(false);
        } else {
            menu.findItem(2131231513).setVisible(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(2131427440, viewGroup, false);
        setHasOptionsMenu(true);
        this.j = new ArrayList<>();
        this.i = (RecyclerView) viewGroup2.findViewById(2131231101);
        this.i.setHasFixedSize(true);
        this.i.setLayoutManager(new GridLayoutManager(getActivity(), e()));
        this.k = new a(this.i);
        this.i.setAdapter(this.k);
        this.o = (TextView) viewGroup2.findViewById(2131231997);
        this.o.setText(xB.b().a(getContext(), 2131625267));
        this.l = (LinearLayout) viewGroup2.findViewById(2131231319);
        this.m = (Button) viewGroup2.findViewById(2131230804);
        this.n = (Button) viewGroup2.findViewById(2131230810);
        this.m.setText(xB.b().a(getContext(), 2131624986));
        this.n.setText(xB.b().a(getContext(), 2131624974));
        this.m.setOnClickListener(this);
        this.n.setOnClickListener(this);
        this.q = (FloatingActionButton) viewGroup2.findViewById(2131231054);
        this.q.setVisibility(0);
        this.q.setOnClickListener(this);
        kC.z();
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 2131231513) {
            a(!this.p);
        } else if (itemId == 2131231515) {
            l();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.f);
        bundle.putString("dir_path", this.g);
        bundle.putParcelableArrayList("images", this.j);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.a<a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public CheckBox t;
            public TextView u;
            public ImageView v;
            public ImageView w;
            public ImageView x;
            public LinearLayout y;

            public a(View view) {
                super(view);
                this.t = (CheckBox) view.findViewById(2131230893);
                this.u = (TextView) view.findViewById(2131232003);
                this.v = (ImageView) view.findViewById(2131231102);
                this.w = (ImageView) view.findViewById(2131231132);
                this.x = (ImageView) view.findViewById(2131231161);
                this.y = (LinearLayout) view.findViewById(2131230959);
                this.v.setOnClickListener(new nu(this, a.this));
                this.v.setOnLongClickListener(new ou(this, a.this));
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.a(new ku(this, pu.this));
            }
        }

        @Override
        public void b(a aVar, int i) {
            if (pu.this.p) {
                aVar.y.setVisibility(0);
            } else {
                aVar.y.setVisibility(8);
            }
            if (((ProjectResourceBean) pu.this.j.get(i)).isNinePatch()) {
                aVar.x.setVisibility(0);
            } else {
                aVar.x.setVisibility(8);
            }
            if (((ProjectResourceBean) pu.this.j.get(i)).isSelected) {
                aVar.w.setImageResource(2131165707);
            } else {
                aVar.w.setImageResource(2131165875);
            }
            aVar.t.setChecked(((ProjectResourceBean) pu.this.j.get(i)).isSelected);
            aVar.u.setText(((ProjectResourceBean) pu.this.j.get(i)).resName);
            if (((ProjectResourceBean) pu.this.j.get(i)).savedPos == 0) {
                int i2 = ((ProjectResourceBean) pu.this.j.get(i)).rotate;
                int i3 = ((ProjectResourceBean) pu.this.j.get(i)).flipVertical;
                int i4 = ((ProjectResourceBean) pu.this.j.get(i)).flipHorizontal;
                RequestManager with = Glide.with(pu.this.getActivity());
                with.load(pu.this.g + File.separator + ((ProjectResourceBean) pu.this.j.get(i)).resFullName).asBitmap().centerCrop().signature((Key) kC.n()).error(2131165831).into((BitmapRequestBuilder<String, Bitmap>) new lu(this, aVar.v, i2, i4, i3));
                return;
            }
            int i5 = ((ProjectResourceBean) pu.this.j.get(i)).rotate;
            int i6 = ((ProjectResourceBean) pu.this.j.get(i)).flipVertical;
            Glide.with(pu.this.getActivity()).load(((ProjectResourceBean) pu.this.j.get(i)).resFullName).asBitmap().centerCrop().signature((Key) kC.n()).error(2131165831).into((BitmapRequestBuilder<String, Bitmap>) new mu(this, aVar.v, i5, ((ProjectResourceBean) pu.this.j.get(i)).flipHorizontal, i6));
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427529, viewGroup, false));
        }

        @Override
        public int a() {
            return pu.this.j.size();
        }
    }

    public final void b(ProjectResourceBean projectResourceBean) {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", this.j);
        intent.putExtra("sc_id", this.f);
        intent.putExtra("dir_path", this.g);
        intent.putExtra("edit_target", projectResourceBean);
        startActivityForResult(intent, 268);
    }

    public final ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList arrayList3 = new ArrayList();
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            String str = next.resName;
            if (a(str)) {
                arrayList3.add(str);
            } else {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, str, next.resFullName);
                projectResourceBean.savedPos = 1;
                projectResourceBean.isNew = true;
                projectResourceBean.rotate = 0;
                projectResourceBean.flipVertical = 1;
                projectResourceBean.flipHorizontal = 1;
                arrayList2.add(projectResourceBean);
            }
        }
        a(arrayList2, false);
        if (arrayList3.size() > 0) {
            String a2 = xB.b().a(getActivity(), 2131624950);
            Iterator it2 = arrayList3.iterator();
            String str2 = "";
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (str2.length() > 0) {
                    str2 = str2 + ", ";
                }
                str2 = str2 + str3;
            }
            bB.a(getActivity(), a2 + "\n[" + str2 + "]", 1).show();
        } else {
            bB.a(getActivity(), xB.b().a(getActivity(), 2131625280), 1).show();
        }
        this.k.c();
        j();
    }

    public boolean a(String str) {
        Iterator<ProjectResourceBean> it = this.j.iterator();
        while (it.hasNext()) {
            if (it.next().resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return this.g + File.separator + projectResourceBean.resFullName;
    }

    public void a(boolean z) {
        this.p = z;
        getActivity().invalidateOptionsMenu();
        g();
        if (this.p) {
            this.l.setVisibility(0);
        } else {
            this.l.setVisibility(8);
        }
        this.k.c();
    }

    public void a(ProjectResourceBean projectResourceBean, boolean z) {
        this.j.add(projectResourceBean);
        if (z) {
            a aVar = this.k;
            aVar.d(aVar.a());
            j();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList, boolean z) {
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            this.j.add(it.next());
        }
        if (z) {
            a aVar = this.k;
            aVar.d(aVar.a());
            j();
        }
    }
}