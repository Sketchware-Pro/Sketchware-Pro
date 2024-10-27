package pro.sketchware.fragments.settings.selector.block

// based on google example

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, BlockSelectorAdapter.BlockSelectorAdapterViewHolder>(BlockSelectorAdapterDiffCallback) {

    class BlockSelectorAdapterViewHolder(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSelectorAdapterViewHolder {
        val binding = LayoutBlockSelectorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BlockSelectorAdapterViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: BlockSelectorAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
        when {
            currentList.size == 1 -> holder.itemView.setBackgroundResource(R.drawable.shape_alone)
            position == 0 -> holder.itemView.setBackgroundResource(R.drawable.shape_top)
            position == currentList.size - 1 -> holder.itemView.setBackgroundResource(R.drawable.shape_bottom)
            else -> holder.itemView.setBackgroundResource(R.drawable.shape_middle)
        }
    }
}

object BlockSelectorAdapterDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}