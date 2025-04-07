package com.besome.sketch.editor.manage.font;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.util.ArrayList;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontImportBinding;

@SuppressLint("NotifyDataSetChanged")
public class ManageFontImportActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private EditText r;
    private ItemAdapter itemAdapter;
    private ArrayList<ProjectResourceBean> projectFonts;
    private ArrayList<ProjectResourceBean> selectedCollection;
    private int selectedItem;
    private QB nameValidator;
    private ManageFontImportBinding binding;

    private boolean isNameInUseByProjectFont(String name) {
        for (ProjectResourceBean font : projectFonts) {
            if (font.resName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void showPreview(int position) {
        String fontFilePath = selectedCollection.get(position).resFullName;
        binding.textFont.setTypeface(Typeface.createFromFile(fontFilePath));
        binding.textFont.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_description_example_sentence));
    }

    private ArrayList<String> getReservedSelectedCollectionNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectResourceBean : selectedCollection) {
            names.add(projectResourceBean.resName);
        }
        return names;
    }

    private ArrayList<String> getReservedProjectImageNames() {
        ArrayList<String> names = new ArrayList<>();
        names.add("app_icon");
        for (ProjectResourceBean projectResourceBean : projectFonts) {
            names.add(projectResourceBean.resName);
        }
        return names;
    }

    private boolean n() {
        ArrayList<String> duplicateNames = new ArrayList<>();
        for (ProjectResourceBean font : selectedCollection) {
            if (font.isDuplicateCollection) {
                duplicateNames.add(font.resName);
            }
        }

        if (!duplicateNames.isEmpty()) {
            String names = "";
            for (String name : duplicateNames) {
                if (!name.isEmpty()) {
                    names += ", ";
                }
                names += name;
            }

            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(),
                    R.string.common_message_name_unavailable) + "\n[" + names + "]", bB.TOAST_WARNING).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameValid() {
        return nameValidator.b();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.btnDecide.getId()) {
            String resName = Helper.getText(r);
            if (!isNameValid()) {
                ProjectResourceBean font = selectedCollection.get(selectedItem);
                r.setText(font.resName);
            } else {
                if (!binding.chkSamename.isChecked()) {
                    ProjectResourceBean resourceBean = selectedCollection.get(selectedItem);
                    resourceBean.resName = resName;
                    resourceBean.isDuplicateCollection = false;
                } else {
                    int i = 0;
                    while (i < selectedCollection.size()) {
                        ProjectResourceBean font = selectedCollection.get(i);
                        font.resName = resName + "_" + ++i;
                        font.isDuplicateCollection = false;
                    }
                }
                nameValidator.a(getReservedSelectedCollectionNames());
                itemAdapter.notifyDataSetChanged();
            }

        } else if (id == binding.imgBackbtn.getId()) {
            onBackPressed();
        } else if (id == binding.tvSendbtn.getId() && !n()) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("results", selectedCollection);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        binding = ManageFontImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBackbtn.setOnClickListener(this);
        binding.tvSendbtn.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        binding.tvSendbtn.setOnClickListener(this);
        binding.tvSamename.setText(xB.b().a(getApplicationContext(), R.string.design_manager_font_title_apply_same_naming));
        itemAdapter = new ItemAdapter();
        binding.recyclerList.setHasFixedSize(true);
        binding.recyclerList.setAdapter(itemAdapter);
        binding.recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        projectFonts = getIntent().getParcelableArrayListExtra("project_fonts");
        selectedCollection = getIntent().getParcelableArrayListExtra("selected_collections");
        binding.tvCurrentnum.setText(String.valueOf(1));
        binding.tvTotalnum.setText(String.valueOf(selectedCollection.size()));
        r = binding.edInput.getEditText();
        r.setText(selectedCollection.get(0).resName);
        r.setPrivateImeOptions("defaultInputmode=english;");
        binding.edInput.setHint(xB.b().a(this, R.string.design_manager_font_hint_enter_font_name));
        nameValidator = new QB(getApplicationContext(), binding.edInput.getTextInputLayout(), uq.b, getReservedProjectImageNames(), getReservedSelectedCollectionNames());
        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollection.size());
            } else {
                nameValidator.c(selectedCollection.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });
        Button btnDecide = findViewById(R.id.btn_decide);
        btnDecide.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        btnDecide.setOnClickListener(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initializeLogic();
        showPreview(0);
        itemAdapter.notifyDataSetChanged();
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
        for (ProjectResourceBean collectionFont : selectedCollection) {
            if (isNameInUseByProjectFont(collectionFont.resName)) {
                collectionFont.isDuplicateCollection = true;
                duplicateCollections.add(collectionFont);
            } else {
                collectionFont.isDuplicateCollection = false;
                notDuplicateCollections.add(collectionFont);
            }
        }

        if (!duplicateCollections.isEmpty()) {
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), 1).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), 0).show();
        }

        selectedCollection = new ArrayList<>();
        selectedCollection.addAll(duplicateCollections);
        selectedCollection.addAll(notDuplicateCollections);
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        public ItemAdapter() {
        }

        @Override
        public int getItemCount() {
            return selectedCollection.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (selectedCollection.get(position).isDuplicateCollection) {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.imgConflict.setImageResource(R.drawable.ic_ok_48dp);
            }

            if (position == selectedItem) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline);
            }
            viewHolder.img.setImageResource(R.drawable.ic_font_48dp);
            viewHolder.tvName.setText(selectedCollection.get(position).resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_import_list_item, parent, false));
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout layoutItem;
            public ImageView imgConflict;
            public ImageView img;
            public TextView tvName;

            public ViewHolder(View itemView) {
                super(itemView);
                layoutItem = itemView.findViewById(R.id.layout_item);
                imgConflict = itemView.findViewById(R.id.img_conflict);
                img = itemView.findViewById(R.id.img);
                tvName = itemView.findViewById(R.id.tv_name);
                img.setOnClickListener(view -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        binding.tvCurrentnum.setText(String.valueOf(getLayoutPosition() + 1));
                        r.setText(selectedCollection.get(getLayoutPosition()).resName);
                        if (binding.chkSamename.isChecked()) {
                            nameValidator.c(null);
                            nameValidator.a(selectedCollection.size());
                        } else {
                            nameValidator.c(selectedCollection.get(getLayoutPosition()).resName);
                            nameValidator.a(1);
                        }

                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
