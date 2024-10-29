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
import com.sketchware.remod.databinding.DialogSelectorActionsBinding

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.utility.FileUtil.writeFile
import pro.sketchware.fragments.settings.selector.block.Selector
import pro.sketchware.fragments.settings.selector.block.BlockSelectorConsts

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
            onClick = {
                showActionsDialog(indexA = it)
            },
            onLongClick = {
                toast(selectors[index].data.get(it))
            }
        )
        adapter.submitList(selectors[index].data)
        binding.list.adapter = adapter
        
        binding.createNew.setOnClickListener {
            showCreateEditDialog()
        }
        super.onViewCreated(view, saved)
    }
    
    private fun showCreateEditDialog(
        isEdit: Boolean = false,
        indexA: Int = 0
    ) {
        val dialogBinding = DialogCreateBinding.inflate(LayoutInflater.from(requireContext())).apply {
            activityNameInputLayout.hint = "Name"
            if(isEdit) activityNameInput.setText(selectors[index].data.get(indexA))
        }
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "New Selector Item"
            dialogYesText = "Create"
            dialogNoText = "Cancel"
            dialogCustomView = dialogBinding.root
            dialogYesListener = View.OnClickListener {
                val newItem = dialogBinding.activityNameInput.text?.toString()
                if (!newItem.isNullOrEmpty()) {
                    if (!isEdit) {
                        selectors[index].data.add(newItem)
                    } else {
                        selectors[index].data[indexA] = newItem
                    }
                    saveAll()
                    adapter.notifyDataSetChanged()
                }
                dismiss()
            }
            dialogNoListener = View.OnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }
    
    private fun showActionsDialog(
        indexA: Int
    ) {
        val dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "Actions"
            dialogCustomView = dialogBinding.root
        }
        dialogBinding.apply {
            edit.setOnClickListener {
                dialog.dismiss()
                showCreateEditDialog(
                    indexA = indexA,
                    isEdit = true
                )
            }
            delete.setOnClickListener {
                dialog.dismiss()
                showConfirmationDialog(
                    message = "Are you sure you want to delete this Selector Item?",
                    onConfirm = {
                        selectors[index].data.removeAt(indexA)
                        saveAll()
                        adapter.notifyDataSetChanged()
                        it.dismiss()
                    },
                    onCancel = {
                        it.dismiss()
                    }
                )
            }
        }
        dialog.show()
    }
    
    private fun showConfirmationDialog(
        message: String,
        onConfirm: (aB) -> Unit,
        onCancel: (aB) -> Unit
    ) {
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "Attention"
            dialogMessageText = message
            dialogYesText = "Yes"
            dialogNoText = "Cancel"
            setCancelable(false)
            dialogYesListener = View.OnClickListener {
                onConfirm(this)
            }
            dialogNoListener = View.OnClickListener {
                onCancel(this)
            }
        }
        dialog.show()
    }
    
    private fun saveAll() {
        val gson: Gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        writeFile(
            BlockSelectorConsts.BLOCK_SELECTORS_FILE.absolutePath, 
            gson.toJson(selectors)
        )
        toast("Saved!")
    }
    
    override fun configureToolbar(toolbar: MaterialToolbar) {
        super.configureToolbar(toolbar)
        if(!selectors[index].name.equals("")) toolbar.setTitle(selectors[index].name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
