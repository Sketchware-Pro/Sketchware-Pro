package pro.sketchware.fragments.settings.ai;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.R;
import pro.sketchware.ai.config.AiConfiguration;
import pro.sketchware.databinding.ItemAiConfigurationBinding;
import pro.sketchware.lib.base.BaseViewBindingAdapter;
import pro.sketchware.utility.ThemeUtils;

/**
 * Lists the configured AI models and marks the one that is used for generating.
 */
class AiConfigurationAdapter extends BaseViewBindingAdapter<AiConfiguration> {

    private final Listener listener;
    private String selectedId;

    AiConfigurationAdapter(@NonNull Listener listener) {
        this.listener = listener;
    }

    void submit(@NonNull List<AiConfiguration> configurations, String selectedId) {
        this.selectedId = selectedId;
        setItems(new ArrayList<>(configurations));
    }

    @NonNull
    @Override
    public ViewBinding getViewBinding(LayoutInflater inflater, ViewGroup parent) {
        return ItemAiConfigurationBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onBindView(@NonNull ViewBinding viewBinding, int position) {
        ItemAiConfigurationBinding binding = (ItemAiConfigurationBinding) viewBinding;
        AiConfiguration configuration = getItem(position);
        boolean selected = configuration.getId().equals(selectedId);

        binding.name.setText(configuration.getDisplayName());
        binding.details.setText(binding.getRoot().getContext().getString(R.string.ai_configuration_details,
                configuration.getProvider().getDisplayName(), configuration.getModel()));

        binding.status.setText(configuration.isVerified() ? R.string.ai_configuration_verified : R.string.ai_configuration_unverified);
        binding.status.setTextColor(ThemeUtils.getColor(binding.status,
                configuration.isVerified() ? R.attr.colorPrimary : R.attr.colorOnSurfaceVariant));

        binding.card.setChecked(selected);
        binding.selected.setChecked(selected);
        binding.selected.setOnClickListener(v -> listener.onSelect(configuration));
        binding.card.setOnClickListener(v -> listener.onEdit(configuration));
        binding.delete.setOnClickListener(v -> listener.onDelete(configuration));
    }

    interface Listener {
        void onSelect(@NonNull AiConfiguration configuration);

        void onEdit(@NonNull AiConfiguration configuration);

        void onDelete(@NonNull AiConfiguration configuration);
    }
}
