package com.besome.sketch.editor.manage.library.material3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;

import com.besome.sketch.beans.ProjectLibraryBean;
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

    // REVERTED: The ActivityResultLauncher has been removed.

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
        // We set the navigation click listener on the toolbar itself.
        binding.toolbar.setNavigationOnClickListener(v -> handleOnBackPressed());
        enableEdgeToEdgeNoContrast();
    }

    private void checkAppCompatDependency() {
        if (!material3LibraryManager.isAppCompatEnabled()) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_mtrl_warning)
                    .setTitle("AppCompat is disabled!")
                    .setMessage("To use the Material You (M3) library, you must first enable AppCompat & Design from the Library Manager.")
                    // REVERTED: Dialog now only has an "OK" button that finishes the activity.
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }

    private void loadInitialState() {
        boolean isMaterial3Enabled = material3LibraryManager.isMaterial3Enabled();
        binding.switchEnableM3.setChecked(isMaterial3Enabled);
        binding.switchDynamicColors.setChecked(material3LibraryManager.isDynamicColorsEnabled());
        
        switch (material3LibraryManager.getTheme()) {
            case "Light" -> binding.toggleGroupTheme.check(binding.selectLight.getId());
            case "Dark" -> binding.toggleGroupTheme.check(binding.selectDark.getId());
            default -> binding.toggleGroupTheme.check(binding.selectDayNight.getId());
        }

        updateConfigurationViews(isMaterial3Enabled);
    }

    private void setupListeners() {
        binding.switchEnableM3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateConfigurationViews(isChecked);
        });
        
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
        
        if (checkedId == binding.selectLight.getId()) {
            theme = "Light";
        } else if (checkedId == binding.selectDark.getId()) {
            theme = "Dark";
        } else {
            theme = "DayNight";
        }
        configurations.put("theme", theme);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("compat", libraryBean);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    
    // This is the method from the OnBackPressedCallback.
    // We call it directly from the toolbar's navigation listener now.
    private void handleOnBackPressed() {
        saveStateAndFinish();
    }
}