package a.a.a;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageImportActivity;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class fu extends qA implements View.OnClickListener {
    public RecyclerView f;
    public String g;
    public ArrayList<ProjectResourceBean> h;
    public a i = null;
    public TextView j;
    public Button k;

    public final int c() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    public void d() {
        this.g = getActivity().getIntent().getStringExtra("sc_id");
    }

    public void e() {
        this.h = Op.g().f();
        this.i.c();
        g();
    }

    public void f() {
        Iterator<ProjectResourceBean> it = this.h.iterator();
        while (it.hasNext()) {
            it.next().isSelected = false;
        }
    }

    public void g() {
        if (this.h.size() == 0) {
            this.j.setVisibility(0);
            this.f.setVisibility(8);
            return;
        }
        this.f.setVisibility(0);
        this.j.setVisibility(8);
    }

    public void h() {
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        Iterator<ProjectResourceBean> it = this.h.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            if (next.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, wq.a() + File.separator + "image" + File.separator + "data" + File.separator + next.resFullName));
            }
        }
        if (arrayList.size() > 0) {
            ArrayList<ProjectResourceBean> d = ((ManageImageActivity) getActivity()).m().d();
            Intent intent = new Intent(getActivity(), ManageImageImportActivity.class);
            intent.putParcelableArrayListExtra("project_images", d);
            intent.putParcelableArrayListExtra("selected_collections", arrayList);
            startActivityForResult(intent, 232);
        }
        f();
        this.i.c();
    }

    public final void i() {
        Iterator<ProjectResourceBean> it = this.h.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSelected) {
                i++;
            }
        }
        if (i > 0) {
            this.k.setText(xB.b().a(getContext(), 2131625003, Integer.valueOf(i)).toUpperCase());
            this.k.setVisibility(0);
            return;
        }
        this.k.setVisibility(8);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            d();
        } else {
            this.g = bundle.getString("sc_id");
        }
        e();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 232 && i2 == -1 && intent != null) {
            a(intent.getParcelableArrayListExtra("results"));
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == 2131230832) {
            this.k.setVisibility(8);
            h();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) this.f.getLayoutManager()).d(c());
        this.f.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(2131427440, viewGroup, false);
        this.f = (RecyclerView) viewGroup2.findViewById(2131231101);
        this.f.setHasFixedSize(true);
        this.f.setLayoutManager(new GridLayoutManager(getActivity(), c()));
        this.i = new a();
        this.f.setAdapter(this.i);
        this.j = (TextView) viewGroup2.findViewById(2131231997);
        this.j.setText(xB.b().a(getContext(), 2131625267));
        this.k = (Button) viewGroup2.findViewById(2131230832);
        this.k.setText(xB.b().a(getContext(), 2131625002).toUpperCase());
        this.k.setOnClickListener(this);
        this.k.setVisibility(8);
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.g);
        super.onSaveInstanceState(bundle);
    }

    class a extends RecyclerView.a<a> {
        public int c = -1;

        class a extends RecyclerView.v {
            public CheckBox t;
            public TextView u;
            public ImageView v;
            public ImageView w;

            public a(View view) {
                super(view);
                this.t = (CheckBox) view.findViewById(2131230893);
                this.u = (TextView) view.findViewById(2131232003);
                this.v = (ImageView) view.findViewById(2131231102);
                this.w = (ImageView) view.findViewById(2131231161);
                this.t.setVisibility(0);
                this.v.setOnClickListener(new eu(this, a.this));
            }
        }

        public a() {
        }

        @Override
        public void b(a aVar, int i) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) fu.this.h.get(i);
            String str = wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
            aVar.t.setVisibility(0);
            if (projectResourceBean.isNinePatch()) {
                aVar.w.setVisibility(0);
            } else {
                aVar.w.setVisibility(8);
            }
            Glide.with(fu.this.getActivity()).load(str).asBitmap().centerCrop().error(2131165831).into((BitmapRequestBuilder<String, Bitmap>) new du(this, aVar.v));
            aVar.u.setText(((ProjectResourceBean) fu.this.h.get(i)).resName);
            aVar.t.setChecked(((ProjectResourceBean) fu.this.h.get(i)).isSelected);
        }

        @Override
        public a b(ViewGroup viewGroup, int i) {
            return new a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427529, viewGroup, false));
        }

        @Override
        public int a() {
            return fu.this.h.size();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        Iterator<ProjectResourceBean> it = arrayList.iterator();
        while (it.hasNext()) {
            ProjectResourceBean next = it.next();
            arrayList2.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, next.resName, next.resFullName));
        }
        if (arrayList2.size() > 0) {
            ((ManageImageActivity) getActivity()).m().a(arrayList2);
            ((ManageImageActivity) getActivity()).f(0);
        }
    }
}
