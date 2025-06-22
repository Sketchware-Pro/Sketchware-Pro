package com.besome.sketch.editor.manage.library.material3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.compat.ManageCompatActivity;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Map;
import java.util.Objects;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryMaterial3Binding;

public class Material3LibraryActivity extends BaseAppCompatActivity {

    private ManageLibraryMaterial3Binding binding;
    private Material3LibraryManager material3LibraryManager;
    private ProjectLibraryBean libraryBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageLibraryMaterial3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initialize();
    }

    private void initialize() {
        libraryBean = getIntent().getParcelableExtra("compat");
        Objects.requireNonNull(libraryBean, "ProjectLibraryBean cannot be null.");
        material3LibraryManager = new Material3LibraryManager(libraryBean);

        setupToolbar();
        checkAppCompatDependency();
        loadInitialState();
        setupListeners();
        setupOnBackPressed();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        enableEdgeToEdgeNoContrast();
    }

    private void checkAppCompatDependency() {
        if (!material3LibraryManager.isAppCompatEnabled()) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_mtrl_warning)
                    .setTitle("AppCompat is disabled!")
                    .setMessage("To use the Material You (M3) library, you must first enable AppCompat & Design from the Library Manager.")
                    .setPositiveButton("Enable AppCompat", (dialog, which) -> {
                        Intent intent = new Intent(getApplicationContext(), ManageCompatActivity.class);
                        intent.putExtra("compat", libraryBean);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }

    private void loadInitialState() {
        boolean isMaterial3Enabled = material3LibraryManager.isMaterial3Enabled();
        binding.switchEnableM3.setChecked(isMaterial3Enabled);
        binding.switchDynamicColors.setChecked(material3LibraryManager.isDynamicColorsEnabled());

        switch (material3LibraryManager.getTheme()) {
            case "Light" -> binding.selectLight.setChecked(true);
            case "Dark" -> binding.selectDark.setChecked(true);
            default -> binding.selectDayNight.setChecked(true);
        }

        updateConfigurationViews(isMaterial3Enabled);
    }

    private void setupListeners() {
        binding.switchEnableM3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateConfigurationViews(isChecked);
        });

        // Make the entire row clickable to toggle the switch
        binding.layoutEnableM3.setOnClickListener(v -> binding.switchEnableM3.toggle());
        binding.layoutEnableDynamicColors.setOnClickListener(v -> {
            if (binding.switchEnableM3.isChecked()) {
                binding.switchDynamicColors.toggle();
            }
        });
    }

    private void updateConfigurationViews(boolean isEnabled) {
        binding.layoutConfiguration.setAlpha(isEnabled ? 1.0f : 0.5f);
        setViewAndChildrenEnabled(binding.layoutConfiguration, isEnabled);
    }

    private void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    private void setupOnBackPressed() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                saveStateAndFinish();
            }
        });
    }

    private void saveStateAndFinish() {
        Map<String, Object> configurations = libraryBean.configurations;

        configurations.put("material3", binding.switchEnableM3.isChecked());
        configurations.put("dynamic_colors", binding.switchDynamicColors.isChecked());

        String theme;
        int checkedId = binding.toggleGroupTheme.getCheckedButtonId();
        if (checkedId == R.id.select_light) {
            theme = "Light";
        } else if (checkedId == R.id.select_dark) {
            theme = "Dark";
        } else {
            theme = "DayNight";
        }
        configurations.put("theme", theme);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("compat", libraryBean);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}