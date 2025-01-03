package pro.sketchware.activities.resources.editors.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import pro.sketchware.activities.resources.editors.ResourcesEditorActivity;
import pro.sketchware.activities.resources.editors.fragments.ColorsEditor;
import pro.sketchware.activities.resources.editors.models.ColorModel;
import pro.sketchware.activities.resources.editors.utils.ColorsEditorManager;
import pro.sketchware.databinding.PalletCustomviewBinding;
import pro.sketchware.utility.PropertiesUtil;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {

    private final ArrayList<ColorModel> originalData;
    private ArrayList<ColorModel> filteredData;
    private final ResourcesEditorActivity activity;
    private final ColorsEditorManager colorsEditorManager = new ColorsEditorManager();

    public ColorsAdapter(ArrayList<ColorModel> filteredData, ResourcesEditorActivity activity) {
        this.originalData = new ArrayList<>(filteredData);
        this.filteredData = filteredData;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ColorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PalletCustomviewBinding itemBinding = PalletCustomviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorModel colorModel = filteredData.get(position);
        String colorName = colorModel.getColorName();
        String colorValue = colorModel.getColorValue();
        String valueHex = colorsEditorManager.getColorValue(activity.getApplicationContext(), colorValue, 4);

        holder.itemBinding.title.setHint(colorName);
        holder.itemBinding.sub.setText(colorValue);

        if (PropertiesUtil.isHexColor(valueHex)) {
            holder.itemBinding.color.setBackgroundColor(PropertiesUtil.parseColor(valueHex));
        }

        holder.itemBinding.backgroundCard.setOnClickListener(v -> activity.colorsEditor.showColorEditDialog(colorModel, position));
        holder.itemBinding.backgroundCard.setOnLongClickListener(v -> {
            activity.colorsEditor.showDeleteDialog(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void filter(String newText) {
        if (newText == null || newText.isEmpty()) {
            filteredData = new ArrayList<>(originalData); // Reset to original data
        } else {
            String filterText = newText.toLowerCase().trim();
            filteredData = originalData.stream()
                    .filter(item -> item.getColorName().toLowerCase().contains(filterText) ||
                            item.getColorValue().toLowerCase().contains(filterText))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PalletCustomviewBinding itemBinding;

        public ViewHolder(PalletCustomviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
