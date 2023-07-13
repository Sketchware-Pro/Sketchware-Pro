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
    private String sc_id;
    private RecyclerView recyclerView;
    private ArrayList<ProjectResourceBean> images;
    private LinearLayout actionButtonContainer;
    private TextView guide;
    private FloatingActionButton fab;
    private String projectImagesDirectory = "";
    private Adapter adapter = null;
    public boolean isSelecting = false;

    public ArrayList<ProjectResourceBean> d() {
        return images;
    }

    private int getColumnCount() {
        return ((int) (getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density)) / 100;
    }

    private void initialize() {
        sc_id = getActivity().getIntent().getStringExtra("sc_id");
        projectImagesDirectory = jC.d(sc_id).l();
        ArrayList<ProjectResourceBean> arrayList = jC.d(sc_id).b;
        if (arrayList != null) {
            for (ProjectResourceBean next : arrayList) {
                if (next.flipVertical == 0) {
                    next.flipVertical = 1;
                }
                if (next.flipHorizontal == 0) {
                    next.flipHorizontal = 1;
                }
                next.savedPos = 0;
                images.add(next);
            }
        }
    }

    private void unselectAll() {
        for (ProjectResourceBean projectResourceBean : images) {
            projectResourceBean.isSelected = false;
        }
    }

    private void deleteSelected() {
        int size = images.size();
        while (true) {
            size--;
            if (size >= 0) {
                if (images.get(size).isSelected) {
                    images.remove(size);
                }
            } else {
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void saveImages() {
        for (ProjectResourceBean image : images) {
            if (image.isNew || image.isEdited) {
                try {
                    String str2 = projectImagesDirectory + File.separator + image.resName;
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
        for (int i = 0; i < images.size(); i++) {
            ProjectResourceBean image = images.get(i);
            if (image.isNew || image.isEdited) {
                String str4 = image.resName;
                images.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, image.isNinePatch() ? str4 + ".9.png" : str4 + ".png"));
            }
        }
        jC.d(sc_id).b(images);
        jC.d(sc_id).y();
        jC.a(sc_id).b(jC.d(sc_id));
        jC.a(sc_id).k();
    }

    private void updateGuideVisibility() {
        if (images.size() == 0) {
            guide.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            guide.setVisibility(View.GONE);
        }
    }

    private void showAddImageDialog() {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", projectImagesDirectory);
        startActivityForResult(intent, 267);
    }

    private void openImportIconActivity() {
        Intent intent = new Intent(getActivity(), ImportIconActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putStringArrayListExtra("imageNames", getAllImageNames());
        startActivityForResult(intent, 210);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (bundle == null) {
            initialize();
        } else {
            sc_id = bundle.getString("sc_id");
            projectImagesDirectory = bundle.getString("dir_path");
            images = bundle.getParcelableArrayList("images");
        }
        oB fileUtil = new oB();
        // mkdirs
        fileUtil.f(projectImagesDirectory);
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 210) {
            if (i2 == Activity.RESULT_OK) {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, intent.getStringExtra("iconName"), intent.getStringExtra("iconPath"));
                projectResourceBean.savedPos = 2;
                projectResourceBean.isNew = true;
                addImage(projectResourceBean, true);
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
        } else if (i == 267) {
            if (i2 == Activity.RESULT_OK) {
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("images");
                Iterator it = parcelableArrayListExtra.iterator();
                while (it.hasNext()) {
                    images.add((ProjectResourceBean) it.next());
                }
                adapter.notifyItemRangeInserted(images.size() - parcelableArrayListExtra.size(), parcelableArrayListExtra.size());
                updateGuideVisibility();
                ((ManageImageActivity) getActivity()).l().e();
                bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
            }
        } else if (i == 268 && i2 == Activity.RESULT_OK) {
            ProjectResourceBean projectResourceBean2 = intent.getParcelableExtra("image");
            kC.z();
            Iterator<ProjectResourceBean> it2 = images.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ProjectResourceBean next = it2.next();
                if (next.resName.equals(projectResourceBean2.resName)) {
                    next.copy(projectResourceBean2);
                    adapter.notifyItemChanged(images.indexOf(next));
                    break;
                }
            }
            updateGuideVisibility();
            ((ManageImageActivity) getActivity()).l().e();
            bB.a(getActivity(), xB.b().a(getActivity(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_accept) {
                if (isSelecting) {
                    deleteSelected();
                    a(false);
                    updateGuideVisibility();
                    bB.a(getActivity(), xB.b().a(getActivity(), R.string.common_message_complete_delete), bB.TOAST_WARNING).show();
                    fab.show();
                }
            } else if (id == R.id.btn_cancel) {
                if (isSelecting) {
                    a(false);
                }
            } else if (id == R.id.fab) {
                a(false);
                showAddImageDialog();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanCount(getColumnCount());
        recyclerView.requestLayout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_image_menu, menu);
        menu.findItem(R.id.menu_image_delete).setVisible(!isSelecting);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_manage_image_list, viewGroup, false);
        setHasOptionsMenu(true);
        images = new ArrayList<>();
        recyclerView = viewGroup2.findViewById(R.id.image_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getColumnCount()));
        adapter = new Adapter(recyclerView);
        recyclerView.setAdapter(adapter);
        guide = viewGroup2.findViewById(R.id.tv_guide);
        guide.setText(xB.b().a(getContext(), R.string.design_manager_image_description_guide_add_image));
        actionButtonContainer = viewGroup2.findViewById(R.id.layout_btn_group);
        Button delete = viewGroup2.findViewById(R.id.btn_accept);
        Button cancel = viewGroup2.findViewById(R.id.btn_cancel);
        delete.setText(xB.b().a(getContext(), R.string.common_word_delete));
        cancel.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        fab = viewGroup2.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(this);
        kC.z();
        return viewGroup2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_image_delete) {
            a(!isSelecting);
        } else if (itemId == R.id.menu_image_import) {
            openImportIconActivity();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", sc_id);
        bundle.putString("dir_path", projectImagesDirectory);
        bundle.putParcelableArrayList("images", images);
        super.onSaveInstanceState(bundle);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private class ViewHolder extends RecyclerView.ViewHolder {
            public final CheckBox checkBox;
            public final TextView name;
            public final ImageView image;
            public final ImageView delete;
            public final ImageView ninePatch;
            public final LinearLayout deleteContainer;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.chk_select);
                name = itemView.findViewById(R.id.tv_image_name);
                image = itemView.findViewById(R.id.img);
                delete = itemView.findViewById(R.id.img_delete);
                ninePatch = itemView.findViewById(R.id.img_nine_patch);
                deleteContainer = itemView.findViewById(R.id.delete_img_container);
                image.setOnClickListener(v -> {
                    if (!isSelecting) {
                        showImageDetailsDialog(images.get(getLayoutPosition()));
                    } else {
                        checkBox.setChecked(!checkBox.isChecked());
                        images.get(getLayoutPosition()).isSelected = checkBox.isChecked();
                        notifyItemChanged(getLayoutPosition());
                    }
                });
                image.setOnLongClickListener(v -> {
                    a(true);
                    checkBox.setChecked(!checkBox.isChecked());
                    images.get(getLayoutPosition()).isSelected = checkBox.isChecked();
                    return true;
                });
            }
        }

        public Adapter(RecyclerView recyclerView) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy > 2) {
                            if (fab.isEnabled()) {
                                fab.hide();
                            }
                        } else if (dy < -2) {
                            if (fab.isEnabled()) {
                                fab.show();
                            }
                        }
                    }
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (isSelecting) {
                holder.deleteContainer.setVisibility(View.VISIBLE);
            } else {
                holder.deleteContainer.setVisibility(View.GONE);
            }
            if (images.get(position).isNinePatch()) {
                holder.ninePatch.setVisibility(View.VISIBLE);
            } else {
                holder.ninePatch.setVisibility(View.GONE);
            }
            if (images.get(position).isSelected) {
                holder.delete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.delete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }
            holder.checkBox.setChecked(images.get(position).isSelected);
            holder.name.setText(images.get(position).resName);
            if (images.get(position).savedPos == 0) {
                int i2 = images.get(position).rotate;
                int i3 = images.get(position).flipVertical;
                int i4 = images.get(position).flipHorizontal;
                RequestManager with = Glide.with(getActivity());
                with.load(projectImagesDirectory + File.separator + images.get(position).resFullName).asBitmap().centerCrop().signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.image) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                super.onResourceReady(iB.a(bitmap, i2, i4, i3), glideAnimation);
                            }
                        });
                return;
            }
            int i5 = images.get(position).rotate;
            int i6 = images.get(position).flipVertical;
            Glide.with(getActivity()).load(images.get(position).resFullName).asBitmap().centerCrop().signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(new BitmapImageViewTarget(holder.image) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(iB.a(bitmap, i5, images.get(position).flipHorizontal, i6), glideAnimation);
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
            return images.size();
        }
    }

    private void showImageDetailsDialog(ProjectResourceBean projectResourceBean) {
        Intent intent = new Intent(getContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", projectImagesDirectory);
        intent.putExtra("edit_target", projectResourceBean);
        startActivityForResult(intent, 268);
    }

    private ArrayList<String> getAllImageNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : images) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
            String str = next.resName;
            if (isImageNameDuplicate(str)) {
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
        addImages(arrayList2, false);
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
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
    }

    private boolean isImageNameDuplicate(String imageName) {
        for (ProjectResourceBean projectResourceBean : images) {
            if (projectResourceBean.resName.equals(imageName)) {
                return true;
            }
        }
        return false;
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return projectImagesDirectory + File.separator + projectResourceBean.resFullName;
    }

    public void a(boolean z) {
        isSelecting = z;
        getActivity().invalidateOptionsMenu();
        unselectAll();
        if (isSelecting) {
            actionButtonContainer.setVisibility(View.VISIBLE);
        } else {
            actionButtonContainer.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private void addImage(ProjectResourceBean projectResourceBean, boolean notifyAdapter) {
        images.add(projectResourceBean);
        if (notifyAdapter) {
            adapter.notifyItemInserted(adapter.getItemCount());
            updateGuideVisibility();
        }
    }

    private void addImages(ArrayList<ProjectResourceBean> arrayList, boolean notifyAdapter) {
        images.addAll(arrayList);
        if (notifyAdapter) {
            adapter.notifyItemInserted(adapter.getItemCount());
            updateGuideVisibility();
        }
    }
}