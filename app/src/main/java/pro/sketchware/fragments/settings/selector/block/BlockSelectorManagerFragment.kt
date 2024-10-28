package pro.sketchware.fragments.settings.selector.block

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.lifecycleScope

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding
import com.sketchware.remod.databinding.DialogBlockConfigurationBinding as DialogCreateBinding

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.utility.FileUtil.writeFile
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
        adapter = BlockSelectorAdapter(
            onClick = { selector ->
                openFragment(BlockSelectorDetailsFragment(selector))
            }
        )
        lifecycleScope.launch {
            selectors = parseJson(
                BLOCK_SELECTOR_MENUS_FILE.readText(
                    Charsets.UTF_8
                )
            )
        }
        binding.list.adapter = adapter
        adapter.submitList(selectors)
        
        binding.createNew.setOnClickListener {
            showCreateNewDialog()
        }
        
        super.onViewCreated(view, saved)
    }
    
    private fun parseJson(jsonString: String): MutableList<Selector> {
        val gson = Gson()
        val listType = object : TypeToken<List<Selector>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
    
    private fun showCreateNewDialog() {
        val dialogBinding = DialogCreateBinding.inflate(LayoutInflater.from(requireContext())).apply {
            tilPalettesPath.hint = "Selector name"
            tilBlocksPath.hint = "Selector title (ex: Select View:)"
        }
        val dialog = aB(requireActivity()).apply {
            dialogTitleText = "New Selector"
            dialogCustomView = dialogBinding.getRoot()
            dialogYesText = "Create"
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
                selectors.add(
                    Selector(
                        name = selectorName,
                        title = selectorTitle,
                        data = emptyList()
                    )
                )
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