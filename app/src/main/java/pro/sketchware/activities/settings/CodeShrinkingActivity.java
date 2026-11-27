package pro.sketchware.activities.settings;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import pro.sketchware.R;
import pro.sketchware.databinding.ActivityCodeShrinkingBinding;
import pro.sketchware.databinding.DialogFullModeLibrariesBinding;
import pro.sketchware.databinding.ItemFullModeLibraryBinding;
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
    private boolean initialUiUpdate = true;
    private final HashMap<View, ValueAnimator> heightAnimators = new HashMap<>();
    private final HashMap<View, Boolean> expandedStates = new HashMap<>();
    private final ExecutorService ioExecutor = Executors.newSingleThreadExecutor();
    private ConfigSnapshot snapshot;

    private static final class ConfigSnapshot {
        boolean shrinkingEnabled;
        boolean r8Enabled;
        boolean shrinkEnabled;
        boolean obfuscateEnabled;
        boolean optimizeEnabled;
        boolean stringfogEnabled;
        boolean debugEnabled;
        ArrayList<String> conflictingDontRules = new ArrayList<>();
        ArrayList<String> fullModeLibs = new ArrayList<>();
        String customRulesPath;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeShrinkingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        scId = getIntent().getStringExtra("sc_id");

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

        ioExecutor.execute(() -> {
            pg = new ProguardHandler(scId);
            stringfog = new StringfogHandler(scId);
            ConfigSnapshot loaded = loadSnapshot();
            runOnUiThread(() -> {
                if (isFinishing() || isDestroyed()) return;
                snapshot = loaded;
                renderSnapshot(loaded);
                initialUiUpdate = false;
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (snapshot != null) {
            refreshConflictingDontRules();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ioExecutor.shutdown();
    }

    private void refreshConflictingDontRules() {
        writeAndRefreshConflicts(() -> {});
    }

    private void writeAndRefreshConflicts(Runnable write) {
        ioExecutor.execute(() -> {
            write.run();
            ArrayList<String> conflicts = new ArrayList<>(pg.getConflictingDontRules());
            runOnUiThread(() -> {
                if (snapshot == null || isFinishing() || isDestroyed()) return;
                snapshot.conflictingDontRules = conflicts;
                renderSnapshot(snapshot);
            });
        });
    }

    private void onShrinkerChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (!isChecked || updatingUi || snapshot == null) return;
        boolean enabled = checkedId != R.id.btn_disabled;
        boolean r8 = checkedId == R.id.btn_r8;
        snapshot.shrinkingEnabled = enabled;
        snapshot.r8Enabled = r8;
        renderSnapshot(snapshot);
        ioExecutor.execute(() -> {
            pg.setProguardEnabled(enabled);
            pg.setR8Enabled(r8);
        });
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

    private ConfigSnapshot loadSnapshot() {
        ConfigSnapshot loaded = new ConfigSnapshot();
        loaded.shrinkingEnabled = pg.isShrinkingEnabled();
        loaded.r8Enabled = pg.isR8Enabled();
        loaded.shrinkEnabled = pg.isShrinkEnabled();
        loaded.obfuscateEnabled = pg.isObfuscateEnabled();
        loaded.optimizeEnabled = pg.isOptimizeEnabled();
        loaded.stringfogEnabled = stringfog.isStringfogEnabled();
        loaded.debugEnabled = pg.isDebugModeEnabled();
        loaded.conflictingDontRules = new ArrayList<>(pg.getConflictingDontRules());
        loaded.fullModeLibs = new ArrayList<>(pg.getFullModeLibs());
        loaded.customRulesPath = pg.getCustomProguardRules();
        return loaded;
    }

    private void renderSnapshot(ConfigSnapshot state) {
        updatingUi = true;
        boolean enabled = state.shrinkingEnabled;
        boolean r8 = enabled && state.r8Enabled;
        int selectedMode = r8 ? R.id.btn_r8 : enabled ? R.id.btn_proguard : R.id.btn_disabled;
        binding.shrinkerGroup.check(selectedMode);
        updateSegments(selectedMode);
        binding.modeHint.setText(selectedMode == R.id.btn_r8 ? R.string.code_shrinking_mode_hint_r8
                : selectedMode == R.id.btn_proguard ? R.string.code_shrinking_mode_hint_proguard
                : R.string.code_shrinking_mode_hint_disabled);

        setRulesExpanded(state.shrinkEnabled);
        binding.rulesWarning.setVisibility(state.conflictingDontRules.isEmpty() ? View.GONE : View.VISIBLE);
        binding.rulesWarning.setText(getString(R.string.code_shrinking_rules_conflict,
                TextUtils.join(", ", state.conflictingDontRules)));

        int fullModeLibs = state.fullModeLibs.size();
        binding.fullModeTitle.setText(fullModeLibs == 0
                ? getString(R.string.code_shrinking_full_mode_none)
                : getString(R.string.code_shrinking_full_mode_count, fullModeLibs));

        binding.optionsSection.setVisibility(enabled ? View.VISIBLE : View.GONE);
        setCheckedIfChanged(binding.shrinkSwitch, state.shrinkEnabled);
        setCheckedIfChanged(binding.obfuscateSwitch, state.obfuscateEnabled);
        setCheckedIfChanged(binding.optimizeSwitch, state.optimizeEnabled);

        setCheckedIfChanged(binding.stringfogSwitch, state.stringfogEnabled);
        setCheckedIfChanged(binding.debugSwitch, state.debugEnabled);
        updatingUi = false;
    }

    private static void setCheckedIfChanged(CompoundButton button, boolean checked) {
        if (button.isChecked() != checked) button.setChecked(checked);
    }

    private void setRulesExpanded(boolean expanded) {
        View panel = binding.rulesPanel;
        if (Boolean.valueOf(expanded).equals(expandedStates.get(panel))) return;
        expandedStates.put(panel, expanded);

        if (initialUiUpdate) {
            /* Initial state is applied without animation. */
            ValueAnimator previous = heightAnimators.remove(panel);
            if (previous != null) previous.cancel();
            panel.setVisibility(expanded ? View.VISIBLE : View.GONE);
            panel.getLayoutParams().height = expanded
                    ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
            panel.requestLayout();
            return;
        }

        setViewExpanded(panel, expanded);
    }

    private void setViewExpanded(View view, boolean expanded) {
        if (expanded) {
            int startHeight = view.getVisibility() == View.VISIBLE ? view.getHeight() : 0;
            view.setVisibility(View.VISIBLE);
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int target = view.getMeasuredHeight();
            view.getLayoutParams().height = startHeight;
            view.requestLayout();
            animateHeight(view, startHeight, target, true);
        } else {
            animateHeight(view, view.getHeight(), 0, false);
        }
    }

    private void animateHeight(View view, int from, int to, boolean expand) {
        // Remove before cancelling so the old animator's synchronous onAnimationEnd sees it is invalidated.
        ValueAnimator previous = heightAnimators.remove(view);
        if (previous != null) previous.cancel();
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        heightAnimators.put(view, animator);
        animator.setDuration(260);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (heightAnimators.get(view) != animation) return;
                heightAnimators.remove(view);
                view.getLayoutParams().height = expand
                        ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;
                if (!expand) view.setVisibility(View.GONE);
                view.requestLayout();
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        if (snapshot == null) return;
        if (v == binding.rulesRow) {
            Intent intent = new Intent(this, SrcCodeEditor.class);
            intent.putExtra("title", "proguard-rules.pro");
            intent.putExtra("content", snapshot.customRulesPath);
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

    private void updateFullModeListHeight(RecyclerView recycler, int itemCount) {
        float density = getResources().getDisplayMetrics().density;
        int rowHeight = Math.round(63 * density);
        int maxHeight = Math.min(Math.round(420 * density),
                getResources().getDisplayMetrics().heightPixels * 55 / 100);
        recycler.getLayoutParams().height = Math.min(
                Math.max(1, itemCount) * rowHeight, maxHeight);
        recycler.requestLayout();
    }

    private void showFullModeDialog() {
        ManageLocalLibrary mll = new ManageLocalLibrary(scId);
        if (mll.list.isEmpty()) {
            SketchwareUtil.toast(getString(R.string.code_shrinking_full_mode_empty), Toast.LENGTH_LONG);
            return;
        }

        ArrayList<String> libraries = new ArrayList<>();
        for (int i = 0; i < mll.list.size(); i++) {
            Object name = mll.list.get(i).get("name");
            libraries.add(name instanceof String stringName
                    ? stringName : "(broken library configuration)");
        }

        DialogFullModeLibrariesBinding dialogBinding =
                DialogFullModeLibrariesBinding.inflate(LayoutInflater.from(this));
        FullModeAdapter adapter = new FullModeAdapter(libraries, snapshot.fullModeLibs, count -> {
            dialogBinding.selectionCount.setText(
                    getString(R.string.code_shrinking_full_mode_selected_count, count));
            dialogBinding.clearSelection.setEnabled(count > 0);
        });
        dialogBinding.librariesRecycler.setLayoutManager(new LinearLayoutManager(this));
        dialogBinding.librariesRecycler.setAdapter(adapter);
        updateFullModeListHeight(dialogBinding.librariesRecycler, adapter.getItemCount());
        dialogBinding.searchButton.setOnClickListener(v -> {
            View field = dialogBinding.searchField;
            Boolean current = expandedStates.get(field);
            boolean show = current != null ? !current : field.getVisibility() == View.GONE;
            expandedStates.put(field, show);
            setViewExpanded(field, show);
            if (show) {
                dialogBinding.searchInput.requestFocus();
            } else {
                dialogBinding.searchInput.setText("");
                adapter.filter("");
                updateFullModeListHeight(dialogBinding.librariesRecycler, adapter.getItemCount());
            }
        });
        dialogBinding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
                updateFullModeListHeight(dialogBinding.librariesRecycler, adapter.getItemCount());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialogBinding.selectAll.setOnClickListener(v -> adapter.selectAll());
        dialogBinding.clearSelection.setOnClickListener(v -> adapter.clear());

        BottomSheetDialog sheet = new BottomSheetDialog(this);
        sheet.setContentView(dialogBinding.getRoot());
        if (sheet.getWindow() != null) {
            sheet.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            sheet.getWindow().setNavigationBarColor(
                    ThemeUtils.getColor(this, R.attr.colorSurface));

        }
        sheet.setOnShowListener(d -> {
            View sheetView = sheet.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (sheetView != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(sheetView);
                behavior.setSkipCollapsed(true);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        dialogBinding.sheetCancel.setOnClickListener(v -> sheet.dismiss());
        dialogBinding.sheetSave.setOnClickListener(v -> {
            ArrayList<String> selected = adapter.selectedLibraries();
            snapshot.fullModeLibs = new ArrayList<>(selected);
            renderSnapshot(snapshot);
            ioExecutor.execute(() -> pg.setProguardFMLibs(selected));
            sheet.dismiss();
        });
        sheet.setOnDismissListener(d -> {
            ValueAnimator animator = heightAnimators.remove(dialogBinding.searchField);
            if (animator != null) animator.cancel();
            expandedStates.remove(dialogBinding.searchField);
        });
        sheet.show();
    }

    private interface CountListener {
        void onCountChanged(int count);
    }

    private static class FullModeAdapter extends RecyclerView.Adapter<FullModeAdapter.ViewHolder> {
        private final ArrayList<String> libraries = new ArrayList<>();
        private final ArrayList<String> visible = new ArrayList<>();
        private final HashMap<String, Boolean> checked = new HashMap<>();
        private final CountListener countListener;

        FullModeAdapter(ArrayList<String> names, ArrayList<String> selected,
                CountListener countListener) {
            libraries.addAll(names);
            visible.addAll(names);
            for (String name : selected) checked.put(name, true);
            this.countListener = countListener;
            countListener.onCountChanged(selectedCount());
        }

        void filter(String query) {
            String filter = query.trim().toLowerCase(Locale.getDefault());
            visible.clear();
            for (String name : libraries) {
                if (name.toLowerCase(Locale.getDefault()).contains(filter)) visible.add(name);
            }
            notifyDataSetChanged();
        }

        void selectAll() {
            for (String name : libraries) checked.put(name, true);
            notifyDataSetChanged();
            countListener.onCountChanged(selectedCount());
        }

        void clear() {
            checked.clear();
            notifyDataSetChanged();
            countListener.onCountChanged(0);
        }

        ArrayList<String> selectedLibraries() {
            ArrayList<String> result = new ArrayList<>();
            for (String name : libraries) {
                if (Boolean.TRUE.equals(checked.get(name))) result.add(name);
            }
            return result;
        }

        private int selectedCount() {
            return selectedLibraries().size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemFullModeLibraryBinding binding = ItemFullModeLibraryBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String name = visible.get(position);
            holder.binding.libraryName.setText(name);
            holder.binding.libraryName.setSelected(true);
            holder.binding.libraryCheck.setChecked(Boolean.TRUE.equals(checked.get(name)));
            holder.itemView.setOnClickListener(v -> {
                boolean selected = !Boolean.TRUE.equals(checked.get(name));
                if (selected) checked.put(name, true);
                else checked.remove(name);
                holder.binding.libraryCheck.setChecked(selected);
                countListener.onCountChanged(selectedCount());
            });
        }

        @Override
        public int getItemCount() {
            return visible.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final ItemFullModeLibraryBinding binding;

            ViewHolder(ItemFullModeLibraryBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if (updatingUi || snapshot == null) return;
        if (button == binding.shrinkSwitch) {
            snapshot.shrinkEnabled = isChecked;
            renderSnapshot(snapshot);
            writeAndRefreshConflicts(() -> pg.setShrinkEnabled(isChecked));
        } else if (button == binding.obfuscateSwitch) {
            snapshot.obfuscateEnabled = isChecked;
            writeAndRefreshConflicts(() -> pg.setObfuscateEnabled(isChecked));
        } else if (button == binding.optimizeSwitch) {
            snapshot.optimizeEnabled = isChecked;
            writeAndRefreshConflicts(() -> pg.setOptimizeEnabled(isChecked));
        } else if (button == binding.stringfogSwitch) {
            snapshot.stringfogEnabled = isChecked;
            ioExecutor.execute(() -> stringfog.setStringfogEnabled(isChecked));
        } else if (button == binding.debugSwitch) {
            snapshot.debugEnabled = isChecked;
            ioExecutor.execute(() -> pg.setDebugModeEnabled(isChecked));
        }
    }
}
