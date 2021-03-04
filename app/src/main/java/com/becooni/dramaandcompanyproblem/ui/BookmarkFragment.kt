package com.becooni.dramaandcompanyproblem.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.becooni.dramaandcompanyproblem.R
import com.becooni.dramaandcompanyproblem.databinding.FragmentBookmarkBinding
import com.becooni.dramaandcompanyproblem.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private lateinit var binding: FragmentBookmarkBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBookmarkBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = sharedViewModel
    }
}