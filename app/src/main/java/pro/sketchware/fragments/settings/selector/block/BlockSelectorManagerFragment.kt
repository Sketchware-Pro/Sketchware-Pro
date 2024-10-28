package pro.sketchware.fragments.settings.selector.block

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.lifecycleScope

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding
import com.sketchware.remod.databinding.DialogBlockConfigurationBinding as DialogCreateBinding
import com.sketchware.remod.databinding.DialogSelectorActionsBinding

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.utility.FileUtil.writeFile
import pro.sketchware.utility.FileUtil.isExistFile
import pro.sketchware.fragments.settings.selector.block.details.BlockSelectorDetailsFragment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.readText

import java.io.File

import a.a.a.aB

class BlockSelectorManagerFragment : BaseFragment() {

    companion object {
        val BLOCK_SELECTOR_MENUS_FILE = 
            File(Environment.getExternalStorageDirectory(), ".sketchware/resources/block/My Block/menu.json")
    }
    
    private var _binding: FragmentBlockSelectorManagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saved: Bundle?
    ): View {
        _binding = FragmentBlockSelectorManagerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    private var selectors: MutableList<Selector> = mutableListOf()
    private lateinit var adapter: BlockSelectorAdapter

    override fun onViewCreated(
        view: View, 
        saved: Bundle?
    ) {
        configureToolbar(binding.toolbar)
        handleInsetts(binding.root)
        adapter = BlockSelectorAdapter(
            onClick = { selector, index ->
                openFragment(BlockSelectorDetailsFragment(selector))
            },
            onLongClick = { selector, index ->
                showActionsDialog(index = index)
            }
        )
        lifecycleScope.launch {
            if (isExistFile(BLOCK_SELECTOR_MENUS_FILE.absolutePath)) {
                selectors = parseJson(
                    BLOCK_SELECTOR_MENUS_FILE.readText(
                        Charsets.UTF_8
                    )
                )
            } else {
                selector.add(
                    Selector(
                        name = "typeview",
                        title = "Select typeview:",
                        data = getTypeViewList()
                    )
                )
                saveAll()
            }
        }
        binding.list.adapter = adapter
        adapter.submitList(selectors)
        
        binding.createNew.setOnClickListener {
            showCreateEditDialog()
        }
        
        super.onViewCreated(view, saved)
    }
    
    private fun parseJson(
        jsonString: String
    ): MutableList<Selector> {
        val gson = Gson()
        val listType = object : TypeToken<List<Selector>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
    
    private fun showCreateEditDialog(
        index: Int = 0,
        isEdit: Boolean = false
    ) {
        val dialogBinding = DialogCreateBinding.inflate(LayoutInflater.from(requireContext())).apply {
            tilPalettesPath.hint = "Selector name"
            tilBlocksPath.hint = "Selector title (ex: Select View:)"
            if (isEdit) {
                palettesPath.setText(selectors.get(index).name)
                blocksPath.setText(selectors.get(index).title)
            }
        }
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = if (!isEdit) "New Selector" else "Edit Selector"
            dialogCustomView = dialogBinding.getRoot()
            dialogYesText = if (!isEdit) "Create" else "Save"
            dialogNoText = "Cancel"
            dialogYesListener = View.OnClickListener {
                val selectorName = dialogBinding.palettesPath.text?.toString()
                val selectorTitle = dialogBinding.blocksPath.text?.toString()
                
                if (selectorName.isNullOrEmpty()) {
                    toast("Please type Selector name")
                    return@OnClickListener
                }
                if (selectorTitle.isNullOrEmpty()) {
                    toast("Please type Selector title")
                    return@OnClickListener
                }
                if (!isEdit) {
                    selectors.add(
                        Selector(
                            name = selectorName,
                            title = selectorTitle,
                            data = emptyList()
                        )
                    )
                } else {
                    selectors[index] = Selector(
                        name = selectorName,
                        title = selectorTitle,
                        data = selectors.get(index).data
                    )
                }
                saveAll()
                adapter.notifyDataSetChanged()
                dismiss()
            }
            dialogNoListener= View.OnClickListener {
                dismiss()
            }
        }
        dialog.show()
    }
    
    private fun showActionsDialog(
        index: Int
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
                    index = index,
                    isEdit = true
                )
            }
            delete.setOnClickListener {
                dialog.dismiss()
                showConfirmationDialog(
                    message = "Are you sure you want to delete this Selector?",
                    onConfirm = {
                        selectors.removeAt(index)
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
            BLOCK_SELECTOR_MENUS_FILE.absolutePath, 
            gson.toJson(selectors)
        )
        toast("Saved!")
    }
    
    /*
    * A Default list of Selector Itens
    */
    private fun getTypeViewList(): List<String> {
        return listOf(
            "View",
            "ViewGroup",
            "LinearLayout",
            "RelativeLayout",
            "ScrollView",
            "HorizontalScrollView",
            "TextView",
            "EditText",
            "Button",
            "RadioButton",
            "CheckBox",
            "Switch",
            "ImageView",
            "SeekBar",
            "ListView",
            "Spinner",
            "WebView",
            "MapView",
            "ProgressBar"
        )
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}