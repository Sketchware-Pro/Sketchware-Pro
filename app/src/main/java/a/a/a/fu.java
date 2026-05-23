package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;

import pro.sketchware.R;
import pro.sketchware.databinding.FrManageImageListBinding;
import pro.sketchware.databinding.ManageImageListItemBinding;

public class fu extends qA implements View.OnClickListener {

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
            if (!newImportedImages.isEmpty()) {
                ((ManageImageActivity) requireActivity()).m().a(newImportedImages);
                ((ManageImageActivity) requireActivity()).f(0);
            }
        }
    });

    private FrManageImageListBinding binding;
    private ArrayList<ProjectResourceBean> collectionImages;
    private ArrayList<ProjectResourceBean> filteredImages;
    private Adapter adapter;
    private String sc_id;

    private Button btnImport;
    private MaterialCardView layoutBtnImport;

    public void refreshData() {
        collectionImages = Op.g().f();
        filter(binding.etSearch.getText().toString());
    }

    private void filter(String query) {
        filteredImages.clear();
        if (query.isEmpty()) {
            filteredImages.addAll(collectionImages);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (ProjectResourceBean image : collectionImages) {
                if (image.resName.toLowerCase().contains(lowerCaseQuery)) {
                    filteredImages.add(image);
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateGuideVisibility();
    }

    public void unselectAll() {
        for (ProjectResourceBean image : collectionImages) {
            image.isSelected = false;
        }
        filter(binding.etSearch.getText().toString());
    }

    public boolean isSelecting() {
        for (ProjectResourceBean image : collectionImages) {
            if (image.isSelected) return true;
        }
        return false;
    }

    public void updateGuideVisibility() {
        boolean isEmpty = filteredImages.isEmpty();
        binding.tvGuide.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        binding.imageList.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    public void importImages() {
        ArrayList<ProjectResourceBean> selectedCollections = new ArrayList<>();
        for (ProjectResourceBean image : collectionImages) {
            if (image.isSelected) {
                selectedCollections.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, image.resName, wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName));
            }
        }
        if (!selectedCollections.isEmpty()) {
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
            btnImport.setText(getString(R.string.common_word_import_count, count).toUpperCase());
            layoutBtnImport.setVisibility(View.VISIBLE);
        } else {
            layoutBtnImport.setVisibility(View.GONE);
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
            layoutBtnImport.setVisibility(View.GONE);
            importImages();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (binding.imageList.getLayoutManager() instanceof GridLayoutManager manager) {
            manager.setSpanCount(ManageImageActivity.getImageGridColumnCount(requireContext()));
        }
        binding.imageList.requestLayout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FrManageImageListBinding.inflate(inflater, container, false);
        filteredImages = new ArrayList<>();
        binding.imageList.setHasFixedSize(true);
        binding.imageList.setLayoutManager(new GridLayoutManager(requireActivity(), ManageImageActivity.getImageGridColumnCount(requireContext())));
        adapter = new Adapter();
        binding.imageList.setAdapter(adapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.tvGuide.setText(R.string.design_manager_image_description_guide_add_image);
        btnImport = requireActivity().findViewById(R.id.btn_import);
        layoutBtnImport = requireActivity().findViewById(R.id.layout_btn_import);
        btnImport.setOnClickListener(this);
        layoutBtnImport.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean image = filteredImages.get(position);
            holder.binding.chkSelect.setVisibility(View.VISIBLE);
            holder.binding.imgNinePatch.setVisibility(image.isNinePatch() ? View.VISIBLE : View.GONE);
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + image.resFullName)
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(holder.binding.img).getView());
            holder.binding.tvImageName.setText(image.resName);
            holder.binding.chkSelect.setChecked(image.isSelected);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageImageListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public int getItemCount() {
            return filteredImages.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final ManageImageListItemBinding binding;

            public ViewHolder(@NonNull ManageImageListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                binding.chkSelect.setVisibility(View.VISIBLE);
                binding.img.setOnClickListener(v -> {
                    binding.chkSelect.setChecked(!binding.chkSelect.isChecked());
                    filteredImages.get(getLayoutPosition()).isSelected = binding.chkSelect.isChecked();
                    onItemSelected();
                    notifyItemChanged(getLayoutPosition());
                });
            }
        }
    }
}
