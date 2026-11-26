package pro.sketchware.activities.settings;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import pro.sketchware.R;
import pro.sketchware.databinding.ActivityCodeShrinkingBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;

/**
 * Single place for code shrinking (ProGuard/R8), StringFog and build logging settings.
 */
public class CodeShrinkingActivity extends BaseAppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ActivityCodeShrinkingBinding binding;
    private ProguardHandler pg;
    private StringfogHandler stringfog;
    private String scId;
    private boolean updatingUi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeShrinkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        scId = getIntent().getStringExtra("sc_id");
        pg = new ProguardHandler(scId);
        stringfog = new StringfogHandler(scId);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.design_drawer_menu_proguard);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.shrinkerGroup.addOnButtonCheckedListener(this::onShrinkerChecked);
        binding.shrinkSwitch.setOnCheckedChangeListener(this);
        binding.obfuscateSwitch.setOnCheckedChangeListener(this);
        binding.optimizeSwitch.setOnCheckedChangeListener(this);
        binding.stringfogSwitch.setOnCheckedChangeListener(this);
        binding.debugSwitch.setOnCheckedChangeListener(this);
        binding.rulesRow.setOnClickListener(this);
        binding.fullModeRow.setOnClickListener(this);
        binding.shrinkRow.setOnClickListener(this);
        binding.obfuscateRow.setOnClickListener(this);
        binding.optimizeRow.setOnClickListener(this);
        binding.stringfogRow.setOnClickListener(this);
        binding.debugRow.setOnClickListener(this);

        updateUi();
    }

    private void onShrinkerChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (!isChecked || updatingUi) return;
        boolean enabled = checkedId != R.id.btn_disabled;
        boolean r8 = checkedId == R.id.btn_r8;
        pg.setProguardEnabled(enabled);
        pg.setR8Enabled(r8);
        updateUi();
    }

    private void updateSegments(int selectedId) {
        applySegment(binding.btnDisabled, selectedId == R.id.btn_disabled);
        applySegment(binding.btnProguard, selectedId == R.id.btn_proguard);
        applySegment(binding.btnR8, selectedId == R.id.btn_r8);
    }

    private void applySegment(MaterialButton button, boolean selected) {
        button.setBackgroundTintList(ColorStateList.valueOf(ThemeUtils.getColor(this,
                selected ? R.attr.colorSecondaryContainer : R.attr.colorSurface)));
        button.setTextColor(ThemeUtils.getColor(this,
                selected ? R.attr.colorOnSecondaryContainer : R.attr.colorOnSurface));
    }

    private void updateUi() {
        updatingUi = true;
        boolean enabled = pg.isShrinkingEnabled();
        boolean r8 = enabled && pg.isR8Enabled();
        int selectedMode = r8 ? R.id.btn_r8 : enabled ? R.id.btn_proguard : R.id.btn_disabled;
        binding.shrinkerGroup.check(selectedMode);
        updateSegments(selectedMode);
        binding.modeHint.setText(selectedMode == R.id.btn_r8 ? R.string.code_shrinking_mode_hint_r8
                : selectedMode == R.id.btn_proguard ? R.string.code_shrinking_mode_hint_proguard
                : R.string.code_shrinking_mode_hint_disabled);

        setRulesExpanded(pg.isShrinkEnabled());
        ArrayList<String> conflicts = pg.getConflictingDontRules();
        binding.rulesWarning.setVisibility(conflicts.isEmpty() ? View.GONE : View.VISIBLE);
        binding.rulesWarning.setText(getString(R.string.code_shrinking_rules_conflict,
                TextUtils.join(", ", conflicts)));

        int fullModeLibs = pg.getFullModeLibs().size();
        binding.fullModeTitle.setText(fullModeLibs == 0
                ? getString(R.string.common_word_full_mode)
                : getString(R.string.code_shrinking_full_mode_count, fullModeLibs));

        binding.optionsSection.setVisibility(enabled ? View.VISIBLE : View.GONE);
        binding.shrinkSwitch.setChecked(pg.isShrinkEnabled());
        binding.obfuscateSwitch.setChecked(pg.isObfuscateEnabled());
        binding.optimizeSwitch.setChecked(pg.isOptimizeEnabled());

        binding.stringfogSwitch.setChecked(stringfog.isStringfogEnabled());
        binding.debugSwitch.setChecked(pg.isDebugModeEnabled());
        updatingUi = false;
    }

    private void setRulesExpanded(boolean expanded) {
        View panel = binding.rulesPanel;
        int target = expanded ? View.VISIBLE : View.GONE;
        if (panel.getVisibility() == target) return;
        TransitionManager.beginDelayedTransition((ViewGroup) panel.getParent(), new AutoTransition());
        panel.setVisibility(target);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.rulesRow) {
            Intent intent = new Intent(this, SrcCodeEditor.class);
            intent.putExtra("title", "proguard-rules.pro");
            intent.putExtra("content", pg.getCustomProguardRules());
            startActivity(intent);
        } else if (v == binding.fullModeRow) {
            showFullModeDialog();
        } else if (v == binding.shrinkRow) {
            binding.shrinkSwitch.toggle();
        } else if (v == binding.obfuscateRow) {
            binding.obfuscateSwitch.toggle();
        } else if (v == binding.optimizeRow) {
            binding.optimizeSwitch.toggle();
        } else if (v == binding.stringfogRow) {
            binding.stringfogSwitch.toggle();
        } else if (v == binding.debugRow) {
            binding.debugSwitch.toggle();
        }
    }

    private void showFullModeDialog() {
        ManageLocalLibrary mll = new ManageLocalLibrary(scId);
        if (mll.list.isEmpty()) {
            SketchwareUtil.toast(getString(R.string.code_shrinking_full_mode_empty), Toast.LENGTH_LONG);
            return;
        }
        String[] libraries = new String[mll.list.size()];
        boolean[] checked = new boolean[mll.list.size()];

        for (int i = 0; i < mll.list.size(); i++) {
            Object name = mll.list.get(i).get("name");
            if (name instanceof String stringName) {
                libraries[i] = stringName;
                checked[i] = pg.libIsProguardFMEnabled(stringName);
            } else {
                libraries[i] = "(broken library configuration)";
            }
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.title_select_local_libraries)
                .setMultiChoiceItems(libraries, checked,
                        (dialog, which, isChecked) -> checked[which] = isChecked)
                .setPositiveButton(R.string.common_word_save, (dialog, which) -> {
                    ArrayList<String> selected = new ArrayList<>();
                    for (int i = 0; i < libraries.length; i++) {
                        if (checked[i]) selected.add(libraries[i]);
                    }
                    pg.setProguardFMLibs(selected);
                    updateUi();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if (updatingUi) return;
        if (button == binding.shrinkSwitch) {
            pg.setShrinkEnabled(isChecked);
            updateUi();
        } else if (button == binding.obfuscateSwitch) {
            pg.setObfuscateEnabled(isChecked);
        } else if (button == binding.optimizeSwitch) {
            pg.setOptimizeEnabled(isChecked);
        } else if (button == binding.stringfogSwitch) {
            stringfog.setStringfogEnabled(isChecked);
        } else if (button == binding.debugSwitch) {
            pg.setDebugModeEnabled(isChecked);
        }
    }
}
