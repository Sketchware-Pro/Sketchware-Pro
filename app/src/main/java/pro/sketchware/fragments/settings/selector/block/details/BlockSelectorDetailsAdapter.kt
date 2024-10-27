package pro.sketchware.fragments.settings.selector.block.details

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.R
import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorDetailsAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, BlockSelectorDetailsAdapter.BlockSelectorDetailsAdapterViewHolder>(BlockSelectorDetailsAdapterDiffCallback) {

    class BlockSelectorDetailsAdapterViewHolder(
        private val binding: LayoutBlockSelectorBinding,
        val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentString: String? = null

        init {
            itemView.setOnClickListener {
                currentString?.let { onClick(it) }
            }
        }

        fun bind(string: String) {
            currentString = string
            binding.name.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSelectorDetailsAdapterViewHolder {
        val binding = LayoutBlockSelectorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BlockSelectorDetailsAdapterViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: BlockSelectorDetailsAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
        when {
            currentList.size == 1 -> holder.itemView.setBackgroundResource(R.drawable.shape_alone)
            position == 0 -> holder.itemView.setBackgroundResource(R.drawable.shape_top)
            position == currentList.size - 1 -> holder.itemView.setBackgroundResource(R.drawable.shape_bottom)
            else -> holder.itemView.setBackgroundResource(R.drawable.shape_middle)
        }
    }
}

object BlockSelectorDetailsAdapterDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}