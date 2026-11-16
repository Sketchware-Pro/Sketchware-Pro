package pro.sketchware.fragments.settings.ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import a.a.a.qA;
import pro.sketchware.R;
import pro.sketchware.ai.config.AiConfiguration;
import pro.sketchware.ai.config.AiConfigurationRepository;
import pro.sketchware.databinding.FragmentAiConfigurationBinding;
import pro.sketchware.utility.UI;

/**
 * Settings screen that lists the configured AI models, and lets the user add, edit, delete and
 * pick the one that generates apps.
 */
public class AiConfigurationFragment extends qA implements AiConfigurationAdapter.Listener {

    private FragmentAiConfigurationBinding binding;
    private AiConfigurationRepository repository;
    private AiConfigurationAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAiConfigurationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new AiConfigurationRepository(requireContext());
        adapter = new AiConfigurationAdapter(this);

        configureToolbar(binding.toolbar);
        binding.configurations.setAdapter(adapter);
        binding.addConfiguration.setOnClickListener(v -> openFragment(AiConfigurationEditorFragment.newInstance(null)));

        UI.addSystemWindowInsetToPadding(binding.appBarLayout, true, true, true, false);
        UI.addSystemWindowInsetToPadding(binding.configurations, true, false, true, true);
        UI.addSystemWindowInsetToMargin(binding.addConfiguration, false, false, true, true);

        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSelect(@NonNull AiConfiguration configuration) {
        repository.setSelected(configuration.getId());
        refresh();
    }

    @Override
    public void onEdit(@NonNull AiConfiguration configuration) {
        openFragment(AiConfigurationEditorFragment.newInstance(configuration.getId()));
    }

    @Override
    public void onDelete(@NonNull AiConfiguration configuration) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.ai_configuration_delete_title)
                .setMessage(getString(R.string.ai_configuration_delete_message, configuration.getDisplayName()))
                .setPositiveButton(R.string.common_word_delete, (dialog, which) -> {
                    repository.delete(configuration.getId());
                    refresh();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private void refresh() {
        List<AiConfiguration> configurations = repository.getAll();
        AiConfiguration selected = repository.getSelected();
        adapter.submit(configurations, selected == null ? null : selected.getId());
        binding.emptyState.setVisibility(configurations.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
