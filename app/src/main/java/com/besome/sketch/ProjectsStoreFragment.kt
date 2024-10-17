package com.besome.sketch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sketchware.remod.databinding.FragmentProjectsStoreBinding

class ProjectsStoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProjectsStoreBinding.inflate(inflater, container, false)
        return binding.root
    }
}
