package com.besome.sketch.editor.manage.font;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import a.a.a.jC;
import a.a.a.oB;
import a.a.a.qA;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.FrManageFontListBinding;
import pro.sketchware.databinding.ManageFontBinding;
import pro.sketchware.databinding.ManageFontListItemBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ImportFontFragment extends qA {

    public String sc_id;
    public boolean isSelecting = false;
    public String dirPath = "";
    public oB oB;
    public ArrayList<ProjectResourceBean> projectResourceBeans;
    private FrManageFontListBinding binding;
    private fontAdapter adapter;
    private ManageFontBinding actBinding;

    public String getResourceFilePath(ProjectResourceBean resourceBean) {
        String resFullName = resourceBean.resFullName;
        resFullName = resFullName.substring(resFullName.lastIndexOf("."));
        return dirPath +
                File.separator +
                resourceBean.resName +
                resFullName;
    }

    public String getFilePath(String resFullName) {
        return dirPath +
                File.separator +
                resFullName;
    }

    public void importFont(String sourcePath, String destPath) {
        FileUtil.copyFile(sourcePath, destPath);
    }

    public void handleResourceImport(ArrayList<ProjectResourceBean> resourceBeans) {
        ArrayList<ProjectResourceBean> newResourceBeans = new ArrayList<>();
        ArrayList<String> unavailableResourceNames = new ArrayList<>();

        for (ProjectResourceBean resourceBean : resourceBeans) {
            String resourceName = resourceBean.resName;
            if (isResourceNameExist(resourceName)) {
                unavailableResourceNames.add(resourceName);
            } else {
                ProjectResourceBean newResource = new ProjectResourceBean(
                        ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                        resourceName,
                        resourceBean.resFullName
                );
                newResource.isNew = true;
                newResourceBeans.add(newResource);
            }
        }

        if (!unavailableResourceNames.isEmpty()) {
            String unavailableMessage = Helper.getResString(R.string.common_message_name_unavailable);
            String unavailableList = String.join(", ", unavailableResourceNames);
            String message = unavailableMessage + "\n[" + unavailableList + "]";
            SketchwareUtil.toast(message);
        } else {
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_import_complete));
            projectResourceBeans.addAll(newResourceBeans);
            adapter.notifyDataSetChanged();
        }

        toggleEmptyStateVisibility();
    }

    public void setSelectingMode(boolean state) {
        isSelecting = state;
        requireActivity().invalidateOptionsMenu();
        ((ManageFontActivity) requireActivity()).changeFabState(!state);

        actBinding.layoutBtnGroup.animate().translationY(isSelecting ? 0F : 400F).setDuration(200L).start();
        actBinding.layoutBtnGroup.setVisibility(isSelecting ? View.VISIBLE : View.GONE);

        if (!isSelecting) {
            projectResourceBeans.forEach(bean -> bean.isSelected = false);
        }

        adapter.notifyDataSetChanged();
    }

    public boolean isResourceNameExist(String resourceName) {
        return projectResourceBeans.stream()
                .anyMatch(bean -> bean.resName.equals(resourceName));
    }

    public final ArrayList<String> getResourceNames() {
        return projectResourceBeans.stream()
                .map(bean -> bean.resName)
                .collect(Collectors.toCollection(() -> {
                    ArrayList<String> list = new ArrayList<>();
                    list.add("app_icon");
                    return list;
                }));
    }

    public ArrayList<ProjectResourceBean> getProjectResourceBeans() {
        return projectResourceBeans;
    }

    public void processResources() {
        if (projectResourceBeans == null) {
            return;
        }

        for (ProjectResourceBean resourceBean : projectResourceBeans) {
            if (resourceBean.isNew) {
                try {
                    importFont(resourceBean.resFullName, getResourceFilePath(resourceBean));
                    oB.c(resourceBean.resFullName);
                } catch (Exception ignored) {
                }
            }
        }

        for (int i = 0; i < projectResourceBeans.size(); i++) {
            ProjectResourceBean resourceBean = projectResourceBeans.get(i);
            if (resourceBean.isNew) {
                String fileExtension = resourceBean.resFullName.substring(resourceBean.resFullName.lastIndexOf("."));
                String newFileName = resourceBean.resName + fileExtension;
                resourceBean = new ProjectResourceBean(
                        ProjectResourceBean.PROJECT_RES_TYPE_FILE,
                        resourceBean.resName,
                        newFileName
                );
                projectResourceBeans.set(i, resourceBean);
            }
        }

        jC.d(sc_id).a(projectResourceBeans);
        jC.d(sc_id).y();
        jC.a(sc_id).a(jC.d(sc_id));
        jC.a(sc_id).k();
    }

    public final void toggleEmptyStateVisibility() {
        if (projectResourceBeans.isEmpty()) {
            binding.tvGuide.setVisibility(View.VISIBLE);
            binding.fontList.setVisibility(View.GONE);
        } else {
            binding.fontList.setVisibility(View.VISIBLE);
            binding.tvGuide.setVisibility(View.GONE);
        }

    }

    public void toFontActivity() {
        Intent intent = new Intent(getContext(), AddFontActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putStringArrayListExtra("font_names", getResourceNames());
        startActivityForResult(intent, 271);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        oB = new oB();
        oB.f(dirPath);
        projectResourceBeans = new ArrayList<>();
        if (bundle == null) {
            sc_id = requireActivity().getIntent().getStringExtra("sc_id");
            dirPath = jC.d(sc_id).j();
            ArrayList<ProjectResourceBean> resourceBeans = jC.d(sc_id).d;
            if (resourceBeans != null) {
                projectResourceBeans.addAll(resourceBeans);
            }
        } else {
            sc_id = bundle.getString("sc_id");
            dirPath = bundle.getString("dir_path");
            projectResourceBeans = bundle.getParcelableArrayList("fonts");
        }

        adapter.notifyDataSetChanged();
        toggleEmptyStateVisibility();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        ProjectResourceBean resourceBean;
        if (requestCode == 271 && resultCode == Activity.RESULT_OK) {
            resourceBean = intent.getParcelableExtra("resource_bean");
            projectResourceBeans.add(resourceBean);
            adapter.notifyDataSetChanged();
            toggleEmptyStateVisibility();
            ((ManageFontActivity) requireActivity()).collectionFontsFragment.loadProjectResources();
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_add_complete));
        } else if (requestCode == 272 && resultCode == Activity.RESULT_OK) {
            resourceBean = intent.getParcelableExtra("resource_bean");
            projectResourceBeans.set(adapter.selectedPosition, resourceBean);
            adapter.notifyDataSetChanged();
            toggleEmptyStateVisibility();
            ((ManageFontActivity) requireActivity()).collectionFontsFragment.loadProjectResources();
            SketchwareUtil.toast(Helper.getResString(R.string.design_manager_message_edit_complete));
        }

    }

    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.manage_font_menu, menu);
        menu.findItem(R.id.menu_font_delete).setVisible(!isSelecting);
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup root, Bundle bundle) {

        binding = FrManageFontListBinding.inflate(layoutInflater, root, false);

        setHasOptionsMenu(true);

        actBinding = ((ManageFontActivity) requireActivity()).binding;
        actBinding.btnCancel.setOnClickListener(view -> setSelectingMode(false));
        actBinding.btnDelete.setOnClickListener(view -> {
            if (isSelecting) {

                for (int i = projectResourceBeans.size() - 1; i >= 0; i--) {
                    if (projectResourceBeans.get(i).isSelected) {
                        projectResourceBeans.remove(i);
                    }
                }

                setSelectingMode(false);
                toggleEmptyStateVisibility();
                SketchwareUtil.toast(Helper.getResString(R.string.common_message_complete_delete));
                ((ManageFontActivity) requireActivity()).changeFabState(true);
            }
        });

        binding.fontList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new fontAdapter();
        binding.fontList.setAdapter(adapter);

        binding.fontList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 2) {
                    if (actBinding.fab.isEnabled()) {
                        actBinding.fab.hide();
                    }
                } else if (dy < -2) {
                    if (actBinding.fab.isEnabled()) {
                        actBinding.fab.show();
                    }
                }
            }
        });

        actBinding.fab.setVisibility(View.VISIBLE);
        actBinding.fab.setOnClickListener(view -> {
            setSelectingMode(false);
            toFontActivity();
        });

        return binding.getRoot();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_font_delete) {
            setSelectingMode(!isSelecting);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", sc_id);
        bundle.putString("dir_path", dirPath);
        bundle.putParcelableArrayList("fonts", projectResourceBeans);
        super.onSaveInstanceState(bundle);
    }

    public class fontAdapter extends RecyclerView.Adapter<fontAdapter.ViewHolder> {
        public int selectedPosition;

        public fontAdapter() {
            selectedPosition = -1;
        }

        @Override
        public int getItemCount() {
            return projectResourceBeans.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean resource = projectResourceBeans.get(position);

            if (isSelecting) {
                holder.binding.imgFont.setVisibility(View.GONE);
                holder.binding.deleteImgContainer.setVisibility(View.VISIBLE);
            } else {
                holder.binding.imgFont.setVisibility(View.VISIBLE);
                holder.binding.deleteImgContainer.setVisibility(View.GONE);
            }

            if (resource.isSelected) {
                holder.binding.imgDelete.setImageResource(R.drawable.ic_checkmark_green_48dp);
            } else {
                holder.binding.imgDelete.setImageResource(R.drawable.ic_trashcan_white_48dp);
            }

            holder.binding.chkSelect.setChecked(resource.isSelected);

            holder.binding.tvFontName.setText(resource.resName + ".ttf");

            String fontPath;
            if (resource.isNew) {
                fontPath = resource.resFullName;
            } else {
                fontPath = getResourceFilePath(resource);
            }

            try {
                holder.binding.tvFontPreview.setTypeface(Typeface.createFromFile(fontPath));
            } catch (Exception ignored) {
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ManageFontListItemBinding binding = ManageFontListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ManageFontListItemBinding binding;

            public ViewHolder(ManageFontListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.tvFontPreview.setText(Helper.getResString(R.string.common_word_preview));
                binding.chkSelect.setVisibility(View.GONE);

                binding.layoutItem.setOnClickListener(view -> {
                    selectedPosition = getLayoutPosition();

                    if (isSelecting) {
                        boolean newState = !binding.chkSelect.isChecked();
                        binding.chkSelect.setChecked(newState);
                        projectResourceBeans.get(selectedPosition).isSelected = newState;
                        notifyItemChanged(selectedPosition);
                    } else {
                        importFont(projectResourceBeans.get(getLayoutPosition()).resFullName, getResourceFilePath(projectResourceBeans.get(getLayoutPosition())));
                    }
                });

                binding.layoutItem.setOnLongClickListener(view -> {
                    setSelectingMode(true);
                    selectedPosition = getLayoutPosition();

                    boolean newState = !binding.chkSelect.isChecked();
                    binding.chkSelect.setChecked(newState);
                    projectResourceBeans.get(selectedPosition).isSelected = newState;

                    return false;
                });
            }
        }
    }

}
