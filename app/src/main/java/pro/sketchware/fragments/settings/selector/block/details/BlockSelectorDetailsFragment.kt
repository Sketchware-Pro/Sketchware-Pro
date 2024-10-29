package pro.sketchware.fragments.settings.selector.block.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.appbar.MaterialToolbar

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding
import com.sketchware.remod.databinding.DialogAddCustomActivityBinding as DialogCreateBinding

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.utility.FileUtil.writeFile
import pro.sketchware.fragments.settings.selector.block.Selector
import pro.sketchware.fragments.settings.selector.block.BlockSelectorManagerFragment.Companion.BLOCK_SELECTOR_MENUS_FILE

import a.a.a.aB

class BlockSelectorDetailsFragment(
    private val index: Int,
    private val selectors: MutableList<Selector>
): BaseFragment() {

    private var _binding: FragmentBlockSelectorManagerBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: BlockSelectorDetailsAdapter
    
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
        
        adapter = BlockSelectorDetailsAdapter(
            onClick = { selectorName ->
                toast(selectorName)
            }
        )
        adapter.submitList(selectors[index].data)
        binding.list.adapter = adapter
        
        binding.createNew.setOnClickListener {
            showCreateDialog()
        }
        super.onViewCreated(view, saved)
    }
    
    private fun showCreateDialog() {
        val dialogBinding = DialogCreateBinding.inflate(LayoutInflater.from(requireContext())).apply {
            activityNameInputLayout.hint = "Name"
        }
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "New Selector Item"
            dialogYesText = "Create"
            dialogNoText = "Cancel"
            dialogCustomView = dialogBinding.root
            dialogYesListener = View.OnClickListener {
                val newItem = dialogBinding.activityNameInput.text?.toString()
                if (!newItem.isNullOrEmpty()) {
                    selectors[index].data.add(newItem)
                    saveAll()
                    adapter.notifyDataSetChanged()
                }
            }
            dialogNoListener = View.OnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }
    
    override fun configureToolbar(toolbar: MaterialToolbar) {
        super.configureToolbar(toolbar)
        if(!selectors[index].name.equals("")) toolbar.setTitle(selectors[index].name)
    }
    
    private fun saveAll() {
        val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        writeFile(
            BLOCK_SELECTOR_MENUS_FILE.absolutePath, 
            gson.toJson(selectors)
        )
        toast("Saved!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
