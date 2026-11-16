package com.besome.sketch.editor.manage.image;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImageImportBinding;
import pro.sketchware.databinding.ManageImportListItemBinding;

public class ManageImageImportActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public ManageImageImportBinding binding;
    private ItemAdapter adapter;
    private ArrayList<ProjectResourceBean> projectImages;
    private ArrayList<ProjectResourceBean> selectedCollections;
    private int selectedItem = 0;
    private QB nameValidator;

    private ArrayList<String> getReservedSelectedCollectionNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            names.add(selectedCollection.resName);
        }
        return names;
    }

    private ArrayList<String> getReservedProjectImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectImage : projectImages) {
            names.add(projectImage.resName);
        }
        return names;
    }

    private boolean n() {
        ArrayList<String> duplicateNames = new ArrayList<>();
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            if (selectedCollection.isDuplicateCollection) {
                duplicateNames.add(selectedCollection.resName);
            }
        }
        if (duplicateNames.isEmpty()) {
            return false;
        }

        String names = "";
        for (String name : duplicateNames) {
            if (!names.isEmpty()) {
                names = names + ", ";
            }
            names = names + name;
        }
        bB.a(getApplicationContext(), getString(R.string.common_message_name_unavailable)
                + "\n[" + names + "]", bB.TOAST_WARNING).show();
        return true;
    }

    private boolean isNameValid() {
        return nameValidator.b();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            int id = v.getId();
            if (id == R.id.btn_decide) {
                String name = Helper.getText(binding.edInput);
                if (!isNameValid()) {
                    binding.edInput.setText(selectedCollections.get(selectedItem).resName);
                } else if (!binding.chkSamename.isChecked()) {
                    ProjectResourceBean projectResourceBean = selectedCollections.get(selectedItem);
                    projectResourceBean.resName = name;
                    projectResourceBean.isDuplicateCollection = false;
                    nameValidator.a(getReservedSelectedCollectionNames());
                    adapter.notifyDataSetChanged();
                } else {
                    int i = 0;
                    while (i < selectedCollections.size()) {
                        ProjectResourceBean projectResourceBean2 = selectedCollections.get(i);
                        projectResourceBean2.resName = name + "_" + ++i;
                        projectResourceBean2.isDuplicateCollection = false;
                    }
                    nameValidator.a(getReservedSelectedCollectionNames());
                    adapter.notifyDataSetChanged();
                }
            } else if (id == R.id.img_backbtn) {
                getOnBackPressedDispatcher().onBackPressed();
            } else if (id == R.id.tv_sendbtn && !n()) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("results", selectedCollections);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
            return;
        }
        
        binding = ManageImageImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_OK);
                finish();
            }
        });

        binding.imgBackbtn.setOnClickListener(this);
        binding.tvSendbtn.setText(getString(R.string.common_word_import).toUpperCase());
        binding.tvSendbtn.setOnClickListener(this);
        binding.tvSamename.setText(R.string.design_manager_image_title_apply_same_naming);

        adapter = new ItemAdapter();
        binding.recyclerList.setAdapter(adapter);
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));

        projectImages = getIntent().getParcelableArrayListExtra("project_images");
        selectedCollections = getIntent().getParcelableArrayListExtra("selected_collections");
        
        int selectedCollectionsSize = selectedCollections.size();
        binding.tvCurrentnum.setText(String.valueOf(1));
        binding.tvTotalnum.setText(String.valueOf(selectedCollectionsSize));

        binding.edInput.setText(selectedCollections.get(0).resName);
        // hint & privateImeOptions are now handled in XML
        
        nameValidator = new QB(getApplicationContext(), binding.tiInput, uq.b, getReservedProjectImageNames(), getReservedSelectedCollectionNames());

        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollections.size());
            } else {
                nameValidator.c(selectedCollections.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });

        binding.btnDecide.setText(R.string.design_manager_change_name_button);
        binding.btnDecide.setOnClickListener(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeLogic();
        showPreview(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    private void initializeLogic() {
        ArrayList<ProjectResourceBean> duplicateCollections = new ArrayList<>();
        ArrayList<ProjectResourceBean> notDuplicateCollections = new ArrayList<>();
        for (ProjectResourceBean selectedCollection : selectedCollections) {
            if (isNameInUseByProjectImage(selectedCollection.resName)) {
                selectedCollection.isDuplicateCollection = true;
                duplicateCollections.add(selectedCollection);
            } else {
                selectedCollection.isDuplicateCollection = false;
                notDuplicateCollections.add(selectedCollection);
            }
        }
        if (!duplicateCollections.isEmpty()) {
            bB.b(getApplicationContext(), getString(R.string.design_manager_message_collection_name_conflict), bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(), getString(R.string.design_manager_message_collection_name_no_conflict), bB.TOAST_NORMAL).show();
        }
        selectedCollections = new ArrayList<>();
        selectedCollections.addAll(duplicateCollections);
        selectedCollections.addAll(notDuplicateCollections);
    }

    private void showPreview(int index) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(selectedCollections.get(index).resFullName)
                .centerCrop()
                .error(R.drawable.ic_remove_grey600_24dp)
                .into(new BitmapImageViewTarget(binding.img).getView());
    }

    private boolean isNameInUseByProjectImage(String name) {
        for (ProjectResourceBean projectImage : projectImages) {
            if (projectImage.resName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        public ItemAdapter() {
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            ProjectResourceBean projectResourceBean = selectedCollections.get(position);
            if (projectResourceBean.isDuplicateCollection) {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (position == selectedItem) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Glide.with(getApplicationContext())
                    .load(projectResourceBean.resFullName)
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(viewHolder.img).getView());
            viewHolder.tvName.setText(selectedCollections.get(position).resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageImportListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public int getItemCount() {
            return selectedCollections.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout layoutItem;
            public final ImageView imgConflict;
            public final ImageView img;
            public final TextView tvName;

            public ViewHolder(ManageImportListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                layoutItem = itemBinding.layoutItem;
                imgConflict = itemBinding.imgConflict;
                img = itemBinding.img;
                tvName = itemBinding.tvName;
                
                img.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        binding.tvCurrentnum.setText(String.valueOf(selectedItem + 1));
                        binding.edInput.setText(selectedCollections.get(selectedItem).resName);
                        
                        if (binding.chkSamename.isChecked()) {
                            nameValidator.c(null);
                            nameValidator.a(selectedCollections.size());
                        } else {
                            nameValidator.c(selectedCollections.get(selectedItem).resName);
                            nameValidator.a(1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}