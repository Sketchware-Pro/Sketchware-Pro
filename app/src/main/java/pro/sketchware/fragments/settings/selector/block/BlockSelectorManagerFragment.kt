package pro.sketchware.fragments.settings.selector.block

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast

class BlockSelectorManagerFragment : BaseFragment() {

    private var _binding: FragmentBlockSelectorManagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saved: Bundle?
    ): View {
        _binding = FragmentBlockSelectorManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, 
        saved: Bundle?
    ) {
        val adapter = BlockSelectorAdapter(
            onClick = { selectorName ->
                toast(selectorName)
            }
        )
        val lst = listOf(
            "TextView", 
            "Button",
            "LinearLayout", 
            "TV"
        ) // fake items for test
        adapter.submitList(lst)
        binding.list.adapter = adapter
        super.onViewCreated(view, saved)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}