package com.besome.sketch.editor.manage.library.material3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryMaterial3Binding;

public class Material3LibraryActivity extends AppCompatActivity {

    private ManageLibraryMaterial3Binding binding;
    private Material3LibraryManager material3LibraryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageLibraryMaterial3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
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
            binding.dynamicColorsSwitch.setEnabled(false);
        }

        binding.libSwitch.setOnCheckedChangeListener(getOnCheckedChangeListener());
        binding.dynamicColorsSwitch.setOnCheckedChangeListener(getOnCheckedChangeListener());

        binding.layoutSwitchLib.setOnClickListener(view -> binding.libSwitch.setChecked(!binding.libSwitch.isChecked()));
        binding.layoutSwitchDynamicColors.setOnClickListener(view -> {
            if (binding.libSwitch.isChecked())
                binding.dynamicColorsSwitch.setChecked(!binding.dynamicColorsSwitch.isChecked());
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("compat", material3LibraryManager.getAppCombatLibraryBean());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (buttonView, isChecked) -> {
            boolean isMaterial3Enabled = binding.libSwitch.isChecked();
            boolean isDynamicColorsEnabled = binding.dynamicColorsSwitch.isChecked();
            binding.dynamicColorsSwitch.setEnabled(isMaterial3Enabled);

            material3LibraryManager.getAppCombatLibraryBean().material3Configurations.put("material3", isMaterial3Enabled);
            material3LibraryManager.getAppCombatLibraryBean().material3Configurations.put("dynamic_colors", isDynamicColorsEnabled);
        };
    }
}
