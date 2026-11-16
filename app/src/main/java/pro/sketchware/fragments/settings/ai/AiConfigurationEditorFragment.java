package pro.sketchware.fragments.settings.ai;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import a.a.a.qA;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.ai.AiTaskRunner;
import pro.sketchware.ai.api.AiClientFactory;
import pro.sketchware.ai.config.AiConfiguration;
import pro.sketchware.ai.config.AiConfigurationRepository;
import pro.sketchware.ai.config.AiProviderType;
import pro.sketchware.databinding.FragmentAiConfigurationEditorBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;
import pro.sketchware.utility.UI;

/**
 * Settings screen for adding or editing one AI model: choose the provider, enter the key, load
 * the provider's models, and verify that all of it works before saving.
 */
public class AiConfigurationEditorFragment extends qA {

    private static final String ARG_CONFIGURATION_ID = "configuration_id";

    private final AiTaskRunner taskRunner = new AiTaskRunner();
    private FragmentAiConfigurationEditorBinding binding;
    private AiConfigurationRepository repository;
    /**
     * The configuration being edited. It is a copy; nothing is stored until the user saves.
     */
    private AiConfiguration configuration;
    /**
     * {@link #fingerprint(AiConfiguration, boolean)} of the values the last successful test ran
     * with, to tell whether the form was changed since.
     */
    private String verifiedFingerprint;
    /**
     * The credentials the model list was last requested for, to not load the same list twice.
     */
    private String modelsFingerprint;

    @NonNull
    public static AiConfigurationEditorFragment newInstance(@Nullable String configurationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_CONFIGURATION_ID, configurationId);
        AiConfigurationEditorFragment fragment = new AiConfigurationEditorFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAiConfigurationEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new AiConfigurationRepository(requireContext());

        AiConfiguration stored = repository.get(requireArguments().getString(ARG_CONFIGURATION_ID));
        configuration = stored == null ? new AiConfiguration() : stored.copy();
        if (configuration.isVerified()) {
            verifiedFingerprint = fingerprint(configuration, true);
        }

        configureToolbar(binding.toolbar);
        binding.toolbar.setTitle(stored == null ? R.string.ai_editor_title_add : R.string.ai_editor_title_edit);
        UI.addSystemWindowInsetToPadding(binding.appBarLayout, true, true, true, false);
        UI.addSystemWindowInsetToPadding(binding.content, true, false, true, true);

        bindForm();
        setupListeners();
        loadModels(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskRunner.cancel();
        binding = null;
    }

    private void bindForm() {
        binding.provider.setSimpleItems(AiProviderType.displayNames());
        binding.provider.setText(configuration.getProvider().getDisplayName(), false);
        binding.name.setText(configuration.getName());
        binding.baseUrl.setText(configuration.getBaseUrl());
        binding.apiKey.setText(configuration.getApiKey());
        binding.model.setText(configuration.getModel(), false);
        bindProvider();
    }

    private void bindProvider() {
        boolean hasApiKeyPage = !configuration.getProvider().getApiKeyUrl().isEmpty();
        binding.getApiKey.setVisibility(hasApiKeyPage ? View.VISIBLE : View.GONE);
    }

