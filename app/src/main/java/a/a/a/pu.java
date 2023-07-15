package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.common.ImportIconActivity;
import com.besome.sketch.editor.manage.image.AddImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

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

    private final ActivityResultLauncher<Intent> openImportIconActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            var data = result.getData();
            assert data != null;
            ProjectResourceBean icon = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                    data.getStringExtra("iconName"), data.getStringExtra("iconPath"));
            icon.savedPos = 2;
            icon.isNew = true;
            addImage(icon);
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
        }
    });
    private final ActivityResultLauncher<Intent> showAddImageDialog = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
       if (result.getResultCode() == Activity.RESULT_OK) {
           assert result.getData() != null;
           ArrayList<ProjectResourceBean> addedImages;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
               addedImages = result.getData().getParcelableArrayListExtra("images", ProjectResourceBean.class);
           } else {
               addedImages = result.getData().getParcelableArrayListExtra("images");
           }
           images.addAll(addedImages);
           adapter.notifyItemRangeInserted(images.size() - addedImages.size(), addedImages.size());
           updateGuideVisibility();
           ((ManageImageActivity) requireActivity()).l().refreshData();
           bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_add_complete), bB.TOAST_NORMAL).show();
       }
    });
    private final ActivityResultLauncher<Intent> showImageDetailsDialog = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            assert result.getData() != null;
            ProjectResourceBean editedImage;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                editedImage = result.getData().getParcelableExtra("image", ProjectResourceBean.class);
            } else {
                editedImage = result.getData().getParcelableExtra("image");
            }
            kC.z();
            for (ProjectResourceBean image : images) {
                if (image.resName.equals(editedImage.resName)) {
                    image.copy(editedImage);
                    adapter.notifyItemChanged(images.indexOf(image));
                    break;
                }
            }
            updateGuideVisibility();
            ((ManageImageActivity) requireActivity()).l().refreshData();
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
        }
    });

    public ArrayList<ProjectResourceBean> d() {
        return images;
    }

    private void initialize() {
        sc_id = requireActivity().getIntent().getStringExtra("sc_id");
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
        for (int i = images.size() - 1; i >= 0; i--) {
            if (images.get(i).isSelected) {
                images.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void saveImages() {
        for (ProjectResourceBean image : images) {
            if (image.isNew || image.isEdited) {
                try {
                    String path;
                    if (image.savedPos == 0) {
                        path = a(image);
                    } else {
                        path = image.resFullName;
                    }
                    String str = projectImagesDirectory + File.separator + image.resName;
                    iB.a(path, image.isNinePatch() ? str + ".9.png" : str + ".png", image.rotate, image.flipHorizontal, image.flipVertical);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < images.size(); i++) {
            ProjectResourceBean image = images.get(i);
            if (image.isNew || image.isEdited) {
                images.set(i, new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                        image.resName, image.isNinePatch() ? image.resName + ".9.png" : image.resName + ".png"));
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
        Intent intent = new Intent(requireContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", projectImagesDirectory);
        showAddImageDialog.launch(intent);
    }

    private void openImportIconActivity() {
        Intent intent = new Intent(requireActivity(), ImportIconActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putStringArrayListExtra("imageNames", getAllImageNames());
        openImportIconActivity.launch(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            initialize();
        } else {
            sc_id = savedInstanceState.getString("sc_id");
            projectImagesDirectory = savedInstanceState.getString("dir_path");
            images = savedInstanceState.getParcelableArrayList("images");
        }
        // mkdirs
        new oB().f(projectImagesDirectory);
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
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
                    bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.common_message_complete_delete), bB.TOAST_WARNING).show();
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
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager manager) {
            manager.setSpanCount(ManageImageActivity.getImageGridColumnCount(requireContext()));
        }
        recyclerView.requestLayout();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.manage_image_menu, menu);
        menu.findItem(R.id.menu_image_delete).setVisible(!isSelecting);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fr_manage_image_list, container, false);
        setHasOptionsMenu(true);
        images = new ArrayList<>();
        recyclerView = root.findViewById(R.id.image_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), ManageImageActivity.getImageGridColumnCount(requireContext())));
        adapter = new Adapter(recyclerView);
        recyclerView.setAdapter(adapter);
        guide = root.findViewById(R.id.tv_guide);
        guide.setText(xB.b().a(requireContext(), R.string.design_manager_image_description_guide_add_image));
        actionButtonContainer = root.findViewById(R.id.layout_btn_group);
        Button delete = root.findViewById(R.id.btn_accept);
        Button cancel = root.findViewById(R.id.btn_cancel);
        delete.setText(xB.b().a(requireContext(), R.string.common_word_delete));
        cancel.setText(xB.b().a(requireContext(), R.string.common_word_cancel));
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        fab = root.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(this);
        kC.z();
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_image_delete) {
            a(!isSelecting);
        } else if (id == R.id.menu_image_import) {
            openImportIconActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        outState.putString("dir_path", projectImagesDirectory);
        outState.putParcelableArrayList("images", images);
        super.onSaveInstanceState(outState);
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
            ProjectResourceBean image = images.get(position);

            holder.deleteContainer.setVisibility(isSelecting ? View.VISIBLE : View.GONE);
            holder.ninePatch.setVisibility(image.isNinePatch() ? View.VISIBLE : View.GONE);
            holder.delete.setImageResource(image.isSelected ? R.drawable.ic_checkmark_green_48dp
                    : R.drawable.ic_trashcan_white_48dp);
            holder.checkBox.setChecked(image.isSelected);
            holder.name.setText(image.resName);

            Glide.with(requireActivity())
                    .load(image.savedPos == 0 ? projectImagesDirectory + File.separator + image.resFullName
                            : images.get(position).resFullName)
                    .asBitmap()
                    .centerCrop()
                    .signature(kC.n())
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(holder.image) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            super.onResourceReady(iB.a(bitmap, image.rotate, image.flipHorizontal, image.flipVertical), glideAnimation);
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
        Intent intent = new Intent(requireContext(), AddImageActivity.class);
        intent.putParcelableArrayListExtra("images", images);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("dir_path", projectImagesDirectory);
        intent.putExtra("edit_target", projectResourceBean);
        showImageDetailsDialog.launch(intent);
    }

    private ArrayList<String> getAllImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectResourceBean : images) {
            names.add(projectResourceBean.resName);
        }
        return names;
    }

    public void a(ArrayList<ProjectResourceBean> arrayList) {
        ArrayList<ProjectResourceBean> imagesToAdd = new ArrayList<>();
        ArrayList<String> duplicateNames = new ArrayList<>();
        for (ProjectResourceBean next : arrayList) {
            String imageName = next.resName;
            if (isImageNameDuplicate(imageName)) {
                duplicateNames.add(imageName);
            } else {
                ProjectResourceBean image = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, imageName, next.resFullName);
                image.savedPos = 1;
                image.isNew = true;
                image.rotate = 0;
                image.flipVertical = 1;
                image.flipHorizontal = 1;
                imagesToAdd.add(image);
            }
        }
        addImages(imagesToAdd);
        if (duplicateNames.size() > 0) {
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.common_message_name_unavailable) + "\n" +
                    "[" + String.join(", ", duplicateNames) + "]", bB.TOAST_WARNING).show();
        } else {
            bB.a(requireActivity(), xB.b().a(requireActivity(), R.string.design_manager_message_import_complete), bB.TOAST_WARNING).show();
        }
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
    }

    private boolean isImageNameDuplicate(String imageName) {
        for (ProjectResourceBean image : images) {
            if (image.resName.equals(imageName)) {
                return true;
            }
        }
        return false;
    }

    private String a(ProjectResourceBean projectResourceBean) {
        return projectImagesDirectory + File.separator + projectResourceBean.resFullName;
    }

    public void a(boolean isSelecting) {
        this.isSelecting = isSelecting;
        requireActivity().invalidateOptionsMenu();
        unselectAll();
        actionButtonContainer.setVisibility(this.isSelecting ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    private void addImage(ProjectResourceBean projectResourceBean) {
        images.add(projectResourceBean);
        adapter.notifyItemInserted(adapter.getItemCount());
        updateGuideVisibility();
    }

    private void addImages(ArrayList<ProjectResourceBean> arrayList) {
        images.addAll(arrayList);
    }
}