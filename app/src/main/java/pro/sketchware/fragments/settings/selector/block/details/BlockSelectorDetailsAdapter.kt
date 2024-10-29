package pro.sketchware.fragments.settings.selector.block.details

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.R
import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorDetailsAdapter(
    private val onClick: (Int) -> Unit,
    private val onLongClick: (Int) -> Unit
) :
    ListAdapter<String, BlockSelectorDetailsAdapter.BlockSelectorDetailsAdapterViewHolder>(BlockSelectorDetailsAdapterDiffCallback) {

    class BlockSelectorDetailsAdapterViewHolder(
        private val binding: LayoutBlockSelectorBinding,
        val onClick: (Int) -> Unit,
        val onLongClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentString: String? = null
        private var currentIndex: Int? = null

        init {
            itemView.setOnClickListener {
                currentIndex?.let { onClick(it) }
            }
            itemView.setOnLongClickListener {
                currentIndex?.let { onLongClick(it) }
                true
            }
        }

        fun bind(
            string: String,
            index: Int
        ) {
            currentString = string
            currentIndex = index
            binding.name.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockSelectorDetailsAdapterViewHolder {
        val binding = LayoutBlockSelectorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BlockSelectorDetailsAdapterViewHolder(binding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: BlockSelectorDetailsAdapterViewHolder, position: Int) {
        holder.bind(
            string = getItem(position),
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

object BlockSelectorDetailsAdapterDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}