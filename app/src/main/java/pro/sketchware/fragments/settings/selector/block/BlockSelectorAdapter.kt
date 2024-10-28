package pro.sketchware.fragments.settings.selector.block

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.R
import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorAdapter(private val onClick: (Selector) -> Unit) :
    ListAdapter<Selector, BlockSelectorAdapter.BlockSelectorAdapterViewHolder>(BlockSelectorAdapterDiffCallback) {

    class BlockSelectorAdapterViewHolder(
        private val binding: LayoutBlockSelectorBinding,
        val onClick: (Selector) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentSelector: Selector? = null

        init {
            itemView.setOnClickListener {
                currentSelector?.let { onClick(it) }
            }
        }

        fun bind(selector: Selector) {
            currentSelector = selector
            binding.name.text = selector.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, selector: Int): BlockSelectorAdapterViewHolder {
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

object BlockSelectorAdapterDiffCallback : DiffUtil.ItemCallback<Selector>() {
    override fun areItemsTheSame(oldItem: Selector, newItem: Selector): Boolean {
        return oldItem.name == newItem.name 
    }

    override fun areContentsTheSame(oldItem: Selector, newItem: Selector): Boolean {
        return oldItem == newItem 
    }
}