package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageScreenActivityAddTempBinding;

public class AddViewActivity extends BaseAppCompatActivity {

    private YB nameValidator;
    private boolean featureStatusBar, featureToolbar, featureFab, featureDrawer;
    private int requestCode;
    private ProjectFileBean projectFileBean;
    private String P;
    private ArrayList<FeatureItem> featureItems;
    private FeaturesAdapter featuresAdapter;

    private ManageScreenActivityAddTempBinding binding;

    private void a(FeatureItem featureItem) {
        int type = featureItem.type;
        switch (type) {
            case 0 -> {
                if (featureItem.isEnabled) {
                    resetTranslationY(binding.previewStatusbar);
                    if (featureToolbar) {
                        resetTranslationY(binding.previewToolbar);
                    }
                } else {
                    slideInVertically(binding.previewStatusbar);
                    if (featureToolbar) {
                        binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
                    } else {
                        slideOutPreviewToolbar();
                    }
                }
            }
            case 1 -> {
                if (featureItem.isEnabled) {
                    if (!featureStatusBar) {
                        binding.previewToolbar.animate().translationY((float) (-binding.previewStatusbar.getMeasuredHeight())).start();
                    } else {
                        resetTranslationY(binding.previewToolbar);
                    }
                } else if (!featureStatusBar) {
                    slideOutPreviewToolbar();
                } else {
                    slideInVertically(binding.previewToolbar);
                }
            }
            case 2 -> {
                if (featureItem.isEnabled) {
                    resetTranslationX(binding.previewDrawer);
                } else {
                    slideOutHorizontally(binding.previewDrawer, "left");
                }
            }
            case 3 -> {
                if (featureItem.isEnabled) {
                    resetTranslationX(binding.previewFab);
                } else {
                    slideOutHorizontally(binding.previewFab, "right");
                }
            }
        }
    }

    private boolean isValid(YB validator) {
        return validator.b();
    }

    private void slideOutHorizontally(View view, String direction) {
        if ("left".equals(direction)) {
            view.animate().translationX((float) (-view.getMeasuredWidth())).start();
        } else {
            view.animate().translationX((float) view.getMeasuredWidth()).start();
        }
    }

    private void slideOutVertically(View view) {
        view.animate().translationY((float) view.getMeasuredHeight()).start();
    }

    private void slideOutPreviewToolbar() {
        binding.previewToolbar.animate().translationY((float) (-(binding.previewStatusbar.getMeasuredHeight() + binding.previewToolbar.getMeasuredHeight()))).start();
    }

    private void slideInVertically(View view) {
        view.animate().translationY((float) (-view.getMeasuredHeight())).start();
    }

