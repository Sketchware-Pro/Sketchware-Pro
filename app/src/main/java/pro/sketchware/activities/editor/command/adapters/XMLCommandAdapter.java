package pro.sketchware.activities.editor.command.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import mod.hilal.saif.blocks.CommandBlock;
import pro.sketchware.R;
import pro.sketchware.databinding.ItemXmlCommandBinding;
import pro.sketchware.listeners.ItemClickListener;

public class XMLCommandAdapter
        extends ListAdapter<HashMap<String, Object>, XMLCommandAdapter.ViewHolder> {
    private static final DiffUtil.ItemCallback<HashMap<String, Object>> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull HashMap<String, Object> oldItem,
                        @NonNull HashMap<String, Object> newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull HashMap<String, Object> oldItem,
                        @NonNull HashMap<String, Object> newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private ItemClickListener<Pair<View, Integer>> listener;

    public XMLCommandAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnItemClickListener(
            ItemClickListener<Pair<View, Integer>> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemXmlCommandBinding binding = ItemXmlCommandBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemXmlCommandBinding binding;

        public ViewHolder(ItemXmlCommandBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HashMap<String, Object> item) {
            binding.tvName.setText(
                    CommandBlock.getInputName(
                            item.get("input") != null ? item.get("input").toString() : ""));
            binding.tvName.setSingleLine(true);
            binding.tvValue.setText("Reference: " + item.get("reference").toString());
            binding.tvValue.setSingleLine(true);
            binding.imgLeftIcon.setImageResource(R.drawable.ic_mtrl_code);
            binding.getRoot()
                    .setOnClickListener(
                            v -> {
                                if (listener != null) {
                                    listener.onItemClick(Pair.create(binding.getRoot(), getAdapterPosition()));
                                }
                            });
        }
    }
}
