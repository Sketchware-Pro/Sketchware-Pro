package pro.sketchware.fragments.settings.appearance;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import a.a.a.qA;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentSettingsAppearanceBinding;
import pro.sketchware.databinding.ThemeItemBinding;
import pro.sketchware.utility.theme.ThemeItem;
import pro.sketchware.utility.theme.ThemeManager;

public class SettingsAppearanceFragment extends qA {
    private FragmentSettingsAppearanceBinding binding;

    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsAppearanceBinding.inflate(inflater, container, false);
        context = requireContext();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        initRecyclerView();
        initializeThemeSettings();
        setupClickListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }else{
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void initRecyclerView() {
        ThemesAdapter adapter = new ThemesAdapter(ThemeManager.getThemesList());
        adapter.setHasStableIds(true);

        binding.themesRecycler.setAdapter(adapter);
        binding.themesRecycler.smoothScrollToPosition(ThemeManager.getCurrentMode(context));
    }

    private void initializeThemeSettings() {
        switch (ThemeManager.getCurrentMode(context)) {
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

        binding.amoledCheck.setChecked(ThemeManager.isAmoledEnabled(context));
    }

    private void setupClickListeners() {
        binding.themeModes.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case (R.id.mode_system):
                        ThemeManager.applyMode(context, ThemeManager.THEME_SYSTEM);
                        binding.amoledCheck.setChecked(false);
                        break;
                    case (R.id.mode_light):
                        ThemeManager.applyMode(context, ThemeManager.THEME_LIGHT);
                        binding.amoledCheck.setChecked(false);
                        break;
                    case (R.id.mode_dark):
                        ThemeManager.applyMode(context, ThemeManager.THEME_DARK);
                        break;
                    default:
                        ThemeManager.applyMode(context, ThemeManager.THEME_SYSTEM);
                        binding.amoledCheck.setChecked(false);

                }
                requireActivity().recreate();
            }
        });

        binding.amoledCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ThemeManager.setAmoled(context, isChecked);
            if (isChecked) ThemeManager.applyMode(context, ThemeManager.THEME_DARK);
            requireActivity().recreate();
        });
    }

    private class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {
        private ThemeItemBinding itemBinding;
        private final ArrayList<ThemeItem> data;

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
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ThemeItem themeItem = data.get(holder.getAbsoluteAdapterPosition());

            int primaryColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorPrimary);
            int secondaryColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSecondary);
            int surfaceContainerColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSurfaceContainer);
            int primaryContainerColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorPrimaryContainer);
            int controlNormalColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorControlNormal);
            int backgroundColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSurface);
            int secondaryContainerColor = ThemeManager.getColorFromTheme(holder.itemView.getContext(), themeItem.getStyleId(), com.google.android.material.R.attr.colorSecondaryContainer);


            itemBinding.themeNameText.setText(themeItem.getName());

            itemBinding.themeItem1.setCardBackgroundColor(surfaceContainerColor);
            itemBinding.themeItem2.setCardBackgroundColor(surfaceContainerColor);
            itemBinding.themeItem3.setCardBackgroundColor(surfaceContainerColor);
            itemBinding.themeItem4.setCardBackgroundColor(secondaryContainerColor);

            itemBinding.themeNameText.setTextColor(primaryContainerColor);
            itemBinding.themeAccentedButton.setCardBackgroundColor(primaryContainerColor);

            itemBinding.themeItem1Rect.setCardBackgroundColor(primaryColor);
            itemBinding.themeItem2Rect.setCardBackgroundColor(secondaryColor);


            itemBinding.themeSpecial.setCardBackgroundColor(controlNormalColor);
            itemBinding.themeCardView.setCardBackgroundColor(backgroundColor);

            itemBinding.themeCardView.setOnClickListener(v -> {
                ThemeManager.saveTheme(context, themeItem.getThemeId());
                ThemeManager.applyMode(context, ThemeManager.getCurrentMode(context));
                requireActivity().recreate();

            });


            if (ThemeManager.isAmoledEnabled(context)) {
                itemBinding.themeCardView.setCardBackgroundColor(Color.BLACK);
                itemBinding.themeItem1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_amoled_surfaceContainerLow));
                itemBinding.themeItem2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_amoled_surfaceContainerLow));
                itemBinding.themeItem3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_amoled_surfaceContainer));
                itemBinding.themeItem4.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_amoled_surfaceContainerLow));
            }

            if (ThemeManager.getCurrentTheme(context) == themeItem.getThemeId()) {
                itemBinding.themeSelected.setBackground(AppCompatResources.getDrawable(context, R.drawable.theme_selected_border));
                itemBinding.check.setVisibility(View.VISIBLE);
            }else{
                itemBinding.themeSelected.setBackground(AppCompatResources.getDrawable(context, R.drawable.theme_unselected_border));
                itemBinding.check.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ThemeItemBinding itemBinding;

            public ViewHolder(ThemeItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
        }

    }
}