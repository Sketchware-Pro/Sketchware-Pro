package pro.sketchware.fragments.settings.selector.block.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.appbar.MaterialToolbar

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding
import com.sketchware.remod.databinding.DialogAddCustomActivityBinding as DialogCreateBinding

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.fragments.settings.selector.block.Selector

import a.a.a.aB

class BlockSelectorDetailsFragment(
    val selector: Selector
): BaseFragment() {

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
        configureToolbar(binding.toolbar)
        handleInsetts(binding.root)
        val adapter = BlockSelectorDetailsAdapter(
            onClick = { selectorName ->
                toast(selectorName)
            }
        )
        adapter.submitList(selector.data)
        binding.list.adapter = adapter
        
        binding.createNew.setOnClickListener {
            showCreateDialog()
        }
        super.onViewCreated(view, saved)
    }
    
    private fun showCreateDialog() {
        val dialogBinding = DialogCreateBinding.inflate(LayoutInflater(requireContext())).apply {
            activityNameInputLayout.hint = "Name"
        }
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "New Selector Item"
            dialogYesText = "Create"
            dialogNoText = "Cancel"
            dialogYesListener = View.OnClickListener {
                //todo
            }
            dialogNoListener = View.OnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }
    
    override fun configureToolbar(toolbar: MaterialToolbar) {
        super.configureToolbar(toolbar)
        if(!selector.name.equals("")) toolbar.setTitle(selector.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
