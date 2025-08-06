package pro.sketchware.fragments.settings.block.selector;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.databinding.LayoutBlockSelectorBinding;
import pro.sketchware.utility.UI;

public class BlockSelectorAdapter extends ListAdapter<Selector, BlockSelectorAdapter.BlockSelectorAdapterViewHolder> {

    public final OnClickListener onClick;
    public final OnLongClickListener onLongClick;

    public BlockSelectorAdapter(OnClickListener onClick, OnLongClickListener onLongClick) {
        super(new BlockSelectorAdapterDiffCallback());
        this.onClick = onClick;
        this.onLongClick = onLongClick;
    }

    @NonNull
    @Override
    public BlockSelectorAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutBlockSelectorBinding binding = LayoutBlockSelectorBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new BlockSelectorAdapterViewHolder(binding, onClick, onLongClick);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockSelectorAdapterViewHolder holder, int position) {
        holder.bind(getItem(position), position);
        holder.itemView.setBackgroundResource(UI.getShapedBackgroundForList(getCurrentList(), position));
    }

    public interface OnClickListener {
        void onClick(Selector selector, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(Selector selector, int position);
    }

    public static class BlockSelectorAdapterViewHolder extends RecyclerView.ViewHolder {
        public final LayoutBlockSelectorBinding binding;
        public final OnClickListener onClick;
        public final OnLongClickListener onLongClick;
        public Selector currentSelector;
        public Integer currentIndex;

        public BlockSelectorAdapterViewHolder(LayoutBlockSelectorBinding binding, OnClickListener onClick, OnLongClickListener onLongClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClick = onClick;
            this.onLongClick = onLongClick;

            itemView.setOnClickListener(v -> {
                if (currentSelector != null && currentIndex != null) {
                    onClick.onClick(currentSelector, currentIndex);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (currentSelector != null && currentIndex != null) {
                    onLongClick.onLongClick(currentSelector, currentIndex);
                }
                return true;
            });
        }

        public void bind(Selector selector, int index) {
            currentSelector = selector;
            currentIndex = index;
            binding.name.setText(selector.getName());
        }
    }

    public static class BlockSelectorAdapterDiffCallback extends DiffUtil.ItemCallback<Selector> {
        @Override
        public boolean areItemsTheSame(@NonNull Selector oldItem, @NonNull Selector newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Selector oldItem, @NonNull Selector newItem) {
            return oldItem.equals(newItem);
        }
    }
}
