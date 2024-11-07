package pro.sketchware.fragments.settings.selector.block;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.R;
import pro.sketchware.databinding.LayoutBlockSelectorBinding;

public class BlockSelectorAdapter extends ListAdapter<Selector, BlockSelectorAdapter.BlockSelectorAdapterViewHolder> {

    public final OnClickListener onClick;
    public final OnLongClickListener onLongClick;

    public interface OnClickListener {
        void onClick(Selector selector, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(Selector selector, int position);
    }

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

        int backgroundResource;
        if (getCurrentList().size() == 1) {
            backgroundResource = R.drawable.shape_alone;
        } else if (position == 0) {
            backgroundResource = R.drawable.shape_top;
        } else if (position == getCurrentList().size() - 1) {
            backgroundResource = R.drawable.shape_bottom;
        } else {
            backgroundResource = R.drawable.shape_middle;
        }

        holder.itemView.setBackgroundResource(backgroundResource);
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