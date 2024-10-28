package pro.sketchware.fragments.settings.selector.block

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.R
import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorAdapter(
    private val onClick: (Selector, Int) -> Unit,
    private val onLongClick: (Selector, Int) -> Unit
) :
    ListAdapter<Selector, BlockSelectorAdapter.BlockSelectorAdapterViewHolder>(BlockSelectorAdapterDiffCallback) {

    class BlockSelectorAdapterViewHolder(
        private val binding: LayoutBlockSelectorBinding,
        val onClick: (Selector, Int) -> Unit,
        val onLongClick: (Selector, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentSelector: Selector? = null
        private var currentIndex: Int? = null

        init {
            itemView.setOnClickListener {
                currentSelector?.let { selector -> 
                    currentIndex?.let { index ->
                        onClick(selector, index)
                    }
                }
            }
            itemView.setOnLongClickListener {
                currentSelector?.let { selector -> 
                    currentIndex?.let { index ->
                        onLongClick(selector, index)
                    }
                }
                true
            }
        }

        fun bind(
            selector: Selector,
            index: Int
        ) {
            currentSelector = selector
            currentIndex = index
            binding.name.text = selector.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, selector: Int): BlockSelectorAdapterViewHolder {
        val binding = LayoutBlockSelectorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BlockSelectorAdapterViewHolder(binding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: BlockSelectorAdapterViewHolder, position: Int) {
        holder.bind(
            selector = getItem(position),
            index = position
        )
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