package a.a.a;

import android.app.Activity;
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
import com.sketchware.remod.R;

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
        g = getActivity().getIntent().getStringExtra("sc_id");
    }

    public void e() {
        h = Op.g().f();
        i.notifyDataSetChanged();
        g();
    }

    public void f() {
        for (ProjectResourceBean image : h) {
            image.isSelected = false;
        }
    }

    public void g() {
        boolean isEmpty = h.size() == 0;
        j.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        f.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    public void h() {
        ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
        for (ProjectResourceBean image : h) {
            if (image.isSelected) {
                arrayList.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName));
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
        i.notifyDataSetChanged();
    }

    public final void i() {
        int count = 0;
        for (ProjectResourceBean image : h) {
            if (image.isSelected) {
                count += 1;
            }
        }
        if (count > 0) {
            k.setText(xB.b().a(getContext(), R.string.common_word_import_count, count).toUpperCase());
            k.setVisibility(View.VISIBLE);
        } else {
            k.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            d();
        } else {
            g = bundle.getString("sc_id");
        }
        e();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 232 && i2 == Activity.RESULT_OK && intent != null) {
            a(intent.getParcelableArrayListExtra("results"));
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a() && view.getId() == R.id.btn_import) {
            k.setVisibility(View.GONE);
            h();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) f.getLayoutManager()).setSpanCount(c());
        f.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        f = viewGroup2.findViewById(R.id.image_list);
        f.setHasFixedSize(true);
        f.setLayoutManager(new GridLayoutManager(getActivity(), c()));
        i = new a();
        f.setAdapter(i);
        j = viewGroup2.findViewById(R.id.tv_guide);
        j.setText(xB.b().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        k = viewGroup2.findViewById(R.id.btn_import);
        k.setText(xB.b().a(getContext(), R.string.common_word_import).toUpperCase());
        k.setOnClickListener(this);
        k.setVisibility(View.GONE);
        return viewGroup2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", g);
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
                t = itemView.findViewById(R.id.chk_select);
                u = itemView.findViewById(R.id.tv_image_name);
                v = itemView.findViewById(R.id.img);
                w = itemView.findViewById(R.id.img_nine_patch);
                t.setVisibility(View.VISIBLE);
                v.setOnClickListener(v -> {
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
            ProjectResourceBean projectResourceBean = h.get(position);
            String str = wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
            holder.t.setVisibility(View.VISIBLE);
            if (projectResourceBean.isNinePatch()) {
                holder.w.setVisibility(View.VISIBLE);
            } else {
                holder.w.setVisibility(View.GONE);
            }
            Glide.with(getActivity()).load(str).asBitmap().centerCrop().error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.v) {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    super.onResourceReady(bitmap, glideAnimation);
                }
            });
            holder.u.setText(h.get(position).resName);
            holder.t.setChecked(h.get(position).isSelected);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return h.size();
        }
    }

    public void a(ArrayList<ProjectResourceBean> importedImages) {
        ArrayList<ProjectResourceBean> newImportedImages = new ArrayList<>();
        for (ProjectResourceBean image : importedImages) {
            newImportedImages.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, image.resFullName));
        }
        if (newImportedImages.size() > 0) {
            ((ManageImageActivity) getActivity()).m().a(newImportedImages);
            ((ManageImageActivity) getActivity()).f(0);
        }
    }
}
