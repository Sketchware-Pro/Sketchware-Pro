package com.besome.sketch.editor.manage.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.SelectableButtonBar;
import com.sketchware.remod.databinding.ManageScreenActivityAddTempBinding;

import java.util.ArrayList;

import a.a.a.YB;
import a.a.a.bB;
import a.a.a.rq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

public class AddViewActivity extends BaseDialogActivity {

    private YB nameValidator;
    private boolean featureStatusBar,
            featureToolbar,
            featureFab,
            featureDrawer;
    private int requestCode;
    private ProjectFileBean projectFileBean;
    private String P;
    private ArrayList<FeatureItem> featureItems;
    private FeaturesAdapter featuresAdapter;

    private ManageScreenActivityAddTempBinding binding;

    private void a(FeatureItem featureItem) {
        int var2 = featureItem.type;
        if (var2 != 0) {
            if (var2 != 1) {
                if (var2 != 2) {
                    if (var2 == 3) {
                        if (featureItem.isEnabled) {
                            resetTranslationY(binding.previewFab);
                        } else {
                            slideOutVertically(binding.previewFab);
                        }
                    }
                } else if (featureItem.isEnabled) {
                    resetTranslationX(binding.previewDrawer);
                } else {
                    slideOutHorizontally(binding.previewDrawer);
                }
            } else if (featureItem.isEnabled) {
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
        } else if (featureItem.isEnabled) {
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

    private boolean isValid(YB validator) {
        return validator.b();
    }

    private void slideOutHorizontally(View view) {
        view.animate().translationX((float) (-view.getMeasuredWidth())).start();
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
        featureToolbar = (option & 1) == 1;
        featureStatusBar = (option & 2) != 2;
        featureFab = (option & 8) == 8;
        featureDrawer = (option & 4) == 4;
    }

    private void initializeItems() {
        featureItems = new ArrayList<>();
        featureItems.add(new FeatureItem(0, 2131165864, "StatusBar", featureStatusBar));
        featureItems.add(new FeatureItem(1, 2131165872, "Toolbar", featureToolbar));
        featureItems.add(new FeatureItem(2, 2131165737, "Drawer", featureDrawer));
        featureItems.add(new FeatureItem(3, 2131165608, "FAB", featureFab));
        featuresAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 276 && resultCode == -1) {
            ProjectFileBean presetData = data.getParcelableExtra("preset_data");
            P = presetData.presetName;
            initItem(presetData.options);
            initializeItems();
        }
    }

    @Override
    @SuppressLint("ResourceType")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageScreenActivityAddTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        e(xB.b().a(getApplicationContext(), 2131625299));
        Intent intent1 = getIntent();
        ArrayList<String> screenNames = intent1.getStringArrayListExtra("screen_names");
        requestCode = intent1.getIntExtra("request_code", 264);
        projectFileBean = intent1.getParcelableExtra("project_file");
        if (projectFileBean != null) {
            e(xB.b().a(getApplicationContext(), 2131625300));
        }

        binding.tvWarning.setVisibility(8);
        binding.tvWarning.setText(xB.b().a(getApplicationContext(), 2131625295));
        binding.tiName.setHint(xB.b().a(this, 2131625293));
        binding.edName.setPrivateImeOptions("defaultInputmode=english;");
        featuresAdapter = new FeaturesAdapter();
        binding.featureTypes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        binding.featureTypes.setHasFixedSize(true);
        binding.featureTypes.setAdapter(featuresAdapter);
        binding.tvScreenOrientation.setText(xB.b().a(getApplicationContext(), 2131625303));
        binding.tvKeyboard.setText(xB.b().a(getApplicationContext(), 2131625302));
        binding.addViewTypeSelector.a(0, "Activity");
        binding.addViewTypeSelector.a(1, "Fragment");
        binding.addViewTypeSelector.a(2, "DialogFragment");
        binding.addViewTypeSelector.a();
        binding.btnbarOrientation.a(0, "Portrait");
        binding.btnbarOrientation.a(1, "Landscape");
        binding.btnbarOrientation.a(2, "Both");
        binding.btnbarOrientation.a();
        binding.btnbarKeyboard.a(0, "Unspecified");
        binding.btnbarKeyboard.a(1, "Visible");
        binding.btnbarKeyboard.a(2, "Hidden");
        binding.btnbarKeyboard.a();
        binding.btnbarKeyboard.setListener(i -> {
            if (0 == i || 1 == i) {
                resetTranslationY(binding.activityPreview);
            } else if (2 == i) {
                binding.activityPreview.animate().translationY((float) binding.imgKeyboard.getMeasuredHeight()).start();
            }
        });
        d(xB.b().a(getApplicationContext(), 2131624970));
        b(xB.b().a(getApplicationContext(), 2131624974));

        super.r.setOnClickListener(v -> {
            int options = 1;
            if (265 == requestCode) {
                projectFileBean.orientation = binding.btnbarOrientation.getSelectedItemKey();
                projectFileBean.keyboardSetting = binding.btnbarKeyboard.getSelectedItemKey();
                if (!featureToolbar) {
                    options = 0;
                }
                if (!featureStatusBar) {
                    options = options | 2;
                }
                if (featureFab) {
                    options = options | 8;
                }
                if (featureDrawer) {
                    options = options | 4;
                }
                projectFileBean.options = options;
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625279, new Object[0]), 0).show();
                finish();
            } else if (isValid(nameValidator)) {
                String var4 = binding.edName.getText().toString() + getSuffix(binding.addViewTypeSelector);
                ProjectFileBean projectFileBean = new ProjectFileBean(0, var4, binding.btnbarOrientation.getSelectedItemKey(), binding.btnbarKeyboard.getSelectedItemKey(), featureToolbar, !featureStatusBar, featureFab, featureDrawer);
                Intent intent = new Intent();
                intent.putExtra("project_file", projectFileBean);
                if (P != null) {
                    intent.putExtra("preset_views", getPresetData(P));
                }
                setResult(-1, intent);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625276, new Object[0]), 0).show();
                finish();
            }

        });
        super.s.setOnClickListener(v -> {
            setResult(0);
            finish();
        });
        if (requestCode == 265) {
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, new ArrayList<>(), projectFileBean.fileName);
            binding.edName.setText(projectFileBean.fileName);
            binding.edName.setEnabled(false);
            binding.edName.setBackgroundResource(2131034318);
            initItem(projectFileBean.options);
            binding.addViewTypeSelectorLayout.setVisibility(8);
            binding.btnbarOrientation.setSelectedItemByKey(projectFileBean.orientation);
            binding.btnbarKeyboard.setSelectedItemByKey(projectFileBean.keyboardSetting);
            super.r.setText(xB.b().a(getApplicationContext(), 2131625031).toUpperCase());
        } else {
            featureToolbar = true;
            featureStatusBar = true;
            nameValidator = new YB(getApplicationContext(), binding.tiName, uq.b, screenNames);
        }
        initializeItems();
    }

    private String getSuffix(SelectableButtonBar buttonBar) {
        return switch (buttonBar.getSelectedItemKey()) {
            case 1 -> "_fragment";
            case 2 -> "_dialog_fragment";
            default -> "";
        };
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
                binding.tvWarning.setVisibility(View.VISIBLE);
            }

            a(featureItem);
            d = false;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
            @SuppressLint("ResourceType") View var3 = wB.a(var1.getContext(), 2131427556);
            var3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new ViewHolder(var3);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView t;
            public TextView u;
            public CheckBox v;

            @SuppressLint("ResourceType")
            public ViewHolder(View var2) {
                super(var2);
                t = var2.findViewById(2131231151);
                u = var2.findViewById(2131232055);
                v = var2.findViewById(2131230883);
                v.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (!d) {
                        layoutPosition = getLayoutPosition();
                        FeatureItem item = featureItems.get(layoutPosition);
                        binding.tvWarning.setVisibility(8);
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