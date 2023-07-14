package a.a.a;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageImportActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

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
        this.i.notifyDataSetChanged();
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
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
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
        this.i.notifyDataSetChanged();
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
        ((GridLayoutManager) this.f.getLayoutManager()).setSpanCount(c());
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

    class a extends RecyclerView.Adapter<fu.a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox t;
            public TextView u;
            public ImageView v;
            public ImageView w;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.t = (CheckBox) itemView.findViewById(2131230893);
                this.u = (TextView) itemView.findViewById(2131232003);
                this.v = (ImageView) itemView.findViewById(2131231102);
                this.w = (ImageView) itemView.findViewById(2131231161);
                this.t.setVisibility(0);
                this.v.setOnClickListener(v -> {
                    t.setChecked(!t.isChecked());
                    c = getLayoutPosition();
                    h.get(c).isSelected = t.isChecked();
                    i();
                    notifyItemChanged(c);
                });
            }
        }

        public a() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean projectResourceBean = (ProjectResourceBean) fu.this.h.get(position);
            String str = wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
            holder.t.setVisibility(0);
            if (projectResourceBean.isNinePatch()) {
                holder.w.setVisibility(0);
            } else {
                holder.w.setVisibility(8);
            }
            Glide.with(fu.this.getActivity()).load(str).asBitmap().centerCrop().error(2131165831).into(new BitmapImageViewTarget(holder.v) {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    super.onResourceReady(bitmap, glideAnimation);
                }
            });
            holder.u.setText(((ProjectResourceBean) fu.this.h.get(position)).resName);
            holder.t.setChecked(((ProjectResourceBean) fu.this.h.get(position)).isSelected);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(2131427529, parent, false));
        }

        @Override
        public int getItemCount() {
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
