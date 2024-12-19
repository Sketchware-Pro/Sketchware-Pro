package a.a.a;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.font.ManageFontActivity;
import com.besome.sketch.editor.manage.font.ManageFontImportActivity;

import java.io.File;
import java.util.ArrayList;

import mod.hey.studios.util.Helper;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontBinding;
import pro.sketchware.databinding.ManageFontListItemBinding;
import pro.sketchware.databinding.FrManageFontListBinding;

public class St extends qA {

    private FrManageFontListBinding binding;

    public String sc_id;
    public fontAdapter adapter;
    public String dirPath = "";
    public ArrayList<ProjectResourceBean> projectResourceBeans;
    private ManageFontBinding actBinding;

    public void processProjectResources(ArrayList<ProjectResourceBean> resourceBeans) {
        if (resourceBeans == null || resourceBeans.isEmpty()) {
            return;
        }

        ArrayList<ProjectResourceBean> processedResources = new ArrayList<>();

        for (ProjectResourceBean resource : resourceBeans) {
            processedResources.add(new ProjectResourceBean(
                    ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                    resource.resName,
                    resource.resFullName
            ));
        }

        if (!processedResources.isEmpty()) {
            ManageFontActivity activity = (ManageFontActivity) requireActivity();
            activity.m().handleResourceImport(processedResources);
            activity.f(0);
        }
    }

    public final void initializeDirPathAndScId() {
        sc_id = requireActivity().getIntent().getStringExtra("sc_id");
        dirPath = requireActivity().getIntent().getStringExtra("dir_path");
    }

    public void loadProjectResources() {
        projectResourceBeans = Np.g().f();
        adapter.notifyDataSetChanged();

        if (projectResourceBeans.isEmpty()) {
            binding.tvGuide.setVisibility(View.VISIBLE);
            binding.fontList.setVisibility(View.GONE);
        } else {
            binding.fontList.setVisibility(View.VISIBLE);
            binding.tvGuide.setVisibility(View.GONE);
        }
    }

    public final void resetSelection() {
        for (ProjectResourceBean resource : projectResourceBeans) {
            resource.isSelected = false;
        }
        adapter.notifyDataSetChanged();
        requireActivity().findViewById(R.id.layout_btn_import).setVisibility(View.GONE);
    }

    public boolean isSelecting() {
        return projectResourceBeans.stream().anyMatch(resource -> resource.isSelected);
    }

    public final void updateImportButtonVisibility() {
        int selectedCount = 0;

        for (ProjectResourceBean resource : projectResourceBeans) {
            if (resource.isSelected) {
                selectedCount++;
            }
        }

        if (selectedCount > 0) {
            actBinding.btnImport.setText(Helper.getResString(R.string.common_word_import_count, selectedCount).toUpperCase());
            actBinding.layoutBtnImport.animate().translationY(0F).setDuration(200L).start();
            actBinding.layoutBtnImport.setVisibility(View.VISIBLE);
        } else {
            actBinding.layoutBtnImport.animate().translationY(400F).setDuration(200L).start();
            actBinding.layoutBtnImport.setVisibility(View.GONE);
        }
    }

    public void importSelectedFonts() {
        ArrayList<ProjectResourceBean> selectedFonts = new ArrayList<>();

        for (ProjectResourceBean resource : projectResourceBeans) {
            if (resource.isSelected) {
                String path = wq.a() +
                        File.separator +
                        "font" +
                        File.separator +
                        "data" +
                        File.separator +
                        resource.resFullName;
                selectedFonts.add(new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, resource.resName, path));
            }
        }

        if (!selectedFonts.isEmpty()) {
            ArrayList<ProjectResourceBean> fontCollection = ((ManageFontActivity) requireActivity()).m().getProjectResourceBeans();
            Intent intent = new Intent(getActivity(), ManageFontImportActivity.class);
            intent.putParcelableArrayListExtra("project_fonts", fontCollection);
            intent.putParcelableArrayListExtra("selected_collections", selectedFonts);
            startActivityForResult(intent, 232);
        }

        resetSelection();
        adapter.notifyDataSetChanged();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        new oB().f(dirPath);
        if (bundle == null) {
            initializeDirPathAndScId();
        } else {
            sc_id = bundle.getString("sc_id");
            dirPath = bundle.getString("dir_path");
        }

        loadProjectResources();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 232 && resultCode == Activity.RESULT_OK && data != null) {
            processProjectResources(data.getParcelableArrayListExtra("results"));
        }
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        binding = FrManageFontListBinding.inflate(layoutInflater, viewGroup, false);
        actBinding = ((ManageFontActivity) requireActivity()).binding;

        setHasOptionsMenu(true);

        binding.fontList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new fontAdapter();
        binding.fontList.setAdapter(adapter);

        binding.tvGuide.setText(Helper.getResString(R.string.design_manager_font_description_guide_add_font));

        actBinding.btnImport.setText(Helper.getResString(R.string.common_word_import).toUpperCase());
        actBinding.btnImport.setOnClickListener(view -> {
            importSelectedFonts();
        });

        return binding.getRoot();
    }

    public void onDestroy() {
        super.onDestroy();
        Np.g().d();
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("sc_id", sc_id);
        bundle.putString("dir_path", dirPath);
    }

    public class fontAdapter extends RecyclerView.Adapter<fontAdapter.MyViewHolder> {
        private int selectedPosition = -1;

        @Override
        public int getItemCount() {
            return projectResourceBeans.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ProjectResourceBean resource = projectResourceBeans.get(position);

            String fontPath = wq.a() + File.separator + "font" + File.separator + "data" + File.separator + resource.resFullName;

            holder.binding.chkSelect.setVisibility(View.VISIBLE);
            holder.binding.chkSelect.setChecked(resource.isSelected);
            holder.binding.tvFontName.setText(resource.resName + ".ttf");

            try {
                holder.binding.tvFontPreview.setTypeface(Typeface.createFromFile(fontPath));
                holder.binding.tvFontPreview.setText(Helper.getResString(R.string.design_manager_font_description_example_sentence));
            } catch (Exception ignored) {
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageFontListItemBinding binding = ManageFontListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MyViewHolder(binding);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ManageFontListItemBinding binding;

            public MyViewHolder(ManageFontListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.getRoot().setOnClickListener(view -> binding.chkSelect.setChecked(!binding.chkSelect.isChecked()));

                binding.chkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    int position = getLayoutPosition();
                    selectedPosition = position;
                    ProjectResourceBean resource = projectResourceBeans.get(position);
                    resource.isSelected = isChecked;
                    updateImportButtonVisibility();

                    new Handler().post(() -> notifyItemChanged(selectedPosition));
                });

            }
        }
    }
}
