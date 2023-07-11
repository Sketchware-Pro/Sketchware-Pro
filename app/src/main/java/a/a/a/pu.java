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
        return j;
    }

    public final int e() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    public void f() {
        f = getActivity().getIntent().getStringExtra("sc_id");
        g = jC.d(f).l();
        ArrayList<ProjectResourceBean> arrayList = jC.d(f).b;
        if (arrayList != null) {
            for (ProjectResourceBean next : arrayList) {
                if (next.flipVertical == 0) {
                    next.flipVertical = 1;
                }
                if (next.flipHorizontal == 0) {
                    next.flipHorizontal = 1;
                }
                next.savedPos = 0;
                j.add(next);
            }
        }
    }

    public final void g() {
        for (ProjectResourceBean projectResourceBean : j) {
            projectResourceBean.isSelected = false;
        }
    }

    public void h() {
        int size = j.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (j.get(size).isSelected) {
                    j.remove(size);
                }
            } else {
                k.notifyDataSetChanged();
                return;
            }
        }
    }

    public void i() {
        for (ProjectResourceBean image : j) {
            if (image.isNew || image.isEdited) {
                try {
                    String str2 = g + File.separator + image.resName;
                    String str3 = image.isNinePatch() ? str2 + ".9.png" : str2 + ".png";
                    String str;
                    if (image.savedPos == 1) {
                        str = image.resFullName;
                    } else if (image.savedPos == 0) {
                        str = a(image);
                    } else {
                        str = image.resFullName;
                    }
                    iB.a(str, str3, image.rotate, image.flipHorizontal, image.flipVertical);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < j.size(); i++) {
            ProjectResourceBean image = j.get(i);
            if (image.isNew || image.isEdited) {
                String str4 = image.resName;
                j.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, image.isNinePatch() ? str4 + ".9.png" : str4 + ".png"));
            }
        }
        jC.d(f).b(j);
        jC.d(f).y();
        jC.a(f).b(jC.d(f));
        jC.a(f).k();
    }

    public void j() {
        if (j.size() == 0) {
            o.setVisibility(View.VISIBLE);
            i.setVisibility(View.GONE);
        } else {
            i.setVisibility(View.VISIBLE);
            o.setVisibility(View.GONE);
        }
    }

    public final void k() {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", j);
        intent.putExtra("sc_id", f);
        intent.putExtra("dir_path", g);
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
            f = bundle.getString("sc_id");
            g = bundle.getString("dir_path");
            j = bundle.getParcelableArrayList("images");
        }
        h = new oB();
        h.f(g);
        k.notifyDataSetChanged();
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
                    j.add((ProjectResourceBean) it.next());
                }
                k.notifyItemRangeInserted(j.size() - parcelableArrayListExtra.size(), parcelableArrayListExtra.size());
                j();
                ((ManageImageActivity) getActivity()).l().e();
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
        } else if (i == 268 && i2 == Activity.RESULT_OK) {
            ProjectResourceBean projectResourceBean2 = intent.getParcelableExtra("image");
            kC.z();
            Iterator<ProjectResourceBean> it2 = j.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ProjectResourceBean next = it2.next();
                if (next.resName.equals(projectResourceBean2.resName)) {
                    next.copy(projectResourceBean2);
                    k.notifyItemChanged(j.indexOf(next));
                    break;
                }
            }
            j();
            ((ManageImageActivity) getActivity()).l().e();
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_accept) {
                if (p) {
                    h();
                    a(false);
                    j();
                    bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_complete_delete), bB.TOAST_WARNING).show();
                    q.show();
                }
            } else if (id == R.id.btn_cancel) {
                if (p) {
                    a(false);
                }
            } else if (id == R.id.fab) {
                a(false);
                k();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) i.getLayoutManager()).setSpanCount(e());
        i.requestLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_image_menu, menu);
        menu.findItem(R.id.menu_image_delete).setVisible(!p);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        setHasOptionsMenu(true);
        j = new ArrayList<>();
        i = viewGroup2.findViewById(R.id.image_list);
        i.setHasFixedSize(true);
        i.setLayoutManager(new GridLayoutManager(getActivity(), e()));
        k = new a(i);
        i.setAdapter(k);
        o = viewGroup2.findViewById(R.id.tv_guide);
        o.setText(xB.b().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        l = viewGroup2.findViewById(R.id.layout_btn_group);
        m = viewGroup2.findViewById(R.id.btn_accept);
        n = viewGroup2.findViewById(R.id.btn_cancel);
        m.setText(xB.b().a(getContext(), R.string.common_word_delete));
        n.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        m.setOnClickListener(this);
        n.setOnClickListener(this);
        q = viewGroup2.findViewById(R.id.fab);
        q.setVisibility(View.VISIBLE);
        q.setOnClickListener(this);
        kC.z();
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_image_delete) {
            a(!p);
        } else if (itemId == R.id.menu_image_import) {
            l();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", f);
        bundle.putString("dir_path", g);
        bundle.putParcelableArrayList("images", j);
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
                t = itemView.findViewById(R.id.chk_select);
                u = itemView.findViewById(R.id.tv_image_name);
                v = itemView.findViewById(R.id.img);
                w = itemView.findViewById(R.id.img_delete);
                x = itemView.findViewById(R.id.img_nine_patch);
                y = itemView.findViewById(R.id.delete_img_container);
                v.setOnClickListener(v -> {
                    c = getLayoutPosition();
                    if (!p) {
                        b(j.get(getLayoutPosition()));
                    } else {
                        t.setChecked(!t.isChecked());
                        j.get(c).isSelected = t.isChecked();
                        notifyItemChanged(c);
                    }
                });
                v.setOnLongClickListener(v -> {
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
            if (p) {
                holder.y.setVisibility(View.VISIBLE);
            } else {
                holder.y.setVisibility(View.GONE);
            }
            if (j.get(position).isNinePatch()) {
                holder.x.setVisibility(View.VISIBLE);
            } else {
                holder.x.setVisibility(View.GONE);
            }
            if (j.get(position).isSelected) {
                holder.w.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.w.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            holder.t.setChecked(j.get(position).isSelected);
            holder.u.setText(j.get(position).resName);
            if (j.get(position).savedPos == 0) {
                int i2 = j.get(position).rotate;
                int i3 = j.get(position).flipVertical;
                int i4 = j.get(position).flipHorizontal;
                RequestManager with = Glide.with(getActivity());
                with.load(g + File.separator + j.get(position).resFullName).asBitmap().centerCrop().signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.v) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                super.onResourceReady(iB.a(bitmap, i2, i4, i3), glideAnimation);
                            }
                        });
                return;
            }
            int i5 = j.get(position).rotate;
            int i6 = j.get(position).flipVertical;
            Glide.with(getActivity()).load(j.get(position).resFullName).asBitmap().centerCrop().signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.v) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(iB.a(bitmap, i5, j.get(position).flipHorizontal, i6), glideAnimation);
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
            return j.size();
        }
    }

    public final void b(ProjectResourceBean projectResourceBean) {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", j);
        intent.putExtra("sc_id", f);
        intent.putExtra("dir_path", g);
        intent.putExtra("edit_target", projectResourceBean);
        startActivityForResult(intent, 268);
    }

    public final ArrayList<String> c() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : j) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
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
            String str2 = "";
            for (String str3 : arrayList3) {
                if (str2.length() > 0) {
                    str2 += ", ";
                }
                str2 += str3;
            }
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_name_unavailable) + "\n[" + str2 + "]", bB.TOAST_WARNING).show();
        } else {
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_import_complete), bB.TOAST_WARNING).show();
        }
        k.notifyDataSetChanged();
        j();
    }

    public boolean a(String str) {
        for (ProjectResourceBean projectResourceBean : j) {
            if (projectResourceBean.resName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return g + File.separator + projectResourceBean.resFullName;
    }

    public void a(boolean z) {
        p = z;
        getActivity().invalidateOptionsMenu();
        g();
        if (p) {
            l.setVisibility(View.VISIBLE);
        } else {
            l.setVisibility(View.GONE);
        }
        k.notifyDataSetChanged();
    }

    public void a(ProjectResourceBean projectResourceBean, boolean z) {
        j.add(projectResourceBean);
        if (z) {
            k.notifyItemInserted(k.getItemCount());
            j();
        }
    }

    public void a(ArrayList<ProjectResourceBean> arrayList, boolean z) {
        j.addAll(arrayList);
        if (z) {
            k.notifyItemInserted(k.getItemCount());
            j();
        }
    }
}