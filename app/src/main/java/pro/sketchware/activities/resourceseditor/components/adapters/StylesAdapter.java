package pro.sketchware.activities.resourceseditor.components.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pro.sketchware.activities.resourceseditor.components.fragments.StylesEditor;
import pro.sketchware.activities.resourceseditor.components.fragments.ThemesEditor;
import pro.sketchware.activities.resourceseditor.components.models.StyleModel;
import pro.sketchware.databinding.PalletCustomviewBinding;

public class StylesAdapter extends RecyclerView.Adapter<StylesAdapter.StyleViewHolder> {

    private final List<StyleModel> stylesList;
    private final List<StyleModel> originalList;
    private final HashMap<Integer, String> notesMap;
    private final Fragment fragment;

    public StylesAdapter(ArrayList<StyleModel> stylesList, Fragment fragment, HashMap<Integer, String> notesMap) {
        this.stylesList = stylesList;
        this.originalList = new ArrayList<>(stylesList);
        this.notesMap = notesMap;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public StyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PalletCustomviewBinding binding = PalletCustomviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new StyleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StyleViewHolder holder, int position) {
        StyleModel style = stylesList.get(position);
        holder.bind(style);
    }

    @Override
    public int getItemCount() {
        return stylesList.size();
    }

    public void filter(String newText) {
        stylesList.clear();

        for (StyleModel style : originalList) {
            if (style.getStyleName().toLowerCase().contains(newText)) {
                stylesList.add(style);
            }
        }

        notifyDataSetChanged();
    }

    public class StyleViewHolder extends RecyclerView.ViewHolder {

        private final PalletCustomviewBinding binding;

        public StyleViewHolder(PalletCustomviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StyleModel style) {
            binding.title.setText(style.getStyleName());
            if (style.getParent().isEmpty()) {
                binding.sub.setText("No Parent");
            } else {
                binding.sub.setText(style.getParent());
            }
            if (notesMap.containsKey(getAbsoluteAdapterPosition())) {
                binding.tvTitle.setText(notesMap.get(getAbsoluteAdapterPosition()));
                binding.tvTitle.setVisibility(View.VISIBLE);
            } else {
                binding.tvTitle.setVisibility(View.GONE);
            }

            binding.backgroundCard.setOnClickListener(view -> {
                if (fragment instanceof StylesEditor stylesEditor) {
                    stylesEditor.showStyleAttributesDialog(getAbsoluteAdapterPosition());
                } else if (fragment instanceof ThemesEditor themesEditor) {
                    themesEditor.showThemeAttributesDialog(getAbsoluteAdapterPosition());
                }
            });

            binding.backgroundCard.setOnLongClickListener(view -> {
                if (fragment instanceof StylesEditor stylesEditor) {
                    stylesEditor.showEditStyleDialog(getAbsoluteAdapterPosition());
                } else if (fragment instanceof ThemesEditor themesEditor) {
                    themesEditor.showEditThemeDialog(getAbsoluteAdapterPosition());
                }
                return true;
            });
        }
    }
}
