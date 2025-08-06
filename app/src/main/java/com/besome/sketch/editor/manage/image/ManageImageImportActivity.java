package com.besome.sketch.editor.manage.image;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import a.a.a.QB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class ManageImageImportActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView tv_currentnum;
    private EditText ed_input_edittext;
    private CheckBox chk_samename;
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
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(),
                R.string.common_message_name_unavailable) + "\n[" + names + "]", bB.TOAST_WARNING).show();
        return true;
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
        if (!mB.a()) {
            int id = v.getId();
            if (id != R.id.btn_decide) {
                if (id != R.id.img_backbtn) {
                    if (id == R.id.tv_sendbtn && !n()) {
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("results", selectedCollections);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    onBackPressed();
                }
            } else {
                String name = Helper.getText(ed_input_edittext);
                if (!isNameValid()) {
                    ed_input_edittext.setText(selectedCollections.get(selectedItem).resName);
                } else if (!chk_samename.isChecked()) {
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
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        setContentView(R.layout.manage_image_import);
        ImageView img_backbtn = findViewById(R.id.img_backbtn);
        img_backbtn.setOnClickListener(this);
        tv_currentnum = findViewById(R.id.tv_currentnum);
        TextView tv_totalnum = findViewById(R.id.tv_totalnum);
        TextView tv_sendbtn = findViewById(R.id.tv_sendbtn);
        tv_sendbtn.setText(xB.b().a(getApplicationContext(), R.string.common_word_import).toUpperCase());
        tv_sendbtn.setOnClickListener(this);
        TextView tv_samename = findViewById(R.id.tv_samename);
        tv_samename.setText(xB.b().a(getApplicationContext(), R.string.design_manager_image_title_apply_same_naming));
        adapter = new ItemAdapter();
        RecyclerView recycler_list = findViewById(R.id.recycler_list);
        recycler_list.setAdapter(adapter);
        recycler_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        projectImages = getIntent().getParcelableArrayListExtra("project_images");
        selectedCollections = getIntent().getParcelableArrayListExtra("selected_collections");
        int selectedCollectionsSize = selectedCollections.size();
        tv_currentnum.setText(String.valueOf(1));
        tv_totalnum.setText(String.valueOf(selectedCollectionsSize));
        EasyDeleteEditText ed_input = findViewById(R.id.ed_input);
        ed_input_edittext = ed_input.getEditText();
        ed_input_edittext.setText(selectedCollections.get(0).resName);
        ed_input_edittext.setPrivateImeOptions("defaultInputmode=english;");
        ed_input.setHint(xB.b().a(this, R.string.design_manager_image_hint_enter_image_name));
        nameValidator = new QB(getApplicationContext(), ed_input.getTextInputLayout(), uq.b, getReservedProjectImageNames(), getReservedSelectedCollectionNames());
        chk_samename = findViewById(R.id.chk_samename);
        chk_samename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nameValidator.c(null);
                nameValidator.a(selectedCollections.size());
            } else {
                nameValidator.c(selectedCollections.get(selectedItem).resName);
                nameValidator.a(1);
            }
        });
        Button btn_decide = findViewById(R.id.btn_decide);
        btn_decide.setText(xB.b().a(getApplicationContext(), R.string.design_manager_change_name_button));
        btn_decide.setOnClickListener(this);
        img = findViewById(R.id.img);
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
            bB.b(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_conflict), bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_collection_name_no_conflict), bB.TOAST_NORMAL).show();
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
                .into(new BitmapImageViewTarget(img).getView());
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
                viewHolder.img_conflict.setImageResource(R.drawable.ic_cancel_48dp);
            } else {
                viewHolder.img_conflict.setImageResource(R.drawable.ic_ok_48dp);
            }
            if (position == selectedItem) {
                viewHolder.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else {
                viewHolder.img.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Glide.with(getApplicationContext())
                    .load(projectResourceBean.resFullName)
                    //.asBitmap()
                    .centerCrop()
                    .error(R.drawable.ic_remove_grey600_24dp)
                    .into(new BitmapImageViewTarget(viewHolder.img).getView());
            viewHolder.tv_name.setText(selectedCollections.get(position).resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_import_list_item, parent, false));
        }

        @Override
        public int getItemCount() {
            return selectedCollections.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final LinearLayout layout_item;
            public final ImageView img_conflict;
            public final ImageView img;
            public final TextView tv_name;

            public ViewHolder(View itemView) {
                super(itemView);
                layout_item = itemView.findViewById(R.id.layout_item);
                img_conflict = itemView.findViewById(R.id.img_conflict);
                img = itemView.findViewById(R.id.img);
                tv_name = itemView.findViewById(R.id.tv_name);
                img.setOnClickListener(v -> {
                    if (!mB.a()) {
                        selectedItem = getLayoutPosition();
                        showPreview(selectedItem);
                        tv_currentnum.setText(String.valueOf(selectedItem + 1));
                        ed_input_edittext.setText(selectedCollections.get(selectedItem).resName);
                        if (chk_samename.isChecked()) {
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
