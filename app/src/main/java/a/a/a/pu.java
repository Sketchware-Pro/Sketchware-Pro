package a.a.a;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.common.ImportIconActivity;
import com.besome.sketch.editor.manage.image.AddImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

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
                this.k.notifyDataSetChanged();
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
            this.o.setVisibility(View.VISIBLE);
            this.i.setVisibility(View.GONE);
            return;
        }
        this.i.setVisibility(View.VISIBLE);
        this.o.setVisibility(View.GONE);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        this.k.notifyDataSetChanged();
        j();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 210) {
            if (i2 == Activity.RESULT_OK) {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, intent.getStringExtra("iconName"), intent.getStringExtra("iconPath"));
                projectResourceBean.savedPos = 2;
                projectResourceBean.isNew = true;
                a(projectResourceBean, true);
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
        } else if (i == 267) {
            if (i2 == Activity.RESULT_OK) {
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("images");
                Iterator it = parcelableArrayListExtra.iterator();
                while (it.hasNext()) {
                    this.j.add((ProjectResourceBean) it.next());
                }
                this.k.notifyItemRangeInserted(this.j.size() - parcelableArrayListExtra.size(), parcelableArrayListExtra.size());
                j();
                ((ManageImageActivity) getActivity()).l().e();
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
        } else if (i == 268 && i2 == Activity.RESULT_OK) {
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
                    this.k.notifyItemChanged(this.j.indexOf(next));
                    break;
                }
            }
            j();
            ((ManageImageActivity) getActivity()).l().e();
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        int id2 = view.getId();
        if (id2 == R.id.btn_accept) {
            if (this.p) {
                h();
                a(false);
                j();
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_complete_delete), bB.TOAST_WARNING).show();
                this.q.show();
            }
        } else if (id2 == R.id.btn_cancel) {
            if (this.p) {
                a(false);
            }
        } else if (id2 != R.id.fab) {
        } else {
            a(false);
            k();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) this.i.getLayoutManager()).setSpanCount(e());
        this.i.requestLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_image_menu, menu);
        if (this.p) {
            menu.findItem(R.id.menu_image_delete).setVisible(false);
        } else {
            menu.findItem(R.id.menu_image_delete).setVisible(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        setHasOptionsMenu(true);
        this.j = new ArrayList<>();
        this.i = (RecyclerView) viewGroup2.findViewById(R.id.image_list);
        this.i.setHasFixedSize(true);
        this.i.setLayoutManager(new GridLayoutManager(getActivity(), e()));
        this.k = new a(this.i);
        this.i.setAdapter(this.k);
        this.o = (TextView) viewGroup2.findViewById(R.id.tv_guide);
        this.o.setText(xB.b().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        this.l = (LinearLayout) viewGroup2.findViewById(R.id.layout_btn_group);
        this.m = (Button) viewGroup2.findViewById(R.id.btn_accept);
        this.n = (Button) viewGroup2.findViewById(R.id.btn_cancel);
        this.m.setText(xB.b().a(getContext(), R.string.common_word_delete));
        this.n.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        this.m.setOnClickListener(this);
        this.n.setOnClickListener(this);
        this.q = (FloatingActionButton) viewGroup2.findViewById(R.id.fab);
        this.q.setVisibility(View.VISIBLE);
        this.q.setOnClickListener(this);
        kC.z();
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_image_delete) {
            a(!this.p);
        } else if (itemId == R.id.menu_image_import) {
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

    class a extends RecyclerView.Adapter<pu.a.ViewHolder> {
        public int c = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox t;
            public TextView u;
            public ImageView v;
            public ImageView w;
            public ImageView x;
            public LinearLayout y;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.t = (CheckBox) itemView.findViewById(R.id.chk_select);
                this.u = (TextView) itemView.findViewById(R.id.tv_image_name);
                this.v = (ImageView) itemView.findViewById(R.id.img);
                this.w = (ImageView) itemView.findViewById(R.id.img_delete);
                this.x = (ImageView) itemView.findViewById(R.id.img_nine_patch);
                this.y = (LinearLayout) itemView.findViewById(R.id.delete_img_container);
                this.v.setOnClickListener(v -> {
                    c = getLayoutPosition();
                    if (!p) {
                        b(j.get(getLayoutPosition()));
                    } else {
                        t.setChecked(!t.isChecked());
                        j.get(c).isSelected = t.isChecked();
                        notifyItemChanged(c);
                    }
                });
                this.v.setOnLongClickListener(v -> {
                    a(true);
                    c = getLayoutPosition();
                    t.setChecked(!t.isChecked());
                    j.get(c).isSelected = t.isChecked();
                    return true;
                });
            }
        }

        public a(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (q.isEnabled()) {
                                q.hide();
                            }
                        } else if (dy < -2) {
                            if (q.isEnabled()) {
                                q.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (pu.this.p) {
                holder.y.setVisibility(View.VISIBLE);
            } else {
                holder.y.setVisibility(View.GONE);
            }
            if (((ProjectResourceBean) pu.this.j.get(position)).isNinePatch()) {
                holder.x.setVisibility(View.VISIBLE);
            } else {
                holder.x.setVisibility(View.GONE);
            }
            if (((ProjectResourceBean) pu.this.j.get(position)).isSelected) {
                holder.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            holder.t.setChecked(((ProjectResourceBean) pu.this.j.get(position)).isSelected);
            holder.u.setText(((ProjectResourceBean) pu.this.j.get(position)).resName);
            if (((ProjectResourceBean) pu.this.j.get(position)).savedPos == 0) {
                int i2 = ((ProjectResourceBean) pu.this.j.get(position)).rotate;
                int i3 = ((ProjectResourceBean) pu.this.j.get(position)).flipVertical;
                int i4 = ((ProjectResourceBean) pu.this.j.get(position)).flipHorizontal;
                RequestManager with = Glide.with(pu.this.getActivity());
                with.load(pu.this.g + File.separator + ((ProjectResourceBean) pu.this.j.get(position)).resFullName).asBitmap().centerCrop().signature((Key) kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.v) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                super.onResourceReady(iB.a(bitmap, i2, i4, i3), glideAnimation);
                            }
                        });
                return;
            }
            int i5 = ((ProjectResourceBean) pu.this.j.get(position)).rotate;
            int i6 = ((ProjectResourceBean) pu.this.j.get(position)).flipVertical;
            Glide.with(pu.this.getActivity()).load(((ProjectResourceBean) pu.this.j.get(position)).resFullName).asBitmap().centerCrop().signature((Key) kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.v) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(iB.a(bitmap, i5, ((ProjectResourceBean) pu.this.j.get(position)).flipHorizontal, i6), glideAnimation);
                        }
                    });
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
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
            String a2 = xB.b().a(getActivity(), R.string.common_message_name_unavailable);
            Iterator it2 = arrayList3.iterator();
            String str2 = "";
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (str2.length() > 0) {
                    str2 = str2 + ", ";
                }
                str2 = str2 + str3;
            }
            bB.a(getActivity(), a2 + "\n[" + str2 + "]", bB.TOAST_WARNING).show();
        } else {
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_import_complete), bB.TOAST_WARNING).show();
        }
        this.k.notifyDataSetChanged();
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
            this.l.setVisibility(View.VISIBLE);
        } else {
            this.l.setVisibility(View.GONE);
        }
        this.k.notifyDataSetChanged();
    }

    public void a(ProjectResourceBean projectResourceBean, boolean z) {
        this.j.add(projectResourceBean);
        if (z) {
            a aVar = this.k;
            aVar.notifyItemInserted(aVar.getItemCount());
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
            aVar.notifyItemInserted(aVar.getItemCount());
            j();
        }
    }
}