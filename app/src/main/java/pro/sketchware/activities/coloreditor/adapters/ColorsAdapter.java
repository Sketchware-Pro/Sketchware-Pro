package pro.sketchware.activities.coloreditor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.databinding.PalletCustomviewBinding;

import java.util.ArrayList;

import mod.elfilibustero.sketch.lib.utils.PropertiesUtil;
import pro.sketchware.activities.coloreditor.ColorEditorActivity;
import pro.sketchware.activities.coloreditor.models.ColorItem;

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

        if (ColorEditorActivity.isValidHexColor(colorValue)) {
            holder.itemBinding.title.setHint(colorName);
            holder.itemBinding.sub.setText(colorValue.toUpperCase());
            holder.itemBinding.color.setBackgroundColor(PropertiesUtil.parseColor(colorValue));
        } else {
            data.remove(position);
            notifyItemRemoved(position);
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