    private void disableDrawer() {
        for (int i = 0; i < featureItems.size(); i++) {
            FeatureItem item = featureItems.get(i);
            if (item.type == 2) {
                item.isEnabled = false;
                featuresAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void enableToolbar() {
        for (int i = 0; i < featureItems.size(); i++) {
            FeatureItem item = featureItems.get(i);
            if (item.type == 1) {
                item.isEnabled = true;
                featuresAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void resetTranslationX(View view) {
        view.animate().translationX(0.0F).start();
    }

    private void resetTranslationY(View view) {
        view.animate().translationY(0.0F).start();
    }

    private ArrayList<ViewBean> getPresetData(String var1) {
        return rq.f(var1);
    }

    private void initItem(int option) {
        featureToolbar = (option & ProjectFileBean.OPTION_ACTIVITY_TOOLBAR) == ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
        featureStatusBar = (option & ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN) != ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN;
        featureFab = (option & ProjectFileBean.OPTION_ACTIVITY_FAB) == ProjectFileBean.OPTION_ACTIVITY_FAB;
        featureDrawer = (option & ProjectFileBean.OPTION_ACTIVITY_DRAWER) == ProjectFileBean.OPTION_ACTIVITY_DRAWER;
    }

    private void initializeItems() {
        featureItems = new ArrayList<>();
        featureItems.add(new FeatureItem(0, R.drawable.ic_statusbar_color_48dp, "StatusBar", featureStatusBar));
        featureItems.add(new FeatureItem(1, R.drawable.ic_toolbar_color_48dp, "Toolbar", featureToolbar));
        featureItems.add(new FeatureItem(2, R.drawable.ic_drawer_color_48dp, "Drawer", featureDrawer));
        featureItems.add(new FeatureItem(3, R.drawable.fab_color, "FAB", featureFab));
        featuresAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 276 && resultCode == RESULT_OK) {
            ProjectFileBean presetData = data.getParcelableExtra("preset_data");
            P = presetData.presetName;
            initItem(presetData.options);
            initializeItems();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageScreenActivityAddTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitle("Create new");
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        Intent intent1 = getIntent();
        ArrayList<String> screenNames = intent1.getStringArrayListExtra("screen_names");
        requestCode = intent1.getIntExtra("request_code", 264);
        projectFileBean = intent1.getParcelableExtra("project_file");
        if (projectFileBean != null) {
            binding.toolbar.setTitle("Edit " + projectFileBean.fileName);
        }

        featuresAdapter = new FeaturesAdapter();
        binding.featureTypes.setAdapter(featuresAdapter);

        binding.keyboardSettingsSelector.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.select_visible || checkedId == R.id.select_unspecified) {
                    resetTranslationY(binding.imgKeyboard);
                } else {
                    slideOutVertically(binding.imgKeyboard);
                }
            }
        });

        binding.btnSave.setOnClickListener(v -> {
            int options = ProjectFileBean.OPTION_ACTIVITY_TOOLBAR;
            if (265 == requestCode) {
                projectFileBean.orientation = getSelectedButtonIndex(binding.screenOrientationSelector);
                projectFileBean.keyboardSetting = getSelectedButtonIndex(binding.keyboardSettingsSelector);
                if (!featureToolbar) {
                    options = 0;
                }
                if (!featureStatusBar) {
                    options = options | ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN;
                }
                if (featureFab) {
                    options = options | ProjectFileBean.OPTION_ACTIVITY_FAB;
                }
                if (featureDrawer) {
                    options = options | ProjectFileBean.OPTION_ACTIVITY_DRAWER;
                }
                projectFileBean.options = options;
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                setResult(RESULT_OK, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete, new Object[0]), bB.TOAST_NORMAL).show();
                finish();
            } else if (isValid(nameValidator)) {
                String var4 = Helper.getText(binding.edName) + getSuffix(binding.viewTypeSelector);
                ProjectFileBean projectFileBean = new ProjectFileBean(ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY, var4, getSelectedButtonIndex(binding.screenOrientationSelector), getSelectedButtonIndex(binding.keyboardSettingsSelector), featureToolbar, !featureStatusBar, featureFab, featureDrawer);
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                if (P != null) {
                    intent.putExtra("preset_views", getPresetData(P));
                }
                setResult(RESULT_OK, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_add_complete, new Object[0]), bB.TOAST_NORMAL).show();
                finish();
            }

        });
        binding.btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        if (requestCode == 265) {
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, new ArrayList<>(), projectFileBean.fileName);
            binding.edName.setText(projectFileBean.fileName);
            binding.edName.setEnabled(false);
            binding.edName.setBackgroundResource(R.color.transparent);
            initItem(projectFileBean.options);
            binding.addViewTypeSelectorLayout.setVisibility(View.GONE);
            binding.screenOrientationSelector.check(binding.screenOrientationSelector.getChildAt(projectFileBean.orientation).getId());
            binding.keyboardSettingsSelector.check(binding.keyboardSettingsSelector.getChildAt(projectFileBean.keyboardSetting).getId());

            binding.imgKeyboard.post(() -> {
                if (binding.keyboardSettingsSelector.getCheckedButtonId() == R.id.select_hidden) {
                    slideOutVertically(binding.imgKeyboard);
                }
            });
        } else {
            featureToolbar = true;
            featureStatusBar = true;
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, screenNames);
        }
        initializeItems();
    }

    private String getSuffix(MaterialButtonToggleGroup toggleGroup) {
        return switch (getSelectedButtonIndex(toggleGroup)) {
            case 1 -> "_fragment";
            case 2 -> "_dialog_fragment";
            case 3 -> "_bottomdialog_fragment";
            default -> "";
        };
    }

    public int getSelectedButtonIndex(MaterialButtonToggleGroup toggleGroup) {
        for (int i = 0; i < toggleGroup.getChildCount(); i++) {
            var button = toggleGroup.getChildAt(i);
            if (toggleGroup.getCheckedButtonIds().contains(button.getId())) {
                return i;
            }
        }
        return -1;  // Return -1 if no button is selected
    }

    public void makeTransitionAnimation(LinearLayout linearLayout) {
        AutoTransition autoTransition = new android.transition.AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(linearLayout, autoTransition);
    }

    private static class FeatureItem {
        public int type;
        public int previewImg;
        public String name;
        public boolean isEnabled;

        public FeatureItem(int type, int previewImg, String name, boolean isEnabled) {
            this.type = type;
            this.previewImg = previewImg;
            this.name = name;
            this.isEnabled = isEnabled;
        }
    }

    public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.ViewHolder> {
        public int layoutPosition = -1;
        public boolean d;

        public FeaturesAdapter() {
        }

        @Override
        public int getItemCount() {
            return featureItems.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            d = true;
            FeatureItem featureItem = featureItems.get(position);
            viewHolder.t.setImageResource(featureItem.previewImg);
            viewHolder.u.setText(featureItem.name);
            viewHolder.v.setChecked(featureItem.isEnabled);
            switch (featureItem.type) {
                case 0 -> featureStatusBar = featureItem.isEnabled;
                case 1 -> featureToolbar = featureItem.isEnabled;
                case 2 -> featureDrawer = featureItem.isEnabled;
                case 3 -> featureFab = featureItem.isEnabled;
            }

            if (featureFab || featureDrawer) {
                makeTransitionAnimation(binding.parentLayout);
                binding.fabDrawerTipView.setVisibility(View.VISIBLE);
            }

            a(featureItem);
            d = false;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
            View var3 = wB.a(var1.getContext(), R.layout.manage_screen_activity_add_feature_item);
            var3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(var3);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView t;
            public TextView u;
            public CheckBox v;

            public ViewHolder(View var2) {
                super(var2);
                t = var2.findViewById(R.id.img_icon);
                u = var2.findViewById(R.id.tv_name);
                v = var2.findViewById(R.id.checkbox);
                v.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!d) {
                        layoutPosition = getLayoutPosition();
                        FeatureItem item = featureItems.get(layoutPosition);
                        item.isEnabled = isChecked;
                        if (item.type == 2 || item.isEnabled) {
                            enableToolbar();
                        } else if (item.type == 1 || !item.isEnabled) {
                            disableDrawer();
                        }
                        notifyItemChanged(layoutPosition);
                    }
                });
            }
        }
    }
}
