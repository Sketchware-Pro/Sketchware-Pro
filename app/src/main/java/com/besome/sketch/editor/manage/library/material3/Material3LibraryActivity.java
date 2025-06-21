package com.besome.sketch.editor.manage.library.material3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryMaterial3Binding;

public class Material3LibraryActivity extends BaseAppCompatActivity {

    private ManageLibraryMaterial3Binding binding;
    private Material3LibraryManager material3LibraryManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageLibraryMaterial3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        enableEdgeToEdgeNoContrast();
        initialize();
    }

    private void initialize() {
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        material3LibraryManager = new Material3LibraryManager((ProjectLibraryBean) Objects.requireNonNull(getIntent().getParcelableExtra("compat")));
        if (!material3LibraryManager.isAppCompatEnabled()) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_mtrl_warning)
                    .setTitle("AppCompat is disabled!")
                    .setMessage("Please enable AppCompat first to use this feature")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }

        binding.libSwitch.setChecked(material3LibraryManager.isMaterial3Enabled());
        binding.dynamicColorsSwitch.setChecked(material3LibraryManager.isDynamicColorsEnabled());

        if (!material3LibraryManager.isMaterial3Enabled()) {
            binding.toggleGroup.setEnabled(false);
            binding.dynamicColorsSwitch.setEnabled(false);
        }

        binding.libSwitch.setOnCheckedChangeListener(getOnCheckedChangeListener());

        binding.layoutSwitchLib.setOnClickListener(view -> binding.libSwitch.setChecked(!binding.libSwitch.isChecked()));
        binding.layoutSwitchDynamicColors.setOnClickListener(view -> {
            if (binding.libSwitch.isChecked())
                binding.dynamicColorsSwitch.setChecked(!binding.dynamicColorsSwitch.isChecked());
        });

        switch (material3LibraryManager.getTheme()) {
            case "DayNight" -> binding.selectDayNight.setChecked(true);
            case "Dark" -> binding.selectDark.setChecked(true);
            case "Light" -> binding.selectLight.setChecked(true);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                material3LibraryManager.getAppCombatLibraryBean().configurations.put("material3", binding.libSwitch.isChecked());
                material3LibraryManager.getAppCombatLibraryBean().configurations.put("dynamic_colors", binding.dynamicColorsSwitch.isChecked());

                if (binding.selectDayNight.isChecked()) {
                    material3LibraryManager.getAppCombatLibraryBean().configurations.put("theme", "DayNight");
                } else if (binding.selectLight.isChecked()) {
                    material3LibraryManager.getAppCombatLibraryBean().configurations.put("theme", "Light");
                } else if (binding.selectDark.isChecked()) {
                    material3LibraryManager.getAppCombatLibraryBean().configurations.put("theme", "Dark");
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("compat", material3LibraryManager.getAppCombatLibraryBean());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (buttonView, isChecked) -> {
            binding.toggleGroup.setEnabled(isChecked);
            binding.dynamicColorsSwitch.setEnabled(isChecked);
        };
    }
}