    private void setupListeners() {
        binding.provider.setOnItemClickListener((parent, view, position, id) ->
                onProviderChanged(AiProviderType.values()[position]));
        binding.getApiKey.setOnClickListener(v -> openApiKeyPage());
        binding.apiKey.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                loadModels(false);
            }
        });
        binding.fetchModels.setOnClickListener(v -> loadModels(true));
        binding.test.setOnClickListener(v -> test(false));
        binding.save.setOnClickListener(v -> onSaveClicked());
    }

    private void onProviderChanged(AiProviderType provider) {
        if (provider == configuration.getProvider()) {
            return;
        }
        configuration.setProvider(provider);
        binding.baseUrl.setText(provider.getDefaultBaseUrl());
        binding.model.setText("", false);
        binding.model.setSimpleItems(new String[0]);
        binding.statusCard.setVisibility(View.GONE);
        modelsFingerprint = null;
        bindProvider();
        loadModels(false);
    }

    private void openApiKeyPage() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(configuration.getProvider().getApiKeyUrl())));
        } catch (ActivityNotFoundException e) {
            SketchwareUtil.toastError(configuration.getProvider().getApiKeyUrl());
        }
    }

    /**
     * Copies what the user typed into {@link #configuration}.
     */
    private void readForm() {
        configuration.setName(Helper.getText(binding.name).trim());
        configuration.setBaseUrl(Helper.getText(binding.baseUrl).trim());
        configuration.setApiKey(Helper.getText(binding.apiKey).trim());
        configuration.setModel(Helper.getText(binding.model).trim());
    }

    /**
     * @param byUser Whether the user asked for it. Automatic loads stay quiet when values are
     *               missing or the list is already loaded for them.
     */
    private void loadModels(boolean byUser) {
        readForm();
        if (byUser) {
            if (!validate(false)) {
                return;
            }
        } else if (configuration.getBaseUrl().isEmpty() || configuration.getApiKey().isEmpty()
                || fingerprint(configuration, false).equals(modelsFingerprint)) {
            return;
        }
        modelsFingerprint = fingerprint(configuration, false);

        AiConfiguration snapshot = configuration.copy();
        showProgress(getString(R.string.ai_editor_fetching_models));
        taskRunner.run(mainThread -> AiClientFactory.create(snapshot).fetchModels(), new AiTaskRunner.Callback<>() {
            @Override
            public void onSuccess(List<String> models) {
                binding.model.setSimpleItems(models.toArray(new String[0]));
                showResult(true, getString(R.string.ai_editor_models_loaded, models.size()));
                if (byUser && !models.isEmpty()) {
                    binding.model.showDropDown();
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                modelsFingerprint = null;
                showResult(false, getString(R.string.ai_editor_models_failed, e.getMessage()));
            }
        });
    }

    /**
     * @param saveAfterwards Whether to store the configuration once the test is through.
     */
    private void test(boolean saveAfterwards) {
        readForm();
        if (!validate(true)) {
            return;
        }

        AiConfiguration snapshot = configuration.copy();
        showProgress(getString(R.string.ai_editor_testing, snapshot.getModel()));
        taskRunner.run(mainThread -> {
            AiClientFactory.create(snapshot).testConnection();
            return snapshot;
        }, new AiTaskRunner.Callback<>() {
            @Override
            public void onSuccess(AiConfiguration tested) {
                verifiedFingerprint = fingerprint(tested, true);
                showResult(true, getString(R.string.ai_editor_test_succeeded, tested.getModel()));
                if (saveAfterwards) {
                    save();
                }
            }

            @Override
            public void onError(@NonNull Exception e) {
                verifiedFingerprint = null;
                showResult(false, getString(R.string.ai_editor_test_failed, e.getMessage()));
                if (saveAfterwards) {
                    confirmSavingUntested(e.getMessage());
                }
            }
        });
    }

    private void onSaveClicked() {
        readForm();
        if (!validate(true)) {
            return;
        }
        if (fingerprint(configuration, true).equals(verifiedFingerprint)) {
            save();
        } else {
            test(true);
        }
    }

    private void confirmSavingUntested(String reason) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.ai_editor_test_failed_title)
                .setMessage(reason)
                .setPositiveButton(R.string.ai_editor_save_anyway, (dialog, which) -> save())
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }

    private void save() {
        readForm();
        configuration.setVerified(fingerprint(configuration, true).equals(verifiedFingerprint));
        repository.save(configuration);
        SketchwareUtil.toast(getString(R.string.ai_editor_saved));
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    /**
     * @param withModel Whether the model field is required as well.
     * @return Whether all required fields are filled in. Marks the ones that aren't.
     */
    private boolean validate(boolean withModel) {
        boolean valid = requireField(binding.tilBaseUrl, configuration.getBaseUrl());
        valid &= requireField(binding.tilApiKey, configuration.getApiKey());
        if (withModel) {
            valid &= requireField(binding.tilModel, configuration.getModel());
        }
        return valid;
    }

    private boolean requireField(TextInputLayout layout, String value) {
        boolean filled = !value.isEmpty();
        layout.setError(filled ? null : getString(R.string.ai_editor_field_required));
        return filled;
    }

    /**
     * @return A value that changes whenever something a request depends on changes.
     */
    private String fingerprint(AiConfiguration configuration, boolean withModel) {
        return String.join("\n", configuration.getProvider().name(), configuration.getBaseUrl(),
                configuration.getApiKey(), withModel ? configuration.getModel() : "");
    }

    private void showProgress(String message) {
        setBusy(true);
        binding.statusCard.setVisibility(View.VISIBLE);
        binding.statusProgress.setVisibility(View.VISIBLE);
        binding.statusIcon.setVisibility(View.GONE);
        binding.statusText.setText(message);
    }

    private void showResult(boolean success, String message) {
        @DrawableRes int icon = success ? R.drawable.ic_mtrl_check : R.drawable.ic_mtrl_load_failed;
        setBusy(false);
        binding.statusCard.setVisibility(View.VISIBLE);
        binding.statusProgress.setVisibility(View.GONE);
        binding.statusIcon.setVisibility(View.VISIBLE);
        binding.statusIcon.setImageResource(icon);
        binding.statusIcon.setColorFilter(ThemeUtils.getColor(binding.statusIcon, success ? R.attr.colorPrimary : R.attr.colorError));
        binding.statusText.setText(message);
    }

    private void setBusy(boolean busy) {
        binding.fetchModels.setEnabled(!busy);
        binding.test.setEnabled(!busy);
        binding.save.setEnabled(!busy);
    }
}
