package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.manage.image.ManageImageImportActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

public class fu extends qA implements View.OnClickListener {
    private RecyclerView recyclerView;
    private String sc_id;
    private ArrayList<ProjectResourceBean> collectionImages;
    private Adapter adapter = null;
    private TextView guide;
    private Button importImages;

    private final ActivityResultLauncher<Intent> openImageImportDetails = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        var data = result.getData();
        if (result.getResultCode() == Activity.RESULT_OK && data != null) {
            ArrayList<ProjectResourceBean> importedImages;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                importedImages = data.getParcelableArrayListExtra("results", ProjectResourceBean.class);
            } else {
                importedImages = data.getParcelableArrayListExtra("results");
            }

            ArrayList<ProjectResourceBean> newImportedImages = new ArrayList<>();
            for (ProjectResourceBean image : importedImages) {
                newImportedImages.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, image.resFullName));
            }
            if (newImportedImages.size() > 0) {
                ((ManageImageActivity) requireActivity()).m().a(newImportedImages);
                ((ManageImageActivity) requireActivity()).f(0);
            }
        }
    });

    public void refreshData() {
        collectionImages = Op.g().f();
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
    }

    public void unselectAll() {
        for (ProjectResourceBean image : collectionImages) {
            image.isSelected = false;
        }
    }

    public void updateGuideVisibility() {
        boolean isEmpty = collectionImages.size() == 0;
        guide.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    public void importImages() {
        ArrayList<ProjectResourceBean> selectedCollections = new ArrayList<>();
        for (ProjectResourceBean image : collectionImages) {
            if (image.isSelected) {
                selectedCollections.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName));
            }
        }
        if (selectedCollections.size() > 0) {
            Intent intent = new Intent(requireActivity(), ManageImageImportActivity.class);
            intent.putParcelableArrayListExtra("project_images", ((ManageImageActivity) requireActivity()).m().d());
            intent.putParcelableArrayListExtra("selected_collections", selectedCollections);
            openImageImportDetails.launch(intent);
        }
        unselectAll();
        adapter.notifyDataSetChanged();
    }

    private void onItemSelected() {
        int count = 0;
        for (ProjectResourceBean image : collectionImages) {
            if (image.isSelected) {
                count += 1;
            }
        }
        if (count > 0) {
            importImages.setText(xB.b().a(requireContext(), R.string.common_word_import_count, count).toUpperCase());
            importImages.setVisibility(View.VISIBLE);
        } else {
            importImages.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sc_id = savedInstanceState != null ? savedInstanceState.getString("sc_id")
                : requireActivity().getIntent().getStringExtra("sc_id");
        refreshData();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && v.getId() == R.id.btn_import) {
            importImages.setVisibility(View.GONE);
            importImages();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager manager) {
            manager.setSpanCount(ManageImageActivity.getImageGridColumnCount(requireContext()));
        }
        recyclerView.requestLayout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fr_manage_image_list, container, false);
        recyclerView = root.findViewById(R.id.image_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), ManageImageActivity.getImageGridColumnCount(requireContext())));
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        guide = root.findViewById(R.id.tv_guide);
        guide.setText(xB.b().a(requireContext(), R.string.design_manager_image_description_guide_add_image));
        importImages = root.findViewById(R.id.btn_import);
        importImages.setText(xB.b().a(requireContext(), R.string.common_word_import).toUpperCase());
        importImages.setOnClickListener(this);
        importImages.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private class ViewHolder extends RecyclerView.ViewHolder {
            public final CheckBox checkBox;
            public final TextView name;
            public final ImageView image;
            public final ImageView ninePatch;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.chk_select);
                name = itemView.findViewById(R.id.tv_image_name);
                image = itemView.findViewById(R.id.img);
                ninePatch = itemView.findViewById(R.id.img_nine_patch);
                checkBox.setVisibility(View.VISIBLE);
                image.setOnClickListener(v -> {
                    checkBox.setChecked(!checkBox.isChecked());
                    collectionImages.get(getLayoutPosition()).isSelected = checkBox.isChecked();
                    onItemSelected();
                    notifyItemChanged(getLayoutPosition());
                });
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean image = collectionImages.get(position);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.ninePatch.setVisibility(image.isNinePatch() ? View.VISIBLE : View.GONE);
            Glide.with(requireActivity())
                    .load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName)
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(holder.image));
            holder.name.setText(image.resName);
            holder.checkBox.setChecked(image.isSelected);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_image_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return collectionImages.size();
        }
    }
}
