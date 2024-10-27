package pro.sketchware.fragments.settings.selector.block

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.sketchware.remod.R
import com.sketchware.remod.databinding.LayoutBlockSelectorBinding

class BlockSelectorAdapter(private val onClick: (ViewType) -> Unit) :
    ListAdapter<ViewType, BlockSelectorAdapter.BlockSelectorAdapterViewHolder>(BlockSelectorAdapterDiffCallback) {

    class BlockSelectorAdapterViewHolder(
        private val binding: LayoutBlockSelectorBinding,
        val onClick: (ViewType) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentViewType: ViewType? = null

        init {
            itemView.setOnClickListener {
                currentViewType?.let { onClick(it) }
            }
        }

        fun bind(viewType: ViewType) {
            currentViewType = viewType
            binding.name.text = viewType.name
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

object BlockSelectorAdapterDiffCallback : DiffUtil.ItemCallback<ViewType>() {
    override fun areItemsTheSame(oldItem: ViewType, newItem: ViewType): Boolean {
        return oldItem.name == newItem.name 
    }

    override fun areContentsTheSame(oldItem: ViewType, newItem: ViewType): Boolean {
        return oldItem == newItem 
    }
}