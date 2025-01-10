package pro.sketchware.fragments.settings.appearance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import a.a.a.qA;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentSettingsAppearanceBinding;
import pro.sketchware.databinding.ThemeItemBinding;
import pro.sketchware.utility.theme.ThemeItem;
import pro.sketchware.utility.theme.ThemeManager;

public class SettingsAppearanceFragment extends qA {
    private FragmentSettingsAppearanceBinding binding;
    private MaterialCardView selectedThemeCard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsAppearanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();

        binding.themesRecycler.setAdapter(new ThemesAdapter(ThemeManager.getThemesList()));
        initializeThemeSettings();
        setupClickListeners();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }else{
                requireActivity().onBackPressed();
            }
        });
    }

    private void initializeThemeSettings() {
        boolean isSystemTheme = ThemeManager.isSystemMode(requireContext());

        switch (ThemeManager.getCurrentMode(requireContext())) {
            case (0):
                binding.themeModes.check(R.id.mode_system);
                break;
            case (1):
                binding.themeModes.check(R.id.mode_light);
                break;
            case (2):
                binding.themeModes.check(R.id.mode_dark);
                break;
            default:
                binding.themeModes.check(R.id.mode_system);
        }
    }

    private void setupClickListeners() {
        binding.themeModes.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case (R.id.mode_system):
                        ThemeManager.applyMode(requireContext(), ThemeManager.THEME_SYSTEM);
                        break;
                    case (R.id.mode_light):
                        ThemeManager.applyMode(requireContext(), ThemeManager.THEME_LIGHT);
                        break;
                    case (R.id.mode_dark):
                        ThemeManager.applyMode(requireContext(), ThemeManager.THEME_DARK);
                        break;
                    default:
                        ThemeManager.applyMode(requireContext(), ThemeManager.THEME_SYSTEM);

                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {
        private ThemeItemBinding itemBinding;
        private ArrayList<ThemeItem> data;

        private ThemesAdapter(ArrayList<ThemeItem> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ThemesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            itemBinding = ThemeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ThemeItem themeItem = data.get(holder.getBindingAdapterPosition());

            int primaryColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorPrimary);
            int secondaryColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSecondary);
            int surfaceContainerColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSurfaceContainer);
            int surfaceColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSurface);

            itemBinding.themeNameText.setText(themeItem.getName());

            itemBinding.themeCardView.setChecked(ThemeManager.getCurrentTheme(requireContext()) == themeItem.getThemeId());
            itemBinding.themeItem1.setCardBackgroundColor(surfaceContainerColor);
            itemBinding.themeItem2.setCardBackgroundColor(surfaceContainerColor);
            itemBinding.themeItem3.setCardBackgroundColor(surfaceContainerColor);


            itemBinding.themeItem1Rect.setCardBackgroundColor(primaryColor);
            itemBinding.themeItem1Rect.setCardBackgroundColor(secondaryColor);
            itemBinding.themeAccentedButton.setCardBackgroundColor(secondaryColor);

            itemBinding.themeCardView.setOnClickListener(v -> {
                ThemeManager.saveTheme(requireContext(), themeItem.getThemeId());
                ThemeManager.applyMode(requireContext(), ThemeManager.getCurrentMode(requireContext()));
                requireActivity().recreate();

            });

            if (ThemeManager.getCurrentTheme(requireContext()) == themeItem.getThemeId()) {
                itemBinding.themeSelected.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.theme_selected_border));
                itemBinding.check.setVisibility(View.VISIBLE);
            }else{
                itemBinding.themeSelected.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.theme_unselected_border));
                itemBinding.check.setVisibility(View.GONE);
            }


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ThemeItemBinding itemBinding;

            public ViewHolder(ThemeItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
        }
    }
}