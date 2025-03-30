package com.besome.sketch.editor.manage.library.material3;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

        String sc_id = getIntent().getStringExtra("sc_id");
        boolean isAppCombatEnabled = getIntent().getBooleanExtra("is_app_compat_enabled", false);
        material3LibraryManager = new Material3LibraryManager(sc_id, isAppCombatEnabled);
        if (!isAppCombatEnabled) {
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
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return (buttonView, isChecked) -> {
            boolean isLibEnabled = binding.libSwitch.isChecked();
            boolean isDynamicColorsEnabled = binding.dynamicColorsSwitch.isChecked();
            binding.dynamicColorsSwitch.setEnabled(isLibEnabled);

            Material3LibraryManager.ConfigData configData = new Material3LibraryManager.ConfigData(isLibEnabled, isDynamicColorsEnabled);
            material3LibraryManager.setConfigData(configData);
        };
    }
}
