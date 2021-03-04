package com.becooni.dramaandcompanyproblem.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.becooni.dramaandcompanyproblem.R
import com.becooni.dramaandcompanyproblem.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
    }
}