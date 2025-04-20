package pro.sketchware.fragments.settings.block.selector.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.databinding.LayoutBlockSelectorBinding;
import pro.sketchware.fragments.settings.block.selector.BlockSelectorAdapter.OnClickListener;
import pro.sketchware.utility.UI;

public class BlockSelectorDetailsAdapter extends ListAdapter<String, BlockSelectorDetailsAdapter.BlockSelectorDetailsAdapterViewHolder> {

    public final OnClickListener onClick;

    public BlockSelectorDetailsAdapter(OnClickListener onClick) {
        super(new BlockSelectorDetailsAdapterDiffCallback());
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public BlockSelectorDetailsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBlockSelectorBinding binding = LayoutBlockSelectorBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new BlockSelectorDetailsAdapterViewHolder(binding, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockSelectorDetailsAdapterViewHolder holder, int position) {
        holder.bind(getItem(position), position);
        holder.itemView.setBackgroundResource(UI.getShapedBackgroundForList(getCurrentList(), position));
    }

    public static class BlockSelectorDetailsAdapterViewHolder extends RecyclerView.ViewHolder {
        public final LayoutBlockSelectorBinding binding;
        public final OnClickListener onClick;
        public String currentString;
        public Integer currentIndex;

        public BlockSelectorDetailsAdapterViewHolder(LayoutBlockSelectorBinding binding, OnClickListener onClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClick = onClick;

            itemView.setOnClickListener(v -> {
                if (currentIndex != null) {
                    onClick.onClick(null, currentIndex);
                }
            });
        }

        public void bind(String string, int index) {
            currentString = string;
            currentIndex = index;
            binding.name.setText(string);
        }
    }

    public static class BlockSelectorDetailsAdapterDiffCallback extends DiffUtil.ItemCallback<String> {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }
}
