package pro.sketchware.fragments.settings.selector.block

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.lifecycleScope

import com.sketchware.remod.databinding.FragmentBlockSelectorManagerBinding

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import pro.sketchware.fragments.base.BaseFragment
import pro.sketchware.utility.SketchwareUtil.toast
import pro.sketchware.fragments.settings.selector.block.details.BlockSelectorDetailsFragment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.io.readText

import java.io.File

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
    
    var typeViews: List<ViewType> = emptyList()

    override fun onViewCreated(
        view: View, 
        saved: Bundle?
    ) {
        configureToolbar(binding.toolbar)
        val adapter = BlockSelectorAdapter(
            onClick = { viewType ->
                openFragment(BlockSelectorDetailsFragment(viewType))
            }
        )
        lifecycleScope.launch {
            typeViews = parseJson(
                BLOCK_SELECTOR_MENUS_FILE.readText(
                    Charsets.UTF_8
                )
            )
        }
        binding.list.adapter = adapter
        adapter.submitList(typeViews)
        super.onViewCreated(view, saved)
    }
    
    fun parseJson(jsonString: String): List<ViewType> {
        val gson = Gson()
        val listType = object : TypeToken<List<ViewType>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}