package pro.sketchware.activities.coloreditor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mod.elfilibustero.sketch.lib.utils.PropertiesUtil;
import pro.sketchware.activities.coloreditor.ColorEditorActivity;
import pro.sketchware.activities.coloreditor.models.ColorItem;
import pro.sketchware.databinding.PalletCustomviewBinding;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ViewHolder> {

    private final ArrayList<ColorItem> data;
    private final ColorEditorActivity activity;

    public ColorsAdapter(ArrayList<ColorItem> data, ColorEditorActivity activity) {
        this.data = data;
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
        ColorItem colorItem = data.get(position);
        String colorName = colorItem.getColorName();
        String colorValue = colorItem.getColorValue();
        String valueHex = ColorEditorActivity.getColorValue(activity.getApplicationContext(), colorValue, 4);


        holder.itemBinding.title.setHint(colorName);
        holder.itemBinding.sub.setText(colorValue);

        if (ColorEditorActivity.isValidHexColor(valueHex)) {
            holder.itemBinding.color.setBackgroundColor(PropertiesUtil.parseColor(valueHex));
        }

        holder.itemBinding.backgroundCard.setOnClickListener(v -> activity.showColorEditDialog(colorItem, position));
        holder.itemBinding.backgroundCard.setOnLongClickListener(v -> {
            activity.showDeleteDialog(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PalletCustomviewBinding itemBinding;

        public ViewHolder(PalletCustomviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
